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

/**
 * ProductsDAO implements the logic of database table product through ConnectionFactory
 */
public class ProductsDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductsDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (productName, price, productQuantity)" + " VALUES (?, ?, ?)";
    private final static String findStatementString = "SELECT * FROM product where productId = ?";
    private final static String findProdNameStatString = "SELECT * FROM product where productName = ?";
    private final static String deleteStatString = "DELETE FROM product WHERE productId = ?";
    private final static String findAllStatString = "SELECT * FROM product";
    private final static String updateStatString = "UPDATE product SET productName = ?, price = ?, productQuantity = ? WHERE productId = ?";

    /**
     * Method insert inserts a new product in the table
     *
     * @param products Products
     * @return The new generated productId
     */
    public static int insert(Products products) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, products.getProductName());
            insertStatement.setDouble(2, products.getPrice());
            insertStatement.setLong(3, products.getProductQuantity());
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

    /**
     * Method update updates a product after searching it by products.getProductId
     *
     * @param products Products
     */
    public static void update(Products products) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStat = null;
        try {
            updateStat = dbConnection.prepareStatement(updateStatString);
            updateStat.setString(1, products.getProductName());
            updateStat.setDouble(2, products.getPrice());
            updateStat.setLong(3, products.getProductQuantity());
            updateStat.setLong(4, products.getProductId());
            updateStat.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductsDAO: update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStat);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Method deleteById is deleting a product by productId
     *
     * @param productId int
     */
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

    /**
     * Method find is providing all products
     *
     * @return List of Objects Products
     */
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
                int quantity0 = rs.getInt("productQuantity");
                toReturn.add(new Products(productId0, name0, price0, quantity0));
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

    /**
     * Method findById is searching a product by productId
     *
     * @param productId int
     * @return An object of Class Products
     */
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
            int quantity0 = rs.getInt("productQuantity");
            toReturn = new Products(productId0, name0, price0, quantity0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductsDAO: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Method findByProdName is searching a product by prodName
     *
     * @param prodName String
     * @return An object of Class Products
     */
    public static Products findByProdName(String prodName) {
        Products toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findProdNameStatString);
            findStatement.setString(1, prodName);
            rs = findStatement.executeQuery();
            rs.next();

            String name0 = rs.getString("productName");
            Double price0 = rs.getDouble("price");
            int productId0 = rs.getInt("productId");
            int quantity0 = rs.getInt("productQuantity");
            toReturn = new Products(productId0, name0, price0, quantity0);
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