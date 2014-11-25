package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.states.IntersectionStateFactory;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * contains an intersection automaton
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the states of the original Buchi automata
 * @param <TRANSITION> is the type of the transitions of the original Buchi automata
 * @param <INTERSECTIONSTATE> is the type of the state in the intersection automaton
 * @param <INTERSECTIONTRANSITION> is the type of the transition in the intersection automaton
 */
public class IntBAImpl<
	STATE extends State, 
	TRANSITION extends Transition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> 
	extends IBAImpl<INTERSECTIONSTATE, INTERSECTIONTRANSITION> 
	implements IIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION>{

	
	/**
	 * contains the set of the mixed states
	 */
	private Set<INTERSECTIONSTATE> mixedStates;
	
	/**
	 * creates a new Intersection automaton starting from the model and its specification
	 * @param model: is the model to be considered
	 * @param specification: is the specification to be considered
	 */
	public IntBAImpl(
			ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION> transitionFactory,
			IntersectionStateFactory<STATE, INTERSECTIONSTATE> stateFactory){
		super(transitionFactory, stateFactory);
		this.mixedStates=new HashSet<INTERSECTIONSTATE>();
	}
	
	
	/**
	 * add a mixed state in the intersection automaton
	 * @param s the state to be added in the set of the mixed states
	 * @throws IllegalArgumentException if the state s is null
	 */
	public void addMixedState(INTERSECTIONSTATE s){
		if(s==null){
			throw new IllegalArgumentException("The state to be added cannot be null");
		}
		this.mixedStates.add(s);
		this.addState(s);
	}
	/**
	 * returns true if the state s is mixed, false otherwise
	 * @param s the state to be checked if transparent or not
	 * @return true if the state s is mixed, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isMixed(INTERSECTIONSTATE s){
		if(s==null){
			throw new IllegalArgumentException("The state s cannot be null");
		}
		return this.mixedStates.contains(s);
	}
	/**
	 * returns the set of the mixed states
	 * @return the set of the mixed states
	 */
	public Set<INTERSECTIONSTATE> getMixedStates(){
		return this.mixedStates;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return super.toString()
				+ "mixedStates: "+this.mixedStates+ "\n";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((mixedStates == null) ? 0 : mixedStates.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> other = (IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>) obj;
		if (mixedStates == null) {
			if (other.mixedStates != null)
				return false;
		} else if (!mixedStates.equals(other.mixedStates))
			return false;
		return true;
	}
}