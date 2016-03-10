/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.TitlePanel;
import bsaDb.client.customComponents.jdatepicker.JDatePicker;
import constants.RequirementTypeConst;
import objects.databaseObjects.Advancement;
import objects.databaseObjects.BoyScout;
import objects.databaseObjects.Requirement;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicRequirement;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import util.CacheObject;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * @author User #2
 */
public class BoyScoutPanel extends JPanel {

    private final Color GOOD = new Color(32, 154, 26);
    private final Color AVG = new Color(51, 102, 153);
    private final Color WARNING = new Color(220,177,26);
    private final Color BAD = new Color(255, 0, 0);

    private BoyScout boyScout;

    public BoyScoutPanel() {
        initComponents();

        setupProgressBars();
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        clearAllErrors();

        scrollPane2.getVerticalScrollBar().setUnitIncrement(18);

        populateAdvancementNameList();
        enableControls(false);
    }

    private void setupProgressBars() {
        barTimeLeft.setForeground(AVG);
        barTimeLeft.setBackground(Color.white);

        barCampsAttended.setForeground(AVG);
        barCampsAttended.setBackground(Color.white);

        barWaitPeriod.setForeground(AVG);
        barWaitPeriod.setBackground(Color.white);

        barProgress.setForeground(AVG);
        barProgress.setBackground(Color.white);
    }

    public void populateAdvancementNameList() {
        Set<BoyScout> boyScoutSet = LogicBoyScout.findAll();
        List<String> boyScoutName = new ArrayList<>();
        for (BoyScout boyScout : boyScoutSet) {
            boyScoutName.add(boyScout.getName());
        }

        listBoyScoutNames.setListData(Util.getSortedList(boyScoutName));
        listBoyScoutNames.revalidate();
        listBoyScoutNames.repaint();
    }

    private void txtSearchNameKeyReleased() {
        Collection<Advancement> advancementList = CacheObject.getAdvancementList();
        List<String> advancementNameList = new ArrayList<>();
        for (Advancement advancement : advancementList) {
            advancementNameList.add(advancement.getName());
        }

        if (txtSearchName.isMessageDefault()) {
            listBoyScoutNames.setListData(Util.getSortedList(advancementNameList));
            listBoyScoutNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<>();
        for (Advancement advancement : advancementList) {
            if (advancement.getName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(advancement.getName());
            }
        }

        listBoyScoutNames.setListData(Util.getSortedList(filteredList));
        listBoyScoutNames.revalidate();
    }

    private void listBoyScoutNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listBoyScoutNamesMouseReleased();
        }
    }

    private void listBoyScoutNamesMouseReleased() {
        if (listBoyScoutNames.getSelectedValue() == null) {
            return;
        }

        clearAllErrors();
        clearData();

        boyScout = LogicBoyScout.findByName(listBoyScoutNames.getSelectedValue().toString());
        loadData();
    }

    private void loadData() {
        if (boyScout == null) {
            return;
        }

        enableControls(true);

        Collection<Advancement> advancementList = CacheObject.getAdvancementList();
        String advancementName = "";
        if (!Util.isEmpty(advancementList)) {
            for (Advancement advancement : advancementList) {
                cboRank.addItem(advancement.getName());
                if (advancement.getId() == boyScout.getAdvancementId()) {
                    advancementName = advancement.getName();
                }
            }
        }

        // Summary Tab
        txtName.setText(boyScout.getName());
        txtPosition.setText(boyScout.getPosition());
        cboRank.setSelectedItem(advancementName);

        Calendar rankDate = Calendar.getInstance();
        rankDate.setTime(boyScout.getAdvancementDate());
        cboRankDate.getModel().setDate(rankDate.get(Calendar.YEAR), rankDate.get(Calendar.MONTH), rankDate.get(Calendar.DATE));
        cboRankDate.getModel().setSelected(true);

        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(boyScout.getBirthDate());
        cboBirthDate.getModel().setDate(birthDate.get(Calendar.YEAR), birthDate.get(Calendar.MONTH), birthDate.get(Calendar.DATE));
        cboBirthDate.getModel().setSelected(true);

        LocalDate birthDateTime = new LocalDate(birthDate.get(Calendar.YEAR), birthDate.get(Calendar.MONTH), birthDate.get(Calendar.DATE));
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(birthDateTime, now);
        lblAgeValue.setText(Integer.toString(age.getYears()));

        // todo: handle barGraphs and advancement table

        // Contact Tab
        if (!Util.isEmpty(boyScout.getPhoneNumber())) {
            txtPhoneNumber.setText(boyScout.getPhoneNumber());
        }

        if (!Util.isEmpty(boyScout.getCity())) {
            txtCity.setText(boyScout.getCity());
        }

        if (!Util.isEmpty(boyScout.getState())) {
            txtState.setText(boyScout.getState());
        }

        if (!Util.isEmpty(boyScout.getStreet())) {
            txtStreet.setText(boyScout.getStreet());
        }

        if (!Util.isEmpty(boyScout.getZip())) {
            txtZip.setText(boyScout.getZip());
        }

        if (!Util.isEmpty(boyScout.getGuardianName())) {
            txtGuardianName.setText(boyScout.getGuardianName());
        }

        if (!Util.isEmpty(boyScout.getGuardianPhoneNumber())) {
            txtGuardianPhone.setText(boyScout.getGuardianPhoneNumber());
        }

        if (!Util.isEmpty(boyScout.getNote())) {
            txtNotes.setText(boyScout.getNote());
        }

        // Details Tab
        // todo: handle this information

        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnSave.setVisible(false);
    }

