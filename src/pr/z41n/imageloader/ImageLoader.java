package pr.z41n.imageloader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pr.z41n.accessories.ProgramInfo;

@SuppressWarnings("serial")
public class ImageLoader extends JPanel{
	
	public static BufferedImage loadImage(String imageName) {
		
		File path = new File(ProgramInfo.getImagePath() + "\\" + imageName);
		try {
			return ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
