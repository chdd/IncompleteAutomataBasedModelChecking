package it.polimi.model.impl.automata;

import it.polimi.model.impl.intersectionbuilder.IntersectionBuilder;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * contains an intersection automaton
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the states of the original Buchi automata
 * @param <TRANSITION> is the type of the transitions of the original Buchi automata
 * @param <INTERSECTIONSTATE> is the type of the state in the intersection automaton
 * @param <INTERSECTIONTRANSITION> is the type of the transition in the intersection automaton
 */
@SuppressWarnings("serial")
public class IntBAImpl<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT,  TRANSITION>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT,  INTERSECTIONTRANSITION>> 
	extends IBAImpl<CONSTRAINEDELEMENT, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> 
	implements DrawableIntBA<CONSTRAINEDELEMENT, STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>{

	
	/**
	 * contains the set of the mixed states
	 */
	private Set<INTERSECTIONSTATE> mixedStates;
	
	/**
	 * is used in the emptiness checking to store if the complete emptiness checking procedure or the "normal" emptiness checking must be performed
	 */
	private boolean completeEmptiness=true;
	
	
	/**
	 * creates a new Intersection automaton starting from the model and its specification
	 * @param model: is the model to be considered
	 * @param specification: is the specification to be considered
	 */
	public IntBAImpl(IBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, BA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification, INTERSECTIONTRANSITIONFACTORY transitionFactory){
		super(transitionFactory);
		if(model==null){
			throw new IllegalArgumentException("The model to be considered cannot be null");
		}
		if(specification==null){
			throw new IllegalArgumentException("The specification to e considered cannot be null");
		}
		this.mixedStates=new HashSet<INTERSECTIONSTATE>();
		new IntersectionBuilder<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE,
		INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>().
		computeIntersection(this, model, specification, transitionFactory);
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
		this.addVertex(s);
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
	
	
	
	/** 
	 * returns true if the complete version (without mixed states) of the intersection automaton is  empty
	 * @return true if the complete version (without mixed states) of the intersection automaton is  empty
	 */
	public boolean isEmpty(ModelCheckerParameters<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> mp){
		
		if(super.isEmpty()){
			return true;
		}
		if(this.completeEmptiness){
			mp.setViolatingPath(this.stack);
			mp.setViolatingPathTransitions(this.stacktransitions);
		}
		
		return false;
	}
	public boolean isCompleteEmpty(){
		this.completeEmptiness=false;
		boolean res=this.isEmpty();
		this.completeEmptiness=true;
		return res;
	}
	
	/**
	 * returns true if an accepting path is found
	 * @param visitedStates contains the set of the visited states during the first DFS
	 * @param currState is the state that is currently analyzed
	 * @param statesOfThePath contains the states of the path that is currently analyzed
	 * @return true if an accepting path is found, false otherwise
	 */
	@Override
	protected boolean firstDFS(Set<INTERSECTIONSTATE> visitedStates, INTERSECTIONSTATE currState, Stack<INTERSECTIONSTATE> statesOfThePath){
		if(this.isMixed(currState) && completeEmptiness){
			return false;
		}
		else{
			return super.firstDFS(visitedStates, currState, statesOfThePath);
		}
	}
	/**
	 * returns true if an accepting path is found during the second DFS
	 * @param visitedStates contains the set of the visited states during the second DFS
	 * @param currState is the state that is currently analyzed
	 * @param statesOfThePath contains the states of the path that is currently analyzed
	 * @return true if an accepting path is found during the second DFS, false otherwise
	 */
	@Override
	protected boolean secondDFS(Set<INTERSECTIONSTATE> visitedStates, INTERSECTIONSTATE currState, Stack<INTERSECTIONSTATE> statesOfThePath, Stack<INTERSECTIONSTATE> stackSecondDFS){
		if(this.isMixed(currState) && completeEmptiness){
			return false;
		}
		else{
			return super.secondDFS(visitedStates, currState, statesOfThePath, stackSecondDFS);
		}
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
		IntBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> other = (IntBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>) obj;
		if (mixedStates == null) {
			if (other.mixedStates != null)
				return false;
		} else if (!mixedStates.equals(other.mixedStates))
			return false;
		return true;
	}
}