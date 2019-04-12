package pr.z41n.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pr.z41n.accessories.Design;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.AdminHandler;

@SuppressWarnings("serial")
public class Admin extends JFrame implements FrameObject{
	
	private JPanel adminTitlePanel;
	private JPanel adminButtonsPanel;
	private JPanel adminInterfacePanel;
	private JPanel userEmailPanel;
	private JPanel passPanel;
	private JPanel rePassPanel;
	private JPanel bottomPanel;
	private JPanel labelPanel;
	private JPanel errorPanel;
	
	private JLabel adminTitleLabel;
	
	private JLabel emailLabel;
	private JLabel passLabel;
	private JLabel rePassLabel;
	public JLabel errorLabel;
	public JLabel loginLabel;
	
	public JTextField emailTF;
	public JPasswordField passPF;
	public JPasswordField rePassPF;
	
	public JButton btnCancel;
	public JButton btnRegister;
	
	public int width, height;
	
	private int adminWidth, adminHeight;
	
	public Admin(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		adminWidth = (int) (width/3);
		adminHeight = (int) (height/2);
		
		System.out.println("Admin Frame Width: " + adminWidth);
		System.out.println("Admin Frame Height: " + adminHeight + "\n");
		
		//Set Size of Installer Frame to half of screen size.
		setSize(adminWidth, adminHeight);
		
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
		((JComponent) getContentPane()).setBorder(new EmptyBorder(20,20,20,20));
		
		//Top Admin Panel.
		int panelHeight = adminHeight/4;
		adminTitlePanel = new JPanel();
		adminTitlePanel.setBackground(Design.primary);
		adminTitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//adminTitlePanel.setPreferredSize(new Dimension(adminWidth, panelHeight));
		add(adminTitlePanel, BorderLayout.NORTH);
		
		//Title Admin Label.
		adminTitleLabel = new JLabel("Admin Registration");
		adminTitleLabel.setFont(Design.titleFont);
		adminTitleLabel.setForeground(Design.textColor);
		adminTitlePanel.add(adminTitleLabel, BorderLayout.CENTER);
		
		//Center Admin Panel.
		int interfaceHeight = adminHeight - panelHeight;
		adminInterfacePanel = new JPanel();
		adminInterfacePanel.setBackground(Design.primary);
		adminInterfacePanel.setLayout(new BoxLayout(adminInterfacePanel, BoxLayout.PAGE_AXIS));
		adminInterfacePanel.setPreferredSize(new Dimension(adminWidth, interfaceHeight));
		add(adminInterfacePanel, BorderLayout.CENTER);
		
		//Panel error
		errorPanel = new JPanel();
		errorPanel.setBackground(Design.primary);
		errorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		errorPanel.setPreferredSize(new Dimension(adminWidth, Design.infoFont.getSize()));
		adminInterfacePanel.add(errorPanel);
		
		//Label error.
		errorLabel = new JLabel("*Please fill out the indicated fields.");
		errorLabel.setForeground(Design.errorColor);
		errorLabel.setFont(Design.infoFont);
		errorLabel.setPreferredSize(new Dimension(adminWidth, errorLabel.getFont().getSize()));
		errorLabel.setVisible(false);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorPanel.add(errorLabel);
		
		int compWidth = adminWidth - adminWidth/2;
		userEmailPanel = new JPanel();
		userEmailPanel.setBackground(Design.primary);
		userEmailPanel.setLayout(new FlowLayout());
		adminInterfacePanel.add(userEmailPanel);
		
		emailLabel = new JLabel("E-Mail: ");
		emailLabel.setFont(Design.bigInfoFont);
		emailLabel.setForeground(Design.textColor);
		userEmailPanel.add(emailLabel);
		
		emailTF = new JTextField();
		Design.createAdminTextField(emailTF, compWidth);
		userEmailPanel.add(emailTF);

		passPanel = new JPanel();
		passPanel.setBackground(Design.primary);
		passPanel.setLayout(new FlowLayout());
		adminInterfacePanel.add(passPanel);
		
		passLabel = new JLabel("Password: ");
		passLabel.setFont(Design.bigInfoFont);
		passLabel.setForeground(Design.textColor);
		passLabel.setBackground(Color.GREEN);
		passPanel.add(passLabel);
		
		passPF = new JPasswordField();
		Design.createAdminTextField(passPF, compWidth);
		passPanel.add(passPF);
		
		rePassPanel = new JPanel();
		rePassPanel.setBackground(Design.primary);
		rePassPanel.setLayout(new FlowLayout());
		adminInterfacePanel.add(rePassPanel);
		
		rePassLabel = new JLabel("Re-Enter Password: ");
		rePassLabel.setFont(Design.bigInfoFont);
		rePassLabel.setForeground(Design.textColor);
		rePassPanel.add(rePassLabel);
		
		rePassPF = new JPasswordField();
		Design.createAdminTextField(rePassPF, compWidth);
		rePassPanel.add(rePassPF);
		
		String [] interfaceLabels = {emailLabel.getText(), passLabel.getText(), rePassLabel.getText()};
		int interfaceLabelWidth = Design.getComponentWidth(interfaceLabels, emailLabel.getFont().getSize());
		emailLabel.setPreferredSize(new Dimension(interfaceLabelWidth, emailLabel.getFont().getSize()));
		passLabel.setPreferredSize(new Dimension(interfaceLabelWidth, emailLabel.getFont().getSize()));
		rePassLabel.setPreferredSize(new Dimension(interfaceLabelWidth, emailLabel.getFont().getSize()));
		
		//Bottom Vertical glue.
		//adminInterfacePanel.add(Box.createVerticalGlue());
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Design.secondary);
		bottomPanel.setLayout(new BorderLayout());
		//bottomPanel.setPreferredSize(new Dimension(adminWidth, adminHeight/3));
		add(bottomPanel, BorderLayout.SOUTH);
		
		adminButtonsPanel = new JPanel();
		adminButtonsPanel.setBackground(Design.primary);
		adminButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(adminButtonsPanel, BorderLayout.NORTH);
		
		//Cancel Button
		btnCancel = new JButton("Cancel");
		Design.createInterfaceButton(btnCancel);
		adminButtonsPanel.add(btnCancel);
		
		//Install Button
		btnRegister = new JButton("Register");
		Design.createInterfaceButton(btnRegister);
		adminButtonsPanel.add(btnRegister);
		
		labelPanel = new JPanel();
		labelPanel.setBackground(Design.primary);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
		bottomPanel.add(labelPanel, BorderLayout.SOUTH);
		
		labelPanel.add(Box.createVerticalGlue());
		
		loginLabel = new JLabel("click here to login");
		Design.createLabelClickAdmin(loginLabel, adminWidth);
		loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPanel.add(loginLabel);
		
		AdminHandler adminHand = new AdminHandler(this);
		adminHand.actionHandler();
		//labelPanel.add(Box.createHorizontalGlue());
		pack();
		//Set Visible
		setVisible(true);
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}

}
