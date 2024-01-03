//package Test;
//
//import javax.swing.*;
//
//import org.jdesktop.swingx.border.DropShadowBorder;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//
//public class CustomShadowExample {
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Soft Shadow Box Example");
//            frame.setSize(300, 200);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            JPanel panel = new JPanel();
//            panel.setLayout(new BorderLayout());
//
//            JButton button = new JButton("Click me");
//            button.setPreferredSize(new Dimension(100, 50));
//
//            // Thêm hiệu ứng shadow box cho JButton
//            button.setBorder(BorderFactory.createCompoundBorder(
//                    new SoftBevelBorder(SoftBevelBorder.LOWERED),
//                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
//            ));
//
//            // Button click listener
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Button clicked!");
//                }
//            });
//
//            panel.add(button, BorderLayout.CENTER);
//            frame.getContentPane().add(panel);
//
//            frame.setVisible(true);
//        });
//    }
//}
//
