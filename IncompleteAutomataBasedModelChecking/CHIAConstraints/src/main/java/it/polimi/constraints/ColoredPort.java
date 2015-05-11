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
public class ColoredPort extends Port {

	/**
	 * Is the counter which is used to associate to each new port a new Id
	 */
	public static int ID_COUNTER = 1;

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
	public ColoredPort(State source, State destination, Transition transition,
			boolean incoming, Color color) {
		super(source, destination, transition, incoming);
		ColoredPort.ID_COUNTER = ColoredPort.ID_COUNTER + 1;
		Preconditions.checkNotNull(color,
				"The color of the transitionc cannot be null");

		this.setColor(color);
	}
	
	@Override
	public String toString() {
		return "Port [source=" + this.getSource() + ", incoming=" + this.isIncoming()
				+ ", destination=" + this.getDestination() + ", transition=" + this.getTransition()
				+ "color=" + color + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColoredPort other = (ColoredPort) obj;
		if (color != other.color)
			return false;
		return true;
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
