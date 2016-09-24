package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultHiking {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(17, 'Hiking', '/images/meritBadges/Hiking.jpg', 1, 1)");

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
        requirement.append("'Do the following:\n" +
                "a. Explain to your counselor the most likely hazards you may encounter while hiking, and what you " +
                "should do to anticipate, help prevent, mitigate, and respond to these hazards.\n" +
                "b. Show that you know first aid for injuries or illnesses that could occur while hiking, including " +
                "hypothermia, heatstroke, heat exhaustion, frostbite, dehydration, sunburn, sprained ankle, insect " +
                "stings, tick bites, snakebite, blisters, hyperventilation, and altitude sickness.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("17");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Explain and, where possible, show the points of good hiking practices including the " +
                "principles of Leave No Trace, hiking safety in the daytime and at night, courtesy to others, choice " +
                "of footwear, and proper care of feet and footwear.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("17");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Explain how hiking is an aerobic activity. Develop a plan for conditioning yourself for " +
                "10-mile hikes, and describe how you will increase your fitness for longer hikes.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("17");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Make a written plan for a 10-mile hike. Include map routes, a clothing and equipment list, " +
                "and a list of items for a trail lunch.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("17");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Take five hikes, each on a different day, and each of 10 continuous miles. You may stop " +
                "for as many short rest periods as needed, as well as one meal, during each hike, but not for an " +
                "extended period (example: overnight). Prepare a hike plan for each hike.*'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("17");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Take a hike of 20 continuous miles in one day following a hike plan you have prepared. " +
                "You may stop for as many short rest periods as needed, as well as one meal, but not for an extended " +
                "period (example: overnight).*'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("17");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'After each of the hikes (or during each hike if on one continuous trek) in requirements 5 " +
                "and 6, write a short report of your experience. Give dates and descriptions of routes covered, the " +
                "weather, and interesting things you saw. Share this report with your merit badge counsel'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("17");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
