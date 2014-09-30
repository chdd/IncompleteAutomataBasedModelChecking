package it.polimi.view.factories;

import it.polimi.model.ba.BuchiAutomaton;
import it.polimi.model.ba.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.view.automaton.AutomatonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;
import it.polimi.view.automaton.AutomatonXMLTextArea;

import java.awt.Dimension;

import javax.swing.JPanel;



public abstract class AbstractAutomatonFactory<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S,T>> {
	
	public abstract JPanel getPanel(Dimension d);
	protected abstract AutomatonJPanel<S,T,A> getAutomatonPanel(Dimension d);
	
	protected abstract AutomatonLoadingPanel<S,T,A> getLoadingPanel(Dimension d);
	
	protected abstract JPanel getJButtonPanel(Dimension d);
	
	protected AutomatonXMLTextArea getXmlAreaDimension(Dimension d){
		return new AutomatonXMLTextArea(d);
	}
}
