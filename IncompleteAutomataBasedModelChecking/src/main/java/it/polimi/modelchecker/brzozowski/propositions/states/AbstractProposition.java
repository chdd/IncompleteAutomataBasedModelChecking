package it.polimi.modelchecker.brzozowski.propositions.states;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

/**
 * @author claudiomenghi
 * Is the abstract class which specifies the abstract behavior (methods) of the predicates
 * @param <STATE> is the type of the states that are involved in the constraints
 */
public abstract class AbstractProposition<STATE extends State, TRANSITION extends Transition> extends LogicalItem<STATE, TRANSITION>{
	
	/**
	 * contains the transitions which are involved by the {@link AbstractProposition} 
	 */
	private Set<TRANSITION> transitions;
	
	/**
	 * creates a new {@link AbstractProposition}
	 * @param transitions is the {@link Set} of transitions that generate the {@link AbstractProposition}
	 * @throws NullPointerException if the transitions involved in the {@link AbstractProposition} are null
	 */
	public AbstractProposition(Set<TRANSITION> transitions){
		if(transitions==null){
			throw new NullPointerException("The set of transitions cannot be null");
		}
		this.transitions=transitions;
	}
	
	/**
	 * crates a new {@link AbstractProposition}
	 * @param transition is the transition that generate the {@link AbstractProposition}
	 * @throws NullPointerException if the transition involved in the {@link AbstractProposition} is null
	 */
	public AbstractProposition(TRANSITION transition){
		if(transition==null){
			throw new NullPointerException("The transition cannot be null");
		}
		this.transitions=new HashSet<TRANSITION>();
		this.transitions.add(transition);
	}
	/**
	 * returns the {@link Set} of transitions involved in the {@link AbstractProposition}
	 * @return the {@link Set} of transitions involved in the {@link AbstractProposition}
	 */
	public Set<TRANSITION> getTransitions(){
		return this.transitions;
	}
	
	/**
	 * ass the transition to the {@link Set} of transitions involved in the {@link AbstractProposition}
	 * @param transition is the transition to be added in the {@link Set} of transitions of the {@link AbstractProposition}
	 */
	public void addTransition(TRANSITION transition){
		if(transition==null){
			throw new NullPointerException("The transition to be added cannot be null");
		}
		this.transitions.add(transition);
	}	
}
