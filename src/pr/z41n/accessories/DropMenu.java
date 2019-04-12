package pr.z41n.accessories;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import pr.z41n.frame.FrameObject;
import pr.z41n.handler.DropMenuHandler;

@SuppressWarnings("serial")
public class DropMenu extends JFrame implements FrameObject {
	
	public JButton btnMenuSettings;
	public JButton btnMenuMinimize;
	public JButton btnMenuExit;
	public JFrame currentFrame;
	
	public DropMenu(int dropWidth, int dropHeight, JFrame currentFrame) {
		dropWidth = dropWidth/7;
		dropHeight = dropHeight/7;
		this.currentFrame = currentFrame;
		System.out.println("Drop Menu Frame Width: " + dropWidth);
		System.out.println("Drop Menu Frame Height: " + dropHeight + "\n");
	
		//Set size of Welcome Frame to full screen.
		setSize(dropWidth, dropHeight);
				
		//Shutdown on exit.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Set application offset from menu button.
		setLocation(currentFrame.getWidth()/27, currentFrame.getHeight()/26);
				
		//Remove exit and minimize bar
		setUndecorated(true);
						
		//Layout of this frame. 
		setLayout(new GridLayout(3, 0));
		
		//Behavior of frame when called.
		setAlwaysOnTop(true);
				
		//Changing the size of the frame is not allowed
		setResizable(false);
	}
	
	public void initFrame() {
	
		getContentPane().setBackground(Design.visorBlack);
		
		btnMenuSettings = new JButton("Program Settings");
		setMenuButtons(btnMenuSettings);
		add(btnMenuSettings);
		
		btnMenuMinimize = new JButton("Minimize");
		setMenuButtons(btnMenuMinimize);
		add(btnMenuMinimize);
		
		btnMenuExit = new JButton("Exit");
		setMenuButtons(btnMenuExit);
		add(btnMenuExit);
		
		DropMenuHandler dropMenuHand = new DropMenuHandler(this, currentFrame);
		dropMenuHand.actionHandler();
		
		setVisible(false);
	}
	
	private void setMenuButtons(JButton btnMenu) {
		btnMenu.setFocusPainted(false);
		btnMenu.setBorderPainted(false);
		btnMenu.setFont(Design.infoFont);
		btnMenu.setForeground(Design.textColor);
		btnMenu.setBackground(Design.visorBlack);
	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
