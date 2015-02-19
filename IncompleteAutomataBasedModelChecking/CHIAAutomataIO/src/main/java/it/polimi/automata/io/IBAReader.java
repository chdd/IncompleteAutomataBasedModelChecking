package it.polimi.automata.io;

import it.polimi.automata.BA;
import it.polimi.automata.BAFactory;
import it.polimi.automata.IBA;
import it.polimi.automata.IBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.io.Reader;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;

/**
 * contains the reader which is used to read an <b>Incomplete</b> Buchi automaton
 * 
 * @author claudiomenghi
 * 
 * @param <L>
 *            is the type of the Label which is applied to the transitions of
 *            the Incomplete Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <F>
 *            is the factory which allows to create the labels of the
 *            transitions. It must implement the interface {@link LabelFactory}
 * @param <S>
 *            is the type of the State of the Incomplete Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <G>
 *            is the factory which is used to create the states of the Incomplete Buchi
 *            Automaton. it must implement the interface {@link StateFactory}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @param <H>
 *            is the factory which allows to create the transitions. It must
 *            implement the interface {@link TransitionFactory}
 * @param <AUTOMATON>
 *            is the type of the automaton. It must extend the interface
 *            {@link BA}
 * @param <I>
 *            is the factory which is able to create a new empty Incomplete Buchi
 *            Automaton. It must implement the interface {@link BAFactory}
 */
public class IBAReader<L extends Label, 
	F extends LabelFactory<L>, 
	S extends State, 
	G extends StateFactory<S>, 
	T extends Transition<L>, 
	H extends TransitionFactory<L, T>, 
	I extends IBAFactory<L, S, T>>{

	/**
	 * is the reader which is used to par
	 */
	protected GraphMLReader2<DirectedSparseGraph<S, T>, S, T> graphReader = null;
	/**
	 * contains the Incomplete Buchi Automaton loaded from the file
	 */
	protected IBA<L, S, T> iba;

	
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
	public IBAReader(F labelFactory,
			H transitionFactory, G stateFactory,
			I automatonFactory, Reader fileReader) {
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
		this.iba = automatonFactory.create();

		this.graphReader = new GraphMLReader2<DirectedSparseGraph<S, T>, S, T>(
				fileReader, 
				new MetadataToBATransformer<L, S, T>(iba),
				new MetadataToIBAStateTransformer<L, S, T>(stateFactory, iba),
				new MetadataToTransitionTransformer<L, F, S, T, H>(transitionFactory, labelFactory, iba),
				new HyperMetadataToTransitionTransformer<L, T, H>(transitionFactory));
	}

	/**
	 * read the Buchi Automaton from the reader
	 * 
	 * @return a new Buchi automaton which is parsed from the reader
	 * @throws GraphIOException
	 *             is generated if a problem occurs in the loading of the
	 *             Buchi Automaton
	 */
	public IBA<L, S, T> read() throws GraphIOException {
		this.graphReader.readGraph();
		return this.iba;
	}	
	
	
}
