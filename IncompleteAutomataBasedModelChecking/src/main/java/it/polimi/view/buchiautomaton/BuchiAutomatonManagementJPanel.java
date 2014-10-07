package it.polimi.view.buchiautomaton;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.view.automaton.AutomatonManagementJPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class BuchiAutomatonManagementJPanel<S extends State, T extends LabelledTransition, A extends BuchiAutomaton<S, T>> extends AutomatonManagementJPanel<S,T,A> {

	
	public BuchiAutomatonManagementJPanel(Dimension d,
			BuchiAutomatonJPanel<S,T, A> automatonPanel,
			BuchiAutomatonLoadingJPanel<S, T, A> loadingPanel
			) {
		
		super(new FlowLayout(), d, automatonPanel, loadingPanel);
	}
	
	
	public void updateAutomatonPanel(A a){
		this.automatonPanel.update(a);
		
	}
	
	
	
	
}
