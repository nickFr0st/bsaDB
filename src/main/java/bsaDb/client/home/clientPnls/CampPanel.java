/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.TitlePanel;
import bsaDb.client.customComponents.jdatepicker.JDatePicker;
import bsaDb.client.home.dialogs.MessageDialog;
import constants.ScoutTypeConst;
import objects.databaseObjects.BoyScout;
import objects.databaseObjects.Camp;
import objects.databaseObjects.Scout;
import objects.databaseObjects.ScoutCamp;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicCamp;
import objects.objectLogic.LogicScoutCamp;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * @author User #2
 */
@SuppressWarnings({"unchecked", "RedundantCast"})
public class CampPanel extends JPanel {

    private Camp camp;
    private List<String> availableList = new ArrayList<>();
    private List<String> selectedList = new ArrayList<>();

    public CampPanel() {
        initComponents();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        clearAllErrors();

        scrollPane2.getVerticalScrollBar().setUnitIncrement(18);

        for (ScoutTypeConst scoutTypeConst : ScoutTypeConst.values()) {
            cboCampType.addItem(scoutTypeConst.getName());
        }

        populateCampNameList();
        enableControls(false);
    }

    public void populateCampNameList() {
        listCampoutNames.setListData(Util.getSortedList((LinkedHashSet) LogicCamp.findAll(null)));
        listCampoutNames.revalidate();
        listCampoutNames.repaint();
    }

    private void txtSearchNameKeyReleased() {
        LinkedHashSet campSet = (LinkedHashSet) LogicCamp.findAll(null);

        if (txtSearchName.isMessageDefault()) {
            listCampoutNames.setListData(Util.getSortedList(campSet));
            listCampoutNames.revalidate();
            return;
        }

        List<Camp> filteredList = new ArrayList<>();
        for (Camp campout : (LinkedHashSet<Camp>) campSet) {
            if (campout.getName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(campout);
            }
        }

        listCampoutNames.setListData(Util.getSortedList((ArrayList) filteredList));
        listCampoutNames.revalidate();
    }

