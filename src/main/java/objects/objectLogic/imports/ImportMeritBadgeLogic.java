package objects.objectLogic.imports;

import au.com.bytecode.opencsv.CSVReader;
import bsaDb.client.dialogs.imports.helper.MeritBadgeImport;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.RequirementTypeConst;
import objects.databaseObjects.Counselor;
import objects.databaseObjects.MeritBadge;
import objects.databaseObjects.Requirement;
import objects.objectLogic.LogicCounselor;
import objects.objectLogic.LogicMeritBadge;
import objects.objectLogic.LogicRequirement;
import util.CacheObject;
import util.Util;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Nathanael on 5/24/2015
 */
public class ImportMeritBadgeLogic {

    private static final int HEADER_INDEX = 0;

    private static final int BADGE_NAME_INDEX = 0;
    private static final int REQUIRED_INDEX = 1;
    private static final int IMAGE_INDEX = 2;
    private static final int COUNSELOR_NAME_INDEX = 0;
    private static final int COUNSELOR_PHONE_INDEX = 1;
    private static final int REQUIREMENT_NAME_INDEX = 0;
    private static final int REQUIREMENT_DESC_INDEX = 1;

    public static boolean execute(Component parent, String importPath) {
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
                line++;
                String errorLine = "line: " + line + "\n";

                if (record[HEADER_INDEX].equals("Merit Badge Name") || record[HEADER_INDEX].equals("Counselor Name") || record[HEADER_INDEX].equals("Requirement Name")) {
                    continue;
                }

                if (record[HEADER_INDEX].isEmpty()) {
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

                    meritBadge = new MeritBadge();
                    String badgeName = record[BADGE_NAME_INDEX];

                    validateBadgeName(meritBadge, errors, errorLine, badgeName);
                    validateRequiredForEagle(meritBadge, record, errors, errorLine);

                    String advancementImgPath = record[IMAGE_INDEX];
                    if (!Util.isEmpty(advancementImgPath)){
                        meritBadge.setImgPath(advancementImgPath);
                    }
                    continue;
                }

                if (record[COUNSELOR_NAME_INDEX].startsWith("*")) {
                    Counselor counselor = new Counselor();
                    String counselorName = stripAstrix(record[COUNSELOR_NAME_INDEX]);
                    if (Util.isEmpty(counselorName)){
                        errors.append("Counselor name is missing. ").append(errorLine);
                    } else if (counselorName.length() > Counselor.COL_NAME_LENGTH) {
                        errors.append("Counselor name is too long. ").append(errorLine);
                    } else if (!counselorNameSet.add(counselorName)) {
                        continue;
                    }
                    counselor.setName(counselorName);

                    validateCounselorPhone(record, errors, errorLine, counselor, counselorName);
                    counselorList.add(counselor);
                    continue;
                }

                Requirement requirement = new Requirement();
                String reqName = record[REQUIREMENT_NAME_INDEX];
                validateRequirementName(errors, errorLine, requirement, reqName);

                vaildateRequirementDesc(record, errors, errorLine, requirement);
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
                    if (!Util.isEmpty(importedBadge.getImgPath()) && !existingBadge.isReadOnly()) {
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
                    LogicCounselor.save(counselors);
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

    private static void vaildateRequirementDesc(String[] record, StringBuilder errors, String errorLine, Requirement requirement) {
        String reqDesc = record[REQUIREMENT_DESC_INDEX];
        if (Util.isEmpty(reqDesc)){
            errors.append("Requirement description is missing. ").append(errorLine);
        }
        requirement.setDescription(reqDesc);
    }

    private static void validateRequirementName(StringBuilder errors, String errorLine, Requirement requirement, String reqName) {
        if (Util.isEmpty(reqName)){
            errors.append("Requirement name is missing. ").append(errorLine);
        } else if (reqName.length() > Requirement.COL_NAME_LENGTH) {
            errors.append("Requirement name is too long. ").append(errorLine);
        }
        requirement.setName(reqName);
    }

    private static void validateCounselorPhone(String[] record, StringBuilder errors, String errorLine, Counselor counselor, String counselorName) {
        String phoneNumber = record[COUNSELOR_PHONE_INDEX];
        if (Util.isEmpty(phoneNumber)){
            errors.append("Phone number is missing. ").append(errorLine);
        } else if (counselorName.length() > Counselor.COL_PHONE_NUMBER_LENGTH) {
            errors.append("Phone number is too long. ").append(errorLine);
        }

        if (!Util.validatePhoneNumber(phoneNumber)) {
            errors.append("Phone number format is incorrect. ").append(errorLine);
        }

        counselor.setPhoneNumber(phoneNumber);
    }

    private static String stripAstrix(String s) {
        if (s.startsWith("*")) {
            return s.substring(1, s.length());
        }
        return s;
    }

    private static void validateRequiredForEagle(MeritBadge meritBadge, String[] record, StringBuilder errors, String errorLine) {
        if (!(checkForBoolean(record[REQUIRED_INDEX].trim()))) {
            errors.append("invalid value: ").append(record[1]).append(". Accepted values are 'true' or 'false'. ").append(errorLine);
            meritBadge.setRequiredForEagle(false);
        } else {
            meritBadge.setRequiredForEagle(Boolean.parseBoolean(record[1].trim()));
        }
    }

    private static void validateBadgeName(MeritBadge meritBadge, StringBuilder errors, String errorLine, String badgeName) {
        if (Util.isEmpty(badgeName)){
            errors.append("Merit badge name is missing. ").append(errorLine);
        } else if (badgeName.length() > MeritBadge.COL_NAME_LENGTH) {
            errors.append("Merit badge name is too long. ").append(errorLine);
        }
        meritBadge.setName(badgeName);
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
