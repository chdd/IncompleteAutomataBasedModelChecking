package it.polimi.constraints;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import java.util.Set;

import com.google.common.base.Preconditions;

public class Replacement {

	private static int counter = 0;
	/**
	 * contains the id of the sub-property
	 */
	private final int id;

	/**
	 * is the state of the original model to which this component is associated
	 */
	private final State modelState;

	/**
	 * contains the IBA corresponding to the sub-property
	 */
	private final BA automaton;

	private final Set<Port> incomingPorts;
	private final Set<Port> outcomingPorts;

	/**
	 * creates a new sub-property that refers to a specific model state and
	 * contains the corresponding IBA
	 * 
	 * @param modelState
	 *            is the state of the model to which the sub-property
	 *            corresponds with
	 * @param automaton
	 *            is the automaton related with the sub-property
	 * 
	 * @throws NullPointerException
	 *             is generated when the name of the state or when the state of
	 *             the model is null
	 */
	public Replacement(State modelState, BA automaton,
			Set<Port> incomingPorts, Set<Port> outcomingPorts) {
		Preconditions.checkNotNull(modelState,
				"The name of the state cannot be null");
		Preconditions.checkNotNull(automaton,
				"The name of the state cannot be null");

		this.id = counter;
		counter++;
		this.modelState = modelState;
		this.automaton = automaton;
		this.incomingPorts = incomingPorts;
		this.outcomingPorts = outcomingPorts;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	public State getModelState() {
		return modelState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getId() + ": " + this.modelState.getName();
	}

	public String toStringAutomata() {
		return super.toString();
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((automaton == null) ? 0 : automaton.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((incomingPorts == null) ? 0 : incomingPorts.hashCode());
		result = prime * result
				+ ((modelState == null) ? 0 : modelState.hashCode());
		result = prime * result
				+ ((outcomingPorts == null) ? 0 : outcomingPorts.hashCode());
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
		Replacement other = (Replacement) obj;
		if (automaton == null) {
			if (other.automaton != null)
				return false;
		} else if (!automaton.equals(other.automaton))
			return false;
		if (id != other.id)
			return false;
		if (incomingPorts == null) {
			if (other.incomingPorts != null)
				return false;
		} else if (!incomingPorts.equals(other.incomingPorts))
			return false;
		if (modelState == null) {
			if (other.modelState != null)
				return false;
		} else if (!modelState.equals(other.modelState))
			return false;
		if (outcomingPorts == null) {
			if (other.outcomingPorts != null)
				return false;
		} else if (!outcomingPorts.equals(other.outcomingPorts))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public BA getAutomaton() {
		return this.automaton;
	}

	/**
	 * @return the outcomingPorts
	 */
	public Set<Port> getOutcomingPorts() {
		return outcomingPorts;
	}

	/**
	 * @return the incomingPorts
	 */
	public Set<Port> getIncomingPorts() {
		return incomingPorts;
	}
}
