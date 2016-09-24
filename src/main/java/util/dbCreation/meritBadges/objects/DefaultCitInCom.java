package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultCitInCom {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(5, 'Citizenship in the Community', '/images/meritBadges/Citizenship_in_the_Community.jpg', 1, 1)");

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
        requirement.append("'Discuss with your counselor what citizenship in the community means and what it takes to " +
                "be a good citizen in your community. Discuss the rights, duties, and obligations of citizenship, and " +
                "explain how you can demonstrate good citizenship in your community, Scouting unit, place of worship or school.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. On a map of your community, locate and point out the following:\n" +
                "1. Chief government buildings such as your city hall, county courthouse, and public works/services facility\n" +
                "2. Fire station, police station, and hospital nearest your home\n" +
                "3. Historical or other interesting points\n" +
                "b. Chart the organization of your local or state government. Show the top offices and tell whether they are elected or appointed.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Attend a meeting of your city, town, or county council or school board; OR attend a municipal, county, or state court session.\n" +
                "b. Choose one of the issues discussed at the meeting where a difference of opinions was expressed, " +
                "and explain to your counselor why you agree with one opinion more than you do another one.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Choose an issue that is important to the citizens of your community; then do the following:\n" +
                "a. Find out which branch of local government is responsible for this issue.\n" +
                "b. With your counselors and a parents approval, interview one person from the branch of government " +
                "you identified in requirement 4a. Ask what is being done about this issue and how young people can help.\n" +
                "c. Share what you have learned with your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'With the approval of your counselor and a parent, watch a movie that shows how the actions " +
                "of one individual or group of individuals can have a positive effect on a community. Discuss with " +
                "your counselor what you learned from the movie about what it means to be a valuable and concerned member of the community.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'List some of the services (such as the library, recreation center, public transportation, " +
                "and public safety) your community provides that are funded by taxpayers. Tell your counselor why these " +
                "services are important to your community.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Choose a charitable organization outside of Scouting that interests you and brings people in your " +
                "community together to work for the good of your community.\n" +
                "b. Using a variety of resources (including newspapers, fliers and other literature, the Internet, " +
                "volunteers, and employees of the organization), find out more about this organization.\n" +
                "c. With your counselors and your parents approval, contact the organization and find out what young " +
                "people can do to help. While working on this merit badge, volunteer at least eight hours of your time " +
                "for the organization. After your volunteer experience is over, discuss what you have learned with your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Develop a public presentation (such as a video, slide show, speech, digital presentation, " +
                "or photo exhibit) about important and unique aspects of your community. Include information about the " +
                "history, cultures, and ethnic groups of your community; its best features and popular places where " +
                "people gather; and the challenges it faces. Stage your presentation in front of your merit badge " +
                "counselor or a group, such as your patrol or a class at school.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("5");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
