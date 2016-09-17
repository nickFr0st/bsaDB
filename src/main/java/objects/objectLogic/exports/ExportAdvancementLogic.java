package objects.objectLogic.exports;

import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.RequirementTypeConst;
import objects.databaseObjects.Advancement;
import objects.databaseObjects.Requirement;
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
public class ExportAdvancementLogic {

    public static boolean execute(Component parent, List<String> exportList) {
        try {
            // get the file path
            CustomChooser chooser = new CustomChooser("Select export location", JFileChooser.FILES_ONLY);

            chooser.setSelectedFile(new File("AdvancementExport.csv"));
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

            records.add(new String[]{"Advancement Name", "Time Requirement", "Advancement Image Path", "Service Hours"});
            records.add(new String[]{"Requirement Name", "Requirement Description"});

            boolean firstPass = true;
            for (Advancement advancement : CacheObject.getAdvancementList(exportList)) {
                if (!firstPass) {
                    records.add(new String[]{""});
                }

                List<String> row = new ArrayList<>();
                row.add(advancement.getName());

                if (advancement.getTimeRequirement() == null) {
                    row.add("");
                } else {
                    row.add(advancement.getTimeRequirement().toString());
                }

                if (Util.isEmpty(advancement.getImgPath())) {
                    row.add("");
                } else {
                    row.add(advancement.getImgPath());
                }

                if (advancement.getServiceHours() == null) {
                    row.add("");
                } else {
                    row.add(advancement.getServiceHours().toString());
                }

                String[] temp = new String[row.size()];
                for (int i = 0; i < row.size(); i++) {
                    temp[i] = row.get(i);
                }
                records.add(temp);

                Set<Requirement> requirementSet = LogicRequirement.findAllByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());
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
}
