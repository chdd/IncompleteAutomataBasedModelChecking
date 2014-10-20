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
 * @param <STATE> is the type of the {@link State} of the {@link DrawableBA}
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the {@link DrawableBA}
 */
public interface DrawableBA<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>> 
	extends BA<STATE, TRANSITION, TRANSITIONFACTORY>, DirectedGraph<STATE, TRANSITION> {

}
