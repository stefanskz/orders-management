package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import start.ReflexiveSelection;

/**
 * Abstract Data Access to the Database
 * @param <T> Class
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }

    /**
     * Method which provides the query for update
     *
     * @param toUpdate List of Strings
     * @return String
     */
    private String updateString(List<String> toUpdate) {
        StringBuilder string = new StringBuilder();
        string.append("UPDATE ");
        string.append(type.getSimpleName());
        string.append(" SET ");
        boolean ok = false, next = false;
        for (String index : toUpdate) {
            if (ok) {
                if (next)
                    string.append(", ");
                string.append(index).append(" = ?");
                next = true;
            }
            ok = true;
        }
        string.append(" WHERE ");
        string.append(toUpdate.get(0));
        string.append(" = ?");
        return string.toString();
    }

    /**
     * Method to update an object from any table
     *
     * @param toUpdate T
     */
    public void absUpdate(T toUpdate) {
        List<Object> objectList = new ArrayList<>();
        objectList.add(toUpdate);
        String[][] updateData = ReflexiveSelection.retrieveProp(objectList);
        List<String> strings = new ArrayList<>();
        int n = type.getDeclaredFields().length;
        for (int i = 0; i < n; i++) {
            strings.add(updateData[0][i]);
        }
        String toExecute = updateString(strings);
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dbConnection.prepareStatement(toExecute);
            for (int i = 1; i < n; i++) {
                preparedStatement.setString(i, updateData[1][i]);
            }
            preparedStatement.setInt(n, Integer.parseInt(updateData[1][0]));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:absDelete " + e.getMessage());
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Method which provides the query for delete
     *
     * @param toDelete String
     * @return String
     */
    private String deleteString(String toDelete) {
        StringBuilder string = new StringBuilder();
        string.append("DELETE ");
        string.append("FROM ");
        string.append(type.getSimpleName());
        string.append(" WHERE ").append(toDelete).append(" = ?");
        return string.toString();
    }

    /**
     * Method to delete an object from any table
     *
     * @param idToDelete int
     */
    public void deleteById(int idToDelete) {
        PreparedStatement preparedStatement = null;
        Connection dbConnection = null;
        String toExecute = deleteString("id");
        try {
            dbConnection = ConnectionFactory.getConnection();
            preparedStatement = dbConnection.prepareStatement(toExecute);
            preparedStatement.setInt(1, idToDelete);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:absDelete " + e.getMessage());
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Method which provides the query for find
     *
     * @param field String
     * @return String
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(field).append(" = ?");
        return sb.toString();
    }

    /**
     * Method to find an object from any table
     *
     * @param id int
     * @return Object
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Method to create an Object
     *
     * @param resultSet ResultSet
     * @return List of Object
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Method which provides the query for insert
     *
     * @param toInsert List of Strings
     * @return String
     */
    private String insertString(List<String> toInsert) {
        StringBuilder string = new StringBuilder();
        string.append("INSERT ");
        string.append("INTO ");
        string.append(type.getSimpleName());
        string.append(" (");
        boolean ok = false;
        int k = 0;
        for (String index : toInsert) {
            if (ok)
                string.append(", ");
            ok = true;
            k++;
            string.append(index);
        }
        ok = false;
        string.append(")");
        string.append(" VALUES ");
        string.append("(");
        while (k != 0) {
            if (ok) {
                string.append(", ");
            }
            ok = true;
            string.append("?");
            k--;
        }
        string.append(")");
        return string.toString();
    }

    /**
     * Method to insert an object into any table
     *
     * @param toInsert T
     * @return new generated id
     */
    public int absInsert(T toInsert) {
        List<Object> objectList = new ArrayList<>();
        objectList.add(toInsert);
        String[][] insertData = ReflexiveSelection.retrieveProp(objectList);
        List<String> strings = new ArrayList<>();
        int n = type.getDeclaredFields().length;
        for (int i = 1; i < n; i++) {
            strings.add(insertData[0][i]);
        }
        String toExecute = insertString(strings);
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        int insertedId = -1;
        try {
            preparedStatement = dbConnection.prepareStatement(toExecute, Statement.RETURN_GENERATED_KEYS);
            for (int i = 1; i < n; i++) {
                preparedStatement.setString(i, insertData[1][i]);
            }
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:absInsert " + e.getMessage());
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }
}