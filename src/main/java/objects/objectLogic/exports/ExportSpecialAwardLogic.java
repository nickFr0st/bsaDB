package objects.objectLogic.exports;

import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.ScoutTypeConst;
import objects.databaseObjects.SpecialAward;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicSpecialAward;
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
public class ExportSpecialAwardLogic {

    public static boolean execute(Component parent, List<String> exportList) {
        try {
            // get the file path
            CustomChooser chooser = new CustomChooser("Select export location", JFileChooser.FILES_ONLY);

            chooser.setSelectedFile(new File("SpecialAwardExport.csv"));
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

            records.add(new String[]{"Scout Name", "Scout Type Name", "Award Name", "Award Description", "Award Date Received"});

            List<Integer> scoutIdList = LogicBoyScout.findIdsByName(exportList);

            for (SpecialAward specialAward : LogicSpecialAward.findAllByScoutId(scoutIdList)) {
                records.add(
                        new String[]{
                                LogicBoyScout.findById(specialAward.getScoutId()).getName(),
                                ScoutTypeConst.getConst(specialAward.getScoutTypeId()).getName(),
                                specialAward.getName(),
                                specialAward.getDescription(),
                                Util.DATA_BASE_DATE_FORMAT.format(specialAward.getDateReceived())
                        });
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

        new MessageDialog(Util.getParent(parent), "Export Successful", "Your selected special award(s) have been successfully exported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }
}
