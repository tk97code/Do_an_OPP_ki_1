package Test;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GradientLabelExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gradient JLabel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel("Welcome back to school");
            applyGradient(label, "school", Color.BLUE, Color.CYAN);

            frame.getContentPane().add(label);
            frame.setSize(300, 100);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void applyGradient(JLabel label, String targetText, Color startColor, Color endColor) {
        String labelText = label.getText();
        int index = labelText.indexOf(targetText);

        if (index != -1) {
            Font font = label.getFont();
            StyledDocument doc = new DefaultStyledDocument();

            // Create a style with the desired gradient colors
            Style style = doc.addStyle("GradientStyle", null);
            StyleConstants.setForeground(style, startColor);
            StyleConstants.setForeground(style, endColor);

            try {
                // Add the text to the document
                doc.insertString(0, labelText, null);

                // Apply the gradient style to the target text
                doc.setCharacterAttributes(index, targetText.length(), style, true);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            // Set the styled document to the label
            
            label.setFont(font); // Restore the font to avoid losing other styling
        }
    }
}
