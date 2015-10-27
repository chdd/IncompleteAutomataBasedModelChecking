package it.polimi.automata.io.out.propositions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * transforms the proposition into the corresponding XML element
 * 
 * @author Claudio Menghi
 *
 */
public class PropositionToElementTrasformer implements
		Transformer<IGraphProposition, Element> {

	/**
	 * contains the document where the XML element must be written to
	 */
	protected final Document doc;

	/**
	 * creates a new PropositionToElementTransformed
	 * 
	 * @param doc
	 *            the document where the XML element must be written to
	 * @throws NullPointerException
	 *             if the document is null
	 */
	public PropositionToElementTrasformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document cannot be null");

		this.doc = doc;
	}

	/**
	 * transforms the proposition into the corresponding XML element
	 * 
	 * @param input
	 *            the proposition to be converted
	 * @throws NullPointerException
	 *             if the proposition is null
	 */
	@Override
	public Element transform(IGraphProposition input) {

		Preconditions.checkNotNull(input,
				"The proposition to be transformed cannot be null");
		Element propositionXMLElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PROPOSITION);

		propositionXMLElement.setAttribute(
				AutomataIOConstants.XML_ELEMENT_PROPOSITION_NAME,
				input.getFullLabel());
		return propositionXMLElement;
	}
}
