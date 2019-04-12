package pr.z41n.handler;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;

import pr.z41n.accessories.ProgramInfo;
import pr.z41n.admin.Settings;
import pr.z41n.database.DBConnection;
import pr.z41n.homepage.HomePage;

public class SettingsHandler {
	
	private Settings settings;
	
	public SettingsHandler (Settings settings) {
		
		this.settings = settings;
	}
	
	public void actionHandler() {
		//Buttons.
		settings.btnBrowse1.addActionListener(a -> setLoadDirectory());
		//settings.btnBrowse2.addActionListener(a -> setSaveDirectory());
		settings.btnNext.addActionListener(a -> finishSettings());
		settings.btnReset.addActionListener(a -> reset());
		
		//TextFields.
		settings.cbPhoto.addActionListener(a -> setPhotoSettings());
		settings.cbEmail.addActionListener(a -> setEmailSettings());
		//settings.cbAddress.addActionListener(a -> setAddressSettings());
	}
	
	//btnBrowse1 LOAD Settings.
	private void setLoadDirectory() {
		System.out.println("Browse Button 1 (Load) Pressed\n");
		//Save String path.
		//OPEN DIALOG
		JFileChooser loadChooser = new JFileChooser();
		loadChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int status = loadChooser.showOpenDialog(null);
		
		if(status == JFileChooser.APPROVE_OPTION) {
			String loadPath = loadChooser.getSelectedFile().getPath();
			settings.tfLoadDir.setText(loadPath);
		}
	}
	
//	//btnBrowse2 SAVE Settings.
//	private void setSaveDirectory() {
//		System.out.println("Browse Button 2 (Save) Pressed\n");
//		//Save String path.
//		//SAVE DIALOG
//		JFileChooser saveChooser = new JFileChooser();
//		saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		int status = saveChooser.showSaveDialog(null);
//		
//		if(status == JFileChooser.APPROVE_OPTION) {
//			String savePath = saveChooser.getSelectedFile().getPath();
//			settings.tfSaveDir.setText(savePath);
//		}
//	}
	
	//btnNext Settings.
	private void finishSettings() {
		System.out.println("Next Button Pressed\n");
		
		//Check if all needed fields are filled out.
		//load directory empty
		//save directory empty
		//computer name empty
		
		//Change this to !isComplete not isComplete to actually work properly instead of skipping check.
		if(!isComplete()) {
			settings.errorLabel.setVisible(true);
		}
		else {
			settings.errorLabel.setVisible(false);
			String imagePath = settings.tfLoadDir.getText();
			//String savePath = settings.tfSaveDir.getText();
			String compName = settings.tfCompName.getText();
			
			ProgramInfo.setImagePath(imagePath);
			//ProgramInfo.setSavePath(savePath);
			ProgramInfo.setCompName(compName);
			ProgramInfo.setPrintSelected(settings.cbPhoto.isSelected());
			ProgramInfo.setEmailSelected(settings.cbEmail.isSelected());
			//ProgramInfo.setAddressSelected(settings.cbAddress.isSelected());
			//ProgramInfo.setRecieptSelected(settings.cbReceipt.isSelected());
			if(settings.cbPhoto.isSelected()) {
				ProgramInfo.setPrintPrice(Double.parseDouble(settings.tfPrintPrice.getText()));
			}
			if(settings.cbEmail.isSelected()) {
				ProgramInfo.setEmailPrice(Double.parseDouble(settings.tfEmailPrice.getText()));
			}
			
			System.out.println("User photos will be loaded from: " + ProgramInfo.getImagePath());
			System.out.println("The computer name: " + ProgramInfo.getCompName() + "\n");
			
			setSettingsTuple();
			
			HomePage homepage = new HomePage(settings.width, settings.height);
			homepage.initFrame();
			settings.setVisible(false);
			settings.dispose();
			
			//Allow the program to start using the image and save paths.
			ProgramInfo.setUpdateStart(true);
		}
	}
	
	//btnReset Settings.
	private void reset() {
		System.out.println("Reset Button Pressed\n");
		//This function will reset the filled information.
		
		ProgramInfo.setCompName("");
		ProgramInfo.setImagePath("");
		ProgramInfo.setPrintSelected(false);
		ProgramInfo.setEmailSelected(false);
		ProgramInfo.setPrintPrice(0);
		ProgramInfo.setEmailPrice(0);
		updateTuple();
		
		ProgramInfo.setUserID(0);
		ProgramInfo.setFullName("");
		ProgramInfo.setEmail("");
		ProgramInfo.setSelectedEmails(new ArrayList<String>());
		ProgramInfo.setPrintsMap(new LinkedHashMap<String, Integer>());
		
		//Reset the Text Fields and Check boxes to blank.
		settings.tfLoadDir.setText("");
		settings.tfCompName.setText("");
		settings.cbPhoto.setSelected(false);
		settings.cbEmail.setSelected(false);
		settings.tfPrintPrice.setText("00.00");
		settings.tfPrintPrice.setEnabled(false);
		settings.tfEmailPrice.setText("00.00");
		settings.tfEmailPrice.setEnabled(false);
	}
	
