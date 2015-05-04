package util;

import bsaDb.client.customComponents.JPasswordFieldDefaultText;
import bsaDb.client.customComponents.JTextFieldDefaultText;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Nathanael on 5/4/2015
 */
public class Util {

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
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

    public static void saveProperties(Properties properties, String propertyFileName) {
        try {
            File f = new File(propertyFileName.substring(propertyFileName.indexOf("/") + 1));
            OutputStream out = new FileOutputStream(f);
            properties.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
