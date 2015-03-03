package it.polimi.refinementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.constraints.Color;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.constraints.impl.PortImpl;
import it.polimi.refinementchecker.decorator.AutomatonDecorator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Is used to check the refinement of a transparent state. The refinement
 * checker updates the constraint associated with the transparent state and the
 * constraints associated with the other transparent states.<br>
 * 
 * The refinement checker computes whether the property is satisfied, not
 * satisfied or possibly satisfied in time O(|S|+|T|)<br>
 * 
 * Furthermore, the refinement checker allows to update the ports color relation
 * using the Floyd–Warshall algorithm in time O(|S|^3)
 * 
 * @author claudiomenghi
 * @param S
 *            is the type of the states of the automata under analysis
 * @param T
 *            is the type of the transitions of the automata under analysis
 * @param I
 *            is the type of the intersection transition to be generated
 */
public class RefinementChecker<S extends State, T extends Transition, I extends IntersectionTransition<S>> {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(RefinementChecker.class);

	/**
	 * contains the constraint to be updated
	 */
	private final Constraint<S, T, BA<S, T>> constraint;

	/**
	 * contains the refinement to be verified
	 */
	private final Component<S, T, IBA<S, T>> refinement;

	/**
	 * contains the intersection rule to be used in the refinement checking
	 */
	private final IntersectionRule<S, T, I> intersectionRule;

	
	private Map<Port<S, I>, Entry<Port<S, T>, Port<S, T>>> intersectionPortMap;

	private Set<Port<S, T>> refinementIncomingPorts;
	private Set<Port<S, T>> refinementOutcomingPorts;

	/**
	 * creates a new Refinement Checker. The refinement checker is used to check
	 * the refinement of a transparent state. The refinement checker updates the
	 * constraint associated with the transparent state and the constraints
	 * associated with the other transparent states.
	 * 
	 * @param constraint
	 *            is the constraint that must be updated by the
	 *            RefinementChecker
	 * @param component
	 *            is the refinement to be considered
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public RefinementChecker(Constraint<S, T, BA<S, T>> constraint,
			Component<S, T, IBA<S, T>> refinement,
			Set<Port<S, T>> refinementIncomingPorts,
			Set<Port<S, T>> refinementOutcomingPorts,
			IntersectionRule<S, T, I> intersectionRule) {
		Preconditions.checkNotNull(constraint,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(refinement,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(intersectionRule,
				"The intersection rule cannot be null");
		Preconditions
				.checkArgument(
						constraint.getConstrainedStates().contains(
								refinement.getModelState()),
						"The state constrained in the refinement must be contained into the set of the states of the constraint");
		this.constraint = constraint;
		this.refinement = refinement;
		this.intersectionRule = intersectionRule;
		this.refinementIncomingPorts=refinementIncomingPorts;
		this.refinementOutcomingPorts=refinementOutcomingPorts;
		this.intersectionPortMap = new HashMap<Port<S, I>, Entry<Port<S, T>, Port<S, T>>>();
	}

	public void check() {

		logger.debug("Starting the refinement checking phase");

		logger.debug("Updating the paths with no transparent states");
		
		/*
		 * updates the paths which do not involve any transparent state of the
		 * refinement. Indeed, when the refinement contains transparent states
		 * the paths that do not include any transparent state are analyzed.
		 * These paths do not generate any constraint
		 */
		this.updatedPathsWithNoTransparentStates();
		
		
		this.updatedPathsWithTransparentStates();

