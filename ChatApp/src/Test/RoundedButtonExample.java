package Test;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoundedButtonExample extends JFrame {

    public RoundedButtonExample() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton roundedButton = new JButton("Click me!");
        roundedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked!");
            }
        });

        // Set the button's border to a rounded border
        roundedButton.setBorder(new RoundedBorder(10));

        buttonPanel.add(roundedButton);

        add(buttonPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoundedButtonExample());
    }
}

class RoundedBorder implements Border {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(c.getForeground());
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
