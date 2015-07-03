package it.polimi.constraints.io.out.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

/**
 * Transforms a constraint into the corresponding XML Element.
 * 
 * @author Claudio1
 *
 */
public class ConstraintToElementTransformer extends XMLTrasformer<Constraint, Element> {


	public ConstraintToElementTransformer() throws ParserConfigurationException {
		super();
	}
	
 

	/**
	 * takes as input a constraint and returns the corresponding XML element
	 * 
	 * @param constraint
	 *            the constraint to be converted into the corresponding XML
	 *            element
	 * @throws Exception
	 */
	@Override
	public Element transform(Constraint constraint) throws Exception {

		Element rootElement = this.getDocument()
				.createElement(AutomataIOConstants.XML_ELEMENT_CONSTRAINT);
		 this.getDocument().appendChild(rootElement);

		SubPropertyToElementTrasformer subPropertyTransformer = new SubPropertyToElementTrasformer(
				 this.getDocument());
		for (SubProperty subProperty : constraint.getSubProperties()) {

			Element componentElement = subPropertyTransformer
					.transform(subProperty);
			rootElement.appendChild(componentElement);

		}

		return rootElement;
	}
}
