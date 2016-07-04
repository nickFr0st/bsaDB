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
        List<User> userList = new ArrayList<>();

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user ORDER BY name");

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(KeyConst.ID.getName()));
                user.setUserName(rs.getString(KeyConst.USERNAME.getName()));
                user.setName(rs.getString(KeyConst.NAME.getName()));
                user.setPosition(rs.getString(KeyConst.POSITION.getName()));
                user.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
                user.setStreet(rs.getString(KeyConst.STREET.getName()));
                user.setState(rs.getString(KeyConst.STATE.getName()));
                user.setCity(rs.getString(KeyConst.CITY.getName()));
                user.setZip(rs.getString(KeyConst.ZIP.getName()));
                user.setEditable(rs.getBoolean(KeyConst.EDITABLE.getName()));
                user.setPassword(rs.getString(KeyConst.PASSWORD.getName()));
                user.setEmail(rs.getString(KeyConst.EMAIL.getName()));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return userList;
    }

    public static synchronized User save(final User user) {
        if (user == null) {
             return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveUser(user);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user;
    }

    private static void saveUser(User user) {
        if (user.getId() < 0) {
            user.setId(MySqlConnector.getInstance().getNextId("user"));
        }

        if (user.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO user VALUES(");
            query.append(user.getId()).append(", ");
            query.append("'").append(user.getUserName().replace("'", "''")).append("', ");
            query.append("'").append(user.getName().replace("'", "''")).append("', ");
            query.append("'").append(user.getPosition().replace("'", "''")).append("', ");
            query.append("'").append(user.getPhoneNumber()).append("', ");
            query.append("'").append(user.getState().replace("'", "''")).append("', ");
            query.append("'").append(user.getCity().replace("'", "''")).append("', ");
            query.append("'").append(user.getStreet().replace("'", "''")).append("', ");
            query.append("'").append(user.getZip()).append("', ");
            query.append(true).append(", ");
            query.append("'").append(user.getPassword().replace("'", "''")).append("', ");
            query.append("'").append(user.getEmail().replace("'", "''")).append("'");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized User update(final User user) {
        if (user == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateUser(user);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user;
    }

    private static void updateUser(User user) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE user SET ");
            query.append("username = '").append(user.getUserName().replace("'", "''")).append("', ");
            query.append("name = '").append(user.getName().replace("'", "''")).append("', ");
            query.append("position = '").append(user.getPosition().replace("'", "''")).append("', ");
            query.append("phoneNumber = '").append(user.getPhoneNumber()).append("', ");
            query.append("state = '").append(user.getState().replace("'", "''")).append("', ");
            query.append("city = '").append(user.getCity().replace("'", "''")).append("', ");
            query.append("street = '").append(user.getStreet().replace("'", "''")).append("', ");
            query.append("zip = '").append(user.getZip()).append("', ");
            query.append("password = '").append(user.getPassword().replace("'", "''")).append("', ");
            query.append("email = '").append(user.getEmail().replace("'", "''")).append("' ");
            query.append("WHERE id = ").append(user.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(final User user) {
        if (user == null || user.getId() <= 1) {
             return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteUser(user.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUser(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM user WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
