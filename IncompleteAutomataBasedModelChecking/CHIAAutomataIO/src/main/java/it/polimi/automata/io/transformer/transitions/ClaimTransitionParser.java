package it.polimi.automata.io.transformer.transitions;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

public interface ClaimTransitionParser<S extends State, T extends Transition, A extends BA<S, T>>
		extends TransitionElementParser<S, T, A> {

}
