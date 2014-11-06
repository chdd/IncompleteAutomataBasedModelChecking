package it.polimi.model.interfaces.automata.drawable;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

/**
 * is the interface a {@link DrawableIBA} (drawable Incomplete Buchi automaton) must implement
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} the drawable Incomplete Buchi automaton must implement
 * @param <TRANSITION> is the type of the {@link LabelledTransition} the drawable Incomplete Buchi Automaton must implement
 */
public interface DrawableIBA<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT,  TRANSITION>> 
	extends DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>, IBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> {

}
