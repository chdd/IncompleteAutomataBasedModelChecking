package it.polimi.controller.actions.automata;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ChangeModelEdgeLabel<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>
	>
	extends ActionEvent
	implements
	ActionInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>
	{

	private String edgeLabel;
	private TRANSITION transition;
	
	public ChangeModelEdgeLabel(Object source, int id, String command, String edgeLabel, TRANSITION transition){
		super(source, id, command);
		this.edgeLabel=edgeLabel;
		this.transition=transition;
	}
	
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>
	> void perform(
			ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE , 
			INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) throws Exception{
	
			this.transition.setDNFFormula(DNFFormula.loadFromString(edgeLabel));
	}
	
}
