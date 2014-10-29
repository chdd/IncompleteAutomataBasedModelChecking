package it.polimi.model.impl.automata.io;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import org.apache.commons.collections15.Transformer;

public class IntBAWriter
	<STATE extends State, 
	TRANSITION extends LabelledTransition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>> 
	extends IBAWriter<INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, 
	DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>{

	/**
	 * sets the transformers of an {@link IntBAWriter}
	 * in particular adds to each state the information of the original states in the model and in the specification and to a
	 * constrained transition information about the constrained {@link State}
	 */
	protected void setTransformers(){
		super.setTransformers();
		
		this.addEdgeData("cstate", "constrained state", "", 
			new  Transformer<INTERSECTIONTRANSITION, String>(){
				@Override
				public String transform(INTERSECTIONTRANSITION input) {
					if(input.getConstrainedState()==null){
						return "";
					}
					else{
						return input.getConstrainedState().toString();
					}
				}
			});
	}
}
