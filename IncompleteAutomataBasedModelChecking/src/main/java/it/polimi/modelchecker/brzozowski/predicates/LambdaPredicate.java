package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author claudiomenghi
 * contains a lambda predicate. The lambda predicate is used to identify states that are accepting in the BA (final in the FSA)
 */
public class LambdaPredicate<S extends State> extends AbstractPredicate<S> {

	/**
	 * the concatenation of a lambda predicate with another predicate is equal to the other predicate
	 * @param a: is the predicate to be concatenated
	 * @return the predicate a since the lambda operator has no effect
	 * @throws IllegalArgumentException is generated when the predicate to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		return a;
	}

	/**
	 * the union of a lambda predicate and the predicate a is equal to the lambda predicate if a is an {@link EmptyPredicate} or 
	 * to a {@link LambdaPredicate} while is equal to a new {@link OrPredicate} that contains the {@link LambdaPredicate} 
	 * and the predicate a in the other cases
	 * @param the {@link AbstractPredicate} a to be concatenated with the {@link LambdaPredicate}
	 * @throws IllegalArgumentException is generated if the constraint to be concatenated is null
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
				return new OrPredicate<S>(this, a);
			}
		}
	}
	
	/**
	 * the star operator when applied to a {@link LambdaPredicate} returns the {@link LambdaPredicate}
	 * @return the {@link LambdaPredicate}
	 */
	@Override
	public AbstractPredicate<S> star() {
		return this;
	}
	
	/**
	 * The omega operator applied to a {@link LambdaPredicate} returns the {@link LambdaPredicate}
	 * @return the {@link LambdaPredicate}
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}
	
	/**
	 * The simplify operator applied to a {@link LambdaPredicate} returns the {@link LambdaPredicate}
	 * @return the {@link LambdaPredicate}
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

	
	

}
