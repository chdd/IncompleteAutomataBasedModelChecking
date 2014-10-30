package it.polimi.model.impl.automata;

import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
	STATE extends State, 
	TRANSITION extends LabelledTransition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>> 
	extends IBAImpl<INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> 
	implements DrawableIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>{

	
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
	public IntBAImpl(IBA<STATE, TRANSITION, TRANSITIONFACTORY> model, BA<STATE, TRANSITION, TRANSITIONFACTORY> specification, INTERSECTIONTRANSITIONFACTORY transitionFactory){
		super(transitionFactory);
		if(model==null){
			throw new IllegalArgumentException("The model to be considered cannot be null");
		}
		if(specification==null){
			throw new IllegalArgumentException("The specification to e considered cannot be null");
		}
		this.mixedStates=new HashSet<INTERSECTIONSTATE>();
		this.computeIntersection(model, specification);
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
	public boolean isEmpty(ModelCheckerParameters<STATE, INTERSECTIONSTATE> mp){
		
		if(super.isEmpty()){
			return true;
		}
		if(this.completeEmptiness){
			mp.setViolatingPath(this.stack);
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
		IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> other = (IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>) obj;
		if (mixedStates == null) {
			if (other.mixedStates != null)
				return false;
		} else if (!mixedStates.equals(other.mixedStates))
			return false;
		return true;
	}
	
	/**
	 * computes the intersection of the current automaton and the automaton a2
	 * @param a2 the automaton with which the current automaton must be intersected
	 * @return the intersection of this automaton and the automaton a2
	 * @throws IllegalArgumentException if the automaton a2 is null
	 */
	protected void computeIntersection(IBA<STATE, TRANSITION, TRANSITIONFACTORY> model, BA<STATE, TRANSITION, TRANSITIONFACTORY> specification){
		
		if(model==null){
			throw new IllegalArgumentException("The model cannot be null");
		}
		if(specification==null){
			throw new IllegalArgumentException("The specification cannot be null");
		}
		
		this.alphabet.addAll(model.getAlphabet());
		this.alphabet.addAll(specification.getAlphabet());
		
		for(STATE s1: model.getInitialStates()){
			for(STATE s2: specification.getInitialStates()){
				INTERSECTIONSTATE p=this.addIntersectionState(s1, s2, 0, true, model, specification);
				
				Map<Entry<Entry<STATE, STATE>,Integer>, INTERSECTIONSTATE> map=new HashMap<Entry<Entry<STATE, STATE>,Integer>, INTERSECTIONSTATE>();
				this.computeIntersection(map, p, model, specification);
			}
		}
	}

	/**
	 * is a recursive procedure that computes the intersection of this automaton and the automaton a2
	 * @param s1 is the current state of this automaton under analysis
	 * @param s2 is the current state of the automaton a2 under analysis
	 * @param visitedStates contains the set of the visited states of the intersection automaton
	 * @param currentState contains the current state of the intersection automaton under analysis
	 */
	private void computeIntersection(
									Map<Entry<Entry<STATE, STATE>,Integer>, INTERSECTIONSTATE> visitedStatesMap, 
									INTERSECTIONSTATE currentState,
									IBA<STATE, TRANSITION, TRANSITIONFACTORY> model, 
									BA<STATE, TRANSITION, TRANSITIONFACTORY> specification
									){
		// if the state currentState has been already been visited it returns
		if(visitedStatesMap.containsKey(
				new AbstractMap.SimpleEntry<Entry<STATE, STATE>,Integer>
							(new AbstractMap.SimpleEntry<STATE, STATE>(currentState.getS1(), currentState.getS2()), currentState.getNumber()))){
			return;
		}
		else{
			// add the current state to the set of the visited states
			visitedStatesMap.put(new AbstractMap.SimpleEntry<Entry<STATE, STATE>,Integer>
					(new AbstractMap.SimpleEntry<STATE, STATE>(currentState.getS1(), currentState.getS2()), currentState.getNumber()), currentState);
			
			// for each transition in the extended automaton whit source s1
			for(TRANSITION t1: model.getOutTransition(currentState.getS1())){
				// for each transition in the extended automaton whit source s2
				for(TRANSITION t2: specification.getOutTransition(currentState.getS2())){
					// if the characters of the two transitions are equal
					
					Set<ConjunctiveClause> commonClauses=t1.getDnfFormula().getCommonClauses(t2.getDnfFormula());
					if(!commonClauses.isEmpty()){
						
						
						// creates a new state made by the states s1next and s2 next
						STATE s1next=model.getTransitionDestination(t1);
						STATE s2next=specification.getTransitionDestination(t2);
						INTERSECTIONTRANSITION t=this.transitionFactory.create(new DNFFormula(commonClauses));
						
						int num=this.comuteNumber(s1next, s2next, currentState.getNumber(), model, specification);
						
						INTERSECTIONSTATE p;
						if(visitedStatesMap.containsKey(
								new AbstractMap.SimpleEntry<Entry<STATE, STATE>,Integer>
								(new AbstractMap.SimpleEntry<STATE, STATE>(s1next, s2next), num))){
							p=visitedStatesMap.get(new AbstractMap.SimpleEntry<Entry<STATE, STATE>,Integer>
							(new AbstractMap.SimpleEntry<STATE, STATE>(s1next, s2next), num));
						}
						else{
							p=this.addIntersectionState(s1next, s2next, num, false, model, specification);
						}
						this.addTransition(currentState, p, t);
						
						
						// re-executes the recursive procedure
						this.computeIntersection(visitedStatesMap, p, model , specification);
						
					}
				}
			}
			// if the current state of the extended automaton is transparent
			if(model.isTransparent(currentState.getS1())){
				// for each transition in the automaton a2
				for(TRANSITION t2: specification.getOutTransition(currentState.getS2())){
					// a new state is created made by the transparent state and the state s2next
					STATE s2next=specification.getTransitionDestination(t2);
					INTERSECTIONSTATE p;
					int num=this.comuteNumber(currentState.getS1(), s2next, currentState.getNumber(), model, specification);
					
					if(visitedStatesMap.containsKey(
							new AbstractMap.SimpleEntry<Entry<STATE, STATE>,Integer>
							(new AbstractMap.SimpleEntry<STATE, STATE>(currentState.getS1(), s2next), num))){
						p=visitedStatesMap.get(new AbstractMap.SimpleEntry<Entry<STATE, STATE>,Integer>
						(new AbstractMap.SimpleEntry<STATE, STATE>(currentState.getS1(), s2next), num));
					}
					else{
						p=this.addIntersectionState(currentState.getS1(), s2next, num, false, model, specification);
					}
					
					// the new state is connected by the previous one through a constrained transition
					INTERSECTIONTRANSITION t=this.transitionFactory.create(t2.getDnfFormula(), currentState.getS1());
					this.addTransition(currentState, p, t);
					// the recursive procedure is recalled
					this.computeIntersection(visitedStatesMap, p, model, specification);
				}
			}
		}
	}
	
	
	
	/**
	 * add a new state in the intersection automaton that is under computation based on the current state that is the state of the intersection
	 * automaton which precedes the state that is generated by this method (the current state is used to compute the number 0,1,2
	 * @param s1 is the state of the extended automaton (this)
	 * @param s2 is the state of the specification
	 * @param currentState is the current state of the intersection (the one that precedes the state that is generated in this method)
	 * @return the new intersection state
	 */
	private INTERSECTIONSTATE addIntersectionState(STATE s1, STATE s2, int number, boolean initial,
			IBA<STATE, TRANSITION, TRANSITIONFACTORY> model, 
			BA<STATE, TRANSITION, TRANSITIONFACTORY> specification){

		INTERSECTIONSTATE p = generateIntersectionState(s1, s2, number, model, specification);
		if(initial){
			this.addInitialState(p);
		}
		if(p.getNumber()==2){
			this.addAcceptState(p);
		}
		if(model.isTransparent(s1)){
			this.addMixedState(p);
		}
		this.addVertex(p);
		return p;
	}
	
	/**
	 * generates a new intersection state
	 * @param s1: is the state of the automaton a1 to be included in the intersection state
	 * @param s2: is the state of the automaton a2 to be included in the intersection state
	 * @param currentState: is the current state to be considered in the generation of the automaton state
	 * @return a new intersection state
	 */
	protected INTERSECTIONSTATE generateIntersectionState(STATE s1, STATE s2, int number,
			IBA<STATE, TRANSITION, TRANSITIONFACTORY> model, 
			BA<STATE, TRANSITION, TRANSITIONFACTORY> specification){
		
		IntersectionStateFactory<STATE, INTERSECTIONSTATE> factory=new IntersectionStateFactory<STATE, INTERSECTIONSTATE>();
		return factory.create(s1, s2, number);
	}
	
	protected int comuteNumber(STATE s1, STATE s2, int prevNumber,
			IBA<STATE, TRANSITION, TRANSITIONFACTORY> model, 
			BA<STATE, TRANSITION, TRANSITIONFACTORY> specification){
		int num=0;
		
		if(prevNumber==0 && model.isAccept(s1)){
			num=1;
		}
		if(prevNumber==1 && specification.isAccept(s2)){
			num=2;
		}
		if(prevNumber==2){
			num=0;
		}
		return num;
	}

}