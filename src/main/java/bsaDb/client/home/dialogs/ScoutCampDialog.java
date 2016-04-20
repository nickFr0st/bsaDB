/*
 * Created by JFormDesigner on Sat Nov 15 15:13:17 MST 2014
 */

package bsaDb.client.home.dialogs;

import objects.databaseObjects.Camp;
import objects.objectLogic.LogicCamp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author User #2
 */
public class ScoutCampDialog extends JDialog {
    public static final int BTN_OK = 1;

    private List<Camp> nameAvailable = new ArrayList<>();
    private List<Camp> nameSelected = new ArrayList<>();
    private int btnChoice;

    {
        btnChoice = 0;
    }

    public ScoutCampDialog(Frame owner, List<Camp> preSelectedList) {
        super(owner, true);
        initComponents();

        nameSelected.addAll(preSelectedList);
        nameAvailable.addAll(LogicCamp.findAll(preSelectedList));

        listSelected.setListData(nameSelected.toArray());
        listAvailable.setListData(nameAvailable.toArray());

        setVisible(true);
    }

    private void cancelButtonMouseClicked() {
        dispose();
    }

    private void okButtonMouseClicked() {
        btnChoice = BTN_OK;
        dispose();
    }

    public int getBtnChoice() {
        return btnChoice;
    }

    public List<Camp> getSelectedCamps() {
        return nameSelected;
    }

    private void btnAddActionPerformed() {
        if (listAvailable.getSelectedValue() == null) {
            return;
        }

        List<Camp> selectedList = listAvailable.getSelectedValuesList();

        for (Camp selected : selectedList) {
            int size = nameAvailable.size();
            for (int i = 0; i < size; i++) {
                if (nameAvailable.get(i).equals(selected)) {
                    nameAvailable.remove(i);
                    break;
                }
            }
        }

        for (Camp selected : selectedList) {
            nameSelected.add(selected);
        }

        listAvailable.setListData(nameAvailable.toArray());
        listSelected.setListData(nameSelected.toArray());
    }

    private void btnRemoveActionPerformed() {
        if (listSelected.getSelectedValue() == null) {
            return;
        }

        List<Camp> selectedList = listSelected.getSelectedValuesList();

        for (Camp selected : selectedList) {
            int size = nameSelected.size();
            for (int i = 0; i < size; i++) {
                if (nameSelected.get(i).equals(selected)) {
                    nameSelected.remove(i);
                    break;
                }
            }
        }

        for (Camp selected : selectedList) {
            nameAvailable.add(selected);
        }

        listAvailable.setListData(nameAvailable.toArray());
        listSelected.setListData(nameSelected.toArray());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        JLabel lblAvailable = new JLabel();
        JLabel lblSelected = new JLabel();
        panel3 = new JScrollPane();
        listAvailable = new JList();
        JPanel panel1 = new JPanel();
        btnAdd = new JButton();
        btnRemove = new JButton();
        panel2 = new JScrollPane();
        listSelected = new JList();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("Scout Camp Select");
        setMinimumSize(new Dimension(500, 340));
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(Color.white);
            dialogPane.setName("dialogPane");
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(Color.white);
                contentPanel.setName("contentPanel");
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {205, 0, 200, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 198, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                //---- lblAvailable ----
                lblAvailable.setText("Available");
                lblAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblAvailable.setForeground(Color.black);
                lblAvailable.setName("lblAvailable");
                contentPanel.add(lblAvailable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 10, 5, 5), 0, 0));

                //---- lblSelected ----
                lblSelected.setText("Selected");
                lblSelected.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblSelected.setForeground(Color.black);
                lblSelected.setName("lblSelected");
                contentPanel.add(lblSelected, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 10, 5, 0), 0, 0));

                //======== panel3 ========
                {
                    panel3.setName("panel3");

                    //---- listAvailable ----
                    listAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listAvailable.setMinimumSize(new Dimension(200, 57));
                    listAvailable.setMaximumSize(new Dimension(300, 57));
                    listAvailable.setName("listAvailable");
                    panel3.setViewportView(listAvailable);
                }
                contentPanel.add(panel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

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
                contentPanel.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //======== panel2 ========
                {
                    panel2.setName("panel2");

                    //---- listSelected ----
                    listSelected.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listSelected.setMinimumSize(new Dimension(200, 57));
                    listSelected.setMaximumSize(new Dimension(300, 57));
                    listSelected.setName("listSelected");
                    panel2.setViewportView(listSelected);
                }
                contentPanel.add(panel2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setBackground(Color.white);
                buttonBar.setName("buttonBar");
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {35};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
                okButton.setForeground(Color.white);
                okButton.setBackground(new Color(51, 156, 229));
                okButton.setFocusPainted(false);
                okButton.setName("okButton");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked();
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.setForeground(Color.white);
                cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
                cancelButton.setBackground(new Color(206, 17, 38));
                cancelButton.setFocusPainted(false);
                cancelButton.setName("cancelButton");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked();
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane panel3;
    private JList listAvailable;
    private JButton btnAdd;
    private JButton btnRemove;
    private JScrollPane panel2;
    private JList listSelected;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
