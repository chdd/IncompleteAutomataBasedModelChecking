package it.polimi.model.interfaces.transitions;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactoryImpl;

/**
 * contains the {@link ConstrainedTransitionFactory} that a {@link ConstrainedTransitionFactoryImpl} must implement to generate {@link ConstrainedTransition}s
 * @author claudiomenghi
 */
public interface ConstrainedTransitionFactory<
		STATE extends State, 
		TRANSITION extends ConstrainedTransition<STATE>> 
		extends LabelledTransitionFactory<TRANSITION> {

	/**
	 * creates a new transition TRANSITION 
	 * @param dnfFormula the {@link DNFFormula} that must label the transition
	 * @param state the {@link State} that constraints the transition, null if no states are constrained
	 * @return a new {@link ConstrainedTransition}
	 */
	public TRANSITION create(int id, DNFFormula dnfFormula, STATE state);
	
	public TRANSITION create(DNFFormula dnfFormula, STATE state);
}
