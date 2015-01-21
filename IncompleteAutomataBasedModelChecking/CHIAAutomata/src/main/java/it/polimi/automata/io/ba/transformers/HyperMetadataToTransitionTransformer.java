package it.polimi.automata.io.ba.transformers;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionImpl;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;

/**
 * contains the Transformer that given a transition represented through
 * {@link GraphMetadata} returns the transition
 * 
 * @see Transition
 * 
 * @author claudiomenghi
 * 
 * @param <L>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @param <F>
 *            is the factory which allows to create the transitions. It must
 *            implement the interface {@link TransitionFactory}
 */
public class HyperMetadataToTransitionTransformer<L extends Label, T extends Transition<L>, F extends TransitionFactory<L, T>>
		implements Transformer<HyperEdgeMetadata, T> {

	/**
	 * contains the {@link TransitionFactory}
	 */
	protected F transitionFactory;

	/**
	 * creates a new {@link HyperEdgeMetadata} {@link Transformer}
	 * 
	 * @param transitionFactory
	 *            is the {@link Factory} which is used to create
	 *            {@link TransitionImpl}
	 * @throws NullPointerException
	 *             if the transitionFactory is null
	 */
	public HyperMetadataToTransitionTransformer(
			F transitionFactory) {
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transitionFactory cannot be null");
		}
		this.transitionFactory = transitionFactory;
	}

	/**
	 * transforms the {@link HyperEdgeMetadata} into the {@link TransitionImpl}
	 * 
	 * @param the
	 *            {@link HyperEdgeMetadata} from which the
	 *            {@link TransitionImpl} must be loaded
	 * @throws NullPointerException
	 *             if the {@link HyperEdgeMetadata} are null
	 */
	@Override
	public T transform(HyperEdgeMetadata input) {
		if (input == null) {
			throw new NullPointerException(
					"The HyperEdgeMetadata cannot be null");
		}
		return this.transitionFactory.create();
	}
}