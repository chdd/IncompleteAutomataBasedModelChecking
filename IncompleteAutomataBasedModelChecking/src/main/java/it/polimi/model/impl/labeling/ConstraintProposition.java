package it.polimi.model.impl.labeling;

import java.util.Set;

import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class ConstraintProposition<STATE> extends GraphProposition implements ConjunctiveClause {

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
		return "<"+constrainedState.toString()+","+super.toString()+">";
	}
	@Override
	public void addProposition(Proposition proposition) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Set<Proposition> getPropositions() {
		// TODO Auto-generated method stub
		return null;
	}
}
