package it.polimi.refinement.constraintcomputation.portsIdentifier;

import it.polimi.automata.state.State;
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
 * out-coming ports of the intersection automaton, i.e., of the automaton which
 * represents the intersection between the automaton associated with the
 * sub-property and the one associated with the refinement
 * 
 * @author claudiomenghi
 *
 */
public class OutComingPortsIdentifier extends PortsIdentifier{

	/**
	 * creates a new OutComingPortsIdentifier. The OutComingPortsIdentifier given
	 * the sub-property and the corresponding replacement computes the outcoming
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
	public OutComingPortsIdentifier(
			Replacement refinement,
			SubProperty subproperty,
			IntersectionBuilder intersectionBuilder) {
		super(refinement, subproperty, intersectionBuilder);
		
	}

	public Set<ColoredPort> computeIntersectionOutcomingPorts() {

		Set<ColoredPort> claimOutcomingPorts = this.subproperty.getOutcomingPorts();

		for (ColoredPort claimOutcomingPort : claimOutcomingPorts) {
			for (Port modelOutcomingPort : refinement.getOutcomingPorts()) {

				if (claimOutcomingPort
						.getTransition()
						.getPropositions()
						.equals(modelOutcomingPort.getTransition()
								.getPropositions())
						&& claimOutcomingPort.getDestination().equals(
								modelOutcomingPort.getDestination())) {

					State claimOutState = claimOutcomingPort.getSource();
					State modelOutState = modelOutcomingPort.getSource();

					Set<State> outintersectionStates = new HashSet<State>(
							intersectionBuilder
									.getClaimReleatedIntersectionStates(claimOutState));
					Set<State> statesAssociatedWithTheOutModelState = intersectionBuilder
							.getModelReleatedIntersectionStates(modelOutState);

					outintersectionStates
							.retainAll(statesAssociatedWithTheOutModelState);

					Transition transition = intersectionBuilder
							.getClaim()
							.getTransitionFactory()
							.create(claimOutcomingPort.getTransition()
									.getPropositions());

					for (State outState : outintersectionStates) {

						ColoredPort intersectionPort = new ColoredPort(
								outState, claimOutcomingPort.getDestination(),
								transition, false, claimOutcomingPort.getColor());
						this.ports.add(intersectionPort);

						this.intersectionPortClaimPortMap.put(intersectionPort,
								claimOutcomingPort);
						if(!claimPortIntersectionPortMap.containsKey(claimOutcomingPort)){
							claimPortIntersectionPortMap.put(claimOutcomingPort, new HashSet<ColoredPort>());
						}
						claimPortIntersectionPortMap.get(claimOutcomingPort).add(intersectionPort);
					}
				}
			}
		}
		return Collections.unmodifiableSet(ports);
	}
}
