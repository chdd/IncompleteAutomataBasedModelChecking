package it.polimi.io;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

public class IntBAWriter
	<STATE extends State, 
	TRANSITION extends LabelledTransition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>,
	AUTOMATON extends DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>
		extends IBAWriter<INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, AUTOMATON>{

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
					if(input.getConstrainedState()==null){
						return "";
					}
					else{
						return Integer.toString(input.getConstrainedState().getId());
					}
				}
			});
		this.addEdgeData("cstateName", "constrained state name", null, 
				new  Transformer<INTERSECTIONTRANSITION, String>(){
					@Override
					public String transform(INTERSECTIONTRANSITION input) {
						if(input.getConstrainedState()==null){
							return "";
						}
						else{
							return input.getConstrainedState().getName();
						}
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
