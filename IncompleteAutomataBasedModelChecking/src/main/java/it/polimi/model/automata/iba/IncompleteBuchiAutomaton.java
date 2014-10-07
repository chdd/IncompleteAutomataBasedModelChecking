package it.polimi.model.automata.iba;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.state.StateFactory;
import it.polimi.model.automata.ba.transition.LabelledTransition;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @author claudiomenghi
 * contains a possibly incomplete Buchi automaton which extends classical automaton with transparent states
 * @param <S> contains the type of the states of the automaton
 * @param <T> contains the type of the transitions of the automaton
 */
@SuppressWarnings("serial")
public class IncompleteBuchiAutomaton<S extends State, T extends LabelledTransition> extends BuchiAutomaton<S, T>{

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<S> transparentStates;
	
	/**
	 * creates a new extended automaton
	 */
	public IncompleteBuchiAutomaton(){
		super();
		this.transparentStates=new HashSet<S>();
	}
	
	/**
	 * creates a new extended automaton with the specified alphabet (see {@link BuchiAutomaton})
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
		this.addVertex(s);
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
	/**
	 * returns the set of the transparent states of the automaton
	 * @return the set of the transparent states of the automaton (if no transparent states are present an empty set is returned)
	 */
	public Set<S>  getTransparentStates(){
		return this.transparentStates;
	}
	
	
	
	
	
	
/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */
	@SuppressWarnings("unchecked")
	public void getRandomAutomaton(int n, double transitionProbability, double initialStateProbability, double acceptingStateProbability, double transparentStateProbability, Set<String> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		this.reset();
		Random r=new Random();
		StateFactory<S> stateFactory=new StateFactory<S>();
		for(int i=0; i<n;i++){
			S s=stateFactory.create();
			if(r.nextInt(10)<=initialStateProbability*10){
				this.addInitialState(s);
			}
			else{
				if(r.nextInt(10)<acceptingStateProbability*10){
					this.addAcceptState(s);
				}
				else{
					this.addVertex(s);
				}
			}
			if(r.nextInt(10)<transparentStateProbability*10){
				this.addTransparentState(s);
			}
		}
		for(S s1: this.getVertices()){
			for(S s2: this.getVertices()){
				double randInt=r.nextInt(11)/10.0;
				if(randInt<=transitionProbability){
					Set<String> characters=new HashSet<String>();
					String character=IncompleteBuchiAutomaton.getRandomString(alphabet, r.nextInt(alphabet.size()));
					characters.add(character);
					this.addTransition(s1, s2, this.transitionFactory.create(characters));
				}
			}
		}
	}
	/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */

	public void getRandomAutomaton2(int n, double transitionProbability, int numInitial, int numAccepting, int numTransparentStates, Set<String> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		this.reset();
		this.addCharacters(alphabet);
		Random r=new Random();
		
		StateFactory<S> stateFactory=new StateFactory<S>();
		for(int i=0; i<n;i++){
			stateFactory.create();
		}
		Iterator<S> it1=this.getVertices().iterator();
		for(int i=0; i<numTransparentStates; i++){
			this.addTransparentState(it1.next());
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
					Set<String> characters=new HashSet<String>();
					
					String character=IncompleteBuchiAutomaton.getRandomString(alphabet, r.nextInt(alphabet.size()));
					characters.add(character);
					this.addTransition(s1, s2, this.transitionFactory.create(characters));
				}
			}
		}
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
	/**
	 * resets the set of transparent states and all the other fields of the (I)BA alphabet, transitions etc.
	 * @see it.polimi.model.automata.ba.BuchiAutomaton#reset()
	 */	
	@Override
	public void reset(){
		this.transparentStates.clear();
		super.reset();
	}
	
	
}