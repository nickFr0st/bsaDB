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
public class LogicCounselor {

    private static final Object lock = new Object();

    public static List<Counselor> findAllByBadgeId(int badgeId) {

        List<Counselor> counselorList = new ArrayList<Counselor>();

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
        List<Counselor> counselorList = new ArrayList<Counselor>();

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

    public static synchronized Counselor save(Counselor counselor) {
        if (counselor == null) {
            return null;
        }

        try {
            synchronized (lock) {
                if ((counselor = saveCounselor(counselor)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return counselor;
    }

    private static Counselor saveCounselor(Counselor counselor) {
        if (counselor.getId() < 0) {
            counselor.setId(MySqlConnector.getInstance().getNextId("counselor"));
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO counselor VALUES(");
            query.append(counselor.getId()).append(", ");
            query.append(counselor.getBadgeId()).append(", ");
            query.append("'").append(counselor.getName()).append("', ");
            query.append("'").append(counselor.getPhoneNumber()).append("' ");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return counselor;
    }

    public static synchronized Counselor update(Counselor counselor) {
        if (counselor == null) {
            return null;
        }

        try {
            synchronized (lock) {
                if ((counselor = updateCounselor(counselor)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return counselor;
    }

    public static Counselor updateCounselor(Counselor counselor) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE counselor SET ");
            query.append("name = '").append(counselor.getName()).append("', ");
            query.append("phoneNumber = '").append(counselor.getPhoneNumber()).append("' ");
            query.append("WHERE id = ").append(counselor.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return counselor;
    }

//    public static Counselor findByNameAndBadgeId(String name, int badgeId) {
//        if (!connector.checkForDataBaseConnection()) {
//            return null;
//        }
//
//        Counselor counselor = null;
//        try {
//            Statement statement = connector.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM counselor WHERE badgeId = " + badgeId + " AND name LIKE '" + name + "'");
//
//            if (rs.next()) {
//                counselor = new Counselor();
//                counselor.setId(rs.getInt(KeyConst.COUNSELOR_ID.getName()));
//                counselor.setBadgeId(rs.getInt(KeyConst.COUNSELOR_BADGE_ID.getName()));
//                counselor.setName(rs.getString(KeyConst.COUNSELOR_NAME.getName()));
//                counselor.setPhoneNumber(rs.getString(KeyConst.COUNSELOR_PHONE_NUMBER.getName()));
//            }
//
//        } catch (Exception e) {
//            return null;
//        }
//
//        return counselor;
//    }

//    public static synchronized void delete(List<Integer> counselorIdList) {
//        if (Util.isEmpty(counselorIdList)) {
//            return;
//        }
//
//        try {
//            synchronized (lock) {
//                if (deleteCounselorList(counselorIdList)) {
//                    lock.wait(MySqlConnector.WAIT_TIME);
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    private static boolean deleteCounselorList(List<Integer> counselorIdList) {
//        try {
//            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
//            statement.executeUpdate("DELETE FROM counselor WHERE id IN (" + Util.listToString(counselorIdList) + ")");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }


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

//    public static synchronized void deleteAllByBadgeId(int badgeId) {
//        try {
//            synchronized (lock) {
//                if (!deleteAllCounselorsByBadgeId(badgeId)) {
//                    lock.wait(Util.WAIT_TIME);
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    private static boolean deleteAllCounselorsByBadgeId(int badgeId) {
//        if (!connector.checkForDataBaseConnection()) {
//            return false;
//        }
//
//        try {
//            Statement statement = connector.createStatement();
//            statement.executeUpdate("DELETE FROM counselor WHERE badgeId = " + badgeId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
}
