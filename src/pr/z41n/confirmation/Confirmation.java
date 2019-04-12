package pr.z41n.confirmation;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import pr.z41n.accessories.Design;
import pr.z41n.accessories.DropMenu;
import pr.z41n.accessories.ProgramInfo;
import pr.z41n.database.DBConnection;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.ConfirmationHandler;
import pr.z41n.imageloader.ImageLoader;

@SuppressWarnings("serial")
public class Confirmation extends JFrame implements FrameObject {
	
	//Frame width and height.
	public int width, height;
	
	//Top Menu Area.
	private JPanel topPanel, menuPanel;
	//Menu Button.
	public JButton btnMenuCon;
	
	//Center Interface Area.
	private JPanel centerPanel, leftPanel, rightPanel;
	
	//Left Panel containers.
	private JPanel picturePanel, picBox, interfacePanel;
	private JPanel displayPicPanel, leftPicPanel, rightPicPanel;
	private JPanel prevPanel, nextPanel;
	
	//Picture Panel components. 
	private Canvas canvas;
	public JButton btnPrev, btnNext;
	public JButton btnView;
	private JLabel prevLabel, nextLabel;
	
	private static Graphics g;
	private static BufferStrategy bs;
	
	//Interface Components.
	public JSpinner spinnerPrint;	
	public JCheckBox cbPrint, cbEmail;
	
	//Right Panel containers and components.
	private JPanel userPanel, checkoutPanel;
	private JPanel [] infoPanel;
	public JLabel [] editLabel;
	public JTextField [] tfInfo;
	
	//Checkout Containers and components.
	private JPanel printPanel, emailPanel, totalPanel;
	public JLabel printAmountLabel, emailAmountLabel, totalLabel, printPriceLabel, emailPriceLabel, totalPriceLabel;
	
	
	//Bottom Button Area.
	private JPanel bottomPanel, backPanel, finishPanel;
	//Buttons for bottom panel.
	public JButton btnBack, btnCheckout;
	//Bottom Panel error Label.
	public JLabel errorLabel;
	
	//Information got from previous user
	public static List<String> names;
	private LinkedHashMap<String, Integer> prints;
	private ArrayList<String> emails;
	
	private int printAmount, emailAmount;
	private double printPrice, emailPrice;

	private NumberFormat usFormat = NumberFormat.getCurrencyInstance();
	public static int index;
	private static int boxWidth;

	private static int boxHeight;
	
