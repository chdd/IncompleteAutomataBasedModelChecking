package it.polimi.view;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.Observer;

public interface ViewInterface<S1 extends State, T1 extends LabelledTransition<S1>, S extends IntersectionState<S1>, T extends LabelledTransition<S>>{

	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model);
	public void updateSpecification(BuchiAutomaton<S1, T1> specification);
	public void updateIntersection(IntersectionAutomaton<S1,T1,S,T> intersection);
	public void updateVerificationResults(ModelCheckerParameters<S1> verificationResults);
	public void addObserver(Observer o);

	public void displayErrorMessage(String message);
	
}
