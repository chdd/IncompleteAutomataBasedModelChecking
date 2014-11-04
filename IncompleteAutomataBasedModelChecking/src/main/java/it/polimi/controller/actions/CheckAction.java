package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.view.ViewInterface;

public class CheckAction<
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
implements ActionInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	@Override

	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>>  void perform(ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, 
			INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model, 
			ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) {
		model.check();
		
		view.updateIntersection(model.getIntersection(),null);
		view.setBrzozoski(new Brzozowski<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>(model.getIntersection()).getConstraintmatrix());
		view.updateVerificationResults(model.getVerificationResults(), model.getIntersection());
	}

}
