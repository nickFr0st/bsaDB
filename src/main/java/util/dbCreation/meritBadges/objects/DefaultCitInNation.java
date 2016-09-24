package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultCitInNation {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(15, 'Citizenship in the Nation', '/images/meritBadges/Citizenship_in_the_Nation.jpg', 1, 1)");

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
        addRequirement8(statement);
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Explain what citizenship in the nation means and what it takes to be a good citizen of " +
                "this country. Discuss the rights, duties, and obligations of a responsible and active American citizen.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do TWO of the following:\n" +
                "a. Visit a place that is listed as a National Historic Landmark or that is on the National Register of " +
                "Historic Places. Tell your counselor what you learned about the landmark or site and what you found interesting about it.\n" +
                "b. Tour your state capitol building or the U.S. Capitol. Tell your counselor what you learned about the " +
                "capitol, its function, and the history.\n" +
                "c. Tour a federal facility. Explain to your counselor what you saw there and what you learned about its " +
                "function in the local community and how it serves this nation.\n" +
                "d. Choose a national monument that interests you. Using books, brochures, the Internet (with your parents " +
                "permission), and other resources, find out more about the monument. Tell your counselor what you learned, " +
                "and explain why the monument is important to this countries citizens.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Watch the national evening news five days in a row OR read the front page of a major daily " +
                "newspaper five days in a row. Discuss the national issues you learned about with your counselor. " +
                "Choose one of the issues and explain how it affects you and your family.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Discuss each of the following documents with your counselor. Tell your counselor how you " +
                "feel life in the United States might be different without each one.\n" +
                "a. Declaration of Independence\n" +
                "b. Preamble to the Constitution\n" +
                "c. The Constitution\n" +
                "d. Bill of Rights\n" +
                "e. Amendments to the Constitution'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'List the six functions of government as noted in the preamble to the Constitution. Discuss " +
                "with your counselor how these functions affect your family and local community.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'With your counselors approval, choose a speech of national historical importance. Find out " +
                "about the author, and tell your counselor about the person who gave the speech. Explain the importance " +
                "of the speech at the time it was given, and tell how it applies to American citizens today. Choose a " +
                "sentence or two from the speech that has significant meaning to you, and tell your counselor why.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Name the three branches of our federal government and explain to your counselor their " +
                "functions. Explain how citizens are involved in each branch. For each branch of government, explain " +
                "the importance of the system of checks and balances.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Name your two senators and the member of Congress from your congressional district. " +
                "Write a letter about a national issue and send it to one of these elected officials, sharing your " +
                "view with him or her. Show your letter and any response you receive to your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("15");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
