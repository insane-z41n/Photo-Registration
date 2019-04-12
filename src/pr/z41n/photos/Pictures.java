package pr.z41n.photos;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import pr.z41n.accessories.ProgramInfo;

public class Pictures {

	public static ArrayList<String> imageNames = new ArrayList<>();
	
	//Get image file names.
	public static String [] getFileList() {
		File file = new File(ProgramInfo.getImagePath());
		String [] fileNames = file.list();
		return fileNames;
	}
	
	public static void createArrayList(){
		String [] fileNames = getFileList();
		int index = 0;
		
		//Checks if Array list image names is smaller than the filenames.
		if(fileNames.length > imageNames.size()) {
			index = imageNames.size();
			imageNames.add(fileNames[index]);
			System.out.println("Add " + imageNames.get(index) + " at: " + index);
			ProgramInfo.setChange(true);
			
		}
		//Check if array list image names is the same size as filenames.
		else if(fileNames.length == imageNames.size()) {
			//System.out.println("Checking if all is in place.");
			for(int i = 0; i < fileNames.length; i++) {
				if(!fileNames[i].equals(imageNames.get(i))) {
					String name = imageNames.get(i);
					imageNames.set(i, fileNames[i]);
					System.out.println("Changed " + name + " at: " + i + "to " + fileNames[i]);
					ProgramInfo.setChange(true);
				}
			}
			ProgramInfo.setChange(false);
		}
		//Delete the imageNames and then re create it with a for loop.
		else {
			System.out.println("Recreating ArrayList");
			while(!imageNames.isEmpty()) {
				int x = 0;
				imageNames.remove(x);
				x++;
			}
			for(int i = 0; i < fileNames.length; i++) {
				imageNames.add(fileNames[i]);
			}
			
			ProgramInfo.setChange(true);
		}
		
		ProgramInfo.setImageNames(imageNames);
	}
	
	public static void setImageList() {
		imageNames = ProgramInfo.getImageNames();
		ArrayList<String> list = new ArrayList<>();
		Queue<String> q = new LinkedList<String>();
		
		for(int i = 0; i < imageNames.size(); i++) {
			q.add(imageNames.get(i));
		}
		int remainder = 0;
		if(imageNames.size() > 0) {
			remainder = 6 - imageNames.size()%6;
			
			if(remainder != 0) {
				for(int r = 0; r<remainder;r++) {
					q.add(null);
				}
			}
		}
		else {
			remainder = 6;
			for(int r = 0; r<remainder;r++) {
				q.add(null);
			}
		}
		
		while(!q.isEmpty()) {
			list.add(q.remove());
		}
		ProgramInfo.setImageNames(list);
	}
	
	
	
}














