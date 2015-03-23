package it.polimi.constraints.io.in;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.in.propositions.StringToModelPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Color;
import it.polimi.constraints.Port;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms an XML element which represents a port into the corresponding port
 * Java element.
 * 
 * @author claudiomenghi
 *
 */
public class ElementToPortTransformer {

	/**
	 * creates a new Transformer which converts an XML element into the corresponding
	 * port
	 */
	public ElementToPortTransformer() {

	}

	/**
	 * transforms the XML element e into the corresponding port
	 * 
	 * @param e
	 *            is the element to be converted
	 * @return the corresponding port
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public Port transform(Element e) {
		Preconditions.checkNotNull(e,
				"The element to be converted cannot be null");

		String labels = e
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);
		StringToModelPropositions propositionsParser = new StringToModelPropositions();

		int transitionId = Integer.parseInt(e
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID));

		Port.ID_COUNTER = Math.max(Port.ID_COUNTER, transitionId + 1);
		Transition transition = new ModelTransitionFactory().create(
				transitionId, propositionsParser.computePropositions(labels));

		int sourceStateId = Integer.parseInt(e
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE));

		State sourceState = new StateFactory().create(
				Integer.toString(sourceStateId), sourceStateId);

		Boolean incoming = Boolean.parseBoolean(e
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INCOMING));

		int destinationStateId = Integer.parseInt(e
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION));

		Color color = Color.valueOf(e
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_COLOR));

		State destinationState = new StateFactory().create(
				Integer.toString(destinationStateId), destinationStateId);

		return new Port(sourceState, destinationState, transition, incoming,
				color);
	}
}
