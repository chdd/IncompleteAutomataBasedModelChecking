package it.polimi.automata.io;

import java.io.IOException;
import java.io.Writer;

import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphMLWriter;

/**
 * 
 * Contains the Writer for an Incomplete Buchi automaton
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
public class IBAWriter<L extends Label, S extends State, T extends Transition<L>>
		extends GraphMLWriter<S, T> {

	/**
	 * contains the Incomplete Buchi Automaton to be written
	 */
	protected IBA<L, S, T> iba;

	/**
	 * creates a new Writer for the specified automaton
	 * 
	 * @param iba
	 *            is the Incomplete Buchi automaton to be converted
	 * @throws NullPointerException
	 *             if the Incomplete Buchi automaton to be converted is null
	 */
	public IBAWriter(IBA<L, S, T> iba) {
		super();
		if (iba == null) {
			throw new NullPointerException(
					"The IBA to be converted cannot be null");
		}
		this.iba = iba;
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
				new BAStateInitialToStringTransformer<L, S, T>(iba));
		this.addVertexData(Constants.ACCEPTINGTAG, Constants.ACCEPTINGTAG,
				Constants.FALSEVALUE,
				new BAStateAcceptingToStringTransformer<L, S, T>(iba));
		this.setEdgeIDs(new Transformer<T, String>() {
			@Override
			public String transform(T input) {
				return Integer.toString(input.getId());
			}
		});
		this.addVertexData(Constants.TRANSPARENTTAG, Constants.TRANSPARENTTAG,
				Constants.FALSEVALUE,
				new IBAStateTransparentToStringTransformer<L, S, T>(this.iba));

		this.addEdgeData(Constants.LABELSTAG, Constants.LABELSTAG,
				Constants.LABELSDEFAULT,
				new BATransitionToStringTransformer<L, T>());
	}

	/**
	 * saves the <b>Incomplete</b> Buchi automaton
	 * 
	 * @param w
	 *            is the Writer ({@link Writer}) in charge of saving the
	 *            <b>Incomplete</b> Buchi automaton
	 * @throws NullPointerException
	 *             if the Writer is null
	 */
	public void save(Writer w) throws IOException {
		if (w == null) {
			throw new NullPointerException("The writer cannot be null");
		}
		super.save(iba.getGraph(), w);
	}
}
