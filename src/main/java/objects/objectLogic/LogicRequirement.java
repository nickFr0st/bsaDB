package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.Requirement;
import util.MySqlConnector;
import util.Util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 5/22/2015
 */
public class LogicRequirement {

    private static final Object lock = new Object();

    public static List<Requirement> findAll() {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return requirementList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM requirement ORDER BY id");

            while (rs.next()) {
                Requirement requirement = new Requirement();
                requirement.setId(rs.getInt(KeyConst.ID.getName()));
                requirement.setName(rs.getString(KeyConst.NAME.getName()));
                requirement.setDescription(rs.getString(KeyConst.DESCRIPTION.getName()));
                requirement.setParentId(rs.getInt(KeyConst.PARENT_ID.getName()));
                requirement.setTypeId(rs.getInt(KeyConst.TYPE_ID.getName()));
                requirementList.add(requirement);
            }

        } catch (Exception e) {
            return new ArrayList<Requirement>();
        }

        return requirementList;
    }

    public static synchronized void save(List<Requirement> requirementList) {
        if (Util.isEmpty(requirementList)) {
            return;
        }

        for (Requirement requirement : requirementList) {
            save(requirement);
        }
    }

    public static synchronized Requirement save(Requirement requirement) {
        try {
            synchronized (lock) {
                if ((requirement = saveRequirement(requirement)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return requirement;
    }

    private static Requirement saveRequirement(Requirement requirement) {
        if (requirement.getId() < 0) {
            requirement.setId(MySqlConnector.getInstance().getNextId("requirement"));
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO requirement VALUES(");
            query.append(requirement.getId()).append(", ");
            query.append("'").append(requirement.getName()).append("', ");
            query.append("'").append(requirement.getDescription()).append("', ");
            query.append(requirement.getTypeId()).append(", ");
            query.append(requirement.getParentId());
            query.append(")");

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return requirement;
    }

    public static synchronized void delete(List<Requirement> requirementList) {
        if (Util.isEmpty(requirementList)) {
            return;
        }

        for (Requirement requirement : requirementList) {
            delete(requirement);
        }
    }

    public static synchronized void delete(Requirement requirement) {
        if (requirement == null || requirement.getId() < 1) {
            return;
        }

        try {
            synchronized (lock) {
                if (deleteRequirement(requirement.getId())) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void delete(int requirementId) {
        if (requirementId < 0) {
            return;
        }

        try {
            synchronized (lock) {
                if (deleteRequirement(requirementId)) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteRequirement(Integer id) {

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate("DELETE FROM requirement WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static synchronized Requirement update(Requirement requirement) {
        if (requirement == null) {
            return null;
        }

        try {
            synchronized (lock) {
                if ((requirement = updateRequirement(requirement)) != null) {
                    lock.wait(MySqlConnector.WAIT_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return requirement;
    }

    private static Requirement updateRequirement(Requirement requirement) {

        try {
            StringBuilder query = new StringBuilder();
            query.append("UPDATE requirement SET ");
            query.append("name = '").append(requirement.getName()).append("', ");
            query.append("description = '").append(requirement.getDescription()).append("' ");
            query.append("WHERE id = ").append(requirement.getId());

            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return requirement;
    }
}
