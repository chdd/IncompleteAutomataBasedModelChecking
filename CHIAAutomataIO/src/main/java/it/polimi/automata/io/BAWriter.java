package it.polimi.automata.io;

import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphMLWriter;

/**
 * 
 * Contains the Writer for a Buchi automaton
 * 
 * @see {@link BA}
 * @author claudiomenghi
 * 
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
public class BAWriter<L extends Label, S extends State, T extends Transition<L>>
		extends GraphMLWriter<S, T> {

	/**
	 * contains the Buchi Automaton to be written
	 */
	protected BA<L, S, T> ba;

	/**
	 * creates a new Writer for the specified automaton
	 * 
	 * @param ba
	 *            the Buchi automaton to be written
	 * @throws NullPointerException
	 *             if the Buchi automaton to be written is null
	 */
	public BAWriter(BA<L, S, T> ba) {
		super();
		if (ba == null) {
			throw new NullPointerException(
					"The ba to be written cannot be null");
		}
		this.ba = ba;
		this.setTransformers();
	}

	/**
	 * sets the Transformers of the automaton
	 */
	protected void setTransformers() {
		this.setVertexIDs(new Transformer<S, String>() {
			@Override
			public String transform(S input) {
				return Integer.toString(input.getId());
			}
		});
		this.addVertexData(Constants.NAMETAG, Constants.NAMETAG,
				Constants.DEFAULTNAME, new Transformer<S, String>() {
					@Override
					public String transform(S input) {
						return input.getName();
					}
				});
		this.addVertexData(Constants.INITIALTAG, Constants.INITIALTAG,
				Constants.FALSEVALUE,
				new BAStateInitialToStringTransformer<L, S, T>(ba));
		this.addVertexData(Constants.ACCEPTINGTAG, Constants.ACCEPTINGTAG,
				Constants.FALSEVALUE,
				new BAStateAcceptingToStringTransformer<L, S, T>(ba));
		this.setEdgeIDs(new Transformer<T, String>() {
			@Override
			public String transform(T input) {
				return Integer.toString(input.getId());
			}
		});
		this.addEdgeData(Constants.LABELSTAG, Constants.LABELSTAG,
				Constants.LABELSDEFAULT,
				new BATransitionToStringTransformer<L, T>());
	}

	/**
	 * saves the Buchi automaton
	 * 
	 * @param w
	 *            is the Writer ({@link Writer}) in charge of saving the Buchi
	 *            automaton
	 * @throws NullPointerException
	 *             if the Writer is null
	 */
	public void save(Writer w) throws IOException {
		if (w == null) {
			throw new NullPointerException("The writer cannot be null");
		}
		super.save(ba.getGraph(), w);
	}
}
