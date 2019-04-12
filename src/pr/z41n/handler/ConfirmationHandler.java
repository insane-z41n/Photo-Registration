package pr.z41n.handler;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import pr.z41n.accessories.CloseDropMenu;
import pr.z41n.accessories.Design;
import pr.z41n.accessories.DropMenu;
import pr.z41n.accessories.ProgramInfo;
import pr.z41n.confirmation.Confirmation;
import pr.z41n.database.DBConnection;
import pr.z41n.homepage.HomePage;
import pr.z41n.photos.FullView;
import pr.z41n.photos.Photos;
import pr.z41n.popup.PopUpFrame;

public class ConfirmationHandler {

	private Confirmation confirm;
	private DropMenu dropMenu;
	private LinkedHashMap<String, Integer> prints;
	private ArrayList<String> emails;
	private List<String> allImages;
	private String orderId;
	private NumberFormat usFormat = NumberFormat.getCurrencyInstance();
	
	public ConfirmationHandler(Confirmation confirm, DropMenu dropMenu) {
		this.confirm = confirm;
		this.dropMenu = dropMenu;
		
		prints = new LinkedHashMap<>(ProgramInfo.getPrintsMap());
		emails = new ArrayList<>(ProgramInfo.getSelectedEmails());
		allImages = new ArrayList<>(confirm.getImageNames());
		//System.out.println("Emails Size " + emails.size());
		
	}
	
	public void actionHandler() {
		
		confirm.btnMenuCon.addActionListener(a -> openMenu());
		confirm.btnBack.addActionListener(a -> back());
		confirm.btnCheckout.addActionListener(a -> checkout());
		
		confirm.btnPrev.addActionListener(a -> prevPic());
		confirm.btnNext.addActionListener(a -> nextPic());
		
		confirm.btnView.addActionListener(a -> view());
		confirm.cbPrint.addActionListener(a -> printSelection());
		confirm.cbEmail.addActionListener(a -> emailSelection());
		confirm.spinnerPrint.addChangeListener(c -> changeNumPrints());
		
	
		for(int i = 0; i < confirm.editLabel.length; i++) {
			confirm.editLabel[i].addMouseListener(new EditListener(confirm.editLabel[i], confirm.tfInfo[i]));
		}
		
		confirm.tfInfo[0].addFocusListener(new TextFieldFocus(confirm.tfInfo[0], "Enter your Full Name"));
		confirm.tfInfo[1].addFocusListener(new TextFieldFocus(confirm.tfInfo[1], "Enter your E-Mail"));
		
		confirm.addMouseListener(new CloseDropMenu(dropMenu, confirm));
	}
	
	private void openMenu() {
		System.out.println("Menu button pressed\n");
		dropMenu.setVisible(true);
	}
	
	private void back() {
		System.out.println("Back Button pressed\n");
		Photos photos = new Photos(confirm.width, confirm.height);
		photos.initFrame();
		confirm.setVisible(false);
		confirm.dispose();
		dropMenu.setVisible(false);
		
		ProgramInfo.setConfirmationToggle(false);
	}
	
