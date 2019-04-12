package pr.z41n.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pr.z41n.accessories.Design;
import pr.z41n.accessories.ProgramInfo;
import pr.z41n.database.DBConnection;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.SettingsHandler;

@SuppressWarnings("serial")
public class Settings extends JFrame implements FrameObject {
	
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel settingsTitlePanel;
	private JPanel loadPanel;
	private JPanel savePanel;
	private JPanel optionsTitlePanel;
	private JPanel leftOptionsPanel;
	private JPanel rightOptionsPanel;
	private JPanel checkboxPanel;
	private JPanel compNamePanel;
	private JPanel pricePrintsPanel;
	private JPanel priceEmailPanel;
	private JPanel lowerPanel;
	private JPanel errorLabelPanel;
	private JPanel buttonsPanel;
	
	private JLabel settingsTitleLabel;
	private JLabel loadInfoLabel;
	//private JLabel saveInfoLabel;
	private JLabel optionsTitleLabel;
	private JLabel compInfoLabel;
	private JLabel printsPriceLabel;
	private JLabel pMoneySignLabel;
	private JLabel emailPriceLabel;
	private JLabel eMoneySignLabel;
	public  JLabel errorLabel;
	
	public JButton btnBrowse1;
	//public JButton btnBrowse2;
	public JButton btnNext;
	public JButton btnReset;
	
	public JTextField tfLoadDir;	
	//public JTextField tfSaveDir;	
	public JTextField tfCompName;
	public JFormattedTextField tfPrintPrice;	
	public JFormattedTextField tfEmailPrice;	
	
	public JCheckBox cbPhoto;	
	public JCheckBox cbEmail;	
	//public JCheckBox cbAddress;
	//public JCheckBox cbReceipt;	
	
	private JSeparator line;

	private int settingsWidth;
	private int settingsHeight;
	
	public int width;
	public int height;
	
	public Settings(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		settingsWidth = (int) width/2;
		settingsHeight = (int) height/2;
		
		System.out.println("Setting Frame Width: " + settingsWidth);
		System.out.println("Settings Frame Height: " + settingsHeight + "\n");
		
		//Set Size of Settings Frame to half of screen size.
		setSize(settingsWidth, settingsHeight);
		
		//Shutdown on exit.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set application in the middle.
		setLocationRelativeTo(null);
		
		//Remove exit and minimize bar
		setUndecorated(true);
				
		//Layout of this frame. 
		setLayout(new BorderLayout());
		
		//Changing the size of the frame is not allowed.
		setResizable(false);	
	}
	
	public void initFrame() {
		
		getContentPane().setBackground(Design.primary);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(0, 5, 20, 5));
		
		initVars();

		System.out.println("Computer Name in settings: " + ProgramInfo.getCompName());
		System.out.println("Computer Print boolean in settings: " + ProgramInfo.getPrintSelected());
		System.out.println("Print double: " + ProgramInfo.getPrintPrice());
		//Add Line to separate top panel and bottom panel.
		int lineWidth = settingsWidth - 10; 
		Dimension lineSize = new Dimension(lineWidth, 4);
		line = new JSeparator();
		line.setSize(lineSize);
		line.setForeground(Design.textColor);
		add(line, BorderLayout.CENTER);
		
		//Top Panel that contains all the settings for where to load and save the files.
		int halfPanelHeight = (settingsHeight/2) - line.getHeight();
		topPanel = new JPanel();
		topPanel.setBackground(Design.primary);
		topPanel.setPreferredSize(new Dimension(settingsWidth, halfPanelHeight));
		topPanel.setLayout(new GridLayout(3, 0));
		add(topPanel, BorderLayout.NORTH);
		
		//Settings Title Panel.
		settingsTitlePanel = new JPanel();
		settingsTitlePanel.setBackground(Design.primary);
		settingsTitlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		topPanel.add(settingsTitlePanel);
		
		//Settings Title Label.
		settingsTitleLabel = new JLabel("Settings");
		settingsTitleLabel.setForeground(Design.textColor);
		settingsTitleLabel.setFont(Design.titleFont);
		settingsTitlePanel.add(settingsTitleLabel);
		
		//Settings Load Panel.
		loadPanel = new JPanel();
		loadPanel.setBackground(Design.primary);
		loadPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		topPanel.add(loadPanel);
		
		//Load Label.
		loadInfoLabel = new JLabel("Select the folder from which you will load your images.");
		loadInfoLabel.setFont(Design.infoFont);
		loadInfoLabel.setForeground(Design.textColor);
		loadInfoLabel.setPreferredSize(new Dimension(settingsWidth, loadInfoLabel.getFont().getSize() + 5));
		loadPanel.add(loadInfoLabel);
		
		//Load Button browse.
		btnBrowse1 = new JButton("Browse");
		Design.createAdminButton(btnBrowse1);
		loadPanel.add(btnBrowse1);
		
