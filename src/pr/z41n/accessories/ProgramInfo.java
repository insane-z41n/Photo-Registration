package pr.z41n.accessories;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ProgramInfo {

	//Program paths.
	public static String imagePath;
	
	//Settings computer name.
	public static String compName;
	
	//Settings prices.
	public static double printPrice, emailPrice;
	
	//Settings check boxes.
	public static boolean printSelected, emailSelected;
	
	//Registration information.
	public static String fullName, email;

	//Photos information.
	public static String [] printPhotos, emailPhotos;
	public static ArrayList<String> imageNames, selectedEmails;
	public static LinkedHashMap<String, Integer> printsMap;
	public static int index;
	public static boolean change;
	
	
	//Programs progression.
	public static boolean confirmationToggle, updatePic, updateStart;;
	
	//Database information.
	public static int adminID, userID;
	
	//Progress Variables for Photo Launcher.
	//Boolean installationWizardDone.
	
	//Main PhotoLauncher Boolean updateStart.
	//Boolean start update of pic names.
	public static void setUpdateStart(boolean updateStart) {
		ProgramInfo.updateStart = updateStart;
	}
	public static boolean getUpdateStart() {
		return updateStart;
	}
	//Boolean start update of pictures.
	public static void setUpdatePic(boolean updatePic) {
		ProgramInfo.updatePic = updatePic;
	}
	public static boolean getUpdatePic() {
		return updatePic;
	}
	
	//Settings Variables :'(.
	//String imagePath.
	public static void setImagePath(String imagePath) {
		ProgramInfo.imagePath = imagePath;
	}
	public static String getImagePath() {
		return imagePath;
	}
	//String compName.
	public static void setCompName(String compName) {
		ProgramInfo.compName = compName;
	}
	public static String getCompName() {
		return compName;
	}
	//Double printPrice.
	public static void setPrintPrice(double printPrice) {
		ProgramInfo.printPrice = printPrice;
	}
	public static double getPrintPrice() {
		return printPrice;
	}
	//Double emailPrice.
	public static void setEmailPrice(double emailPrice) {
		ProgramInfo.emailPrice = emailPrice;
	}
	public static double getEmailPrice() {
		return emailPrice;
	}
	//Boolean printSelected.
	public static void setPrintSelected(boolean printSelected) {
		ProgramInfo.printSelected = printSelected;
	}
	public static boolean getPrintSelected() {
		return printSelected;
	}
	//Boolean emailSelected.
	public static void setEmailSelected(boolean emailSelected) {
		ProgramInfo.emailSelected = emailSelected;
	}
	public static boolean getEmailSelected() {
		return emailSelected;
	}
	
	//Registration Variables.
	//String fullName.
	public static void setFullName(String fullName) {
		ProgramInfo.fullName = fullName;
	}
	public static String getFullName() {
		return fullName;
	}
	//String email.
	public static void setEmail(String email) {
		ProgramInfo.email = email;
	}
	public String getEmail() {
		return email;
	}

	
	//Photos Variables.
	//String [] printPhotos.
	public void setPrintPhotos(String [] printPhotos) {
		ProgramInfo.printPhotos = printPhotos;
	}
	public String[] getPrintPhotos() {
		return printPhotos;
	}
	//String [] emailPhotos.
	public void setEmailPhotos(String [] emailPhotos) {
		ProgramInfo.emailPhotos = emailPhotos;
	}
	public String[] getEmailPhotos() {
		return emailPhotos;
	}
	//int index.
	public static void setIndex(int index) {
		ProgramInfo.index = index;
	}
	public static int getIndex() {
		return index;
	}
	//ArrayList imageNames.
	public static void setImageNames(ArrayList<String> imageNames) {
		ProgramInfo.imageNames = imageNames;
	}
	public static ArrayList<String> getImageNames() {
		return imageNames;
	}
	//LinkedHashMap printsMap.
	public static void setPrintsMap(LinkedHashMap<String, Integer> printsMap) {
		ProgramInfo.printsMap = printsMap;
	}
	public static LinkedHashMap<String, Integer> getPrintsMap() {
		if(printsMap == null) {
			return new LinkedHashMap<String, Integer>();
		}
		else {
			return printsMap;
		}
	}
	//ArrayList selectedEmails.
	public static void setSelectedEmails(ArrayList<String> selectedEmails) {
		ProgramInfo.selectedEmails = selectedEmails;
	}
	public static ArrayList<String> getSelectedEmails(){
		if(selectedEmails == null) {
			return new ArrayList<String>();
			
		}
		else {
			return selectedEmails;
		}
		
	}
	
	public static void setChange(boolean change) {
		ProgramInfo.change = change;
	}
	public static boolean getChange() {
		return change;
	}
	
	//Confirmation Variables.
	//Boolean confimationComplete.
	public static void setConfirmationToggle(boolean confirmationToggle) {
		ProgramInfo.confirmationToggle = confirmationToggle;
	}
	public static boolean getConfirmationToggle() {
		return confirmationToggle;
	}
	
	//Database Variables.
	//int adminID.
	public static void setAdminID(int adminID) {
		ProgramInfo.adminID = adminID;
	}
	public static int getAdminID() {
		return adminID;
	}
	
	//int userID.
	public static void setUserID(int userID) {
		ProgramInfo.userID = userID;
	}
	public static int getUserID() {
		return userID;
	}
	
	

	
}



















