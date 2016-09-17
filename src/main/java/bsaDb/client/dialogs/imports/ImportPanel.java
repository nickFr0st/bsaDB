/*
 * Created by JFormDesigner on Tue May 26 21:21:52 MDT 2015
 */

package bsaDb.client.dialogs.imports;

import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import constants.IETypeConst;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @author User #2
 */
public class ImportPanel extends JPanel {
    IETypeConst typeConst;

    public ImportPanel() {
        initComponents();
    }

    public ImportPanel (IETypeConst typeConst) {
        this();

        this.typeConst = typeConst;

        switch (typeConst) {
            case ADVANCEMENT:
                setTxtImportInstructions(getClass().getResource("/instructions/ImportAdvancementInstructions.html").toString());
                break;
            case MERIT_BADGE:
                setTxtImportInstructions(getClass().getResource("/instructions/ImportMeritBadgeInstructions.html").toString());
                break;
            case CAMPOUT:
                setTxtImportInstructions(getClass().getResource("/instructions/ImportCampInstructions.html").toString());
                break;
            case SERVICE_PROJECTS:
                setTxtImportInstructions(getClass().getResource("/instructions/ImportServiceProjectInstructions.html").toString());
                break;
        }
    }

    public void setTxtImportInstructions(String url) {
        try {
            txtInstructions.setPage(url);
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void btnBrowseActionPerformed() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");

        CustomChooser chooser = new CustomChooser();
        chooser.setDialogTitle("Select a file to import");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
        int returnValue = chooser.showOpenDialog(this);
        chooser.resetLookAndFeel();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            txtImportPath.setText(file.getPath());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JLabel lblSelectMessage = new JLabel();
        txtImportPath = new JTextFieldDefaultText();
        btnBrowse = new JButton();
        JLabel lblImportInstructionMessage = new JLabel();
        scrollPane1 = new JScrollPane();
        txtInstructions = new JEditorPane();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 80, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 40, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //---- lblSelectMessage ----
        lblSelectMessage.setText("Please select a file to import");
        lblSelectMessage.setFont(new Font("Vijaya", Font.PLAIN, 20));
        lblSelectMessage.setForeground(new Color(0, 63, 135));
        lblSelectMessage.setName("lblSelectMessage");
        add(lblSelectMessage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 10, 5, 5), 0, 0));

        //---- txtImportPath ----
        txtImportPath.setDefaultText("Import file path");
        txtImportPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtImportPath.setName("txtImportPath");
        add(txtImportPath, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 10, 5, 5), 0, 0));

        //---- btnBrowse ----
        btnBrowse.setText("Browse");
        btnBrowse.setBackground(new Color(0, 63, 135));
        btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBrowse.setForeground(Color.white);
        btnBrowse.setFocusPainted(false);
        btnBrowse.setName("btnBrowse");
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBrowseActionPerformed();
            }
        });
        add(btnBrowse, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 10), 0, 0));

        //---- lblImportInstructionMessage ----
        lblImportInstructionMessage.setText("Import file instructions");
        lblImportInstructionMessage.setForeground(new Color(0, 63, 135));
        lblImportInstructionMessage.setFont(new Font("Vijaya", Font.PLAIN, 20));
        lblImportInstructionMessage.setName("lblImportInstructionMessage");
        add(lblImportInstructionMessage, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 10, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setName("scrollPane1");

            //---- txtInstructions ----
            txtInstructions.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtInstructions.setBackground(Color.white);
            txtInstructions.setForeground(Color.black);
            txtInstructions.setEditable(false);
            txtInstructions.setName("txtInstructions");
            scrollPane1.setViewportView(txtInstructions);
        }
        add(scrollPane1, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 10, 10, 10), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public String getImportPath() {
        if (txtImportPath.isMessageDefault() || txtImportPath.getText().isEmpty()) {
            return "";
        }

        return txtImportPath.getText();
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextFieldDefaultText txtImportPath;
    private JButton btnBrowse;
    private JScrollPane scrollPane1;
    private JEditorPane txtInstructions;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
