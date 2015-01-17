package it.polimi.automata.labeling;

import java.util.Set;

import org.apache.commons.collections15.Factory;

/**
 * contains the factory that is used to create the Labels of the transitions of a Buchi
 * automaton or of an incomplete Buchi automaton
 * @see {@link Label}
 * @author claudiomenghi
 * @param LABEL
 *            is the type of the label of the transition to be created
 */
public interface LabelFactory<LABEL extends Label> extends Factory<LABEL> {

	/**
	 * parses the label of the transition from a string
	 * @param label the label of the transition represented as a String
	 * @return the set of labels parsed from the string
	 */
	public Set<LABEL> create(String labels);
}
