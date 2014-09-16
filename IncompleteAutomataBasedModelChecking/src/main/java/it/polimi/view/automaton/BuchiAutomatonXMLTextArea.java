package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Dimension;
import java.awt.TextArea;

import javax.xml.bind.JAXBException;

public class BuchiAutomatonXMLTextArea<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends TextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuchiAutomatonXMLTextArea(){
		super();
	}
	public BuchiAutomatonXMLTextArea(Dimension d) throws JAXBException{
		super();
		this.setSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setVisible(true);
	}
	public void update(A a) throws JAXBException{
		this.setText(a.toXMLString());
	}
}
