package it.polimi.model.interfaces.automata.drawable;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

/**
 * is the interface the {@link DrawableIntBA} the drawable intersection automaton must implement
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the two original automata
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the two original automata
 * @param <INTERSECTIONSTATE> is the type of the states of the {@link IntersectionState} that is the state that i generated from the two original automata
 * @param <INTERSECTIONTRANSITION> is the type of the {@link ConstrainedTransition} which is generate is the intersection of a Buchi and an Incomplete Buchi automata
 */
public interface DrawableIntBA<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION  extends LabelledTransition<CONSTRAINEDELEMENT>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> 
	extends IIntBA<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>,
	DrawableIBA<CONSTRAINEDELEMENT, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> {

}
