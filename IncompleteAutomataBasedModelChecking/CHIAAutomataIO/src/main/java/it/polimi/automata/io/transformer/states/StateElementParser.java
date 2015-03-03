package it.polimi.automata.io.transformer.states;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;

import java.util.Map.Entry;

import org.w3c.dom.Element;

/**
 * transforms the object of type I into an object of type O
 * 
 */
public interface StateElementParser<S extends State, T extends Transition, A extends BA<S,T>> {

	
	public Entry<Integer, S> transform(Element input, A automaton);
	
	public StateFactory<S> getStateFactory();

}
