package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.Counselor;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 10/25/2014
 */
@SuppressWarnings("StringBufferReplaceableByString")
public class LogicCounselor {

    public static List<Counselor> findAllByBadgeId(int badgeId) {

        List<Counselor> counselorList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return counselorList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM counselor WHERE badgeId = " + badgeId + " ORDER BY name");

            while (rs.next()) {
                Counselor counselor = new Counselor();
                counselor.setId(rs.getInt(KeyConst.ID.getName()));
                counselor.setBadgeId(rs.getInt(KeyConst.BADGE_ID.getName()));
                counselor.setName(rs.getString(KeyConst.NAME.getName()));
                counselor.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
                counselorList.add(counselor);
            }

        } catch (Exception e) {
            return null;
        }

        return counselorList;
    }

    public static List<Counselor> findAll() {
        List<Counselor> counselorList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return counselorList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM counselor ORDER BY name");

            while (rs.next()) {
                Counselor counselor = new Counselor();
                counselor.setId(rs.getInt(KeyConst.ID.getName()));
                counselor.setBadgeId(rs.getInt(KeyConst.BADGE_ID.getName()));
                counselor.setName(rs.getString(KeyConst.NAME.getName()));
                counselor.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
                counselorList.add(counselor);
            }

        } catch (Exception e) {
            return null;
        }

        return counselorList;
    }

//    public static List<Integer> findAllIdsByBadgeId(int badgeId) {
//        if (!connector.checkForDataBaseConnection()) {
//            return null;
//        }
//
//        List<Integer> counselorIdList = new ArrayList<Integer>();
//
//        try {
//            Statement statement = connector.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT id FROM counselor WHERE badgeId = " + badgeId);
//
//            while (rs.next()) {
//                counselorIdList.add(rs.getInt(KeyConst.MERIT_BADGE_ID.getName()));
//            }
//
//        } catch (Exception e) {
//            return null;
//        }
//
//        return counselorIdList;
//    }

//    public static void importList(List<Counselor> counselors) {
//        if (Util.isEmpty(counselors)) {
//            return;
//        }
//
//        for (Counselor counselor : counselors) {
//            Counselor record = findByNameAndBadgeId(counselor.getName(), counselor.getBadgeId());
//            if (record != null) {
//                counselor.setId(record.getId());
//                update(counselor);
//            } else {
//                save(counselor);
//            }
//        }
//    }

    public static void save(List<Counselor> counselorList) {
        if (Util.isEmpty(counselorList)) {
            return;
        }

        for (Counselor counselor : counselorList) {
            save(counselor);
        }
    }

    public static synchronized Counselor save(final Counselor counselor) {
        if (counselor == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveCounselor(counselor);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return counselor;
    }

    private static void saveCounselor(Counselor counselor) {
        if (counselor.getId() < 0) {
            counselor.setId(MySqlConnector.getInstance().getNextId("counselor"));
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO counselor VALUES(");
            query.append(counselor.getId()).append(", ");
            query.append(counselor.getBadgeId()).append(", ");
            query.append("'").append(counselor.getName().replace("'", "''")).append("', ");
            query.append("'").append(counselor.getPhoneNumber()).append("' ");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Counselor update(final Counselor counselor) {
        if (counselor == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateCounselor(counselor);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return counselor;
    }

    public static void updateCounselor(Counselor counselor) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE counselor SET ");
            query.append("name = '").append(counselor.getName().replace("'", "''")).append("', ");
            query.append("phoneNumber = '").append(counselor.getPhoneNumber()).append("' ");
            query.append("WHERE id = ").append(counselor.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Counselor findByNameAndBadgeId(String name, int badgeId) {
        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return null;
        }

        Counselor counselor = null;

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM counselor WHERE badgeId = " + badgeId + " AND name LIKE '" + name + "'");

            if (rs.next()) {
                counselor = new Counselor();
                counselor.setId(rs.getInt(KeyConst.ID.getName()));
                counselor.setBadgeId(rs.getInt(KeyConst.BADGE_ID.getName()));
                counselor.setName(rs.getString(KeyConst.NAME.getName()));
                counselor.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
            }

        } catch (Exception e) {
            return null;
        }

        return counselor;
    }

    public static synchronized void delete(List<Counselor> counselorList) {
        if (Util.isEmpty(counselorList)) {
            return;
        }

        for (Counselor counselor : counselorList) {
             delete(counselor);
        }
    }

    public static synchronized void delete(final Counselor counselor) {
        if (counselor == null || counselor.getId() < 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteCounselor(counselor.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCounselor(Integer counselorId) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM counselor WHERE id = " + counselorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void updateList(List<Counselor> counselorList, int meritBadgeId) {
//        for (Counselor counselor : counselorList) {
//            if (counselor.getId() < 0) {
//                save(counselor);
//            } else {
//                update(counselor);
//            }
//        }
//
//        List<Integer> existingCounselorIdList = findAllIdsByBadgeId(meritBadgeId);
//        List<Integer> tempList = new ArrayList<Integer>();
//
//        for (Counselor counselor : counselorList) {
//            if (counselor.getId() < 0) {
//                continue;
//            }
//
//            tempList.add(counselor.getId());
//        }
//
//        if (Util.isEmpty(tempList)) {
//            deleteList(existingCounselorIdList);
//            return;
//        }
//
//        List<Integer> deletionList = new ArrayList<Integer>();
//
//        for (Integer id : existingCounselorIdList) {
//            if (!tempList.contains(id)) {
//                deletionList.add(id);
//            }
//        }
//
//        deleteList(deletionList);
//    }

    public static synchronized void deleteAllByBadgeId(final int badgeId) {
        try {
            Thread t  = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteAllCounselorsByBadgeId(badgeId);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAllCounselorsByBadgeId(int badgeId) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM counselor WHERE badgeId = " + badgeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
