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

    private static final Object lock = new Object();

    public static List<Advancement> findAll() {
        List<Advancement> advancementList = new ArrayList<Advancement>();

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
            return new ArrayList<Advancement>();
        }

        return advancementList;
    }
}
