package it.polimi.model.impl.transitions;

import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * contains the {@link LabelledTransitionFactoryImpl} which generates {@link LabelledTransition}s
 * @author claudiomenghi
 *
 */
public class LabelledTransitionFactoryImpl implements LabelledTransitionFactory<LabelledTransition>{

	/**
	 * contains the next id of the {@link LabelledTransition}
	 */
	protected static int transitionCount=0;
	
	/**
	 * creates a new {@link LabelledTransition} with a {@link DNFFormula} which contains a single {@link ConjunctiveClauseImpl}
	 * with a single {@link GraphProposition} SIGMA
	 * @return a new {@link LabelledTransition} labeled with a {@link DNFFormula} which contains only the {@link GraphProposition} SIGMA
	 */
	@Override
	public LabelledTransition create() {
		DNFFormula dnfFormula=new DNFFormula();
		ConjunctiveClauseImpl clause=new ConjunctiveClauseImpl();
		clause.addProposition(new SigmaProposition());
		dnfFormula.addDisjunctionClause(clause);
		
		LabelledTransition t=new LabelledTransition(dnfFormula, LabelledTransitionFactoryImpl.transitionCount);
		LabelledTransitionFactoryImpl.transitionCount++;
		return t;
	}
	
	/**
	 * creates a new {@link LabelledTransition} with the specified {@link DNFFormula}
	 * @param dnfFormula is the {@link DNFFormula} to be added as a label of the {@link LabelledTransition}
	 * @return a new {@link LabelledTransition} with the {@link DNFFormula} as label
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 */
	public LabelledTransition create(DNFFormula dnfFormula) {
		if(dnfFormula==null){
			throw new NullPointerException("The dnfFormula to be added at the LabelledTransition cannot be null");
		}
		LabelledTransition t=new LabelledTransition(dnfFormula, LabelledTransitionFactoryImpl.transitionCount);
		LabelledTransitionFactoryImpl.transitionCount++;
		return t;
	}
	
	/**
	 * creates a new {@link LabelledTransition} with the specified {@link DNFFormula}, and the specified id 
	 * @param dnfFormula is the {@link DNFFormula} to be added as a label of the {@link LabelledTransition}
	 * @param id is the if of the {@link LabelledTransition}
	 * @return a new {@link LabelledTransition} with the {@link DNFFormula} as label, and the specified id
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 * @throws IllegalArgumentException if the id is not grater than or equal to zero
	 */
	public LabelledTransition create(int id, DNFFormula dnfFormula) {
		if(id<0){
			throw new IllegalArgumentException("The id must be grater than or equal to zero");
		}
		if(dnfFormula==null){
			throw new NullPointerException("The dnfFormula to be added at the LabelledTransition cannot be null");
		}
		LabelledTransition t=new LabelledTransition(dnfFormula, id);
		LabelledTransitionFactoryImpl.transitionCount=Math.max(id++, LabelledTransitionFactoryImpl.transitionCount++);
		LabelledTransitionFactoryImpl.transitionCount++;
		return t;
	}
}
