package it.polimi.automata.io.in.transitions;

import it.polimi.automata.BA;
import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.propositions.StringToClaimPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

public class ElementToBATransitionTransformer implements Transformer<Element, Transition>{

	
	/**
	 * is the factory which is used to create the transitions of the Buchi
	 * automaotn
	 */
	private final ClaimTransitionFactory transitionFactory;
	private final BA automaton;
	private final Map<Integer, State> idStateMap;
	
	public ElementToBATransitionTransformer(BA automaton, Map<Integer, State> idStateMap){
				
		this.transitionFactory=new ClaimTransitionFactory();
		this.automaton=automaton;
		this.idStateMap=idStateMap;
		
		
	}
	
	@Override
	public Transition transform(Element eElement) {
		int id = Integer.parseInt(eElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_ID));

		Set<IGraphProposition> propositions = 
				new StringToClaimPropositions()
				.transform(eElement
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS));

		Transition t = transitionFactory.create(id, propositions);

		int sourceId = Integer.parseInt(eElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE));
		int destinationId = Integer
				.parseInt(eElement
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION));
		automaton.addTransition(idStateMap.get(sourceId),
				idStateMap.get(destinationId), t);
		return t;
	}

	
}
