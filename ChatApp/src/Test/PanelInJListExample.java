package Test;

import javax.swing.*;

import Client.AccountComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class CustomPanel extends JPanel {
    private String text;

    public CustomPanel(String text) {
        this.text = text;
        setPreferredSize(new Dimension(100, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(text, 10, 20);
    }
}

class CustomCellRenderer implements ListCellRenderer<AccountComponent> {
    private int gap;

    public CustomCellRenderer(int gap) {
        this.gap = gap;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends AccountComponent> list, AccountComponent value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(290, 80));
        panel.add(value, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));

        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
        	value.setBackground(list.getBackground());
        	value.setForeground(list.getForeground());
        }

        return panel;
    }
}

public class PanelInJListExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JList with Gap");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DefaultListModel<AccountComponent> dataList = new DefaultListModel<>();
            for (int i = 1; i <= 10; i++) {
                dataList.addElement(new AccountComponent());
            }

            JList<AccountComponent> jList = new JList<>(dataList);
            jList.setCellRenderer(new CustomCellRenderer(10));

            JScrollPane scrollPane = new JScrollPane(jList);
            frame.add(scrollPane);

            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
