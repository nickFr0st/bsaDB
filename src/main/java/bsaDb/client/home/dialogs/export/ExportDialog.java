/*
 * Created by JFormDesigner on Sun May 24 13:04:15 MDT 2015
 */

package bsaDb.client.home.dialogs.export;

import bsaDb.client.home.dialogs.MessageDialog;
import constants.IETypeConst;
import objects.objectLogic.IEAdvancementLogic;
import objects.objectLogic.IECampLogic;
import objects.objectLogic.IEMeritBadgeLogic;
import util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author User #2
 */
public class ExportDialog extends JDialog {

    private static final String EXPORT_SELECT_PAGE = "exportSelect";
    private static final String EXPORT_PAGE = "export";

    private ExportSelectPanel pnlSelect;
    private ExportPanel pnlExport;
    private IETypeConst typeConst;

    public ExportDialog(Frame owner) {
        super(owner);
        initComponents();

        pnlSelect = new ExportSelectPanel();
        pnlContent.add(pnlSelect, EXPORT_SELECT_PAGE);

        btnExport.setEnabled(false);
    }

    private void btnCancelActionPerformed() {
        setVisible(false);
        dispose();
    }

    private void btnNextActionPerformed() {
        typeConst = pnlSelect.getSelectedExport();

        if (typeConst == null) {
            new MessageDialog(Util.getParent(this), "Empty Export Choice", "Please select an option from the list to export.", MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return;
        }

        if (pnlExport == null) {
            pnlExport = new ExportPanel(typeConst);
            pnlContent.add(pnlExport, EXPORT_PAGE);
        }

        ((CardLayout)pnlContent.getLayout()).show(pnlContent, EXPORT_PAGE);

        btnNext.setVisible(false);
        btnExport.setEnabled(true);
    }

    private void btnExportActionPerformed() {
        List<String> exportList = pnlExport.getExportList();

        if (Util.isEmpty(exportList)) {
            new MessageDialog(Util.getParent(this), "Empty Export Choice", "Please select at least one " + typeConst.getName() + " to export.", MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return;
        }

        boolean success = false;
        switch (typeConst) {
            case ADVANCEMENT:
                success = IEAdvancementLogic.export(this, exportList);
                break;
            case MERIT_BADGE:
                success = IEMeritBadgeLogic.export(this, exportList);
                break;
            case CAMPOUT:
                success = IECampLogic.export(this, exportList);
                break;
        }

        if (success) {
            setVisible(false);
            dispose();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        pnlContent = new JPanel();
        buttonBar = new JPanel();
        btnNext = new JButton();
        btnExport = new JButton();
        btnCancel = new JButton();

        //======== this ========
        setModal(true);
        setResizable(false);
        setTitle("Export");
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(Color.white);
            dialogPane.setName("dialogPane");
            dialogPane.setLayout(new BorderLayout());

            //======== pnlContent ========
            {
                pnlContent.setOpaque(false);
                pnlContent.setPreferredSize(new Dimension(500, 230));
                pnlContent.setName("pnlContent");
                pnlContent.setLayout(new CardLayout());
            }
            dialogPane.add(pnlContent, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setOpaque(false);
                buttonBar.setName("buttonBar");
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {35};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

                //---- btnNext ----
                btnNext.setText("Next");
                btnNext.setBackground(new Color(51, 156, 229));
                btnNext.setForeground(Color.white);
                btnNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnNext.setFocusPainted(false);
                btnNext.setName("btnNext");
                btnNext.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnNextActionPerformed();
                    }
                });
                buttonBar.add(btnNext, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnExport ----
                btnExport.setText("Export");
                btnExport.setBackground(new Color(51, 102, 153));
                btnExport.setForeground(Color.white);
                btnExport.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnExport.setFocusPainted(false);
                btnExport.setName("btnExport");
                btnExport.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnExportActionPerformed();
                    }
                });
                buttonBar.add(btnExport, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- btnCancel ----
                btnCancel.setText("Cancel");
                btnCancel.setBackground(new Color(207, 0, 0));
                btnCancel.setForeground(Color.white);
                btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnCancel.setFocusPainted(false);
                btnCancel.setName("btnCancel");
                btnCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnCancelActionPerformed();
                    }
                });
                buttonBar.add(btnCancel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel pnlContent;
    private JPanel buttonBar;
    private JButton btnNext;
    private JButton btnExport;
    private JButton btnCancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
