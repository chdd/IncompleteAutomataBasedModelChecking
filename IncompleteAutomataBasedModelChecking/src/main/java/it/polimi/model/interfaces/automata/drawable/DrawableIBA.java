package it.polimi.model.interfaces.automata.drawable;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

/**
 * is the interface a {@link DrawableIBA} (drawable Incomplete Buchi automaton) must implement
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} the drawable Incomplete Buchi automaton must implement
 * @param <TRANSITION> is the type of the {@link LabelledTransition} the drawable Incomplete Buchi Automaton must implement
 */
public interface DrawableIBA<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>> 
	extends DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY>, IBA<STATE, TRANSITION, TRANSITIONFACTORY> {

}
