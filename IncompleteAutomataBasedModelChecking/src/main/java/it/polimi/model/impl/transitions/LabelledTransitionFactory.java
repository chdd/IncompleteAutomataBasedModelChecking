package it.polimi.model.impl.transitions;

import java.util.Set;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class LabelledTransitionFactory implements LabelledTransitionFactoryInterface<LabelledTransition>{

	protected static int transitionCount=0;
	
	
	@Override
	public LabelledTransition create() {
		DNFFormula dnfFormula=new DNFFormula();
		ConjunctiveClause clause=new ConjunctiveClause();
		clause.addProposition(new GraphProposition("SIGMA", false));
		dnfFormula.addDisjunctionClause(clause);
		
		LabelledTransition t=new LabelledTransition(dnfFormula, LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
	
	public LabelledTransition create(DNFFormula dnfFormula) {
		LabelledTransition t=new LabelledTransition(dnfFormula, LabelledTransitionFactory.transitionCount);
		LabelledTransitionFactory.transitionCount++;
		return t;
	}
	
	public LabelledTransition create(IGraphProposition proposition) {
		DNFFormula dnfFormula=new DNFFormula();
		ConjunctiveClause clause=new ConjunctiveClause();
		clause.addProposition(proposition);
		dnfFormula.addDisjunctionClause(clause);
		
		
		LabelledTransition t=new LabelledTransition(dnfFormula, LabelledTransitionFactory.transitionCount);
		
		LabelledTransitionFactory.transitionCount++;
		return t;
	}

	@Override
	public LabelledTransition create(Set<ConjunctiveClause> commonClauses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LabelledTransition create(DNFFormula dnfFormula, State s) {
		// TODO Auto-generated method stub
		return null;
	}
}
