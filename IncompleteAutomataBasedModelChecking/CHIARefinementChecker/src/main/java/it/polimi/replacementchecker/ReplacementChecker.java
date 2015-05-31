package it.polimi.replacementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Color;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Set;

import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * Is used to check the replacement of a transparent state i.e., check whether
 * the original property is satisfied, possibly satisfied or not satisfied given
 * a specific replacement.
 * 
 * It takes as input the constraint and a replacement for one of the transparent
 * states involved in the constraint. The method check returns 1 if the property
 * is satisfied given the current replacement, -1 if the property is possibly
 * satisfied or 0 if the property is not satisfied. When a -1 value is generated
 * the satisfaction of the property may depends on the refinement of other
 * transparent states involved in the constraint or on the refinement of the
 * transparent states of the model specified into the replacement itself.
 * 
 * 
 * @author claudiomenghi
 */
public class ReplacementChecker extends CHIAAction {

	/**
	 * the name of the action
	 */
	private static final String NAME = "REPLACEMENT CHECKER";
	/**
	 * contains the replacement to be verified
	 */
	private final Replacement replacement;

	/**
	 * the sub-property to be considered
	 */
	private final SubProperty subproperty;

	private final AcceptingPolicy acceptingPolicy;

	private IntersectionBA intersectionBA;

	/**
	 * creates a new Refinement Checker. The refinement checker is used to check
	 * the refinement of a transparent state. The refinement checker updates the
	 * constraint associated with the transparent state and the constraints
	 * associated with the other transparent states.
	 * 
	 * @param constraint
	 *            is the constraint that must be considered by the
	 *            RefinementChecker
	 * @param component
	 *            is the replacement to be considered by the refinement checker
	 * @param acceptingPolicy
	 *            is the policy to be used in computing the accepting states
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the sub-property and the replacement refer to different
	 *             transparent states
	 */
	public ReplacementChecker(SubProperty subProperty, Replacement replacement,
			AcceptingPolicy acceptingPolicy) {
		super(NAME);
		Preconditions.checkNotNull(subProperty,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(replacement,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The acceptingPolicy cannot be null");
		this.acceptingPolicy = acceptingPolicy;
		Preconditions
				.checkArgument(
						subProperty.getModelState().equals(
								replacement.getModelState()),
						"The sub-property and the replacement must refer to the same model state\\"
								+ "The sub-property refers to the model state"
								+ subProperty.getModelState()
								+ "\\"
								+ "while the replacement refers to the model state"
								+ replacement.getModelState());
		this.replacement = replacement;
		this.subproperty = subProperty;
	}

	/**
	 * returns the updated constraint
	 * 
	 * @return the updated constraint
	 */
	public SatisfactionValue check() {

		if (this.checkNotSatisfied()) {
			this.performed();
			return SatisfactionValue.NOTSATISFIED;
		}
		if (this.checkPossiblySatisfied()) {
			this.performed();
			return SatisfactionValue.POSSIBLYSATISFIED;
		}
		this.performed();
		return SatisfactionValue.SATISFIED;
	}

	private boolean checkNotSatisfied() {
		BA claim = subproperty.getAutomaton().clone();
		IBA model = this.replacement.getAutomaton().clone();

		Set<State> modelInitialStates = model.getInitialStates();
		Set<State> additionalInitStatesModel = this
				.getAdditionalModelInitialStates();
		Set<State> claimInitialStates = claim.getInitialStates();
		Set<State> additionalInitStatesClaim = this
				.getAdditionalClaimGreenInitialStates();

		for (State s : additionalInitStatesModel) {
			model.addInitialState(s);
		}

		for (State s : additionalInitStatesClaim) {
			claim.addInitialState(s);
		}
		model = new IBATransparentStateRemoval().removeTransparentStates(model);

		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, this.acceptingPolicy);
		this.intersectionBA = intersectionBuilder.computeIntersection();

		TransitionFactory<State, Transition> transitionFactory = new ClaimTransitionFactory();
		StateFactory stateFactory = new StateFactory();

		for (ColoredPluggingTransition initTransitionSubProperty : this.subproperty
				.getIncomingPorts()) {
			if (initTransitionSubProperty.getColor() == Color.GREEN) {
				for (PluggingTransition initTransitionReplacement : this.replacement
						.getIncomingPorts()) {
					// if the two incoming transitions have the same source and
					// the same label
					if (initTransitionReplacement.getSource().equals(
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
							State greenState = stateFactory.create("GREEN");
							intersectionBuilder.getIntersectionAutomaton()
									.addState(greenState);
							intersectionBuilder.getIntersectionAutomaton()
									.addInitialState(greenState);
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(greenState, initialState,
											transitionFactory.create());
						}
					}
				}
			}
		}
		for (ColoredPluggingTransition outTransitionSubProperty : this.subproperty
				.getOutcomingPorts()) {
			if (outTransitionSubProperty.getColor() == Color.RED) {
				for (PluggingTransition outTransitionReplacement : this.replacement
						.getOutcomingPorts()) {
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
							intersectionBuilder.getIntersectionAutomaton()
									.addAcceptState(outState);
							State redState = stateFactory.create("RED");
							intersectionBuilder.getIntersectionAutomaton()
									.addAcceptState(redState);
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(outState, redState,
											transitionFactory.create());
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(redState, redState,
											transitionFactory.create());
						}
					}
				}
			}
		}

