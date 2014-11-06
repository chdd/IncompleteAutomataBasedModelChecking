package it.polimi.controller.actions.automata.edges;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.event.ActionEvent;

public abstract class ChangeEdgeLabel
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>
	>
extends ActionEvent implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String edgeLabel;
	protected TRANSITION transition;
	
	public ChangeEdgeLabel(Object source, int id, String command, String edgeLabel, TRANSITION transition){
		super(source, id, command);
		this.edgeLabel=edgeLabel;
		this.transition=transition;
	}
}
