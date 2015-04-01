package it.polimi.constraints;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;

import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * The Replacement class extends the Component class and specifies the IBA which
 * represent the model that describes the replacement of the transparent state.
 * 
 * @author claudiomenghi
 *
 */
public class Replacement extends Component {

	/**
	 * contains the IBA corresponding to the sub-property
	 */
	private final IBA automaton;

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
	public Replacement(State modelState, IBA automaton,
			Set<Port> incomingPorts, Set<Port> outcomingPorts) {
		super(modelState, incomingPorts, outcomingPorts);
		Preconditions.checkNotNull(automaton,
				"The name of the state cannot be null");

		this.automaton = automaton;
	}

	/**
	 * returns the IBA associated with the replacement
	 * 
	 * @return the IBA associated with the replacement
	 */
	public IBA getAutomaton() {
		return this.automaton;
	}

}
