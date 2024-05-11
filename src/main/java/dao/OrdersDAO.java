package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Orders;

public class OrdersDAO {
    protected static final Logger LOGGER = Logger.getLogger(OrdersDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO orders (clientId, productId, quantity)" + " VALUES (?, ?, ?)";
    private static final String findAllStatString = "SELECT * FROM orders";

    public static int insert(Orders orders) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setLong(1, orders.getClientId());
            insertStatement.setLong(2, orders.getProductId());
            insertStatement.setLong(3, orders.getQuantity());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrdersDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static List<Orders> find() {
        List<Orders> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStat = null;
        ResultSet rs = null;
        try {
            findStat = dbConnection.prepareStatement(findAllStatString);
            rs = findStat.executeQuery();
            while (rs.next()) {
                int orderId0 = rs.getInt("orderId");
                int clientId0 = rs.getInt("clientId");
                int productId0 = rs.getInt("productId");
                int quantity0 = rs.getInt("quantity");
                toReturn.add(new Orders(orderId0, clientId0, productId0, quantity0));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrdersDAO: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStat);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }
}