package pr.z41n.handler;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pr.z41n.accessories.DropMenu;
import pr.z41n.accessories.ProgramInfo;
import pr.z41n.database.DBConnection;
import pr.z41n.homepage.HomePage;
import pr.z41n.photos.Photos;

public class HomePageHandler {

	private HomePage homepage;
	private DropMenu dropMenu;
	
	public HomePageHandler(HomePage homepage, DropMenu dropMenu) {
		this.homepage = homepage; 
		this.dropMenu = dropMenu;
	}
	
	public void actionHandler() {
		
		//Buttons.
		homepage.btnMenu.addActionListener(a -> openMenu());
		homepage.btnLogin.addActionListener(a -> userLogin());
		homepage.btnContinue.addActionListener(a -> continueNewUser());
		
		//TextFields Login side (LEFT SIDE).
		homepage.tfUserLog.addFocusListener(new TextFieldFocus(homepage.tfUserLog, "E-Mail"));
		homepage.pfPassLog.addFocusListener(new PasswordFieldFocus(homepage.pfPassLog, "Password"));
		
		//TextFields Register side (RIGHT SIDE).
		homepage.tfFullName.addFocusListener(new TextFieldFocus(homepage.tfFullName, "Full Name"));
		homepage.tfEmail.addFocusListener(new TextFieldFocus(homepage.tfEmail, "E-Mail"));
		homepage.pfPassRegi.addFocusListener(new PasswordFieldFocus(homepage.pfPassRegi, "Password"));
		homepage.pfRePassRegi.addFocusListener(new PasswordFieldFocus(homepage.pfRePassRegi, "Re-Enter Password"));

	}
	
	private void openMenu() {
		System.out.println("Menu Button pressed\n");
		dropMenu.setVisible(true);
	}
	
	private void userLogin() {
		//Check to see if user exists in Where primary Key computer name.
		System.out.println("Login Button Pressed\n");
		String email = homepage.tfUserLog.getText();
		String pass = String.valueOf(homepage.pfPassLog.getPassword());
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM `users` WHERE `user_email` = ? AND `user_pass` = ?";
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println("User was Found");
				ProgramInfo.setUserID(rs.getInt("user_id"));
				homepage.errorLogLabel.setVisible(false);
				nextFrame();
			}
			else {
				System.out.println("User was not found");
				homepage.errorLogLabel.setVisible(true);
			}
		} catch(SQLException e) {
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
		
		dropMenu.setVisible(false);
	}
	
	private void continueNewUser() {
		//Add information to database at Primary Key computer name.
		System.out.println("Continue Pressed\n");
		String fullName = homepage.tfFullName.getText();
		String email = homepage.tfEmail.getText();
		String pass = String.valueOf(homepage.pfPassRegi.getPassword());
		String rePass = String.valueOf(homepage.pfRePassRegi.getPassword());
		
		boolean entered = true;
		if(fullName.equals("") || fullName.equals("Full Name")) {
			entered = false;
		}
		else if(email.equals("") || email.equals("E-Mail")) {
			entered = false;
		}
		else if(pass.equals("") || pass.equals("Password")) {
			entered = false;
		}
		else if(!pass.equals(rePass)){
			entered = false;
		}
		if(!entered) {
			homepage.errorRegiLabel.setText("Please fill out all the indicated fields");
			homepage.errorRegiLabel.setVisible(true);
		}
		else {
			System.out.println("full_name: " + fullName);
			System.out.println("user_email: " + email);
			System.out.println("user_pass: " + pass);
			
			String queryCheck = "SELECT * FROM `users` WHERE `user_email`= ?";
			Connection conCheck = null;
			PreparedStatement psCheck = null;
			ResultSet rsCheck = null;
			try {
				conCheck = DBConnection.getConnection();
				psCheck = conCheck.prepareStatement(queryCheck);
				psCheck.setString(1, email);
				rsCheck = psCheck.executeQuery();
				if(rsCheck.next()) {
					System.out.println("User email has already been registered");
					homepage.errorRegiLabel.setText("This E-Mail is already registered.");
					homepage.errorRegiLabel.setVisible(true);
				}
				else {
					if(insertClientInfoStatement(fullName, email, pass)) {
						homepage.errorRegiLabel.setVisible(false);
						setupUserID(email, pass);
						nextFrame();
					}
					
					else {
						homepage.errorRegiLabel.setText("An error occured while registering.");
						homepage.errorRegiLabel.setVisible(true);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(conCheck != null)
						conCheck.close();
					if(psCheck != null)
						psCheck.close();
					if(rsCheck != null)
						rsCheck.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		dropMenu.setVisible(false);
		
	}
	
	private boolean insertClientInfoStatement(String fullName, String email, String pass) {
		boolean addUser = false;
		String query = "INSERT INTO `users` (`full_name`, `user_email`, `user_pass`) VALUES(?,?,?)";
		
		Connection con = null;
		PreparedStatement ps = null;
		
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, fullName);
			ps.setString(2, email);
			ps.setString(3, pass);
			
			if(ps.executeUpdate() > 0) {
				System.out.println("New User Added");
				addUser = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null)
					con.close();
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return addUser;
	}
	
	private void setupUserID(String username, String pass) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM `users` WHERE `user_email` = ? AND `user_pass` = ?";
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				ProgramInfo.setUserID(rs.getInt("user_id"));
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
		
		
	}
	
	private void nextFrame() {
		homepage.setVisible(false);
		homepage.dispose();
		Photos photos = new Photos(homepage.width, homepage.height);
		photos.initFrame();
		
	}
	
	
	//FOCUS LISTENER CLASSES+++++++++++++++++++++++++++++++++++
	//Text field focus listener actions.
	class TextFieldFocus implements FocusListener {
		
		private JTextField tf;
		private String message;
		
		public TextFieldFocus(JTextField tf, String message) {
			this.tf = tf;
			this.message = message;
		}

		//Clears message from text field.
		public void focusGained(FocusEvent e) {
			//System.out.println("Focus Gained\n");
			if(tf.getText().equals(message)) {
				tf.setText("");
			}
				
		}
		//Returns message to text field if user has left blank.
		public void focusLost(FocusEvent e) {
			//System.out.println("Focus Lost\n");
			if(tf.getText().equals("") || tf.getText().equals(" ")) {
				tf.setText(message);
			}
		}
	}
	
	//Password field focus listener actions.
	class PasswordFieldFocus implements FocusListener {
		
		private JPasswordField pf;
		private String message;
		private char dot = '\u25CF';
		
		public PasswordFieldFocus(JPasswordField pf, String message) {
			this.pf = pf;
			this.message = message;
		}
		//Clears message from password field.
		public void focusGained(FocusEvent f) {
			//System.out.println("Focus Gained\n");
			if(String.valueOf(pf.getPassword()).equals(message)) {
				pf.setText("");
				pf.setEchoChar(dot);
			}
		}
		//Returns message to password field if user has left blank.
		public void focusLost(FocusEvent f) {
			//System.out.println("Focus Lost\n");
			if(String.valueOf(pf.getPassword()).equals("")) {
				pf.setText(message);
				pf.setEchoChar((char)0);
			}
			
		}
		
	}
}














