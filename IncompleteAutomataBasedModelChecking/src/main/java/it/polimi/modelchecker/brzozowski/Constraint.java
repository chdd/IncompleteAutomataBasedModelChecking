package it.polimi.modelchecker.brzozowski;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

public class Constraint<STATE extends State, INTERSECTIONTRANSITION extends LabelledTransition<STATE>> {

	private final LogicalItem<STATE, INTERSECTIONTRANSITION> p;
	public Constraint(LogicalItem<STATE, INTERSECTIONTRANSITION> p){
		this.p=p;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
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
		Constraint<STATE, INTERSECTIONTRANSITION> other = (Constraint<STATE, INTERSECTIONTRANSITION>) obj;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "¬("+p.toString()+")";
	}
	
}
