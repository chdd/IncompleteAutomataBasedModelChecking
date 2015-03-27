package it.polimi.automata.io.out.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class IntBAStateToElementTransformer extends BAStateToElementTransformer implements Transformer<State, Element> {

	public IntBAStateToElementTransformer(IntersectionBA automaton, Document doc) {
		super(automaton, doc);
	}

	@Override
	public Element transform(State input) {
		Preconditions.checkNotNull(input, "The input state cannot be null");

		Element stateXMLElement = super.transform(input);

		
		if (((IntersectionBA) this.automaton).getMixedStates().contains(input)) {
			// adding the id
			Attr accepting = doc
					.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_MIXED);
			accepting.setValue(AutomataIOConstants.TRUEVALUE);
			stateXMLElement.setAttributeNode(accepting);

		}
		return stateXMLElement;
	}

}
