/*
 * Created by JFormDesigner on Tue May 12 22:02:42 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.customComponents.JTextFieldDefaultText;
import bsaDb.client.customComponents.TitlePanel;
import bsaDb.client.customComponents.jdatepicker.JDatePicker;
import bsaDb.client.home.dialogs.*;
import constants.RequirementTypeConst;
import constants.ScoutTypeConst;
import objects.databaseObjects.*;
import objects.objectLogic.*;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import util.CacheObject;
import util.Util;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * @author User #2
 */
public class BoyScoutPanel extends JPanel {

    private final Color GOOD = new Color(0,107,63);
    private final Color AVG = new Color(0, 63, 135);
    private final Color WARNING = new Color(255,204,0);
    private final Color BAD = new Color(206, 17, 38);

    private BoyScout boyScout;

    private DefaultTableModel tblModelProgress;
    private DefaultTableModel tblModelSpecialAwards;

    DefaultListModel<MeritBadge> mdlMeritBadgeList;
    DefaultListModel<Camp> mdlCampList;

    public BoyScoutPanel() {
        mdlMeritBadgeList = new DefaultListModel();
        mdlCampList = new DefaultListModel();

        initComponents();

        setupProgressBars();
        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);
        clearAllErrors();

        scrollPane2.getVerticalScrollBar().setUnitIncrement(18);

