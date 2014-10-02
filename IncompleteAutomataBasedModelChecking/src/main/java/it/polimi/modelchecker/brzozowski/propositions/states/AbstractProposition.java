package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.graph.State;

/**
 * @author claudiomenghi
 * Is the abstract class which specifies the abstract behavior (methods) of the predicates
 * @param <S> is the type of the states that are involved in the constraints
 */
public interface AbstractProposition<S extends State>{
	
	/**
	 * the concatenate method generates the concatenation of the this {@link AbstractProposition}  with the 
	 * {@link AbstractProposition} a
	 * @param a is the {@link AbstractProposition} to be concatenated
	 * @return a new predicate which is the concatenation of this {@link AbstractProposition} and the {@link AbstractProposition} a
	 */
	public abstract AbstractProposition<S> concatenate(AbstractProposition<S> a);
	
	/**
	 * generates the union of this {@link AbstractProposition} and the {@link AbstractProposition} a
	 * @param a the predicate to be "unified" with this
	 * @return a new {@link AbstractProposition} which is equivalent to the union of this {@link AbstractProposition} and the {@link AbstractProposition} a
	 */
	public abstract AbstractProposition<S> union(AbstractProposition<S> a);
	
	/**
	 * applies the star operator to this {@link AbstractProposition}
	 * @return a new {@link AbstractProposition} that is equivalent to this {@link AbstractProposition} where the star operator is applied
	 */
	public abstract AbstractProposition<S> star();
	
	/**
	 * applies the omega operator to this {@link AbstractProposition}
	 * @return a new {@link AbstractProposition} that is equivalent to this {@link AbstractProposition} where the omega operator is applied
	 */
	public abstract AbstractProposition<S> omega();
	
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
}
