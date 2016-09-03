package updater.updates;

import util.MySqlConnector;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/1/2016.
 */
public class UpdateVersion_2 {

    public static void run() {
        try {
            Statement statement = MySqlConnector.getInstance().getConnection().createStatement();
            addNewVersionData(statement);

            addForeignKeysToAccessRightTable(statement);
            addForeignKeysToCounselorTable(statement);
            addForeignKeysToScoutCampTable(statement);
            addForeignKeysToScoutMeritBadgeTable(statement);
            addForeignKeysToScoutRequirementTable(statement);

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addForeignKeysToScoutRequirementTable(Statement statement) throws SQLException {
        String query = "ALTER TABLE scoutRequirement" +
                       " ADD CONSTRAINT fk_scout_requirement_advancement_id_to_advancement FOREIGN KEY (advancementId)" +
                       " REFERENCES advancement(id)," +
                       " ADD CONSTRAINT fk_scout_requirement_requirement_id_to_requirement FOREIGN KEY (requirementId)" +
                       " REFERENCES requirement(id)";
        statement.addBatch(query);
    }

    private static void addForeignKeysToScoutMeritBadgeTable(Statement statement) throws SQLException {
        String query = "ALTER TABLE scoutMeritBadge" +
                       " ADD CONSTRAINT fk_scout_badge_badge_id_to_merit_badge " +
                       " FOREIGN KEY (meritBadgeId) REFERENCES meritBadge(id)";
        statement.addBatch(query);
    }

    private static void addForeignKeysToScoutCampTable(Statement statement) throws SQLException {
        String query = "ALTER TABLE scoutCamp" +
                       " ADD CONSTRAINT fk_scout_camp_camp_id_to_camp " +
                       " FOREIGN KEY (campId) REFERENCES camp(id)";
        statement.addBatch(query);
    }

    private static void addForeignKeysToAccessRightTable(Statement statement) throws SQLException {
        String query = "ALTER TABLE accessRight" +
                       " ADD CONSTRAINT fk_access_right_user_id_to_user " +
                       " FOREIGN KEY (userId) REFERENCES user(id)";
        statement.addBatch(query);
    }

    private static void addForeignKeysToCounselorTable(Statement statement) throws SQLException {
        String query = "ALTER TABLE counselor" +
                       " ADD CONSTRAINT fk_counselor_badge_id_to_merit_badge " +
                       " FOREIGN KEY (badgeId) REFERENCES meritBadge(id)";
        statement.addBatch(query);
    }

    private static void addNewVersionData(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO dbVersion (version) VALUES(2)");
    }
}
