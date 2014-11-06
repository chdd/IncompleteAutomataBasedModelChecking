package it.polimi.model.interfaces.automata;

import org.apache.commons.collections15.Factory;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

/**
 * contains a new {@link Factory} for a {@link DrawableIBA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link DrawableIBA}
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the {@link DrawableIBA}
 * @param <TRANSITIONFACTORY> is the {@link Factory} which is used to create the {@link LabelledTransition} of the {@link DrawableIBA}
 */
public interface IBAFactory<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>, 
	AUTOMATON extends DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>
	extends BAFactory<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, AUTOMATON> {

}
