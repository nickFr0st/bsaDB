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
        pnlOptions = new JPanel();
        btnSettings = new ButtonSideMenu();
        btnSignout = new ButtonSideMenu();
        pnlCards = new JPanel();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {75, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

        //======== pnlOptions ========
        {
            pnlOptions.setBackground(Color.white);
            pnlOptions.setName("pnlOptions");
            pnlOptions.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlOptions.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)pnlOptions.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)pnlOptions.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)pnlOptions.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- btnSettings ----
            btnSettings.setDefaultImage(new ImageIcon(getClass().getResource("/images/settings60.png")));
            btnSettings.setSelectedImage(new ImageIcon(getClass().getResource("/images/settings_blue60.png")));
            btnSettings.setName("btnSettings");
            pnlOptions.add(btnSettings, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

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
            pnlOptions.add(btnSignout, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(pnlOptions, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //======== pnlCards ========
        {
            pnlCards.setBackground(Color.white);
            pnlCards.setName("pnlCards");
            pnlCards.setLayout(new CardLayout());
        }
        add(pnlCards, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlOptions;
    private ButtonSideMenu btnSettings;
    private ButtonSideMenu btnSignout;
    private JPanel pnlCards;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
