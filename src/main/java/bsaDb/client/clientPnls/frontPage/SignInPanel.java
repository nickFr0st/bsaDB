/*
 * Created by JFormDesigner on Mon May 04 13:46:43 MDT 2015
 */

package bsaDb.client.clientPnls.frontPage;

import bsaDb.client.baseFrame.BaseFrame;
import bsaDb.client.customComponents.JPasswordFieldDefaultText;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import constants.KeyConst;
import objects.databaseObjects.User;
import util.CacheObject;
import util.MySqlConnector;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Nathanael
 */
public class SignInPanel extends JPanel {
    private static final String SIGN_IN = "SignIn";


    private Properties properties;
    private BaseFrame baseFrame;

    public SignInPanel() {
        initComponents();

        pnlCapsLock.setVisible(false);
        addSubmitListener();

        setupProperties();

        loadSavedUser();
    }

    private void addSubmitListener() {
        btnSignIn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), SIGN_IN);
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSignInActionPerformed();
            }
        };
        btnSignIn.getActionMap().put(SIGN_IN, action);
    }

    public SignInPanel(BaseFrame baseFrame) {
        this();
        this.baseFrame = baseFrame;
    }

    private void setupProperties() {
        properties = new Properties();
        try {
            properties.load(new FileReader(MySqlConnector.USER_PROPERTIES_PATH));
        } catch (IOException ignore) {
        }
    }

    private void loadSavedUser() {
        String savedUser = properties.getProperty(KeyConst.SAVED_USER.getName());
        txtUserName.requestFocus();
        if (!Util.isEmpty(savedUser)) {
            txtUserName.setTextColorToActive();
            txtUserName.setText(savedUser);
            chkRememberMe.setSelected(true);
        } else {
            txtUserName.setDefault();
            txtPassword.setDefault();
            chkRememberMe.setSelected(false);
        }
    }

    private void clearUserNameError() {
        lblUserNameError.setText("");
        lblUserNameError.setVisible(false);
    }

    private void clearPasswordError() {
        lblPasswordError.setText("");
        lblPasswordError.setVisible(false);
    }

    private boolean valuesExists() {
        User user = CacheObject.getUserByUserName(txtUserName.getText());

        if (user == null) {
            Util.setError(lblUserNameError, "User does not exists.");
            return false;
        }

        if (!user.getPassword().equals(txtPassword.getText())) {
            Util.setError(lblPasswordError, "Password does not match.");
            return false;
        }

        return true;
    }

    private boolean hasEmptyFields() {
        return isUserNameBlank() || isPasswordBlank();
    }

    private boolean isPasswordBlank() {
        if (Util.isEmpty(txtPassword)) {
            Util.setError(lblPasswordError, "Password cannot be left blank.");
            return true;
        }
        return false;
    }

    private boolean isUserNameBlank() {
        if (Util.isEmpty(txtUserName)) {
            Util.setError(lblUserNameError, "Username cannot be left blank.");
            return true;
        }
        return false;
    }

    private void btnSignInActionPerformed() {
        clearUserNameError();
        clearPasswordError();

        if (hasEmptyFields()) {
            return;
        }

        if (!valuesExists()) {
            return;
        }

        if (chkRememberMe.isSelected()) {
            properties.setProperty(KeyConst.SAVED_USER.getName(), txtUserName.getText());
        }

        properties.setProperty(KeyConst.CURRENT_USER.getName(), txtUserName.getText());
        Util.saveProperties(properties, MySqlConnector.USER_PROPERTIES_PATH);

        txtPassword.setDefault();

        baseFrame.addCard(BaseFrame.HOME_PAGE);
        baseFrame.slideCard(BaseFrame.HOME_PAGE);
    }

    private void txtUserNameFocusLost() {
        isUserNameBlank();
    }

    private void txtUserNameFocusGained() {
        clearUserNameError();
    }

    private void txtPasswordFocusLost() {
        isPasswordBlank();
    }

    private void txtPasswordFocusGained() {
        clearPasswordError();
        checkForCapsLock();
    }

    private void checkForCapsLock() {
        pnlCapsLock.setVisible(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK));
    }

    private void txtPasswordKeyReleased() {
        checkForCapsLock();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblTitle = new JLabel();
        panel1 = new JPanel();
        vSpacer1 = new JPanel(null);
        pnlCapsLock = new JPanel();
        lblCapsLock = new JLabel();
        txtUserName = new JTextFieldDefaultText();
        lblUserNameError = new JLabel();
        txtPassword = new JPasswordFieldDefaultText();
        lblPasswordError = new JLabel();
        chkRememberMe = new JCheckBox();
        btnSignIn = new JButton();

        //======== this ========
        setBackground(Color.white);
        setBorder(new LineBorder(new Color(51, 102, 153), 2));
        setName("this");
        setLayout(new BorderLayout());

        //---- lblTitle ----
        lblTitle.setIcon(new ImageIcon(getClass().getResource("/images/signInTitlebar.png")));
        lblTitle.setName("lblTitle");
        add(lblTitle, BorderLayout.NORTH);

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 358, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {98, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

            //---- vSpacer1 ----
            vSpacer1.setOpaque(false);
            vSpacer1.setName("vSpacer1");
            panel1.add(vSpacer1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 8), 0, 0));

            //======== pnlCapsLock ========
            {
                pnlCapsLock.setBackground(new Color(153, 102, 51));
                pnlCapsLock.setFont(pnlCapsLock.getFont().deriveFont(pnlCapsLock.getFont().getStyle() & ~Font.BOLD));
                pnlCapsLock.setName("pnlCapsLock");
                pnlCapsLock.setLayout(new GridBagLayout());
                ((GridBagLayout)pnlCapsLock.getLayout()).columnWidths = new int[] {350, 0};
                ((GridBagLayout)pnlCapsLock.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)pnlCapsLock.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                ((GridBagLayout)pnlCapsLock.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- lblCapsLock ----
                lblCapsLock.setText("CAPS LOCK IS ON");
                lblCapsLock.setForeground(Color.white);
                lblCapsLock.setBackground(new Color(153, 102, 0, 103));
                lblCapsLock.setHorizontalAlignment(SwingConstants.CENTER);
                lblCapsLock.setFont(new Font("Tahoma", Font.ITALIC, 11));
                lblCapsLock.setName("lblCapsLock");
                pnlCapsLock.add(lblCapsLock, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(3, 0, 3, 0), 0, 0));
            }
            panel1.add(pnlCapsLock, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 8), 0, 0));

            //---- txtUserName ----
            txtUserName.setPreferredSize(new Dimension(14, 40));
            txtUserName.setMinimumSize(new Dimension(14, 40));
            txtUserName.setMaximumSize(new Dimension(2147483647, 40));
            txtUserName.setDefaultText("Username");
            txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtUserName.setName("txtUserName");
            txtUserName.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    txtUserNameFocusGained();
                }
                @Override
                public void focusLost(FocusEvent e) {
                    txtUserNameFocusLost();
                }
            });
            panel1.add(txtUserName, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 8), 0, 0));

            //---- lblUserNameError ----
            lblUserNameError.setText("* Error Message");
            lblUserNameError.setForeground(new Color(206, 17, 38));
            lblUserNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblUserNameError.setVisible(false);
            lblUserNameError.setName("lblUserNameError");
            panel1.add(lblUserNameError, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 8), 0, 0));

            //---- txtPassword ----
            txtPassword.setPreferredSize(new Dimension(14, 40));
            txtPassword.setMinimumSize(new Dimension(14, 40));
            txtPassword.setMaximumSize(new Dimension(2147483647, 40));
            txtPassword.setDefaultText("Password");
            txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
            txtPassword.setName("txtPassword");
            txtPassword.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    txtPasswordFocusGained();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    txtPasswordFocusLost();
                }
            });
            txtPassword.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    txtPasswordKeyReleased();
                }
            });
            panel1.add(txtPassword, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 8), 0, 0));

            //---- lblPasswordError ----
            lblPasswordError.setText("* Error Message");
            lblPasswordError.setForeground(new Color(206, 17, 38));
            lblPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
            lblPasswordError.setVisible(false);
            lblPasswordError.setName("lblPasswordError");
            panel1.add(lblPasswordError, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 8), 0, 0));

            //---- chkRememberMe ----
            chkRememberMe.setText("Remember me");
            chkRememberMe.setFont(new Font("Tahoma", Font.PLAIN, 14));
            chkRememberMe.setForeground(Color.black);
            chkRememberMe.setBackground(Color.white);
            chkRememberMe.setName("chkRememberMe");
            panel1.add(chkRememberMe, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 8, 8), 0, 0));

            //---- btnSignIn ----
            btnSignIn.setText("Sign In");
            btnSignIn.setPreferredSize(new Dimension(90, 30));
            btnSignIn.setMinimumSize(new Dimension(80, 30));
            btnSignIn.setMaximumSize(new Dimension(90, 30));
            btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 14));
            btnSignIn.setMargin(new Insets(5, 20, 5, 20));
            btnSignIn.setBackground(new Color(0, 63, 135));
            btnSignIn.setForeground(Color.white);
            btnSignIn.setFocusPainted(false);
            btnSignIn.setName("btnSignIn");
            btnSignIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnSignInActionPerformed();
                }
            });
            panel1.add(btnSignIn, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 8, 8), 0, 0));
        }
        add(panel1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblTitle;
    private JPanel panel1;
    private JPanel vSpacer1;
    private JPanel pnlCapsLock;
    private JLabel lblCapsLock;
    private JTextFieldDefaultText txtUserName;
    private JLabel lblUserNameError;
    private JPasswordFieldDefaultText txtPassword;
    private JLabel lblPasswordError;
    private JCheckBox chkRememberMe;
    private JButton btnSignIn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
