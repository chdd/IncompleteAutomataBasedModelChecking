package it.polimi.view;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.BAImpl;
import it.polimi.model.automata.impl.IBAImpl;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.Observer;

public interface ViewInterface<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>>{

	public void updateModel(IBAImpl<S1, T1> model);
	public void updateSpecification(BAImpl<S1, T1> specification);
	public void updateIntersection(IntersectionAutomaton<S1,T1,S,T> intersection);
	public void updateVerificationResults(ModelCheckerParameters<S1, S> verificationResults,
			IntersectionAutomaton<S1, T1,S,T> intersection);
	public void addObserver(Observer o);

	public void displayErrorMessage(String message);
	
	public void setBrzozoski(String system);
}
