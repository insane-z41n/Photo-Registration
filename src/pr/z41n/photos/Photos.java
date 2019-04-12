package pr.z41n.photos;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import pr.z41n.accessories.Design;
import pr.z41n.accessories.DropMenu;
import pr.z41n.accessories.ProgramInfo;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.PhotosHandler;
import pr.z41n.imageloader.ImageLoader;


@SuppressWarnings("serial")
public class Photos extends JFrame implements FrameObject {
	
	private JPanel topPanel;
	private JPanel menuPanel;
	private JPanel centerPanel;
	private JPanel eastPanel;
	private JPanel westPanel;
	private JPanel previousPanel;
	private JPanel nextPanel;
	private JPanel bottomPanel;
	private JPanel backPanel;
	private JPanel donePanel;

	private JLabel nextLabel;
	private JLabel prevLabel;
	
	public JButton btnMenuPho;
	public JButton btnBack;
	public JButton btnDone;
	public JButton btnPrev;
	public JButton btnNext;
	
	private JPanel [] picPanel;
	private JPanel [] interfacePanel;
	public static Canvas [] canvas;
	private static JPanel [] picBox;
	public static JButton [] btnView;
	public static JCheckBox [] printPhotos;
	public static JCheckBox [] emailPhotos;
	public static JSpinner [] spinnerPhotos;
	
	public static String [] displayImages = new String[6];
	
	private static BufferStrategy [] bs;
	private static Graphics [] g;
	
	private static int boxWidth;
	private static int boxHeight;
	
	public int boxNum;

	public int width;
	public int height;
	
	public Photos(int width, int height) {
	
		this.width = width;
		this.height = height;
		
		setSize(width, height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		
		setUndecorated(true);
		
		setLayout(new BorderLayout());
		
		setResizable(false);
	}
	
	public void initFrame() {
		
		
		System.out.println("Photos Frame Width: " + width);
		System.out.println("Photos Frame Height: " + height + "\n");
		
		getContentPane().setBackground(Design.primary);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//Panel containing the menu button and title.
		topPanel = new JPanel();
		topPanel.setBackground(Design.primary);
		topPanel.setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		
		//Menu Panel containing btnMenuPho.
		menuPanel = new JPanel();
		menuPanel.setBackground(Design.primary);
		menuPanel.setBorder(null);
		topPanel.add(menuPanel, BorderLayout.WEST);
		
		//Panel containing menu button.
		btnMenuPho = new JButton("");
		Design.createMenuButton(btnMenuPho);
		btnMenuPho.setIcon(new ImageIcon(Photos.class.getResource("/icon/menu.png")));
		menuPanel.add(btnMenuPho);
		
		//Center Panel contains east, west panels along with picture panels.
		int rows = 2;
		int columns = 3;
		centerPanel = new JPanel();
		centerPanel.setBackground(Design.primary);
		centerPanel.setLayout(new GridLayout(rows, columns));
		add(centerPanel, BorderLayout.CENTER);
		
		int units = rows*columns;
		if(units == 0) {
			if(rows > columns) {
				units = rows;
			}
			else {
				units = columns;
			}
		}
		
		g = new Graphics[units];
		bs = new BufferStrategy[units];
		picPanel = new JPanel[units];
		interfacePanel = new JPanel[units];
		canvas = new Canvas[units];
		picBox = new JPanel[units];
		btnView = new JButton[units];
		printPhotos = new JCheckBox[units];
		emailPhotos = new JCheckBox[units];
		spinnerPhotos = new JSpinner[units];
		
		createPicturePanels();
		
		//East Panel contains Next Panel.
		eastPanel = new JPanel();
		eastPanel.setBackground(Design.primary);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
		add(eastPanel, BorderLayout.EAST);
		
		eastPanel.add(Box.createVerticalGlue());
		
		//Next Panel contains btnNext and nextLabel.
		nextPanel = new JPanel();
		nextPanel.setBackground(Design.primary);
		nextPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		nextPanel.setLayout(new BoxLayout(nextPanel, BoxLayout.PAGE_AXIS));
		eastPanel.add(nextPanel, BorderLayout.CENTER);
		
		eastPanel.add(Box.createVerticalGlue());
		
		//Next Button get next array for pictures.
		btnNext = new JButton(">");
		Design.createPlainButton(btnNext);
		nextPanel.add(btnNext);
		
		//Next Label located below next button.
		nextLabel = new JLabel("<html>Next<br>Pictures</html>");
		nextLabel.setFont(Design.infoFont);
		nextLabel.setForeground(Design.textColor);
		nextPanel.add(nextLabel);
		
		//West Panel contains Previous Panel.
		westPanel = new JPanel();
		westPanel.setBackground(Design.primary);
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
		add(westPanel, BorderLayout.WEST);
		
		westPanel.add(Box.createVerticalGlue());
		
		//Previous Panel contains previous Button and previous label.
		previousPanel = new JPanel();
		previousPanel.setBackground(Design.primary);
		previousPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		previousPanel.setLayout(new BoxLayout(previousPanel, BoxLayout.PAGE_AXIS));
		westPanel.add(previousPanel);
		
		westPanel.add(Box.createVerticalGlue());
		
		//Previous Button getting the previous set of array for pictures
		btnPrev = new JButton("<");
		Design.createPlainButton(btnPrev);
		previousPanel.add(btnPrev);
		
		//Previous Label located below previous button.
		prevLabel = new JLabel("<html>Previous<br>Pictures</html>");
		prevLabel.setFont(Design.infoFont);
		prevLabel.setForeground(Design.textColor);
		previousPanel.add(prevLabel);
		
		//Bottom Panel contains button panels for back and done
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Design.primary);
		bottomPanel.setLayout(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);
		
		//Back Panel contains back button.
		backPanel = new JPanel();
		backPanel.setBackground(Design.primary);
		backPanel.setBorder(null);
		bottomPanel.add(backPanel, BorderLayout.WEST);
		
		//Back button goes back to register frame.
		btnBack = new JButton("Back");
		Design.createBigInterfaceButton(btnBack);
		backPanel.add(btnBack);
		
		//Done panel contains done button.
		donePanel = new JPanel();
		donePanel.setBackground(Design.primary);
		donePanel.setBorder(null);
		bottomPanel.add(donePanel, BorderLayout.EAST);
	
		//Done button continues on to verification frame.
		btnDone = new JButton("Done");
		Design.createBigInterfaceButton(btnDone);
		donePanel.add(btnDone);
		
		DropMenu dropMenu = new DropMenu(width, height, this);
		dropMenu.initFrame();
		PhotosHandler photosHand = new PhotosHandler(this, dropMenu);
		photosHand.actionHandler();
		
		setVisible(true);
		
		boxWidth = picBox[0].getWidth()-6;
		boxHeight = picBox[0].getHeight()-6;
		
		System.out.println("Box Width: " + boxWidth);
		System.out.println("Box Height: " + boxHeight + "\n");
		
		ProgramInfo.setIndex(ProgramInfo.getImageNames().size());	//Set Index
		ProgramInfo.setUpdatePic(true);								//Start picBox Updates
		setBufferStrategy();
		setSelections(ProgramInfo.getImageNames());
	}
	
