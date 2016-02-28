/*
 * Created by JFormDesigner on Sat Feb 27 13:04:03 MST 2016
 */

package bsaDb.client.home.dialogs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author User #2
 */
public class MessageDialog extends JDialog {

    public static final int OPTION_CLOSE = 0;
    public static final int OPTION_YES = 1;
    public static final int OPTION_NO = 2;

    private int choice = OPTION_CLOSE;

    public MessageDialog(Frame owner, String title, String message, MessageType type, ButtonType btnType) {
        super(owner);
        initComponents();

        setTitle(title);
        txtMessage.setText(message);
        lblImage.setIcon(new ImageIcon(getClass().getResource(type.getImagePath())));
        setButtons(btnType);
        setVisible(true);
    }

    public final int getChoice() {
        return choice;
    }

    private void setButtons(ButtonType type) {
        switch (type) {
            case YES:
                buildOkayButton("Yes");
                break;
            case YES_NO:
                buildOkayButton("Yes");
                buildCancelButton("No");
                break;
            case OKAY:
                buildOkayButton("Okay");
                break;
            case OKAY_CANCEL:
            default:
                buildOkayButton("Okay");
                buildCancelButton("Cancel");
        }
    }

    private void buildOkayButton(String name) {
        JButton btnOkay = new JButton();
        btnOkay.setText(name);
        btnOkay.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnOkay.setForeground(Color.white);
        btnOkay.setBackground(new Color(51, 156, 229));
        btnOkay.setMaximumSize(new Dimension(80, 35));
        btnOkay.setMinimumSize(new Dimension(80, 35));
        btnOkay.setPreferredSize(new Dimension(80, 35));
        btnOkay.setFocusPainted(false);
        btnOkay.setName("btnOkay");
        btnOkay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOkayActionPerformed();
            }
        });
        buttonBar.add(btnOkay, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
    }

    private void buildCancelButton(String name) {
        JButton btnCancel = new JButton();
        btnCancel.setText(name);
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancel.setForeground(Color.white);
        btnCancel.setBackground(new Color(207, 0, 0));
        btnCancel.setMaximumSize(new Dimension(80, 35));
        btnCancel.setMinimumSize(new Dimension(80, 35));
        btnCancel.setPreferredSize(new Dimension(80, 35));
        btnCancel.setFocusPainted(false);
        btnCancel.setName("btnCancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCancelActionPerformed();
            }
        });
        buttonBar.add(btnCancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    private void btnCancelActionPerformed() {
        choice = OPTION_NO;
        setVisible(false);
    }

    private void btnOkayActionPerformed() {
        choice = OPTION_YES;
        setVisible(false);
    }

    public enum MessageType {
        INFO(1, "/images/info64.png"),
        QUESTION(2, "/images/question64.png"),
        WARNING(3, "/images/warning64.png"),
        ERROR(4, "/images/error64.png"),
        SUCCESS(5, "/images/success64.png"),
        ;

        int id;
        String ImagePath;

        MessageType(int id, String imagePath) {
            this.id = id;
            ImagePath = imagePath;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public int getId() {
            return id;
        }
    }

    public enum ButtonType {
        YES(),
        YES_NO(),
        OKAY(),
        OKAY_CANCEL(),
        ;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel1 = new JPanel();
        JPanel pnlContents = new JPanel();
        buttonBar = new JPanel();
        txtMessage = new JTextArea();
        JPanel pnlImage = new JPanel();
        lblImage = new JLabel();

        //======== this ========
        setModal(true);
        setMinimumSize(new Dimension(430, 200));
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setMinimumSize(new Dimension(448, 20));
            panel1.setName("panel1");
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //======== pnlContents ========
            {
                pnlContents.setBackground(Color.white);
                pnlContents.setOpaque(false);
                pnlContents.setName("pnlContents");
                pnlContents.setLayout(new BorderLayout());

                //======== buttonBar ========
                {
                    buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                    buttonBar.setOpaque(false);
                    buttonBar.setName("buttonBar");
                    buttonBar.setLayout(new GridBagLayout());
                    ((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {35};
                    ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};
                }
                pnlContents.add(buttonBar, BorderLayout.SOUTH);

                //---- txtMessage ----
                txtMessage.setOpaque(false);
                txtMessage.setText("Message here");
                txtMessage.setLineWrap(true);
                txtMessage.setWrapStyleWord(true);
                txtMessage.setForeground(Color.black);
                txtMessage.setEditable(false);
                txtMessage.setMargin(new Insets(10, 10, 10, 10));
                txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
                txtMessage.setName("txtMessage");
                pnlContents.add(txtMessage, BorderLayout.CENTER);

                //======== pnlImage ========
                {
                    pnlImage.setOpaque(false);
                    pnlImage.setName("pnlImage");
                    pnlImage.setLayout(new GridBagLayout());
                    ((GridBagLayout)pnlImage.getLayout()).columnWidths = new int[] {82, 0};
                    ((GridBagLayout)pnlImage.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)pnlImage.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                    ((GridBagLayout)pnlImage.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                    //---- lblImage ----
                    lblImage.setIcon(new ImageIcon(getClass().getResource("/images/warning64.png")));
                    lblImage.setName("lblImage");
                    pnlImage.add(lblImage, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                pnlContents.add(pnlImage, BorderLayout.WEST);
            }
            panel1.add(pnlContents, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 10, 10), 0, 0));
        }
        contentPane.add(panel1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel buttonBar;
    private JTextArea txtMessage;
    private JLabel lblImage;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
