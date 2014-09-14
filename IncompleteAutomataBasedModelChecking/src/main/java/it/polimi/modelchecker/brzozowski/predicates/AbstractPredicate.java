package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author claudiomenghi
 * Is the abstract class which specifies the abstract behavior (methods) of the predicates
 * @param <S> is the type of the states that are involved in the constraints
 */
public abstract class  AbstractPredicate<S extends State>{
	
	/**
	 * the concatenate method generates the concatenation of the this {@link AbstractPredicate}  with the 
	 * {@link AbstractPredicate} a
	 * @param a is the {@link AbstractPredicate} to be concatenated
	 * @return a new predicate which is the concatenation of this {@link AbstractPredicate} and the {@link AbstractPredicate} a
	 */
	public abstract AbstractPredicate<S> concatenate(AbstractPredicate<S> a);
	
	/**
	 * generates the union of this {@link AbstractPredicate} and the {@link AbstractPredicate} a
	 * @param a the predicate to be "unified" with this
	 * @return a new {@link AbstractPredicate} which is equivalent to the union of this {@link AbstractPredicate} and the {@link AbstractPredicate} a
	 */
	public abstract AbstractPredicate<S> union(AbstractPredicate<S> a);
	
	/**
	 * applies the star operator to this {@link AbstractPredicate}
	 * @return a new {@link AbstractPredicate} that is equivalent to this {@link AbstractPredicate} where the star operator is applied
	 */
	public abstract AbstractPredicate<S> star();
	
	/**
	 * applies the omega operator to this {@link AbstractPredicate}
	 * @return a new {@link AbstractPredicate} that is equivalent to this {@link AbstractPredicate} where the omega operator is applied
	 */
	public abstract AbstractPredicate<S> omega();
	
	/**
	 * returns the String representation of this {@link AbstractPredicate} 
	 * @return the String representation of this {@link AbstractPredicate}
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