	private void createPicturePanels() {
		for(int i = 0; i < picPanel.length; i++) {
			
			//Create picPanels.
			picPanel[i] = new JPanel();
			picPanel[i].setBackground(Design.primary);
			picPanel[i].setBorder(new EmptyBorder(5, 5, 5, 5));
			picPanel[i].setLayout(new BorderLayout());
			centerPanel.add(picPanel[i]);
			
			//Adds components to picPanel.
			//picBox Label initialization.
			picBox[i] = new JPanel();
			picBox[i].setBackground(Design.secondary);
			picBox[i].setBorder(new LineBorder(Design.textColor, 3));
			picBox[i].setLayout(new BorderLayout());
			picPanel[i].add(picBox[i], BorderLayout.CENTER);
			
			canvas[i] = new Canvas();
			canvas[i].setBackground(Design.primary);
			picBox[i].add(canvas[i], BorderLayout.CENTER);
			
			//Interface Panels.
			interfacePanel[i] = new JPanel();
			interfacePanel[i].setBackground(Design.primary);
			interfacePanel[i].setLayout(new FlowLayout());
			picPanel[i].add(interfacePanel[i], BorderLayout.SOUTH);
			
			//Spinners
			spinnerPhotos[i] = new JSpinner();
			spinnerPhotos[i].setFont(Design.infoFont);
			spinnerPhotos[i].setEnabled(false);
			interfacePanel[i].add(spinnerPhotos[i]);
			
			//Print Check Boxes.
			//Email Check Boxes.
			printPhotos[i] = new JCheckBox("Print");
			emailPhotos[i] = new JCheckBox("Email");
			Design.createPhotosCheckBoxes(printPhotos[i]);
			Design.createPhotosCheckBoxes(emailPhotos[i]);
			interfacePanel[i].add(printPhotos[i]);
			interfacePanel[i].add(emailPhotos[i]);
			
			//View Buttons.
			btnView[i] = new JButton("View");
			Design.createInterfaceButton(btnView[i]);
			interfacePanel[i].add(btnView[i]);	
		
		}
	}
	
	private static String [] getDisplayImages(ArrayList<String> imageNames) {
		int index = ProgramInfo.getIndex();
		if(index >= imageNames.size())
			index = imageNames.size() - 6;
		
		String [] images = new String[6];
		for(int i = 0; i < displayImages.length; i++) {
			images[i] = imageNames.get(index);
			index++;
		}
		
		return images;
	}

