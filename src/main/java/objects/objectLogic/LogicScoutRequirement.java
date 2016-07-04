package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ScoutRequirement;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * User: nmalloch
 */
public class LogicScoutRequirement {

    public static Set<ScoutRequirement> findByAllScoutIdScoutTypeIdAndAdvancementId(int scoutId, int scoutTypeId, int advancementId) {
        Set<ScoutRequirement> scoutRequirementSet = new LinkedHashSet<>();

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
                scoutRequirementSet.add(scoutRequirement);
            }

        } catch (Exception e) {
            return new LinkedHashSet<>();
        }

        return scoutRequirementSet;
    }

    public static ScoutRequirement findByScoutIdScoutTypeIdAdvancementIdAndRequirementId(int scoutId, int scoutTypeId, int advancementId, int requirementId) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutRequirement WHERE scoutId = " + scoutId + " AND scoutTypeId = " + scoutTypeId + " AND advancementId = " + advancementId + " AND requirementId = " + requirementId);

            if (rs.next()) {
                ScoutRequirement scoutRequirement = new ScoutRequirement();
                scoutRequirement.setId(rs.getInt(KeyConst.ID.getName()));
                scoutRequirement.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutRequirement.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutRequirement.setAdvancementId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                scoutRequirement.setRequirementId(rs.getInt(KeyConst.REQUIREMENT_ID.getName()));
                scoutRequirement.setDateCompleted(rs.getDate(KeyConst.REQUIREMENT_DATE_COMPLETED.getName()));
                return scoutRequirement;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static synchronized void delete(Collection<ScoutRequirement> scoutCampList) {
        if (Util.isEmpty(scoutCampList)) {
            return;
        }

        for (ScoutRequirement scoutRequirement : scoutCampList) {
            delete(scoutRequirement.getId());
        }
    }

    public static synchronized void delete(final Integer id) {
        if (id < 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteScoutRequirement(id);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteScoutRequirement(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM scoutRequirement WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void save(List<ScoutRequirement> scoutRequirementList) {
        if (Util.isEmpty(scoutRequirementList)) {
            return;
        }

        for (ScoutRequirement scoutRequirement : scoutRequirementList) {
            save(scoutRequirement);
        }
    }

    public static synchronized ScoutRequirement save(final ScoutRequirement scoutRequirement) {
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveScoutRequirement(scoutRequirement);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return scoutRequirement;
    }

    private static void saveScoutRequirement(ScoutRequirement scoutRequirement) {
        if (scoutRequirement.getId() < 0) {
            scoutRequirement.setId(MySqlConnector.getInstance().getNextId("scoutRequirement"));
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO scoutRequirement VALUES(");
            query.append(scoutRequirement.getId()).append(", ");
            query.append(scoutRequirement.getScoutId()).append(", ");
            query.append(scoutRequirement.getScoutTypeId()).append(", ");
            query.append(scoutRequirement.getAdvancementId()).append(", ");
            query.append(scoutRequirement.getRequirementId()).append(", ");
            query.append("'").append(Util.DATA_BASE_DATE_FORMAT.format(scoutRequirement.getDateCompleted())).append("'");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized ScoutRequirement update(final ScoutRequirement scoutRequirement) {
        if (scoutRequirement == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateBoyScout(scoutRequirement);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return scoutRequirement;
    }

    private static void updateBoyScout(ScoutRequirement scoutRequirement) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE scoutRequirement SET ");
            query.append("birthCompleted = '").append(Util.DATA_BASE_DATE_FORMAT.format(scoutRequirement.getDateCompleted())).append("' ");
            query.append("WHERE id = ").append(scoutRequirement.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
