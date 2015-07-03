package it.polimi.constraints.io.out.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.automata.io.out.BAToElementTrasformer;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.out.constraint.reachability.ReachabilityToElementTransformer;
import it.polimi.constraints.transitions.ColoredPluggingTransition;

import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * it is used to  transform the sub-property into the corresponding XML element.
 * 
 * @author claudiomenghi
 *
 */
public class SubPropertyToElementTrasformer extends XMLTrasformer<SubProperty, Element>{


	/**
	 * creates a new PortsGraph To Element Transformer element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public SubPropertyToElementTrasformer(Document doc) {
		super(doc);
	}

	@Override
	public Element transform(SubProperty input) throws Exception {

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

		Attr indispensable = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_SUBPROPERTY_INDISPESNABLE);
		indispensable.setValue(Boolean.toString(input.isIndispensable()));
		constraintElement.setAttributeNode(indispensable);
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
		this.addPorts(inComingPorts, input.getIncomingTransitions());

		// adding the outComing Ports
		Element outComingPorts = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PORTS_OUT);
		constraintElement.appendChild(outComingPorts);
		this.addPorts(outComingPorts, input.getOutgoingTransitions());

		Element lowerReachability=new ReachabilityToElementTransformer(this.getDocument()).transform(input.getLowerReachabilityRelation());
		Element lower = doc
				.createElement(AutomataIOConstants.XML_LOWER_REACHABILITY);
		lower.appendChild(lowerReachability);
		constraintElement.appendChild(lower);
		
		Element higherReachability=new ReachabilityToElementTransformer(this.getDocument()).transform(input.getUpperReachabilityRelation());
		Element upper = doc
				.createElement(AutomataIOConstants.XML_UPPER_REACHABILITY);
		upper.appendChild(higherReachability);
		constraintElement.appendChild(upper);
		
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
	private void addPorts(Element portsElement, Set<ColoredPluggingTransition> ports) throws ParserConfigurationException {
		Preconditions.checkNotNull(portsElement, "The element where the ports must be added cannot be null");
		Preconditions.checkNotNull(ports, "The set of the ports to be added cannot be null");
		// create a new port transformed
		ColoredPluggingTransitionToElementTransformer transformer = new ColoredPluggingTransitionToElementTransformer(this.getDocument()
				);
		// transforms each port into the corresponding port element 
		for (ColoredPluggingTransition port : ports) {
			Element portElement = transformer.transform(port);
			portsElement.appendChild(portElement);
		}
	}
}
