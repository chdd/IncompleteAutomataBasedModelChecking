package it.polimi.view.automaton;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AutomatonButtonJPanel extends JPanel{

	protected ActionListener l;
	
	protected void setActionListener(ActionListener l){
		this.l=l;
	}
}
