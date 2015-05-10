package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 5/10/2015
 */
public class DatabaseCreator {

    public static String createDatabase(Connection connection, String name) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + name);
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

        statement.executeBatch();
    }
}
