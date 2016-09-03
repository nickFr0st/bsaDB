package updater.updates;

import util.MySqlConnector;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016.
 */
public class UpdateVersion_3 {

    private final static int VERSION = 3;

    public static void run() {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            addNewVersionData(statement);

            createImageTable(statement);
            createServiceProjectTable(statement);
            createScoutServiceProjectTable(statement);

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createImageTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE image" +
                " (id INT NOT NULL AUTO_INCREMENT," +
                " path BLOB NOT NULL," +
                " typeId INT NOT NULL," +
                " recordId INT NOT NULL," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createServiceProjectTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE serviceProject" +
                " (id INT NOT NULL AUTO_INCREMENT," +
                " startDate DATE NOT NULL," +
                " duration DOUBLE DEFAULT 0 NOT NULL," +
                " scoutTypeId INT NOT NULL," +
                " name VARCHAR(255) NOT NULL," +
                " location VARCHAR(255) NOT NULL," +
                " leaders VARCHAR(255) NOT NULL," +
                " note BLOB," +
                " PRIMARY KEY (id))";
        statement.addBatch(query);
    }

    private static void createScoutServiceProjectTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE scoutServiceProject" +
                " (id INT NOT NULL AUTO_INCREMENT," +
                " scoutId INT NOT NULL," +
                " scoutTypeId INT NOT NULL," +
                " serviceProjectId INT NOT NULL," +
                " PRIMARY KEY (id)," +
                " INDEX fk_scout_service_project_idx (serviceProjectId)," +
                " CONSTRAINT fk_scout_service_project" +
                " FOREIGN KEY (serviceProjectId)" +
                " REFERENCES serviceProject(id))";
        statement.addBatch(query);
    }

    private static void addNewVersionData(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO dbVersion (version) VALUES( " + VERSION + ")");
    }
}
