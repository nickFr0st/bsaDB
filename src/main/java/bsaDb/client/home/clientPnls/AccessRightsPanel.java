/*
 * Created by JFormDesigner on Fri May 15 19:25:31 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author User #2
 */
public class AccessRightsPanel extends JPanel {
    public AccessRightsPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        checkBox1 = new JCheckBox();
        checkBox2 = new JCheckBox();

        //======== this ========
        setBorder(new TitledBorder(new LineBorder(new Color(51, 102, 153)), "Access Rights", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION,
            new Font("Vijaya", Font.PLAIN, 22), new Color(51, 102, 153)));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

        //---- checkBox1 ----
        checkBox1.setText("Database Settings");
        checkBox1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        checkBox1.setForeground(Color.black);
        checkBox1.setOpaque(false);
        checkBox1.setName("checkBox1");
        add(checkBox1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0));

        //---- checkBox2 ----
        checkBox2.setText("Users");
        checkBox2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        checkBox2.setForeground(Color.black);
        checkBox2.setOpaque(false);
        checkBox2.setName("checkBox2");
        add(checkBox2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
