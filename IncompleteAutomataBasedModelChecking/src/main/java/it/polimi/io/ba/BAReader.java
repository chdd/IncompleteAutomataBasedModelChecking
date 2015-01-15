package it.polimi.io.ba;

import it.polimi.automata.BA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.factories.BAFactory;
import it.polimi.automata.factories.LabelFactory;
import it.polimi.automata.factories.StateFactory;
import it.polimi.automata.factories.TransitionFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.io.ba.transformers.HyperMetadataToTransitionTransformer;
import it.polimi.io.ba.transformers.MetadataToBATransformer;
import it.polimi.io.ba.transformers.MetadataToBAStateTransformer;
import it.polimi.io.ba.transformers.MetadataToTransitionTransformer;

import java.io.BufferedReader;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;

/**
 * contains the reader which is used to read a Buchi automaton
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
 * @param <STATE>
 *            is the type of the State of the Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <STATEFACTORY>
 *            is the factory which is used to create the states of the Buchi
 *            Automaton. it must implement the interface {@link StateFactory}
 * @param <TRANSITION>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @param <TRANSITIONFACTORY>
 *            is the factory which allows to create the transitions. It must
 *            implement the interface {@link TransitionFactory}
 * @param <AUTOMATON>
 *            is the type of the automaton. It must extend the interface
 *            {@link BA}
 * @param <AUTOMATONFACTORY>
 *            is the factory which is able to create a new empty Buchi
 *            Automaton. It must implement the interface {@link BAFactory}
 */
public class BAReader<LABEL extends Label, LABELFACTORY extends LabelFactory<LABEL>, STATE extends State, STATEFACTORY extends StateFactory<STATE>, TRANSITION extends Transition<LABEL>, TRANSITIONFACTORY extends TransitionFactory<LABEL, TRANSITION>, AUTOMATONFACTORY extends BAFactory<LABEL, STATE, TRANSITION>> {

	/**
	 * is the reader which is used to parse the Buchi Automaton
	 */
	protected GraphMLReader2<DirectedSparseGraph<STATE, TRANSITION>, STATE, TRANSITION> graphReader = null;
	/**
	 * contains the Buchi Automaton loaded from the file
	 */
	protected BA<LABEL, STATE, TRANSITION> ba;

	/**
	 * creates a new Buchi automaton reader which can be used to read a Buchi
	 * automaton through the method
	 * 
	 * @see BAReader#read()
	 * 
	 * @param labelFactory
	 *            is the factory which allows to create the labels of the
	 *            transitions
	 * @param transitionFactory
	 *            is the factory which allows to create the transitions of the
	 *            Buchi automaton
	 * @param stateFactory
	 *            is the factory which allows to create the states of the Buchi
	 *            automaton
	 * @param automatonFactory
	 *            is the factory which is used to create the Buchi automaton
	 * @param fileReader
	 *            is the reader from which the Buchi automaton must be loaded
	 * @throws NullPointerException
	 *             if the labelFactory, transitionFactory, stateFactory,
	 *             automatonFactory or the fileReader is null
	 */
	public BAReader(LABELFACTORY labelFactory,
			TRANSITIONFACTORY transitionFactory, STATEFACTORY stateFactory,
			AUTOMATONFACTORY automatonFactory, BufferedReader fileReader) {
		if (labelFactory == null) {
			throw new NullPointerException(
					"The labeling factory cannot be null");
		}
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		if (automatonFactory == null) {
			throw new NullPointerException(
					"The automaton factory cannot be null");
		}
		if (fileReader == null) {
			throw new NullPointerException("The fileReader cannot be null");
		}
		
		this.ba = automatonFactory.create();
		this.graphReader = new GraphMLReader2<DirectedSparseGraph<STATE, TRANSITION>, STATE, TRANSITION>(
				fileReader, new MetadataToBATransformer<LABEL, STATE, TRANSITION>(ba),
				new MetadataToBAStateTransformer<LABEL, STATE, TRANSITION>(stateFactory, ba),
				new MetadataToTransitionTransformer<LABEL, LABELFACTORY, TRANSITION, TRANSITIONFACTORY>(transitionFactory, labelFactory),
				new HyperMetadataToTransitionTransformer<LABEL, TRANSITION, TRANSITIONFACTORY>(transitionFactory));
	}

	/**
	 * read the Buchi Automaton from the reader
	 * 
	 * @return a new Buchi automaton which is parsed from the reader
	 * @throws GraphIOException
	 *             is generated if a problem occurs in the loading of the
	 *             Buchi Automaton
	 */
	public BA<LABEL, STATE, TRANSITION> read() throws GraphIOException {
		this.graphReader.readGraph();
		return this.ba;
	}	
}
