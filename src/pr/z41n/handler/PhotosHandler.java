package pr.z41n.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import pr.z41n.accessories.CloseDropMenu;
import pr.z41n.accessories.DropMenu;
import pr.z41n.accessories.ProgramInfo;
import pr.z41n.confirmation.Confirmation;
import pr.z41n.homepage.HomePage;
import pr.z41n.photos.FullView;
import pr.z41n.photos.Photos;

public class PhotosHandler {
	
	private Photos photos;
	private DropMenu dropMenu;
	private ArrayList<String> emails;
	private LinkedHashMap<String, Integer> prints;
	
	
	public PhotosHandler(Photos photos, DropMenu dropMenu) {
		this.photos = photos;
		this.dropMenu = dropMenu;

		prints = ProgramInfo.getPrintsMap();
		emails = ProgramInfo.getSelectedEmails();
	}
	
	public void actionHandler() {
		photos.btnMenuPho.addActionListener(a -> openMenu());
		photos.btnBack.addActionListener(a -> back());
		photos.btnDone.addActionListener(a -> done());
		photos.btnNext.addActionListener(a -> nextPictures());
		photos.btnPrev.addActionListener(a -> prevPictures());
		
		//View Button.
		//Print Photos.
		//Email Photos.
		//Spinner Photos.
		for(int i = 0; i < Photos.btnView.length; i++) {
			int x = i;
			Photos.btnView[i].addActionListener(a -> viewPicture(x));
			Photos.printPhotos[i].addActionListener(a -> checkPrint(x));
			Photos.emailPhotos[i].addActionListener(a -> checkEmail(x));
			Photos.spinnerPhotos[i].addChangeListener(cl -> countPrint(x));
		}
		
		photos.addMouseListener(new CloseDropMenu(dropMenu, photos));
	}
	
	private void openMenu() {
		System.out.println("Menu Button pressed\n");
		dropMenu.setVisible(true);
	}
	
	private void back() {
		System.out.println("Back Button pressed\n");
		ProgramInfo.setUpdatePic(false);
		storeSelections();

		HomePage homepage = new HomePage(photos.width, photos.height);
		homepage.initFrame();
		photos.setVisible(false);
		photos.dispose();

		dropMenu.setVisible(false);
	}
	
	private void done() {
		System.out.println("Done Button pressed\n");
		ProgramInfo.setUpdatePic(false);
		storeSelections();
		
		Confirmation confirm = new Confirmation(photos.width, photos.height);
		confirm.initFrame();
		photos.setVisible(false);
		photos.dispose();
		
		ProgramInfo.setConfirmationToggle(true);

		dropMenu.setVisible(false);
	}
	
	private void nextPictures() {
		System.out.println("Next Button pressed\n");
		storeSelections();
		int index = ProgramInfo.getIndex();
		int max = ProgramInfo.getImageNames().size() - 6;
		//System.out.print(max);
		index+=6;
		if(max < 0)
			max = 0;
		if(index>max)
			index = max;
		ProgramInfo.setIndex(index);
		Photos.setPictureBox(ProgramInfo.getImageNames());
		Photos.setSelections(ProgramInfo.getImageNames());
		dropMenu.setVisible(false);
		
	}
	
	private void prevPictures() {
		System.out.println("Previous Button pressed\n");
		storeSelections();
		int index = ProgramInfo.getIndex();
		if(index >= ProgramInfo.getImageNames().size()) {
			index= ProgramInfo.getImageNames().size() -6;
		}
		index-=6;
		if(index<=0)
			index = 0;
		ProgramInfo.setIndex(index);
		Photos.setPictureBox(ProgramInfo.getImageNames());
		Photos.setSelections(ProgramInfo.getImageNames());
		dropMenu.setVisible(false);
	}
	
	//View Button.
	private void viewPicture(int x) {
		System.out.println("View Picture Button Pressed\n");
		
		String imageName = Photos.displayImages[x];
		FullView fullView = new FullView(photos.width, photos.height, imageName);
		fullView.initFrame();
		dropMenu.setVisible(false);
	}
	
	//Check Box Email.
	private void checkEmail(int x) {
		System.out.println("Email Check Box was clicked\n");
		if(Photos.emailPhotos[x].isSelected()) {
			//Photos.picBox[x].setText("Email was selected!");
		}
		else {
			//Photos.picBox[x].setText("Email was unselected!");
		}
		dropMenu.setVisible(false);
	}
	
	
	//Check Box Print.
	private void checkPrint(int x) {
		System.out.println("Print Check Box was clicked\n");
		if(Photos.printPhotos[x].isSelected()) {
			//Photos.picBox[x].setText("Print was selected!");
			Photos.spinnerPhotos[x].setEnabled(true);
			Photos.spinnerPhotos[x].setValue(1);
		}
		else {
			//Photos.picBox[x].setText("Print was deselected!");
			Photos.spinnerPhotos[x].setEnabled(false);
			Photos.spinnerPhotos[x].setValue(0);
		}
		dropMenu.setVisible(false);
	}

	//Spinner Photos.
	private void countPrint(int x) {
		System.out.println("Spinner value Changed!\n");
		int numPrints = (int) Photos.spinnerPhotos[x].getValue();
		if(numPrints < 1) {
			Photos.spinnerPhotos[x].setValue(0);
			Photos.spinnerPhotos[x].setEnabled(false);
			Photos.printPhotos[x].setSelected(false);
		}
		dropMenu.setVisible(false);
	}
	
	private void storeSelections() {
		for(int i = 0; i < Photos.printPhotos.length; i++) {
			if(Photos.printPhotos[i].isSelected()) {
				addToPrints(Photos.displayImages[i], (int) Photos.spinnerPhotos[i].getValue());
			}
			else {
				removeFromPrints(Photos.displayImages[i], (int) Photos.spinnerPhotos[i].getValue());
			}
			
			if(Photos.emailPhotos[i].isSelected()) {
				addEmailSelection(Photos.displayImages[i]);
			}
			else {
				removeEmailSelection(Photos.displayImages[i]);
			}
		}
	}
	
	//Add image name from prints list.
	private void addToPrints(String img, int amount) {
		prints.put(img, amount);
		ProgramInfo.setPrintsMap(prints);
	}
	
	//Remove image name from prints list.
	private void removeFromPrints(String img, int amount) {
		prints.remove(img, amount);
		ProgramInfo.setPrintsMap(prints);
	}
	
	//Remove image name from emails list.
	private void addEmailSelection(String img) {
		boolean emailStored = false;
		if(emails.size() == 0) {
			emails.add(img);
			emailStored = true;
		}
		else {
			for(int i = 0; i < emails.size(); i++) {
				if(emails.get(i).equals(img)) {
					System.out.println(img + " has been stored in email list");
					emailStored = true;
				}
			}
		}
		
		if(!emailStored) {
			emails.add(img);
		}
		
		ProgramInfo.setSelectedEmails(emails);
	}
	//Add image name from emails list.
	private void removeEmailSelection(String img) {
		if(emails.size() != 0) {
			for(int i = 0; i < emails.size(); i++) {
				if(emails.get(i).equals(img)) {
					emails.remove(i);
				}
				else {
					emails.remove(img);
				}
			}
		}
		ProgramInfo.setSelectedEmails(emails);
	}
	
}










