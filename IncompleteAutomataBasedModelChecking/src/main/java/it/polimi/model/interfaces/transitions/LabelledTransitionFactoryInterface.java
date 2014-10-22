package it.polimi.model.interfaces.transitions;

import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactory;

import org.apache.commons.collections15.Factory;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;


/**
 * contains the {@link LabelledTransitionFactoryInterface} that a {@link LabelledTransitionFactory} must implement to generate {@link LabelledTransition}s
 * @author claudiomenghi
 */
public interface LabelledTransitionFactoryInterface<
	TRANSITION extends LabelledTransition> 
	extends Factory<TRANSITION> {

	/**
	 * creates a new transition TRANSITION with a {@link DNFFormula} which contains a single {@link ConjunctiveClause}
	 * with a single {@link GraphProposition} SIGMA
	 * @return a new transition TRANSITION labeled with a {@link DNFFormula} which contains only the {@link GraphProposition} SIGMA
	 */
	public TRANSITION create();
	public TRANSITION create(DNFFormula dnfFormula);
	
	public TRANSITION create(int id, DNFFormula dnfFormula);
}
