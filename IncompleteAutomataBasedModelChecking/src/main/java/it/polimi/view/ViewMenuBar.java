package it.polimi.view;

import java.awt.MenuBar;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * contains the {@link JMenuBar} of the application
 * @author claudiomenghi
 *
 */
@SuppressWarnings("serial")
public class ViewMenuBar extends JMenuBar {

	// file menu
	protected JMenu fileMenu;
	protected JMenuItem filenew;
	protected JMenuItem fileopen;
	protected JMenuItem filesave;
	protected JMenuItem loadClaimFromLTLMenuItem;
	
	// edit menu
	protected JMenu editMenu;
	protected JMenuItem editModeMenu;
	protected JMenuItem transformingModeMenu;
	
	//check menu
	protected JMenu checkMenu;
	protected JMenuItem runCheckerMenuItem;
	
	/**
	 * creates a new {@link MenuBar}
	 * @param l is the {@link ActionListener} invoked when an action on the {@link MenuBar} is performed
	 * @param jframe is the {@link JFrame} where the {@link MenuBar} must be added
	 * @throws NullPointerException if the {@link ActionListener} or the {@link JFrame} is null
	 */
	public ViewMenuBar(ActionListener l, JFrame jframe){
		if(l==null){
			throw new NullPointerException("The ActionListener cannot be null");
		}
		if(jframe==null){
			throw new NullPointerException("The jFrame cannot be null");
		}
		
		//*****************
		// MENU
		//*****************
		
		// file
		this.fileMenu=new JMenu("File");
		this.filenew = new JMenuItem("New", Constants.newIcon);
		this.filenew.addActionListener(l);
		this.fileMenu.add(this.filenew);
		
		this.fileopen = new JMenuItem("Open", Constants.openIcon);
		this.fileopen.addActionListener(l);
		this.fileMenu.add(this.fileopen);
		
		this.filesave = new JMenuItem("Save", Constants.saveIcon);
		this.filesave.addActionListener(l);
		this.fileMenu.add(this.filesave);
		
		this.loadClaimFromLTLMenuItem=new JMenuItem("Load Claim From LTL", Constants.ltlIcon);
		this.loadClaimFromLTLMenuItem.addActionListener(l);
		this.fileMenu.add(this.loadClaimFromLTLMenuItem);
		
		this.add(this.fileMenu);
		
		// editing
		this.editMenu=new JMenu("Edit");
		this.editModeMenu= new JMenuItem("Editing Mode", Constants.editingIcon);
		this.editModeMenu.addActionListener(l);
		this.editMenu.add(this.editModeMenu);
		
		this.transformingModeMenu= new JMenuItem("Transforming Mode", Constants.trasformingIcon);
		this.transformingModeMenu.addActionListener(l);
		this.editMenu.add(this.transformingModeMenu);
		
		this.add(this.editMenu);
		
		// checking
		this.checkMenu=new JMenu("Check");
		this.runCheckerMenuItem= new JMenuItem("Run", Constants.checkIcon);
		this.runCheckerMenuItem.addActionListener(l);
		this.checkMenu.add(this.runCheckerMenuItem);
		this.add(this.checkMenu);
	}
}
