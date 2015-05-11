package it.polimi.refinement.constraintcomputation.portsIdentifier;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.ColoredPort;
import it.polimi.constraints.Port;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Given the sub-property and the corresponding replacement computes the
 * incoming ports of the intersection automaton, i.e., of the automaton which
 * represents the intersection between the automaton associated with the
 * sub-property and the one associated with the refinement
 * 
 * @author claudiomenghi
 *
 */
public class IncomingPortsIdentifier extends PortsIdentifier {

	/**
	 * creates a new IncomingPortsIdentifier. The IncomingPortsIdentifier given
	 * the sub-property and the corresponding replacement computes the incoming
	 * ports of the intersection automaton
	 * 
	 * @param refinement
	 *            is the replacement to be considered
	 * @param subproperty
	 *            is the sub-property to be considered
	 * @param intersectionBuilder
	 *            is the intersectionBuilder which has been used to create the
	 *            intersection between the claim corresponding with the
	 *            sub-property and the model corresponding with the replacement
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IncomingPortsIdentifier(Replacement refinement,
			SubProperty subproperty, IntersectionBuilder intersectionBuilder) {
		super(refinement, subproperty, intersectionBuilder);
	}

	/**
	 * returns the set of the computed computeIntersectionIncomingPorts
	 * 
	 * @return the set of the intersection ports which have been computed
	 */
	public Set<ColoredPort> computeIntersectionIncomingPorts() {

		// gets the incoming port of the claim
		for (ColoredPort claimIncomingPort : subproperty.getIncomingPorts()) {

			// gets the incoming port of the model
			for (Port modelIncomingPort : refinement.getIncomingPorts()) {

				// if the labeling of the two transitions are equals and the
				// source of the two transitions are equal they are the
				// "same incoming transition"
				if (claimIncomingPort
						.getTransition()
						.getPropositions()
						.equals(modelIncomingPort.getTransition()
								.getPropositions())
						&& claimIncomingPort.getSource().equals(
								modelIncomingPort.getSource())) {

					// getting the corresponding states of the claim and of the
					// model
					State claimInitState = claimIncomingPort.getDestination();

					State modelInitState = modelIncomingPort.getDestination();

					Set<State> initStates = new HashSet<State>(
							intersectionBuilder
									.getClaimReleatedIntersectionStates(claimInitState));
					Set<State> statesAssociatedWithTheInitModelState = intersectionBuilder
							.getModelReleatedIntersectionStates(modelInitState);

					initStates.retainAll(statesAssociatedWithTheInitModelState);

					for (State intersectionInitState : initStates) {

						Transition transition = new ClaimTransitionFactory()
								.create(claimIncomingPort.getTransition()
										.getId(), claimIncomingPort
										.getTransition().getPropositions());

						ColoredPort intersectionPort = new ColoredPort(
								claimIncomingPort.getSource(),
								intersectionInitState, transition, true,
								claimIncomingPort.getColor());

						ports.add(intersectionPort);

						intersectionPortClaimPortMap.put(intersectionPort,
								claimIncomingPort);
						if(!claimPortIntersectionPortMap.containsKey(claimIncomingPort)){
							claimPortIntersectionPortMap.put(claimIncomingPort, new HashSet<ColoredPort>());
						}
						claimPortIntersectionPortMap.get(claimIncomingPort).add(intersectionPort);
					}
				}
			}
		}
		return Collections.unmodifiableSet(ports);
	}
}
