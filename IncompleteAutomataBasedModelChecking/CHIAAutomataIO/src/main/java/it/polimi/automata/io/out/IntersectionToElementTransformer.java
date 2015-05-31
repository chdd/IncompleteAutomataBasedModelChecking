package it.polimi.automata.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.automata.io.out.propositions.PropositionToElementTrasformer;
import it.polimi.automata.io.out.states.IntBAStateToElementTransformer;
import it.polimi.automata.io.out.transitions.IntBATransitionToElementTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class IntersectionToElementTransformer extends
		XMLTrasformer<IntersectionBA, Element> {
	public IntersectionToElementTransformer(Document doc) {
		super(doc);
	}

	public IntersectionToElementTransformer()
			throws ParserConfigurationException {
		super();
	}

	@Override
	public Element transform(IntersectionBA input)
			throws ParserConfigurationException {

		Document doc = this.getDocument();
		Element intersectionAutomatonElement = doc.createElement(AutomataIOConstants.XML_ELEMENT_INTERSECTION);
		
		Element propositions = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_PROPOSITIONS);
		intersectionAutomatonElement.appendChild(propositions);
		this.computingStatePropositions(doc, propositions, input);
		Element states = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_STATES);
		intersectionAutomatonElement.appendChild(states);
		this.computingStateElements(doc, states, input);
		Element transitions = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITIONS);
		intersectionAutomatonElement.appendChild(transitions);
		this.computingTransitionElements(doc, transitions, input);
		return intersectionAutomatonElement;

	}

	
	private void computingStatePropositions(Document doc, Element rootElement,
			IntersectionBA input) {

		PropositionToElementTrasformer propositionTrasformer = new PropositionToElementTrasformer(
				doc);
		for (IGraphProposition p : input.getPropositions()) {
			if (!(p instanceof SigmaProposition)) {
				rootElement.appendChild(propositionTrasformer.transform(p));
			}
		}
	}

	private void computingStateElements(Document doc,
			Element intersectionAutomatonElement,
			IntersectionBA intersectionAutomaton) {

		Element statesElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_STATES);
		intersectionAutomatonElement.appendChild(statesElement);
		IntBAStateToElementTransformer stateTransformer = new IntBAStateToElementTransformer(
				intersectionAutomaton, doc);
		for (State s : intersectionAutomaton.getStates()) {
			Element xmlStateElement = stateTransformer.transform(s);
			statesElement.appendChild(xmlStateElement);

		}
	}

	private void computingTransitionElements(Document doc,
			Element intersectionAutomatonElement,
			IntersectionBA intersectionAutomaton) {
		Element transitionsElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITIONS);
		intersectionAutomatonElement.appendChild(transitionsElement);

		IntBATransitionToElementTransformer transitionTransformer = new IntBATransitionToElementTransformer(
				intersectionAutomaton, doc);
		for (Transition transition : intersectionAutomaton.getTransitions()) {
			Element transitionElement = transitionTransformer
					.transform(transition);
			transitionsElement.appendChild(transitionElement);
		}
	}
}
