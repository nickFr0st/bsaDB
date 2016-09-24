package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultLifesaving {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(3, 'Lifesaving', '/images/meritBadges/Lifesaving.jpg', 1, 1)");

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
        addRequirement9(statement);
        addRequirement10(statement);
        addRequirement11(statement);
        addRequirement12(statement);
        addRequirement13(statement);
        addRequirement14(statement);
        addRequirement15(statement);
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Before doing requirements 2 through 15\n" +
                "a. Complete Second Class requirements 7a through 7c and First Class requirements 9a through 9c.\n" +
                "Second Class requirements 7a through 7c\n" +
                "7.a. Tell what precautions must be taken for a safe swim.\n" +
                "7.b. Demonstrate your ability to jump feetfirst into water over your head in depth, level off and swim 25 feet on the surface, stop, turn sharply, resume swimming, then return to your starting place.\n" +
                "7.c. Demonstrate water rescue methods by reaching with your arm or leg, reaching with a suitable object, and by throwing lines and objects. Explain why swimming rescues should not be attempted when a reaching or throwing rescue is possible, and explain why and how a rescue swimmer should avoid contact with the victim.\n" +
                "First Class requirements 9a through 9c\n" +
                "9.a. Tell what precautions should be taken for a safe trip afloat.\n" +
                "9.b. Successfully complete the BSA swimmer test.\n" +
                "9.c. With a helper and a practice victim, show a line rescue both as tender and as rescuer. (The practice victim should be approximately 30 feet from shore in deep water).\n" +
                "b. Swim continuously for 400 yards using each of the following strokes in a strong manner for at least 50 continuous yards: front crawl, sidestroke, breaststroke, and elementary backstroke.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Explain the following:\n" +
                "a. Common drowning situations and how to prevent them.\n" +
                "b. How to identify persons in the water who need assistance.\n" +
                "c. The order of methods in water rescue.\n" +
                "d. How rescue techniques vary depending on the setting and the condition of the person needing assistance.\n" +
                "e. Situations for which in-water rescues should not be undertaken.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Demonstrate reaching rescues using various items such as arms, legs, towels, shirts, paddles, and poles.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Demonstrate throwing rescues using various items such as lines, ring buoys, rescue bags, " +
                "and free-floating supports. Successfully place at least one such aid within reach of a practice victim 25 feet from shore.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Show or explain the use of rowboats, canoes, and other small craft in performing rescues.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'List various items that can be used as rescue aids in a noncontact swimming rescue. Explain why buoyant aids are preferred.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Perform the following equipment-based rescues for a conscious practice subject 30 feet from shore. Use a proper entry and a strong approach stroke. Speak to the subject to determine his condition and to provide instructions and encouragement.\n" +
                "a. Present a rescue tube to the subject, release it, and escort the victim to safety.\n" +
                "b. Present a rescue tube to the subject and use it to tow the victim to safety.\n" +
                "c. Present a buoyant aid other than a rescue tube to the subject, release it, and escort the victim to safety.\n" +
                "d. Present a buoyant aid other than a rescue tube to the subject and use it to tow the victim to safety.\n" +
                "e. Remove street clothes in 20 seconds or less and use a non-buoyant aid, such as a shirt or towel, to tow the subject to safety. Explain when it is appropriate to remove heavy clothing before attempting a swimming rescue.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Explain the importance of avoiding contact with an active victim and describe lead-and-wait tactics.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Perform the following nonequipment rescues for a conscious practice subject 30 feet from shore. Begin in the water from a position near the subject. Speak to the subject to determine his condition and to provide instructions and encouragement.\n" +
                "a. Provide a swim-along assist for a calm, responsive, tired swimmer moving with a weak forward stroke.\n" +
                "b. Perform an armpit tow for a calm responsive, tired swimmer resting with a back float.\n" +
                "c. Perform a cross-chest carry for an exhausted, passive victim who does not respond to instructions to aid himself.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10'").append(", ");
        requirement.append("'In deep water, show how to escape from a victims grasp on your wrist. Repeat for front and rear holds about the head and shoulders.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement11(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'11'").append(", ");
        requirement.append("'Perform the following rescues for an unconscious practice subject at or near the surface 30 feet from shore. Use a proper entry and strong approach stroke. Speak to the subject and splash water on him to determine his condition before making contact. Remove the victim from the water, with assistance if needed, and position for CPR.\n" +
                "a. Perform an equipment assist using a buoyant aid.\n" +
                "b. Perform a front approach and wrist tow.\n" +
                "c. Perform a rear approach and armpit tow.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement12(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'12'").append(", ");
        requirement.append("'Describe how to respond if a victim submerges before being reached by a rescuer, and do the following:\n" +
                "a. Recover a 10-pound weight in 8 to 10 feet of water using a feetfirst surface dive.\n" +
                "b. Repeat using a headfirst surface dive.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement13(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'13'").append(", ");
        requirement.append("'Demonstrate knowledge of resuscitation procedures:\n" +
                "a. Describe how to recognize the need for rescue breathing and CPR.\n" +
                "b. Demonstrate proper CPR technique for at least 3 minutes using a mannequin designed to simulate ventilations and compressions.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement14(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'14'").append(", ");
        requirement.append("'Demonstrate management of a spinal injury:\n" +
                "a. Explain the signs and symptoms of a spinal injury\n" +
                "b. Support a face up victim in calm, shallow water.\n" +
                "c. Turn a subject from a facedown to a faceup position while maintaining support.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement15(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'15'").append(", ");
        requirement.append("'Show that you know first aid for other injuries or illnesses that could occur while " +
                "swimming or boating, including hypothermia, heat reactions, muscle cramps, sunburn, stings, and hyperventilation.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("3");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
