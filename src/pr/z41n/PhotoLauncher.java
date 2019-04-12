package pr.z41n;

import java.awt.Dimension;
import java.awt.Toolkit;

import pr.z41n.accessories.ProgramInfo;
import pr.z41n.admin.Login;
import pr.z41n.confirmation.Confirmation;
import pr.z41n.photos.Photos;
import pr.z41n.photos.Pictures;

public class PhotoLauncher implements Runnable{
	
	private Login login;
	
	private int width;
	private int height;

	//Main. 
	public static void main (String [] args) {
		PhotoLauncher launcher = new PhotoLauncher();
		Thread launcherThread = new Thread(launcher);
		launcherThread.start();
	}
	
	//Constructor.
	public PhotoLauncher() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		width = size.width;
		height = size.height;
	}

	//Initialize a frame based on programs progression.
	public void initialize() {
		//Connect to database.
		//Create or update database.

		login = new Login(width, height);
		
		System.out.println("---INITIALIZE PROGRAM---");
		login.initFrame();
	}
	
	public void update() {
		
		//Update database.
		//Check for any errors in database.
		//Delay 20 milliseconds.
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Update pictureNames array.
		if(ProgramInfo.getUpdateStart()) {
			Pictures.createArrayList();
			Pictures.setImageList();
			//Get Pictures Names.
			//Set Picture Names into set array.
		}
		//Update and store user progress.
		//Update current photo page array.
	}
	
	public void render() {
		//Display photo lists on photos frame.
		//Update Photos pic box.
		if(ProgramInfo.getUpdatePic()) {
			Photos.setPictureBox(ProgramInfo.getImageNames());
			if(ProgramInfo.getChange()) {
				Photos.setSelections(ProgramInfo.getImageNames());
				ProgramInfo.setChange(false);
			}
		}
		else if(ProgramInfo.getConfirmationToggle()) {
			Confirmation.setPicture(Confirmation.getIndex());
		}

	}
	
	public void run() {
		//initialize objects and other variables.
		initialize();
		while(true) {
			//update value.
			update();	
			//render objects.
			render();
		}
	}
}