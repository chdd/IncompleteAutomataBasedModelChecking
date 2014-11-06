package it.polimi.model.interfaces.automata.drawable;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * is the interface a {@link DrawableBA} must implement
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link DrawableBA}
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the {@link DrawableBA}
 */
public interface DrawableBA<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> 
	extends BA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>, DirectedGraph<STATE, TRANSITION> {

}
