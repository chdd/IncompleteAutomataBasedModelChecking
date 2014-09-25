package it.polimi.model;

import it.polimi.model.io.MapAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author claudiomenghi
 * contains a complete {@link BuchiAutomaton}
 * @param <S> the type of the states
 * @param <T> the type of the transitions
 */
@XmlRootElement
public class BuchiAutomaton<S extends State, T extends Transition<S>>{
	
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
	
	/**
	 * contains the set of the character of the {@link BuchiAutomaton}
	 */
	@XmlElementWrapper(name="alphabet")
	@XmlElement(name="character")
	protected Set<String> alphabet;
	
	/**
	 * creates a new empty {@link BuchiAutomaton}
	 */
	public BuchiAutomaton() {
		this.states=new LinkedHashSet<S>();
		this.initialStates=new HashSet<S>();
		this.acceptStates=new HashSet<S>();
		this.transitionRelation=new HashMap<S, HashSet<T>>();
		this.alphabet=new HashSet<String>(0);
	}
	
	
	
	/** 
	 * Constructs a new {@link BuchiAutomaton} with the specified alphabet
	 * @param alphabet: is the alphabet of the {@link BuchiAutomaton}
	 * @throws NullPointerException is generated if the alphabet of the {@link BuchiAutomaton} is null
	 */
	public BuchiAutomaton(Set<String> alphabet) {
		if(alphabet==null){
			throw new IllegalArgumentException();
		}
		this.states=new LinkedHashSet<S>();
		this.initialStates=new HashSet<S>();
		this.acceptStates=new HashSet<S>();
		this.transitionRelation=new HashMap<S, HashSet<T>>();
		this.alphabet=alphabet;
	}
	
	/**
	 * Returns the alphabet of the {@link BuchiAutomaton}
	 * @return the alphabet of the {@link BuchiAutomaton}
	 */
	public Set<String> getAlphabet() {
		return alphabet;
	}
	
