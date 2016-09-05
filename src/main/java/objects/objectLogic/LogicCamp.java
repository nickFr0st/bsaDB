package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.Camp;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathanael on 5/12/2015
 */
public class LogicCamp {

    public static Set<Camp> findAll(List<Camp> excludedCamps) {
        Set<Camp> campList = new LinkedHashSet<>();

        StringBuilder ids = new StringBuilder();
        if (!Util.isEmpty(excludedCamps)) {
            for (Camp badge : excludedCamps) {
                if (!ids.toString().isEmpty()) {
                    ids.append(",");
                }
                ids.append(badge.getId());
            }
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs;
            if (ids.toString().isEmpty()) {
                rs = statement.executeQuery("SELECT * FROM camp ORDER BY startDate");
            } else {
                rs = statement.executeQuery("SELECT * FROM camp WHERE id NOT IN(" + ids + ")  ORDER BY startDate");
            }

            while (rs.next()) {
                Camp camp = new Camp();
                camp.setId(rs.getInt(KeyConst.ID.getName()));
                camp.setName(rs.getString(KeyConst.NAME.getName()));
                camp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                camp.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                camp.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                camp.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                camp.setNote(rs.getString(KeyConst.NOTE.getName()));
                camp.setNumberOfNights(rs.getInt(KeyConst.NIGHT_COUNT.getName()));

                campList.add(camp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedHashSet<>();
        }

        return campList;
    }

    public static Set<Camp> findAllByName(List<String> nameList) {
        if (Util.isEmpty(nameList)) {
            return new LinkedHashSet<>();
        }

        Set<Camp> campList = new LinkedHashSet<>();

        for (String name : nameList) {
            try {
                Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM camp WHERE name LIKE '" + name + "'" + " ORDER BY startDate");

                if (rs.next()) {
                    Camp camp = new Camp();
                    camp.setId(rs.getInt(KeyConst.ID.getName()));
                    camp.setName(rs.getString(KeyConst.NAME.getName()));
                    camp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                    camp.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                    camp.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                    camp.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                    camp.setNote(rs.getString(KeyConst.NOTE.getName()));
                    camp.setNumberOfNights(rs.getInt(KeyConst.NIGHT_COUNT.getName()));

                    campList.add(camp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new LinkedHashSet<>();
            }
        }

        return campList;
    }

    public static Camp findByName(String name) {
        Camp camp = null;

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
                camp.setNumberOfNights(rs.getInt(KeyConst.NIGHT_COUNT.getName()));
            }
        } catch (SQLException e) {
            return null;
        }

        return camp;
    }

    public static Camp findById(int campId) {
        Camp camp = null;

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM camp WHERE id = " + campId);

            if (rs.next()) {
                camp = new Camp();
                camp.setId(rs.getInt(KeyConst.ID.getName()));
                camp.setName(rs.getString(KeyConst.NAME.getName()));
                camp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                camp.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                camp.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                camp.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                camp.setNote(rs.getString(KeyConst.NOTE.getName()));
                camp.setNumberOfNights(rs.getInt(KeyConst.NIGHT_COUNT.getName()));
            }
        } catch (SQLException e) {
            return null;
        }

        return camp;
    }

    public static synchronized Camp save(final Camp camp) {
        if (camp == null) {
             return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveCamp(camp);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return camp;
    }

    private static void saveCamp(Camp camp) {
        if (camp.getId() < 0) {
            camp.setId(MySqlConnector.getInstance().getNextId("camp"));
        }

        if (camp.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO camp VALUES(");
            query.append(camp.getId()).append(", ");
            query.append("'").append(camp.getName().replace("'", "''")).append("', ");
            query.append(camp.getScoutTypeId()).append(", ");
            query.append("'").append(camp.getLocation().replace("'", "''")).append("', ");
            query.append("'").append(Util.DATA_BASE_DATE_FORMAT.format(camp.getStartDate())).append("', ");
            query.append("'").append(camp.getLeaders().replace("'", "''")).append("', ");
            query.append("'").append(camp.getNote().replace("'", "''")).append("', ");
            query.append(camp.getNumberOfNights());
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Camp update(final Camp camp) {
        if (camp == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateCamp(camp);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return camp;
    }

    private static void updateCamp(Camp camp) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE camp SET ");
            query.append("name = '").append(camp.getName().replace("'", "''")).append("', ");
            query.append("scoutTypeId = ").append(camp.getScoutTypeId()).append(", ");
            query.append("location = '").append(camp.getLocation()).append("', ");
            query.append("startDate = '").append(Util.DATA_BASE_DATE_FORMAT.format(camp.getStartDate())).append("', ");
            query.append("leaders = '").append(camp.getLeaders().replace("'", "''")).append("', ");
            query.append("note = '").append(camp.getNote().replace("'", "''")).append("', ");
            query.append("numberOfNights = ").append(camp.getNumberOfNights()).append(" ");
            query.append("WHERE id = ").append(camp.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(final Camp camp) {
        if (camp == null || camp.getId() < 0) {
             return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteCamp(camp.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCamp(Integer id) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("DELETE camp, scoutCamp ");
            query.append("FROM camp ");
            query.append("LEFT JOIN scoutCamp ON scoutCamp.campId = camp.id ");
            query.append("WHERE camp.id = ").append(id);

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Camp> findAllByScoutIdAndTypeId(int scoutId, int scoutTypeId) {
        List<Camp> campList = new ArrayList<>();

        try {

            StringBuilder query = new StringBuilder();
            query.append("SELECT camp.* ");
            query.append("FROM camp ");
            query.append("INNER JOIN scoutCamp ON scoutCamp.campId = camp.id ");
            query.append("WHERE camp.scoutTypeId = ").append(scoutTypeId).append(" ");
            query.append("AND scoutCamp.scoutId = ").append(scoutId).append(" ");
            query.append("ORDER BY camp.startDate");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query.toString());

            while (rs.next()) {
                Camp camp = new Camp();
                camp.setId(rs.getInt(KeyConst.ID.getName()));
                camp.setName(rs.getString(KeyConst.NAME.getName()));
                camp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                camp.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                camp.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                camp.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                camp.setNote(rs.getString(KeyConst.NOTE.getName()));
                camp.setNumberOfNights(rs.getInt(KeyConst.NIGHT_COUNT.getName()));

                campList.add(camp);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return campList;
    }
}
