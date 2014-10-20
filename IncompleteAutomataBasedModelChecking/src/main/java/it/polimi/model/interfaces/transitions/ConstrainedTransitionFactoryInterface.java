package it.polimi.model.interfaces.transitions;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactory;

/**
 * contains the {@link ConstrainedTransitionFactoryInterface} that a {@link ConstrainedTransitionFactory} must implement to generate {@link ConstrainedTransition}s
 * @author claudiomenghi
 */
public interface ConstrainedTransitionFactoryInterface<
		STATE extends State, 
		TRANSITION extends ConstrainedTransition<STATE>> 
		extends LabelledTransitionFactoryInterface<TRANSITION> {

	/**
	 * creates a new transition TRANSITION 
	 * @param dnfFormula the {@link DNFFormula} that must label the transition
	 * @param state the {@link State} that constraints the transition, null if no states are constrained
	 * @return a new {@link ConstrainedTransition}
	 */
	public TRANSITION create(DNFFormula dnfFormula, STATE state);
}
