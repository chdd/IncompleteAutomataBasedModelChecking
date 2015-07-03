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
import it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Color;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;

public class UnderApproximationBuilder {

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
	 * creates a new {@link UnderApproximationBuilder}. The over-approximation
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
	public UnderApproximationBuilder(Replacement replacement,
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
		Set<State> additionalInitStatesClaim = Utils
				.getAdditionalClaimInitialStates(claim, this.subproperty);

		// removes from the state of the automaton associated to the replacement
		// the initial states
		model = new IBATransparentStateRemoval().removeTransparentStates(model);

		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, this.acceptingPolicy,
				new IntersectionStateFactory(),
				new IntersectionTransitionBuilder());
		IntersectionBA lowerIntersectionBA = intersectionBuilder.perform();

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

		this.addingGreenState(intersectionBuilder, transitionFactory,
				stateFactory);
		this.addingRedState(intersectionBuilder, transitionFactory,
				stateFactory);

		this.oldaddReachabilityTransitions(intersectionBuilder, transitionFactory);

		Utils.removeArtificiallyInjectedInitialStates(
				additionalInitStatesModel, additionalInitStatesClaim,
				intersectionBuilder, lowerIntersectionBA, claim, model);
		return lowerIntersectionBA;
	}

	

	
	/**
	 * @param intersectionBuilder
	 * @param transitionFactory
	 */
	private void oldaddReachabilityTransitions(
			IntersectionBuilder intersectionBuilder,
			TransitionFactory<State, Transition> transitionFactory) {
		for (Entry<Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean> reachabilityEntry : this.subproperty
				.getLowerReachabilityRelation().getReachabilityAcceptingMap()
				.entrySet()) {

			Set<State> sourceStates = new HashSet<State>();
			for (PluggingTransition outgoingTransition : this.replacement
					.getOutcomingTransition()) {
				if (Utils.isOutgoingEqual(outgoingTransition, reachabilityEntry
						.getKey().getKey())) {
					State subPropertysourceState = reachabilityEntry.getKey()
							.getKey().getSource();
					State replacementSourceState = outgoingTransition
							.getSource();

					sourceStates.addAll(intersectionBuilder
							.getIntersectionStates(subPropertysourceState,
									replacementSourceState));
				}
			}
			Set<State> destinationStates = new HashSet<State>();

			for (PluggingTransition incomingTransition : this.replacement
					.getIncomingPorts()) {
				if (Utils.isIncomingEqual(incomingTransition, reachabilityEntry
						.getKey().getValue())) {
					destinationStates.addAll(intersectionBuilder
							.getIntersectionStates(reachabilityEntry.getKey()
									.getValue().getDestination(),
									incomingTransition.getDestination()));
				}
			}
			for (State sourceState : sourceStates) {
				for (State destinationState : destinationStates) {
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
								.addTransition(sourceState, destinationState,
										transitionFactory.create());
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
	private void addingRedState(IntersectionBuilder intersectionBuilder,
			TransitionFactory<State, Transition> transitionFactory,
			StateFactory stateFactory) {
		State redState = stateFactory.create("RED");
		intersectionBuilder.getIntersectionAutomaton().addAcceptState(redState);
		intersectionBuilder.getIntersectionAutomaton().addTransition(redState,
				redState, transitionFactory.create());
		for (ColoredPluggingTransition outTransitionSubProperty : this.subproperty
				.getOutgoingTransitions()) {
			if (outTransitionSubProperty.getColor() == Color.RED) {
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
	 * @param greenState
	 */
	private void addingGreenState(IntersectionBuilder intersectionBuilder,
			TransitionFactory<State, Transition> transitionFactory,
			StateFactory stateFactory) {
		State greenState = stateFactory.create("GREEN");
		intersectionBuilder.getIntersectionAutomaton().addState(greenState);
		intersectionBuilder.getIntersectionAutomaton().addInitialState(
				greenState);

		for (ColoredPluggingTransition initTransitionSubProperty : this.subproperty
				.getIncomingTransitions()) {
			if (initTransitionSubProperty.getColor() == Color.GREEN) {
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

						
						intersectionBuilder.updateIntersection(initTransitionReplacement
												.getDestination(), initTransitionSubProperty
												.getDestination(), 0);
						
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

}