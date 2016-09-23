package util.dbCreation.advancements.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultStar {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder newScout = new StringBuilder("INSERT INTO advancement ");
        newScout.append("VALUES(5, 'Star', 4, '/images/advancements/star.png', 6, 6.0, 1)");

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
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Be active in your unit (and patrol if you are in one) for at least four months as a First Class Scout.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Earn six merit badges, including any four from the required list for Eagle.\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________(required for Eagle)*\n" +
                "___________________________________\n" +
                "___________________________________'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'While a First Class Scout, take part in service projects totaling at least six hours of " +
                "work. These projects must be approved by your Scoutmaster.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'While a First Class Scout, serve actively in your unit for four months in one or more " +
                "of the following positions of responsibility (or carry out a unit leader-assigned leadership project " +
                "to help your unit): (see boy scout handbook for available positions)'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Take part in a Scoutmaster conference.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Complete your board of review.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
