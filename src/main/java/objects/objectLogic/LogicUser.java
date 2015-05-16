package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.User;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/12/2015
 */
public class LogicUser {

    public static List<User> findAll() {
        List<User> userList = new ArrayList<User>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return userList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user ORDER BY name");

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(KeyConst.ID.getName()));
                user.setName(rs.getString(KeyConst.NAME.getName()));
                user.setPosition(rs.getString(KeyConst.POSITION.getName()));
                user.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
                user.setStreet(rs.getString(KeyConst.STREET.getName()));
                user.setCity(rs.getString(KeyConst.CITY.getName()));
                user.setZip(rs.getString(KeyConst.ZIP.getName()));
                user.setEditable(rs.getBoolean(KeyConst.EDITABLE.getName()));
                user.setPassword(rs.getString(KeyConst.PASSWORD.getName()));
                user.setEmail(rs.getString(KeyConst.EMAIL.getName()));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }

        return userList;
    }
}