//    private void loadRequirementSet() {
//        Set<Requirement> requirementSet = LogicRequirement.findAllByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());
//
//        boolean firstAdded = false;
//        for (Requirement requirement : requirementSet) {
//            PnlRequirement pnlRequirement = new PnlRequirement(requirement.getName(), requirement.getDescription(), firstAdded, requirement.getId());
//            pnlRequirementList.add(pnlRequirement);
//
//            if (!firstAdded) {
//                firstAdded = true;
//            }
//        }
//
//
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                scrollPane3.getViewport().setViewPosition(new Point(0, 0));
//            }
//        });
//
//        pnlRequirementList.revalidate();
//        pnlRequirementList.repaint();
//    }

    private void enableControls(boolean enable) {
        // Settings Tab
        txtName.setEnabled(enable);
        txtPosition.setEnabled(enable);
        cboRank.setEnabled(enable);
        cboRankDate.setEnabled(enable);
        cboBirthDate.setEnabled(enable);
        tblProgress.setEnabled(enable);

        // Contact Info Tab
        txtPhoneNumber.setEnabled(enable);
        txtCity.setEnabled(enable);
        txtStreet.setEnabled(enable);
        txtGuardianName.setEnabled(enable);
        txtState.setEnabled(enable);
        txtZip.setEnabled(enable);
        txtGuardianPhone.setEnabled(enable);
        txtNotes.setEnabled(enable);

        // Details Tab
        tblSpecialAwards.setEnabled(enable);
        listMeritBadges.setEnabled(enable);
        listCamps.setEnabled(enable);
        btnAddAward.setEnabled(enable);
        btnRemoveAward.setEnabled(enable);
        btnAddMeritBadge.setEnabled(enable);
        btnRemoveMeritBadge.setEnabled(enable);
        btnAddCamp.setEnabled(enable);
        btnRemoveCamp.setEnabled(enable);
    }

    private void clearAllErrors() {
        // Settings Tab
        Util.clearError(lblNameError);
        Util.clearError(lblBirthDateError);
        Util.clearError(lblRankDateError);

        // Contact Info Tab
        Util.clearError(lblPhoneNumberError);
        Util.clearError(lblGuardianPhoneError);
    }

    private void btnNewActionPerformed() {
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        enableControls(true);
        clearAllErrors();
        clearData();

//        txtName.requestFocus();
    }

    private void clearData() {
//        advancement = null;
//
//        btnBadgeImage.setIcon(noImage);
//        txtName.setDefault();
//        imagePath = "";
//
//        pnlRequirementList.removeAll();
//        pnlRequirementList.repaint();
    }

    private void btnSaveActionPerformed() {
        if (!validateFields()) {
            return;
        }

        setData();

//        Set<Requirement> requirementSet = validateRequirements(-1, true);
//        if (requirementSet == null) return;

//        saveRecords(requirementSet, true);

        btnSave.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        populateAdvancementNameList();

        listBoyScoutNames.setSelectedValue(boyScout.getName(), true);
    }

    private void saveRecords(Set<Requirement> requirementSet, boolean newAdvancement) {
//        boyScout = LogicAdvancement.save(boyScout);

        if (newAdvancement) {
            for (Requirement requirement : requirementSet) {
                requirement.setParentId(boyScout.getId());
            }
        }

        LogicRequirement.save(requirementSet);

//        CacheObject.addToAdvancements(boyScout);
    }

//    private Set<Requirement> validateRequirements(int parentId, boolean validate) {
//        Set<Requirement> requirementSet = new LinkedHashSet<>();
//        Set<String> reqNameSet = new HashSet<>();
//
//        for (Component component : pnlRequirementList.getComponents()) {
//            if (!(component instanceof PnlRequirement)) {
//                continue;
//            }
//
//            if (!validate && ((PnlRequirement)component).getReqId() < 0) {
//                continue;
//            }
//
//            String reqName = ((PnlRequirement)component).getName().trim();
//
//            if (validate && reqName.isEmpty()) {
//                Util.setError(lblRequirementError, "Requirement name cannot be left blank");
//                return null;
//            }
//
//            if (validate && !reqNameSet.add(reqName)) {
//                Util.setError(lblRequirementError, "Requirement name '" + reqName + "' already exists");
//                component.requestFocus();
//                return null;
//            }
//
//            if (validate && ((PnlRequirement)component).getDescription().trim().isEmpty()) {
//                Util.setError(lblRequirementError, "Requirement description cannot be left blank");
//                return null;
//            }
//
//            Requirement requirement = new Requirement();
//            if (parentId > 0) {
//                requirement.setParentId(parentId);
//            }
//            requirement.setName(((PnlRequirement)component).getName());
//            requirement.setDescription(((PnlRequirement) component).getDescription());
//            requirement.setId(((PnlRequirement) component).getReqId());
//            requirement.setTypeId(RequirementTypeConst.ADVANCEMENT.getId());
//
//            requirementSet.add(requirement);
//        }
//        return requirementSet;
//    }

    private void setData() {
        if (boyScout == null) {
//            boyScout = new Advancement();
        }

//        advancement.setName(txtName.getText());
    }

    private boolean validateFields() {
        boolean valid = true;

//        if (!validateAdvancementName()) {
//            valid = false;
//        }

        int advancementId;
        if (boyScout == null) {
            advancementId = -1;
        } else {
            advancementId = boyScout.getId();
        }

//        if (validateRequirements(advancementId, true) == null) {
//            valid = false;
//        }

        return valid;
    }

    private void btnUpdateActionPerformed() {
        if (listBoyScoutNames.getSelectedValue() == null) {
            return;
        }

        if (!validateFields()) {
            return;
        }

        setData();
//        boyScout = LogicAdvancement.update(boyScout);

        Set<Requirement> currentRequirementSet = LogicRequirement.findAllByParentIdAndTypeId(boyScout.getId(), RequirementTypeConst.ADVANCEMENT.getId());
//        Set<Requirement> newRequirementSet = getRequirementSet(advancement.getId());
        Set<Requirement> deleteRequirementSet = new LinkedHashSet<>();

//        if (newRequirementSet.isEmpty()) {
//            for (Requirement requirement : currentRequirementSet) {
//                deleteRequirementSet.add(requirement);
//            }
//        } else {
//            for (Requirement requirement : currentRequirementSet) {
//                boolean addToList = true;
//                for (Requirement newRequirement : newRequirementSet) {
//                    if (newRequirement.getId() > 0 && newRequirement.getId() == requirement.getId()) {
//                        addToList = false;
//                    }
//                }
//                if (addToList) {
//                    deleteRequirementSet.add(requirement);
//                }
//            }
//        }
//
//        for (Requirement deleteRequirement :  deleteRequirementSet) {
//            LogicRequirement.delete(deleteRequirement);
//        }
//
//        for (Requirement requirement : newRequirementSet) {
//            if (requirement.getId() > 0) {
//                LogicRequirement.update(requirement);
//            } else {
//                LogicRequirement.save(requirement);
//            }
//        }


//        CacheObject.addToAdvancements(boyScout);
        populateAdvancementNameList();

        listBoyScoutNames.setSelectedValue(boyScout.getName(), true);
    }

