/*
 * Created by JFormDesigner on Thu Mar 03 04:31:21 MST 2016
 */

package bsaDb.client.home.dialogs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author User #2
 */
public class AboutDialog extends JDialog {

    public AboutDialog(Frame owner) {
        super(owner);
        initComponents();
        setVisible(true);
    }

    private void btnOkayActionPerformed() {
        setVisible(false);
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        label1 = new JLabel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label7 = new JLabel();
        textArea1 = new JTextArea();
        buttonBar = new JPanel();
        btnOkay = new JButton();

        //======== this ========
        setTitle("About");
        setModal(true);
        setMinimumSize(new Dimension(600, 350));
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //======== panel2 ========
            {
                panel2.setOpaque(false);
                panel2.setName("panel2");
                panel2.setLayout(new BorderLayout());

                //======== panel3 ========
                {
                    panel3.setBorder(LineBorder.createBlackLineBorder());
                    panel3.setBackground(new Color(179, 148, 117));
                    panel3.setName("panel3");
                    panel3.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {125, 0};
                    ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                    ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                    //---- label1 ----
                    label1.setIcon(new ImageIcon(getClass().getResource("/images/flurDeLis90.png")));
                    label1.setName("label1");
                    panel3.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                panel2.add(panel3, BorderLayout.WEST);

                //======== panel4 ========
                {
                    panel4.setOpaque(false);
                    panel4.setName("panel4");
                    panel4.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0};
                    ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0};
                    ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                    ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

                    //======== panel5 ========
                    {
                        panel5.setOpaque(false);
                        panel5.setName("panel5");
                        panel5.setLayout(new GridBagLayout());
                        ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 34, 0, 0};
                        ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                        ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                        //---- label2 ----
                        label2.setText("BSA Database");
                        label2.setFont(new Font("Tahoma", Font.BOLD, 22));
                        label2.setForeground(Color.black);
                        label2.setName("label2");
                        panel5.add(label2, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- label3 ----
                        label3.setText("Version 1.0");
                        label3.setFont(new Font("Tahoma", Font.PLAIN, 12));
                        label3.setForeground(Color.black);
                        label3.setName("label3");
                        panel5.add(label3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- label4 ----
                        label4.setText("By Nathanael Malloch");
                        label4.setFont(new Font("Tahoma", Font.PLAIN, 12));
                        label4.setForeground(Color.black);
                        label4.setName("label4");
                        panel5.add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- label7 ----
                        label7.setText("About the Product:");
                        label7.setFont(new Font("Tahoma", Font.BOLD, 14));
                        label7.setForeground(Color.black);
                        label7.setName("label7");
                        panel5.add(label7, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- textArea1 ----
                        textArea1.setWrapStyleWord(true);
                        textArea1.setLineWrap(true);
                        textArea1.setEditable(false);
                        textArea1.setFont(new Font("Tahoma", Font.PLAIN, 12));
                        textArea1.setForeground(Color.black);
                        textArea1.setText("BSA Database is an easy to use scout tracking program.\nThis project uses an active MySql server connection.\nBSA Database helps you track various information points for your troop and boys individually.\nsome of the information points are:\n- Campouts\n- Advancement Progress\n- Age Tracking\n- Merit Badges Earned\n- etc.");
                        textArea1.setBackground(Color.white);
                        textArea1.setName("textArea1");
                        panel5.add(textArea1, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    panel4.add(panel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 10, 5, 0), 0, 0));

                    //======== buttonBar ========
                    {
                        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                        buttonBar.setOpaque(false);
                        buttonBar.setName("buttonBar");
                        buttonBar.setLayout(new GridBagLayout());
                        ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                        ((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {35};
                        ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                        //---- btnOkay ----
                        btnOkay.setText("Okay");
                        btnOkay.setBackground(new Color(0, 63, 135));
                        btnOkay.setForeground(Color.white);
                        btnOkay.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        btnOkay.setFocusPainted(false);
                        btnOkay.setName("btnOkay");
                        btnOkay.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                btnOkayActionPerformed();
                            }
                        });
                        buttonBar.add(btnOkay, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    panel4.add(buttonBar, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                panel2.add(panel4, BorderLayout.CENTER);
            }
            panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 10, 10), 0, 0));
        }
        contentPane.add(panel1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JLabel label1;
    private JPanel panel4;
    private JPanel panel5;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label7;
    private JTextArea textArea1;
    private JPanel buttonBar;
    private JButton btnOkay;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
