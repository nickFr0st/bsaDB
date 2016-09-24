package updater;

import updater.updates.*;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016
 */
public class DatabaseUpdater {
    public static final Long CURRENT_VERSION = 7L;

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
        version = UpdateVersion_1.run(version);
        version = UpdateVersion_2.run(version);
        version = UpdateVersion_3.run(version);
        version = UpdateVersion_4.run(version);
        version = UpdateVersion_5.run(version);
        version = UpdateVersion_6.run(version);
        version = UpdateVersion_7.run(version);
    }
}
