package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.elements.states.State;
import it.polimi.modelchecker.brzozowski.Constraint;

/**
 * @author claudiomenghi
 * contains an {@link EpsilonProposition} Predicate. This predicate is associated with regular transitions of the (I)BA since these transitions
 * are not relevant in the {@link Constraint} computation
 */
public class EpsilonProposition<S extends State> implements AbstractProposition<S>{

	private final String ret="ε";
	/**
	 * the concatenation of an {@link EmptyProposition} is defined as follows:
	 * -	if a is an {@link EmptyProposition} the {@link EmptyProposition} is returned
	 * -	the concatenation of an {@link EpsilonProposition} and the {@link LambdaProposition} is equal to the {@link EpsilonProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and an {@link EpsilonProposition} is equal to the {@link EpsilonProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and a {@link AtomicProposition} is a new {@link AndProposition} that contains the {@link EpsilonProposition}
	 * 		and the {@link AtomicProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and an {@link AndProposition} is a new {@link AndProposition} that contains the {@link EpsilonProposition}
	 *		and the original {@link AndProposition}
	 * -	the concatenation of an {@link EpsilonProposition} and an {@link OrProposition} is a new {@link AndProposition} that contains the {@link EpsilonProposition}
	 * 		and the original {@link OrProposition}
	 * @param a: is the {@link AbstractProposition} to be concatenated
	 * @return the {@link AbstractProposition} which is the concatenation of the {@link EpsilonProposition} and the {@link AbstractProposition} a
	 * @throws IllegalArgumentException is generated when the {@link AbstractProposition} to be concatenated is null
	 */
	@Override
	public AbstractProposition<S> concatenate(AbstractProposition<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		// if a is an empty predicate the empty predicate is returned
		if(a instanceof EmptyProposition){
			return a;
		}
		// the concatenation of an epsilon predicate and the lambda predicate is equal to the epsilon predicate
		if(a instanceof LambdaProposition){
			return this;
		}
		// the concatenation of an epsilon predicate and an epsilon predicate is equal to the epsilon predicate
		if(a instanceof EpsilonProposition){
			return this;
		}
		// the concatenation of an epsilon predicate and a predicate is a new and predicate that contains the epsilon predicate and the predicate
		if(a instanceof AtomicProposition){
			return new AndProposition<S>(this, a);
		}
		// the concatenation of an epsilon predicate and an and predicate is a new and predicate that contains the epsilon predicate
		// and the original and predicate
		if(a instanceof AndProposition){
			return new AndProposition<S>(this, a);
		}
		// the concatenation of an epsilon predicate and an or predicate is a new and predicate that contains the epsilon predicate
		// and the original or predicate
		if(a instanceof OrProposition){
			return new OrProposition<S>(this, a);
		}
		// is generated if the type of the predicate is not supported by the predicate computation
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}

	/** the union operator applied to an {@link EpsilonProposition} is defined as follows:
	 * -	the union of an {@link EpsilonProposition} and a {@link LambdaProposition} is the {@link LambdaProposition}
	 * -	the union of an {@link EpsilonProposition} and an {@link EmptyProposition} is the {@link EpsilonProposition}
	 * -	the union of an {@link EpsilonProposition} and an {@link EpsilonProposition} is the {@link EpsilonProposition}
	 * -	the union of an {@link EpsilonProposition} and an {@link AtomicProposition} is an {@link OrProposition} that contains the {@link EpsilonProposition} and the {@link AtomicProposition}
	 * -	the union of an {@link EpsilonProposition} and an {@link AndProposition} is an {@link OrProposition} that contains the {@link EpsilonProposition} and the {@link AndProposition} 
	 * @param a: is the {@link AbstractProposition} to be unified
	 * @return the {@link AbstractProposition} which is the union of the {@link EpsilonProposition} and the {@link AbstractProposition} a
	 * @throws IllegalArgumentException is generated when the {@link AbstractProposition} to be concatenated is null
	 */
	@Override
	public AbstractProposition<S> union(AbstractProposition<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		// the union of an epsilon predicate and a lambda predicate is the lambda predicate
		if(a instanceof LambdaProposition){
			return a;
		}
		//  the union of an epsilon predicate and an EmptyPredicate is the epsilon predicate
		if(a instanceof EmptyProposition){
			return this;
		}
		//  the union of an epsilon predicate and an epsilon predicate is the epsilon predicate
		if(a instanceof EpsilonProposition){
			return this;
		}
		//  the union of an epsilon predicate and an epsilon predicate is an or predicate that contains the epsilon predicate and the predicate
		if(a instanceof AtomicProposition){
			return new OrProposition<S>(this, a);
		}
		//  the union of an epsilon predicate and an or predicate is an or predicate that contains the epsilon predicate and the or predicate 
		if(a instanceof OrProposition){
			return new OrProposition<S>(this, a);
		}
		//  the union of an epsilon predicate and an and predicate is an or predicate that contains the epsilon predicate and the and predicate 
		if(a instanceof AndProposition){
			return new OrProposition<S>(this, a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}
	/**
	 * the star operator applied to an {@link EpsilonProposition} returns the {@link EpsilonProposition}
	 * @return the {@link EpsilonProposition}
	 */
	@Override
	public AbstractProposition<S> star() {
		return new EpsilonProposition<S>();
	}
	/**
	 * the omega operator applied to an {@link EpsilonProposition} returns the {@link EpsilonProposition}
	 * @return the {@link EpsilonProposition}
	 */
	@Override
	public AbstractProposition<S> omega() {
		return this;
	}
	

	/**
	 * see {@link AbstractProposition}
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
		EpsilonProposition<S> other = (EpsilonProposition<S>) obj;
		if (ret == null) {
			if (other.ret != null)
				return false;
		} else if (!ret.equals(other.ret))
			return false;
		return true;
	}
	
}