		logger.debug("Refinement checking phase ended");
	}

	/**
	 * updates the paths which the transparent states of the
	 * refinement. Indeed, when the refinement contains transparent states the
	 * paths that do not include any transparent state are analyzed. These paths
	 * do not generate any constraint
	 */
	private void updatedPathsWithTransparentStates() {
		
		/*
		 * COMPUTING THE INTERSECTION BETWEEN THE CLAIM (SUBPROPERTY) AND THE REFINEMENT
		 */
		// gets the sub-property associated with the model state, i.e., the claim automaton
		Component<S, T, BA<S, T>> subproperty = this.constraint
				.getSubproperties(this.refinement.getModelState());
		new AutomatonDecorator<S, T, BA<S,T>>(subproperty.getAutomaton()).decorate(this.constraint.getIncomingPorts(subproperty), this.constraint.getOutcomingPorts(subproperty));
		BA<S, T> claim = subproperty.getAutomaton();
		claim.addStuttering();

		// gets the model to be considered, i.e., the model of the refinement
		// where the transparent states have been removed
		IBA<S, T> tmpmodelWithTransparentStates = this.refinement
				.getAutomaton();
		new AutomatonDecorator<S, T, IBA<S,T>>(refinement.getAutomaton()).decorate(this.refinementIncomingPorts, this.refinementOutcomingPorts);
		tmpmodelWithTransparentStates.addStuttering();
		IBA<S, T> modelWithoutTransparentStates = tmpmodelWithTransparentStates;

		// computes the intersection between the claim and the model
		IntersectionBuilder<S, T, I> intersectionBuilder = new IntersectionBuilder<S, T, I>(
				this.intersectionRule, modelWithoutTransparentStates, claim);
		IntersectionBA<S, I> intersection = intersectionBuilder
				.computeIntersection();

		/*
		 * COMPUTING THE INCOMING AND OUTCOMING PORTS OF THE INTERSECTION AUTOMATON
		 */
		Set<Port<S, T>> incomingPorts = this.computeIncomingPorts( subproperty,
				intersectionBuilder);
		Set<Port<S, T>> outcomingPorts = this.computeOutComingPorts( subproperty,
				intersectionBuilder);
		/*
		 * UPDATES THE COLOR OF THE INCOMING AND OUTCOMING PORTS
		 */
		this.updateComponentPorts(intersection,
				incomingPorts, outcomingPorts);

		/*
		 * UPDATES THE PORT RELATION GRAPH WITH RESPECT TO THE VALUES OF THE INCOMING AND OUTCOMING PORTS
		 */
		this.constraint.updatePortRelation(incomingPorts, outcomingPorts);
	}
	
	/**
	 * updates the paths which do not involve any transparent state of the
	 * refinement. Indeed, when the refinement contains transparent states the
	 * paths that do not include any transparent state are analyzed. These paths
	 * do not generate any constraint
	 */
	private void updatedPathsWithNoTransparentStates() {
		
		/*
		 * COMPUTING THE INTERSECTION BETWEEN THE CLAIM (SUBPROPERTY) AND THE REFINEMENT
		 */
		// gets the sub-property associated with the model state, i.e., the claim automaton
		Component<S, T, BA<S, T>> subproperty = this.constraint
				.getSubproperties(this.refinement.getModelState());
		new AutomatonDecorator<S, T, BA<S,T>>(subproperty.getAutomaton()).decorate(this.constraint.getIncomingPorts(subproperty), this.constraint.getOutcomingPorts(subproperty));
		BA<S, T> claim = subproperty.getAutomaton();
		claim.addStuttering();

		// gets the model to be considered, i.e., the model of the refinement
		// where the transparent states have been removed
		IBA<S, T> tmpmodelWithoutTransparentStates = this.refinement
				.getAutomaton();
		new AutomatonDecorator<S, T, IBA<S,T>>(refinement.getAutomaton()).decorate(this.refinementIncomingPorts, this.refinementOutcomingPorts);
		tmpmodelWithoutTransparentStates.addStuttering();
		IBA<S, T> modelWithoutTransparentStates = new IBATransparentStateRemoval<S, T>()
				.transparentStateRemoval(tmpmodelWithoutTransparentStates
						.clone());

		// computes the intersection between the claim and the model
		IntersectionBuilder<S, T, I> intersectionBuilder = new IntersectionBuilder<S, T, I>(
				this.intersectionRule, modelWithoutTransparentStates, claim);
		IntersectionBA<S, I> intersection = intersectionBuilder
				.computeIntersection();

		/*
		 * COMPUTING THE INCOMING AND OUTCOMING PORTS OF THE INTERSECTION AUTOMATON
		 */
		Set<Port<S, T>> incomingPorts = this.computeIncomingPorts( subproperty,
				intersectionBuilder);
		Set<Port<S, T>> outcomingPorts = this.computeOutComingPorts( subproperty,
				intersectionBuilder);
		/*
		 * UPDATES THE COLOR OF THE INCOMING AND OUTCOMING PORTS
		 */
		this.updateComponentPorts(intersection,
				incomingPorts, outcomingPorts);

		/*
		 * UPDATES THE PORT RELATION GRAPH WITH RESPECT TO THE VALUES OF THE INCOMING AND OUTCOMING PORTS
		 */
		this.constraint.updatePortRelation(incomingPorts, outcomingPorts);
	}

	private Set<Port<S, T>> computeIncomingPorts(
			Component<S, T, BA<S, T>> subproperty,
			IntersectionBuilder<S, T, I> intersectionBuilder) {

		Set<Port<S, T>> claimIncomingPorts = this.constraint
				.getIncomingPorts(subproperty);

		Set<Port<S, T>> newInComingPorts = new HashSet<Port<S, T>>();

		for (Port<S, T> claimIncomingPort : claimIncomingPorts) {
			for (Port<S, T> modelIncomingPort : refinementIncomingPorts) {

				if (claimIncomingPort
						.getTransition()
						.getPropositions()
						.equals(modelIncomingPort.getTransition()
								.getPropositions())
						&& claimIncomingPort.getSource().equals(
								modelIncomingPort.getSource())) {

					S claimInitState = claimIncomingPort.getDestination();
					S modelInitState = modelIncomingPort.getDestination();

					Set<S> initStates = new HashSet<S>(intersectionBuilder
							.getClaimIntersectionStateMap().get(claimInitState));
					Set<S> statesAssociatedWithTheInitModelState = intersectionBuilder
							.getClaimIntersectionStateMap().get(modelInitState);

					initStates.retainAll(statesAssociatedWithTheInitModelState);

					for (S initState : initStates) {
						newInComingPorts.add(new PortImpl<S, T>(
								claimIncomingPort.getSource(), initState,
								claimIncomingPort.getTransition(), true));
					}
				}
			}
		}
		return newInComingPorts;
	}

	public Set<Port<S, T>> computeOutComingPorts(
			Component<S, T, BA<S, T>> subproperties,
			IntersectionBuilder<S, T, I> intersectionBuilder) {

		Set<Port<S, T>> claimOutcomingPorts = this.constraint
				.getOutcomingPorts(subproperties);
		Set<Port<S, T>> newOutComingPorts = new HashSet<Port<S, T>>();

		for (Port<S, T> claimOutcomingPort : claimOutcomingPorts) {
			for (Port<S, T> modelOutcomingPort : refinementOutcomingPorts) {

				if (claimOutcomingPort
						.getTransition()
						.getPropositions()
						.equals(claimOutcomingPort.getTransition()
								.getPropositions())
						&& claimOutcomingPort.getSource().equals(
								claimOutcomingPort.getSource())) {

					S claimOutState = claimOutcomingPort.getDestination();
					S modelOutState = modelOutcomingPort.getDestination();

					Set<S> outStates = new HashSet<S>(intersectionBuilder
							.getClaimIntersectionStateMap().get(claimOutState));
					Set<S> statesAssociatedWithTheOutModelState = intersectionBuilder
							.getClaimIntersectionStateMap().get(modelOutState);

					outStates.retainAll(statesAssociatedWithTheOutModelState);

					for (S outState : outStates) {
						newOutComingPorts.add(new PortImpl<S, T>(
								outState, claimOutcomingPort.getDestination(),
								claimOutcomingPort.getTransition(), false));
					}
				}
			}
		}
		return newOutComingPorts;
	}

	/**
	 * checks the component and computes which out-coming ports are reachable
	 * from which out-coming ports
	 * 
	 * @param subproperty
	 *            is the component under analysis
	 * @throws NullPointerException
	 *             if the sub-property is null
	 */
	private void updateComponentPorts(IntersectionBA<S, I> intersectionAutomaton,
			Set<Port<S, T>> incomingPorts,
			Set<Port<S, T>> outcomingports) {

		// computes a map that contains the set of the reachable states
		// reachable from each state of the automaton
		Map<S, Set<S>> reachabilityMap = new ReachabilityChecker<S, I, BA<S, I>>(
				intersectionAutomaton).check();

		Map<Port<S, T>, Color> incomingColorMap = new HashMap<Port<S, T>, Color>();
		for (Entry<Port<S, T>, Color> incomingPort : this.constraint
				.getPortValue().entrySet()) {
			incomingColorMap
					.put(incomingPort.getKey(), incomingPort.getValue());
		}

		// back propagating: back propagates the color of the ports
		for (Port<S, T> incomingPort : incomingPorts) {
			for (Port<S, T> outcomingPort : outcomingports) {
				// back-propagating the colors
				if (reachabilityMap.get(incomingPort.getDestination())
						.contains(outcomingPort.getSource())) {

					Port<S, T> modelIncomingPort = this.intersectionPortMap
							.get(incomingPort).getValue();
					Port<S, T> modelOutcomingPort = this.intersectionPortMap
							.get(incomingPort).getValue();

					Color incomingColor = this.constraint
							.getPortValue(modelIncomingPort);
					Color outcomingColor = this.constraint
							.getPortValue(modelOutcomingPort);

					this.constraint.setPortValue(modelIncomingPort,
							ColorPropagator.backPropagateColor(incomingColor,
									outcomingColor));
				}

			}
		}
		// forward propagating: propagates forward the color of the ports
		for (Port<S, T> incomingPort : incomingPorts) {
			for (Port<S, T> outcomingPort : outcomingports) {
				// back-propagating the colors
				if (reachabilityMap.get(incomingPort.getDestination())
						.contains(outcomingPort.getSource())) {

					Port<S, T> modelIncomingPort = this.intersectionPortMap
							.get(incomingPort).getValue();
					Port<S, T> modelOutcomingPort = this.intersectionPortMap
							.get(incomingPort).getValue();

					Color incomingColor = incomingColorMap
							.get(modelIncomingPort);
					Color outcomingColor = this.constraint
							.getPortValue(modelOutcomingPort);

					this.constraint.setPortValue(modelOutcomingPort,
							ColorPropagator.forwardColorPropagation(
									incomingColor, outcomingColor));
				}
			}
		}
	}
}
