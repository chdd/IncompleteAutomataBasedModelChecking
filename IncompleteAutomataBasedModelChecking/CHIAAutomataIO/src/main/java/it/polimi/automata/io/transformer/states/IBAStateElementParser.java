package it.polimi.automata.io.transformer.states;

import it.polimi.automata.Constants;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;

import java.util.AbstractMap;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class IBAStateElementParser implements StateElementParser<State, Transition, IBA<State, Transition>> {

	

	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory<State> stateFactory;
	
	
	public IBAStateElementParser(StateFactory<State> stateFactory){
		Preconditions.checkNotNull(stateFactory,"The state factory cannot be null");
		this.stateFactory=stateFactory;
	}
	
	@Override
	public Entry<Integer, State> transform(Element eElement, IBA<State, Transition> iba) {
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
		return new AbstractMap.SimpleEntry<Integer, State>(id,s);
	}

	@Override
	public StateFactory<State> getStateFactory() {
		return this.stateFactory;
	}

}


