package it.polimi.model.impl.labeling;

import java.util.Set;

import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class ConstraintProposition<STATE extends State> extends GraphProposition implements ConjunctiveClause {

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
		return "<"+Integer.toString(constrainedState.getId())+","+super.toString()+">";
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
	@Override
	public boolean satisfies(ConjunctiveClause conjunctiveClause) {
		// TODO Auto-generated method stub
		return false;
	}
}
