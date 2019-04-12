package pr.z41n.imageloader;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CreateImagePanel extends JPanel{
	
	private BufferedImage image;

	
	public CreateImagePanel(BufferedImage image) {
		this.image = image;
		
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}

	
	public void paintComponent(Graphics g) {
//		int xPos = 0;
//		int yPos = 0;
//		
//		if(image.getWidth() < panelWidth) {
//			xPos = Math.abs(panelWidth - image.getWidth())/2;
//		}
//		if(image.getHeight() < panelHeight) {
//			yPos = Math.abs(panelHeight - image.getHeight())/2;
//		}
		super.paintComponent(g);

		g.drawImage(image, 0, 0, this);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		//setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	
	
	
	
	//Resize the image to fit within the panels size.
//	private BufferedImage resizeImage(BufferedImage image) {
//		
//		int newWidth = 0;
//		int newHeight = 0;
//		double factor = 1;
//	
//		if(image.getWidth() > panelWidth) {
//			factor = image.getWidth()/panelWidth;
//		}
//		else if(image.getHeight() > panelHeight) {
//			factor = image.getHeight()/panelHeight;
//		}
//			
//		newWidth = (int) (image.getWidth()/factor);
//		newHeight = (int) (image.getHeight()/factor);
//		
//		
//		
//		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
//		Graphics2D g2d = resizedImage.createGraphics();
//		g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
//		g2d.dispose();
//		
//		return resizedImage;
//	}

}
