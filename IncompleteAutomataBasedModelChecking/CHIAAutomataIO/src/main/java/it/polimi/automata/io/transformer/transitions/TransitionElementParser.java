package it.polimi.automata.io.transformer.transitions;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Map;

import org.w3c.dom.Element;

/**
 * transforms the XML element passed as input into a transition and insert the
 * transition in the automaton A
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the states of the automaton
 * @param <T>
 *            is the type of the transitions of the automaton
 * @param <A>
 *            is the type of the automaton
 */
public interface TransitionElementParser<S extends State, T extends Transition, A extends BA<S, T>> {

	/**
	 * transforms the specified element into a transition
	 * 
	 * @param input
	 *            is the input element to be transformed
	 * @param automaton
	 *            is the automaton where the transformed element must be
	 *            inserted
	 * @param idStateMap
	 *            is the map that correlates each id with the corresponding
	 *            state that has been created
	 */
	public void transform(Element input, A automaton,
			Map<Integer, S> idStateMap);

	/**
	 * returns the transition factory associated with the parser
	 * 
	 * @return the transition factory associated with the parser
	 */
	public TransitionFactory<S, T> getTransitionFactory();

}
