package pr.z41n.handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import pr.z41n.admin.Admin;
import pr.z41n.admin.Login;
import pr.z41n.admin.Settings;
import pr.z41n.database.DBConnection;
import pr.z41n.popup.PopUpFrame;

public class AdminHandler {
	
	private Admin admin;
	
	public AdminHandler(Admin admin) {
		this.admin = admin;
	}
	
	public void actionHandler() {
		admin.btnCancel.addActionListener(a -> cancel());
		admin.btnRegister.addActionListener(a -> register());
		admin.loginLabel.addMouseListener(new LabelClickCreate(admin));
	}
	
	private void cancel() {
		admin.setVisible(false);
		admin.dispose();
		System.exit(0);
	}
	
	private void register() {
		String email = admin.emailTF.getText();
		String pass = String.valueOf(admin.passPF.getPassword());
		String rePass = String.valueOf(admin.rePassPF.getPassword());
		
		boolean done = true;
		
		if(email.equals("")) {
			System.out.println("No email entered");
			done = false;
		}
		else if(pass.equals("")) {
			System.out.println("No pass entered");
			done = false;
		}
		else if(!pass.equals(rePass)) {
			System.out.println("Please re-enter your password");
			done = false;
		}
		
		if(!done) {
			admin.errorLabel.setVisible(true);
			admin.errorLabel.setText("*Please fill out all information.");
		}
		else {
			System.out.println("admin_user: " + email);
			System.out.println("admin_pass: " + pass);
			if(insertAdminInfoStatement(email, pass)) {
				Settings settings = new Settings(admin.width, admin.height);
				PopUpFrame popUp = new PopUpFrame(admin.width, admin.height, "Would You like to login?", admin, settings);
				popUp.initFrame();
			} else {
				admin.errorLabel.setVisible(true);
				admin.errorLabel.setText("Could not add user.");
			}
			
			
		}
	}
	
	private boolean insertAdminInfoStatement(String email, String pass) {
		
		boolean addAdmin = true;
		if(checkExists(email)) {
			admin.errorLabel.setVisible(true);
			admin.errorLabel.setText("*User E-Mail is already registered.");
		}
		else {
			String query = "INSERT INTO `admin` (`admin_user`, `admin_pass`) VALUES (?,?)";

			Connection con = null;
			PreparedStatement ps = null;
			
			try {
				con = DBConnection.getConnection();
				ps = con.prepareStatement(query);			
				ps.setString(1, email);
				ps.setString(2, pass);
				
				if(ps.executeUpdate() > 0) {
					System.out.println("New User Added");
					addAdmin = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					
				if(con !=null)
					con.close();
				if(ps != null)
					ps.close();
				
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return addAdmin;
	}
	
	private boolean checkExists(String email) {
		boolean exists = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT `admin_user` FROM `admin`";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("admin_user").equals(email)) {
					exists = true;
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

	class LabelClickCreate extends MouseAdapter implements MouseListener {
		
		private JFrame frame;
		public LabelClickCreate(JFrame frame) {
			this.frame = frame;
		}
		
		public void mousePressed(MouseEvent me) {
			Login login = new Login(admin.width, admin.height);
			login.initFrame();
			frame.setVisible(false);
			frame.dispose();
		}
	}

	

}
