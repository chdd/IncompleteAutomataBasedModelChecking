package it.polimi.automata.io.transformer.transitions;

import it.polimi.automata.Constants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.transformer.propositions.ModelPropositionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

public class IBATransitionParser<S extends State, T extends Transition, A extends IBA<S, T>>
		implements ModelTransitionParser<S, T, A> {

	
	/**
	 * is the factory which is used to create the transitions of the Buchi
	 * automaotn
	 */
	private final TransitionFactory<S, T> transitionFactory;
	
	
	public IBATransitionParser(TransitionFactory<S, T> transitionFactory){
		Preconditions.checkNotNull(transitionFactory,"The transition factory cannot be null");
		this.transitionFactory=transitionFactory;
		
		
	}
	
	@Override
	public void transform(Element eElement, A automaton,
			Map<Integer, S> idStateMap) {
		
		int id = Integer.parseInt(eElement
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_ID));

		Set<IGraphProposition> propositions = new ModelPropositionParser<State, Transition>()
				.computePropositions(eElement
						.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS));

		if(!automaton.getPropositions().containsAll(propositions)){
			automaton.addPropositions(propositions);
		}
		T t = transitionFactory.create(id, propositions);

		int sourceId = Integer.parseInt(eElement
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE));
		int destinationId = Integer
				.parseInt(eElement
						.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION));
		automaton.addTransition(idStateMap.get(sourceId),
				idStateMap.get(destinationId), t);
		

	}

	@Override
	public TransitionFactory<S, T> getTransitionFactory() {
		return this.transitionFactory;
	}
}
