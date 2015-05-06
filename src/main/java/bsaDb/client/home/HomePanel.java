/*
 * Created by JFormDesigner on Mon May 04 16:19:18 MDT 2015
 */

package bsaDb.client.home;

import bsaDb.client.BaseFrame;
import bsaDb.client.customComponents.ButtonSideMenu;

import javax.swing.*;
import javax.swing.border.LineBorder;
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

    private void menu1MouseEntered() {
        mnuSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings_blue24.png")));
    }

    private void mnuSettingsMouseExited() {
        mnuSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings24.png")));
    }

    private void button1MouseReleased() {
        baseFrame.slideCard(BaseFrame.SIGN_IN_PAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        menuBar1 = new JMenuBar();
        mnuSettings = new JMenu();
        menuItem1 = new JMenuItem();
        hSpacer1 = new JPanel(null);
        button1 = new ButtonSideMenu();
        pnlCards = new JPanel();

        //======== this ========
        setBackground(Color.white);
        setBorder(new LineBorder(new Color(51, 102, 153), 2));
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {30, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //======== menuBar1 ========
            {
                menuBar1.setName("menuBar1");

                //======== mnuSettings ========
                {
                    mnuSettings.setText("Settings");
                    mnuSettings.setIcon(new ImageIcon(getClass().getResource("/images/settings24.png")));
                    mnuSettings.setForeground(Color.black);
                    mnuSettings.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    mnuSettings.setName("mnuSettings");
                    mnuSettings.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            menu1MouseEntered();
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            mnuSettingsMouseExited();
                        }
                    });

                    //---- menuItem1 ----
                    menuItem1.setText("Database Setup");
                    menuItem1.setName("menuItem1");
                    mnuSettings.add(menuItem1);
                }
                menuBar1.add(mnuSettings);

                //---- hSpacer1 ----
                hSpacer1.setOpaque(false);
                hSpacer1.setName("hSpacer1");
                menuBar1.add(hSpacer1);

                //---- button1 ----
                button1.setText("Sign out");
                button1.setDefaultImage(new ImageIcon(getClass().getResource("/images/sign_out24.png")));
                button1.setSelectedImage(new ImageIcon(getClass().getResource("/images/sign_out_blue24.png")));
                button1.setForeground(Color.black);
                button1.setFont(new Font("Tahoma", Font.PLAIN, 12));
                button1.setName("button1");
                button1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button1MouseReleased();
                    }
                });
                menuBar1.add(button1);
            }
            panel1.add(menuBar1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== pnlCards ========
        {
            pnlCards.setBackground(Color.white);
            pnlCards.setName("pnlCards");
            pnlCards.setLayout(new CardLayout());
        }
        add(pnlCards, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JMenuBar menuBar1;
    private JMenu mnuSettings;
    private JMenuItem menuItem1;
    private JPanel hSpacer1;
    private ButtonSideMenu button1;
    private JPanel pnlCards;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
