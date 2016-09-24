package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultEmergencyPreparedness {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(13, 'Emergency Preparedness', '/images/meritBadges/Emergency_Preparedness.jpg', 1, 1)");

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
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Earn the First Aid merit badge.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Discuss with your counselor the aspects of emergency preparedness:\n" +
                "1. Prepare for emergency situations\n" +
                "2. Respond to emergency situations\n" +
                "3. Recover from emergency situations\n" +
                "4. Mitigate and prevent emergency situations\n" +
                "Include in your discussion the kinds of questions that are important to ask yourself as you consider each of these.\n" +
                "b. Make a chart that demonstrates your understanding of each of the aspects of emergency preparedness " +
                "in requirement 2a (prepare, respond, recover, mitigate) with regard to 10 of the situations listed " +
                "below. You must use situations 1, 2, 3, 4, and 5 below in boldface, but you may choose any other five " +
                "listed here for a total of 10 situations. Discuss this chart with your counselor.\n" +
                "1. Home kitchen fire\n" +
                "2. Home basement/storage room/garage fire\n" +
                "3. Explosion in the home\n" +
                "4. Automobile crash\n" +
                "5. Food-borne disease (food poisoning)\n" +
                "6. Fire or explosion in a public place\n" +
                "7. Vehicle stalled in the desert\n" +
                "8. Vehicle trapped in a blizzard\n" +
                "9. Flash flooding in town or the country\n" +
                "10. Mountain/backcountry accident\n" +
                "11. Boating or water accident\n" +
                "12. Gas leak in a home or a building\n" +
                "13. Tornado or hurricane\n" +
                "14. Major flood\n" +
                "15. Nuclear power plant emergency\n" +
                "16. Avalanche (snowslide or rockslide)\n" +
                "17. Violence in a public place\n" +
                "c. Meet with and teach your family how to get or build a kit, make a plan, and be informed for the " +
                "situations on the chart you created for requirement 2b. Complete a family plan. Then meet with " +
                "your counselor and report on your family meeting, discuss their responses, and share your family plan.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Show how you could safely save a person from the following:\n" +
                "a. Touching a live household electric wire.\n" +
                "b. A room filled with carbon monoxide\n" +
                "c. Clothes on fire.\n" +
                "d. Drowning, using nonswimming rescues (including accidents on ice).'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Show three ways of attracting and communicating with rescue planes/aircraft.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'With another person, show a good way to transport an injured person out of a remote " +
                "and/or rugged area, conserving the energy of rescuers while ensuring the well-being and protection of the injured person.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Tell the things a group of Scouts should be prepared to do, the training they need, and the safety " +
                "precautions they should take for the following emergency services:\n" +
                "1. Crowd and traffic control\n" +
                "2. Messenger service and communication.\n" +
                "3. Collection and distribution services.\n" +
                "4. Group feeding, shelter, and sanitation.\n" +
                "b. Identify the government or community agencies that normally handle and prepare for the emergency " +
                "services listed under 6a, and explain to your counselor how a group of Scouts could volunteer to help " +
                "in the event of these types of emergencies.\n" +
                "c. Find out who is your communitys emergency management director and learn what this person does to " +
                "prepare, respond to, recover from, and mitigate and prevent emergency situations in your community. " +
                "Discuss this information with your counselor and apply what you discover to the chart you created for requirement 2b.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Take part in an emergency service project, either a real one or a practice drill, with a Scouting unit or a community agency.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Prepare a written plan for mobilizing your troop when needed to do emergency service. If there is " +
                "already a plan, explain it. Tell your part in making it work.\n" +
                "b. Take part in at least one troop mobilization. Before the exercise, describe your part to your " +
                "counselor. Afterward, conduct an (after-action) lesson, discussing what you learned during the " +
                "exercise that required changes or adjustments to the plan.\n" +
                "c. Prepare a personal emergency service pack for a mobilization call. Prepare a family kit (suitcase " +
                "or waterproof box) for use by your family in case an emergency evacuation is needed. Explain the " +
                "needs and uses of the contents.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Do ONE of the following:\n" +
                "a. Using a safety checklist approved by your counselor, inspect your home for potential hazards. Explain the hazards you find and how they can be corrected.\n" +
                "b. Review or develop a plan of escape for your family in case of fire in your home.\n" +
                "c. Develop an accident prevention program for five family activities outside the home (such as taking " +
                "a picnic or seeing a movie) that includes an analysis of possible hazards, a proposed plan to correct " +
                "those hazards, and the reasons for the corrections you propose.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("13");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
