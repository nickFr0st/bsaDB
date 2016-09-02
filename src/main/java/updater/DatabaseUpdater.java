package updater;

import updater.updates.UpdateVersion_1;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016
 */
public class DatabaseUpdater {
    private static final Long CURRENT_VERSION = 1L;

    public static void runUpdates() {
        Long version = 0L;
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(version) AS version FROM dbVersion");

            if (rs.next()) {
                version = rs.getLong("version");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (version.equals(CURRENT_VERSION)) {
             return;
        }

        update(version);
    }

    private static void update(Long version) {
        if (version < 1L) {
            UpdateVersion_1.run();
            version = 1L;
        }
    }
}
