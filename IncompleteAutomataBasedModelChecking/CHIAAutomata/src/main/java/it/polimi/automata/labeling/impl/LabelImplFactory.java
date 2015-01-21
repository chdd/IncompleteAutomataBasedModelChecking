package it.polimi.automata.labeling.impl;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * is the factory which allows to create labels
 * @author claudiomenghi
 *
 */
public class LabelImplFactory implements LabelFactory<Label>{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label create() {
		return new LabelImpl(new HashSet<IGraphProposition>());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Label create(Set<IGraphProposition> labels) {
		if( labels==null)
			throw new NullPointerException("The set of labels cannot be null");
		return new LabelImpl(labels);
	}

}
