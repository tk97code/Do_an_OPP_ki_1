package Test;

import javax.swing.*;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class BubblePanel extends JXPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        int width = getWidth();
//        int height = getHeight();
//        int borderRadius = 20;
//        int shadowSize = 5;
//
//        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(shadowSize, shadowSize, width - 2 * shadowSize, height - 2 * shadowSize, borderRadius, borderRadius);
//
//        // Draw shadow
//        g2.setColor(new Color(0, 0, 0, 100));
//        g2.fillRoundRect(shadowSize, shadowSize + 5, width - 2 * shadowSize, height - 2 * shadowSize, borderRadius, borderRadius);
//
//        // Draw bubble background
//        g2.setColor(new Color(234, 242, 254));
//        g2.fill(bubble);
//
//        // Draw bubble border
//        g2.setColor(new Color(112, 156, 230));
//        g2.setStroke(new BasicStroke(2));
//        g2.draw(bubble);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bubble Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DropShadowBorder shadow = new DropShadowBorder();
            shadow.setShadowColor(Color.BLACK);
            shadow.setShowLeftShadow(true);
            shadow.setShowRightShadow(true);
            shadow.setShowBottomShadow(true);
            shadow.setShowTopShadow(true);
            
            BubblePanel bubblePanel = new BubblePanel();
            bubblePanel.setBounds(10, 10, 300, 100);
            bubblePanel.setBackground(Color.red);
            bubblePanel.setOpaque(true);
            bubblePanel.setBorder(shadow);
            frame.add(bubblePanel);

            frame.setSize(500, 500);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
