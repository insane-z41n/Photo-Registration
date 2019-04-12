package pr.z41n.handler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import pr.z41n.accessories.ProgramInfo;
import pr.z41n.admin.Admin;
import pr.z41n.admin.Login;
import pr.z41n.admin.Settings;
import pr.z41n.database.DBConnection;

public class LoginHandler {

	private Login login;
	
	public LoginHandler(Login login) {
		this.login = login;
	}
	
	public void actionHandler() {
		login.btnLogin.addActionListener(a -> verify());
		login.btnCancel.addActionListener(a -> cancel());
		login.createLabel.addMouseListener(new LabelClickCreate(login));
	}
	
	//btnLogin Login
	private void verify() {
		System.out.println("Login Button Pressed\n");
		
		if(login.usernameTF.getText().isEmpty() || login.pfLogin.getPassword().length < 1) {
			//Show error label.
			login.incorrectLabel.setVisible(true);
			login.incorrectLabel.setText("*Please enter the username and password");
			login.usernameTF.setText(null);
			login.pfLogin.setText(null);
		}
		else {
			String username = login.usernameTF.getText();
			String password = String.valueOf(login.pfLogin.getPassword());
			
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			String query = "SELECT * FROM `admin` WHERE `admin_user` = ? AND `admin_pass` = ?";
			
			try {
				con = DBConnection.getConnection();
				ps = con.prepareStatement(query);
				ps.setString(1, username);
				ps.setString(2, password);
				rs = ps.executeQuery();
				//Authenticated.
				if(rs.next()) {
					//Go to next frame.
					System.out.println("Correct Password!\n");
					login.incorrectLabel.setVisible(false);
					int id = rs.getInt("admin_id");
					ProgramInfo.setAdminID(id);
					System.out.println("The admin_id is: " + id);
					
					Settings settings = new Settings(login.width, login.height);
					settings.initFrame();
					login.setVisible(false);
					login.dispose();
				}
				//Failed Log in.
				else {
					System.out.println("Incorrect Password!\n");
					//Show error label.
					login.incorrectLabel.setVisible(true);
					login.incorrectLabel.setText("*The Username or Password cannot be found.");
					login.usernameTF.setText(null);
					login.pfLogin.setText(null);
				}
			} catch (SQLException e) {
				login.incorrectLabel.setVisible(true);
				login.incorrectLabel.setText("*An Error occured connecting to server.");
				login.usernameTF.setText(null);
				login.pfLogin.setText(null);
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
		
	
	}
	
	private void cancel() {
		login.setVisible(false);
		login.dispose();
		System.exit(0);
	}
	
	class LabelClickCreate extends MouseAdapter implements MouseListener {
		
		private JFrame frame;
		public LabelClickCreate(JFrame frame) {
			this.frame = frame;
		}
		
		public void mousePressed(MouseEvent me) {
			Admin admin= new Admin(login.width, login.height);
			admin.initFrame();
			frame.setVisible(false);
			frame.dispose();
		}
	}

}
