package util;

import bsaDb.client.dialogs.message.MessageDialog;
import constants.ConnectionConst;
import constants.KeyConst;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
    private final static String HOME_DIR = System.getProperty("user.home");
    private static String DB_PROPERTIES_PATH = HOME_DIR + File.separator + ".bsaDB" + File.separator + "database.properties";
    public static String USER_PROPERTIES_PATH = HOME_DIR + File.separator + ".bsaDB" + File.separator + "user.properties";

    private final static Object lock = new Object();

    private static MySqlConnector connector;

    private Connection connection;
    private Properties properties;

    private MySqlConnector() {
        try {
            checkForFileExists();

            properties = new Properties();
            properties.load(new FileReader(DB_PROPERTIES_PATH));
        } catch (IOException ioe) {
            new MessageDialog(null, "Cannot Find Property", "Path \"/properties/database.properties\" does not exist.", MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            ioe.printStackTrace();
        }
    }

    private void checkForFileExists() {
        try {
            File file = new File(DB_PROPERTIES_PATH);
            File userProperties = new File(USER_PROPERTIES_PATH);
            if (!file.exists()) {
                File dir = new File(HOME_DIR + File.separator + ".bsaDB");
                dir.mkdirs();
                file.createNewFile();
                createDatabasePropertyFile(file.getCanonicalPath());

                userProperties.createNewFile();
                createUserPropertyFile(userProperties.getCanonicalPath());
            }
            DB_PROPERTIES_PATH = file.getCanonicalPath();
            USER_PROPERTIES_PATH = userProperties.getCanonicalPath();
        } catch (IOException ignore) {
        }
    }

    private void createUserPropertyFile(String filePath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            Properties prop = new Properties();
            prop.setProperty(KeyConst.SAVED_USER.getName(), "");
            prop.setProperty(KeyConst.CURRENT_USER.getName(), "");
            prop.store(writer, null);
        } catch (IOException e) {
            // do something
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore) {
                }
            }
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

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ConnectionConst checkConnection() {
        try {
            properties.load(new FileReader(DB_PROPERTIES_PATH));
            String dbName = properties.getProperty(KeyConst.DB_NAME.getName());
            String userName = properties.getProperty(KeyConst.DB_USER_NAME.getName());
            String password = properties.getProperty(KeyConst.DB_PASSWORD.getName());

            if (Util.isEmpty(dbName) || userName == null || password == null) {
                return ConnectionConst.NO_SERVER_CONNECTION;
            }

            Class.forName(DRIVER).newInstance();

            // test the connection
            connection = DriverManager.getConnection(DB_PATH + dbName, userName, password);
        } catch (SQLException sqle) {
            if (sqle.getErrorCode() == 0) {
                return ConnectionConst.NO_SERVER_CONNECTION;
            } else {
                return ConnectionConst.NO_DATABASE_CONNECTION;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ConnectionConst.NO_SERVER_CONNECTION;
        }

        return ConnectionConst.CONNECTION_GOOD;
    }

    private void createDatabasePropertyFile(String filePath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            Properties prop = new Properties();
            prop.setProperty(KeyConst.DB_NAME.getName(), "");
            prop.setProperty(KeyConst.DB_USER_NAME.getName(), "");
            prop.setProperty(KeyConst.DB_PASSWORD.getName(), "");
            prop.setProperty(KeyConst.DB_URL.getName(), "jdbc:mysql://localhost:3306/");
            prop.store(writer, null);
        } catch (IOException e) {
            // do something
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    private void resetProperties(String name, String rootUser, String rootPassword) {
        checkForFileExists();

        properties.setProperty(KeyConst.DB_NAME.getName(), name);
        properties.setProperty(KeyConst.DB_USER_NAME.getName(), rootUser);
        properties.setProperty(KeyConst.DB_PASSWORD.getName(), rootPassword);

        Util.saveProperties(properties, DB_PROPERTIES_PATH);
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

        return -1;
    }
}
