package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultPersonalManagement {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(9, 'Personal Management', '/images/meritBadges/Personal_Management.jpg', 1, 1)");

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
        requirement.append("'Do the following:\n" +
                "a. Choose an item that your family might want to purchase that is considered a major expense.\n" +
                "b. Write a plan that tells how your family would save money for the purchase identified in requirement 1a.\n" +
                "1. Discuss the plan with your merit badge counselor\n" +
                "2. Discuss the plan with your family\n" +
                "3. Discuss how other family needs must be considered in this plan.\n" +
                "c. Develop a written shopping strategy for the purchase identified in requirement 1a.\n" +
                "1. Determine the quality of the item or service (using consumer publications or rating systems).\n" +
                "2. Comparison shop for the item. Find out where you can buy the item for the best price. (Provide " +
                "prices from at least two different price sources.) Call around; study ads. Look for a sale or " +
                "discount coupon. Consider alternatives. Can you buy the item used? Should you wait for a sale?'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Prepare a budget reflecting your expected income (allowance, gifts, wages), expenses, and savings. " +
                "Track your actual income, expenses, and savings for 13 consecutive weeks. (You may use the forms " +
                "provided in this pamphlet, devise your own, or use a computer generated version.) When complete, " +
                "present the results to your merit badge counselor.\n" +
                "b. Compare expected income with expected expenses.\n" +
                "1. If expenses exceed income, determine steps to balance your budget.\n" +
                "2. If income exceeds expenses, state how you would use the excess money (new goal, savings).'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'Discuss with your merit badge counselor FIVE of the following concepts:\n" +
                "a. The emotions you feel when you receive money.\n" +
                "b. Your understanding of how the amount of money you have with you affects your spending habits.\n" +
                "c. Your thoughts when you buy something new and your thoughts about the same item three months later. Explain the concept of buyers remorse.\n" +
                "d. How hunger affects you when shopping for food items (snacks, groceries).\n" +
                "e. Your experience of an item you have purchased after seeing or hearing advertisements for it. Did the item work as well as advertised?\n" +
                "f. Your understanding of what happens when you put money into a savings account.\n" +
                "g. Charitable giving. Explain its purpose and your thoughts about it.\n" +
                "h. What you can do to better manage your money.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Explain the following to your merit badge counselor:\n" +
                "a. The differences between saving and investing, including reasons for using one over the other.\n" +
                "b. The concepts of return on investment and risk.\n" +
                "c. The concepts of simple interest and compound interest and how these affected the results of your investment exercise.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Select five publicly traded stocks from the business section of the newspaper. Explain to " +
                "your merit badge counselor the importance of the following information for each stock:\n" +
                "a. Current price\n" +
                "b. How much the price changed from the previous day\n" +
                "c. The 52-week high and the 52-week low prices'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Pretend you have $1,000 to save, invest, and help prepare yourself for the future. " +
                "Explain to your merit badge counselor the advantages or disadvantages of saving or investing in each of the following:\n" +
                "a. Common stocks\n" +
                "b. Mutual funds\n" +
                "c. Life insurance\n" +
                "d. A certificate of deposit (CD)\n" +
                "e. A savings account or U.S. savings bond'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Explain to your merit badge counselor the following:\n" +
                "a. What a loan is, what interest is, and how the annual percentage rate (APR) measures the true cost of a loan.\n" +
                "b. The different ways to borrow money.\n" +
                "c. The differences between a charge card, debit card, and credit card. What are the costs and pitfalls " +
                "of using these financial tools? Explain why it is unwise to make only the minimum payment on your credit card.\n" +
                "d. Credit reports and how personal responsibility can affect your credit report.\n" +
                "e. Ways to reduce or eliminate debt.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Demonstrate to your merit badge counselor your understanding of time management by doing the following:\n" +
                "a. Write a to do list of tasks or activities, such as homework assignments, chores, and personal projects, " +
                "that must be done in the coming week. List these in order of importance to you.\n" +
                "b. Make a seven-day calendar or schedule. Put in your set activities, such as school classes, sports " +
                "practices or games, jobs or chores, and/or Scout or church or club meetings, then plan when you will " +
                "do all the tasks from your to do list between your set activities.\n" +
                "c. Follow the one-week schedule you planned. Keep a daily diary or journal during each of the seven " +
                "days of this weeks activities, writing down when you completed each of the tasks on your to do list " +
                "compared to when you scheduled them.\n" +
                "d. Review your to do list, one-week schedule, and diary/journal to understand when your schedule " +
                "worked and when it did not work. With your merit badge counselor, discuss and understand what you " +
                "learned from this requirement and what you might do differently the next time.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Prepare a written project plan demonstrating the steps below, including the desired outcome. " +
                "This is a project on paper, not a real-life project. Examples could include planning a camping trip, " +
                "developing a community service project or a school or religious event, or creating an annual patrol " +
                "plan with additional activities not already included in the troop annual plan. Discuss your completed " +
                "project plan with your merit badge counselor.\n" +
                "a. Define the project. What is your goal?\n" +
                "b. Develop a timeline for your project that shows the steps you must take from beginning to completion.\n" +
                "c. Describe your project.\n" +
                "d. Develop a list of resources. Identify how these resources will help you achieve your goal.\n" +
                "e. If necessary, develop a budget for your project.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement10(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'10'").append(", ");
        requirement.append("'Do the following:\n" +
                "a. Choose a career you might want to enter after high school or college graduation.\n" +
                "b. Research the limitations of your anticipated career and discuss with your merit badge counselor " +
                "what you have learned about qualifications such as education, skills, and experience.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("9");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