	//btnCheckout
	private void checkout() {
		System.out.println("Checkout Button pressed\n");
		ProgramInfo.setConfirmationToggle(false);
		
		boolean blankError = false;
		
		for(int i = 0; i < confirm.tfInfo.length; i++) {
			String text = confirm.tfInfo[i].getText().trim();
			if(text.equals("") || text.equals("Enter your Full Name") || text.equals("Enter your E-Mail")) {
				blankError = true;
				System.out.println("Blank error true");
			}
			confirm.tfInfo[i].setBackground(Design.primary);
			confirm.tfInfo[i].setEditable(false);
			confirm.tfInfo[i].select(0, 0);
			confirm.editLabel[i].setText("<< Edit");
		}
		
		boolean emailError = false;
		if(!blankError) {
			if(emailTaken(confirm.tfInfo[1].getText())) {
				emailError = true;
			}
			
			if(emailError) {
				confirm.errorLabel.setText("*That E-Mail is registered to another user. Please enter another E-Mail");
				confirm.errorLabel.setVisible(true);
				confirm.tfInfo[1].setText("Enter your E-Mail");
				System.out.println("User Taken");
			}
			else {
				
				System.out.println("Save User Settings");
				//Saves changes to user information.
				saveUserInformation(confirm.tfInfo[0].getText(), confirm.tfInfo[1].getText());
				saveUserSelections();
				resetProgramInfo();
				
				HomePage homepage = new HomePage(confirm.width, confirm.height);
				PopUpFrame popUp = new PopUpFrame(confirm.width, confirm.height, "Would you like to checkout?", confirm, homepage);
				popUp.initFrame();
			
			}
		} else {
			
			if(confirm.tfInfo[0].getText().trim().equals("")) {
				confirm.tfInfo[0].setText("Enter your Full Name");
			}
			if(confirm.tfInfo[1].getText().trim().equals("")) {
				confirm.tfInfo[1].setText("Enter your E-Mail");
			}
			
			confirm.errorLabel.setText("*Please fill out your information.");
			confirm.errorLabel.setVisible(true);
		}
		
		
		
		
	}
	
	//Goes to previous picture of user selections.
	private void prevPic() {
		System.out.println("Previous button pressed\n");
		int index = Confirmation.getIndex();
		index--;
		if(index < 0) {
			index = 0;
		}
		
		Confirmation.setIndex(index);
		Confirmation.setPicture(index);
		confirm.setSelections(index);
		
		dropMenu.setVisible(false);
	}
	
	//Goes to next picture of user selections.
	private void nextPic() {
		System.out.println("Next button pressed\n");
		int index = Confirmation.getIndex();
		index++;
		if(index >= allImages.size()) {
			index = allImages.size() - 1;
		}
		Confirmation.setIndex(index);
		Confirmation.setPicture(index);
		confirm.setSelections(index);
		
		dropMenu.setVisible(false);
	}
	
	//btnView.
	private void view() {
		System.out.println("View Button Pressed\n");
		int index = Confirmation.getIndex();
		String imageName = allImages.get(index);
		FullView fullView = new FullView(confirm.width, confirm.height, imageName);
		fullView.initFrame();
		dropMenu.setVisible(false);
	}
	
	//Returns the amount of emails selected.
	private void emailSelection() {
		int emailAmount = emails.size();
		System.out.println("Email Amount " + emailAmount);
		int index = Confirmation.getIndex();
		int printAmount = calcPrintAmount();
		
			
		if(confirm.cbEmail.isSelected()) {
			System.out.println("Email Check Box is selected\n");
			emailAmount++;
			emails.add(allImages.get(index));
		}
		else {
			System.out.println("Email Check Box is deselected\n");
			emailAmount--;
			emails.remove(allImages.get(index));
		}
		confirm.emailAmountLabel.setText(emailAmount + "x E-Mails");
		confirm.setEmails(emails);
		
		//Total 
		confirm.emailPriceLabel.setText(usFormat.format(emailAmount*ProgramInfo.getPrintPrice()));
		double total = Double.valueOf(printAmount*ProgramInfo.getPrintPrice() + emailAmount*ProgramInfo.getEmailPrice());
		confirm.totalPriceLabel.setText(usFormat.format(total));
		
		dropMenu.setVisible(false);
	}
	
