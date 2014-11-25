package it.polimi.modelchecker.emptinesschecker;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.modelchecker.ModelCheckingResults;

import java.util.Set;
import java.util.Stack;

public class IntersectionEmptinessChecker
<
CONSTRAINEDELEMENT extends State,
STATE extends State,
TRANSITION extends Transition,
INTERSECTIONSTATE extends IntersectionState<STATE>,
INTERSECTIONTRANSITION extends Transition,
AUTOMATON extends IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>
extends BAEmptinessChecker<INTERSECTIONSTATE, INTERSECTIONTRANSITION, AUTOMATON>{

	
	/**
	 * is used in the emptiness checking to store if the complete emptiness checking procedure or the "normal" emptiness checking must be performed
	 */
	private boolean completeEmptiness=true;
	
	
	public IntersectionEmptinessChecker(AUTOMATON automaton) {
		super(automaton);
	}
	
	
	/** 
	 * returns true if the complete version (without mixed states) of the intersection automaton is  empty
	 * @return true if the complete version (without mixed states) of the intersection automaton is  empty
	 */
	public boolean isEmpty(ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> mp){
		
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
		if(this.automaton.isMixed(currState) && completeEmptiness){
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
		if(this.automaton.isMixed(currState) && completeEmptiness){
			return false;
		}
		else{
			return super.secondDFS(visitedStates, currState, statesOfThePath, stackSecondDFS);
		}
	}

}
