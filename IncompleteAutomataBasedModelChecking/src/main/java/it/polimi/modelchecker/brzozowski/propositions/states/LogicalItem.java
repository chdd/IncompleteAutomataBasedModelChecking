package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.impl.transitions.Transition;

public abstract class LogicalItem<CONSTRAINTELEMENT, TRANSITION extends Transition> {

	/**
	 * the concatenate method generates the concatenation of the this {@link AbstractProposition}  with the 
	 * {@link AbstractProposition} a
	 * @param a is the {@link AbstractProposition} to be concatenated
	 * @return a new predicate which is the concatenation of this {@link AbstractProposition} and the {@link AbstractProposition} a
	 */
	public abstract LogicalItem<CONSTRAINTELEMENT, TRANSITION> concatenate(LogicalItem<CONSTRAINTELEMENT, TRANSITION> a);
	
	/**
	 * generates the union of this {@link AbstractProposition} and the {@link AbstractProposition} a
	 * @param a the predicate to be "unified" with this
	 * @return a new {@link AbstractProposition} which is equivalent to the union of this {@link AbstractProposition} and the {@link AbstractProposition} a
	 */
	public abstract LogicalItem<CONSTRAINTELEMENT, TRANSITION> union(LogicalItem<CONSTRAINTELEMENT, TRANSITION> a);
	
	/**
	 * applies the star operator to this {@link AbstractProposition}
	 * @return a new {@link AbstractProposition} that is equivalent to this {@link AbstractProposition} where the star operator is applied
	 */
	public abstract LogicalItem<CONSTRAINTELEMENT, TRANSITION> star();
	
	/**
	 * applies the omega operator to this {@link AbstractProposition}
	 * @return a new {@link AbstractProposition} that is equivalent to this {@link AbstractProposition} where the omega operator is applied
	 */
	public abstract LogicalItem<CONSTRAINTELEMENT, TRANSITION> omega();
	
	/**
	 * returns the String representation of this {@link AbstractProposition} 
	 * @return the String representation of this {@link AbstractProposition}
	 */
	public abstract String toString();
	
	/**
	 * @see {@link Object} 
	 */
	@Override
	public abstract boolean equals(Object obj);
	/**
	 * @see {@link Object}
	 */
	@Override
	public abstract int hashCode();
	
	public abstract LogicalItem<CONSTRAINTELEMENT, TRANSITION> simplify();
	
	
}
