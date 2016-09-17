package objects.objectLogic.imports;

import au.com.bytecode.opencsv.CSVReader;
import bsaDb.client.dialogs.message.MessageDialog;
import objects.databaseObjects.Advancement;
import objects.databaseObjects.BoyScout;
import objects.objectLogic.LogicAdvancement;
import objects.objectLogic.LogicBoyScout;
import util.Util;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nathanael on 5/24/2015
 */
public class ImportBoyScoutLogic {

    private static final int COL_SCOUT_NAME = 0;
    private static final int COL_BIRTH_DATE = 1;
    private static final int COL_ADVANCEMENT_NAME = 2;
    private static final int COL_ADVANCEMENT_DATE = 3;
    private static final int COL_POSITION = 4;
    private static final int COL_SCOUT_PHONE = 5;
    private static final int COL_CITY = 6;
    private static final int COL_STATE = 7;
    private static final int COL_STREET = 8;
    private static final int COL_ZIP = 9;
    private static final int COL_PARENT = 10;
    private static final int COL_PARENT_PHONE = 11;
    private static final int COL_NOTES = 12;

    public static boolean execute(Component parent, String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            List<BoyScout> importList = new ArrayList<>();

            BoyScout boyScout = null;

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[COL_SCOUT_NAME].equals("Boy Scout Name")) {
                    continue;
                }

                if (record[COL_SCOUT_NAME].isEmpty()) {

                    if (boyScout != null) {
                        if (!checkForErrors(errors, parent)) {
                            return false;
                        }

                        importList.add(boyScout);

                        boyScout = null;
                    }
                    continue;
                }

                boyScout = new BoyScout();
                String scoutName = record[COL_SCOUT_NAME];

                validateScoutName(boyScout, errors, errorLine, scoutName);
                validateBirthDate(boyScout, record[COL_BIRTH_DATE], errors, errorLine);
                validateAdvancement(boyScout, record[COL_ADVANCEMENT_NAME], errors, errorLine);
                validateAdvancementDate(boyScout, record[COL_ADVANCEMENT_DATE], errors, errorLine);
                validateScoutPhone(boyScout, record[COL_SCOUT_PHONE], errors, errorLine);
                validateParentPhone(boyScout, record[COL_PARENT_PHONE], errors, errorLine);

