/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.CustomChooser;
import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.PnlRequirement;
import bsaDb.client.customComponents.TitlePanel;
import constants.RequirementTypeConst;
import objects.databaseObjects.Advancement;
import objects.databaseObjects.Requirement;
import objects.objectLogic.LogicAdvancement;
import objects.objectLogic.LogicRequirement;
import org.jdesktop.swingx.VerticalLayout;
import util.CacheObject;
import util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
public class AdvancementPanel extends JPanel {

    private Icon noImage;
    private Advancement advancement;
    private String imagePath;

    {
        imagePath = "";
    }

    public AdvancementPanel() {
        initComponents();

        noImage = new ImageIcon(getClass().getResource("/images/no_image.png"));
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        populateAdvancementNameList();

        enableControls(false);
    }

    public void populateAdvancementNameList() {
        Collection<Advancement> advancementList = CacheObject.getAdvancementList();
        List<String> advancementNameList = new ArrayList<String>();
        for (Advancement advancement : advancementList) {
            advancementNameList.add(advancement.getName());
        }

        listAdvancementNames.setListData(advancementNameList.toArray());
        listAdvancementNames.revalidate();
    }

    private void txtSearchNameKeyReleased() {
        Collection<Advancement> advancementList = CacheObject.getAdvancementList();
        List<String> advancementNameList = new ArrayList<String>();
        for (Advancement advancement : advancementList) {
            advancementNameList.add(advancement.getName());
        }

        if (txtSearchName.isMessageDefault()) {
            listAdvancementNames.setListData(advancementNameList.toArray());
            listAdvancementNames.revalidate();
            return;
        }

        List<String> filteredList = new ArrayList<String>();
        for (Advancement advancement : advancementList) {
            if (advancement.getName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(advancement.getName());
            }
        }

        listAdvancementNames.setListData(filteredList.toArray());
        listAdvancementNames.revalidate();
    }