	//Gets imageNames ArrayList and displays the images onto pic box labels.
	public static void setPictureBox(ArrayList<String> imageNames) {
	
		//Initialize the index and restrain to inbounds of array list.
		displayImages = getDisplayImages(imageNames);
		
		//initalize the display images array.
		for(int i = 0; i < displayImages.length; i++) {
			
			if(displayImages[i] != null) {
				BufferedImage image = ImageLoader.loadImage(displayImages[i]);
				setImage(image, g[i], bs[i]);
			} else {
				clearCanvas(g[i], bs[i]);
			}
			
		}
	}
	
	//Sets the check boxes as selected, and the spinner value
	public static void setSelections(ArrayList<String> imageNames) {

		System.out.println("Emails Selected: " + ProgramInfo.getSelectedEmails());
		System.out.println("Prints with num Prints: " + ProgramInfo.getPrintsMap() + "\n");
		
		displayImages = getDisplayImages(imageNames);
		for(int i = 0; i < displayImages.length; i++) {
			//System.out.println("Display Image " + i + " : " + displayImages[i]);
			if(displayImages[i] != null) {
				//Prints and spinner.
				setupPrints(printPhotos[i], spinnerPhotos[i], displayImages[i]);
				//Emails
				setupEmailCB(emailPhotos[i], displayImages[i]);
				btnView[i].setEnabled(true);
			}
			else {
				//Disable components.
				//Prints.
				printPhotos[i].setSelected(false);
				printPhotos[i].setEnabled(false);
				//Emails.
				emailPhotos[i].setSelected(false);
				emailPhotos[i].setEnabled(false);
				//Spinners.
				spinnerPhotos[i].setValue(0);
				spinnerPhotos[i].setEnabled(false);
				//View Buttons.
				btnView[i].setEnabled(false);
			}
		}
	}
	
	//Setup Print Checkboxes if selected from user.
	private static void setupPrints(JCheckBox cbPrint, JSpinner spinner, String img) {
		LinkedHashMap<String, Integer> prints = ProgramInfo.getPrintsMap();
		boolean printSelected = false;
		for(int i = 0; i < prints.size(); i++) {
			if(prints.containsKey(img)) {
				cbPrint.setEnabled(true);
				cbPrint.setSelected(true);
				spinner.setEnabled(true);
				spinner.setValue(prints.get(img));
				printSelected = true;
			}
		}
		if(!printSelected) {
			cbPrint.setEnabled(true);
			cbPrint.setSelected(false);
			spinner.setEnabled(false);
			spinner.setValue(0);
		}
	}
	
	//Setup Email checkboxes if selected from user.
	private static void setupEmailCB(JCheckBox cbEmail, String img) {
		ArrayList<String> emails = ProgramInfo.getSelectedEmails();
		boolean emailSelected = false;
		for(int i = 0; i < emails.size(); i++) {
			if(emails.get(i).equals(img)) {
				cbEmail.setEnabled(true);
				cbEmail.setSelected(true);
				emailSelected = true;
			}
			
		}
		if(!emailSelected) {
			cbEmail.setEnabled(true);
			cbEmail.setSelected(false);
		}
	}
	
	private static void setImage(BufferedImage image, Graphics g, BufferStrategy bs) {
		
		if(ProgramInfo.updatePic) {
		
			g = bs.getDrawGraphics();
			
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			int xStartPos = 0;
			int yStartPos = 0;
			double scale = 1;
			
			if(imageWidth <= imageHeight) {
				scale = imageHeight/boxHeight;
			}
			else {
				scale = imageWidth/boxWidth;
			}
			
			imageWidth = (int) Math.floor(imageWidth/scale);
			imageHeight = (int) Math.floor(imageHeight/scale);
			
			if(imageWidth < boxWidth) {
				xStartPos = Math.abs(boxWidth - imageWidth)/2;
			}
			if(imageHeight < boxHeight) {
				yStartPos = Math.abs(boxHeight - imageHeight)/2;
			}
			
			
			g.clearRect(0, 0, boxWidth, boxHeight);
			g.drawImage(image, xStartPos, yStartPos, imageWidth, imageHeight, null);
			bs.show();
			g.dispose();
		}
	}
	
	private static void clearCanvas(Graphics g, BufferStrategy bs) {
		if(ProgramInfo.getUpdatePic()) {
			g = bs.getDrawGraphics();
			g.clearRect(0, 0, boxWidth, boxHeight);
			bs.show();
			g.dispose();
		}
	}
	
	public static void setBufferStrategy() {
		for(int i = 0; i < canvas.length; i++) {
			//Buffer Strategy.
			canvas[i].createBufferStrategy(3);
			bs[i] = canvas[i].getBufferStrategy();
		}
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
}
























