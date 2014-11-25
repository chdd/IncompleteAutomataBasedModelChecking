package it.polimi.io;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IIntBA;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

public class IntBAWriter
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition, 
	AUTOMATON extends IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>
		extends IBAWriter< INTERSECTIONSTATE, INTERSECTIONTRANSITION, AUTOMATON>{

	public IntBAWriter(
			AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> layout) {
		super(layout);
	}

	/**
	 * sets the transformers of an {@link IntBAWriter}
	 * in particular adds to each state the information of the original states in the model and in the specification and to a
	 * constrained transition information about the constrained {@link State}
	 */
	protected void setTransformers(){
		super.setTransformers();
		
		this.addVertexData("mixed", "is true if the state is mixed", "false", new StateMixedToStringTransformer(this.ba));
		this.addEdgeData("cstateId", "constrained state Id", null, 
			new  Transformer<INTERSECTIONTRANSITION, String>(){
				@Override
				public String transform(INTERSECTIONTRANSITION input) {
					/*if(input.getConstrainedState()==null){
						return "";
					}
					else{
						return Integer.toString(input.getConstrainedState().getId());
					//}*/
					return "";
				}
			});
		this.addEdgeData("cstateName", "constrained state name", null, 
				new  Transformer<INTERSECTIONTRANSITION, String>(){
					@Override
					public String transform(INTERSECTIONTRANSITION input) {
						/*if(input.getConstrainedState()==null){
							return "";
						}
						else{
							return input.getConstrainedState().getName();
						}*/
						return "";
					}
				});
	}
	
	/**
	 * contains the {@link Transformer} that given a STATE returns if it is mixed or not
	 * @author claudiomenghi
	 */
	protected class StateMixedToStringTransformer implements Transformer<INTERSECTIONSTATE, String> {

		private AUTOMATON ba;
		
		/**
		 * creates the  {@link StateMixedToStringTransformer}
		 * @param ba is the {@link DrawableIBA} where the state is placed
		 * @throws NullPointerException if the {@link DrawableIBA} is null
		 */
		public StateMixedToStringTransformer(AUTOMATON ba){
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
		public String transform(INTERSECTIONSTATE input) {
			return Boolean.toString(ba.isMixed(input));
		}
	}
}
