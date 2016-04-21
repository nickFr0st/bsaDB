/*
 * Created by JFormDesigner on Thu May 07 20:23:08 MDT 2015
 */

package bsaDb.client.home.clientPnls;

import bsaDb.client.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author User #2
 */
public class NoServerConnectionPanel extends JPanel {
    BaseFrame baseFrame;

    public NoServerConnectionPanel() {
        initComponents();
    }

    public NoServerConnectionPanel(BaseFrame baseFrame) {
        this.baseFrame = baseFrame;

        initComponents();
    }

    private void button1ActionPerformed() {
        if (!Desktop.isDesktopSupported()) {
            return;
        }

        try {
            URI uri = new URI("https://dev.mysql.com/downloads/mysql/");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException ignore) {
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        label3 = new JLabel();
        panel2 = new JPanel();
        label6 = new JLabel();
        panel3 = new JPanel();
        JLabel label5 = new JLabel();
        JTextArea textArea4 = new JTextArea();
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
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {55, 741, 50, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {40, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- label3 ----
            label3.setIcon(new ImageIcon(getClass().getResource("/images/signInTitlebar.png")));
            label3.setName("label3");
            panel1.add(label3, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== panel2 ========
            {
                panel2.setOpaque(false);
                panel2.setName("panel2");
                panel2.setLayout(new BorderLayout());

                //---- label6 ----
                label6.setIcon(new ImageIcon(getClass().getResource("/images/no_database.png")));
                label6.setName("label6");
                panel2.add(label6, BorderLayout.WEST);

                //======== panel3 ========
                {
                    panel3.setOpaque(false);
                    panel3.setName("panel3");
                    panel3.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
                    ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
                    ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                    ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- label5 ----
                    label5.setText("BSA Database requires an active connection to MySql Server");
                    label5.setFont(new Font("Tahoma", Font.BOLD, 16));
                    label5.setForeground(Color.black);
                    label5.setName("label5");
                    panel3.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(20, 0, 5, 5), 0, 0));

                    //---- textArea4 ----
                    textArea4.setText("- If you have already installed and setup MySql Server please make \n  sure your server is started then restart BSA Database\n\n- If you haven't installed MySql Server yet please follow the \n  instructions on the link below to get the server setup");
                    textArea4.setWrapStyleWord(true);
                    textArea4.setLineWrap(true);
                    textArea4.setEditable(false);
                    textArea4.setOpaque(false);
                    textArea4.setForeground(Color.black);
                    textArea4.setFont(new Font("Tahoma", Font.PLAIN, 13));
                    textArea4.setName("textArea4");
                    panel3.add(textArea4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 10, 5, 15), 0, 0));

                    //---- button1 ----
                    button1.setText("MySql Server");
                    button1.setBackground(new Color(0, 63, 135));
                    button1.setForeground(Color.white);
                    button1.setFocusPainted(false);
                    button1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    button1.setToolTipText("https://dev.mysql.com/downloads/mysql/");
                    button1.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    button1.setPreferredSize(new Dimension(200, 36));
                    button1.setMinimumSize(new Dimension(200, 36));
                    button1.setMaximumSize(new Dimension(200, 36));
                    button1.setName("button1");
                    button1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            button1ActionPerformed();
                        }
                    });
                    panel3.add(button1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                        new Insets(10, 0, 15, 5), 0, 0));

                    //---- label4 ----
                    label4.setText("<HTML>- Once your MySQL server is active, click on the button below <BR>&nbsp;&nbsp;&nbsp;to create and configure your database.</HTML>");
                    label4.setFont(new Font("Tahoma", Font.PLAIN, 13));
                    label4.setForeground(Color.black);
                    label4.setName("label4");
                    panel3.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 10, 5, 15), 0, 0));
                }
                panel2.add(panel3, BorderLayout.CENTER);
            }
            panel1.add(panel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        }
        add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label3;
    private JPanel panel2;
    private JLabel label6;
    private JPanel panel3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
