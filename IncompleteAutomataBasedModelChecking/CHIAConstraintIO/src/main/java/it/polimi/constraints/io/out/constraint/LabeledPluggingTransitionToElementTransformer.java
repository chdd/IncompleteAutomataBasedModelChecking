package it.polimi.constraints.io.out.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.automata.io.out.states.StateToElementTrasformer;
import it.polimi.constraints.io.ConstraintsIOConstants;
import it.polimi.constraints.io.out.TransToElementTransformer;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * converts an incoming or outgoing transition of the sub-property into the corresponding XML element
 * 
 * @author claudiomenghi
 *
 */
public class LabeledPluggingTransitionToElementTransformer extends XMLTrasformer<LabeledPluggingTransition, Element> {

	
	/**
	 * creates a new Port element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 * @throws NullPointerException
	 *             if the document is null
	 */
	public LabeledPluggingTransitionToElementTransformer(Document doc) {
		super(doc);
	}

	public Element transform(LabeledPluggingTransition labeledPlugTransition) throws ParserConfigurationException {
		Preconditions.checkNotNull(labeledPlugTransition, "The port element cannot be null");
		Document doc=this.getDocument();
		Element portElement = doc
				.createElement(ConstraintsIOConstants.XML_ELEMENT_LABELED_PLUG_TRANSITION);

		Attr portId = doc.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID);
		portId.setValue(Integer.toString(labeledPlugTransition.getId()));
		portElement.setAttributeNode(portId);

		Attr portColor = doc
				.createAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_LABEL);
		portColor.setValue(labeledPlugTransition.getColor().toString());
		portElement.setAttributeNode(portColor);

		// transition source
		Attr nextPortColor = doc
				.createAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_LABEL);
		nextPortColor.setValue(labeledPlugTransition.getColor().toString());
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
				.transform(labeledPlugTransition.getSource());
		// contains the element which corresponds to the destination of the
		// transition
		Element transitionDestination = new StateToElementTrasformer(doc)
				.transform(labeledPlugTransition.getDestination());

		transitionSourceStateContainer.appendChild(transitionSource);
		transitionDestinationStateContainer.appendChild(transitionDestination);
		Element transitionElement = new TransToElementTransformer(doc)
				.transform(labeledPlugTransition.getTransition());

		portElement.appendChild(transitionElement);

		return portElement;
	}
}
