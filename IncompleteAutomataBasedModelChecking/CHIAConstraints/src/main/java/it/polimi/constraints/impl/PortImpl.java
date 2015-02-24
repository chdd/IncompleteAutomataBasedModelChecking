package it.polimi.constraints.impl;

import org.apache.commons.lang3.Validate;

import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Port;

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
public class PortImpl<S extends State, T extends Transition> extends StateImpl implements Port<S,T>{

	/**
	 * the source can be a state of a sub0-properties or a state of the model
	 * depending on whether the port is an out-coming or incoming port of the
	 * sub-properties. If the port is out-coming the source is a state of the
	 * sub-property, otherwise it is a state of the model
	 */
	private final S source;
	
	/**
	 * the destination can be a state of a sub0-properties or a state of the
	 * model depending on whether the port is an incoming or out-coming port of
	 * the sub-properties. If the port is out-coming the destination is a state
	 * of the model, otherwise it is a state of the sub-property
	 */
	private final S destination;
	
	/**
	 * returns the transition between the source and the destination state
	 */
	private final T transition;

	/**
	 * creates a new port
	 * 
	 * @param source
	 *            is the source of the transition of the port it can be a state
	 *            of the refinement of the model or an already specified state
	 *            of the model
	 * @param destination
	 *            is the destination of the transition of the port it can be a
	 *            state of the refinement of the model or an already specified
	 *            state of the model
	 * @param transition
	 *            is the transition that connect the source with the destination
	 * 
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public PortImpl(S source, S destination, T transition) {
		super(transition.getId());
		Validate.notNull(source, "The source of the port cannot be null");
		Validate.notNull(destination,
				"The destination of the port cannot be null");
		Validate.notNull(transition,
				"The transition that connect the source and the destination cannot be null");

		this.source = source;
		this.destination = destination;
		this.transition = transition;
	}
	/**
	 * {@inheritDoc}
	 */
	public S getSource() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 */
	public S getDestination() {
		return destination;
	}

	/**
	 * {@inheritDoc}
	 */
	public T getTransition() {
		return transition;
	}
}
