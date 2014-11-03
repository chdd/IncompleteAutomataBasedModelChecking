package it.polimi.model.interfaces.labeling;

import it.polimi.model.impl.labeling.Proposition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

public interface ConjunctiveClause {

	/**
	 * @return the {@link Set} of the propositions ( {@link IGraphProposition}) of the conjunctive clause
	 */
	public abstract Set<Proposition> getPropositions();
}
