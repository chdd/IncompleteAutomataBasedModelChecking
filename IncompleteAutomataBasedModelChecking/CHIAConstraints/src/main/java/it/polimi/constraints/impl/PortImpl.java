package it.polimi.constraints.impl;

import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Port;

import com.google.common.base.Preconditions;

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
public class PortImpl<S extends State, T extends Transition> extends StateImpl
		implements Port<S, T> {

	

		/**
	 * the source can be a state of a sub0-properties or a state of the model
	 * depending on whether the port is an out-coming or incoming port of the
	 * sub-properties. If the port is out-coming the source is a state of the
	 * sub-property, otherwise it is a state of the model
	 */
	private final S source;
	
	private boolean incoming;

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

	public static int ID_COUNTER=1;

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
	 * @param component
	 *            the component to which the port belongs
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public PortImpl(S source, S destination, T transition, boolean incoming) {
		this(ID_COUNTER, source, destination, transition, incoming);
		
	}
	public PortImpl(int id, S source, S destination, T transition, boolean incoming) {
		super(id);
		PortImpl.ID_COUNTER=PortImpl.ID_COUNTER+1;
		Preconditions.checkNotNull(source, "The source of the port cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination of the port cannot be null");
		Preconditions.checkNotNull(transition,
				"The transition that connect the source and the destination cannot be null");

		this.source = source;
		this.destination = destination;
		this.transition = transition;
		this.incoming=incoming;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getSource() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getDestination() {
		return destination;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getTransition() {
		return transition;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + (incoming ? 1231 : 1237);
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result
				+ ((transition == null) ? 0 : transition.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		PortImpl<S,T> other = (PortImpl<S,T>) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (incoming != other.incoming)
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (transition == null) {
			if (other.transition != null)
				return false;
		} else if (!transition.equals(other.transition))
			return false;
		return true;
	}

	@Override
	public boolean isIncoming() {
		return incoming;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PortImpl [source=" + source + ", incoming=" + incoming
				+ ", destination=" + destination + ", transition=" + transition
				+ "]";
	}
	
	
}
