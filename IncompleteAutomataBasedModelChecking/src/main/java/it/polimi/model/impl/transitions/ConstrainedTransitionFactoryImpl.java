package it.polimi.model.impl.transitions;

import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.ConstraintProposition;
import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.DNFFormula;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * contains the {@link ConstrainedTransitionFactoryImpl} which generates {@link ConstrainedTransition}s
 * @author claudiomenghi
 *
 */
public class ConstrainedTransitionFactoryImpl extends TransitionFactoryImpl
implements ConstrainedTransitionFactory<State,  Transition>{

	
	
	/**
	 * creates a new {@link ConstrainedTransition} with a {@link DNFFormula} which contains a single {@link ConjunctiveClauseImpl} with a single {@link GraphProposition} SIGMA
	 * @return a new {@link ConstrainedTransition} with a {@link DNFFormula} which contains a single {@link ConjunctiveClauseImpl} with a single {@link GraphProposition} SIGMA
	 */
	@Override
	public Transition create() {
		
		Set<String> labeling=new HashSet<String>();
		labeling.add("SIGMA");
		DNFFormula dnfFormula=new DNFFormulaImpl();
		dnfFormula.addDisjunctionClause(new SigmaProposition<State>());
		
		Transition t=new Transition(dnfFormula,  TransitionFactoryImpl.transitionCount);
		this.updateCounter(TransitionFactoryImpl.transitionCount);
		return t;
	}
	
	/**
	 * creates a new {@link ConstrainedTransition} with the specified {@link DNFFormula} and a null {@link State}
	 * @param dnfFormula is the {@link DNFFormula} to be added as a label of the {@link ConstrainedTransition}
	 * @return a new {@link ConstrainedTransition} with the {@link DNFFormula} as label and a null {@link State}
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 */
	public Transition create(DNFFormula dnfFormula) {
		if(dnfFormula==null){
			throw new NullPointerException("The DNFFormula to be added at the transition cannot be null");
		}
		
		
		
		Transition t=new Transition(dnfFormula, TransitionFactoryImpl.transitionCount);
		this.updateCounter(TransitionFactoryImpl.transitionCount);
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
	public Transition create(int id, DNFFormula dnfFormula) {
		if(id<0){
			throw new IllegalArgumentException("The id must be grater than or equal to zero");
		}
		if(dnfFormula==null){
			throw new NullPointerException("The DNFFormula to be added at the transition cannot be null");
		}
		Transition t=new Transition(dnfFormula, id);
		
		this.updateCounter(id);;
		return t;
	}

	
	
	
	@Override
	public Transition create(DNFFormula dnfFormula, State state) {
		return this.create(TransitionFactoryImpl.transitionCount, dnfFormula, state);
	}

	@Override
	public Transition create(int id, DNFFormula dnfFormula, State state) {
		Transition t=new Transition(new DNFFormulaImpl(new ConstraintProposition<State>(state, dnfFormula.toString())), id);
		
		
		this.updateCounter(id);
		return t;
	}
}