    private void listUserNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listUserNamesMouseReleased();
        }
    }

    private void listUserNamesMouseReleased() {
        if (listCampoutNames.getSelectedValue() == null) {
            return;
        }

        clearAllErrors();
        clearData();

        camp = (Camp) listCampoutNames.getSelectedValue();
        loadData();
    }

    private void loadData() {
        if (camp == null) {
            return;
        }

        enableControls(true);

        txtName.setText(camp.getName());
        cboCampType.setSelectedItem(ScoutTypeConst.getConst(camp.getScoutTypeId()).getName());
        txtLocation.setText(camp.getLocation());

        Calendar campDate = Calendar.getInstance();
        campDate.setTime(camp.getStartDate());
        cboCampDate.getModel().setDate(campDate.get(Calendar.YEAR), campDate.get(Calendar.MONTH), campDate.get(Calendar.DATE));
        cboCampDate.getModel().setSelected(true);

        txtLeaders.setText(camp.getLeaders());
        txtNumOfNights.setText(Integer.toString(camp.getNumberOfNights()));
        txtNotes.setText(camp.getNote());

        availableList = new ArrayList<>();
        Set<BoyScout> scoutList = LogicBoyScout.findAll();
        for (BoyScout boyScout : scoutList) {
            availableList.add(boyScout.getName());
        }

        List<ScoutCamp> scoutCampList = LogicScoutCamp.findAllByCampId(camp.getId());
        for (ScoutCamp scoutCamp : scoutCampList) {
            Scout scout;
            int scoutTypeId = scoutCamp.getScoutTypeId();
//            if (scoutTypeId == ScoutTypeConst.CUB_SCOUT.getId()) {
//                scout = new BoyScout(); // change this to cub scout when cub scouts get added
//            } else
            if (scoutTypeId == ScoutTypeConst.BOY_SCOUT.getId()) {
                scout = LogicBoyScout.findById(scoutCamp.getScoutId());
            }
//            else if (scoutTypeId == ScoutTypeConst.VARSITY_SCOUT.getId()) {
//                scout = new BoyScout();
//            }
            else {
                scout = new BoyScout();
            }

            availableList.remove(scout.getName());
            selectedList.add(scout.getName());
        }

        listAvailable.setListData(availableList.toArray());
        listSelected.setListData(selectedList.toArray());

        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnSave.setVisible(false);
        btnRefresh.setVisible(true);
    }

    private void enableControls(boolean enable) {
        txtName.setEnabled(enable);
        cboCampType.setEnabled(enable);
        txtLocation.setEnabled(enable);
        cboCampDate.setEnabled(enable);
        txtLeaders.setEnabled(enable);
        txtNumOfNights.setEnabled(enable);
        listAvailable.setEnabled(enable);
        listSelected.setEnabled(enable);
        btnAdd.setEnabled(enable);
        btnRemove.setEnabled(enable);
        txtNotes.setEnabled(enable);
    }

    private void clearAllErrors() {
        Util.clearError(lblNameError);
        Util.clearError(lblLocationError);
        Util.clearError(lblCampDateError);
        Util.clearError(lblLeaderError);
        Util.clearError(lblNumOfNightsError);
    }

    private void btnNewActionPerformed() {
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
        btnRefresh.setVisible(false);

        enableControls(true);
        clearAllErrors();
        clearData();

        Set<BoyScout> scoutList = LogicBoyScout.findAll();
        for (BoyScout boyScout : scoutList) {
            availableList.add(boyScout.getName());
        }
        listAvailable.setListData(availableList.toArray());

        txtName.requestFocus();
    }

    private void clearData() {
        camp = null;

        txtName.setDefault();
        cboCampType.setSelectedItem(ScoutTypeConst.BOY_SCOUT.getName());
        txtLocation.setDefault();
        cboCampDate.getModel().setValue(null);
        cboCampDate.getModel().setSelected(true);
        txtLeaders.setDefault();
        txtNumOfNights.setDefault();
        availableList = new ArrayList<>();
        selectedList = new ArrayList<>();
        listAvailable.setListData(availableList.toArray());
        listSelected.setListData(selectedList.toArray());
        txtNotes.setText("");
    }

    private void btnSaveActionPerformed() {
        if (!validateFields()) {
            return;
        }

        setData();

        LogicCamp.save(camp);

        if (!selectedList.isEmpty()) {
            List<ScoutCamp> scoutCampList = new ArrayList<>();
            for (String scoutName : selectedList) {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setCampId(camp.getId());
                scoutCamp.setScoutTypeId(camp.getScoutTypeId());
                scoutCamp.setScoutId(LogicBoyScout.findByName(scoutName).getId());
                scoutCamp.setNumberOfNights(camp.getNumberOfNights());

                scoutCampList.add(scoutCamp);
            }

            LogicScoutCamp.save(scoutCampList);
        }

        btnSave.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnRefresh.setVisible(true);

        populateCampNameList();

        listCampoutNames.setSelectedValue(camp, true);
    }

    private void setData() {
        if (camp == null) {
            camp = new Camp();
        }

        camp.setName(txtName.getText());
        camp.setScoutTypeId(ScoutTypeConst.getConst(cboCampType.getSelectedItem().toString()).getId());
        camp.setLocation(txtLocation.getText());

        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.YEAR, cboCampDate.getModel().getYear());
        startDate.set(Calendar.MONTH, cboCampDate.getModel().getMonth());
        startDate.set(Calendar.DATE, cboCampDate.getModel().getDay());

        camp.setStartDate(startDate.getTime());
        camp.setLeaders(txtLeaders.getText());
        camp.setNumberOfNights(Integer.parseInt(txtNumOfNights.getText()));
        camp.setNote(txtNotes.getText());
    }

    private boolean validateFields() {
        boolean valid = validateCampName();

        if (!validateLocation()) {
            valid = false;
        }

        if (!validateLeaders()) {
            valid = false;
        }

        if (!validateCampDate()) {
            valid = false;
        }

        if (!validateNumberOfNights()) {
            valid = false;
        }

        return valid;
    }

    private boolean validateCampDate() {
        Util.clearError(lblCampDateError);

        if (!cboCampDate.getModel().isSelected()) {
            Util.setError(lblCampDateError, "Must select a start date");
            return false;
        }
        return true;
    }

    private boolean validateLeaders() {
        Util.clearError(lblLeaderError);

        if (txtLeaders.isMessageDefault()) {
            Util.setError(lblLeaderError, "Cannot leave leaders blank");
            return false;
        }
        return true;
    }

    private boolean validateLocation() {
        Util.clearError(lblLocationError);

        if (txtLocation.isMessageDefault()) {
            Util.setError(lblLocationError, "Cannot leave location blank");
            return false;
        }
        return true;
    }

    private void btnUpdateActionPerformed() {
        if (listCampoutNames.getSelectedValue() == null) {
            return;
        }

        if (!validateFields()) {
            return;
        }

        setData();
        LogicCamp.update(camp);

        LogicScoutCamp.deleteAllByCampId(camp.getId());

        if (!selectedList.isEmpty()) {
            List<ScoutCamp> scoutCampList = new ArrayList<>();
            for (String scoutName : selectedList) {
                ScoutCamp scoutCamp = new ScoutCamp();
                scoutCamp.setCampId(camp.getId());
                scoutCamp.setScoutTypeId(camp.getScoutTypeId());
                scoutCamp.setScoutId(LogicBoyScout.findByName(scoutName).getId());
                scoutCamp.setNumberOfNights(camp.getNumberOfNights());

                scoutCampList.add(scoutCamp);
            }

            LogicScoutCamp.save(scoutCampList);
        }


        populateCampNameList();
        listCampoutNames.setSelectedValue(camp, true);
    }

    private void btnDeleteActionPerformed() {
        if (listCampoutNames.getSelectedValue() == null) {
            return;
        }

        if (new MessageDialog(Util.getParent(this), "Delete Camp", "Are you sure you want to delete the selected camp?", MessageDialog.MessageType.QUESTION, MessageDialog.ButtonType.YES_NO).getChoice() != MessageDialog.OPTION_YES) {
            return;
        }

        LogicScoutCamp.deleteAllByCampId(camp.getId());
        LogicCamp.delete(camp);

        populateCampNameList();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        btnRefresh.setVisible(true);

        clearAllErrors();
        clearData();
        enableControls(false);
    }

    private boolean validateCampName() {
        Util.clearError(lblNameError);

        if (txtName.isMessageDefault() || txtName.getText().trim().isEmpty()) {
            Util.setError(lblNameError, "Name cannot be left blank");
            return false;
        }

        Camp tempCamp = LogicCamp.findByName(txtName.getText());
        if (tempCamp == null) {
            return true;
        }

        if (camp == null || !tempCamp.getName().equals(camp.getName())) {
            Util.setError(lblNameError, "A camp with the name '" + txtName.getText() + "' already exists");
            return false;
        }

        return true;
    }

    private void btnRemoveActionPerformed() {
        if (listSelected.getSelectedValue() == null) {
            return;
        }

        List<String> newSection = listSelected.getSelectedValuesList();

        for (String selected : newSection) {
            int size = selectedList.size();
            for (int i = 0; i < size; i++) {
                if (selectedList.get(i).equals(selected)) {
                    selectedList.remove(i);
                    break;
                }
            }
        }

        for (String selected : newSection) {
            availableList.add(selected);
        }

        listAvailable.setListData(availableList.toArray());
        listSelected.setListData(selectedList.toArray());
    }

    private void btnAddActionPerformed() {
        if (listAvailable.getSelectedValue() == null) {
            return;
        }

        List<String> newSelection = listAvailable.getSelectedValuesList();

        for (String selected : newSelection) {
            int size = availableList.size();
            for (int i = 0; i < size; i++) {
                if (availableList.get(i).equals(selected)) {
                    availableList.remove(i);
                    break;
                }
            }
        }

        for (String selected : newSelection) {
            selectedList.add(selected);
        }

        listAvailable.setListData(availableList.toArray());
        listSelected.setListData(selectedList.toArray());
    }

    private void cboCampTypeActionPerformed() {
        if (cboCampType.getSelectedItem().toString().equals(ScoutTypeConst.BOY_SCOUT.getName())) {
            Set<BoyScout> scoutList = LogicBoyScout.findAll();
            for (BoyScout boyScout : scoutList) {
                availableList.add(boyScout.getName());
            }
        }
    }

    private boolean validateNumberOfNights() {
        Util.clearError(lblNumOfNightsError);

        if (txtNumOfNights.isMessageDefault()) {
            Util.setError(lblNumOfNightsError, "Value missing, must be a positive whole number");
            return false;
        }

        try {
            int timeRequirement = Integer.parseInt(txtNumOfNights.getText());
            if (timeRequirement < 0) {
                Util.setError(lblNumOfNightsError, "Invalid value, must be positive whole numbers only");
                return false;
            }
        } catch (NumberFormatException e) {
            Util.setError(lblNumOfNightsError, "Invalid value, whole numbers only");
            return false;
        }

        return true;
    }

    private void btnRefreshActionPerformed() {
        Camp currentSelectedCamp = null;
        if (listCampoutNames.getSelectedValue() != null) {
            currentSelectedCamp = (Camp) listCampoutNames.getSelectedValue();
        }

        populateCampNameList();
        if (currentSelectedCamp != null) {
            listCampoutNames.setSelectedValue(currentSelectedCamp, true);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        TitlePanel pnlTitle = new TitlePanel();
        JPanel pnlContents = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        JScrollPane scrollPane1 = new JScrollPane();
        listCampoutNames = new JList();
        JPanel panel3 = new JPanel();
        scrollPane2 = new JScrollPane();
        JPanel panel4 = new JPanel();
        JPanel panel1 = new JPanel();
        JLabel lblGeneralInformation = new JLabel();
        JLabel lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        JLabel lblCampType = new JLabel();
        cboCampType = new JComboBox();
        lblNameError = new JLabel();
        JLabel lblLocation = new JLabel();
        txtLocation = new JTextFieldDefaultText();
        JLabel lblCampDate = new JLabel();
        cboCampDate = new JDatePicker();
        lblLocationError = new JLabel();
        lblCampDateError = new JLabel();
        JLabel lblLeaders = new JLabel();
        txtLeaders = new JTextFieldDefaultText();
        lblLeaderError = new JLabel();
        JLabel lblNumOfNights = new JLabel();
        txtNumOfNights = new JTextFieldDefaultText();
        lblNumOfNightsError = new JLabel();
        JLabel lblWhoCame = new JLabel();
        JPanel pnlWhoCame = new JPanel();
        JLabel lblAvailable = new JLabel();
        JLabel lblSelected = new JLabel();
        JScrollPane scrollPane3 = new JScrollPane();
        listAvailable = new JList();
        JPanel pnlMoveChoices = new JPanel();
        btnRemove = new JButton();
        btnAdd = new JButton();
        JScrollPane scrollPane4 = new JScrollPane();
        listSelected = new JList();
        JLabel lblNotes = new JLabel();
        JScrollPane scrollPane5 = new JScrollPane();
        txtNotes = new JTextArea();
        JPanel panel5 = new JPanel();
        JButton btnNew = new JButton();
        btnSave = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();
        btnRefresh = new JButton();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //---- pnlTitle ----
        pnlTitle.setTitle("Campouts");
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

                    //---- listCampoutNames ----
                    listCampoutNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listCampoutNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listCampoutNames.setName("listCampoutNames");
                    listCampoutNames.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            listUserNamesKeyReleased(e);
                        }
                    });
                    listCampoutNames.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            listUserNamesMouseReleased();
                        }
                    });
                    scrollPane1.setViewportView(listCampoutNames);
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
                        ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0};
                        ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
                        ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                        ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                        //======== panel1 ========
                        {
                            panel1.setOpaque(false);
                            panel1.setName("panel1");
                            panel1.setLayout(new GridBagLayout());
                            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 260, 129, 236, 0};
                            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 35, 0, 35, 0, 35, 0, 0, 0, 0, 0, 0, 123, 0};
                            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                            //---- lblGeneralInformation ----
                            lblGeneralInformation.setText("General Information:");
                            lblGeneralInformation.setFont(new Font("Vijaya", Font.PLAIN, 22));
                            lblGeneralInformation.setForeground(new Color(0, 63, 135));
                            lblGeneralInformation.setName("lblGeneralInformation");
                            panel1.add(lblGeneralInformation, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));

                            //---- lblName ----
                            lblName.setText("Name:");
                            lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblName.setForeground(Color.black);
                            lblName.setName("lblName");
                            panel1.add(lblName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 5, 5, 5), 0, 0));

                            //---- txtName ----
                            txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            txtName.setDefaultText("Camp Name");
                            txtName.setName("txtName");
                            txtName.addFocusListener(new FocusAdapter() {
                                @Override
                                public void focusLost(FocusEvent e) {
                                    validateCampName();
                                }
                            });
                            txtName.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    validateCampName();
                                }
                            });
                            panel1.add(txtName, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));

                            //---- lblCampType ----
                            lblCampType.setText("Camp Type:");
                            lblCampType.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblCampType.setForeground(Color.black);
                            lblCampType.setName("lblCampType");
                            panel1.add(lblCampType, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 15, 5, 5), 0, 0));

                            //---- cboCampType ----
                            cboCampType.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            cboCampType.setBackground(Color.white);
                            cboCampType.setForeground(Color.black);
                            cboCampType.setName("cboCampType");
                            cboCampType.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    cboCampTypeActionPerformed();
                                }
                            });
                            panel1.add(cboCampType, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblNameError ----
                            lblNameError.setText("*error message");
                            lblNameError.setForeground(new Color(206, 17, 38));
                            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblNameError.setName("lblNameError");
                            panel1.add(lblNameError, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 5), 0, 0));

                            //---- lblLocation ----
                            lblLocation.setText("Location:");
                            lblLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblLocation.setForeground(Color.black);
                            lblLocation.setName("lblLocation");
                            panel1.add(lblLocation, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 5, 5, 5), 0, 0));

                            //---- txtLocation ----
                            txtLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            txtLocation.setDefaultText("Location");
                            txtLocation.setName("txtLocation");
                            txtLocation.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    validateLocation();
                                }
                            });
                            txtLocation.addFocusListener(new FocusAdapter() {
                                @Override
                                public void focusLost(FocusEvent e) {
                                    validateLocation();
                                }
                            });
                            panel1.add(txtLocation, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));

                            //---- lblCampDate ----
                            lblCampDate.setText("Camp Start Date:");
                            lblCampDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblCampDate.setForeground(Color.black);
                            lblCampDate.setName("lblCampDate");
                            panel1.add(lblCampDate, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 15, 5, 5), 0, 0));

                            //---- cboCampDate ----
                            cboCampDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            cboCampDate.setMaximumSize(new Dimension(32822, 30));
                            cboCampDate.setMinimumSize(new Dimension(57, 30));
                            cboCampDate.setName("cboCampDate");
                            cboCampDate.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    validateCampDate();
                                }
                            });
                            panel1.add(cboCampDate, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblLocationError ----
                            lblLocationError.setText("*error message");
                            lblLocationError.setForeground(new Color(206, 17, 38));
                            lblLocationError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblLocationError.setName("lblLocationError");
                            panel1.add(lblLocationError, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 5), 0, 0));

                            //---- lblCampDateError ----
                            lblCampDateError.setText("*error message");
                            lblCampDateError.setForeground(new Color(206, 17, 38));
                            lblCampDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblCampDateError.setName("lblCampDateError");
                            panel1.add(lblCampDateError, new GridBagConstraints(2, 4, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 0), 0, 0));

                            //---- lblLeaders ----
                            lblLeaders.setText("Leaders:");
                            lblLeaders.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblLeaders.setForeground(Color.black);
                            lblLeaders.setName("lblLeaders");
                            panel1.add(lblLeaders, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 5, 5, 5), 0, 0));

                            //---- txtLeaders ----
                            txtLeaders.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            txtLeaders.setDefaultText("Leaders 1, Leader 2...");
                            txtLeaders.setName("txtLeaders");
                            txtLeaders.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    validateLeaders();
                                }
                            });
                            txtLeaders.addFocusListener(new FocusAdapter() {
                                @Override
                                public void focusLost(FocusEvent e) {
                                    validateLeaders();
                                }
                            });
                            panel1.add(txtLeaders, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblLeaderError ----
                            lblLeaderError.setText("*error message");
                            lblLeaderError.setForeground(new Color(206, 17, 38));
                            lblLeaderError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblLeaderError.setName("lblLeaderError");
                            panel1.add(lblLeaderError, new GridBagConstraints(0, 6, 4, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 0), 0, 0));

                            //---- lblNumOfNights ----
                            lblNumOfNights.setText("# of Nights:");
                            lblNumOfNights.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblNumOfNights.setForeground(Color.black);
                            lblNumOfNights.setName("lblNumOfNights");
                            panel1.add(lblNumOfNights, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 5, 5, 5), 0, 0));

                            //---- txtNumOfNights ----
                            txtNumOfNights.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            txtNumOfNights.setDefaultText("1");
                            txtNumOfNights.setName("txtNumOfNights");
                            txtNumOfNights.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    validateNumberOfNights();
                                }
                            });
                            txtNumOfNights.addFocusListener(new FocusAdapter() {
                                @Override
                                public void focusLost(FocusEvent e) {
                                    validateNumberOfNights();
                                }
                            });
                            panel1.add(txtNumOfNights, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));

                            //---- lblNumOfNightsError ----
                            lblNumOfNightsError.setText("*error message");
                            lblNumOfNightsError.setForeground(new Color(206, 17, 38));
                            lblNumOfNightsError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblNumOfNightsError.setName("lblNumOfNightsError");
                            panel1.add(lblNumOfNightsError, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 5), 0, 0));

                            //---- lblWhoCame ----
                            lblWhoCame.setText("Who Came:");
                            lblWhoCame.setFont(new Font("Vijaya", Font.PLAIN, 22));
                            lblWhoCame.setForeground(new Color(0, 63, 135));
                            lblWhoCame.setName("lblWhoCame");
                            panel1.add(lblWhoCame, new GridBagConstraints(0, 9, 3, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));

                            //======== pnlWhoCame ========
                            {
                                pnlWhoCame.setOpaque(false);
                                pnlWhoCame.setName("pnlWhoCame");
                                pnlWhoCame.setLayout(new GridBagLayout());
                                ((GridBagLayout)pnlWhoCame.getLayout()).columnWidths = new int[] {308, 0, 300, 0};
                                ((GridBagLayout)pnlWhoCame.getLayout()).rowHeights = new int[] {0, 183, 0};
                                ((GridBagLayout)pnlWhoCame.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                                ((GridBagLayout)pnlWhoCame.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                                //---- lblAvailable ----
                                lblAvailable.setText("Did Not Attend:");
                                lblAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblAvailable.setForeground(Color.black);
                                lblAvailable.setName("lblAvailable");
                                pnlWhoCame.add(lblAvailable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 5, 5, 8), 0, 0));

                                //---- lblSelected ----
                                lblSelected.setText("Attended:");
                                lblSelected.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblSelected.setForeground(Color.black);
                                lblSelected.setName("lblSelected");
                                pnlWhoCame.add(lblSelected, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 5, 5, 0), 0, 0));

                                //======== scrollPane3 ========
                                {
                                    scrollPane3.setName("scrollPane3");

                                    //---- listAvailable ----
                                    listAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    listAvailable.setForeground(Color.black);
                                    listAvailable.setBackground(Color.white);
                                    listAvailable.setName("listAvailable");
                                    scrollPane3.setViewportView(listAvailable);
                                }
                                pnlWhoCame.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 5, 0, 8), 0, 0));

                                //======== pnlMoveChoices ========
                                {
                                    pnlMoveChoices.setOpaque(false);
                                    pnlMoveChoices.setName("pnlMoveChoices");
                                    pnlMoveChoices.setLayout(new GridBagLayout());
                                    ((GridBagLayout)pnlMoveChoices.getLayout()).columnWidths = new int[] {0, 0};
                                    ((GridBagLayout)pnlMoveChoices.getLayout()).rowHeights = new int[] {0, 0, 0};
                                    ((GridBagLayout)pnlMoveChoices.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                                    ((GridBagLayout)pnlMoveChoices.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                                    //---- btnRemove ----
                                    btnRemove.setOpaque(false);
                                    btnRemove.setFocusPainted(false);
                                    btnRemove.setIcon(new ImageIcon(getClass().getResource("/images/remove.png")));
                                    btnRemove.setMargin(new Insets(0, 0, 0, 0));
                                    btnRemove.setBorder(null);
                                    btnRemove.setBackground(Color.white);
                                    btnRemove.setName("btnRemove");
                                    btnRemove.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            btnRemoveActionPerformed();
                                        }
                                    });
                                    pnlMoveChoices.add(btnRemove, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 0, 0), 0, 0));

                                    //---- btnAdd ----
                                    btnAdd.setOpaque(false);
                                    btnAdd.setFocusPainted(false);
                                    btnAdd.setIcon(new ImageIcon(getClass().getResource("/images/put.png")));
                                    btnAdd.setMargin(new Insets(0, 0, 0, 0));
                                    btnAdd.setPreferredSize(new Dimension(32, 32));
                                    btnAdd.setMinimumSize(new Dimension(32, 32));
                                    btnAdd.setBorder(null);
                                    btnAdd.setBackground(Color.white);
                                    btnAdd.setName("btnAdd");
                                    btnAdd.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            btnAddActionPerformed();
                                        }
                                    });
                                    pnlMoveChoices.add(btnAdd, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 10, 0), 0, 0));
                                }
                                pnlWhoCame.add(pnlMoveChoices, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(0, 0, 0, 8), 0, 0));

                                //======== scrollPane4 ========
                                {
                                    scrollPane4.setName("scrollPane4");

                                    //---- listSelected ----
                                    listSelected.setBackground(Color.white);
                                    listSelected.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    listSelected.setForeground(Color.black);
                                    listSelected.setName("listSelected");
                                    scrollPane4.setViewportView(listSelected);
                                }
                                pnlWhoCame.add(scrollPane4, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 5, 0, 0), 0, 0));
                            }
                            panel1.add(pnlWhoCame, new GridBagConstraints(0, 10, 4, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblNotes ----
                            lblNotes.setText("Notes:");
                            lblNotes.setFont(new Font("Vijaya", Font.PLAIN, 22));
                            lblNotes.setForeground(new Color(0, 63, 135));
                            lblNotes.setName("lblNotes");
                            panel1.add(lblNotes, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));

                            //======== scrollPane5 ========
                            {
                                scrollPane5.setName("scrollPane5");

                                //---- txtNotes ----
                                txtNotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtNotes.setBackground(Color.white);
                                txtNotes.setForeground(Color.black);
                                txtNotes.setName("txtNotes");
                                scrollPane5.setViewportView(txtNotes);
                            }
                            panel1.add(scrollPane5, new GridBagConstraints(0, 12, 4, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        panel4.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(10, 10, 10, 10), 0, 0));
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
                    btnSave.setBackground(new Color(0, 63, 135));
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
                    btnUpdate.setBackground(new Color(0, 63, 135));
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
                    btnDelete.setBackground(new Color(206, 17, 38));
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
                        new Insets(0, 0, 10, 0), 0, 0));

                    //---- btnRefresh ----
                    btnRefresh.setText("Refresh");
                    btnRefresh.setBackground(new Color(0, 107, 63));
                    btnRefresh.setForeground(Color.white);
                    btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnRefresh.setFocusPainted(false);
                    btnRefresh.setPreferredSize(new Dimension(68, 40));
                    btnRefresh.setName("btnRefresh");
                    btnRefresh.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnRefreshActionPerformed();
                        }
                    });
                    panel5.add(btnRefresh, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
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
    private JList listCampoutNames;
    private JScrollPane scrollPane2;
    private JTextFieldDefaultText txtName;
    private JComboBox cboCampType;
    private JLabel lblNameError;
    private JTextFieldDefaultText txtLocation;
    private JDatePicker cboCampDate;
    private JLabel lblLocationError;
    private JLabel lblCampDateError;
    private JTextFieldDefaultText txtLeaders;
    private JLabel lblLeaderError;
    private JTextFieldDefaultText txtNumOfNights;
    private JLabel lblNumOfNightsError;
    private JList listAvailable;
    private JButton btnRemove;
    private JButton btnAdd;
    private JList listSelected;
    private JTextArea txtNotes;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRefresh;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
