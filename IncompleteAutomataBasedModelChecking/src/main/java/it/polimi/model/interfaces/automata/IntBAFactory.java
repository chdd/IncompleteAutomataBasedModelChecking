package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactoryImpl;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

public interface IntBAFactory<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	TRANSITION extends Transition,
	TRANSITIONFACTORY extends TransitionFactory<TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends StateFactoryImpl,
	INTERSECTIONTRANSITION extends Transition, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	AUTOMATON extends IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>
	extends
		IBAFactory<INTERSECTIONSTATE, INTERSECTIONTRANSITION, AUTOMATON>{

	public IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> create(
			IBA<STATE, TRANSITION> model,
			BA<STATE, TRANSITION> specification);
}
