package it.polimi.constraints;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * Is a transition that connect the refinement of a transparent state with a
 * state already specified in the model
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the states connected by the transition
 * @param <T>
 *            is the type of the transition that connects the states
 */
public interface Port<S extends State, T extends Transition>
		extends State {

	/**
	 * the source can be a state of a sub0-properties or a state of the model
	 * depending on whether the port is an out-coming or incoming port of the
	 * sub-properties. If the port is out-coming the source is a state of the
	 * sub-property, otherwise it is a state of the model
	 * 
	 * @return the source of the port
	 */
	public S getSource();
	
	
	public boolean isIncoming();

	/**
	 * the destination can be a state of a sub0-properties or a state of the
	 * model depending on whether the port is an incoming or out-coming port of
	 * the sub-properties. If the port is out-coming the destination is a state
	 * of the model, otherwise it is a state of the sub-property
	 * 
	 * @return the destination of the port
	 */
	public S getDestination();

	/**
	 * returns the transition between the source and the destination state
	 * 
	 * @return the transition between the source and the destination state
	 */
	public T getTransition();


	/**
	 * Returns true if two ports are equals two ports are equal if and only if
	 * they refer to the same transition, i.e., a transition with the same id
	 * 
	 * @param obj
	 *            is the Port to which the current port must be compared
	 * @return true if the two ports are equals
	 */
	public boolean equals(Object obj);
	
	public String toString();
}
