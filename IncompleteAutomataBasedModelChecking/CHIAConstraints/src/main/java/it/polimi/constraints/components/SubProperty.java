package it.polimi.constraints.components;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.ColoredPluggingTransition;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * The SubProperty class contains the description of a sub-property. The
 * Sub-property class extends the Component by specifying the IBA which
 * describes the claim the developer must consider in the refinement process.
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
	private final Map<Integer, ColoredPluggingTransition> incomingPorts;

	/**
	 * contains the out-coming ports of the component
	 */
	private final Map<Integer, ColoredPluggingTransition> outcomingPorts;

	/**
	 * contains the self-reachability relation based on purely regular states
	 * between the outgoing and the incoming ports of the sub-property
	 */
	private final ReachabilityRelation lowerApproximationReachabilityRelation;

	/**
	 * contains the self-reachability relation based on purely regular and mixed
	 * states between the outgoing and the incoming ports of the sub-property
	 */
	private final ReachabilityRelation overApproximationReachabilityRelation;

	private boolean indispensable;

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
	public SubProperty(State modelState, BA automaton) {
		super(modelState);

		Preconditions.checkNotNull(automaton,
				"The name of the state cannot be null");
		this.automaton = automaton;
		this.incomingPorts = new HashMap<Integer, ColoredPluggingTransition>();
		this.outcomingPorts = new HashMap<Integer, ColoredPluggingTransition>();
		this.lowerApproximationReachabilityRelation = new ReachabilityRelation();
		this.overApproximationReachabilityRelation = new ReachabilityRelation();
		this.setIndispensable(true);
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
	 * return a not modifiable set which contains the set of the out-coming
	 * ports of the component
	 * 
	 * @return a not modifiable set which contains the outcomingPorts of the
	 *         component
	 */
	public Set<ColoredPluggingTransition> getOutgoingTransitions() {
		return Collections
				.unmodifiableSet(new HashSet<ColoredPluggingTransition>(
						outcomingPorts.values()));
	}

	/**
	 * return a not modifiable set which contains the incoming ports of the
	 * component
	 * 
	 * @return a not modificable set which contains the incomingPorts of the
	 *         component
	 */
	public Set<ColoredPluggingTransition> getIncomingTransitions() {
		return Collections
				.unmodifiableSet(new HashSet<ColoredPluggingTransition>(
						incomingPorts.values()));
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
	public void addIncomingTransition(ColoredPluggingTransition port) {

		Preconditions.checkNotNull(port, "The port to be added cannot be null");
		this.incomingPorts.put(port.hashCode(), port);
	}

	public ColoredPluggingTransition getIncomingTransition(
			ColoredPluggingTransition port) {
		Preconditions.checkNotNull(port,
				"The incoming transition cannot be null");
		Preconditions
				.checkArgument(
						this.incomingPorts.containsKey(port.hashCode()),
						"The incoming transition must be contained into the incoming transitions of the sub-property");
		return this.incomingPorts.get(port.hashCode());
	}

	public ColoredPluggingTransition getOutgoingTransition(
			ColoredPluggingTransition port) {
		Preconditions.checkNotNull(port,
				"The outgoing transition cannot be null");
		Preconditions
				.checkArgument(
						this.outcomingPorts.containsKey(port.hashCode()),
						"The outgoing transition must be contained into the outgoing transitions of the sub-property");
		return this.outcomingPorts.get(port.hashCode());
	}

	/**
	 * adds an out-coming port to the component
	 * 
	 * @param port
	 *            is the port to be added as an out-coming port
	 * @throws NullPointerException
	 *             if the port is null
	 */
	public void addOutgoingTransition(ColoredPluggingTransition port) {

		Preconditions.checkNotNull(port, "The port to be added cannot be null");
		this.outcomingPorts.put(port.hashCode(), port);
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
	public void removePluggingTransition(ColoredPluggingTransition p) {
		Preconditions.checkNotNull(p, "The port p cannot be null");
		Preconditions
				.checkArgument(this.getIncomingTransitions().contains(p)
						|| this.getOutgoingTransitions().contains(p),
						"The port must be contained in the set of incoming or utcoming ports");
		if (this.getIncomingTransitions().contains(p)) {
			this.incomingPorts.remove(p);
		}
		if (this.getOutgoingTransitions().contains(p)) {
			this.outcomingPorts.remove(p);
		}
	}

	/**
	 * add a reachability entity, specifies that the destination port is
	 * reachable from the sourcePort through a path that only involves purely
	 * regular states
	 * 
	 * @param sourcePort
	 *            is the source port
	 * @param destinationPort
	 *            is the destination
	 * @throws NullPointerException
	 *             if one of the ports is null
	 * @throws IllegalArgumentException
	 *             if the source port is not an outgoing port of the
	 *             sub-property
	 * @throws IllegalArgumentException
	 *             if the destination port is not an incoming port of the
	 *             sub-property
	 */
	public void addReachabilityRelation(ColoredPluggingTransition sourcePort,
			ColoredPluggingTransition destinationPort, Boolean accepting, Boolean modelAcceptingState, Boolean claimAcceptingState) {
		// validates the parameters
		Preconditions.checkNotNull(sourcePort,
				"The incomingPort port cannot be null");
		Preconditions.checkNotNull(destinationPort,
				"The outcomingPort port cannot be null");
		Preconditions
				.checkArgument(
						this.getOutgoingTransitions().contains(sourcePort),
						"The source port "
								+ sourcePort
								+ " must be contained into the set of the outgoing port of the sub-property");
		Preconditions
				.checkArgument(
						this.getIncomingTransitions().contains(destinationPort),
						"The destination port "
								+ destinationPort
								+ " must be contained into the set of the incoming port of the sub-property");

		this.lowerApproximationReachabilityRelation.addTransition(sourcePort,
				destinationPort, accepting, modelAcceptingState, claimAcceptingState);
	}

	public ReachabilityRelation getLowerReachabilityRelation() {
		return this.lowerApproximationReachabilityRelation;
	}

	public ReachabilityRelation getUpperReachabilityRelation() {
		return this.overApproximationReachabilityRelation;
	}

	/**
	 * add a reachability entity, specifies that the destination port is
	 * reachable from the sourcePort through a path that only involves purely
	 * regular and mixed states
	 * 
	 * @param sourcePort
	 *            is the source port
	 * @param destinationPort
	 *            is the destination
	 * @throws NullPointerException
	 *             if one of the ports is null
	 * @throws IllegalArgumentException
	 *             if the source port is not an outgoing port of the
	 *             sub-property
	 * @throws IllegalArgumentException
	 *             if the destination port is not an incoming port of the
	 *             sub-property
	 */
	public void addPossibleReachabilityRelation(
			ColoredPluggingTransition sourcePort,
			ColoredPluggingTransition destinationPort, Boolean accepting, Boolean modelAcceptingState, Boolean claimAcceptingState) {
		// validates the parameters
		Preconditions.checkNotNull(sourcePort,
				"The incomingPort port cannot be null");
		Preconditions.checkNotNull(destinationPort,
				"The outcomingPort port cannot be null");
		Preconditions
				.checkArgument(
						this.getOutgoingTransitions().contains(sourcePort),
						"The source port "
								+ sourcePort
								+ " must be contained into the set of the outgoing port of the sub-property");
		Preconditions
				.checkArgument(
						this.getIncomingTransitions().contains(destinationPort),
						"The destination port "
								+ destinationPort
								+ " must be contained into the set of the incoming port of the sub-property");

		this.overApproximationReachabilityRelation.addTransition(sourcePort,
				destinationPort, accepting,  modelAcceptingState,  claimAcceptingState);
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

	public boolean isIndispensable() {
		return indispensable;
	}

	public void setIndispensable(boolean indispensable) {
		this.indispensable = indispensable;
	}
}
