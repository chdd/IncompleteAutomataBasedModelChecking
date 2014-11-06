package it.polimi.model.interfaces.transitions;

import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactoryImpl;

import org.apache.commons.collections15.Factory;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * contains the {@link LabelledTransitionFactory} that a {@link LabelledTransitionFactoryImpl} must implement to generate {@link LabelledTransition}s
 * @author claudiomenghi
 */
public interface LabelledTransitionFactory<
	CONSTRAINEDELEMENT extends State,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>> 
	extends Factory<TRANSITION> {

	/**
	 * creates a new transition TRANSITION with a {@link DNFFormula} which contains a single {@link ConjunctiveClauseImpl}
	 * with a single {@link GraphProposition} SIGMA
	 * @return a new transition TRANSITION labeled with a {@link DNFFormula} which contains only the {@link GraphProposition} SIGMA
	 */
	public TRANSITION create();
	
	/**
	 * creates a new {@link LabelledTransition} with the specified {@link DNFFormula}
	 * @param dnfFormula is the {@link DNFFormula} to be added as a label of the {@link LabelledTransition}
	 * @return a new {@link LabelledTransition} with the {@link DNFFormula} as label
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 */
	public TRANSITION create(DNFFormula<CONSTRAINEDELEMENT> dnfFormula);
	
	/**
	 * creates a new {@link LabelledTransition} with the specified {@link DNFFormula}, and the specified id 
	 * @param dnfFormula is the {@link DNFFormula} to be added as a label of the {@link LabelledTransition}
	 * @param id is the if of the {@link LabelledTransition}
	 * @return a new {@link LabelledTransition} with the {@link DNFFormula} as label, and the specified id
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 * @throws IllegalArgumentException if the id is not grater than or equal to zero
	 */
	public TRANSITION create(int id, DNFFormula<CONSTRAINEDELEMENT> dnfFormula);
}