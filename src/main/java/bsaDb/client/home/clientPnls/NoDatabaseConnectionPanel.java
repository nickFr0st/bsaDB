/*
 * Created by JFormDesigner on Thu May 07 20:23:08 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author User #2
 */
public class NoDatabaseConnectionPanel extends JPanel {
    public NoDatabaseConnectionPanel() {
        initComponents();
    }

    private void button1ActionPerformed() {
        if (!Desktop.isDesktopSupported()) {
            return;
        }

        try {
            URI uri = new URI("https://dev.mysql.com/downloads/mysql/");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException ignore) {
        } catch (IOException ignore){
        }
    }

    private void button1MouseEntered() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void button1MouseExited() {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JLabel label3 = new JLabel();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JTextArea textArea3 = new JTextArea();
        JButton button1 = new JButton();
        JLabel label4 = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setName("this");
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {32, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

        //======== panel1 ========
        {
            panel1.setOpaque(false);
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {76, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- label3 ----
            label3.setIcon(new ImageIcon(getClass().getResource("/images/flurDeLis90.png")));
            label3.setName("label3");
            panel1.add(label3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- label1 ----
            label1.setText("You do not have an active connection to a MySQL database.");
            label1.setFont(new Font("Tahoma", Font.BOLD, 20));
            label1.setForeground(Color.red);
            label1.setName("label1");
            panel1.add(label1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(10, 10, 10, 15), 0, 0));

            //---- label2 ----
            label2.setText("In order to create an active database connection you must first setup a MySQL server.");
            label2.setFont(new Font("Tahoma", Font.BOLD, 14));
            label2.setForeground(Color.black);
            label2.setName("label2");
            panel1.add(label2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- textArea3 ----
            textArea3.setText("- To setup a MySQL server click on the following link, install the MySQL server.");
            textArea3.setWrapStyleWord(true);
            textArea3.setLineWrap(true);
            textArea3.setEditable(false);
            textArea3.setOpaque(false);
            textArea3.setForeground(Color.black);
            textArea3.setFont(new Font("Tahoma", Font.PLAIN, 13));
            textArea3.setName("textArea3");
            panel1.add(textArea3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 5, 15), 0, 0));

            //---- button1 ----
            button1.setText("https://dev.mysql.com/downloads/mysql/");
            button1.setBorderPainted(false);
            button1.setOpaque(false);
            button1.setBackground(Color.white);
            button1.setForeground(new Color(51, 102, 153));
            button1.setFocusPainted(false);
            button1.setName("button1");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1ActionPerformed();
                }
            });
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button1MouseEntered();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    button1MouseExited();
                }
            });
            panel1.add(button1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 10, 5, 5), 0, 0));

            //---- label4 ----
            label4.setText("<HTML>- Once your MySQL server is active go to <B>Setup -> Database Settings</B><BR>&nbsp;&nbsp;&nbsp;to create and configure your BSA Database database.</HTML>");
            label4.setFont(new Font("Tahoma", Font.PLAIN, 13));
            label4.setForeground(Color.black);
            label4.setName("label4");
            panel1.add(label4, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 0, 15), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
