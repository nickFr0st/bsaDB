/*
 * Created by JFormDesigner on Fri May 15 19:25:31 MDT 2015
 */

package bsaDb.client.home.clientPnls.common;

import constants.AccessRightConst;
import objects.databaseObjects.AccessRight;
import objects.databaseObjects.User;
import util.CacheObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author User #2
 */
public class AccessRightsPanel extends JPanel {

    public AccessRightsPanel() {
        initComponents();
    }

    @Override
    public void setEnabled(boolean enabled) {
        chkDatabaseSettings.setEnabled(enabled);
        chkImports.setEnabled(enabled);
        chkExports.setEnabled(enabled);
        chkUsers.setEnabled(enabled);
        chkAdvancements.setEnabled(enabled);
        chkMeritBadges.setEnabled(enabled);
        chkCampouts.setEnabled(enabled);
    }

    public void populateRights(User user) {
        if (user == null || user.getId() <= 1) {
            clearRights();
            return;
        }

        List<AccessRight> accessRightList = CacheObject.getAccessRights(user.getId());
        for (AccessRight accessRight : accessRightList) {
            if (accessRight.getRightId() == AccessRightConst.DATABASE_SETTINGS.getId()) {
                chkDatabaseSettings.setSelected(true);
            } else if (accessRight.getRightId() == AccessRightConst.USERS.getId()) {
                chkUsers.setSelected(true);
            } else if (accessRight.getRightId() == AccessRightConst.ADVANCEMENTS.getId()) {
                chkAdvancements.setSelected(true);
            } else if (accessRight.getRightId() == AccessRightConst.IMPORTS.getId()) {
                chkImports.setSelected(true);
            } else if (accessRight.getRightId() == AccessRightConst.EXPORTS.getId()) {
                chkExports.setSelected(true);
            } else if (accessRight.getRightId() == AccessRightConst.MERIT_BADGES.getId()) {
                chkMeritBadges.setSelected(true);
            } else if (accessRight.getRightId() == AccessRightConst.CAMPOUTS.getId()) {
                chkCampouts.setSelected(true);
            }
        }
    }

    public List<Integer> getAccessRightIdList() {
        List<Integer> accessRightIdList = new ArrayList<>();

        if (chkDatabaseSettings.isSelected()) {
            accessRightIdList.add(AccessRightConst.DATABASE_SETTINGS.getId());
        }
        if (chkUsers.isSelected()) {
            accessRightIdList.add(AccessRightConst.USERS.getId());
        }
        if (chkAdvancements.isSelected()) {
            accessRightIdList.add(AccessRightConst.ADVANCEMENTS.getId());
        }
        if (chkImports.isSelected()) {
            accessRightIdList.add(AccessRightConst.IMPORTS.getId());
        }
        if (chkExports.isSelected()) {
            accessRightIdList.add(AccessRightConst.EXPORTS.getId());
        }
        if (chkMeritBadges.isSelected()) {
            accessRightIdList.add(AccessRightConst.MERIT_BADGES.getId());
        }
        if (chkCampouts.isSelected()) {
            accessRightIdList.add(AccessRightConst.CAMPOUTS.getId());
        }

        return accessRightIdList;
    }

    private void clearRights() {
        chkDatabaseSettings.setSelected(false);
        chkUsers.setSelected(false);
        chkAdvancements.setSelected(false);
        chkImports.setSelected(false);
        chkExports.setSelected(false);
        chkMeritBadges.setSelected(false);
        chkCampouts.setSelected(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        chkDatabaseSettings = new JCheckBox();
        chkMeritBadges = new JCheckBox();
        chkImports = new JCheckBox();
        chkCampouts = new JCheckBox();
        chkExports = new JCheckBox();
        chkUsers = new JCheckBox();
        chkAdvancements = new JCheckBox();

        //======== this ========
        setBorder(new TitledBorder(new LineBorder(new Color(51, 102, 153)), "Access Rights", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION,
            new Font("Vijaya", Font.PLAIN, 22), new Color(51, 102, 153)));
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- chkDatabaseSettings ----
        chkDatabaseSettings.setText("Database Settings");
        chkDatabaseSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkDatabaseSettings.setForeground(Color.black);
        chkDatabaseSettings.setOpaque(false);
        chkDatabaseSettings.setName("chkDatabaseSettings");
        add(chkDatabaseSettings, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 20), 0, 0));

        //---- chkMeritBadges ----
        chkMeritBadges.setText("Merit Badges");
        chkMeritBadges.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkMeritBadges.setForeground(Color.black);
        chkMeritBadges.setOpaque(false);
        chkMeritBadges.setName("chkMeritBadges");
        add(chkMeritBadges, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 0), 0, 0));

        //---- chkImports ----
        chkImports.setText("Imports");
        chkImports.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkImports.setOpaque(false);
        chkImports.setForeground(Color.black);
        chkImports.setName("chkImports");
        add(chkImports, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 20), 0, 0));

        //---- chkCampouts ----
        chkCampouts.setText("Campouts");
        chkCampouts.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkCampouts.setForeground(Color.black);
        chkCampouts.setOpaque(false);
        chkCampouts.setName("chkCampouts");
        add(chkCampouts, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 0), 0, 0));

        //---- chkExports ----
        chkExports.setText("Exports");
        chkExports.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkExports.setOpaque(false);
        chkExports.setForeground(Color.black);
        chkExports.setName("chkExports");
        add(chkExports, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 20), 0, 0));

        //---- chkUsers ----
        chkUsers.setText("Users");
        chkUsers.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkUsers.setForeground(Color.black);
        chkUsers.setOpaque(false);
        chkUsers.setName("chkUsers");
        add(chkUsers, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 20), 0, 0));

        //---- chkAdvancements ----
        chkAdvancements.setText("Advancements");
        chkAdvancements.setFont(new Font("Tahoma", Font.PLAIN, 14));
        chkAdvancements.setForeground(Color.black);
        chkAdvancements.setOpaque(false);
        chkAdvancements.setName("chkAdvancements");
        add(chkAdvancements, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 20), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JCheckBox chkDatabaseSettings;
    private JCheckBox chkMeritBadges;
    private JCheckBox chkImports;
    private JCheckBox chkCampouts;
    private JCheckBox chkExports;
    private JCheckBox chkUsers;
    private JCheckBox chkAdvancements;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
