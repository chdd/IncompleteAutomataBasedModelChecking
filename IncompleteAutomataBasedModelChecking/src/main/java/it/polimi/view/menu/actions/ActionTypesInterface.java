package it.polimi.view.menu.actions;

import it.polimi.controller.actions.automata.edges.ChangeEdgeLabel;
import it.polimi.controller.actions.automata.edges.delete.DeleteEdgeAction;
import it.polimi.controller.actions.automata.states.accepting.SetAccepting;
import it.polimi.controller.actions.automata.states.delete.DeleteState;
import it.polimi.controller.actions.automata.states.initial.SetInitial;
import it.polimi.controller.actions.automata.states.transparent.SetTransparent;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public abstract class ActionTypesInterface<
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> {

	public abstract DeleteEdgeAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> deleteEdgeAction(Object source, int id, String command, TRANSITION transition);
	
	public abstract ChangeEdgeLabel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getChangingLabelAction(Object source, int id, String command, String edgeLabel, TRANSITION transition);
	
	// STATES
	public abstract DeleteState<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>  getDeleteStateAction(Object source, int id, String command, STATE state);
	public abstract SetAccepting<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setAccepting(Object source, int id, String command, STATE state);
	public abstract SetInitial<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setInitial(Object source, int id, String command, STATE state);
	
	public SetTransparent<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setTransparent(Object source, int id, String command, STATE state){
		return new SetTransparent<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}
}
