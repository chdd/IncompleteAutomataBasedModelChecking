package it.polimi.automata.io.out;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.transition.Transition;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class TransitionToElementTransformer
		implements Transformer<Transition, Element> {

	private final BA automaton;

	private final Document doc;

	public TransitionToElementTransformer(BA automaton, Document doc) {
		Preconditions.checkNotNull(automaton, "The automaton cannot be null");
		Preconditions.checkNotNull(doc, "The document cannot be null");

		this.automaton = automaton;
		this.doc = doc;
	}

	@Override
	public Element transform(Transition transition) {
		Preconditions.checkNotNull(transition, "The transition element to be converted cannot be null");
		
		Element transitionElement = doc
				.createElement(Constants.XML_ELEMENT_TRANSITION);
		// adding the id
		Attr id = doc.createAttribute(Constants.XML_ATTRIBUTE_ID);
		id.setValue(Integer.toString(transition.getId()));
		transitionElement.setAttributeNode(id);

		// adding the source
		Attr sourceId = doc
				.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE);
		sourceId.setValue(Integer.toString(this.automaton
				.getTransitionSource(transition).getId()));
		transitionElement.setAttributeNode(sourceId);

		// adding the destination
		Attr destinationId = doc
				.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION);
		destinationId.setValue(Integer.toString(this.automaton
				.getTransitionDestination(transition).getId()));
		transitionElement.setAttributeNode(destinationId);

		// adding the propositions
		Attr propositions = doc
				.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);

		propositions.setValue(new IGraphPropositionsToStringTransformer().transform(transition.getPropositions()));
		transitionElement.setAttributeNode(propositions);
		return transitionElement;
	}

}
