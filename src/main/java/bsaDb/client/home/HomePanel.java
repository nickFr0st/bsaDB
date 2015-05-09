/*
 * Created by JFormDesigner on Mon May 04 16:19:18 MDT 2015
 */

package bsaDb.client.home;

import bsaDb.client.BaseFrame;
import bsaDb.client.home.clientPnls.NoDatabaseConnectionPanel;
import bsaDb.client.home.clientPnls.SplashPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Nathanael
 */
public class HomePanel extends JPanel {
    private final static String NO_CONNECTION_PAGE = "noConnection";
    private final static String SPLASH_PAGE = "splash";

    private BaseFrame baseFrame;

    public HomePanel() {
        initComponents();

        pnlCards.add(new NoDatabaseConnectionPanel(), NO_CONNECTION_PAGE);
        pnlCards.add(new SplashPanel(), SPLASH_PAGE);
    }

    public HomePanel(BaseFrame baseFrame) {
        this();
        this.baseFrame = baseFrame;
    }

    private void menu1MouseEntered() {
        mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings_blue24.png")));
    }

    private void mnuSettingsMouseExited() {
        mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings24.png")));
    }

    private void btnSignoutActionPerformed() {
        baseFrame.slideCard(BaseFrame.SIGN_IN_PAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JMenuBar menuBar1 = new JMenuBar();
        mnuSetup = new JMenu();
        JMenuItem mnuDatabaseSettings = new JMenuItem();
        JPanel hSpacer1 = new JPanel(null);
        JButton btnSignout = new JButton();
        pnlCards = new JPanel();

        //======== this ========
        setBackground(new Color(51, 102, 153));
        setBorder(new LineBorder(new Color(51, 102, 153), 2));
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setBackground(new Color(153, 153, 153));
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {30, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

            //======== menuBar1 ========
            {
                menuBar1.setBackground(new Color(153, 153, 153));
                menuBar1.setMargin(new Insets(0, 10, 0, 10));
                menuBar1.setName("menuBar1");

                //======== mnuSetup ========
                {
                    mnuSetup.setText("Setup");
                    mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings24.png")));
                    mnuSetup.setForeground(Color.black);
                    mnuSetup.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    mnuSetup.setOpaque(false);
                    mnuSetup.setName("mnuSetup");
                    mnuSetup.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            menu1MouseEntered();
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            mnuSettingsMouseExited();
                        }
                    });

                    //---- mnuDatabaseSettings ----
                    mnuDatabaseSettings.setText("Database Settings");
                    mnuDatabaseSettings.setName("mnuDatabaseSettings");
                    mnuSetup.add(mnuDatabaseSettings);
                }
                menuBar1.add(mnuSetup);

                //---- hSpacer1 ----
                hSpacer1.setOpaque(false);
                hSpacer1.setName("hSpacer1");
                menuBar1.add(hSpacer1);

                //---- btnSignout ----
                btnSignout.setText("Sign out");
                btnSignout.setForeground(Color.black);
                btnSignout.setFont(new Font("Tahoma", Font.PLAIN, 12));
                btnSignout.setPreferredSize(new Dimension(80, 24));
                btnSignout.setMinimumSize(new Dimension(80, 24));
                btnSignout.setMaximumSize(new Dimension(80, 24));
                btnSignout.setFocusPainted(false);
                btnSignout.setBackground(new Color(153, 153, 153));
                btnSignout.setBorderPainted(false);
                btnSignout.setOpaque(false);
                btnSignout.setName("btnSignout");
                btnSignout.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnSignoutActionPerformed();
                    }
                });
                menuBar1.add(btnSignout);
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
    private JMenu mnuSetup;
    private JPanel pnlCards;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
