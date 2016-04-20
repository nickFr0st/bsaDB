/*
 * Created by JFormDesigner on Sat Nov 15 15:13:17 MST 2014
 */

package bsaDb.client.home.dialogs;

import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.jdatepicker.DateModel;
import bsaDb.client.customComponents.jdatepicker.JDatePicker;
import objects.databaseObjects.SpecialAward;
import util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/**
 * @author User #2
 */
public class SpecialAwardDialog extends JDialog {
    public static final int BTN_OK = 1;

    private int btnChoice;
    private SpecialAward specialAward;

    {
        btnChoice = 0;
    }

    public SpecialAwardDialog(Frame owner, SpecialAward specialAward) {
        super(owner, true);
        initComponents();

        this.specialAward = specialAward;

        if (specialAward == null) {
            txtName.setDefault();
            txtDescription.setDefault();
            cboDateReceived.getModel().setSelected(false);
        } else {
            txtName.setText(specialAward.getName());
            txtDescription.setText(specialAward.getDescription());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(specialAward.getDateReceived());

            cboDateReceived.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
            cboDateReceived.getModel().setSelected(true);
        }

        clearErrors();
        setVisible(true);
    }

    public SpecialAward getSpecialAward() {
        return specialAward;
    }

    private void clearErrors() {
        Util.clearError(lblNameError);
        Util.clearError(lblDescriptionError);
        Util.clearError(lblDateReceivedError);
        revalidate();
    }

    private void cancelButtonMouseClicked() {
        dispose();
    }

    private void okButtonMouseClicked() {
        if (!validateFields()) return;

        if (specialAward == null) {
             specialAward = new SpecialAward();
        }

        specialAward.setName(txtName.getText());
        specialAward.setDescription(txtDescription.getText());

        Calendar calendar = Calendar.getInstance();
        DateModel<?> model = cboDateReceived.getModel();
        calendar.set(model.getYear(), model.getMonth(), model.getDay());
        specialAward.setDateReceived(calendar.getTime());

        btnChoice = BTN_OK;
        dispose();
    }

    private boolean validateFields() {
        clearErrors();
        boolean hasErrors = false;

        if (txtName.isMessageDefault()) {
            Util.setError(lblNameError, "cannot leave name empty");
            hasErrors = true;
        }

        if (txtDescription.isMessageDefault()) {
            Util.setError(lblDescriptionError, "cannot leave description empty");
            hasErrors = true;
        }

        if (!cboDateReceived.getModel().isSelected()) {
            Util.setError(lblDateReceivedError, "must specify a valid date");
            hasErrors = true;
        }

        return !hasErrors;
    }

    public int getBtnChoice() {
        return btnChoice;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblDescription = new JLabel();
        txtDescription = new JTextFieldDefaultText();
        lblDescriptionError = new JLabel();
        lblDateReceived = new JLabel();
        cboDateReceived = new JDatePicker();
        lblDateReceivedError = new JLabel();
        JPanel buttonBar = new JPanel();
        JButton okButton = new JButton();
        JButton cancelButton = new JButton();

        //======== this ========
        setTitle("Special Award");
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
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {35, 0, 35, 0, 35, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- lblName ----
                lblName.setText("Name:");
                lblName.setForeground(Color.black);
                lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblName.setName("lblName");
                contentPanel.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 5, 5), 0, 0));

                //---- txtName ----
                txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtName.setDefaultText("Name");
                txtName.setName("txtName");
                contentPanel.add(txtName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblNameError ----
                lblNameError.setText("text");
                lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblNameError.setForeground(new Color(206, 17, 38));
                lblNameError.setName("lblNameError");
                contentPanel.add(lblNameError, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblDescription ----
                lblDescription.setText("Description:");
                lblDescription.setForeground(Color.black);
                lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblDescription.setName("lblDescription");
                contentPanel.add(lblDescription, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 5, 5), 0, 0));

                //---- txtDescription ----
                txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtDescription.setDefaultText("Description");
                txtDescription.setName("txtDescription");
                contentPanel.add(txtDescription, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblDescriptionError ----
                lblDescriptionError.setText("text");
                lblDescriptionError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblDescriptionError.setForeground(new Color(206, 17, 38));
                lblDescriptionError.setName("lblDescriptionError");
                contentPanel.add(lblDescriptionError, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblDateReceived ----
                lblDateReceived.setText("Date Received:");
                lblDateReceived.setForeground(Color.black);
                lblDateReceived.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblDateReceived.setName("lblDateReceived");
                contentPanel.add(lblDateReceived, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 5, 5), 0, 0));

                //---- cboDateReceived ----
                cboDateReceived.setFont(new Font("Tahoma", Font.PLAIN, 14));
                cboDateReceived.setForeground(Color.black);
                cboDateReceived.setName("cboDateReceived");
                contentPanel.add(cboDateReceived, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblDateReceivedError ----
                lblDateReceivedError.setText("text");
                lblDateReceivedError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblDateReceivedError.setForeground(new Color(206, 17, 38));
                lblDateReceivedError.setName("lblDateReceivedError");
                contentPanel.add(lblDateReceivedError, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
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
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblNameError;
    private JLabel lblDescription;
    private JTextFieldDefaultText txtDescription;
    private JLabel lblDescriptionError;
    private JLabel lblDateReceived;
    private JDatePicker cboDateReceived;
    private JLabel lblDateReceivedError;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
