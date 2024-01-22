package Test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import TheSedativePackage.ScrollablePanel;

public class TestChatPanel {

    private int row;
    private static boolean isRight = true;

    public TestChatPanel()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setPreferredSize(new Dimension(1200, 800));
//        final JPanel content = new JPanel(new GridBagLayout());
        final ScrollablePanel content = new ScrollablePanel(new GridBagLayout());
        content.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        
//        JPanel panel  = new JPanel(new GridBagLayout());
//        panel.setBackground(Color.red);
//        panel.setLayout();
//        
        
//        content.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "");
        f.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		// TODO Auto-generated method stub
        		gbc.gridy = row++;
        		
//                if (isRight) {
//	                
//	                JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT) );
//	                p.setBorder( new EmptyBorder(10, 10, 10, 10) );
//	                p.setBackground(new Color(0, 0, 0, 0));
//	                JTextArea text = new JTextArea();
//	                text.append("BCABCABCABCABCABCABCABC");
//	                text.setEditable(false);
//	                text.setBounds(0, 0,0 ,0);
//	                text.setLineWrap( true );
//	                p.add(text);
//	                content.add(p, gbc);
//	                content.revalidate();
//	                isRight = false;
//                } else  {
                	String s = null;
                	JPanel p = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
                    p.setBorder( new EmptyBorder(10, 10, 10, 10) );
                    
                    p.setBackground(Color.GRAY);
                    JTextArea text = new JTextArea() {
                    	@Override
        			    protected void paintBorder(Graphics g) {
        					Graphics2D g2 = (Graphics2D) g;
        					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        			        g2.setColor(new Color(112, 156, 230));
        			        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
        			    }
                    	
                    	@Override
        			    protected void paintComponent(Graphics g) {
        					Graphics2D g2 = (Graphics2D) g;
        					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        			        g2.setColor(getBackground());
        			        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
        			        
        			        super.paintComponent(g);
        			        
        				}
                    };
                    text.setOpaque(false);
//                    text.append(s);
                    text.append("xin chao tat ca moi nguoi nha toi ten la");
                    text.setEditable(true);
                    text.setBorder(new EmptyBorder(10, 10, 10, 10));
//                    text.setBounds(0, 0,100 ,100);
                    text.setForeground(Color.white);
                    text.setBackground(new Color(91, 150, 247));
                    text.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//                    text.setColumns(100);
//                    text.setColumns(1);
                    Canvas c = new Canvas();
                    int w = c.getFontMetrics(text.getFont()).stringWidth(text.getText());
                    if (w >= 100 && w < 200) {
                    	text.setColumns(30);
                    } else if (w >= 200 && w < 400) { 
                    	text.setColumns(40);
                    }
                    else if (w >= 400 && w < 1000) {
                    	text.setColumns(50);
                    }
                    else if (w >= 1000) {
                    	text.setColumns(70);
                    }
//                    text.setMaximumSize(new Dimension(400, 100));
                    text.setBounds(30, 0, 0, 0);
                    text.setLineWrap( true );
                    p.add(text, FlowLayout.LEFT);
                    content.add(p, gbc);
                    content.revalidate();
                    isRight = true;
//                }
        		super.mouseClicked(e);
        	}
        });
        
        
        JScrollPane scrollPane = new JScrollPane(content);
//        scrollPane.setSize(new Dimension(1000, 700));
        scrollPane.setBounds(50, 50, 1000, 700);
        content.addContainerListener(new ContainerAdapter() {
            public void componentAdded(ContainerEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    	scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                    }
                });
            }
        }); 
        f.setLayout(null);
        f.pack();
        f.setLocationRelativeTo(null);
        f.add(scrollPane);
        f.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new TestChatPanel();
    }
}