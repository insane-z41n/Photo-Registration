package pr.z41n.handler;

import pr.z41n.frame.FrameObject;
import pr.z41n.popup.PopUpFrame;

public class PopUpHandler {
	
	PopUpFrame pop;
	FrameObject current, next;
	
	public PopUpHandler(PopUpFrame pop, FrameObject current, FrameObject next) {
		this.pop = pop;
		this.current = current;
		this.next = next;
	}
	
	public void actionHandler() {
		pop.btnYes.addActionListener(a -> nextFrame());
		pop.btnNo.addActionListener(a -> exit());
	}
	
	private void nextFrame() {
		pop.close();
		next.initFrame();
		current.close();	
	}
	
	private void exit() {
		pop.close();
	}

}
