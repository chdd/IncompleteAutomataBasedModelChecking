package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.constraints.ColoredPort;
import it.polimi.constraints.SubProperty;

import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

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
public class SubPropertyToElementTransformer extends XMLTrasformer<SubProperty, Element>{


	/**
	 * creates a new PortsGraph To Element Transformer element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public SubPropertyToElementTransformer(Document doc) {
		super(doc);
	}

	@Override
	public Element transform(SubProperty input) throws ParserConfigurationException {

		Document doc=this.getDocument();
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

		Element baElement = new BAToElementTrasformer(doc).transform(input
				.getAutomaton());
		constraintElement.appendChild(baElement);

		// adding the incoming Ports
		Element inComingPorts = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PORTS_IN);
		constraintElement.appendChild(inComingPorts);
		this.addPorts(inComingPorts, input.getIncomingPorts());

		// adding the outComing Ports
		Element outComingPorts = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PORTS_OUT);
		constraintElement.appendChild(outComingPorts);
		this.addPorts(outComingPorts, input.getOutcomingPorts());

		return constraintElement;
	}

	/**
	 * adds the set of the port to the specified portElement
	 * 
	 * @param portsElement
	 *            is the element where the ports must be added
	 * @param ports
	 *            the set of ports to be added
	 * @throws ParserConfigurationException 
	 * @throws NullPointerException
	 *             if one of the argument is null
	 */
	private void addPorts(Element portsElement, Set<ColoredPort> ports) throws ParserConfigurationException {
		Preconditions.checkNotNull(portsElement, "The element where the ports must be added cannot be null");
		Preconditions.checkNotNull(ports, "The set of the ports to be added cannot be null");
		// create a new port transformed
		ColoredPortToElementTransformer transformer = new ColoredPortToElementTransformer(this.getDocument()
				);
		// transforms each port into the corresponding port element 
		for (ColoredPort port : ports) {
			Element portElement = transformer.transform(port);
			portsElement.appendChild(portElement);
		}
	}
}
