package objects.objectLogic.exports;

import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.dialogs.message.MessageDialog;
import objects.databaseObjects.BoyScout;
import objects.objectLogic.LogicAdvancement;
import objects.objectLogic.LogicBoyScout;
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
public class ExportBoyScoutLogic {

    public static boolean execute(Component parent, List<String> exportList) {
        try {
            // get the file path
            CustomChooser chooser = new CustomChooser("Select export location", JFileChooser.FILES_ONLY);

            chooser.setSelectedFile(new File("BoyScoutExport.csv"));
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

            String[] header = {
                    "Boy Scout Name",
                    "Birth Date",
                    "Advancement",
                    "Advancement Date",
                    "Position",
                    "Scout Phone Number",
                    "City",
                    "State",
                    "Street",
                    "Zip",
                    "Parent Name",
                    "Parent Phone Number",
                    "Notes"
            };

            records.add(header);

            boolean firstPass = true;
            for (BoyScout scout : LogicBoyScout.findAllByName(exportList)) {
                if (!firstPass) {
                    records.add(new String[]{""});
                }

                records.add(
                        new String[]{
                                scout.getName(),
                                Util.DATA_BASE_DATE_FORMAT.format(scout.getBirthDate()),
                                LogicAdvancement.findById(scout.getAdvancementId()).getName(),
                                Util.DATA_BASE_DATE_FORMAT.format(scout.getAdvancementDate()),
                                scout.getPosition(),
                                scout.getPhoneNumber(),
                                scout.getCity(),
                                scout.getState(),
                                scout.getStreet(),
                                scout.getZip(),
                                scout.getGuardianName(),
                                scout.getGuardianPhoneNumber(),
                                scout.getNote()
                        });

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

        new MessageDialog(Util.getParent(parent), "Export Successful", "Your selected boy scout(s) have been successfully exported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }
}
