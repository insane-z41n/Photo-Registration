package pr.z41n.handler;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import pr.z41n.photos.FullView;

public class FullViewHandler {

	private FullView fullView;
	BufferedImage image;
	
	public FullViewHandler(FullView fullView, BufferedImage image) {
		this.fullView = fullView;
		this.image = image;
	}
	
	public void actionHandler() {
		fullView.btnExit.addActionListener(a -> exit());
		fullView.btnRotateCW.addActionListener(a -> rotateCW());
		fullView.btnRotateCCW.addActionListener(a -> rotateCCW());
	}
	
	private void exit() {
		fullView.close();
	}
	
	private void rotateCW() {
		image = rotateImage(image, 90);
		fullView.imagePanel.setImage(image);
		fullView.imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		fullView.imagePanel.repaint();
		fullView.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		fullView.pack();
		fullView.setLocationRelativeTo(null);
	}
	
	private void rotateCCW() {
		image = rotateImage(image, -90);
		fullView.imagePanel.setImage(image);
		fullView.imagePanel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		fullView.imagePanel.repaint();
		fullView.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		fullView.pack();
		fullView.setLocationRelativeTo(null);
	}
	
	private BufferedImage rotateImage(BufferedImage image, double degrees) {
		int type = image.getType();
		BufferedImage rotated = null;
		double rads = Math.toRadians(degrees);
		double sin = Math.abs(Math.sin(rads));
		double cos = Math.abs(Math.cos(rads));
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		int newWidth = (int) Math.floor(w * cos + h * sin);
		int newHeight = (int) Math.floor(h * cos + w * sin);
		
		rotated = new BufferedImage(newWidth, newHeight, type);
		Graphics2D g2 = rotated.createGraphics();
		AffineTransform at = new AffineTransform();
		at.translate((newWidth - w)/2, (newHeight - h)/2);
		at.rotate(rads, w/2 - 1, h/2 - 1);
		g2.setTransform(at);
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
		return rotated;
	}
}
