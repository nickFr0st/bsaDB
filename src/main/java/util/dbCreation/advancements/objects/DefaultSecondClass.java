package util.dbCreation.advancements.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultSecondClass {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addSecondClass(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addSecondClass(Statement statement) throws SQLException {
        StringBuilder newScout = new StringBuilder("INSERT INTO advancement ");
        newScout.append("VALUES(3, 'Second Class', 0, '/images/advancements/second_class.png', 4, 1.0, 1)");

        statement.addBatch(newScout.toString());
    }

    private static void addRequirements(Statement statement) throws SQLException {
        addRequirement1a(statement);
        addRequirement1b(statement);
        addRequirement2(statement);
        addRequirement3a(statement);
        addRequirement3b(statement);
        addRequirement3c(statement);
        addRequirement3d(statement);
        addRequirement3e(statement);
        addRequirement3f(statement);
        addRequirement3g(statement);
        addRequirement4(statement);
        addRequirement5(statement);
        addRequirement6(statement);
        addRequirement7a(statement);
        addRequirement7b(statement);
        addRequirement7c(statement);
        addRequirement8a(statement);
        addRequirement8b(statement);
        addRequirement8c(statement);
        addRequirement9a(statement);
        addRequirement9b(statement);
        addRequirement10(statement);
        addRequirement11(statement);
        addRequirement12(statement);
        addRequirement13(statement);
    }

    private static void addRequirement1a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1a'").append(", ");
        requirement.append("'Demonstrate how a compass works and how to orient a map. Explain what map symbols mean.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement1b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1b'").append(", ");
        requirement.append("'Using a compass and a map together, take a five-mile hike (or 10 miles by bike) " +
                "approved by your adult leader and your parent or guardian.*'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Discuss the principles of Leave No Trace.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3a'").append(", ");
        requirement.append("'Since joining, have participated in five separate troop/patrol activities " +
                "(other than troop/patrol meetings), two of which included camping overnight.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3b'").append(", ");
        requirement.append("'On one of these campouts, select your patrol site and sleep in a tent that you pitched. " +
                "Explain what factors you should consider when choosing a patrol site and where to pitch a tent.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3c(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3c'").append(", ");
        requirement.append("'Demonstrate proper care, sharpening, and use of the knife, saw, and ax, and describe when they should be used.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3d(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3d'").append(", ");
        requirement.append("'Use the tools listed in requirement 3c to prepare tinder, kindling, and fuel for a cooking fire.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3e(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3e'").append(", ");
        requirement.append("'Explain when it is appropriate to use a cooking fire. At an approved outdoor location " +
                "and at an approved time, and using the tinder, kindling, and fuel wood from requirement 3d, " +
                "demonstrate how to build a fire; light the fire, unless prohibited by local fire restrictions. " +
                "After allowing the flames to burn safely for at least two minutes, safely extinguish the flames " +
                "with minimal impact to the fire site.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3f(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3f'").append(", ");
        requirement.append("'Explain when it is appropriate to use a lightweight stove or propane stove. Set up a " +
                "lightweight stove or propane stove; light the stove, unless prohibited by local fire restrictions. " +
                "Describe the safety procedures for using these types of stoves.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3g(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3g'").append(", ");
        requirement.append("'On one campout, plan and cook one hot breakfast or lunch, selecting foods from the " +
                "MyPlate food guide or the current USDA nutrition model. Explain the importance of good nutrition. " +
                "Tell how to transport, store, and prepare the foods you selected.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Participate in a flag ceremony for your school, religious institution, chartered " +
                "organization, community, or troop activity. Explain to your leader what respect is due the flag of the United States.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Participate in an approved (minimum of one hour) service project.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Identify or show evidence of at least 10 kinds of wild animals (birds, mammals, reptiles, fish, mollusks) found in your community.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7a'").append(", ");
        requirement.append("'Show what to do for \"hurry\" cases of stopped breathing, serious bleeding, and ingested poisoning.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7b'").append(", ");
        requirement.append("'Prepare a personal first aid kit to take with you on a hike.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7c(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7c'").append(", ");
        requirement.append("'Demonstrate first aid for the following:\n" +
                "  - Object in the eye\n" +
                "  - Bite of a suspected rabid animal\n" +
                "  - Puncture wounds from a splinter, nail, and fishhook\n" +
                "  - Serious burns (partial thickness, or second-degree)\n" +
                "  - Heat exhaustion\n" +
                "  - Shock\n" +
                "  - Heatstroke, dehydration, hypothermia, and hyperventilation'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8a'").append(", ");
        requirement.append("'Tell what precautions must be taken for a safe swim.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8b'").append(", ");
        requirement.append("'Demonstrate your ability to jump feet first into water over your head in depth, " +
                "level off and swim 25 feet on the surface, stop, turn sharply, resume swimming, then return to your starting place.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8c(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8c'").append(", ");
        requirement.append("'Demonstrate water rescue methods by reaching with your arm or leg, by reaching with a" +
                " suitable object, and by throwing lines and objects. Explain why swimming rescues should not be" +
                " attempted when a reaching or throwing rescue is possible, and explain why and how a rescue swimmer" +
                " should avoid contact with the victim.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9a(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9a'").append(", ");
        requirement.append("'Participate in a school, community, or troop program on the dangers of using drugs, " +
                "alcohol, and tobacco, and other practices that could be harmful to your health. Discuss your " +
                "participation in the program with your family, and explain the dangers of substance addictions.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9b(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9b'").append(", ");
        requirement.append("'Explain the three Rs of personal safety and protection.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10a'").append(", ");
        requirement.append("'Earn an amount of money agreed upon by you and your parent, then save at least 50 percent of that money.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement11(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'11'").append(", ");
        requirement.append("'Demonstrate Scout spirit by living the Scout Oath and Scout Law in your everyday life. " +
                "Discuss four specific examples (different from those used for Tenderfoot requirement 13) of how you " +
                "have lived the points of the Scout Law in your daily life.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement12(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'12'").append(", ");
        requirement.append("'Participate in a Scoutmaster conference.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement13(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'13'").append(", ");
        requirement.append("'Complete your board of review.'").append(", ");
        requirement.append("10").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
