package updater.updates;

import util.MySqlConnector;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016.
 */
public class UpdateVersion_1 {

    public static Long run(Long version) {
        long VERSION = 1L;

        if (version >= VERSION) {
            return version;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("INSERT INTO dbVersion (version) VALUES(" + VERSION + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return VERSION;
    }
}
