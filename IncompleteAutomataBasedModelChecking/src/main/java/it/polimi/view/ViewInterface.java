package it.polimi.view;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.interfaces.DrawableBA;
import it.polimi.model.interfaces.DrawableIBA;
import it.polimi.model.interfaces.DrawableIntBA;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.Observer;

public interface ViewInterface<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>>{

	public void updateModel(DrawableIBA<S1, T1> model);
	public void updateSpecification(DrawableBA<S1, T1> specification);
	public void updateIntersection(DrawableIntBA<S1,T1,S,T> intersection);
	public void updateVerificationResults(ModelCheckerParameters<S1, S> verificationResults,
			DrawableIntBA<S1, T1,S,T> intersection);
	public void addObserver(Observer o);

	public void displayErrorMessage(String message);
	
	public void setBrzozoski(String system);
}
