package util.dbCreation;

import updater.DatabaseUpdater;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/20/2016.
 */
public class DefaultValues {

    private static DefaultValues defaultValues;

    private DefaultValues() {
    }

    public static DefaultValues getInstance() {
        if (defaultValues == null) {
            defaultValues = new DefaultValues();
        }

        return defaultValues;
    }

    public void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.addBatch("INSERT INTO user VALUES(1, 'admin', 'Admin', 'administrator', '', '', '', '', '', false, 'admin', '')");
        statement.addBatch("INSERT INTO dbVersion (version) VALUES(" + DatabaseUpdater.CURRENT_VERSION + ")");
        statement.executeBatch();
    }
}
