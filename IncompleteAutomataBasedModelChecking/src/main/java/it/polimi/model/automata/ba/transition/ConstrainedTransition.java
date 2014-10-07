package it.polimi.model.automata.ba.transition;

import it.polimi.model.automata.ba.state.State;

import java.util.Set;

/**
 * contains a constrainedTransition which is a transition that can be performed only if the constrainedState recognizes 
 * a specific character
 * @author claudiomenghi
 *
 * @param <S1> is the type of the state of the constrained state (the state of the original model or of the specification
 * @param <S> is the type of the state of the intersection automaton
 */
public class ConstrainedTransition<S1 extends State> extends LabelledTransition {

	/**
	 * contains the constrained state
	 */
	private final S1 constrainedState;
	
	/**
	 * creates a new constrained transition
	 * @param c is the character that the constrained transition must be able to recognize
	 * @param to is the destination of the transition in the intersection automaton
	 * @param constrainedState is the state in the original model which is constrained
	 * @throws IllegalArgumentException if the character, the destination or the constrained state is null
	 */
	protected ConstrainedTransition(Set<String> c,S1 constrainedState, int id) {
		super(c, id);
		if(constrainedState==null){
			throw new IllegalArgumentException("The constrained state cannot be null");
		}
		this.constrainedState=constrainedState;
	}

	/**
	 * @return the constrainedState: the state of the model or of the transition which must recognize the specific character
	 */
	public S1 getConstrainedState() {
		return constrainedState;
	}
	
	@Override
	public String toString() {
		return "<"+constrainedState.getName()+","+super.getCharacter()+">";
	}
}