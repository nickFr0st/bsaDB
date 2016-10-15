package objects.objectLogic.imports;

import au.com.bytecode.opencsv.CSVReader;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.ScoutTypeConst;
import objects.databaseObjects.BoyScout;
import objects.databaseObjects.SpecialAward;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicSpecialAward;
import util.Util;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nathanael on 9/19/2016
 */
public class ImportSpecialAwardLogic {

    private static final int COL_SCOUT_NAME = 0;
    private static final int COL_SCOUT_TYPE_NAME = 1;
    private static final int COL_AWARD_NAME = 2;
    private static final int COL_AWARD_DESCRIPTION = 3;
    private static final int COL_AWARD_DATE_RECEIVED = 4;

    public static boolean execute(Component parent, String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            List<SpecialAward> importList = new ArrayList<>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[COL_SCOUT_NAME].equals("Scout Name")) {
                    continue;
                }

                SpecialAward specialAward = new SpecialAward();
                validateScoutType(specialAward, record[COL_SCOUT_TYPE_NAME], errors, errorLine);
                validateScoutName(specialAward, record[COL_SCOUT_NAME], errors, errorLine);
                validateAwardName(specialAward, record[COL_AWARD_NAME], errors, errorLine);
                validateAwardDescription(specialAward, record[COL_AWARD_DESCRIPTION], errors, errorLine);
                validateAwardDateReceived(specialAward, record[COL_AWARD_DATE_RECEIVED], errors, errorLine);

                importList.add(specialAward);
            }

            reader.close();

            if (!checkForErrors(errors, parent)) {
                return false;
            }

            LogicSpecialAward.save(importList);

        } catch (IOException ioe) {
            new MessageDialog(null, "Import Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Import Successful", "Your camp(s) have been successfully imported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    private static void validateAwardDescription(SpecialAward specialAward, String awardDescription, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(awardDescription)) {
            errors.append("Award Description is missing. ").append(errorLine);
            return;
        }

        specialAward.setDescription(awardDescription);
    }

    private static void validateAwardDateReceived(SpecialAward specialAward, String dateReceived, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(dateReceived)) {
            errors.append("Award Date Received is missing. ").append(errorLine);
            return;
        }

        try {
            Date date = Util.DATA_BASE_DATE_FORMAT.parse(dateReceived);
            specialAward.setDateReceived(date);
        } catch (ParseException e) {
            errors.append("Invalid date format date. ").append(errorLine);
        }
    }

    private static void validateScoutName(SpecialAward specialAward, String scoutName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(scoutName)){
            errors.append("Scout Name is missing. ").append(errorLine);
            return;
        }

        // todo: when adding other scout types this will need to change
        // find scout by name and type
        BoyScout boyScout = LogicBoyScout.findByName(scoutName);
        if (boyScout == null) {
            errors.append("Scout '").append(scoutName).append("' does not exists in BSA Database. ").append(errorLine);
            return;
        }

        specialAward.setScoutId(boyScout.getId());
    }

    private static void validateAwardName(SpecialAward specialAward, String awardName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(awardName)) {
            errors.append("Award Name is missing. ").append(errorLine);
            return;
        }

        specialAward.setName(awardName);
    }

    private static void validateScoutType(SpecialAward specialAward, String scoutTypeName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(scoutTypeName)) {
            errors.append("Scout Type Name is missing. ").append(errorLine);
            return;
        }

        ScoutTypeConst scoutTypeConst = ScoutTypeConst.getConst(scoutTypeName);
        if (scoutTypeConst == null) {
            errors.append("Invalid Scout Type Name. ").append(errorLine);
            return;
        }

        specialAward.setScoutTypeId(scoutTypeConst.getId());
    }

    private static boolean checkForErrors(StringBuilder errors, Component parent) {
        if (errors.length() <= 0) {
            return true;
        }

        String errorHeaderMessage = "Please fix the following issues and try again.\n\n";
        errors.insert(0, errorHeaderMessage);

        new MessageDialog(Util.getParent(parent), "Import Errors", errors.toString(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
        return false;
    }
}
