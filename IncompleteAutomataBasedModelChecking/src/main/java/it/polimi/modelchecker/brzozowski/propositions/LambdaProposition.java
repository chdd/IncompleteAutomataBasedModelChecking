package it.polimi.modelchecker.brzozowski.propositions;

import it.polimi.model.graph.State;
import it.polimi.modelchecker.brzozowski.propositions.state.OrProposition;

/**
 * @author claudiomenghi
 * contains a {@link LambdaProposition}. The {@link LambdaProposition} is used to identify states that are accepting in the BA (final in the FSA)
 */
public class LambdaProposition<S extends State> extends AbstractProposition<S> {

	private final String ret="λ";
	
	/**
	 * the concatenation of a {@link LambdaProposition} predicate with another {@link AbstractProposition} is equal to the other {@link AbstractProposition}
	 * @param a: is the {@link AbstractProposition} to be concatenated
	 * @return the {@link AbstractProposition} a since the {@link LambdaProposition} has no effect
	 * @throws IllegalArgumentException is generated when the {@link AbstractProposition} to be concatenated is null
	 */
	@Override
	public AbstractProposition<S> concatenate(AbstractProposition<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The predicate to be concatenated cannot be null");
		}
		return a;
	}

	/**
	 * the union of a {@link LambdaProposition} and the {@link AbstractProposition} a 
	 * is equal to the {@link LambdaProposition} if a is an {@link EmptyProposition} or a {@link LambdaProposition}
	 * while is equal to a new {@link OrProposition} that contains the {@link LambdaProposition} and the {@link AbstractProposition} a in the other cases
	 * @param the {@link AbstractProposition} a to be concatenated with the {@link LambdaProposition}
	 * @throws IllegalArgumentException is generated if the {@link AbstractProposition} to be concatenated is null
	 */
	@Override
	public AbstractProposition<S> union(AbstractProposition<S> a) {
		if(a==null){
			throw new IllegalArgumentException("the constraint a cannot be null");
		}
		if(a instanceof EmptyProposition){
			return this;
		}
		else{
			if(a instanceof LambdaProposition)
				return this;
			else{
				return new OrProposition<S>(this, a);
			}
		}
	}
	
	/**
	 * the star operator when applied to a {@link LambdaProposition} returns the {@link LambdaProposition}
	 * @return the {@link LambdaProposition}
	 */
	@Override
	public AbstractProposition<S> star() {
		return this;
	}
	
	/**
	 * The omega operator applied to a {@link LambdaProposition} returns the {@link LambdaProposition}
	 * @return the {@link LambdaProposition}
	 */
	@Override
	public AbstractProposition<S> omega() {
		return this;
	}
	
	/**
	 * @see {@link AbstractProposition}
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
		LambdaProposition<S> other = (LambdaProposition<S>) obj;
		if (ret == null) {
			if (other.ret != null)
				return false;
		} else if (!ret.equals(other.ret))
			return false;
		return true;
	}
	
	

	
	

}
