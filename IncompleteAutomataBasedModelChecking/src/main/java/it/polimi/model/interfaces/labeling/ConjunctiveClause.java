package it.polimi.model.interfaces.labeling;

import it.polimi.model.impl.labeling.Proposition;

public interface ConjunctiveClause extends Formula {

	/**
	 * adds the {@link Proposition} proposition to the current {@link ConjunctiveClause}
	 * @param proposition the Proposition to be added to the {@link ConjunctiveClause}
	 * @throws NullPointerException if the {@link Proposition} to be added is null
	 */
	public void addProposition(Proposition proposition);

	/**
	 * returns true if this {@link ConjunctiveClause} satisfies the
	 * {@link ConjunctiveClause} passed as parameter which represents a
	 * condition the current {@link ConjunctiveClause} must satisfy if a
	 * Proposition of this conjunctive is negated is supposed as assigned to a
	 * false value, otherwise it is assigned to true
	 * 
	 * @param conjunctiveClause
	 *            is the {@link ConjunctiveClause} that must be compared with
	 *            this conjunctive clause
	 * @return true if this {@link ConjunctiveClause} satisfies the
	 *         {@link ConjunctiveClause} passed as paramenter
	 * @throws NullPointerException
	 *             if the {@link ConjunctiveClause} passed as parameter is null
	 */
	public boolean satisfies(ConjunctiveClause conjunctiveClause);
}
