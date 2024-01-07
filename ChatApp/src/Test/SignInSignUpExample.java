package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInSignUpExample {
    private JFrame frame;
    private JPanel signInPanel;
    private JPanel signUpPanel;

    public SignInSignUpExample() {
        frame = new JFrame("Sign In/Sign Up Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        signInPanel = createSignInPanel();
        signUpPanel = createSignUpPanel();

        // Display the Sign In panel initially
        frame.getContentPane().add(signInPanel);

        frame.setVisible(true);
    }

    private JPanel createSignInPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Sign In Panel");
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton switchButton = new JButton("Switch to Sign Up");
        JButton signInButton = new JButton("Sign In");

        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the Sign Up panel
                frame.getContentPane().removeAll();
                frame.getContentPane().add(signUpPanel);
                frame.repaint();
                frame.revalidate();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform Sign In logic here
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Your Sign In logic goes here

                JOptionPane.showMessageDialog(frame, "Sign In logic goes here for: " + username);
            }
        });

        panel.setLayout(new GridLayout(4, 1));
        panel.add(label);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(switchButton);
        panel.add(signInButton);

        return panel;
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Sign Up Panel");
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JButton switchButton = new JButton("Switch to Sign In");
        JButton signUpButton = new JButton("Sign Up");

        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the Sign In panel
                frame.getContentPane().removeAll();
                frame.getContentPane().add(signInPanel);
                frame.repaint();
                frame.revalidate();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform Sign Up logic here
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Your Sign Up logic goes here

                JOptionPane.showMessageDialog(frame, "Sign Up logic goes here for: " + username);
            }
        });

        panel.setLayout(new GridLayout(4, 1));
        panel.add(label);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(switchButton);
        panel.add(signUpButton);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignInSignUpExample();
            }
        });
    }
}
