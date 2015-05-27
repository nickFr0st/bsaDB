package objects.objectLogic;

import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import constants.RequirementTypeConst;
import objects.databaseObjects.Advancement;
import objects.databaseObjects.Requirement;
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

/**
 * Created by Nathanael on 5/24/2015
 */
public class IEAdvancementLogic {

    public static boolean export(Component parent, List<String> exportList) {
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
            java.util.List<String[]> records = new ArrayList<String[]>();

            records.add(new String[]{"Advancement Name", "Advancement Image Path"});
            records.add(new String[]{"Requirement Name", "Requirement Description"});

            boolean firstPass = true;
            for (Advancement advancement : CacheObject.getAdvancementList(exportList)) {
                if (!firstPass) {
                    records.add(new String[]{""});
                }

                if (Util.isEmpty(advancement.getImgPath())) {
                    records.add(new String[]{advancement.getName()});
                } else {
                    records.add(new String[]{advancement.getName(), advancement.getImgPath()});
                }

                List<Requirement> requirementList = CacheObject.getRequirementListByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());
                if (!Util.isEmpty(requirementList)) {
                    for (Requirement requirement : requirementList) {
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
            ioe.printStackTrace();
            return false;
        }

        JOptionPane.showMessageDialog(parent, "Your selected advancement(s) have been successfully exported.", "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