	public Confirmation(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		System.out.println("Confirmation Frame Width: " + width);
		System.out.println("Confirmation Frame Height " + height + "\n");
		
		setSize(width, height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		
		setUndecorated(true);
		
		setLayout(new BorderLayout());
		
		setResizable(false);
	}
	
	public void initFrame() {
		
		getContentPane().setBackground(Design.primary);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//Initializes user information.
		//getUserInfo();
		
		index = 0;
		setIndex(index);
		
		//Initializes user selection of photos.
		prints = ProgramInfo.getPrintsMap();				//Gets Selected Prints from user.
		emails = ProgramInfo.getSelectedEmails();			//Gets Selected Emails from user.
		names = getImageNames();							//Gets all selected images without repeats.
		
		//Amount of selected print and email images,
		printAmount = calcPrintAmount(prints);
		emailAmount = emails.size();
		System.out.println("Confirm Emails size: " + emailAmount);
		//Price of all prints and email combined
		printPrice = printAmount * ProgramInfo.getPrintPrice();
		emailPrice = emailAmount * ProgramInfo.getEmailPrice();
		
		//initialize box width and height.
		BufferedImage image = ImageLoader.loadImage(names.get(0));
		boxWidth = image.getWidth();
		boxHeight = image.getHeight();
				
		if(image.getWidth() > width/4 || image.getHeight() > height/4) {
			boxWidth /= 6;
			boxHeight /= 6;
		}
		
		System.out.println("Box Width: " + boxWidth);
		System.out.println("Box Height: " + boxHeight);
		
		//Top Panel contains Menu Panel.
		topPanel = new JPanel();
		topPanel.setBackground(Design.primary);
		topPanel.setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		
		//Menu Panel contains Menu Button.
		menuPanel = new JPanel();
		menuPanel.setBackground(Design.primary);
		menuPanel.setBorder(null);
		topPanel.add(menuPanel, BorderLayout.WEST);
		
		//Menu Button.
		btnMenuCon = new JButton("");
		Design.createMenuButton(btnMenuCon);
		btnMenuCon.setIcon(new ImageIcon(Confirmation.class.getResource("/icon/menu.png")));
		menuPanel.add(btnMenuCon);
		
		//Center Panel.
		centerPanel = new JPanel();
		centerPanel.setBackground(Design.primary);
		centerPanel.setLayout(new GridLayout(0,2));
		add(centerPanel, BorderLayout.CENTER);
		
		//Left Panel containing selected user photos.
		leftPanel = new JPanel();
		leftPanel.setBackground(Design.primary);
		leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel.add(leftPanel);
		
		//Panel that holds the picture and interface components.
		picturePanel = new JPanel();
		picturePanel.setBackground(Design.primary);
		picturePanel.setLayout(new BorderLayout());
		leftPanel.add(picturePanel, BorderLayout.CENTER);
		
		//Holds left, right and pic box panel.
		displayPicPanel = new JPanel();
		displayPicPanel.setBackground(Design.primary);
		displayPicPanel.setLayout(new BorderLayout());
		picturePanel.add(displayPicPanel, BorderLayout.CENTER);
		
		//Left Picture Panel containing previous components and containers.
		leftPicPanel = new JPanel();
		leftPicPanel.setBackground(Design.primary);
		leftPicPanel.setLayout(new BoxLayout(leftPicPanel, BoxLayout.PAGE_AXIS));
		displayPicPanel.add(leftPicPanel, BorderLayout.WEST);
		
		leftPicPanel.add(Box.createVerticalGlue());
		
		//Previous Panel.
		prevPanel = new JPanel();
		prevPanel.setBackground(Design.primary);
		prevPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		prevPanel.setLayout(new BoxLayout(prevPanel, BoxLayout.PAGE_AXIS));
		leftPicPanel.add(prevPanel);
				
		leftPicPanel.add(Box.createVerticalGlue());

		//Prev Button.
		btnPrev = new JButton("<");
		Design.createPlainButton(btnPrev);
		prevPanel.add(btnPrev);
				
		//Prev Label.
		prevLabel = new JLabel("<html>Previous<br>Pictures</html>");
		prevLabel.setFont(Design.infoFont);
		prevLabel.setForeground(Design.textColor);
		prevPanel.add(prevLabel);
		
		//Center Picture panel containing the canvas where the picture will display.
		picBox = new JPanel();
		picBox.setBackground(Design.primary);
		picBox.setLayout(new BorderLayout());
		picBox.setBorder(new LineBorder(Design.textColor, 3));
		picBox.setPreferredSize(new Dimension(boxWidth, boxHeight));
		displayPicPanel.add(picBox, BorderLayout.CENTER);
		
		//Canvas shows picture.
		canvas = new Canvas();
		canvas.setBackground(Design.primary);
		picBox.add(canvas, BorderLayout.CENTER);
		
		//Right Picture panel containing next components and containers.
		rightPicPanel = new JPanel();
		rightPicPanel.setBackground(Design.primary);
		rightPicPanel.setLayout(new BoxLayout(rightPicPanel, BoxLayout.PAGE_AXIS));
		displayPicPanel.add(rightPicPanel, BorderLayout.EAST);
		
		rightPicPanel.add(Box.createVerticalGlue());
		
		//Next Panel.
		nextPanel = new JPanel();
		nextPanel.setBackground(Design.primary);
		nextPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		nextPanel.setLayout(new BoxLayout(nextPanel, BoxLayout.PAGE_AXIS));
		rightPicPanel.add(nextPanel);

		rightPicPanel.add(Box.createVerticalGlue());
		
		//Next Button.
		btnNext = new JButton(">");
		Design.createPlainButton(btnNext);
		nextPanel.add(btnNext);
		
		//Next Label.
		nextLabel = new JLabel("<html>Next<br>Pictures</html>");
		nextLabel.setFont(Design.infoFont);
		nextLabel.setForeground(Design.textColor);
		nextPanel.add(nextLabel);
		
		//Interface panel containing buttons and checkboxes.
		interfacePanel = new JPanel();
		interfacePanel.setBackground(Design.primary);
		interfacePanel.setLayout(new FlowLayout());
		picturePanel.add(interfacePanel, BorderLayout.SOUTH);
		
		//Spinner for Prints.
		spinnerPrint = new JSpinner();
		spinnerPrint.setFont(Design.infoFont);
		spinnerPrint.setEnabled(false);
		interfacePanel.add(spinnerPrint);
		
		//Print Check Box.
		cbPrint = new JCheckBox("Print");
		Design.createPhotosCheckBoxes(cbPrint);
		interfacePanel.add(cbPrint);
		
		//E-Mail Check Box.
		cbEmail = new JCheckBox("E-Mail");
		Design.createPhotosCheckBoxes(cbEmail);
		interfacePanel.add(cbEmail);
		
		//View Button.
		btnView = new JButton("View");
		Design.createInterfaceButton(btnView);
		interfacePanel.add(btnView);
		
		//Right Panel containing user info and price info.
		rightPanel = new JPanel();
		rightPanel.setBackground(Design.primary);
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBorder(new EmptyBorder(0,0,0,boxWidth/4));
		centerPanel.add(rightPanel);
		
		//User information panel.
		userPanel = new JPanel();
		userPanel.setBackground(Design.primary);
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.PAGE_AXIS));
		rightPanel.add(userPanel, BorderLayout.NORTH);
		
