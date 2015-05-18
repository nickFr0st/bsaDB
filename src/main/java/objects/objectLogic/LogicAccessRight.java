package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.AccessRight;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/16/2015
 */
public class LogicAccessRight {

    private static final Object lock = new Object();

    public static List<AccessRight> findAllByUserId(int userId) {
        List<AccessRight> accessRightList = new ArrayList<AccessRight>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return accessRightList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM accessRight WHERE userId = " + userId);

            while (rs.next()) {
                AccessRight accessRight = new AccessRight();
                accessRight.setId(rs.getInt(KeyConst.ID.getName()));
                accessRight.setUserId(rs.getInt(KeyConst.USER_ID.getName()));
                accessRight.setRightId(rs.getInt(KeyConst.RIGHT_ID.getName()));

                accessRightList.add(accessRight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<AccessRight>();
        }

        return accessRightList;
    }

    public static List<AccessRight> findAll() {
        List<AccessRight> accessRightList = new ArrayList<AccessRight>();

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
            return new ArrayList<AccessRight>();
        }

        return accessRightList;
    }

    public static synchronized AccessRight save(AccessRight accessRight) {
        if (accessRight == null) {
            return null;
        }

        try {
            synchronized (lock) {
                if ((accessRight = saveAccessRight(accessRight)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return accessRight;
    }

    private static AccessRight saveAccessRight(AccessRight accessRight) {
        if (accessRight.getId() < 0) {
            accessRight.setId(MySqlConnector.getInstance().getNextId("accessRight"));
        }

        if (accessRight.getId() < 0) {
            return null;
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
            return null;
        }

        return accessRight;
    }

    public static synchronized void delete(Integer id) {
        if (id < 2) {
            return;
        }

        try {
            synchronized (lock) {
                if (deleteAccessRight(id)) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteAccessRight(Integer id) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM accessRight WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
