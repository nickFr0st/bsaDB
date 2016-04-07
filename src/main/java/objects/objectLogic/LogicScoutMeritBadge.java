package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ScoutMeritBadge;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: nmalloch
 */
public class LogicScoutMeritBadge {

    public static Set<ScoutMeritBadge> findByAllScoutIdScoutTypeId(int scoutId, int scoutTypeId) {
        Set<ScoutMeritBadge> scoutMeritBadgeSet = new LinkedHashSet<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return scoutMeritBadgeSet;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutMeritBadge WHERE scoutId = " + scoutId + " AND scoutTypeId = " + scoutTypeId + " ORDER BY id");

            while (rs.next()) {
                ScoutMeritBadge scoutMeritBadge = new ScoutMeritBadge();
                scoutMeritBadge.setId(rs.getInt(KeyConst.ID.getName()));
                scoutMeritBadge.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutMeritBadge.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutMeritBadge.setMeritBadgeId(rs.getInt(KeyConst.MERIT_BADGE_ID.getName()));
                scoutMeritBadgeSet.add(scoutMeritBadge);
            }

        } catch (Exception e) {
            return new LinkedHashSet<>();
        }

        return scoutMeritBadgeSet;
    }
}
