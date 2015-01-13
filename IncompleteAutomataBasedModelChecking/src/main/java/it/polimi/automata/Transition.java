package it.polimi.automata;

import it.polimi.model.impl.transitions.TransitionImpl;

/**
 * <p>Represents a transition of an automaton. <br>
 * A transition is identified by an id and is labeled by characters</p>
 * 
 * @author claudiomenghi
 * @see TransitionImpl
 */
public interface Transition {

	/**
	 * returns the id of the transition
	 * 
	 * @return the id of the transition
	 */
	public int getId();
	
}
