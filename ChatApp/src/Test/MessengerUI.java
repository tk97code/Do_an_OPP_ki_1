package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessengerUI extends JFrame {

    private DefaultListModel<Message> messageListModel;
    private JList<Message> messageList;
    private JTextArea messageTextArea;
    private JTextArea inputField;

    public MessengerUI() {
        setTitle("Messenger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        messageListModel = new DefaultListModel<>();
        messageList = new JList<>(messageListModel);
        messageList.setCellRenderer(new MessageListRenderer());
        JScrollPane messageListScrollPane = new JScrollPane(messageList);

        messageTextArea = new JTextArea();
        messageTextArea.setEditable(false);
        JScrollPane messageTextScrollPane = new JScrollPane(messageTextArea);

        inputField = new JTextArea();
        inputField.setMaximumSize(new Dimension(200, 200));
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        JButton send1Button = new JButton("Send 1");
        send1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send1Message();
            }
        });
        
        JButton send2Button = new JButton("Send 2");
        send2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send2Message();
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(send1Button, BorderLayout.EAST);
        inputPanel.add(send2Button, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(messageTextScrollPane, BorderLayout.CENTER);
        rightPanel.add(inputPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messageListScrollPane, rightPanel);
        splitPane.setResizeWeight(0.2);

        getContentPane().add(splitPane);

        // Add some sample messages
        addMessage("User 1", "Hello");
        addMessage("User 2", "Hi there!");

        messageList.addListSelectionListener(e -> displaySelectedMessage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private void send1Message() {
        String messageText = inputField.getText().trim();
        if (!messageText.isEmpty()) {
            // Determine sender based on user
            String sender = "User 1"; // Replace with logic to determine the sender
            addMessage(sender, messageText);
            inputField.setText("");
        }
    }
    
    private void send2Message() {
        String messageText = inputField.getText().trim();
        if (!messageText.isEmpty()) {
            // Determine sender based on user
            String sender = "User 2"; // Replace with logic to determine the sender
            addMessage(sender, messageText);
            inputField.setText("");
        }
    }

    private void addMessage(String sender, String message) {
        Message newMessage = new Message(sender, message);
        messageListModel.addElement(newMessage);
    }

    private void displaySelectedMessage() {
        int selectedIndex = messageList.getSelectedIndex();
        if (selectedIndex != -1) {
            Message selectedMessage = messageListModel.getElementAt(selectedIndex);
            messageTextArea.setText(selectedMessage.getSender() + ": " + selectedMessage.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MessengerUI messengerUI = new MessengerUI();
            messengerUI.setVisible(true);
        });
    }

    private static class Message {
        private String sender;
        private String message;

        public Message(String sender, String message) {
            this.sender = sender;
            this.message = message;
        }

        public String getSender() {
            return sender;
        }

        public String getMessage() {
            return message;
        }
    }

    private static class MessageListRenderer extends JLabel implements ListCellRenderer<Message> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(value.getSender() + ": " + value.getMessage());

            // Align messages to the right for User 1 and to the left for others
            setHorizontalAlignment(value.getSender().equals("User 1") ? SwingConstants.RIGHT : SwingConstants.LEFT);

            // Apply different background colors for User 1 and others
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

            return this;
        }
    }
}



