package it.polimi.automata.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.BA;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.automata.io.out.propositions.PropositionToElementTrasformer;
import it.polimi.automata.io.out.states.BAStateToElementTransformer;
import it.polimi.automata.io.out.transitions.BATransitionToElementTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class BAToElementTrasformer extends XMLTrasformer<BA, Element> {

	public BAToElementTrasformer(Document doc){
		super(doc);
	}
	public BAToElementTrasformer() throws ParserConfigurationException {
		super();
	}
	@Override
	public Element transform(BA input) throws ParserConfigurationException {

		Document doc=this.getDocument();
		Element baElement =
				doc.createElement(AutomataIOConstants.XML_ELEMENT_BA);
		
		Element propositions=doc.createElement(AutomataIOConstants.XML_ELEMENT_PROPOSITIONS);
		baElement.appendChild(propositions);
		this.computingStatePropositions(doc, propositions, input);
		Element states=doc.createElement(AutomataIOConstants.XML_ELEMENT_STATES);
		baElement.appendChild(states);
		this.computingStateElements(doc, states, input);
		Element transitions=doc.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITIONS);
		baElement.appendChild(transitions);
		this.computingTransitionElements(doc, transitions, input);
		return baElement;
		
	}
	private void computingStatePropositions(Document doc, Element rootElement,
			BA input) {
		
		PropositionToElementTrasformer propositionTrasformer=new PropositionToElementTrasformer(doc);
		for(IGraphProposition p: input.getPropositions()){
			if(!(p instanceof SigmaProposition)){
				rootElement.appendChild(propositionTrasformer.transform(p));
			}
		}
	}
	private void computingStateElements(Document doc, Element rootElement,
			BA input) {

		BAStateToElementTransformer stateTransformer = new BAStateToElementTransformer(
				input, doc);
		for (State s : input.getStates()) {
			Element xmlStateElement = stateTransformer.transform(s);
			rootElement.appendChild(xmlStateElement);
		}
	}

	private void computingTransitionElements(Document doc, Element rootElement,
			BA input) {
		BATransitionToElementTransformer transitionTransformer = new BATransitionToElementTransformer(
				input, doc);
		for (Transition transition : input.getTransitions()) {
			Element transitionElement = transitionTransformer
					.transform(transition);
			rootElement.appendChild(transitionElement);
		}
	}
}
