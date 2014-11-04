package it.polimi.model.impl.transitions;

import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * contains the {@link ConstrainedTransitionFactoryImpl} which generates {@link ConstrainedTransition}s
 * @author claudiomenghi
 *
 */
public class ConstrainedTransitionFactoryImpl implements ConstrainedTransitionFactory<State, ConstrainedTransition<State>>{

	/**
	 * creates a new {@link ConstrainedTransition} with the specified {@link State} and {@link DNFFormula}
	 * @param s is the {@link State} constrained by the {@link ConstrainedTransition} (null if no state are constrained)
	 * @param dnfFormula is the {@link DNFFormula} which labels the {@link ConstrainedTransition}
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 * @return a new {@link ConstrainedTransition} with the specified {@link DNFFormula} and the constrained {@link State}	  
	 */
	public ConstrainedTransition<State> create(int id, DNFFormula dnfFormula, State s) {
		if(dnfFormula==null){
			throw new NullPointerException("The DNFFormula to be added at the transition cannot be null");
		}
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, s,  id);
		LabelledTransitionFactoryImpl.transitionCount++;
		return t;
	}
	
	/**
	 * creates a new {@link ConstrainedTransition} with a {@link DNFFormula} which contains a single {@link ConjunctiveClauseImpl} with a single {@link GraphProposition} SIGMA
	 * @return a new {@link ConstrainedTransition} with a {@link DNFFormula} which contains a single {@link ConjunctiveClauseImpl} with a single {@link GraphProposition} SIGMA
	 */
	@Override
	public ConstrainedTransition<State> create() {
		
		Set<String> labeling=new HashSet<String>();
		labeling.add("SIGMA");
		DNFFormula dnfFormula=new DNFFormula();
		dnfFormula.addDisjunctionClause(new SigmaProposition());
		
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, null, LabelledTransitionFactoryImpl.transitionCount);
		LabelledTransitionFactoryImpl.transitionCount++;
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
		
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, null, LabelledTransitionFactoryImpl.transitionCount);
		LabelledTransitionFactoryImpl.transitionCount++;
		return t;
	}

	/**
	 * creates a new {@link ConstrainedTransition} with the specified {@link DNFFormula}, a null {@link State} and the specified id 
	 * @param dnfFormula is the {@link DNFFormula} to be added as a label of the {@link ConstrainedTransition}
	 * @param id is the if of the {@link ConstrainedTransition}
	 * @return a new {@link ConstrainedTransition} with the {@link DNFFormula} as label, a null {@link State} and the specified id
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 * @throws IllegalArgumentException if the id is not grater than or equal to zero
	 */
	@Override
	public ConstrainedTransition<State> create(int id, DNFFormula dnfFormula) {
		if(id<0){
			throw new IllegalArgumentException("The id must be grater than or equal to zero");
		}
		if(dnfFormula==null){
			throw new NullPointerException("The DNFFormula to be added at the transition cannot be null");
		}
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, null, LabelledTransitionFactoryImpl.transitionCount);
		
		LabelledTransitionFactoryImpl.transitionCount=Math.max(id++, LabelledTransitionFactoryImpl.transitionCount++);
		LabelledTransitionFactoryImpl.transitionCount++;
		return t;
	}

	@Override
	public ConstrainedTransition<State> create(DNFFormula dnfFormula,
			State state) {
		ConstrainedTransition<State> t=this.create(LabelledTransitionFactoryImpl.transitionCount, dnfFormula, state);
		this.updateCounter(LabelledTransitionFactoryImpl.transitionCount);
		return t;
	}
	
	private void updateCounter(int id){
		LabelledTransitionFactoryImpl.transitionCount=Math.max(id++, LabelledTransitionFactoryImpl.transitionCount++);
	}
}