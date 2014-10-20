package it.polimi.model.impl.transitions;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;

/**
 * contains a constrainedTransition which is a transition that can be performed only if the constrainedState recognizes 
 * a specific character
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the state of the constrained state (the state of the original model or of the specification
 */
public class ConstrainedTransition<STATE extends State> extends LabelledTransition {

	/**
	 * contains the constrained state
	 */
	private final STATE constrainedState;
	
	/**
	 * creates a new constrained transition
	 * @param c is the character that the constrained transition must be able to recognize
	 * @param to is the destination of the transition in the intersection automaton
	 * @param constrainedState is the state in the original model which is constrained
	 * @throws IllegalArgumentException if the character, the destination or the constrained state is null
	 */
	protected ConstrainedTransition(DNFFormula dnfFormula,STATE constrainedState, int id) {
		super(dnfFormula, id);
		
		this.constrainedState=constrainedState;
	}

	/**
	 * @return the constrainedState: the state of the model or of the transition which must recognize the specific character
	 */
	public STATE getConstrainedState() {
		return constrainedState;
	}
	
	/**
	 * returns the {@link String} representation of the {@link ConstrainedTransition}
	 * @return the {@link String} representation of the {@link ConstrainedTransition}
	 */
	@Override
	public String toString() {
		if(constrainedState!=null){
			return "<"+constrainedState.getName()+","+super.getDnfFormula().toString()+">";
		}
		else{
			return super.toString();
		}
	}
}
