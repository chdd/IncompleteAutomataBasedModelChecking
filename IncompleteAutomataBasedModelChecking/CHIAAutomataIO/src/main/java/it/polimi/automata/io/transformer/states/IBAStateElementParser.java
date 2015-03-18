package it.polimi.automata.io.transformer.states;

import it.polimi.automata.Constants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class IBAStateElementParser implements Transformer<Element, State> {

	

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory stateFactory;
	private final IBA iba;
	
	
	public IBAStateElementParser(StateFactory stateFactory, IBA iba){
		Preconditions.checkNotNull(stateFactory,"The state factory cannot be null");
		Preconditions.checkNotNull(iba,"The incomplete Buchi automaton cannot be null");
		
		this.stateFactory=stateFactory;
		this.iba=iba;
	}
	
	@Override
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
		return s;
	}
}


