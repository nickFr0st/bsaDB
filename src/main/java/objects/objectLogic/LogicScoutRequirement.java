package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ScoutRequirement;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: nmalloch
 */
public class LogicScoutRequirement {

    public static Set<ScoutRequirement> findByAllScoutIdScoutTypeIdAndAdvancementId(int scoutId, int scoutTypeId, int advancementId) {
        Set<ScoutRequirement> scoutRequirementSet = new LinkedHashSet<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return scoutRequirementSet;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutRequirement WHERE scoutId = " + scoutId + " AND scoutTypeId = " + scoutTypeId + " AND advancementId = " + advancementId + " ORDER BY id");

            while (rs.next()) {
                ScoutRequirement scoutRequirement = new ScoutRequirement();
                scoutRequirement.setId(rs.getInt(KeyConst.ID.getName()));
                scoutRequirement.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutRequirement.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutRequirement.setAdvancementId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                scoutRequirement.setRequirementId(rs.getInt(KeyConst.REQUIREMENT_ID.getName()));
                scoutRequirement.setDateCompleted(rs.getDate(KeyConst.REQUIREMENT_DATE_COMPLETED.getName()));
                scoutRequirement.setNote(rs.getString(KeyConst.NOTE.getName()));
                scoutRequirementSet.add(scoutRequirement);
            }

        } catch (Exception e) {
            return new LinkedHashSet<>();
        }

        return scoutRequirementSet;
    }
}
