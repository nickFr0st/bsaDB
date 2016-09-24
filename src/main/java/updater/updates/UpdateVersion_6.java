package updater.updates;

import util.MySqlConnector;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016.
 */
public class UpdateVersion_6 {

    private final static Long VERSION = 6L;

    public static Long run(Long version) {

        if (version >= VERSION) {
            return version;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            addNewVersionData(statement);

            makeRequirementIdAutoIncrement(statement);

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VERSION;
    }

    private static void makeRequirementIdAutoIncrement(Statement statement) throws SQLException {
        statement.addBatch("ALTER TABLE requirement MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT");
    }

    private static void addNewVersionData(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO dbVersion (version) VALUES( " + VERSION + ")");
    }
}
