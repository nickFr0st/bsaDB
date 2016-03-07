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
            insertDefaultValues(connection);

        } catch (SQLException sqle) {
            return sqle.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return null;
    }

    private static void insertDefaultValues(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO user VALUES(1, 'admin', 'Admin', 'administrator', '', '', '', '', '', false, 'admin', '')");
    }

    private static void buildDataBase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String tableUser = "CREATE TABLE user " +
                "(id INT NOT NULL," +
                " username VARCHAR(30) NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " position VARCHAR(90) NULL," +
                " phoneNumber VARCHAR(20) NULL," +
                " state VARCHAR(255) NULL," +
                " city VARCHAR(255) NULL," +
                " street VARCHAR(255) NULL," +
                " zip VARCHAR(10) NULL," +
                " editable BOOL NOT NULL," +
                " password VARCHAR(90) NOT NULL," +
                " email VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableUser);

        String tableAccessRight = "CREATE TABLE accessRight " +
                "(id INT NOT NULL," +
                " userId INT NOT NULL," +
                " rightId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableAccessRight);

        String tableAdvancement = "CREATE TABLE advancement " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableAdvancement);

        String tableMeritBadge = "CREATE TABLE meritBadge " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NOT NULL," +
                " requiredForEagle TINYINT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableMeritBadge);

        String tableRequirement = "CREATE TABLE requirement " +
                "(id INT NOT NULL," +
                " name VARCHAR(45) NOT NULL," +
                " description BLOB NOT NULL," +
                " typeId INT NOT NULL," +
                " parentId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableRequirement);

        String tableCounselor = "CREATE TABLE counselor " +
                "(id INT NOT NULL," +
                " badgeId INT NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " phoneNumber VARCHAR(20) NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableCounselor);

        String tableBoyScout = "CREATE TABLE boyScout " +
                "(id INT NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " position VARCHAR(90) NULL," +
                " birthDate DATE NOT NULL," +
                " advancementId INT NULL," +
                " advancementDate DATE NULL," +
                " phoneNumber VARCHAR(20) NULL," +
                " guardianName VARCHAR(90) NULL," +
                " guardianPhoneNumber VARCHAR(20) NULL," +
                " street VARCHAR(255) NULL," +
                " city VARCHAR(255) NULL," +
                " state VARCHAR(255) NULL," +
                " zip VARCHAR(10) NULL," +
                " note BLOB NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableBoyScout);

        String tableSpecialAward = "CREATE TABLE specialAward " +
                "(id INT NOT NULL," +
                "scoutId INT NOT NULL," +
                "scoutTypeId INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(tableSpecialAward);

        statement.executeBatch();
    }
}
