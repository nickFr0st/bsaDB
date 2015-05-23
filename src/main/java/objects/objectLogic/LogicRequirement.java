package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.Requirement;
import util.MySqlConnector;

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
}
