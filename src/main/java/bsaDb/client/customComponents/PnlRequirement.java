/*
 * Created by JFormDesigner on Sat Jun 07 18:04:30 MDT 2014
 */

package bsaDb.client.customComponents;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author User #2
 */
public class PnlRequirement extends JPanel {
    private int reqId;

    public PnlRequirement(String reqName, String reqDescription, boolean showBorder, int reqId) {
        initComponents();

        this.reqId = reqId;
        txtReqName.setText(reqName);
        txtReqDescription.setText(reqDescription);
        lblBorder.setVisible(showBorder);
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getName() {
        return txtReqName.getText();
    }

    public String getDescription() {
        return txtReqDescription.getText();
    }

    private void txtReqDescriptionFocusLost() {
        txtReqDescription.setBackground(Color.white);
        txtReqDescription.setBorder(null);
    }

    private void txtReqNameFocusLost() {
        txtReqName.setBackground(Color.white);
        txtReqName.setBorder(null);
    }

    private void txtReqDescriptionFocusGained() {
        txtReqDescription.setBackground(new Color(242, 233, 206));
        txtReqDescription.setBorder(new LineBorder(new Color(32, 154, 26), 2));
        txtReqDescription.selectAll();
    }

    private void txtReqNameFocusGained() {
        txtReqName.setBackground(new Color(242, 233, 206));
        txtReqName.setBorder(new LineBorder(new Color(32, 154, 26), 2));
        txtReqName.selectAll();
    }

    public JTextField getTxtReqName() {
        return txtReqName;
    }

    public JTextArea getTxtReqDescription() {
        return txtReqDescription;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblBorder = new JLabel();
        txtReqName = new JTextField();
        txtReqDescription = new JTextArea();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {40, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {5, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

        //---- lblBorder ----
        lblBorder.setBackground(new Color(51, 102, 153));
        lblBorder.setOpaque(true);
        lblBorder.setMaximumSize(new Dimension(0, 2));
        lblBorder.setMinimumSize(new Dimension(0, 2));
        lblBorder.setPreferredSize(new Dimension(0, 2));
        lblBorder.setName("lblBorder");
        add(lblBorder, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- txtReqName ----
        txtReqName.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtReqName.setBackground(Color.white);
        txtReqName.setBorder(null);
        txtReqName.setName("txtReqName");
        txtReqName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtReqNameFocusGained();
            }
            @Override
            public void focusLost(FocusEvent e) {
                txtReqNameFocusLost();
            }
        });
        add(txtReqName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 0, 5), 0, 0));

        //---- txtReqDescription ----
        txtReqDescription.setWrapStyleWord(true);
        txtReqDescription.setLineWrap(true);
        txtReqDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtReqDescription.setBorder(null);
        txtReqDescription.setBackground(Color.white);
        txtReqDescription.setName("txtReqDescription");
        txtReqDescription.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtReqDescriptionFocusGained();
            }
            @Override
            public void focusLost(FocusEvent e) {
                txtReqDescriptionFocusLost();
            }
        });
        add(txtReqDescription, new GridBagConstraints(1, 1, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 15), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblBorder;
    private JTextField txtReqName;
    private JTextArea txtReqDescription;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
