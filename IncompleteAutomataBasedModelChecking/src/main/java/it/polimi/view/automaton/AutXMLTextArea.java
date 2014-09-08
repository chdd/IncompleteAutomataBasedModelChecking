package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Dimension;
import java.awt.TextArea;

import javax.xml.bind.JAXBException;

public class AutXMLTextArea<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends TextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutXMLTextArea(){
		super();
	}
	public AutXMLTextArea(Dimension d, A  a) throws JAXBException{
		super();
		this.setSize(d);
		this.setText(a.toXMLString());
		this.setVisible(true);
	}
}
