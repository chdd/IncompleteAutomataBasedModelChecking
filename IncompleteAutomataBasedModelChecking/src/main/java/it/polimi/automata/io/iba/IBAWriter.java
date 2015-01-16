package it.polimi.automata.io.iba;

import java.io.IOException;
import java.io.Writer;

import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.ba.transformers.BAStateAcceptingToStringTransformer;
import it.polimi.automata.io.ba.transformers.BAStateInitialToStringTransformer;
import it.polimi.automata.io.ba.transformers.BATransitionToStringTransformer;
import it.polimi.automata.io.iba.transformers.IBAStateTransparentToStringTransformer;
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
 */
public class IBAWriter<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
extends GraphMLWriter<STATE, TRANSITION> {

	/**
	 * contains the Incomplete Buchi Automaton to be written
	 */
	protected IBA<LABEL, STATE, TRANSITION> iba;


	/**
	 * creates a new Writer for the specified automaton
	 */
	public IBAWriter(IBA<LABEL, STATE, TRANSITION> ba) {
		super();
		this.iba = ba;
		this.setTransformers();
	}
	
	/**
	 * sets the Transformers of the automaton
	 */
	protected void setTransformers() {
		this.setVertexIDs(new Transformer<STATE, String>() {
			@Override
			public String transform(STATE input) {
				return Integer.toString(input.getId());
			}
		});
		this.addVertexData(Constants.nameTag, Constants.nameTag, Constants.defaultName,
				new Transformer<STATE, String>() {
					@Override
					public String transform(STATE input) {
						return input.getName();
					}
				});
		this.addVertexData(
				Constants.initialTag,
				Constants.initialTag,
				Constants.falseValue,
				new BAStateInitialToStringTransformer<LABEL, STATE, TRANSITION>(
						iba));
		this.addVertexData(
				Constants.acceptingTag,
				Constants.acceptingTag,
				Constants.falseValue,
				new BAStateAcceptingToStringTransformer<LABEL, STATE, TRANSITION>(
						iba));
		this.setEdgeIDs(new Transformer<TRANSITION, String>() {
			@Override
			public String transform(TRANSITION input) {
				return Integer.toString(input.getId());
			}
		});
		this.addVertexData(Constants.transparentTag, Constants.transparentTag, Constants.falseValue, 
				new IBAStateTransparentToStringTransformer<LABEL, STATE, TRANSITION>(this.iba));
		
		this.addEdgeData(Constants.labelsTag, Constants.labelsTag, Constants.labelsDefault, new BATransitionToStringTransformer<LABEL, TRANSITION>());
	}
	
	/**
	 * saves the <b>Incomplete</b> Buchi automaton
	 * 
	 * @param w
	 *            is the Writer ({@link Writer}) in charge of saving the <b>Incomplete</b> Buchi automaton
	 * @throws NullPointerException
	 *             if the Writer is null
	 */
	public void save(Writer w)
			throws IOException {
		if (w == null) {
			throw new NullPointerException("The writer cannot be null");
		}
		super.save(iba.getGraph(), w);
	}
}
