package it.polimi.automata.io.out.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * Transforms a State of an IBA into the corresponding XML element
 * 
 * @author Claudio Menghi
 *
 */
public class IBAStateToElementTransformer extends BAStateToElementTransformer {

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
	public IBAStateToElementTransformer(IBA automaton, Document doc) {
		super(automaton, doc);
	}

	/**
	 * transforms the state into the corresponding element
	 * 
	 * @param input
	 *            is the state to be transformed
	 * @throws NullPointerException
	 *             if the state to be transformed is null
	 * @throws IllegalArgumentException
	 *             if the state is not a state of the IBA
	 */
	@Override
	public Element transform(State input) {
		Preconditions.checkNotNull(input, "The input state cannot be null");

		Element stateXMLElement = super.transform(input);

		
		if (((IBA) this.automaton).getBlackBoxStates().contains(input)) {
			Attr blackbox = doc
					.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_BLACKBOX);
			blackbox.setValue(AutomataIOConstants.TRUEVALUE);
			stateXMLElement.setAttributeNode(blackbox);

		}
		return stateXMLElement;
	}
}
