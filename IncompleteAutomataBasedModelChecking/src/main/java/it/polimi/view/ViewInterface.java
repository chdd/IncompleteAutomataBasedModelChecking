package it.polimi.view;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.Observer;

public interface ViewInterface<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>>{

	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model);
	public void updateSpecification(BuchiAutomaton<S1, T1> specification);
	public void updateIntersection(IntersectionAutomaton<S1,T1,S,T> intersection);
	public void updateVerificationResults(ModelCheckerParameters<S1, S> verificationResults,
			IntersectionAutomaton<S1, T1,S,T> intersection);
	public void addObserver(Observer o);

	public void displayErrorMessage(String message);
	
	public void setBrzozoski(String system);
}
