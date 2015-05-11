package it.polimi.contraintcomputation.portreachability;

import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

import it.polimi.automata.BA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Color;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.ColoredPort;
import it.polimi.constraints.SubProperty;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;

/**
 * The PortReachability class allows to compute the reachability between the
 * ports of the sub-properties and the corresponding colors, through the methods
 * computeInternalReachability and computeExternalReachability. <br/>
 * 
 * The computeInternalReachability method computes the internal reachability
 * between the ports (i.e., the reachability between the incoming and the
 * outcoming ports of a subproperty). It uses the ReachabilityChecker to compute
 * the reachability between the mixed states of the intersection automaton and
 * it connects the incoming and outcoming ports of the sub-properties
 * accordingly. <br/>
 * 
 * The computeExternalReachability method computes the external reachability
 * between the ports (i.e., the reachability between the incoming and the
 * outcoming ports of the different subpropertites). It uses the
 * ReachabilityChecker to compute the reachability between the regular states of
 * the intersection automaton and it connects the incoming and outcoming ports
 * of the sub-properties accordingly.
 * 
 * @author claudiomenghi
 *
 */
public class PortReachability {

	/**
	 * is the constraint whose sub-properties must be updated
	 */
	private final Constraint constraint;

	/**
	 * is the builder which is used to compute the intersection automaton
	 */
	private final IntersectionBuilder intersectionBuilder;

	/**
	 * is the identifier which has been used to generate the sub-properties
	 */
	private final SubPropertiesIdentifier subPropertiesIdentifier;

	/**
	 * creates a new PortReachability component which is used to compute and
	 * update the reachability relation between the ports of the component
	 * 
	 * @param constraint
	 *            is the constraint whose sub-properties must be updated
	 * @param intersectionBuilder
	 *            is the builder which is used to compute the intersection
	 * @param subPropertiesIdentifier
	 *            is the component which has been used to compute the
	 *            sub-properties
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public PortReachability(Constraint constraint,
			IntersectionBuilder intersectionBuilder,
			SubPropertiesIdentifier subPropertiesIdentifier) {
		Preconditions.checkNotNull(constraint,
				"The constraint to be considered cannot be null");
		Preconditions.checkNotNull(intersectionBuilder,
				"The intersectionBuilder cannot be null");
		Preconditions.checkNotNull(subPropertiesIdentifier,
				"The subproperties cannot be null");

		this.constraint = constraint;
		this.intersectionBuilder = intersectionBuilder;
		this.subPropertiesIdentifier = subPropertiesIdentifier;
	}

	/**
	 * updates the reachability relation insider the constraint
	 */
	public void computeReachability() {
		this.computeInternalReachability();
		this.computeExternalReachability();
		this.computeColors();
	}

	private void computeColors() {

		// gets the intersection automaton
		IntersectionBA intersection = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		// creates a new reachability checker
		ReachabilityChecker<IntersectionBA> reachabilityChecker = new ReachabilityChecker<IntersectionBA>(
				intersection);
		// computes the reachability relation between the mixed states of the
		// intersection automaton
		reachabilityChecker.computeReachabilityRelation(intersection
				.getRegularStates());
		
		// computes the green ports
		Set<State> initialStates=new HashSet<State>(intersection.getInitialStates());
		initialStates.removeAll(intersection.getMixedStates());
		for(State initialState: initialStates){
			
			for(State successor: reachabilityChecker.getForwardReachability().get(initialState)){
				for (Transition transition : intersection.getOutTransitions(successor)) {

					if (this.subPropertiesIdentifier.isInTransition(transition)) {
						this.subPropertiesIdentifier.getInPort(transition).setColor(Color.GREEN);
					}
				}
				
			}
		}
		Set<State> acceptingStates=new HashSet<State>(intersection.getAcceptStates());
		acceptingStates.removeAll(intersection.getMixedStates());
		for(State acceptingState: acceptingStates){
			
			for(State predecessor: reachabilityChecker.getBackwardReachability().get(acceptingState)){
				for (Transition transition : intersection.getInTransitions(predecessor)) {

					if (this.subPropertiesIdentifier.isOutTransition(transition)) {
						this.subPropertiesIdentifier.getOutPort(transition).setColor(Color.RED);
					}
				}
				
			}
		}
		
	}

	private void computeExternalReachability() {
		// gets the intersection automaton
		IntersectionBA intersection = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		// creates a new reachability checker
		ReachabilityChecker<IntersectionBA> reachabilityChecker = new ReachabilityChecker<IntersectionBA>(
				intersection);
		// computes the reachability relation between the mixed states of the
		// intersection automaton
		reachabilityChecker.computeReachabilityRelation(intersection
				.getRegularStates());
		for (Entry<State, Set<State>> reachabilityEntry : reachabilityChecker
				.getForwardReachability().entrySet()) {
			State source = reachabilityEntry.getKey();
			for (Transition inTransition : intersection
					.getInTransitions(source)) {

				if (this.subPropertiesIdentifier.isInTransition(inTransition)) {
					ColoredPort inPort = this.subPropertiesIdentifier
							.getInPort(inTransition);

					for (State destination : reachabilityEntry.getValue()) {

						for (Transition outTransition : intersection
								.getOutTransitions(destination)) {

							if (this.subPropertiesIdentifier
									.isOutTransition(outTransition)) {
								ColoredPort outPort = this.subPropertiesIdentifier
										.getOutPort(outTransition);
								inPort.setColor(Color.YELLOW);
								outPort.setColor(Color.YELLOW);
								constraint.addReachabilityRelation(inPort,
										outPort);
							}
						}
					}
				}
			}
		}
		
		// computing the incoming ports that are also outcoming
		Set<ColoredPort> inports=subPropertiesIdentifier.inPorts();
		Set<ColoredPort> outports=subPropertiesIdentifier.outPorts();
		for(ColoredPort inport: inports){
			for(ColoredPort outport: outports){
				if(inport.getId()==outport.getId()){
					constraint.addReachabilityRelation(inport,
							outport);
				}
			}
		}
	}

	private void computeInternalReachability() {
		IntersectionBA intersection = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		for(SubProperty p: subPropertiesIdentifier.getSubProperties()){
			// creates a new reachability checker
			ReachabilityChecker<BA> reachabilityChecker = new ReachabilityChecker<BA>(
					p.getAutomaton());
			// computes the reachability relation between the mixed states of the
			// intersection automaton
			reachabilityChecker.computeReachabilityRelation(p.getAutomaton().getStates());
			for (Entry<State, Set<State>> reachabilityEntry : reachabilityChecker
					.getForwardReachability().entrySet()) {
				State source = reachabilityEntry.getKey();
				for (Transition inTransition : intersection
						.getInTransitions(source)) {

					if (this.subPropertiesIdentifier.isInTransition(inTransition)) {
						ColoredPort inPort = this.subPropertiesIdentifier
								.getInPort(inTransition);

						for (State destination : reachabilityEntry.getValue()) {

							for (Transition outTransition : intersection
									.getOutTransitions(destination)) {
								if (this.subPropertiesIdentifier
										.isOutTransition(outTransition)) {
									ColoredPort outPort = this.subPropertiesIdentifier
											.getOutPort(outTransition);
									inPort.setColor(Color.YELLOW);
									outPort.setColor(Color.YELLOW);
									
									// adds the reachability between the in-coming
									// and the out-coming port
									constraint.addReachabilityRelation(inPort,
											outPort);
								}
							}
						}
					}
				}
			}
		}
	}
}
