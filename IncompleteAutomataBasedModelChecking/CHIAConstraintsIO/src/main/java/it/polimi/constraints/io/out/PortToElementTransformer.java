package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.out.states.StateToElementTrasformer;
import it.polimi.automata.io.out.transitions.TransitionToElementTransformer;
import it.polimi.constraints.Port;
import it.polimi.constraints.io.ConstraintsIOConstants;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms a port object into the corresponding XML element representation.
 * 
 * @author claudiomenghi
 *
 */
public class PortToElementTransformer implements Transformer<Port, Element> {

	private final Document doc;

	/**
	 * creates a new Port element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 * @throws NullPointerException
	 *             if the document is null
	 */
	public PortToElementTransformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");

		this.doc = doc;
	}

	public Element transform(Port port) {
		Preconditions.checkNotNull(port, "The port element cannot be null");
		Element portElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PORT);

		Attr portId = doc.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID);
		portId.setValue(Integer.toString(port.getId()));
		portElement.setAttributeNode(portId);

		Attr portColor = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_COLOR);
		portColor.setValue(port.getColor().toString());
		portElement.setAttributeNode(portColor);

		// transition source
		Attr nextPortColor = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_COLOR);
		nextPortColor.setValue(port.getColor().toString());
		portElement.setAttributeNode(nextPortColor);

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
