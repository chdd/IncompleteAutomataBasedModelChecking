package it.polimi.automata.io;

import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

/**
 * contains the Transformer that given a state represented through
 * {@link GraphMetadata} returns the state
 * 
 * @see State
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
 * @author claudiomenghi
 */
class MetadataToBAStateTransformer<L extends Label, S extends State, T extends Transition<L>>
		implements Transformer<NodeMetadata, S> {

	/**
	 * contains the {@link Factory} which is used to create the states
	 * {@link StateImpl}s
	 */
	protected StateFactory<S> stateFactory;
	
	/**
	 * contains the Buchi automaton to be updated by the transformer
	 */
	protected BA<L, S, T> a;

	/**
	 * creates a new BAMetadataStateTransformer
	 * 
	 * @param a
	 *            is the Buchi automaton to be updated by the transformer
	 * @param stateFactory
	 *            is the {@link Factory} in charge of creating the states of the Buchi automaton
	 * @throws NullPointerException
	 *             if the Buchi automaton or the factory are null
	 */
	public MetadataToBAStateTransformer(StateFactory<S> stateFactory,
			BA<L, S, T> a) {
		if (a == null) {
			throw new NullPointerException("The Buchi automaton cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The stateFactory cannot be null");
		}
		this.stateFactory = stateFactory;
		this.a = a;
	}

	/**
	 * transforms the {@link NodeMetadata} into the corresponding State
	 * 
	 * @param input
	 *            contains the {@link NodeMetadata} to be converted into the
	 *            corresponding State
	 * @throws NullPointerException
	 *             if the input is null
	 */
	@Override
	public S transform(NodeMetadata input) {
		if (input == null) {
			throw new NullPointerException(
					"The NodeMetadata to be converted into a State cannot be null");
		}

		S s = this.stateFactory.create(input.getProperty(Constants.NAMETAG),
				Integer.parseInt(input.getId()));
		this.a.addState(s);
		if (Boolean.parseBoolean(input.getProperty(Constants.INITIALTAG))) {
			this.a.addInitialState(s);
		}
		if (Boolean.parseBoolean(input.getProperty(Constants.ACCEPTINGTAG))) {
			this.a.addAcceptState(s);
		}

		return s;
	}
}
