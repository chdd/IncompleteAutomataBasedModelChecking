package it.polimi.modelchecker.brzozowski.propositions.states;

import java.util.HashSet;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

/**
 * @author claudiomenghi
 * contains an {@link EmptyProposition}.  This predicate is used when no transitions of the (I)BA are present to connect two states
 */
public class EmptyProposition<CONSTRAINTELEMENT extends State, TRANSITION extends LabelledTransition<CONSTRAINTELEMENT>> extends LogicalItem<CONSTRAINTELEMENT, TRANSITION> {

	
	private final String ret="∅";
	/**
	 * the concatenation of an {@link EmptyProposition} with another {@link AbstractProposition} is an {@link EmptyProposition}
	 * @param a: is the {@link AbstractProposition} to be concatenated with this
	 * @return the concatenation of this {@link EmptyProposition} with the {@link AbstractProposition}
	 * @throws IllegalArgumentException is generated when the {@link AbstractProposition} a is null
	 */
	@Override
	public LogicalItem<CONSTRAINTELEMENT, TRANSITION> concatenate(LogicalItem<CONSTRAINTELEMENT, TRANSITION> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot concatenate an empty contraint with a null element");
		}
		return this;
	}

	/**
	 * the star operator applied to an {@link EmptyProposition} returns a {@link LambdaProposition}
	 * @return a new {@link LambdaProposition}
	 */
	@Override
	public LogicalItem<CONSTRAINTELEMENT, TRANSITION> star() {
		return new EpsilonProposition<CONSTRAINTELEMENT, TRANSITION>(new HashSet<TRANSITION>());
		//return new EmptyProposition<CONSTRAINTELEMENT, TRANSITION>();
	}
	
	/**
	 * the omega operator applied to an {@link EmptyProposition} returns a {@link LambdaProposition}
	 * @return a new {@link LambdaProposition}
	 */
	@Override
	public LogicalItem<CONSTRAINTELEMENT, TRANSITION> omega() {
		return new EpsilonProposition<CONSTRAINTELEMENT, TRANSITION>(new HashSet<TRANSITION>());
		//return new EmptyProposition<CONSTRAINTELEMENT, TRANSITION>();
	}

	/**
	 * The union operator of an {@link EmptyProposition} and another {@link AbstractProposition} a returns the other {@link AbstractProposition}
	 * @return the {@link AbstractProposition} a
	 * @throws IllegalArgumentException the {@link AbstractProposition} a cannot be null
	 */
	@Override
	public LogicalItem<CONSTRAINTELEMENT, TRANSITION> union(LogicalItem<CONSTRAINTELEMENT, TRANSITION> a) {
		if(a==null){
			throw new IllegalArgumentException("cannot make the union of an empty contraint with a null element");
		}
		return a;
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
		EmptyProposition<CONSTRAINTELEMENT, TRANSITION> other = (EmptyProposition<CONSTRAINTELEMENT, TRANSITION>) obj;
		if (ret == null) {
			if (other.ret != null)
				return false;
		} else if (!ret.equals(other.ret))
			return false;
		return true;
	}

	@Override
	public LogicalItem<CONSTRAINTELEMENT, TRANSITION> simplify() {
		return this;
	}
	
	
}
