package objects.objectLogic;

import constants.KeyConst;
import objects.databaseObjects.ScoutCamp;
import util.MySqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 3/10/2016
 */
public class LogicScoutCamp {

    public static List<ScoutCamp> findAllByCampId(int campId) {
        List<ScoutCamp> scoutCampList = new ArrayList<>();

        if (!MySqlConnector.getInstance().checkForDataBaseConnection()) {
            return scoutCampList;
        }

        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scoutCamp WHERE campId = " + campId);

            while (rs.next()) {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setId(rs.getInt(KeyConst.ID.getName()));
                scoutCamp.setScoutId(rs.getInt(KeyConst.SCOUT_ID.getName()));
                scoutCamp.setScoutTypeId(rs.getInt(KeyConst.SCOUT_TYPE_ID.getName()));
                scoutCamp.setCampId(rs.getInt(KeyConst.CAMP_ID.getName()));

                scoutCampList.add(scoutCamp);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return scoutCampList;
    }
}
