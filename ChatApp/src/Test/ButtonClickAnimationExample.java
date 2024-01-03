package Test;

import javax.swing.*;

import Client.AccountComponent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

class CustomPanelListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = (Component) value;

        if (isSelected && (index + 1) % 2 == 0) {
            renderer.setBackground(new Color(91, 150, 247));
            setPanelForeground(renderer, Color.white);
        } else {
            renderer.setBackground(Color.white);
            setPanelForeground(renderer, Color.black);
        }

        return renderer;
    }

    private void setPanelForeground(Component panel, Color color) {
        if (panel instanceof JPanel) {
            Component[] components = ((JPanel) panel).getComponents();
            for (Component component : components) {
                if (component instanceof JLabel) {
                    ((JLabel) component).setForeground(color);
                }
            }
        }
    }
}

public class ButtonClickAnimationExample extends JFrame {

    private int selectedCellIndex = -1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ButtonClickAnimationExample frame = new ButtonClickAnimationExample();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ButtonClickAnimationExample() {
        setSize(1024, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultListModel<Component> model = new DefaultListModel<>();
        JList<Component> list = new JList<>(model);
        list.setCellRenderer(new CustomPanelListCellRenderer());
        list.setBorder(BorderFactory.createEmptyBorder());
        list.setBackground(new Color(248, 250, 255));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add ListSelectionListener to track cell selection
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedCellIndex = list.getSelectedIndex();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(290, 760));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Populate the JList with data
        for (int i = 0; i < 20; i++) {
            model.addElement(new AccountComponent());
        }

        add(scrollPane, BorderLayout.CENTER);
    }
}
