package it.polimi.model.impl.automata;


import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @author claudiomenghi
 * contains a possibly incomplete Buchi automaton which extends classical automaton with transparent states
 * @param <STATE> contains the type of the states of the automaton
 * @param <TRANSITION> contains the type of the transitions of the automaton
 * @param <TRANSITIONFACTORY> contains the factory which allows to create TRANSITIONs
 */
@SuppressWarnings("serial")
public class IBAImpl<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT,  TRANSITION>> 
	extends BAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> 
	implements DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>, Cloneable{

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
	public IBAImpl(Set<Proposition> alphabet, TRANSITIONFACTORY transitionFactory) {
		super(alphabet, transitionFactory);
		transparentStates=new HashSet<STATE>();
	}
	
	public void addTransparentStates(Set<STATE> setTransparentStates){
		for(STATE s: setTransparentStates){
			this.addTransparentState(s);
		}
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
	public void getRandomAutomaton(int n, double transitionProbability, double initialStateProbability, double acceptingStateProbability, double transparentStateProbability, Set<Proposition> alphabet){
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
					Proposition character=IBAImpl.getRandomString(alphabet, r.nextInt(alphabet.size()));
					this.addTransition(s1, s2, this.transitionFactory.create(new DNFFormula<CONSTRAINEDELEMENT>(new ConjunctiveClauseImpl<CONSTRAINEDELEMENT>(character))));
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

	public void getRandomAutomaton2(int n, double transitionProbability, int numInitial, int numAccepting, int numTransparentStates, Set<Proposition> alphabet){
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
					
					Proposition character=BAImpl.getRandomString(alphabet, r.nextInt(alphabet.size()));
					this.addTransition(s1, s2, this.transitionFactory.create(new DNFFormula<CONSTRAINEDELEMENT>(new ConjunctiveClauseImpl<CONSTRAINEDELEMENT>(character))));
				}
			}
		}
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
		IBAImpl<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY> other = (IBAImpl<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY>) obj;
		if (transparentStates == null) {
			if (other.transparentStates != null)
				return false;
		} else if (!transparentStates.equals(other.transparentStates))
			return false;
		return true;
	}
	
	@Override
	public IBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> clone(){
		IBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> clone=
				new IBAImpl<CONSTRAINEDELEMENT, 
				STATE, TRANSITION, TRANSITIONFACTORY>(transitionFactory);
		clone.setAlphabet(this.getAlphabet());
		clone.setStates(this.getStates());
		clone.setAcceptStates(this.getAcceptStates());
		clone.setInitialStates(this.getInitialStates());
		for(TRANSITION t: this.getEdges()){
			clone.addTransition(this.getSource(t), this.getDest(t), t);
		}
		clone.addTransparentStates(this.getTransparentStates());
		
		return clone;
	}
	
	public void addState(STATE s){
		this.addVertex(s);
	}
	public void addStates(Set<STATE> states){
		for(STATE s: states){
			this.addState(s);
		}
	}
	
	public Set<STATE> getStates(){
		return new HashSet<STATE>(this.getVertices());
	}
	
	public void replace(STATE transparentState, IBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> ibaToInject){
		if(!this.isTransparent(transparentState)){
			throw new IllegalArgumentException("The state t must be transparent");
		}
		
		IBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> ibaToInjectImpl=
				(IBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>)ibaToInject;
		
		this.addStates(ibaToInjectImpl.getStates());
		this.addTransparentStates(ibaToInjectImpl.getTransparentStates());
		if(ibaToInjectImpl.getEdges()!=null){
			for(TRANSITION transition: ibaToInjectImpl.getEdges()){
				this.addEdge(transition, ibaToInjectImpl.getSource(transition), ibaToInjectImpl.getDest(transition));
			}
		}
		if(this.getInEdges(transparentState)!=null){
			for(TRANSITION transition: this.getInEdges(transparentState)){
				
				if(this.getSource(transition).equals(transparentState)){
					for(STATE initialState: ibaToInjectImpl.getInitialStates()){
						for(STATE acceptingState: ibaToInjectImpl.getAcceptStates()){
							this.addEdge(
									this.transitionFactory.create(transition.getDnfFormula()), acceptingState, initialState);
						}
					}
				}
				else{
					for(STATE initialState: ibaToInjectImpl.getInitialStates()){
						this.addEdge(
								this.transitionFactory.create(transition.getDnfFormula()), this.getSource(transition), initialState);
					}
				}
			}
		}
		
		if(this.getOutEdges(transparentState)!=null){
			for(TRANSITION transition: this.getOutEdges(transparentState)){
				if(!this.getDest(transition).equals(transparentState)){
					for(STATE finalState: ibaToInjectImpl.getAcceptStates()){
						this.addEdge(
								this.transitionFactory.create(transition.getDnfFormula()), finalState,this.getDest(transition));
					}
				}
			}
		}
		
		if(this.isAccept(transparentState)){
			this.addAcceptStates(ibaToInjectImpl.getAcceptStates());
		}
		if(this.isInitial(transparentState)){
			this.addInitialStates(ibaToInjectImpl.getInitialStates());
		}
		this.removeVertex(transparentState);
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
