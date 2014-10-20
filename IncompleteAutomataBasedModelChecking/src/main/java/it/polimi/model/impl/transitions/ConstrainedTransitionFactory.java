package it.polimi.model.impl.transitions;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;

public class ConstrainedTransitionFactory implements ConstrainedTransitionFactoryInterface<State, ConstrainedTransition<State>>{

	public ConstrainedTransition<State> create(DNFFormula dnfFormula, State s) {
		
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, s,  LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
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
	
	public ConstrainedTransition<State> create(DNFFormula dnfFormula) {
		
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(dnfFormula, null, LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
	public ConstrainedTransition<State> create(Set<ConjunctiveClause> commonClauses) {
		
		ConstrainedTransition<State> t=new ConstrainedTransition<State>(new DNFFormula(), null, LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
	@Override
	public ConstrainedTransition<State> create(IGraphProposition p) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}


