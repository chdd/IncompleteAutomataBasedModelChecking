package it.polimi.refinement.constraintcomputation.merger;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.ColoredPort;
import it.polimi.constraints.SubProperty;
import it.polimi.contraintcomputation.portreachability.ReachabilityChecker;
import it.polimi.refinement.constraintcomputation.portsIdentifier.IncomingPortsIdentifier;
import it.polimi.refinement.constraintcomputation.portsIdentifier.OutComingPortsIdentifier;

import java.util.Collections;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.base.Preconditions;

/**
 * The merger class is used to merge the old constraint with the sub-constraint
 * associated with the replacement that has been considered. Given the original
 * constraint and the sub constraint it computes a new constraint that merges
 * the two.
 * 
 * @author claudiomenghi
 *
 */
public class Merger {

	/**
	 * is the original constraint to be considered
	 */
	private final Constraint originalConstraint;
	/**
	 * is the sub-constraint associated with the current replacement
	 */
	private final Constraint subConstraint;
	/**
	 * is the intersection automaton obtained by considering the IBA associated
	 * with the replacement and the BA included in the original constraint
	 */
	private final IntersectionBA intersectionAutomaton;

	private final IncomingPortsIdentifier incomingPortsIdentifier;
	private final OutComingPortsIdentifier outcomingPortsIdentifier;

	public Merger(Constraint originalConstraint, Constraint subConstraint,
			IntersectionBA intersectionAutomaton,
			IncomingPortsIdentifier incomingPortsIdentifier,
			OutComingPortsIdentifier outcomingPortsIdentifier) {
		Preconditions.checkNotNull(originalConstraint,
				"The original constraint to be considered cannot be null");
		Preconditions.checkNotNull(subConstraint,
				"The subConstraint to be considere cannot be null");
		Preconditions.checkNotNull(intersectionAutomaton,
				"The intersection automaton to be considered cannot be null");
		Preconditions
				.checkNotNull(incomingPortsIdentifier,
						"The incoming ports identifier to be considered cannot be null");
		Preconditions
				.checkNotNull(outcomingPortsIdentifier,
						"The incoming outcoming identifier to be considered cannot be null");

		this.originalConstraint = originalConstraint;
		this.subConstraint = subConstraint;
		this.intersectionAutomaton = intersectionAutomaton;
		this.incomingPortsIdentifier = incomingPortsIdentifier;
		this.outcomingPortsIdentifier = outcomingPortsIdentifier;
	}

	/**
	 * merges the original constraint with the sub-constraint with respect to
	 * the intersetionAutomaton specified
	 * 
	 * @return the merging between the original constraint with the
	 *         sub-constraint with respect to the intersetionAutomaton specified
	 */
	public Constraint merge(State s) {

		// creating the return constraint c
		Constraint c = this.originalConstraint.clone();
		SubProperty subProperty = this.originalConstraint.getSubproperty(s);
		//REMOVING THE SUB-PROPERTY
		c.removeSubProperty(subProperty);
		c.addSubProperties(this.subConstraint.getSubProperties());
		for (DefaultEdge edge : this.subConstraint.getPortsGraph().edgeSet()) {
			c.addReachabilityRelation(this.subConstraint.getPortsGraph()
					.getEdgeSource(edge), this.subConstraint.getPortsGraph()
					.getEdgeTarget(edge));
		}

		ReachabilityChecker<IntersectionBA> regularReachabilityChecker = new ReachabilityChecker<IntersectionBA>(
				this.intersectionAutomaton);
		regularReachabilityChecker
				.computeReachabilityRelation(this.intersectionAutomaton
						.getRegularStates());
		ReachabilityChecker<IntersectionBA> mixedReachabilityChecker = new ReachabilityChecker<IntersectionBA>(
				this.intersectionAutomaton);
		mixedReachabilityChecker
				.computeReachabilityRelation(this.intersectionAutomaton
						.getMixedStates());

		// COMPUTING THE INCOMING PORTS
		computeIncomingPorts(c, subProperty, regularReachabilityChecker,
				mixedReachabilityChecker);
		// COMPUTING THE OUTCOMING PORTS
		computeOutcomingPorts(c, subProperty, regularReachabilityChecker,
				mixedReachabilityChecker);
	
		return c;
	}

