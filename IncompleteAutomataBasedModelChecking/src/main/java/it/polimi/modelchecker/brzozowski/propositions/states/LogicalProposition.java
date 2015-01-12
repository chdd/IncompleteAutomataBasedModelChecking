package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.impl.transitions.Transition;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicalProposition<CONSTRAINTELEMENT, TRANSITION extends Transition>
		extends LogicalItem<CONSTRAINTELEMENT, TRANSITION> {

	
	/**
	 * returns the list of the {@link AbstractProposition} associated with the
	 * {@link AndProposition}
	 * 
	 * @return the list of the {@link AbstractProposition} associated with the
	 *         {@link AndProposition}
	 */
	public abstract List<LogicalItem<CONSTRAINTELEMENT, TRANSITION>> getPredicates();

	

	

	/**
	 * @see {@link AbstractProposition}
	 */
	@Override
	public String toString() {
		String ret = "";
		List<LogicalItem<CONSTRAINTELEMENT, TRANSITION>> value = getPredicates();
			
		for (int i = 0; i < value.size() - 1; i++) {
			ret = ret + "(" + value.get(i).toString() + ")" + this.getType();
		}
		ret = ret + "(" + value.get(value.size() - 1).toString() + ")";
		return ret;
	}

	public abstract String getType();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public abstract int hashCode();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public abstract boolean equals(Object obj);

	

}
