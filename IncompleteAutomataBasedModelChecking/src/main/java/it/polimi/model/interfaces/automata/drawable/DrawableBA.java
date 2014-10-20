package it.polimi.model.interfaces.automata.drawable;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * is the interface a {@link DrawableBA} must implement
 * @author claudiomenghi
 *
 * @param <S> is the type of the {@link State} of the {@link DrawableBA}
 * @param <T> is the type of the {@link LabelledTransition} of the {@link DrawableBA}
 */
public interface DrawableBA<S extends State, T extends LabelledTransition, TFactory extends LabelledTransitionFactoryInterface<T>> extends BA<S, T, TFactory>, DirectedGraph<S, T> {

}