                addPosition(boyScout, record);
                addCity(boyScout, record);
                addState(boyScout, record);
                addStreet(boyScout, record);
                addZip(boyScout, record);
                addParentName(boyScout, record);
                addNotes(boyScout, record);
            }

            reader.close();

            if (!checkForErrors(errors, parent)) {
                return false;
            }

            if (boyScout != null) {
                importList.add(boyScout);
            }

            for (BoyScout importedScout : importList) {
                BoyScout existingScout = LogicBoyScout.findByName(importedScout.getName());

                if (existingScout != null) {
                    existingScout.setBirthDate(importedScout.getBirthDate());
                    existingScout.setAdvancementId(importedScout.getAdvancementId());
                    existingScout.setAdvancementDate(importedScout.getAdvancementDate());
                    existingScout.setPosition(importedScout.getPosition());
                    existingScout.setPhoneNumber(importedScout.getPhoneNumber());
                    existingScout.setCity(importedScout.getCity());
                    existingScout.setState(importedScout.getState());
                    existingScout.setStreet(importedScout.getStreet());
                    existingScout.setZip(importedScout.getZip());
                    existingScout.setGuardianName(importedScout.getGuardianName());
                    existingScout.setGuardianPhoneNumber(importedScout.getGuardianPhoneNumber());
                    existingScout.setNote(importedScout.getNote());

                    LogicBoyScout.update(existingScout);
                } else {
                    LogicBoyScout.save(importedScout);
                }
            }

        } catch (IOException ioe) {
            new MessageDialog(null, "Import Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Import Successful", "Your boy scout(s) have been successfully imported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    private static void validateAdvancementDate(BoyScout boyScout, String advancementDate, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(advancementDate)) {
            errors.append("Advancement Date is missing. ").append(errorLine);
            return;
        }

        if (!advancementDate.matches("\\d{4}/\\d{2}/\\d{2}?")) {
            errors.append("Invalid date format date. ").append(errorLine);
            return;
        }

        try {
            boyScout.setAdvancementDate(Util.DATA_BASE_DATE_FORMAT.parse(advancementDate));
        } catch (ParseException e) {
            errors.append("Invalid date format date. ").append(errorLine);
        }
    }

    private static void validateAdvancement(BoyScout boyScout, String advancementName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(advancementName)) {
            errors.append("Advancement is missing. ").append(errorLine);
            return;
        }

        Advancement advancement = LogicAdvancement.findByName(advancementName);
        if (advancement == null) {
            errors.append("Advancement Does not exists. ").append(errorLine);
            return;
        }

        boyScout.setAdvancementId(advancement.getId());
    }

    private static void validateParentPhone(BoyScout boyScout, String phoneNumber, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(phoneNumber)) {
            return;
        }

        if (!Util.validatePhoneNumber(phoneNumber)) {
            errors.append("Invalid Parent Phone Number format. ").append(errorLine);
            return;
        }

        boyScout.setGuardianPhoneNumber(phoneNumber);
    }

    private static void validateScoutPhone(BoyScout boyScout, String phoneNumber, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(phoneNumber)) {
            return;
        }

        if (!Util.validatePhoneNumber(phoneNumber)) {
            errors.append("Invalid Scout Phone Number format. ").append(errorLine);
            return;
        }

        boyScout.setPhoneNumber(phoneNumber);
    }

    private static void addParentName(BoyScout boyScout, String[] record) {
        String parentName = record[COL_PARENT];
        if (!Util.isEmpty(parentName)) {
            boyScout.setGuardianName(parentName);
        }
    }

    private static void addZip(BoyScout boyScout, String[] record) {
        String zip = record[COL_ZIP];
        if (!Util.isEmpty(zip)) {
            boyScout.setZip(zip);
        }
    }

    private static void addStreet(BoyScout boyScout, String[] record) {
        String street = record[COL_STREET];
        if (!Util.isEmpty(street)) {
            boyScout.setStreet(street);
        }
    }

    private static void addState(BoyScout boyScout, String[] record) {
        String state = record[COL_STATE];
        if (!Util.isEmpty(state)) {
            boyScout.setState(state);
        }
    }

    private static void addCity(BoyScout boyScout, String[] record) {
        String city = record[COL_CITY];
        if (!Util.isEmpty(city)) {
            boyScout.setCity(city);
        }
    }

    private static void addPosition(BoyScout boyScout, String[] record) {
        String position = record[COL_POSITION];
        if (!Util.isEmpty(position)) {
            boyScout.setPosition(position);
        }
    }

    private static void addNotes(BoyScout boyScout, String[] record) {
        String note = record[COL_NOTES];
        if (!Util.isEmpty(note)) {
            boyScout.setNote(note);
        }
    }

    private static void validateBirthDate(BoyScout boyScout, String birthDate, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(birthDate)) {
            errors.append("Birth Date is missing. ").append(errorLine);
            return;
        }

        if (!birthDate.matches("\\d{4}/\\d{2}/\\d{2}?")) {
            errors.append("Invalid date format date. ").append(errorLine);
            return;
        }

        try {
            Date date = Util.DATA_BASE_DATE_FORMAT.parse(birthDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (calendar.getTime().getTime() >= new Date().getTime()) {
                errors.append("Invalid birth date: scout hasn't been born yet. ").append(errorLine);
                return;
            }

            Calendar minAgeDate = Calendar.getInstance();
            minAgeDate.add(Calendar.YEAR, -11);

            if (calendar.getTimeInMillis() - minAgeDate.getTimeInMillis() >= 0) {
                errors.append("Invalid birth date: scout must be at least 11 years old. ").append(errorLine);
                return;
            }

            boyScout.setBirthDate(date);
        } catch (ParseException e) {
            errors.append("Invalid date format date. ").append(errorLine);
        }
    }

    private static void validateScoutName(BoyScout boyScout, StringBuilder errors, String errorLine, String scoutName) {
        if (Util.isEmpty(scoutName)){
            errors.append("Boy Scout Name is missing. ").append(errorLine);
            return;
        }
        boyScout.setName(scoutName);
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
