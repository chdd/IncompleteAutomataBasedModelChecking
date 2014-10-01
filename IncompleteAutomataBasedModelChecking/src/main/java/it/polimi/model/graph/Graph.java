package it.polimi.model.graph;

import it.polimi.model.automata.MapAdapter;
import it.polimi.model.automata.ba.BuchiAutomaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Graph<S extends State, T extends Transition<S>> {

	/**
	 * contains the initial states of the {@link BuchiAutomaton}
	 */
	@XmlElementWrapper(name="initialStates")
	@XmlElements({
	    @XmlElement(name="initialState", type=State.class)
	  })
	@XmlIDREF
	protected Set<S> initialStates;
	
	/**
	 * contains the states of the {@link BuchiAutomaton}
	 */
	@XmlElementWrapper(name="states")
	@XmlElement(name="state")
	protected LinkedHashSet<S> states;
	
	/**
	 * contains the accepting states of the {@link BuchiAutomaton}
	 */
	@XmlElementWrapper(name="acceptingStates")
	@XmlElements({
	    @XmlElement(name="acceptingState", type=State.class)
	  })
	@XmlIDREF
	protected Set<S> acceptStates;
	
	/** 
	 * contains the transition relation
	 */
	@XmlJavaTypeAdapter(MapAdapter.class)
	protected Map<S, HashSet<T>> transitionRelation;
	
	public Graph(){
		this.states=new LinkedHashSet<S>();
		this.initialStates=new HashSet<S>();
		this.acceptStates=new HashSet<S>();
		this.transitionRelation=new HashMap<S, HashSet<T>>();
	}
	
	/**
	 * add the {@link State} s in the set of the states of the automaton. If s is already present in the set of the states it is not added in the graph
	 * @param s the {@link State} to be added in the set of the states of the {@link BuchiAutomaton}
	 * @throws IllegalArgumentException is generated if the {@link State} s to be added is null
	 */
	public void addState(S s){
		if(s==null){
			throw new IllegalArgumentException("The state s to be added cannot be null");
		}
		if(!this.states.contains(s)){
			this.states.add(s);
			this.transitionRelation.put(s, new HashSet<T>());
		}
	}
	
	/**
	 * check is the {@link State} s is contained into the set of the {@link State}s of the {@link BuchiAutomaton}
	 * @param s the {@link State} to be checked if present
	 * @return true if the {@link State} s is contained into the set of the {@link State}s of the {@link BuchiAutomaton}, false otherwise
	 * @throws IllegalArgumentException when the {@link State} s is null
	 */
	public boolean isContained(S s){
		if(s==null){
			throw new IllegalArgumentException("The state s cannot be null");
		}
		return this.states.contains(s);
	}
	
	/** 
	 * Returns the set of states of the graph. If no states are present an empty set is returned
	 * @return the set of the states of the graph see {@link State}
	 */
	public LinkedHashSet<S> getStates() {
		return this.states;
	}
	public int statePosition(State s){
		
		int i=0;
		for(State tmp: this.states){
			if(tmp.equals(s)){
				return i;
			}
			i++;
		}
		return -1;
		
	}
	
	/** 
	 * Add a new initial state in the set of the states of the graph. 
	 * The state is also added in the set of the states of the graph through the method {@link addState}
	 * @param s state is the initial state to be added in the set of the states of the graph
	 * @throws IllegalArgumentException is generate if the state s to be added is null
	 */
	public void addInitialState(S s) {
		if(s==null){
			throw new IllegalArgumentException("The state s to be added cannot be null");
		}
		this.initialStates.add(s);
		this.addState(s);
	}
	/**
	 * check is the state s is contained into the set of the initial states of the automaton
	 * @param s the state to be checked if present
	 * @return true if the state s is contained into the set of the initial states of the automaton, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isInitial(S s){
		if(s==null){
			throw new IllegalArgumentException("The state s cannot be null");
		}
		return this.initialStates.contains(s);
	}
	/** 
	 * Returns the set (possibly empty) of the initial states of the graph. 
	 * @return the set of the initial states of the graph
	 */
	public Set<S> getInitialStates() {
		return this.initialStates;
	}
	
	/** 
	 * Add a new accept state in the set of the states of the graph. 
	 * The state is also added in the set of the states of the graph through the method {@link addState}
	 * @param s state is the accept state to be added in the set of the states of the graph
	 * @throws IllegalArgumentException is generate if the state s to be added is null
	 */
	public void addAcceptState(S s){
		if(s==null){
			throw new IllegalArgumentException("The state s to be added cannot be null");
		}
		this.acceptStates.add(s);
		this.addState(s);
	}
	/**
	 * check is the state s is contained into the set of the accept states of the automaton
	 * @param s the state to be checked if present
	 * @return true if the state s is contained into the set of the accept states of the automaton, false otherwise
	 * @throws IllegalArgumentException is generate if the state s to be added is null
	 */
	public boolean isAccept(S s){
		if(s==null){
			throw new IllegalArgumentException("The state s to be added cannot be null");
		}
		return this.acceptStates.contains(s);
	}
	
	/** 
	 * Returns the set of accepting states of the automaton. 
	 * @return set of the accepting states of the automaton (see {@link State})
	 */
	public Set<S> getAcceptStates() {
		return this.acceptStates;
	}
	
	/**
	 * add the transition t, with source source to the set of the transitions of the automaton
	 * @param source is the source of the transition
	 * @param t is the transition to be added
	 * @throws IllegalArgumentException is generated in one of the following cases <br/>
	 * 					the source is null <br/>
	 * 					the transition is null <br/>
	 * 					the character of the transition is not contained in the alphabet of the automaton <br/>
	 * 					the source is not contained into the set of the states of the automaton <br/>
	 *					the destination of the transition is not contained into the set of the states of the automaton <br/>
	 */
	public void addTransition(S source, T t){
		if(source==null){
			throw new IllegalArgumentException("The source state of a transition cannot be null");
		}
		if(t==null){
			throw new IllegalArgumentException("The transition to be added cannot be null");
		}
		
		if(!this.states.contains(source)){
			throw new IllegalArgumentException("The state "+source.toString()+" is not present in the set of the states of the graph");
		}
		if(!this.states.contains(t.getDestination())){
			throw new IllegalArgumentException("The destination state: "+t.getDestination()+" is not contained in the set of the states of the automaton.");
		}
		this.transitionRelation.get(source).add(t);
	}
	/**
	 * returns the set of transitions with a specified source. When no transitions with the specified source are present an empty set is returned
	 * @param s is the source of the transitions 
	 * @return the set of the transitions that start from the state s
	 * @throws IllegalArgumentException is generated when the state s is null
	 */
	public Set<T> getTransitionsWithSource(S s){
		if(s==null){
			throw new IllegalArgumentException("The state s of the source of a transitions cannot be null");
		}
		if(!this.transitionRelation.containsKey(s)){
			return new HashSet<T>();
		}
		else{
			return this.transitionRelation.get(s);
		}
		
	}
	
	/**
	 * resets the automaton, removes its states, its initial states, the accepting states, the transitions and the alphabet
	 */
	public void reset(){
		this.initialStates.clear();
		this.states.clear();
		this.acceptStates.clear();
		this.transitionRelation.clear();
	}

	
}
