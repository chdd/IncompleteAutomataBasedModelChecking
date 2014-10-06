package it.polimi.model.automata.ba;

import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * @author claudiomenghi
 * contains a complete {@link BuchiAutomaton}
 * @param <S> the type of the states
 * @param <T> the type of the transitions
 */
@SuppressWarnings("serial")
@XmlRootElement
public class BuchiAutomaton<S extends State, T extends LabelledTransition<S>> extends SparseMultigraph<S,T>{
	
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
	 * contains the set of the character of the {@link BuchiAutomaton}
	 */
	@XmlElementWrapper(name="alphabet")
	@XmlElement(name="character")
	protected Set<String> alphabet;
	

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
	 * creates a new empty {@link BuchiAutomaton}
	 */
	public BuchiAutomaton() {
		
		this.alphabet=new HashSet<String>(0);
		this.acceptStates=new HashSet<S>();
		this.initialStates=new HashSet<S>();
	}
	
	
	
	/** 
	 * Constructs a new {@link BuchiAutomaton} with the specified alphabet
	 * @param alphabet: is the alphabet of the {@link BuchiAutomaton}
	 * @throws NullPointerException is generated if the alphabet of the {@link BuchiAutomaton} is null
	 */
	public BuchiAutomaton(Set<String> alphabet) {
		super();
		if(alphabet==null){
			throw new IllegalArgumentException();
		}
		this.alphabet=alphabet;
		this.acceptStates=new HashSet<S>();
		this.initialStates=new HashSet<S>();
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
		
		if(!this.alphabet.containsAll(t.getCharacter())){
			throw new IllegalArgumentException("The character: "+t.getCharacter()+" is not contained in the set of the characters of the automaton.");
		}
		super.addEdge(t, source, t.getDestination());
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Automaton \n"
				+ "initialStates: " + initialStates + "\n"
				+ "states: "+ this.getVertices() + "\n"
				+ "acceptStates: " + acceptStates + "\n"
				+ "transitionRelation: " + this.edges+ "\n"
				+ "alphabet: "+ alphabet + "\n";
	}
	
	
	/**
	 * resets the automaton, removes its states, its initial states, the accepting states, the transitions and the alphabet
	 */
	public void reset(){
		this.initialStates=new HashSet<S>();
		this.alphabet=new HashSet<String>();
		this.acceptStates=new HashSet<S>();
		this.directedEdges=new HashSet<T>();
		this.edges=new HashMap<T,Pair<S>>();
		this.vertices=new HashMap<S,Pair<Set<T>>>();
		this.alphabet.clear();
	}
	
	
	/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */
	public static<S extends State, T extends LabelledTransition<S>> BuchiAutomaton<State,LabelledTransition<State>> getRandomAutomaton2(int n, double transitionProbability, int numInitial, int numAccepting, Set<String> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		Random r=new Random();
		BuchiAutomaton<State,LabelledTransition<State>> a=new IncompleteBuchiAutomaton<State,LabelledTransition<State>>(alphabet);
		for(int i=0; i<n;i++){
			State s=new State("s"+i);
			a.addVertex(s);
		}
		
		for(int i=0; i<numInitial; i++){
			int transp=r.nextInt(a.getVertices().size());
			Iterator<State> it=a.getVertices().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			a.addInitialState(it.next());
		}
		for(int i=0; i<numAccepting; i++){
			int transp=r.nextInt(a.getVertices().size());
			Iterator<State> it=a.getVertices().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			a.addAcceptState(it.next());
		}
		for(State s1: a.getVertices()){
			for(State s2: a.getVertices()){
				double randInt=r.nextInt(11)/10.0;
				if(randInt<=transitionProbability){
					Set<String> characters=new HashSet<String>();
					String character=IncompleteBuchiAutomaton.getRandomString(alphabet, r.nextInt(alphabet.size()));
					characters.add(character);
					a.addTransition(s1, new LabelledTransition<State>(characters, s2));
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
				for(T t: this.getOutEdges(currState)){
					// I start the second DFS if the answer of the second DFS is true I return true
					if(this.secondDFS(new HashSet<S>(), t.getDestination(), statesOfThePath)){
						return true;
					}
				}
			}
			// otherwise, I check each transition that leaves the state currState
			for(T t: this.getOutEdges(currState)){
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
				for(T t: this.getOutEdges(currState)){
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
	
	
}
