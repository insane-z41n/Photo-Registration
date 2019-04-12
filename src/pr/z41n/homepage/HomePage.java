package pr.z41n.homepage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import pr.z41n.accessories.Design;
import pr.z41n.accessories.DropMenu;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.HomePageHandler;

@SuppressWarnings("serial")
public class HomePage extends JFrame implements FrameObject {

	public int width, height;
	
	//North Panels.
	private JPanel topPanel, menuPanel;
	
	//Menu Button.
	public JButton btnMenu;
	
	//Center Panels.
	private JPanel centerPanel, leftPanel, rightPanel, loginPanel, regiPanel;
	
	//LEFT
	//Login Components and Containers.
	private JPanel titleLogPanel, errorLogPanel, userLogPanel, passLogPanel, logBtnPanel;
	private JLabel logTitleLabel;
	public JLabel errorLogLabel;
	public JTextField tfUserLog;
	public JPasswordField pfPassLog;
	public JButton btnLogin;
	public JLabel logErrorLabel;
	
	//RIGHT
	//Register Components and Containers.
	private JPanel titleRegiPanel, errorRegiPanel, emailPanel, fullNamePanel, passRegiPanel, rePassRegiPanel;
	private JPanel regiBtnPanel;
	private JLabel titleRegiLabel;
	public JLabel errorRegiLabel;
	public JTextField tfFullName, tfEmail;
	public JPasswordField pfPassRegi, pfRePassRegi;
	public JButton btnContinue;
	public JLabel regiErrorLabel;
	
	
	public HomePage(int width, int height) {
		this.width = width;
		this.height = height;
		
		System.out.println("Home Page Frame Width: " + width);
		System.out.println("Home Page Frame Height: " + height + "\n");
		
		//Set size of Welcome Frame to full screen.
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		//Shutdown on exit.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set application in the middle.
		setLocationRelativeTo(null);
				
		//Remove exit and minimize bar
		setUndecorated(true);
						
		//Layout of this frame. 
		setLayout(new BorderLayout());
				
		//Changing the size of the frame is not allowed
		setResizable(false);
	}
	
