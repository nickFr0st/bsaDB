/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.JPasswordFieldDefaultText;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.TitlePanel;
import bsaDb.client.home.clientPnls.common.AccessRightsPanel;
import bsaDb.client.home.dialogs.MessageDialog;
import constants.KeyConst;
import objects.databaseObjects.AccessRight;
import objects.databaseObjects.User;
import objects.objectLogic.LogicAccessRight;
import objects.objectLogic.LogicUser;
import util.CacheObject;
import util.MySqlConnector;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @author User #2
 */
public class UserPanel extends JPanel {

    private User user;
    private Properties properties;

    public UserPanel() {
        initComponents();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        populateUserNameList();
        setupProperties();

        enableControls(false);
    }

    private void setupProperties() {
        properties = new Properties();
        try {
            properties.load(new FileReader(MySqlConnector.USER_PROPERTIES_PATH));
        } catch (IOException ignore) {
        }
    }

    public void populateUserNameList() {
        Collection<User> userList = CacheObject.getUserList();
        List<String> userNameList = new ArrayList<>();
        for (User user : userList) {
            userNameList.add(user.getUserName());
        }

        listUserNames.setListData(Util.getSortedList(userNameList));
        listUserNames.revalidate();
    }

    private void txtSearchNameKeyReleased() {
        // instead of the logic get this from a cached list
        Collection<User> userList = CacheObject.getUserList();
        List<String> userNameList = new ArrayList<>();
        for (User user : userList) {
            userNameList.add(user.getUserName());
        }

        if (txtSearchName.isMessageDefault()) {
            listUserNames.setListData(Util.getSortedList(userNameList));
            listUserNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<>();
        for (User user : userList) {
            if (user.getUserName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(user.getUserName());
            }
        }

        listUserNames.setListData(Util.getSortedList(filteredList));
        listUserNames.revalidate();
    }

    private void listUserNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listUserNamesMouseReleased();
        }
    }

    private void listUserNamesMouseReleased() {
        if (listUserNames.getSelectedValue() == null) {
            return;
        }

        clearAllErrors();
        clearData();

        user = CacheObject.getUserByUserName(listUserNames.getSelectedValue().toString());
        loadData();
    }

    private void loadData() {
        if (user == null) {
            return;
        }

        enableControls(true);

        txtUsername.setText(user.getUserName());
        txtName.setText(user.getName());
        txtPassword.loadText(user.getPassword());

        if (!Util.isEmpty(user.getPosition())) {
            txtPosition.setText(user.getPosition());
        }

        if (!Util.isEmpty(user.getPhoneNumber())) {
            txtPhoneNumber.setText(user.getPhoneNumber());
        }

        if (!Util.isEmpty(user.getEmail())) {
            txtEmail.setText(user.getEmail());
        }

        if (!Util.isEmpty(user.getStreet())) {
            txtStreet.setText(user.getStreet());
        }

        if (!Util.isEmpty(user.getCity())) {
            txtCity.setText(user.getCity());
        }

        if (!Util.isEmpty(user.getZip())) {
            txtZip.setText(user.getZip());
        }

        pnlAccessRights.populateRights(user);

        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnSave.setVisible(false);

        handleAdmin();
    }

    private void enableControls(boolean enable) {
        txtUsername.setEnabled(enable);
        txtName.setEnabled(enable);
        txtPassword.setEnabled(enable);
        txtPosition.setEnabled(enable);
        txtPhoneNumber.setEnabled(enable);
        txtEmail.setEnabled(enable);
        txtState.setEnabled(enable);
        txtCity.setEnabled(enable);
        txtStreet.setEnabled(enable);
        txtZip.setEnabled(enable);
        pnlAccessRights.setEnabled(enable);
    }

    private void handleAdmin() {
        if (user.getId() != 1) {
            if (user.getUserName().equals(properties.getProperty(KeyConst.CURRENT_USER.getName()))) {
                txtUsername.setEnabled(false);
            }
            return;
        }

        txtUsername.setEnabled(false);
        pnlAccessRights.setEnabled(false);
        btnDelete.setVisible(false);
    }

    private void clearAllErrors() {
        Util.clearError(lblUsernameError);
        Util.clearError(lblNameError);
        Util.clearError(lblPasswordError);
        Util.clearError(lblPhoneNumberError);
        Util.clearError(lblEmailError);
    }

    private void validateUName() {
        validateUserUserName();
    }

    private void validateName() {
        validateUserName();
    }

    private boolean validateUserName() {
        Util.clearError(lblNameError);

        if (txtName.isMessageDefault() || txtName.getText().trim().isEmpty()) {
            Util.setError(lblNameError, "Name cannot be left blank");
            return false;
        }

        return true;
    }

    private boolean validateUserUserName() {
        Util.clearError(lblUsernameError);

        if (txtUsername.isMessageDefault() || txtUsername.getText().trim().isEmpty()) {
            Util.setError(lblUsernameError, "Username cannot be left blank");
            return false;
        }

        User tempUser = CacheObject.getUserByUserName(txtUsername.getText());
        if (tempUser == null) {
            return true;
        }

        if (user == null) {
            Util.setError(lblUsernameError, "The username already exists");
            return false;
        }

        if (!tempUser.getUserName().equals(user.getUserName())) {
            Util.setError(lblUsernameError, "The username already exists");
            return false;
        }

        return true;
    }

    private void validatePassword() {
        validateUserPassword();
    }

    private boolean validateUserPassword() {
        Util.clearError(lblPasswordError);

        if (txtPassword.isMessageDefault() || txtPassword.getText().isEmpty()) {
            Util.setError(lblPasswordError, "Password cannot be left blank");
            return false;
        }

        return true;
    }

    private void validatePhoneNumber() {
        validatePhoneNum();
    }

    private boolean validatePhoneNum() {
        Util.clearError(lblPhoneNumberError);

        if (txtPhoneNumber.isMessageDefault() || txtPhoneNumber.getText().isEmpty()) {
            return true;
        }

        if (!Util.validatePhoneNumber(txtPhoneNumber.getText())) {
            Util.setError(lblPhoneNumberError, "Invalid phone number format");
            return false;
        }

        return true;
    }

    private void validateEmail() {
        validateUserEmail();
    }

    private boolean validateUserEmail() {
        Util.clearError(lblEmailError);

        if (txtEmail.isMessageDefault() || txtEmail.getText().isEmpty()) {
            return true;
        }

        if (!Util.validateEmail(txtEmail.getText())) {
            Util.setError(lblEmailError, "Invalid email format");
            return false;
        }

        return true;
    }

    private void btnNewActionPerformed() {
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        enableControls(true);
        clearAllErrors();
        clearData();
    }

    private void clearData() {
        user = null;

        txtUsername.setDefault();
        txtName.setDefault();
        txtPassword.setDefault();
        txtPosition.setDefault();
        txtPhoneNumber.setDefault();
        txtEmail.setDefault();
        txtState.setDefault();
        txtCity.setDefault();
        txtStreet.setDefault();
        txtZip.setDefault();
        pnlAccessRights.populateRights(null);
    }

    private void btnSaveActionPerformed() {
        if (!validateFields()) {
            return;
        }

        setData();
        user = LogicUser.save(user);

        for (Integer accessRightId : pnlAccessRights.getAccessRightIdList()) {
            AccessRight accessRight = new AccessRight();
            accessRight.setUserId(user.getId());
            accessRight.setRightId(accessRightId);

            accessRight = LogicAccessRight.save(accessRight);
            CacheObject.addToAccessRights(accessRight);
        }

        btnSave.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        CacheObject.addToUsers(user);
        populateUserNameList();
    }

    private void setData() {
        if (user == null) {
            user = new User();
        }

        user.setUserName(txtUsername.getText());
        user.setName(txtName.getText());
        user.setPassword(txtPassword.getText());

        if (!txtPosition.getText().trim().isEmpty() && !txtPosition.isMessageDefault()) {
            user.setPosition(txtPosition.getText());
        }

        if (!txtPhoneNumber.getText().trim().isEmpty() && !txtPhoneNumber.isMessageDefault()) {
            user.setPhoneNumber(txtPhoneNumber.getText());
        }

        if (!txtEmail.getText().trim().isEmpty() && !txtEmail.isMessageDefault()) {
            user.setEmail(txtEmail.getText());
        }

        if (!txtStreet.getText().trim().isEmpty() && !txtStreet.isMessageDefault()) {
            user.setStreet(txtStreet.getText());
        }

        if (!txtCity.getText().trim().isEmpty() && !txtCity.isMessageDefault()) {
            user.setCity(txtCity.getText());
        }

        if (!txtState.getText().trim().isEmpty() && !txtState.isMessageDefault()) {
            user.setState(txtState.getText());
        }

        if (!txtZip.getText().trim().isEmpty() && !txtZip.isMessageDefault()) {
            user.setZip(txtZip.getText());
        }
    }

    private boolean validateFields() {
        boolean valid = true;

        if (!validateUserUserName()) {
            valid = false;
        }

        if (!validateUserName()) {
            valid = false;
        }

        if (!validateUserPassword()) {
            valid = false;
        }

        if (!validatePhoneNum()) {
            valid = false;
        }

        if (!validateUserEmail()) {
            valid = false;
        }

        return valid;
    }

    private void btnUpdateActionPerformed() {
        if (!validateFields()) {
            return;
        }

        setData();
        user = LogicUser.update(user);

        List<AccessRight> currentAccessRightList = CacheObject.getAccessRights(user.getId());
        List<Integer> newAccessRightIdList = pnlAccessRights.getAccessRightIdList();
        List<Integer> deleteIdList = new ArrayList<>();

        if (newAccessRightIdList.isEmpty()) {
            for (AccessRight accessRight : currentAccessRightList) {
                deleteIdList.add(accessRight.getId());
            }
        } else {
            for (AccessRight accessRight : currentAccessRightList) {
                if (!newAccessRightIdList.contains(accessRight.getRightId())) {
                    deleteIdList.add(accessRight.getId());
                }
            }
        }

        for (Integer deleteId :  deleteIdList) {
            LogicAccessRight.delete(deleteId);
            CacheObject.removeFromAccessRights(deleteId);
        }

        for (Integer accessRightId : newAccessRightIdList) {
            AccessRight accessRight = new AccessRight();
            accessRight.setUserId(user.getId());
            accessRight.setRightId(accessRightId);

            accessRight = LogicAccessRight.save(accessRight);
            CacheObject.addToAccessRights(accessRight);
        }

        CacheObject.addToUsers(user);
        populateUserNameList();
    }

    private void btnDeleteActionPerformed() {
        if (user.getUserName().equals(properties.getProperty(KeyConst.CURRENT_USER.getName()))) {
            new MessageDialog(Util.getParent(this), "Delete Error", "You cannot delete the currently logged in user.", MessageDialog.MessageType.ERROR, MessageDialog.ButtonType.OKAY);
            return;
        }

        for (AccessRight accessRight : CacheObject.getAccessRights(user.getId())) {
            CacheObject.removeFromAccessRights(accessRight.getId());
            LogicAccessRight.delete(accessRight.getId());
        }

        CacheObject.removeFromUsers(user.getId());
        LogicUser.delete(user);

        populateUserNameList();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        clearAllErrors();
        clearData();
        enableControls(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        TitlePanel pnlTitle = new TitlePanel();
        JPanel pnlContents = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        JScrollPane scrollPane1 = new JScrollPane();
        listUserNames = new JList();
        JPanel panel3 = new JPanel();
        JScrollPane scrollPane2 = new JScrollPane();
        JPanel panel4 = new JPanel();
        JLabel lblGeneralInfo = new JLabel();
        JLabel lblUsername = new JLabel();
        txtUsername = new JTextFieldDefaultText();
        JLabel lblPassword = new JLabel();
        txtPassword = new JPasswordFieldDefaultText();
        lblUsernameError = new JLabel();
        lblPasswordError = new JLabel();
        JLabel lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        JLabel lblPosition = new JLabel();
        txtPosition = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        JLabel lblContactInfo = new JLabel();
        JLabel lblPhoneNumber = new JLabel();
        txtPhoneNumber = new JTextFieldDefaultText();
        JLabel lblEmail = new JLabel();
        txtEmail = new JTextFieldDefaultText();
        lblPhoneNumberError = new JLabel();
        lblEmailError = new JLabel();
        JLabel lblCity = new JLabel();
        txtCity = new JTextFieldDefaultText();
        JLabel lblCity2 = new JLabel();
        txtState = new JTextFieldDefaultText();
        JLabel lblStreet = new JLabel();
        txtStreet = new JTextFieldDefaultText();
        JLabel lblZip = new JLabel();
        txtZip = new JTextFieldDefaultText();
        pnlAccessRights = new AccessRightsPanel();
        JPanel panel5 = new JPanel();
        JButton btnNew = new JButton();
        btnSave = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //---- pnlTitle ----
        pnlTitle.setTitle("Users");
        pnlTitle.setName("pnlTitle");
        add(pnlTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== pnlContents ========
        {
            pnlContents.setOpaque(false);
            pnlContents.setName("pnlContents");
            pnlContents.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlContents.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)pnlContents.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlContents.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlContents.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //======== panel2 ========
            {
                panel2.setOpaque(false);
                panel2.setName("panel2");
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                //======== pnlSearch ========
                {
                    pnlSearch.setBorder(LineBorder.createBlackLineBorder());
                    pnlSearch.setOpaque(false);
                    pnlSearch.setName("pnlSearch");
                    pnlSearch.setLayout(new GridBagLayout());
                    ((GridBagLayout)pnlSearch.getLayout()).columnWidths = new int[] {188, 0};
                    ((GridBagLayout)pnlSearch.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)pnlSearch.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                    ((GridBagLayout)pnlSearch.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                    //---- txtSearchName ----
                    txtSearchName.setDefaultText("Search by Name");
                    txtSearchName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    txtSearchName.setBorder(null);
                    txtSearchName.setPreferredSize(new Dimension(101, 20));
                    txtSearchName.setMinimumSize(new Dimension(0, 20));
                    txtSearchName.setName("txtSearchName");
                    txtSearchName.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtSearchNameKeyReleased();
                        }
                    });
                    pnlSearch.add(txtSearchName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 5, 0, 0), 0, 0));
                }
                panel2.add(pnlSearch, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 0), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    scrollPane1.setName("scrollPane1");

                    //---- listUserNames ----
                    listUserNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listUserNames.setName("listUserNames");
                    listUserNames.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            listUserNamesKeyReleased(e);
                        }
                    });
                    listUserNames.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            listUserNamesMouseReleased();
                        }
                    });
                    scrollPane1.setViewportView(listUserNames);
                }
                panel2.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            pnlContents.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 15), 0, 0));

            //======== panel3 ========
            {
                panel3.setOpaque(false);
                panel3.setName("panel3");
                panel3.setLayout(new GridBagLayout());
                ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                //======== scrollPane2 ========
                {
                    scrollPane2.setName("scrollPane2");

                    //======== panel4 ========
                    {
                        panel4.setBackground(Color.white);
                        panel4.setName("panel4");
                        panel4.setLayout(new GridBagLayout());
                        ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 188, 0, 155, 0, 0};
                        ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 40, 0, 40, 0, 0, 40, 0, 40, 40, 238, 0, 0};
                        ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                        ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                        //---- lblGeneralInfo ----
                        lblGeneralInfo.setText("General Information:");
                        lblGeneralInfo.setFont(new Font("Vijaya", Font.PLAIN, 22));
                        lblGeneralInfo.setForeground(new Color(51, 102, 153));
                        lblGeneralInfo.setName("lblGeneralInfo");
                        panel4.add(lblGeneralInfo, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(10, 8, 10, 5), 0, 0));

                        //---- lblUsername ----
                        lblUsername.setText("Username:");
                        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblUsername.setForeground(Color.black);
                        lblUsername.setName("lblUsername");
                        panel4.add(lblUsername, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtUsername ----
                        txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtUsername.setDefaultText("john");
                        txtUsername.setName("txtUsername");
                        txtUsername.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                validateUName();
                            }
                        });
                        txtUsername.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                validateUName();
                            }
                        });
                        panel4.add(txtUsername, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblPassword ----
                        lblPassword.setText("Password:");
                        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblPassword.setForeground(Color.black);
                        lblPassword.setName("lblPassword");
                        panel4.add(lblPassword, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 5, 10, 5), 0, 0));

                        //---- txtPassword ----
                        txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtPassword.setDefaultText("Password");
                        txtPassword.setName("txtPassword");
                        txtPassword.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                validatePassword();
                            }
                        });
                        txtPassword.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                validatePassword();
                            }
                        });
                        panel4.add(txtPassword, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblUsernameError ----
                        lblUsernameError.setText("* Error Message");
                        lblUsernameError.setForeground(Color.red);
                        lblUsernameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblUsernameError.setVisible(false);
                        lblUsernameError.setName("lblUsernameError");
                        panel4.add(lblUsernameError, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 25), 0, 0));

                        //---- lblPasswordError ----
                        lblPasswordError.setText("* Error Message");
                        lblPasswordError.setForeground(Color.red);
                        lblPasswordError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblPasswordError.setVisible(false);
                        lblPasswordError.setName("lblPasswordError");
                        panel4.add(lblPasswordError, new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 0), 0, 0));

                        //---- lblName ----
                        lblName.setText("Name:");
                        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblName.setForeground(Color.black);
                        lblName.setName("lblName");
                        panel4.add(lblName, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtName ----
                        txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtName.setDefaultText("John Doe");
                        txtName.setName("txtName");
                        txtName.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                validateName();
                            }
                        });
                        txtName.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                validateName();
                            }
                        });
                        panel4.add(txtName, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblPosition ----
                        lblPosition.setText("Position:");
                        lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblPosition.setForeground(Color.black);
                        lblPosition.setName("lblPosition");
                        panel4.add(lblPosition, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 5, 10, 5), 0, 0));

                        //---- txtPosition ----
                        txtPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtPosition.setDefaultText("Scout Master");
                        txtPosition.setName("txtPosition");
                        panel4.add(txtPosition, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblNameError ----
                        lblNameError.setText("* Error Message");
                        lblNameError.setForeground(Color.red);
                        lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblNameError.setVisible(false);
                        lblNameError.setName("lblNameError");
                        panel4.add(lblNameError, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 25), 0, 0));

                        //---- lblContactInfo ----
                        lblContactInfo.setText("Contact Information:");
                        lblContactInfo.setFont(new Font("Vijaya", Font.PLAIN, 22));
                        lblContactInfo.setForeground(new Color(51, 102, 153));
                        lblContactInfo.setName("lblContactInfo");
                        panel4.add(lblContactInfo, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 8, 10, 5), 0, 0));

                        //---- lblPhoneNumber ----
                        lblPhoneNumber.setText("Phone Number:");
                        lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblPhoneNumber.setForeground(Color.black);
                        lblPhoneNumber.setName("lblPhoneNumber");
                        panel4.add(lblPhoneNumber, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 15, 10, 5), 0, 0));

                        //---- txtPhoneNumber ----
                        txtPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtPhoneNumber.setDefaultText("(123) 456-7890");
                        txtPhoneNumber.setName("txtPhoneNumber");
                        txtPhoneNumber.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                validatePhoneNumber();
                            }
                        });
                        txtPhoneNumber.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                validatePhoneNumber();
                            }
                        });
                        panel4.add(txtPhoneNumber, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblEmail ----
                        lblEmail.setText("Email:");
                        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblEmail.setForeground(Color.black);
                        lblEmail.setName("lblEmail");
                        panel4.add(lblEmail, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 15, 10, 5), 0, 0));

                        //---- txtEmail ----
                        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtEmail.setDefaultText("john.doe@scout.com");
                        txtEmail.setName("txtEmail");
                        txtEmail.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                validateEmail();
                            }
                        });
                        txtEmail.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                validateEmail();
                            }
                        });
                        panel4.add(txtEmail, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblPhoneNumberError ----
                        lblPhoneNumberError.setText("* Error Message");
                        lblPhoneNumberError.setForeground(Color.red);
                        lblPhoneNumberError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblPhoneNumberError.setVisible(false);
                        lblPhoneNumberError.setName("lblPhoneNumberError");
                        panel4.add(lblPhoneNumberError, new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 25), 0, 0));

                        //---- lblEmailError ----
                        lblEmailError.setText("* Error Message");
                        lblEmailError.setForeground(Color.red);
                        lblEmailError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblEmailError.setVisible(false);
                        lblEmailError.setName("lblEmailError");
                        panel4.add(lblEmailError, new GridBagConstraints(3, 7, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 0), 0, 0));

                        //---- lblCity ----
                        lblCity.setText("City:");
                        lblCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblCity.setForeground(Color.black);
                        lblCity.setName("lblCity");
                        panel4.add(lblCity, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtCity ----
                        txtCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtCity.setDefaultText("Eagle Mtn");
                        txtCity.setName("txtCity");
                        panel4.add(txtCity, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblCity2 ----
                        lblCity2.setText("State:");
                        lblCity2.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblCity2.setForeground(Color.black);
                        lblCity2.setName("lblCity2");
                        panel4.add(lblCity2, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtState ----
                        txtState.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtState.setDefaultText("Utah");
                        txtState.setName("txtState");
                        panel4.add(txtState, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblStreet ----
                        lblStreet.setText("Street:");
                        lblStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblStreet.setForeground(Color.black);
                        lblStreet.setName("lblStreet");
                        panel4.add(lblStreet, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtStreet ----
                        txtStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtStreet.setDefaultText("100 South 100 West");
                        txtStreet.setName("txtStreet");
                        panel4.add(txtStreet, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblZip ----
                        lblZip.setText("Zip:");
                        lblZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblZip.setForeground(Color.black);
                        lblZip.setName("lblZip");
                        panel4.add(lblZip, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtZip ----
                        txtZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtZip.setDefaultText("12345");
                        txtZip.setName("txtZip");
                        panel4.add(txtZip, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- pnlAccessRights ----
                        pnlAccessRights.setName("pnlAccessRights");
                        panel4.add(pnlAccessRights, new GridBagConstraints(0, 10, 5, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(10, 8, 20, 8), 0, 0));
                    }
                    scrollPane2.setViewportView(panel4);
                }
                panel3.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 15), 0, 0));

                //======== panel5 ========
                {
                    panel5.setOpaque(false);
                    panel5.setName("panel5");
                    panel5.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {89, 0};
                    ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                    ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                    ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- btnNew ----
                    btnNew.setText("New");
                    btnNew.setBackground(new Color(51, 156, 229));
                    btnNew.setFocusPainted(false);
                    btnNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnNew.setForeground(Color.white);
                    btnNew.setPreferredSize(new Dimension(56, 40));
                    btnNew.setName("btnNew");
                    btnNew.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnNewActionPerformed();
                        }
                    });
                    panel5.add(btnNew, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 10, 0), 0, 0));

                    //---- btnSave ----
                    btnSave.setText("Save");
                    btnSave.setBackground(new Color(51, 102, 153));
                    btnSave.setFocusPainted(false);
                    btnSave.setForeground(Color.white);
                    btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnSave.setPreferredSize(new Dimension(60, 40));
                    btnSave.setName("btnSave");
                    btnSave.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnSaveActionPerformed();
                        }
                    });
                    panel5.add(btnSave, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 10, 0), 0, 0));

                    //---- btnUpdate ----
                    btnUpdate.setText("Update");
                    btnUpdate.setBackground(new Color(51, 102, 153));
                    btnUpdate.setForeground(Color.white);
                    btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnUpdate.setFocusPainted(false);
                    btnUpdate.setPreferredSize(new Dimension(74, 40));
                    btnUpdate.setName("btnUpdate");
                    btnUpdate.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnUpdateActionPerformed();
                        }
                    });
                    panel5.add(btnUpdate, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 10, 0), 0, 0));

                    //---- btnDelete ----
                    btnDelete.setText("Delete");
                    btnDelete.setBackground(new Color(207, 0, 0));
                    btnDelete.setForeground(Color.white);
                    btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnDelete.setFocusPainted(false);
                    btnDelete.setPreferredSize(new Dimension(68, 40));
                    btnDelete.setName("btnDelete");
                    btnDelete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnDeleteActionPerformed();
                        }
                    });
                    panel5.add(btnDelete, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                panel3.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            pnlContents.add(panel3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlContents, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 10, 10, 10), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextFieldDefaultText txtSearchName;
    private JList listUserNames;
    private JTextFieldDefaultText txtUsername;
    private JPasswordFieldDefaultText txtPassword;
    private JLabel lblUsernameError;
    private JLabel lblPasswordError;
    private JTextFieldDefaultText txtName;
    private JTextFieldDefaultText txtPosition;
    private JLabel lblNameError;
    private JTextFieldDefaultText txtPhoneNumber;
    private JTextFieldDefaultText txtEmail;
    private JLabel lblPhoneNumberError;
    private JLabel lblEmailError;
    private JTextFieldDefaultText txtCity;
    private JTextFieldDefaultText txtState;
    private JTextFieldDefaultText txtStreet;
    private JTextFieldDefaultText txtZip;
    private AccessRightsPanel pnlAccessRights;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
