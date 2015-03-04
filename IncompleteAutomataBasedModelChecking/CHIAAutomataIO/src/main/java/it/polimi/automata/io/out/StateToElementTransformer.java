package it.polimi.automata.io.out;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;


public class StateToElementTransformer<S extends State, T extends Transition, A extends BA<S, T>> implements Transformer<S, Element> {

	private final A automaton;
	
	private final Document doc;
	
	public StateToElementTransformer(A automaton, Document doc){
		Preconditions.checkNotNull(automaton, "The automaton cannot be null");
		Preconditions.checkNotNull(doc, "The document cannot be null");
		
		this.automaton=automaton;
		this.doc=doc;
	}
	
	@Override
	public Element transform(S input) {
		Preconditions.checkNotNull(input, "The input state cannot be null");

		Element stateXMLElement = doc.createElement(Constants.XML_ELEMENT_STATE);
		
		// adding the id
		Attr id = doc.createAttribute(Constants.XML_ATTRIBUTE_ID);
		id.setValue(Integer.toString(input.getId()));
		stateXMLElement.setAttributeNode(id);

		// adding the name
		Attr name = doc.createAttribute(Constants.XML_ATTRIBUTE_NAME);
		name.setValue(input.getName());
		stateXMLElement.setAttributeNode(name);

		if (this.automaton.getInitialStates().contains(input)) {
			// adding the id
			Attr initial = doc
					.createAttribute(Constants.XML_ATTRIBUTE_INITIAL);
			initial.setValue(Constants.TRUEVALUE);
			stateXMLElement.setAttributeNode(initial);
		}
		if (this.automaton.getAcceptStates().contains(input)) {
			// adding the id
			Attr accepting = doc
					.createAttribute(Constants.XML_ATTRIBUTE_ACCEPTING);
			accepting.setValue(Constants.TRUEVALUE);
			stateXMLElement.setAttributeNode(accepting);
		}
		return stateXMLElement;
	}

}
