package it.polimi.model.automata.ba.transition;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import it.polimi.model.automata.ba.transition.labeling.ConjunctiveClause;
import it.polimi.model.automata.ba.transition.labeling.DNFFormula;
import it.polimi.model.elements.states.State;

public class ConstrainedTransitionFactory<S extends State, T extends ConstrainedTransition<S>> extends TransitionFactory<T> {

	public T create(DNFFormula dnfFormula, State s) {
		
		T t=(T) new ConstrainedTransition(dnfFormula, s,  TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	@Override
	public T create() {
		
		Set<String> labeling=new HashSet<String>();
		labeling.add("SIGMA");
		DNFFormula dnfFormula=new DNFFormula();
		ConjunctiveClause clause=new ConjunctiveClause();
		clause.addProposition(new GraphProposition("SIGMA", false));
		dnfFormula.addDisjunctionClause(clause);
		
		T t=(T) new ConstrainedTransition(dnfFormula, null, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	
	public T create(DNFFormula dnfFormula) {
		
		T t=(T) new ConstrainedTransition(dnfFormula, null, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	public T create(Set<ConjunctiveClause> commonClauses) {
		
		T t=(T) new ConstrainedTransition(new DNFFormula(), null, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	
	
}


