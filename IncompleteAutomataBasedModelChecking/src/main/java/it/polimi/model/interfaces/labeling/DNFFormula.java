package it.polimi.model.interfaces.labeling;

public interface DNFFormula extends Formula {

	public void addDisjunctionClause(Formula clause);
}
