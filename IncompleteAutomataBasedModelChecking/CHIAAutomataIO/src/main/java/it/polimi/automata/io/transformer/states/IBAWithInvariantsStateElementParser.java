package it.polimi.automata.io.transformer.states;

import java.util.AbstractMap;
import java.util.Set;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.IBAWithInvariants;
import it.polimi.automata.io.transformer.propositions.ClaimPropositionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;

public class IBAWithInvariantsStateElementParser implements StateElementParser<State, Transition, IBAWithInvariants<State, Transition>>{

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory<State> stateFactory;
	
	
	public IBAWithInvariantsStateElementParser(StateFactory<State> stateFactory){
		Preconditions.checkNotNull(stateFactory,"The state factory cannot be null");
		this.stateFactory=stateFactory;
	}

	
	public Entry<Integer, State> transform(Element eElement, IBAWithInvariants<State, Transition> iba) {
		
		int id = Integer.parseInt(eElement
				.getAttribute(Constants.XML_ATTRIBUTE_ID));

		State s = stateFactory.create(
				eElement.getAttribute(Constants.XML_ATTRIBUTE_NAME), id);
		iba.addState(s);
		
		if (!eElement.getAttribute(Constants.XML_ATTRIBUTE_INITIAL)
				.isEmpty()) {
			iba.addInitialState(s);
		}
		if (!eElement.getAttribute(Constants.XML_ATTRIBUTE_ACCEPTING)
				.isEmpty()) {
			iba.addAcceptState(s);
		}
		if (!eElement.getAttribute(Constants.XML_ATTRIBUTE_TRANSPARENT)
				.isEmpty()) {
			iba.addTransparentState(s);
		}
		if (!eElement.getAttribute(Constants.XML_ATTRIBUTE_INVARIANT)
				.isEmpty()) {
			Set<IGraphProposition> invariantPropositions = 
					new ClaimPropositionParser<State, Transition, BA<State, Transition>>()
					.computePropositions(eElement
							.getAttribute(Constants.XML_ATTRIBUTE_INVARIANT), iba);
			iba.addInvariantPropositions(s, invariantPropositions);
		}
		
		
		return new AbstractMap.SimpleEntry<Integer, State>(id,s);
	}

}
