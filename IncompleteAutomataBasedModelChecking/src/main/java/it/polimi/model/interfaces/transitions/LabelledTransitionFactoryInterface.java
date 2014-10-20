package it.polimi.model.interfaces.transitions;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.transitions.LabelledTransition;

import java.util.Set;

import org.apache.commons.collections15.Factory;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

public interface LabelledTransitionFactoryInterface<T extends LabelledTransition> extends Factory<T> {

	public T create(IGraphProposition p);
	public T create(Set<ConjunctiveClause> commonClauses);
	public T create(DNFFormula dnfFormula, State s);
}
