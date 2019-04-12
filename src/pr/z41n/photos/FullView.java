package pr.z41n.photos;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pr.z41n.accessories.Design;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.FullViewHandler;
import pr.z41n.imageloader.CreateImagePanel;
import pr.z41n.imageloader.ImageLoader;

@SuppressWarnings("serial")
public class FullView extends JFrame implements FrameObject{
	
	public int width, height;
	
	public JButton btnExit, btnRotateCW, btnRotateCCW;
	public CreateImagePanel imagePanel;
	
	private String imageName;
	
	public FullView(int width, int height, String imageName) {
		this.width = width;
		this.height = height;
		this.imageName = imageName;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setUndecorated(true);
		
		setLayout(new BorderLayout());
		
		setResizable(false);
	}

	public void initFrame() {
		
		BufferedImage viewImage = getImage(imageName);
		//setSize(viewImage.getWidth(), viewImage.getHeight());
		setAlwaysOnTop(true);

		imagePanel = new CreateImagePanel(viewImage);
		imagePanel.setLayout(new BorderLayout());
		add(imagePanel, BorderLayout.CENTER);
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Design.primary);
		topPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		imagePanel.add(topPanel, BorderLayout.NORTH);
		
		btnExit = new JButton("X");
		btnExit.setBackground(Color.RED);
		btnExit.setForeground(Design.textColor);
		topPanel.add(btnExit);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Design.primary);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		imagePanel.add(buttonPanel, BorderLayout.SOUTH);
		
		btnRotateCW = new JButton("->");
		btnRotateCCW = new JButton("<-");
		
		btnRotateCW.setBackground(Design.secondary);
		btnRotateCW.setForeground(Design.textColor);
		btnRotateCW.setFocusPainted(false);
		
		btnRotateCCW.setBackground(Design.secondary);
		btnRotateCCW.setForeground(Design.textColor);
		btnRotateCCW.setFocusPainted(false);
		
		buttonPanel.add(btnRotateCCW);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(btnRotateCW);
		
		FullViewHandler viewHandler = new FullViewHandler(this, viewImage);
		viewHandler.actionHandler();
		
		
		pack();
		setVisible(true);
		
		setLocationRelativeTo(null);
	}

	private BufferedImage getImage(String imageName) {
		BufferedImage img = ImageLoader.loadImage(imageName);
		
		int orgW = img.getWidth();
		int orgH = img.getHeight();
		
		int trgtW = orgW;
		int trgtH = orgH;
		
		double scale = 1;
		double screenD = 0;
		if(width >= height) {
			screenD = height;
		}
		else {
			screenD = width;
		}
		
		if(screenD < orgW || screenD < orgH) {
			if(orgW >=orgH) {
				scale = orgW/screenD;
			}
			else {
				scale = orgH/screenD;
			}
		}
		
		trgtW = (int) Math.ceil(orgW/scale);
		trgtH = (int) Math.ceil(orgH/scale);
		
		System.out.println("Scale = " + scale);
		System.out.println("Picture Width - " + trgtW);
		System.out.println("Picture Height - " + trgtH);
		BufferedImage resizedImg = resizeImage(img, trgtW, trgtH);
	
		return resizedImg;
	}
	
	private BufferedImage resizeImage(BufferedImage img, int targetWidth, int targetHeight) {
		BufferedImage reducedImage = img;
		BufferedImage temp = null;
		Graphics2D g2 = null;
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		
		int newW = img.getWidth();
		int newH = img.getHeight();
		
		int prevW = newW;
		int prevH = newH;
		
		do {
			System.out.println("DO IT!!!");
			if(newW > targetWidth){
				newW /= 2;
				newW = (newW < targetWidth) ? targetWidth : newW;
			}

			if(newH > targetHeight) {
				newH /= 2;
				newH = (newH < targetHeight) ? targetHeight : newH;
			}
			
			if(temp == null) {
				temp = new BufferedImage(newW, newH, type);
				g2 = temp.createGraphics();
			}
			
			g2.setComposite(AlphaComposite.Src);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.drawImage(reducedImage, 0, 0, newW, newH, 0, 0, prevW, prevH, null);
		    
		    prevW = newW;
		    prevH = newH;
		    
		    reducedImage = temp;
		    
		} while(newW != targetWidth || newH != targetHeight);
		
		if(g2 != null) {
			g2.dispose();
		}
		
		if(targetWidth != reducedImage.getWidth() || targetHeight != reducedImage.getHeight()) {
			temp = new BufferedImage(targetWidth, targetHeight, type);
			g2 = temp.createGraphics();
			g2.drawImage(reducedImage, 0, 0, null);
			g2.dispose();
			reducedImage = temp;
		}
		return reducedImage;
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
		
	}

}