package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultCommunication {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(7, 'Communication', '/images/meritBadges/Communication.jpg', 1, 1)");

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
        requirement.append("'Do ONE of the following:\n" +
                "a. For one day, keep a log in which you describe your communication activities. Keep track of the " +
                "time and different ways you spend communicating, such as talking person-to-person, listening to" +
                " teachers or the radio, watching television, reading books and other print media, and using any " +
                "electronic communication device. Discuss with your counselor what your log reveals about the " +
                "importance of communication in your life. Think of ways to improve your communications skills.\n" +
                "b. For three days, keep a journal of your listening experiences. Identify one example of each of the " +
                "following, and discuss with your counselor when you have listened to:\n" +
                "1. Obtain information\n" +
                "2. A persuasive argument\n" +
                "3. Appreciate or enjoy something\n" +
                "4. Understand someones feelings\n" +
                "c. In a small-group setting, meet with other scouts or with friends. Have them share personal stories " +
                "about significant events in their lives that affected them in some way. Take note of how each scout " +
                "participates in the group discussion and how effectively he communicates his story. Report what you " +
                "have learned to your counselor about the differences you observed in effective communication.\n" +
                "d. List as many ways as you can think of to communicate with others (face-to-face, by telephone, " +
                "letter, e-mail, text messages, and so on). For each type of communication discuss with your counselor " +
                "an instance when that method might not be appropriate or effective.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do ONE of the following:\n" +
                "a. Think of a creative way to describe yourself, using, for example, a collage, short story or " +
                "autobiography, drawing or series of photographs, or a song or skit. Using the aid you created, " +
                "make a presentation to your counselor about yourself.\n" +
                "b. Choose a concept, product, or service in which you have great confidence. Build a sales plan " +
                "based on its good points. Try to persuade the counselor to agree with, use, or buy your concept, " +
                "product or service. After your sales talk, discuss with your counselor how persuasive you were.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Write a five-minute speech. Give it at a meeting of a group.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Interview someone you know fairly well, like, or respect because of his or her position, " +
                "talent, career or life experiences. Listen actively to learn as much as you can about the person. " +
                "Then prepare and deliver to your counselor an introduction of the person as though this person were " +
                "to be a guest speaker, and include reasons why the audience would want to hear this person speak. " +
                "Show how you would call to invite this person to speak.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Attend a public meeting (city council, school board, debate) approved by your counselor" +
                " where several points of view are given on a single issue. Practice active listening skills and take " +
                "careful notes of each point of view. Prepare an objective report that includes all points of view " +
                "that were expressed, and share this with your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'With your counselors approval, develop a plan to teach a skill or inform someone about " +
                "something. Prepare teaching aids for your plan. Carry out your plan. With your counselor, determine " +
                "whether the person has learned what you intended.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Do ONE of the following:\n" +
                "a. Write to the editor of a magazine or your local newspaper to express your opinion or share information " +
                "on any subject you choose. Send your message by fax, email or regular mail.\n" +
                "b. Create a web page or blog of special interest to you (for instance, your troop or crew, a hobby, or " +
                "a sport). Include at least three articles or entries and one photograph or illustration, and one link " +
                "to some other Web page or blog that would be helpful to someone who visits the Web page or blog you " +
                "have created. It is not necessary to post your Web page or blog to the Internet, but if you decide to " +
                "do so, you must first share it with your parents and counselor and get their permission.\n" +
                "c. Use desktop publishing to produce a newsletter, brochure, flier or other printed material for your " +
                "scout troop, class at school, or other group. Include at least one article and one photograph or illustration.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Plan a troop or crew court of honor, campfire program, or an interfaith worship service. " +
                "Have the patrol leaders council approve it, then write the script and prepare the program. Serve as master of ceremonies.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Find out about three career opportunities in the field of communication. Pick one and " +
                "find out the education, training, and experience required for this profession. Discuss this with " +
                "your counselor, and explain why this profession might interest you.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("7");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
