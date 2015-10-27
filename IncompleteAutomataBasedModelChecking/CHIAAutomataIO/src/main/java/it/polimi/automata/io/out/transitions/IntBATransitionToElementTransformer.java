package it.polimi.automata.io.out.transitions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.transition.Transition;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Transforms the transition of the intersection automaton into the
 * corresponding XML element
 * 
 * @author Claudio Menghi
 *
 */

public class IntBATransitionToElementTransformer extends
		BATransitionToElementTransformer implements
		Transformer<Transition, Element> {

	/**
	 * transforms a transition of the intersection automaton into the
	 * corresponding XML element
	 * 
	 * 
	 * @param doc
	 *            is the document where the XML element must be added
	 * @param automaton
	 *            is the BA whose transitions must be transformed
	 * @throws NullPointerException
	 *             if the document doc or the BA are null
	 */
	public IntBATransitionToElementTransformer(IntersectionBA automaton,
			Document doc) {
		super(automaton, doc);
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

		Element transitionElement = super.transform(transition);

		if (((IntersectionBA) this.automaton).getConstrainedTransitions()
				.contains(transition)) {
			Attr constrained = doc
					.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_CONSTRAINED);
			constrained.setValue(AutomataIOConstants.TRUEVALUE);
			transitionElement.setAttributeNode(constrained);
		}
		return transitionElement;
	}

}
