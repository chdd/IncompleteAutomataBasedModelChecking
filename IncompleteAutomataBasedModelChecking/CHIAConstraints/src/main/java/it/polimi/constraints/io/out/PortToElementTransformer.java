package it.polimi.constraints.io.out;

import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.transformer.propositions.PropositionsToStringTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Port;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms an object of type Port into an XML element
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the state involved in the port
 * @param <I>
 *            is the type of the transition included in the port
 */
public class PortToElementTransformer<S extends State, T extends Transition>
		implements Transformer<Port<S, T>, Element> {

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

	public Element transform(Port<S, T> port) {
		Preconditions.checkNotNull(port, "The port element cannot be null");
		Element portElement = doc.createElement(Constants.XML_ELEMENT_PORT);
		
		Attr type = doc
				.createAttribute(Constants.XML_ATTRIBUTE_PORT_TYPE);
		
		if(port.isIncoming()){
			type.setValue(Constants.XML_ATTRIBUTE_VALUE_IN);
		}
		else{
			type.setValue(Constants.XML_ATTRIBUTE_VALUE_OUT);	
		}
		
		portElement.setAttributeNode(type);


		// transition source
		Attr transitionSource = doc
				.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE);
		transitionSource.setValue(Integer.toString(port.getSource().getId()));
		portElement.setAttributeNode(transitionSource);

		// transition destination
		Attr transitionDestination = doc
				.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION);
		transitionDestination.setValue(Integer.toString(port.getDestination()
				.getId()));
		portElement.setAttributeNode(transitionDestination);

		// transition id
		Attr transitionId = doc
				.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_ID);
		transitionId.setValue(Integer.toString(port.getTransition().getId()));
		portElement.setAttributeNode(transitionId);

		// transition label
		Attr transitionPropositions = doc
				.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);

		PropositionsToStringTransformer transformer = new PropositionsToStringTransformer();
		transitionPropositions.setValue(transformer.transform(port
				.getTransition().getPropositions()));
		portElement.setAttributeNode(transitionPropositions);

		return portElement;
	}
}
