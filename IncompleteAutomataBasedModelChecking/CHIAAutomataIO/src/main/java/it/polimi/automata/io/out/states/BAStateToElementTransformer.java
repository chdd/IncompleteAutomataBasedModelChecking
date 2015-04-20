package it.polimi.automata.io.out.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.BA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class BAStateToElementTransformer extends StateToElementTrasformer  implements Transformer<State, Element> {

	protected final BA automaton;

	public BAStateToElementTransformer(BA automaton, Document doc) {
		
		super(doc);
		Preconditions.checkNotNull(automaton, "The automaton cannot be null");

		this.automaton = automaton;
	}

	@Override
	public Element transform(State input) {
		Preconditions.checkNotNull(input, "The input state cannot be null");


		Element stateXMLElement=super.transform(input);
		if (this.automaton.getInitialStates().contains(input)) {
			// adding the id
			Attr initial = doc
					.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_INITIAL);
			initial.setValue(AutomataIOConstants.TRUEVALUE);
			stateXMLElement.setAttributeNode(initial);
		}
		if (this.automaton.getAcceptStates().contains(input)) {
			// adding the id
			Attr accepting = doc
					.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING);
			accepting.setValue(AutomataIOConstants.TRUEVALUE);
			stateXMLElement.setAttributeNode(accepting);
		}
		return stateXMLElement;
	}
}
