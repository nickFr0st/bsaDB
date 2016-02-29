package objects.objectLogic;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.home.dialogs.MessageDialog;
import constants.RequirementTypeConst;
import objects.databaseObjects.Advancement;
import objects.databaseObjects.Counselor;
import objects.databaseObjects.MeritBadge;
import objects.databaseObjects.Requirement;
import util.CacheObject;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Nathanael on 5/24/2015
 */
public class IEMeritBadgeLogic {

    public static boolean export(Component parent, List<String> exportList) {
        try {
            // get the file path
            CustomChooser chooser = new CustomChooser("Select export location", JFileChooser.FILES_ONLY);

            chooser.setSelectedFile(new File("MeritBadgeExport.csv"));
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

            records.add(new String[]{"Merit Badge Name", "Is Required For Eagle", "Merit Badge Image Path"});
            records.add(new String[]{"Counselor Name", "Counselor Phone Number"});
            records.add(new String[]{"Requirement Name", "Requirement Description"});

            boolean firstPass = true;
            for (MeritBadge meritBadge : CacheObject.getMeritBadgeList(exportList)) {
                if (!firstPass) {
                    records.add(new String[]{""});
                }

                if (Util.isEmpty(meritBadge.getImgPath())) {
                    records.add(new String[]{meritBadge.getName(), Boolean.toString(meritBadge.isRequiredForEagle())});
                } else {
                    records.add(new String[]{meritBadge.getName(), Boolean.toString(meritBadge.isRequiredForEagle()), meritBadge.getImgPath()});
                }

                List<Counselor> counselorList = CacheObject.getCounselorListByBadgeId(meritBadge.getId());
                if (!Util.isEmpty(counselorList)) {
                    for (Counselor counselor : counselorList) {
                        records.add(new String[]{counselor.getName(), counselor.getPhoneNumber()});
                    }
                }

                Set<Requirement> requirementSet = LogicRequirement.findAllByParentIdAndTypeId(meritBadge.getId(), RequirementTypeConst.MERIT_BADGE.getId());
                if (!Util.isEmpty(requirementSet)) {
                    for (Requirement requirement : requirementSet) {
                        records.add(new String[]{requirement.getName(), requirement.getDescription()});
                    }
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

        new MessageDialog(Util.getParent(parent), "Export Successful", "Your selected advancement(s) have been successfully exported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    public static boolean doImport(Component parent, String importPath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(importPath), ',');
            Map<Advancement, List<Requirement>> importMap = new HashMap<>();

            boolean getAdvancement = true;
            Advancement advancement = null;
            List<Requirement> requirementList = new ArrayList<>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Advancement Name") || record[0].equals("Requirement Name")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getAdvancement = true;

                    if (advancement != null) {
                        if (!checkForErrors(errors, parent)) {
                            return false;
                        }

                        importMap.put(advancement, requirementList);
                        advancement = null;
                        requirementList = new ArrayList<>();
                    }

                    continue;
                }

                if (getAdvancement) {
                    getAdvancement = false;

                    advancement = new Advancement();
                    String advancementName = record[0];

                    if (Util.isEmpty(advancementName)){
                        errors.append("Advancement name is missing. ").append(errorLine);
                    } else if (advancementName.length() > Advancement.COL_NAME_LENGTH) {
                        errors.append("Advancement name is too long. ").append(errorLine);
                    }
                    advancement.setName(advancementName);

                    if (record.length == 1) {
                        continue;
                    }

                    String advancementImgPath = record[1];
                    if (Util.isEmpty(advancementImgPath)){
                        errors.append("Advancement image path is missing. ").append(errorLine);
                    } else if (advancementImgPath.length() > Advancement.COL_IMG_PATH_LENGTH) {
                        errors.append("Advancement image path is too long. ").append(errorLine);
                    }
                    advancement.setImgPath(advancementImgPath);

                    continue;
                }

                if (record.length < 2) {
                    errors.append("Requirements needs both a name and a description.").append(errorLine);
                    continue;
                }

                Requirement requirement = new Requirement();
                String reqName = record[0];
                if (Util.isEmpty(reqName)){
                    errors.append("Requirement name is missing. ").append(errorLine);
                } else if (reqName.length() > Requirement.COL_NAME_LENGTH) {
                    errors.append("Requirement name is too long. ").append(errorLine);
                }
                requirement.setName(reqName);

                String reqDesc = record[1];
                if (Util.isEmpty(reqDesc)){
                    errors.append("Requirement description is missing. ").append(errorLine);
                }
                requirement.setDescription(reqDesc);
                requirement.setTypeId(RequirementTypeConst.ADVANCEMENT.getId());

                requirementList.add(requirement);
            }

            reader.close();

            if (!checkForErrors(errors, parent)) {
                return false;
            }

            importMap.put(advancement, requirementList);

            for (Map.Entry<Advancement, List<Requirement>> entry : importMap.entrySet()) {
                Advancement importedAdvancement = entry.getKey();
                Advancement existingAdv = CacheObject.getAdvancement(importedAdvancement.getName());
                int advancementId;

                if (existingAdv != null) {
                    advancementId = existingAdv.getId();
                    if (!Util.isEmpty(importedAdvancement.getImgPath())) {
                        existingAdv.setImgPath(importedAdvancement.getImgPath());
                    }
                    existingAdv = LogicAdvancement.update(existingAdv);
                    CacheObject.addToAdvancements(existingAdv);

                    Set<Requirement> existingRequirementSet = LogicRequirement.findAllByParentIdAndTypeId(existingAdv.getId(), RequirementTypeConst.ADVANCEMENT.getId());
                    if (!Util.isEmpty(existingRequirementSet)) {
                        LogicRequirement.delete(existingRequirementSet);
                    }
                } else {
                    if (importedAdvancement.getImgPath() == null) {
                        importedAdvancement.setImgPath("");
                    }

                    importedAdvancement = LogicAdvancement.save(importedAdvancement);
                    CacheObject.addToAdvancements(importedAdvancement);
                    advancementId = importedAdvancement.getId();
                }

                if (Util.isEmpty(entry.getValue())) {
                    continue;
                }

                for (Requirement requirement : entry.getValue()) {
                    requirement.setParentId(advancementId);
                    requirement.setTypeId(RequirementTypeConst.ADVANCEMENT.getId());
                    LogicRequirement.save(requirement);
                }
            }

        } catch (IOException ioe) {
            new MessageDialog(null, "Import Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Import Successful", "Your advancements have been successfully imported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
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
