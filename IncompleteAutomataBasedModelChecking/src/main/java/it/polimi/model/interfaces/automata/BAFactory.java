package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import org.apache.commons.collections15.Factory;

/**
 * contains a {@link Factory} method which is used to generate a new empty
 * {@link BA}
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the {@link State} of the {@link BA}
 * @param <TRANSITION>
 *            is the type of the {@link Transition} of the {@link BA}
 */
public interface BAFactory<STATE extends State, TRANSITION extends Transition, AUTOMATON extends BA<STATE, TRANSITION>>
		extends Factory<AUTOMATON> {

}
