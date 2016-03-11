package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.Camp;
import objects.databaseObjects.User;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Nathanael on 5/12/2015
 */
public class LogicCamp {

    public static Set<Camp> findAll() {
        Set<Camp> campList = new LinkedHashSet<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return campList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM camp ORDER BY name");

            while (rs.next()) {
                Camp camp = new Camp();
                camp.setId(rs.getInt(KeyConst.ID.getName()));
                camp.setName(rs.getString(KeyConst.NAME.getName()));
                camp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                camp.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                camp.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                camp.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                camp.setNote(rs.getString(KeyConst.NOTE.getName()));

                campList.add(camp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedHashSet<>();
        }

        return campList;
    }

    public static Camp findByName(String name) {
        Camp camp = null;

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return camp;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM camp WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                camp = new Camp();
                camp.setId(rs.getInt(KeyConst.ID.getName()));
                camp.setName(rs.getString(KeyConst.NAME.getName()));
                camp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                camp.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                camp.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                camp.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                camp.setNote(rs.getString(KeyConst.NOTE.getName()));
            }
        } catch (SQLException e) {
            return null;
        }

        return camp;
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
