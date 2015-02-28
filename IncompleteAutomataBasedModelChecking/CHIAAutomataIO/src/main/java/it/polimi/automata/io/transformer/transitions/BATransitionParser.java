package it.polimi.automata.io.transformer.transitions;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.transformer.propositions.ClaimPropositionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

public class BATransitionParser
		implements ClaimTransitionParser<State, Transition, BA<State, Transition>> {

	
	/**
	 * is the factory which is used to create the transitions of the Buchi
	 * automaotn
	 */
	private final TransitionFactory<State, Transition> transitionFactory;
	
	
	public BATransitionParser(TransitionFactory<State, Transition> transitionFactory){
		Preconditions.checkNotNull(transitionFactory,"The transition factory cannot be null");
		this.transitionFactory=transitionFactory;
		
		
	}
	
	@Override
	public void transform(Element eElement, BA<State, Transition> automaton,
			Map<Integer, State> idStateMap) {
		int id = Integer.parseInt(eElement
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_ID));

		Set<IGraphProposition> propositions = 
				new ClaimPropositionParser<State, Transition, BA<State, Transition>>()
				.computePropositions(eElement
						.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS), automaton);

		Transition t = transitionFactory.create(id, propositions);

		int sourceId = Integer.parseInt(eElement
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE));
		int destinationId = Integer
				.parseInt(eElement
						.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION));
		automaton.addTransition(idStateMap.get(sourceId),
				idStateMap.get(destinationId), t);
	}

	@Override
	public TransitionFactory<State, Transition> getTransitionFactory() {
		return this.transitionFactory;
	}
}
