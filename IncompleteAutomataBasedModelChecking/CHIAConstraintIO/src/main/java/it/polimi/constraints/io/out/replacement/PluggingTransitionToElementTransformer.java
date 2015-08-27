package it.polimi.constraints.io.out.replacement;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.out.states.StateToElementTrasformer;
import it.polimi.automata.io.out.transitions.TransitionToElementTransformer;
import it.polimi.constraints.io.ConstraintsIOConstants;
import it.polimi.constraints.transitions.PluggingTransition;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class PluggingTransitionToElementTransformer implements Transformer<PluggingTransition, Element> {

	private final Document doc;

	/**
	 * creates a new Port element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 * @throws NullPointerException
	 *             if the document is null
	 */
	public PluggingTransitionToElementTransformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");

		this.doc = doc;
	}

	public Element transform(PluggingTransition port) {
		Preconditions.checkNotNull(port, "The port element cannot be null");
		Element portElement = doc
				.createElement(ConstraintsIOConstants.XML_ELEMENT_PLUG_TRANSITION);

		Attr portId = doc.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID);
		portId.setValue(Integer.toString(port.getId()));
		portElement.setAttributeNode(portId);

		Element transitionSourceStateContainer = doc
				.createElement(ConstraintsIOConstants.XML_ELEMENT_PORT_SOURCE_STATE);
		Element transitionDestinationStateContainer = doc
				.createElement(ConstraintsIOConstants.XML_ELEMENT_PORT_DESTINATION_STATE);

		portElement.appendChild(transitionSourceStateContainer);
		portElement.appendChild(transitionDestinationStateContainer);

		// contains the element which corresponds to the source of the
		// transition
		Element transitionSource = new StateToElementTrasformer(doc)
				.transform(port.getSource());
		// contains the element which corresponds to the destination of the
		// transition
		Element transitionDestination = new StateToElementTrasformer(doc)
				.transform(port.getDestination());

		transitionSourceStateContainer.appendChild(transitionSource);
		transitionDestinationStateContainer.appendChild(transitionDestination);
		Element transitionElement = new TransitionToElementTransformer(doc)
				.transform(port.getTransition());

		portElement.appendChild(transitionElement);

		return portElement;
	}

}
