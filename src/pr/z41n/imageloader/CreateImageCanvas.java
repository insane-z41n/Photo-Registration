package pr.z41n.imageloader;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class CreateImageCanvas extends Canvas{
	
	private String imageName;
	private BufferStrategy bs;
	private int panelWidth;
	private int panelHeight;
	private BufferedImage image;
	
	public CreateImageCanvas(String imageName, BufferStrategy bs, int panelWidth, int panelHeight) {
		this.imageName = imageName;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		this.bs = bs;
		
		setPreferredSize(new Dimension(panelWidth, panelHeight));
	}

	
	public void paint(Graphics g) {
		int xPos = 0;
		int yPos = 0;
		image = resizeImage(ImageLoader.loadImage(imageName));
		
		if(image.getWidth() < panelWidth) {
			xPos = Math.abs(panelWidth - image.getWidth())/2;
		}
		if(image.getHeight() < panelHeight) {
			yPos = Math.abs(panelHeight - image.getHeight())/2;
		}
		super.paint(g);

		g.drawImage(image, xPos, yPos, image.getWidth(), image.getHeight(), null);
		bs.show();
	}
	
	
	
	
	//Resize the image to fit within the panels size.
	private BufferedImage resizeImage(BufferedImage image) {
		
		int newWidth = 0;
		int newHeight = 0;
		double factor = 1;
	
		if(image.getWidth() > panelWidth) {
			factor = image.getWidth()/panelWidth;
		}
		else if(image.getHeight() > panelHeight) {
			factor = image.getHeight()/panelHeight;
		}
			
		newWidth = (int) (image.getWidth()/factor);
		newHeight = (int) (image.getHeight()/factor);
		
		
		
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
		g2d.dispose();
		
		return resizedImage;
	}

}

