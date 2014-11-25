package it.polimi.model.interfaces.labeling;

import it.polimi.model.impl.labeling.Proposition;



public interface ConjunctiveClause  extends Formula{

	public void addProposition(Proposition proposition);
}
