package objects.objectLogic.imports;

import au.com.bytecode.opencsv.CSVReader;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.ScoutTypeConst;
import objects.databaseObjects.Camp;
import objects.databaseObjects.Scout;
import objects.databaseObjects.ScoutServiceProject;
import objects.databaseObjects.ServiceProject;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicScoutServiceProject;
import objects.objectLogic.LogicServiceProject;
import util.Util;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.List;

/**
 * Created by Nathanael on 5/24/2015
 */
public class ImportServiceProjectLogic {

    private static final int COL_PROJECT_NAME = 0;
    private static final int COL_SCOUT_TYPE_NAME = 1;
    private static final int COL_LOCATION = 2;
    private static final int COL_START_DATE = 3;
    private static final int COL_LEADERS = 4;
    private static final int COL_DURATION = 5;
    private static final int COL_NOTES = 6;

    private static final int COL_SCOUT_NAME = 0;


    public static boolean execute(Component parent, String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<ServiceProject, List<ScoutServiceProject>> importMap = new HashMap<>();

            boolean getProject = true;
            ServiceProject project = null;
            List<ScoutServiceProject> scoutProjectList = new ArrayList<>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[COL_PROJECT_NAME].equals("Service Project Name") || record[0].equals("Scouts Attended")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getProject = true;

                    if (project != null) {
                        if (!checkForErrors(errors, parent)) {
                            return false;
                        }

                        importMap.put(project, scoutProjectList);

                        project = null;
                        scoutProjectList = new ArrayList<>();
                    }
                    continue;
                }

                if (getProject) {
                    getProject = false;

                    project = new ServiceProject();
                    String campName = record[COL_PROJECT_NAME];

                    validateProjectName(project, errors, errorLine, campName);
                    validateScoutType(project, record[COL_SCOUT_TYPE_NAME], errors, errorLine);
                    validateLocation(project, record[COL_LOCATION], errors, errorLine);
                    validateStartDate(project, record[COL_START_DATE], errors, errorLine);
                    validateLeaders(project, record[COL_LEADERS], errors, errorLine);
                    validateDuration(project, record[COL_DURATION], errors, errorLine);

                    String note = record[COL_NOTES];
                    if (!Util.isEmpty(note)) {
                        project.setNote(note);
                    }
                    continue;
                }

                String scoutName = record[COL_SCOUT_NAME];
                Scout scout = LogicBoyScout.findByName(scoutName);
                if (scout == null) {
                    errors.append("Scout ").append(scoutName).append(" does not exists.").append(errorLine);
                    continue;
                }

                ScoutServiceProject scoutProject = new ScoutServiceProject();
                scoutProject.setScoutId(scout.getId());
                scoutProject.setScoutTypeId(project.getScoutTypeId());

                scoutProjectList.add(scoutProject);
            }

            reader.close();

            if (!checkForErrors(errors, parent)) {
                return false;
            }

            if (project != null) {
                importMap.put(project, scoutProjectList);
            }

            for (Map.Entry<ServiceProject, List<ScoutServiceProject>> entry : importMap.entrySet()) {
                ServiceProject importedProject = entry.getKey();
                ServiceProject existingProject = LogicServiceProject.findByName(importedProject.getName());
                int projectId;

                if (existingProject != null) {
                    projectId = existingProject.getId();
                    existingProject.setLocation(importedProject.getLocation());
                    existingProject.setStartDate(importedProject.getStartDate());
                    existingProject.setLeaders(importedProject.getLeaders());
                    existingProject.setScoutTypeId(importedProject.getScoutTypeId());
                    existingProject.setNote(importedProject.getNote());

                    LogicServiceProject.update(existingProject);

                    LogicScoutServiceProject.deleteAllByProjectId(projectId);
                } else {
                    LogicServiceProject.save(importedProject);
                    projectId = importedProject.getId();
                }

                List<ScoutServiceProject> scoutProjects = entry.getValue();
                if (!Util.isEmpty(scoutProjects)) {
                    for (ScoutServiceProject scoutProject : scoutProjects) {
                        scoutProject.setServiceProjectId(projectId);
                    }

                    LogicScoutServiceProject.save(scoutProjects);
                }
            }

        } catch (IOException ioe) {
            new MessageDialog(null, "Import Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Import Successful", "Your service project(s) have been successfully imported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    private static void validateDuration(ServiceProject project, String duration, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(duration)) {
            errors.append("Duration is missing. ").append(errorLine);
            return;
        }

        try {
            double count = Double.parseDouble(duration);
            if (count <= 0) {
                errors.append("Duration must be a positive number. ").append(errorLine);
            } else {
                project.setDuration(count);
            }
        } catch (NumberFormatException ignore) {
            errors.append("Duration must be a positive number. ").append(errorLine);
        }
    }

    private static void validateLeaders(ServiceProject project, String leaders, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(leaders)) {
            errors.append("Leaders are missing. ").append(errorLine);
            return;
        }

        project.setLeaders(leaders);
    }

    private static void validateStartDate(ServiceProject project, String startDate, StringBuilder errors, String errorLine) {
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
            project.setStartDate(date);
        } catch (ParseException e) {
            errors.append("Invalid date format date. ").append(errorLine);
            return;
        }
    }

    private static void validateProjectName(ServiceProject project, StringBuilder errors, String errorLine, String projectName) {
        if (Util.isEmpty(projectName)){
            errors.append("Service Project Name is missing. ").append(errorLine);
            return;
        }
        project.setName(projectName);
    }

    private static void validateLocation(ServiceProject project, String location, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(location)) {
            errors.append("Location is missing. ").append(errorLine);
            return;
        }

        if (location.length() > Camp.COL_LOCATION_LENGTH) {
            errors.append("Location is too long. ").append(errorLine);
            return;
        }

        project.setLocation(location);
    }

    private static void validateScoutType(ServiceProject project, String scoutTypeName, StringBuilder errors, String errorLine) {
        if (Util.isEmpty(scoutTypeName)) {
            errors.append("Scout type name is missing. ").append(errorLine);
            return;
        }

        ScoutTypeConst scoutTypeConst = ScoutTypeConst.getConst(scoutTypeName);
        if (scoutTypeConst == null) {
            errors.append("Invalid scout type name. ").append(errorLine);
            return;
        }

        project.setScoutTypeId(scoutTypeConst.getId());
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
