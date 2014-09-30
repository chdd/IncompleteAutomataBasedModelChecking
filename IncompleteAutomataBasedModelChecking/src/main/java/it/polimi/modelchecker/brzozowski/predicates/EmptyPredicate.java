package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.graph.State;

/**
 * @author claudiomenghi
 * contains an {@link EmptyPredicate}.  This predicate is used when no transitions of the (I)BA are present to connect two states
 */
public class EmptyPredicate<S extends State> extends AbstractPredicate<S> {

	private final String ret="∅";
	/**
	 * the concatenation of an {@link EmptyPredicate} with another {@link AbstractPredicate} is an {@link EmptyPredicate}
	 * @param a: is the {@link AbstractPredicate} to be concatenated with this
	 * @return the concatenation of this {@link EmptyPredicate} with the {@link AbstractPredicate}
	 * @throws IllegalArgumentException is generated when the {@link AbstractPredicate} a is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot concatenate an empty contraint with a null element");
		}
		return this;
	}

	/**
	 * the star operator applied to an {@link EmptyPredicate} returns a {@link LambdaPredicate}
	 * @return a new {@link LambdaPredicate}
	 */
	@Override
	public AbstractPredicate<S> star() {
		return new LambdaPredicate<S>();
	}
	
	/**
	 * the omega operator applied to an {@link EmptyPredicate} returns a {@link LambdaPredicate}
	 * @return a new {@link LambdaPredicate}
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return new LambdaPredicate<S>();
	}

	/**
	 * The union operator of an {@link EmptyPredicate} and another {@link AbstractPredicate} a returns the other {@link AbstractPredicate}
	 * @return the {@link AbstractPredicate} a
	 * @throws IllegalArgumentException the {@link AbstractPredicate} a cannot be null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot make the union of an empty contraint with a null element");
		}
		return a;
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
		@SuppressWarnings("unchecked")
		EmptyPredicate<S> other = (EmptyPredicate<S>) obj;
		if (ret == null) {
			if (other.ret != null)
				return false;
		} else if (!ret.equals(other.ret))
			return false;
		return true;
	}
	
	
}
