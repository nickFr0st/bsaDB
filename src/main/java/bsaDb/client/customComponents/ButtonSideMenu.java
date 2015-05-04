package bsaDb.client.customComponents;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Malloch on 7/2/14
 */
public class ButtonSideMenu extends JLabel {
    private Icon defaultImage;
    private Icon selectedImage;
    private SideMenuSelectBorder border;
    private Border paddingBorder;
    private Border defaultBorder;

    public ButtonSideMenu() {
        super();

        paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        border = new SideMenuSelectBorder();
        defaultBorder = new EmptyBorder(0, 0, getWidth(), getHeight());
        setBorder(BorderFactory.createCompoundBorder(defaultBorder, paddingBorder));

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isEnabled()) {
                    return;
                }

                setOpaque(true);
                setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(selectedImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(defaultImage);
            }
        });
    }

    public Icon getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(Icon defaultImage) {
        this.defaultImage = defaultImage;
        setIcon(defaultImage);
    }

    public Icon getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Icon selectedImage) {
        this.selectedImage = selectedImage;
    }

    public void setDefault() {
        setIcon(defaultImage);
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(defaultBorder, paddingBorder));
    }
}
