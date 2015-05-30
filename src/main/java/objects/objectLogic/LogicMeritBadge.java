package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.MeritBadge;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/18/2015
 */
public class LogicMeritBadge {

    private static final Object lock = new Object();

    public static List<MeritBadge> findAll() {
        List<MeritBadge> meritBadgeList = new ArrayList<MeritBadge>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return meritBadgeList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meritBadge ORDER BY name");

            while (rs.next()) {
                MeritBadge meritBadge = new MeritBadge();
                meritBadge.setId(rs.getInt(KeyConst.ID.getName()));
                meritBadge.setName(rs.getString(KeyConst.NAME.getName()));
                meritBadge.setImgPath(rs.getString(KeyConst.IMG_PATH.getName()));
                meritBadge.setRequiredForEagle(rs.getBoolean(KeyConst.REQUIRED_FOR_EAGLE.getName()));
                meritBadge.setRevisionDate(rs.getDate(KeyConst.REV_DATE.getName()));
                meritBadgeList.add(meritBadge);
            }

        } catch (Exception e) {
            return new ArrayList<MeritBadge>();
        }

        return meritBadgeList;
    }

    public static synchronized MeritBadge save(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        try {
            synchronized (lock) {
                if ((meritBadge = saveMeritBadge(meritBadge)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return meritBadge;
    }

    private static MeritBadge saveMeritBadge(MeritBadge meritBadge) {
        if (meritBadge.getId() < 0) {
            meritBadge.setId(MySqlConnector.getInstance().getNextId("meritBadge"));
        }

        if (meritBadge.getId() < 0) {
            return null;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO meritBadge VALUES(");
            query.append(meritBadge.getId()).append(", ");
            query.append("'").append(meritBadge.getName()).append("', ");
            query.append("'").append(meritBadge.getImgPath().replace("\\", "\\\\")).append("', ");
            query.append(Util.getIntValue(meritBadge.isRequiredForEagle())).append(", ");
            query.append("'").append(Util.DATA_BASE_DATE_FORMAT.format(meritBadge.getRevisionDate())).append("'");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return meritBadge;
    }

    public static synchronized void delete(MeritBadge meritBadge) {
        if (meritBadge == null || meritBadge.getId() < 1) {
            return;
        }

        try {
            synchronized (lock) {
                if (deleteMeritBadge(meritBadge.getId())) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteMeritBadge(Integer id) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM meritBadge WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized MeritBadge update(MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        try {
            synchronized (lock) {
                if ((meritBadge = updateMeritBadge(meritBadge)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return meritBadge;
    }

    private static MeritBadge updateMeritBadge(MeritBadge meritBadge) {

        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE meritBadge SET ");
            query.append(KeyConst.NAME).append(" = '").append(meritBadge.getName()).append("', ");
            query.append(KeyConst.IMG_PATH).append(" = '").append(meritBadge.getImgPath().replace("\\", "\\\\")).append("', ");
            query.append(KeyConst.REQUIRED_FOR_EAGLE).append(" = ").append(Util.getIntValue(meritBadge.isRequiredForEagle())).append(", ");
            query.append(KeyConst.REV_DATE).append(" = '").append(Util.DATA_BASE_DATE_FORMAT.format(meritBadge.getRevisionDate())).append("', ");
            query.append("WHERE id = ").append(meritBadge.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return meritBadge;
    }
}
