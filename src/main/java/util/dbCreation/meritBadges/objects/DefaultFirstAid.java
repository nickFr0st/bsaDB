package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultFirstAid {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(10, 'First Aid', '/images/meritBadges/First_Aid.jpg', 1, 1)");

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
        requirement.append("'Satisfy your counselor that you have current knowledge of all first aid requirements for " +
                "Tenderfoot rank, Second Class rank, and First Class rank.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("10");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Explain how you would obtain emergency medical assistance from your home, on a wilderness camping trip, and during an activity on open water.\n" +
                "b. Explain the term triage.\n" +
                "c. Explain the standard precautions as applied to bloodborne pathogens.\n" +
                "d. Prepare a first aid kit for your home. Display and discuss its contents with your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("10");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Explain what action you should take for someone who shows signals of shock, for someone who shows " +
                "signals of a heart attack, and for someone who shows signals of stroke.\n" +
                "b. Identify the conditions that must exist before performing CPR on a person. Then demonstrate proper " +
                "technique in performing CPR using a training device approved by your counselor.\n" +
                "c. Explain the use of an automated external defibrillator (AED).\n" +
                "d. Show the steps that need to be taken for someone suffering from a severe cut on the leg and on the " +
                "wrist. Tell the dangers in the use of a tourniquet and the conditions under which its use is justified.\n" +
                "e. Explain when a bee sting could be life threatening and what action should be taken for prevention and for first aid.\n" +
                "f. Explain the symptoms of heatstroke and what action needs to be taken for first aid and for prevention.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("10");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Describe the signals of a broken bone. Show first aid procedures for handling fractures (broken " +
                "bones), including open (compound) fractures of the forearm, wrist, upper leg, and lower leg using improvised materials.\n" +
                "b. Describe the symptoms and possible complications and demonstrate proper procedures for treating " +
                "suspected injuries to the head, neck, and back. Explain what measures should be taken to reduce the " +
                "possibility of further complicating these injuries.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("10");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Describe the symptoms, proper first aid procedures, and possible prevention measures for the following conditions:\n" +
                "a. Hypothermia\n" +
                "b. Convulsions / seizures\n" +
                "c. Frostbite\n" +
                "d. Dehydration\n" +
                "e. Bruises, strains, sprains\n" +
                "f. Burns\n" +
                "g. Abdominal pain\n" +
                "h. Broken, chipped, or loosened tooth\n" +
                "i. Knocked out tooth\n" +
                "j. Muscle cramps'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("10");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Do TWO of the following:\n" +
                "a. If a sick or injured person must be moved, tell how you would determine the best method. Demonstrate this method.\n" +
                "b. With helpers under your supervision, improvise a stretcher and move a presumably unconscious person.\n" +
                "c. With your counselors approval, arrange a visit with your patrol or troop to an emergency medical " +
                "facility or through an American Red Cross chapter for a demonstration of how an AED is used.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("10");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Teach another Scout a first-aid skill selected by your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("10");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
