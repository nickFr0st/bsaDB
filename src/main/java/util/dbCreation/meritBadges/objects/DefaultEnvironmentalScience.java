package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultEnvironmentalScience {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(11, 'Environmental Science', '/images/meritBadges/Environmental_Science.jpg', 1, 1)");

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
        requirement.append("'Make a timeline of the history of environmental science in America. Identify the " +
                "contribution made by the Boy Scouts of America to environmental science. Include dates, names of " +
                "people or organizations, and important events.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("11");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Define the following terms: population, community, ecosystem, biosphere, symbiosis, niche, " +
                "habitat, conservation, threatened species, endangered species, extinction, pollution prevention, " +
                "brownfield, ozone, watershed, airshed, nonpoint source, hybrid vehicle, fuel cell.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("11");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Do ONE activity in EACH of the following categories (using the activities in this {the merit badge} pamphlet as the basis for planning and carrying out your projects):\n" +
                "a. Ecology\n" +
                "1. Conduct an experiment to find out how living things respond to changes in their environments. Discuss your observations with your counselor.\n" +
                "2. Conduct an experiment illustrating the greenhouse effect. Keep a journal of your data and observations. Discuss your conclusions with your counselor.\n" +
                "3. Discuss what is an ecosystem. Tell how it is maintained in nature and how it survives.\n" +
                "b. Air Pollution\n" +
                "1. Perform an experiment to test for particulates that contribute to air pollution. Discuss your findings with your counselor.\n" +
                "2. Record the trips taken, mileage, and fuel consumption of a family car for seven days, and calculate how many miles per gallon the car gets. Determine whether any trips could have been combined (chained) rather than taken out and back. Using the idea of trip chaining, determine how many miles and gallons of gas could have been saved in those seven days.\n" +
                "3. Explain what is acid rain. In your explanation, tell how it affects plants and the environment and the steps society can take to help reduce its effects.\n" +
                "c. Water Pollution\n" +
                "1. Conduct an experiment to show how living things react to thermal pollution. Discuss your observations with your counselor.\n" +
                "2. Conduct an experiment to identify the methods that could be used to mediate (reduce) the effects of an oil spill on waterfowl. Discuss your results with your counselor.\n" +
                "3. Describe the impact of a waterborne pollutant on an aquatic community. Write a 100-word report on how that pollutant affected aquatic life, what the effect was, and whether the effect is linked to biomagnification.\n" +
                "d. Land Pollution\n" +
                "1. Conduct an experiment to illustrate soil erosion by water. Take photographs or make a drawing of the soil before and after your experiment, and make a poster showing your results. Present your poster to your patrol or troop.\n" +
                "2. Perform an experiment to determine the effect of an oil spill on land. Discuss your conclusions with your counselor.\n" +
                "3. Photograph an area affected by erosion. Share your photographs with your counselor and discuss why the area has eroded and what might be done to help alleviate the erosion.\n" +
                "e. Endangered Species\n" +
                "1. Do research on one endangered species found in your state. Find out what its natural habitat is, why it is endangered, what is being done to preserve it, and how many individual organisms are left in the wild. Prepare a 100-word report about the organism, including a drawing. Present your report to your patrol or troop.\n" +
                "2. Do research on one species that was endangered or threatened but which has now recovered. Find out how the organism recovered, and what its new status is. Write a 100-word report on the species and discuss it with your counselor.\n" +
                "3. With your parents and counselors approval, work with a natural resource professional to identify two projects that have been approved to improve the habitat for a threatened or endangered species in your area. Visit the site of one of these projects and report on what you saw.\n" +
                "f. Pollution Prevention, Resource Recovery, and Conservation\n" +
                "1. Look around your home and determine 10 ways your family can help reduce pollution. Practice at least two of these methods for seven days and discuss with your counselor what you have learned.\n" +
                "2. Determine 10 ways to conserve resources or use resources more efficiently in your home, at school, or at camp. Practice at least two of these methods for seven days and discuss with your counselor what you have learned.\n" +
                "3. Perform an experiment on packaging materials to find out which ones are biodegradable. Discuss your conclusions with your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("11");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Choose two outdoor study areas that are very different from one another (e.g., hilltop vs. " +
                "bottom of a hill; field vs. forest; swamp vs. dry land). For BOTH study areas, do ONE of the following:\n" +
                "a. Mark off a plot of 4 square yards in each study area, and count the number of species found there. " +
                "Estimate how much space is occupied by each plant species and the type and number of nonplant species " +
                "you find. Write a report that adequately discusses the biodiversity and population density of these " +
                "study areas. Discuss your report with your counselor.\n" +
                "b. Make at least three visits to each of the two study areas (for a total of six visits), staying for " +
                "at least 20 minutes each time, to observe the living and nonliving parts of the ecosystem. Space each " +
                "visit far enough apart that there are readily apparent differences in the observations. Keep a journal " +
                "that includes the differences you observe. Then, write a short report that adequately addresses your " +
                "observations, including how the differences of the study areas might relate to the differences noted, " +
                "and discuss this with your counselor.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("11");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Using the construction project provided or a plan you create on your own, identify the " +
                "items that would need to be included in an environmental impact statement for the project planned.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("11");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Find out about three career opportunities in environmental science. Pick one and find " +
                "out the education, training, and experience required for this profession. Discuss this with your " +
                "counselor, and explain why this profession might interest you.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("11");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
