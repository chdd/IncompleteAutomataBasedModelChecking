package it.polimi.automata.io.out.transitions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.BA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.transition.Transition;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * Transforms the transition into the corresponding XML element it adds the id
 * of the transition and its proposition the id of the source and the
 * destination of the transition
 * 
 * @author Claudio Menghi
 *
 */
public class BATransitionToElementTransformer extends
		TransitionToElementTransformer implements
		Transformer<Transition, Element> {

	/**
	 * The automaton whose transitions must be transformed
	 */
	protected final BA automaton;

	/**
	 * Creates a new BATransitionToElementTransformer
	 * 
	 * @param doc
	 *            is the document where the XML element must be added
	 * @param automaton
	 *            is the BA whose transitions must be transformed
	 * @throws NullPointerException
	 *             if the document doc or the BA are null
	 */
	public BATransitionToElementTransformer(BA automaton, Document doc) {
		super(doc);
		Preconditions.checkNotNull(automaton, "The automaton cannot be null");
		this.automaton = automaton;
	}

	/**
	 * Transforms the transition to the corresponding element
	 * 
	 * @param transition
	 *            the transition to be transformed
	 * @throws NullPointerException
	 *             if the transition is null
	 * @throws IllegalArgumentException
	 *             if the transition is not a transition of the BA
	 */
	@Override
	public Element transform(Transition transition) {
		Preconditions.checkNotNull(transition,
				"The transition element to be converted cannot be null");

		Preconditions.checkArgument(
				automaton.getTransitions().contains(transition),
				"The transition must be contained into the set of transitions of the automaton");
		
		Element transitionElement = super.transform(transition);

		// adding the source
		Attr sourceId = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE);
		sourceId.setValue(Integer.toString(this.automaton.getTransitionSource(
				transition).getId()));
		transitionElement.setAttributeNode(sourceId);

		// adding the destination
		Attr destinationId = doc
				.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION);
		destinationId.setValue(Integer.toString(this.automaton
				.getTransitionDestination(transition).getId()));
		transitionElement.setAttributeNode(destinationId);

		return transitionElement;
	}

}
