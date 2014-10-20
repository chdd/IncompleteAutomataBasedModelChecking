package it.polimi.model.impl.transitions;

import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * contains the {@link ConstrainedTransitionFactory} which generates {@link ConstrainedTransition}s
 * @author claudiomenghi
 *
 */
public class ConstrainedTransitionFactory implements ConstrainedTransitionFactoryInterface<State, ConstrainedTransition<State>>{

	/**
	 * creates a new {@link ConstrainedTransition} with the specified {@link State} and {@link DNFFormula}
	 * @param s is the {@link State} constrained by the {@link ConstrainedTransition} (null if no state are constrained)
	 * @param dnfFormula is the {@link DNFFormula} which labels the {@link ConstrainedTransition}
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 * @return a new {@link ConstrainedTransition} with the specified {@link DNFFormula} and the constrained {@link State}	  
	 */
	public ConstrainedTransition<State> create(DNFFormula dnfFormula, State s) {
		if(dnfFormula==null){
			throw new NullPointerException("The DNFFormula to be added at the transition cannot be null");
		}
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, s,  LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
	
	/**
	 * creates a new {@link ConstrainedTransition} with a {@link DNFFormula} which contains a single {@link ConjunctiveClause} with a single {@link GraphProposition} SIGMA
	 * @return a new {@link ConstrainedTransition} with a {@link DNFFormula} which contains a single {@link ConjunctiveClause} with a single {@link GraphProposition} SIGMA
	 */
	@Override
	public ConstrainedTransition<State> create() {
		
		Set<String> labeling=new HashSet<String>();
		labeling.add("SIGMA");
		DNFFormula dnfFormula=new DNFFormula();
		ConjunctiveClause clause=new ConjunctiveClause();
		clause.addProposition(new GraphProposition("SIGMA", false));
		dnfFormula.addDisjunctionClause(clause);
		
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, null, LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
	
	/**
	 * creates a new {@link ConstrainedTransition} with the specified {@link DNFFormula} and a null {@link State}
	 * @param dnfFormula is the {@link DNFFormula} to be added as a label of the {@link ConstrainedTransition}
	 * @return a new {@link ConstrainedTransition} with the {@link DNFFormula} as label and a null {@link State}
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 */
	public ConstrainedTransition<State> create(DNFFormula dnfFormula) {
		if(dnfFormula==null){
			throw new NullPointerException("The DNFFormula to be added at the transition cannot be null");
		}
		
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, null, LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
}


