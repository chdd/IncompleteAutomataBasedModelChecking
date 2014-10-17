package it.polimi.model.interfaces.drawable;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.State;
import it.polimi.model.interfaces.BA;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * is the interface a {@link DrawableBA} must implement
 * @author claudiomenghi
 *
 * @param <S> is the type of the {@link State} of the {@link DrawableBA}
 * @param <T> is the type of the {@link LabelledTransition} of the {@link DrawableBA}
 */
public interface DrawableBA<S extends State, T extends LabelledTransition> extends BA<S, T>, DirectedGraph<S, T> {

}
