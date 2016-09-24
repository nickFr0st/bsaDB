package util.dbCreation.meritBadges.objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nathanael on 9/22/2016.
 */
public class DefaultPersonalFitness {

    public static void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        addNewScout(statement);
        addRequirements(statement);

        statement.executeBatch();
    }

    private static void addNewScout(Statement statement) throws SQLException {
        StringBuilder meritBadge = new StringBuilder("INSERT INTO meritBadge ");
        meritBadge.append("VALUES(2, 'Personal Fitness', '/images/meritBadges/Personal_Fitness.jpg', 1, 1)");

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
        requirement.append("'Do the following:\n" +
                "a. Before completing requirements 2 through 9, have your health-care practitioner give you a physical examination, using the Scout medical examination form. Describe the examination. Tell what questions the doctor asked about your health. Tell what health or medical recommendations the doctor made and report what you have done in response to the recommendations. Explain the following:\n" +
                "1. Why physical exams are important\n" +
                "2. Why preventative habits (such as exercising regularly) are important in maintaining good health, and how the use of tobacco products, alcohol, and other harmful substances can negatively affect our personal fitness.\n" +
                "3. Diseases that can be prevented and how.\n" +
                "4. The seven warning signs of cancer.\n" +
                "5. The youth risk factors that affect cardiovascular fitness in adulthood.\n" +
                "b. Have a dental examination. Get a statement saying that your teeth have been checked and cared for. Tell how to care for your teeth.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement2(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'2'").append(", ");
        requirement.append("'Explain to your merit badge counselor verbally or in writing what personal fitness means to you, including:\n" +
                "a. Components of personal fitness\n" +
                "b. Reasons for being fit in all components.\n" +
                "c. What it means to be mentally healthy\n" +
                "d. What it means to be physically healthy and fit.\n" +
                "e. What it means to be socially healthy. Discuss your activity in the areas of healthy social fitness.\n" +
                "f. What you can do to prevent social, emotional, or mental problems.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement3(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'3'").append(", ");
        requirement.append("'With your counselor answer and discuss the following questions:\n" +
                "a. Are you free from all curable diseases? Are you living in such a way that your risk of preventable diseases is minimized?\n" +
                "b. Are you immunized and vaccinated according to the advice of your health-care provider?\n" +
                "c. Do you understand the meaning of a nutritious diet and know why it is important for you? Does your diet include foods from all food groups?\n" +
                "d. Are your body weight and composition what you would like them to be, and do you know how to modify them safely through exercise, diet, and lifestyle?\n" +
                "e. Do you carry out daily activities without noticeable effort? Do you have extra energy for other activities?\n" +
                "f. Are you free from habits relating to poor nutrition and the use of alcohol, tobacco, drugs, and other practices that could be harmful to your health?\n" +
                "g. Do you participate in a regular exercise program or recreational activities?\n" +
                "h. Do you sleep well at night and wake up ready to start the new day?\n" +
                "i. Are you actively involved in the religious organization of your choice, and do you participate in its youth activities?\n" +
                "j. Do you spend quality time with your family and friends in social and recreational activities?\n" +
                "k. Do you support family activities and efforts to maintain a good home life?'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement4(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'4'").append(", ");
        requirement.append("'Explain the following about physical fitness:\n" +
                "a. The components of physical fitness\n" +
                "b. Your weakest and strongest component of physical fitness\n" +
                "c. The need to have a balance in all four components of physical fitness.\n" +
                "d. How the components of personal fitness relate to the Scout Law and Scout Oath.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement5(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'5'").append(", ");
        requirement.append("'Explain the following about nutrition:\n" +
                "a. The importance of good nutrition\n" +
                "b. What good nutrition means to you\n" +
                "c. How good nutrition is related to the other components of personal fitness\n" +
                "d. The three components of a sound weight (fat) control program.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement6(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'6'").append(", ");
        requirement.append("'Before doing requirements 7 and 8, complete the aerobic fitness, flexibility, muscular strength, and body composition tests as described in the Personal Fitness merit badge pamphlet. Record your results and identify those areas where you feel you need to improve.\n" +
                "AEROBIC FITNESS TEST\n" +
                "Record your performance on one of the following tests:\n" +
                "a. Run/walk as far as you can as fast as you can in nine minutes\n" +
                "OR\n" +
                "b. Run/walk one mile as fast as you can.\n" +
                "FLEXIBILITY TEST\n" +
                "Using a sit-and-reach box constructed according to specifications in the Personal Fitness merit badge pamphlet, make four repetitions and record the fourth reach. This last reach must be held steady for 15 seconds to qualify. (Remember to keep your knees down.)\n" +
                "STRENGTH TESTS\n" +
                "You must do the sit-ups exercise and one other (either push-ups or pull-ups). You may also do all three for extra experience and benefit.\n" +
                "a. Sit-ups. Record the number of sit-ups done correctly in 60 seconds. The sit-ups must be done in the form explained and illustrated in the Personal Fitness merit badge pamphlet.\n" +
                "b. Pull-ups. Record the total number of pull-ups completed correctly in 60 seconds. Be consistent with the procedures presented in the Personal Fitness merit badge pamphlet.\n" +
                "c. Push-ups. Record the total number of push-ups completed correctly in 60 seconds. Be consistent with the procedures presented in the Personal Fitness merit badge pamphlet.\n" +
                "Body Composition Evaluation (Calculating Your BMI percentile):\n" +
                "Step 1 - Multiply your weight in pounds by 703.\n" +
                "Step 2 - Divide the figure you get in No. 1 above by your height in inches.\n" +
                "Step 3 - Divide the figure you get in No. 2 above by your height in inches to get your BMI.\n" +
                "Step 4 - Use the chart in the Personal Fitness merit badge pamphlet to determine the BMI percentile for your age.\n" +
                "As an example, if you are 15 years old, you weigh 130 pounds, and you are 5ft 8in (68in) tall, then:\n" +
                "1. 130 x 703 = 91390\n" +
                "2. 91390 / 68 = 1344\n" +
                "3. 1344 / 68 = 20. This means your BMI is 20.\n" +
                "4. From the chart in the pamphlet, you are at the 50th percentile.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement7(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'7'").append(", ");
        requirement.append("'Outline a comprehensive 12-week physical fitness program using the results of your " +
                "fitness tests. Be sure your program incorporates the endurance, intensity, and warm-up guidelines " +
                "discussed in the Personal Fitness merit badge pamphlet. Before beginning your exercises, have the " +
                "program approved by your counselor and parents.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement8(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'8'").append(", ");
        requirement.append("'Complete the physical fitness program you outlined in requirement 7. Keep a log of your " +
                "fitness program activity (how long you exercised; how far you ran, swam, or biked; how many exercise " +
                "repetitions you completed; your exercise heart rate; etc.). Repeat the aerobic fitness, muscular " +
                "strength, and flexibility tests every two weeks and record your results. After the 12th week, repeat " +
                "all of the required activities in each of the three test categories, record your results, and show " +
                "improvement in each one. For the body composition evaluation, compare and analyze your preprogram and " +
                "postprogram body composition measurements. Discuss the meaning and benefit of your experience, and " +
                "describe your long-term plans regarding your personal fitness.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }

    private static void addRequirement9(Statement statement) throws SQLException {
        StringBuilder requirement = new StringBuilder("INSERT INTO requirement ");
        requirement.append("VALUES(null, ");
        requirement.append("'9'").append(", ");
        requirement.append("'Find out about three career opportunities in personal fitness. Pick one and find out " +
                "the education, training, and experience required for these professions. Discuss what you learned " +
                "with your counselor, and explain why this profession might interest you.'").append(", ");
        requirement.append("20").append(", ");
        requirement.append("2");
        requirement.append(")");

        statement.addBatch(requirement.toString());
    }
}
