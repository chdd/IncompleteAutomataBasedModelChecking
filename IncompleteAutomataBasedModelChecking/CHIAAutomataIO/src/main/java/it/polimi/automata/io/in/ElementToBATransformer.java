package it.polimi.automata.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.propositions.StringToModelPropositions;
import it.polimi.automata.io.in.states.ElementToBAStateTransformer;
import it.polimi.automata.io.in.transitions.ElementToBATransitionTransformer;
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

public class ElementToBATransformer implements Transformer<Element, BA>  {

	
	/**
	 * contains a map that connects the id with the corresponding state
	 */
	private  Map<Integer, State> mapIdState;
	
	public ElementToBATransformer(){
		this.mapIdState=new HashMap<Integer, State>();
	}

	@Override
	public BA transform(Element input) {
		Preconditions.checkNotNull(input,
				"The input elemento to be converted into a BA cannot be null");
		
		this.mapIdState=new HashMap<Integer, State>();
		BA ba=new BA(new ClaimTransitionFactory());
		
		this.loadPropositions(input, ba);
		this.loadStates(input, ba);
		this.loadTransitions(input, ba);
		return ba;
	} 
	
	private void loadPropositions(Element doc, BA ba){
		StringToModelPropositions propositionParser=new StringToModelPropositions();
		NodeList xmlstates = doc
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PROPOSITION);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			Set<IGraphProposition> proposition= propositionParser.transform(eElement.getAttribute(AutomataIOConstants.XML_ELEMENT_PROPOSITION_VALUE));
			ba.addPropositions(proposition);

		}
	}
	
	private void loadStates(Element doc, BA ba) {
		ElementToBAStateTransformer stateElementParser=new ElementToBAStateTransformer(ba); 
		
		NodeList xmlstates = doc
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			State s= stateElementParser.transform(
					eElement);
			this.mapIdState.put(s.getId(), s);

		}
	}

	private void loadTransitions(Element doc, BA ba) {
		ElementToBATransitionTransformer transitionElementParser=new ElementToBATransitionTransformer(ba, this.mapIdState);
		
		NodeList xmltransitions = doc
				.getElementsByTagName(AutomataIOConstants.XML_TAG_TRANSITION);

		for (int transitionCounter = 0; transitionCounter < xmltransitions.getLength(); transitionCounter++) {
			Node xmltransition = xmltransitions.item(transitionCounter);
			Element eElement = (Element) xmltransition;
			transitionElementParser.transform(eElement);

		}
	}

}
