package util.dbCreation.advancements.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultNewScout {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder newScout = new StringBuilder("INSERT INTO advancement ");
        newScout.append("VALUES(1, 'New Scout', 0, '/images/advancements/new_scout.png', 2, null, 1)");

        statement.addBatch(newScout.toString());
    }

    private static void addRequirements(Statement statement) throws SQLException {
        addRequirement1(statement);
        addRequirement2(statement);
        addRequirement3(statement);
        addRequirement4(statement);
        addRequirement5(statement);
        addRequirement6(statement);
        addRequirement7(statement);
        addRequirement8(statement);
        addRequirement9(statement);
        addRequirement10(statement);
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Meet the age requirements. Be a boy who is 11 years old, or one who has completed the " +
                "fifth grade or earned the Arrow of Light Award and is at least 10 years old, but is not yet 18 years old.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Find a Scout troop near your home.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Complete a Boy Scout application and health history signed by your parent or guardian.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Repeat the Pledge of Allegiance.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Demonstrate the Scout sign, salute, and handshake.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Demonstrate tying the square knot (a joining knot).'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Understand and agree to live by the Scout Oath, Scout Law, motto, slogan, and the Outdoor Code.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Describe the Scout badge.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Complete the pamphlet exercises. With your parent or guardian, complete the exercises " +
                "in the pamphlet \"How to Protect Your Children from Child Abuse: A Parents Guide\".'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10'").append(", ");
        requirement.append("'Participate in a Scoutmaster conference. Turn in your Boy Scout application and health " +
                "history form signed by your parent or guardian, then participate in a Scoutmaster conference.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("1");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
