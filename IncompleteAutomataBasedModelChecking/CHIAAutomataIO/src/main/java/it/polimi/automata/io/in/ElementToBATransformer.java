package it.polimi.automata.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.transformer.states.BAStateElementParser;
import it.polimi.automata.io.transformer.transitions.BATransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

public class ElementToBATransformer implements Transformer<Element, BA>  {

	private final BAStateElementParser stateElementParser;
	private final BATransitionParser transitionElementParser;
	
	/**
	 * contains a map that connects the id with the corresponding state
	 */
	private  Map<Integer, State> mapIdState;

	
	public ElementToBATransformer(
			BAStateElementParser stateElementParser,
			BATransitionParser transitionElementParser){
		Preconditions.checkNotNull(stateElementParser,
				"The state element parser cannot be null");
		Preconditions.checkNotNull(transitionElementParser,
				"The transition factory cannot be null");

		this.stateElementParser=stateElementParser;
		this.transitionElementParser=transitionElementParser;
		this.mapIdState=new HashMap<Integer, State>();
		
	}
	@Override
	public BA transform(Element input) {
		Preconditions.checkNotNull(input,
				"The input elemento to be converted into a BA cannot be null");
		
		this.mapIdState=new HashMap<Integer, State>();
		BA ba=new BA(new ClaimTransitionFactory());
		
		this.loadStates(input, ba);
		this.loadTransitions(input, ba);
		return ba;
	} 
	private void loadStates(Element doc, BA ba) {
		NodeList xmlstates = doc
				.getElementsByTagName(Constants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			State s= this.stateElementParser.transform(
					eElement);
			this.mapIdState.put(s.getId(), s);

		}
	}

	private void loadTransitions(Element doc, BA ba) {
		NodeList xmltransitions = doc
				.getElementsByTagName(Constants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;
			this.transitionElementParser.transform(eElement);

		}
	}

}
