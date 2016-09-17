package objects.objectLogic;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.ScoutTypeConst;
import objects.databaseObjects.Camp;
import objects.databaseObjects.Scout;
import objects.databaseObjects.ScoutCamp;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * Created by Nathanael on 5/24/2015
 */
public class IECampLogic {

    private static final int COL_CAMP_NAME = 0;
    private static final int COL_SCOUT_TYPE_NAME = 1;
    private static final int COL_LOCATION = 2;
    private static final int COL_START_DATE = 3;
    private static final int COL_LEADERS = 4;
    private static final int COL_NUMBER_OF_NIGHTS = 5;
    private static final int COL_NOTES = 6;

    private static final int COL_SCOUT_NAME = 0;

    public static boolean export(Component parent, List<String> exportList) {
        try {
            // get the file path
            CustomChooser chooser = new CustomChooser("Select export location", JFileChooser.FILES_ONLY);

            chooser.setSelectedFile(new File("CampExport.csv"));
            int returnValue = chooser.showSaveDialog(parent);
            chooser.resetLookAndFeel();

            if (returnValue != JFileChooser.APPROVE_OPTION) {
                return false;
            }

            String exportPath = chooser.getSelectedFile().getPath();

            // write to location
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',');
            List<String[]> records = new ArrayList<>();

            records.add(new String[]{"Camp Name", "Scout Type Name", "Location", "Start Date", "Leaders", "Number Of Nights", "Notes"});
            records.add(new String[]{"Scouts Attended"});

            boolean firstPass = true;
            for (Camp camp : LogicCamp.findAllByName(exportList)) {
                if (!firstPass) {
                    records.add(new String[]{""});
                }

                records.add(new String[]{camp.getName(), ScoutTypeConst.getConst(camp.getScoutTypeId()).getName(), camp.getLocation(),
                        Util.DATA_BASE_DATE_FORMAT.format(camp.getStartDate()), camp.getLeaders(), String.valueOf(camp.getNumberOfNights()), camp.getNote()});

                for (ScoutCamp scoutCamp : LogicScoutCamp.findAllByCampId(camp.getId())) {
                    Scout boyScout = LogicBoyScout.findById(scoutCamp.getScoutId());
                    if (boyScout == null) {
                         continue;
                    }
                    records.add(new String[]{boyScout.getName()});
                }

                if (firstPass) {
                    firstPass = false;
                }
            }

            csvWriter.writeAll(records);
            csvWriter.close();

            // check directory and let know if we are overwriting something, ask if it is ok
            if (!exportPath.endsWith(".csv")) {
                exportPath += ".csv";
            }

            FileWriter export = new FileWriter(exportPath);
            export.append(writer.toString());
            export.flush();
            export.close();

        } catch (IOException ioe) {
            new MessageDialog(null, "Export Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Export Successful", "Your selected camp(s) have been successfully exported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    public static boolean doImport(Component parent, String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<Camp, List<ScoutCamp>> importMap = new HashMap<>();

            boolean getCamp = true;
            Camp camp = null;
            List<ScoutCamp> scoutCampList = new ArrayList<>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Camp Name") || record[0].equals("Scouts Attended")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getCamp = true;

                    if (camp != null) {
                        if (!checkForErrors(errors, parent)) {
                            return false;
                        }

                        importMap.put(camp, scoutCampList);

                        camp = null;
                        scoutCampList = new ArrayList<>();
                    }
                    continue;
                }

                if (getCamp) {
                    getCamp = false;

                    camp = new Camp();
                    String campName = record[COL_CAMP_NAME];

                    validateCampName(camp, errors, errorLine, campName);
                    validateScoutType(camp, record[COL_SCOUT_TYPE_NAME], errors, errorLine);
                    validateLocation(camp, record[COL_LOCATION], errors, errorLine);
                    validateStartDate(camp, record[COL_START_DATE], errors, errorLine);
                    validateLeaders(camp, record[COL_LEADERS], errors, errorLine);
                    validateNumberOfNights(camp, record[COL_NUMBER_OF_NIGHTS], errors, errorLine);

                    String note = record[COL_NOTES];
                    if (!Util.isEmpty(note)) {
                        camp.setNote(note);
                    }
                    continue;
                }

                String scoutName = record[COL_SCOUT_NAME];
                Scout scout = LogicBoyScout.findByName(scoutName);
                if (scout == null) {
                    errors.append("Scout ").append(scoutName).append(" does not exists.").append(errorLine);
                    continue;
                }

                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setScoutId(scout.getId());
                scoutCamp.setScoutTypeId(camp.getScoutTypeId());
                scoutCamp.setNumberOfNights(camp.getNumberOfNights());

                scoutCampList.add(scoutCamp);
            }

