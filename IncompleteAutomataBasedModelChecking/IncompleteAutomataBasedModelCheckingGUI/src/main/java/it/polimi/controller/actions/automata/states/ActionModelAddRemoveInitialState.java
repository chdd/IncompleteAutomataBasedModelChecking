package it.polimi.controller.actions.automata.states;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ActionModelAddRemoveInitialState<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,  
	TRANSITION extends Transition> 
	extends ActionEvent
	implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	private STATE s;
	
	public ActionModelAddRemoveInitialState(Object source, int id, String command, STATE s){
		super(source, id, command);
	
		this.s=s;
	}
	
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition
	> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception{
	
			if(model.getModel().isInitial(s)){
				model.getModel().getInitialStates().remove(s);
			}
			else{
				model.getModel().addInitialState(s);
			}
	}
}
