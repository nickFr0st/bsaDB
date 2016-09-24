package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultFamilyLife {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(8, 'Family Life', '/images/meritBadges/Family_Life.jpg', 1, 1)");

        statement.addBatch(meritBadge.toString());
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
        requirement.append("'Prepare an outline on what a family is and discuss this with your merit badge counselor. " +
                "Tell why families are important to individuals and to society. Discuss how the actions of one member can affect other members.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("8");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'List several reasons why you are important to your family and discuss this with your " +
                "parents or guardians and with your merit badge counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("8");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Prepare a list of your regular home duties or chores (at least five) and do them " +
                "for 90 days. Keep a record of how often you do each of them.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("8");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'With the approval of your parents or guardians and your merit badge counselor, decide " +
                "on and carry out a project that you would do around the home that would benefit your family. Submit " +
                "a report to your merit badge counselor outlining how the project benefited your family.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("8");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Plan and carry out a project that involves the participation of your family. After completing the project, discuss the following with your merit badge counselor:\n" +
                "a. The objective or goal of the project\n" +
                "b. How individual members of your family participated\n" +
                "c. The results of the project'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("8");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Discuss with your merit badge counselor how to plan and carry out a family meeting.\n" +
                "b. After this discussion, plan and carry out a family meeting to include the following subjects:\n" +
                "1. Avoiding substance abuse, including tobacco, alcohol, and drugs, all of which negatively affect your health and well-being\n" +
                "2. Understanding the growing-up process and how the body changes, and making responsible decisions dealing with sex\n" +
                "3. Personal and family finances\n" +
                "4. A crisis situation within your family\n" +
                "5. The effect of technology on your family\n" +
                "6. Good etiquette and manners\n" +
                "Discussion of each of these subjects will very likely carry over to more than one family meeting.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("8");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Discuss the following with your counselor:\n" +
                "a. Your understanding of what makes an effective father and why, and your thoughts on the fathers role in the family\n" +
                "b. Your understanding of the responsibilities of a parent.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("8");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
