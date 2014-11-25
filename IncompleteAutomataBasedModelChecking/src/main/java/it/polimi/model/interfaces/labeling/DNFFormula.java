package it.polimi.model.interfaces.labeling;

import java.util.Set;

public interface DNFFormula extends Formula {

	public void addDisjunctionClause(Formula clause);
	public Set<ConjunctiveClause> getConjunctiveClauses();
	
	public void addDisjunctionClauses(Set<ConjunctiveClause> clauses);
}
