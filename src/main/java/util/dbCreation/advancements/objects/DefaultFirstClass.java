package util.dbCreation.advancements.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultFirstClass {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addSecondClass(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addSecondClass(Statement statement) throws SQLException {
        StringBuilder newScout = new StringBuilder("INSERT INTO advancement ");
        newScout.append("VALUES(4, 'First Class', 0, '/images/advancements/first_class.png', 5, null, 1)");

        statement.addBatch(newScout.toString());
    }

    private static void addRequirements(Statement statement) throws SQLException {
        addRequirement1(statement);
        addRequirement2(statement);
        addRequirement3(statement);
        addRequirement4a(statement);
        addRequirement4b(statement);
        addRequirement4c(statement);
        addRequirement4d(statement);
        addRequirement4e(statement);
        addRequirement5(statement);
        addRequirement6(statement);
        addRequirement7a(statement);
        addRequirement7b(statement);
        addRequirement8a(statement);
        addRequirement8b(statement);
        addRequirement8c(statement);
        addRequirement8d(statement);
        addRequirement9a(statement);
        addRequirement9b(statement);
        addRequirement9c(statement);
        addRequirement10(statement);
        addRequirement11(statement);
        addRequirement12(statement);
        addRequirement13(statement);
        addRequirement14(statement);
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Demonstrate how to find directions during the day and at night without using a compass.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Using a map and compass, complete an orienteering course that covers at least one mile " +
                "and requires measuring the height and/or width of designated items (tree, tower, canyon, ditch, etc.)'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Since joining, have participated in 10 separate troop/patrol activities (other than " +
                "troop/patrol meetings), three of which included camping overnight. Demonstrate the principles of " +
                "Leave No Trace on these outings.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4a'").append(", ");
        requirement.append("'Help plan a patrol menu for one campout that includes at least one breakfast, one lunch, " +
                "and one dinner, and that requires cooking at least two of the meals. Tell how the menu includes the " +
                "foods from the MyPlate food guide or the current USDA nutrition model and meets nutritional needs.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4b'").append(", ");
        requirement.append("'Using the menu planned in requirement 4a, make a list showing the cost and food amounts " +
                "needed to feed three or more boys and secure the ingredients.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4c(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4c'").append(", ");
        requirement.append("'Tell which pans, utensils, and other gear will be needed to cook and serve these meals.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4d(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4d'").append(", ");
        requirement.append("'Explain the procedures to follow in the safe handling and storage of fresh meats, dairy " +
                "products, eggs, vegetables, and other perishable food products. Tell how to properly dispose of camp " +
                "garbage, cans, plastic containers, and other rubbish.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4e(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4e'").append(", ");
        requirement.append("'On one campout, serve as your patrols cook. Supervise your assistant(s) in using a stove " +
                "or building a cooking fire. Prepare the breakfast, lunch, and dinner planned in requirement 4a. Lead " +
                "your patrol in saying grace at the meals and supervise cleanup.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Visit and discuss with a selected individual approved by your leader (elected official, " +
                "judge, attorney, civil servant, principal, teacher) your constitutional rights and obligations as a U.S. citizen.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Identify or show evidence of at least 10 kinds of native plants found in your community.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7a'").append(", ");
        requirement.append("'Discuss when you should and should not use lashings. Then demonstrate tying the timber " +
                "hitch and clove hitch and their use in square, shear, and diagonal lashings by joining two or more poles or staves together.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7b'").append(", ");
        requirement.append("'Use lashing to make a useful camp gadget.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8a'").append(", ");
        requirement.append("'Demonstrate tying the bowline knot and describe several ways it can be used.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8b'").append(", ");
        requirement.append("'Demonstrate bandages for a sprained ankle and for injuries on the head, the upper arm, and the collarbone.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8c(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8c'").append(", ");
        requirement.append("'Show how to transport by yourself, and with one other person, a person:\n" +
                "   - From a smoke-filled room\n" +
                "   - With a sprained ankle, for at least 25 yards'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8d(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8c'").append(", ");
        requirement.append("'Tell the five most common signs of a heart attack. Explain the steps (procedures) in " +
                "cardiopulmonary resuscitation (CPR).'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9a'").append(", ");
        requirement.append("'Tell what precautions must be taken for a safe trip afloat.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9b'").append(", ");
        requirement.append("'Successfully complete the BSA swimmer test.*'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9c(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9b'").append(", ");
        requirement.append("'With a helper and a practice victim, show a line rescue both as tender and as rescuer. " +
                "(The practice victim should be approximately 30 feet from shore in deep water.)'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10a'").append(", ");
        requirement.append("'Tell someone who is eligible to join Boy Scouts, or an inactive Boy Scout, about your " +
                "troops activities. Invite him to a troop outing, activity, service project, or meeting. Tell him " +
                "how to join, or encourage the inactive Boy Scout to become active.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement11(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'11'").append(", ");
        requirement.append("'Describe the three things you should avoid doing related to the use of the Internet. " +
                "Describe a cyberbully and how you should respond to one.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement12(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'12'").append(", ");
        requirement.append("'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life. " +
                "Discuss four specific examples (different from those used in Tenderfoot requirement 13 and Second " +
                "Class requirement 11) of how you have lived the points of the Scout Law in your daily life.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement13(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'13'").append(", ");
        requirement.append("'Participate in a Scoutmaster conference.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement14(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'14'").append(", ");
        requirement.append("'Complete your board of review.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
