package it.polimi.constraints.components;

import it.polimi.automata.state.State;

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
	public Component(State modelState) {
		Preconditions.checkNotNull(modelState,
				"The name of the state cannot be null");
		
		this.id = counter;
		counter++;
		this.modelState = modelState;
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
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getId() + ": " + this.getModelState().getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((modelState == null) ? 0 : modelState.hashCode());
		return result;
	}

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
		if (modelState == null) {
			if (other.modelState != null)
				return false;
		} else if (!modelState.equals(other.modelState))
			return false;
		return true;
	}
}
