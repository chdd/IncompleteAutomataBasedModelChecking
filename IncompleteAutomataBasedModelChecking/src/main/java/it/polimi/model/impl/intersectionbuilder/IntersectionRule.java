package it.polimi.model.impl.intersectionbuilder;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

public abstract class IntersectionRule<
		CONSTRAINEDELEMENT extends State,
		STATE extends State,
		TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
		INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
		INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT,  INTERSECTIONTRANSITION>>
	 {
	
	public abstract INTERSECTIONTRANSITION getIntersectionTransition(TRANSITION modelTransition, TRANSITION claimTransition, INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory);

}
