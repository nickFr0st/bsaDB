package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ScoutServiceProject;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 3/10/2016
 */
public class LogicScoutServiceProject {

    public static List<ScoutServiceProject> findAllByProjectId(int serviceProjectId) {
        List<ScoutServiceProject> scoutServiceProjectList = new ArrayList<>();

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutServiceProject WHERE serviceProjectId = " + serviceProjectId);

            while (rs.next()) {
                ScoutServiceProject scoutServiceProject = new ScoutServiceProject();
                scoutServiceProject.setId(rs.getInt(KeyConst.ID.getName()));
                scoutServiceProject.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutServiceProject.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutServiceProject.setServiceProjectId(rs.getInt(KeyConst.SERVICE_PROJECT_ID.getName()));

                scoutServiceProjectList.add(scoutServiceProject);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return scoutServiceProjectList;
    }

    public static synchronized void deleteAllByProjectId(final int serviceProjectId) {
        if (serviceProjectId <= 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteScoutServiceProjectsByServiceProjectId(serviceProjectId);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteScoutServiceProjectsByServiceProjectId(Integer serviceProjectId) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM scoutServiceProject WHERE serviceProjectId = " + serviceProjectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized List<ScoutServiceProject> save(List<ScoutServiceProject> scoutServiceProjectList) {
        if (scoutServiceProjectList == null) {
            return null;
        }

        for (final ScoutServiceProject scoutServiceProject : scoutServiceProjectList) {
            try {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveScoutServiceProject(scoutServiceProject);
                    }
                });

                t.start();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return scoutServiceProjectList;
    }

    private static void saveScoutServiceProject(ScoutServiceProject scoutServiceProject) {
        if (scoutServiceProject.getId() < 0) {
            scoutServiceProject.setId(MySqlConnector.getInstance().getNextId("scoutServiceProject"));
        }

        if (scoutServiceProject.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO scoutServiceProject VALUES(");
            query.append(scoutServiceProject.getId()).append(", ");
            query.append(scoutServiceProject.getScoutId()).append(", ");
            query.append(scoutServiceProject.getScoutTypeId()).append(", ");
            query.append(scoutServiceProject.getServiceProjectId());
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ScoutServiceProject> findAllByScoutIdAndScoutTypeId(int scoutId, int scoutTypeId) {
        List<ScoutServiceProject> scoutCampList = new ArrayList<>();

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutServiceProject WHERE scoutId = " + scoutId + " AND scoutTypeId = " + scoutTypeId);

            while (rs.next()) {
                ScoutServiceProject scoutServiceProject = new ScoutServiceProject();
                scoutServiceProject.setId(rs.getInt(KeyConst.ID.getName()));
                scoutServiceProject.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutServiceProject.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutServiceProject.setServiceProjectId(rs.getInt(KeyConst.SERVICE_PROJECT_ID.getName()));

                scoutCampList.add(scoutServiceProject);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return scoutCampList;
    }

    public static synchronized void delete(List<ScoutServiceProject> scoutServiceProjectList) {
        if (Util.isEmpty(scoutServiceProjectList)) {
            return;
        }

        for (ScoutServiceProject scoutServiceProject : scoutServiceProjectList) {
            delete(scoutServiceProject.getId());
        }
    }

    public static synchronized void delete(final Integer id) {
        if (id < 1) {
            return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteScoutServiceProject(id);
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteScoutServiceProject(Integer id) {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM scoutServiceProject WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
