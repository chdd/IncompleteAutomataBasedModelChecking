package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.transitions.TransitionFactory;

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
public interface IBAFactory<CONSTRAINEDELEMENT extends State, STATE extends State, TRANSITION extends Transition, TRANSITIONFACTORY extends TransitionFactory<TRANSITION>, AUTOMATON extends IBA<CONSTRAINEDELEMENT, STATE, TRANSITION>>
		extends BAFactory<STATE, TRANSITION, AUTOMATON> {

}
