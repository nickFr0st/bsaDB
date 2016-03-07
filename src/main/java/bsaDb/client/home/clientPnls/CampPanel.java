/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.PnlRequirement;
import bsaDb.client.customComponents.TitlePanel;
import bsaDb.client.customComponents.jdatepicker.JDatePicker;
import bsaDb.client.home.dialogs.CounselorDialog;
import constants.RequirementTypeConst;
import objects.databaseObjects.Counselor;
import objects.databaseObjects.MeritBadge;
import objects.databaseObjects.Requirement;
import objects.objectLogic.LogicCounselor;
import objects.objectLogic.LogicMeritBadge;
import objects.objectLogic.LogicRequirement;
import util.CacheObject;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * @author User #2
 */
@SuppressWarnings({"unchecked", "RedundantCast"})
public class CampPanel extends JPanel {

    private Icon noImage;
    private MeritBadge meritBadge;
    private String imagePath;
    private DefaultTableModel tableModel;

    {
        imagePath = "";
    }

    public CampPanel() {
        initComponents();

        noImage = new ImageIcon(getClass().getResource("/images/no_image.png"));
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        scrollPane3.getVerticalScrollBar().setUnitIncrement(18);
        scrollPane2.getVerticalScrollBar().setUnitIncrement(18);

        populateMeritBadgeNameList();

        enableControls(false);
    }

    public void populateMeritBadgeNameList() {
        List<String> meritBadgeNameList = new ArrayList<>();
        for (MeritBadge meritBadge : CacheObject.getMeritBadgeList()) {
            meritBadgeNameList.add(meritBadge.getName());
        }

//        listMeritBadgeNames.setListData(Util.getSortedList(meritBadgeNameList));
//        listMeritBadgeNames.revalidate();
//        listMeritBadgeNames.repaint();
    }

