package it.polimi.refinementchecker.checker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.constraints.Color;
import it.polimi.constraints.ColoredPort;
import it.polimi.constraints.Port;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;

import com.google.common.base.Preconditions;

/**
 * The AutomatonDecorator is used by the ReplacementChecker to modify the
 * automata to be considered. It offers two methods: decorateClaim and
 * decorateModel.
 * 
 * @author claudiomenghi
 *
 */
public class AutomatonDecorator {

	/**
	 * creates a new AutomatonDecorator
	 */
	public AutomatonDecorator() {
	}

	

	/**
	 * the decorateClaim method takes the claim contained into the subproperty
	 * and marks as initial each state that is reached from a green or yellow
	 * incoming transition and as accepting each state left by a red or yellow
	 * outcoming transition. The accepting states generated are also equipped
	 * with a self-loop transition marked with a stuttering character. This
	 * procedure allows the client to check the presence of possibly accepting
	 * paths.
	 * 
	 * @param claim
	 *            is the claim which corresponds to the subproperty
	 * @param subproperty
	 *            is the subproperty to be considered
	 * @throws NullPointerException
	 *             if the claim or the subproperty is null
	 */
	public void decoratePossiblyClaim(BA claim, SubProperty subproperty) {
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		Preconditions.checkNotNull(subproperty,
				"The subproperty to be considered cannot be null");

		for (ColoredPort inPort : subproperty.getIncomingPorts()) {
			if (inPort.getColor().equals(Color.GREEN) || inPort.getColor().equals(Color.YELLOW) ) {
				Preconditions
						.checkArgument(
								claim.getStates().contains(
										inPort.getDestination()),
								"The destination of an incoming port must be contained in the set of the states of the claim");
				claim.addInitialState(inPort.getDestination());
			}
		}
		for (ColoredPort outPort : subproperty.getOutcomingPorts()) {
			if (outPort.getColor().equals(Color.RED) || outPort.getColor().equals(Color.YELLOW)) {
				Preconditions
						.checkArgument(
								claim.getStates().contains(outPort.getSource()),
								"The source of an out-coming port must be contained in the set of the states of the claim");
				claim.addAcceptState(outPort.getSource());
				claim.addStuttering(outPort.getSource());
			}
		}
	}

	/**
	 * The decorateModel method takes the IBA that corresponds to the model
	 * contained in the replacement to be consider and mark each state reached
	 * by an incoming transition as initial.
	 * 
	 * @param model
	 *            is the model to be considered
	 * @param replacement
	 *            is the replacement to which the model belongs
	 * @throws NullPointerException
	 *             if the model or the replacement is null
	 */
	public void decorateInitialStatesReplacement(IBA model, Replacement replacement) {
		Preconditions.checkNotNull(model,
				"The model to be considered cannot be null");
		Preconditions.checkNotNull(replacement,
				"The replacement to be considered cannot be null");

		for (Port incomingPort : replacement.getIncomingPorts()) {
			Preconditions
					.checkArgument(
							model.getStates().contains(
									incomingPort.getDestination()),
							"The destination of an incoming port must be contained in the set of the states of the model");
			model.addInitialState(incomingPort.getSource());
			model.addAcceptState(incomingPort.getSource());
			
			model.addPropositions(incomingPort.getTransition().getPropositions());
			model.addTransition(incomingPort.getSource(), incomingPort.getDestination(), incomingPort.getTransition());
		}
	}
	
	/**
	 * The decorateModel method takes the IBA that corresponds to the model
	 * contained in the replacement to be consider and mark each state reached
	 * by an incoming transition as initial.
	 * 
	 * @param model
	 *            is the model to be considered
	 * @param replacement
	 *            is the replacement to which the model belongs
	 * @throws NullPointerException
	 *             if the model or the replacement is null
	 */
	public void decorateFinalStatesReplacement(IBA model, Replacement replacement) {
		Preconditions.checkNotNull(model,
				"The model to be considered cannot be null");
		Preconditions.checkNotNull(replacement,
				"The replacement to be considered cannot be null");

		for (Port outgoingPort : replacement.getOutcomingPorts()) {
			Preconditions
					.checkArgument(
							model.getStates().contains(
									outgoingPort.getSource()),
							"The destination of an incoming port must be contained in the set of the states of the model");
			model.addAcceptState(outgoingPort.getDestination());
			model.addStuttering(outgoingPort.getDestination());
			model.addPropositions(outgoingPort.getTransition().getPropositions());
			model.addTransition(outgoingPort.getSource(), outgoingPort.getDestination(), outgoingPort.getTransition());
		}
	}
	
	/**
	 * the decorateClaim method takes the claim contained into the subproperty
	 * and marks as initial each state that is reached from a green incoming
	 * transition and as accepting each state left by a red outcoming
	 * transition. The accepting states generated are also equipped with a
	 * self-loop transition marked with a stuttering character. This procedure
	 * allows the client to check the presence of accepting paths.
	 * 
	 * @param claim
	 *            is the claim which corresponds to the subproperty
	 * @param subproperty
	 *            is the subproperty to be considered
	 * @throws NullPointerException
	 *             if the claim or the subproperty is null
	 */
	public void decorateGreenInitialStatesSubproperty(BA claim, SubProperty subproperty) {
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		Preconditions.checkNotNull(subproperty,
				"The subproperty to be considered cannot be null");

		for (ColoredPort inPort : subproperty.getIncomingPorts()) {
			if (inPort.getColor().equals(Color.GREEN)) {
				Preconditions
						.checkArgument(
								claim.getStates().contains(
										inPort.getDestination()),
								"The destination of an incoming port must be contained in the set of the states of the claim");
				claim.addInitialState(inPort.getSource());
				claim.addPropositions(inPort.getTransition().getPropositions());
				claim.addTransition(inPort.getSource(), inPort.getDestination(), inPort.getTransition());
			}
		}
		
	}
	public void decorateRedFinalStatesSubproperty(BA claim, SubProperty subproperty) {
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		Preconditions.checkNotNull(subproperty,
				"The subproperty to be considered cannot be null");
		for (ColoredPort outPort : subproperty.getOutcomingPorts()) {
			if (outPort.getColor().equals(Color.RED)) {
				Preconditions
						.checkArgument(
								claim.getStates().contains(outPort.getSource()),
								"The source of an out-coming port must be contained in the set of the states of the claim");
				claim.addAcceptState(outPort.getDestination());
				claim.addStuttering(outPort.getDestination());
				claim.addPropositions(outPort.getTransition().getPropositions());
				claim.addTransition(outPort.getSource(), outPort.getDestination(), outPort.getTransition());
			}
		}
	}
}
