package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.view.ViewInterface;

public class CheckAction<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition>
implements ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	@Override

	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>  void 
	perform(ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, 
			INTERSECTIONTRANSITION> model, 
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) {
		model.check();
		
		view.updateIntersection(model.getIntersection(),null);
		view.setBrzozoski(new Brzozowski<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(model.getIntersection()).getConstraintmatrix());
		view.updateVerificationResults(model.getVerificationResults(), model.getIntersection());
		System.out.println(model.getVerificationResults().getConstraint().getLogicalItem().simplify().toString());
	}

}
