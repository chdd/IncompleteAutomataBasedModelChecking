package it.polimi.model.interfaces.transitions;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactoryImpl;
import it.polimi.model.impl.transitions.LabelledTransition;

/**
 * contains the {@link ConstrainedTransitionFactory} that a {@link ConstrainedTransitionFactoryImpl} must implement to generate {@link ConstrainedTransition}s
 * @author claudiomenghi
 */
public interface ConstrainedTransitionFactory<
		CONSTRAINEDELEMENT extends State,
		TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>> 
		extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION> {

	
	
	public TRANSITION create(DNFFormula<CONSTRAINEDELEMENT> dnfFormula, CONSTRAINEDELEMENT state);
	public TRANSITION create(int id, DNFFormula<CONSTRAINEDELEMENT> dnfFormula, CONSTRAINEDELEMENT state);
}
