package it.polimi.browzozky.predicates.types;

import it.polimi.browzozky.predicates.AbstractConstraint;
import it.polimi.model.State;

/**
 * @author Claudio Menghi
 * contains an empty constraint
 */
public class EmptyConstraint<S extends State> extends AbstractConstraint<S> {

	/**
	 * the concatenation of an empty constraint with another constraint is an empty constraint
	 * @param a: is the constraint to be concatenated with this
	 * @return the concatenation of this constraint with the empty constraint
	 * @throws IllegalArgumentException the constraint a cannot be null
	 */
	@Override
	public AbstractConstraint<S> concatenate(AbstractConstraint<S> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot concatenate an empty contraint with a null element");
		}
		return this;
	}

	/**
	 * the star operator applied to an empty constraint returns a Lambda constraint
	 * @return a new Lambda constraint
	 */
	@Override
	public AbstractConstraint<S> star() {
		return new LambdaConstraint<S>();
	}

	/**
	 * The union operator of an empty constraint and another constraint a returns the other constraint
	 * @return the constraint a
	 * @throws IllegalArgumentException the constraint a cannot be null
	 */
	@Override
	public AbstractConstraint<S> union(AbstractConstraint<S> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot make the union of an empty contraint with a null element");
		}
		return a;
	}

	/**
	 * @see AbstractConstraint
	 */
	@Override
	public String toString() {
		return "∅";
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof EmptyConstraint){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public AbstractConstraint<S> omega() {
		return this;
	}
	
}
