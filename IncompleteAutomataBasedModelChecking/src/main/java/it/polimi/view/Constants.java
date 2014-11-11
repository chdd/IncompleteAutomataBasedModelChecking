package it.polimi.view;

import javax.swing.ImageIcon;

public class Constants {

	// Icons
	public static final ImageIcon newIcon = new ImageIcon(Constants.class.getResource("/org/freedesktop/tango/22x22/actions/document-new.png"));
	public static final ImageIcon openIcon = new ImageIcon(Constants.class.getResource("/org/freedesktop/tango/22x22/actions/document-open.png"));
	public static final ImageIcon saveIcon = new ImageIcon(Constants.class.getResource("/org/freedesktop/tango/22x22/devices/media-floppy.png"));
	public static final ImageIcon ltlIcon = new ImageIcon(Constants.class.getResource("/org/freedesktop/tango/22x22/devices/input-keyboard.png"));
	public static final ImageIcon editingIcon = new ImageIcon(Constants.class.getResource("/org/freedesktop/tango/22x22/categories/applications-office.png"));

	public static final ImageIcon trasformingIcon = new ImageIcon(Constants.class.getResource("/org/freedesktop/tango/22x22/actions/view-fullscreen.png"));
	public static final ImageIcon checkIcon = new ImageIcon(Constants.class.getResource("/org/freedesktop/tango/22x22/categories/applications-system.png"));
	public static final ImageIcon resultYes=new ImageIcon(Constants.class.getResource("/img/Yes.png"));
	public static final ImageIcon resultNo=new ImageIcon(Constants.class.getResource("/img/No.png"));
	public static final ImageIcon resultMaybe=new ImageIcon(Constants.class.getResource("/img/Maybe.png"));
	public static final ImageIcon resultInitial=new ImageIcon(Constants.class.getResource("/img/QuestionMark.png"));
	public static final ImageIcon flattenIcon = new ImageIcon(Constants.class.getResource("/img/Packaging.png"));
	public static final ImageIcon hierarchyIcon = new ImageIcon(Constants.class.getResource("/img/Hierarchy.png"));
	
	// Messages
	public static final String editingMessage="<html>Editing Mode:<br>"
				+ "MouseButtonOne press on empty space creates a new State<br>"
				+ "MouseButtonOne press on a State, followed by a drag to another State creates an directed transition between them<br>"
				+ "Right click on the state or on the transition to modify its properties<br></html>";
	
	public static final String transorfmingMessage="<html>Transorming Mode:<br>"
				+ "MouseButtonOne+drag to translate the display<br>"
				+ "MouseButtonOne+Shift+drag to rotate the display<br>"
				+ "MouseButtonOne+ctrl(or Command)+drag to shear the display<br>"
				+ "Position the mouse on a state and press p to enter the state selection mode that allows to move the states<br>"
				+ "Press t to exit the state selection mode<br></html>";
	
	public static final String hierarchyMessage="<html>Hyerarchicel View:<br>"
			+ "Displays the IBA in a hierarchical fashion including the transparent states in the model<br></html>";
	
	public static final String flatMessage="<html>Flat View:<br>"
			+ "Computes an IBA which includes the current IBA and the refinement of its transparent states<br></html>";
	
	public static final String ltlLoadingMessage="<html>Loads the claim form an LTL formula</html>";
	public static final String checkingMessage="<html>Checks if the model satisfies, possibly satisfies or not satisfies the claim</html>";		
	public static final String newMessage="<html>Creates a new Model or Claims<br> in relation with the selected tab</html>";
	public static final String openMessage="<html>Load the Model, Claims or Intersection<br> in relation with the selected tab</html>";
	public static final String saveMessage="<html>Save the Model, Claims or Intersection<br> in relation with the selected tab</html>";
		
	public static final String appName="CHIA: CHecker for Incompete Automata";

	
}
