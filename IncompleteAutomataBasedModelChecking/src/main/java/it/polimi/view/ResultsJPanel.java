package it.polimi.view;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.awt.TextArea;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ResultsJPanel<
	CONSTRAINTELEMENT extends State,
	S extends State, T extends LabelledTransition<CONSTRAINTELEMENT>, 
	S1 extends IntersectionState<S>, 
	T1 extends LabelledTransition<CONSTRAINTELEMENT>, 
	LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINTELEMENT, T>,
	CONSTRAINTTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINTELEMENT,  T1>, A  
	extends IntBAImpl<CONSTRAINTELEMENT, S, T, S1, T1, LABELLEDTRANSITIONFACTORY, CONSTRAINTTRANSITIONFACTORY>> extends JPanel {

	private TextArea xmlArea;
	
	public ResultsJPanel() {
		 this.xmlArea=new TextArea();
		 	
		 this.add(this.xmlArea);
	}
	public void updateResults(@SuppressWarnings("rawtypes") ModelCheckerParameters results){
		
		if(results.getResult()==-1){
			this.xmlArea.setText(this.xmlArea.getText()+"Constraint:\n"+results.getConstraint());
		}
	}
}
