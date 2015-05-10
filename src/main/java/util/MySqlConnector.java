package util;

import constants.KeyConst;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Nathanael on 5/9/2015
 */
public class MySqlConnector {
    public final static String DB_PATH = "jdbc:mysql://localhost:3306/";
    public final static long WAIT_TIME = 3000;

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

    public boolean checkForDataBaseConnection() {
        try {
            properties.load(getClass().getResourceAsStream(DB_PROPERTIES_PATH));
            String url = properties.getProperty(KeyConst.DB_URL.getName());
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

            if (Util.isEmpty(url) || Util.isEmpty(dbName) || userName == null || password == null) {
                return false;
            }

            Class.forName(DRIVER).newInstance();

            // test the connection
            connection = DriverManager.getConnection(url + dbName, userName, password);

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
                if (Util.isEmpty(response = DatabaseCreator.createDatabase(connection, name))) {
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
}
