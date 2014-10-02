package it.polimi.modelchecker.brzozowski.propositions.regex;


/**
 * @author claudiomenghi
 * Is the abstract class which specifies the abstract behavior (methods) of the predicates
 */
public interface  AbstractRegexProposition{
	
	/**
	 * the concatenate method generates the concatenation of the this {@link AbstractRegexProposition}  with the 
	 * {@link AbstractRegexProposition} a
	 * @param a is the {@link AbstractRegexProposition} to be concatenated
	 * @return a new predicate which is the concatenation of this {@link AbstractRegexProposition} and the {@link AbstractRegexProposition} a
	 */
	public abstract AbstractRegexProposition concatenate(AbstractRegexProposition a);
	
	/**
	 * generates the union of this {@link AbstractRegexProposition} and the {@link AbstractRegexProposition} a
	 * @param a the predicate to be "unified" with this
	 * @return a new {@link AbstractRegexProposition} which is equivalent to the union of this {@link AbstractRegexProposition} and the {@link AbstractRegexProposition} a
	 */
	public abstract AbstractRegexProposition union(AbstractRegexProposition a);
	
	/**
	 * applies the star operator to this {@link AbstractRegexProposition}
	 * @return a new {@link AbstractRegexProposition} that is equivalent to this {@link AbstractRegexProposition} where the star operator is applied
	 */
	public abstract AbstractRegexProposition star();
	
	/**
	 * applies the omega operator to this {@link AbstractRegexProposition}
	 * @return a new {@link AbstractRegexProposition} that is equivalent to this {@link AbstractRegexProposition} where the omega operator is applied
	 */
	public abstract AbstractRegexProposition omega();
	
	/**
	 * returns the String representation of this {@link AbstractRegexProposition} 
	 * @return the String representation of this {@link AbstractRegexProposition}
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
