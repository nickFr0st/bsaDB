package objects.objectLogic;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.dialogs.imports.helper.MeritBadgeImport;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.RequirementTypeConst;
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

                List<Counselor> counselorList = LogicCounselor.findAllByBadgeId(meritBadge.getId());
                if (!Util.isEmpty(counselorList)) {
                    for (Counselor counselor : counselorList) {
                        records.add(new String[]{"*" + counselor.getName(), counselor.getPhoneNumber()});
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
            Map<MeritBadge, MeritBadgeImport> importMap = new HashMap<>();

            boolean getMeritBadge = true;
            MeritBadge meritBadge = null;
            Set<Requirement> requirementSet = new LinkedHashSet<>();
            List<Counselor> counselorList = new ArrayList<>();
            Set<String> counselorNameSet = new HashSet<>();

            String[] record;
            int line = 0;
            StringBuilder errors = new StringBuilder();

            while ((record = reader.readNext()) != null) {
                ++line;
                String errorLine = "line: " + line + "\n";

                // check for the headers
                if (record[0].equals("Merit Badge Name") || record[0].equals("Counselor Name") || record[0].equals("Requirement Name")) {
                    continue;
                }

                if (record[0].isEmpty()) {
                    getMeritBadge = true;

                    if (meritBadge != null) {
                        if (!checkForErrors(errors, parent)) {
                            return false;
                        }

                        MeritBadgeImport badgeImport = new MeritBadgeImport();
                        badgeImport.setRequirementSet(requirementSet);
                        badgeImport.setCounselorList(counselorList);

                        importMap.put(meritBadge, badgeImport);

                        meritBadge = null;
                        requirementSet = new LinkedHashSet<>();
                        counselorList = new ArrayList<>();
                        counselorNameSet = new HashSet<>();
                    }

                    continue;
                }

                if (getMeritBadge) {
                    getMeritBadge = false;

                    if (record.length < 2) {
                        errors.append("There are too few values for the merit badge. ").append(errorLine);
                        continue;
                    }


                    meritBadge = new MeritBadge();
                    String badgeName = record[0];

                    if (Util.isEmpty(badgeName)){
                        errors.append("Merit badge name is missing. ").append(errorLine);
                    } else if (badgeName.length() > MeritBadge.COL_NAME_LENGTH) {
                        errors.append("Merit badge name is too long. ").append(errorLine);
                    }
                    meritBadge.setName(badgeName);

                    if (!(checkForBoolean(record[1].trim()))) {
                        errors.append("invalid value: ").append(record[1]).append(". Accepted values are 'true' or 'false'. ").append(errorLine);
                        meritBadge.setRequiredForEagle(false);
                        continue;
                    } else {
                        meritBadge.setRequiredForEagle(Boolean.parseBoolean(record[1].trim()));
                    }

                    if (record.length == 2) {
                        continue;
                    }

                    String advancementImgPath = record[2];
                    if (Util.isEmpty(advancementImgPath)){
                        errors.append("Merit badge image path is missing. ").append(errorLine);
                    } else if (advancementImgPath.length() > MeritBadge.COL_IMG_PATH_LENGTH) {
                        errors.append("Merit badge image path is too long. ").append(errorLine);
                    }
                    meritBadge.setImgPath(advancementImgPath);
                    continue;
                }

                if (record[0].startsWith("*")) {
                    if (record.length < 2) {
                        errors.append("Counselors needs both a name and a phone number.").append(errorLine);
                        continue;
                    }

                    Counselor counselor = new Counselor();
                    String counselorName = record[0];
                    if (Util.isEmpty(counselorName)){
                        errors.append("Counselor name is missing. ").append(errorLine);
                    } else if (counselorName.length() > Counselor.COL_NAME_LENGTH) {
                        errors.append("Counselor name is too long. ").append(errorLine);
                    } else if (!counselorNameSet.add(counselorName)) {
                        errors.append("Counselor names must be unique per Merit Badge. ").append(errorLine);
                    }
                    counselor.setName(counselorName.substring(1, counselorName.length()));

                    String phoneNumber = record[1];
                    if (Util.isEmpty(phoneNumber)){
                        errors.append("Phone number is missing. ").append(errorLine);
                    } else if (counselorName.length() > Counselor.COL_PHONE_NUMBER_LENGTH) {
                        errors.append("Phone number is too long. ").append(errorLine);
                    }

                    if (!Util.validatePhoneNumber(phoneNumber)) {
                        errors.append("Phone number format is incorrect. ").append(errorLine);
                    }

                    counselor.setPhoneNumber(phoneNumber);
                    counselorList.add(counselor);
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
                requirement.setTypeId(RequirementTypeConst.MERIT_BADGE.getId());

                requirementSet.add(requirement);
            }

            reader.close();

            if (!checkForErrors(errors, parent)) {
                return false;
            }

            MeritBadgeImport badgeImport = new MeritBadgeImport();
            badgeImport.setCounselorList(counselorList);
            badgeImport.setRequirementSet(requirementSet);
            importMap.put(meritBadge, badgeImport);

            for (Map.Entry<MeritBadge, MeritBadgeImport> entry : importMap.entrySet()) {
                MeritBadge importedBadge = entry.getKey();
                MeritBadge existingBadge = LogicMeritBadge.findByName(importedBadge.getName());
                int meritBadgeId;

                if (existingBadge != null) {
                    meritBadgeId = existingBadge.getId();
                    if (!Util.isEmpty(importedBadge.getImgPath())) {
                        existingBadge.setImgPath(importedBadge.getImgPath());
                    }
                    existingBadge.setRequiredForEagle(importedBadge.isRequiredForEagle());
                    LogicMeritBadge.update(existingBadge);
                    CacheObject.addToMeritBadges(existingBadge);

                    LogicCounselor.deleteAllByBadgeId(meritBadgeId);
                    LogicRequirement.deleteAllByParentIdAndTypeId(meritBadgeId, RequirementTypeConst.MERIT_BADGE.getId());
                } else {
                    if (importedBadge.getImgPath() == null) {
                        importedBadge.setImgPath("");
                    }

                    LogicMeritBadge.save(importedBadge);
                    CacheObject.addToMeritBadges(importedBadge);
                    meritBadgeId = importedBadge.getId();
                }


                MeritBadgeImport listContainer = importMap.get(importedBadge);
                List<Counselor> counselors = listContainer.getCounselorList();
                if (!Util.isEmpty(counselors)) {
                    for (Counselor counselor : counselors) {
                        counselor.setBadgeId(meritBadgeId);
                    }
                    LogicCounselor.save(counselorList);
                }

                Set<Requirement> reqList = listContainer.getRequirementSet();
                if (!Util.isEmpty(reqList)) {
                    for (Requirement req : reqList) {
                        req.setParentId(meritBadgeId);
                    }
                    LogicRequirement.save(reqList);
                }
            }

        } catch (IOException ioe) {
            new MessageDialog(null, "Import Error", ioe.getMessage(), MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return false;
        }

        new MessageDialog(Util.getParent(parent), "Import Successful", "Your merit badges have been successfully imported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }

    private static boolean checkForBoolean(String arg) {
        return !Util.isEmpty(arg) && ("true".equalsIgnoreCase(arg) || "false".equalsIgnoreCase(arg));
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
