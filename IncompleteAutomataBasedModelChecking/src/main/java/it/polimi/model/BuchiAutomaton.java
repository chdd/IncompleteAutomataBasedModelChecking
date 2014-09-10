package it.polimi.model;

import it.polimi.model.io.MapAdapter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author claudiomenghi
 * contains a complete Buchi Automaton
 * @param <S> the type of the states
 * @param <T> the type of the transitions
 */
@XmlRootElement
public class BuchiAutomaton<S extends State, T extends Transition<S>>{
	
	/**
	 * contains the initial states of the automaton
	 */
	@XmlElementWrapper(name="initialStates")
	@XmlElements({
	    @XmlElement(name="initialState", type=State.class)
	  })
	@XmlIDREF
	private Set<S> initialStates;
	
	/**
	 * contains the states of the automaton
	 */
	@XmlElementWrapper(name="states")
	@XmlElement(name="state")
	private Set<S> states;
	
	/**
	 * contains the accepting states of the automaton
	 */
	@XmlElementWrapper(name="acceptingStates")
	@XmlElements({
	    @XmlElement(name="acceptingState", type=State.class)
	  })
	@XmlIDREF
	private Set<S> acceptStates;
	
	/** 
	 * contains the transition relation
	 */
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<S, HashSet<T>> transitionRelation;
	
	/**
	 * contains the set of the character of the automaton
	 */
	@XmlElementWrapper(name="alphabet")
	@XmlElement(name="character")
	protected Set<String> alphabet;
	
	/**
	 * creates a new empty automaton
	 */
	public BuchiAutomaton() {
		this.states=new HashSet<S>();
		this.initialStates=new HashSet<S>();
		this.acceptStates=new HashSet<S>();
		this.transitionRelation=new HashMap<S, HashSet<T>>();
		this.alphabet=new HashSet<String>(0);
	}
	
	
	/** 
	 * Constructs a new automaton
	 * @param alphabet: is the alphabet of the automaton
	 * @throws NullPointerException is generated if the alphabet of the automaton is null
	 */
	public BuchiAutomaton(Set<String> alphabet) {
		if(alphabet==null){
			throw new IllegalArgumentException();
		}
		this.states=new HashSet<S>();
		this.initialStates=new HashSet<S>();
		this.acceptStates=new HashSet<S>();
		this.transitionRelation=new HashMap<S, HashSet<T>>();
		this.alphabet=alphabet;
	}
	
	/**
	 * Returns the alphabet of the automaton
	 * @return the alphabet of the automaton
	 */
	public Set<String> getAlphabet() {
		return alphabet;
	}
	
	/**
	 * add the state s in the set of the states of the automaton. If s is already present in the set of the states it is not added in the graph
	 * @param s the state to be added in the set of the states of the automaton
	 * @throws IllegalArgumentException is generated if the state s to be added is null
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
	 * check is the state s is contained into the set of the states of the automaton
	 * @param s the state to be checked if present
	 * @return true if the state s is contained into the set of the states of the automaton, false otherwise
	 * @throws IllegalArgumentException when the state s is null
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
	public Set<S> getStates() {
		return this.states;
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
		return this.transitionRelation.get(s);
	}
	
	/**
	 * returns the states ordered in an array.
	 * @param init the initial state is placed in the first position of the array
	 * @param end the end state is positioned in the last position of the array
	 * @return the set of the states ordered in an arbitrary way with the initial and the end states placed in the first and last
	 * position of the array, respectively
	 * if the init and the end states are equal, the state is placed in the first position of the array
	 * @throws IllegalArgumentException is generated in one of the following cases: <br/>
	 * 				the init state is null <br/>
	 * 				the end state is null <br/>
	 * 				the init state is not in the set of the states of the automaton <br/>
	 * 				the end state is not in the set of the states of the automaton <br/>
	 */
	@SuppressWarnings("unchecked")
	public S[] getOrderedStates(S init, S end){
		if(init==null){
			throw new IllegalArgumentException("The init state cannot be null");
		}
		if(end==null){
			throw new IllegalArgumentException("the end state cannot be null");
		}
		if(!this.states.contains(init)){
			throw new IllegalArgumentException("The init state is not in the set of the states of the automaton");
		}
		if(!this.states.contains(end)){
			throw new IllegalArgumentException("The end state is not in the set of the states of the automaton");
		}
		int size=this.getStates().size();
		S[] ret=(S[]) new State[size];
		int i=1;
		ret[0]=init;
		if(!init.equals(end)){
			ret[size-1]=end;
		}
		for(S s: this.getStates()){
			if(!s.equals(init) && !s.equals(end)){
				ret[i]=s;
				i++;
			}
		}
		return ret;
	}
	

