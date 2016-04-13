package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ScoutCamp;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 3/10/2016
 */
public class LogicScoutCamp {

    public static List<ScoutCamp> findAllByCampId(int campId) {
        List<ScoutCamp> scoutCampList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return scoutCampList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutCamp WHERE campId = " + campId);

            while (rs.next()) {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setId(rs.getInt(KeyConst.ID.getName()));
                scoutCamp.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutCamp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutCamp.setCampId(rs.getInt(KeyConst.CAMP_ID.getName()));

                scoutCampList.add(scoutCamp);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return scoutCampList;
    }

    public static synchronized void deleteAllByCampId(final int campId) {
        if (campId <= 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteScoutCampByCampId(campId);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteScoutCampByCampId(Integer campId) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM scoutCamp WHERE campId = " + campId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized List<ScoutCamp> save(List<ScoutCamp> scoutCampList) {
        if (scoutCampList == null) {
            return null;
        }

        for (final ScoutCamp scoutCamp : scoutCampList) {
            try {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveScoutCamp(scoutCamp);
                    }
                });

                t.start();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return scoutCampList;
    }

    private static void saveScoutCamp(ScoutCamp scoutCamp) {
        if (scoutCamp.getId() < 0) {
            scoutCamp.setId(MySqlConnector.getInstance().getNextId("scoutCamp"));
        }

        if (scoutCamp.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO scoutCamp VALUES(");
            query.append(scoutCamp.getId()).append(", ");
            query.append(scoutCamp.getScoutId()).append(", ");
            query.append(scoutCamp.getScoutTypeId()).append(", ");
            query.append(scoutCamp.getCampId());
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ScoutCamp> findAllByScoutIdAndScoutTypeId(int scoutId, int scoutTypeId) {
        List<ScoutCamp> scoutCampList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return scoutCampList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutCamp WHERE scoutId = " + scoutId + " AND scoutTypeId = " + scoutTypeId);

            while (rs.next()) {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setId(rs.getInt(KeyConst.ID.getName()));
                scoutCamp.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutCamp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutCamp.setCampId(rs.getInt(KeyConst.CAMP_ID.getName()));

                scoutCampList.add(scoutCamp);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return scoutCampList;
    }

    public static synchronized void delete(List<ScoutCamp> scoutCampList) {
        if (Util.isEmpty(scoutCampList)) {
            return;
        }

        for (ScoutCamp scoutCamp : scoutCampList) {
            delete(scoutCamp.getId());
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
                    deleteScoutCamp(id);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteScoutCamp(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM scoutCamp WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
