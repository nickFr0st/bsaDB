/*
 * Created by JFormDesigner on Fri May 08 20:05:36 MDT 2015
 */

package bsaDb.client.clientPnls.database;

import bsaDb.client.baseFrame.BaseFrame;
import bsaDb.client.customComponents.JPasswordFieldDefaultText;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.TitlePanel;
import constants.KeyConst;
import util.CacheObject;
import util.MySqlConnector;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

/**
 * @author Nathanael
 */
public class DatabaseSettingsPanel extends JPanel {

    private BaseFrame baseFrame;

    public DatabaseSettingsPanel() {
        initComponents();
    }

    public DatabaseSettingsPanel(BaseFrame baseFrame, boolean showTitle) {
        this();
        loadData();
        this.baseFrame = baseFrame;
        pnlTitle.setVisible(showTitle);
    }

    private void loadData() {
        clearData();

        Properties dbProperties = MySqlConnector.getInstance().getProperties();

        lblConnectionName.setText(dbProperties.getProperty(KeyConst.DB_NAME.getName()));
        lblConnectionName.setForeground(new Color(32, 154, 26));

        txtDatabaseName.setText(dbProperties.getProperty(KeyConst.DB_NAME.getName()));
        txtServerUserName.setText(dbProperties.getProperty(KeyConst.DB_USER_NAME.getName()));
        txtServerPassword.loadText(dbProperties.getProperty(KeyConst.DB_PASSWORD.getName()));
    }

    private void clearData() {
        lblConnectionName.setText("None");
        lblConnectionName.setForeground(Color.RED);

        txtDatabaseName.setDefault();
        txtServerUserName.setDefault();
        txtServerPassword.setDefault();

        Util.clearError(lblDatabaseNameError);
        Util.clearError(lblUserNameError);
        Util.clearError(lblPasswordError);
    }

    private boolean validateDbName() {
        Util.clearError(lblDatabaseNameError);

        if (Util.isEmpty(txtDatabaseName.getText()) || txtDatabaseName.isMessageDefault()) {
            Util.setError(lblDatabaseNameError, "Database name cannot be left blank");
            return false;
        }

        return true;
    }

