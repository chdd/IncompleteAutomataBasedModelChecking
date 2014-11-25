package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import org.apache.commons.collections15.Factory;

/**
 * contains a new {@link Factory} for a {@link IBA}
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the {@link State} of the {@link DrawableIBA}
 * @param <TRANSITION>
 *            is the type of the {@link Transition} of the {@link DrawableIBA}
 */
public interface IBAFactory<STATE extends State, TRANSITION extends Transition, AUTOMATON extends IBA<STATE, TRANSITION>>
		extends BAFactory<STATE, TRANSITION, AUTOMATON> {

}
