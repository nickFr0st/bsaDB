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
import objects.databaseObjects.Requirement;
import objects.objectLogic.LogicAdvancement;
import objects.objectLogic.LogicRequirement;
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

    private Icon noImage;
    private Advancement advancement;
    private String imagePath;

    {
        imagePath = "";
    }

    public BoyScoutPanel() {
        initComponents();

        noImage = new ImageIcon(getClass().getResource("/images/no_image.png"));
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        scrollPane2.getVerticalScrollBar().setUnitIncrement(18);

        populateAdvancementNameList();

        enableControls(false);
    }

    public void populateAdvancementNameList() {
        Collection<Advancement> advancementList = CacheObject.getAdvancementList();
        List<String> advancementNameList = new ArrayList<>();
        for (Advancement advancement : advancementList) {
            advancementNameList.add(advancement.getName());
        }

        listBoyScoutNames.setListData(Util.getSortedList(advancementNameList));
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

        advancement = CacheObject.getAdvancement(listBoyScoutNames.getSelectedValue().toString());
        loadData();
    }

    private void loadData() {
        if (advancement == null) {
            return;
        }

        enableControls(true);

//        txtName.setText(advancement.getName());

        ImageIcon tryPath = new ImageIcon(advancement.getImgPath());
        if (tryPath.getImageLoadStatus() < MediaTracker.COMPLETE) {
//            btnBadgeImage.setIcon(noImage);
        } else {
            setImage(advancement.getImgPath());
        }

//        loadRequirementSet();

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
//        btnBadgeImage.setEnabled(enable);
//        txtName.setEnabled(enable);
//        btnAddRequirement.setEnabled(enable);
//        btnRemoveRequirement.setEnabled(enable);
    }

    private void clearAllErrors() {
//        Util.clearError(lblNameError);
//        Util.clearError(lblRequirementError);
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

        listBoyScoutNames.setSelectedValue(advancement.getName(), true);
    }

    private void saveRecords(Set<Requirement> requirementSet, boolean newAdvancement) {
        advancement = LogicAdvancement.save(advancement);

        if (newAdvancement) {
            for (Requirement requirement : requirementSet) {
                requirement.setParentId(advancement.getId());
            }
        }

        LogicRequirement.save(requirementSet);

        CacheObject.addToAdvancements(advancement);
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
        if (advancement == null) {
            advancement = new Advancement();
        }

//        advancement.setName(txtName.getText());
        if (Util.isEmpty(imagePath) || getImage() == null) {
            advancement.setImgPath("");
        } else {
            advancement.setImgPath(imagePath);
        }
    }

    private Image getImage() {
        return new ImageIcon(imagePath).getImage();
    }

    private boolean validateFields() {
        boolean valid = true;

//        if (!validateAdvancementName()) {
//            valid = false;
//        }

        int advancementId;
        if (advancement == null) {
            advancementId = -1;
        } else {
            advancementId = advancement.getId();
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
        advancement = LogicAdvancement.update(advancement);

        Set<Requirement> currentRequirementSet = LogicRequirement.findAllByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());
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


        CacheObject.addToAdvancements(advancement);
        populateAdvancementNameList();

        listBoyScoutNames.setSelectedValue(advancement.getName(), true);
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

        int advancementId = advancement.getId();

//        Set<Requirement> requirementSet = validateRequirements(advancement.getId(), false);
//        LogicRequirement.delete(requirementSet);
        LogicAdvancement.delete(advancement);

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
        txtRole = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblRank = new JLabel();
        cboRank = new JComboBox();
        lblBirthDate = new JLabel();
        cboBirthDate = new JDatePicker();
        lblAge = new JLabel();
        lblAgeValue = new JLabel();
        lblBirthDateError = new JLabel();
        lblBirthDate2 = new JLabel();
        cboRankDate = new JDatePicker();
        lblRankDateError = new JLabel();
        label11 = new JLabel();
        label13 = new JLabel();
        progressBar1 = new JProgressBar();
        progressBar2 = new JProgressBar();
        label12 = new JLabel();
        label14 = new JLabel();
        label15 = new JLabel();
        label1 = new JLabel();
        progressBar3 = new JProgressBar();
        progressBar4 = new JProgressBar();
        label16 = new JLabel();
        label2 = new JLabel();
        pnlAdvancementTracker = new JPanel();
        label17 = new JLabel();
        scrollPane3 = new JScrollPane();
        table1 = new JTable();
        panel7 = new JPanel();
        panel8 = new JPanel();
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
                                ((GridBagLayout)pnlSummary.getLayout()).rowHeights = new int[] {0, 210, 0};
                                ((GridBagLayout)pnlSummary.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                                ((GridBagLayout)pnlSummary.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                                //======== pnlGeneral ========
                                {
                                    pnlGeneral.setOpaque(false);
                                    pnlGeneral.setName("pnlGeneral");
                                    pnlGeneral.setLayout(new GridBagLayout());
                                    ((GridBagLayout)pnlGeneral.getLayout()).columnWidths = new int[] {0, 195, 42, 0, 190, 0, 0, 0};
                                    ((GridBagLayout)pnlGeneral.getLayout()).rowHeights = new int[] {35, 0, 35, 0, 35, 0, 24, 25, 0, 35, 25, 0, 0};
                                    ((GridBagLayout)pnlGeneral.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                                    ((GridBagLayout)pnlGeneral.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

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
                                    pnlGeneral.add(lblPosition, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- txtRole ----
                                    txtRole.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    txtRole.setDefaultText("Troop Position");
                                    txtRole.setName("txtRole");
                                    pnlGeneral.add(txtRole, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

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

                                    //---- lblBirthDate ----
                                    lblBirthDate.setText("Birth Date:");
                                    lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblBirthDate.setForeground(Color.black);
                                    lblBirthDate.setName("lblBirthDate");
                                    pnlGeneral.add(lblBirthDate, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboBirthDate ----
                                    cboBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboBirthDate.setMaximumSize(new Dimension(32822, 30));
                                    cboBirthDate.setMinimumSize(new Dimension(57, 30));
                                    cboBirthDate.setName("cboBirthDate");
                                    pnlGeneral.add(cboBirthDate, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblAge ----
                                    lblAge.setText("Age:");
                                    lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblAge.setForeground(Color.black);
                                    lblAge.setName("lblAge");
                                    pnlGeneral.add(lblAge, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 10, 5, 5), 0, 0));

                                    //---- lblAgeValue ----
                                    lblAgeValue.setText("12");
                                    lblAgeValue.setFont(new Font("Tahoma", Font.BOLD, 14));
                                    lblAgeValue.setForeground(new Color(32, 154, 26));
                                    lblAgeValue.setName("lblAgeValue");
                                    pnlGeneral.add(lblAgeValue, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblBirthDateError ----
                                    lblBirthDateError.setText("*error message");
                                    lblBirthDateError.setForeground(Color.red);
                                    lblBirthDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblBirthDateError.setName("lblBirthDateError");
                                    pnlGeneral.add(lblBirthDateError, new GridBagConstraints(3, 3, 3, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 5), 0, 0));

                                    //---- lblBirthDate2 ----
                                    lblBirthDate2.setText("Rank Date:");
                                    lblBirthDate2.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblBirthDate2.setForeground(Color.black);
                                    lblBirthDate2.setName("lblBirthDate2");
                                    pnlGeneral.add(lblBirthDate2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboRankDate ----
                                    cboRankDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboRankDate.setMaximumSize(new Dimension(32822, 30));
                                    cboRankDate.setMinimumSize(new Dimension(57, 30));
                                    cboRankDate.setName("cboRankDate");
                                    pnlGeneral.add(cboRankDate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblRankDateError ----
                                    lblRankDateError.setText("*error message");
                                    lblRankDateError.setForeground(Color.red);
                                    lblRankDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblRankDateError.setName("lblRankDateError");
                                    pnlGeneral.add(lblRankDateError, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 5), 0, 0));

                                    //---- label11 ----
                                    label11.setText("Time Left in Boy Scouts");
                                    label11.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    label11.setForeground(Color.black);
                                    label11.setName("label11");
                                    pnlGeneral.add(label11, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- label13 ----
                                    label13.setText("Number of Camps Attended");
                                    label13.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    label13.setForeground(Color.black);
                                    label13.setName("label13");
                                    pnlGeneral.add(label13, new GridBagConstraints(3, 6, 4, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- progressBar1 ----
                                    progressBar1.setValue(50);
                                    progressBar1.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    progressBar1.setName("progressBar1");
                                    pnlGeneral.add(progressBar1, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- progressBar2 ----
                                    progressBar2.setValue(50);
                                    progressBar2.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    progressBar2.setName("progressBar2");
                                    pnlGeneral.add(progressBar2, new GridBagConstraints(3, 7, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- label12 ----
                                    label12.setText("1 yr, 6 mon, 2 days");
                                    label12.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    label12.setForeground(Color.black);
                                    label12.setName("label12");
                                    pnlGeneral.add(label12, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- label14 ----
                                    label14.setText("11 of 21");
                                    label14.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    label14.setForeground(Color.black);
                                    label14.setName("label14");
                                    pnlGeneral.add(label14, new GridBagConstraints(3, 8, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- label15 ----
                                    label15.setText("Waiting Period for Next Advancement");
                                    label15.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    label15.setForeground(Color.black);
                                    label15.setName("label15");
                                    pnlGeneral.add(label15, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- label1 ----
                                    label1.setText("Progress Towards Next Advancement");
                                    label1.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    label1.setForeground(Color.black);
                                    label1.setName("label1");
                                    pnlGeneral.add(label1, new GridBagConstraints(3, 9, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- progressBar3 ----
                                    progressBar3.setValue(50);
                                    progressBar3.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    progressBar3.setName("progressBar3");
                                    pnlGeneral.add(progressBar3, new GridBagConstraints(0, 10, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- progressBar4 ----
                                    progressBar4.setValue(60);
                                    progressBar4.setName("progressBar4");
                                    pnlGeneral.add(progressBar4, new GridBagConstraints(3, 10, 2, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 5, 5), 0, 0));

                                    //---- label16 ----
                                    label16.setText("2 mo, 3 days");
                                    label16.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    label16.setForeground(Color.black);
                                    label16.setName("label16");
                                    pnlGeneral.add(label16, new GridBagConstraints(0, 11, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 0, 5), 0, 0));

                                    //---- label2 ----
                                    label2.setText("6 of 10 Requirements");
                                    label2.setForeground(Color.black);
                                    label2.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    label2.setName("label2");
                                    pnlGeneral.add(label2, new GridBagConstraints(3, 11, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                        new Insets(0, 0, 0, 5), 0, 0));
                                }
                                pnlSummary.add(pnlGeneral, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(15, 10, 10, 10), 0, 0));

                                //======== pnlAdvancementTracker ========
                                {
                                    pnlAdvancementTracker.setOpaque(false);
                                    pnlAdvancementTracker.setPreferredSize(new Dimension(592, 150));
                                    pnlAdvancementTracker.setMaximumSize(new Dimension(2147483647, 150));
                                    pnlAdvancementTracker.setMinimumSize(new Dimension(567, 150));
                                    pnlAdvancementTracker.setName("pnlAdvancementTracker");
                                    pnlAdvancementTracker.setLayout(new GridBagLayout());
                                    ((GridBagLayout)pnlAdvancementTracker.getLayout()).columnWidths = new int[] {567, 0};
                                    ((GridBagLayout)pnlAdvancementTracker.getLayout()).rowHeights = new int[] {0, 0, 0};
                                    ((GridBagLayout)pnlAdvancementTracker.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                                    ((GridBagLayout)pnlAdvancementTracker.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                                    //---- label17 ----
                                    label17.setText("Progress of Current Advancement");
                                    label17.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    label17.setForeground(Color.black);
                                    label17.setName("label17");
                                    pnlAdvancementTracker.add(label17, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== scrollPane3 ========
                                    {
                                        scrollPane3.setName("scrollPane3");

                                        //---- table1 ----
                                        table1.setPreferredScrollableViewportSize(new Dimension(450, 150));
                                        table1.setMaximumSize(new Dimension(2147483647, 200));
                                        table1.setName("table1");
                                        scrollPane3.setViewportView(table1);
                                    }
                                    pnlAdvancementTracker.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 0, 0), 0, 0));
                                }
                                pnlSummary.add(pnlAdvancementTracker, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 10, 10, 10), 0, 0));
                            }
                            tabPnlContent.addTab("Summary", pnlSummary);

                            //======== panel7 ========
                            {
                                panel7.setBackground(Color.white);
                                panel7.setName("panel7");
                                panel7.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0};
                                ((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                                ((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                                ((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                            }
                            tabPnlContent.addTab("text", panel7);

                            //======== panel8 ========
                            {
                                panel8.setBackground(Color.white);
                                panel8.setName("panel8");
                                panel8.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0};
                                ((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                                ((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                                ((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                            }
                            tabPnlContent.addTab("text", panel8);
                        }
                        panel4.add(tabPnlContent, BorderLayout.CENTER);
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
    private JTextFieldDefaultText txtRole;
    private JLabel lblNameError;
    private JLabel lblRank;
    private JComboBox cboRank;
    private JLabel lblBirthDate;
    private JDatePicker cboBirthDate;
    private JLabel lblAge;
    private JLabel lblAgeValue;
    private JLabel lblBirthDateError;
    private JLabel lblBirthDate2;
    private JDatePicker cboRankDate;
    private JLabel lblRankDateError;
    private JLabel label11;
    private JLabel label13;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JLabel label12;
    private JLabel label14;
    private JLabel label15;
    private JLabel label1;
    private JProgressBar progressBar3;
    private JProgressBar progressBar4;
    private JLabel label16;
    private JLabel label2;
    private JPanel pnlAdvancementTracker;
    private JLabel label17;
    private JScrollPane scrollPane3;
    private JTable table1;
    private JPanel panel7;
    private JPanel panel8;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
