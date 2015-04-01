package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.out.states.BAStateToElementTransformer;
import it.polimi.automata.io.out.states.IBAStateToElementTransformer;
import it.polimi.automata.io.out.transitions.BATransitionToElementTransformer;
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
	private final IBA model;
	private final IntersectionBA intersection;
	
	/**
	 * creates a new Port element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public PortToElementTransformer(Document doc, IBA model, IntersectionBA intersection) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");
		Preconditions.checkNotNull(model, "The model cannot be null");
		Preconditions.checkNotNull(intersection, "The intersection cannot be null");
		
		this.doc = doc;
		this.model=model;
		this.intersection=intersection;
		
	}

	public Element transform(Port port) {
		Preconditions.checkNotNull(port, "The port element cannot be null");
		Element portElement = doc.createElement(AutomataIOConstants.XML_ELEMENT_PORT);
		
		Attr portId = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID);
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

		
		Element transitionSource;
		Element transitionDestination;
		Element transitionElement=new BATransitionToElementTransformer(intersection, doc).transform(port.getTransition());

		if(port.isIncoming()){
			transitionSource =new IBAStateToElementTransformer(this.model, doc).transform(port.getSource());
			transitionDestination=new BAStateToElementTransformer(this.intersection, doc).transform(port.getDestination());
		}
		else{
			transitionSource =new BAStateToElementTransformer(this.intersection, doc).transform(port.getSource());
			transitionDestination=new IBAStateToElementTransformer(this.model, doc).transform(port.getDestination());
		}
		
		portElement.appendChild(transitionSource);
		portElement.appendChild(transitionDestination);
		portElement.appendChild(transitionElement);
		
		return portElement;
	}
}
