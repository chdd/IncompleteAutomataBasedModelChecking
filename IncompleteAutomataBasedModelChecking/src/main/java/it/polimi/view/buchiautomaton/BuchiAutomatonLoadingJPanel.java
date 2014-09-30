package it.polimi.view.buchiautomaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.LabelledTransition;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;
import it.polimi.view.automaton.AutomatonXMLTextArea;

import java.awt.Dimension;

@SuppressWarnings("serial")
public class BuchiAutomatonLoadingJPanel<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S,T>> extends AutomatonLoadingPanel<S,T,A> {

	public BuchiAutomatonLoadingJPanel(Dimension d, AutomatonButtonJPanel jButtonPanel, AutomatonXMLTextArea jAutomatonTextArea){
		 super(d, jButtonPanel, jAutomatonTextArea);
	}
	
	public void update(A a){
		this.xmlArea.update(a.toXMLString());
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

	public String getAutomatonXML() {
		return this.xmlArea.getText();
		
	}
}
