/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.PnlRequirement;
import bsaDb.client.customComponents.TitlePanel;
import bsaDb.client.home.dialogs.CounselorDialog;
import constants.RequirementTypeConst;
import objects.databaseObjects.Counselor;
import objects.databaseObjects.MeritBadge;
import objects.databaseObjects.Requirement;
import objects.objectLogic.LogicCounselor;
import objects.objectLogic.LogicMeritBadge;
import objects.objectLogic.LogicRequirement;
import org.jdesktop.swingx.VerticalLayout;
import util.CacheObject;
import util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author User #2
 */
@SuppressWarnings({"unchecked", "RedundantCast"})
public class MeritBadgePanel extends JPanel {

    private Icon noImage;
    private MeritBadge meritBadge;
    private String imagePath;
    private DefaultTableModel tableModel;

    {
        imagePath = "";
    }

    public MeritBadgePanel() {
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

        listMeritBadgeNames.setListData(Util.getSortedList(meritBadgeNameList));
        listMeritBadgeNames.revalidate();
        listMeritBadgeNames.repaint();
    }

    private void txtSearchNameKeyReleased() {
        List<String> meritBadgeNameList = new ArrayList<>();
        for (MeritBadge meritBadge : CacheObject.getMeritBadgeList()) {
            meritBadgeNameList.add(meritBadge.getName());
        }

        if (txtSearchName.isMessageDefault()) {
            listMeritBadgeNames.setListData(Util.getSortedList(meritBadgeNameList));
            listMeritBadgeNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<>();
        for (MeritBadge meritBadge : CacheObject.getMeritBadgeList()) {
            if (meritBadge.getName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(meritBadge.getName());
            }
        }

        listMeritBadgeNames.setListData(Util.getSortedList(filteredList));
        listMeritBadgeNames.revalidate();
    }

    private void listUserNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listUserNamesMouseReleased();
        }
    }

    private void listUserNamesMouseReleased() {
        if (listMeritBadgeNames.getSelectedValue() == null) {
            return;
        }

        clearAllErrors();
        clearData();

        meritBadge = CacheObject.getMeritBadge(listMeritBadgeNames.getSelectedValue().toString());
        loadData();
    }

    private void loadData() {
        if (meritBadge == null) {
            return;
        }

        enableControls(true);

        txtName.setText(meritBadge.getName());
        chkRequiredForEagle.setSelected(meritBadge.isRequiredForEagle());

        ImageIcon tryPath = new ImageIcon(meritBadge.getImgPath());
        if (tryPath.getImageLoadStatus() < MediaTracker.COMPLETE) {
            btnBadgeImage.setIcon(noImage);
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

        java.util.List<Counselor> counselorList = CacheObject.getCounselorListByBadgeId(meritBadge.getId());
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

            if (validate && Util.isEmpty(counselorName)) {
                Util.setError(lblCounselorError, "Counselor name cannot be left blank");
                return null;
            }

            if (validate && !counselorNameSet.add(counselorName)) {
                Util.setError(lblCounselorError, "Counselor name '" + counselorName + "' already exists");
                return null;
            }

            String phoneNumber = (String)tableModel.getValueAt(i, 1);
            if (validate && Util.isEmpty(phoneNumber)) {
                Util.setError(lblCounselorError, "Counselor phone number cannot be left blank");
                return null;
            }

            if (validate && !Util.validatePhoneNumber(phoneNumber)) {
                Util.setError(lblCounselorError, "Invalid phone number format: " + phoneNumber);
                return null;
            }

            Counselor counselor = new Counselor();
            if (badgeId > 0) {
                counselor.setBadgeId(badgeId);
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
            pnlRequirementList.add(pnlRequirement);

            if (!firstAdded) {
                firstAdded = true;
            }
        }

        scrollPane3.getViewport().setViewPosition(new Point(0, 0));

        pnlRequirementList.revalidate();
        pnlRequirementList.repaint();
    }

    private void enableControls(boolean enable) {
        btnBadgeImage.setEnabled(enable);
        txtName.setEnabled(enable);
        btnAddRequirement.setEnabled(enable);
        btnRemoveRequirement.setEnabled(enable);
        btnAddCounselor.setEnabled(enable);
        btnRemoveCounselor.setEnabled(enable);
        tblCounselors.setEnabled(enable);
    }

    private void clearAllErrors() {
        Util.clearError(lblNameError);
        Util.clearError(lblRequirementError);
        Util.clearError(lblCounselorError);
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

        btnBadgeImage.setIcon(noImage);
        txtName.setDefault();
        chkRequiredForEagle.setSelected(false);
        imagePath = "";

        pnlRequirementList.removeAll();
        pnlRequirementList.repaint();

        tblCounselors.removeAll();
        tblCounselors.revalidate();
        tblCounselors.repaint();
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

        listMeritBadgeNames.setSelectedValue(meritBadge.getName(), true);
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
        CacheObject.addToCounselors(counselorList);
    }

    private Set<Requirement> validateRequirements(int parentId, boolean validate) {
        Set<Requirement> requirementSet = new LinkedHashSet<>();
        Set<String> reqNameSet = new HashSet<>();

        for (Component component : pnlRequirementList.getComponents()) {
            if (!(component instanceof PnlRequirement)) {
                continue;
            }

            if (!validate && ((PnlRequirement)component).getReqId() < 0) {
                continue;
            }

            String reqName = ((PnlRequirement)component).getName().trim();

            if (validate && reqName.isEmpty()) {
                Util.setError(lblRequirementError, "Requirement name cannot be left blank");
                return null;
            }

            if (validate && !reqNameSet.add(reqName)) {
                Util.setError(lblRequirementError, "Requirement name '" + reqName + "' already exists");
                component.requestFocus();
                return null;
            }

            if (validate && ((PnlRequirement)component).getDescription().trim().isEmpty()) {
                Util.setError(lblRequirementError, "Requirement description cannot be left blank");
                return null;
            }

            Requirement requirement = new Requirement();
            if (parentId > 0) {
                requirement.setParentId(parentId);
            }
            requirement.setName(((PnlRequirement)component).getName());
            requirement.setDescription(((PnlRequirement) component).getDescription());
            requirement.setId(((PnlRequirement) component).getReqId());
            requirement.setTypeId(RequirementTypeConst.MERIT_BADGE.getId());

            requirementSet.add(requirement);
        }
        return requirementSet;
    }

    private void setData() {
        if (meritBadge == null) {
            meritBadge = new MeritBadge();
        }

        meritBadge.setName(txtName.getText());
        meritBadge.setRequiredForEagle(chkRequiredForEagle.isSelected());
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
        if (listMeritBadgeNames.getSelectedValue() == null) {
            return;
        }

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

        // todo: may need to do something different with this
        List<Counselor> counselorList = validateCounselors(meritBadge.getId(), true);
        if (counselorList == null) return;


        CacheObject.addToMeritBadges(meritBadge);
        populateMeritBadgeNameList();

        listMeritBadgeNames.setSelectedValue(meritBadge.getName(), true);
    }

    private List<Requirement> getRequirementList(int parentId) {
        List<Requirement> requirementList = new ArrayList<>();

        for (Component component : pnlRequirementList.getComponents()) {
            if (!(component instanceof PnlRequirement)) {
                continue;
            }

            Requirement requirement = new Requirement();
            if (parentId > 0) {
                requirement.setParentId(parentId);
            }
            requirement.setName(((PnlRequirement)component).getName());
            requirement.setDescription(((PnlRequirement) component).getDescription());
            requirement.setId(((PnlRequirement) component).getReqId());
            requirement.setTypeId(RequirementTypeConst.MERIT_BADGE.getId());

            requirementList.add(requirement);
        }

        return requirementList;
    }

    private void btnDeleteActionPerformed() {
        if (listMeritBadgeNames.getSelectedValue() == null) {
            return;
        }

        int meritBadgeId = meritBadge.getId();

        Set<Requirement> requirementSet = validateRequirements(meritBadgeId, false);
        List<Counselor> counselorList = validateCounselors(meritBadgeId, false);


        // todo: counselor id is wrong
        LogicCounselor.delete(counselorList);
        LogicRequirement.delete(requirementSet);
        LogicMeritBadge.delete(meritBadge);

        CacheObject.removeFromMeritBadges(meritBadgeId);

        counselorList = CacheObject.getCounselorListByBadgeId(meritBadgeId);
        for (Counselor counselor : counselorList) {
            CacheObject.removeFromCounselors(counselor.getId());
        }

        populateMeritBadgeNameList();

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
        if (!btnBadgeImage.isEnabled()) {
            return;
        }

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
        try {
            BufferedImage img = ImageIO.read(new File(imgPath));

            int height = img.getHeight() > btnBadgeImage.getHeight() ? btnBadgeImage.getHeight() : img.getHeight();
            int width = img.getWidth() > btnBadgeImage.getWidth() ? btnBadgeImage.getWidth() : img.getWidth();

            ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            if (icon.getImage() == null) {
                btnBadgeImage.setIcon(noImage);
                imagePath = "";
            } else {
                btnBadgeImage.setIcon(icon);
                imagePath = imgPath;
            }
        } catch (IOException ignore) {
        }
    }

    private void validateName() {
        validateMeritBadgeName();
    }

    private boolean validateMeritBadgeName() {
        Util.clearError(lblNameError);

        if (txtName.isMessageDefault() || txtName.getText().trim().isEmpty()) {
            Util.setError(lblNameError, "Name cannot be left blank");
            return false;
        }

        MeritBadge tempMeritBadge = CacheObject.getMeritBadge(txtName.getText());
        if (tempMeritBadge == null) {
            return true;
        }

        if (meritBadge == null) {
            Util.setError(lblNameError, "A merit badge with the name '" + txtName.getText() + "' already exists");
            return false;
        }

        if (!tempMeritBadge.getName().equals(meritBadge.getName())) {
            Util.setError(lblNameError, "A merit badge with the name '" + txtName.getText() + "' already exists");
            return false;
        }

        return true;
    }

    private void btnAddRequirementMouseReleased() {
        if (!btnAddRequirement.isEnabled()) {
            return;
        }

        PnlRequirement pnlRequirement = new PnlRequirement("[name]", "[description]", pnlRequirementList.getComponentCount() > 0, -1);
        pnlRequirementList.add(pnlRequirement);

        pnlRequirement.getTxtReqName().requestFocus();

        pnlRequirementList.revalidate();
    }

    private void btnRemoveRequirementMouseReleased() {
        if (!btnRemoveRequirement.isEnabled() || pnlRequirementList.getComponentCount() == 0 || !(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getParent() instanceof PnlRequirement)) {
            return;
        }

        pnlRequirementList.remove(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getParent());
        pnlRequirementList.revalidate();
        pnlRequirementList.repaint();
    }

    private void createUIComponents() {
        tableModel = new DefaultTableModel(new Object[] {"Name", "Phone Number"}, 0);

        tblCounselors = new JTable();
        tblCounselors.setBackground(Color.WHITE);

        JTableHeader header = tblCounselors.getTableHeader();
        header.setBackground(new Color(51, 102, 153));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Tahoma", Font.PLAIN, 14));


        tblCounselors.setModel(tableModel);
        tblCounselors.setSurrendersFocusOnKeystroke(true);
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
        if (tblCounselors.getSelectedRow() < 0) {
            return;
        }

        tableModel.removeRow(tblCounselors.getSelectedRow());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        createUIComponents();

        TitlePanel pnlTitle = new TitlePanel();
        JPanel pnlContents = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        JScrollPane scrollPane1 = new JScrollPane();
        listMeritBadgeNames = new JList();
        JPanel panel3 = new JPanel();
        scrollPane2 = new JScrollPane();
        JPanel panel4 = new JPanel();
        JPanel panel1 = new JPanel();
        btnBadgeImage = new JLabel();
        JPanel panel6 = new JPanel();
        JLabel lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        chkRequiredForEagle = new JCheckBox();
        JPanel panel10 = new JPanel();
        lblCounselorError = new JLabel();
        JLabel lblCounselors = new JLabel();
        btnAddCounselor = new JLabel();
        btnRemoveCounselor = new JLabel();
        JPanel panel9 = new JPanel();
        JScrollPane scrollPane4 = new JScrollPane();
        JPanel panel7 = new JPanel();
        JPanel panel8 = new JPanel();
        JLabel lblRequirement = new JLabel();
        btnAddRequirement = new JLabel();
        btnRemoveRequirement = new JLabel();
        lblRequirementError = new JLabel();
        scrollPane3 = new JScrollPane();
        pnlRequirementList = new JPanel();
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
        pnlTitle.setTitle("Merit Badges");
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

                    //---- listMeritBadgeNames ----
                    listMeritBadgeNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listMeritBadgeNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listMeritBadgeNames.setName("listMeritBadgeNames");
                    listMeritBadgeNames.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            listUserNamesKeyReleased(e);
                        }
                    });
                    listMeritBadgeNames.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            listUserNamesMouseReleased();
                        }
                    });
                    scrollPane1.setViewportView(listMeritBadgeNames);
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
                        ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {270, 22, 0};
                        ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {191, 0, 0};
                        ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                        ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                        //======== panel1 ========
                        {
                            panel1.setPreferredSize(new Dimension(145, 170));
                            panel1.setBackground(new Color(204, 204, 204));
                            panel1.setBorder(new LineBorder(new Color(51, 102, 153)));
                            panel1.setName("panel1");
                            panel1.setLayout(new GridBagLayout());
                            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
                            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
                            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                            //---- btnBadgeImage ----
                            btnBadgeImage.setPreferredSize(new Dimension(128, 128));
                            btnBadgeImage.setIcon(new ImageIcon(getClass().getResource("/images/no_image.png")));
                            btnBadgeImage.setToolTipText("click to upload an image here");
                            btnBadgeImage.setName("btnBadgeImage");
                            btnBadgeImage.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseReleased(MouseEvent e) {
                                    btnBadgeImageMouseReleased();
                                }
                            });
                            panel1.add(btnBadgeImage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        panel4.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.NONE,
                            new Insets(0, 0, 10, 20), 0, 0));

                        //======== panel6 ========
                        {
                            panel6.setOpaque(false);
                            panel6.setName("panel6");
                            panel6.setLayout(new GridBagLayout());
                            ((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 215, 0};
                            ((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0, 43, 0, 0};
                            ((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                            ((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                            //---- lblName ----
                            lblName.setText("Name:");
                            lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblName.setForeground(Color.black);
                            lblName.setName("lblName");
                            panel6.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 10, 5, 5), 0, 0));

                            //---- txtName ----
                            txtName.setDefaultText("Merit Badge Name");
                            txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            txtName.setPreferredSize(new Dimension(138, 30));
                            txtName.setMinimumSize(new Dimension(14, 30));
                            txtName.setName("txtName");
                            txtName.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    validateName();
                                }
                            });
                            txtName.addFocusListener(new FocusAdapter() {
                                @Override
                                public void focusLost(FocusEvent e) {
                                    validateName();
                                }
                            });
                            panel6.add(txtName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //---- lblNameError ----
                            lblNameError.setText("* Error Message");
                            lblNameError.setForeground(Color.red);
                            lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                            lblNameError.setVisible(false);
                            lblNameError.setName("lblNameError");
                            panel6.add(lblNameError, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 10, 5, 0), 0, 0));

                            //---- chkRequiredForEagle ----
                            chkRequiredForEagle.setText("Required for Eagle");
                            chkRequiredForEagle.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            chkRequiredForEagle.setForeground(Color.black);
                            chkRequiredForEagle.setOpaque(false);
                            chkRequiredForEagle.setName("chkRequiredForEagle");
                            panel6.add(chkRequiredForEagle, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 5, 5, 0), 0, 0));

                            //======== panel10 ========
                            {
                                panel10.setOpaque(false);
                                panel10.setName("panel10");
                                panel10.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                                ((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0, 0};
                                ((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                ((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                                //---- lblCounselorError ----
                                lblCounselorError.setText("* Error Message");
                                lblCounselorError.setForeground(Color.red);
                                lblCounselorError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                lblCounselorError.setVisible(false);
                                lblCounselorError.setName("lblCounselorError");
                                panel10.add(lblCounselorError, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 10, 5, 0), 0, 0));

                                //---- lblCounselors ----
                                lblCounselors.setText("Counselors");
                                lblCounselors.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                lblCounselors.setForeground(new Color(51, 102, 153));
                                lblCounselors.setName("lblCounselors");
                                panel10.add(lblCounselors, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                    new Insets(0, 10, 0, 5), 0, 0));

                                //---- btnAddCounselor ----
                                btnAddCounselor.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                btnAddCounselor.setToolTipText("Add a new requirement");
                                btnAddCounselor.setName("btnAddCounselor");
                                btnAddCounselor.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseEntered(MouseEvent e) {
                                        changeToHand();
                                    }
                                    @Override
                                    public void mouseExited(MouseEvent e) {
                                        setDefaultCursor();
                                    }
                                    @Override
                                    public void mouseReleased(MouseEvent e) {
                                        btnAddCounselorMouseReleased();
                                    }
                                });
                                panel10.add(btnAddCounselor, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 5), 0, 0));

                                //---- btnRemoveCounselor ----
                                btnRemoveCounselor.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                btnRemoveCounselor.setToolTipText("Remove selected requirement");
                                btnRemoveCounselor.setName("btnRemoveCounselor");
                                btnRemoveCounselor.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseEntered(MouseEvent e) {
                                        changeToHand();
                                    }
                                    @Override
                                    public void mouseExited(MouseEvent e) {
                                        setDefaultCursor();
                                    }
                                    @Override
                                    public void mouseReleased(MouseEvent e) {
                                        btnRemoveCounselorMouseReleased();
                                    }
                                });
                                panel10.add(btnRemoveCounselor, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 5), 0, 0));
                            }
                            panel6.add(panel10, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(20, 0, 5, 0), 0, 0));

                            //======== panel9 ========
                            {
                                panel9.setName("panel9");
                                panel9.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0};
                                ((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
                                ((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                                ((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                                //======== scrollPane4 ========
                                {
                                    scrollPane4.setName("scrollPane4");

                                    //---- tblCounselors ----
                                    tblCounselors.setPreferredScrollableViewportSize(new Dimension(200, 200));
                                    tblCounselors.setBackground(Color.white);
                                    tblCounselors.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    tblCounselors.setForeground(Color.black);
                                    tblCounselors.setFillsViewportHeight(true);
                                    tblCounselors.setName("tblCounselors");
                                    scrollPane4.setViewportView(tblCounselors);
                                }
                                panel9.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 0), 0, 0));
                            }
                            panel6.add(panel9, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 10, 5, 0), 0, 0));
                        }
                        panel4.add(panel6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 20), 0, 0));

                        //======== panel7 ========
                        {
                            panel7.setOpaque(false);
                            panel7.setName("panel7");
                            panel7.setLayout(new GridBagLayout());
                            ((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {450, 0};
                            ((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0};
                            ((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                            ((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                            //======== panel8 ========
                            {
                                panel8.setOpaque(false);
                                panel8.setName("panel8");
                                panel8.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                                ((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
                                ((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                ((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                //---- lblRequirement ----
                                lblRequirement.setText("Requirements");
                                lblRequirement.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                lblRequirement.setForeground(new Color(51, 102, 153));
                                lblRequirement.setName("lblRequirement");
                                panel8.add(lblRequirement, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(3, 0, 0, 5), 0, 0));

                                //---- btnAddRequirement ----
                                btnAddRequirement.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                btnAddRequirement.setToolTipText("Add a new requirement");
                                btnAddRequirement.setName("btnAddRequirement");
                                btnAddRequirement.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseEntered(MouseEvent e) {
                                        changeToHand();
                                    }
                                    @Override
                                    public void mouseExited(MouseEvent e) {
                                        setDefaultCursor();
                                    }
                                    @Override
                                    public void mouseReleased(MouseEvent e) {
                                        btnAddRequirementMouseReleased();
                                    }
                                });
                                panel8.add(btnAddRequirement, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 5), 0, 0));

                                //---- btnRemoveRequirement ----
                                btnRemoveRequirement.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                btnRemoveRequirement.setToolTipText("Remove selected requirement");
                                btnRemoveRequirement.setName("btnRemoveRequirement");
                                btnRemoveRequirement.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseEntered(MouseEvent e) {
                                        changeToHand();
                                    }
                                    @Override
                                    public void mouseExited(MouseEvent e) {
                                        setDefaultCursor();
                                    }
                                    @Override
                                    public void mouseReleased(MouseEvent e) {
                                        btnRemoveRequirementMouseReleased();
                                    }
                                });
                                panel8.add(btnRemoveRequirement, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
                            panel7.add(panel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));

                            //======== scrollPane3 ========
                            {
                                scrollPane3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                                scrollPane3.setName("scrollPane3");

                                //======== pnlRequirementList ========
                                {
                                    pnlRequirementList.setBackground(Color.white);
                                    pnlRequirementList.setMaximumSize(new Dimension(450, 700));
                                    pnlRequirementList.setName("pnlRequirementList");
                                    pnlRequirementList.setLayout(new VerticalLayout());
                                }
                                scrollPane3.setViewportView(pnlRequirementList);
                            }
                            panel7.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        panel4.add(panel7, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 10), 0, 0));
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
    private JList listMeritBadgeNames;
    private JScrollPane scrollPane2;
    private JLabel btnBadgeImage;
    private JTextFieldDefaultText txtName;
    private JLabel lblNameError;
    private JCheckBox chkRequiredForEagle;
    private JLabel lblCounselorError;
    private JLabel btnAddCounselor;
    private JLabel btnRemoveCounselor;
    private JTable tblCounselors;
    private JLabel btnAddRequirement;
    private JLabel btnRemoveRequirement;
    private JLabel lblRequirementError;
    private JScrollPane scrollPane3;
    private JPanel pnlRequirementList;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
