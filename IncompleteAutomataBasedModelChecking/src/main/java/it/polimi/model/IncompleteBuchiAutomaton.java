package it.polimi.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Claudio Menghi
 * contains a possibly incomplete automaton which extends classical automaton with transparent states
 */
@XmlRootElement
public class IncompleteBuchiAutomaton<S extends State, T extends Transition<S>> extends BuchiAutomaton<S,T>{

	/**
	 * contains the set of the transparent states of the automaton
	 */
	@XmlElementWrapper(name="transparentStates")
	@XmlElements({
	    @XmlElement(name="transparentState", type=State.class)
	  })
	@XmlIDREF
	private Set<S> transparentStates;
	
	/**
	 * creates a new extended automaton
	 */
	protected IncompleteBuchiAutomaton(){
		super();
		this.transparentStates=new HashSet<S>();
	}
	
	/**
	 * creates a new extended automaton with the specified alphabet (see {@link BuchiAutomaton}
	 * @param alphabet is the alphabet of the extended automaton
	 * @throws NullPointerException is generated if the alphabet of the automaton is null
	 */
	public IncompleteBuchiAutomaton(Set<String> alphabet) {
		super(alphabet);
		transparentStates=new HashSet<S>();
	}
	
	/**
	 * add a new transparent state in the automaton (the transparent state is also added to the set of the states of the automaton
	 * @param s is the state to be added and is also added to the set of the states of the automaton
	 * @throws IllegalArgumentException if the state to be added is null
	 */
	public void addTransparentState(S s){
		if(s==null){
			throw new IllegalArgumentException("The state to be added cannot be null");
		}
		this.transparentStates.add(s);
		this.addState(s);
	}
	
	/**
	 * check if the state is transparent
	 * @param s is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isTransparent(S s){
		if(s==null){
			throw new IllegalArgumentException("The state to be added cannot be null");
		}
		return this.transparentStates.contains(s);
	}
	public Set<S>  getTransparentStates(){
		return this.transparentStates;
	}
	
	/**
	 * Returns the string representation of the automaton
	 * @see BuchiAutomaton
	 */
	@Override
	public String toString() {
		return super.toString()
				+ "transparentStates: "+this.transparentStates+ "\n";
	}
	
	
	/**
	 * computes the intersection of the current automaton and the automaton a2
	 * @param a2 the automaton with which the current automaton must be intersected
	 * @return the intersection of this automaton and the automaton a2
	 * @throws IllegalArgumentException if the automaton a2 is null
	 */
	public IntersectionAutomaton<S, T, IntersectionState<S>, Transition<IntersectionState<S>>> computeIntersection( BuchiAutomaton<S, T>  a2) {
		
		if(a2==null){
			throw new IllegalArgumentException("The automaton a2 cannot be null");
		}
		
		Set<String> newAlphabet=new HashSet<String>();
		newAlphabet.addAll(this.getAlphabet());
		newAlphabet.addAll(a2.getAlphabet());
		
		IntersectionAutomaton<S, T, IntersectionState<S>, Transition<IntersectionState<S>>> c = new IntersectionAutomaton<S, T, IntersectionState<S>, Transition<IntersectionState<S>>>(newAlphabet, this, a2);
		
		for(S s1: this.getInitialStates()){
			for( S s2: a2.getInitialStates()){
				IntersectionState<S> p = IntersectionState.generateIntersectionState(this, a2, s1, s2, null);
				c.addInitialState(p);
				
				Set<IntersectionState<S>> visitedStates=new HashSet<IntersectionState<S>>();
				this.computeIntersection(c, a2, s1, s2, visitedStates, p);
			}
		}
		return c;
	}

	/**
	 * is a recursive procedure that computes the intersection of this automaton and the automaton a2
	 * @param ris contains the result of the intersection procedure
	 * @param a2 is the automaton to be intersected
	 * @param s1 is the current state of this automaton under analysis
	 * @param s2 is the current state of the automaton a2 under analysis
	 * @param visitedStates contains the set of the visited states of the intersection automaton
	 * @param currentState contains the current state of the intersection automaton under analysis
	 */
	private void computeIntersection(IntersectionAutomaton<S, T, IntersectionState<S>, Transition<IntersectionState<S>>> ris,  
									BuchiAutomaton<S, T>  a2,
									S s1, 
									S s2, 
									Set<IntersectionState<S>> visitedStates, 
									IntersectionState<S> currentState
									){
		// if the state currentState has been already been visited it returns
		if(visitedStates.contains(currentState)){
			return;
		}
		else{
			// add the current state to the set of the visited states
			visitedStates.add(currentState);
			
			// for each transition in the extended automaton whit source s1
			for(T t1: this.getTransitionsWithSource(s1)){
				// for each transition in the extended automaton whit source s2
				for(T t2: a2.getTransitionsWithSource(s2)){
					// if the characters of the two transitions are equal
					if(t1.getCharacter().equals(t2.getCharacter())){
						
						// creates a new state made by the states s1next and s2 next
						S s1next=t1.getDestination();
						S s2next=t2.getDestination();
						IntersectionState<S> p=this.addIntersectionState(ris, a2, s1next, s2next, currentState);
						// add the transition from the current state to the new created state
						Transition<IntersectionState<S>> t=new Transition<IntersectionState<S>>(t1.getCharacter(), p);
						ris.addTransition(currentState, t);
						
						// re-executes the recursive procedure
						this.computeIntersection(ris, a2, s1next, s2next, visitedStates, p);
						
					}
				}
			}
			// if the current state of the extended automaton is transparent
			if(this.isTransparent(s1)){
				// for each transition in the automaton a2
				for(T t2: a2.getTransitionsWithSource(s2)){
					// a new state is created made by the transparent state and the state s2next
					S s2next=t2.getDestination();
					IntersectionState<S> p=this.addIntersectionState(ris, a2, s1, t2.getDestination(), currentState);
					// the new state is connected by the previous one through a constrained transition
					ConstrainedTransition<S,IntersectionState<S>> t=new ConstrainedTransition<S,IntersectionState<S>>(t2.getCharacter(), p, s1);
					ris.addTransition(currentState, t);
					// the recursive procedure is recalled
					this.computeIntersection(ris, a2, s1, s2next, visitedStates, p);
				}
			}
		}
	}
	
	
	
