package util.dbCreation.advancements;

import util.dbCreation.advancements.objects.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Nathanael on 9/21/2016.
 */
public class DefaultAdvancements {
    public static void addAdvancements(Connection connection) throws SQLException {
        DefaultNewScout.execute(connection);
        DefaultTenderfoot.execute(connection);
        DefaultSecondClass.execute(connection);
        DefaultFirstClass.execute(connection);
        DefaultStar.execute(connection);
        DefaultLife.execute(connection);
        DefaultEagle.execute(connection);
    }
}
