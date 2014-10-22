package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import org.apache.commons.collections15.Factory;

/**
 * contains a {@link Factory} method which is used to generate a new empty {@link DrawableBA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link DrawableBA}
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the {@link DrawableBA}
 * @param <TRANSITIONFACTORY> is the {@link Factory} which is used to create transitions of the {@link DrawableBA}
 */
public interface BAFactory<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
	AUTOMATON extends DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY>>
	extends Factory<AUTOMATON>{

}
