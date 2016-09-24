package objects.objectLogic.exports;

import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.RequirementTypeConst;
import objects.databaseObjects.Counselor;
import objects.databaseObjects.MeritBadge;
import objects.databaseObjects.Requirement;
import objects.objectLogic.LogicCounselor;
import objects.objectLogic.LogicRequirement;
import util.CacheObject;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathanael on 5/24/2015
 */
public class ExportMeritBadgeLogic {

    public static boolean execute(Component parent, List<String> exportList) {
        try {
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

        new MessageDialog(Util.getParent(parent), "Export Successful", "Your selected merit badge(s) have been successfully exported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }
}
