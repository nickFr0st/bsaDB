package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.AccessRight;
import objects.databaseObjects.SpecialAward;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/16/2015
 */
public class LogicSpecialAward {

    // todo: make work for the special award object
    public static List<AccessRight> findAll() {
        List<AccessRight> accessRightList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return accessRightList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM accessRight ORDER BY userId");

            while (rs.next()) {
                AccessRight accessRight = new AccessRight();
                accessRight.setId(rs.getInt(KeyConst.ID.getName()));
                accessRight.setUserId(rs.getInt(KeyConst.USER_ID.getName()));
                accessRight.setRightId(rs.getInt(KeyConst.RIGHT_ID.getName()));

                accessRightList.add(accessRight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return accessRightList;
    }

    public static List<SpecialAward> findAllByScoutIdAndScoutTypeId(int scoutId, int scoutTypeId) {
        List<SpecialAward> specialAwardList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return specialAwardList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM specialAward WHERE scoutId = " + scoutId + " AND scoutTypeId = " + scoutTypeId + " ORDER BY name");

            while (rs.next()) {
                SpecialAward specialAward = new SpecialAward();
                specialAward.setId(rs.getInt(KeyConst.ID.getName()));
                specialAward.setName(rs.getString(KeyConst.NAME.getName()));
                specialAward.setDescription(rs.getString(KeyConst.DESCRIPTION.getName()));
                specialAward.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                specialAward.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                specialAward.setDateReceived(rs.getDate(KeyConst.DATE_RECEIVED.getName()));

                specialAwardList.add(specialAward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return specialAwardList;
    }

    public static synchronized AccessRight save(final AccessRight accessRight) {
        if (accessRight == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveAccessRight(accessRight);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return accessRight;
    }

    private static void saveAccessRight(AccessRight accessRight) {
        if (accessRight.getId() < 0) {
            accessRight.setId(MySqlConnector.getInstance().getNextId("accessRight"));
        }

        if (accessRight.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO accessRight VALUES(");
            query.append(accessRight.getId()).append(", ");
            query.append(accessRight.getUserId()).append(", ");
            query.append(accessRight.getRightId()).append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(List<SpecialAward> specialAwardList) {
        if (Util.isEmpty(specialAwardList)) {
            return;
        }

        for (SpecialAward specialAward : specialAwardList) {
            delete(specialAward.getId());
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
                    deleteSpecialAward(id);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteSpecialAward(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM specialAward WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
