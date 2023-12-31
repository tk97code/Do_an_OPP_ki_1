package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingAnimationExample {
    private int x = 50;
    private int y = 50;
    private int deltaX = 5;
    private int deltaY = 3;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SwingAnimationExample().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Swing Animation Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBall(g);
            }
        };

        frame.add(panel);
        frame.setVisible(true);

        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePosition();
                panel.repaint();
            }
        });

        timer.start();
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 50, 50);
    }

    private void updatePosition() {
        x += deltaX;
        y += deltaY;

        if (x < 0 || x > 350) {
            deltaX = -deltaX;
        }

        if (y < 0 || y > 350) {
            deltaY = -deltaY;
        }
    }
}
