package it.polimi.controller.actions.automata.states.initial;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

@SuppressWarnings("serial")
public class SetInitialClaim<STATE extends State, STATEFACTORY extends StateFactory<STATE>, TRANSITION extends LabelledTransition, TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
		extends SetInitial<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	public SetInitialClaim(Object source, int id, String command, STATE state) {
		super(source, id, command, state);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>, INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>> void perform(
			ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> view)
			throws Exception {
		if (model.getSpecification().isInitial(state)) {
			model.getSpecification().getInitialStates().remove(state);
		} else {
			model.getSpecification().addInitialState(state);
		}
	}
}