	/**
	 * add a new state in the intersection automaton that is under computation based on the current state that is the state of the intersection
	 * automaton which precedes the state that is generated by this method (the current state is used to compute the number 0,1,2
	 * @param ris is the intersection automaton that is computed
	 * @param a2 is the automaton that contains the specification
	 * @param s1 is the state of the extended automaton (this)
	 * @param s2 is the state of the specification
	 * @param currentState is the current state of the intersection (the one that precedes the state that is generated in this method)
	 * @return the new intersection state
	 */
	private IntersectionState<S> addIntersectionState(IntersectionAutomaton<S, T, IntersectionState<S>, Transition<IntersectionState<S>>> ris, BuchiAutomaton<S, T>  a2, S s1, S s2, IntersectionState<S> currentState){

		IntersectionState<S> p = IntersectionState.generateIntersectionState(this, a2, s1, s2, currentState);
		if(p.getNumber()==2){
			ris.addAcceptState(p);
		}
		else{
			ris.addState(p);
		}
		if(this.isTransparent(s1)){
			ris.addMixedState(p);
		}
		return p;
	}


	
	public static void validate(String filePath) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandler() {
		    @Override
		    public void error(SAXParseException exception) throws SAXException {
		        // do something more useful in each of these handlers
		        exception.printStackTrace();
		    }
		    @Override
		    public void fatalError(SAXParseException exception) throws SAXException {
		        exception.printStackTrace();
		    }

		    @Override
		    public void warning(SAXParseException exception) throws SAXException {
		        exception.printStackTrace();
		    }
		});
		builder.parse(filePath);
	}
	
	public static<S extends State, T extends Transition<S>> IncompleteBuchiAutomaton<S,T> loadAutomaton(String filePath) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		
		//ExtendedAutomaton.validate(filePath);
        
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(IncompleteBuchiAutomaton.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		IncompleteBuchiAutomaton<S,T> automaton = (IncompleteBuchiAutomaton<S,T>) jaxbUnmarshaller.unmarshal(file);
		return automaton;
	}
	
	public static<S extends State, T extends Transition<S>> IncompleteBuchiAutomaton<S, T> loadAutomatonFromText(String automatonText) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(IncompleteBuchiAutomaton.class);
		 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		IncompleteBuchiAutomaton<S, T> automaton = (IncompleteBuchiAutomaton<S, T>) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(automatonText.getBytes()));
		return automaton;
	}
	
	/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */
	public static<S extends State, T extends Transition<S>> IncompleteBuchiAutomaton<State,Transition<State>> getRandomAutomaton(int n, double transitionProbability, double initialStateProbability, double acceptingStateProbability, double transparentStateProbability, Set<String> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		Random r=new Random();
		IncompleteBuchiAutomaton<State,Transition<State>> a=new IncompleteBuchiAutomaton<State,Transition<State>>(alphabet);
		for(int i=0; i<n;i++){
			State s=new State("s"+i);
			if(r.nextInt(10)<=initialStateProbability*10){
				a.addInitialState(s);
			}
			else{
				if(r.nextInt(10)<acceptingStateProbability*10){
					a.addAcceptState(s);
				}
				else{
					a.addState(s);
				}
			}
			if(r.nextInt(10)<transparentStateProbability*10){
				a.addTransparentState(s);
			}
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
	/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */
	public static<S extends State, T extends Transition<S>> IncompleteBuchiAutomaton<State,Transition<State>> getRandomAutomaton2(int n, double transitionProbability, int numInitial, int numAccepting, int numTransparentStates, Set<String> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		Random r=new Random();
		IncompleteBuchiAutomaton<State,Transition<State>> a=new IncompleteBuchiAutomaton<State,Transition<State>>(alphabet);
		for(int i=0; i<n;i++){
			State s=new State("s"+i);
			a.addState(s);
		}
		Iterator<State> it1=a.getStates().iterator();
		for(int i=0; i<numTransparentStates; i++){
			a.addTransparentState(it1.next());
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
		if(position==0)
		for(int i=0; i<position; i++){
		
			it.next();
		}
		return it.next();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((transparentStates == null) ? 0 : transparentStates
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncompleteBuchiAutomaton<S,T> other = (IncompleteBuchiAutomaton<S,T>) obj;
		if (transparentStates == null) {
			if (other.transparentStates != null)
				return false;
		} else if (!transparentStates.equals(other.transparentStates))
			return false;
		return true;
	}
	
	
}
