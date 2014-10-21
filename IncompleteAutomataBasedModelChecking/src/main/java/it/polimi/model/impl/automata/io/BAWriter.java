package it.polimi.model.impl.automata.io;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphMLWriter;
import edu.uci.ics.jung.io.graphml.DataMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

/**
 * @author claudiomenghi
 * contains a writer for an automata which extends the {@link DrawableBA} interface
 * 
 * @param <STATE> is the type of the states of the {@link DrawableBA}
 * @param <TRANSITION> is the type of the transitions of the {@link DrawableBA}
 * @param <TRANSITIONFACTORY> is the factory which creates the transitions of the {@link DrawableBA}
 * @param <AUTOMATA> is the automata to be written by the {@link GraphMLWriter}
 */
public class BAWriter<
	STATE extends State, 
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>,
	AUTOMATA extends DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY>>
	extends GraphMLWriter<STATE, TRANSITION> {

	/**
	 * stores the AUTOMATA to be written on by the {@link GraphMLWriter}
	 */
	protected AUTOMATA ba;
	
	/**
	 * creates a new {@link BAWriter}
	 */
	public BAWriter(){
		super();
	}
	
	/**
	 * sets the {@link Transformer}s of the {@link DrawableBA}
	 */
	protected void setTransformers(){
		this.setVertexIDs(this.getStateIdTransformer());
		this.addVertexData("name", "name", "", this.getStateNameTransformer());
		this.addVertexData("initial", "initial", "false", this.getStateInitialTransformer());
		this.addVertexData("initial", "initial", "false", this.getStateAcceptingTransformer());
				
		this.setEdgeIDs(this.getTransitionIdTransformer());
		this.addEdgeData("DNFFormula", "DNFFormula", "", this.getTransitionDNFFormulaTransformer());
	}
	
	/**
	 * saves the {@link DrawableBA}
	 * @param ba is the {@link DrawableBA} to be saved
	 * @param w is the {@link Writer} in charge of saving the {@link DrawableBA}
	 * @throws NullPointerException if the {@link DrawableBA} or the {@link Writer} are null
	 * @throws IOException if there are problem in the IO process
	 */
	public void save(AUTOMATA ba, Writer w) throws IOException{
		if(ba==null){
			throw new NullPointerException("The ba cannot be null");
		}
		if(w==null){
			throw new NullPointerException("The writer cannot be null");
		}
		this.ba=ba;
		this.setTransformers();
		super.save(ba, w);
	}
	
	/**
	 * returns a transformer that given a STATE return its id as {@link String}
	 * @return returns the id of a {@link State} as a {@link String}
	 */
	protected Transformer<STATE, String> getStateIdTransformer(){
		return new Transformer<STATE, String>() {
			@Override
			public String transform(STATE input) {
				return Integer.toString(input.getId());
			}
		};
	}

	/**
	 * returns a transformer that given a STATE returns the {@link String} "true" if it is initial, "false" otherwise
	 * @return a transformer that given a STATE returns the {@link String} "true" if it is initial, "false" otherwise
	 */
	protected Transformer<STATE, String> getStateInitialTransformer(){
		return new BAStateInitialToStringTransformer(ba);
	}
	
	/**
	 * returns a transformer that given a STATE returns the {@link String} "true" if it is accepting, "false" otherwise
	 * @return a transformer that given a STATE returns the {@link String} "true" if it is accepting, "false" otherwise
	 */
	protected Transformer<STATE, String> getStateAcceptingTransformer(){
		return new BAStateAcceptingToStringTransformer(ba);
	}
	
	/**
	 * returns a transformer that given a STATE returns its name 
	 * @return a transformer that given a STATE returns its name 
	 */
	protected Transformer<STATE, String> getStateNameTransformer(){
		return new Transformer<STATE, String>() {
			@Override
			public String transform(State input) {
				return input.getName();
			}
		};
	}
	
	/**
	 * returns a transformer that given a TRANSITION return its id as {@link String}
	 * @return returns the id of a {@link LabelledTransition} as a {@link String}
	 */
	protected Transformer<TRANSITION, String> getTransitionIdTransformer(){
		return new Transformer<TRANSITION, String>() {
			@Override
			public String transform(TRANSITION input) {
				return Integer.toString(input.getId());
			}
		};
	}
	
	/**
	 * returns a transformer that given a TRANSITION return the {@link String} representation of the {@link DNFFormula} that labels the {@link LabelledTransition}
	 * @return a transformer that given a TRANSITION return the {@link String} representation of the {@link DNFFormula} that labels the {@link LabelledTransition}
	 */
	protected Transformer<TRANSITION, String> getTransitionDNFFormulaTransformer(){
		return new Transformer<TRANSITION, String>() {
			@Override
			public String transform(TRANSITION input) {
				return input.getDnfFormula().toString();
			}
		};
	}
	
	/**
	 * contains the {@link Transformer} that given a STATE returns if it is initial or not
	 * @author claudiomenghi
	 */
	protected class BAStateInitialToStringTransformer implements Transformer<STATE, String>  {

		private BA<STATE, TRANSITION, TRANSITIONFACTORY> ba;
		
		/**
		 * creates the  {@link BAStateInitialToStringTransformer}
		 * @param ba is the {@link BA} where the state is placed
		 * @throws NullPointerException if the {@link BA} is null
		 */
		public BAStateInitialToStringTransformer(BA<STATE, TRANSITION, TRANSITIONFACTORY> ba){
			if(ba==null){
				throw new NullPointerException("The ba cannot be null");
			}
			this.ba=ba;
		}
		
		/**
		 * returns a transformer that given a STATE returns the {@link String} "true" if it is initial, "false" otherwise
		 * @return a transformer that given a STATE returns the {@link String} "true" if it is initial, "false" otherwise
		 */
		@Override
		public String transform(STATE input) {
			return Boolean.toString(ba.isInitial(input));
		}
	}
	
	/**
	 * contains the {@link Transformer} that given a STATE returns if it is accepting or not
	 * @author claudiomenghi
	 */
	protected class BAStateAcceptingToStringTransformer implements Transformer<STATE, String>  {

		private BA<STATE, TRANSITION, TRANSITIONFACTORY> ba;
		
		/**
		 * creates the  {@link BAStateAcceptingToStringTransformer}
		 * @param ba is the {@link BA} where the state is placed
		 * @throws NullPointerException if the {@link BA} is null
		 */
		public BAStateAcceptingToStringTransformer(BA<STATE, TRANSITION, TRANSITIONFACTORY> ba){
			if(ba==null){
				throw new NullPointerException("The ba cannot be null");
			}
			this.ba=ba;
		}
		
		/**
		 * returns a transformer that given a STATE returns the {@link String} "true" if it is accepting, "false" otherwise
		 * @return a transformer that given a STATE returns the {@link String} "true" if it is accepting, "false" otherwise
		 */
		@Override
		public String transform(STATE input) {
			
			NodeMetadata m=new NodeMetadata();
			m.setId(input.getName());
			
			DataMetadata initial=new DataMetadata();
			initial.setKey("accepting");
			return Boolean.toString(ba.isAccept(input));
		}
	}
}
