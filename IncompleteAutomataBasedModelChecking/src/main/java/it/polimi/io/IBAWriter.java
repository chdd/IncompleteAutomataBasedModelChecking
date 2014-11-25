package it.polimi.io;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.io.GraphMLWriter;

/**
 * @author claudiomenghi
 * contains a writer for an automata which extends the {@link DrawableIBA} interface
 *
 * @param <STATE> is the type of the states of the {@link DrawableIBA}
 * @param <TRANSITION> is the type of the transitions of the {@link DrawableIBA}
 * @param <TRANSITIONFACTORY> is the factory which creates the transitions of the {@link DrawableIBA}
 * @param <AUTOMATA> is the automata to be written by the {@link GraphMLWriter}
 */
public class IBAWriter<
	STATE extends State, 
	TRANSITION extends Transition,
	AUTOMATA extends IBA<STATE, TRANSITION>>
	extends BAWriter<STATE, TRANSITION, AUTOMATA> {

	public IBAWriter(AbstractLayout<STATE, TRANSITION> layout) {
		super(layout);
	}

	/**
	 * sets the {@link Transformer}s of the {@link DrawableIBA}
	 */
	protected void setTransformers(){
		super.setTransformers();
		this.addVertexData("transparent", "transparent", "false", 
				new StateTransparentToStringTransformer(this.ba));
		
	}
	
	/**
	 * contains the {@link Transformer} that given a STATE returns if it is transparent or not
	 * @author claudiomenghi
	 */
	protected class StateTransparentToStringTransformer implements Transformer<STATE, String> {

		private AUTOMATA ba;
		
		/**
		 * creates the  {@link StateTransparentToStringTransformer}
		 * @param ba is the {@link DrawableIBA} where the state is placed
		 * @throws NullPointerException if the {@link DrawableIBA} is null
		 */
		public StateTransparentToStringTransformer(AUTOMATA ba){
			if(ba==null){
				throw new NullPointerException("The ba cannot be null");
			}
			this.ba=ba;
		}
		
		/**
		 * returns a transformer that given a STATE returns the {@link String} "true" if it is transparent, "false" otherwise
		 * @return a transformer that given a STATE returns the {@link String} "true" if it is transparent, "false" otherwise
		 */
		@Override
		public String transform(STATE input) {
			return Boolean.toString(ba.isTransparent(input));
		}
	}
}