    private void listUserNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listUserNamesMouseReleased();
        }
    }

    private void listUserNamesMouseReleased() {
        if (listAdvancementNames.getSelectedValue() == null) {
            return;
        }

        clearAllErrors();
        clearData();

        advancement = CacheObject.getAdvancement(listAdvancementNames.getSelectedValue().toString());
        loadData();
    }

    private void loadData() {
        if (advancement == null) {
            return;
        }

        enableControls(true);

        txtName.setText(advancement.getName());

        ImageIcon tryPath = new ImageIcon(advancement.getImgPath());
        if (tryPath.getImageLoadStatus() < MediaTracker.COMPLETE) {
            btnBadgeImage.setIcon(noImage);
        } else {
            setImage(advancement.getImgPath());
        }

        loadRequirementList();

        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnSave.setVisible(false);
    }

    private void loadRequirementList() {
        List<Requirement> requirementList = CacheObject.getRequirementListByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());

        boolean firstPass = false;
        for (Requirement requirement : requirementList) {
            PnlRequirement pnlRequirement = new PnlRequirement(requirement.getName(), requirement.getDescription(), firstPass, requirement.getId());
            pnlRequirementList.add(pnlRequirement);

            if (!firstPass) {
                firstPass = true;
            }
        }

        pnlRequirementList.revalidate();
        pnlRequirementList.repaint();
    }

    private void enableControls(boolean enable) {
        btnBadgeImage.setEnabled(enable);
        txtName.setEnabled(enable);
        btnAddRequirement.setEnabled(enable);
        btnRemoveRequirement.setEnabled(enable);
    }

    private void clearAllErrors() {
        Util.clearError(lblNameError);
        Util.clearError(lblRequirementError);
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
        advancement = null;

        btnBadgeImage.setIcon(noImage);
        txtName.setDefault();
        imagePath = "";

        pnlRequirementList.removeAll();
        pnlRequirementList.repaint();
    }

    private void btnSaveActionPerformed() {
        if (!validateFields()) {
            return;
        }

        setData();

        List<Requirement> requirementList = validateRequirements(-1, true);
        if (requirementList == null) return;

        saveRecords(requirementList, true);

        btnSave.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        populateAdvancementNameList();

        listAdvancementNames.setSelectedValue(advancement.getName(), true);
    }

    private void saveRecords(List<Requirement> requirementList, boolean newAdvancement) {
        advancement = LogicAdvancement.save(advancement);

        if (newAdvancement) {
            for (Requirement requirement : requirementList) {
                requirement.setParentId(advancement.getId());
            }
        }

        LogicRequirement.save(requirementList);

        CacheObject.addToAdvancements(advancement);
        CacheObject.addToRequirements(requirementList);
    }

    private List<Requirement> validateRequirements(int parentId, boolean validate) {
        List<Requirement> requirementList = new ArrayList<Requirement>();
        Set<String> reqNameSet = new HashSet<String>();

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
            requirement.setTypeId(RequirementTypeConst.ADVANCEMENT.getId());

            requirementList.add(requirement);
        }
        return requirementList;
    }

    private void setData() {
        if (advancement == null) {
            advancement = new Advancement();
        }

        advancement.setName(txtName.getText());
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

        if (!validateAdvancementName()) {
            valid = false;
        }

        int advancementId;
        if (advancement == null) {
            advancementId = -1;
        } else {
            advancementId = advancement.getId();
        }

        if (validateRequirements(advancementId, true) == null) {
            valid = false;
        }

        return valid;
    }

    private void btnUpdateActionPerformed() {
        if (listAdvancementNames.getSelectedValue() == null) {
            return;
        }

        if (!validateFields()) {
            return;
        }

        setData();
        advancement = LogicAdvancement.update(advancement);

        List<Requirement> currentRequirementList = CacheObject.getRequirementListByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());
        List<Requirement> newRequirementList = getRequirementList(advancement.getId());
        List<Requirement> deleteRequirementList = new ArrayList<Requirement>();

        if (newRequirementList.isEmpty()) {
            for (Requirement requirement : currentRequirementList) {
                deleteRequirementList.add(requirement);
            }
        } else {
            for (Requirement requirement : currentRequirementList) {
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
            CacheObject.removeFromRequirements(deleteRequirement.getId());
        }

        for (Requirement requirement : newRequirementList) {
            if (requirement.getId() > 0) {
                LogicRequirement.update(requirement);
            } else {
                LogicRequirement.save(requirement);
            }
            CacheObject.addToRequirements(requirement);
        }


        CacheObject.addToAdvancements(advancement);
        populateAdvancementNameList();

        listAdvancementNames.setSelectedValue(advancement.getName(), true);
    }

    private List<Requirement> getRequirementList(int parentId) {
        List<Requirement> requirementList = new ArrayList<Requirement>();

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
            requirement.setTypeId(RequirementTypeConst.ADVANCEMENT.getId());

            requirementList.add(requirement);
        }

        return requirementList;
    }

    private void btnDeleteActionPerformed() {
        if (listAdvancementNames.getSelectedValue() == null) {
            return;
        }

        int advancementId = advancement.getId();

        List<Requirement> requirementList = validateRequirements(advancement.getId(), false);
        LogicRequirement.delete(requirementList);
        LogicAdvancement.delete(advancement);

        CacheObject.removeFromAdvancements(advancementId);

        requirementList = CacheObject.getRequirementListByParentIdAndTypeId(advancementId, RequirementTypeConst.ADVANCEMENT.getId());

        for (Requirement requirement : requirementList) {
            CacheObject.removeFromRequirements(requirement.getId());
        }

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
        if (!btnBadgeImage.isEnabled()) {
            return;
        }

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");

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
        validateAdvancementName();
    }

    private boolean validateAdvancementName() {
        Util.clearError(lblNameError);

        if (txtName.isMessageDefault() || txtName.getText().trim().isEmpty()) {
            Util.setError(lblNameError, "Name cannot be left blank");
            return false;
        }

        Advancement tempAdvancement = CacheObject.getAdvancement(txtName.getText());
        if (tempAdvancement == null) {
            return true;
        }

        if (advancement == null) {
            Util.setError(lblNameError, "An advancement with the name '" + txtName.getText() + "' already exists");
            return false;
        }

        if (!tempAdvancement.getName().equals(advancement.getName())) {
            Util.setError(lblNameError, "An advancement with the name '" + txtName.getText() + "' already exists");
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        TitlePanel pnlTitle = new TitlePanel();
        JPanel pnlContents = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        JScrollPane scrollPane1 = new JScrollPane();
        listAdvancementNames = new JList();
        JPanel panel3 = new JPanel();
        JScrollPane scrollPane2 = new JScrollPane();
        JPanel panel4 = new JPanel();
        JPanel panel1 = new JPanel();
        btnBadgeImage = new JLabel();
        JPanel panel6 = new JPanel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        JPanel panel7 = new JPanel();
        JPanel panel8 = new JPanel();
        lblRequirement = new JLabel();
        btnAddRequirement = new JLabel();
        btnRemoveRequirement = new JLabel();
        lblRequirementError = new JLabel();
        JScrollPane scrollPane3 = new JScrollPane();
        pnlRequirementList = new JPanel();
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
        pnlTitle.setTitle("Advancements");
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

                    //---- listAdvancementNames ----
                    listAdvancementNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listAdvancementNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listAdvancementNames.setName("listAdvancementNames");
                    listAdvancementNames.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            listUserNamesKeyReleased(e);
                        }
                    });
                    listAdvancementNames.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            listUserNamesMouseReleased();
                        }
                    });
                    scrollPane1.setViewportView(listAdvancementNames);
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
                            ((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0};
                            ((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                            ((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                            //---- lblName ----
                            lblName.setText("Name:");
                            lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                            lblName.setForeground(Color.black);
                            lblName.setName("lblName");
                            panel6.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 10, 5, 5), 0, 0));

                            //---- txtName ----
                            txtName.setDefaultText("Advancement Name");
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
                                new Insets(0, 10, 0, 0), 0, 0));
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
    private JList listAdvancementNames;
    private JLabel btnBadgeImage;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblNameError;
    private JLabel lblRequirement;
    private JLabel btnAddRequirement;
    private JLabel btnRemoveRequirement;
    private JLabel lblRequirementError;
    private JPanel pnlRequirementList;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
