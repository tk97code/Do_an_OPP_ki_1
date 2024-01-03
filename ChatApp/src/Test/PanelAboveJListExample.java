package Test;

import javax.swing.*;
import java.awt.*;

public class PanelAboveJListExample extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Above JList Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a JList
            String[] data = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
            JList<String> jList = new JList<>(data);

            // Create a JPanel that you want to show above the JList
            JPanel abovePanel = new JPanel();
            abovePanel.setBackground(new Color(255, 0, 0, 100)); // Semi-transparent red
            abovePanel.setPreferredSize(new Dimension(150, 50));
            abovePanel.setOpaque(false);

            // Create a JLayeredPane
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(200, 150));

            // Add components to the layered pane
            layeredPane.add(jList, JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(abovePanel, JLayeredPane.PALETTE_LAYER);

            // Set layout for the frame and add layeredPane
            frame.setLayout(new BorderLayout());
            frame.add(layeredPane, BorderLayout.CENTER);

            frame.setSize(200, 150);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
