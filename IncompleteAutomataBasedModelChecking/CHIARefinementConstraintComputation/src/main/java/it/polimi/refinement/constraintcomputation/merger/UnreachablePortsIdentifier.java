package it.polimi.refinement.constraintcomputation.merger;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.constraints.ColoredPort;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * identifies the ports (incoming and outcoming) that are not any more reachable. It requires a temporal complexity O(S+T).
 * @author claudiomenghi
 *
 */
public class UnreachablePortsIdentifier {

	/**
	 * contains the intersection automaton to be considered
	 */
	private final IntersectionBA intersection;
	/**
	 * contains the set of incoming ports
	 */
	private final Set<ColoredPort> incomingPorts;
	/**
	 * contains the set of outcoming ports
	 */
	private final Set<ColoredPort> outcomingPorts;

	/**
	 * is a set which contains the states from which it is possible to reach a
	 * red outcoming port
	 */
	private Set<State> redStates;

	/**
	 * is a set which contains the states that are reachable from a green
	 * incoming port
	 */
	private Set<State> greenStates;
	
	/**
	 * contains the set of unreachable out-coming ports
	 */
	private final Set<ColoredPort> unreachableOutPorts;
	
	/**
	 * contains the set of unreachable incoming ports
	 */
	private final Set<ColoredPort> unreachableInPorts;

	/**
	 * creates a new component color updater which refers to the specific port
	 * 
	 * @param constraint
	 *            the constraint to be updated
	 */
	public UnreachablePortsIdentifier(IntersectionBA intersection,
			Set<ColoredPort> incomingPorts, Set<ColoredPort> outcomingPorts) {
		Preconditions.checkNotNull(intersection,
				"The intersection automaton cannot be null");
		Preconditions.checkNotNull(incomingPorts,
				"The  incoming ports cannot be null");
		Preconditions.checkNotNull(outcomingPorts,
				"The  outcoming ports cannot be null");
		this.intersection = intersection;
		this.incomingPorts = incomingPorts;
		this.outcomingPorts = outcomingPorts;
		this.redStates = new HashSet<State>();
		this.greenStates=new HashSet<State>();
		this.unreachableOutPorts=new HashSet<ColoredPort>();
		this.unreachableInPorts=new HashSet<ColoredPort>();
	}
	
	/**
	 * updates the color of the ports. Marks as green the out-coming ports
	 * reachable from the green incoming ports. Mark as red the in-coming ports
	 * from witch it is possible to reach a red out-coming port
	 * 
	 * @throws InternalError
	 *             if a red port has to be marked as green or vice-versa it
	 *             means that the property must be not satisfied, thus something
	 *             went wrong in the refinement checking
	 */
	public void updateComponentPorts() {
		this.backPropagatingRedPorts();
		this.forwardPropagatingGreenPorts();

	}


	/**
	 * The private method forwardPropagatingGreenPorts back propagates the green
	 * color to the out-coming ports that are reachable from a green incoming
	 * port
	 * 
	 * @throws InternalError
	 *             if a red port has to be marked as green it means that the
	 *             property must be not satisfied, thus something went wrong in
	 *             the refinement checking
	 */
	private void forwardPropagatingGreenPorts() {
		for (ColoredPort p : this.incomingPorts) {
			this.greenStates.add(p.getDestination());
		}
		Set<State> toBeAnalyzed = new HashSet<State>(this.greenStates);

		while (!toBeAnalyzed.isEmpty()) {
			State currentState = toBeAnalyzed.iterator().next();
			for (State s : this.intersection.getSuccessors(currentState)) {
				if (!greenStates.contains(s)) {
					toBeAnalyzed.add(s);
					greenStates.add(s);
				}
			}
			toBeAnalyzed.remove(currentState);
		}
		for (ColoredPort p : this.outcomingPorts) {
			if (!this.greenStates.contains(p.getSource())) {
				this.unreachableOutPorts.add(p);
			}
		}
	}

	/**
	 * The private method backPropagatingRedPorts back propagates the red color
	 * to the incoming ports from which it is possible to reach a red out-coming
	 * port (if a green incoming port have to be marked as red an InternalError
	 * is generated since something went wrong in the refinement checking).
	 * 
	 * @throws InternalError
	 *             if a green port has to be marked as red it means that the
	 *             property must be not satisfied, thus something went wrong in
	 *             the refinement checking
	 */
	private void backPropagatingRedPorts() {
		// each state that is a source of an outcoming red port is added to the
		// set of the states from which it is possible to reach a red out-coming
		// port
		for (ColoredPort p : this.outcomingPorts) {
			this.redStates.add(p.getSource());
		}
		Set<State> toBeAnalyzed = new HashSet<State>(this.redStates);

		while (!toBeAnalyzed.isEmpty()) {
			State currentState = toBeAnalyzed.iterator().next();
			for (State s : this.intersection.getPredecessors(currentState)) {
				if (!redStates.contains(s)) {
					toBeAnalyzed.add(s);
					redStates.add(s);
				}
			}
			toBeAnalyzed.remove(currentState);
		}
		for (ColoredPort p : this.incomingPorts) {
			if (!this.redStates.contains(p.getDestination())) {
				this.unreachableInPorts.add(p);
			}
		}
	}
	
	/**
	 * @return the unreachableOutPorts
	 */
	public Set<ColoredPort> getUnreachableOutPorts() {
		return Collections.unmodifiableSet(unreachableOutPorts);
	}

	/**
	 * @return the unreachableInPorts
	 */
	public Set<ColoredPort> getUnreachableInPorts() {
		return Collections.unmodifiableSet(unreachableInPorts);
	}
}
