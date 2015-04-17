package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.constraints.Port;
import it.polimi.constraints.SubProperty;

import java.util.Set;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms the component to an XML element.
 * 
 * @author claudiomenghi
 *
 */
public class SubPropertyToElementTransformer
		implements Transformer<SubProperty, Element> {

	private final Document doc;
	private final IBA model;
	private final IntersectionBA intersectionBA;
	
	/**
	 * creates a new PortsGraph To Element Transformer element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public SubPropertyToElementTransformer(Document doc, IBA model, IntersectionBA intersectionBA) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");
		this.doc = doc;
		this.model=model;
		this.intersectionBA=intersectionBA;
	}
	
	@Override
	public Element transform(SubProperty input) {
		
		// root elements
		Element constraintElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_SUBPROPERTY);
	
		// adding the id
		Attr modelTransparentStateIDd = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_MODEL_STATE_ID);
		modelTransparentStateIDd.setValue(Integer.toString(input
				.getModelState().getId()));
		constraintElement.setAttributeNode(modelTransparentStateIDd);

		// adding the name of the state
		Attr modelTransparentStateName = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);
		modelTransparentStateName.setValue(input.getModelState().getName());
		constraintElement.setAttributeNode(modelTransparentStateName);
		
		Element baElement =new BAToElementTrasformer(doc).transform(input.getAutomaton());
		constraintElement.appendChild(baElement);

		

		// adding the outComing Ports
		Element outComingPorts = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PORTS_OUT);
		constraintElement.appendChild(outComingPorts);
		this.addPorts(doc, outComingPorts, input.getOutcomingPorts());

		// adding the incoming Ports
		Element inComingPorts = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PORTS_IN);
		constraintElement.appendChild(inComingPorts);
		this.addPorts(doc, inComingPorts, input.getIncomingPorts());

		return constraintElement;
	}
	
	

	private void addPorts(Document doc, Element portsElement,
			Set<Port> ports) {

		PortToElementTransformer transformer = new PortToElementTransformer(
				doc, model, intersectionBA);
		for (Port port : ports) {
			Element portElement = transformer.transform(port);
			portsElement.appendChild(portElement);
		}
	}
	
	
}