	//cbPhoto Settings.
	private void setPhotoSettings() {
		//Enable price 1 tf.
		if(settings.cbPhoto.isSelected()) {
			System.out.println("Check Box Print Photos Selected\n");
			
			settings.tfPrintPrice.setEnabled(true);
			settings.tfPrintPrice.setText("00.00");
		}
		else {
			System.out.println("Check Box Print Photos Unselected\n");
			
			settings.tfPrintPrice.setEnabled(false);
			settings.tfPrintPrice.setText("");
		}
	}
	
	//cbEmail Settings.
	private void setEmailSettings() {
		//Enable price 2 tf.
		if(settings.cbEmail.isSelected()) {
			System.out.println("Check Box E-Mail Selected\n");
			
			settings.tfEmailPrice.setEnabled(true);
			settings.tfEmailPrice.setText("00.00");
		}
		else {
			System.out.println("Check Box E-Mail Unselected\n");
			
			settings.tfEmailPrice.setEnabled(false);
			settings.tfEmailPrice.setText("");
		}
	}
	
//	//cbAddress Settings.
//	private void setAddressSettings() {
//		//May not be needed.
//		if(settings.cbAddress.isSelected()) {
//			System.out.println("Check Box Address Selected\n");
//		}
//		else {
//			System.out.println("Check Box Address Unselected\n");
//		}
//	}
	
	//Check to see if complete is done.
	private boolean isComplete() {
		
		boolean complete = true;		
	
		if(settings.tfLoadDir.getText().isEmpty()) {
			settings.tfLoadDir.setBorder(new LineBorder(Color.RED));
			complete = false;
		}
//		if(settings.tfSaveDir.getText().isEmpty()) {
//			settings.tfSaveDir.setBorder(new LineBorder(Color.RED));
//			complete = false;
//		}
		if(settings.tfCompName.getText().isEmpty()) {
			settings.tfCompName.setBorder(new LineBorder(Color.RED));
			complete = false;
		}
		//price Print empty if photo is selected
		if(settings.tfPrintPrice.getText().isEmpty() && settings.cbPhoto.isSelected()) {
			settings.tfPrintPrice.setBorder(new LineBorder(Color.RED));
			complete = false;
		}
		//price email empty if email is selected
		if(settings.tfEmailPrice.getText().isEmpty() && settings.cbEmail.isSelected()) {
			settings.tfEmailPrice.setBorder(new LineBorder(Color.RED));	
			complete = false;
		}

		return complete;
	}
	
	//Sets Tuple in database. Method occurs in finishSettings action method.
	private void setSettingsTuple() {
		Connection conCheck = null;
		PreparedStatement psCheck = null;
		ResultSet rsCheck = null;
		
		String queryCheck = "SELECT*FROM `settings` WHERE `admin_id` = " + ProgramInfo.getAdminID();
		
		try {
			conCheck = DBConnection.getConnection();
			psCheck = conCheck.prepareStatement(queryCheck);
			rsCheck = psCheck.executeQuery();
			//If admin id exists.
			if(rsCheck.next()) {
				System.out.println("This admin is an old user");
				
				updateTuple();
			}
			//if admin is new user.
			else {
				System.out.println("This admin is a new user");
				
				insertTuple();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateTuple() {
		Connection conExists = null;
		PreparedStatement psExists = null;
		String existsQuery = "UPDATE `settings` SET `comp_name` = ?, `image_path` = ?, `print_opt` = ?, `email_opt` = ?, `print_price` = ?, `email_price` = ? WHERE `admin_id` = " + ProgramInfo.getAdminID();
		
		try {
			conExists = DBConnection.getConnection();
			psExists = conExists.prepareStatement(existsQuery);
			psExists.setString(1, ProgramInfo.getCompName());
			psExists.setString(2, ProgramInfo.getImagePath());
			psExists.setBoolean(3, ProgramInfo.getPrintSelected());
			psExists.setBoolean(4, ProgramInfo.getEmailSelected());
			psExists.setDouble(5, ProgramInfo.getPrintPrice());
			psExists.setDouble(6, ProgramInfo.getEmailPrice());
			if(psExists.executeUpdate() > 0) {
				System.out.println("Data Updated");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conExists != null)
					conExists.close();
				if(psExists != null)
					psExists.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void insertTuple() {
		Connection conNew = null;
		PreparedStatement psNew = null;
		String newQuery = "INSERT INTO `settings` (`comp_name`, `image_path`, `print_opt`, `email_opt`, `print_price`, `email_price`, `admin_id`) VALUES (?,?,?,?,?,?,?)";
		
		try {
			conNew = DBConnection.getConnection();
			psNew = conNew.prepareStatement(newQuery);
			psNew.setString(1, ProgramInfo.getCompName());
			psNew.setString(2, ProgramInfo.getImagePath());
			psNew.setBoolean(3, ProgramInfo.getPrintSelected());
			psNew.setBoolean(4, ProgramInfo.getEmailSelected());
			psNew.setDouble(5, ProgramInfo.getPrintPrice());
			psNew.setDouble(6, ProgramInfo.getEmailPrice());
			psNew.setInt(7, ProgramInfo.getAdminID());
			
			if(psNew.executeUpdate() > 0) {
				System.out.println("Data Inserted");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(conNew != null)
					conNew.close();
				if(psNew != null)
					psNew.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	

}
