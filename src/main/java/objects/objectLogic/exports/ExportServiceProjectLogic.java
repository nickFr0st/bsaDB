package objects.objectLogic.exports;

import au.com.bytecode.opencsv.CSVWriter;
import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.dialogs.message.MessageDialog;
import constants.ScoutTypeConst;
import objects.databaseObjects.Scout;
import objects.databaseObjects.ScoutServiceProject;
import objects.databaseObjects.ServiceProject;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicScoutServiceProject;
import objects.objectLogic.LogicServiceProject;
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
public class ExportServiceProjectLogic {

    public static boolean execute(Component parent, List<String> exportList) {
        try {
            // get the file path
            CustomChooser chooser = new CustomChooser("Select export location", JFileChooser.FILES_ONLY);

            chooser.setSelectedFile(new File("ServiceProjectExport.csv"));
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

            records.add(new String[]{"Service Project Name", "Scout Type Name", "Location", "Start Date", "Leaders", "Duration", "Notes"});
            records.add(new String[]{"Scouts Attended"});

            boolean firstPass = true;
            for (ServiceProject serviceProject : LogicServiceProject.findAllByName(exportList)) {
                if (!firstPass) {
                    records.add(new String[]{""});
                }

                records.add(
                        new String[]{
                                serviceProject.getName(), 
                                ScoutTypeConst.getConst(serviceProject.getScoutTypeId()).getName(), 
                                serviceProject.getLocation(), 
                                Util.DATA_BASE_DATE_FORMAT.format(serviceProject.getStartDate()), 
                                serviceProject.getLeaders(), 
                                String.valueOf(serviceProject.getDuration()), 
                                serviceProject.getNote()
                        });

                for (ScoutServiceProject scoutServiceProject : LogicScoutServiceProject.findAllByProjectId(serviceProject.getId())) {
                    Scout boyScout = LogicBoyScout.findById(scoutServiceProject.getScoutId());
                    if (boyScout == null) {
                         continue;
                    }
                    records.add(new String[]{boyScout.getName()});
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

        new MessageDialog(Util.getParent(parent), "Export Successful", "Your selected service project(s) have been successfully exported.", MessageDialog.MessageType.SUCCESS, MessageDialog.ButtonType.OKAY);
        return true;
    }
}
