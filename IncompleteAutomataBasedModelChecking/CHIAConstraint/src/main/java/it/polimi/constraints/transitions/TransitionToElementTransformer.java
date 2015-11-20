package it.polimi.constraints.transitions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.out.propositions.IGraphPropositionsToStringTransformer;
import it.polimi.automata.transition.Transition;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * Transforms the transition into the corresponding XML element it adds only the
 * id of the transition and its proposition (it does not add attribute for its
 * source and destination)
 * 
 * @author Claudio Menghi
 *
 */
public class TransitionToElementTransformer implements
		Transformer<Transition, Element> {

	/**
	 * contains the document where the XML element must be written to
	 */
	protected final Document doc;

	/**
	 * Creates a new Transition to element transformer
	 * 
	 * @param doc
	 *            is the document where the XML element must be added
	 * @throws NullPointerException
	 *             if the document doc is null
	 */
	public TransitionToElementTransformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document cannot be null");
		this.doc = doc;
	}

	/**
	 * transforms the transition into the corresponding XML element. The XML
	 * element has two attributes the ID and the propositions of the transition
	 * 
	 * @param transition
	 *            the transition to be transformed
	 * @throws NullPointerException
	 *             if the transition is null
	 */
	@Override
	public Element transform(Transition transition) {
		Preconditions.checkNotNull(transition,
				"The transition element to be converted cannot be null");

		Element transitionElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_TRANS);
		// adding the id
		Attr id = doc.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID);
		id.setValue(Integer.toString(transition.getId()));
		transitionElement.setAttributeNode(id);

		// adding the propositions
		Attr propositions = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);

		propositions.setValue(new IGraphPropositionsToStringTransformer()
				.transform(transition.getPropositions()));
		transitionElement.setAttributeNode(propositions);

		return transitionElement;
	}
}
