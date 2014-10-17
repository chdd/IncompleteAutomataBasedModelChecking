package it.polimi.model.interfaces;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.State;

/**
 * is the interface a {@link DrawableIBA} (drawable Incomplete Buchi automaton) must implement
 * @author claudiomenghi
 *
 * @param <S> is the type of the {@link State} the drawable Incomplete Buchi automaton must implement
 * @param <T> is the type of the {@link LabelledTransition} the drawable Incomplete Buchi Automaton must implement
 */
public interface DrawableIBA<S extends State, T extends LabelledTransition> extends DrawableBA<S, T>, IBA<S, T> {

}
