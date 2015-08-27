package it.polimi.automata.io.out.propositions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

public class PropositionToElementTrasformer implements Transformer<IGraphProposition, Element>{
	
	protected final Document doc;

	public PropositionToElementTrasformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document cannot be null");

		this.doc = doc;
	}

	@Override
	public Element transform(IGraphProposition input) {
		
		Element propositionXMLElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PROPOSITION);

		propositionXMLElement.setAttribute(AutomataIOConstants.XML_ELEMENT_PROPOSITION_NAME, input.getFullLabel());
		return propositionXMLElement;
	}
}
