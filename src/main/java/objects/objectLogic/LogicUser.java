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

    private static final Object lock = new Object();

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

    public static synchronized User save(User user) {
        if (user == null) {
             return null;
        }

        try {
            synchronized (lock) {
                if ((user = saveUser(user)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user;
    }

    private static User saveUser(User user) {
        if (user.getId() < 0) {
            user.setId(MySqlConnector.getInstance().getNextId("user"));
        }

        if (user.getId() < 0) {
            return null;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO user VALUES(");
            query.append(user.getId()).append(", ");
            query.append("'").append(user.getName()).append("', ");
            query.append("'").append(user.getPosition()).append("', ");
            query.append("'").append(user.getPhoneNumber()).append("', ");
            query.append("'").append(user.getStreet()).append("', ");
            query.append("'").append(user.getCity()).append("', ");
            query.append("'").append(user.getZip()).append("', ");
            query.append(true).append(", ");
            query.append("'").append(user.getPassword()).append("', ");
            query.append("'").append(user.getEmail()).append("'");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    public static synchronized User update(User user) {
        if (user == null) {
            return null;
        }

        try {
            synchronized (lock) {
                if ((user = updateUser(user)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user;
    }

    private static User updateUser(User user) {

        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE user SET ");
            query.append("name = '").append(user.getName()).append("', ");
            query.append("position = '").append(user.getPosition()).append("', ");
            query.append("phoneNumber = '").append(user.getPhoneNumber()).append("', ");
            query.append("street = '").append(user.getStreet()).append("', ");
            query.append("city = '").append(user.getCity()).append("', ");
            query.append("zip = '").append(user.getZip()).append("', ");
            query.append("password = '").append(user.getPassword()).append("', ");
            query.append("email = '").append(user.getEmail()).append("' ");
            query.append("WHERE id = ").append(user.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    public static synchronized void delete(User user) {
        if (user == null || user.getId() <= 1) {
             return;
        }

        try {
            synchronized (lock) {
                if (deleteUser(user.getId())) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteUser(Integer id) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM user WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
