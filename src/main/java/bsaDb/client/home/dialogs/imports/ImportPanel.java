/*
 * Created by JFormDesigner on Tue May 26 21:21:52 MDT 2015
 */

package bsaDb.client.home.dialogs.imports;

import constants.IETypeConst;

import javax.swing.*;
import java.awt.*;

/**
 * @author User #2
 */
public class ImportPanel extends JPanel {
    IETypeConst typeConst;

    public ImportPanel() {
        initComponents();
    }

    public ImportPanel (IETypeConst typeConst) {
        this();

        this.typeConst = typeConst;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public String getImportPath() {
        return "";
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
