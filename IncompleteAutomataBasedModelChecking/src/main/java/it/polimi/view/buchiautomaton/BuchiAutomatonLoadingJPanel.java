package it.polimi.view.buchiautomaton;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;

import java.awt.Dimension;

@SuppressWarnings("serial")
public class BuchiAutomatonLoadingJPanel<S extends State, T extends LabelledTransition, A extends BuchiAutomaton<S,T>> extends AutomatonLoadingPanel<S,T,A> {

	public BuchiAutomatonLoadingJPanel(Dimension d, AutomatonButtonJPanel jButtonPanel){
		 super(d, jButtonPanel);
		
	}
	

}
