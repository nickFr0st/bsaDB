/*
 * Created by JFormDesigner on Mon May 04 16:19:18 MDT 2015
 */

package bsaDb.client.home;

import bsaDb.client.BaseFrame;
import bsaDb.client.customComponents.ButtonSideMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Nathanael
 */
public class HomePanel extends JPanel {
    private BaseFrame baseFrame;

    public HomePanel() {
        initComponents();
    }

    public HomePanel(BaseFrame baseFrame) {
        this();
        this.baseFrame = baseFrame;
    }

    private void btnSignoutMouseReleased() {
        baseFrame.slideCard(BaseFrame.SIGN_IN_PAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        btnSettings = new ButtonSideMenu();
        pnlOptions = new JPanel();
        btnSignout = new ButtonSideMenu();
        pnlCards = new JPanel();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {75, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(new Color(51, 102, 153));
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {30, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- btnSettings ----
            btnSettings.setDefaultImage(new ImageIcon(getClass().getResource("/images/settings24.png")));
            btnSettings.setSelectedImage(new ImageIcon(getClass().getResource("/images/settings_blue24.png")));
            btnSettings.setName("btnSettings");
            panel1.add(btnSettings, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 0, 5, 5), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== pnlOptions ========
        {
            pnlOptions.setBackground(Color.white);
            pnlOptions.setName("pnlOptions");
            pnlOptions.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlOptions.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)pnlOptions.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlOptions.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlOptions.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //---- btnSignout ----
            btnSignout.setDefaultImage(new ImageIcon(getClass().getResource("/images/sign_out60.png")));
            btnSignout.setSelectedImage(new ImageIcon(getClass().getResource("/images/sign_out_blue60.png")));
            btnSignout.setName("btnSignout");
            btnSignout.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    btnSignoutMouseReleased();
                }
            });
            pnlOptions.add(btnSignout, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlOptions, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //======== pnlCards ========
        {
            pnlCards.setBackground(Color.white);
            pnlCards.setName("pnlCards");
            pnlCards.setLayout(new CardLayout());
        }
        add(pnlCards, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private ButtonSideMenu btnSettings;
    private JPanel pnlOptions;
    private ButtonSideMenu btnSignout;
    private JPanel pnlCards;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
