package TheSedativePackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ImageLoader extends JPanel {
	
	private Image image;
	
	public ImageLoader(Image image) {
		this.image = image;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		
		super.paintComponent(g);
		g2.drawImage(image, 0, 0, this);
	}
}
