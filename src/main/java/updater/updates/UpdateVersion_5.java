package updater.updates;

import util.MySqlConnector;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016.
 */
public class UpdateVersion_5 {

    private final static int VERSION = 5;

    public static void run() {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            addNewVersionData(statement);

            addReadOnlyFieldToAdvancement(statement);
            addReadOnlyFieldToMeritBadge(statement);

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addReadOnlyFieldToAdvancement(Statement statement) throws SQLException {
        statement.addBatch("ALTER TABLE advancement ADD COLUMN readOnly TINYINT NOT NULL DEFAULT 0");
    }


    private static void addReadOnlyFieldToMeritBadge(Statement statement) throws SQLException {
        statement.addBatch("ALTER TABLE meritBadge ADD COLUMN readOnly TINYINT NOT NULL DEFAULT 0");
    }

    private static void addNewVersionData(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO dbVersion (version) VALUES( " + VERSION + ")");
    }
}
