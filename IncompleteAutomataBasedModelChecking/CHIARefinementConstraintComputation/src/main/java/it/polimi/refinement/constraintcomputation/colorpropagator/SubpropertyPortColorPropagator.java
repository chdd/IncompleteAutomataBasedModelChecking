package it.polimi.refinement.constraintcomputation.colorpropagator;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.constraints.Color;
import it.polimi.constraints.Port;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Given the incoming and out-coming ports of the intersection automaton
 * (obtained considering the claim corresponding with a sub-property and the
 * model of the replacement where the transparent states have been removed),
 * updates the color of the ports depending on their reachability relation (with
 * respect to the intersection automaton).
 * 
 * @author claudiomenghi
 *
 */
public class SubpropertyPortColorPropagator {

	/**
	 * contains the intersection automaton to be considered
	 */
	private final IntersectionBA intersection;
	/**
	 * contains the set of incoming ports
	 */
	private final Set<Port> incomingPorts;
	/**
	 * contains the set of outcoming ports
	 */
	private final Set<Port> outcomingPorts;

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
	 * creates a new component color updater which refers to the specific port
	 * 
	 * @param constraint
	 *            the constraint to be updated
	 */
	public SubpropertyPortColorPropagator(IntersectionBA intersection,
			Set<Port> incomingPorts, Set<Port> outcomingPorts) {
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
		for (Port p : this.incomingPorts) {
			if (p.getColor().equals(Color.GREEN)) {
				this.greenStates.add(p.getDestination());
			}
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
		for (Port p : this.outcomingPorts) {
			if (this.greenStates.contains(p.getSource())) {
				if (p.getColor().equals(Color.RED)) {
					throw new InternalError(
							"Something went wrong in the refinement checking it is not possible to mark the port "
									+ p + " as red since it is already yellow");
				}
				p.setColor(Color.GREEN);
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
		for (Port p : this.outcomingPorts) {
			if (p.getColor().equals(Color.RED)) {
				this.redStates.add(p.getSource());
			}
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
		for (Port p : this.incomingPorts) {
			if (this.redStates.contains(p.getDestination())) {
				if (p.getColor().equals(Color.GREEN)) {
					throw new InternalError(
							"Something went wrong in the refinement checking it is not possible to mark the port "
									+ p + " as red since it is already yellow");
				}
				p.setColor(Color.RED);
			}
		}
	}
}
