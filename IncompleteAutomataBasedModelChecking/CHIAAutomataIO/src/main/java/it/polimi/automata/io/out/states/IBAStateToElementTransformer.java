package it.polimi.automata.io.out.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class IBAStateToElementTransformer extends BAStateToElementTransformer {

	public IBAStateToElementTransformer(IBA automaton, Document doc) {
		super(automaton, doc);
	}

	@Override
	public Element transform(State input) {
		Preconditions.checkNotNull(input, "The input state cannot be null");

		Element stateXMLElement = super.transform(input);

		
		if (((IBA) this.automaton).getTransparentStates().contains(input)) {
			Attr transparent = doc
					.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSPARENT);
			transparent.setValue(AutomataIOConstants.TRUEVALUE);
			stateXMLElement.setAttributeNode(transparent);

		}
		return stateXMLElement;
	}
}