        populateBoyScoutNameList();
        clearData();
        enableControls(false);
    }

    private void setupProgressBars() {
        barTimeLeft.setBackground(Color.white);
        lblTimeLeftDisplay.setText("");
        barTimeLeft.setValue(0);
        barTimeLeft.setForeground(AVG);

        barCampsAttended.setBackground(Color.white);
        barCampsAttended.setValue(0);
        barCampsAttended.setForeground(BAD);
        lblCampsAttendedDisplay.setText("");

        barWaitPeriod.setBackground(Color.white);
        barWaitPeriod.setValue(0);
        barWaitPeriod.setForeground(GOOD);
        lblWaitPeriodDisplay.setText("");

        barProgress.setBackground(Color.white);
        barProgress.setForeground(AVG);
        barProgress.setValue(0);
        lblProgressDisplay.setText("");
    }

    public void populateBoyScoutNameList() {
        listBoyScoutNames.setListData(Util.getSortedList((LinkedHashSet) LogicBoyScout.findAll()));
        listBoyScoutNames.revalidate();
        listBoyScoutNames.repaint();
    }

    private void txtSearchNameKeyReleased() {
        LinkedHashSet boyScoutSet = (LinkedHashSet) LogicBoyScout.findAll();
        if (txtSearchName.isMessageDefault()) {
            listBoyScoutNames.setListData(Util.getSortedList(boyScoutSet));
            listBoyScoutNames.revalidate();
            return;
        }

        List<BoyScout> filteredList = new ArrayList<>();
        for (BoyScout boyScout : (LinkedHashSet<BoyScout>) boyScoutSet) {
            if (boyScout.getName().toLowerCase().contains(txtSearchName.getText().toLowerCase())) {
                filteredList.add(boyScout);
            }
        }

        listBoyScoutNames.setListData(Util.getSortedList((ArrayList) filteredList));
        listBoyScoutNames.revalidate();
    }

    private void listBoyScoutNamesKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            listBoyScoutNamesMouseReleased();
        }
    }

    private void listBoyScoutNamesMouseReleased() {
        if (listBoyScoutNames.getSelectedValue() == null) {
            return;
        }

        clearData();

        boyScout = (BoyScout) listBoyScoutNames.getSelectedValue();
        loadData();

        clearAllErrors();
    }

    private void loadData() {
        if (boyScout == null) {
            return;
        }

        enableControls(true);

        loadSummaryTab();
        loadContactTab();
        loadDetailsTab();

        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
        btnSave.setVisible(false);
    }

    private void loadDetailsTab() {
        List<SpecialAward> specialAwardList = LogicSpecialAward.findAllByScoutIdAndScoutTypeId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId());
        if (!Util.isEmpty(specialAwardList)) {
            for (SpecialAward specialAward : specialAwardList) {
                tblModelSpecialAwards.addRow(new Object[]{specialAward.getName(), specialAward.getDescription(), specialAward.getDateReceived()});
            }
        }

        Set<ScoutMeritBadge> scoutMeritBadgeSet = LogicScoutMeritBadge.findByAllScoutIdScoutTypeId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId());
        if (!Util.isEmpty(scoutMeritBadgeSet)) {
            List<MeritBadge> meritBadgeList = new ArrayList<>();
            for (ScoutMeritBadge scoutMeritBadge : scoutMeritBadgeSet) {
                meritBadgeList.add(LogicMeritBadge.findById(scoutMeritBadge.getMeritBadgeId()));
            }

            mdlMeritBadgeList.removeAllElements();
            for (MeritBadge meritBadge : meritBadgeList) {
                mdlMeritBadgeList.addElement(meritBadge);
            }

            setBadgeTotals(meritBadgeList);
        }

        List<ScoutCamp> scoutCampList = LogicScoutCamp.findAllByScoutIdAndScoutTypeId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId());
        if (!Util.isEmpty(scoutCampList)) {
            List<Camp> campList = new ArrayList<>();
            for (ScoutCamp scoutCamp : scoutCampList) {
                campList.add(LogicCamp.findById(scoutCamp.getCampId()));
            }

            mdlCampList.removeAllElements();
            for (Camp camp : campList) {
                mdlCampList.addElement(camp);
            }
        }
    }

    private void setBadgeTotals(List<MeritBadge> meritBadgeList) {
        lblTotalBadgesValue.setText(String.valueOf(meritBadgeList.size()));

        int requiredCount = 0;
        for (MeritBadge badge : meritBadgeList) {
            if (badge.isRequiredForEagle()) {
                requiredCount++;
            }
        }

        lblTotalRequiredValue.setText(String.valueOf(requiredCount));
    }

    private void loadContactTab() {
        if (!Util.isEmpty(boyScout.getPhoneNumber())) {
            txtPhoneNumber.setText(boyScout.getPhoneNumber());
        }

        if (!Util.isEmpty(boyScout.getCity())) {
            txtCity.setText(boyScout.getCity());
        }

        if (!Util.isEmpty(boyScout.getState())) {
            txtState.setText(boyScout.getState());
        }

        if (!Util.isEmpty(boyScout.getStreet())) {
            txtStreet.setText(boyScout.getStreet());
        }

        if (!Util.isEmpty(boyScout.getZip())) {
            txtZip.setText(boyScout.getZip());
        }

        if (!Util.isEmpty(boyScout.getGuardianName())) {
            txtGuardianName.setText(boyScout.getGuardianName());
        }

        if (!Util.isEmpty(boyScout.getGuardianPhoneNumber())) {
            txtGuardianPhone.setText(boyScout.getGuardianPhoneNumber());
        }

        if (!Util.isEmpty(boyScout.getNote())) {
            txtNotes.setText(boyScout.getNote());
        }
    }

    private void loadSummaryTab() {

        txtName.setText(boyScout.getName());
        txtPosition.setText(boyScout.getPosition());
        cboRank.setSelectedItem(CacheObject.getAdvancement(boyScout.getAdvancementId()));

        Calendar rankDate = Calendar.getInstance();
        rankDate.setTime(boyScout.getAdvancementDate());
        cboRankDate.getModel().setDate(rankDate.get(Calendar.YEAR), rankDate.get(Calendar.MONTH), rankDate.get(Calendar.DATE));
        cboRankDate.getModel().setSelected(true);

        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(boyScout.getBirthDate());
        cboBirthDate.getModel().setDate(birthDate.get(Calendar.YEAR), birthDate.get(Calendar.MONTH), birthDate.get(Calendar.DATE));
        cboBirthDate.getModel().setSelected(true);

        LocalDate birthDateTime = new LocalDate(birthDate.get(Calendar.YEAR), birthDate.get(Calendar.MONTH) + 1, birthDate.get(Calendar.DATE));
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(birthDateTime, now);
        lblAgeValue.setText(Integer.toString(age.getYears()));

        setTimeLeftBar();
        setCampsAttendedBar();
        setWaitingPeriodBar();
        setProgressBar();
    }

    private void setAdvancementTable() {
        Advancement advancement = (Advancement)cboRank.getSelectedItem();
        if (advancement == null) {
            if (tblModelProgress.getRowCount() > 0) {
                for (int i = tblModelProgress.getRowCount() - 1; i > -1; i--) {
                    tblModelProgress.removeRow(i);
                }
            }
            return;
        }

        Set<Requirement> requirementSet = LogicRequirement.findAllByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());
        if (requirementSet.isEmpty()) {
            if (tblModelProgress.getRowCount() > 0) {
                for (int i = tblModelProgress.getRowCount() - 1; i > -1; i--) {
                    tblModelProgress.removeRow(i);
                }
            }
            return;
        }

        Set<ScoutRequirement> scoutRequirementSet = LogicScoutRequirement.findByAllScoutIdScoutTypeIdAndAdvancementId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId(), advancement.getId());

        for (Requirement requirement : requirementSet) {
            ScoutRequirement scoutRequirement = null;
            for (ScoutRequirement scoutReq : scoutRequirementSet) {
                if (scoutReq.getRequirementId() == requirement.getId()) {
                    scoutRequirement = scoutReq;
                    break;
                }
            }

            if (scoutRequirement == null) {
                tblModelProgress.addRow(new Object[]{Boolean.FALSE, requirement.getName(), null});
            } else {
                tblModelProgress.addRow(new Object[]{Boolean.TRUE, requirement.getName(), scoutRequirement.getDateCompleted()});
            }
        }
    }

    private void setProgressBar() {
        if (cboRank.getSelectedItem() == null) {
            barProgress.setValue(barProgress.getMaximum());
            barProgress.setForeground(BAD);
            lblProgressDisplay.setText("no advancement selected");
            return;
        }

        Advancement advancement = (Advancement) cboRank.getSelectedItem();

        Set<Requirement> requirementSet = LogicRequirement.findAllByParentIdAndTypeId(advancement.getId(), RequirementTypeConst.ADVANCEMENT.getId());
        if (Util.isEmpty(requirementSet)) {
            barProgress.setValue(barProgress.getMaximum());
            barProgress.setForeground(GOOD);
            lblProgressDisplay.setText("no requirements for badge");
            return;
        }

        barProgress.setMaximum(requirementSet.size());

        Set<ScoutRequirement> scoutRequirementSet = LogicScoutRequirement.findByAllScoutIdScoutTypeIdAndAdvancementId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId(), advancement.getId());
        int value = scoutRequirementSet.size();
        barProgress.setValue(value);
        lblProgressDisplay.setText(value + " of " + barProgress.getMaximum());

        if (value < barProgress.getMaximum() * .30) {
            barProgress.setForeground(BAD);
        } else if (value < barProgress.getMaximum() * .60) {
            barProgress.setForeground(WARNING);
        } else if (value < barProgress.getMaximum() * .80) {
            barProgress.setForeground(AVG);
        } else {
            barProgress.setForeground(GOOD);
        }
    }

    private void setWaitingPeriodBar() {
        if (cboRank.getSelectedItem() == null) {
            barWaitPeriod.setValue(barWaitPeriod.getMaximum());
            barWaitPeriod.setForeground(BAD);
            lblWaitPeriodDisplay.setText("no advancement selected");
            return;
        }

        Advancement advancement = (Advancement)cboRank.getSelectedItem();
        Integer timeRequirement = advancement.getTimeRequirement();
        if (timeRequirement == null || timeRequirement == 0) {
            barWaitPeriod.setValue(barWaitPeriod.getMaximum());
            barWaitPeriod.setForeground(GOOD);
            lblWaitPeriodDisplay.setText("no time requirement for this advancement");
            return;
        }

        if (!cboRankDate.getModel().isSelected()) {
            lblWaitPeriodDisplay.setText("rank date not select");
            barWaitPeriod.setValue(0);
            barWaitPeriod.setForeground(BAD);
            return;
        }

        Calendar rankDate = Calendar.getInstance();
        rankDate.set(Calendar.YEAR, cboRankDate.getModel().getYear());
        rankDate.set(Calendar.MONTH, cboRankDate.getModel().getMonth());
        rankDate.set(Calendar.DATE, cboRankDate.getModel().getDay());

        Date now = new Date();
        if (rankDate.getTime().getTime() > now.getTime()) {
            lblWaitPeriodDisplay.setText("rank date cannot be after today's date");
            barWaitPeriod.setValue(0);
            barWaitPeriod.setForeground(BAD);
            return;
        }

        Calendar completedDate = Calendar.getInstance();
        completedDate.set(Calendar.YEAR, cboRankDate.getModel().getYear());
        completedDate.set(Calendar.MONTH, cboRankDate.getModel().getMonth() + timeRequirement);
        completedDate.set(Calendar.DATE, cboRankDate.getModel().getDay());

        LocalDate rankTime = new LocalDate(cboRankDate.getModel().getYear(), cboRankDate.getModel().getMonth() + 1, cboRankDate.getModel().getDay());
        LocalDate dateCompleted = new LocalDate(completedDate.get(Calendar.YEAR), completedDate.get(Calendar.MONTH) + 1, completedDate.get(Calendar.DATE));

        Days days = Days.daysBetween(rankTime, dateCompleted);
        barWaitPeriod.setMaximum(days.getDays());

        LocalDate localDate = new LocalDate();
        Days remaining = Days.daysBetween(rankTime, localDate);

        int value = 0;
        if (remaining.getDays() >= barWaitPeriod.getMaximum()) {
            value = barWaitPeriod.getMaximum();
        } else if (remaining.getDays() > 0) {
            value = remaining.getDays();
        }

        barWaitPeriod.setValue(value);

        if (value < barWaitPeriod.getMaximum() * .30) {
            barWaitPeriod.setForeground(BAD);
        } else if (value < barWaitPeriod.getMaximum() * .60) {
            barWaitPeriod.setForeground(WARNING);
        } else if (value < barWaitPeriod.getMaximum() * .80) {
            barWaitPeriod.setForeground(AVG);
        } else {
            barWaitPeriod.setForeground(GOOD);
        }

        if (value >= barWaitPeriod.getMaximum()) {
            lblWaitPeriodDisplay.setText("waiting period requirement complete");
            return;
        }

        long diff = completedDate.getTime().getTime() - now.getTime();
        LocalDate displayDate = new LocalDate(diff);

        String display = "";
        String comma = "";
        int month = displayDate.getMonthOfYear() - 1;
        if (month > 0) {
            display += month + " mo";
            comma = ", ";
        }

        int day = displayDate.getDayOfMonth() - 1;
        if (day > 0) {
            display += comma;
            display += day + " day";
            if (day > 1) {
                display += "s";
            }
        }

        if (Util.isEmpty(display)) {
            display = "waiting period requirement complete";
            barWaitPeriod.setValue(barWaitPeriod.getMaximum());
        }

        lblWaitPeriodDisplay.setText(display);
    }

    private void setCampsAttendedBar() {
        if (boyScout.getId() < 0) {
            barCampsAttended.setValue(0);
            barCampsAttended.setForeground(BAD);
            lblCampsAttendedDisplay.setText("0 of " + barCampsAttended.getMaximum());
            return;
        }

        List<ScoutCamp> scoutCampList = LogicScoutCamp.findAllByScoutIdAndScoutTypeId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId());
        if (scoutCampList.isEmpty()) {
            barCampsAttended.setValue(0);
            barCampsAttended.setForeground(BAD);
            lblCampsAttendedDisplay.setText("0 of " + barCampsAttended.getMaximum());
            return;
        }

        int value = 0;
        for (ScoutCamp scoutCamp : scoutCampList) {
            value += scoutCamp.getNumberOfNights();
        }
        barCampsAttended.setValue(value);
        lblCampsAttendedDisplay.setText(value + " of " + barCampsAttended.getMaximum());

        if (value < 6) {
            barCampsAttended.setForeground(BAD);
        } else if (value < 12) {
            barCampsAttended.setForeground(WARNING);
        } else if (value < 16) {
            barCampsAttended.setForeground(AVG);
        } else {
            barCampsAttended.setForeground(GOOD);
        }
    }

    private void setTimeLeftBar() {
        if (!cboBirthDate.getModel().isSelected()) {
            lblTimeLeftDisplay.setText("birth date not select");
            barTimeLeft.setValue(0);
            barTimeLeft.setForeground(AVG);
            return;
        }

        Calendar birthDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, cboBirthDate.getModel().getYear());
        birthDate.set(Calendar.MONTH, cboBirthDate.getModel().getMonth());
        birthDate.set(Calendar.DATE, cboBirthDate.getModel().getDay());

        if (birthDate.getTime().getTime() >= new Date().getTime()) {
            lblTimeLeftDisplay.setText("scout hasn't been born yet");
            barTimeLeft.setValue(barTimeLeft.getMaximum());
            barTimeLeft.setForeground(BAD);
            return;
        }

        Calendar minAgeDate = Calendar.getInstance();
        minAgeDate.add(Calendar.YEAR, -11);

        if (birthDate.getTimeInMillis() - minAgeDate.getTimeInMillis() >= 0) {
            lblTimeLeftDisplay.setText("scout is too young");
            barTimeLeft.setValue(barTimeLeft.getMaximum());
            barTimeLeft.setForeground(BAD);
            return;
        }

        Calendar maxAgeDate = Calendar.getInstance();
        maxAgeDate.add(Calendar.YEAR, -14);

        long displayTime = birthDate.getTimeInMillis() - maxAgeDate.getTimeInMillis();
        if (displayTime <= 0) {
            lblTimeLeftDisplay.setText("age requirement complete");
            barTimeLeft.setValue(barTimeLeft.getMaximum());
            barTimeLeft.setForeground(BAD);
            return;
        }

        LocalDate birthDateTime = new LocalDate(birthDate.getTimeInMillis());
        LocalDate minDate = new LocalDate(minAgeDate.getTimeInMillis());
        Days minDays = Days.daysBetween(birthDateTime, minDate);
        int value = minDays.getDays();

        barTimeLeft.setValue(value);

        if (value < barTimeLeft.getMaximum() * .30) {
            barTimeLeft.setForeground(GOOD);
        } else if (value < barTimeLeft.getMaximum() * .60) {
            barTimeLeft.setForeground(AVG);
        } else if (value < barTimeLeft.getMaximum() * .90) {
            barTimeLeft.setForeground(WARNING);
        } else {
            barTimeLeft.setForeground(BAD);
        }

        LocalDate maxDate = new LocalDate(maxAgeDate.getTimeInMillis());

        String display = "";
        String comma = "";
        LocalDate displayDate = new LocalDate(displayTime);
        Years year =  Years.yearsBetween(maxDate, birthDateTime);
        if (year.getYears() > 0) {
            display += year.getYears() + " yr";
            if (year.getYears() > 1) {
                display += "s";
            }
            comma = ", ";
        }

        int month = displayDate.getMonthOfYear() -1;
        if (month > 0) {
            display += comma;
            display += month + " mo";
            comma = ", ";
        }

        int day = displayDate.getDayOfMonth() -1;
        if (day > 0) {
            display += comma;
            display += day + " day";
            if (day > 1) {
                display += "s";
            }
        }

        if (Util.isEmpty(display)) {
            display = "age requirement complete";
        }

        lblTimeLeftDisplay.setText(display);
    }

    private void enableControls(boolean enable) {
        // Settings Tab
        txtName.setEnabled(enable);
        txtPosition.setEnabled(enable);
        cboRank.setEnabled(enable);
        cboRankDate.setEnabled(enable);
        cboBirthDate.setEnabled(enable);
        tblProgress.setEnabled(enable);
        btnEditAdvancementProgress.setEnabled(enable);

        // Contact Info Tab
        txtPhoneNumber.setEnabled(enable);
        txtCity.setEnabled(enable);
        txtStreet.setEnabled(enable);
        txtGuardianName.setEnabled(enable);
        txtState.setEnabled(enable);
        txtZip.setEnabled(enable);
        txtGuardianPhone.setEnabled(enable);
        txtNotes.setEnabled(enable);

        // Details Tab
        tblSpecialAwards.setEnabled(enable);
        listMeritBadges.setEnabled(enable);
        listCamps.setEnabled(enable);
        btnAddAward.setEnabled(enable);
        btnRemoveAward.setEnabled(enable);
        btnAddMeritBadge.setEnabled(enable);
        btnRemoveMeritBadge.setEnabled(enable);
        btnAddCamp.setEnabled(enable);
        btnRemoveCamp.setEnabled(enable);
        btnEditSpecialAward.setEnabled(enable);
    }

    private void clearAllErrors() {
        // Settings Tab
        Util.clearError(lblNameError);
        Util.clearError(lblBirthDateError);
        Util.clearError(lblRankDateError);

        // Contact Info Tab
        Util.clearError(lblPhoneNumberError);
        Util.clearError(lblGuardianPhoneError);
    }

    private void btnNewActionPerformed() {
        btnSave.setVisible(true);
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);

        enableControls(true);
        clearAllErrors();
        clearData();

        txtName.requestFocus();
    }

    private void clearData() {
        boyScout = null;

        clearSummaryTab();
        clearContactTab();
        clearDetailsTab();
    }

    private void clearDetailsTab() {
        if (tblModelSpecialAwards.getRowCount() > 0) {
            for (int i = tblModelSpecialAwards.getRowCount() - 1; i > -1; i--) {
                tblModelSpecialAwards.removeRow(i);
            }
        }

        mdlMeritBadgeList.removeAllElements();
        mdlCampList.removeAllElements();
    }

    private void clearContactTab() {
        txtPhoneNumber.setDefault();
        txtCity.setDefault();
        txtState.setDefault();
        txtStreet.setDefault();
        txtZip.setDefault();
        txtGuardianName.setDefault();
        txtGuardianPhone.setDefault();
        txtNotes.setText("");
    }

    private void clearSummaryTab() {
        txtName.setDefault();
        txtPosition.setDefault();

        cboRank.removeAllItems();
        Collection<Advancement> advancementList = CacheObject.getAdvancementList();
        if (!Util.isEmpty(advancementList)) {
            for (Advancement advancement : advancementList) {
                cboRank.addItem(advancement);
            }
        }

        if (tblModelProgress.getRowCount() > 0) {
            for (int i = tblModelProgress.getRowCount() - 1; i > -1; i--) {
                tblModelProgress.removeRow(i);
            }
        }

        Calendar now = Calendar.getInstance();
        cboRankDate.getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
        cboRankDate.getModel().setSelected(false);

        cboBirthDate.getModel().setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
        cboBirthDate.getModel().setSelected(false);

        lblAgeValue.setText("");
        lblTotalBadgesValue.setText("");
        lblTotalRequiredValue.setText("");

        setupProgressBars();
    }

    private void btnSaveActionPerformed() {
        if (!validateFields()) {
            return;
        }

        setData();

        boyScout = LogicBoyScout.save(boyScout);

        saveRelatedScoutData();

        clearAllErrors();

        btnSave.setVisible(false);
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);

        populateBoyScoutNameList();

        listBoyScoutNames.setSelectedValue(boyScout, true);
    }

    private void saveRelatedScoutData() {
        List<ScoutRequirement> scoutRequirementList = new ArrayList<>();
        for (int i = 0; i < tblModelProgress.getRowCount(); ++i) {
            Boolean isCompleted = (Boolean) tblModelProgress.getValueAt(i, 0);
            if (!isCompleted) {
                continue;
            }

            String requirementName = (String) tblModelProgress.getValueAt(i, 1);
            Date dateCompleted = (Date) tblModelProgress.getValueAt(i, 2);

            ScoutRequirement scoutRequirement = new ScoutRequirement();
            scoutRequirement.setAdvancementId(boyScout.getAdvancementId());
            scoutRequirement.setDateCompleted(dateCompleted);
            scoutRequirement.setScoutId(boyScout.getId());
            scoutRequirement.setScoutTypeId(ScoutTypeConst.BOY_SCOUT.getId());
            scoutRequirement.setRequirementId(LogicRequirement.findByParentIdAndTypeIdAndName(boyScout.getAdvancementId(), RequirementTypeConst.ADVANCEMENT.getId(), requirementName).getId());

            scoutRequirementList.add(scoutRequirement);
        }

        if (!Util.isEmpty(scoutRequirementList)) {
            LogicScoutRequirement.save(scoutRequirementList);
        }

        List<SpecialAward> specialAwardList = new ArrayList<>();
        for (int i = 0; i < tblModelSpecialAwards.getRowCount(); ++i) {
            String name = (String) tblModelSpecialAwards.getValueAt(i, 0);
            String description = (String) tblModelSpecialAwards.getValueAt(i, 1);
            Date dateReceived = (Date) tblModelSpecialAwards.getValueAt(i, 2);

            SpecialAward specialAward = new SpecialAward();
            specialAward.setName(name);
            specialAward.setDescription(description);
            specialAward.setDateReceived(dateReceived);
            specialAward.setScoutId(boyScout.getId());
            specialAward.setScoutTypeId(ScoutTypeConst.BOY_SCOUT.getId());

            specialAwardList.add(specialAward);
        }

        if (!Util.isEmpty(specialAwardList)) {
            LogicSpecialAward.save(specialAwardList);
        }

        List<ScoutMeritBadge> scoutMeritBadgeList = new ArrayList<>();
        for (int i = 0; i < listMeritBadges.getModel().getSize(); i++) {
            MeritBadge meritBadge = (MeritBadge) listMeritBadges.getModel().getElementAt(i);

            ScoutMeritBadge scoutMeritBadge = new ScoutMeritBadge();
            scoutMeritBadge.setScoutTypeId(ScoutTypeConst.BOY_SCOUT.getId());
            scoutMeritBadge.setScoutId(boyScout.getId());
            scoutMeritBadge.setMeritBadgeId(meritBadge.getId());

            scoutMeritBadgeList.add(scoutMeritBadge);
        }

        if (!Util.isEmpty(scoutMeritBadgeList)) {
            LogicScoutMeritBadge.save(scoutMeritBadgeList);
        }

        List<ScoutCamp> scoutCampList = new ArrayList<>();
        for (int i = 0; i < listCamps.getModel().getSize(); i++) {
            Camp camp = (Camp) listCamps.getModel().getElementAt(i);

            ScoutCamp scoutCamp = new ScoutCamp();
            scoutCamp.setScoutTypeId(ScoutTypeConst.BOY_SCOUT.getId());
            scoutCamp.setScoutId(boyScout.getId());
            scoutCamp.setCampId(camp.getId());
            scoutCamp.setNumberOfNights(camp.getNumberOfNights());

            scoutCampList.add(scoutCamp);
        }

        if (!Util.isEmpty(scoutCampList)) {
            LogicScoutCamp.save(scoutCampList);
        }
    }

    private void setData() {
        if (boyScout == null) {
            boyScout = new BoyScout();
        }

        boyScout.setName(txtName.getText());
        if (!txtPosition.isMessageDefault()) {
            boyScout.setPosition(txtPosition.getText());
        }
        boyScout.setAdvancementId(((Advancement)cboRank.getSelectedItem()).getId());

        Calendar birthDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, cboBirthDate.getModel().getYear());
        birthDate.set(Calendar.MONTH, cboBirthDate.getModel().getMonth());
        birthDate.set(Calendar.DATE, cboBirthDate.getModel().getDay());
        boyScout.setBirthDate(birthDate.getTime());

        Calendar rankDate = Calendar.getInstance();
        rankDate.set(Calendar.YEAR, cboRankDate.getModel().getYear());
        rankDate.set(Calendar.MONTH, cboRankDate.getModel().getMonth());
        rankDate.set(Calendar.DATE, cboRankDate.getModel().getDay());
        boyScout.setAdvancementDate(rankDate.getTime());

        if (!txtPhoneNumber.isMessageDefault()) {
            boyScout.setPhoneNumber(txtPhoneNumber.getText());
        }

        if (!txtCity.isMessageDefault()) {
            boyScout.setCity(txtCity.getText());
        }

        if (!txtState.isMessageDefault()) {
            boyScout.setState(txtState.getText());
        }

        if (!txtStreet.isMessageDefault()) {
            boyScout.setStreet(txtStreet.getText());
        }

        if (!txtZip.isMessageDefault()) {
            boyScout.setZip(txtZip.getText());
        }

        if (!txtGuardianName.isMessageDefault()) {
            boyScout.setGuardianName(txtGuardianName.getText());
        }

        if (!txtGuardianPhone.isMessageDefault()) {
            boyScout.setGuardianPhoneNumber(txtGuardianPhone.getText());
        }

        if (!Util.isEmpty(txtNotes.getText())) {
            boyScout.setNote(txtNotes.getText());
        }
    }

    private boolean validateFields() {
        boolean valid = true;

        if (!validateBoyScoutName()) {
            valid = false;
        }

        if (!cboRankDate.getModel().isSelected()) {
            Util.setError(lblRankDateError, "must select a valid rank date");
            valid = false;
        }

        if (lblWaitPeriodDisplay.getText().equals("rank date cannot be after today's date") || lblWaitPeriodDisplay.getText().equals("rank date not select")) {
            Util.setError(lblRankDateError, "must select a valid rank date");
            valid = false;
        }

        if (!cboBirthDate.getModel().isSelected()) {
            Util.setError(lblBirthDateError, "must select a valid birth date");
            valid = false;
        }

        if (lblTimeLeftDisplay.getText().equals("birth date not select") || lblTimeLeftDisplay.getText().equals("scout hasn't been born yet") || lblTimeLeftDisplay.getText().equals("scout is too young")) {
            Util.setError(lblBirthDateError, "must select a valid birth date");
            valid = false;
        }

        if (!txtPhoneNumber.isMessageDefault() && lblPhoneNumberError.isVisible()) {
            valid = false;
        }

        if (!txtGuardianPhone.isMessageDefault() && lblGuardianPhoneError.isVisible()) {
            valid = false;
        }

        return valid;
    }

    private void btnUpdateActionPerformed() {
        if (listBoyScoutNames.getSelectedValue() == null) {
            return;
        }

        if (!validateFields()) {
            return;
        }

        setData();
        boyScout = LogicBoyScout.update(boyScout);

        LogicScoutRequirement.delete(LogicScoutRequirement.findByAllScoutIdScoutTypeIdAndAdvancementId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId(), boyScout.getAdvancementId()));
        LogicSpecialAward.delete(LogicSpecialAward.findAllByScoutIdAndScoutTypeId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId()));
        LogicScoutMeritBadge.delete(LogicScoutMeritBadge.findByAllScoutIdScoutTypeId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId()));
        LogicScoutCamp.delete(LogicScoutCamp.findAllByScoutIdAndScoutTypeId(boyScout.getId(), ScoutTypeConst.BOY_SCOUT.getId()));

        saveRelatedScoutData();

        clearAllErrors();

        populateBoyScoutNameList();

        listBoyScoutNames.setSelectedValue(boyScout, true);
    }

    private void btnDeleteActionPerformed() {
        if (listBoyScoutNames.getSelectedValue() == null) {
            return;
        }

        if (new MessageDialog(Util.getParent(this), "Delete Boy Scout", "Are you sure you want to delete the selected boy scout?", MessageDialog.MessageType.QUESTION, MessageDialog.ButtonType.YES_NO).getChoice() != MessageDialog.OPTION_YES) {
            return;
        }

        int boyScoutId = boyScout.getId();

        List<SpecialAward> specialAwardList = LogicSpecialAward.findAllByScoutIdAndScoutTypeId(boyScoutId, ScoutTypeConst.BOY_SCOUT.getId());
        if (!Util.isEmpty(specialAwardList)) {
            LogicSpecialAward.delete(specialAwardList);
        }

        Set<ScoutMeritBadge> scoutMeritBadgeList = LogicScoutMeritBadge.findByAllScoutIdScoutTypeId(boyScoutId, ScoutTypeConst.BOY_SCOUT.getId());
        if (!Util.isEmpty(scoutMeritBadgeList)) {
            LogicScoutMeritBadge.delete(scoutMeritBadgeList);
        }

        List<ScoutCamp> scoutCampList = LogicScoutCamp.findAllByScoutIdAndScoutTypeId(boyScoutId, ScoutTypeConst.BOY_SCOUT.getId());
        if (!Util.isEmpty(scoutCampList)) {
            LogicScoutCamp.delete(scoutCampList);
        }

        Collection<Advancement> advancementList = CacheObject.getAdvancementList();
        List<ScoutRequirement> scoutRequirementList = new ArrayList<>();
        if (!Util.isEmpty(advancementList)) {
            for (Advancement advancement : advancementList) {
                scoutRequirementList.addAll(LogicScoutRequirement.findByAllScoutIdScoutTypeIdAndAdvancementId(boyScoutId, ScoutTypeConst.BOY_SCOUT.getId(), advancement.getId()));
            }
        }

        if (!Util.isEmpty(scoutRequirementList)) {
            LogicScoutRequirement.delete(scoutRequirementList);
        }

        LogicBoyScout.delete(boyScout);

        populateBoyScoutNameList();

        btnDelete.setVisible(false);
        btnSave.setVisible(false);
        btnUpdate.setVisible(false);

        clearAllErrors();
        clearData();
        enableControls(false);
    }

    private void validatePhoneNumber() {
        validatePhoneNum();
    }

    private void validateGuardianPhoneNumber() {
        validateGuardianPhoneNum();
    }

    private boolean validateGuardianPhoneNum() {
        Util.clearError(lblGuardianPhoneError);

        if (txtGuardianPhone.isMessageDefault() || txtGuardianPhone.getText().isEmpty()) {
            return true;
        }

        if (!Util.validatePhoneNumber(txtGuardianPhone.getText())) {
            Util.setError(lblGuardianPhoneError, "Invalid phone number format");
            return false;
        }

        return true;
    }

    private boolean validatePhoneNum() {
        Util.clearError(lblPhoneNumberError);

        if (txtPhoneNumber.isMessageDefault() || txtPhoneNumber.getText().isEmpty()) {
            return true;
        }

        if (!Util.validatePhoneNumber(txtPhoneNumber.getText())) {
            Util.setError(lblPhoneNumberError, "Invalid phone number format");
            return false;
        }

        return true;
    }

    private void cboBirthDateActionPerformed() {
        Util.clearError(lblBirthDateError);

        if (!cboBirthDate.getModel().isSelected()) {
            Util.setError(lblBirthDateError, "Must select a birth date");
            lblAgeValue.setText("n/a");
        } else {
            LocalDate birthDateTime = new LocalDate(cboBirthDate.getModel().getYear(), cboBirthDate.getModel().getMonth() + 1, cboBirthDate.getModel().getDay());
            LocalDate now = new LocalDate();
            Years age = Years.yearsBetween(birthDateTime, now);

            if (age.getYears() < 0) {
                lblAgeValue.setText("0");
            } else {
                lblAgeValue.setText(Integer.toString(age.getYears()));
            }
        }

        setTimeLeftBar();
    }

    private void cboRankDateActionPerformed() {
        if (boyScout == null) {
            return;
        }
        Util.clearError(lblRankDateError);

        if (!cboRankDate.getModel().isSelected()) {
            Util.setError(lblRankDateError, "must select a valid rank date");
        }

        setWaitingPeriodBar();
    }

    private void updateProgressTable() {
        if (tblModelProgress.getRowCount() > 0) {
            for (int i = tblModelProgress.getRowCount() - 1; i > -1; i--) {
                tblModelProgress.removeRow(i);
            }
        }

        setAdvancementTable();
    }

    private void cboRankActionPerformed() {
        if (boyScout == null) {
            return;
        }
        cboRankDateActionPerformed();

        setProgressBar();
        updateProgressTable();
    }

    private void createUIComponents() {
        createSpecialAwardsTable();
        createProgressTable();
    }

    private void createSpecialAwardsTable() {
        tblModelSpecialAwards = new DefaultTableModel(new Object[] {"Name", "Description", "Date Received"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 2:
                        return Date.class;
                    default:
                        return String.class;
                }
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Date) {
                    value = Util.DISPLAY_DATE_FORMAT_LONG.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        tblSpecialAwards = new JTable();
        tblSpecialAwards.setDefaultRenderer(String.class, centerRenderer);
        tblSpecialAwards.setDefaultRenderer(Date.class, centerRenderer);
        tblSpecialAwards.setBackground(Color.WHITE);
        tblSpecialAwards.setFillsViewportHeight(true);
        tblSpecialAwards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tblSpecialAwards.getTableHeader();
        header.setBackground(new Color(0, 63, 135));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Tahoma", Font.PLAIN, 14));

        tblSpecialAwards.setModel(tblModelSpecialAwards);
    }

    private void createProgressTable() {
        tblModelProgress = new DefaultTableModel(new Object[] {"Completed", "Requirement", "Date Completed"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Boolean.class;
                    case 2:
                        return Date.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Date) {
                    value = Util.DISPLAY_DATE_FORMAT_LONG.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);


        tblProgress = new JTable();
        tblProgress.setDefaultRenderer(String.class, centerRenderer);
        tblProgress.setDefaultRenderer(Date.class, centerRenderer);
        tblProgress.setBackground(Color.WHITE);
        tblProgress.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tblProgress.getTableHeader();
        header.setBackground(new Color(0, 63, 135));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Tahoma", Font.PLAIN, 14));

        tblProgress.setModel(tblModelProgress);
        tblProgress.setSurrendersFocusOnKeystroke(true);
    }

    private void btnEditAdvancementProgressMouseReleased() {
        int selectedRow = tblProgress.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        Boolean isCompleted = (Boolean) tblModelProgress.getValueAt(selectedRow, 0);
        String name = (String) tblModelProgress.getValueAt(selectedRow, 1);
        Date dateCompleted = (Date) tblModelProgress.getValueAt(selectedRow, 2);
        EditScoutAdvancementDialog dialog = new EditScoutAdvancementDialog(Util.getParent(this), name, isCompleted, dateCompleted);

        if (dialog.getBtnChoice() != EditScoutAdvancementDialog.BTN_OK) {
            return;
        }

        tblModelProgress.setValueAt(dialog.getCompleted(), selectedRow, 0);
        tblModelProgress.setValueAt(dialog.getCompletedDate(), selectedRow, 2);
    }

    private void validateName() {
        validateBoyScoutName();
    }

    private boolean validateBoyScoutName() {
        Util.clearError(lblNameError);

        if (txtName.isMessageDefault() || txtName.getText().trim().isEmpty()) {
            Util.setError(lblNameError, "Name cannot be left blank");
            return false;
        }

        Scout tempBoyScout = LogicBoyScout.findByName(txtName.getText());
        if (tempBoyScout == null) {
            return true;
        }

        if (boyScout == null || !tempBoyScout.getName().equals(boyScout.getName())) {
            Util.setError(lblNameError, "A Boy Scout with the name '" + txtName.getText() + "' already exists");
            return false;
        }

        return true;
    }

    private void btnAddMeritBadgeMouseReleased() {
        List<MeritBadge> meritBadgeList = new ArrayList<>();
        for (int i = 0; i < mdlMeritBadgeList.getSize(); i++) {
            meritBadgeList.add(mdlMeritBadgeList.getElementAt(i));
        }

        ScoutMeritBadgeDialog dialog = new ScoutMeritBadgeDialog((JFrame) SwingUtilities.getWindowAncestor(this), meritBadgeList);
        if (dialog.getBtnChoice() != ScoutMeritBadgeDialog.BTN_OK) {
            return;
        }

        mdlMeritBadgeList.removeAllElements();
        for (MeritBadge meritBadge : dialog.getSelectedMeritBadges()) {
            mdlMeritBadgeList.addElement(meritBadge);
        }
    }

    private void btnRemoveMeritBadgeMouseReleased() {
        if (listMeritBadges.getSelectedIndex() == -1) {
            return;
        }

        mdlMeritBadgeList.removeElementAt(listMeritBadges.getSelectedIndex());
    }

    private void btnAddCampMouseReleased() {
        List<Camp> campList = new ArrayList<>();
        for (int i = 0; i < mdlCampList.getSize(); i++) {
            campList.add(mdlCampList.getElementAt(i));
        }

        ScoutCampDialog dialog = new ScoutCampDialog((JFrame) SwingUtilities.getWindowAncestor(this), campList);
        if (dialog.getBtnChoice() != ScoutCampDialog.BTN_OK) {
            return;
        }

        mdlCampList.removeAllElements();
        for (Camp camp : dialog.getSelectedCamps()) {
            mdlCampList.addElement(camp);
        }
    }

    private void btnRemoveCampMouseReleased() {
        if (listCamps.getSelectedIndex() == -1) {
            return;
        }

        mdlCampList.removeElementAt(listCamps.getSelectedIndex());
    }

    private void btnRemoveAwardMouseReleased() {
        if (tblSpecialAwards.getSelectedRow() < 0) {
            return;
        }

        tblModelSpecialAwards.removeRow(tblSpecialAwards.getSelectedRow());
    }

    private void btnAddAwardMouseReleased() {
        SpecialAwardDialog dialog = new SpecialAwardDialog((JFrame) SwingUtilities.getWindowAncestor(this), null);

        if (dialog.getBtnChoice() != SpecialAwardDialog.BTN_OK) {
            return;
        }

        SpecialAward specialAward = dialog.getSpecialAward();
        Object[] row = new Object[] {specialAward.getName(), specialAward.getDescription(), specialAward.getDateReceived()};
        tblModelSpecialAwards.addRow(row);
    }

    private void btnEditSpecialAwardMouseReleased() {
        int selectedRow = tblSpecialAwards.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        SpecialAward specialAward = new SpecialAward();
        specialAward.setName((String) tblModelSpecialAwards.getValueAt(selectedRow, 0));
        specialAward.setDescription((String) tblModelSpecialAwards.getValueAt(selectedRow, 1));
        specialAward.setDateReceived((Date) tblModelSpecialAwards.getValueAt(selectedRow, 2));

        SpecialAwardDialog dialog = new SpecialAwardDialog((JFrame) SwingUtilities.getWindowAncestor(this), specialAward);

        if (dialog.getBtnChoice() != SpecialAwardDialog.BTN_OK) {
            return;
        }

        SpecialAward updatedSpecialAward = dialog.getSpecialAward();
        tblModelSpecialAwards.setValueAt(updatedSpecialAward.getName(), selectedRow, 0);
        tblModelSpecialAwards.setValueAt(updatedSpecialAward.getDescription(), selectedRow, 1);
        tblModelSpecialAwards.setValueAt(updatedSpecialAward.getDateReceived(), selectedRow, 2);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        createUIComponents();

        TitlePanel pnlTitle = new TitlePanel();
        JPanel pnlContents = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel pnlSearch = new JPanel();
        txtSearchName = new JTextFieldDefaultText();
        JScrollPane scrollPane1 = new JScrollPane();
        listBoyScoutNames = new JList();
        JPanel panel3 = new JPanel();
        scrollPane2 = new JScrollPane();
        JPanel panel4 = new JPanel();
        tabPnlContent = new JTabbedPane();
        pnlSummary = new JPanel();
        pnlGeneral = new JPanel();
        lblName = new JLabel();
        txtName = new JTextFieldDefaultText();
        lblPosition = new JLabel();
        txtPosition = new JTextFieldDefaultText();
        lblNameError = new JLabel();
        lblRank = new JLabel();
        cboRank = new JComboBox();
        lblRankDate = new JLabel();
        cboRankDate = new JDatePicker();
        lblRankDateError = new JLabel();
        lblBirthDate = new JLabel();
        cboBirthDate = new JDatePicker();
        lblAge = new JLabel();
        lblAgeValue = new JLabel();
        pnlBadgeTotals = new JPanel();
        lblTotalBadges = new JLabel();
        lblTotalBadgesValue = new JLabel();
        lblTotalRequired = new JLabel();
        lblTotalRequiredValue = new JLabel();
        lblBirthDateError = new JLabel();
        lblTimeLeft = new JLabel();
        lblCampsAttended = new JLabel();
        barTimeLeft = new JProgressBar();
        barCampsAttended = new JProgressBar();
        lblTimeLeftDisplay = new JLabel();
        lblCampsAttendedDisplay = new JLabel();
        lblWaitPeriod = new JLabel();
        lblProgress = new JLabel();
        barWaitPeriod = new JProgressBar();
        barProgress = new JProgressBar();
        lblWaitPeriodDisplay = new JLabel();
        lblProgressDisplay = new JLabel();
        panel1 = new JPanel();
        lblProgressTable = new JLabel();
        btnEditAdvancementProgress = new JLabel();
        scrollPane3 = new JScrollPane();
        pnlContact = new JPanel();
        JLabel lblContactInfo = new JLabel();
        JLabel lblPhoneNumber = new JLabel();
        txtPhoneNumber = new JTextFieldDefaultText();
        lblPhoneNumberError = new JLabel();
        JLabel lblCity = new JLabel();
        txtCity = new JTextFieldDefaultText();
        JLabel lblState = new JLabel();
        txtState = new JTextFieldDefaultText();
        JLabel lblStreet = new JLabel();
        txtStreet = new JTextFieldDefaultText();
        JLabel lblZip = new JLabel();
        txtZip = new JTextFieldDefaultText();
        JLabel lblGuardianName = new JLabel();
        txtGuardianName = new JTextFieldDefaultText();
        JLabel lblGuardianPhone = new JLabel();
        txtGuardianPhone = new JTextFieldDefaultText();
        lblGuardianPhoneError = new JLabel();
        JLabel lblNotes = new JLabel();
        scrollPane4 = new JScrollPane();
        txtNotes = new JTextArea();
        pnlDetails = new JPanel();
        pnlDetailContents = new JPanel();
        JPanel panel8 = new JPanel();
        JLabel lblSpecialAwards = new JLabel();
        btnAddAward = new JLabel();
        btnEditSpecialAward = new JLabel();
        btnRemoveAward = new JLabel();
        scrollPane5 = new JScrollPane();
        JPanel panel9 = new JPanel();
        JLabel lblMeritBadges = new JLabel();
        btnAddMeritBadge = new JLabel();
        btnRemoveMeritBadge = new JLabel();
        lblRequirementError2 = new JLabel();
        JPanel panel10 = new JPanel();
        JLabel lblCamps = new JLabel();
        btnAddCamp = new JLabel();
        btnRemoveCamp = new JLabel();
        lblRequirementError3 = new JLabel();
        scrollPane6 = new JScrollPane();
        listMeritBadges = new JList(mdlMeritBadgeList);
        scrollPane7 = new JScrollPane();
        listCamps = new JList(mdlCampList);
        JPanel panel5 = new JPanel();
        JButton btnNew = new JButton();
        btnSave = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //---- pnlTitle ----
        pnlTitle.setTitle("Boy Scouts");
        pnlTitle.setImagePath("/images/fleurdelis48.jpg");
        pnlTitle.setName("pnlTitle");
        add(pnlTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== pnlContents ========
        {
            pnlContents.setOpaque(false);
            pnlContents.setName("pnlContents");
            pnlContents.setLayout(new GridBagLayout());
            ((GridBagLayout)pnlContents.getLayout()).columnWidths = new int[] {0, 0, 0};
            ((GridBagLayout)pnlContents.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)pnlContents.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
            ((GridBagLayout)pnlContents.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //======== panel2 ========
            {
                panel2.setOpaque(false);
                panel2.setName("panel2");
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                //======== pnlSearch ========
                {
                    pnlSearch.setBorder(LineBorder.createBlackLineBorder());
                    pnlSearch.setOpaque(false);
                    pnlSearch.setMaximumSize(new Dimension(190, 2147483647));
                    pnlSearch.setName("pnlSearch");
                    pnlSearch.setLayout(new GridBagLayout());
                    ((GridBagLayout)pnlSearch.getLayout()).columnWidths = new int[] {188, 0};
                    ((GridBagLayout)pnlSearch.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)pnlSearch.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                    ((GridBagLayout)pnlSearch.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                    //---- txtSearchName ----
                    txtSearchName.setDefaultText("Search by Name");
                    txtSearchName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    txtSearchName.setBorder(null);
                    txtSearchName.setPreferredSize(new Dimension(101, 20));
                    txtSearchName.setMinimumSize(new Dimension(0, 20));
                    txtSearchName.setName("txtSearchName");
                    txtSearchName.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtSearchNameKeyReleased();
                        }
                    });
                    pnlSearch.add(txtSearchName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 5, 0, 0), 0, 0));
                }
                panel2.add(pnlSearch, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 8, 0), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    scrollPane1.setName("scrollPane1");

                    //---- listBoyScoutNames ----
                    listBoyScoutNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    listBoyScoutNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listBoyScoutNames.setName("listBoyScoutNames");
                    listBoyScoutNames.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            listBoyScoutNamesKeyReleased(e);
                        }
                    });
                    listBoyScoutNames.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            listBoyScoutNamesMouseReleased();
                        }
                    });
                    scrollPane1.setViewportView(listBoyScoutNames);
                }
                panel2.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            pnlContents.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 15), 0, 0));

            //======== panel3 ========
            {
                panel3.setOpaque(false);
                panel3.setName("panel3");
                panel3.setLayout(new GridBagLayout());
                ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                //======== scrollPane2 ========
                {
                    scrollPane2.setName("scrollPane2");

                    //======== panel4 ========
                    {
                        panel4.setBackground(new Color(179, 148, 117));
                        panel4.setName("panel4");
                        panel4.setLayout(new BorderLayout());

                        //======== tabPnlContent ========
                        {
                            tabPnlContent.setBackground(Color.white);
                            tabPnlContent.setForeground(Color.black);
                            tabPnlContent.setFont(new Font("Tahoma", Font.PLAIN, 18));
                            tabPnlContent.setName("tabPnlContent");

                            //======== pnlSummary ========
                            {
                                pnlSummary.setBackground(Color.white);
                                pnlSummary.setBorder(null);
                                pnlSummary.setName("pnlSummary");
                                pnlSummary.setLayout(new GridBagLayout());
                                ((GridBagLayout)pnlSummary.getLayout()).columnWidths = new int[] {0, 0};
                                ((GridBagLayout)pnlSummary.getLayout()).rowHeights = new int[] {0, 0};
                                ((GridBagLayout)pnlSummary.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                                ((GridBagLayout)pnlSummary.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                                //======== pnlGeneral ========
                                {
                                    pnlGeneral.setOpaque(false);
                                    pnlGeneral.setName("pnlGeneral");
                                    pnlGeneral.setLayout(new GridBagLayout());
                                    ((GridBagLayout)pnlGeneral.getLayout()).columnWidths = new int[] {0, 207, 36, 30, 0, 195, 0};
                                    ((GridBagLayout)pnlGeneral.getLayout()).rowHeights = new int[] {35, 0, 35, 0, 35, 0, 24, 25, 0, 35, 25, 0, 35, 201, 0};
                                    ((GridBagLayout)pnlGeneral.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                                    ((GridBagLayout)pnlGeneral.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                                    //---- lblName ----
                                    lblName.setText("Name:");
                                    lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblName.setForeground(Color.black);
                                    lblName.setName("lblName");
                                    pnlGeneral.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- txtName ----
                                    txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    txtName.setDefaultText("Scout Name");
                                    txtName.setName("txtName");
                                    txtName.addKeyListener(new KeyAdapter() {
                                        @Override
                                        public void keyReleased(KeyEvent e) {
                                            validateName();
                                        }
                                    });
                                    txtName.addFocusListener(new FocusAdapter() {
                                        @Override
                                        public void focusLost(FocusEvent e) {
                                            validateName();
                                        }
                                    });
                                    pnlGeneral.add(txtName, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblPosition ----
                                    lblPosition.setText("Position:");
                                    lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblPosition.setForeground(Color.black);
                                    lblPosition.setName("lblPosition");
                                    pnlGeneral.add(lblPosition, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- txtPosition ----
                                    txtPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    txtPosition.setDefaultText("Troop Position");
                                    txtPosition.setName("txtPosition");
                                    pnlGeneral.add(txtPosition, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblNameError ----
                                    lblNameError.setText("*error message");
                                    lblNameError.setForeground(new Color(206, 17, 38));
                                    lblNameError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblNameError.setName("lblNameError");
                                    pnlGeneral.add(lblNameError, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 5), 0, 0));

                                    //---- lblRank ----
                                    lblRank.setText("Rank:");
                                    lblRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblRank.setForeground(Color.black);
                                    lblRank.setName("lblRank");
                                    pnlGeneral.add(lblRank, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboRank ----
                                    cboRank.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboRank.setBackground(Color.white);
                                    cboRank.setForeground(Color.black);
                                    cboRank.setName("cboRank");
                                    cboRank.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            cboRankActionPerformed();
                                        }
                                    });
                                    pnlGeneral.add(cboRank, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblRankDate ----
                                    lblRankDate.setText("Rank Date:");
                                    lblRankDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblRankDate.setForeground(Color.black);
                                    lblRankDate.setName("lblRankDate");
                                    pnlGeneral.add(lblRankDate, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboRankDate ----
                                    cboRankDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboRankDate.setMaximumSize(new Dimension(32822, 30));
                                    cboRankDate.setMinimumSize(new Dimension(57, 30));
                                    cboRankDate.setName("cboRankDate");
                                    cboRankDate.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            cboRankDateActionPerformed();
                                        }
                                    });
                                    pnlGeneral.add(cboRankDate, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblRankDateError ----
                                    lblRankDateError.setText("*error message");
                                    lblRankDateError.setForeground(new Color(206, 17, 38));
                                    lblRankDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblRankDateError.setName("lblRankDateError");
                                    pnlGeneral.add(lblRankDateError, new GridBagConstraints(4, 3, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 0), 0, 0));

                                    //---- lblBirthDate ----
                                    lblBirthDate.setText("Birth Date:");
                                    lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblBirthDate.setForeground(Color.black);
                                    lblBirthDate.setName("lblBirthDate");
                                    pnlGeneral.add(lblBirthDate, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- cboBirthDate ----
                                    cboBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    cboBirthDate.setMaximumSize(new Dimension(32822, 30));
                                    cboBirthDate.setMinimumSize(new Dimension(57, 30));
                                    cboBirthDate.setName("cboBirthDate");
                                    cboBirthDate.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            cboBirthDateActionPerformed();
                                        }
                                    });
                                    pnlGeneral.add(cboBirthDate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblAge ----
                                    lblAge.setText("Age:");
                                    lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblAge.setForeground(Color.black);
                                    lblAge.setName("lblAge");
                                    pnlGeneral.add(lblAge, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 10, 5, 5), 0, 0));

                                    //---- lblAgeValue ----
                                    lblAgeValue.setFont(new Font("Tahoma", Font.BOLD, 14));
                                    lblAgeValue.setForeground(new Color(0, 63, 135));
                                    lblAgeValue.setName("lblAgeValue");
                                    pnlGeneral.add(lblAgeValue, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //======== pnlBadgeTotals ========
                                    {
                                        pnlBadgeTotals.setOpaque(false);
                                        pnlBadgeTotals.setBorder(new LineBorder(new Color(0, 63, 135), 2, true));
                                        pnlBadgeTotals.setName("pnlBadgeTotals");
                                        pnlBadgeTotals.setLayout(new GridBagLayout());
                                        ((GridBagLayout)pnlBadgeTotals.getLayout()).columnWidths = new int[] {0, 35, 0, 27, 0};
                                        ((GridBagLayout)pnlBadgeTotals.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)pnlBadgeTotals.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0E-4};
                                        ((GridBagLayout)pnlBadgeTotals.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                                        //---- lblTotalBadges ----
                                        lblTotalBadges.setText("Total Badges:");
                                        lblTotalBadges.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                        lblTotalBadges.setForeground(Color.black);
                                        lblTotalBadges.setName("lblTotalBadges");
                                        pnlBadgeTotals.add(lblTotalBadges, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 2, 0, 5), 0, 0));

                                        //---- lblTotalBadgesValue ----
                                        lblTotalBadgesValue.setFont(new Font("Tahoma", Font.BOLD, 16));
                                        lblTotalBadgesValue.setForeground(new Color(0, 63, 135));
                                        lblTotalBadgesValue.setName("lblTotalBadgesValue");
                                        pnlBadgeTotals.add(lblTotalBadgesValue, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 5, 0, 5), 0, 0));

                                        //---- lblTotalRequired ----
                                        lblTotalRequired.setText("Total Required:");
                                        lblTotalRequired.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                        lblTotalRequired.setForeground(Color.black);
                                        lblTotalRequired.setName("lblTotalRequired");
                                        pnlBadgeTotals.add(lblTotalRequired, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.EAST, GridBagConstraints.NONE,
                                            new Insets(0, 15, 0, 5), 0, 0));

                                        //---- lblTotalRequiredValue ----
                                        lblTotalRequiredValue.setFont(new Font("Tahoma", Font.BOLD, 16));
                                        lblTotalRequiredValue.setForeground(new Color(0, 107, 63));
                                        lblTotalRequiredValue.setName("lblTotalRequiredValue");
                                        pnlBadgeTotals.add(lblTotalRequiredValue, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 5, 0, 2), 0, 0));
                                    }
                                    pnlGeneral.add(pnlBadgeTotals, new GridBagConstraints(4, 4, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblBirthDateError ----
                                    lblBirthDateError.setText("*error message");
                                    lblBirthDateError.setForeground(new Color(206, 17, 38));
                                    lblBirthDateError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                    lblBirthDateError.setName("lblBirthDateError");
                                    pnlGeneral.add(lblBirthDateError, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 20, 5, 5), 0, 0));

                                    //---- lblTimeLeft ----
                                    lblTimeLeft.setText("Time Left in Boy Scouts");
                                    lblTimeLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblTimeLeft.setForeground(Color.black);
                                    lblTimeLeft.setName("lblTimeLeft");
                                    pnlGeneral.add(lblTimeLeft, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblCampsAttended ----
                                    lblCampsAttended.setText("Number of Camps Attended");
                                    lblCampsAttended.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblCampsAttended.setForeground(Color.black);
                                    lblCampsAttended.setName("lblCampsAttended");
                                    pnlGeneral.add(lblCampsAttended, new GridBagConstraints(4, 6, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- barTimeLeft ----
                                    barTimeLeft.setValue(50);
                                    barTimeLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    barTimeLeft.setMaximum(1095);
                                    barTimeLeft.setName("barTimeLeft");
                                    pnlGeneral.add(barTimeLeft, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- barCampsAttended ----
                                    barCampsAttended.setValue(11);
                                    barCampsAttended.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    barCampsAttended.setMaximum(20);
                                    barCampsAttended.setName("barCampsAttended");
                                    pnlGeneral.add(barCampsAttended, new GridBagConstraints(4, 7, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblTimeLeftDisplay ----
                                    lblTimeLeftDisplay.setText("1 yr, 6 mon, 2 days");
                                    lblTimeLeftDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblTimeLeftDisplay.setForeground(Color.black);
                                    lblTimeLeftDisplay.setName("lblTimeLeftDisplay");
                                    pnlGeneral.add(lblTimeLeftDisplay, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblCampsAttendedDisplay ----
                                    lblCampsAttendedDisplay.setText("11 of 21");
                                    lblCampsAttendedDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblCampsAttendedDisplay.setForeground(Color.black);
                                    lblCampsAttendedDisplay.setName("lblCampsAttendedDisplay");
                                    pnlGeneral.add(lblCampsAttendedDisplay, new GridBagConstraints(4, 8, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblWaitPeriod ----
                                    lblWaitPeriod.setText("Time Requirement");
                                    lblWaitPeriod.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblWaitPeriod.setForeground(Color.black);
                                    lblWaitPeriod.setName("lblWaitPeriod");
                                    pnlGeneral.add(lblWaitPeriod, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblProgress ----
                                    lblProgress.setText("Progress Towards Next Advancement");
                                    lblProgress.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    lblProgress.setForeground(Color.black);
                                    lblProgress.setName("lblProgress");
                                    pnlGeneral.add(lblProgress, new GridBagConstraints(4, 9, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- barWaitPeriod ----
                                    barWaitPeriod.setValue(50);
                                    barWaitPeriod.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    barWaitPeriod.setName("barWaitPeriod");
                                    pnlGeneral.add(barWaitPeriod, new GridBagConstraints(0, 10, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- barProgress ----
                                    barProgress.setValue(60);
                                    barProgress.setName("barProgress");
                                    pnlGeneral.add(barProgress, new GridBagConstraints(4, 10, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //---- lblWaitPeriodDisplay ----
                                    lblWaitPeriodDisplay.setText("2 mo, 3 days");
                                    lblWaitPeriodDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblWaitPeriodDisplay.setForeground(Color.black);
                                    lblWaitPeriodDisplay.setName("lblWaitPeriodDisplay");
                                    pnlGeneral.add(lblWaitPeriodDisplay, new GridBagConstraints(0, 11, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.NORTH, GridBagConstraints.NONE,
                                        new Insets(0, 0, 5, 5), 0, 0));

                                    //---- lblProgressDisplay ----
                                    lblProgressDisplay.setText("6 of 10 Requirements");
                                    lblProgressDisplay.setForeground(Color.black);
                                    lblProgressDisplay.setFont(new Font("Tahoma", Font.PLAIN, 12));
                                    lblProgressDisplay.setName("lblProgressDisplay");
                                    pnlGeneral.add(lblProgressDisplay, new GridBagConstraints(4, 11, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== panel1 ========
                                    {
                                        panel1.setOpaque(false);
                                        panel1.setName("panel1");
                                        panel1.setLayout(new GridBagLayout());
                                        ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {198, 0, 0};
                                        ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                                        ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                                        //---- lblProgressTable ----
                                        lblProgressTable.setText("Progress of Current Advancement");
                                        lblProgressTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                        lblProgressTable.setForeground(Color.black);
                                        lblProgressTable.setName("lblProgressTable");
                                        panel1.add(lblProgressTable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnEditAdvancementProgress ----
                                        btnEditAdvancementProgress.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
                                        btnEditAdvancementProgress.setVerticalAlignment(SwingConstants.BOTTOM);
                                        btnEditAdvancementProgress.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnEditAdvancementProgress.setPreferredSize(new Dimension(20, 20));
                                        btnEditAdvancementProgress.setToolTipText("Edit selected advancement");
                                        btnEditAdvancementProgress.setName("btnEditAdvancementProgress");
                                        btnEditAdvancementProgress.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnEditAdvancementProgressMouseReleased();
                                            }
                                        });
                                        panel1.add(btnEditAdvancementProgress, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 0), 0, 0));
                                    }
                                    pnlGeneral.add(panel1, new GridBagConstraints(0, 12, 6, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== scrollPane3 ========
                                    {
                                        scrollPane3.setName("scrollPane3");

                                        //---- tblProgress ----
                                        tblProgress.setPreferredScrollableViewportSize(new Dimension(450, 150));
                                        tblProgress.setMaximumSize(new Dimension(2147483647, 200));
                                        tblProgress.setBackground(Color.white);
                                        tblProgress.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                        tblProgress.setForeground(Color.black);
                                        tblProgress.setFillsViewportHeight(true);
                                        tblProgress.setName("tblProgress");
                                        scrollPane3.setViewportView(tblProgress);
                                    }
                                    pnlGeneral.add(scrollPane3, new GridBagConstraints(0, 13, 6, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 10, 0), 0, 0));
                                }
                                pnlSummary.add(pnlGeneral, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(15, 10, 0, 10), 0, 0));
                            }
                            tabPnlContent.addTab("Summary", pnlSummary);

                            //======== pnlContact ========
                            {
                                pnlContact.setBackground(Color.white);
                                pnlContact.setName("pnlContact");
                                pnlContact.setLayout(new GridBagLayout());
                                ((GridBagLayout)pnlContact.getLayout()).columnWidths = new int[] {0, 188, 83, 183, 0};
                                ((GridBagLayout)pnlContact.getLayout()).rowHeights = new int[] {0, 35, 0, 35, 35, 35, 0, 0, 313, 0};
                                ((GridBagLayout)pnlContact.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                                ((GridBagLayout)pnlContact.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                                //---- lblContactInfo ----
                                lblContactInfo.setText("Contact Information:");
                                lblContactInfo.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                lblContactInfo.setForeground(new Color(0, 63, 135));
                                lblContactInfo.setName("lblContactInfo");
                                pnlContact.add(lblContactInfo, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 8, 5, 5), 0, 0));

                                //---- lblPhoneNumber ----
                                lblPhoneNumber.setText("Phone Number:");
                                lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblPhoneNumber.setForeground(Color.black);
                                lblPhoneNumber.setName("lblPhoneNumber");
                                pnlContact.add(lblPhoneNumber, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtPhoneNumber ----
                                txtPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtPhoneNumber.setDefaultText("(123) 456-7890");
                                txtPhoneNumber.setName("txtPhoneNumber");
                                txtPhoneNumber.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyReleased(KeyEvent e) {
                                        validatePhoneNumber();
                                    }
                                });
                                txtPhoneNumber.addFocusListener(new FocusAdapter() {
                                    @Override
                                    public void focusLost(FocusEvent e) {
                                        validatePhoneNumber();
                                    }
                                });
                                pnlContact.add(txtPhoneNumber, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblPhoneNumberError ----
                                lblPhoneNumberError.setText("*error message");
                                lblPhoneNumberError.setForeground(new Color(206, 17, 38));
                                lblPhoneNumberError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                lblPhoneNumberError.setName("lblPhoneNumberError");
                                pnlContact.add(lblPhoneNumberError, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 20, 5, 5), 0, 0));

                                //---- lblCity ----
                                lblCity.setText("City:");
                                lblCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblCity.setForeground(Color.black);
                                lblCity.setName("lblCity");
                                pnlContact.add(lblCity, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtCity ----
                                txtCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtCity.setDefaultText("Eagle Mtn");
                                txtCity.setName("txtCity");
                                pnlContact.add(txtCity, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblState ----
                                lblState.setText("State:");
                                lblState.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblState.setForeground(Color.black);
                                lblState.setName("lblState");
                                pnlContact.add(lblState, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtState ----
                                txtState.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtState.setDefaultText("Utah");
                                txtState.setName("txtState");
                                pnlContact.add(txtState, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- lblStreet ----
                                lblStreet.setText("Street:");
                                lblStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblStreet.setForeground(Color.black);
                                lblStreet.setName("lblStreet");
                                pnlContact.add(lblStreet, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtStreet ----
                                txtStreet.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtStreet.setDefaultText("100 South 100 West");
                                txtStreet.setName("txtStreet");
                                pnlContact.add(txtStreet, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblZip ----
                                lblZip.setText("Zip:");
                                lblZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblZip.setForeground(Color.black);
                                lblZip.setName("lblZip");
                                pnlContact.add(lblZip, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtZip ----
                                txtZip.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtZip.setDefaultText("12345");
                                txtZip.setName("txtZip");
                                pnlContact.add(txtZip, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- lblGuardianName ----
                                lblGuardianName.setText("Parent Name:");
                                lblGuardianName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblGuardianName.setForeground(Color.black);
                                lblGuardianName.setName("lblGuardianName");
                                pnlContact.add(lblGuardianName, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtGuardianName ----
                                txtGuardianName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtGuardianName.setDefaultText("Parent Name");
                                txtGuardianName.setName("txtGuardianName");
                                pnlContact.add(txtGuardianName, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- lblGuardianPhone ----
                                lblGuardianPhone.setText("Parent Phone Number:");
                                lblGuardianPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                lblGuardianPhone.setForeground(Color.black);
                                lblGuardianPhone.setName("lblGuardianPhone");
                                pnlContact.add(lblGuardianPhone, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                                    new Insets(0, 15, 5, 5), 0, 0));

                                //---- txtGuardianPhone ----
                                txtGuardianPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                txtGuardianPhone.setDefaultText("(123) 456-7890");
                                txtGuardianPhone.setName("txtGuardianPhone");
                                txtGuardianPhone.addFocusListener(new FocusAdapter() {
                                    @Override
                                    public void focusLost(FocusEvent e) {
                                        validateGuardianPhoneNumber();
                                    }
                                });
                                txtGuardianPhone.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyReleased(KeyEvent e) {
                                        validateGuardianPhoneNumber();
                                    }
                                });
                                pnlContact.add(txtGuardianPhone, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- lblGuardianPhoneError ----
                                lblGuardianPhoneError.setText("*error message");
                                lblGuardianPhoneError.setForeground(new Color(206, 17, 38));
                                lblGuardianPhoneError.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                lblGuardianPhoneError.setName("lblGuardianPhoneError");
                                pnlContact.add(lblGuardianPhoneError, new GridBagConstraints(2, 6, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 20, 5, 0), 0, 0));

                                //---- lblNotes ----
                                lblNotes.setText("Notes:");
                                lblNotes.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                lblNotes.setForeground(new Color(0, 63, 135));
                                lblNotes.setName("lblNotes");
                                pnlContact.add(lblNotes, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 8, 5, 5), 0, 0));

                                //======== scrollPane4 ========
                                {
                                    scrollPane4.setName("scrollPane4");

                                    //---- txtNotes ----
                                    txtNotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    txtNotes.setBackground(Color.white);
                                    txtNotes.setForeground(Color.black);
                                    txtNotes.setName("txtNotes");
                                    scrollPane4.setViewportView(txtNotes);
                                }
                                pnlContact.add(scrollPane4, new GridBagConstraints(0, 8, 4, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 15, 10, 0), 0, 0));
                            }
                            tabPnlContent.addTab("Contact Info", pnlContact);

                            //======== pnlDetails ========
                            {
                                pnlDetails.setBackground(Color.white);
                                pnlDetails.setName("pnlDetails");
                                pnlDetails.setLayout(new GridBagLayout());
                                ((GridBagLayout)pnlDetails.getLayout()).columnWidths = new int[] {0, 0};
                                ((GridBagLayout)pnlDetails.getLayout()).rowHeights = new int[] {560, 0};
                                ((GridBagLayout)pnlDetails.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                                ((GridBagLayout)pnlDetails.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                                //======== pnlDetailContents ========
                                {
                                    pnlDetailContents.setOpaque(false);
                                    pnlDetailContents.setName("pnlDetailContents");
                                    pnlDetailContents.setLayout(new GridBagLayout());
                                    ((GridBagLayout)pnlDetailContents.getLayout()).columnWidths = new int[] {330, 300, 0};
                                    ((GridBagLayout)pnlDetailContents.getLayout()).rowHeights = new int[] {0, 0, 64, 314, 0};
                                    ((GridBagLayout)pnlDetailContents.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                                    ((GridBagLayout)pnlDetailContents.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                                    //======== panel8 ========
                                    {
                                        panel8.setOpaque(false);
                                        panel8.setName("panel8");
                                        panel8.setLayout(new GridBagLayout());
                                        ((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
                                        ((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                        ((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                        //---- lblSpecialAwards ----
                                        lblSpecialAwards.setText("Special Awards");
                                        lblSpecialAwards.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                        lblSpecialAwards.setForeground(new Color(0, 63, 135));
                                        lblSpecialAwards.setName("lblSpecialAwards");
                                        panel8.add(lblSpecialAwards, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(3, 0, 0, 5), 0, 0));

                                        //---- btnAddAward ----
                                        btnAddAward.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                        btnAddAward.setToolTipText("Add a new award");
                                        btnAddAward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnAddAward.setName("btnAddAward");
                                        btnAddAward.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnAddAwardMouseReleased();
                                            }
                                        });
                                        panel8.add(btnAddAward, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnEditSpecialAward ----
                                        btnEditSpecialAward.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
                                        btnEditSpecialAward.setVerticalAlignment(SwingConstants.BOTTOM);
                                        btnEditSpecialAward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnEditSpecialAward.setPreferredSize(new Dimension(20, 20));
                                        btnEditSpecialAward.setToolTipText("Edit selected advancement");
                                        btnEditSpecialAward.setName("btnEditSpecialAward");
                                        btnEditSpecialAward.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnEditSpecialAwardMouseReleased();
                                            }
                                        });
                                        panel8.add(btnEditSpecialAward, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnRemoveAward ----
                                        btnRemoveAward.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                        btnRemoveAward.setToolTipText("Remove selected award");
                                        btnRemoveAward.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnRemoveAward.setName("btnRemoveAward");
                                        btnRemoveAward.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnRemoveAwardMouseReleased();
                                            }
                                        });
                                        panel8.add(btnRemoveAward, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));
                                    }
                                    pnlDetailContents.add(panel8, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== scrollPane5 ========
                                    {
                                        scrollPane5.setName("scrollPane5");

                                        //---- tblSpecialAwards ----
                                        tblSpecialAwards.setPreferredScrollableViewportSize(new Dimension(450, 100));
                                        tblSpecialAwards.setBackground(Color.white);
                                        tblSpecialAwards.setName("tblSpecialAwards");
                                        scrollPane5.setViewportView(tblSpecialAwards);
                                    }
                                    pnlDetailContents.add(scrollPane5, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== panel9 ========
                                    {
                                        panel9.setOpaque(false);
                                        panel9.setName("panel9");
                                        panel9.setLayout(new GridBagLayout());
                                        ((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                                        ((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                        ((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                        //---- lblMeritBadges ----
                                        lblMeritBadges.setText("Merit Badges");
                                        lblMeritBadges.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                        lblMeritBadges.setForeground(new Color(0, 63, 135));
                                        lblMeritBadges.setName("lblMeritBadges");
                                        panel9.add(lblMeritBadges, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(3, 0, 0, 5), 0, 0));

                                        //---- btnAddMeritBadge ----
                                        btnAddMeritBadge.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                        btnAddMeritBadge.setToolTipText("Add a merit badge");
                                        btnAddMeritBadge.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnAddMeritBadge.setName("btnAddMeritBadge");
                                        btnAddMeritBadge.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnAddMeritBadgeMouseReleased();
                                            }
                                        });
                                        panel9.add(btnAddMeritBadge, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnRemoveMeritBadge ----
                                        btnRemoveMeritBadge.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                        btnRemoveMeritBadge.setToolTipText("Remove selected merit badge");
                                        btnRemoveMeritBadge.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnRemoveMeritBadge.setName("btnRemoveMeritBadge");
                                        btnRemoveMeritBadge.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnRemoveMeritBadgeMouseReleased();
                                            }
                                        });
                                        panel9.add(btnRemoveMeritBadge, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- lblRequirementError2 ----
                                        lblRequirementError2.setText("* Error Message");
                                        lblRequirementError2.setForeground(Color.red);
                                        lblRequirementError2.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                        lblRequirementError2.setVisible(false);
                                        lblRequirementError2.setName("lblRequirementError2");
                                        panel9.add(lblRequirementError2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 10, 0, 0), 0, 0));
                                    }
                                    pnlDetailContents.add(panel9, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 30), 0, 0));

                                    //======== panel10 ========
                                    {
                                        panel10.setOpaque(false);
                                        panel10.setName("panel10");
                                        panel10.setLayout(new GridBagLayout());
                                        ((GridBagLayout)panel10.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                                        ((GridBagLayout)panel10.getLayout()).rowHeights = new int[] {0, 0};
                                        ((GridBagLayout)panel10.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
                                        ((GridBagLayout)panel10.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                                        //---- lblCamps ----
                                        lblCamps.setText("Camps");
                                        lblCamps.setFont(new Font("Vijaya", Font.PLAIN, 22));
                                        lblCamps.setForeground(new Color(0, 63, 135));
                                        lblCamps.setName("lblCamps");
                                        panel10.add(lblCamps, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(3, 0, 0, 5), 0, 0));

                                        //---- btnAddCamp ----
                                        btnAddCamp.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
                                        btnAddCamp.setToolTipText("Add a camp");
                                        btnAddCamp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnAddCamp.setName("btnAddCamp");
                                        btnAddCamp.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnAddCampMouseReleased();
                                            }
                                        });
                                        panel10.add(btnAddCamp, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- btnRemoveCamp ----
                                        btnRemoveCamp.setIcon(new ImageIcon(getClass().getResource("/images/delete.png")));
                                        btnRemoveCamp.setToolTipText("Remove selected camp");
                                        btnRemoveCamp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        btnRemoveCamp.setName("btnRemoveCamp");
                                        btnRemoveCamp.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                btnRemoveCampMouseReleased();
                                            }
                                        });
                                        panel10.add(btnRemoveCamp, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 0, 0, 5), 0, 0));

                                        //---- lblRequirementError3 ----
                                        lblRequirementError3.setText("* Error Message");
                                        lblRequirementError3.setForeground(Color.red);
                                        lblRequirementError3.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                        lblRequirementError3.setVisible(false);
                                        lblRequirementError3.setName("lblRequirementError3");
                                        panel10.add(lblRequirementError3, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                            new Insets(0, 10, 0, 0), 0, 0));
                                    }
                                    pnlDetailContents.add(panel10, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                                        new Insets(0, 0, 5, 0), 0, 0));

                                    //======== scrollPane6 ========
                                    {
                                        scrollPane6.setName("scrollPane6");

                                        //---- listMeritBadges ----
                                        listMeritBadges.setName("listMeritBadges");
                                        scrollPane6.setViewportView(listMeritBadges);
                                    }
                                    pnlDetailContents.add(scrollPane6, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 5, 0, 30), 0, 0));

                                    //======== scrollPane7 ========
                                    {
                                        scrollPane7.setName("scrollPane7");

                                        //---- listCamps ----
                                        listCamps.setName("listCamps");
                                        scrollPane7.setViewportView(listCamps);
                                    }
                                    pnlDetailContents.add(scrollPane7, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                        new Insets(0, 5, 0, 0), 0, 0));
                                }
                                pnlDetails.add(pnlDetailContents, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(10, 10, 10, 10), 0, 0));
                            }
                            tabPnlContent.addTab("Details", pnlDetails);
                        }
                        panel4.add(tabPnlContent, BorderLayout.NORTH);
                    }
                    scrollPane2.setViewportView(panel4);
                }
                panel3.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 15), 0, 0));

                //======== panel5 ========
                {
                    panel5.setOpaque(false);
                    panel5.setName("panel5");
                    panel5.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {89, 0};
                    ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                    ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                    ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- btnNew ----
                    btnNew.setText("New");
                    btnNew.setBackground(new Color(51, 156, 229));
                    btnNew.setFocusPainted(false);
                    btnNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnNew.setForeground(Color.white);
                    btnNew.setPreferredSize(new Dimension(56, 40));
                    btnNew.setName("btnNew");
                    btnNew.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnNewActionPerformed();
                        }
                    });
                    panel5.add(btnNew, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 10, 0), 0, 0));

                    //---- btnSave ----
                    btnSave.setText("Save");
                    btnSave.setBackground(new Color(0, 63, 135));
                    btnSave.setFocusPainted(false);
                    btnSave.setForeground(Color.white);
                    btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnSave.setPreferredSize(new Dimension(60, 40));
                    btnSave.setName("btnSave");
                    btnSave.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnSaveActionPerformed();
                        }
                    });
                    panel5.add(btnSave, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 10, 0), 0, 0));

                    //---- btnUpdate ----
                    btnUpdate.setText("Update");
                    btnUpdate.setBackground(new Color(0, 63, 135));
                    btnUpdate.setForeground(Color.white);
                    btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnUpdate.setFocusPainted(false);
                    btnUpdate.setPreferredSize(new Dimension(74, 40));
                    btnUpdate.setName("btnUpdate");
                    btnUpdate.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnUpdateActionPerformed();
                        }
                    });
                    panel5.add(btnUpdate, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 10, 0), 0, 0));

                    //---- btnDelete ----
                    btnDelete.setText("Delete");
                    btnDelete.setBackground(new Color(206, 17, 38));
                    btnDelete.setForeground(Color.white);
                    btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    btnDelete.setFocusPainted(false);
                    btnDelete.setPreferredSize(new Dimension(68, 40));
                    btnDelete.setName("btnDelete");
                    btnDelete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnDeleteActionPerformed();
                        }
                    });
                    panel5.add(btnDelete, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                panel3.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            pnlContents.add(panel3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(pnlContents, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 10, 10, 10), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextFieldDefaultText txtSearchName;
    private JList listBoyScoutNames;
    private JScrollPane scrollPane2;
    private JTabbedPane tabPnlContent;
    private JPanel pnlSummary;
    private JPanel pnlGeneral;
    private JLabel lblName;
    private JTextFieldDefaultText txtName;
    private JLabel lblPosition;
    private JTextFieldDefaultText txtPosition;
    private JLabel lblNameError;
    private JLabel lblRank;
    private JComboBox cboRank;
    private JLabel lblRankDate;
    private JDatePicker cboRankDate;
    private JLabel lblRankDateError;
    private JLabel lblBirthDate;
    private JDatePicker cboBirthDate;
    private JLabel lblAge;
    private JLabel lblAgeValue;
    private JPanel pnlBadgeTotals;
    private JLabel lblTotalBadges;
    private JLabel lblTotalBadgesValue;
    private JLabel lblTotalRequired;
    private JLabel lblTotalRequiredValue;
    private JLabel lblBirthDateError;
    private JLabel lblTimeLeft;
    private JLabel lblCampsAttended;
    private JProgressBar barTimeLeft;
    private JProgressBar barCampsAttended;
    private JLabel lblTimeLeftDisplay;
    private JLabel lblCampsAttendedDisplay;
    private JLabel lblWaitPeriod;
    private JLabel lblProgress;
    private JProgressBar barWaitPeriod;
    private JProgressBar barProgress;
    private JLabel lblWaitPeriodDisplay;
    private JLabel lblProgressDisplay;
    private JPanel panel1;
    private JLabel lblProgressTable;
    private JLabel btnEditAdvancementProgress;
    private JScrollPane scrollPane3;
    private JTable tblProgress;
    private JPanel pnlContact;
    private JTextFieldDefaultText txtPhoneNumber;
    private JLabel lblPhoneNumberError;
    private JTextFieldDefaultText txtCity;
    private JTextFieldDefaultText txtState;
    private JTextFieldDefaultText txtStreet;
    private JTextFieldDefaultText txtZip;
    private JTextFieldDefaultText txtGuardianName;
    private JTextFieldDefaultText txtGuardianPhone;
    private JLabel lblGuardianPhoneError;
    private JScrollPane scrollPane4;
    private JTextArea txtNotes;
    private JPanel pnlDetails;
    private JPanel pnlDetailContents;
    private JLabel btnAddAward;
    private JLabel btnEditSpecialAward;
    private JLabel btnRemoveAward;
    private JScrollPane scrollPane5;
    private JTable tblSpecialAwards;
    private JLabel btnAddMeritBadge;
    private JLabel btnRemoveMeritBadge;
    private JLabel lblRequirementError2;
    private JLabel btnAddCamp;
    private JLabel btnRemoveCamp;
    private JLabel lblRequirementError3;
    private JScrollPane scrollPane6;
    private JList listMeritBadges;
    private JScrollPane scrollPane7;
    private JList listCamps;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
