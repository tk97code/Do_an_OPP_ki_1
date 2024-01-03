package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ShadowPanelExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Shadow Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                addShadow(g);
            }
        };
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(26, 26, 26));

        // Add your components to the contentPane
        JLabel label = new JLabel("This is a shadow panel");
        label.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(label, BorderLayout.CENTER);

        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void addShadow(Graphics g) {
        int width = 400;
        int height = 300;
        int shadowSize = 10;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(new Color(0, 0, 0, 60));

        // Draw shadow
        g2d.fillRoundRect(shadowSize, shadowSize, width - shadowSize * 2, height - shadowSize * 2, 20, 20);
        g2d.dispose();

        // Draw the panel on top of the shadow
        ((Graphics2D) g).drawImage(image, 0, 0, null);
    }
}
