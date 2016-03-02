package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.MeritBadge;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/18/2015
 */
public class LogicMeritBadge {

    public static List<MeritBadge> findAll() {
        List<MeritBadge> meritBadgeList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return meritBadgeList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meritBadge ORDER BY name");

            while (rs.next()) {
                MeritBadge meritBadge = new MeritBadge();
                meritBadge.setId(rs.getInt(KeyConst.ID.getName()));
                meritBadge.setName(rs.getString(KeyConst.NAME.getName()));
                meritBadge.setImgPath(rs.getString(KeyConst.IMG_PATH.getName()));
                meritBadge.setRequiredForEagle(rs.getBoolean(KeyConst.REQUIRED_FOR_EAGLE.getName()));
                meritBadgeList.add(meritBadge);
            }

        } catch (Exception e) {
            return new ArrayList<>();
        }

        return meritBadgeList;
    }

    public static MeritBadge findByName(String name) {
        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return null;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meritBadge WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                MeritBadge meritBadge = new MeritBadge();
                meritBadge.setId(rs.getInt(KeyConst.ID.getName()));
                meritBadge.setName(rs.getString(KeyConst.NAME.getName()));
                meritBadge.setImgPath(rs.getString(KeyConst.IMG_PATH.getName()));
                meritBadge.setRequiredForEagle(rs.getBoolean(KeyConst.REQUIRED_FOR_EAGLE.getName()));
                return meritBadge;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static synchronized MeritBadge save(final MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveMeritBadge(meritBadge);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return meritBadge;
    }

    private static void saveMeritBadge(MeritBadge meritBadge) {
        if (meritBadge.getId() < 0) {
            meritBadge.setId(MySqlConnector.getInstance().getNextId("meritBadge"));
        }

        if (meritBadge.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO meritBadge VALUES(");
            query.append(meritBadge.getId()).append(", ");
            query.append("'").append(meritBadge.getName().replace("'", "''")).append("', ");
            query.append("'").append(meritBadge.getImgPath().replace("\\", "\\\\").replace("'", "''")).append("', ");
            query.append(Util.getIntValue(meritBadge.isRequiredForEagle())).append(" ");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(final MeritBadge meritBadge) {
        if (meritBadge == null || meritBadge.getId() < 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteMeritBadge(meritBadge.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMeritBadge(Integer id) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM meritBadge WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized MeritBadge update(final MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateMeritBadge(meritBadge);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return meritBadge;
    }

    private static void updateMeritBadge(MeritBadge meritBadge) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE meritBadge SET ");
            query.append(KeyConst.NAME.getName()).append(" = '").append(meritBadge.getName().replace("'", "''")).append("', ");
            query.append(KeyConst.IMG_PATH.getName()).append(" = '").append(meritBadge.getImgPath().replace("\\", "\\\\").replace("'", "''")).append("', ");
            query.append(KeyConst.REQUIRED_FOR_EAGLE.getName()).append(" = ").append(Util.getIntValue(meritBadge.isRequiredForEagle())).append(" ");
            query.append("WHERE id = ").append(meritBadge.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