	public void initFrame() {
		getContentPane().setBackground(Design.primary);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//Top Panel, North, Holds Menu Panel and Menu Button.
		topPanel = new JPanel();
		topPanel.setBackground(Design.primary);
		topPanel.setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		
		//Menu Panel, West, Holds Menu Button.
		menuPanel = new JPanel();
		menuPanel.setBackground(Design.primary);
		menuPanel.setBorder(null);
		topPanel.add(menuPanel, BorderLayout.WEST);
		
		//Menu Button, Accesses Drop Down Class.
		btnMenu = new JButton();
		Design.createMenuButton(btnMenu);
		btnMenu.setIcon(new ImageIcon(HomePage.class.getResource("/icon/menu.png")));
		menuPanel.add(btnMenu);
		
		//Holds Left & Right panels.
		centerPanel = new JPanel();
		centerPanel.setBackground(Design.primary);
		centerPanel.setLayout(new GridLayout(0,2));
		add(centerPanel, BorderLayout.CENTER);
		
		//Left Panel, Grid 1, Holds Login Components.
		leftPanel = new JPanel();
		leftPanel.setBackground(Design.primary);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.LINE_AXIS));
		leftPanel.setBorder(new MatteBorder(0,0,0,1, Design.textColor));
		centerPanel.add(leftPanel);
		
		//Left Glue spacing
		//leftPanel.add(Box.createHorizontalGlue());
		
		//Login Panel, Holds Components: Panels, Text Fields, Labels, Buttons.
		loginPanel = new JPanel();
		loginPanel.setBackground(Design.primary);
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(loginPanel);
		//Top Login panel glue spacing.
		//loginPanel.add(Box.createVerticalGlue());
		
		//CONTAINERS
		
		//Title Log Panel.
		titleLogPanel = new JPanel();
		Design.createPanelHomePage(titleLogPanel);
		loginPanel.add(titleLogPanel);
		
		loginPanel.add(Box.createVerticalGlue());
		
		int errorPanelHeight = Design.bigInfoFont.getSize() * 2 + 10;
		errorLogPanel = new JPanel();
		errorLogPanel.setBackground(Design.primary);
		errorLogPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		errorLogPanel.setPreferredSize(new Dimension(loginPanel.getWidth(), errorPanelHeight));
		loginPanel.add(errorLogPanel);
		
		//Email Login panel.
		userLogPanel = new JPanel();
		Design.createPanelHomePage(userLogPanel);
		loginPanel.add(userLogPanel);
		
		//Pass Login panel.
		passLogPanel = new JPanel();
		Design.createPanelHomePage(passLogPanel);
		loginPanel.add(passLogPanel);
		
		
		loginPanel.add(Box.createVerticalGlue());
		
		//Button Login Panel.
		logBtnPanel = new JPanel();
		logBtnPanel.setBackground(Design.primary);
		logBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		loginPanel.add(logBtnPanel);
		
		//COMPONENTS
		
		//Title Login Label.
		logTitleLabel = new JLabel("Login");
		logTitleLabel.setForeground(Design.textColor);
		logTitleLabel.setFont(Design.bigTitleFont);
		titleLogPanel.add(logTitleLabel);
		
		errorLogLabel = new JLabel("<html>This user does not exists.<br>Please try again.</html>");
		errorLogLabel.setForeground(Design.errorColor);
		errorLogLabel.setFont(Design.bigInfoFont);
		errorLogLabel.setVisible(false);
		errorLogPanel.add(errorLogLabel);
		
		String [] logTexts = {"E-Mail", "Password"};
		//Match this font width font inside createTFHomePage Font.
		int tfFontSize = Design.largeFont.getSize() + 40;
		int logTFWidth = Design.getComponentWidth(logTexts, tfFontSize);
		//Email Text Field.
		tfUserLog = new JTextField(logTexts[0]);
		Design.createTFHomePage(tfUserLog, logTFWidth);
		userLogPanel.add(tfUserLog);
		
		//Password Field
		pfPassLog = new JPasswordField(logTexts[1]);
		Design.createTFHomePage(pfPassLog, logTFWidth);
		pfPassLog.setEchoChar((char)0);
		passLogPanel.add(pfPassLog);
		
		btnLogin = new JButton("Login");
		Design.createBigInterfaceButton(btnLogin);
		logBtnPanel.add(btnLogin);
		
		//Bottom Login panel glue spacing.
		loginPanel.add(Box.createVerticalGlue());
		
		//Right Panel, Grid 2, Holds Register Components.
		rightPanel = new JPanel();
		rightPanel.setBackground(Design.primary);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.LINE_AXIS));
		rightPanel.setBorder(new MatteBorder(0,1,0,0, Design.textColor));
		centerPanel.add(rightPanel);
		
		//Regi Panel, Holds ComponetsL Panels, Text Fields, Labels, Buttons.
		regiPanel = new JPanel();
		regiPanel.setBackground(Design.primary);
		regiPanel.setLayout(new BoxLayout(regiPanel, BoxLayout.PAGE_AXIS));
		rightPanel.add(regiPanel);
		
		//CONTAINERS
		
		//Title Panel, Holds Title Label component.
		titleRegiPanel = new JPanel();
		Design.createPanelHomePage(titleRegiPanel);
		regiPanel.add(titleRegiPanel);
		
		errorRegiPanel = new JPanel();
		errorRegiPanel.setBackground(Design.primary);
		errorRegiPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		errorRegiPanel.setPreferredSize(new Dimension(regiPanel.getWidth(), errorPanelHeight));
		regiPanel.add(errorRegiPanel);
		
		//Full Name Panel, Holds Full Name Text Field component.
		fullNamePanel = new JPanel();
		Design.createPanelHomePage(fullNamePanel);
		regiPanel.add(fullNamePanel);
		
		emailPanel = new JPanel();
		Design.createPanelHomePage(emailPanel);
		regiPanel.add(emailPanel);
		
		//Password Panel, holds password password field component
		passRegiPanel = new JPanel();
		Design.createPanelHomePage(passRegiPanel);
		regiPanel.add(passRegiPanel);
		
		//Re Enter Password Panel, holds re enter password password field component
		rePassRegiPanel = new JPanel();
		Design.createPanelHomePage(rePassRegiPanel);
		regiPanel.add(rePassRegiPanel);
		
		//Bottom Regi panel glue spacing.
		regiPanel.add(Box.createVerticalGlue());
		
		//Register Button Panel, Holds create new user button.
		regiBtnPanel = new JPanel();
		Design.createPanelHomePage(regiBtnPanel);
		regiPanel.add(regiBtnPanel);
		
		//COMPONENTS
		
		//Registration Title Label.
		titleRegiLabel = new JLabel("Create New User");
		titleRegiLabel.setFont(Design.bigTitleFont);
		titleRegiLabel.setForeground(Design.textColor);
		titleRegiPanel.add(titleRegiLabel);
		
		errorRegiLabel = new JLabel("This user already exists.");
		errorRegiLabel.setFont(Design.bigInfoFont);
		errorRegiLabel.setForeground(Design.errorColor);
		errorRegiLabel.setVisible(false);
		errorRegiPanel.add(errorRegiLabel);
		
		String [] regiText = {"Full Name", "E-Mail"}; 
		int regiTFWidth = Design.getComponentWidth(regiText, tfFontSize)- 30;
		
		//Full Name TextField.
		tfFullName = new JTextField(regiText[0]);
		Design.createTFHomePage(tfFullName, regiTFWidth);
		fullNamePanel.add(tfFullName);
		
		tfEmail = new JTextField(regiText[1]);
		Design.createTFHomePage(tfEmail, regiTFWidth);
		emailPanel.add(tfEmail);
		
		//Pass PasswordField.
		pfPassRegi = new JPasswordField("Password");
		Design.createTFHomePage(pfPassRegi, regiTFWidth);
		pfPassRegi.setEchoChar((char)0);
		passRegiPanel.add(pfPassRegi);
		
		//Pass PasswordField.
		pfRePassRegi = new JPasswordField("Re-Enter Password");
		Design.createTFHomePage(pfRePassRegi, regiTFWidth);
		pfRePassRegi.setEchoChar((char)0);
		rePassRegiPanel.add(pfRePassRegi);
		
		btnContinue = new JButton("Continue as New User");
		Design.createBigInterfaceButton(btnContinue);
		regiBtnPanel.add(btnContinue);

		DropMenu dropMenu = new DropMenu(width, height, this);
		dropMenu.initFrame();
		HomePageHandler homePageHand = new HomePageHandler(this, dropMenu);
		homePageHand.actionHandler();
		
		//Right Glue spacing.
		//rightPanel.add(Box.createHorizontalGlue());
		
		
		setVisible(true);
		pack();
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}

}
