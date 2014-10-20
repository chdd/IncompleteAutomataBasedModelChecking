package it.polimi.view;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.awt.TextArea;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ResultsJPanel<S extends State, T extends LabelledTransition, S1 extends IntersectionState<S>, T1 extends ConstrainedTransition<S>, 
LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactoryInterface<T>,
CONSTRAINTTRANSITIONFACTORY extends ConstrainedTransitionFactoryInterface<S, T1>, A  extends IntBAImpl<S, T, S1, T1, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY>> extends JPanel {

	private TextArea xmlArea;
	
	public ResultsJPanel() {
		 this.xmlArea=new TextArea();
		 	
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
