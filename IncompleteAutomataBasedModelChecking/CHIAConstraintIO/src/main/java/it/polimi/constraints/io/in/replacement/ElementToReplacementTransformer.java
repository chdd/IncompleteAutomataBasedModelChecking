package it.polimi.constraints.io.in.replacement;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.ElementToIBATransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.ConstraintsIOConstants;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

/**
 * Transforms an XML element into the corresponding object
 * 
 * @author Claudio Menghi
 *
 */
public class ElementToReplacementTransformer implements
		Transformer<Element, Replacement> {

	/**
	 * Transforms an XML element into a corresponding object
	 */
	public ElementToReplacementTransformer() {
	}

	/**
	 * transforms the XML element into the corresponding object
	 * 
	 * @param input
	 *            is the input of the element
	 * @throws NullPointerException
	 *             if the input is null
	 */
	@Override
	public Replacement transform(Element input) {

		Preconditions.checkNotNull(input, "The input element cannot be null");

		int componentId = Integer
				.parseInt(input
						.getAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_MODEL_STATE_ID));

		String stateName = input
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);

		ElementToIBATransformer elementToIBATransformer = new ElementToIBATransformer();

		IBA ba = elementToIBATransformer.transform((Element) (input
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_IBA)
				.item(0)));

		State modelState = new StateFactory().create(stateName, componentId);

		Set<PluggingTransition> outcomingPorts = new HashSet<PluggingTransition>();
		Element xmlOutComingPorts = (Element) input.getElementsByTagName(
				AutomataIOConstants.XML_ELEMENT_TRANSITIONS_OUT).item(0);
		NodeList xmlOutComingPortsList = xmlOutComingPorts
				.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_PLUG_TRANSITION);
		for (int portId = 0; portId < xmlOutComingPortsList.getLength(); portId++) {
			Element xmlOutComingPort = (Element) xmlOutComingPortsList
					.item(portId);
			PluggingTransition port = new ElementToPluggingTransitionTransformer(
					false).transform(xmlOutComingPort);
			outcomingPorts.add(port);
		}

		Set<PluggingTransition> incomingPorts = new HashSet<PluggingTransition>();
		Element xmlInComingPorts = (Element) input.getElementsByTagName(
				AutomataIOConstants.XML_ELEMENT_TRANSITIONS_IN).item(0);
		NodeList xmlInComingPortsList = xmlInComingPorts
				.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_PLUG_TRANSITION);
		for (int portId = 0; portId < xmlInComingPortsList.getLength(); portId++) {
			Element xmlInComingPort = (Element) xmlInComingPortsList
					.item(portId);
			PluggingTransition port = new ElementToPluggingTransitionTransformer(
					true).transform(xmlInComingPort);
			incomingPorts.add(port);
		}

		Replacement replacement = new Replacement(modelState, ba,
				incomingPorts, outcomingPorts);
		return replacement;
	}
}