	/**
	 * adds the character character in the alphabet of the automaton 
	 * @param character is the character to be added in the alphabet of the automaton
	 * @throws IllegalArgumentException if the character is null or if the character is already contained into the alphabet of the automaton
	 */
	public void addCharacter(String character){
		if(character==null){
			throw new IllegalArgumentException("The character to be inserted into the alphabet cannot be null");
		}
		if(this.alphabet.contains(character)){
			throw new IllegalArgumentException("The character "+character+"is already contained in the alphabet of the automaton");
		}
		this.alphabet.add(character);
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
		if(!this.alphabet.contains(t.getCharacter())){
			throw new IllegalArgumentException("The character: "+t.getCharacter()+" is not contained in the set of the characters of the automaton.");
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
	 * returns a String which contains the XML description of the BuchiAutomaton
	 * @return a String which contains the XML description of the BuchiAutomaton
	 * @throws JAXBException if an error was encountered while creating the XML description of the BuchiAutomaton
	 */
	public String toXMLString() {
	
		StringWriter sw = new StringWriter();
		// create JAXB context and initializing Marshaller
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(this.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			// for getting nice formatted output
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Writing to console
			jaxbMarshaller.marshal(this, sw);
			return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * writes the BuchiAutomaton to the file with path filePath
	 * @param filePath is the path of the file where the BuchiAutomaton must be written
	 * @throws JAXBException if an error was encountered while creating the XML description of the BuchiAutomaton
	 * @throws IOException - if an I/O error occurs.
	 */
	public void toFile(String filePath) throws JAXBException, IOException{
		JAXBContext context = JAXBContext.newInstance(this.getClass());
		 
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           
        File baFile = new File(filePath);
        if(!baFile.exists()) {
            baFile.createNewFile();
        } 
    	OutputStream os = new FileOutputStream(baFile, false);
        m.marshal( this, os ); 
		os.close();
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acceptStates == null) ? 0 : acceptStates.hashCode());
		result = prime * result
				+ ((alphabet == null) ? 0 : alphabet.hashCode());
		result = prime * result
				+ ((initialStates == null) ? 0 : initialStates.hashCode());
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		result = prime
				* result
				+ ((transitionRelation == null) ? 0 : transitionRelation
						.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuchiAutomaton<S,T> other = (BuchiAutomaton<S,T>) obj;
		if (acceptStates == null) {
			if (other.acceptStates != null)
				return false;
		} else if (!acceptStates.equals(other.acceptStates))
			return false;
		if (alphabet == null) {
			if (other.alphabet != null)
				return false;
		} else if (!alphabet.equals(other.alphabet))
			return false;
		if (initialStates == null) {
			if (other.initialStates != null)
				return false;
		} else if (!initialStates.equals(other.initialStates))
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		if (transitionRelation == null) {
			if (other.transitionRelation != null)
				return false;
		} else if (!transitionRelation.equals(other.transitionRelation))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Automaton \n"
				+ "initialStates: " + initialStates + "\n"
				+ "states: "+ states + "\n"
				+ "acceptStates: " + acceptStates + "\n"
				+ "transitionRelation: " + transitionRelation+ "\n"
				+ "alphabet: "+ alphabet + "\n";
	}
	
	
	/**
	 * resets the automaton, removes its states, its initial states, the accepting states, the transitions and the alphabet
	 */
	public void reset(){
		this.initialStates.clear();
		this.states.clear();
		this.acceptStates.clear();
		this.transitionRelation.clear();
		this.alphabet.clear();
	}
	
	/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */
	public static<S extends State, T extends Transition<S>> BuchiAutomaton<State,Transition<State>> getRandomAutomaton2(int n, double transitionProbability, int numInitial, int numAccepting, Set<String> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		Random r=new Random();
		BuchiAutomaton<State,Transition<State>> a=new IncompleteBuchiAutomaton<State,Transition<State>>(alphabet);
		for(int i=0; i<n;i++){
			State s=new State("s"+i);
			a.addState(s);
		}
		
		for(int i=0; i<numInitial; i++){
			int transp=r.nextInt(a.getStates().size());
			Iterator<State> it=a.getStates().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			a.addInitialState(it.next());
		}
		for(int i=0; i<numAccepting; i++){
			int transp=r.nextInt(a.getStates().size());
			Iterator<State> it=a.getStates().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			a.addAcceptState(it.next());
		}
		for(State s1: a.getStates()){
			for(State s2: a.getStates()){
				double randInt=r.nextInt(11)/10.0;
				if(randInt<=transitionProbability){
					String character=IncompleteBuchiAutomaton.getRandomString(alphabet, r.nextInt(alphabet.size()));
					a.addTransition(s1, new Transition<State>(character, s2));
				}
			}
		}
		return a;
	}
	public static String getRandomString(Set<String> alphabet, int position){

		Iterator<String> it=alphabet.iterator();
		for(int i=0; i<position; i++){
		
			it.next();
		}
		return it.next();
	}
	/**
	 * returns true if the automaton is empty
	 * @return true if the automaton is empty
	 */
	public boolean isEmpty(){
		boolean res=true;
		Set<S> visitedStates=new HashSet<S>();
		for(S init: this.getInitialStates()){
			if(firstDFS(visitedStates, init, new Stack<S>())){
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
				// I start the second DFS if the answer of the second DFS is true I return true
				if(this.secondDFS(new HashSet<S>(), currState, statesOfThePath)){
					return true;
				}
			}
			// otherwise, I check each transition that leaves the state currState
			for(T t: this.getTransitionsWithSource(currState)){
				// I call the first DFS method, If the answer is true I return true
				if(firstDFS(visitedStates, t.getDestination(), statesOfThePath)){
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
	protected boolean secondDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath){
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
				// add the state into the set of the visited states
				visitedStates.add(currState);
				// for each transition that leaves the current state
				for(T t: this.getTransitionsWithSource(currState)){
					// if the second DFS returns a true answer than the accepting path has been found
					if(secondDFS(visitedStates, t.getDestination(), statesOfThePath)){
						return true;
					}
				}
				// otherwise the path is not accepting
				return false;
			}
		}
	}
}
