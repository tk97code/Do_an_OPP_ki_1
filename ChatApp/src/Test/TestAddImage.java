package Test;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import TheSedativePackage.MyTextField;
import TheSedativePackage.RoundedBorder;

import javax.imageio.ImageIO;
import javax.swing.*;

class AddImage extends JPanel {
	
	private Image image;
	
	public AddImage(Image image) {
		this.image = image;
	}
	
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(image, 0, 0, this);
	}
}


class Blocks extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        for (int i = 1; i <= 1; i++) {
            g.setColor(Color.red);
            g.fillRect(this.getX() + i * 10, this.getY(), 100, 100);
        }
    }
}


public class TestAddImage extends JFrame {

	private JPanel userPanel;
	Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Pictures\\Untitled.png");
	private JLabel testImage;
	private AddImage img;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					TestAddImage frame = new TestAddImage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public TestAddImage() {
		setBounds(0, 0, 1024, 900);
		setBackground(Color.red);
		setLayout(null);
		Image newImage = image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
//		testImage = new JLabel(new ImageIcon(newImage));
//		testImage.setBounds(300, 100, 100, 100);
//		testImage.setBorder(new RoundedBorder(30));
//		add(testImage);
		img = new AddImage(newImage);
		img.setBounds(100, 100, 100, 100);
		img.setBorder(new RoundedBorder(Color.blue, 4, 100));
		
		userPanel = new JPanel();
		userPanel.setBounds(200, 200, 350, 200);
		userPanel.setBorder(new RoundedBorder(Color.blue, 4, 30));
		
		JTextField t = new JTextField();
		t.setBounds(20, 20, 200, 30);
		t.setBorder(new RoundedBorder(Color.black, 2, 30));
	
		
		DefaultListModel<MyTextField> h = new DefaultListModel<MyTextField>();
		MyTextField m = new MyTextField();
//		for (int i = 0; i < 9; i++) {
			h.addElement(m);
			m.setBounds(0, 0, 100, 100);
//		}
		
		JList<MyTextField> j = new JList<MyTextField>(h);
		j.setBounds(50,50,300,300);
		
		add(t);
		add(j);
		add(userPanel);
		add(img);
	}

}
