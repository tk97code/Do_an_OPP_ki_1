package TheSedativePackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MyTextField extends JPasswordField {

    private float location;
    private boolean mouseOver = false;
    private boolean isFocused = false;
    private Color lineColor = new Color(3, 155, 216);
    private boolean isPass = false;
    
    public MyTextField() {
//        setBorder(new EmptyBorder(20, 3, 10, 3));
        setSelectionColor(new Color(76, 204, 255));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }
        });
        
        addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				isFocused = false;
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				isFocused = true;
				
			}
		});
    }
    
    public void setType(boolean isPass) {
    	this.isPass = isPass;
    	
    	if (!isPass) {
        	setEchoChar((char)0);
        } else {
        	setEchoChar('*');
        }
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = getWidth();
        int height = getHeight();
        if (mouseOver || isFocused) {
            g2.setColor(lineColor);
        } else if (!isFocused) { 
        	g2.setColor(new Color(150, 150, 150));
        } else {
            g2.setColor(new Color(150, 150, 150));
        }
        
        g2.fillRect(2, height - 1, width - 4, 2);
//        createHintText(g2);
        createLineStyle(g2);
        g2.dispose();
    }
    
    public void setSelectionColor(Graphics2D g2) {
    	g2.setColor(lineColor);
    }

    private void createLineStyle(Graphics2D g2) {
        if (isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight();
            g2.setColor(lineColor);
            double size;
            size = width;
            double x = (width - size) / 2;
            g2.fillRect((int) (x + 2), height - 2, (int) size, 2);
        }
    }

}