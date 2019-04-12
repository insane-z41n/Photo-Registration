package pr.z41n.accessories;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Design {
	
	public static String fontType = "Century Gothic";
	
	//Fonts
	public static Font smallInfoFont = new Font(fontType, Font.PLAIN, 15);
	public static Font infoFont = new Font(fontType, Font.PLAIN, 18);
	public static Font bigInfoFont = new Font(fontType, Font.PLAIN, 22);
	public static Font largeFont = new Font(fontType, Font.PLAIN, 28);
	public static Font buttonFont = new Font(fontType, Font.PLAIN, 30);
	public static Font titleFont = new Font(fontType, Font.BOLD, 35);
	public static Font bigTitleFont = new Font(fontType, Font.BOLD, 75);
	
	//Colors 
	public static Color primary = Color.decode("#001d35"); //#0099ff
	public static Color secondary = Color.decode("#006b5e"); //#0066ff
	public static Color textColor = Color.white;
	public static Color errorColor = Color.yellow;
	public static Color visorBlack = new Color(0, 0, 102);
	public static Color visorBlue = new Color(0, 0, 153);

	//BUTTONS
	public static void createMenuButton(JButton btnMenu) {
		btnMenu.setBorder(null);
		btnMenu.setFocusPainted(false);
		btnMenu.setContentAreaFilled(false);
		btnMenu.setOpaque(false);
	}
	
	public static void createAdminButton(JButton btnAdmin) {
		btnAdmin.setFont(smallInfoFont);
		btnAdmin.setFocusPainted(false);
	}
	
	//Create Buttons for the Application.
	public static void createInterfaceButton(JButton btnInterface) {
		btnInterface.setForeground(textColor);
		btnInterface.setBackground(secondary);
		btnInterface.setFont(infoFont);
		btnInterface.setFocusPainted(false);
		btnInterface.setBorder(new LineBorder(textColor));
		
		//Create Size.
		String text = btnInterface.getText();
		int buttonWidth = getButtonWidth(text, btnInterface.getFont().getSize());
		int buttonHeight = btnInterface.getFont().getSize() + 15;
		btnInterface.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
	}
	
	//Create Same Button but BIGGER for the Application.
	public static void createBigInterfaceButton(JButton btnInterface) {
		btnInterface.setForeground(textColor);
		btnInterface.setBackground(secondary);
		btnInterface.setFont(buttonFont);
		btnInterface.setFocusPainted(false);
		btnInterface.setBorder(new LineBorder(textColor));
		
		//Create Size.
		String text = btnInterface.getText();
		int buttonWidth = getButtonWidth(text, btnInterface.getFont().getSize());
		int buttonHeight = btnInterface.getFont().getSize() + 15;
		btnInterface.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
	}	
	
	//Returns a width value created from the length of the text and font size.
	private static int getButtonWidth(String text, int fontSize) {
		char[] textArray = text.toCharArray();
		int width = textArray.length * fontSize;
		return width;
	}
	
	//Creates A Button with the same background color as the panel and has not animations on click or hover.
	public static void createPlainButton(JButton btnPlain) {
		btnPlain.setForeground(textColor);
		btnPlain.setBackground(primary);
		btnPlain.setFont(bigTitleFont);
		btnPlain.setFocusPainted(false);
		btnPlain.setBorder(null);
		btnPlain.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPlain.setContentAreaFilled(false);
	}
	
	//TEXTFIELDS
	public static void createAdminTextField(JTextField tfAdmin, int width) {
		tfAdmin.setFont(bigInfoFont);
		tfAdmin.setForeground(textColor);
		tfAdmin.setBackground(secondary);
		tfAdmin.setCaretColor(textColor);
		tfAdmin.setBorder(new EmptyBorder(0,5,0,0));
		tfAdmin.setHorizontalAlignment(SwingConstants.LEFT);
		tfAdmin.setPreferredSize(new Dimension(width, tfAdmin.getFont().getSize() + 20));
	}
	
	public static void createTFHomePage(JTextField tfUser, int width) {
		tfUser.setCaretColor(textColor);
		tfUser.setFont(largeFont);
		tfUser.setBorder(new MatteBorder(0,0,2,0, textColor));
		tfUser.setBackground(primary);
		tfUser.setForeground(textColor);
		tfUser.setPreferredSize(new Dimension(width, tfUser.getFont().getSize() + 10));
	}
	
	public static void createUserFormattedTextField(JFormattedTextField tfFormatted, int columns) {
		tfFormatted.setCaretColor(textColor);
		tfFormatted.setFont(bigInfoFont);
		tfFormatted.setBackground(primary);
		tfFormatted.setForeground(textColor);
		tfFormatted.setColumns(columns);
		tfFormatted.setBorder(new MatteBorder(0,0,3,0, textColor));
	}
	
	//CHECKBOXES
	public static void createAdminCheckBoxes(JCheckBox cbAdmin) {
		cbAdmin.setFont(infoFont);
		cbAdmin.setForeground(textColor);
		cbAdmin.setBackground(primary);
		cbAdmin.setFocusable(false);
	}
	
	public static void createPhotosCheckBoxes(JCheckBox cb) {
		cb.setFocusPainted(false);
		cb.setContentAreaFilled(false);
		cb.setForeground(Design.textColor);
		cb.setFont(Design.bigInfoFont);
		cb.setBackground(Design.primary);
	}
	
	//LABELS
	public static void createLabelClickAdmin(JLabel adminLabel, int width) {
		adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
		adminLabel.setForeground(textColor);
		adminLabel.setFont(infoFont);
		adminLabel.setPreferredSize(new Dimension(width, adminLabel.getFont().getSize() + 5));
		adminLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public static int getComponentWidth(String [] labels, int fontSize) {
		int biggestLen = 0;
		for(int i = 0; i < labels.length; i++) {
			int labelLen = labels[i].length();
			if(labelLen > biggestLen) {
				biggestLen = labelLen;
			}
		}
		
		return biggestLen * fontSize;
	}
	
	public static void createLabelRegi(JLabel userLabel) {
		userLabel.setForeground(textColor);
		userLabel.setFont(titleFont);
	}
	
	//PANELS
	public static void createPanelHomePage(JPanel hpPanel) {
		hpPanel.setBackground(primary);
		hpPanel.setLayout(new FlowLayout());
	}
	
	public static void createPanelHomePageLeft(JPanel hpPanel) {
		hpPanel.setBackground(primary);
		hpPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
	}
}
