package it.polimi.automata.io.in.states;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class IBAElementToStateTransformer implements Transformer<Element, State> {

	

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory stateFactory;
	protected final IBA iba;
	
	
	public IBAElementToStateTransformer(StateFactory stateFactory, IBA iba){
		Preconditions.checkNotNull(stateFactory,"The state factory cannot be null");
		Preconditions.checkNotNull(iba,"The incomplete Buchi automaton cannot be null");
		
		this.stateFactory=stateFactory;
		this.iba=iba;
	}
	
	@Override
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
		if (!eElement.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_BLACKBOX)
				.isEmpty()) {
			iba.addBlackBoxState(s);
		}
		return s;
	}
}