		//Load TextField.+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		int settingsTextFieldWidth = settingsWidth - (btnBrowse1.getWidth() + 200);
		tfLoadDir = new JTextField();
		Design.createAdminTextField(tfLoadDir, settingsTextFieldWidth);
		loadPanel.add(tfLoadDir);
		
		//Settings Save Panel.
		savePanel = new JPanel();
		savePanel.setBackground(Design.primary);
		savePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		topPanel.add(savePanel);

		//Save Label.
//		saveInfoLabel = new JLabel("Select the folder in which you will save your clients information.");
//		saveInfoLabel.setFont(Design.infoFont);
//		saveInfoLabel.setForeground(Design.textColor);
//		saveInfoLabel.setPreferredSize(new Dimension(settingsWidth, loadInfoLabel.getFont().getSize() + 5));
//		savePanel.add(saveInfoLabel);
//
//		//Save Button Browse.
//		btnBrowse2 = new JButton("Browse");
//		Design.createAdminButton(btnBrowse2);
//		savePanel.add(btnBrowse2);
//		
//		//Save TextField.++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//		tfSaveDir = new JTextField();
//		Design.createAdminTextField(tfSaveDir, settingsTextFieldWidth);
//		savePanel.add(tfSaveDir);
		
		//Bottom panel that contains all the options for the program itself.
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Design.primary);
		bottomPanel.setPreferredSize(new Dimension(settingsWidth, halfPanelHeight));
		bottomPanel.setLayout(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);
		
		//Bottom Title Panel.
		optionsTitlePanel = new JPanel();
		optionsTitlePanel.setBackground(Design.primary);
		optionsTitlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		bottomPanel.add(optionsTitlePanel, BorderLayout.NORTH);
		
		//Bottom Title Label.
		optionsTitleLabel = new JLabel("Program Options");
		optionsTitleLabel.setForeground(Design.textColor);
		optionsTitleLabel.setFont(Design.titleFont);
		optionsTitlePanel.add(optionsTitleLabel);
		
		//Panel that contains check boxes and computer name field.
		leftOptionsPanel = new JPanel();
		leftOptionsPanel.setBackground(Design.primary);
		leftOptionsPanel.setLayout(new GridLayout(2, 0));
		bottomPanel.add(leftOptionsPanel, BorderLayout.WEST);
		
		//Checkbox Panel.
		checkboxPanel = new JPanel();
		checkboxPanel.setBackground(Design.primary);
		checkboxPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		leftOptionsPanel.add(checkboxPanel);
		
		//Checkbox Print Photos.
		cbPhoto = new JCheckBox("Print Photos");
		Design.createAdminCheckBoxes(cbPhoto);
		cbPhoto.setSelected(ProgramInfo.getPrintSelected());
		checkboxPanel.add(cbPhoto);
		
		//Checkbox E-Mail Photos.
		cbEmail = new JCheckBox("E-Mail Photos");
		Design.createAdminCheckBoxes(cbEmail);
		cbEmail.setSelected(ProgramInfo.getEmailSelected());
		checkboxPanel.add(cbEmail);
		
		//Checkbox Address.
//		cbAddress = new JCheckBox("Address");
//		Design.createAdminCheckBoxes(cbAddress);
//		cbAddress.setSelected(ProgramInfo.getAddressSelected());
//		checkboxPanel.add(cbAddress);
		
		//Checkbox Receipt.
