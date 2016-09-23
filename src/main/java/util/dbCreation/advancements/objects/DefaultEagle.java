package util.dbCreation.advancements.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultEagle {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder newScout = new StringBuilder("INSERT INTO advancement ");
        newScout.append("VALUES(7, 'Eagle', 6, '/images/advancements/eagle.png', null, null, 1)");

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
        requirement.append("'Be active in your troop, team, crew, or ship for a period of at least six months after " +
                "you have achieved the rank of Life Scout.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Demonstrate that you live by the principles of the Scout Oath and Scout Law in your " +
                "daily life. List on your Eagle Scout Rank Application the names of individuals who know you " +
                "personally and would be willing to provide a recommendation on your behalf, including parents/" +
                "guardians, religious, educational, and employer references.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Earn a total of 21 merit badges (10 more than you already have), including the following:\n" +
                "  a. First Aid\n" +
                "  b. Citizenship in the Community\n" +
                "  c. Citizenship in the Nation\n" +
                "  d. Citizenship in the World\n" +
                "  e. Communication\n" +
                "  f. Cooking\n" +
                "  g. Personal Fitness\n" +
                "  h. Emergency Preparedness OR Lifesaving*\n" +
                "  i. Environmental Science OR Sustainability*\n" +
                "  j. Personal Management\n" +
                "  k. Swimming OR Hiking OR Cycling*\n" +
                "  l. Camping, and\n" +
                "  m. Family Life\n" +
                "* You must choose only one merit badge listed in items h, i, and k. If you have earned more than one of " +
                "the badges listed in items h, i, and/or k, choose one and list the remaining badges to make your total of 21.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'While a Life Scout, serve actively in your unit for a period of six months in one or more" +
                " of the following positions of responsibility. List only those positions served after your Life board" +
                " of review date. (see boy scout handbook for available positions)'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'While a Life Scout, plan, develop, and give leadership to others in a service project " +
                "helpful to any religious institution, any school, or your community. (The project should benefit an " +
                "organization other than Boy Scouting.) The project proposal must be approved by the organization " +
                "benefiting from the effort, your unit leader and unit committee, and the council or district before " +
                "you start. You must use the Eagle Scout Leadership Service Project Workbook, BSA publication No. " +
                "521-927, in meeting this requirement. (To learn more about the Eagle Scout service project, see the " +
                "Guide To Advancement, topics 9.0.2.0 through 9.0.2.15.)'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Take part in a unit leader conference.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Successfully complete an Eagle Scout board of review. In preparation for your board of " +
                "review, prepare and attach to your Eagle Scout Rank Application a statement of your ambitions and " +
                "life purpose and a listing of positions held in your religious institution, school, camp, community, " +
                "or other organizations, during which you demonstrated leadership skills. Include honors and awards " +
                "received during this service.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
