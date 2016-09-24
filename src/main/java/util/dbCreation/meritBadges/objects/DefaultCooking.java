package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultCooking {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(16, 'Cooking', '/images/meritBadges/Cooking.jpg', 1, 1)");

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
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Explain to your counselor the most likely hazards you may encounter while participating in cooking activities and what you should do to anticipate, help prevent, mitigate, and respond to these hazards.\n" +
                "b. Show that you know first aid for and how to prevent injuries or illnesses that could occur while preparing meals and eating, including burns and scalds, cuts, choking, and allergic reactions.\n" +
                "c. Describe how meat, fish, chicken, eggs, dairy products, and fresh vegetables should be stored, transported, and properly prepared for cooking. Explain how to prevent cross-contamination.\n" +
                "d. Describe the following food-related illnesses and tell what you can do to help prevent each from happening:\n" +
                "1. Salmonella\n" +
                "2. Staphylococcal aureus\n" +
                "3. Escherichia coli (E. coli)\n" +
                "4. Clostridium botulinum (Botulism)\n" +
                "5. Campylobacter jejuni\n" +
                "6. Hepatitis\n" +
                "7. Listeria monocytogenes\n" +
                "8. Cryptosporidium\n" +
                "9. Norovirus\n" +
                "e. Discuss with your counselor food allergies, food intolerance, food-related diseases, and your awareness of these concerns.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Using the MyPlate food guide or the current USDA nutrition model, give five examples for EACH of the following food groups, the recommended number of daily servings, and the recommended serving size:\n" +
                "1. Fruits\n" +
                "2. Vegetables\n" +
                "3. Grains\n" +
                "4. Proteins\n" +
                "5. Dairy\n" +
                "b. Explain why you should limit your intake of oils and sugars.\n" +
                "c. Determine your daily level of activity and your caloric need based on your activity level. Then, based on the MyPlate food guide, discuss with your counselor an appropriate meal plan for yourself for one day.\n" +
                "d. Discuss your current eating habits with your counselor and what you can do to eat healthier, based on the MyPlate food guide.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Discuss the following food label terms: calorie, fat, saturated fat, trans fat, cholesterol, " +
                "sodium, carbohydrate, dietary fiber, sugar, protein. Explain how to calculate total carbohydrates " +
                "and nutritional values for two servings, based on the serving size specified on the label.\n" +
                "b. Refer to (How to Read a Food Label) in the Cooking merit badge pamphlet, and name ingredients that " +
                "help the consumer identify the following allergens: peanuts, tree nuts, milk, eggs, wheat, soy, and shellfish.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Discuss EACH of the following cooking methods. For each one, describe the equipment needed and name " +
                "at least one food that can be cooked using that method: baking, boiling, pan frying, simmering, steaming, microwaving, and grilling.\n" +
                "b. Discuss the benefits of using a camp stove on an outing vs. a charcoal or wood fire.\n" +
                "c. Discuss how the Outdoor Code and no-trace principles pertain to cooking in the outdoors.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Using the MyPlate food guide or the current USDA nutrition model, plan a menu for three full days of meals (three breakfasts, three lunches, and three dinners) plus one dessert. Your menu should include enough to feed yourself and at least one adult, keeping in mind any special needs (such as food allergies) of those to be served. List the equipment and utensils needed to prepare and serve these meals. Then do the following:\n" +
                "a. Create a shopping list for your meals showing the amount of food needed to prepare and serve each meal, and the cost for each meal.\n" +
                "b. Share and discuss your meal plan and shopping list with your counselor.\n" +
                "c. Using at least five of the seven cooking methods from requirement 4, prepare and serve yourself and at least one adult (parent, family member, guardian, or other responsible adult) one breakfast, one lunch, one dinner, and one dessert from the meals you planned.*\n" +
                "d. Time your cooking to have each meal ready to serve at the proper time. Have an adult verify the preparation of the meal to your counselor.\n" +
                "e. After each meal, ask a person you served to evaluate the meal on presentation and taste, then evaluate your own meal. Discuss what you learned with your counselor, including any adjustments that could have improved or enhanced your meals. Tell how better planning and preparation help ensure a successful meal.\n" +
                "f. Explain how you kept perishable foods safe and free from cross-contamination.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Using the MyPlate food guide or the current USDA nutrition model, plan a menu for your patrol or a similar size group of up to eight youth, including you) for a camping trip. Include five meals AND at least one snack OR one dessert. List the equipment and utensils needed to prepare and serve these meals. Then do the following:\n" +
                "a. Create a shopping list for your meals showing the amount of food needed to prepare and serve each meal, and the cost for each meal.\n" +
                "b. Share and discuss your meal plan and shopping list with your counselor.\n" +
                "c. In the outdoors, cook two of the meals you planned in requirement 6 using either a lightweight stove or a low-impact fire. Use a different cooking method for each meal.** The same fireplace may be used for both meals. Serve this meal to your patrol or a group of youth.\n" +
                "d. In the outdoors, cook one of the meals you planned in requirement 6.Use either a Dutch oven, OR a foil pack, OR kabobs. Serve this meal to your patrol or a group of youth.**\n" +
                "e. In the outdoors, prepare a dessert OR a snack and serve it to your patrol or a group of youth.**\n" +
                "f. After each meal, have those you served evaluate the meal on presentation and taste, and then evaluate your own meal. Discuss what you learned with your counselor, including any adjustments that could have improved or enhanced your meals. Tell how better planning and preparation help ensure successful outdoor cooking.\n" +
                "g. Explain how you kept perishable foods safe and free from cross-contamination.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Using the MyPlate food guide or the current USDA nutrition model, plan a menu for trail " +
                "hiking or backpacking that includes one breakfast, one lunch, one dinner, and one snack. These meals " +
                "must not require refrigeration and are to be consumed by three to five people (including you). List " +
                "the equipment and utensils needed to prepare and serve these meals. Then do the following:\n" +
                "a. Create a shopping list for your meals, showing the amount of food needed to prepare and serve each " +
                "meal, and the cost for each meal.\n" +
                "b. Share and discuss your meal plan and shopping list with your counselor. Your plan must include how " +
                "to repackage foods for your hike or backpacking trip to eliminate as much bulk, weight, and garbage as possible.\n" +
                "c. While on a trail hike or backpacking trip, prepare and serve two meals and a snack from the menu " +
                "planned for requirement 7. At least one of those meals must be cooked over a fire, or an approved " +
                "trail stove (with proper supervision).**\n" +
                "d. For each meal prepared in requirement 7c, use safe food-handling practices. Clean up equipment, " +
                "utensils, and the site thoroughly after each meal. Properly dispose of dishwater, and pack out all garbage.\n" +
                "e. After each meal, have those you served evaluate the meal on presentation and taste, then evaluate your " +
                "own meal. Discuss what you learned with your counselor, including any adjustments that could have improved " +
                "or enhanced your meals. Tell how better planning and preparation help ensure successful trail hiking or backpacking meals.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Find out about three career opportunities in cooking. Select one and find out the education, " +
                "training, and experience required for this profession. Discuss this with your counselor, and explain why " +
                "this profession might interest you.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("16");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