//		cbReceipt = new JCheckBox("Print Receipt");
//		Design.createAdminCheckBoxes(cbReceipt);
//		checkboxPanel.add(cbReceipt);
		
		//Computer Name Panel.
		compNamePanel = new JPanel();
		compNamePanel.setBackground(Design.primary);
		compNamePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		leftOptionsPanel.add(compNamePanel);
		
		//Computer Name info label.
		compInfoLabel = new JLabel("Enter Computer Name: ");
		compInfoLabel.setFont(Design.infoFont);
		compInfoLabel.setForeground(Design.textColor);
		compNamePanel.add(compInfoLabel);
		
		//Computer Name info Textfield.
		tfCompName = new JTextField("");
		tfCompName.setFont(Design.smallInfoFont);
		tfCompName.setPreferredSize(new Dimension(100 , tfCompName.getFont().getSize() + 10));
		compNamePanel.add(tfCompName);
		
		//Panel that contains the price fields for Prints and E-Mail.
		rightOptionsPanel = new JPanel();
		rightOptionsPanel.setBackground(Design.primary);
		rightOptionsPanel.setLayout(new GridLayout(2, 0));
		bottomPanel.add(rightOptionsPanel, BorderLayout.EAST);
		
		//Price Prints Panel.
		pricePrintsPanel = new JPanel();
		pricePrintsPanel.setBackground(Design.primary);
		pricePrintsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		rightOptionsPanel.add(pricePrintsPanel);
		
		//Price Print Label.
		printsPriceLabel = new JLabel("Price per Prints ");
		printsPriceLabel.setFont(Design.infoFont);
		printsPriceLabel.setForeground(Design.textColor);
		pricePrintsPanel.add(printsPriceLabel);
		
		//Price $ sign Label.
		pMoneySignLabel = new JLabel("$");
		pMoneySignLabel.setFont(Design.infoFont);
		pMoneySignLabel.setForeground(Design.textColor);
		pricePrintsPanel.add(pMoneySignLabel);
		
		//Price Print Text Field.
		Dimension sizePrintTextField = new Dimension(50, tfCompName.getFont().getSize() + 10);
		tfPrintPrice = new JFormattedTextField(ProgramInfo.getPrintPrice());
		tfPrintPrice.setFont(Design.smallInfoFont);
		tfPrintPrice.setPreferredSize(sizePrintTextField);
		tfPrintPrice.setEnabled(false);
		pricePrintsPanel.add(tfPrintPrice);
		
		//Price Email Panel.
		priceEmailPanel = new JPanel();
		priceEmailPanel.setBackground(Design.primary);
		priceEmailPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		rightOptionsPanel.add(priceEmailPanel);
		
		//Price E-Mail Label.
		emailPriceLabel = new JLabel("Price per E-Mail ");
		emailPriceLabel.setFont(Design.infoFont);
		emailPriceLabel.setForeground(Design.textColor);
		priceEmailPanel.add(emailPriceLabel);
		
		//Price $ sign Label.
		eMoneySignLabel = new JLabel("$");
		eMoneySignLabel.setFont(Design.infoFont);
		eMoneySignLabel.setForeground(Design.textColor);
		priceEmailPanel.add(eMoneySignLabel);
		
		//Price E-Mail Text Field.
		tfEmailPrice = new JFormattedTextField(ProgramInfo.getEmailPrice());
		tfEmailPrice.setFont(Design.smallInfoFont);
		tfEmailPrice.setPreferredSize(new Dimension(sizePrintTextField));
		tfEmailPrice.setEnabled(false);
		priceEmailPanel.add(tfEmailPrice);
		
		//Lower Panel.
		lowerPanel = new JPanel();
		lowerPanel.setBackground(Design.primary);
		lowerPanel.setLayout(new GridLayout(2, 0));
		bottomPanel.add(lowerPanel, BorderLayout.SOUTH);
		
		//Error Label Panel.
		errorLabelPanel = new JPanel();
		errorLabelPanel.setBackground(Design.primary);
		errorLabelPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		lowerPanel.add(errorLabelPanel);
		
		//Error Label.
		errorLabel = new JLabel("*Please fill out the indicated fields.");
		errorLabel.setForeground(Design.errorColor);
		errorLabel.setFont(Design.infoFont);
		errorLabel.setVisible(false);
		errorLabel.setPreferredSize(new Dimension(settingsWidth, errorLabel.getFont().getSize() + 10));
		errorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		errorLabelPanel.add(errorLabel);
		
		//Buttons Panel.
		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Design.primary);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		lowerPanel.add(buttonsPanel);
		
		//Button Reset.
		btnReset = new JButton("Reset");
		Design.createAdminButton(btnReset);
		buttonsPanel.add(btnReset);
		
		//Button Next.
		btnNext = new JButton("Next");
		Design.createAdminButton(btnNext);
		buttonsPanel.add(btnNext);		
		
		setComponents();
		
		
		//Settings Handlers for Buttons 
		SettingsHandler settingsHand = new SettingsHandler(this);
		settingsHand.actionHandler();
	
		System.out.println(ProgramInfo.getCompName());
		
		//Set Visible.
		setVisible(true);
		
	}
	
	private void initVars() {	
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM `settings` WHERE `admin_id` = " + ProgramInfo.getAdminID();
		try {
			ps = DBConnection.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()) {
				ProgramInfo.setCompName(rs.getString("comp_name"));
				ProgramInfo.setImagePath(rs.getString("image_path"));
				ProgramInfo.setPrintSelected(rs.getBoolean("print_opt"));
				ProgramInfo.setEmailSelected(rs.getBoolean("email_opt"));;
				ProgramInfo.setPrintPrice(rs.getDouble("print_price"));
				ProgramInfo.setEmailPrice(rs.getDouble("email_price"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setComponents() {
		//Comp Name Text Field.
		if(!(ProgramInfo.getCompName() == null)) {
			tfCompName.setText(ProgramInfo.getCompName());
		}
		//Image Path Text Field.
		if(!(ProgramInfo.getImagePath() == null)) {
			tfLoadDir.setText(ProgramInfo.getImagePath());
		}
		//Print Price Text Field.
		if(ProgramInfo.getPrintSelected()) {
			tfPrintPrice.setEnabled(true);
		}else {
			tfPrintPrice.setEnabled(false);
		}
		//Email Price Text Field.
		if(ProgramInfo.getEmailSelected()) {
			tfEmailPrice.setEnabled(true);
		}
		else {
			tfEmailPrice.setEnabled(false);
		}
	
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
