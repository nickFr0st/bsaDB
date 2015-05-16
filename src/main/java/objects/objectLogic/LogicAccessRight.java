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
}
