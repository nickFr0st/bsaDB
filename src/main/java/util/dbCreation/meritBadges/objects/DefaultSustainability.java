package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultSustainability {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(12, 'Sustainability', '/images/meritBadges/Sustainability.jpg', 1, 1)");

        statement.addBatch(meritBadge.toString());
    }

    private static void addRequirements(Statement statement) throws SQLException {
        addRequirement1(statement);
        addRequirement2(statement);
        addRequirement3(statement);
        addRequirement4(statement);
        addRequirement5(statement);
        addRequirement6(statement);
    }

    private static void addRequirement1(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'1'").append(", ");
        requirement.append("'Before starting work on any other requirements for this merit badge, write in your own " +
                "words the meaning of sustainability. Explain how you think conservation and stewardship of our " +
                "natural resources relate to sustainability. Have a family meeting, and ask family members to write " +
                "down what they think sustainability means. Be sure to take notes. You will need this information again for requirement 5.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("12");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do the following:\n" +
                "Water. Do A AND either B OR C.\n" +
                "A. Develop and implement a plan that attempts to reduce your families water usage. Examine your families water bills reflecting usage for three months (past or current).As a family, discuss water usage. To aid in your discussion, if past water bills are available, you may choose to examine a few. As a family, choose three ways to help reduce consumption. Implement those ideas for one month. Share what you learn with your counselor, and tell how your plan affected your familys water usage.\n" +
                "OR\n" +
                "Since water bills are not always accessible, any Scout who wishes to, may use the following as an alternative to 2A above. This alternative will become the official requirement 2A upon the next reprinting of the Sustainability merit badge pamphlet.\n" +
                "Develop and implement a plan that attempts to reduce your families water usage. As a family, discuss water usage. To aid in your discussion, if past water bills are available, you may choose to examine a few. As a family, choose three ways to help reduce water consumption. Implement those ideas for one month. share what you learn with your counselor, and tell how you think your plan affected your familys water usage\n" +
                "B. Using a diagram you have created, explain to your counselor how your household gets its clean water from a natural source and what happens with the water after you use it. Include water that goes down the kitchen, bathroom, and laundry drains, and any runoff from watering the yard or washing the car. Tell two ways to preserve your families access to clean water in the future.\n" +
                "C. Discuss with your counselor two areas in the world that have been affected by drought over the last three years. For each area, identify a water conservation practice (successful or unsuccessful) that has been used. Tell whether the practice was effective and why. Discuss what water conservation practice you would have tried and why.\n" +
                "Food. Do A AND either B OR C.\n" +
                "A. Develop and implement a plan that attempts to reduce your household food waste. Establish a baseline and then track and record your results for two weeks. Report your results to your family and counselor.\n" +
                "B. Discuss with your counselor the ways individuals, families, and communities can create their own food sources (potted plants, family garden, rooftop garden, neighborhood or community garden). Tell how this plan might contribute to a more sustainable way of life if practiced globally.\n" +
                "C. Discuss with your counselor factors that limit the availability of food and food production in different regions of the world. Tell three ways these factors influence the sustainability of worldwide food supplies.\n" +
                "Community. Do A AND either B OR C.\n" +
                "A. Draw a rough sketch depicting how you would design a sustainable community. Share your sketch with your counselor, and explain how the housing, work locations, shops, schools, and transportation systems affect energy, pollution, natural resources, and the economy of the community.\n" +
                "B. With your parents permission and your counselors approval, interview a local architect, engineer, contractor, or building materials supplier. Find out the factors that are considered when using sustainable materials in renovating or building a home. Share what you learn with your counselor.\n" +
                "C. Review a current housing needs assessment for your town, city, county, or state. Discuss with your counselor how birth and death rates affect sufficient housing, and how a lack of housing (or too much housing) can influence the sustainability of a local or global area.\n" +
                "Energy. Do A AND either B OR C.\n" +
                "A. Learn about the sustainability of different energy sources, including fossil fuels, solar, wind, nuclear, hydropower, and geothermal. Find out how the production and consumption of each of these energy sources affects the environment and what the term carbon footprint means. Discuss what you learn with your counselor, and explain how you think your family can reduce its carbon footprint.\n" +
                "B. Develop and implement a plan that attempts to reduce consumption for one of your families household utilities. Examine your families bills for that utility reflecting usage for three months (past or current). As a family, choose three ways to help reduce consumption and be a better steward of this resource. Implement those ideas for one month. Share what you learn with your counselor, and tell how your plan affected your families usage.\n" +
                "C. Evaluate your families fuel and transportation usage. Review your families transportation-related bills (gasoline, diesel, electric, public transportation, etc.) reflecting usage for three months (past or current). As a family, choose three ways to help reduce consumption and be a better steward of this resource. Implement those ideas for one month. Share what you learn with your counselor, and tell how your plan affected your families transportation habits.\n" +
                "Stuff. Do A AND either B OR C.\n" +
                "A. Keep a log of the stuff your family purchases (excluding food items) for two weeks. In your log, categorize each purchase as an essential need (such as soap) or a desirable want (such as a DVD). Share what you learn with your counselor.\n" +
                "B. Plan a project that involves the participation of your family to identify the stuff your family no longer needs. Complete your project by donating, repurposing, or recycling these items.\n" +
                "C. Discuss with your counselor how having too much stuff affects you, your family, and your community. " +
                "Include the following: the financial impact, time spent, maintenance, health, storage, and waste. " +
                "Include in your discussion the practices that can be used to avoid accumulating too much stuff.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("12");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Explain to your counselor how the planetary life-support systems (soil, climate, freshwater, " +
                "atmospheric, nutrient, oceanic, ecosystems, and species) support life on Earth and interact with one another.\n" +
                "b. Tell how the harvesting or production of raw materials (by extraction or recycling), along with " +
                "distribution of the resulting products, consumption, and disposal/repurposing, influences current and " +
                "future sustainability thinking and planning.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("12");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Explore TWO of the following categories. Have a discussion with your family about the two you select. In your discussion, include your observations, and best and worst practices. Share what you learn with your counselor.\n" +
                "a. Plastic waste. Discuss the impact plastic waste has on the environment (land, water, air). Learn about the number system for plastic recyclables, and determine which plastics are more commonly recycled. Find out what the trash vortex is and how it was formed.\n" +
                "b. Electronic waste. Choose three electronic devices in your household. Find out the average lifespan of each, what happens to these devices once they pass their useful life, and whether they can be recycled in whole or part. Discuss the impact of electronic waste on the environment.\n" +
                "c. Food waste. Learn about the value of composting and how to start a compost pile. Start a compost pile appropriate for your living situation. Tell what can be done with the compost when it is ready for use.\n" +
                "d. Species decline. Explain the term species (plant or animal) decline. Discuss the human activities that contribute to species decline, what can be done to help reverse the decline, and its impact on a sustainable environment.\n" +
                "e. World population. Learn how the worlds population affects the sustainability of Earth. Discuss three human activities that may contribute to putting Earth at risk, now and in the future.\n" +
                "f. Climate change. Find a world map that shows the pattern of temperature change for a period of at " +
                "least 100 years. Share this map with your counselor, and discuss three factors that scientists believe " +
                "affect the global weather and temperature.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("12");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. After completing requirements 1 through 4, have a family meeting. Discuss what your family has " +
                "learned about what it means to be a sustainable citizen. Talk about the behavioral changes and life " +
                "choices your family can make to live more sustainably. Share what you learn with your counselor.\n" +
                "b. Discuss with your counselor how living by the Scout Oath and Scout Law in your daily life helps" +
                " promote sustainability and good stewardship.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("12");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Learn about career opportunities in the sustainability field. Pick one and find out the " +
                "education, training, and experience required. Discuss what you have learned with your counselor and " +
                "explain why this career might interest you.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("12");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
