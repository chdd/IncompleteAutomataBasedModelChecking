package it.polimi.model.impl;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.TransitionFactory;
import it.polimi.model.elements.states.FactoryState;
import it.polimi.model.elements.states.State;
import it.polimi.model.interfaces.BA;
import it.polimi.model.interfaces.drawable.DrawableBA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * @author claudiomenghi
 * contains a complete {@link BAImpl}
 * @param <S> the type of the states
 * @param <T> the type of the transitions
 */
@SuppressWarnings("serial")
public class BAImpl<S extends State, T extends LabelledTransition> extends DirectedSparseGraph<S,T> implements DrawableBA<S,T>{
	
	/**
	 * contains the initial states of the {@link BAImpl}
	 */
	protected Set<S> initialStates;
	
	/**
	 * contains the set of the character of the {@link BAImpl}
	 */
	protected Set<IGraphProposition> alphabet;
	
	/**
	 * contains the accepting states of the {@link BAImpl}
	 */
	protected Set<S> acceptStates;
	
	protected Map<Integer, S> mapNameState;
	
	protected TransitionFactory<T> transitionFactory;
	
	/**
	 * creates a new empty {@link BAImpl}
	 */
	public BAImpl() {
		super();
		
		this.alphabet=new HashSet<IGraphProposition>(0);
		this.acceptStates=new HashSet<S>();
		this.initialStates=new HashSet<S>();
		this.mapNameState=new HashMap<Integer,S>();
		this.transitionFactory=new TransitionFactory<T>();
	}
	
	
	
	
	
	/** 
	 * Constructs a new {@link BAImpl} with the specified alphabet
	 * @param alphabet: is the alphabet of the {@link BAImpl}
	 * @throws NullPointerException is generated if the alphabet of the {@link BAImpl} is null
	 */
	public BAImpl(Set<IGraphProposition> alphabet) {
		super();
		if(alphabet==null){
			throw new IllegalArgumentException();
		}
		this.alphabet=alphabet;
		this.acceptStates=new HashSet<S>();
		this.initialStates=new HashSet<S>();
		this.mapNameState=new HashMap<Integer,S>();
		this.transitionFactory=new TransitionFactory<T>();
	}
	
	/**
	 * Returns the alphabet of the {@link BAImpl}
	 * @return the alphabet of the {@link BAImpl}
	 */
	public Set<IGraphProposition> getAlphabet() {
		return alphabet;
	}
	
	/**
	 * adds the character character in the alphabet of the automaton 
	 * @param character is the character to be added in the alphabet of the automaton
	 * @throws IllegalArgumentException if the character is null or if the character is already contained into the alphabet of the automaton
	 */
	public void addCharacter(IGraphProposition character){
		if(character==null){
			throw new IllegalArgumentException("The character to be inserted into the alphabet cannot be null");
		}
		this.alphabet.add(character);
	}
	
	public void addCharacters(Set<IGraphProposition> characters){
		if(characters==null){
			throw new IllegalArgumentException("The character to be inserted into the alphabet cannot be null");
		}
		for(IGraphProposition character: characters){
			this.addCharacter(character);
		}
	}
	
