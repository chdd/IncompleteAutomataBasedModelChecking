package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

public class LoadClaimAction<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition> implements 
ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	private String claim;
	
	public LoadClaimAction(String claim){
		this.claim=claim;
	}
	
	@Override

	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>  void perform(
			ModelInterface<CONSTRAINEDELEMENT,
			STATE, TRANSITION, INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION> model, 
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)  {
		if(this.claim==null){
			throw new NullPointerException("The LTL formula be converted cannot be empty");
		}
		model.loadClaimFromLTL(this.claim);	
		view.updateClaim(model.getSpecification());
	}
}
