package it.polimi.automata.io.in.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBAWithInvariants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.propositions.StringToClaimPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

public class ElementToIBAWithInvariantsStateTransformer implements Transformer<Element, State>{

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory stateFactory;
	private final IBAWithInvariants iba;
	
	
	public ElementToIBAWithInvariantsStateTransformer(StateFactory stateFactory, IBAWithInvariants iba){
		Preconditions.checkNotNull(stateFactory,"The state factory cannot be null");
		this.stateFactory=stateFactory;
		this.iba=iba;
	}

	
	public State transform(Element eElement) {
		
		int id = Integer.parseInt(eElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID));

		State s = stateFactory.create(
				eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME), id);
		iba.addState(s);
		
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INITIAL)
				.isEmpty()) {
			iba.addInitialState(s);
		}
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING)
				.isEmpty()) {
			iba.addAcceptState(s);
		}
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSPARENT)
				.isEmpty()) {
			iba.addTransparentState(s);
		}
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INVARIANT)
				.isEmpty()) {
			Set<IGraphProposition> invariantPropositions = 
					new StringToClaimPropositions()
					.computePropositions(eElement
							.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_INVARIANT));
			iba.addInvariantPropositions(s, invariantPropositions);
		}
		
		
		return s;
	}
}