	//cbPrint
	//Adjust the number of prints displayed on the checkout panel.
	private void printSelection() {
		int printAmount = 0;
		int index = Confirmation.getIndex();
		if(confirm.cbPrint.isSelected()) {
			
			//Change spinner value to 1 and enable the component.
			confirm.spinnerPrint.setEnabled(true);
			confirm.spinnerPrint.setValue(1);
		
			prints.put(allImages.get(index), (int) confirm.spinnerPrint.getValue());
			
		}
		else {
	
			//Change spinner value to 0 and disable the component.
			confirm.spinnerPrint.setEnabled(false);
			confirm.spinnerPrint.setValue(0);
			
			//Subtract total print amount from what the spinner value held.
			prints.remove(allImages.get(index));
			
		}
		
		printAmount = calcPrintAmount();
		int emailAmount = emails.size();
		confirm.printAmountLabel.setText(printAmount + "x Prints");
		confirm.setPrints(prints);
		
		//Total 
		confirm.printPriceLabel.setText(usFormat.format(printAmount*ProgramInfo.getPrintPrice()));
		double total = Double.valueOf(printAmount*ProgramInfo.getPrintPrice() + emailAmount*ProgramInfo.getEmailPrice());
		confirm.totalPriceLabel.setText(usFormat.format(total));
		
		dropMenu.setVisible(false);
	}
	
	
	
	//spinnerPrint
	//Change the value shown at checkout panel when user adjusts spinnerPrints value.
	private void changeNumPrints() {
		System.out.println("Spinner value changed\n");
		
		int numPrints = (int) confirm.spinnerPrint.getValue();
		int printAmount = 0;
		int index = Confirmation.getIndex();
		
		if(numPrints < 1) {
			confirm.spinnerPrint.setValue(0);
			confirm.spinnerPrint.setEnabled(false);
			confirm.cbPrint.setSelected(false);
			prints.remove(allImages.get(index));
		} else {
			prints.put(allImages.get(index), numPrints);
		}
		
		printAmount = calcPrintAmount();
		int emailAmount = emails.size();
		confirm.printAmountLabel.setText(printAmount + "x Prints");
		confirm.setPrints(prints);
		
		//Total 
		confirm.printPriceLabel.setText(usFormat.format(printAmount*ProgramInfo.getPrintPrice()));
		double total = Double.valueOf(printAmount*ProgramInfo.getPrintPrice() + emailAmount*ProgramInfo.getEmailPrice());
		confirm.totalPriceLabel.setText(usFormat.format(total));
		
		dropMenu.setVisible(false);
	}

	//Calculates the print amount by getting the values of the set hash map and adding the up.
	private int calcPrintAmount() {
		int printAmount = 0;
		List<String> names = new ArrayList<>(prints.keySet());
		for(int i = 0; i < prints.size(); i++) {
			printAmount += prints.get(names.get(i));
		}
		return printAmount;
	}
	
	//btnCheckout.
	//Saves user information for checkout.
	private void saveUserInformation(String fullName, String email) {
		
		System.out.println("Full name: " + fullName);
		System.out.println("Email: " + email);
		Connection con = null;
		PreparedStatement ps = null;
		String query = "UPDATE `users` SET `full_name` = ?, `user_email` = ? WHERE `user_id` = " + ProgramInfo.getUserID();
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, fullName);
			ps.setString(2, email);
			
			if(ps.executeUpdate() > 0) {
				System.out.println("User info updated");
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		dropMenu.setVisible(false);
	}
	
	private void saveUserSelections() {
		
		deletePreviousSelections();
		
		List<String> selected = new ArrayList<>(confirm.getImageNames());
		ArrayList<String> emailSelections = new ArrayList<>(confirm.getEmails());
		LinkedHashMap<String, Integer> printSelections = new LinkedHashMap<>(confirm.getPrints());
		boolean emailAdded = false;
		boolean printAdded = false;
		orderId = setOrderId();
		//double total = confirm.getTotal();
		
		//Saves all selections.
		for(int i = 0; i < selected.size(); i++) {
			int printAmount = 0;
			int emailAmount = 0;
			String imgSelected = selected.get(i);
			
			//Saves Selected emails to database.
			for(int j = 0; j < emailSelections.size(); j++) {
				if(emailSelections.get(j).equals(imgSelected)) {
					emailAmount = 1;
					emailAdded = true;
				}
			}
			
			if(!emailAdded) {
				emailAmount = 0;
			}
			
			//Saves Selected prints to database.
			for(int k = 0; k < printSelections.size(); k++) {
				if(printSelections.get(imgSelected) != null && printSelections.get(imgSelected) > 0) {
					printAmount = (printSelections.get(imgSelected));
					printAdded = true;
				}
			}
			
			if(!printAdded) {
				printAmount = 0;
			}
			insertOrderInfo(ProgramInfo.getUserID(), imgSelected, printAmount, emailAmount, 0, orderId);
		}
	}
	
