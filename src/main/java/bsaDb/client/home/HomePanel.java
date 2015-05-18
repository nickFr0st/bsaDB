/*
 * Created by JFormDesigner on Mon May 04 16:19:18 MDT 2015
 */

package bsaDb.client.home;

import bsaDb.client.BaseFrame;
import bsaDb.client.home.clientPnls.DatabaseSettingsPanel;
import bsaDb.client.home.clientPnls.SplashPanel;
import bsaDb.client.home.clientPnls.UserPanel;
import constants.AccessRightConst;
import constants.KeyConst;
import objects.databaseObjects.AccessRight;
import objects.databaseObjects.User;
import util.CacheObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Nathanael
 */
public class HomePanel extends JPanel {
    private final static String SPLASH_PAGE = "splash";
    private final static String DATABASE_SETTINGS_PAGE = "databaseSettings";
    private final static String USER_PAGE = "user";

    private UserPanel pnlUser = new UserPanel();
    private BaseFrame baseFrame;
    private String propertyFileName;

    {
        propertyFileName = "/properties/users.properties";
    }

    public HomePanel() {
        initComponents();

        pnlUser = new UserPanel();

        pnlCards.add(new SplashPanel(), SPLASH_PAGE);
        pnlCards.add(new DatabaseSettingsPanel(), DATABASE_SETTINGS_PAGE);
        pnlCards.add(pnlUser, USER_PAGE);

        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(propertyFileName));

            String currentUserName = properties.getProperty(KeyConst.CURRENT_USER.getName());
            User currentUser = CacheObject.getUser(currentUserName);

            if (currentUser.getId() == 1) {
                return;
            }

            turnOffAllRights();

            for (AccessRight accessRight : CacheObject.getAccessRights(currentUser.getId())) {
                if (accessRight.getRightId() == AccessRightConst.DATABASE_SETTINGS.getId()) {
                    mnuDatabaseSettings.setEnabled(true);
                } else if (accessRight.getRightId() == AccessRightConst.USERS.getId()) {
                    mnuUsers.setEnabled(true);
                }
            }

        } catch (IOException ignore) {
        }
    }

    private void turnOffAllRights() {
        mnuDatabaseSettings.setEnabled(false);
        mnuUsers.setEnabled(false);
    }

    public HomePanel(BaseFrame baseFrame) {
        this();
        this.baseFrame = baseFrame;
    }

    public void slideCard(final String moveToPage) {
        ((CardLayout)pnlCards.getLayout()).show(pnlCards, moveToPage);
    }

    private void menu1MouseEntered() {
        mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings_blue24.png")));
    }

    private void mnuSettingsMouseExited() {
        mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings24.png")));
    }

    private void btnSignoutActionPerformed() {
        slideCard(SPLASH_PAGE);
        baseFrame.slideCard(BaseFrame.SIGN_IN_PAGE);
    }

    private void mnuDatabaseSettingsActionPerformed() {
        ((CardLayout)pnlCards.getLayout()).show(pnlCards, DATABASE_SETTINGS_PAGE);
    }

    private void mnuUsersActionPerformed() {
        pnlUser.populateUserNameList();
        ((CardLayout)pnlCards.getLayout()).show(pnlCards, USER_PAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JMenuBar menuBar1 = new JMenuBar();
        mnuSetup = new JMenu();
        mnuDatabaseSettings = new JMenuItem();
        mnuUsers = new JMenuItem();
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
                    mnuDatabaseSettings.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mnuDatabaseSettingsActionPerformed();
                        }
                    });
                    mnuSetup.add(mnuDatabaseSettings);

                    //---- mnuUsers ----
                    mnuUsers.setText("Users");
                    mnuUsers.setName("mnuUsers");
                    mnuUsers.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mnuUsersActionPerformed();
                        }
                    });
                    mnuSetup.add(mnuUsers);
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
    private JMenuItem mnuDatabaseSettings;
    private JMenuItem mnuUsers;
    private JPanel pnlCards;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
