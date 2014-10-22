package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import org.apache.commons.collections15.Factory;

public interface BAFactoryInterface<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>, 
	AUTOMATON extends DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY>>
	extends Factory<AUTOMATON>{

}
