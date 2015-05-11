package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.SubProperty;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Transforms a constraint into the corresponding XML Element.
 * 
 * @author Claudio1
 *
 */
public class ConstraintToElementTransformer implements
		Transformer<Constraint, Element> {

	/**
	 * takes as input a constraint and returns the corresponding XML element
	 * 
	 * @param constraint
	 *            the constraint to be converted into the corresponding XML
	 *            element
	 */
	@Override
	public Element transform(Constraint constraint)
			throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_CONSTRAINT);
		doc.appendChild(rootElement);
		
		SubPropertyToElementTransformer componentTransformer = new SubPropertyToElementTransformer(doc
				);
		for (SubProperty component : constraint.getSubProperties()) {

			Element componentElement = componentTransformer
					.transform(component);
			rootElement.appendChild(componentElement);

		}

		Element graphPortXMLElement = new PortsGraphToElementTransformer(doc)
				.transform(constraint.getPortsGraph());
		rootElement.appendChild(graphPortXMLElement);
		return rootElement;
	}
}
