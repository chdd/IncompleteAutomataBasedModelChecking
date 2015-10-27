package it.polimi.automata.io.out.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * Transforms a State of the intersection automaton into the corresponding XML
 * element
 * 
 * @author Claudio Menghi
 *
 */
public class IntBAStateToElementTransformer extends BAStateToElementTransformer
		implements Transformer<State, Element> {

	/**
	 * creates a new state transformer
	 * 
	 * @param automaton
	 *            is the Buchi automaton containing the state
	 * @param doc
	 *            is the document that must be modified by the transformer
	 * @throws NullPointerException
	 *             if the document or the BA is null
	 */
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
