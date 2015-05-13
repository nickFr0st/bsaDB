package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 5/10/2015
 */
public class DatabaseCreator {

    public static String createDatabase(Connection connection, String name, String userName, String password) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + name);
            connection = DriverManager.getConnection(MySqlConnector.DB_PATH + name, userName, password);
            buildDataBase(connection);

        } catch (SQLException sqle) {
            return sqle.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return null;
    }

    private static void buildDataBase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String tableUser = "CREATE TABLE user " +
                "(id INT NOT NULL," +
                " name VARCHAR(90) NULL," +
                " position VARCHAR(90) NULL," +
                " phoneNumber VARCHAR(20) NULL," +
                " street VARCHAR(255) NULL," +
                " city VARCHAR(255) NULL," +
                " zip VARCHAR(10) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableUser);

        statement.executeBatch();
    }
}
