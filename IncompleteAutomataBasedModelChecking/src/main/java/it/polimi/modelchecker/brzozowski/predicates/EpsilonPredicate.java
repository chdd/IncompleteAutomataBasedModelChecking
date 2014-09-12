package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author claudiomenghi
 * contains an epsilon Predicate. This predicate is associated with regular transitions of the (I)BA since these transitions
 * are not relevant in the predicate computation
 */
public class EpsilonPredicate<S extends State> extends AbstractPredicate<S>{

	/**
	 * the concatenation of a epsilon predicate is defined as follows:
	 * -	if a is an empty predicate the empty predicate is returned
	 * -	the concatenation of an epsilon predicate and the lambda predicate is equal to the epsilon predicate
	 * -	the concatenation of an epsilon predicate and an epsilon predicate is equal to the epsilon predicate
	 * -	the concatenation of an epsilon predicate and a predicate is a new and predicate that contains the epsilon predicate
	 * 		and the predicate
	 * -	the concatenation of an epsilon predicate and an and predicate is a new and predicate that contains the epsilon predicate
	 *		and the original and predicate
	 * -	the concatenation of an epsilon predicate and an or predicate is a new and predicate that contains the epsilon predicate
	 * 		and the original or predicate
	 * @param a: is the predicate to be concatenated
	 * @return the predicate which is the concatenation of the epsilon predicate and a
	 * @throws IllegalArgumentException is generated when the predicate to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		// if a is an empty predicate the empty predicate is returned
		if(a instanceof EmptyPredicate){
			return a;
		}
		// the concatenation of an epsilon predicate and the lambda predicate is equal to the epsilon predicate
		if(a instanceof LambdaPredicate){
			return this;
		}
		// the concatenation of an epsilon predicate and an epsilon predicate is equal to the epsilon predicate
		if(a instanceof EpsilonPredicate){
			return this;
		}
		// the concatenation of an epsilon predicate and a predicate is a new and predicate that contains the epsilon predicate and the predicate
		if(a instanceof Predicate){
			return new AndPredicate<S>(this, a);
		}
		// the concatenation of an epsilon predicate and an and predicate is a new and predicate that contains the epsilon predicate
		// and the original and predicate
		if(a instanceof AndPredicate){
			return new AndPredicate<S>(new EpsilonPredicate<S>(), ((AndPredicate<S>) a).getConstraints());
		}
		// the concatenation of an epsilon predicate and an or predicate is a new and predicate that contains the epsilon predicate
		// and the original or predicate
		if(a instanceof OrPredicate){
			return new AndPredicate<S>(this, a);
		}
		// is generated if the type of the predicate is not supported by the predicate computation
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}

	
	

	/** the union operator applied to an epsilon predicate is defined as follows:
	 * -	the union of an epsilon predicate and a lambda predicate is the lambda predicate
	 * -	the union of an epsilon predicate and an EmptyPredicate is the epsilon predicate
	 * -	the union of an epsilon predicate and an epsilon predicate is the epsilon predicate
	 * -	the union of an epsilon predicate and an epsilon predicate is an or predicate that contains the epsilon predicate and the predicate
	 * -	the union of an epsilon predicate and an and predicate is an or predicate that contains the epsilon predicate and the and predicate 
	 * @param a: is the predicate to be unified
	 * @return the predicate which is the union of the epsilon predicate and a
	 * @throws IllegalArgumentException is generated when the predicate to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		// the union of an epsilon predicate and a lambda predicate is the lambda predicate
		if(a instanceof LambdaPredicate){
			return new LambdaPredicate<S>();
		}
		//  the union of an epsilon predicate and an EmptyPredicate is the epsilon predicate
		if(a instanceof EmptyPredicate){
			return this;
		}
		//  the union of an epsilon predicate and an epsilon predicate is the epsilon predicate
		if(a instanceof EpsilonPredicate){
			return this;
		}
		//  the union of an epsilon predicate and an epsilon predicate is an or predicate that contains the epsilon predicate and the predicate
		if(a instanceof Predicate){
			return new OrPredicate<S>(this, a);
		}
		//  the union of an epsilon predicate and an or predicate is an or predicate that contains the epsilon predicate and the or predicate 
		if(a instanceof OrPredicate){
			return new OrPredicate<S>(this, a);
		}
		//  the union of an epsilon predicate and an and predicate is an or predicate that contains the epsilon predicate and the and predicate 
		if(a instanceof AndPredicate){
			return new OrPredicate<S>(this, ((AndPredicate<S>) a).getConstraints());
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}
	/**
	 * the star operator applied to an epsilon predicate returns the epsilon predicate
	 * @return the epsilon predicate
	 */
	@Override
	public AbstractPredicate<S> star() {
		return new EpsilonPredicate<S>();
	}
	/**
	 * the omega operator applied to an epsilon predicate returns the epsilon predicate
	 * @return the epsilon predicate
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}
	/**
	 * simplify the predicate. The simplification of the epsilon predicate is the epsilon predicate its-self
	 * @return the epsilon predicate
	 */
	@Override
	public AbstractPredicate<S> simplify() {
		return this;
	}

	/**
	 * see {@link AbstractPredicate}
	 */
	@Override
	public String toString() {
		return "ε";
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof EpsilonPredicate){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 132;
	}
}
