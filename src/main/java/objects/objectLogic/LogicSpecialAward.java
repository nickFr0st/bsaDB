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

    public static List<AccessRight> findAll() {
        List<AccessRight> accessRightList = new ArrayList<>();

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

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM specialAward WHERE scoutId = " + scoutId + " AND scoutTypeId = " + scoutTypeId + " ORDER BY name");

            while (rs.next()) {
                SpecialAward specialAward = buildSpecialAward(rs);

                specialAwardList.add(specialAward);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return specialAwardList;
    }

    private static SpecialAward buildSpecialAward(ResultSet rs) throws SQLException {
        SpecialAward specialAward = new SpecialAward();
        specialAward.setId(rs.getInt(KeyConst.ID.getName()));
        specialAward.setName(rs.getString(KeyConst.NAME.getName()));
        specialAward.setDescription(rs.getString(KeyConst.DESCRIPTION.getName()));
        specialAward.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
        specialAward.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
        specialAward.setDateReceived(rs.getDate(KeyConst.DATE_RECEIVED.getName()));
        return specialAward;
    }

    public static synchronized void save(List<SpecialAward> specialAwardList) {
        if (Util.isEmpty(specialAwardList)) {
            return;
        }

        for (SpecialAward specialAward : specialAwardList) {
            save(specialAward);
        }
    }

    public static synchronized SpecialAward save(final SpecialAward specialAward) {
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveSpecialAward(specialAward);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return specialAward;
    }

    private static void saveSpecialAward(SpecialAward specialAward) {
        if (specialAward.getId() < 0) {
            specialAward.setId(MySqlConnector.getInstance().getNextId("specialAward"));
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO specialAward VALUES(");
            query.append(specialAward.getId()).append(", ");
            query.append(specialAward.getScoutId()).append(", ");
            query.append(specialAward.getScoutTypeId()).append(", ");
            query.append("'").append(specialAward.getName().replace("'", "''")).append("', ");
            query.append("'").append(specialAward.getDescription().replace("'", "''")).append("', ");
            query.append("'").append(Util.DATA_BASE_DATE_FORMAT.format(specialAward.getDateReceived())).append("'");
            query.append(")");

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

    public static List<SpecialAward> findAllByScoutId(List<Integer> scoutIdList) {
        if (Util.isEmpty(scoutIdList)) {
            return new ArrayList<>();
        }

        List<SpecialAward> specialAwardList = new ArrayList<>();

        for (Integer scoutId : scoutIdList) {
            try {
                Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM specialAward WHERE scoutId = " + scoutId + " ORDER BY name");

                while (rs.next()) {
                    SpecialAward specialAward = buildSpecialAward(rs);
                    specialAwardList.add(specialAward);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        return specialAwardList;
    }
}
