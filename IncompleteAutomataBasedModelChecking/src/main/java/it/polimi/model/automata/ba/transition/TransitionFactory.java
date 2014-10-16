package it.polimi.model.automata.ba.transition;

import it.polimi.model.automata.ba.transition.labeling.ConjunctiveClause;
import it.polimi.model.automata.ba.transition.labeling.DNFFormula;

import org.apache.commons.collections15.Factory;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class TransitionFactory<T extends LabelledTransition> implements Factory<T>{

	protected static int transitionCount=0;
	
	@Override
	public T create() {
		DNFFormula dnfFormula=new DNFFormula();
		ConjunctiveClause clause=new ConjunctiveClause();
		clause.addProposition(new GraphProposition("SIGMA", false));
		dnfFormula.addDisjunctionClause(clause);
		
		T t=(T) new LabelledTransition(dnfFormula, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	
	public T create(DNFFormula dnfFormula) {
		T t=(T) new LabelledTransition(dnfFormula, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	
	public T create(IGraphProposition proposition) {
		DNFFormula dnfFormula=new DNFFormula();
		ConjunctiveClause clause=new ConjunctiveClause();
		clause.addProposition(proposition);
		dnfFormula.addDisjunctionClause(clause);
		
		
		T t=(T) new LabelledTransition(dnfFormula, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
}
