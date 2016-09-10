package updater.updates;

import util.MySqlConnector;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016.
 */
public class UpdateVersion_4 {

    private final static int VERSION = 4;

    public static void run() {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            addNewVersionData(statement);

            addServiceHourToAdvancement(statement);

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addServiceHourToAdvancement(Statement statement) throws SQLException {
        statement.addBatch("ALTER TABLE advancement ADD COLUMN serviceHours DOUBLE PRECISION");
    }

    private static void addNewVersionData(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO dbVersion (version) VALUES( " + VERSION + ")");
    }
}