		for (Entry<ColoredPluggingTransition, ColoredPluggingTransition> reachabilityEntry : this.subproperty
				.getLowerReachabilityRelation().getMap().entries()) {
			Set<State> sourceStates = intersectionBuilder
					.getClaimIntersectionStates(reachabilityEntry.getKey()
							.getSource());
			Set<State> destinationStates = intersectionBuilder
					.getClaimIntersectionStates(reachabilityEntry.getValue()
							.getSource());
			for (State sourceState : sourceStates) {
				for (State destinationState : destinationStates) {
					intersectionBuilder.getIntersectionAutomaton()
							.addTransition(sourceState, destinationState,
									transitionFactory.create());
				}
			}
		}
		additionalInitStatesModel.removeAll(modelInitialStates);
		additionalInitStatesClaim.removeAll(claimInitialStates);
		for (State modelInit : additionalInitStatesModel) {
			for (State claimInit : additionalInitStatesClaim) {
				for (State intInit : intersectionBuilder.getIntersectionStates(
						claimInit, modelInit)) {
					if (this.intersectionBA.getInitialStates()
							.contains(intInit)) {
						this.intersectionBA.removeInitialState(intInit);
					}
				}
			}
		}

		EmptinessChecker emptinessChecker = new EmptinessChecker(
				intersectionBuilder.getIntersectionAutomaton());