            reader.close();

            if (!checkForErrors(errors, parent)) {
                return false;
            }

            if (camp != null) {
                importMap.put(camp, scoutCampList);
            }

            for (Map.Entry<Camp, List<ScoutCamp>> entry : importMap.entrySet()) {
                Camp importedCamp = entry.getKey();
                Camp existingCamp = LogicCamp.findByName(importedCamp.getName());
                int campId;

                if (existingCamp != null) {
                    campId = existingCamp.getId();
                    existingCamp.setLocation(importedCamp.getLocation());
                    existingCamp.setStartDate(importedCamp.getStartDate());
                    existingCamp.setLeaders(importedCamp.getLeaders());
                    existingCamp.setScoutTypeId(importedCamp.getScoutTypeId());
                    existingCamp.setNote(importedCamp.getNote());

                    LogicCamp.update(existingCamp);

                    LogicScoutCamp.deleteAllByCampId(campId);
                } else {
                    LogicCamp.save(importedCamp);
                    campId = importedCamp.getId();
                }

                List<ScoutCamp> scoutCamps = entry.getValue();
                if (!Util.isEmpty(scoutCamps)) {
                    for (ScoutCamp scoutCamp : scoutCamps) {
                        scoutCamp.setCampId(campId);
                    }

                    LogicScoutCamp.save(scoutCamps);
                }
            }

        } catch (IOException ioe) {
            new MessageDialog(null, "Import Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Import Successful", "Your camp(s) have been successfully imported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    private static void validateNumberOfNights(Camp camp, String numberOfNights, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(numberOfNights)) {
            errors.append("Number of Nights is missing. ").append(errorLine);
            return;
        }

        try {
            int count = Integer.parseInt(numberOfNights);
            if (count <= 0) {
                errors.append("Number of Nights must be a positive whole number. ").append(errorLine);
            } else {
                camp.setNumberOfNights(count);
            }
        } catch (NumberFormatException ignore) {
            errors.append("Number of Nights must be a positive whole number. ").append(errorLine);
        }
    }

    private static void validateLeaders(Camp camp, String leaders, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(leaders)) {
            errors.append("Leaders are missing. ").append(errorLine);
            return;
        }

        camp.setLeaders(leaders);
    }

    private static void validateStartDate(Camp camp, String startDate, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(startDate)) {
            errors.append("Start date is missing. ").append(errorLine);
            return;
        }

        if (!startDate.matches("\\d{4}/\\d{2}/\\d{2}?")) {
            errors.append("Invalid date format date. ").append(errorLine);
            return;
        }

        try {
            Date date = Util.DATA_BASE_DATE_FORMAT.parse(startDate);
            camp.setStartDate(date);
        } catch (ParseException e) {
            errors.append("Invalid date format date. ").append(errorLine);
            return;
        }
    }

    private static void validateCampName(Camp camp, StringBuilder errors, String errorLine, String campName) {
        if (Util.isEmpty(campName)){
            errors.append("Camp name is missing. ").append(errorLine);
            return;
        } else if (campName.length() > Camp.COL_NAME_LENGTH) {
            errors.append("Camp name is too long. ").append(errorLine);
            return;
        }
        camp.setName(campName);
    }

    private static void validateLocation(Camp camp, String location, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(location)) {
            errors.append("Location is missing. ").append(errorLine);
            return;
        }

        if (location.length() > Camp.COL_LOCATION_LENGTH) {
            errors.append("Location is too long. ").append(errorLine);
            return;
        }

        camp.setLocation(location);
    }

    private static void validateScoutType(Camp camp, String scoutTypeName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(scoutTypeName)) {
            errors.append("Scout type name is missing. ").append(errorLine);
            return;
        }

        ScoutTypeConst scoutTypeConst = ScoutTypeConst.getConst(scoutTypeName);
        if (scoutTypeConst == null) {
            errors.append("Invalid scout type name. ").append(errorLine);
            return;
        }

        camp.setScoutTypeId(scoutTypeConst.getId());
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
