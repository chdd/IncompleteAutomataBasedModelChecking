package it.polimi.constraints.components;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.Collections;
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
	 * contains the incoming ports of the component
	 */
	private final Set<PluggingTransition> incomingPorts;

	/**
	 * contains the out-coming ports of the component
	 */
	private final Set<PluggingTransition> outcomingPorts;

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
			Set<PluggingTransition> incomingPorts, Set<PluggingTransition> outcomingPorts) {
		super(modelState);
		Preconditions.checkNotNull(automaton,
				"The name of the state cannot be null");
		Preconditions.checkNotNull(incomingPorts,
				"The set of the incoming ports cannot be null");
		Preconditions.checkNotNull(outcomingPorts,
				"The set of the outcoming ports cannot be null");
		this.incomingPorts = incomingPorts;
		this.outcomingPorts = outcomingPorts;
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
	/**
	 * return the set of the out-coming ports of the component
	 * 
	 * @return the outcomingPorts of the component
	 */
	public Set<PluggingTransition> getOutcomingTransition() {
		return Collections.unmodifiableSet(outcomingPorts);
	}

	/**
	 * return the set of the in-coming ports of the component
	 * 
	 * @return the incomingPorts of the component
	 */
	public Set<PluggingTransition> getIncomingPorts() {
		return Collections.unmodifiableSet(incomingPorts);
	}

	/**
	 * adds an incoming port to the component
	 * 
	 * @param port
	 *            is the port to be added as an in-coming port
	 * 
	 * @throws NullPointerException
	 *             if the port is null
	 */
	public void addIncomingTransition(PluggingTransition port) {

		Preconditions.checkNotNull(port, "The port to be added cannot be null");
		this.incomingPorts.add(port);
	}

	/**
	 * adds an out-coming port to the component
	 * 
	 * @param port
	 *            is the port to be added as an out-coming port
	 * @throws NullPointerException
	 *             if the port is null
	 */
	public void addOutComingTransition(PluggingTransition port) {

		Preconditions.checkNotNull(port, "The port to be added cannot be null");
		this.outcomingPorts.add(port);
	}

	/**
	 * removes the port from the set of incoming or outcoming port
	 * 
	 * @param p
	 *            is the port to be removed
	 * @throws NullPointerException
	 *             if the port p is null
	 * @throws IllegalArgumentException
	 *             if the port is not contained into the set of incoming or
	 *             outcoming ports
	 */
	public void removePort(PluggingTransition p) {
		Preconditions.checkNotNull(p, "The port p cannot be null");
		Preconditions
				.checkArgument(this.getIncomingPorts().contains(p)
						|| this.getOutcomingTransition().contains(p),
						"The port must be contained in the set of incoming or utcoming ports");
		if(this.getIncomingPorts().contains(p)){
			this.incomingPorts.remove(p);
		}
		if(this.getOutcomingTransition().contains(p)){
			this.outcomingPorts.remove(p);
		}
	}

}