	private void insertOrderInfo(int userId, String imgName, int printAmount, int emailAmount, double total, String orderId){
		Connection con = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO `orders` (`user_id`, `image_name`, `print`, `email`, `total`, `order_id`) VALUES(?,?,?,?,?,?)";
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, imgName);
			ps.setInt(3, printAmount);
			ps.setInt(4, emailAmount);
			ps.setDouble(5, total);
			ps.setString(6, orderId);
			if(ps.executeUpdate() > 0) {
				System.out.println("Print Added");
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
	}
	
	private void deletePreviousSelections() {
		Connection con = null;
		PreparedStatement ps = null;
		String query = "DELETE FROM `orders` WHERE `user_id` = " + ProgramInfo.getUserID();
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			if(ps.executeUpdate() > 0) {
				System.out.println("Deleted Previous Selections");
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
		
	}
	
	private void resetProgramInfo() {
		ProgramInfo.setSelectedEmails(new ArrayList<String>());
		ProgramInfo.setPrintsMap(new LinkedHashMap<String, Integer>());
		ProgramInfo.setFullName(null);
		ProgramInfo.setEmail(null);
	}
	
	//btnCheckout Method.
	//If user changes email this makes sure the email is'nt already registered.
	private boolean emailTaken(String email) {
		boolean taken = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT `user_email` FROM `users` WHERE `user_id` <> " + ProgramInfo.userID;
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("user_email").equals(email)) {
					taken = true;
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
		
		return taken;
		
	}
	
	//create and order id that does not repeat.
	private String setOrderId() {
		
		String orderId = null;
		boolean exists = false;
		String query = "SELECT `order_id` FROM `orders`";
		List<String> ids = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				ids.add(rs.getString("order_id"));
			}
			
			if(ids.size() < 1) {
				orderId = createOrderId();
			}
			else {
				orderId = createOrderId();
				for(int i = 0; i < ids.size(); i++) {
					if(orderId.equals(ids.get(i))) {
						exists = true;
					}
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
		
		if(exists) {
			setOrderId();
		}
		else {
			orderId = createOrderId();
		}
		
		return orderId;
		
	}
	
	private String createOrderId() {
		double max = 99999;
		double min = 10000;
		int numID = (int) (Math.random() * ((max-min) + 1)+min);
		return "@" + numID;
	}
	
	
	class EditListener extends MouseAdapter implements MouseListener{
		
		private JLabel edit;
		private JTextField tf;
		private String text;
		
		public EditListener(JLabel edit, JTextField tf) {
			this.edit = edit;
			this.tf = tf;
			text = tf.getText();
		}
		
		public void mousePressed(MouseEvent me) {
			if(edit.getText().equals("<< Edit")) {
				tf.setBackground(Design.secondary);
				tf.setEditable(true);
				tf.requestFocus();
				tf.selectAll();
				edit.setText("<< SAVE");
			}
			else if(edit.getText().equals("<< SAVE")) {
				String blank = tf.getText().trim();
				if(blank.equals("")) {
					tf.setText(text);
				}
				tf.setBackground(Design.primary);
				tf.setEditable(false);
				tf.select(0, 0);
				edit.setText("<< Edit");
			}
			dropMenu.setVisible(false);
				
		}
		
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
	
	
}



