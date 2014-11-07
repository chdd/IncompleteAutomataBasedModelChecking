package it.polimi.modelchecker.brzozowski.propositions.states;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

/**
 * @author claudiomenghi
 * Is the abstract class which specifies the abstract behavior (methods) of the predicates
 * @param <STATE> is the type of the states that are involved in the constraints
 */
public abstract class AbstractProposition<STATE extends State, TRANSITION extends LabelledTransition<STATE>> extends LogicalItem<STATE, TRANSITION>{
	
	private Set<TRANSITION> transitions;
	
	public AbstractProposition(Set<TRANSITION> transitions){
		this.transitions=transitions;
	}
	
	public AbstractProposition(TRANSITION transition){
		this.transitions=new HashSet<TRANSITION>();
		this.transitions.add(transition);
	}
	public Set<TRANSITION> getTransitions(){
		return this.transitions;
	}
	
	public void addTransition(TRANSITION transition){
		this.transitions.add(transition);
	}
	
	
	
}
