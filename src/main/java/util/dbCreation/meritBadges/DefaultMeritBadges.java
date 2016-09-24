package util.dbCreation.meritBadges;

import util.dbCreation.meritBadges.objects.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Nathanael on 9/23/2016.
 */
public class DefaultMeritBadges {
    public static void addMeritBadges(Connection connection) throws SQLException {
        DefaultSwimming.execute(connection);
        DefaultPersonalFitness.execute(connection);
        DefaultLifesaving.execute(connection);
        DefaultCamping.execute(connection);
        DefaultCitInCom.execute(connection);
        DefaultCitInWorld.execute(connection);
        DefaultCommunication.execute(connection);
//        DefaultFamilyLife.execute(connection);
//        DefaultPersonalManagement.execute(connection);
//        DefaultFirstAid.execute(connection);
//        DefaultEnvironmentalScience.execute(connection);
//        DefaultSustainability.execute(connection);
//        DefaultEmergencyPreparedness.execute(connection);
//        DefaultCycling.execute(connection);
//        DefaultCitInNation.execute(connection);
//        DefaultCooking.execute(connection);
//        DefaultHiking.execute(connection);
//        DefaultHiking.execute(connection);


    }
}