	/** 
	 * Returns the set (possibly empty) of the initial states of the graph. 
	 * @return the set of the initial states of the graph
	 */
	public Set<S> getInitialStates() {
		return this.initialStates;
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
		this.addVertex(s);
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
		this.addVertex(s);
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
	public void addTransition(S source, S destination, T transition){
		
		this.alphabet.addAll(transition.getDnfFormula().getPropositions());
		super.addEdge(transition, source, destination, EdgeType.DIRECTED);
	}
	
	
	/**
	 * resets the automaton, removes its states, its initial states, the accepting states, the transitions and the alphabet
	 */
	public void reset(){
		this.initialStates=new HashSet<S>();
		this.alphabet=new HashSet<IGraphProposition>();
		this.acceptStates=new HashSet<S>();
		this.edges=new HashMap<T,Pair<S>>();
		this.vertices=new HashMap<S,Pair<Map<S,T>>>();
		this.alphabet.clear();
	}
	
	
	/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */
	public void getRandomAutomaton2(int n, double transitionProbability, int numInitial, int numAccepting, Set<IGraphProposition> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		this.reset();
		this.addCharacters(alphabet);
		Random r=new Random();
		
		FactoryState<S> stateFactory=new FactoryState<S>();
		for(int i=0; i<n;i++){
			
			S s=stateFactory.create("s"+i);
			this.addVertex(s);
		}
		
		for(int i=0; i<numInitial; i++){
			int transp=r.nextInt(this.getVertices().size());
			Iterator<S> it=this.getVertices().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			this.addInitialState(it.next());
		}
		for(int i=0; i<numAccepting; i++){
			int transp=r.nextInt(this.getVertices().size());
			Iterator<S> it=this.getVertices().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			this.addAcceptState(it.next());
		}
		for(S s1: this.getVertices()){
			for(S s2: this.getVertices()){
				double randInt=r.nextInt(11)/10.0;
				if(randInt<=transitionProbability){
					
					
					IGraphProposition character=IBAImpl.getRandomString(alphabet, r.nextInt(alphabet.size()));
					this.addTransition(s1, s2,  this.transitionFactory.create(character));
				}
			}
		}
	}
	public static IGraphProposition getRandomString(Set<IGraphProposition> alphabet, int position){

		Iterator<IGraphProposition> it=alphabet.iterator();
		for(int i=0; i<position; i++){
		
			it.next();
		}
		return it.next();
	}
	
	protected Stack<S> stack;
	/**
	 * returns true if the automaton is empty
	 * @return true if the automaton is empty
	 */
	public boolean isEmpty(){
		boolean res=true;
		Set<S> visitedStates=new HashSet<S>();
		
		for(S init: this.getInitialStates()){
			stack=new Stack<S>();
			if(firstDFS(visitedStates, init, stack)){
				
				return false;
			}
		}
		// clear the set of the visited states
		visitedStates.clear();
		return res;
	}
	
	/**
	 * returns true if an accepting path is found
	 * @param visitedStates contains the set of the visited states by the algorithm
	 * @param currState is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 */
	protected boolean firstDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath){
		// if the current state have been already visited (and the second DFS has not been started) it means that the path is not accepting
		if(visitedStates.contains(currState)){
			return false;
		}
		else{
			// I add the state in the set of visited states
			visitedStates.add(currState);
			// I add the state in the state of the path
			statesOfThePath.push(currState);
			// if the state is accepting
			if(this.isAccept(currState)){
				for(T t: this.getOutEdges(currState)){
					
					Stack<S> stackSecondDFS=new Stack<S>();
					// I start the second DFS if the answer of the second DFS is true I return true
					if(this.secondDFS(new HashSet<S>(), this.getDest(t), statesOfThePath, stackSecondDFS)){
						statesOfThePath.addAll(stackSecondDFS);
						return true;
					}
				}
			}
			// otherwise, I check each transition that leaves the state currState
			for(T t: this.getOutEdges(currState)){
				// I call the first DFS method, If the answer is true I return true
				if(firstDFS(visitedStates, this.getDest(t), statesOfThePath)){
					return true;
				}
			}
			// I remove the state from the stack of the states of the current path
			statesOfThePath.pop();
			return false;
		}
	}
	/**
	 * contains the second DFS procedure
	 * @param visitedStates contains the set of the states visited in the SECOND DFS procedure 
	 * @param currState is the current state under analysis
	 * @param statesOfThePath is the state of the path that is currently analyzed
	 * @return true if an accepting path is found (a path that contains a state in the set of the states statesOfThePath), false otherwise
	 */
	// note that at the beginning the visited states do not contain the current state
	protected boolean secondDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath, Stack<S> stackSecondDFS){
		// if the state is in the set of the states on the path the an accepting path is found
		if(statesOfThePath.contains(currState)){
			return true;
		}
		else{
			// if the state is in the set of the visited states of the second DFS, the path is not accepting
			if(visitedStates.contains(currState)){
				return false;
			}
			else{
				stackSecondDFS.push(currState);
				// add the state into the set of the visited states
				visitedStates.add(currState);
				// for each transition that leaves the current state
				for(T t: this.getOutEdges(currState)){
					
					// if the second DFS returns a true answer than the accepting path has been found
					if(secondDFS(visitedStates, this.getDest(t), statesOfThePath, stackSecondDFS)){
						return true;
					}
				}
				stackSecondDFS.pop();
				// otherwise the path is not accepting
				return false;
			}
		}
	}
	@Override
	public EdgeType getDefaultEdgeType() 
	{
		return EdgeType.DIRECTED;
	}
	
	public boolean addVertex(S vertex) {
		boolean ret=super.addVertex(vertex);
		this.mapNameState.put(vertex.getId(), vertex);
		return ret;
	}
	
	public boolean removeVertex(S vertex) {
		if(this.initialStates.contains(vertex)){
			this.initialStates.remove(vertex);
		}
		if(this.acceptStates.contains(vertex)){
			this.acceptStates.remove(vertex);
		}
        return super.removeVertex(vertex);
    }
	
	public S getVertex(int id){
		
		if(!this.mapNameState.containsKey(id)){
			throw new IllegalArgumentException("The state with the id "+id+" is not contained in the set of the states of the automaton");
		}
		
		return this.mapNameState.get(id);
	}

	/**
	 * returns the transitions that exits the {@link State} state
	 * @return the transitions that exits the {@link State} state
	 */
	@Override
	public Set<T> getOutTransition(S state) {
		return new HashSet<T>(super.getOutEdges(state));
	}

	/**
	 * returns the destination of the {@link LabelledTransition} transition
	 * @return the {@link State} which is the destination of the {@link LabelledTransition} transition
	 */
	@Override
	public S getTransitionDestination(T transition) {
		return this.getDest(transition);
	}

	/**
	 * returns the number of the states of the {@link BAImpl}
	 */
	@Override
	public int getStateNumber() {
		return this.getVertexCount();
	}
}
