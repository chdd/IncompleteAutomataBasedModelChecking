package it.polimi.model.impl.labeling;

import it.polimi.model.interfaces.labeling.ConjunctiveClause;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * contains a Conjunctive clause, i.e., a set of propositions
 * {@link IGraphProposition} separated by the and operator
 * 
 * @author claudiomenghi
 * 
 */
public class ConjunctiveClauseImpl implements ConjunctiveClause {

	/**
	 * is the operator of the conjunctive clause
	 */
	private static final String andSymbol = "&&";

	/**
	 * is the set of propositions {@link IGraphProposition} which are separated
	 * by the and operator
	 */
	private Set<Proposition> propositions;

	/**
	 * creates a new conjunctive clause
	 */
	public ConjunctiveClauseImpl() {
		propositions = new HashSet<Proposition>();
	}

	/**
	 * creates a new {@link ConjunctiveClauseImpl} with the single
	 * {@link IGraphProposition} p
	 * 
	 * @param p
	 *            the only proposition of the {@link ConjunctiveClauseImpl}
	 * @throws NullPointerException
	 *             if the {@link IGraphProposition} p is null
	 */
	public ConjunctiveClauseImpl(Proposition p) {
		if (p == null) {
			throw new NullPointerException("The proposition p cannot be null");
		}
		propositions = new HashSet<Proposition>();
		this.propositions.add(p);
	}

	/**
	 * @return the {@link Set} of the propositions ( {@link IGraphProposition})
	 *         of the conjunctive clause
	 */
	public Set<Proposition> getPropositions() {
		return propositions;
	}

	/**
	 * set the {@link IGraphProposition} of the conjunctive clause
	 * 
	 * @param propositions
	 *            contains the set of propositions to be set in the conjunctive
	 *            clause
	 * @throws NullPointerException
	 *             if the set of propositions to be set is null
	 */
	public void setPropositions(Set<Proposition> propositions) {
		if (propositions == null) {
			throw new NullPointerException(
					"The set of propositions to be setted cannot be null");
		}
		this.propositions = propositions;
	}

	/**
	 * add the {@link IGraphProposition} proposition in the set of the
	 * propositions of the conjunctive clause
	 * 
	 * @param proposition
	 *            is the proposition to be added in the set of the propositions
	 * @throws NullPointerException
	 *             if the proposition to be added is null
	 */
	public void addProposition(Proposition proposition) {
		if (proposition == null) {
			throw new NullPointerException(
					"The proposition to be added cannot be null");
		}
		this.propositions.add(proposition);
	}

	/**
	 * convert the conjunctive clause into its {@link String} representation
	 * 
	 * @return the {@link String} description of the
	 *         {@link ConjunctiveClauseImpl}
	 */
	public String toString() {
		if (propositions.isEmpty()) {
			return "";
		}
		if (propositions.size() == 1) {
			return "(" + propositions.iterator().next().toString() + ")";
		}
		Iterator<Proposition> it = this.propositions.iterator();
		String ret = "";
		for (int i = 0; i < this.propositions.size() - 1; i++) {
			ret += it.next().toString() + andSymbol;
		}
		ret += it.next().toString();
		return "(" + ret + ")";
	}

	/**
	 * loads the {@link ConjunctiveClauseImpl} from its {@link String}
	 * representation
	 * 
	 * @param clause
	 *            is the {@link String} representation of the clause
	 * @return the {@link ConjunctiveClauseImpl} loaded from the {@link String}
	 */
	public static ConjunctiveClause loadFromString(String clause) {
		if (clause.startsWith("(")) {
			clause = clause.substring(1, clause.length() - 1);
		}
		clause += andSymbol;
		ConjunctiveClause ret = new ConjunctiveClauseImpl();
		String[] andClauses = clause.split(andSymbol);
		for (int i = 0; i < andClauses.length; i++) {
			ret.addProposition(Proposition.loadProposition(andClauses[i]));
		}
		return ret;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((propositions == null) ? 0 : propositions.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		ConjunctiveClauseImpl other = (ConjunctiveClauseImpl) obj;
		if (propositions == null) {
			if (other.propositions != null)
				return false;
		} else if (!propositions.equals(other.propositions))
			return false;
		return true;
	}

	@Override
	public boolean satisfies(ConjunctiveClause conjunctiveClause) {
		if(conjunctiveClause instanceof SigmaProposition){
			return true;
		}
		for(Proposition p: conjunctiveClause.getPropositions()){
			if(p.isNegated() && this.propositions.contains(new Proposition(p.getLabel(), false))){
				return false;
			}
			if(!p.isNegated() && !this.propositions.contains(p)){
				return false;
			}
		}
		return true;
	}
}
