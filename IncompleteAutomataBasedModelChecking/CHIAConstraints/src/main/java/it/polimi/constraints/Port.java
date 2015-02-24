package it.polimi.constraints;

import it.polimi.automata.IBA;
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
public class Port<S extends State, T extends Transition> {

	private final S source;
	private final S destination;
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
	 * @throws NullPointerException
	 *             if the source, the destination or the transition is null
	 */
	public Port(S source, S destination, T transition, IBA<S, T> model) {
		if (source == null) {
			throw new NullPointerException(
					"The source of the port cannot be null");
		}
		if (destination == null) {
			throw new NullPointerException(
					"The destination of the port cannot be null");
		}
		if (transition == null) {
			throw new NullPointerException(
					"The transition that connect the source and the destination cannot be null");
		}
		if (model == null) {
			throw new NullPointerException(
					"The model to be considered cannot be null");
		}
		if (!(model.getStates().contains(source) || model.getStates().contains(
				destination))) {
			throw new IllegalArgumentException(
					"At least one between the source and the destination must be contained into the states of the model");
		}
		this.source = source;
		this.destination = destination;
		this.transition = transition;
	}

	/**
	 * @return the source
	 */
	public S getSource() {
		return source;
	}

	/**
	 * @return the destination
	 */
	public S getDestination() {
		return destination;
	}

	/**
	 * @return the transition
	 */
	public T getTransition() {
		return transition;
	}

}
