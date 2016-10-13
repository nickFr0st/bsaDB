/*
 * Created by JFormDesigner on Sun May 24 13:47:07 MDT 2015
 */

package bsaDb.client.dialogs.export;

import constants.IETypeConst;
import objects.databaseObjects.*;
import objects.objectLogic.LogicBoyScout;
import objects.objectLogic.LogicCamp;
import objects.objectLogic.LogicServiceProject;
import util.CacheObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author User #2
 */
public class ExportPanel extends JPanel {

    private List<String> nameSource = new ArrayList<>();
    private List<String> nameExport = new ArrayList<>();

    public ExportPanel() {
        initComponents();
        rbtnExportAll.setSelected(true);
    }

    public ExportPanel(IETypeConst typeConst) {
        this();

        populateSourceList(typeConst);
        enableComponents(false);
    }

    private void enableComponents(boolean enable) {
        listSource.setEnabled(enable);
        listExport.setEnabled(enable);
        btnAdd.setEnabled(enable);
        btnRemove.setEnabled(enable);
    }

    private void populateSourceList(final IETypeConst typeConst) {
        switch (typeConst) {
            case ADVANCEMENT:
                Collection<Advancement> advancementList = CacheObject.getAdvancementList();
                nameSource.addAll(advancementList.stream().map(Advancement::getName).collect(Collectors.toList()));
                break;
            case MERIT_BADGE:
                Collection<MeritBadge> meritBadgeList = CacheObject.getMeritBadgeList();
                nameSource.addAll(meritBadgeList.stream().map(MeritBadge::getName).collect(Collectors.toList()));
                break;
            case CAMPOUT:
                Set<Camp> campList = LogicCamp.findAll(null);
                nameSource.addAll(campList.stream().map(Camp::getName).collect(Collectors.toList()));
                break;
            case SERVICE_PROJECTS:
                Set<ServiceProject> serviceProjectSet = LogicServiceProject.findAll(null);
                nameSource.addAll(serviceProjectSet.stream().map(ServiceProject::getName).collect(Collectors.toList()));
                break;
            case SPECIAL_AWARD:
            case BOY_SCOUT:
                Set<BoyScout> boyScoutSet = LogicBoyScout.findAll();
                nameSource.addAll(boyScoutSet.stream().map(BoyScout::getName).collect(Collectors.toList()));
                break;
        }

        listSource.setListData(nameSource.toArray());
    }

    private void rbtnExportAllActionPerformed() {
        enableComponents(false);
    }

    private void rbtnExportSelectedActionPerformed() {
        enableComponents(true);
    }

    private void btnAddActionPerformed() {
        if (listSource.getSelectedValue() == null) {
            return;
        }

        List<String> selectedList = listSource.getSelectedValuesList();

        for (String selected : selectedList) {
            int size = nameSource.size();
            for (int i = 0; i < size; i++) {
                if (nameSource.get(i).equals(selected)) {
                    nameSource.remove(i);
                    break;
                }
            }
        }

        for (String selected : selectedList) {
            nameExport.add(selected);
        }

        listSource.setListData(nameSource.toArray());
        listExport.setListData(nameExport.toArray());
    }

    private void btnRemoveActionPerformed() {
        if (listExport.getSelectedValue() == null) {
            return;
        }

        List<String> selectedList = listExport.getSelectedValuesList();

        for (String selected : selectedList) {
            int size = nameExport.size();
            for (int i = 0; i < size; i++) {
                if (nameExport.get(i).equals(selected)) {
                    nameExport.remove(i);
                    break;
                }
            }
        }

        for (String selected : selectedList) {
            nameSource.add(selected);
        }

        listSource.setListData(nameSource.toArray());
        listExport.setListData(nameExport.toArray());
    }

    public List<String> getExportList() {
        if (rbtnExportAll.isSelected()) {
            nameExport.addAll(nameSource);
        }

        return nameExport;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        rbtnExportAll = new JRadioButton();
        JLabel label1 = new JLabel();
        JRadioButton rbtnExportSelected = new JRadioButton();
        JLabel lblAvailable = new JLabel();
        JLabel lblSelected = new JLabel();
        JScrollPane scrollPane1 = new JScrollPane();
        listSource = new JList();
        JPanel panel1 = new JPanel();
        btnAdd = new JButton();
        btnRemove = new JButton();
        JScrollPane scrollPane2 = new JScrollPane();
        listExport = new JList();

        //======== this ========
        setBackground(Color.white);
        setPreferredSize(new Dimension(600, 268));
        setMinimumSize(new Dimension(600, 223));
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {235, 0, 235, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 7, 0, 30, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //---- rbtnExportAll ----
        rbtnExportAll.setText("Export All");
        rbtnExportAll.setFont(new Font("Vijaya", Font.PLAIN, 20));
        rbtnExportAll.setForeground(new Color(0, 63, 135));
        rbtnExportAll.setOpaque(false);
        rbtnExportAll.setName("rbtnExportAll");
        rbtnExportAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rbtnExportAllActionPerformed();
            }
        });
        add(rbtnExportAll, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 10, 2, 5), 0, 0));

        //---- label1 ----
        label1.setBackground(Color.black);
        label1.setOpaque(true);
        label1.setMaximumSize(new Dimension(0, 2));
        label1.setMinimumSize(new Dimension(0, 2));
        label1.setName("label1");
        add(label1, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(0, 10, 2, 15), 0, 0));

        //---- rbtnExportSelected ----
        rbtnExportSelected.setText("Export Selected");
        rbtnExportSelected.setFont(new Font("Vijaya", Font.PLAIN, 20));
        rbtnExportSelected.setForeground(new Color(0, 63, 135));
        rbtnExportSelected.setOpaque(false);
        rbtnExportSelected.setName("rbtnExportSelected");
        rbtnExportSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rbtnExportSelectedActionPerformed();
            }
        });
        add(rbtnExportSelected, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 10, 2, 5), 0, 0));

        //---- lblAvailable ----
        lblAvailable.setText("Available");
        lblAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAvailable.setForeground(Color.black);
        lblAvailable.setName("lblAvailable");
        add(lblAvailable, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 10, 2, 5), 0, 0));

        //---- lblSelected ----
        lblSelected.setText("Selected");
        lblSelected.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSelected.setForeground(Color.black);
        lblSelected.setName("lblSelected");
        add(lblSelected, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 2, 5), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setName("scrollPane1");

            //---- listSource ----
            listSource.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listSource.setMinimumSize(new Dimension(200, 57));
            listSource.setMaximumSize(new Dimension(300, 57));
            listSource.setName("listSource");
            scrollPane1.setViewportView(listSource);
        }
        add(scrollPane1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 10, 10, 5), 0, 0));

        //======== panel1 ========
        {
            panel1.setOpaque(false);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};

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
            panel1.add(btnAdd, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

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
            panel1.add(btnRemove, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setName("scrollPane2");

            //---- listExport ----
            listExport.setFont(new Font("Tahoma", Font.PLAIN, 14));
            listExport.setMinimumSize(new Dimension(200, 57));
            listExport.setMaximumSize(new Dimension(300, 57));
            listExport.setName("listExport");
            scrollPane2.setViewportView(listExport);
        }
        add(scrollPane2, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 10, 15), 0, 0));

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rbtnExportAll);
        buttonGroup1.add(rbtnExportSelected);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JRadioButton rbtnExportAll;
    private JList listSource;
    private JButton btnAdd;
    private JButton btnRemove;
    private JList listExport;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
