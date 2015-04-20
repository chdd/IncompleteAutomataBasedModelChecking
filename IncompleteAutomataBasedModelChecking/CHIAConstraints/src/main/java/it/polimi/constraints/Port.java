package it.polimi.constraints;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import com.google.common.base.Preconditions;

/**
 * The Port class is used to describe how the IBA/BA that refers to the
 * sub-property/replacement to the a transparent state of the model is connected
 * with the states of the original model. The port class stores in the source,
 * destination and transition attributes, the source and the destinations state
 * of the port, and the corresponding transition. Depending on whether the port
 * represents an in-coming or an out-coming transition the source or the
 * destination state corresponds with a state of the model. The port is also
 * associated with a color which specifies whether from the port is possible to
 * reach an accepting state or is reached from an initial state.
 * 
 * The port class contains the source and the destinations state of the port, a
 * boolean flag that specifies if the port is in-coming or out-coming and the
 * corresponding transition
 * 
 * @author claudiomenghi
 *
 */
public class Port extends State {

	/**
	 * Is the counter which is used to associate to each new port a new Id
	 */
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

	/**
	 * The port is also associated with a color which specifies whether from the
	 * port is possible to reach an accepting state or is reached from an
	 * initial state.
	 */
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
	 * returns the color associated with the port
	 * 
	 * @return the color of the port
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * sets the color to the port
	 * 
	 * @param color
	 *            the color to set
	 * @throws NullPointerException
	 *             if the color is null
	 */
	public void setColor(Color color) {
		Preconditions.checkNotNull(color,
				"The color of the port cannot be null");
		this.color = color;
	}
}
