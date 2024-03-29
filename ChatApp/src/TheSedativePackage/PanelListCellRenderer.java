package TheSedativePackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import Client.AccountComponent;

public class PanelListCellRenderer implements ListCellRenderer<AccountComponent> {
	@Override
    public Component getListCellRendererComponent(JList<? extends AccountComponent> list, AccountComponent value, int index, boolean isSelected, boolean cellHasFocus) {
//    	Component renderer = (Component) value;
//    	
//    	if (isSelected) {
//            // Customize the appearance of selected cells
////    		renderer.setBackground(Color.BLUE);
//    		renderer.setBackground(new Color(91, 150, 247));
//    		setPanelForeground(renderer, Color.white);
//        } else {
//    		renderer.setBackground(Color.white);
//    		setPanelForeground(renderer, Color.black);
//    	}
//    	
//    	return renderer;
		
		JPanel renderer = new JPanel();
		renderer.setLayout(new BorderLayout());
		renderer.add(value, BorderLayout.CENTER);
		renderer.setPreferredSize(new Dimension(290, 94));
		renderer.setBackground(new Color(248, 250, 255));
		
		
		if (isSelected) {
          // Customize the appearance of selected cells
//  		renderer.setBackground(Color.BLUE);
			value.setBackground(new Color(91, 150, 247));
			setPanelForeground(value.getLblName(), Color.white);
		} else {
			value.setBackground(Color.white);
			setPanelForeground(value.getLblName(), Color.black);
		}
		
//		System.out.println(list.getModel().getSize());
		
		if (index == list.getModel().getSize() - 1) {
			renderer.setBorder(BorderFactory.createEmptyBorder(0, 0, 300, 0));
		} else {
			renderer.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));
		}
		
		return renderer;
    }
    
    private void setPanelForeground(JLabel label, Color color) {
    	label.setForeground(color);
    }
    
}