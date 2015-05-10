package util;

import constants.KeyConst;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by Nathanael on 5/9/2015
 */
public class MySqlConnector {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_PATH = "jdbc:mysql://localhost:3306/";
    private final static String DB_PROPERTIES_PATH = "/properties/database.properties";

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
}
