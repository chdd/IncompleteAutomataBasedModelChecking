package it.polimi.refinementchecker.checker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.constraints.Color;
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
	 * and marks as initial each state that is reached from a green incoming
	 * transition and as accepting each state left by a red outcoming
	 * transition. The accepting states generated are also equipped with a
	 * self-loop transition marked with a stuttering character.
	 * 
	 * @param claim
	 *            is the claim which corresponds to the subproperty
	 * @param subproperty
	 *            is the subproperty to be considered
	 * @throws NullPointerException
	 *             if the claim or the subproperty is null
	 */
	public void decorateClaim(BA claim, SubProperty subproperty) {
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		Preconditions.checkNotNull(subproperty,
				"The subproperty to be considered cannot be null");

		for (Port inPort : subproperty.getIncomingPorts()) {
			if (inPort.getColor().equals(Color.GREEN)) {
				Preconditions
						.checkArgument(
								claim.getStates().contains(
										inPort.getDestination()),
								"The destination of an incoming port must be contained in the set of the states of the claim");
				claim.addInitialState(inPort.getDestination());
			}
		}
		for (Port outPort : subproperty.getOutcomingPorts()) {
			if (outPort.getColor().equals(Color.RED)) {
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
	 * by an incoming transition as initial and each state left by an outcoming
	 * transition as accepting. As previously, the accepting states generated
	 * are also equipped with a self-loop transition marked with a stuttering
	 * character.
	 * 
	 * @param model
	 *            is the model to be considered
	 * @param replacement
	 *            is the replacement to which the model belongs
	 * @throws NullPointerException
	 *             if the model or the replacement is null
	 */
	public void decorateModel(IBA model, Replacement replacement) {
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
			model.addInitialState(incomingPort.getDestination());
		}
		for (Port outcomingPort : replacement.getOutcomingPorts()) {
			Preconditions
					.checkArgument(
							model.getStates().contains(
									outcomingPort.getSource()),
							"The source of an outcoming port must be contained in the set of the states of the model");
			model.addAcceptState(outcomingPort.getSource());
			model.addStuttering(outcomingPort.getSource());
		}
	}
}
