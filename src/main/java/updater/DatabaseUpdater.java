package updater;

import updater.updates.UpdateVersion_1;
import updater.updates.UpdateVersion_2;
import updater.updates.UpdateVersion_3;
import updater.updates.UpdateVersion_4;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016
 */
public class DatabaseUpdater {
    public static final Long CURRENT_VERSION = 4L;

    public static void runUpdates() {
        Long version = getDbVersion();
        if (version.equals(CURRENT_VERSION)) {
             return;
        }

        update(version);
    }

    private static Long getDbVersion() {
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
        return version;
    }

    private static void update(Long version) {
        if (version < 1L) {
            UpdateVersion_1.run();
            version = 1L;
        }

        if (version < 2L) {
            UpdateVersion_2.run();
            version = 2L;
        }

        if (version < 3L) {
            UpdateVersion_3.run();
            version = 3L;
        }

        if (version < 4L) {
            UpdateVersion_4.run();
            version = 4L;
        }
    }
}
