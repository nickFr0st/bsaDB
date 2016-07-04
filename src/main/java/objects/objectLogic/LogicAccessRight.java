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

    public static synchronized void delete(final Integer id) {
        if (id < 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteAccessRight(id);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAccessRight(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM accessRight WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
