package pr.z41n.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import pr.z41n.accessories.Design;
import pr.z41n.accessories.DropMenu;
import pr.z41n.accessories.ProgramInfo;
import pr.z41n.admin.Settings;

public class DropMenuHandler {
	
	private DropMenu dropMenu;
	
	private JFrame currentFrame;
	private int width;
	private int height;
	
	
	public DropMenuHandler(DropMenu dropMenu, JFrame currentFrame) {
		this.currentFrame = currentFrame;
		this.dropMenu = dropMenu;
		
		width = currentFrame.getWidth();
		height = currentFrame.getHeight();
	}
	
	public void actionHandler() {
		//DropMenu Buttons.
		//Action Listener.
		dropMenu.btnMenuSettings.addActionListener(a -> openSettings());
		dropMenu.btnMenuMinimize.addActionListener(a -> minimize());
		dropMenu.btnMenuExit.addActionListener(a -> exit());
		//Mouse Listener.
		dropMenu.btnMenuSettings.addMouseListener(new Hover(dropMenu.btnMenuSettings));
		dropMenu.btnMenuMinimize.addMouseListener(new Hover(dropMenu.btnMenuMinimize));
		dropMenu.btnMenuExit.addMouseListener(new Hover(dropMenu.btnMenuExit));
		
	}
	
	//btnMenuSettings DropMenu.
	private void openSettings() {
		System.out.println("Program Settings Button Pressed\n");
		dropMenu.setVisible(false);
		currentFrame.dispose();
		Settings settings = new Settings(width, height);
		settings.initFrame();
		ProgramInfo.setConfirmationToggle(false);
		ProgramInfo.setUpdateStart(false);
		ProgramInfo.setUpdatePic(false);
	}
	
	//btnMenuMinimize DropMenu.
	private void minimize() {
		System.out.println("Minimize Button Pressed\n");
		dropMenu.setVisible(false);
		currentFrame.setState(JFrame.ICONIFIED);
	}
	
	//btnMenuExit DropMenu.
	private void exit() {
		System.out.println("Exit Button Pressed\n");
		System.exit(0);
	}

}

//Look and Feel Class - Changes the color of button background as mouse hovers button.
class Hover implements MouseListener {
	
	private JButton button;
	
	public Hover(JButton button) {

		this.button = button;
	}
	
	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("Mouse Entered " + button.getText());
		button.setBackground(Design.visorBlue);
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("Mouse Exited " + button.getText());
		button.setBackground(Design.visorBlack);
		
	}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
}
