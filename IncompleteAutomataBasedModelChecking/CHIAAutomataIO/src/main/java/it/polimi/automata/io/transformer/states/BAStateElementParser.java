package it.polimi.automata.io.transformer.states;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;

import java.util.AbstractMap;
import java.util.Map.Entry;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;
/**
 * transforms an object of type Node into an object of type State
 * 
 * @author claudiomenghi
 *
 */
public class BAStateElementParser implements StateElementParser<State, Transition, BA<State, Transition>>{

	
	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	protected final StateFactory<State> stateFactory;
	
	
	public BAStateElementParser(StateFactory<State> stateFactory){
		Preconditions.checkNotNull(stateFactory,"The state factory cannot be null");
		this.stateFactory=stateFactory;
		
		
	}
	
	@Override
	public Entry<Integer, State> transform(Element eElement, BA<State, Transition> ba) {
		int id= Integer.parseInt(eElement.getAttribute(Constants.XML_ATTRIBUTE_ID));
		
		State s=stateFactory.create(eElement.getAttribute(Constants.XML_ATTRIBUTE_NAME), id);
		ba.addState(s);
		
		if(!eElement.getAttribute(Constants.XML_ATTRIBUTE_INITIAL).isEmpty()){
			ba.addInitialState(s);
		}
		if(!eElement.getAttribute(Constants.XML_ATTRIBUTE_ACCEPTING).isEmpty()){
			ba.addAcceptState(s);
		}
		return new AbstractMap.SimpleEntry<Integer, State>(id,s);
	}

	

	
}
