package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author Claudio Menghi
 * contains a lambda constraint
 */
public class LambdaPredicate<S extends State> extends AbstractPredicate<S> {

	/**
	 * the concatenation of a lambda constraint with another constraint is equal to the other constraint
	 * @param a: is the constraint to be concatenated
	 * @return the constraint a since the lambda operator has no effect
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("the constraint a cannot be null");
		}
		return a;
	}

	/**
	 * the star operator when applied to a lambda constraint returns the lambda constraint
	 * @return the lambda constraint
	 */
	@Override
	public AbstractPredicate<S> star() {
		return this;
	}

	/**
	 * the union of a lambda constraint and the constraint a is
	 *  equal to the lambda constraint if a is empty
	 *  is equal to a new OrConstraint that contains the lambda constraint and the constraint a in the other cases
	 *	@param the constraint a to be concatenated with the Lambda constraint
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("the constraint a cannot be null");
		}
		if(a instanceof EmptyPredicate){
			return this;
		}
		else{
			if(a instanceof LambdaPredicate)
				return this;
			else{
				return new OrConstraint<>(this, a);
			}
		}
	}

	/**
	 * see {@link AbstractPredicate}
	 */
	@Override
	public String toString() {
		return "λ";
	}
	/**
	 * see {@link Object}
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof LambdaPredicate){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 555;
	}

	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}
	

}
