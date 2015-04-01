package it.polimi.automata.io.out.transitions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.transition.Transition;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class IntBATransitionToElementTransformer extends BATransitionToElementTransformer
		implements Transformer<Transition, Element> {

	
	public IntBATransitionToElementTransformer(IntersectionBA automaton, Document doc) {
		super(automaton, doc);
	}

	@Override
	public Element transform(Transition transition) {
		
		Element transitionElement = super.transform(transition);

		if(((IntersectionBA) this.automaton).getConstrainedTransitions().contains(transition)){
			Attr constrained = doc
					.createAttribute(AutomataIOConstants.XML_ATTRIBUTE_CONSTRAINED);
			constrained.setValue(AutomataIOConstants.TRUEVALUE);
			transitionElement.setAttributeNode(constrained);
		}
		return transitionElement;
	}

}
