package it.polimi.automata.io.transformer.transitions;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.transformer.propositions.ClaimPropositionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

public class BATransitionParser implements Transformer<Element, Transition>{

	
	/**
	 * is the factory which is used to create the transitions of the Buchi
	 * automaotn
	 */
	private final ClaimTransitionFactory transitionFactory;
	private final BA automaton;
	private final Map<Integer, State> idStateMap;
	
	public BATransitionParser(BA automaton, Map<Integer, State> idStateMap){
				
		this.transitionFactory=new ClaimTransitionFactory();
		this.automaton=automaton;
		this.idStateMap=idStateMap;
		
		
	}
	
	@Override
	public Transition transform(Element eElement) {
		int id = Integer.parseInt(eElement
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_ID));

		Set<IGraphProposition> propositions = 
				new ClaimPropositionParser()
				.computePropositions(eElement
						.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS));

		Transition t = transitionFactory.create(id, propositions);

		int sourceId = Integer.parseInt(eElement
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE));
		int destinationId = Integer
				.parseInt(eElement
						.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION));
		automaton.addTransition(idStateMap.get(sourceId),
				idStateMap.get(destinationId), t);
		return t;
	}

	
}
