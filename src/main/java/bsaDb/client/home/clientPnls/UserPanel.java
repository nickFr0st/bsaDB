/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.JPasswordFieldDefaultText;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.TitlePanel;
import objects.databaseObjects.User;
import util.CacheObject;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author User #2
 */
public class UserPanel extends JPanel {

    private User user;

    public UserPanel() {
        initComponents();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        populateUserNameList();
    }

    private void populateUserNameList() {
        Collection<User> userList = CacheObject.getUserList();
        List<String> userNameList = new ArrayList<String>();
        for (User user : userList) {
            userNameList.add(user.getName());
        }

        listUserNames.setListData(userNameList.toArray());
        listUserNames.revalidate();
    }

    private void txtSearchNameKeyReleased() {
        // instead of the logic get this from a cached list
        Collection<User> userList = CacheObject.getUserList();
        List<String> userNameList = new ArrayList<String>();
        for (User user : userList) {
            userNameList.add(user.getName());
        }

        if (txtSearchName.isMessageDefault()) {
            listUserNames.setListData(userNameList.toArray());
            listUserNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<String>();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(user.getName());
            }
        }

        listUserNames.setListData(filteredList.toArray());
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

        user = CacheObject.getUser(listUserNames.getSelectedValue().toString());
        loadData();
    }

    private void loadData() {
        if (user == null) {
            return;
        }

        enableControls(true);

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
        txtName.setEnabled(enable);
        txtPassword.setEnabled(enable);
        txtPosition.setEnabled(enable);
        txtPhoneNumber.setEnabled(enable);
        txtEmail.setEnabled(enable);
        txtStreet.setEnabled(enable);
        txtCity.setEnabled(enable);
        txtZip.setEnabled(enable);
        pnlAccessRights.setEnabled(enable);
    }

    private void handleAdmin() {
        if (user.getId() != 1) {
            return;
        }

        txtName.setEnabled(false);
        pnlAccessRights.setEnabled(false);
        btnDelete.setVisible(false);
    }

    private void clearAllErrors() {
        Util.clearError(lblNameError);
        Util.clearError(lblPasswordError);
        Util.clearError(lblPhoneNumberError);
        Util.clearError(lblEmailError);
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

        User tempUser = CacheObject.getUser(txtName.getText());
        if (tempUser == null) {
            return true;
        }

        if (user == null) {
            Util.setError(lblNameError, "A user with name '" + txtName.getText() + "' already exists");
            return false;
        }

        if (!tempUser.getName().equals(user.getName())) {
            Util.setError(lblNameError, "A user with name '" + txtName.getText() + "' already exists");
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
        lblGeneralInfo = new JLabel();
        JLabel lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        JLabel lblPassword = new JLabel();
        txtPassword = new JPasswordFieldDefaultText();
        JLabel lblPosition = new JLabel();
        txtPosition = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblPasswordError = new JLabel();
        lblContactInfo = new JLabel();
        JLabel lblPhoneNumber = new JLabel();
        txtPhoneNumber = new JTextFieldDefaultText();
        JLabel lblEmail = new JLabel();
        txtEmail = new JTextFieldDefaultText();
        lblPhoneNumberError = new JLabel();
        lblEmailError = new JLabel();
        JLabel lblStreet = new JLabel();
        txtStreet = new JTextFieldDefaultText();
        JLabel lblCity = new JLabel();
        txtCity = new JTextFieldDefaultText();
        JLabel lblZip = new JLabel();
        txtZip = new JTextFieldDefaultText();
        pnlAccessRights = new AccessRightsPanel();
        JPanel panel5 = new JPanel();
        btnNew = new JButton();
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
                        ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 188, 0, 155, 0, 127, 0, 0};
                        ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 40, 0, 0, 40, 0, 40, 238, 0, 0};
                        ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                        ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                        //---- lblGeneralInfo ----
                        lblGeneralInfo.setText("General Information:");
                        lblGeneralInfo.setFont(new Font("Vijaya", Font.PLAIN, 22));
                        lblGeneralInfo.setForeground(new Color(51, 102, 153));
                        lblGeneralInfo.setName("lblGeneralInfo");
                        panel4.add(lblGeneralInfo, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(10, 8, 10, 5), 0, 0));

                        //---- lblName ----
                        lblName.setText("Name:");
                        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblName.setForeground(Color.black);
                        lblName.setName("lblName");
                        panel4.add(lblName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
                        panel4.add(txtName, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
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

                        //---- lblPosition ----
                        lblPosition.setText("Position:");
                        lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblPosition.setForeground(Color.black);
                        lblPosition.setName("lblPosition");
                        panel4.add(lblPosition, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 5, 10, 5), 0, 0));

                        //---- txtPosition ----
                        txtPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtPosition.setDefaultText("Scout Master");
                        txtPosition.setName("txtPosition");
                        panel4.add(txtPosition, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblNameError ----
                        lblNameError.setText("* Error Message");
                        lblNameError.setForeground(Color.red);
                        lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblNameError.setVisible(false);
                        lblNameError.setName("lblNameError");
                        panel4.add(lblNameError, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
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
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblContactInfo ----
                        lblContactInfo.setText("Contact Information:");
                        lblContactInfo.setFont(new Font("Vijaya", Font.PLAIN, 22));
                        lblContactInfo.setForeground(new Color(51, 102, 153));
                        lblContactInfo.setName("lblContactInfo");
                        panel4.add(lblContactInfo, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 8, 10, 5), 0, 0));

                        //---- lblPhoneNumber ----
                        lblPhoneNumber.setText("Phone Number:");
                        lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblPhoneNumber.setForeground(Color.black);
                        lblPhoneNumber.setName("lblPhoneNumber");
                        panel4.add(lblPhoneNumber, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
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
                        panel4.add(txtPhoneNumber, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblEmail ----
                        lblEmail.setText("Email:");
                        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblEmail.setForeground(Color.black);
                        lblEmail.setName("lblEmail");
                        panel4.add(lblEmail, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
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
                        panel4.add(txtEmail, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblPhoneNumberError ----
                        lblPhoneNumberError.setText("* Error Message");
                        lblPhoneNumberError.setForeground(Color.red);
                        lblPhoneNumberError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblPhoneNumberError.setVisible(false);
                        lblPhoneNumberError.setName("lblPhoneNumberError");
                        panel4.add(lblPhoneNumberError, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 25), 0, 0));

                        //---- lblEmailError ----
                        lblEmailError.setText("* Error Message");
                        lblEmailError.setForeground(Color.red);
                        lblEmailError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                        lblEmailError.setVisible(false);
                        lblEmailError.setName("lblEmailError");
                        panel4.add(lblEmailError, new GridBagConstraints(3, 5, 3, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblStreet ----
                        lblStreet.setText("Street:");
                        lblStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblStreet.setForeground(Color.black);
                        lblStreet.setName("lblStreet");
                        panel4.add(lblStreet, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtStreet ----
                        txtStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtStreet.setDefaultText("100 South 100 West");
                        txtStreet.setName("txtStreet");
                        panel4.add(txtStreet, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblCity ----
                        lblCity.setText("City:");
                        lblCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblCity.setForeground(Color.black);
                        lblCity.setName("lblCity");
                        panel4.add(lblCity, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtCity ----
                        txtCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtCity.setDefaultText("Scout Town");
                        txtCity.setName("txtCity");
                        panel4.add(txtCity, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- lblZip ----
                        lblZip.setText("Zip:");
                        lblZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        lblZip.setForeground(Color.black);
                        lblZip.setName("lblZip");
                        panel4.add(lblZip, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- txtZip ----
                        txtZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtZip.setDefaultText("12345");
                        txtZip.setName("txtZip");
                        panel4.add(txtZip, new GridBagConstraints(5, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 10, 5), 0, 0));

                        //---- pnlAccessRights ----
                        pnlAccessRights.setName("pnlAccessRights");
                        panel4.add(pnlAccessRights, new GridBagConstraints(0, 7, 7, 1, 0.0, 0.0,
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
                    ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                    ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- btnNew ----
                    btnNew.setText("New");
                    btnNew.setBackground(new Color(51, 156, 229));
                    btnNew.setFocusPainted(false);
                    btnNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnNew.setForeground(Color.white);
                    btnNew.setPreferredSize(new Dimension(56, 40));
                    btnNew.setName("btnNew");
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
                    panel5.add(btnDelete, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 10, 0), 0, 0));
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
    private JLabel lblGeneralInfo;
    private JTextFieldDefaultText txtName;
    private JPasswordFieldDefaultText txtPassword;
    private JTextFieldDefaultText txtPosition;
    private JLabel lblNameError;
    private JLabel lblPasswordError;
    private JLabel lblContactInfo;
    private JTextFieldDefaultText txtPhoneNumber;
    private JTextFieldDefaultText txtEmail;
    private JLabel lblPhoneNumberError;
    private JLabel lblEmailError;
    private JTextFieldDefaultText txtStreet;
    private JTextFieldDefaultText txtCity;
    private JTextFieldDefaultText txtZip;
    private AccessRightsPanel pnlAccessRights;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