		if (!emptinessChecker.isEmpty()) {
			return true;
		}
		return false;

	}

	private boolean checkPossiblySatisfied() {

		if (!this.subproperty.isIndispensable()) {
			return true;
		}
		BA claim = subproperty.getAutomaton().clone();
		IBA model = this.replacement.getAutomaton().clone();

		Set<State> modelInitialStates=model.getInitialStates();
		Set<State> additionalInitStatesModel=this.getAdditionalModelInitialStates();
		Set<State> claimInitialStates=claim.getInitialStates();
		Set<State> additionalInitStatesClaim=this.getAdditionalClaimGreenAndYellowInitialStates();
		
		for(State s:additionalInitStatesModel){
			model.addInitialState(s);
		}

		for(State s:additionalInitStatesClaim){
			claim.addInitialState(s);
		}
		
		
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, this.acceptingPolicy);
		this.intersectionBA = intersectionBuilder.computeIntersection();

		TransitionFactory<State, Transition> transitionFactory = new ClaimTransitionFactory();
		StateFactory stateFactory = new StateFactory();

		for (ColoredPluggingTransition initTransitionSubProperty : this.subproperty
				.getIncomingPorts()) {
			if (initTransitionSubProperty.getColor() == Color.GREEN
					|| initTransitionSubProperty.getColor() == Color.YELLOW) {
				for (PluggingTransition initTransitionReplacement : this.replacement
						.getIncomingPorts()) {
					// if the two incoming transitions have the same source and
					// the same label
					if (initTransitionReplacement.getSource().equals(
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
							State greenState = stateFactory.create("GREEN");
							intersectionBuilder.getIntersectionAutomaton()
									.addState(greenState);
							intersectionBuilder.getIntersectionAutomaton()
									.addInitialState(greenState);
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(greenState, initialState,
											transitionFactory.create());
						}
					}
				}
			}
		}
		for (ColoredPluggingTransition outTransitionSubProperty : this.subproperty
				.getOutcomingPorts()) {
			if (outTransitionSubProperty.getColor() == Color.RED
					|| outTransitionSubProperty.getColor() == Color.YELLOW) {
				for (PluggingTransition outTransitionReplacement : this.replacement
						.getOutcomingPorts()) {
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
							intersectionBuilder.getIntersectionAutomaton()
									.addAcceptState(outState);
							State redState = stateFactory.create("RED");
							intersectionBuilder.getIntersectionAutomaton()
									.addAcceptState(redState);
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(outState, redState,
											transitionFactory.create());
							intersectionBuilder.getIntersectionAutomaton()
									.addTransition(redState, redState,
											transitionFactory.create());
						}
					}
				}
			}
		}

		for (Entry<ColoredPluggingTransition, ColoredPluggingTransition> reachabilityEntry : this.subproperty
				.getUpperReachabilityRelation().getMap().entries()) {
			Set<State> sourceStates = intersectionBuilder
					.getClaimIntersectionStates(reachabilityEntry.getKey()
							.getSource());
			Set<State> destinationStates = intersectionBuilder
					.getClaimIntersectionStates(reachabilityEntry.getValue()
							.getSource());
			for (State sourceState : sourceStates) {
				for (State destinationState : destinationStates) {
					intersectionBuilder.getIntersectionAutomaton()
							.addTransition(sourceState, destinationState,
									transitionFactory.create());
				}
			}
		}
		additionalInitStatesModel.removeAll(modelInitialStates);
		additionalInitStatesClaim.removeAll(claimInitialStates);
		for (State modelInit : additionalInitStatesModel) {
			for (State claimInit : additionalInitStatesClaim) {
				for (State intInit : intersectionBuilder.getIntersectionStates(
						claimInit, modelInit)) {
					if (this.intersectionBA.getInitialStates()
							.contains(intInit)) {
						this.intersectionBA.removeInitialState(intInit);
					}
				}
			}
		}

		EmptinessChecker emptinessChecker = new EmptinessChecker(
				intersectionBuilder.getIntersectionAutomaton());

		if (!emptinessChecker.isEmpty()) {
			return true;
		}
		return false;

	}

	private Set<State> getAdditionalClaimGreenInitialStates() {
		Set<State> claimInitialStates = new HashSet<State>();
		for (ColoredPluggingTransition initTransitionSubProperty : this.subproperty
				.getIncomingPorts()) {
			if (initTransitionSubProperty.getColor() == Color.GREEN) {
				claimInitialStates.add(initTransitionSubProperty
						.getDestination());
			}
		}
		return claimInitialStates;
	}

	private Set<State> getAdditionalClaimGreenAndYellowInitialStates() {
		Set<State> claimInitialStates = new HashSet<State>();
		for (ColoredPluggingTransition initTransitionSubProperty : this.subproperty
				.getIncomingPorts()) {
			if (initTransitionSubProperty.getColor() == Color.GREEN
					|| initTransitionSubProperty.getColor() == Color.YELLOW) {
				claimInitialStates.add(initTransitionSubProperty
						.getDestination());
			}
		}
		return claimInitialStates;
	}

	private Set<State> getAdditionalModelInitialStates() {
		Set<State> modelInitialStates = new HashSet<State>();
		for (PluggingTransition initTransitionReplacement : this.replacement
				.getIncomingPorts()) {
			modelInitialStates.add(initTransitionReplacement.getDestination());
		}
		return modelInitialStates;

	}

	/**
	 * @return the replacement considered by the replacement checker
	 */
	public Replacement getReplacement() {
		return replacement;
	}

	/**
	 * @return the sub-property considered by the replacement checker
	 */
	public SubProperty getSubproperty() {
		return subproperty;
	}

	public IntersectionBA getIntersectionBA() {
		Preconditions
				.checkArgument(this.isPerformed(),
						"You must check the replacement before getting the intersection ");
		return intersectionBA;
	}
}
