package it.polimi.constraints.transitions;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import com.google.common.base.Preconditions;

/**
 * The Port class is used to describe how the IBA/BA that refers to the
 * sub-property/replacement to the a black box state of the model is connected
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
 * @author Claudio Menghi
 *
 */
public class LabeledPluggingTransition extends PluggingTransition {

	/**
	 * Is the counter which is used to associate to each new port a new Id
	 */
	public static int ID_COUNTER = 1;

	/**
	 * The port is also associated with a color which specifies whether from the
	 * port is possible to reach an accepting state or is reached from an
	 * initial state.
	 */
	private Label color;

	/**
	 * creates a new {@link LabeledPluggingTransition}
	 * 
	 * @param id
	 *            the id of the transition to be plugged
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
	 * @param color
	 *            the color of the LabeledPluggingTransition
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public LabeledPluggingTransition(int id, State source, State destination,
			Transition transition, boolean incoming, Label color) {
		super(id, source, destination, transition, incoming);
		Preconditions.checkNotNull(color,
				"The color of the transitionc cannot be null");

		this.setColor(color);
	}

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
	public LabeledPluggingTransition(State source, State destination,
			Transition transition, boolean incoming, Label color) {
		this(LabeledPluggingTransition.ID_COUNTER, source, destination,
				transition, incoming, color);
		LabeledPluggingTransition.ID_COUNTER = LabeledPluggingTransition.ID_COUNTER + 1;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Plugging transition [source=" + this.getSource() + ", incoming="
				+ this.isIncoming() + ", destination=" + this.getDestination()
				+ ", transition=" + this.getTransition() + ", color=" + color
				+ "]";
	}

	/**
	 * returns the color associated with the port
	 * 
	 * @return the color of the port
	 */
	public Label getColor() {
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
	public void setColor(Label color) {
		Preconditions.checkNotNull(color,
				"The color of the port cannot be null");
		this.color = color;
	}
}
