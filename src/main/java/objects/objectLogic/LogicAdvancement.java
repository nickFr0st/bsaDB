package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.Advancement;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/18/2015
 */
public class LogicAdvancement {

    public static List<Advancement> findAll() {
        List<Advancement> advancementList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return advancementList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM advancement ORDER BY name");

            while (rs.next()) {
                Advancement advancement = new Advancement();
                advancement.setId(rs.getInt(KeyConst.ID.getName()));
                advancement.setName(rs.getString(KeyConst.NAME.getName()));
                advancement.setImgPath(rs.getString(KeyConst.IMG_PATH.getName()));
                advancementList.add(advancement);
            }

        } catch (Exception e) {
            return new ArrayList<>();
        }

        return advancementList;
    }

    public static synchronized Advancement save(final Advancement advancement) {
        if (advancement == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveAdvancement(advancement);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return advancement;
    }

    private static void saveAdvancement(Advancement advancement) {
        if (advancement.getId() < 0) {
            advancement.setId(MySqlConnector.getInstance().getNextId("advancement"));
        }

        if (advancement.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO advancement VALUES(");
            query.append(advancement.getId()).append(", ");
            query.append("'").append(advancement.getName().replace("'", "''")).append("', ");
            query.append("'").append(advancement.getImgPath().replace("\\", "\\\\").replace("'", "''")).append("'");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(final Advancement advancement) {
        if (advancement == null || advancement.getId() < 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteAdvancement(advancement.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAdvancement(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM advancement WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Advancement update(final Advancement advancement) {
        if (advancement == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateAdvancement(advancement);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return advancement;
    }

    private static Advancement updateAdvancement(Advancement advancement) {

        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE advancement SET ");
            query.append("name = '").append(advancement.getName().replace("'", "''")).append("', ");
            query.append("imgPath = '").append(advancement.getImgPath().replace("\\", "\\\\").replace("'", "''")).append("' ");
            query.append("WHERE id = ").append(advancement.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return advancement;
    }
}
