package it.polimi.model.impl.labeling;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;

public class ConstraintProposition<STATE extends State> extends GraphProposition implements ConjunctiveClause<STATE> {

	private STATE constrainedState;
	
	public ConstraintProposition(STATE constrainedState) {
		this(constrainedState, "a", false);
	}
	public ConstraintProposition(STATE constrainedState, String label) {
		this(constrainedState, label, false);
	}
	
	public ConstraintProposition(STATE constrainedState, String label, boolean isNegated) {
		super(label, isNegated);
		this.constrainedState=constrainedState;
	}

	/**
	 * @return the constrainedState
	 */
	public STATE getConstrainedState() {
		return constrainedState;
	}

	/**
	 * @param constrainedState the constrainedState to set
	 */
	public void setConstrainedState(STATE constrainedState) {
		this.constrainedState = constrainedState;
	}

	@Override
	public String toString() {
		return "<"+constrainedState.getName()+","+super.toString()+">";
	}
}
