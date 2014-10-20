package it.polimi.model.interfaces.transitions;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.transitions.ConstrainedTransition;

public interface ConstrainedTransitionFactoryInterface<S extends State, T extends ConstrainedTransition<S>> extends
		LabelledTransitionFactoryInterface<T> {

	public T create(DNFFormula dnfFormula, S state);
}
