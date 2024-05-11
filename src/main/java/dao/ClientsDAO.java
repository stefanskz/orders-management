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
import model.Clients;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class ClientsDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientsDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name, email)" + " VALUES (?, ?)";
    private final static String findStatementString = "SELECT * FROM client where clientId = ?";
    private final static String deleteStatString = "DELETE FROM client WHERE clientId = ?";
    private final static String findAllStatString = "SELECT * FROM client";
    private final static String updateStatString = "UPDATE client SET name = ?, email = ? WHERE clientId = ?";

    public static Clients findById(int clientId) {
        Clients toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, clientId);
            rs = findStatement.executeQuery();
            rs.next();

            String name0 = rs.getString("name");
            String email0 = rs.getString("email");
            int clientId1 = rs.getInt("clientId");
            toReturn = new Clients(clientId1, name0, email0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientsDAO: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Clients clients) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, clients.getName());
            insertStatement.setString(2, clients.getEmail());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientsDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static void deleteById(int clientId) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStat = null;
        try {
            deleteStat = dbConnection.prepareStatement(deleteStatString);
            deleteStat.setLong(1, clientId);
            deleteStat.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientsDAO: delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStat);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static List<Clients> find() {
        List<Clients> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStat = null;
        ResultSet rs = null;
        try {
            findStat = dbConnection.prepareStatement(findAllStatString);
            rs = findStat.executeQuery();
            while (rs.next()) {
                String name0 = rs.getString("name");
                String email0 = rs.getString("email");
                int clientId = rs.getInt("clientId");
                toReturn.add(new Clients(clientId, name0, email0));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientsDAO: find " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStat);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static void update(Clients clients) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStat = null;
        try {
            updateStat = dbConnection.prepareStatement(updateStatString);
            updateStat.setString(1, clients.getName());
            updateStat.setString(2, clients.getEmail());
            updateStat.setLong(3, clients.getClientId());
            updateStat.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientsDAO: update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStat);
            ConnectionFactory.close(dbConnection);
        }
    }
}