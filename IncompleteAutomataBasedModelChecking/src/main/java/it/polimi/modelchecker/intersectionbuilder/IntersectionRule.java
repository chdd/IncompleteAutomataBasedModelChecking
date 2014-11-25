package it.polimi.modelchecker.intersectionbuilder;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

public abstract class IntersectionRule<CONSTRAINEDELEMENT extends State, STATE extends State, TRANSITION extends Transition, INTERSECTIONTRANSITION extends Transition, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> {

	public abstract INTERSECTIONTRANSITION getIntersectionTransition(
			TRANSITION modelTransition, TRANSITION claimTransition,
			INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory);

}
