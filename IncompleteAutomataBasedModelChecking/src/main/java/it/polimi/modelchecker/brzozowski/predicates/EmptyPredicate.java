package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author claudiomenghi
 * contains an empty predicate.  This predicate is used when no transitions of the (I)BA are present to connect two states
 */
public class EmptyPredicate<S extends State> extends AbstractPredicate<S> {

	/**
	 * the concatenation of an empty predicate with another predicate is an empty predicate
	 * @param a: is the predicate to be concatenated with this
	 * @return the concatenation of this predicate with the empty predicate
	 * @throws IllegalArgumentException is generated when the predicate a is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot concatenate an empty contraint with a null element");
		}
		return this;
	}

	/**
	 * the star operator applied to an empty predicate returns a Lambda predicate
	 * @return a new Lambda predicate
	 */
	@Override
	public AbstractPredicate<S> star() {
		return new LambdaPredicate<S>();
	}
	
	/**
	 * the omega operator applied to an empty predicate returns a Lambda predicate
	 * @return a new LambdaPredicate
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return new LambdaPredicate<S>();
	}

	/**
	 * The union operator of an empty predicate and another predicate a returns the other predicate
	 * @return the predicate a
	 * @throws IllegalArgumentException the predicate a cannot be null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot make the union of an empty contraint with a null element");
		}
		return a;
	}
	
	/**
	 * the simplification of the empty predicate is the empty predicate its-self
	 * @return the {@link EmptyPredicate}
	 */
	@Override
	public AbstractPredicate<S> simplify() {
		return this;
	}
	
	/**
	 * @see AbstractPredicate
	 */
	@Override
	public String toString() {
		return "∅";
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof EmptyPredicate){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
