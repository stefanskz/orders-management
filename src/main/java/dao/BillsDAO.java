package dao;

import connection.ConnectionFactory;
import model.Bills;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillsDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientsDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO bill (orderId, clientId, productId, productQuantity, totalPrice)" + " VALUES (?, ?, ?, ?, ?)";
    private static final String findClientIdStatString = "SELECT * FROM bill where clientId = ?";

    public static int insert(Bills bills) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setLong(1, bills.orderId());
            insertStatement.setLong(2, bills.clientId());
            insertStatement.setLong(3, bills.productId());
            insertStatement.setLong(4, bills.productQuantity());
            insertStatement.setDouble(5, bills.totalPrice());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillsDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static List<Bills> find(int clientId0) {
        List<Bills> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStat = null;
        ResultSet rs = null;
        try {
            findStat = dbConnection.prepareStatement(findClientIdStatString);
            findStat.setLong(1, clientId0);
            rs = findStat.executeQuery();
            while (rs.next()) {
                int newOrderId = rs.getInt("orderId");
                int newClientId = rs.getInt("clientId");
                int newProductId = rs.getInt("productId");
                int newQuantity = rs.getInt("productQuantity");
                Double newTotal = rs.getDouble("totalPrice");
                toReturn.add(new Bills(newOrderId, newClientId, newProductId, newQuantity, newTotal));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillsDAO: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStat);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

}