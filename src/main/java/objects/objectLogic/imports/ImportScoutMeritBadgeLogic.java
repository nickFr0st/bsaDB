package objects.objectLogic.imports;

import au.com.bytecode.opencsv.CSVReader;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.ScoutTypeConst;
import objects.databaseObjects.BoyScout;
import objects.databaseObjects.MeritBadge;
import objects.databaseObjects.ScoutMeritBadge;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicMeritBadge;
import objects.objectLogic.LogicScoutMeritBadge;
import util.Util;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathanael on 9/19/2016
 */
public class ImportScoutMeritBadgeLogic {

    private static final int COL_SCOUT_NAME = 0;
    private static final int COL_SCOUT_TYPE_NAME = 1;
    private static final int COL_MERIT_BADGE_NAME = 2;

    public static boolean execute(Component parent, String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            List<ScoutMeritBadge> importList = new ArrayList<>();

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

                ScoutMeritBadge scoutMeritBadge = new ScoutMeritBadge();
                validateScoutType(scoutMeritBadge, record[COL_SCOUT_TYPE_NAME], errors, errorLine);
                validateScoutName(scoutMeritBadge, record[COL_SCOUT_NAME], errors, errorLine);
                validateMeritBadgeName(scoutMeritBadge, record[COL_MERIT_BADGE_NAME], errors, errorLine);

                importList.add(scoutMeritBadge);
            }

            reader.close();

            if (!checkForErrors(errors, parent)) {
                return false;
            }

            for (ScoutMeritBadge scoutBadge : importList) {
                ScoutMeritBadge existingScoutBadge = LogicScoutMeritBadge.findByScoutIdScoutTypeIdAndMeritBadgeId(scoutBadge.getScoutId(),
                        scoutBadge.getScoutTypeId(), scoutBadge.getMeritBadgeId());

                if (existingScoutBadge != null) {
                    continue;
                }

                LogicScoutMeritBadge.save(scoutBadge);
            }

        } catch (IOException ioe) {
            new MessageDialog(null, "Import Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Import Successful", "Your camp(s) have been successfully imported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    private static void validateScoutName(ScoutMeritBadge scoutMeritBadge, String scoutName, StringBuilder errors, String errorLine) {
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

        scoutMeritBadge.setScoutId(boyScout.getId());
    }

    private static void validateMeritBadgeName(ScoutMeritBadge scoutMeritBadge, String meritBadgeName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(meritBadgeName)) {
            errors.append("Merit Badge Name is missing. ").append(errorLine);
            return;
        }

        MeritBadge meritBadge = LogicMeritBadge.findByName(meritBadgeName);
        if (meritBadge == null) {
            errors.append("Merit Badge '").append(meritBadgeName).append("' does not exists in BSA Database. ").append(errorLine);
            return;
        }

        scoutMeritBadge.setMeritBadgeId(meritBadge.getId());
    }

    private static void validateScoutType(ScoutMeritBadge scoutMeritBadge, String scoutTypeName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(scoutTypeName)) {
            errors.append("Scout Type Name is missing. ").append(errorLine);
            return;
        }

        ScoutTypeConst scoutTypeConst = ScoutTypeConst.getConst(scoutTypeName);
        if (scoutTypeConst == null) {
            errors.append("Invalid Scout Type Name. ").append(errorLine);
            return;
        }

        scoutMeritBadge.setScoutTypeId(scoutTypeConst.getId());
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
