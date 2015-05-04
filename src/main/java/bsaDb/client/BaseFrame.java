/*
 * Created by JFormDesigner on Mon May 04 13:38:28 MDT 2015
 */

package bsaDb.client;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * @author User #2
 */
public class BaseFrame extends JFrame {
    private final static String SIGN_IN_PAGE = "signIn";
    private final static String HOME_PAGE = "home";

    private SignInPanel pnlSignIn;

    {
        pnlSignIn = new SignInPanel();
    }

    public BaseFrame() {
        initComponents();

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        pnlCards.add(pnlSignIn, SIGN_IN_PAGE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        pnlCards = new JPanel();

        //======== this ========
        setTitle("BSA Database");
        setIconImage(new ImageIcon(getClass().getResource("/images/flurDeLis16.png")).getImage());
        setMinimumSize(new Dimension(650, 500));
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

        //======== pnlCards ========
        {
            pnlCards.setName("pnlCards");
            pnlCards.setLayout(new CardLayout());
        }
        contentPane.add(pnlCards, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel pnlCards;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
