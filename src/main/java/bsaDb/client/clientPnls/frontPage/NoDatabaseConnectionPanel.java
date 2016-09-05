/*
 * Created by JFormDesigner on Thu May 07 20:23:08 MDT 2015
 */

package bsaDb.client.clientPnls.frontPage;

import bsaDb.client.baseFrame.BaseFrame;
import bsaDb.client.clientPnls.database.DatabaseSettingsPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class NoDatabaseConnectionPanel extends JPanel {
    BaseFrame baseFrame;

    public NoDatabaseConnectionPanel() {
        initComponents();
    }

    public NoDatabaseConnectionPanel(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        initComponents();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        label3 = new JLabel();
        label1 = new JLabel();
        databaseSettingsPanel1 = new DatabaseSettingsPanel(baseFrame, true);

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {32, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setOpaque(false);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {25, 0, 20, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {40, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

            //---- label3 ----
            label3.setIcon(new ImageIcon(getClass().getResource("/images/signInTitlebar.png")));
            label3.setName("label3");
            panel1.add(label3, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- label1 ----
            label1.setText("Please Setup a Database Connection");
            label1.setFont(new Font("Tahoma", Font.BOLD, 16));
            label1.setForeground(Color.black);
            label1.setName("label1");
            panel1.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 5, 5), 0, 0));

            //---- databaseSettingsPanel1 ----
            databaseSettingsPanel1.setName("databaseSettingsPanel1");
            panel1.add(databaseSettingsPanel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label3;
    private JLabel label1;
    private DatabaseSettingsPanel databaseSettingsPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
