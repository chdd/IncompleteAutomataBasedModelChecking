package it.polimi.refinementchecker.checker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.constraints.Color;
import it.polimi.constraints.Port;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;

import com.google.common.base.Preconditions;

/**
 * Given an automaton, its in-coming and out-coming ports it sets the state
 * which are destinations of in-coming ports as initial and the one which are
 * sources of out-coming ports as final (accepting)
 * 
 * @author claudiomenghi
 *
 */
public class AutomatonDecorator {

	/**
	 */
	public AutomatonDecorator() {
	}

	public void decorateClaim(BA claim, SubProperty subproperty) {
		Preconditions.checkNotNull(claim);
		Preconditions.checkNotNull(subproperty);

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
		claim.addStuttering();
		for (Port outPort : subproperty.getOutcomingPorts()) {
			if (outPort.getColor().equals(Color.RED)) {
				Preconditions
						.checkArgument(
								claim.getStates().contains(outPort.getSource()),
								"The source of an out-coming port must be contained in the set of the states of the claim");
				claim.addAcceptState(outPort.getSource());
			}
		}
		claim.addStuttering();
	}
	
	public void decorateModel(IBA model, Replacement replacement){
		Preconditions.checkNotNull(model);
		Preconditions.checkNotNull(replacement);

		for (Port incomingPort : replacement.getIncomingPorts()) {
			Preconditions.checkArgument(model.getStates().contains(
					incomingPort.getDestination()), "The destination of an incoming port must be contained in the set of the states of the model");
			model.addInitialState(incomingPort.getDestination());
		}
		for (Port outcomingPort : replacement.getOutcomingPorts()) {
			Preconditions.checkArgument(model.getStates().contains(
					outcomingPort.getSource()), "The source of an outcoming port must be contained in the set of the states of the model");
			model.addAcceptState(outcomingPort.getSource());
		}
		model.addStuttering();
	}
}
