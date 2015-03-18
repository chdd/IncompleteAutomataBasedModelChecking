package it.polimi.automata.io.transformer.states;

import it.polimi.automata.Constants;
import it.polimi.automata.IBAWithInvariants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.transformer.propositions.ClaimPropositionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

public class IBAWithInvariantsStateElementParser implements Transformer<Element, State>{

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory stateFactory;
	private final IBAWithInvariants iba;
	
	
	public IBAWithInvariantsStateElementParser(StateFactory stateFactory, IBAWithInvariants iba){
		Preconditions.checkNotNull(stateFactory,"The state factory cannot be null");
		this.stateFactory=stateFactory;
		this.iba=iba;
	}

	
	public State transform(Element eElement) {
		
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
					new ClaimPropositionParser()
					.computePropositions(eElement
							.getAttribute(Constants.XML_ATTRIBUTE_INVARIANT));
			iba.addInvariantPropositions(s, invariantPropositions);
		}
		
		
		return s;
	}
}
