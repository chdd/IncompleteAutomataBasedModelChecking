package it.polimi.model.impl;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.ConstrainedTransitionFactory;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.labeling.ConjunctiveClause;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.FactoryIntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.interfaces.BA;
import it.polimi.model.interfaces.DrawableIntBA;
import it.polimi.model.interfaces.IBA;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * contains an intersection automaton
 * @author claudiomenghi
 *
 * @param <S1> is the type of the states of the original Buchi automata
 * @param <T1> is the type of the transitions of the original Buchi automata
 * @param <S> is the type of the state in the intersection automaton
 * @param <T> is the type of the transition in the intersection automaton
 */
@SuppressWarnings("serial")
public class IntersectionAutomaton<S1 extends State, T1 extends LabelledTransition,S extends IntersectionState<S1>, T 
extends ConstrainedTransition<S1>> extends IBAImpl<S, T> implements DrawableIntBA<S1,T1,S,T>{

	/**
	 * contains the model of the system that generates the intersection automaton
	 */
	private IBA<S1, T1> model;
	
	/**
	 * contains the specification that generates the intersection automaton
	 */
	private BA<S1, T1> specification;
	
	/**
	 * contains the set of the mixed states
	 */
	private Set<S> mixedStates;
	
	/**
	 * is used in the emptiness checking to store if the complete emptiness checking procedure or the "normal" emptiness checking must be performed
	 */
	private boolean completeEmptiness=true;
	
	public IntersectionAutomaton(){
		this.model=new IBAImpl<S1, T1>();
		this.specification=new BAImpl<S1, T1>();
		this.mixedStates=new HashSet<S>(); 
	}
	/**
	 * creates a new Intersection automaton starting from the model and its specification
	 * @param model: is the model to be considered
	 * @param specification: is the specification to be considered
	 */
	public IntersectionAutomaton(IBA<S1, T1> model, BA<S1, T1> specification){
		super();
		if(model==null){
			throw new IllegalArgumentException("The model to be considered cannot be null");
		}
		if(specification==null){
			throw new IllegalArgumentException("The specification to e considered cannot be null");
		}
		this.model=model;
		this.specification=specification;
		this.mixedStates=new HashSet<S>();
		this.computeIntersection();
	}
	
	
	/**
	 * add a mixed state in the intersection automaton
	 * @param s the state to be added in the set of the mixed states
	 * @throws IllegalArgumentException if the state s is null
	 */
	public void addMixedState(S s){
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
	public boolean isMixed(S s){
		if(s==null){
			throw new IllegalArgumentException("The state s cannot be null");
		}
		return this.mixedStates.contains(s);
	}
	/**
	 * returns the set of the mixed states
	 * @return the set of the mixed states
	 */
	public Set<S> getMixedStates(){
		return this.mixedStates;
	}
	
	/**
	 * @return the model that generates the intersection automaton
	 */
	public IBA<S1, T1> getModel() {
		return model;
	}
	
	/** 
	 * returns true if the complete version (without mixed states) of the intersection automaton is  empty
	 * @return true if the complete version (without mixed states) of the intersection automaton is  empty
	 */
	public boolean isEmpty(ModelCheckerParameters<S1, S> mp){
		
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
	protected boolean firstDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath){
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
	protected boolean secondDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath, Stack<S> stackSecondDFS){
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
		IntersectionAutomaton<S1, T1, S, T> other = (IntersectionAutomaton<S1, T1, S, T>) obj;
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
	protected void computeIntersection() {
		
		if(model==null){
			throw new IllegalArgumentException("The model cannot be null");
		}
		if(specification==null){
			throw new IllegalArgumentException("The specification cannot be null");
		}
		
		this.alphabet.addAll(model.getAlphabet());
		this.alphabet.addAll(specification.getAlphabet());
		
		for(S1 s1: model.getInitialStates()){
			for(S1 s2: specification.getInitialStates()){
				S p=this.addIntersectionState(s1, s2, null);
				
				Set<S> visitedStates=new HashSet<S>();
				this.computeIntersection(s1, s2, visitedStates, p);
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
									S1 s1, 
									S1 s2, 
									Set<S> visitedStates, 
									S currentState
									){
		// if the state currentState has been already been visited it returns
		if(visitedStates.contains(currentState)){
			return;
		}
		else{
			// add the current state to the set of the visited states
			visitedStates.add(currentState);
			
			// for each transition in the extended automaton whit source s1
			for(T1 t1: this.model.getOutTransition(s1)){
				// for each transition in the extended automaton whit source s2
				for(T1 t2: this.specification.getOutTransition(s2)){
					// if the characters of the two transitions are equal
					
					Set<ConjunctiveClause> commonClauses=t1.getDnfFormula().getCommonClauses(t2.getDnfFormula());
					if(!commonClauses.isEmpty()){
						
						
						// creates a new state made by the states s1next and s2 next
						S1 s1next=this.model.getTransitionDestination(t1);
						S1 s2next=this.specification.getTransitionDestination(t2);
						S p=this.addIntersectionState(s1next, s2next, currentState);
						// add the transition from the current state to the new created state
						ConstrainedTransitionFactory<S1, T> ctfactory=new ConstrainedTransitionFactory<S1, T>();
						T t=ctfactory.create(commonClauses);
						this.addTransition(currentState, p, t);
						
						// re-executes the recursive procedure
						this.computeIntersection(s1next, s2next, visitedStates, p);
						
					}
				}
			}
			// if the current state of the extended automaton is transparent
			if(model.isTransparent(s1)){
				// for each transition in the automaton a2
				for(T1 t2: specification.getOutTransition(s2)){
					// a new state is created made by the transparent state and the state s2next
					S1 s2next=this.specification.getTransitionDestination(t2);
					S p=this.addIntersectionState(s1, s2next, currentState);
					// the new state is connected by the previous one through a constrained transition
					ConstrainedTransitionFactory<S1, T> ctfactory=new ConstrainedTransitionFactory<S1, T>();
					T t=ctfactory.create(t2.getDnfFormula(), s1);
					this.addTransition(currentState, p, t);
					// the recursive procedure is recalled
					this.computeIntersection(s1, s2next, visitedStates, p);
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
	private S addIntersectionState(S1 s1, S1 s2, S currentState){

		@SuppressWarnings("unchecked")
		S p = (S) this.generateIntersectionState(s1, s2, currentState);
		if(p.getNumber()==2){
			this.addAcceptState(p);
		}
		else{
			this.addVertex(p);
		}
		if(this.model.isTransparent(s1)){
			this.addMixedState(p);
		}
		this.addVertex(p);
		if(currentState==null){
			this.addInitialState(p);
		}
		return p;
	}
	
	/**
	 * generates a new intersection state
	 * @param s1: is the state of the automaton a1 to be included in the intersection state
	 * @param s2: is the state of the automaton a2 to be included in the intersection state
	 * @param currentState: is the current state to be considered in the generation of the automaton state
	 * @return a new intersection state
	 */
	protected IntersectionState<S1> generateIntersectionState(S1 s1, S1 s2, S currentState){
		int num=0;
		if(currentState!=null){
			num=currentState.getNumber();
		}
		if(num==0 && this.model.isAccept(s1)){
			num=1;
		}
		else{
			if(num==1 && this.specification.isAccept(s2)){
				num=2;
			}
			else{
				if(num==2){
					num=0;
				}
			}
		}
		FactoryIntersectionState<S1, S> factory=new FactoryIntersectionState<S1, S>();
		IntersectionState<S1> p = factory.create(s1, s2, num);
		return p;
	}
}