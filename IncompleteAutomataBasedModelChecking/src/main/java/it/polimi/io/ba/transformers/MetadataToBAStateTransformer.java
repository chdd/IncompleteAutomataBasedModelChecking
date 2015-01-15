package it.polimi.io.ba.transformers;

import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.factories.StateFactory;
import it.polimi.automata.impl.StateImpl;
import it.polimi.automata.labeling.Label;

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
 * @param <LABEL>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <STATE>
 *            is the type of the State of the Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @author claudiomenghi
 */
public class MetadataToBAStateTransformer<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
		implements Transformer<NodeMetadata, STATE> {

	/**
	 * contains the {@link Factory} which is used to create the states
	 * {@link StateImpl}s
	 */
	protected StateFactory<STATE> stateFactory;
	
	/**
	 * contains the Buchi automaton to be updated by the transformer
	 */
	protected BA<LABEL, STATE, TRANSITION> a;

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
	public MetadataToBAStateTransformer(StateFactory<STATE> stateFactory,
			BA<LABEL, STATE, TRANSITION> a) {
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
	public STATE transform(NodeMetadata input) {
		if (input == null) {
			throw new NullPointerException(
					"The NodeMetadata to be converted into a State cannot be null");
		}

		STATE s = this.stateFactory.create(input.getProperty(Constants.nameTag),
				Integer.parseInt(input.getId()));
		if (Boolean.parseBoolean(input.getProperty(Constants.initialTag))) {
			this.a.addInitialState(s);
		}
		if (Boolean.parseBoolean(input.getProperty(Constants.acceptingTag))) {
			this.a.addAcceptState(s);
		}

		return s;
	}
}
