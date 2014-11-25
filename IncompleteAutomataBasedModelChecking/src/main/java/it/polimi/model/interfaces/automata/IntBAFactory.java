package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

public interface IntBAFactory<
	STATE extends State,
	TRANSITION extends Transition,
	INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition, 
	AUTOMATON extends IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>
	extends
		IBAFactory<INTERSECTIONSTATE, INTERSECTIONTRANSITION, AUTOMATON>{

	public AUTOMATON create(
			IBA<STATE, TRANSITION> model,
			BA<STATE, TRANSITION> specification);
}