    private void txtSearchNameKeyReleased() {
        List<String> meritBadgeNameList = new ArrayList<>();
        for (MeritBadge meritBadge : CacheObject.getMeritBadgeList()) {
            meritBadgeNameList.add(meritBadge.getName());
        }

        if (txtSearchName.isMessageDefault()) {
//            listMeritBadgeNames.setListData(Util.getSortedList(meritBadgeNameList));
//            listMeritBadgeNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<>();
        for (MeritBadge meritBadge : CacheObject.getMeritBadgeList()) {
            if (meritBadge.getName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(meritBadge.getName());
            }
        }

//        listMeritBadgeNames.setListData(Util.getSortedList(filteredList));
//        listMeritBadgeNames.revalidate();
    }

    private void listUserNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listUserNamesMouseReleased();
        }
    }

    private void listUserNamesMouseReleased() {
//        if (listMeritBadgeNames.getSelectedValue() == null) {
//            return;
//        }

        clearAllErrors();
        clearData();

//        meritBadge = CacheObject.getMeritBadge(listMeritBadgeNames.getSelectedValue().toString());
        loadData();
    }

    private void loadData() {
        if (meritBadge == null) {
            return;
        }

        enableControls(true);

        txtName.setText(meritBadge.getName());
//        chkRequiredForEagle.setSelected(meritBadge.isRequiredForEagle());

        ImageIcon tryPath = new ImageIcon(meritBadge.getImgPath());
        if (tryPath.getImageLoadStatus() < MediaTracker.COMPLETE) {
//            btnBadgeImage.setIcon(noImage);
        } else {
            setImage(meritBadge.getImgPath());
        }

        loadRequirements();

        loadCounselorTable();

        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnSave.setVisible(false);
    }

    private void loadCounselorTable() {
        clearCounselorTable();

        List<Counselor> counselorList = LogicCounselor.findAllByBadgeId(meritBadge.getId());
        if (!Util.isEmpty(counselorList)) {
            for (Counselor counselor : counselorList) {
                Object[] rowData = new Object[]{counselor.getName(), counselor.getPhoneNumber()};
                tableModel.addRow(rowData);
            }
        }
    }

    private void clearCounselorTable() {
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
    }

    private List<Counselor> validateCounselors(int badgeId, boolean validate) {
        List<Counselor> counselorList = new ArrayList<>();
        Set<String> counselorNameSet = new HashSet<>();

        if (tableModel.getRowCount() <= 0) {
            return counselorList;
        }

        for (int i = 0; i < tableModel.getRowCount(); ++i) {
            String counselorName = (String)tableModel.getValueAt(i, 0);

//            if (validate && Util.isEmpty(counselorName)) {
//                Util.setError(lblCounselorError, "Counselor name cannot be left blank");
//                return null;
//            }
//
//            if (validate && !counselorNameSet.add(counselorName)) {
//                Util.setError(lblCounselorError, "Counselor name '" + counselorName + "' already exists");
//                return null;
//            }
//
//            String phoneNumber = (String)tableModel.getValueAt(i, 1);
//            if (validate && Util.isEmpty(phoneNumber)) {
//                Util.setError(lblCounselorError, "Counselor phone number cannot be left blank");
//                return null;
//            }
//
//            if (validate && !Util.validatePhoneNumber(phoneNumber)) {
//                Util.setError(lblCounselorError, "Invalid phone number format: " + phoneNumber);
//                return null;
//            }

            Counselor counselor = LogicCounselor.findByNameAndBadgeId(counselorName, badgeId);
            if (counselor == null) {
                counselor = new Counselor();
                if (badgeId > 0) {
                    counselor.setBadgeId(badgeId);
                }
            }

            counselor.setName(counselorName);
            counselor.setPhoneNumber((String) tableModel.getValueAt(i, 1));

            counselorList.add(counselor);
        }

        return counselorList;
    }

    private void loadRequirements() {
        Set<Requirement> requirementSet = LogicRequirement.findAllByParentIdAndTypeId(meritBadge.getId(), RequirementTypeConst.MERIT_BADGE.getId());

        boolean firstAdded = false;
        for (Requirement requirement : requirementSet) {
            PnlRequirement pnlRequirement = new PnlRequirement(requirement.getName(), requirement.getDescription(), firstAdded, requirement.getId());
//            pnlRequirementList.add(pnlRequirement);

            if (!firstAdded) {
                firstAdded = true;
            }
        }

        scrollPane3.getViewport().setViewPosition(new Point(0, 0));

//        pnlRequirementList.revalidate();
//        pnlRequirementList.repaint();
    }

    private void enableControls(boolean enable) {
//        btnBadgeImage.setEnabled(enable);
//        txtName.setEnabled(enable);
//        btnAddRequirement.setEnabled(enable);
//        btnRemoveRequirement.setEnabled(enable);
//        btnAddCounselor.setEnabled(enable);
//        btnRemoveCounselor.setEnabled(enable);
//        tblCounselors.setEnabled(enable);
    }

    private void clearAllErrors() {
//        Util.clearError(lblNameError);
//        Util.clearError(lblRequirementError);
//        Util.clearError(lblCounselorError);
    }

    private void btnNewActionPerformed() {
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        enableControls(true);
        clearAllErrors();
        clearData();

        txtName.requestFocus();
    }

    private void clearData() {
        meritBadge = null;

//        btnBadgeImage.setIcon(noImage);
//        txtName.setDefault();
//        chkRequiredForEagle.setSelected(false);
//        imagePath = "";
//
//        pnlRequirementList.removeAll();
//        pnlRequirementList.repaint();

        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
    }

    private void btnSaveActionPerformed() {
        if (!validateFields()) {
            return;
        }

        setData();

        Set<Requirement> requirementSet = validateRequirements(-1, true);
        if (requirementSet == null) return;

        List<Counselor> counselorList = validateCounselors(-1, true);
        if (counselorList == null) return;

        saveRecords(requirementSet, true, counselorList);

        btnSave.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        populateMeritBadgeNameList();

//        listMeritBadgeNames.setSelectedValue(meritBadge.getName(), true);
    }

    private void saveRecords(Set<Requirement> requirementSet, boolean newMeritBadge, List<Counselor> counselorList) {
        meritBadge = LogicMeritBadge.save(meritBadge);

        if (newMeritBadge) {
            for (Requirement requirement : requirementSet) {
                requirement.setParentId(meritBadge.getId());
            }
        }
        LogicRequirement.save(requirementSet);

        for (Counselor counselor : counselorList) {
            counselor.setBadgeId(meritBadge.getId());
        }
        LogicCounselor.save(counselorList);

        CacheObject.addToMeritBadges(meritBadge);
    }

    private Set<Requirement> validateRequirements(int parentId, boolean validate) {
        Set<Requirement> requirementSet = new LinkedHashSet<>();
        Set<String> reqNameSet = new HashSet<>();

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
//            requirement.setTypeId(RequirementTypeConst.MERIT_BADGE.getId());
//
//            requirementSet.add(requirement);
//        }
        return requirementSet;
    }

    private void setData() {
        if (meritBadge == null) {
            meritBadge = new MeritBadge();
        }

        meritBadge.setName(txtName.getText());
//        meritBadge.setRequiredForEagle(chkRequiredForEagle.isSelected());
        if (Util.isEmpty(imagePath) || getImage() == null) {
            meritBadge.setImgPath("");
        } else {
            meritBadge.setImgPath(imagePath);
        }
    }

    private Image getImage() {
        return new ImageIcon(imagePath).getImage();
    }

    private boolean validateFields() {
        boolean valid = true;

        if (!validateMeritBadgeName()) {
            valid = false;
        }

        int meritBadgeId;
        if (meritBadge == null) {
            meritBadgeId = -1;
        } else {
            meritBadgeId = meritBadge.getId();
        }

        if (validateRequirements(meritBadgeId, true) == null) {
            valid = false;
        }

        if (validateCounselors(meritBadgeId, true) == null) {
            valid = false;
        }

        return valid;
    }

    private void btnUpdateActionPerformed() {
//        if (listMeritBadgeNames.getSelectedValue() == null) {
//            return;
//        }

        if (!validateFields()) {
            return;
        }

        setData();
        meritBadge = LogicMeritBadge.update(meritBadge);

        Set<Requirement> currentRequirementSet = LogicRequirement.findAllByParentIdAndTypeId(meritBadge.getId(), RequirementTypeConst.MERIT_BADGE.getId());
        List<Requirement> newRequirementList = getRequirementList(meritBadge.getId());
        List<Requirement> deleteRequirementList = new ArrayList<>();

        if (newRequirementList.isEmpty()) {
            deleteRequirementList.addAll(currentRequirementSet);
        } else {
            for (Requirement requirement : currentRequirementSet) {
                boolean addToList = true;
                for (Requirement newRequirement : newRequirementList) {
                    if (newRequirement.getId() > 0 && newRequirement.getId() == requirement.getId()) {
                        addToList = false;
                    }
                }
                if (addToList) {
                    deleteRequirementList.add(requirement);
                }
            }
        }

        for (Requirement deleteRequirement :  deleteRequirementList) {
            LogicRequirement.delete(deleteRequirement);
        }

        for (Requirement requirement : newRequirementList) {
            if (requirement.getId() > 0) {
                LogicRequirement.update(requirement);
            } else {
                LogicRequirement.save(requirement);
            }
        }

        List<Counselor> counselorList = validateCounselors(meritBadge.getId(), true);
        for (Counselor counselor : counselorList) {
            if (counselor.getId() > 0) {
                LogicCounselor.update(counselor);
            } else {
                LogicCounselor.save(counselor);
            }
        }

        CacheObject.addToMeritBadges(meritBadge);
        populateMeritBadgeNameList();

//        listMeritBadgeNames.setSelectedValue(meritBadge.getName(), true);
    }

    private List<Requirement> getRequirementList(int parentId) {
        List<Requirement> requirementList = new ArrayList<>();

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
//            requirement.setTypeId(RequirementTypeConst.MERIT_BADGE.getId());
//
//            requirementList.add(requirement);
//        }

        return requirementList;
    }

    private void btnDeleteActionPerformed() {
//        if (listMeritBadgeNames.getSelectedValue() == null) {
//            return;
//        }

        int meritBadgeId = meritBadge.getId();

        Set<Requirement> requirementSet = validateRequirements(meritBadgeId, false);
        List<Counselor> counselorList = getCounselorListForDeletion(meritBadgeId);

        LogicCounselor.delete(counselorList);
        LogicRequirement.delete(requirementSet);
        LogicMeritBadge.delete(meritBadge);

        CacheObject.removeFromMeritBadges(meritBadgeId);

        populateMeritBadgeNameList();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        clearAllErrors();
        clearData();
        enableControls(false);
    }

    private List<Counselor> getCounselorListForDeletion(int meritBadgeId) {
        List<Counselor> counselorList = new ArrayList<>();

        if (tableModel.getRowCount() <= 0) {
            return counselorList;
        }

        for (int i = 0; i < tableModel.getRowCount(); ++i) {
            String counselorName = (String)tableModel.getValueAt(i, 0);

            Counselor counselor = LogicCounselor.findByNameAndBadgeId(counselorName, meritBadgeId);
            if (counselor != null) {
                counselorList.add(counselor);
            }

        }

        return counselorList;
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

    private void validateName() {
        validateMeritBadgeName();
    }

    private boolean validateMeritBadgeName() {
//        Util.clearError(lblNameError);
//
//        if (txtName.isMessageDefault() || txtName.getText().trim().isEmpty()) {
//            Util.setError(lblNameError, "Name cannot be left blank");
//            return false;
//        }
//
//        MeritBadge tempMeritBadge = CacheObject.getMeritBadge(txtName.getText());
//        if (tempMeritBadge == null) {
//            return true;
//        }
//
//        if (meritBadge == null) {
//            Util.setError(lblNameError, "A merit badge with the name '" + txtName.getText() + "' already exists");
//            return false;
//        }
//
//        if (!tempMeritBadge.getName().equals(meritBadge.getName())) {
//            Util.setError(lblNameError, "A merit badge with the name '" + txtName.getText() + "' already exists");
//            return false;
//        }

        return true;
    }

    private void createUIComponents() {
        tableModel = new DefaultTableModel(new Object[] {"Name", "Phone Number"}, 0);

//        tblCounselors = new JTable();
//        tblCounselors.setBackground(Color.WHITE);
//
//        JTableHeader header = tblCounselors.getTableHeader();
//        header.setBackground(new Color(51, 102, 153));
//        header.setForeground(Color.WHITE);
//        header.setFont(new Font("Tahoma", Font.PLAIN, 14));
//
//
//        tblCounselors.setModel(tableModel);
//        tblCounselors.setSurrendersFocusOnKeystroke(true);
    }

    private void btnAddCounselorMouseReleased() {
        CounselorDialog dialog = new CounselorDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);

        if (dialog.getBtnChoice() == CounselorDialog.BTN_OK) {
            Counselor counselor = dialog.getCounselor();
            Object[] row = new Object[] {counselor.getName(), counselor.getPhoneNumber()};
            tableModel.addRow(row);
        }
    }

    private void btnRemoveCounselorMouseReleased() {
//        if (tblCounselors.getSelectedRow() < 0) {
//            return;
//        }
//
//        tableModel.removeRow(tblCounselors.getSelectedRow());
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
        panel1 = new JPanel();
        JLabel lblContactInfo2 = new JLabel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblCampType = new JLabel();
        cboCampType = new JComboBox();
        lblNameError = new JLabel();
        lblCampTypeError = new JLabel();
        lblLocation = new JLabel();
        txtLocation = new JTextFieldDefaultText();
        lblCampDate = new JLabel();
        cboCampDate = new JDatePicker();
        lblLocationError = new JLabel();
        lblCampDateError = new JLabel();
        lblLeaders = new JLabel();
        txtLeaders = new JTextFieldDefaultText();
        lblLeaderError = new JLabel();
        JLabel lblContactInfo = new JLabel();
        lblAttendanceError = new JLabel();
        lblName5 = new JLabel();
        lblName6 = new JLabel();
        scrollPane3 = new JScrollPane();
        list1 = new JList();
        scrollPane4 = new JScrollPane();
        list2 = new JList();
        JLabel lblContactInfo3 = new JLabel();
        scrollPane5 = new JScrollPane();
        txtNotes = new JTextArea();
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
                            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 35, 0, 35, 0, 35, 0, 0, 0, 0, 0, 123, 0};
                            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                            //---- lblContactInfo2 ----
                            lblContactInfo2.setText("General Information:");
                            lblContactInfo2.setFont(new Font("Vijaya", Font.PLAIN, 22));
                            lblContactInfo2.setForeground(new Color(51, 102, 153));
                            lblContactInfo2.setName("lblContactInfo2");
                            panel1.add(lblContactInfo2, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
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
                            panel1.add(cboCampType, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblNameError ----
                            lblNameError.setText("*error message");
                            lblNameError.setForeground(Color.red);
                            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblNameError.setName("lblNameError");
                            panel1.add(lblNameError, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 5), 0, 0));

                            //---- lblCampTypeError ----
                            lblCampTypeError.setText("*error message");
                            lblCampTypeError.setForeground(Color.red);
                            lblCampTypeError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblCampTypeError.setName("lblCampTypeError");
                            panel1.add(lblCampTypeError, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 0), 0, 0));

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
                            panel1.add(cboCampDate, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblLocationError ----
                            lblLocationError.setText("*error message");
                            lblLocationError.setForeground(Color.red);
                            lblLocationError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblLocationError.setName("lblLocationError");
                            panel1.add(lblLocationError, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 20, 5, 5), 0, 0));

                            //---- lblCampDateError ----
                            lblCampDateError.setText("*error message");
                            lblCampDateError.setForeground(Color.red);
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
                            panel1.add(txtLeaders, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblLeaderError ----
                            lblLeaderError.setText("*error message");
                            lblLeaderError.setForeground(Color.red);
                            lblLeaderError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblLeaderError.setName("lblLeaderError");
                            panel1.add(lblLeaderError, new GridBagConstraints(1, 6, 3, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 20, 5, 0), 0, 0));

                            //---- lblContactInfo ----
                            lblContactInfo.setText("Who Came:");
                            lblContactInfo.setFont(new Font("Vijaya", Font.PLAIN, 22));
                            lblContactInfo.setForeground(new Color(51, 102, 153));
                            lblContactInfo.setName("lblContactInfo");
                            panel1.add(lblContactInfo, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                            //---- lblAttendanceError ----
                            lblAttendanceError.setText("*error message");
                            lblAttendanceError.setForeground(Color.red);
                            lblAttendanceError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblAttendanceError.setName("lblAttendanceError");
                            panel1.add(lblAttendanceError, new GridBagConstraints(2, 7, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                    new Insets(0, 20, 5, 0), 0, 0));

                            //---- lblName5 ----
                            lblName5.setText("Name:");
                            lblName5.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblName5.setForeground(Color.black);
                            lblName5.setName("lblName5");
                            panel1.add(lblName5, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                            //---- lblName6 ----
                            lblName6.setText("Name:");
                            lblName6.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblName6.setForeground(Color.black);
                            lblName6.setName("lblName6");
                            panel1.add(lblName6, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                            //======== scrollPane3 ========
                            {
                                scrollPane3.setName("scrollPane3");

                                //---- list1 ----
                                list1.setName("list1");
                                scrollPane3.setViewportView(list1);
                            }
                            panel1.add(scrollPane3, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                            //======== scrollPane4 ========
                            {
                                scrollPane4.setName("scrollPane4");

                                //---- list2 ----
                                list2.setName("list2");
                                scrollPane4.setViewportView(list2);
                            }
                            panel1.add(scrollPane4, new GridBagConstraints(2, 9, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblContactInfo3 ----
                            lblContactInfo3.setText("Notes:");
                            lblContactInfo3.setFont(new Font("Vijaya", Font.PLAIN, 22));
                            lblContactInfo3.setForeground(new Color(51, 102, 153));
                            lblContactInfo3.setName("lblContactInfo3");
                            panel1.add(lblContactInfo3, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
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
                            panel1.add(scrollPane5, new GridBagConstraints(0, 11, 4, 1, 0.0, 0.0,
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
    private JList listCampoutNames;
    private JScrollPane scrollPane2;
    private JPanel panel1;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblCampType;
    private JComboBox cboCampType;
    private JLabel lblNameError;
    private JLabel lblCampTypeError;
    private JLabel lblLocation;
    private JTextFieldDefaultText txtLocation;
    private JLabel lblCampDate;
    private JDatePicker cboCampDate;
    private JLabel lblLocationError;
    private JLabel lblCampDateError;
    private JLabel lblLeaders;
    private JTextFieldDefaultText txtLeaders;
    private JLabel lblLeaderError;
    private JLabel lblAttendanceError;
    private JLabel lblName5;
    private JLabel lblName6;
    private JScrollPane scrollPane3;
    private JList list1;
    private JScrollPane scrollPane4;
    private JList list2;
    private JScrollPane scrollPane5;
    private JTextArea txtNotes;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
