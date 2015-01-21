package it.polimi.automata.io.ba.transformers;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.graphml.GraphMetadata;

/**
 * contains the Transformer that given the Buchi automaton represented through {@link GraphMetadata}
 * returns the corresponding automaton
 * @see BA
 * 
 * @author claudiomenghi
 *
 * @param <L>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <S>
 *            is the type of the State of the Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 */
public class MetadataToBATransformer<L extends Label, S extends State, T extends Transition<L>> implements
		Transformer<GraphMetadata, DirectedSparseGraph<S, T>> {
	/**
	 * contains the Buchi automaton to be returned after the Transforming
	 * procedure
	 */
	private BA<L, S, T> ba;

	/**
	 * creates the new transformer that converts the metadata into the corresponding Buchi automaton
	 * 
	 * @param ba
	 *            is the Buchi automaton to be updated by the transforming activity
	 * @throws NullPointerException
	 *             if the Buchi automaton to be updated is null
	 */
	public MetadataToBATransformer(BA<L, S, T> ba) {
		if (ba == null) {
			throw new NullPointerException("The be cannot be null");
		}
		this.ba = ba;
	}

	/**
	 * transforms the Metadata into the corresponding Buchi automaton. It directly modifies the Buchi automaton 
	 * passed in the constructor. The returning value is the graph upon which the Buchi Automaton is based
	 * 
	 * @param input
	 *            the {@link GraphMetadata} which corresponds to the
	 *            Buchi Automaton
	 * @throws NullPointerException
	 *             if the metadata are null
	 */
	@Override
	public DirectedSparseGraph<S, T> transform(
			GraphMetadata input) {
		if (input == null) {
			throw new NullPointerException("The input data cannot be null");
		}
		return ba.getGraph();
	}
}