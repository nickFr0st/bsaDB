package util.dbCreation.advancements.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultLife {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder newScout = new StringBuilder("INSERT INTO advancement ");
        newScout.append("VALUES(6, 'Life', 6, '/images/advancements/life.png', 7, 6.0, 1)");

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
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Be active in your unit (and patrol if you are in one) for at least six months as a Star Scout.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Earn five more merit badges (so that you have 11 in all), including any three more from the required list for Eagle.\n" +
                "___________________________________ (required for Eagle)*\n" +
                "___________________________________ (required for Eagle)*\n" +
                "___________________________________ (required for Eagle)*\n" +
                "___________________________________\n" +
                "___________________________________'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'While a Star Scout, take part in service projects totaling at least six hours of work. " +
                "These projects must be approved by your Scoutmaster.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'While a Star Scout, serve actively in your unit for six months in one or more of the " +
                "positions of responsibility listed in requirement 5 for Star Scout (or carry out a Scoutmaster-assigned " +
                "leadership project to help the troop).'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'While a Star Scout, use the EDGE method to teach another Scout (preferably younger than you) the skills from ONE of the following seven choices, so that he is prepared to pass those requirements to his unit leaders satisfaction.\n" +
                "  a.  Second Class: 7a and 7c (first aid)\n" +
                "  b.  Second Class: 1a (outdoor skills)\n" +
                "  c.  Second Class: 3c, 3d, 3e, and 3f (cooking/camping)\n" +
                "  d.  First Class: 8a, 8b, 8c, and 8d (first aid)\n" +
                "  e.  First Class: 1, 7a, and 7b (outdoor skills)\n" +
                "  f.  First Class: 4a, 4b, and 4d (cooking/camping)\n" +
                "  g.  Three requirements from one of the required for Eagle merit badges, as approved by your unit leader.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Take part in a Scoutmaster conference.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Complete your board of review.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
