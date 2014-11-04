package it.polimi.view.menu.actions;

import it.polimi.controller.actions.automata.edges.ChangeEdgeLabel;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public interface ActionTypesInterface<
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> {

	public ChangeEdgeLabel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getChangingLabelAction(Object source, int id, String command, String edgeLabel, TRANSITION transition);
}
