package it.polimi.view.incompleteautomaton;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class IncompleteBuchiAutomatonLoadingPanel<S extends State, T extends LabelledTransition, A extends IncompleteBuchiAutomaton<S,T>> extends AutomatonLoadingPanel<S,T,A>{

	public IncompleteBuchiAutomatonLoadingPanel(Dimension d, AutomatonButtonJPanel jButtonPanel){
		 super(d, jButtonPanel);
	}
	
	public JPanel createButtonPanel(Dimension buttonPanelDimension){
		return new IncompleteBuchiButtonJPanel<S,T,A>(buttonPanelDimension);
	}
}
