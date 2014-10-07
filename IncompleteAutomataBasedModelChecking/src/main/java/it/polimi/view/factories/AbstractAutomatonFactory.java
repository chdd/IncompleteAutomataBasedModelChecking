package it.polimi.view.factories;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.view.automaton.AutomatonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;

import java.awt.Dimension;

import javax.swing.JPanel;



public abstract class AbstractAutomatonFactory<S extends State, T extends LabelledTransition, A extends BuchiAutomaton<S,T>> {
	
	public abstract JPanel getPanel(Dimension d);
	protected abstract AutomatonJPanel<S,T,A> getAutomatonPanel(Dimension d);
	
	protected abstract AutomatonLoadingPanel<S,T,A> getLoadingPanel(Dimension d);
	
	protected abstract JPanel getJButtonPanel(Dimension d);
	
	
}
