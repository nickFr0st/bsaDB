package util;

import bsaDb.client.customComponents.JPasswordFieldDefaultText;
import bsaDb.client.customComponents.JTextFieldDefaultText;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Nathanael on 5/4/2015
 */
public class Util {

    public static final String DATE_PATTERN = "(\\d{2})/(\\d{2})/(\\d{4})";
    public static final SimpleDateFormat DATA_BASE_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Set set) {
        return set == null || set.isEmpty();
    }

    public static boolean isEmpty(JTextFieldDefaultText txtField) {
        return txtField == null || txtField.isMessageDefault() || txtField.getText().isEmpty();
    }

    public static boolean isEmpty(JPasswordFieldDefaultText txtField) {
        return txtField == null || txtField.isMessageDefault() || txtField.getText().isEmpty();
    }

    public static void setError(JLabel lblNewUserError, String errorMessage) {
        lblNewUserError.setText("* " + errorMessage);
        lblNewUserError.setVisible(true);
    }

    public static void clearError(JLabel lblError) {
        lblError.setText("");
        lblError.setVisible(false);
    }

    public static void saveProperties(Properties properties, String propertyFileName) {
        try {
            File f = new File(propertyFileName.substring(propertyFileName.indexOf("/") + 1));
            OutputStream out = new FileOutputStream(f);
            properties.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        //validate phone numbers of format "1234567890"
        if (phoneNumber.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNumber.matches("\\(\\d{3}\\)[-\\s]\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;
    }

    public static boolean validateEmail(String email) {
        String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(pattern);
    }

    public static int getIntValue(boolean value) {
        return value ? 1 : 0;
    }

    public static String csvWrap(String str) {
        if (isEmpty(str)) {
            return "";
        }

        StringBuilder sb = new StringBuilder(str);
        int id = 0;
        while (sb.indexOf("\"", id) >= 0) {
            id = sb.indexOf("\"", id);
            sb.insert(id, "\"");
            id = id + 2;
        }

        return "\"" + sb.toString() + "\"";
    }

    public static Frame getParent(Component panel) {
        return (JFrame) SwingUtilities.getWindowAncestor(panel);
    }

    public static Frame getParent(JDialog dialog) {
        return (JFrame) SwingUtilities.getWindowAncestor(dialog);
    }

    public static Object[] getSortedList(List<String> nameList) {
        if (Util.isEmpty(nameList)) {
            return new Object[0];
        }

        NameComparator comparator = new NameComparator();
        TreeSet<String> sortedNames = new TreeSet<>(comparator);
        sortedNames.addAll(nameList);
        return sortedNames.toArray();
    }
}
