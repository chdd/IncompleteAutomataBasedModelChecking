package it.polimi.controller.actions.automata.edges;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

@SuppressWarnings("serial")
public class ChangeModelEdgeLabel<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition>
	extends ChangeEdgeLabel<CONSTRAINEDELEMENT, STATE, TRANSITION>
{

	
	
	public ChangeModelEdgeLabel(Object source, int id, String command, String edgeLabel, TRANSITION transition){
		super(source, id, command, edgeLabel, transition);
	}
	
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition
	> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE , 
			 INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception{
	
			this.transition.setCondition(DNFFormulaImpl.<CONSTRAINEDELEMENT>loadFromString(edgeLabel));
	}
	
}
