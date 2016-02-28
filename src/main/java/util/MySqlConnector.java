package util;

import bsaDb.client.home.dialogs.MessageDialog;
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
    public final static long WAIT_TIME = 500;

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
            new MessageDialog(null, "Cannot Find Property", "Path \"/properties/database.properties\" does not exist.", MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
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
        try {
            properties.load(getClass().getResourceAsStream(DB_PROPERTIES_PATH));
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

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
                    new MessageDialog(parentFrame, "Database Creation Error", response, MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
                }
            }
        } catch (InterruptedException e) {
            new MessageDialog(parentFrame, "Interruption Error", "Database create was interrupted, please try again.", MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            success = false;
        } catch (SQLException sqlE) {
            new MessageDialog(parentFrame, "Database Creation Error", sqlE.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
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
            new MessageDialog(parentFrame, "Connection Error", sqle.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
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
            return -1;
        }

        return 1;
    }
}
