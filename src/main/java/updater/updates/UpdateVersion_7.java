package updater.updates;

import util.MySqlConnector;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016.
 */
public class UpdateVersion_7 {

    private final static Long VERSION = 7L;

    public static Long run(Long version) {
        if (version >= VERSION) {
            return version;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            addNewVersionData(statement);

            makemeritBadgeIdAutoIncrement(statement);

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VERSION;
    }

    private static void makemeritBadgeIdAutoIncrement(Statement statement) throws SQLException {
        statement.addBatch("ALTER TABLE meritBadge MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT");
    }

    private static void addNewVersionData(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO dbVersion (version) VALUES( " + VERSION + ")");
    }
}
