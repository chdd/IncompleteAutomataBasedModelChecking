package it.polimi.constraints;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * The SubProperty class contains the description of a sub-property. The
 * Sub-property class extends the Component by specifying the IBA which describes
 * the claim the developer must consider in the refinement process.
 * 
 * @author claudiomenghi
 * 
 */
public class SubProperty extends Component {

	/**
	 * contains the IBA corresponding to the sub-property
	 */
	private final BA automaton;

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
	public SubProperty(State modelState, BA automaton, Set<Port> incomingPorts,
			Set<Port> outcomingPorts) {
		super(modelState, incomingPorts, outcomingPorts);
		Preconditions.checkNotNull(automaton,
				"The name of the state cannot be null");
		this.automaton = automaton;
	}


	/**
	 * returns the incomplete Buchi automaton associated with the subProperty
	 * 
	 * @return the IBA associated with the sub-property
	 */
	public BA getAutomaton() {
		return this.automaton;
	}
}
