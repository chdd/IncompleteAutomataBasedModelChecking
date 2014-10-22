package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

public interface IBAFactoryInterface<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>, 
	AUTOMATON extends DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY>>
	extends BAFactoryInterface<STATE, TRANSITION, TRANSITIONFACTORY, AUTOMATON> {

}
