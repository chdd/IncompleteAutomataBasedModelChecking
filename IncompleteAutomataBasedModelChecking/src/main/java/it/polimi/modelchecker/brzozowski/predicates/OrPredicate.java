package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author claudiomenghi
 * contains an OrPredicate
 */
public class OrPredicate<S extends State> extends LogicalPredicate<S> {

	private String type="v";
	
	/**
	 * creates a new {@link OrPredicate} that contains the two {@link AbstractPredicate} firstPredicate, secondPredicate 
	 * @param firstPredicate is the first {@link AbstractPredicate} to be included in the {@link OrPredicate}
	 * @param secondPredicate is the second {@link AbstractPredicate} included in the {@link OrPredicate}
	 * @throws IllegalArgumentException is generated if the first or the second {@link AbstractPredicate} are null
	 * 												 if the first and the second {@link AbstractPredicate} are equals
	 * 							
	 */
	 public OrPredicate(AbstractPredicate<S> firstPredicate, AbstractPredicate<S> secondPredicate){
		super(firstPredicate, secondPredicate);
	 }
	 
	 /**
	 * the concatenation of an {@link OrPredicate} is defined as follows:
	 * -	if a is an {@link EmptyPredicate} the {@link OrPredicate} is returned
	 * -	if a is a {@link LambdaPredicate} the {@link OrPredicate} is returned
	 * -	if a is an {@link EpsilonPredicate} the {@link AndPredicate}  of this {@link OrPredicate} and the {@link EpsilonPredicate} is returned
	 * -	if a is a {@link Predicate} the concatenation of the {@link OrPredicate}  and  the {@link Predicate} is returned
	 * -	if a is an {@link AndPredicate} the concatenation of the {@link OrPredicate} and the {@link AndPredicate} is returned
	 * -	if a is an {@link OrPredicate} the concatenation of the {@link OrPredicate} and the {@link OrPredicate} a is returned
	 * @param a: is the constraint to be concatenated
	 * @return the constraint which is the concatenation of the or constraint and a
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		//System.out.println("or concatenate");
		if(a==null){
			throw new IllegalArgumentException("the constraint a cannot be null");
		}
		// if a is an empty constraint the or constraint is returned
		if(a instanceof EmptyPredicate){
			return a;
		}
		// if a is an lambda constraint the or constraint is returned
		if(a instanceof LambdaPredicate){
			return this;
		}
		// if a is an EpsilonConstraint concatenation of the or constraint and EpsilonConstraint is returned
		if(a instanceof EpsilonPredicate){
			return new AndPredicate<S>(this, a);
		}
		// if a is a Predicate the concatenation of the or constraint and Predicate is returned
		if(a instanceof Predicate){
			return new AndPredicate<S>(this, a);
		}
		// if a is an AndConstraint the concatenation of the or constraint and the AndConstraint is returned
		if(a instanceof AndPredicate){
			return new AndPredicate<S>(this, a);
		}
		// if a is an OrConstraint the concatenation of the or constraint and the OrConstraint is returned
		if(a instanceof OrPredicate){
			return new AndPredicate<S>(this, a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	/** the union operator applied to an {@link OrPredicate}  is defined as follows:
	 * -	the union of an {@link OrPredicate} and an {@link EmptyPredicate} returns the {@link OrPredicate}
	 * -	the union of an {@link OrPredicate} and a {@link LambdaPredicate} is a new {@link OrPredicate} that contains the {@link OrPredicate} and {@link LambdaPredicate}
	 * -	the union of an {@link OrPredicate} and an {@link EpsilonPredicate} is a new {@link OrPredicate} that contains the {@link OrPredicate}  and the {@link EpsilonPredicate}
	 * -	the union of an {@link OrPredicate} and a {@link Predicate} is a new {@link OrPredicate} that contains the {@link OrPredicate} and the {@link Predicate}
	 * -	the union of an {@link OrPredicate} and an {@link OrPredicate} is a new {@link OrPredicate} that contains the two {@link OrPredicate} 
	 * -	the union of an {@link OrPredicate}  and an {@link AndPredicate} is a new {@link OrPredicate} that contains the {@link OrPredicate} and the {@link AndPredicate}
	 * @param a: is the {@link AbstractPredicate} to be unified
	 * @return the {@link AbstractPredicate} which is the union of the {@link OrPredicate} and a
	 * @throws IllegalArgumentException is generated when the {@link AbstractPredicate} to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		//System.out.println("or union");
		
		if(a==null){
			throw new IllegalArgumentException("The constraint to be concatenated cannot be null");
		}
		// the union of an or constraint and an empty constraint returns the or constraint
		if(a instanceof EmptyPredicate){
			return this;
		}
		// the union of an or constraint and a LambdaConstraint is a new orConstraint that contains the or constraint and lambda
		if(a instanceof LambdaPredicate){
			return new OrPredicate<S>(this, a);
		}
		// the union of an or constraint and an EpsilonConstrain is a new orConstraint that contains the or constraint and the EpsilonConstrain
		if(a instanceof EpsilonPredicate){
			return new OrPredicate<S>(this, a);
		}
		// the union of an or constraint and a Predicate is a new orConstraint that contains the or constraint and the Predicate
		if(a instanceof Predicate){
			return new OrPredicate<S>(this, a);
		}
		// the union of an or constraint and an orConstraint is a new orConstraint that contains the two or constraints
		if(a instanceof OrPredicate){
			return new OrPredicate<S>(this,a);
		}
		// the union of an or constraint and an andConstraint is a new orConstraint that contains the or constraint and the andConstraint
		if(a instanceof AndPredicate){
			return new OrPredicate<S>(this, a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}
	
	/**
	 * the star operator when applied to an or constraint does not produce any effect
	 * @return the or constraint
	 */
	@Override
	public AbstractPredicate<S> star() {
		return this;
	}

	/**
	 * the star operator when applied to an {@link OrPredicate} does not produce any effect
	 * @return the or constraint
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}

	public String getType(){
		return this.type;
	}

	/**
	 * @see {@link Object}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * @see {@link Object}
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
		OrPredicate<S> other = (OrPredicate<S>) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
		
}
