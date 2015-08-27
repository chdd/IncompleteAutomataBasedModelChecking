package it.polimi.automata.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.propositions.StringToModelPropositions;
import it.polimi.automata.io.in.states.BAElementToStateTransformer;
import it.polimi.automata.io.in.transitions.BAElementToTransitionTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

public class ElementToBATransformer implements Transformer<Element, BA> {

	/**
	 * contains a map that connects the id with the corresponding state
	 */
	protected Map<Integer, State> mapIdState;

	public ElementToBATransformer() {
		this.mapIdState = new HashMap<Integer, State>();
	}

	@Override
	public BA transform(Element input) {
		Preconditions.checkNotNull(input,
				"The input elemento to be converted into a BA cannot be null");

		this.mapIdState = new HashMap<Integer, State>();
		BA ba = new BA(new ClaimTransitionFactory());

		this.loadPropositions(input, ba);
		this.loadStates(input, ba);
		this.loadTransitions(input, ba);
		return ba;
	}

	private void loadPropositions(Element doc, BA ba) {
		StringToModelPropositions propositionParser = new StringToModelPropositions();
		NodeList xmlstates = doc
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PROPOSITION);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			Set<IGraphProposition> proposition = propositionParser
					.transform(eElement
							.getAttribute(AutomataIOConstants.XML_ELEMENT_PROPOSITION_NAME));
			ba.addPropositions(proposition);

		}
	}

	/**
	 * loads the states of the BA from the element
	 * 
	 * @param element
	 *            is the element from which the states must be loaded
	 * @param ba
	 *            is the BA where the states must be added
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	private void loadStates(Element element, BA ba) {
		Preconditions.checkNotNull(element,
				"The element to be considered cannot be null");
		Preconditions
				.checkNotNull(ba, "The ba to be considered cannot be null");
		BAElementToStateTransformer stateElementParser = new BAElementToStateTransformer(
				ba);

		NodeList xmlstates = element
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			State s = stateElementParser.transform(eElement);
			this.mapIdState.put(s.getId(), s);

		}
	}

	/**
	 * loads the transitions of the BA from the element
	 * 
	 * @param element
	 *            is the element from which the transitions must be loaded
	 * @param ba
	 *            is the BA where the transitions must be added
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	private void loadTransitions(Element element, BA ba) {
		Preconditions.checkNotNull(element,
				"The element to be considered cannot be null");
		Preconditions
				.checkNotNull(ba, "The ba to be considered cannot be null");
		BAElementToTransitionTransformer transitionElementParser = new BAElementToTransitionTransformer(
				ba, this.mapIdState);

		NodeList xmltransitions = element
				.getElementsByTagName(AutomataIOConstants.XML_TAG_TRANSITION);

		for (int transitionCounter = 0; transitionCounter < xmltransitions
				.getLength(); transitionCounter++) {
			Node xmltransition = xmltransitions.item(transitionCounter);
			Element eElement = (Element) xmltransition;
			transitionElementParser.transform(eElement);

		}
	}

}
