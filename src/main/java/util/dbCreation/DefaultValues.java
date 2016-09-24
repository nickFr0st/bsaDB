package util.dbCreation;

import updater.DatabaseUpdater;
import util.dbCreation.advancements.DefaultAdvancements;
import util.dbCreation.meritBadges.DefaultMeritBadges;

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

        addDatabaseVersion(statement);
        addAdminUser(statement);
        statement.executeBatch();

        DefaultAdvancements.addAdvancements(connection);
        DefaultMeritBadges.addMeritBadges(connection);
    }

    private void addDatabaseVersion(Statement statement) throws SQLException{
        statement.addBatch("INSERT INTO dbVersion (version) VALUES(" + DatabaseUpdater.CURRENT_VERSION + ")");
    }

    private void addAdminUser(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO user VALUES(1, 'admin', 'Admin', 'administrator', '', '', '', '', '', false, 'admin', '')");
    }
}
