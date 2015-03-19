package it.polimi.constraints;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import com.google.common.base.Preconditions;

/**
 * The ports class specifies the in/out-coming transitions that connect the
 * IBA/BA that refers to the sub-property/replacement to the a transparent state
 * to the states of the IBA of the original model<br/>
 * 
 * The port class contains the source and the destinations state of the port, a
 * boolean flag that specifies if the port is in-coming or out-coming and the
 * corresponding transition
 * 
 * @author claudiomenghi
 *
 */
public class Port extends State {

	public static int ID_COUNTER = 1;

	/**
	 * is true if the port is an incoming port, false if it is out-coming
	 */
	private final boolean incoming;

	/**
	 * the source can be a state of a sub0-properties or a state of the model
	 * depending on whether the port is an out-coming or incoming port of the
	 * sub-properties. If the port is out-coming the source is a state of the
	 * sub-property, otherwise it is a state of the model
	 */
	private final State source;

	/**
	 * the destination can be a state of a sub0-properties or a state of the
	 * model depending on whether the port is an incoming or out-coming port of
	 * the sub-properties. If the port is out-coming the destination is a state
	 * of the model, otherwise it is a state of the sub-property
	 */
	private final State destination;

	/**
	 * returns the transition between the source and the destination state
	 */
	private final Transition transition;
	
	
	private Color color;

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
	public Port(State source, State destination, Transition transition,
			boolean incoming, Color color) {
		super(transition.getId());
		Port.ID_COUNTER = Port.ID_COUNTER + 1;
		Preconditions.checkNotNull(source,
				"The source of the port cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination of the port cannot be null");
		Preconditions
				.checkNotNull(transition,
						"The transition that connect the source and the destination cannot be null");

		this.source = source;
		this.destination = destination;
		this.transition = transition;
		this.incoming = incoming;
		this.setColor(color);
	}

	/**
	 * the source can be a state of a sub0-properties or a state of the model
	 * depending on whether the port is an out-coming or incoming port of the
	 * sub-properties. If the port is out-coming the source is a state of the
	 * sub-property, otherwise it is a state of the model
	 * 
	 * @return the source of the port
	 */
	public State getSource() {
		return source;
	}

	/**
	 * the destination can be a state of a sub0-properties or a state of the
	 * model depending on whether the port is an incoming or out-coming port of
	 * the sub-properties. If the port is out-coming the destination is a state
	 * of the model, otherwise it is a state of the sub-property
	 * 
	 * @return the destination of the port
	 */
	public State getDestination() {
		return destination;
	}

	/**
	 * returns the transition between the source and the destination state
	 * 
	 * @return the transition between the source and the destination state
	 */
	public Transition getTransition() {
		return transition;
	}

	/**
	 * returns true if the transition is an incoming port, false if it is
	 * out-coming
	 * 
	 * @return true if the transition is an incoming port, false if it is
	 *         out-coming
	 */
	public boolean isIncoming() {
		return incoming;
	}

	/*
	 * (non-Javadoc)
	 * 
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

	/**
	 * Returns true if two ports are equals two ports are equal if and only if
	 * they refer to the same transition, i.e., a transition with the same id
	 * 
	 * @param obj
	 *            is the Port to which the current port must be compared
	 * @return true if the two ports are equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Port other = (Port) obj;
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

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Port [source=" + source + ", incoming=" + incoming
				+ ", destination=" + destination + ", transition=" + transition
				+ "]";
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
