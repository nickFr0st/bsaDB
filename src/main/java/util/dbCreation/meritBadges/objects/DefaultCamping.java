package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultCamping {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(4, 'Camping', '/images/meritBadges/camping.jpg', 1, 1)");

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
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Show that you know first aid for and how to prevent injuries or illnesses that could " +
                "occur while camping, including hypothermia, frostbite, heat reactions, dehydration, altitude sickness, " +
                "insect stings, tick bites, snakebite, blisters, and hyperventilation.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Learn the Leave No Trace principles and the Outdoor Code and explain what they mean. " +
                "Write a personal plan for implementing these principles on your next outing.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Make a written plan for an overnight trek and show how to get to your camping spot using " +
                "a topographical map and compass OR a topographical map and a GPS receiver.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Make a duty roster showing how your patrol is organized for an actual overnight campout. " +
                "List assignments for each member.\n" +
                "b. Help a Scout patrol or a Webelos Scout unit in your area prepare for an actual campout, " +
                "including creating the duty roster, menu planning, equipment needs, general planning, and setting up camp.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Prepare a list of clothing you would need for overnight campouts in both warm and cold weather. Explain the term layering.\n" +
                "b. Discuss footwear for different kinds of weather and how the right footwear is important for protecting your feet.\n" +
                "c. Explain the proper care and storage of camping equipment (clothing, footwear, bedding).\n" +
                "d. List the outdoor essentials necessary for any campout, and explain why each item is needed.\n" +
                "e. Present yourself to your Scoutmaster with your pack for inspection. Be correctly clothed and equipped for an overnight campout.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Describe the features of four types of tents, when and where they could be used, and how to care for tents. Working with another Scout, pitch a tent.\n" +
                "b. Discuss the importance of camp sanitation and tell why water treatment is essential. Then demonstrate two ways to treat water.\n" +
                "c. Describe the factors to be considered in deciding where to pitch your tent.\n" +
                "d. Tell the difference between internal- and external-frame packs. Discuss the advantages and disadvantages of each.\n" +
                "e. Discuss the types of sleeping bags and what kind would be suitable for different conditions." +
                " Explain the proper care of your sleeping bag and how to keep it dry. Make a comfortable ground bed.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Prepare for an overnight campout with your patrol by doing the following:\n" +
                "a. Make a checklist of personal and patrol gear that will be needed.\n" +
                "b. Pack your own gear and your share of the patrol equipment and food for proper carrying. Show that " +
                "your pack is right for quickly getting what is needed first, and that it has been assembled properly " +
                "for comfort, weight, balance, size, and neatness.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Explain the safety procedures for:\n" +
                "1. Using a propane or butane/propane stove\n" +
                "2. Using a liquid fuel stove\n" +
                "3. Proper storage of extra fuel\n" +
                "b. Discuss the advantages and disadvantages of different types of lightweight cooking stoves.\n" +
                "c. Prepare a camp menu. Explain how the menu would differ from a menu for a backpacking or float trip. " +
                "Give recipes and make a food list for your patrol. Plan two breakfasts, three lunches, and two suppers. " +
                "Discuss how to protect your food against bad weather, animals, and contamination.\n" +
                "d. Cook at least one breakfast, one lunch, and one dinner for your patrol from the meals you have " +
                "planned for requirement 8c. At least one of those meals must be a trail meal requiring the use of a lightweight stove.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Show experience in camping by doing the following:\n" +
                "a. Camp a total of at least 20 days and 20 nights.* Sleep each night under the sky or in a tent you " +
                "have pitched. The 20 days and 20 nights must be at a designated Scouting activity or event. You may " +
                "use a week of long-term camp toward this requirement. If the camp provides a tent that has already " +
                "been pitched, you need not pitch your own tent.\n" +
                "*All campouts since becoming a Boy Scout or Varsity Scout may count toward this requirement.\n" +
                "b. On any of these camping experiences, you must do TWO of the following, only with proper preparation and under qualified supervision:\n" +
                "1. Hike up a mountain, gaining at least 1,000 vertical feet.\n" +
                "2. Backpack, snowshoe, or cross-country ski for at least 4 miles.\n" +
                "3. Take a bike trip of at least 15 miles or at least four hours.\n" +
                "4. Take a nonmotorized trip on the water of at least four hours or 5 miles.\n" +
                "5. Plan and carry out an overnight snow camping experience.\n" +
                "6. Rappel down a rappel route of 30 feet or more.\n" +
                "c. Perform a conservation project approved by the landowner or land managing agency.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10'").append(", ");
        requirement.append("'Discuss how the things you did to earn this badge have taught you about personal health " +
                "and safety, survival, public health, conservation, and good citizenship. In your discussion, tell " +
                "how Scout spirit and the Scout Oath and Law apply to camping and outdoor ethics.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("4");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
