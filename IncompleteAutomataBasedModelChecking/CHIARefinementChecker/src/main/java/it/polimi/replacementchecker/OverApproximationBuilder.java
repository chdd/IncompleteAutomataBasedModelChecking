package it.polimi.replacementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Color;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

public class OverApproximationBuilder {

	/**
	 * contains the replacement to be verified
	 */
	private final Replacement replacement;

	/**
	 * the sub-property to be considered
	 */
	private final SubProperty subproperty;

	private final AcceptingPolicy acceptingPolicy;

	
	/**
	 * creates a new {@link OverApproximationBuilder}. The over-approximation
	 * builder computes an over approximation of the intersection automaton
	 * between the replacement and the sub-property
	 * 
	 * @param replacement
	 *            the replacement to be considered
	 * @param subproperty
	 *            the sub-property under analysis
	 * @param acceptingPolicy
	 *            the accepting policy to be used in the computation of the
	 *            intersection
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public OverApproximationBuilder(Replacement replacement,
			SubProperty subproperty, AcceptingPolicy acceptingPolicy) {
		Preconditions.checkNotNull(replacement,
				"The replacement to be considered cannot be null");
		Preconditions.checkNotNull(subproperty,
				"The sub=property to be considered cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The accepting policy to be considered cannot be null");

		this.replacement = replacement;
		this.subproperty = subproperty;
		this.acceptingPolicy = acceptingPolicy;
	}
	
	public IntersectionBA perform() {

		
		BA claim = subproperty.getAutomaton().clone();
		IBA model = this.replacement.getAutomaton().clone();

		Set<State> additionalInitStatesModel = Utils
				.getAdditionalModelInitialStates(model, this.replacement);
		Set<State> additionalInitStatesClaim = Utils.getAdditionalClaimInitialStates(claim, this.subproperty);

		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, this.acceptingPolicy,
				new IntersectionStateFactory(),
				new IntersectionTransitionBuilder());
		IntersectionBA upperIntersectionBA = intersectionBuilder.perform();

		for (State claimAcceptState : claim.getAcceptStates()) {
			for (State modelState : model.getAcceptStates()) {
				intersectionBuilder.updateIntersection(modelState,
						claimAcceptState, 1);
			}
		}

		for (State claimAcceptState : claim.getAcceptStates()) {
			for (State modelAcceptState : model.getAcceptStates()) {
				intersectionBuilder.updateIntersection(modelAcceptState,
						claimAcceptState, 2);
			}
		}
		
		TransitionFactory<State, Transition> transitionFactory = new ClaimTransitionFactory();
		StateFactory stateFactory = new StateFactory();

		this.addingGreenAndYellowInitialStates(intersectionBuilder,
				transitionFactory, stateFactory);
		
		this.addingRedAndYellowFinalStates(intersectionBuilder, transitionFactory,
				stateFactory);

		this.addReachabilityTransitions(intersectionBuilder, transitionFactory);
		Utils.removeArtificiallyInjectedInitialStates(additionalInitStatesModel, additionalInitStatesClaim, intersectionBuilder, upperIntersectionBA, claim, model);
		
		return upperIntersectionBA;
	}

	/**
	 * @param intersectionBuilder
	 * @param transitionFactory
	 */
	private void addReachabilityTransitions(
			IntersectionBuilder intersectionBuilder,
			TransitionFactory<State, Transition> transitionFactory) {
		for (Entry<Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean> reachabilityEntry : this.subproperty
				.getUpperReachabilityRelation().getReachabilityAcceptingMap()
				.entrySet()) {

			Set<State> sourceStates = new HashSet<State>();
			for (PluggingTransition outgoingTransition : this.replacement
					.getOutcomingTransition()) {
				if (this.isOutgoingEqual(outgoingTransition, reachabilityEntry
						.getKey().getKey())) {
					sourceStates.addAll(intersectionBuilder
							.getIntersectionStates(reachabilityEntry.getKey()
									.getKey().getSource(),
									outgoingTransition.getSource()));
				}
			}
			Set<State> destinationStates = new HashSet<State>();

			for (PluggingTransition incomingTransition : this.replacement
					.getIncomingPorts()) {
				if (this.isIncomingEqual(incomingTransition, reachabilityEntry
						.getKey().getValue())) {
					destinationStates.addAll(intersectionBuilder
							.getIntersectionStates(reachabilityEntry.getKey()
									.getValue().getDestination(),
									incomingTransition.getDestination()));
				}
			}
			for (State sourceState : sourceStates) {
				for (State destinationState : destinationStates) {
					if (!intersectionBuilder.getIntersectionAutomaton()
							.isSuccessor(sourceState, destinationState)) {
						if (reachabilityEntry.getValue()) {

							State s = new StateFactory().create();
							intersectionBuilder.getIntersectionAutomaton()
									.addAcceptState(s);
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(sourceState, s,
											transitionFactory.create());
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(s, destinationState,
											transitionFactory.create());
						} else {
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(sourceState,
											destinationState,
											transitionFactory.create());
						}

					}
				}
			}
		}
	}

