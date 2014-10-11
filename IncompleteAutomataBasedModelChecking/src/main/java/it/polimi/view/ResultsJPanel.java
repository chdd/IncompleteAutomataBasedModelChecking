package it.polimi.view;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ResultsJPanel<S extends State, T extends LabelledTransition, S1 extends IntersectionState<S>, T1 extends ConstrainedTransition<S>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends JPanel {

	private TextArea xmlArea;
	
	public ResultsJPanel(Dimension d) {
		 this.xmlArea=new TextArea();
		 	
		 //this.xmlArea.setPreferredSize(d);
		 //this.xmlArea.setMinimumSize(d);
		 //this.setPreferredSize(d);
		 //this.setMaximumSize(d);
		 //this.setMinimumSize(d);
		 //this.setSize(d);
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
