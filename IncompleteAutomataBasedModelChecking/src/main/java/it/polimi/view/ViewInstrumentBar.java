package it.polimi.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ViewInstrumentBar extends JToolBar {

	// ------------------------------------
	// instrumentBar
	// ------------------------------------
	
	// file
	public final JButton saveButton;
	public final JButton openButton;
	public final JButton newButton;
	
	// from ltl
	public final JButton loadLTLButton;
	
	// editing
	public final JButton editingButton;
	public final JButton transformingButton;
	public final JButton flattenButton;
	public final JButton hierarchyButton;
	
	// checking
	public final JButton checkButton;
	
	public ViewInstrumentBar(ActionListener l, JFrame jframe){
		
		//*****************
		// INSTRUMENT BAR
		//*****************
		
		this.setBackground(jframe.getContentPane().getBackground());
		
		// file
		this.newButton=new JButton(Constants.newIcon);
		this.newButton.addActionListener(l);
		this.newButton.setFocusPainted(false);
		this.newButton.setToolTipText(Constants.newMessage);
		
		this.openButton=new JButton(Constants.openIcon);
		this.openButton.addActionListener(l);
		this.openButton.setFocusPainted(false);
		this.openButton.setToolTipText(Constants.openMessage);
		
		this.saveButton=new JButton(Constants.saveIcon);
		this.saveButton.addActionListener(l);
		this.saveButton.setFocusPainted(false);
		this.saveButton.setToolTipText(Constants.saveMessage);
		
		this.add(this.newButton);
		this.add(this.openButton);
		this.add(this.saveButton);
		
		this.addSeparator();
		
		// from ltl formula
		this.loadLTLButton=new JButton(Constants.ltlIcon);
		this.loadLTLButton.addActionListener(l);
		this.loadLTLButton.setFocusPainted(false);
		this.loadLTLButton.setToolTipText(Constants.ltlLoadingMessage);
		
		this.add(this.loadLTLButton);
		this.addSeparator();
		
		// editing
		this.editingButton=new JButton(Constants.editingIcon);
		this.editingButton.addActionListener(l);
		this.editingButton.setFocusPainted(false);
		this.editingButton.setToolTipText(Constants.editingMessage);
		this.editingButton.setEnabled(false);
		
		this.transformingButton=new JButton(Constants.trasformingIcon);
		this.transformingButton.addActionListener(l);
		this.transformingButton.setFocusPainted(false);
		this.transformingButton.setToolTipText(Constants.transorfmingMessage);
		
		this.add(this.editingButton);
		this.add(this.transformingButton);
		this.addSeparator();
		
		this.flattenButton=new JButton(Constants.flattenIcon);
		this.add(this.flattenButton);
		this.flattenButton.addActionListener(l);
		this.flattenButton.setToolTipText(Constants.flatMessage);
		
		this.hierarchyButton=new JButton(Constants.hierarchyIcon);
		this.add(this.hierarchyButton);
		this.hierarchyButton.addActionListener(l);
		this.hierarchyButton.setToolTipText(Constants.hierarchyMessage);
		
		this.addSeparator();
		
		//checking
		this.checkButton=new JButton(Constants.checkIcon);
		this.checkButton.addActionListener(l);
		this.checkButton.setFocusPainted(false);
		this.checkButton.setToolTipText(Constants.checkingMessage);
		
		this.add(this.checkButton);
	}
}
