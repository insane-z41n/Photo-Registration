package pr.z41n.accessories;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class CloseDropMenu implements MouseListener{
	
	private JFrame dropMenu;
	private JFrame currentFrame;

	public CloseDropMenu(JFrame dropMenu, JFrame currentFrame) {
		this.dropMenu = dropMenu;
		this.currentFrame = currentFrame;
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == currentFrame) {
			System.out.println("Dispose Drop Menu\n");
			dropMenu.setVisible(false);
		}
		
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
	
}
