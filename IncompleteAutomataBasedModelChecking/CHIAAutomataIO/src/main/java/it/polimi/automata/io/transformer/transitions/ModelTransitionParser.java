package it.polimi.automata.io.transformer.transitions;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

public interface ModelTransitionParser<S extends State, T extends Transition, A extends IBA<S, T>>
		extends TransitionElementParser<S, T, A> {

}
