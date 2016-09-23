package util.dbCreation.advancements.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultTenderfoot {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addTenderfoot(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addTenderfoot(Statement statement) throws SQLException {
        StringBuilder newScout = new StringBuilder("INSERT INTO advancement ");
        newScout.append("VALUES(2, 'Tenderfoot', 0, '/images/advancements/tenderfoot.png', 3, null, 1)");

        statement.addBatch(newScout.toString());
    }

    private static void addRequirements(Statement statement) throws SQLException {
        addRequirement1(statement);
        addRequirement2(statement);
        addRequirement3(statement);
        addRequirement4a(statement);
        addRequirement4b(statement);
        addRequirement4c(statement);
        addRequirement5(statement);
        addRequirement6(statement);
        addRequirement7(statement);
        addRequirement8(statement);
        addRequirement9(statement);
        addRequirement10a(statement);
        addRequirement10b(statement);
        addRequirement11(statement);
        addRequirement12a(statement);
        addRequirement12b(statement);
        addRequirement13(statement);
        addRequirement14(statement);
        addRequirement15(statement);
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Present yourself to your leader, properly dressed, before going on an overnight " +
                "camping trip. Show the camping gear you will use. Show the right way to pack and carry it.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Spend at least one night on a patrol or troop campout. Sleep in a tent you have helped pitch.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'On the campout, assist in preparing and cooking one of your patrols meals. Tell why it is" +
                " important for each patrol member to share in meal preparation and cleanup, and explain the importance" +
                " of eating together.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4a'").append(", ");
        requirement.append("'Demonstrate how to whip and fuse the ends of a rope.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4b'").append(", ");
        requirement.append("'Demonstrate you know how to tie the following knots and tell what their uses are: " +
                "two half hitches and the taut-line hitch.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4c(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4c'").append(", ");
        requirement.append("'Using the EDGE method, teach another person how to tie the square knot.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Explain the rules of safe hiking, both on the highway and cross-country, during the day " +
                "and at night. Explain what to do if you are lost.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Demonstrate how to display, raise, lower, and fold the American flag.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Repeat from memory and explain in your own words the Scout Oath, Law, motto, and slogan.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Know your patrol name, give the patrol yell, and describe your patrol flag.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Explain the importance of the buddy system as it relates to your personal safety on " +
                "outings and in your neighborhood. Describe what a bully is and how you should respond to one.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10a'").append(", ");
        requirement.append("'Record your best in the following tests:\n" +
                "Current results\n" +
                "Push-ups ________\n" +
                "Pull-ups ________\n" +
                "Sit-ups ________\n" +
                "Standing long jump (______ ft. ______ in.)\n" +
                "1/4 mile walk/run _____________\n" +
                "30 days later\n" +
                "Push-ups ________\n" +
                "Pull-ups ________\n" +
                "Sit-ups ________\n" +
                "Standing long jump (______ ft. ______ in.)\n" +
                "1/4 mile walk/run _____________'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10b'").append(", ");
        requirement.append("'Show improvement in the activities listed in requirement 10a after practicing for 30 days.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement11(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'11'").append(", ");
        requirement.append("'Identify local poisonous plants; tell how to treat for exposure to them.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement12a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'12a'").append(", ");
        requirement.append("'Demonstrate how to care for someone who is choking.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement12b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'12b'").append(", ");
        requirement.append("'Show first aid for the following:\n" +
                "  - Simple cuts and scrapes\n" +
                "  - Blisters on the hand and foot\n" +
                "  - Minor (thermal/heat) burns or scalds (superficial, or first degree)\n" +
                "  - Bites or stings of insects and ticks\n" +
                "  - Venomous snakebite\n" +
                "  - Nosebleed\n" +
                "  - Frostbite and sunburn'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement13(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'13'").append(", ");
        requirement.append("'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday " +
                "life. Discuss four specific examples of how you have lived the points of the Scout Law in your daily life.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement14(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'14'").append(", ");
        requirement.append("'Participate in a Scoutmaster conference.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement15(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'15'").append(", ");
        requirement.append("'Complete your board of review.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
