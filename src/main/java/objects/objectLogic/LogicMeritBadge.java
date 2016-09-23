package objects.objectLogic;

import constants.KeyConst;
import constants.RequirementTypeConst;
import objects.databaseObjects.MeritBadge;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/18/2015
 */
public class LogicMeritBadge {

    public static List<MeritBadge> findAll(List<MeritBadge> excludedBadges) {
        List<MeritBadge> meritBadgeList = new ArrayList<>();

        StringBuilder ids = new StringBuilder();
        if (!Util.isEmpty(excludedBadges)) {
            for (MeritBadge badge : excludedBadges) {
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
                rs = statement.executeQuery("SELECT * FROM meritBadge ORDER BY name");
            } else {
                rs = statement.executeQuery("SELECT * FROM meritBadge WHERE id NOT IN(" + ids + ")  ORDER BY name");
            }

            while (rs.next()) {
                MeritBadge meritBadge = buildMeritBadge(rs);
                meritBadgeList.add(meritBadge);
            }

        } catch (Exception e) {
            return new ArrayList<>();
        }

        return meritBadgeList;
    }

    public static MeritBadge findByName(String name) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meritBadge WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                MeritBadge meritBadge = buildMeritBadge(rs);
                return meritBadge;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static MeritBadge findById(int meritBadgeId) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM meritBadge WHERE id = " + meritBadgeId);

            if (rs.next()) {
                MeritBadge meritBadge = buildMeritBadge(rs);
                return meritBadge;
            }

        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static synchronized MeritBadge save(final MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveMeritBadge(meritBadge);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return meritBadge;
    }

    private static void saveMeritBadge(MeritBadge meritBadge) {
        if (meritBadge.getId() < 0) {
            meritBadge.setId(MySqlConnector.getInstance().getNextId("meritBadge"));
        }

        if (meritBadge.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO meritBadge VALUES(");
            query.append(meritBadge.getId()).append(", ");
            query.append("'").append(meritBadge.getName().replace("'", "''")).append("', ");
            query.append("'").append(meritBadge.getImgPath().replace("\\", "\\\\").replace("'", "''")).append("', ");
            query.append(Util.getIntValue(meritBadge.isRequiredForEagle())).append(" ");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(final MeritBadge meritBadge) {
        if (meritBadge == null || meritBadge.getId() < 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteMeritBadge(meritBadge.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMeritBadge(Integer id) {

        try {
            StringBuilder query = new StringBuilder();
            query.append("DELETE meritBadge, counselor, requirement, scoutMeritBadge ");
            query.append("FROM meritBadge ");
            query.append("LEFT JOIN counselor ON counselor.badgeId = meritBadge.id " );
            query.append("LEFT JOIN requirement ON requirement.parentId = meritBadge.id AND requirement.typeId = ").append(RequirementTypeConst.MERIT_BADGE.getId()).append(" ");
            query.append("LEFT JOIN scoutMeritBadge ON scoutMeritBadge.meritBadgeId = meritBadge.id ");
            query.append("WHERE meritBadge.id = ").append(id);

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized MeritBadge update(final MeritBadge meritBadge) {
        if (meritBadge == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateMeritBadge(meritBadge);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return meritBadge;
    }

    private static void updateMeritBadge(MeritBadge meritBadge) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE meritBadge SET ");
            query.append(KeyConst.NAME.getName()).append(" = '").append(meritBadge.getName().replace("'", "''")).append("', ");
            query.append(KeyConst.IMG_PATH.getName()).append(" = '").append(meritBadge.getImgPath().replace("\\", "\\\\").replace("'", "''")).append("', ");
            query.append(KeyConst.REQUIRED_FOR_EAGLE.getName()).append(" = ").append(Util.getIntValue(meritBadge.isRequiredForEagle())).append(" ");
            query.append("WHERE id = ").append(meritBadge.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MeritBadge buildMeritBadge(ResultSet rs) throws SQLException {
        MeritBadge meritBadge = new MeritBadge();
        meritBadge.setId(rs.getInt(KeyConst.ID.getName()));
        meritBadge.setName(rs.getString(KeyConst.NAME.getName()));
        meritBadge.setImgPath(rs.getString(KeyConst.IMG_PATH.getName()));
        meritBadge.setRequiredForEagle(rs.getBoolean(KeyConst.REQUIRED_FOR_EAGLE.getName()));
        meritBadge.setReadOnly(rs.getBoolean(KeyConst.READ_ONLY.getName()));
        return meritBadge;
    }
}
