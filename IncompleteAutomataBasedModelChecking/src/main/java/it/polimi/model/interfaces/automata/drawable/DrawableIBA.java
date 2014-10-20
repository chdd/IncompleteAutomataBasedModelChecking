package it.polimi.model.interfaces.automata.drawable;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

/**
 * is the interface a {@link DrawableIBA} (drawable Incomplete Buchi automaton) must implement
 * @author claudiomenghi
 *
 * @param <S> is the type of the {@link State} the drawable Incomplete Buchi automaton must implement
 * @param <T> is the type of the {@link LabelledTransition} the drawable Incomplete Buchi Automaton must implement
 */
public interface DrawableIBA<S extends State, T extends LabelledTransition, TFactory extends LabelledTransitionFactoryInterface<T>> extends DrawableBA<S, T, TFactory>, IBA<S, T, TFactory> {

}
