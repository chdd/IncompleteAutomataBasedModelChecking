package it.polimi.model.impl.automata;


import it.polimi.model.impl.labeling.ConjunctiveClause;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author claudiomenghi
 * contains a possibly incomplete Buchi automaton which extends classical automaton with transparent states
 * @param <STATE> contains the type of the states of the automaton
 * @param <TRANSITION> contains the type of the transitions of the automaton
 */
@SuppressWarnings("serial")
public class IBAImpl<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>> 
	extends BAImpl<STATE, TRANSITION, TRANSITIONFACTORY> implements DrawableIBA<STATE,TRANSITION, TRANSITIONFACTORY>{

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<STATE> transparentStates;
	
	/**
	 * creates a new extended automaton
	 */
	public IBAImpl(TRANSITIONFACTORY transitionFactory){
		super(transitionFactory);
		this.transparentStates=new HashSet<STATE>();
	}
	
	/**
	 * creates a new extended automaton with the specified alphabet (see {@link BAImpl})
	 * @param alphabet is the alphabet of the extended automaton
	 * @throws NullPointerException is generated if the alphabet of the automaton is null
	 */
	public IBAImpl(Set<IGraphProposition> alphabet, TRANSITIONFACTORY transitionFactory) {
		super(alphabet, transitionFactory);
		transparentStates=new HashSet<STATE>();
	}
	
	/**
	 * add a new transparent state in the automaton (the transparent state is also added to the set of the states of the automaton
	 * @param s is the state to be added and is also added to the set of the states of the automaton
	 * @throws IllegalArgumentException if the state to be added is null
	 */
	public void addTransparentState(STATE s){
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
	public boolean isTransparent(STATE s){
		if(s==null){
			throw new IllegalArgumentException("The state to be added cannot be null");
		}
		return this.transparentStates.contains(s);
	}
	
	/**
	 * returns the set of the transparent states of the automaton
	 * @return the set of the transparent states of the automaton (if no transparent states are present an empty set is returned)
	 */
	public Set<STATE>  getTransparentStates(){
		return this.transparentStates;
	}
	
	/**
	 * generates a new random graph (note that almost every graph is connected with the parameters n, 2ln(n)/n
	 * @param n: number of nodes
	 * @param p: probability through which each transition is included in the graph
	 * @return a new random graph
	 */
	public void getRandomAutomaton(int n, double transitionProbability, double initialStateProbability, double acceptingStateProbability, double transparentStateProbability, Set<IGraphProposition> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		this.reset();
		Random r=new Random();
		StateFactory<STATE> stateFactory=new StateFactory<STATE>();
		for(int i=0; i<n;i++){
			STATE s=stateFactory.create();
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
		for(STATE s1: this.getVertices()){
			for(STATE s2: this.getVertices()){
				double randInt=r.nextInt(11)/10.0;
				if(randInt<=transitionProbability){
					IGraphProposition character=IBAImpl.getRandomString(alphabet, r.nextInt(alphabet.size()));
					this.addTransition(s1, s2, this.transitionFactory.create(new DNFFormula(new ConjunctiveClause(character))));
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

	public void getRandomAutomaton2(int n, double transitionProbability, int numInitial, int numAccepting, int numTransparentStates, Set<IGraphProposition> alphabet){
		if(transitionProbability>=1||transitionProbability<0){
			throw new IllegalArgumentException("The value of p must be included in the trange [0,1]");
		}

		this.reset();
		this.addCharacters(alphabet);
		Random r=new Random();
		
		StateFactory<STATE> stateFactory=new StateFactory<STATE>();
		for(int i=0; i<n;i++){
			this.addVertex(stateFactory.create());
		}
		Iterator<STATE> it1=this.getVertices().iterator();
		for(int i=0; i< Math.min(numTransparentStates, this.getVertexCount()); i++){
			this.addTransparentState(it1.next());
		}
		for(int i=0; i<Math.min(numInitial, this.getVertexCount()); i++){
			int transp=r.nextInt(this.getVertices().size());
			Iterator<STATE> it=this.getVertices().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			this.addInitialState(it.next());
		}
		for(int i=0; i<numAccepting; i++){
			int transp=r.nextInt(this.getVertices().size());
			Iterator<STATE> it=this.getVertices().iterator();
			for(int j=0;j<transp;j++)
			{
				it.next();
			}
			this.addAcceptState(it.next());
		}
		for(STATE s1: this.getVertices()){
			for(STATE s2: this.getVertices()){
				double randInt=r.nextInt(11)/10.0;
				if(randInt<=transitionProbability){
					
					IGraphProposition character=IBAImpl.getRandomString(alphabet, r.nextInt(alphabet.size()));
					this.addTransition(s1, s2, this.transitionFactory.create(new DNFFormula(new ConjunctiveClause(character))));
				}
			}
		}
	}
	public static IGraphProposition getRandomString(Set<IGraphProposition> alphabet, int position){

		Iterator<IGraphProposition> it=alphabet.iterator();
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
		IBAImpl<STATE,TRANSITION, TRANSITIONFACTORY> other = (IBAImpl<STATE,TRANSITION, TRANSITIONFACTORY>) obj;
		if (transparentStates == null) {
			if (other.transparentStates != null)
				return false;
		} else if (!transparentStates.equals(other.transparentStates))
			return false;
		return true;
	}
	/**
	 * resets the set of transparent states and all the other fields of the (I)BA alphabet, transitions etc.
	 * @see it.polimi.model.impl.automata.BAImpl#reset()
	 */	
	@Override
	public void reset(){
		this.transparentStates.clear();
		super.reset();
	}
	
	
}
