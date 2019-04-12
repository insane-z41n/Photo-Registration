package pr.z41n.popup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import pr.z41n.accessories.Design;
import pr.z41n.frame.FrameObject;
import pr.z41n.handler.PopUpHandler;

@SuppressWarnings("serial")
public class PopUpFrame extends JFrame implements FrameObject{
	
	public int width, height;
	public FrameObject current, next;
	
	private int popWidth, popHeight;
	private String message;
	
	public JButton btnYes, btnNo;
	
	public PopUpFrame(int width, int height, String message, FrameObject current, FrameObject next) {
		this.width = width;
		this.height = height;
		this.message = message;
		this.current = current;
		this.next = next;
		
		//Set Size of Settings Frame to half of screen size.
		setSize(popWidth, popHeight);
				
		//Shutdown on exit.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Remove exit and minimize bar
		setUndecorated(true);
		
		//Layout of this frame. 
		setLayout(new BorderLayout());
			
		//Changing the size of the frame is not allowed.
		setResizable(false);
		
		setAlwaysOnTop(true);
	}
	
	public void initFrame() {
		
		getContentPane().setBackground(Design.secondary);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(10,10,10,10));
		((JComponent) getContentPane()).setBorder(new LineBorder(Design.textColor, 2));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Design.secondary);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(topPanel, BorderLayout.NORTH);
		
		JLabel msgLabel = new JLabel(message);
		msgLabel.setFont(Design.infoFont);
		msgLabel.setForeground(Design.textColor);
		topPanel.add(msgLabel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Design.secondary);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(buttonPanel, BorderLayout.SOUTH);
		
		btnYes = new JButton("Yes");
		Design.createInterfaceButton(btnYes);
		buttonPanel.add(btnYes);
		
		btnNo = new JButton("No");
		Design.createInterfaceButton(btnNo);
		buttonPanel.add(btnNo);
		
		PopUpHandler popHandler = new PopUpHandler(this, current, next);
		popHandler.actionHandler();
		
		setVisible(true);
		pack();
		//Set application in the middle.
		setLocationRelativeTo(null);
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}

}
