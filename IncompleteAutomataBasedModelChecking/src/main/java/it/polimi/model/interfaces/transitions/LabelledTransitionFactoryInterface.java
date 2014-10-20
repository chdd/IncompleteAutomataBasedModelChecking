package it.polimi.model.interfaces.transitions;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.transitions.LabelledTransition;

import org.apache.commons.collections15.Factory;

public interface LabelledTransitionFactoryInterface<T extends LabelledTransition> extends Factory<T> {

	public T create();
	public T create(DNFFormula dnfFormula);
}
