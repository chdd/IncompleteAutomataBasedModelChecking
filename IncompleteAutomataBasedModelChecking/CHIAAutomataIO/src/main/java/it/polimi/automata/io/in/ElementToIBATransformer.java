package it.polimi.automata.io.in;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.states.ElementToIBAStateTransformer;
import it.polimi.automata.io.in.transitions.ElementToIBATransitionTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

public class ElementToIBATransformer implements Transformer<Element, IBA>  {

	
	/**
	 * contains a map that connects the id with the corresponding state
	 */
	private  Map<Integer, State> mapIdState;

	
	public ElementToIBATransformer(){
	
		this.mapIdState=new HashMap<Integer, State>();
		
	}
	@Override
	public IBA transform(Element input) {
		Preconditions.checkNotNull(input,
				"The input elemento to be converted into a BA cannot be null");
		
		this.mapIdState=new HashMap<Integer, State>();
		IBA ba=new IBA(new ModelTransitionFactory());
		
		this.loadStates(input, ba);
		this.loadTransitions(input, ba);
		return ba;
	} 
	private void loadStates(Element doc, IBA ba) {
		
		ElementToIBAStateTransformer stateElementParser=new ElementToIBAStateTransformer(new StateFactory(), ba);
		NodeList xmlstates = doc
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			State s=stateElementParser.transform(
					eElement);
			this.mapIdState.put(s.getId(), s);

		}
	}

	private void loadTransitions(Element doc, IBA ba) {
		ElementToIBATransitionTransformer transitionElementParser=new ElementToIBATransitionTransformer(new ClaimTransitionFactory(), ba, this.mapIdState);
		NodeList xmltransitions = doc
				.getElementsByTagName(AutomataIOConstants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;

			transitionElementParser.transform(eElement);

		}
	}
}
