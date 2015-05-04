package bsaDb.client.customComponents;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Malloch on 7/2/14
 */
public class SideMenuSelectBorder extends AbstractBorder {
    public SideMenuSelectBorder() {}

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d;
        if (g instanceof Graphics2D) {
            g2d = (Graphics2D) g;
            g2d.setColor(new Color(51,102,153));

            // Top Border
            g2d.fill(new Rectangle2D.Double(x, y, width, 5));
            // Bottom Border
            g2d.fill(new Rectangle2D.Double(x, height - 5, width, 5));
            // Left Border
            g2d.fill(new Rectangle2D.Double(x, y, 4, height));
        }
    }
}
