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

            buildDatabase(connection);
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

    private static void buildDatabase(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        createDbVersionTable(statement);
        createUserTable(statement);
        createAccessRightTable(statement);
        createAdvancementTable(statement);
        createMeritBadgeTable(statement);
        createRequirementTable(statement);
        createCounselorTable(statement);
        createBoyScoutTable(statement);
        createSpecialAwardTable(statement);
        createScoutRequirementTable(statement);
        createScoutMeritBadgeTable(statement);
        createCampTable(statement);
        createScoutCampTable(statement);

        statement.executeBatch();
    }

    private static void createDbVersionTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE dbversion " +
                "(id INT NOT NULL AUTO_INCREMENT," +
                " version BIGINT(19) NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createUserTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE user " +
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
        statement.addBatch(query);
    }

    private static void createAccessRightTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE accessRight " +
                "(id INT NOT NULL," +
                " userId INT NOT NULL," +
                " rightId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createAdvancementTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE advancement " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " timeRequirement INT NULL," +
                " imgPath VARCHAR(255) NULL," +
                " nextAdvancementId INT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createMeritBadgeTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE meritBadge " +
                "(id INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " imgPath VARCHAR(255) NOT NULL," +
                " requiredForEagle TINYINT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createRequirementTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE requirement " +
                "(id INT NOT NULL," +
                " name VARCHAR(45) NOT NULL," +
                " description BLOB NOT NULL," +
                " typeId INT NOT NULL," +
                " parentId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createCounselorTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE counselor " +
                "(id INT NOT NULL," +
                " badgeId INT NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " phoneNumber VARCHAR(20) NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createBoyScoutTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE boyScout " +
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
        statement.addBatch(query);
    }

    private static void createSpecialAwardTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE specialAward " +
                "(id INT NOT NULL," +
                " scoutId INT NOT NULL," +
                " scoutTypeId INT NOT NULL," +
                " name VARCHAR(225) NOT NULL," +
                " description BLOB NULL," +
                " dateReceived DATE NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createScoutRequirementTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE scoutRequirement " +
                "(id INT NOT NULL," +
                " scoutId INT NOT NULL," +
                " scoutTypeId INT NOT NULL," +
                " advancementId INT NOT NULL," +
                " requirementId INT NOT NULL," +
                " dateCompleted DATE NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createScoutMeritBadgeTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE scoutMeritBadge " +
                "(id INT NOT NULL," +
                " scoutId INT NOT NULL," +
                " scoutTypeId INT NOT NULL," +
                " meritBadgeId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createCampTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE camp " +
                "(id INT NOT NULL," +
                " name VARCHAR(90) NOT NULL," +
                " scoutTypeId INT NOT NULL," +
                " location VARCHAR(255) NULL," +
                " startDate DATE NOT NULL," +
                " leaders VARCHAR(255) NULL," +
                " note BLOB NULL," +
                " numberOfNights INT NOT NULL DEFAULT 1," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createScoutCampTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE scoutCamp " +
                "(id INT NOT NULL," +
                " scoutId INT NOT NULL," +
                " scoutTypeId INT NOT NULL," +
                " campId INT NOT NULL," +
                " numberOfNights INT NOT NULL DEFAULT 1," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }
}
