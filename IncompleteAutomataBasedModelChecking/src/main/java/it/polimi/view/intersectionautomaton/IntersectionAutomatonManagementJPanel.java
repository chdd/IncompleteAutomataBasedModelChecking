package it.polimi.view.intersectionautomaton;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.model.graph.State;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.automaton.AutomatonManagementJPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class IntersectionAutomatonManagementJPanel<S extends State, T extends LabelledTransition<S>, S1 extends IntersectionState<S>, T1 extends LabelledTransition<S1>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends
AutomatonManagementJPanel<S1, T1, A> {

	public IntersectionAutomatonManagementJPanel(Dimension d,
			IntersectionAutomatonJPanel<S,T, S1, T1, A> automatonPanel,
			IntersectionAutomatonLoadingPanel<S, T, S1, T1, A> loadingPanel) 
	{
		super(new FlowLayout(), d, automatonPanel,loadingPanel);
	}
	
	public void updateAutomatonPanel(A a){
		this.automatonPanel.update(a);
	}
	
	public void updateVerificationResults(ModelCheckerParameters<S> results){
		if(results==null){
			throw new IllegalArgumentException("The incomplete automaton a cannot be null");
		}
		String text="";
		if(results.getResult()==1){
			text="The property is satisfied";
		}
		if(results.getResult()==0){
			text="The property is not satisfied";
		}
		if(results.getResult()==-1){
			text="The property is possibly satisfied\n\n";
			text=text+"Constraint:\n"+results.getConstraint();
		}
		((IntersectionAutomatonLoadingPanel<S, T, S1, T1, A>) this.loadingPanel).update(text);
	}




}
