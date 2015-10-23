package it.polimi.automata.io.out.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.BA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * Transforms a State of a BA into the corresponding XML element
 * 
 * @author Claudio Menghi
 *
 */
public class BAStateToElementTransformer extends StateToElementTrasformer
		implements Transformer<State, Element> {

	/**
	 * is the Buchi automaton containing the state
	 */
	protected final BA automaton;

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
	public BAStateToElementTransformer(BA automaton, Document doc) {

		super(doc);
		Preconditions.checkNotNull(automaton, "The automaton cannot be null");

		this.automaton = automaton;
	}

	/**
	 * transforms the state into the corresponding element
	 * 
	 * @param input
	 *            is the state to be transformed
	 * @throws NullPointerException
	 *             if the state to be transformed is null
	 * @throws IllegalArgumentException
	 *             if the state is not a state of the BA
	 */
	@Override
	public Element transform(State input) {
		Preconditions.checkNotNull(input, "The input state cannot be null");
		Preconditions.checkArgument(automaton.getStates().contains(input),
				"The state must be a state of the BA");

		Element stateXMLElement = super.transform(input);
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
