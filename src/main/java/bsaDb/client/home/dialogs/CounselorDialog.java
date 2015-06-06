/*
 * Created by JFormDesigner on Sat Nov 15 15:13:17 MST 2014
 */

package bsaDb.client.home.dialogs;

import bsaDb.client.customComponents.JTextFieldDefaultText;
import objects.databaseObjects.Counselor;
import util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author User #2
 */
public class CounselorDialog extends JDialog {
    public static final int BTN_OK = 1;

    private Counselor counselor;
    private int btnChoice;

    {
        btnChoice = 0;
    }

    public CounselorDialog(Frame owner) {
        super(owner, true);
        initComponents();

        clearErrors();
    }

    private void clearErrors() {
        lblNameError.setText("");
        lblNameError.setVisible(false);

        lblPhoneError.setText("");
        lblPhoneError.setVisible(false);

        revalidate();
    }

    private void cancelButtonMouseClicked() {
        counselor = null;
        dispose();
    }

    private void okButtonMouseClicked() {
        if (!validateFields()) return;

        counselor = new Counselor();
        counselor.setPhoneNumber(txtPhone.getText());
        counselor.setName(txtName.getText());

        btnChoice = BTN_OK;
        dispose();
    }

    private boolean validateFields() {
        clearErrors();
        boolean hasErrors = false;

        if (txtName.isMessageDefault()) {
            Util.setError(lblNameError, "Cannot leave name blank");
            hasErrors = true;
        }

        if (txtPhone.isMessageDefault()) {
            Util.setError(lblPhoneError, "Cannot leave phone number blank");
            hasErrors = true;
        } else if (!Util.validatePhoneNumber(txtPhone.getText())) {
            Util.setError(lblPhoneError, "invalid phone number format");
            hasErrors = true;
        }

        return !hasErrors;
    }

    public int getBtnChoice() {
        return btnChoice;
    }

    public Counselor getCounselor() {
        return counselor;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblPhone = new JLabel();
        txtPhone = new JTextFieldDefaultText();
        lblPhoneError = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("Counselor Creation");
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
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- lblName ----
                lblName.setText("Name:");
                lblName.setForeground(new Color(51, 102, 153));
                lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblName.setName("lblName");
                contentPanel.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- txtName ----
                txtName.setPreferredSize(new Dimension(14, 35));
                txtName.setDefaultText("Name");
                txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtName.setName("txtName");
                contentPanel.add(txtName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblNameError ----
                lblNameError.setText("text");
                lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblNameError.setForeground(Color.red);
                lblNameError.setName("lblNameError");
                contentPanel.add(lblNameError, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblPhone ----
                lblPhone.setText("Phone Number:");
                lblPhone.setForeground(new Color(51, 102, 153));
                lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblPhone.setName("lblPhone");
                contentPanel.add(lblPhone, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- txtPhone ----
                txtPhone.setPreferredSize(new Dimension(14, 35));
                txtPhone.setDefaultText("123-456-7890");
                txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtPhone.setName("txtPhone");
                contentPanel.add(txtPhone, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- lblPhoneError ----
                lblPhoneError.setText("text");
                lblPhoneError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblPhoneError.setForeground(Color.red);
                lblPhoneError.setName("lblPhoneError");
                contentPanel.add(lblPhoneError, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
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
                cancelButton.setBackground(new Color(206, 0, 0));
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
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblNameError;
    private JLabel lblPhone;
    private JTextFieldDefaultText txtPhone;
    private JLabel lblPhoneError;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
