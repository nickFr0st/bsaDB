package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ScoutMeritBadge;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * User: nmalloch
 */
public class LogicScoutMeritBadge {

    public static Set<ScoutMeritBadge> findByAllScoutIdScoutTypeId(int scoutId, int scoutTypeId) {
        Set<ScoutMeritBadge> scoutMeritBadgeSet = new LinkedHashSet<>();

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

    public static synchronized void delete(Set<ScoutMeritBadge> scoutMeritBadgeSet) {
        if (Util.isEmpty(scoutMeritBadgeSet)) {
            return;
        }

        for (ScoutMeritBadge scoutMeritBadge : scoutMeritBadgeSet) {
            delete(scoutMeritBadge.getId());
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
                    deleteScoutMeritBadge(id);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteScoutMeritBadge(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM scoutMeritBadge WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized List<ScoutMeritBadge> save(List<ScoutMeritBadge> scoutMeritBadgeList) {
        if (scoutMeritBadgeList == null) {
            return null;
        }

        for (final ScoutMeritBadge scoutMeritBadge : scoutMeritBadgeList) {
            try {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveScoutMeritBadge(scoutMeritBadge);
                    }
                });

                t.start();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return scoutMeritBadgeList;
    }

    public static synchronized ScoutMeritBadge save(final ScoutMeritBadge scoutMeritBadge) {
        if (scoutMeritBadge == null) {
            return null;
        }

            try {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveScoutMeritBadge(scoutMeritBadge);
                    }
                });

                t.start();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return scoutMeritBadge;
    }

    private static void saveScoutMeritBadge(ScoutMeritBadge scoutMeritBadge) {
        if (scoutMeritBadge.getId() < 0) {
            scoutMeritBadge.setId(MySqlConnector.getInstance().getNextId("scoutMeritBadge"));
        }

        if (scoutMeritBadge.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO scoutMeritBadge VALUES(");
            query.append(scoutMeritBadge.getId()).append(", ");
            query.append(scoutMeritBadge.getScoutId()).append(", ");
            query.append(scoutMeritBadge.getScoutTypeId()).append(", ");
            query.append(scoutMeritBadge.getMeritBadgeId());
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ScoutMeritBadge findByScoutIdScoutTypeIdAndMeritBadgeId(int scoutId, int scoutTypeId, int meritBadgeId) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            String query = "SELECT * " +
                    "FROM scoutMeritBadge " +
                    "WHERE scoutId = " + scoutId +
                    " AND scoutTypeId = " + scoutTypeId +
                    " AND meritBadgeId = " + meritBadgeId;
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                ScoutMeritBadge scoutMeritBadge = new ScoutMeritBadge();
                scoutMeritBadge.setId(rs.getInt(KeyConst.ID.getName()));
                scoutMeritBadge.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutMeritBadge.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutMeritBadge.setMeritBadgeId(rs.getInt(KeyConst.MERIT_BADGE_ID.getName()));
                return scoutMeritBadge;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
