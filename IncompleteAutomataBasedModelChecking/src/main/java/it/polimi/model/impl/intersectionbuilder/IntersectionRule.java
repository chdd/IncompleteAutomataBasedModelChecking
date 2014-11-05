package it.polimi.model.impl.intersectionbuilder;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

public abstract class IntersectionRule<
		STATE extends State,
		TRANSITION extends LabelledTransition,
		INTERSECTIONTRANSITION extends LabelledTransition,
		INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>>
	 {
	
	public abstract INTERSECTIONTRANSITION getIntersectionTransition(TRANSITION modelTransition, TRANSITION claimTransition, INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory);

}
