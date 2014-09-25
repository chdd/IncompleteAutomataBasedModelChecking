package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

/**
 * @author claudiomenghi
 * contains a {@link LambdaPredicate}. The {@link LambdaPredicate} is used to identify states that are accepting in the BA (final in the FSA)
 */
public class LambdaPredicate<S extends State> extends AbstractPredicate<S> {

	private final String ret="λ";
	
	/**
	 * the concatenation of a {@link LambdaPredicate} predicate with another {@link AbstractPredicate} is equal to the other {@link AbstractPredicate}
	 * @param a: is the {@link AbstractPredicate} to be concatenated
	 * @return the {@link AbstractPredicate} a since the {@link LambdaPredicate} has no effect
	 * @throws IllegalArgumentException is generated when the {@link AbstractPredicate} to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		return a;
	}

	/**
	 * the union of a {@link LambdaPredicate} and the {@link AbstractPredicate} a 
	 * is equal to the {@link LambdaPredicate} if a is an {@link EmptyPredicate} or a {@link LambdaPredicate}
	 * while is equal to a new {@link OrPredicate} that contains the {@link LambdaPredicate} and the {@link AbstractPredicate} a in the other cases
	 * @param the {@link AbstractPredicate} a to be concatenated with the {@link LambdaPredicate}
	 * @throws IllegalArgumentException is generated if the {@link AbstractPredicate} to be concatenated is null
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
	 * @see {@link AbstractPredicate}
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
		LambdaPredicate other = (LambdaPredicate) obj;
		if (ret == null) {
			if (other.ret != null)
				return false;
		} else if (!ret.equals(other.ret))
			return false;
		return true;
	}
	
	

	
	

}
