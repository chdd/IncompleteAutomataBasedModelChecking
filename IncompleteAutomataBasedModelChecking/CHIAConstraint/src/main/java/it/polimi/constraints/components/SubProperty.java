package it.polimi.constraints.components;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

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
	private final Map<Integer, LabeledPluggingTransition> incomingTransitions;

	/**
	 * contains the set of the green incoming transitions
	 */
	private final Set<LabeledPluggingTransition> greenIncomingTransitions;
	private final Set<LabeledPluggingTransition> yellowIncomingTransitions;

	private int numRedOutgoingTransitions;
	private int numYellowOutgoingTransitions;
	private int numOutgoingTransitions;
	/**
	 * contains the out-coming ports of the component
	 */
	private final Map<Integer, LabeledPluggingTransition> outgoingTransitions;

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
		this.incomingTransitions = new HashMap<Integer, LabeledPluggingTransition>();
		this.outgoingTransitions = new HashMap<Integer, LabeledPluggingTransition>();
		this.greenIncomingTransitions = new HashSet<LabeledPluggingTransition>();
		this.yellowIncomingTransitions = new HashSet<LabeledPluggingTransition>();

		this.lowerApproximationReachabilityRelation = new ReachabilityRelation();
		this.overApproximationReachabilityRelation = new ReachabilityRelation();
		this.setIndispensable(true);
		numRedOutgoingTransitions = 0;
		numYellowOutgoingTransitions = 0;
		numOutgoingTransitions = 0;
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
	public Set<LabeledPluggingTransition> getOutgoingTransitions() {
		return Collections
				.unmodifiableSet(new HashSet<LabeledPluggingTransition>(
						outgoingTransitions.values()));
	}

	/**
	 * return a not modifiable set which contains the incoming ports of the
	 * component
	 * 
	 * @return a not modificable set which contains the incomingPorts of the
	 *         component
	 */
	public Set<LabeledPluggingTransition> getIncomingTransitions() {
		return Collections
				.unmodifiableSet(new HashSet<LabeledPluggingTransition>(
						incomingTransitions.values()));
	}

	/**
	 * adds an incoming transition to the sub-property
	 * 
	 * @param transition
	 *            is the incoming transition to be added to the sub-property *
	 * @throws NullPointerException
	 *             if the port is null
	 */
	public void addIncomingTransition(LabeledPluggingTransition transition) {

		Preconditions.checkNotNull(transition,
				"The incoming transition to be added cannot be null");
		// updating the number of green incoming transitions
		if (transition.getLabel().equals(Label.G)) {
			this.greenIncomingTransitions.add(transition);
		}
		// updating the number of yellow incoming transitions
		if (transition.getLabel().equals(Label.Y)) {
			this.yellowIncomingTransitions.add(transition);
		}

		this.incomingTransitions.put(transition.hashCode(), transition);

	}

	public LabeledPluggingTransition getIncomingTransition(
			LabeledPluggingTransition port) {
		Preconditions.checkNotNull(port,
				"The incoming transition cannot be null");
		Preconditions
				.checkArgument(
						this.incomingTransitions.containsKey(port.hashCode()),
						"The incoming transition must be contained into the incoming transitions of the sub-property");
		return this.incomingTransitions.get(port.hashCode());
	}

	public LabeledPluggingTransition getOutgoingTransition(
			LabeledPluggingTransition port) {
		Preconditions.checkNotNull(port,
				"The outgoing transition cannot be null");
		Preconditions
				.checkArgument(
						this.outgoingTransitions.containsKey(port.hashCode()),
						"The outgoing transition must be contained into the outgoing transitions of the sub-property");
		return this.outgoingTransitions.get(port.hashCode());
	}

	/**
	 * adds an out-coming port to the component
	 * 
	 * @param port
	 *            is the port to be added as an out-coming port
	 * @throws NullPointerException
	 *             if the port is null
	 */
	public void addOutgoingTransition(LabeledPluggingTransition port) {

		if (port.getLabel().equals(Label.R)) {
			this.setNumRedOutgoingTransitions(this
					.getNumRedOutgoingTransitions() + 1);
		}
		if (port.getLabel().equals(Label.Y)) {
			this.setNumYellowOutgoingTransitions(this
					.getNumYellowOutgoingTransitions() + 1);
		}
		this.setNumOutgoingTransitions(this.getNumOutgoingTransitions() + 1);
		Preconditions.checkNotNull(port, "The port to be added cannot be null");
		this.outgoingTransitions.put(port.hashCode(), port);
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
	public void removePluggingTransition(LabeledPluggingTransition p) {
		Preconditions.checkNotNull(p, "The port p cannot be null");
		Preconditions
				.checkArgument(this.getIncomingTransitions().contains(p)
						|| this.getOutgoingTransitions().contains(p),
						"The port must be contained in the set of incoming or utcoming ports");
		if (this.getIncomingTransitions().contains(p)) {
			this.incomingTransitions.remove(p);
		}
		if (this.getOutgoingTransitions().contains(p)) {
			this.outgoingTransitions.remove(p);
		}
	}

	/**
	 * add a reachability entity, specifies that the destination port is
	 * reachable from the sourcePort through a path that only involves purely
	 * regular states
	 * 
	 * @param outgoingTransition
	 *            is the source port
	 * @param incomingTransition
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
	public void addReachabilityRelation(
			LabeledPluggingTransition outgoingTransition,
			LabeledPluggingTransition incomingTransition,
			Boolean modelAcceptingState, Boolean claimAcceptingState) {
		// validates the parameters
		Preconditions.checkNotNull(outgoingTransition,
				"The incomingPort port cannot be null");
		Preconditions.checkNotNull(incomingTransition,
				"The outcomingPort port cannot be null");
		Preconditions
				.checkArgument(
						this.getOutgoingTransitions().contains(
								outgoingTransition),
						"The source port "
								+ outgoingTransition
								+ " must be contained into the set of the outgoing port of the sub-property");
		Preconditions
				.checkArgument(
						this.getIncomingTransitions().contains(
								incomingTransition),
						"The destination port "
								+ incomingTransition
								+ " must be contained into the set of the incoming port of the sub-property");

		this.lowerApproximationReachabilityRelation.addTransition(
				outgoingTransition, incomingTransition, modelAcceptingState,
				claimAcceptingState);
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
	 * @param outgoingTransition
	 *            is the source port
	 * @param incomingTransition
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
			LabeledPluggingTransition outgoingTransition,
			LabeledPluggingTransition incomingTransition,
			Boolean modelAcceptingState, Boolean claimAcceptingState) {
		// validates the parameters
		Preconditions.checkNotNull(outgoingTransition,
				"The incomingPort port cannot be null");
		Preconditions.checkNotNull(incomingTransition,
				"The outcomingPort port cannot be null");
		Preconditions
				.checkArgument(
						this.getOutgoingTransitions().contains(
								outgoingTransition),
						"The source port "
								+ outgoingTransition
								+ " must be contained into the set of the outgoing port of the sub-property");
		Preconditions
				.checkArgument(
						this.getIncomingTransitions().contains(
								incomingTransition),
						"The destination port "
								+ incomingTransition
								+ " must be contained into the set of the incoming port of the sub-property");

		this.overApproximationReachabilityRelation.addTransition(
				outgoingTransition, incomingTransition, modelAcceptingState,
				claimAcceptingState);
	}

	@Override
	public String toString() {
		return "SubProperty [automaton=" + automaton + ",\n "
				+ "incomingTransitions=" + incomingTransitions + ",\n "
				+ "outgoingTransitions=" + outgoingTransitions +",\n"
				+ " indispensable=" + indispensable + "]";
	}


	public boolean isIndispensable() {
		return indispensable;
	}

	public void setIndispensable(boolean indispensable) {
		this.indispensable = indispensable;
	}

	/**
	 * returns the set of the green incoming transitions
	 * 
	 * @return the set of the green incoming transitions
	 */
	public Set<LabeledPluggingTransition> getGreenIncomingTransitions() {
		return Collections.unmodifiableSet(this.greenIncomingTransitions);
	}

	/**
	 * sets the color of the incoming transition to green. The incoming
	 * transition must already be inserted into the set of the incoming
	 * transitions of the sub-property. If the incoming transition was already a
	 * yellow transition it is removed from the set of the yellow transitions
	 * 
	 * @param incomingTransition
	 *            the incoming transition whose color must be modified to green
	 * @throws NullPointerException
	 *             if the incoming transition is green
	 * @throws IllegalArgumentException
	 *             if the incoming transition is not contained into the set of
	 *             incoming transitions of the sub-property
	 */
	public void setGreenIncomingTransition(
			LabeledPluggingTransition incomingTransition) {
		Preconditions.checkNotNull(incomingTransition,
				"The incoming transition cannot be null");
		Preconditions
				.checkArgument(
						this.incomingTransitions.values().contains(
								incomingTransition),
						"The incoming transition must be contained into the set of incoming transitions of the sub-property");
		if (this.yellowIncomingTransitions.contains(incomingTransition)) {
			this.yellowIncomingTransitions.remove(incomingTransition);
		}
		this.greenIncomingTransitions.add(incomingTransition);

	}

	public void setYellowIncomingTransition(
			LabeledPluggingTransition incomingTransition) {
		Preconditions.checkNotNull(incomingTransition,
				"The incoming transition cannot be null");
		Preconditions
				.checkArgument(
						this.incomingTransitions.values().contains(
								incomingTransition),
						"The incoming transition must be contained into the set of incoming transitions of the sub-property");
		if (this.greenIncomingTransitions.contains(incomingTransition)) {
			throw new InternalError(
					"It is not possible to change the label of an incoming transition from G to Y");
		}
		this.yellowIncomingTransitions.add(incomingTransition);

	}

	public void incrementNumberRedOutgoingTransitions() {
		this.numRedOutgoingTransitions++;
	}

	public void incrementNumberYellowOutgoingTransitions() {
		this.numYellowOutgoingTransitions++;
	}

	public void decrementNumberYellowOutgoingTransitions() {
		this.numYellowOutgoingTransitions--;
	}

	/**
	 * @return the numYellowIncomingTransitions
	 */
	public int getNumYellowIncomingTransitions() {
		return this.yellowIncomingTransitions.size();
	}

	/**
	 * @return the numIncomingTransitions
	 */
	public int getNumIncomingTransitions() {
		return this.incomingTransitions.size();
	}

	/**
	 * @return the numRedOutgoingTransitions
	 */
	public int getNumRedOutgoingTransitions() {
		return numRedOutgoingTransitions;
	}

	/**
	 * @param numRedOutgoingTransitions
	 *            the numRedOutgoingTransitions to set
	 */
	public void setNumRedOutgoingTransitions(int numRedOutgoingTransitions) {
		this.numRedOutgoingTransitions = numRedOutgoingTransitions;
	}

	/**
	 * @return the numYellowOutgoingTransitions
	 */
	public int getNumYellowOutgoingTransitions() {
		return numYellowOutgoingTransitions;
	}

	/**
	 * @param numYellowOutgoingTransitions
	 *            the numYellowOutgoingTransitions to set
	 */
	public void setNumYellowOutgoingTransitions(int numYellowOutgoingTransitions) {
		this.numYellowOutgoingTransitions = numYellowOutgoingTransitions;
	}

	/**
	 * @return the numOutgoingTransitions
	 */
	public int getNumOutgoingTransitions() {
		return numOutgoingTransitions;
	}

	/**
	 * @param numOutgoingTransitions
	 *            the numOutgoingTransitions to set
	 */
	public void setNumOutgoingTransitions(int numOutgoingTransitions) {
		this.numOutgoingTransitions = numOutgoingTransitions;
	}

}
