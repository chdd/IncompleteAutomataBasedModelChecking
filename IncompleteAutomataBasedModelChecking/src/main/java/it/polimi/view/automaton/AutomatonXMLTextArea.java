package it.polimi.view.automaton;

import java.awt.Dimension;
import java.awt.TextArea;

public class AutomatonXMLTextArea extends TextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutomatonXMLTextArea(){
		super();
	}
	public AutomatonXMLTextArea(Dimension d){
		super();
		this.setSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setVisible(true);
	}
	public void update(String a){
		this.setText(a);
	}
}
