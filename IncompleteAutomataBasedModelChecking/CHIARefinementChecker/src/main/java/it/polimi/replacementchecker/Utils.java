package it.polimi.replacementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

public class Utils {

	
	/**
	 * @param additionalInitStatesModel
	 * @param additionalInitStatesClaim
	 * @param intersectionBuilder
	 * @param lowerIntersectionBA
	 */
	public static void removeArtificiallyInjectedInitialStates(
			Set<State> additionalInitStatesModel,
			Set<State> additionalInitStatesClaim,
			IntersectionBuilder intersectionBuilder,
			IntersectionBA lowerIntersectionBA, BA claim, IBA model) {
		for (State modelInit : additionalInitStatesModel) {
			for (State claimInit : claim.getInitialStates()) {
				for (State intInit : intersectionBuilder.getIntersectionStates(
						claimInit, modelInit)) {
					if (lowerIntersectionBA.getInitialStates()
							.contains(intInit)) {
						lowerIntersectionBA.removeInitialState(intInit);
					}
				}
			}
		}
		for (State claimInit : additionalInitStatesClaim) {
			for (State modelInit : model.getInitialStates()) {
				for (State intInit : intersectionBuilder.getIntersectionStates(
						claimInit, modelInit)) {
					if (lowerIntersectionBA.getInitialStates()
							.contains(intInit)) {
						lowerIntersectionBA.removeInitialState(intInit);
					}
				}
			}
		}
	}
	
	/**
	 * adds each state that is a destination of an incoming transition of the
	 * replacement to the set of the initial states of the incomplete Buchi
	 * automaton associated to the replacement
	 * 
	 * @param model
	 *            is the IBA associated to the replacement
	 * @return the set of the initial states added to the model
	 * @throws NullPointerException
	 *             if the model to be considered is null
	 */
	public static Set<State> getAdditionalModelInitialStates(IBA model, Replacement replacement) {
		Preconditions.checkNotNull(model,
				"The model to be considered cannot be null");

		Set<State> modelInitialStates = new HashSet<State>();
		for (PluggingTransition initTransitionReplacement : replacement
				.getIncomingTransitions()) {
			if (!model.getInitialStates().contains(
					initTransitionReplacement.getDestination())) {
				State newInitialState = initTransitionReplacement
						.getDestination();
				modelInitialStates.add(newInitialState);
				model.addInitialState(newInitialState);
			}
		}
		for (PluggingTransition outTransitionReplacement : replacement
				.getOutgoingTransitions()) {
			if (!model.getInitialStates().contains(
					outTransitionReplacement.getSource())) {
				State newInitialState = outTransitionReplacement.getSource();
				modelInitialStates.add(newInitialState);
				model.addInitialState(newInitialState);
			}
		}
		return modelInitialStates;

	}
	
	/**
	 * adds each state that is a destination of an incoming transition of the
	 * sub-property to the set of the initial states of the Buchi automaton
	 * associated to the sub-property
	 * 
	 * @param claim
	 *            is the BA associated to the sub-property
	 * @return the set of the initial states added to the claim associated to
	 *         the sub-property
	 * @throws NullPointerException
	 *             if the claim to be considered is null
	 */
	public static Set<State> getAdditionalClaimInitialStates(BA claim, SubProperty subProperty) {
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		Set<State> claimInitialStates = new HashSet<State>();
		for (ColoredPluggingTransition initTransitionSubProperty : subProperty
				.getIncomingTransitions()) {
			if (!claim.getInitialStates().contains(
					initTransitionSubProperty.getDestination())) {
				State newInitialState = initTransitionSubProperty
						.getDestination();
				claimInitialStates.add(newInitialState);
				claim.addInitialState(newInitialState);
			}
		}
		for (ColoredPluggingTransition outTransitionSubProperty : subProperty
				.getOutgoingTransitions()) {
			if (!claim.getInitialStates().contains(
					outTransitionSubProperty.getSource())) {
				State newInitialState = outTransitionSubProperty.getSource();
				claimInitialStates.add(newInitialState);
				claim.addInitialState(newInitialState);
			}
		}
		return claimInitialStates;
	}
	
	public static boolean isOutgoingEqual(PluggingTransition replacementTransition,
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

	public static boolean isIncomingEqual(PluggingTransition replacementTransition,
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
