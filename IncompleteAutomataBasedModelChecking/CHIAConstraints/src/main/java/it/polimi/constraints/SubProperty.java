package it.polimi.constraints;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import java.util.Collections;
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
	 * contains the incoming ports of the component
	 */
	private final Set<ColoredPort> incomingPorts;

	/**
	 * contains the out-coming ports of the component
	 */
	private final Set<ColoredPort> outcomingPorts;

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
	public SubProperty(State modelState, BA automaton, Set<ColoredPort> incomingPorts,
			Set<ColoredPort> outcomingPorts) {
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
	 * returns the incomplete Buchi automaton associated with the subProperty
	 * 
	 * @return the IBA associated with the sub-property
	 */
	public BA getAutomaton() {
		return this.automaton;
	}
	
	/**
	 * return the set of the out-coming ports of the component
	 * 
	 * @return the outcomingPorts of the component
	 */
	public Set<ColoredPort> getOutcomingPorts() {
		return Collections.unmodifiableSet(outcomingPorts);
	}

	/**
	 * return the set of the in-coming ports of the component
	 * 
	 * @return the incomingPorts of the component
	 */
	public Set<ColoredPort> getIncomingPorts() {
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
	public void addIncomingPort(ColoredPort port) {

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
	public void addOutComingPort(ColoredPort port) {

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
	public void removePort(ColoredPort p) {
		Preconditions.checkNotNull(p, "The port p cannot be null");
		Preconditions
				.checkArgument(this.getIncomingPorts().contains(p)
						|| this.getOutcomingPorts().contains(p),
						"The port must be contained in the set of incoming or utcoming ports");
		if(this.getIncomingPorts().contains(p)){
			this.incomingPorts.remove(p);
		}
		if(this.getOutcomingPorts().contains(p)){
			this.outcomingPorts.remove(p);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((automaton == null) ? 0 : automaton.hashCode());
		result = prime * result
				+ ((incomingPorts == null) ? 0 : incomingPorts.hashCode());
		result = prime * result
				+ ((outcomingPorts == null) ? 0 : outcomingPorts.hashCode());
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
		SubProperty other = (SubProperty) obj;
		if (automaton == null) {
			if (other.automaton != null)
				return false;
		} else if (!automaton.equals(other.automaton))
			return false;
		if (incomingPorts == null) {
			if (other.incomingPorts != null)
				return false;
		} else if (!incomingPorts.equals(other.incomingPorts))
			return false;
		if (outcomingPorts == null) {
			if (other.outcomingPorts != null)
				return false;
		} else if (!outcomingPorts.equals(other.outcomingPorts))
			return false;
		return true;
	}
}
