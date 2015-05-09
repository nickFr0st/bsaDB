/*
 * Created by JFormDesigner on Fri May 08 20:09:57 MDT 2015
 */

package bsaDb.client.customComponents;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class TitlePanel extends JPanel {
    private String title;

    public TitlePanel() {
        initComponents();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        lblTitle.setText(title);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblTitle = new JLabel();
        lblLine = new JLabel();

        //======== this ========
        setOpaque(false);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 7, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

        //---- lblTitle ----
        lblTitle.setText("Page Name");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblTitle.setForeground(new Color(51, 102, 153));
        lblTitle.setName("lblTitle");
        add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(10, 10, 0, 0), 0, 0));

        //---- lblLine ----
        lblLine.setBackground(new Color(51, 102, 153));
        lblLine.setOpaque(true);
        lblLine.setPreferredSize(new Dimension(0, 2));
        lblLine.setMinimumSize(new Dimension(0, 2));
        lblLine.setMaximumSize(new Dimension(0, 2));
        lblLine.setName("lblLine");
        add(lblLine, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
            new Insets(2, 10, 0, 10), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblTitle;
    private JLabel lblLine;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
