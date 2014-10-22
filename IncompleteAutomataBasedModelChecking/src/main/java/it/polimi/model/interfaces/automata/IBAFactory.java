package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public interface IBAFactory<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
	AUTOMATON extends DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY>>
	extends BAFactory<STATE, TRANSITION, TRANSITIONFACTORY, AUTOMATON> {

}
