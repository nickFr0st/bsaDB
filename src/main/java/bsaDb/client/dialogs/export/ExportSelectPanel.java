/*
 * Created by JFormDesigner on Sun May 24 13:06:10 MDT 2015
 */

package bsaDb.client.dialogs.export;

import constants.IETypeConst;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class ExportSelectPanel extends JPanel {
    public ExportSelectPanel() {
        initComponents();

        scrollPane1.getVerticalScrollBar().setUnitIncrement(18);
        listExportType.setListData(IETypeConst.getValueNames());
    }

    public IETypeConst getSelectedExport() {
        return IETypeConst.getConst(listExportType.getSelectedValue());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblInstruction = new JLabel();
        scrollPane1 = new JScrollPane();
        listExportType = new JList();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {299, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //---- lblInstruction ----
        lblInstruction.setText("Please select what you would like to export.");
        lblInstruction.setFont(new Font("Vijaya", Font.PLAIN, 22));
        lblInstruction.setForeground(new Color(0, 63, 135));
        lblInstruction.setName("lblInstruction");
        add(lblInstruction, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 10, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setName("scrollPane1");

            //---- listExportType ----
            listExportType.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listExportType.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listExportType.setName("listExportType");
            scrollPane1.setViewportView(listExportType);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 10, 10, 10), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblInstruction;
    private JScrollPane scrollPane1;
    private JList listExportType;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