    private boolean validateUserName() {
        Util.clearError(lblUserNameError);

        if (Util.isEmpty(txtServerUserName.getText()) || txtServerUserName.isMessageDefault()) {
            Util.setError(lblUserNameError, "Server username cannot be left blank");
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
        Util.clearError(lblPasswordError);

        if (Util.isEmpty(txtServerPassword.getText()) || txtServerPassword.isMessageDefault()) {
            Util.setError(lblPasswordError, "Server password cannot be left blank");
            return false;
        }

        return true;
    }

    private boolean validateData() {
        boolean isValid = true;

        if (!validateDbName()) {
            isValid = false;
        }

        if (!validateUserName()) {
            isValid = false;
        }

        if (!validatePassword()) {
            isValid = false;
        }

        return isValid;
    }

    private void validateDatabaseName() {
        validateDbName();
    }

    private void validateServerUserName() {
        validateUserName();
    }

    private void validateServerPassword() {
        validatePassword();
    }

    private void btnCreateActionPerformed() {
        if (!validateData()) {
            return;
        }

        if (!MySqlConnector.getInstance().createDatabase((JFrame) SwingUtilities.getWindowAncestor(this), txtDatabaseName.getText(), txtServerUserName.getText(), txtServerPassword.getText())) {
            return;
        }

        lblConnectionName.setText(txtDatabaseName.getText());
        lblConnectionName.setForeground(new Color(32, 154, 26));

        moveToSignInPage();
    }

    private void btnConnectActionPerformed() {
        if (!validateData()) {
            return;
        }

        if (lblConnectionName.getText().equals(txtDatabaseName.getText())) {
            return;
        }

        if (!MySqlConnector.getInstance().connectToDatabase((JFrame) SwingUtilities.getWindowAncestor(this), txtDatabaseName.getText(), txtServerUserName.getText(), txtServerPassword.getText())) {
            return;
        }

        lblConnectionName.setText(txtDatabaseName.getText());
        lblConnectionName.setForeground(new Color(32, 154, 26));

        moveToSignInPage();
    }

    private void moveToSignInPage() {
        if (baseFrame != null) {
            CacheObject.reset();
            baseFrame.clearHomePnl();
            baseFrame.slideCard(BaseFrame.SIGN_IN_PAGE);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlTitle = new TitlePanel();
        JPanel panel1 = new JPanel();
        JLabel lblConnection = new JLabel();
        lblConnectionName = new JLabel();
        JPanel panel2 = new JPanel();
        label1 = new JLabel();
        txtDatabaseName = new JTextFieldDefaultText();
        lblDatabaseNameError = new JLabel();
        label2 = new JLabel();
        txtServerUserName = new JTextFieldDefaultText();
        lblUserNameError = new JLabel();
        label3 = new JLabel();
        txtServerPassword = new JPasswordFieldDefaultText();
        lblPasswordError = new JLabel();
        JButton btnCreate = new JButton();
        JButton btnConnect = new JButton();
        panel3 = new JPanel();
        textArea1 = new JTextArea();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //---- pnlTitle ----
        pnlTitle.setTitle("Database Settings");
        pnlTitle.setName("pnlTitle");
        add(pnlTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- lblConnection ----
            lblConnection.setText("Current database connection:");
            lblConnection.setFont(new Font("Vijaya", Font.PLAIN, 22));
            lblConnection.setForeground(new Color(0, 63, 135));
            lblConnection.setName("lblConnection");
            panel1.add(lblConnection, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- lblConnectionName ----
            lblConnectionName.setFont(new Font("Vijaya", Font.PLAIN, 22));
            lblConnectionName.setForeground(new Color(0, 107, 63));
            lblConnectionName.setName("lblConnectionName");
            panel1.add(lblConnectionName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== panel2 ========
            {
                panel2.setOpaque(false);
                panel2.setName("panel2");
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 132, 113, 136, 0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("Database Name:");
                label1.setForeground(Color.black);
                label1.setFont(new Font("Tahoma", Font.PLAIN, 14));
                label1.setName("label1");
                panel2.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 8, 10), 0, 0));

                //---- txtDatabaseName ----
                txtDatabaseName.setPreferredSize(new Dimension(14, 40));
                txtDatabaseName.setMinimumSize(new Dimension(14, 40));
                txtDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtDatabaseName.setDefaultText("Database Name");
                txtDatabaseName.setName("txtDatabaseName");
                txtDatabaseName.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        validateDatabaseName();
                    }
                });
                txtDatabaseName.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        validateDatabaseName();
                    }
                });
                panel2.add(txtDatabaseName, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 5), 0, 0));

                //---- lblDatabaseNameError ----
                lblDatabaseNameError.setText("* Error Message");
                lblDatabaseNameError.setForeground(new Color(206, 17, 38));
                lblDatabaseNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblDatabaseNameError.setVisible(false);
                lblDatabaseNameError.setName("lblDatabaseNameError");
                panel2.add(lblDatabaseNameError, new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 10, 8, 0), 0, 0));

                //---- label2 ----
                label2.setText("Username:");
                label2.setForeground(Color.black);
                label2.setFont(new Font("Tahoma", Font.PLAIN, 14));
                label2.setName("label2");
                panel2.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 5, 8, 10), 0, 0));

                //---- txtServerUserName ----
                txtServerUserName.setMinimumSize(new Dimension(14, 40));
                txtServerUserName.setPreferredSize(new Dimension(14, 40));
                txtServerUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtServerUserName.setDefaultText("MySQL server username");
                txtServerUserName.setName("txtServerUserName");
                txtServerUserName.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        validateServerUserName();
                    }
                });
                txtServerUserName.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        validateServerUserName();
                    }
                });
                panel2.add(txtServerUserName, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 8, 5), 0, 0));

                //---- lblUserNameError ----
                lblUserNameError.setText("* Error Message");
                lblUserNameError.setForeground(new Color(206, 17, 38));
                lblUserNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblUserNameError.setVisible(false);
                lblUserNameError.setName("lblUserNameError");
                panel2.add(lblUserNameError, new GridBagConstraints(1, 3, 4, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 10, 8, 0), 0, 0));

                //---- label3 ----
                label3.setText("Password:");
                label3.setForeground(Color.black);
                label3.setFont(new Font("Tahoma", Font.PLAIN, 14));
                label3.setName("label3");
                panel2.add(label3, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 5, 8, 10), 0, 0));

                //---- txtServerPassword ----
                txtServerPassword.setPreferredSize(new Dimension(14, 40));
                txtServerPassword.setMinimumSize(new Dimension(14, 40));
                txtServerPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtServerPassword.setDefaultText("MySQL server password");
                txtServerPassword.setName("txtServerPassword");
                txtServerPassword.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        validateServerPassword();
                    }
                });
                txtServerPassword.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        validateServerPassword();
                    }
                });
                panel2.add(txtServerPassword, new GridBagConstraints(1, 4, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 8, 5), 0, 0));

                //---- lblPasswordError ----
                lblPasswordError.setText("* Error Message");
                lblPasswordError.setForeground(new Color(206, 17, 38));
                lblPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblPasswordError.setVisible(false);
                lblPasswordError.setName("lblPasswordError");
                panel2.add(lblPasswordError, new GridBagConstraints(1, 5, 4, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 10, 8, 0), 0, 0));

                //---- btnCreate ----
                btnCreate.setText("Create");
                btnCreate.setPreferredSize(new Dimension(90, 30));
                btnCreate.setMinimumSize(new Dimension(80, 30));
                btnCreate.setMaximumSize(new Dimension(90, 30));
                btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnCreate.setMargin(new Insets(5, 20, 5, 20));
                btnCreate.setBackground(new Color(0, 63, 135));
                btnCreate.setForeground(Color.white);
                btnCreate.setFocusPainted(false);
                btnCreate.setName("btnCreate");
                btnCreate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnCreateActionPerformed();
                    }
                });
                panel2.add(btnCreate, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 5, 8, 5), 0, 0));

                //---- btnConnect ----
                btnConnect.setText("Connect");
                btnConnect.setPreferredSize(new Dimension(90, 30));
                btnConnect.setMinimumSize(new Dimension(80, 30));
                btnConnect.setMaximumSize(new Dimension(90, 30));
                btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 14));
                btnConnect.setMargin(new Insets(5, 20, 5, 20));
                btnConnect.setBackground(new Color(0, 63, 135));
                btnConnect.setForeground(Color.white);
                btnConnect.setFocusPainted(false);
                btnConnect.setName("btnConnect");
                btnConnect.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnConnectActionPerformed();
                    }
                });
                panel2.add(btnConnect, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 20, 8, 5), 0, 0));

                //======== panel3 ========
                {
                    panel3.setBackground(new Color(152, 178, 204));
                    panel3.setName("panel3");
                    panel3.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
                    ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                    ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                    //---- textArea1 ----
                    textArea1.setText("On database creation a default user will be added.   \nThe default user credentials are:   \nusername: admin   \npassword: admin");
                    textArea1.setEditable(false);
                    textArea1.setWrapStyleWord(true);
                    textArea1.setLineWrap(true);
                    textArea1.setForeground(Color.black);
                    textArea1.setBackground(new Color(153, 204, 255));
                    textArea1.setOpaque(false);
                    textArea1.setFont(new Font("Monospaced", Font.PLAIN, 13));
                    textArea1.setName("textArea1");
                    panel3.add(textArea1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 10, 5, 10), 0, 0));
                }
                panel2.add(panel3, new GridBagConstraints(0, 7, 4, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(10, 5, 0, 5), 0, 0));
            }
            panel1.add(panel2, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 10, 5, 10), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private TitlePanel pnlTitle;
    private JLabel lblConnectionName;
    private JLabel label1;
    private JTextFieldDefaultText txtDatabaseName;
    private JLabel lblDatabaseNameError;
    private JLabel label2;
    private JTextFieldDefaultText txtServerUserName;
    private JLabel lblUserNameError;
    private JLabel label3;
    private JPasswordFieldDefaultText txtServerPassword;
    private JLabel lblPasswordError;
    private JPanel panel3;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
