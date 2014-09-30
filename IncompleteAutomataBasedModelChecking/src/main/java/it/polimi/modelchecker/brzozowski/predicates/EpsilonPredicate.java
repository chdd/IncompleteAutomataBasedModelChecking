package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.graph.State;

/**
 * @author claudiomenghi
 * contains an {@link EpsilonPredicate} Predicate. This predicate is associated with regular transitions of the (I)BA since these transitions
 * are not relevant in the {@link Constraint} computation
 */
public class EpsilonPredicate<S extends State> extends AbstractPredicate<S>{

	private final String ret="ε";
	/**
	 * the concatenation of an {@link EmptyPredicate} is defined as follows:
	 * -	if a is an {@link EmptyPredicate} the {@link EmptyPredicate} is returned
	 * -	the concatenation of an {@link EpsilonPredicate} and the {@link LambdaPredicate} is equal to the {@link EpsilonPredicate}
	 * -	the concatenation of an {@link EpsilonPredicate} and an {@link EpsilonPredicate} is equal to the {@link EpsilonPredicate}
	 * -	the concatenation of an {@link EpsilonPredicate} and a {@link Predicate} is a new {@link AndPredicate} that contains the {@link EpsilonPredicate}
	 * 		and the {@link Predicate}
	 * -	the concatenation of an {@link EpsilonPredicate} and an {@link AndPredicate} is a new {@link AndPredicate} that contains the {@link EpsilonPredicate}
	 *		and the original {@link AndPredicate}
	 * -	the concatenation of an {@link EpsilonPredicate} and an {@link OrPredicate} is a new {@link AndPredicate} that contains the {@link EpsilonPredicate}
	 * 		and the original {@link OrPredicate}
	 * @param a: is the {@link AbstractPredicate} to be concatenated
	 * @return the {@link AbstractPredicate} which is the concatenation of the {@link EpsilonPredicate} and the {@link AbstractPredicate} a
	 * @throws IllegalArgumentException is generated when the {@link AbstractPredicate} to be concatenated is null
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
			return new AndPredicate<S>(this, a);
		}
		// the concatenation of an epsilon predicate and an or predicate is a new and predicate that contains the epsilon predicate
		// and the original or predicate
		if(a instanceof OrPredicate){
			return new AndPredicate<S>(this, a);
		}
		// is generated if the type of the predicate is not supported by the predicate computation
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}

	/** the union operator applied to an {@link EpsilonPredicate} is defined as follows:
	 * -	the union of an {@link EpsilonPredicate} and a {@link LambdaPredicate} is the {@link LambdaPredicate}
	 * -	the union of an {@link EpsilonPredicate} and an {@link EmptyPredicate} is the {@link EpsilonPredicate}
	 * -	the union of an {@link EpsilonPredicate} and an {@link EpsilonPredicate} is the {@link EpsilonPredicate}
	 * -	the union of an {@link EpsilonPredicate} and an {@link Predicate} is an {@link OrPredicate} that contains the {@link EpsilonPredicate} and the {@link Predicate}
	 * -	the union of an {@link EpsilonPredicate} and an {@link AndPredicate} is an {@link OrPredicate} that contains the {@link EpsilonPredicate} and the {@link AndPredicate} 
	 * @param a: is the {@link AbstractPredicate} to be unified
	 * @return the {@link AbstractPredicate} which is the union of the {@link EpsilonPredicate} and the {@link AbstractPredicate} a
	 * @throws IllegalArgumentException is generated when the {@link AbstractPredicate} to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		// the union of an epsilon predicate and a lambda predicate is the lambda predicate
		if(a instanceof LambdaPredicate){
			return a;
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
			return new OrPredicate<S>(this, a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}
	/**
	 * the star operator applied to an {@link EpsilonPredicate} returns the {@link EpsilonPredicate}
	 * @return the {@link EpsilonPredicate}
	 */
	@Override
	public AbstractPredicate<S> star() {
		return new EpsilonPredicate<S>();
	}
	/**
	 * the omega operator applied to an {@link EpsilonPredicate} returns the {@link EpsilonPredicate}
	 * @return the {@link EpsilonPredicate}
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}
	

	/**
	 * see {@link AbstractPredicate}
	 */
	@Override
	public String toString() {
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ret == null) ? 0 : ret.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		@SuppressWarnings("unchecked")
		EpsilonPredicate<S> other = (EpsilonPredicate<S>) obj;
		if (ret == null) {
			if (other.ret != null)
				return false;
		} else if (!ret.equals(other.ret))
			return false;
		return true;
	}
	
}