//    private Set<Requirement> getRequirementSet(int parentId) {
//        Set<Requirement> requirementSet = new LinkedHashSet<>();
//
//        for (Component component : pnlRequirementList.getComponents()) {
//            if (!(component instanceof PnlRequirement)) {
//                continue;
//            }
//
//            Requirement requirement = new Requirement();
//            if (parentId > 0) {
//                requirement.setParentId(parentId);
//            }
//            requirement.setName(((PnlRequirement)component).getName());
//            requirement.setDescription(((PnlRequirement) component).getDescription());
//            requirement.setId(((PnlRequirement) component).getReqId());
//            requirement.setTypeId(RequirementTypeConst.ADVANCEMENT.getId());
//
//            requirementSet.add(requirement);
//        }
//
//        return requirementSet;
//    }

    private void btnDeleteActionPerformed() {
        if (listBoyScoutNames.getSelectedValue() == null) {
            return;
        }

        int advancementId = boyScout.getId();

//        Set<Requirement> requirementSet = validateRequirements(advancement.getId(), false);
//        LogicRequirement.delete(requirementSet);
//        LogicAdvancement.delete(boyScout);

        CacheObject.removeFromAdvancements(advancementId);

        populateAdvancementNameList();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        clearAllErrors();
        clearData();
        enableControls(false);
    }

    private void changeToHand() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setDefaultCursor() {
        setCursor(Cursor.getDefaultCursor());
    }

    private void btnBadgeImageMouseReleased() {
//        if (!btnBadgeImage.isEnabled()) {
//            return;
//        }

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files(*.jpg, *.png, *.gif, *.jpeg)", "jpg", "png", "gif", "jpeg");

        CustomChooser chooser = new CustomChooser();
        chooser.setDialogTitle("Select an image");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
        int returnValue = chooser.showOpenDialog(this);
        chooser.resetLookAndFeel();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            setImage(file.getPath());
        }
    }

    private void setImage(String imgPath) {
//        try {
//            BufferedImage img = ImageIO.read(new File(imgPath));
//
//            int height = img.getHeight() > btnBadgeImage.getHeight() ? btnBadgeImage.getHeight() : img.getHeight();
//            int width = img.getWidth() > btnBadgeImage.getWidth() ? btnBadgeImage.getWidth() : img.getWidth();
//
//            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
//            if (icon.getImage() == null) {
//                btnBadgeImage.setIcon(noImage);
//                imagePath = "";
//            } else {
//                btnBadgeImage.setIcon(icon);
//                imagePath = imgPath;
//            }
//        } catch (IOException ignore) {
//        }
    }

    private void validatePhoneNumber() {
        validatePhoneNum();
    }

    private void validateGuardianPhoneNumber() {
        validateGuardianPhoneNum();
    }

    private boolean validateGuardianPhoneNum() {
        Util.clearError(lblGuardianPhoneError);

        if (txtGuardianPhone.isMessageDefault() || txtGuardianPhone.getText().isEmpty()) {
            return true;
        }

        if (!Util.validatePhoneNumber(txtGuardianPhone.getText())) {
            Util.setError(lblGuardianPhoneError, "Invalid phone number format");
            return false;
        }

        return true;
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

//    private void validateName() {
//        validateAdvancementName();
//    }

//    private boolean validateAdvancementName() {
//        Util.clearError(lblNameError);
//
//        if (txtName.isMessageDefault() || txtName.getText().trim().isEmpty()) {
//            Util.setError(lblNameError, "Name cannot be left blank");
//            return false;
//        }
//
//        Advancement tempAdvancement = CacheObject.getAdvancement(txtName.getText());
//        if (tempAdvancement == null) {
//            return true;
//        }
//
//        if (advancement == null) {
//            Util.setError(lblNameError, "An advancement with the name '" + txtName.getText() + "' already exists");
//            return false;
//        }
//
//        if (!tempAdvancement.getName().equals(advancement.getName())) {
//            Util.setError(lblNameError, "An advancement with the name '" + txtName.getText() + "' already exists");
//            return false;
//        }
//
//        return true;
//    }

//    private void btnAddRequirementMouseReleased() {
//        if (!btnAddRequirement.isEnabled()) {
//            return;
//        }
//
//        PnlRequirement pnlRequirement = new PnlRequirement("[name]", "[description]", pnlRequirementList.getComponentCount() > 0, -1);
//        pnlRequirementList.add(pnlRequirement);
//
//        pnlRequirement.getTxtReqName().requestFocus();
//
//        pnlRequirementList.revalidate();
//    }

//    private void btnRemoveRequirementMouseReleased() {
//        if (!btnRemoveRequirement.isEnabled() || pnlRequirementList.getComponentCount() == 0 || !(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getParent() instanceof PnlRequirement)) {
//            return;
//        }
//
//        pnlRequirementList.remove(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getParent());
//        pnlRequirementList.revalidate();
//        pnlRequirementList.repaint();
//    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        TitlePanel pnlTitle = new TitlePanel();
        JPanel pnlContents = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        JScrollPane scrollPane1 = new JScrollPane();
        listBoyScoutNames = new JList();
        JPanel panel3 = new JPanel();
        scrollPane2 = new JScrollPane();
        JPanel panel4 = new JPanel();
        tabPnlContent = new JTabbedPane();
        pnlSummary = new JPanel();
        pnlGeneral = new JPanel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblPosition = new JLabel();
        txtPosition = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblRank = new JLabel();
        cboRank = new JComboBox();
        lblRankDate = new JLabel();
        cboRankDate = new JDatePicker();
        lblRankDateError = new JLabel();
        lblBirthDate = new JLabel();
        cboBirthDate = new JDatePicker();
        lblAge = new JLabel();
        lblAgeValue = new JLabel();
        lblBirthDateError = new JLabel();
        lblTimeLeft = new JLabel();
        lblCampsAttended = new JLabel();
        barTimeLeft = new JProgressBar();
        barCampsAttended = new JProgressBar();
        lblTimeLeftDisplay = new JLabel();
        lblCampsAttendedDisplay = new JLabel();
        lblWaitPeriod = new JLabel();
        lblProgress = new JLabel();
        barWaitPeriod = new JProgressBar();
        barProgress = new JProgressBar();
        lblWaitPeriodDisplay = new JLabel();
        lblProgressDisplay = new JLabel();
        lblProgressTable = new JLabel();
        scrollPane3 = new JScrollPane();
        tblProgress = new JTable();
        pnlContact = new JPanel();
        JLabel lblContactInfo = new JLabel();
        JLabel lblPhoneNumber = new JLabel();
        txtPhoneNumber = new JTextFieldDefaultText();
        lblPhoneNumberError = new JLabel();
        JLabel lblCity = new JLabel();
        txtCity = new JTextFieldDefaultText();
        JLabel lblState = new JLabel();
        txtState = new JTextFieldDefaultText();
        JLabel lblStreet = new JLabel();
        txtStreet = new JTextFieldDefaultText();
        JLabel lblZip = new JLabel();
        txtZip = new JTextFieldDefaultText();
        JLabel lblGuardianName = new JLabel();
        txtGuardianName = new JTextFieldDefaultText();
        JLabel lblGuardianPhone = new JLabel();
        txtGuardianPhone = new JTextFieldDefaultText();
        lblGuardianPhoneError = new JLabel();
        JLabel lblNotes = new JLabel();
        scrollPane4 = new JScrollPane();
        txtNotes = new JTextArea();
        pnlDetails = new JPanel();
        pnlDetailContents = new JPanel();
        JPanel panel8 = new JPanel();
        JLabel lblSpecialAwards = new JLabel();
        btnAddAward = new JLabel();
        btnRemoveAward = new JLabel();
        lblRequirementError = new JLabel();
        scrollPane5 = new JScrollPane();
        tblSpecialAwards = new JTable();
        JPanel panel9 = new JPanel();
        JLabel lblMeritBadges = new JLabel();
        btnAddMeritBadge = new JLabel();
        btnRemoveMeritBadge = new JLabel();
        lblRequirementError2 = new JLabel();
        JPanel panel10 = new JPanel();
        JLabel lblCamps = new JLabel();
        btnAddCamp = new JLabel();
        btnRemoveCamp = new JLabel();
        lblRequirementError3 = new JLabel();
        scrollPane6 = new JScrollPane();
        listMeritBadges = new JList();
        scrollPane7 = new JScrollPane();
        listCamps = new JList();
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
        pnlTitle.setTitle("Boy Scouts");
        pnlTitle.setImagePath("/images/fleurdelis48.jpg");
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
                    pnlSearch.setMaximumSize(new Dimension(190, 2147483647));
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

                    //---- listBoyScoutNames ----
                    listBoyScoutNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listBoyScoutNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listBoyScoutNames.setName("listBoyScoutNames");
                    listBoyScoutNames.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            listBoyScoutNamesKeyReleased(e);
                        }
                    });
                    listBoyScoutNames.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            listBoyScoutNamesMouseReleased();
                        }
                    });
                    scrollPane1.setViewportView(listBoyScoutNames);
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
                        panel4.setBackground(new Color(202, 183, 160));
                        panel4.setName("panel4");
                        panel4.setLayout(new BorderLayout());

                        //======== tabPnlContent ========
                        {
                            tabPnlContent.setBackground(Color.white);
                            tabPnlContent.setForeground(Color.black);
                            tabPnlContent.setFont(new Font("Tahoma", Font.PLAIN, 18));
                            tabPnlContent.setName("tabPnlContent");

                            //======== pnlSummary ========
                            {
                                pnlSummary.setBackground(Color.white);
                                pnlSummary.setBorder(null);
                                pnlSummary.setName("pnlSummary");
                                pnlSummary.setLayout(new GridBagLayout());
                                ((GridBagLayout)pnlSummary.getLayout()).columnWidths = new int[] {0, 0};
                                ((GridBagLayout)pnlSummary.getLayout()).rowHeights = new int[] {0, 0};
                                ((GridBagLayout)pnlSummary.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                                ((GridBagLayout)pnlSummary.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                //======== pnlGeneral ========
                                {
                                    pnlGeneral.setOpaque(false);
                                    pnlGeneral.setName("pnlGeneral");
                                    pnlGeneral.setLayout(new GridBagLayout());
                                    ((GridBagLayout)pnlGeneral.getLayout()).columnWidths = new int[] {0, 207, 36, 0, 0, 195, 0};
                                    ((GridBagLayout)pnlGeneral.getLayout()).rowHeights = new int[] {35, 0, 35, 0, 35, 0, 24, 25, 0, 35, 25, 0, 35, 201, 0};
                                    ((GridBagLayout)pnlGeneral.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                                    ((GridBagLayout)pnlGeneral.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                                    //---- lblName ----
                                    lblName.setText("Name:");
                                    lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblName.setForeground(Color.black);
                                    lblName.setName("lblName");
                                    pnlGeneral.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- txtName ----
                                    txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    txtName.setDefaultText("Scout Name");
                                    txtName.setName("txtName");
                                    pnlGeneral.add(txtName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblPosition ----
                                    lblPosition.setText("Position:");
                                    lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblPosition.setForeground(Color.black);
                                    lblPosition.setName("lblPosition");
                                    pnlGeneral.add(lblPosition, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- txtPosition ----
                                    txtPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    txtPosition.setDefaultText("Troop Position");
                                    txtPosition.setName("txtPosition");
                                    pnlGeneral.add(txtPosition, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblNameError ----
                                    lblNameError.setText("*error message");
                                    lblNameError.setForeground(Color.red);
                                    lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblNameError.setName("lblNameError");
                                    pnlGeneral.add(lblNameError, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 5), 0, 0));

                                    //---- lblRank ----
                                    lblRank.setText("Rank:");
                                    lblRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblRank.setForeground(Color.black);
                                    lblRank.setName("lblRank");
                                    pnlGeneral.add(lblRank, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboRank ----
                                    cboRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboRank.setBackground(Color.white);
                                    cboRank.setForeground(Color.black);
                                    cboRank.setName("cboRank");
                                    pnlGeneral.add(cboRank, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblRankDate ----
                                    lblRankDate.setText("Rank Date:");
                                    lblRankDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblRankDate.setForeground(Color.black);
                                    lblRankDate.setName("lblRankDate");
                                    pnlGeneral.add(lblRankDate, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboRankDate ----
                                    cboRankDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboRankDate.setMaximumSize(new Dimension(32822, 30));
                                    cboRankDate.setMinimumSize(new Dimension(57, 30));
                                    cboRankDate.setName("cboRankDate");
                                    pnlGeneral.add(cboRankDate, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblRankDateError ----
                                    lblRankDateError.setText("*error message");
                                    lblRankDateError.setForeground(Color.red);
                                    lblRankDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblRankDateError.setName("lblRankDateError");
                                    pnlGeneral.add(lblRankDateError, new GridBagConstraints(4, 3, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 0), 0, 0));

                                    //---- lblBirthDate ----
                                    lblBirthDate.setText("Birth Date:");
                                    lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblBirthDate.setForeground(Color.black);
                                    lblBirthDate.setName("lblBirthDate");
                                    pnlGeneral.add(lblBirthDate, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboBirthDate ----
                                    cboBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboBirthDate.setMaximumSize(new Dimension(32822, 30));
                                    cboBirthDate.setMinimumSize(new Dimension(57, 30));
                                    cboBirthDate.setName("cboBirthDate");
                                    pnlGeneral.add(cboBirthDate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblAge ----
                                    lblAge.setText("Age:");
                                    lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblAge.setForeground(Color.black);
                                    lblAge.setName("lblAge");
                                    pnlGeneral.add(lblAge, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 10, 5, 5), 0, 0));

                                    //---- lblAgeValue ----
                                    lblAgeValue.setText("12");
                                    lblAgeValue.setFont(new Font("Tahoma", Font.BOLD, 14));
                                    lblAgeValue.setForeground(new Color(32, 154, 26));
                                    lblAgeValue.setName("lblAgeValue");
                                    pnlGeneral.add(lblAgeValue, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblBirthDateError ----
                                    lblBirthDateError.setText("*error message");
                                    lblBirthDateError.setForeground(Color.red);
                                    lblBirthDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblBirthDateError.setName("lblBirthDateError");
                                    pnlGeneral.add(lblBirthDateError, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 5), 0, 0));

                                    //---- lblTimeLeft ----
                                    lblTimeLeft.setText("Time Left in Boy Scouts");
                                    lblTimeLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblTimeLeft.setForeground(Color.black);
                                    lblTimeLeft.setName("lblTimeLeft");
                                    pnlGeneral.add(lblTimeLeft, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblCampsAttended ----
                                    lblCampsAttended.setText("Number of Camps Attended");
                                    lblCampsAttended.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblCampsAttended.setForeground(Color.black);
                                    lblCampsAttended.setName("lblCampsAttended");
                                    pnlGeneral.add(lblCampsAttended, new GridBagConstraints(4, 6, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- barTimeLeft ----
                                    barTimeLeft.setValue(50);
                                    barTimeLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    barTimeLeft.setName("barTimeLeft");
                                    pnlGeneral.add(barTimeLeft, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- barCampsAttended ----
                                    barCampsAttended.setValue(50);
                                    barCampsAttended.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    barCampsAttended.setName("barCampsAttended");
                                    pnlGeneral.add(barCampsAttended, new GridBagConstraints(4, 7, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblTimeLeftDisplay ----
                                    lblTimeLeftDisplay.setText("1 yr, 6 mon, 2 days");
                                    lblTimeLeftDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblTimeLeftDisplay.setForeground(Color.black);
                                    lblTimeLeftDisplay.setName("lblTimeLeftDisplay");
                                    pnlGeneral.add(lblTimeLeftDisplay, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblCampsAttendedDisplay ----
                                    lblCampsAttendedDisplay.setText("11 of 21");
                                    lblCampsAttendedDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblCampsAttendedDisplay.setForeground(Color.black);
                                    lblCampsAttendedDisplay.setName("lblCampsAttendedDisplay");
                                    pnlGeneral.add(lblCampsAttendedDisplay, new GridBagConstraints(4, 8, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblWaitPeriod ----
                                    lblWaitPeriod.setText("Waiting Period for Next Advancement");
                                    lblWaitPeriod.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblWaitPeriod.setForeground(Color.black);
                                    lblWaitPeriod.setName("lblWaitPeriod");
                                    pnlGeneral.add(lblWaitPeriod, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblProgress ----
                                    lblProgress.setText("Progress Towards Next Advancement");
                                    lblProgress.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblProgress.setForeground(Color.black);
                                    lblProgress.setName("lblProgress");
                                    pnlGeneral.add(lblProgress, new GridBagConstraints(4, 9, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- barWaitPeriod ----
                                    barWaitPeriod.setValue(50);
                                    barWaitPeriod.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    barWaitPeriod.setName("barWaitPeriod");
                                    pnlGeneral.add(barWaitPeriod, new GridBagConstraints(0, 10, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- barProgress ----
                                    barProgress.setValue(60);
                                    barProgress.setName("barProgress");
                                    pnlGeneral.add(barProgress, new GridBagConstraints(4, 10, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblWaitPeriodDisplay ----
                                    lblWaitPeriodDisplay.setText("2 mo, 3 days");
                                    lblWaitPeriodDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblWaitPeriodDisplay.setForeground(Color.black);
                                    lblWaitPeriodDisplay.setName("lblWaitPeriodDisplay");
                                    pnlGeneral.add(lblWaitPeriodDisplay, new GridBagConstraints(0, 11, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblProgressDisplay ----
                                    lblProgressDisplay.setText("6 of 10 Requirements");
                                    lblProgressDisplay.setForeground(Color.black);
                                    lblProgressDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblProgressDisplay.setName("lblProgressDisplay");
                                    pnlGeneral.add(lblProgressDisplay, new GridBagConstraints(4, 11, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblProgressTable ----
                                    lblProgressTable.setText("Progress of Current Advancement");
                                    lblProgressTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblProgressTable.setForeground(Color.black);
                                    lblProgressTable.setName("lblProgressTable");
                                    pnlGeneral.add(lblProgressTable, new GridBagConstraints(0, 12, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //======== scrollPane3 ========
                                    {
                                        scrollPane3.setName("scrollPane3");

                                        //---- tblProgress ----
                                        tblProgress.setPreferredScrollableViewportSize(new Dimension(450, 150));
                                        tblProgress.setMaximumSize(new Dimension(2147483647, 200));
                                        tblProgress.setName("tblProgress");
                                        scrollPane3.setViewportView(tblProgress);
                                    }
                                    pnlGeneral.add(scrollPane3, new GridBagConstraints(0, 13, 6, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 10, 0), 0, 0));
                                }
                                pnlSummary.add(pnlGeneral, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(15, 10, 0, 10), 0, 0));
                            }
                            tabPnlContent.addTab("Summary", pnlSummary);

                            //======== pnlContact ========
                            {
                                pnlContact.setBackground(Color.white);
                                pnlContact.setName("pnlContact");
                                pnlContact.setLayout(new GridBagLayout());
                                ((GridBagLayout)pnlContact.getLayout()).columnWidths = new int[] {0, 188, 83, 183, 0};
                                ((GridBagLayout)pnlContact.getLayout()).rowHeights = new int[] {0, 35, 0, 35, 35, 35, 0, 0, 313, 0};
                                ((GridBagLayout)pnlContact.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                                ((GridBagLayout)pnlContact.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                                //---- lblContactInfo ----
                                lblContactInfo.setText("Contact Information:");
                                lblContactInfo.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                lblContactInfo.setForeground(new Color(51, 102, 153));
                                lblContactInfo.setName("lblContactInfo");
                                pnlContact.add(lblContactInfo, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 8, 5, 5), 0, 0));

                                //---- lblPhoneNumber ----
                                lblPhoneNumber.setText("Phone Number:");
                                lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblPhoneNumber.setForeground(Color.black);
                                lblPhoneNumber.setName("lblPhoneNumber");
                                pnlContact.add(lblPhoneNumber, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                                    new Insets(0, 15, 5, 5), 0, 0));

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
                                pnlContact.add(txtPhoneNumber, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblPhoneNumberError ----
                                lblPhoneNumberError.setText("*error message");
                                lblPhoneNumberError.setForeground(Color.red);
                                lblPhoneNumberError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                lblPhoneNumberError.setName("lblPhoneNumberError");
                                pnlContact.add(lblPhoneNumberError, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 20, 5, 5), 0, 0));

                                //---- lblCity ----
                                lblCity.setText("City:");
                                lblCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblCity.setForeground(Color.black);
                                lblCity.setName("lblCity");
                                pnlContact.add(lblCity, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtCity ----
                                txtCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtCity.setDefaultText("Eagle Mtn");
                                txtCity.setName("txtCity");
                                pnlContact.add(txtCity, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblState ----
                                lblState.setText("State:");
                                lblState.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblState.setForeground(Color.black);
                                lblState.setName("lblState");
                                pnlContact.add(lblState, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtState ----
                                txtState.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtState.setDefaultText("Utah");
                                txtState.setName("txtState");
                                pnlContact.add(txtState, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- lblStreet ----
                                lblStreet.setText("Street:");
                                lblStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblStreet.setForeground(Color.black);
                                lblStreet.setName("lblStreet");
                                pnlContact.add(lblStreet, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtStreet ----
                                txtStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtStreet.setDefaultText("100 South 100 West");
                                txtStreet.setName("txtStreet");
                                pnlContact.add(txtStreet, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblZip ----
                                lblZip.setText("Zip:");
                                lblZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblZip.setForeground(Color.black);
                                lblZip.setName("lblZip");
                                pnlContact.add(lblZip, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtZip ----
                                txtZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtZip.setDefaultText("12345");
                                txtZip.setName("txtZip");
                                pnlContact.add(txtZip, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- lblGuardianName ----
                                lblGuardianName.setText("Parent Name:");
                                lblGuardianName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblGuardianName.setForeground(Color.black);
                                lblGuardianName.setName("lblGuardianName");
                                pnlContact.add(lblGuardianName, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtGuardianName ----
                                txtGuardianName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtGuardianName.setDefaultText("Parent Name");
                                txtGuardianName.setName("txtGuardianName");
                                pnlContact.add(txtGuardianName, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblGuardianPhone ----
                                lblGuardianPhone.setText("Parent Phone Number:");
                                lblGuardianPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblGuardianPhone.setForeground(Color.black);
                                lblGuardianPhone.setName("lblGuardianPhone");
                                pnlContact.add(lblGuardianPhone, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtGuardianPhone ----
                                txtGuardianPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtGuardianPhone.setDefaultText("(123) 456-7890");
                                txtGuardianPhone.setName("txtGuardianPhone");
                                txtGuardianPhone.addFocusListener(new FocusAdapter() {
                                    @Override
                                    public void focusLost(FocusEvent e) {
                                        validateGuardianPhoneNumber();
                                    }
                                });
                                txtGuardianPhone.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyReleased(KeyEvent e) {
                                        validateGuardianPhoneNumber();
                                    }
                                });
                                pnlContact.add(txtGuardianPhone, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- lblGuardianPhoneError ----
                                lblGuardianPhoneError.setText("*error message");
                                lblGuardianPhoneError.setForeground(Color.red);
                                lblGuardianPhoneError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                lblGuardianPhoneError.setName("lblGuardianPhoneError");
                                pnlContact.add(lblGuardianPhoneError, new GridBagConstraints(2, 6, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 20, 5, 0), 0, 0));

                                //---- lblNotes ----
                                lblNotes.setText("Notes:");
                                lblNotes.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                lblNotes.setForeground(new Color(51, 102, 153));
                                lblNotes.setName("lblNotes");
                                pnlContact.add(lblNotes, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 8, 5, 5), 0, 0));

                                //======== scrollPane4 ========
                                {
                                    scrollPane4.setName("scrollPane4");

                                    //---- txtNotes ----
                                    txtNotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    txtNotes.setBackground(Color.white);
                                    txtNotes.setForeground(Color.black);
                                    txtNotes.setName("txtNotes");
                                    scrollPane4.setViewportView(txtNotes);
                                }
                                pnlContact.add(scrollPane4, new GridBagConstraints(0, 8, 4, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 10, 0), 0, 0));
                            }
                            tabPnlContent.addTab("Contact Info", pnlContact);

                            //======== pnlDetails ========
                            {
                                pnlDetails.setBackground(Color.white);
                                pnlDetails.setName("pnlDetails");
                                pnlDetails.setLayout(new GridBagLayout());
                                ((GridBagLayout)pnlDetails.getLayout()).columnWidths = new int[] {0, 0};
                                ((GridBagLayout)pnlDetails.getLayout()).rowHeights = new int[] {560, 0};
                                ((GridBagLayout)pnlDetails.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                                ((GridBagLayout)pnlDetails.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                                //======== pnlDetailContents ========
                                {
                                    pnlDetailContents.setOpaque(false);
                                    pnlDetailContents.setName("pnlDetailContents");
                                    pnlDetailContents.setLayout(new GridBagLayout());
                                    ((GridBagLayout)pnlDetailContents.getLayout()).columnWidths = new int[] {330, 300, 0};
                                    ((GridBagLayout)pnlDetailContents.getLayout()).rowHeights = new int[] {0, 0, 64, 314, 0};
                                    ((GridBagLayout)pnlDetailContents.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                                    ((GridBagLayout)pnlDetailContents.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                                    //======== panel8 ========
                                    {
                                        panel8.setOpaque(false);
                                        panel8.setName("panel8");
                                        panel8.setLayout(new GridBagLayout());
                                        ((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                                        ((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                        ((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                        //---- lblSpecialAwards ----
                                        lblSpecialAwards.setText("Special Awards");
                                        lblSpecialAwards.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                        lblSpecialAwards.setForeground(new Color(51, 102, 153));
                                        lblSpecialAwards.setName("lblSpecialAwards");
                                        panel8.add(lblSpecialAwards, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(3, 0, 0, 5), 0, 0));

                                        //---- btnAddAward ----
                                        btnAddAward.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                        btnAddAward.setToolTipText("Add a new award");
                                        btnAddAward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnAddAward.setName("btnAddAward");
                                        panel8.add(btnAddAward, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnRemoveAward ----
                                        btnRemoveAward.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                        btnRemoveAward.setToolTipText("Remove selected award");
                                        btnRemoveAward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnRemoveAward.setName("btnRemoveAward");
                                        panel8.add(btnRemoveAward, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- lblRequirementError ----
                                        lblRequirementError.setText("* Error Message");
                                        lblRequirementError.setForeground(Color.red);
                                        lblRequirementError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                        lblRequirementError.setVisible(false);
                                        lblRequirementError.setName("lblRequirementError");
                                        panel8.add(lblRequirementError, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 10, 0, 0), 0, 0));
                                    }
                                    pnlDetailContents.add(panel8, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== scrollPane5 ========
                                    {
                                        scrollPane5.setName("scrollPane5");

                                        //---- tblSpecialAwards ----
                                        tblSpecialAwards.setPreferredScrollableViewportSize(new Dimension(450, 100));
                                        tblSpecialAwards.setName("tblSpecialAwards");
                                        scrollPane5.setViewportView(tblSpecialAwards);
                                    }
                                    pnlDetailContents.add(scrollPane5, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== panel9 ========
                                    {
                                        panel9.setOpaque(false);
                                        panel9.setName("panel9");
                                        panel9.setLayout(new GridBagLayout());
                                        ((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                                        ((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                        ((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                        //---- lblMeritBadges ----
                                        lblMeritBadges.setText("Merit Badges");
                                        lblMeritBadges.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                        lblMeritBadges.setForeground(new Color(51, 102, 153));
                                        lblMeritBadges.setName("lblMeritBadges");
                                        panel9.add(lblMeritBadges, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(3, 0, 0, 5), 0, 0));

                                        //---- btnAddMeritBadge ----
                                        btnAddMeritBadge.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                        btnAddMeritBadge.setToolTipText("Add a merit badge");
                                        btnAddMeritBadge.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnAddMeritBadge.setName("btnAddMeritBadge");
                                        panel9.add(btnAddMeritBadge, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnRemoveMeritBadge ----
                                        btnRemoveMeritBadge.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                        btnRemoveMeritBadge.setToolTipText("Remove selected merit badge");
                                        btnRemoveMeritBadge.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnRemoveMeritBadge.setName("btnRemoveMeritBadge");
                                        panel9.add(btnRemoveMeritBadge, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- lblRequirementError2 ----
                                        lblRequirementError2.setText("* Error Message");
                                        lblRequirementError2.setForeground(Color.red);
                                        lblRequirementError2.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                        lblRequirementError2.setVisible(false);
                                        lblRequirementError2.setName("lblRequirementError2");
                                        panel9.add(lblRequirementError2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 10, 0, 0), 0, 0));
                                    }
                                    pnlDetailContents.add(panel9, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 30), 0, 0));

                                    //======== panel10 ========
                                    {
                                        panel10.setOpaque(false);
                                        panel10.setName("panel10");
                                        panel10.setLayout(new GridBagLayout());
                                        ((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                                        ((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                        ((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                        //---- lblCamps ----
                                        lblCamps.setText("Camps");
                                        lblCamps.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                        lblCamps.setForeground(new Color(51, 102, 153));
                                        lblCamps.setName("lblCamps");
                                        panel10.add(lblCamps, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(3, 0, 0, 5), 0, 0));

                                        //---- btnAddCamp ----
                                        btnAddCamp.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                        btnAddCamp.setToolTipText("Add a camp");
                                        btnAddCamp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnAddCamp.setName("btnAddCamp");
                                        panel10.add(btnAddCamp, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnRemoveCamp ----
                                        btnRemoveCamp.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                        btnRemoveCamp.setToolTipText("Remove selected camp");
                                        btnRemoveCamp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnRemoveCamp.setName("btnRemoveCamp");
                                        panel10.add(btnRemoveCamp, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- lblRequirementError3 ----
                                        lblRequirementError3.setText("* Error Message");
                                        lblRequirementError3.setForeground(Color.red);
                                        lblRequirementError3.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                        lblRequirementError3.setVisible(false);
                                        lblRequirementError3.setName("lblRequirementError3");
                                        panel10.add(lblRequirementError3, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 10, 0, 0), 0, 0));
                                    }
                                    pnlDetailContents.add(panel10, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== scrollPane6 ========
                                    {
                                        scrollPane6.setName("scrollPane6");

                                        //---- listMeritBadges ----
                                        listMeritBadges.setName("listMeritBadges");
                                        scrollPane6.setViewportView(listMeritBadges);
                                    }
                                    pnlDetailContents.add(scrollPane6, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 5, 0, 30), 0, 0));

                                    //======== scrollPane7 ========
                                    {
                                        scrollPane7.setName("scrollPane7");

                                        //---- listCamps ----
                                        listCamps.setName("listCamps");
                                        scrollPane7.setViewportView(listCamps);
                                    }
                                    pnlDetailContents.add(scrollPane7, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 5, 0, 0), 0, 0));
                                }
                                pnlDetails.add(pnlDetailContents, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(10, 10, 10, 10), 0, 0));
                            }
                            tabPnlContent.addTab("Details", pnlDetails);
                        }
                        panel4.add(tabPnlContent, BorderLayout.NORTH);
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
    private JList listBoyScoutNames;
    private JScrollPane scrollPane2;
    private JTabbedPane tabPnlContent;
    private JPanel pnlSummary;
    private JPanel pnlGeneral;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblPosition;
    private JTextFieldDefaultText txtPosition;
    private JLabel lblNameError;
    private JLabel lblRank;
    private JComboBox cboRank;
    private JLabel lblRankDate;
    private JDatePicker cboRankDate;
    private JLabel lblRankDateError;
    private JLabel lblBirthDate;
    private JDatePicker cboBirthDate;
    private JLabel lblAge;
    private JLabel lblAgeValue;
    private JLabel lblBirthDateError;
    private JLabel lblTimeLeft;
    private JLabel lblCampsAttended;
    private JProgressBar barTimeLeft;
    private JProgressBar barCampsAttended;
    private JLabel lblTimeLeftDisplay;
    private JLabel lblCampsAttendedDisplay;
    private JLabel lblWaitPeriod;
    private JLabel lblProgress;
    private JProgressBar barWaitPeriod;
    private JProgressBar barProgress;
    private JLabel lblWaitPeriodDisplay;
    private JLabel lblProgressDisplay;
    private JLabel lblProgressTable;
    private JScrollPane scrollPane3;
    private JTable tblProgress;
    private JPanel pnlContact;
    private JTextFieldDefaultText txtPhoneNumber;
    private JLabel lblPhoneNumberError;
    private JTextFieldDefaultText txtCity;
    private JTextFieldDefaultText txtState;
    private JTextFieldDefaultText txtStreet;
    private JTextFieldDefaultText txtZip;
    private JTextFieldDefaultText txtGuardianName;
    private JTextFieldDefaultText txtGuardianPhone;
    private JLabel lblGuardianPhoneError;
    private JScrollPane scrollPane4;
    private JTextArea txtNotes;
    private JPanel pnlDetails;
    private JPanel pnlDetailContents;
    private JLabel btnAddAward;
    private JLabel btnRemoveAward;
    private JLabel lblRequirementError;
    private JScrollPane scrollPane5;
    private JTable tblSpecialAwards;
    private JLabel btnAddMeritBadge;
    private JLabel btnRemoveMeritBadge;
    private JLabel lblRequirementError2;
    private JLabel btnAddCamp;
    private JLabel btnRemoveCamp;
    private JLabel lblRequirementError3;
    private JScrollPane scrollPane6;
    private JList listMeritBadges;
    private JScrollPane scrollPane7;
    private JList listCamps;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}