	/**
	 * @param intersectionBuilder
	 * @param transitionFactory
	 * @param stateFactory
	 */
	private void addingRedAndYellowFinalStates(
			IntersectionBuilder intersectionBuilder,
			TransitionFactory<State, Transition> transitionFactory,
			StateFactory stateFactory) {
		State redState = stateFactory.create("RED");
		intersectionBuilder.getIntersectionAutomaton().addAcceptState(redState);
		intersectionBuilder.getIntersectionAutomaton().addTransition(redState,
				redState, transitionFactory.create());
		for (ColoredPluggingTransition outTransitionSubProperty : this.subproperty
				.getOutgoingTransitions()) {
			if (outTransitionSubProperty.getColor() == Color.RED
					|| outTransitionSubProperty.getColor() == Color.YELLOW) {
				for (PluggingTransition outTransitionReplacement : this.replacement
						.getOutcomingTransition()) {
					if (outTransitionSubProperty.getDestination().equals(
							outTransitionReplacement.getDestination())
							&& outTransitionSubProperty
									.getTransition()
									.getPropositions()
									.equals(outTransitionReplacement
											.getTransition().getPropositions())) {
						Set<State> outIntersectionStates = intersectionBuilder
								.getIntersectionStates(
										outTransitionSubProperty.getSource(),
										outTransitionReplacement.getSource());
						for (State outState : outIntersectionStates) {
							if (!intersectionBuilder.getIntersectionAutomaton()
									.isPredecessor(outState, redState)) {
								intersectionBuilder.getIntersectionAutomaton()
										.addTransition(outState, redState,
												transitionFactory.create());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param intersectionBuilder
	 * @param transitionFactory
	 * @param stateFactory
	 */
	private void addingGreenAndYellowInitialStates(
			IntersectionBuilder intersectionBuilder,
			TransitionFactory<State, Transition> transitionFactory,
			StateFactory stateFactory) {
		State greenState = stateFactory.create("GREEN");
		intersectionBuilder.getIntersectionAutomaton().addState(greenState);
		intersectionBuilder.getIntersectionAutomaton().addInitialState(
				greenState);

		for (ColoredPluggingTransition initTransitionSubProperty : this.subproperty
				.getIncomingTransitions()) {
			if (initTransitionSubProperty.getColor() == Color.GREEN
					|| initTransitionSubProperty.getColor() == Color.YELLOW) {
				for (PluggingTransition initTransitionReplacement : this.replacement
						.getIncomingPorts()) {
					// if the two incoming transitions have the same source and
					// the same label
					if (initTransitionSubProperty.getSource().equals(
							initTransitionReplacement.getSource())
							&& initTransitionSubProperty
									.getTransition()
									.getPropositions()
									.equals(initTransitionReplacement
											.getTransition().getPropositions())) {

						Set<State> initntersectionStates = intersectionBuilder
								.getIntersectionStates(
										initTransitionSubProperty
												.getDestination(),
										initTransitionReplacement
												.getDestination());
						for (State initialState : initntersectionStates) {
							if (!intersectionBuilder.getIntersectionAutomaton()
									.isPredecessor(greenState, initialState)) {
								intersectionBuilder.getIntersectionAutomaton()
										.addTransition(greenState,
												initialState,
												transitionFactory.create());
							}
						}
					}
				}
			}
		}
	}
	
	
	

	

	

	public boolean isOutgoingEqual(PluggingTransition replacementTransition,
			ColoredPluggingTransition subPropertyTransition) {

		if (replacementTransition
				.getTransition()
				.getPropositions()
				.equals(subPropertyTransition.getTransition().getPropositions())) {
			if (replacementTransition.getDestination().equals(
					subPropertyTransition.getDestination())) {
				return true;
			}
		}
		return false;
	}

	public boolean isIncomingEqual(PluggingTransition replacementTransition,
			ColoredPluggingTransition subPropertyTransition) {

		if (replacementTransition
				.getTransition()
				.getPropositions()
				.equals(subPropertyTransition.getTransition().getPropositions())) {
			if (replacementTransition.getSource().equals(
					subPropertyTransition.getSource())) {
				return true;
			}
		}
		return false;
	}

}
