package pr.z41n.admin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pr.z41n.accessories.Design;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.LoginHandler;

@SuppressWarnings("serial")
public class Login extends JFrame implements FrameObject {
	
	private JPanel loginTitlePanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JPanel errorPanel;
	private JPanel interfacePanel;
	private JPanel buttonPanel;
	private JPanel regiPanel;
	
	private int loginWidth;
	private int loginHeight;
	
	public int width;
	public int height;
	
	private JLabel loginTitleLabel;
	public JLabel incorrectLabel;
	public JLabel passwordLabel;
	public JLabel usernameLabel;
	public JLabel createLabel;
	
	public JTextField usernameTF;
	public JPasswordField pfLogin;
	
	public JButton btnLogin;
	public JButton btnCancel;
	
	private EmptyBorder loginBorder = new EmptyBorder(10, 10, 0, 10);
	
	public Login(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		loginWidth = (int) width/3;
		loginHeight = (int) height/3;
		
		System.out.println("Login Frame Width: " + loginWidth);
		System.out.println("Login Frame Height: " + loginHeight + "\n");
		
		//Set size of Login frame to a 3rd of screen size.
		setSize(loginWidth, loginHeight);
		
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
		
		//Set Color to Primary.
		getContentPane().setBackground(Design.primary);
		
		//Login Title Panel.
		loginTitlePanel = new JPanel();
		loginTitlePanel.setBackground(Design.primary);
		loginTitlePanel.setBorder(loginBorder);
		loginTitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(loginTitlePanel, BorderLayout.NORTH);
		
		//Login Title Label.
		loginTitleLabel = new JLabel("Admin Login");
		loginTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
		loginTitleLabel.setForeground(Design.textColor);
		loginTitleLabel.setFont(Design.titleFont);
		loginTitlePanel.add(loginTitleLabel);
		
		interfacePanel = new JPanel();
		interfacePanel.setBackground(Design.primary);
		interfacePanel.setLayout(new BoxLayout(interfacePanel, BoxLayout.PAGE_AXIS));
		interfacePanel.setBorder(new EmptyBorder(20,20,0,20));
		add(interfacePanel, BorderLayout.CENTER);
		
		errorPanel = new JPanel();
		errorPanel.setBackground(Design.primary);
		errorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		errorPanel.setPreferredSize(new Dimension(loginWidth, Design.infoFont.getSize() + 15));
		interfacePanel.add(errorPanel);
		
		//Label that appears only if the password entered is incorrect.
		incorrectLabel = new JLabel();
		incorrectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		incorrectLabel.setForeground(Design.errorColor);
		incorrectLabel.setFont(Design.infoFont);
		incorrectLabel.setPreferredSize(new Dimension(loginWidth, incorrectLabel.getFont().getSize() + 5));
		incorrectLabel.setVisible(false);
		errorPanel.add(incorrectLabel);
		
		//Login Username Panel.
		usernamePanel = new JPanel();
		usernamePanel.setBackground(Design.primary);
		usernamePanel.setBorder(loginBorder);
		usernamePanel.setLayout(new FlowLayout());
		interfacePanel.add(usernamePanel);
	
		//Login username Label.
		int compWidth = (int) (loginWidth - (loginWidth/2));
		usernameLabel = new JLabel("E-Mail: ");
		usernameLabel.setForeground(Design.textColor);
		usernameLabel.setFont(Design.bigInfoFont);
		usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		//usernameLabel.setPreferredSize(new Dimension(labelWidth, usernameLabel.getFont().getSize()));
		usernamePanel.add(usernameLabel);
		
		//Login Username TextField.
		usernameTF = new JTextField();
		Design.createAdminTextField(usernameTF, compWidth);
		usernamePanel.add(usernameTF);
		
		//Login Password Panel.
		int passHeight = loginHeight/4;
		passwordPanel = new JPanel();
		passwordPanel.setBackground(Design.primary);
		passwordPanel.setPreferredSize(new Dimension(loginWidth, passHeight));
		passwordPanel.setBorder(loginBorder);
		passwordPanel.setLayout(new FlowLayout());
		interfacePanel.add(passwordPanel);
		
		//Login PasswordField.
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setForeground(Design.textColor);
		passwordLabel.setFont(Design.bigInfoFont);
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordPanel.add(passwordLabel);
		
		pfLogin = new JPasswordField();
		Design.createAdminTextField(pfLogin, compWidth);
		passwordPanel.add(pfLogin, BorderLayout.SOUTH);
		
		//Set Preferred Size Labels.
		String [] labels = {usernameLabel.getText(), passwordLabel.getText()};
		int labelWidth = Design.getComponentWidth(labels, usernameLabel.getFont().getSize());
		passwordLabel.setPreferredSize(new Dimension(labelWidth, usernameLabel.getFont().getSize()));
		usernameLabel.setPreferredSize(new Dimension(labelWidth, usernameLabel.getFont().getSize()));
		
		interfacePanel.add(Box.createVerticalStrut(loginHeight/15));
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Design.primary);
		buttonPanel.setLayout(new FlowLayout());
		interfacePanel.add(buttonPanel);

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(Design.infoFont);
		Design.createInterfaceButton(btnCancel);
		buttonPanel.add(btnCancel);
		
		//Login Button.
		btnLogin = new JButton("Login");
		btnLogin.setFont(Design.infoFont);
		Design.createInterfaceButton(btnLogin);
		buttonPanel.add(btnLogin);
		
		interfacePanel.add(Box.createVerticalStrut(loginHeight/10));
		
		regiPanel = new JPanel();
		regiPanel.setBackground(Design.primary);
		regiPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		interfacePanel.add(regiPanel);
		
		createLabel = new JLabel("register new account");
		Design.createLabelClickAdmin(createLabel, loginWidth);
		regiPanel.add(createLabel);
		
		interfacePanel.add(Box.createVerticalStrut(loginHeight/10));
		
		LoginHandler loginHand = new LoginHandler(this);
		loginHand.actionHandler();
		
		//Set Visible
		setVisible(true);
		pack();
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}

}
