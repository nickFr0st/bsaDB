package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.BoyScout;
import objects.databaseObjects.Scout;
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
public class LogicBoyScout {
// todo: change this to work with the boy scout object
    public static Set<BoyScout> findAll() {
        Set<BoyScout> boyScoutSet = new LinkedHashSet<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return boyScoutSet;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM boyScout ORDER BY name");

            while (rs.next()) {
                BoyScout boyScout = new BoyScout();
                boyScout.setId(rs.getInt(KeyConst.ID.getName()));
                boyScout.setName(rs.getString(KeyConst.NAME.getName()));
                boyScout.setPosition(rs.getString(KeyConst.POSITION.getName()));
                boyScout.setBirthDate(rs.getDate(KeyConst.BIRTH_DATE.getName()));
                boyScout.setAdvancementId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                boyScout.setAdvancementDate(rs.getDate(KeyConst.ADVANCEMENT_DATE.getName()));
                boyScout.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
                boyScout.setGuardianName(rs.getString(KeyConst.GUARDIAN_NAME.getName()));
                boyScout.setGuardianPhoneNumber(rs.getString(KeyConst.GUARDIAN_PHONE_NUMBER.getName()));
                boyScout.setStreet(rs.getString(KeyConst.STREET.getName()));
                boyScout.setCity(rs.getString(KeyConst.CITY.getName()));
                boyScout.setState(rs.getString(KeyConst.STATE.getName()));
                boyScout.setZip(rs.getString(KeyConst.ZIP.getName()));
                boyScout.setNote(rs.getString(KeyConst.NOTE.getName()));

                boyScoutSet.add(boyScout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedHashSet<>();
        }

        return boyScoutSet;
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

    public static synchronized void delete(final BoyScout boyScout) {
        if (boyScout == null || boyScout.getId() < 1) {
             return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteBoyScout(boyScout.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteBoyScout(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM boyScout WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Scout findByName(String name) {
        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return null;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM boyScout WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                BoyScout boyScout = new BoyScout();
                boyScout.setId(rs.getInt(KeyConst.ID.getName()));
                boyScout.setName(rs.getString(KeyConst.NAME.getName()));
                boyScout.setPosition(rs.getString(KeyConst.POSITION.getName()));
                boyScout.setBirthDate(rs.getDate(KeyConst.BIRTH_DATE.getName()));
                boyScout.setAdvancementId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                boyScout.setAdvancementDate(rs.getDate(KeyConst.ADVANCEMENT_DATE.getName()));
                boyScout.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
                boyScout.setGuardianName(rs.getString(KeyConst.GUARDIAN_NAME.getName()));
                boyScout.setGuardianPhoneNumber(rs.getString(KeyConst.GUARDIAN_PHONE_NUMBER.getName()));
                boyScout.setStreet(rs.getString(KeyConst.STREET.getName()));
                boyScout.setCity(rs.getString(KeyConst.CITY.getName()));
                boyScout.setState(rs.getString(KeyConst.STATE.getName()));
                boyScout.setZip(rs.getString(KeyConst.ZIP.getName()));
                boyScout.setNote(rs.getString(KeyConst.NOTE.getName()));

                return boyScout;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static Scout findById(int scoutId) {
        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return null;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM boyScout WHERE id = " + scoutId);

            if (rs.next()) {
                BoyScout boyScout = new BoyScout();
                boyScout.setId(rs.getInt(KeyConst.ID.getName()));
                boyScout.setName(rs.getString(KeyConst.NAME.getName()));
                boyScout.setPosition(rs.getString(KeyConst.POSITION.getName()));
                boyScout.setBirthDate(rs.getDate(KeyConst.BIRTH_DATE.getName()));
                boyScout.setAdvancementId(rs.getInt(KeyConst.ADVANCEMENT_ID.getName()));
                boyScout.setAdvancementDate(rs.getDate(KeyConst.ADVANCEMENT_DATE.getName()));
                boyScout.setPhoneNumber(rs.getString(KeyConst.PHONE_NUMBER.getName()));
                boyScout.setGuardianName(rs.getString(KeyConst.GUARDIAN_NAME.getName()));
                boyScout.setGuardianPhoneNumber(rs.getString(KeyConst.GUARDIAN_PHONE_NUMBER.getName()));
                boyScout.setStreet(rs.getString(KeyConst.STREET.getName()));
                boyScout.setCity(rs.getString(KeyConst.CITY.getName()));
                boyScout.setState(rs.getString(KeyConst.STATE.getName()));
                boyScout.setZip(rs.getString(KeyConst.ZIP.getName()));
                boyScout.setNote(rs.getString(KeyConst.NOTE.getName()));

                return boyScout;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
