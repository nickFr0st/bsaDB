package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ServiceProject;
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
public class LogicServiceProject {

    public static Set<ServiceProject> findAll(List<ServiceProject> excludedProjects) {
        Set<ServiceProject> projectList = new LinkedHashSet<>();

        StringBuilder ids = new StringBuilder();
        if (!Util.isEmpty(excludedProjects)) {
            for (ServiceProject project : excludedProjects) {
                if (!ids.toString().isEmpty()) {
                    ids.append(",");
                }
                ids.append(project.getId());
            }
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs;
            if (ids.toString().isEmpty()) {
                rs = statement.executeQuery("SELECT * FROM serviceProject ORDER BY startDate");
            } else {
                rs = statement.executeQuery("SELECT * FROM serviceProject WHERE id NOT IN(" + ids + ")  ORDER BY startDate");
            }

            while (rs.next()) {
                ServiceProject serviceProject = new ServiceProject();
                serviceProject.setId(rs.getInt(KeyConst.ID.getName()));
                serviceProject.setName(rs.getString(KeyConst.NAME.getName()));
                serviceProject.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                serviceProject.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                serviceProject.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                serviceProject.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                serviceProject.setNote(rs.getString(KeyConst.NOTE.getName()));
                serviceProject.setDuration(rs.getDouble(KeyConst.DURATION.getName()));

                projectList.add(serviceProject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedHashSet<>();
        }

        return projectList;
    }

    public static Set<ServiceProject> findAllByName(List<String> nameList) {
        if (Util.isEmpty(nameList)) {
            return new LinkedHashSet<>();
        }

        Set<ServiceProject> serviceProjectSet = new LinkedHashSet<>();

        for (String name : nameList) {
            try {
                Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM serviceProject WHERE name LIKE '" + name + "'" + " ORDER BY startDate");

                if (rs.next()) {
                    ServiceProject serviceProject = new ServiceProject();
                    serviceProject.setId(rs.getInt(KeyConst.ID.getName()));
                    serviceProject.setName(rs.getString(KeyConst.NAME.getName()));
                    serviceProject.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                    serviceProject.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                    serviceProject.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                    serviceProject.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                    serviceProject.setNote(rs.getString(KeyConst.NOTE.getName()));
                    serviceProject.setDuration(rs.getDouble(KeyConst.DURATION.getName()));

                    serviceProjectSet.add(serviceProject);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return new LinkedHashSet<>();
            }
        }

        return serviceProjectSet;
    }

    public static ServiceProject findByName(String name) {
        ServiceProject serviceProject = null;

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM serviceProject WHERE name LIKE '" + name + "'");

            if (rs.next()) {
                serviceProject = new ServiceProject();
                serviceProject.setId(rs.getInt(KeyConst.ID.getName()));
                serviceProject.setName(rs.getString(KeyConst.NAME.getName()));
                serviceProject.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                serviceProject.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                serviceProject.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                serviceProject.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                serviceProject.setNote(rs.getString(KeyConst.NOTE.getName()));
                serviceProject.setDuration(rs.getDouble(KeyConst.DURATION.getName()));
            }
        } catch (SQLException e) {
            return null;
        }

        return serviceProject;
    }

    public static ServiceProject findById(int serviceProjectId) {
        ServiceProject serviceProject = null;

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM serviceProject WHERE id = " + serviceProjectId);

            if (rs.next()) {
                serviceProject = new ServiceProject();
                serviceProject.setId(rs.getInt(KeyConst.ID.getName()));
                serviceProject.setName(rs.getString(KeyConst.NAME.getName()));
                serviceProject.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                serviceProject.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                serviceProject.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                serviceProject.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                serviceProject.setNote(rs.getString(KeyConst.NOTE.getName()));
                serviceProject.setDuration(rs.getDouble(KeyConst.DURATION.getName()));
            }
        } catch (SQLException e) {
            return null;
        }

        return serviceProject;
    }

    public static synchronized ServiceProject save(final ServiceProject serviceProject) {
        if (serviceProject == null) {
             return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveServiceProject(serviceProject);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serviceProject;
    }

    private static void saveServiceProject(ServiceProject serviceProject) {
        if (serviceProject.getId() < 0) {
            serviceProject.setId(MySqlConnector.getInstance().getNextId("serviceProject"));
        }

        if (serviceProject.getId() < 0) {
            return;
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO serviceProject VALUES(");
            query.append(serviceProject.getId()).append(", ");
            query.append("'").append(Util.DATA_BASE_DATE_FORMAT.format(serviceProject.getStartDate())).append("', ");
            query.append(serviceProject.getDuration()).append(", ");
            query.append(serviceProject.getScoutTypeId()).append(", ");
            query.append("'").append(serviceProject.getName().replace("'", "''")).append("', ");
            query.append("'").append(serviceProject.getLocation().replace("'", "''")).append("', ");
            query.append("'").append(serviceProject.getLeaders().replace("'", "''")).append("', ");
            query.append("'").append(serviceProject.getNote().replace("'", "''")).append("' ");
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized ServiceProject update(final ServiceProject serviceProject) {
        if (serviceProject == null) {
            return null;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    updateServiceProject(serviceProject);
                }
            });

            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serviceProject;
    }

    private static void updateServiceProject(ServiceProject serviceProject) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE serviceProject SET ");
            query.append("name = '").append(serviceProject.getName().replace("'", "''")).append("', ");
            query.append("scoutTypeId = ").append(serviceProject.getScoutTypeId()).append(", ");
            query.append("location = '").append(serviceProject.getLocation()).append("', ");
            query.append("startDate = '").append(Util.DATA_BASE_DATE_FORMAT.format(serviceProject.getStartDate())).append("', ");
            query.append("leaders = '").append(serviceProject.getLeaders().replace("'", "''")).append("', ");
            query.append("note = '").append(serviceProject.getNote().replace("'", "''")).append("', ");
            query.append("duration = ").append(serviceProject.getDuration()).append(" ");
            query.append("WHERE id = ").append(serviceProject.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(final ServiceProject serviceProject) {
        if (serviceProject == null || serviceProject.getId() <= 1) {
             return;
        }

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    deleteServiceProject(serviceProject.getId());
                }
            });
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void deleteServiceProject(Integer id) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("DELETE serviceProject, scoutServiceProject ");
            query.append("FROM serviceProject ");
            query.append("LEFT JOIN scoutServiceProject ON scoutServiceProject.serviceProjectId = serviceProject.id ");
            query.append("WHERE serviceProject.id = ").append(id);

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ServiceProject> findAllByScoutIdAndTypeId(int scoutId, int scoutTypeId) {
        List<ServiceProject> serviceProjectList = new ArrayList<>();

        try {

            StringBuilder query = new StringBuilder();
            query.append("SELECT serviceProject.* ");
            query.append("FROM serviceProject ");
            query.append("INNER JOIN scoutServiceProject ON scoutServiceProject.serviceProjectId = serviceProject.id ");
            query.append("WHERE serviceProject.scoutTypeId = ").append(scoutTypeId).append(" ");
            query.append("AND scoutServiceProject.scoutId = ").append(scoutId).append(" ");
            query.append("ORDER BY serviceProject.startDate");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query.toString());

            while (rs.next()) {
                ServiceProject serviceProject = new ServiceProject();
                serviceProject.setId(rs.getInt(KeyConst.ID.getName()));
                serviceProject.setName(rs.getString(KeyConst.NAME.getName()));
                serviceProject.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                serviceProject.setLocation(rs.getString(KeyConst.LOCATION.getName()));
                serviceProject.setStartDate(rs.getDate(KeyConst.START_DATE.getName()));
                serviceProject.setLeaders(rs.getString(KeyConst.LEADER.getName()));
                serviceProject.setNote(rs.getString(KeyConst.NOTE.getName()));
                serviceProject.setDuration(rs.getDouble(KeyConst.DURATION.getName()));

                serviceProjectList.add(serviceProject);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return serviceProjectList;
    }
}
