package it.polimi.constraints;

import it.polimi.automata.state.State;

import java.util.Collections;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * The Component class is an abstract class which is used to describe an element
 * (a sub-property or a replacement) which refers to a state of the model. The
 * modelState and the id attributes stores the state of the model associated
 * with the component and its id, respectively. The inComingPorts and the
 * outComingPorts attributes specify how the component is plugged into the
 * model, using ports.
 */
public abstract class Component {

	/**
	 * contains the counter which is used to associate to a new component
	 * (sub-property or replacement) a new id
	 */
	private static int counter = 0;

	/**
	 * contains the id of the component
	 */
	private final int id;

	/**
	 * is the state of the original model to which this component is associated
	 */
	private final State modelState;

	/**
	 * contains the incoming ports of the component
	 */
	private final Set<Port> incomingPorts;

	/**
	 * contains the out-coming ports of the component
	 */
	private final Set<Port> outcomingPorts;

	/**
	 * creates a new component which refers to a specific model state has a set
	 * of incomingPorts and out-comingPorts
	 * 
	 * @param modelState
	 *            is the state of the model to which the component refers to
	 * @param incomingPorts
	 *            is the set of the in-coming ports
	 * @param outcomingPorts
	 *            is the set of the out-coming ports
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public Component(State modelState, Set<Port> incomingPorts,
			Set<Port> outcomingPorts) {
		Preconditions.checkNotNull(modelState,
				"The name of the state cannot be null");
		Preconditions.checkNotNull(incomingPorts,
				"The set of the incoming ports cannot be null");
		Preconditions.checkNotNull(outcomingPorts,
				"The set of the outcoming ports cannot be null");

		this.id = counter;
		counter++;
		this.modelState = modelState;
		this.incomingPorts = incomingPorts;
		this.outcomingPorts = outcomingPorts;
	}

	/**
	 * returns the id of the component
	 * 
	 * @return the id of the component
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * returns the state of the model to which the component refers to
	 * 
	 * @return the state of the model to which the component refers to
	 */
	public State getModelState() {
		return modelState;
	}

	/**
	 * return the set of the out-coming ports of the component
	 * 
	 * @return the outcomingPorts of the component
	 */
	public Set<Port> getOutcomingPorts() {
		return Collections.unmodifiableSet(outcomingPorts);
	}

	/**
	 * return the set of the in-coming ports of the component
	 * 
	 * @return the incomingPorts of the component
	 */
	public Set<Port> getIncomingPorts() {
		return Collections.unmodifiableSet(incomingPorts);
	}

	/**
	 * adds an incoming port to the component
	 * 
	 * @param port is the port to be added as an in-coming port
	 *          
	 * @throws NullPointerException
	 *             if the port is null
	 */
	public void addIncomingPort(Port port) {

		Preconditions.checkNotNull(port, "The port to be added cannot be null");
		this.incomingPorts.add(port);
		port.setColor(Color.YELLOW);
	}

	/**
	 * adds an out-coming port to the component
	 * 
	 * @param port is the port to be added as an out-coming port
	 * @throws NullPointerException
	 *             if the port is null
	 */
	public void addOutComingPort(Port port) {
		
		Preconditions.checkNotNull(port, "The port to be added cannot be null");
		this.outcomingPorts.add(port);
		port.setColor(Color.YELLOW);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getId() + ": " + this.getModelState().getName();
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
		result = prime * result
				+ ((incomingPorts == null) ? 0 : incomingPorts.hashCode());
		result = prime * result
				+ ((modelState == null) ? 0 : modelState.hashCode());
		result = prime * result
				+ ((outcomingPorts == null) ? 0 : outcomingPorts.hashCode());
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
		Component other = (Component) obj;
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
}
