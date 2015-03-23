package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.out.PropositionsToStringTransformer;
import it.polimi.constraints.Port;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 *  transforms a port object into the corresponding XML element representation.
 * 
 * @author claudiomenghi
 *
 */
public class PortToElementTransformer
		implements Transformer<Port, Element> {

	private final Document doc;

	/**
	 * creates a new Port element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public PortToElementTransformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");
		this.doc = doc;
	}

	public Element transform(Port port) {
		Preconditions.checkNotNull(port, "The port element cannot be null");
		Element portElement = doc.createElement(AutomataIOConstants.XML_ELEMENT_PORT);
		
		Attr type = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_PORT_TYPE);
		
		if(port.isIncoming()){
			type.setValue(AutomataIOConstants.XML_ATTRIBUTE_VALUE_IN);
		}
		else{
			type.setValue(AutomataIOConstants.XML_ATTRIBUTE_VALUE_OUT);	
		}
		
		portElement.setAttributeNode(type);


		// transition source
		Attr transitionSource = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE);
		transitionSource.setValue(Integer.toString(port.getSource().getId()));
		portElement.setAttributeNode(transitionSource);

		// transition destination
		Attr transitionDestination = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION);
		transitionDestination.setValue(Integer.toString(port.getDestination()
				.getId()));
		portElement.setAttributeNode(transitionDestination);

		// transition id
		Attr transitionId = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_ID);
		transitionId.setValue(Integer.toString(port.getId()));
		portElement.setAttributeNode(transitionId);

		// transition label
		Attr transitionPropositions = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);

		PropositionsToStringTransformer transformer = new PropositionsToStringTransformer();
		transitionPropositions.setValue(transformer.transform(port
				.getTransition().getPropositions()));
		portElement.setAttributeNode(transitionPropositions);


		// transition source
		Attr nextPortColor = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_COLOR);
		nextPortColor.setValue(port.getColor().toString());
		portElement.setAttributeNode(nextPortColor);
		
		return portElement;
	}
}
