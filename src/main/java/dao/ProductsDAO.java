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
import model.Products;

public class ProductsDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductsDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (productName, price)" + " VALUES (?, ?)";
    private final static String findStatementString = "SELECT * FROM product where productId = ?";
    private final static String deleteStatString = "DELETE FROM product WHERE productId = ?";
    private final static String findAllStatString = "SELECT * FROM product";
    private final static String updateStatString = "UPDATE product SET productName = ?, price = ? WHERE productId = ?";


    public static int insert(Products products) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, products.getProductName());
            insertStatement.setDouble(2, products.getPrice());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductsDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static void update(Products products) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStat = null;
        try {
            updateStat = dbConnection.prepareStatement(updateStatString);
            updateStat.setString(1, products.getProductName());
            updateStat.setDouble(2, products.getPrice());
            updateStat.setLong(3, products.getProductId());
            updateStat.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductsDAO: update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStat);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static void deleteById(int productId) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStat = null;
        try {
            deleteStat = dbConnection.prepareStatement(deleteStatString);
            deleteStat.setLong(1, productId);
            deleteStat.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductsDAO: delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStat);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static List<Products> find() {
        List<Products> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStat = null;
        ResultSet rs = null;
        try {
            findStat = dbConnection.prepareStatement(findAllStatString);
            rs = findStat.executeQuery();
            while (rs.next()) {
                String name0 = rs.getString("productName");
                Double price0 = rs.getDouble("price");
                int productId0 = rs.getInt("productId");
                toReturn.add(new Products(productId0, name0, price0));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductsDAO: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStat);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static Products findById(int productId) {
        Products toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, productId);
            rs = findStatement.executeQuery();
            rs.next();

            String name0 = rs.getString("productName");
            Double price0 = rs.getDouble("price");
            int productId0 = rs.getInt("productId");
            toReturn = new Products(productId0, name0, price0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductsDAO: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }
}