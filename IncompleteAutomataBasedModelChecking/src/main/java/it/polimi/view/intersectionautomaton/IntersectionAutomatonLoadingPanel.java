package it.polimi.view.intersectionautomaton;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;
import it.polimi.view.automaton.AutomatonXMLTextArea;

import java.awt.Dimension;

@SuppressWarnings("serial")
public class IntersectionAutomatonLoadingPanel<S extends State, T extends LabelledTransition<S>, S1 extends IntersectionState<S>, T1 extends LabelledTransition<S1>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends AutomatonLoadingPanel<S1,T1,A> {

	public IntersectionAutomatonLoadingPanel(Dimension d, AutomatonButtonJPanel jButtonPanel,
			AutomatonXMLTextArea jAutomatonTextArea) {
		super(d, jButtonPanel, jAutomatonTextArea);
	}
	public void update(String automatonXML){
		
		this.xmlArea.update(automatonXML);
	}
}
