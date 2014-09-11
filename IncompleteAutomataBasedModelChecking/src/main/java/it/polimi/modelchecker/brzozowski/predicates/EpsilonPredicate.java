package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author Claudio Menghi
 * contains the epsilon constraint
 */
public class EpsilonPredicate<S extends State> extends AbstractPredicate<S>{

	/**
	 * the concatenation of a epsilon constraint is defined as follows:
	 * -	if a is an empty constraint the empty constraint is returned
	 * -	the concatenation of an epsilon constraint and the lambda constraint is equal to the epsilon constraint
	 * -	the concatenation of an epsilon constraint and an epsilon constraint is equal to the epsilon constraint
	 * -	the concatenation of an epsilon constraint and a predicate is a new and constraint that contains the epsilon constraint
	 * 		and the predicate
	 * -	the concatenation of an epsilon constraint and an and constraint is a new and constraint that contains the epsilon constraint
	 *		and the original and constraint
	 * -	the concatenation of an epsilon constraint and an or constraint is a new and constraint that contains the epsilon constraint
	 * 		and the original or constraint
	 * @param a: is the constraint to be concatenated
	 * @return the constraint which is the concatenation of the epsilon constraint and a
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint to be concatenated cannot be null");
		}
		// if a is an empty constraint the empty constraint is returned
		if(a instanceof EmptyPredicate){
			return a;
		}
		// the concatenation of an epsilon constraint and the lambda constraint is equal to the epsilon constraint
		if(a instanceof LambdaPredicate){
			return this;
		}
		// the concatenation of an epsilon constraint and an epsilon constraint is equal to the epsilon constraint
		if(a instanceof EpsilonPredicate){
			return this;
		}
		// the concatenation of an epsilon constraint and a predicate is a new and constraint that contains the epsilon constraint and the predicate
		if(a instanceof Predicate){
			return new AndPredicate<S>(this, a);
		}
		// the concatenation of an epsilon constraint and an and constraint is a new and constraint that contains the epsilon constraint
		// and the original and constraint
		if(a instanceof AndPredicate){
			return new AndPredicate<S>(new EpsilonPredicate<S>(), a);
		}
		// the concatenation of an epsilon constraint and an or constraint is a new and constraint that contains the epsilon constraint
		// and the original or constraint
		if(a instanceof OrPredicate){
			return new AndPredicate<S>(this, a);
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	
	/**
	 * the star operator applied to an epsilon constraint returns the epsilon constraint
	 * @return the epsilon constraint
	 */
	@Override
	public AbstractPredicate<S> star() {
		return new EpsilonPredicate<S>();
	}

	/** the union operator applied to an epsilon constraint is defined as follows:
	 * -	the union of an epsilon constraint and a lambda constraint is the lambda constraint
	 * -	the union of an epsilon constraint and an EmptyConstraint is the epsilon constraint
	 * -	the union of an epsilon constraint and an epsilon constraint is the epsilon constraint
	 * -	the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint and the predicate
	 * -	the union of an epsilon constraint and an and constraint is an or constraint that contains the epsilon constraint and the and constraint 
	 * @param a: is the constraint to be unified
	 * @return the constraint which is the union of the epsilon constraint and a
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint to be concatenated cannot be null");
		}
		// the union of an epsilon constraint and a lambda constraint is the lambda constraint
		if(a instanceof LambdaPredicate){
			return new LambdaPredicate<S>();
		}
		//  the union of an epsilon constraint and an EmptyConstraint is the epsilon constraint
		if(a instanceof EmptyPredicate){
			return this;
		}
		//  the union of an epsilon constraint and an epsilon constraint is the epsilon constraint
		if(a instanceof EpsilonPredicate){
			return this;
		}
		//  the union of an epsilon constraint and an epsilon constraint is an or constraint that contains the epsilon constraint and the predicate
		if(a instanceof Predicate){
			return new Predicate<S>(((Predicate<S>) a).getState(), "("+((Predicate<S>) a).getRegularExpression()+")+"+"(λ)");
		}
		//  the union of an epsilon constraint and an or constraint is an or constraint that contains the epsilon constraint and the or constraint 
		if(a instanceof OrPredicate){
			return new OrPredicate<S>(this, a);
		}
		//  the union of an epsilon constraint and an and constraint is an or constraint that contains the epsilon constraint and the and constraint 
		if(a instanceof AndPredicate){
			return new OrPredicate<S>(this, ((AndPredicate<S>) a).getConstraints());
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
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


	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}

}