		//Set all the containers and components for userPanel contianing user information.
		List<String> info = getUserInfo();
		
		infoPanel = new JPanel[info.size() - 1];
		editLabel = new JLabel[info.size() - 1];
		tfInfo = new JTextField[info.size() - 1];
		
		setInfoPanel(infoPanel, editLabel, tfInfo);
		userPanel.add(Box.createVerticalStrut(boxHeight));
		//Checkout Panel.
		checkoutPanel = new JPanel();
		checkoutPanel.setBackground(Design.primary);
		checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.PAGE_AXIS));
		userPanel.add(checkoutPanel, BorderLayout.CENTER);
		
		checkoutPanel.add(Box.createVerticalGlue());
		
		//Email panel containing the email componennts.
		printAmountLabel = new JLabel(printAmount + "x Prints");
		emailAmountLabel = new JLabel(emailAmount + "x E-Mails");
		printPriceLabel = new JLabel(usFormat.format(printPrice));
		emailPriceLabel = new JLabel(usFormat.format(emailPrice));
		createCheckoutItem(printPanel, printAmountLabel, printPriceLabel, checkoutPanel);
		createCheckoutItem(emailPanel, emailAmountLabel, emailPriceLabel, checkoutPanel);
		
		totalPanel = new JPanel();
		totalPanel.setBackground(Design.primary);
		totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.LINE_AXIS));
		checkoutPanel.add(totalPanel);
		
		totalLabel = new JLabel("TOTAL");
		totalLabel.setFont(Design.bigInfoFont);
		totalLabel.setForeground(Design.textColor);
		totalPanel.add(totalLabel);
		
		totalPanel.add(Box.createHorizontalGlue());
		
		totalPriceLabel = new JLabel(usFormat.format(printPrice + emailPrice));
		totalPriceLabel.setFont(Design.bigInfoFont);
		totalPriceLabel.setForeground(Design.textColor);
		totalPanel.add(totalPriceLabel);
		
		checkoutPanel.add(Box.createVerticalGlue());
		//Bottom panel contianing back and finish containers.
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Design.primary);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setBorder(new EmptyBorder(0,boxWidth/4,width/10,boxWidth/4) );
		add(bottomPanel, BorderLayout.SOUTH);
		
		errorLabel = new JLabel("*Please make sure that all the indicated areas filled.");
		errorLabel.setForeground(Design.errorColor);
		errorLabel.setFont(Design.bigInfoFont);
		errorLabel.setVisible(false);
		
		JPanel errorPanel = new JPanel();
		errorPanel.setBackground(Design.primary);
		errorPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		errorPanel.setPreferredSize(new Dimension(width, errorLabel.getFont().getSize()*3 + 10));
		bottomPanel.add(errorPanel, BorderLayout.NORTH);
		
		errorPanel.add(errorLabel);
		//Contains back button.
		backPanel = new JPanel();
		backPanel.setBackground(Design.primary);
		backPanel.setBorder(null);
		bottomPanel.add(backPanel, BorderLayout.WEST);
		
		btnBack = new JButton("Back");
		Design.createBigInterfaceButton(btnBack);
		backPanel.add(btnBack);
		
		//Contains finish button.
		finishPanel = new JPanel();
		finishPanel.setBackground(Design.primary);
		finishPanel.setBorder(null);
		bottomPanel.add(finishPanel, BorderLayout.EAST);

		btnCheckout = new JButton("Finish");
		Design.createBigInterfaceButton(btnCheckout);
		finishPanel.add(btnCheckout);
		
		setVisible(true);
		
		setBufferStrategy();
		setPicture(index);
		setSelections(index);
		
		
		//Action handler initialization.
		DropMenu dropMenu = new DropMenu(width, height, this);
		dropMenu.initFrame();
		ConfirmationHandler confirmHand = new ConfirmationHandler(this, dropMenu);
		confirmHand.actionHandler();
		
	}
	
	private void createCheckoutItem(JPanel panel, JLabel amountLabel , JLabel priceLabel, JPanel parent) {
		
		panel = new JPanel();
		panel.setBackground(Design.primary);
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		parent.add(panel);
		
		amountLabel.setFont(Design.bigInfoFont);
		amountLabel.setForeground(Design.textColor);
		panel.add(amountLabel);
		
		panel.add(Box.createHorizontalGlue());
		
		priceLabel.setFont(Design.bigInfoFont);
		priceLabel.setForeground(Design.textColor);
		panel.add(priceLabel);
	}
	private void setInfoPanel(JPanel [] panel, JLabel [] edit, JTextField [] userInfo) {
		
		
		List<String> info = getUserInfo();
		int rows = info.size() - 1;
	
		for(int i = 0; i < rows; i++) {
			
			panel[i] = new JPanel();
			panel[i].setBackground(Design.primary);
			panel[i].setLayout(new BoxLayout(panel[i], BoxLayout.LINE_AXIS));
			userPanel.add(panel[i]);
			
			userInfo[i] = new JTextField(info.get(i));
			userInfo[i].setFont(Design.bigInfoFont);
			userInfo[i].setForeground(Design.textColor);
			userInfo[i].setBackground(Design.primary);
			userInfo[i].setBorder(new EmptyBorder(0,5,0,0));
			userInfo[i].setCaretColor(Design.textColor);
			userInfo[i].setEditable(false);
			panel[i].add(userInfo[i]);
			
			panel[i].add(Box.createHorizontalGlue());
			
			edit[i] = new JLabel("<< Edit");
			edit[i].setFont(Design.bigInfoFont);
			edit[i].setForeground(Design.secondary);
			edit[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			panel[i].add(edit[i]);
			
		}
	}
	
	public static void setPicture(int index) {
		if(names.get(index) != null) {
			BufferedImage image = ImageLoader.loadImage(names.get(index));
			setImage(image, g, bs);
		}
		else {
			clearCanvas(g, bs);
		}
	}
	
	private static void setImage(BufferedImage image, Graphics g, BufferStrategy bs) {
		if(ProgramInfo.getConfirmationToggle()) {
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
				
			imageWidth = (int) Math.ceil(imageWidth/scale);
			imageHeight = (int) Math.ceil(imageHeight/scale);
			
			if(imageWidth < boxWidth) {
				xStartPos = Math.abs(boxWidth - imageWidth)/2;
			}
			if(imageHeight < boxHeight) {
				yStartPos = Math.abs(boxHeight - imageHeight)/2;
			}
				
			g = bs.getDrawGraphics();
			g.clearRect(0, 0, boxWidth, boxHeight);
			g.drawImage(image, xStartPos, yStartPos, imageWidth, imageHeight, null);
			bs.show();
			g.dispose();
		}
		
	}
	
	private static void clearCanvas(Graphics g, BufferStrategy bs) {
	
		g = bs.getDrawGraphics();
		g.clearRect(0,0,boxWidth, boxHeight);
		bs.show();
		g.dispose();
		
	}
	private void setBufferStrategy() {
		canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
	}
	
	public void setSelections(int index) {
		if(prints.size() < 0 && emails.size() < 0) {
			//Prints components disabled.
			cbPrint.setSelected(false);
			cbPrint.setEnabled(false);
			spinnerPrint.setEnabled(false);
			spinnerPrint.setValue(0);
			
			//Email components disabled.
			cbEmail.setSelected(false);
			cbEmail.setEnabled(false);
		}
		else {
			setupPrintCB(cbPrint, spinnerPrint, names.get(index));
			setupEmailCB(cbEmail, names.get(index));
		}
	}
	private void setupPrintCB(JCheckBox cbPrint, JSpinner spin, String img) {
		
		prints = getPrints();
		boolean printSelected = false;
		for(int i = 0; i < prints.size(); i++) {
			if(prints.containsKey(img)) {
				cbPrint.setSelected(true);
				spin.setEnabled(true);
				spin.setValue(prints.get(img));
				printSelected = true;
			}
		}
		if(!printSelected) {
			cbPrint.setSelected(false);
			spin.setEnabled(false);
			spin.setValue(0);
		}
	
	}
	
	//Setup Email checkboxes if selected from user.
	private void setupEmailCB(JCheckBox cbEmail, String img) {
		
		emails = getEmails();
		boolean emailSelected = false;
		for(int i = 0; i < emails.size(); i++) {
			if(emails.get(i).equals(img)) {
				cbEmail.setEnabled(true);
				cbEmail.setSelected(true);
				emailSelected = true;
				emailAmount++;
			}
				
		}
		if(!emailSelected) {
			cbEmail.setEnabled(true);
			cbEmail.setSelected(false);
		}
	}
	
	private int calcPrintAmount(LinkedHashMap<String, Integer> prints) {
		int amount = 0;
		List<String> keys = new ArrayList<String>(prints.keySet());
		for(int i = 0; i < prints.size(); i++) {
			amount+=prints.get(keys.get(i));
		}
		return amount;
	}
	
	//Return names of selected images.
	public List<String> getImageNames(){
		
		List<String> imgs = new ArrayList<>(prints.keySet());
		List<String> temp = new ArrayList<>(emails);
		
		//If prints and emails are selected.
		if(imgs.size() > 0 && temp.size() > 0) {
			for(int i = 0; i < imgs.size(); i++) {
				for(int j = 0; j < temp.size(); j++) {
					if(temp.get(j).equals(imgs.get(i))) {
						temp.remove(j);
					}
				}
			}
			int index = temp.size() - 1;
			while(!temp.isEmpty()) {
				imgs.add(temp.remove(index));
				index--;
			}
			
			return imgs;
		}
		//if only prints are selected.
		else if(imgs.size() > 0){
			return imgs;
			
		}
		//if only emails are selected.
		else if(temp.size() > 0) {
			return temp;
		}
		//if nothing was selected.
		else {
			return null;
		}
		
	}
	
	//Return a list of user information from database.
	private List<String> getUserInfo() {
		
		List<String> info = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		String query = "SELECT * FROM `users` WHERE `user_id` = " + ProgramInfo.getUserID();
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			rsmd = rs.getMetaData();

			while (rs.next()) {
				for(int i = 1; i < rsmd.getColumnCount(); i++) {
					info.add(rs.getString(i));
					//System.out.println("User Information: " + info.get(i-1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null)
					con.close();
				if(ps != null)
					ps.close();
				if(rs != null)
					rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return info;
	}
	
	public void setEmails(ArrayList<String> emails) {
		this.emails = emails;
	}
	
	public ArrayList<String> getEmails() {
		return emails;
	}
	
	public void setPrints(LinkedHashMap<String, Integer> prints) {
		this.prints = prints;
	}
	
	public LinkedHashMap<String, Integer> getPrints() {
		return prints;
	}
	
	public static void setIndex(int index) {
		Confirmation.index = index;
	}
	
	public static int getIndex() {
		return index;
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	


}