	/**
	 * returns the vector S where the position which corresponds to the state accept is marked with λ meaning that the state 
	 * is accepting. All the other states are marked with the empty set ∅
	 * @param accept is the state to be marked with λ
	 * @param statesOrdered contains the ordered set of states
	 * @return the vector S where the position which corresponds to the state accept is marked with λ meaning that the state 
	 * is accepting. All the other states are marked with the empty set ∅
	 * @throws IllegalArgumentException is generated if the state accept or the statesOrdered vector is null or if a state in the state ordered set is not 
	 * contained into the set of the states of the automaton
	 */
	public String[] getS(S accept, S[] statesOrdered){
		if(accept==null){
			throw new IllegalArgumentException("The state accept cannot be null");
		}
		if(statesOrdered==null){
			throw new IllegalArgumentException("The vector of the ordered states cannot be null");
		}
		String [] s=new String[statesOrdered.length];
		
		for(int i=0; i< statesOrdered.length; i++){
			if(!this.states.contains(statesOrdered[i])){
				throw new IllegalArgumentException("The state "+statesOrdered[i]+" which is contained into the state ordered set is not contained into the set of the states of the automaton");
			}
			if(accept.equals(statesOrdered[i])){
				s[i]="λ";
			}
			else{
				s[i]="∅";
			}
		}
		return s;
	}
	/**
	 * returns the matrix that describes the transition relation of the automaton. 
	 * @param init is the initial state which is considered as a source
	 * @param accept is the accept state 
	 * @param statesOrdered is the ordered set of the states
	 * @return the matrix which describes the transition relation of the automaton
	 * @throws IllegalArgumentException is generated when one of the following conditions holds:
	 * 				when the state init is null
	 * 				when the state s is null
	 * 				when the state init or s is not in the set of the states of the automaton
	 * 				when a state in the state ordered set is not in the set of the states of the automaton
	 * 				when a state in the state ordered set is null
	 */
	public String [][] getT(S init, S accept, S[] statesOrdered){
		if(init==null){
			throw new IllegalArgumentException("The state init cannot be null");
		}
		if(accept==null){
			throw new IllegalArgumentException("The state accept cannot be null");
		}
		if(!this.states.contains(init)){
			throw new IllegalArgumentException("The state init: "+init+" must be included into the set of the states of the automaton");
		}
		if(!this.states.contains(accept)){
			throw new IllegalArgumentException("The state accept: "+accept+" must be included into the set of the states of the automaton");
		}
		if(statesOrdered==null){
			throw new IllegalArgumentException("The stateOrdered set cannot be null");
		}
		for(int i=0; i < statesOrdered.length; i++){
			if(statesOrdered[i]==null){
				throw new IllegalArgumentException("The stateOrdered set contains a null element in position: "+i+" be null");
			}
			if(!this.states.contains(statesOrdered[i])){
				throw new IllegalArgumentException("The state "+statesOrdered[i]+" is not contained into the set of the states of the automaton");
			}
			
		}
		
		
		// creates the matrix to be returned
		String [][] t=new String[statesOrdered.length][statesOrdered.length];
		// for each state i
		for(int i=0; i< statesOrdered.length; i++){
			// for each state j
			for(int j=0; j< statesOrdered.length; j++){
				// this flag is created and setted to true if there is a connection from the state i to the state j
				boolean setted=false;
				for(T trans: this.getTransitionsWithSource(statesOrdered[i])){
					if(trans.getDestination().equals(statesOrdered[j])){
						setted=true;
						if(t[i][j]!=null){
							t[i][j]=t[i][j]+"+"+trans.getCharacter();
						}
						else{
							t[i][j]=""+trans.getCharacter();
						}
					}
				}
				if(!setted){
					t[i][j]="∅";
				}
			}
		}
		return t;
	}
	
	
		public static<S extends State, T extends Transition<S>> BuchiAutomaton<S,T> loadAutomaton(String filePath) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(BuchiAutomaton.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		BuchiAutomaton<S,T> automaton = (BuchiAutomaton<S,T>) jaxbUnmarshaller.unmarshal(file);
		return automaton;
	}
	/**
	 * returns a String which contains the XML description of the BuchiAutomaton
	 * @return a String which contains the XML description of the BuchiAutomaton
	 * @throws JAXBException if an error was encountered while creating the XML description of the BuchiAutomaton
	 */
	public String toXMLString() throws JAXBException {
	
		StringWriter sw = new StringWriter();
		// create JAXB context and initializing Marshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		// for getting nice formatted output
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		// Writing to console
		jaxbMarshaller.marshal(this, sw);
		return sw.toString();
		
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
           
    	OutputStream os = new FileOutputStream(filePath);
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
	public static<S extends State, T extends Transition<S>> BuchiAutomaton<S, T> loadAutomatonFromText(String automatonText) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(BuchiAutomaton.class);
		 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		BuchiAutomaton<S, T> automaton = (BuchiAutomaton<S, T>) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(automatonText.getBytes()));
		return automaton;
	}
	
	public void clear(){
		this.acceptStates.clear();
		this.alphabet.clear();
		this.initialStates.clear();
		this.transitionRelation.clear();
		this.states.clear();
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
