package util;

import constants.KeyConst;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Nathanael on 5/9/2015
 */
public class MySqlConnector {
    public final static String DB_PATH = "jdbc:mysql://localhost:3306/";
    public final static long WAIT_TIME = 1000;

    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_PROPERTIES_PATH = "/properties/database.properties";
    private final static Object lock = new Object();

    private static MySqlConnector connector;

    private Connection connection;
    private Properties properties;

    private MySqlConnector() {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream(DB_PROPERTIES_PATH));
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Path \"/properties/database.properties\" does not exist.", "Cannot find Property", JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
        }
    }

    public static MySqlConnector getInstance() {
        if (connector == null) {
            connector = new MySqlConnector();
        }

        return connector;
    }

    public Properties getProperties() {
        return properties;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean checkForDataBaseConnection() {
        String dbName;
        String userName = null;
        String password = null;

        try {
            properties.load(getClass().getResourceAsStream(DB_PROPERTIES_PATH));
            dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

            if (Util.isEmpty(dbName) || userName == null || password == null) {
                return false;
            }

            Class.forName(DRIVER).newInstance();

            // test the connection
            connection = DriverManager.getConnection(DB_PATH + dbName, userName, password);
        } catch (SQLException ignore) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void resetProperties(String name, String rootUser, String rootPassword) {
        properties.setProperty(KeyConst.DB_NAME.getName(), name);
        properties.setProperty(KeyConst.DB_USER_NAME.getName(), rootUser);
        properties.setProperty(KeyConst.DB_PASSWORD.getName(), rootPassword);

        Util.saveProperties(properties, getClass().getResource(DB_PROPERTIES_PATH).toString());
    }

    public boolean createDatabase(Frame parentFrame, String name, String rootUser, String rootPassword) {
        boolean success = true;

        try {
            Class.forName(DRIVER).newInstance();
            connection = DriverManager.getConnection(DB_PATH + "?user=" + rootUser + "&password=" + rootPassword);

            synchronized (lock) {
                String response;
                if (Util.isEmpty(response = DatabaseCreator.createDatabase(connection, name, rootUser, rootPassword))) {
                    lock.wait(WAIT_TIME);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, response);
                }
            }
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(parentFrame, "Database create was interrupted, please try again.", "Interruption Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(parentFrame, sqlE.getMessage(), "Creation Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("\n\n******Error in creating database********\n\n");
            sqlE.printStackTrace();
            success = false;
        } catch (Exception ignore) {
            success = false;
        }

        resetProperties(name, rootUser, rootPassword);
        return success;
    }

    public boolean connectToDatabase(JFrame parentFrame, String name, String rootUser, String rootPassword) {
        boolean success = true;

        try {
            Class.forName(DRIVER).newInstance();
            connection = DriverManager.getConnection(DB_PATH + name, rootUser, rootPassword);

        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(parentFrame, sqle.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            success = false;
        } catch (Exception ignore) {
            success = false;
        }

        resetProperties(name, rootUser, rootPassword);
        return success;
    }

    public int getNextId(String tableName) {
        if (!checkForDataBaseConnection()) {
            return -1;
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(id) as id FROM " + tableName);

            if (rs.next()) {
                return rs.getInt(KeyConst.ID.getName()) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
