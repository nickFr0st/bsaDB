/*
 * Created by JFormDesigner on Sat Nov 15 15:13:17 MST 2014
 */

package bsaDb.client.home.dialogs;

import bsaDb.client.customComponents.jdatepicker.JDatePicker;
import util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

/**
 * @author User #2
 */
public class EditScoutAdvancementDialog extends JDialog {
    public static final int BTN_OK = 1;

    private int btnChoice;

    {
        btnChoice = 0;
    }

    public EditScoutAdvancementDialog(Frame owner, String requirementName, boolean isCompleted, Date dateCompleted) {
        super(owner, true);
        initComponents();

        lblReqNameValue.setText(requirementName);

        chkCompleted.setSelected(isCompleted);
        cboDateCompleted.setEnabled(isCompleted);

        if (dateCompleted == null || !isCompleted) {
            cboDateCompleted.getModel().setSelected(false);
        } else {
            try {
                Calendar date = Calendar.getInstance();
                date.setTime(dateCompleted);

                cboDateCompleted.getModel().setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
                cboDateCompleted.getModel().setSelected(true);
            } catch (Exception ignore) {
                cboDateCompleted.getModel().setSelected(false);
            }
        }

        clearErrors();
        setVisible(true);
    }

    private void clearErrors() {
        Util.clearError(lblDateCompletedError);
        revalidate();
    }

    private void cancelButtonMouseClicked() {
        dispose();
    }

    private void okButtonMouseClicked() {
        if (!validateFields()) return;

        btnChoice = BTN_OK;
        dispose();
    }

    private boolean validateFields() {
        clearErrors();
        boolean hasErrors = false;

        if (chkCompleted.isSelected() && !cboDateCompleted.getModel().isSelected()) {
            Util.setError(lblDateCompletedError, "must specify a valid date");
            hasErrors = true;
        }

        return !hasErrors;
    }

    public int getBtnChoice() {
        return btnChoice;
    }

    public Date getCompletedDate() {
        if (!getCompleted()) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cboDateCompleted.getModel().getYear());
        cal.set(Calendar.MONTH, cboDateCompleted.getModel().getMonth());
        cal.set(Calendar.DATE, cboDateCompleted.getModel().getDay());

        return cal.getTime();
    }

    public boolean getCompleted() {
        return chkCompleted.isSelected();
    }

    private void chkCompletedActionPerformed() {
        if (chkCompleted.isSelected()) {
            cboDateCompleted.setEnabled(true);
        } else {
            cboDateCompleted.setEnabled(false);
            cboDateCompleted.getModel().setSelected(false);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        lblReqName = new JLabel();
        lblReqNameValue = new JLabel();
        chkCompleted = new JCheckBox();
        cboDateCompleted = new JDatePicker();
        lblDateCompletedError = new JLabel();
        JPanel buttonBar = new JPanel();
        JButton okButton = new JButton();
        JButton cancelButton = new JButton();

        //======== this ========
        setTitle("Edit Scout Advancement");
        setMinimumSize(new Dimension(500, 250));
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(Color.white);
            dialogPane.setName("dialogPane");
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(Color.white);
                contentPanel.setName("contentPanel");
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 35, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //---- lblReqName ----
                lblReqName.setText("Requirement Name:");
                lblReqName.setForeground(Color.black);
                lblReqName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblReqName.setName("lblReqName");
                contentPanel.add(lblReqName, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 5, 5), 0, 0));

                //---- lblReqNameValue ----
                lblReqNameValue.setText("Name");
                lblReqNameValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblReqNameValue.setForeground(new Color(0, 107, 63));
                lblReqNameValue.setName("lblReqNameValue");
                contentPanel.add(lblReqNameValue, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- chkCompleted ----
                chkCompleted.setText("Completed");
                chkCompleted.setFont(new Font("Tahoma", Font.PLAIN, 14));
                chkCompleted.setForeground(Color.black);
                chkCompleted.setOpaque(false);
                chkCompleted.setName("chkCompleted");
                chkCompleted.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chkCompletedActionPerformed();
                    }
                });
                contentPanel.add(chkCompleted, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- cboDateCompleted ----
                cboDateCompleted.setFont(new Font("Tahoma", Font.PLAIN, 14));
                cboDateCompleted.setForeground(Color.black);
                cboDateCompleted.setName("cboDateCompleted");
                contentPanel.add(cboDateCompleted, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblDateCompletedError ----
                lblDateCompletedError.setText("text");
                lblDateCompletedError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblDateCompletedError.setForeground(new Color(206, 17, 38));
                lblDateCompletedError.setName("lblDateCompletedError");
                contentPanel.add(lblDateCompletedError, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setBackground(Color.white);
                buttonBar.setName("buttonBar");
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {35};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
                okButton.setForeground(Color.white);
                okButton.setBackground(new Color(51, 156, 229));
                okButton.setFocusPainted(false);
                okButton.setName("okButton");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked();
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.setForeground(Color.white);
                cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
                cancelButton.setBackground(new Color(206, 17, 38));
                cancelButton.setFocusPainted(false);
                cancelButton.setName("cancelButton");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked();
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblReqName;
    private JLabel lblReqNameValue;
    private JCheckBox chkCompleted;
    private JDatePicker cboDateCompleted;
    private JLabel lblDateCompletedError;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
