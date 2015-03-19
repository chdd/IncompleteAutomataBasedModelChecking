/**
 * 
 */
package it.polimi.constraints.impl;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.constraints.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Contains a sub-property. A component is a sub part of the intersection
 * automaton that corresponds with a transparent state of the model and will
 * generate a constraint
 * 
 * @author claudiomenghi
 * 
 */
public class SubProperty {

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
	 * specifies for each port the corresponding color The red color means that
	 * from this port it is possible to reach an accepting state The green color
	 * means that the port is reachable from an initial state The yellow color
	 * means that the port is possibly reachable from an initial state and from
	 * the port it is possibly reachable an accepting state
	 */
	private Map<Port, Color> portValue;
	
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
	public SubProperty(State modelState, BA automaton,
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
		this.portValue=new HashMap<Port, Color>();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		SubProperty other = (SubProperty) obj;
		if (id != other.id)
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
	
	/**
	 * adds an incoming port to the component
	 * 
	 * @param source
	 *            is the source state of the port
	 * @param transition
	 *            is the transition that connect the source state to the
	 *            destination state, i.e., the state of the automaton that
	 *            refines the component
	 * @param destination
	 *            is the state of the automaton that refines the component to be
	 *            connected
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the destination state is not a state of the component
	 */
	public void addIncomingPort(Port port) {
	
		this.incomingPorts.add(port);
		this.portValue.put(port, Color.YELLOW);
	}

	/**
	 * adds an out-coming port to the component
	 * 
	 * @param source
	 *            is the source state of the port, i.e., the state that refines
	 *            the component to be connected
	 * @param transition
	 *            is the transition that connect the source state (i.e., the
	 *            state of the automaton that refines the component) to the
	 *            destination state
	 * @param destination
	 *            is the destination state of the port
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the source state is not a state of the component
	 */
	public void addOutComingPort( Port port) {
		
		this.outcomingPorts.add(port);
		this.portValue.put(port, Color.YELLOW);
	}

}
