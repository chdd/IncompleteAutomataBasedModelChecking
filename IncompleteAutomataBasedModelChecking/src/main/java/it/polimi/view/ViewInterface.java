package it.polimi.view;

import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.Observer;

public interface ViewInterface<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	LabelTransitionFactory extends LabelledTransitionFactoryInterface<TRANSITION>,
ConstrainedTransitionFactory extends ConstrainedTransitionFactoryInterface<STATE,INTERSECTIONTRANSITION>>{

	public void updateModel(DrawableIBA<STATE, TRANSITION, LabelTransitionFactory> model);
	public void updateSpecification(DrawableBA<STATE, TRANSITION, LabelTransitionFactory> specification);
	public void updateIntersection(DrawableIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, ConstrainedTransitionFactory> intersection);
	public void updateVerificationResults(ModelCheckerParameters<STATE, INTERSECTIONSTATE> verificationResults,
			DrawableIntBA<STATE, TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, ConstrainedTransitionFactory> intersection);
	public void addObserver(Observer o);

	public void displayErrorMessage(String message);
	
	public void setBrzozoski(String system);
}
