/*
 * Created by JFormDesigner on Sun May 24 13:06:10 MDT 2015
 */

package bsaDb.client.home.dialogs.imports;

import constants.IETypeConst;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class ImportSelectPanel extends JPanel {

    public ImportSelectPanel() {
        initComponents();

        scrollPane1.getVerticalScrollBar().setUnitIncrement(18);
        listImportType.setListData(IETypeConst.getValueNames());
    }

    public IETypeConst getSelectedImport() {
        return IETypeConst.getConst(listImportType.getSelectedValue());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblInstruction = new JLabel();
        scrollPane1 = new JScrollPane();
        listImportType = new JList();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {299, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //---- lblInstruction ----
        lblInstruction.setText("Please select what you would like to import.");
        lblInstruction.setFont(new Font("Vijaya", Font.PLAIN, 22));
        lblInstruction.setForeground(new Color(51, 102, 153));
        lblInstruction.setName("lblInstruction");
        add(lblInstruction, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 10, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setName("scrollPane1");

            //---- listImportType ----
            listImportType.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listImportType.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listImportType.setName("listImportType");
            scrollPane1.setViewportView(listImportType);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 10, 10, 10), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblInstruction;
    private JScrollPane scrollPane1;
    private JList listImportType;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
