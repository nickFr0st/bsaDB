/*
 * Created by JFormDesigner on Mon May 04 16:19:18 MDT 2015
 */

package bsaDb.client.home;

import bsaDb.client.BaseFrame;
import bsaDb.client.home.clientPnls.*;
import bsaDb.client.home.dialogs.export.ExportDialog;
import bsaDb.client.home.dialogs.imports.ImportDialog;
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
    private final static String ADVANCEMENT_PAGE = "advancement";
    private final static String MERIT_BADGE_PAGE = "meritBadge";

    private UserPanel pnlUser;
    private AdvancementPanel pnlAdvancement;
    private DatabaseSettingsPanel pnlDatabaseSettings;
    private MeritBadgePanel pnlMeritBadge;
    private BaseFrame baseFrame;

    private String propertyFileName;

    {
        propertyFileName = "/properties/users.properties";
    }

    public HomePanel() {
        initComponents();

        pnlCards.add(new SplashPanel(), SPLASH_PAGE);

        setupAccessRights();
    }

    private void setupAccessRights() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(propertyFileName));

            String currentUserName = properties.getProperty(KeyConst.CURRENT_USER.getName());
            User currentUser = CacheObject.getUserByUserName(currentUserName);

            if (currentUser.getId() == 1) {
                return;
            }

            turnOffAllRights();

            for (AccessRight accessRight : CacheObject.getAccessRights(currentUser.getId())) {
                if (accessRight.getRightId() == AccessRightConst.DATABASE_SETTINGS.getId()) {
                    mniDatabaseSettings.setEnabled(true);
                } else if (accessRight.getRightId() == AccessRightConst.USERS.getId()) {
                    mniUsers.setEnabled(true);
                } else if (accessRight.getRightId() == AccessRightConst.ADVANCEMENTS.getId()) {
                    mniAdvancements.setEnabled(true);
                } else if (accessRight.getRightId() == AccessRightConst.IMPORTS.getId()) {
                    mniImports.setEnabled(true);
                } else if (accessRight.getRightId() == AccessRightConst.EXPORTS.getId()) {
                    mniExports.setEnabled(true);
                } else if (accessRight.getRightId() == AccessRightConst.MERIT_BADGES.getId()) {
                    mniMeritBadges.setEnabled(true);
                }
            }

        } catch (IOException ignore) {
        }
    }

    private void turnOffAllRights() {
        mniDatabaseSettings.setEnabled(false);
        mniUsers.setEnabled(false);
        mniAdvancements.setEnabled(false);
        mniImports.setEnabled(false);
        mniExports.setEnabled(false);
        mniMeritBadges.setEnabled(false);
    }

    public HomePanel(BaseFrame baseFrame) {
        this();
        this.baseFrame = baseFrame;
    }

    public void slideCard(final String moveToPage) {
        ((CardLayout)pnlCards.getLayout()).show(pnlCards, moveToPage);
    }

    private void btnSignoutActionPerformed() {
        slideCard(SPLASH_PAGE);
        baseFrame.clearHomePnl();
        baseFrame.slideCard(BaseFrame.SIGN_IN_PAGE);
    }

    private void mnuDatabaseSettingsActionPerformed() {
        if (pnlDatabaseSettings == null) {
            pnlDatabaseSettings = new DatabaseSettingsPanel();
            pnlCards.add(pnlDatabaseSettings, DATABASE_SETTINGS_PAGE);
        }

        ((CardLayout)pnlCards.getLayout()).show(pnlCards, DATABASE_SETTINGS_PAGE);
    }

    private void mnuUsersActionPerformed() {
        if (pnlUser == null) {
            pnlUser = new UserPanel();
            pnlCards.add(pnlUser, USER_PAGE);
        }

        pnlUser.populateUserNameList();
        ((CardLayout)pnlCards.getLayout()).show(pnlCards, USER_PAGE);
    }

    private void mnuAdvancementsActionPerformed() {
        if (pnlAdvancement == null) {
            pnlAdvancement = new AdvancementPanel();
            pnlCards.add(pnlAdvancement, ADVANCEMENT_PAGE);
        }

        pnlAdvancement.populateAdvancementNameList();
        ((CardLayout)pnlCards.getLayout()).show(pnlCards, ADVANCEMENT_PAGE);
    }

    private void mnuSettingsMouseEntered() {
        mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings_blue24.png")));
    }

    private void mnuSettingsMouseExited() {
        mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings24.png")));
    }

    private void mnuFileMouseEntered() {
        mnuFile.setIcon(new ImageIcon(getClass().getResource("/images/file_blue24.png")));
    }

    private void mnuFileMouseExited() {
        mnuFile.setIcon(new ImageIcon(getClass().getResource("/images/file24.png")));
    }

    private void mniExitActionPerformed() {
        baseFrame.exit();
    }

    private void mniExportsActionPerformed() {
        ExportDialog dialog = new ExportDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
    }

    private void btnSignOutMouseEntered() {
        btnSignOut.setIcon(new ImageIcon(getClass().getResource("/images/signout_blue24.png")));
    }

    private void btnSignOutMouseExited() {
        btnSignOut.setIcon(new ImageIcon(getClass().getResource("/images/signout24.png")));
    }

    private void mniImportsActionPerformed() {
        ImportDialog dialog = new ImportDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);

        if (dialog.isSuccess()) {
            switch (dialog.getTypeConst()) {
                case ADVANCEMENT:
                    pnlAdvancement.populateAdvancementNameList();
                    break;
            }
        }
    }

    private void mniMeritBadgesActionPerformed() {
        if (pnlMeritBadge == null) {
            pnlMeritBadge = new MeritBadgePanel();
            pnlCards.add(pnlMeritBadge, MERIT_BADGE_PAGE);
        }

        pnlMeritBadge.populateMeritBadgeNameList();
        ((CardLayout)pnlCards.getLayout()).show(pnlCards, MERIT_BADGE_PAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JMenuBar menuBar1 = new JMenuBar();
        mnuFile = new JMenu();
        mniImports = new JMenuItem();
        mniExports = new JMenuItem();
        mniExit = new JMenuItem();
        mnuSetup = new JMenu();
        mniDatabaseSettings = new JMenuItem();
        mniUsers = new JMenuItem();
        mniAdvancements = new JMenuItem();
        mniMeritBadges = new JMenuItem();
        JPanel hSpacer1 = new JPanel(null);
        btnSignOut = new JButton();
        label1 = new JLabel();
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
                menuBar1.setBorderPainted(false);
                menuBar1.setName("menuBar1");

                //======== mnuFile ========
                {
                    mnuFile.setText("File");
                    mnuFile.setMnemonic('F');
                    mnuFile.setOpaque(false);
                    mnuFile.setForeground(Color.black);
                    mnuFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    mnuFile.setIcon(new ImageIcon(getClass().getResource("/images/file24.png")));
                    mnuFile.setName("mnuFile");
                    mnuFile.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            mnuFileMouseEntered();
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            mnuFileMouseExited();
                        }
                    });

                    //---- mniImports ----
                    mniImports.setText("Imports");
                    mniImports.setMnemonic('I');
                    mniImports.setName("mniImports");
                    mniImports.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mniImportsActionPerformed();
                        }
                    });
                    mnuFile.add(mniImports);

                    //---- mniExports ----
                    mniExports.setText("Exports");
                    mniExports.setMnemonic('E');
                    mniExports.setName("mniExports");
                    mniExports.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mniExportsActionPerformed();
                        }
                    });
                    mnuFile.add(mniExports);

                    //---- mniExit ----
                    mniExit.setText("Exit");
                    mniExit.setMnemonic('E');
                    mniExit.setName("mniExit");
                    mniExit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mniExitActionPerformed();
                        }
                    });
                    mnuFile.add(mniExit);
                }
                menuBar1.add(mnuFile);

                //======== mnuSetup ========
                {
                    mnuSetup.setText("Setup");
                    mnuSetup.setIcon(new ImageIcon(getClass().getResource("/images/settings24.png")));
                    mnuSetup.setForeground(Color.black);
                    mnuSetup.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    mnuSetup.setOpaque(false);
                    mnuSetup.setMnemonic('S');
                    mnuSetup.setName("mnuSetup");
                    mnuSetup.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            mnuSettingsMouseEntered();
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            mnuSettingsMouseExited();
                        }
                    });

                    //---- mniDatabaseSettings ----
                    mniDatabaseSettings.setText("Database Settings");
                    mniDatabaseSettings.setMnemonic('D');
                    mniDatabaseSettings.setName("mniDatabaseSettings");
                    mniDatabaseSettings.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mnuDatabaseSettingsActionPerformed();
                        }
                    });
                    mnuSetup.add(mniDatabaseSettings);

                    //---- mniUsers ----
                    mniUsers.setText("Users");
                    mniUsers.setMnemonic('U');
                    mniUsers.setName("mniUsers");
                    mniUsers.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mnuUsersActionPerformed();
                        }
                    });
                    mnuSetup.add(mniUsers);

                    //---- mniAdvancements ----
                    mniAdvancements.setText("Advancements");
                    mniAdvancements.setMnemonic('A');
                    mniAdvancements.setName("mniAdvancements");
                    mniAdvancements.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mnuAdvancementsActionPerformed();
                        }
                    });
                    mnuSetup.add(mniAdvancements);

                    //---- mniMeritBadges ----
                    mniMeritBadges.setText("Merit Badges");
                    mniMeritBadges.setMnemonic('M');
                    mniMeritBadges.setName("mniMeritBadges");
                    mniMeritBadges.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mniMeritBadgesActionPerformed();
                        }
                    });
                    mnuSetup.add(mniMeritBadges);
                }
                menuBar1.add(mnuSetup);

                //---- hSpacer1 ----
                hSpacer1.setOpaque(false);
                hSpacer1.setName("hSpacer1");
                menuBar1.add(hSpacer1);

                //---- btnSignOut ----
                btnSignOut.setText("Sign out");
                btnSignOut.setIcon(new ImageIcon(getClass().getResource("/images/signout24.png")));
                btnSignOut.setFont(new Font("Tahoma", Font.PLAIN, 12));
                btnSignOut.setForeground(Color.black);
                btnSignOut.setFocusPainted(false);
                btnSignOut.setOpaque(false);
                btnSignOut.setBorder(null);
                btnSignOut.setBackground(new Color(153, 153, 153));
                btnSignOut.setName("btnSignOut");
                btnSignOut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btnSignOutMouseEntered();
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        btnSignOutMouseExited();
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        btnSignoutActionPerformed();
                    }
                });
                menuBar1.add(btnSignOut);

                //---- label1 ----
                label1.setMinimumSize(new Dimension(10, 0));
                label1.setPreferredSize(new Dimension(10, 0));
                label1.setMaximumSize(new Dimension(10, 0));
                label1.setName("label1");
                menuBar1.add(label1);
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
    private JMenu mnuFile;
    private JMenuItem mniImports;
    private JMenuItem mniExports;
    private JMenuItem mniExit;
    private JMenu mnuSetup;
    private JMenuItem mniDatabaseSettings;
    private JMenuItem mniUsers;
    private JMenuItem mniAdvancements;
    private JMenuItem mniMeritBadges;
    private JButton btnSignOut;
    private JLabel label1;
    private JPanel pnlCards;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
