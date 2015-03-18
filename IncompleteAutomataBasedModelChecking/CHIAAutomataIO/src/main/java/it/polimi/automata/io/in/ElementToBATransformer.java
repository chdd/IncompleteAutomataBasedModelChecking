package it.polimi.automata.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.impl.BA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.transformer.states.StateElementParser;
import it.polimi.automata.io.transformer.transitions.ClaimTransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

public class ElementToBATransformer<S extends State, T extends Transition> implements Transformer<Element, BA<S,T>>  {

	private final StateElementParser<S, T, BA<S, T>> stateElementParser;
	private final ClaimTransitionParser<S, T, BA<S, T>> transitionElementParser;
	
	/**
	 * contains a map that connects the id with the corresponding state
	 */
	private  Map<Integer, S> mapIdState;

	
	public ElementToBATransformer(
			StateElementParser<S, T, BA<S, T>> stateElementParser,
			ClaimTransitionParser<S, T, BA<S, T>> transitionElementParser){
		Preconditions.checkNotNull(stateElementParser,
				"The state element parser cannot be null");
		Preconditions.checkNotNull(transitionElementParser,
				"The transition factory cannot be null");

		this.stateElementParser=stateElementParser;
		this.transitionElementParser=transitionElementParser;
		this.mapIdState=new HashMap<Integer, S>();
		
	}
	@Override
	public BA<S, T> transform(Element input) {
		Preconditions.checkNotNull(input,
				"The input elemento to be converted into a BA cannot be null");
		
		this.mapIdState=new HashMap<Integer, S>();
		BA<S,T> ba=new BA<S, T>(transitionElementParser.getTransitionFactory());
		
		this.loadStates(input, ba);
		this.loadTransitions(input, ba);
		return ba;
	} 
	private void loadStates(Element doc, BA<S,T> ba) {
		NodeList xmlstates = doc
				.getElementsByTagName(Constants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			Entry<Integer, S> entry = this.stateElementParser.transform(
					eElement, ba);
			this.mapIdState.put(entry.getKey(), entry.getValue());

		}
	}

	private void loadTransitions(Element doc, BA<S,T> ba) {
		NodeList xmltransitions = doc
				.getElementsByTagName(Constants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;

			this.transitionElementParser.transform(eElement, ba,
					this.mapIdState);

		}
	}

}
