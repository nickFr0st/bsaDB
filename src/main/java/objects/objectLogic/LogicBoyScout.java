package objects.objectLogic;

import constants.KeyConst;
import constants.ScoutTypeConst;
import objects.databaseObjects.BoyScout;
import objects.databaseObjects.Scout;
import util.MySqlConnector;
import util.Util;

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

    public static synchronized BoyScout save(final BoyScout boyScout) {
        if (boyScout == null) {
             return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveBoyScout(boyScout);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return boyScout;
    }

    private static void saveBoyScout(BoyScout boyScout) {
        if (boyScout.getId() < 0) {
            boyScout.setId(MySqlConnector.getInstance().getNextId("boyScout"));
        }

        if (boyScout.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO boyScout VALUES(");
            query.append(boyScout.getId()).append(", ");
            query.append("'").append(boyScout.getName().replace("'", "''")).append("', ");
            query.append("'").append(boyScout.getPosition().replace("'", "''")).append("', ");
            query.append("'").append(Util.DATA_BASE_DATE_FORMAT.format(boyScout.getBirthDate())).append("', ");
            query.append(boyScout.getAdvancementId()).append(", ");
            query.append("'").append(Util.DATA_BASE_DATE_FORMAT.format(boyScout.getAdvancementDate())).append("', ");
            query.append("'").append(boyScout.getPhoneNumber().replace("'", "''")).append("', ");
            query.append("'").append(boyScout.getGuardianPhoneNumber().replace("'", "''")).append("', ");
            query.append("'").append(boyScout.getGuardianName().replace("'", "''")).append("', ");
            query.append("'").append(boyScout.getStreet().replace("'", "''")).append("', ");
            query.append("'").append(boyScout.getCity().replace("'", "''")).append("', ");
            query.append("'").append(boyScout.getState().replace("'", "''")).append("', ");
            query.append("'").append(boyScout.getZip()).append("', ");
            query.append("'").append(boyScout.getNote()).append("'");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized BoyScout update(final BoyScout boyScout) {
        if (boyScout == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateBoyScout(boyScout);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return boyScout;
    }

    private static void updateBoyScout(BoyScout boyScout) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE boyScout SET ");
            query.append("name = '").append(boyScout.getName().replace("'", "''")).append("', ");
            query.append("position = '").append(boyScout.getPosition().replace("'", "''")).append("', ");
            query.append("birthDate = '").append(Util.DATA_BASE_DATE_FORMAT.format(boyScout.getBirthDate())).append("', ");
            query.append("advancementId = ").append(boyScout.getAdvancementId()).append(", ");
            query.append("advancementDate = '").append(Util.DATA_BASE_DATE_FORMAT.format(boyScout.getAdvancementDate())).append("', ");
            query.append("phoneNumber = '").append(boyScout.getPhoneNumber()).append("', ");
            query.append("guardianName = '").append(boyScout.getGuardianName().replace("'", "''")).append("', ");
            query.append("guardianPhoneNumber = '").append(boyScout.getGuardianPhoneNumber()).append("', ");
            query.append("street = '").append(boyScout.getStreet().replace("'", "''")).append("', ");
            query.append("city = '").append(boyScout.getCity().replace("'", "''")).append("', ");
            query.append("state = '").append(boyScout.getState().replace("'", "''")).append("', ");
            query.append("zip = '").append(boyScout.getZip()).append("', ");
            query.append("note = '").append(boyScout.getNote()).append("' ");
            query.append("WHERE id = ").append(boyScout.getId());

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
            StringBuilder query = new StringBuilder();
            query.append("DELETE boyscout, specialaward, scoutmeritbadge, scoutrequirement, scoutcamp ");
            query.append("FROM boyscout ");
            query.append("LEFT JOIN specialaward ON specialaward.scoutId = boyscout.id AND specialaward.scoutTypeId = ").append(ScoutTypeConst.BOY_SCOUT.getId()).append(" ");
            query.append("LEFT JOIN scoutmeritbadge ON scoutmeritbadge.scoutId = boyscout.id AND scoutmeritbadge.scoutTypeId = ").append(ScoutTypeConst.BOY_SCOUT.getId()).append(" ");
            query.append("LEFT JOIN scoutcamp ON scoutcamp.scoutId = boyscout.id AND scoutcamp.scoutTypeId = ").append(ScoutTypeConst.BOY_SCOUT.getId()).append(" ");
            query.append("LEFT JOIN scoutrequirement ON scoutrequirement.scoutId = boyscout.id AND scoutrequirement.scoutTypeId = ").append(ScoutTypeConst.BOY_SCOUT.getId()).append(" ");
            query.append("WHERE boyscout.id = ").append(id);

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BoyScout findByName(String name) {

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
