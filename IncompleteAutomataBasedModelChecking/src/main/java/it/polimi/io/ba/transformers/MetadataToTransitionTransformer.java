package it.polimi.io.ba.transformers;

import it.polimi.Constants;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionImpl;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMetadata;

/**
 * contains the Transformer that given a transition represented through
 * {@link GraphMetadata} returns the transition
 * 
 * @see Transition
 * 
 * @author claudiomenghi
 * 
 * @param <LABEL>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <LABELFACTORY>
 *            is the factory which allows to create the labels of the
 *            transitions. It must implement the interface {@link LabelFactory}
 * @param <TRANSITION>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @param <TRANSITIONFACTORY>
 *            is the factory which allows to create the transitions. It must
 *            implement the interface {@link TransitionFactory}
 */
public class MetadataToTransitionTransformer<LABEL extends Label, LABELFACTORY extends LabelFactory<LABEL>, TRANSITION extends Transition<LABEL>, TRANSITIONFACTORY extends TransitionFactory<LABEL, TRANSITION>>
		implements Transformer<EdgeMetadata, TRANSITION> {
	/**
	 * contains the {@link TransitionFactory}
	 */
	protected TRANSITIONFACTORY transitionFactory;

	/**
	 * contains the factory which is used to parse the label
	 */
	protected LABELFACTORY labelFactory;

	/**
	 * creates a new {@link EdgeMetadata} {@link Transformer}
	 * 
	 * @param transitionFactory
	 *            is the {@link Factory} which is used to create
	 *            {@link TransitionImpl}
	 * @param labelFactory
	 *            is the {@link Factory} which is used to parse the label
	 * @throws NullPointerException
	 *             if the transitionFactory is null
	 */
	public MetadataToTransitionTransformer(TRANSITIONFACTORY transitionFactory,
			LABELFACTORY labelFactory) {
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		this.transitionFactory = transitionFactory;
		this.labelFactory = labelFactory;
	}

	/**
	 * transforms the {@link EdgeMetadata} into the corresponding
	 * {@link TransitionImpl}
	 * 
	 * @param input
	 *            contains the {@link EdgeMetadata} to be converted
	 * @throws NullPointerException
	 *             if the {@link EdgeMetadata} to be converted is null
	 */
	@Override
	public TRANSITION transform(EdgeMetadata input) {
		if (input == null) {
			throw new NullPointerException(
					"The EdgeMetadata to be converted cannot be null");
		}
		return this.transitionFactory.create(Integer.parseInt(input.getId()),
				labelFactory.create(input.getProperty(Constants.labelsTag)));
	}
}
