package it.polimi.model.impl.labeling;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.interfaces.labeling.ConjunctiveClause;


public class SigmaProposition extends Proposition implements ConjunctiveClause{

	public SigmaProposition() {
		super("SIGMA", false);
	}

	@Override
	public Set<Proposition> getPropositions() {
		Set<Proposition> propositions=new HashSet<Proposition>();
		propositions.add(this);
		return propositions;
	}

	
}
