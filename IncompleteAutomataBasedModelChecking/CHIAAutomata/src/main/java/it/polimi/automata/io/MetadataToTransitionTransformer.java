package it.polimi.automata.io;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

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
 * @param <L>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <F>
 *            is the factory which allows to create the labels of the
 *            transitions. It must implement the interface {@link LabelFactory}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @param <G>
 *            is the factory which allows to create the transitions. It must
 *            implement the interface {@link TransitionFactory}
 */
class MetadataToTransitionTransformer<L extends Label, F extends LabelFactory<L>, T extends Transition<L>, G extends TransitionFactory<L, T>>
		implements Transformer<EdgeMetadata, T> {
	/**
	 * contains the {@link TransitionFactory}
	 */
	protected G transitionFactory;

	/**
	 * contains the factory which is used to parse the label
	 */
	protected F labelFactory;

	/**
	 * creates a new {@link EdgeMetadata} {@link Transformer}
	 * 
	 * @param transitionFactory
	 *            is the {@link Factory} which is used to create
	 *            {@link TransitionImpl}
	 * @param labelFactory
	 *            is the {@link Factory} which is used to parse the label
	 * @throws NullPointerException
	 *             if the transitionFactory or the labelFactory is null
	 */
	public MetadataToTransitionTransformer(G transitionFactory,
			F labelFactory) {
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		if (labelFactory == null) {
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
	public T transform(EdgeMetadata input) {
		if (input == null) {
			throw new NullPointerException(
					"The EdgeMetadata to be converted cannot be null");
		}
		String[] labels=input.getProperty(Constants.LABELSTAG).split(Pattern.quote(Constants.OR));
		
		Set<L> propositions=new HashSet<L>();
		for(String label: new HashSet<String>(Arrays.asList(labels))){
			L l=new StringToLabelTransformer<L>(labelFactory).transform(label.substring(Constants.LPAR.length(), label.length()-Constants.RPAR.length()));
			propositions.add(l);
		}
		return this.transitionFactory.create(Integer.parseInt(input.getId()),
				propositions);
	}
}
