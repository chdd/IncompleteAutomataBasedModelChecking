package it.polimi.view.intersectionautomaton;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;

import java.awt.Dimension;
import java.awt.TextArea;

@SuppressWarnings("serial")
public class IntersectionAutomatonLoadingPanel<S extends State, T extends LabelledTransition<S>, S1 extends IntersectionState<S>, T1 extends LabelledTransition<S1>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends AutomatonLoadingPanel<S1,T1,A> {

	private TextArea xmlArea;
	
	public IntersectionAutomatonLoadingPanel(Dimension d, AutomatonButtonJPanel jButtonPanel, TextArea jAutomatonTextArea) {
		super(d, jButtonPanel);
		 this.xmlArea=jAutomatonTextArea;
		 this.add(this.xmlArea);
	}
	public void updateResults(@SuppressWarnings("rawtypes") ModelCheckerParameters results){
		if(results.getResult()==1){
			this.xmlArea.setText("The property is satisfied");
		}
		if(results.getResult()==0){
			this.xmlArea.setText("The property is not satisfied");
		}
		if(results.getResult()==-1){
			this.xmlArea.setText("The property is possibly satisfied\n\n");
			this.xmlArea.setText(this.xmlArea.getText()+"Constraint:\n"+results.getConstraint());
		}
	}
}
