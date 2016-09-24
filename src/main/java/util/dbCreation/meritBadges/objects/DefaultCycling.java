package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultCycling {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(14, 'Cycling', '/images/meritBadges/Cycling.jpg', 1, 1)");

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
                "a. Explain to your counselor the most likely hazards you may encounter while participating in cycling " +
                "activities and what you should do to anticipate, help prevent, mitigate, and respond to these hazards.\n" +
                "b. Show that you know first aid for injuries or illnesses that could occur while cycling, including " +
                "cuts, scratches, blisters, sunburn, heat exhaustion, heatstroke, hypothermia, dehydration, " +
                "insect stings, tick bites, and snakebite. Explain to your counselor why you should be able to identify " +
                "the poisonous plants and poisonous animals that are found in your area.\n" +
                "c. Explain the importance of wearing a properly sized and fitted helmet while cycling, and of wearing " +
                "the right clothing for the weather. Know the BSA Bike Safety Guidelines.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("14");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Clean and adjust a bicycle. Prepare it for inspection using a bicycle safety checklist. " +
                "Be sure the bicycle meets local laws.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("14");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Show your bicycle to your counselor for inspection. Point out the adjustments or repairs you have made. Do the following:\n" +
                "a. Show all points that need oiling regularly.\n" +
                "b. Show points that should be checked regularly to make sure the bicycle is safe to ride.\n" +
                "c. Show how to adjust brakes, seat level and height, and steering tube.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("14");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Describe how to brake safely with foot brakes and with hand brakes.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("14");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Show how to repair a flat by removing the tire, replacing or patching the tube, and remounting the tire.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("14");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Describe your state and local traffic laws for bicycles. Compare them with motor-vehicle laws.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("14");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Using the BSA buddy system, complete all of the requirements for ONE of the following options: road biking OR mountain biking.\n" +
                "a. Road Biking\n" +
                "(1) Take a road test with your counselor and demonstrate the following:\n" +
                "(a) Properly mount, pedal, and brake, including emergency stops.\n" +
                "(b) On an urban street with light traffic, properly execute a left turn from the center of the street; also demonstrate an alternate left-turn technique used during periods of heavy traffic.\n" +
                "(c) Properly execute a right turn.\n" +
                "(d) Demonstrate appropriate actions at a right-turn-only lane when you are continuing straight.\n" +
                "(e) Show proper curbside and road-edge riding.Show how to ride safely along a row of parked cars.\n" +
                "(f) Cross railroad tracks properly.\n" +
                "(2) Avoiding main highways, take two rides of 10 miles each, two rides of 15 miles each, and two rides of 25 miles each. You must make a report of the rides taken. List dates for the routes traveled, and interesting things seen.\n" +
                "(3) After completing requirement b for the road biking option, do ONE of the following:\n" +
                "(a) Lay out on a road map a 50-mile trip. Stay away from main highways. Using your map, make this ride in eight hours.\n" +
                "(b) Participate in an organized bike tour of at least 50 miles. Make this ride in eight hours. Afterward, use the tours cue sheet to make a map of the ride.\n" +
                "b. Mountain Biking\n" +
                "(1) Take a trail ride with your counselor and demonstrate the following:\n" +
                "(a) Properly mount, pedal, and brake, including emergency stops.\n" +
                "(b) Show shifting skills as applicable to climbs and obstacles.\n" +
                "(c) Show proper trail etiquette to hikers and other cyclists, including when to yield the right-of-way.\n" +
                "(d) Show proper technique for riding up and down hills.\n" +
                "(e) Demonstrate how to correctly cross an obstacle by either going over the obstacle on your bike or dismounting your bike and crossing over or around the obstacle.\n" +
                "(f) Cross rocks, gravel, and roots properly.\n" +
                "(2) Describe the rules of trail riding, including how to know when a trail is unsuitable for riding.\n" +
                "(3) On trails approved by your counselor, take two rides of 2 miles each, two rides of 5 miles each, and two rides of 8 miles each. You must make a report of the rides taken. List dates for the routes traveled, and interesting things seen.\n" +
                "(4) After fulfilling the previous requirement, lay out on a trail map a 22-mile trip. You may include multiple trail systems, if needed. Stay away from main highways. Using your map, make this ride in six hours.\n" +
                "*The bicycle must have all required safety features. It must be registered as required by your local traffic laws.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("14");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
