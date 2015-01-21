package it.polimi.automata.labeling;

import java.util.Set;

import org.apache.commons.collections15.Factory;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * contains the factory that is used to create the Labels of the transitions of a Buchi
 * automaton or of an incomplete Buchi automaton
 * @see {@link Label}
 * @author claudiomenghi
 * @param LABEL
 *            is the type of the label of the transition to be created
 */
public interface LabelFactory<L extends Label> extends Factory<L> {

	/**
	 * parses the label of the transition
	 * @param label the label of the transition, the set of atomic proposition to be true
	 * @return the set of labels parsed from the string
	 * @throws NullPointerException if the set of labels is null
	 */
	public L create(Set<IGraphProposition> labels);
}
