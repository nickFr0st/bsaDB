package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultCitInWorld {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(6, 'Citizenship in the World', '/images/meritBadges/Citizenship_in_the_World.jpg', 1, 1)");

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
        requirement.append("'Explain what citizenship in the world means to you and what you think it takes to be a good world citizen.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Explain how one becomes a citizen in the United States, and explain the rights, duties, " +
                "and obligations of U.S. citizenship. Discuss the similarities and differences between the rights, " +
                "duties, and obligations of U.S. citizens and the citizens of two other countries.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Pick a current world event. In relation to this current event, discuss with your counselor how a " +
                "countries national interest and its relationship with other countries might affect areas such as its " +
                "security, its economy, its values, and the health of its citizens.\n" +
                "b. Select a foreign country and discuss with your counselor how its geography, natural resources, and " +
                "climate influence its economy and its global partnerships with other countries.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Do TWO of the following:\n" +
                "a. Explain international law and how it differs from national law. Explain the role of international law and how international law can be used as a tool for conflict resolution.\n" +
                "b. Using resources such as major daily newspapers, the Internet (with your parents permission), and news magazines, observe a current issue that involves international trade, foreign exchange, balance of payments, tariffs, and free trade. Explain what you have learned. Include in your discussion an explanation of why countries must cooperate in order for world trade and global competition to thrive.\n" +
                "c. Select TWO of the following organizations and describe their role in the world.\n" +
                "1. The United Nations\n" +
                "2. The World Court\n" +
                "3. World Organization of the Scout Movement\n" +
                "4. The World Health Organization\n" +
                "5. Amnesty International\n" +
                "6. The International Committee of the Red Cross\n" +
                "7. CARE'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Discuss the differences between constitutional and nonconstitutional governments.\n" +
                "b. Name at least five different types of governments currently in power in the world.\n" +
                "c. Show on a world map countries that use each of these five different forms of government.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Explain how a government is represented abroad and how the United States government is accredited to international organizations.\n" +
                "b. Describe the roles of the following in the conduct of foreign relations.\n" +
                "1. Ambassador\n" +
                "2. Consul\n" +
                "3. Bureau of International Information Programs\n" +
                "4. Agency for International Development\n" +
                "5. United States and Foreign Commercial Service\n" +
                "c. Explain the purpose of a passport and visa for international travel.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Do TWO of the following and share with your counselor what you have learned:\n" +
                "a. Visit the Web site (With your parent/guardians permission) of the U.S. State Department. Learn " +
                "more about an issue you find interesting that is discussed on this Web site.\n" +
                "b. Visit the Web site (With your parent/guardians permission) of an international news organization " +
                "or foreign government, OR examine a foreign newspaper available at your local library, bookstore, or " +
                "newsstand. Find a news story about a human right realized in the United States that is not recognized in another country.\n" +
                "c. Visit with a student or Scout from another country and discuss the typical values, holidays, ethnic " +
                "foods, and traditions practiced or enjoyed there.\n" +
                "d. Attend a world Scout jamboree.\n" +
                "e. Participate in or attend an international event in your area, such as an ethnic festival, concert, or play.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("6");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