	private void computeOutcomingPorts(Constraint c, SubProperty subProperty,
			ReachabilityChecker<IntersectionBA> regularReachabilityChecker,
			ReachabilityChecker<IntersectionBA> mixedReachabilityChecker) {

		for (ColoredPort outPort : subProperty.getOutcomingPorts()) {
			for (ColoredPort refinedOutPort : this.outcomingPortsIdentifier
					.getCorrespondingIntersectionPort(outPort)) {
				if (intersectionAutomaton.getMixedStates().contains(
						refinedOutPort.getSource())) {
					for (SubProperty subPropertySubConstraint : this.subConstraint
							.getSubProperties()) {
						for (ColoredPort subConstraintInPort : subPropertySubConstraint
								.getOutcomingPorts()) {
							if (!Collections
									.disjoint(
											mixedReachabilityChecker
													.getBackwardReachability()
													.get(refinedOutPort
															.getSource()),
											intersectionAutomaton
													.getSuccessors(subConstraintInPort
															.getDestination()))) {
								subPropertySubConstraint
										.addOutComingPort(refinedOutPort);
								c.addReachabilityRelation(subConstraintInPort,
										refinedOutPort);

							}
							if (refinedOutPort.getTransition().equals(
									subConstraintInPort.getTransition())) {
								subPropertySubConstraint
										.addOutComingPort(refinedOutPort);
								c.addReachabilityRelation(subConstraintInPort,
										refinedOutPort);
							}
						}
					}
				}
				// if the destination is not MIXED
				else {
					for (SubProperty subPropertySubConstraint : this.subConstraint
							.getSubProperties()) {
						for (ColoredPort subConstraintOutPort : subPropertySubConstraint
								.getOutcomingPorts()) {
							if (!Collections.disjoint(
									regularReachabilityChecker
											.getBackwardReachability().get(
													outPort.getSource()),
									intersectionAutomaton
											.getSuccessors(subConstraintOutPort
													.getSource()))) {
								for (ColoredPort p : Graphs.successorListOf(
										c.getPortsGraph(), outPort)) {
									c.addReachabilityRelation(
											subConstraintOutPort, p);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * updates the incoming ports of the constraint
	 * 
	 * @param c
	 * @param subProperty
	 * @param regularReachabilityChecker
	 * @param mixedReachabilityChecker
	 */
	private void computeIncomingPorts(Constraint c, SubProperty subProperty,
			ReachabilityChecker<IntersectionBA> regularReachabilityChecker,
			ReachabilityChecker<IntersectionBA> mixedReachabilityChecker) {
		// analyzes an incoming port of the sub-property
		for (ColoredPort inPort : subProperty.getIncomingPorts()) {
			for (ColoredPort refinedInPort : this.incomingPortsIdentifier
					.getCorrespondingIntersectionPort(inPort)) {
				if (intersectionAutomaton.getMixedStates().contains(
						refinedInPort.getDestination())) {
					for (SubProperty subPropertySubConstraint : this.subConstraint
							.getSubProperties()) {
						for (ColoredPort subConstraintOutPort : subPropertySubConstraint
								.getOutcomingPorts()) {
							if (!Collections
									.disjoint(
											mixedReachabilityChecker
													.getForwardReachability()
													.get(refinedInPort
															.getDestination()),
											intersectionAutomaton
													.getPredecessors(subConstraintOutPort
															.getSource()))) {
								subPropertySubConstraint
										.addIncomingPort(refinedInPort);
								c.addReachabilityRelation(refinedInPort,
										subConstraintOutPort);

							}
							if (refinedInPort.getTransition().equals(
									subConstraintOutPort.getTransition())) {
								subPropertySubConstraint
										.addIncomingPort(refinedInPort);
								c.addReachabilityRelation(refinedInPort,
										subConstraintOutPort);
							}
						}
					}
				}
				// if the destination is not MIXED
				else {
					for (SubProperty subPropertySubConstraint : this.subConstraint
							.getSubProperties()) {
						for (ColoredPort subConstraintInPort : subPropertySubConstraint
								.getIncomingPorts()) {
							if (!Collections
									.disjoint(
											regularReachabilityChecker
													.getForwardReachability()
													.get(refinedInPort
															.getDestination()),
											intersectionAutomaton
													.getPredecessors(subConstraintInPort
															.getDestination()))) {
								for (ColoredPort p : Graphs.predecessorListOf(
										c.getPortsGraph(), inPort)) {
									c.addReachabilityRelation(p,
											subConstraintInPort);
								}
							}
						}
					}
				}
			}
		}
	}
}
