package it.polimi.constraints.io.out.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
	 * @throws Exception 
	 */
	@Override
	public Element transform(Constraint constraint)
			throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_CONSTRAINT);
		doc.appendChild(rootElement);
		
		SubPropertyToElementTrasformer subPropertyTransformer = new SubPropertyToElementTrasformer(doc
				);
		for (SubProperty subProperty : constraint.getSubProperty()) {

			Element componentElement = subPropertyTransformer
					.transform(subProperty);
			rootElement.appendChild(componentElement);

		}

				return rootElement;
	}
}
