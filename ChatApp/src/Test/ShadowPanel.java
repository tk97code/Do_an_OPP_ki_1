package Test;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.AbstractPainter;
import org.jdesktop.swingx.border.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ShadowPanel extends JXPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SwingX ShadowRenderer Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ShadowPanel shadowPanel = new ShadowPanel();
            shadowPanel.setPreferredSize(new Dimension(200, 100));
            DropShadowBorder d = new DropShadowBorder();
            shadowPanel.setBorder(d);
            frame.add(shadowPanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public ShadowPanel() {
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea("This is a shadowed panel.");
        textArea.setOpaque(false);

        // Create a painter for shadow effect
        AbstractPainter<JComponent> shadowPainter = new AbstractPainter<JComponent>() {
            @Override
            protected void doPaint(Graphics2D g, JComponent component, int width, int height) {
                int borderRadius = 20;


                // Draw the component
                g.setColor(component.getBackground());
                g.fill(new RoundRectangle2D.Double(0, 0, width, height, borderRadius, borderRadius));

                // Draw the border
                g.setColor(component.getForeground());
                g.setStroke(new BasicStroke(2));
                g.draw(new RoundRectangle2D.Double(0, 0, width, height, borderRadius, borderRadius));
            }
        };

        // Set the painter for the panel
        setBackgroundPainter(shadowPainter);

        // Add the JTextArea to the panel
        add(textArea, BorderLayout.CENTER);
    }
}
