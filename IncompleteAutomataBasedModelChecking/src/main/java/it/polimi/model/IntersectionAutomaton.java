package it.polimi.model;

import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * contains an intersection automaton
 * @author claudiomenghi
 *
 * @param <S1> is the type of the states of the original Buchi automata
 * @param <T1> is the type of the transitions of the original Buchi automata
 * @param <S> is the type of the state in the intersection automaton
 * @param <T> is the type of the transition in the intersection automaton
 */
@XmlRootElement
public class IntersectionAutomaton<S1 extends State, T1 extends Transition<S1>,S extends IntersectionState<S1>, T 
extends Transition<S>> extends IncompleteBuchiAutomaton<S, T> {

	private IncompleteBuchiAutomaton<S1, T1> model;
	@SuppressWarnings("unused")
	private BuchiAutomaton<S1, T1> specification;
	
	
	@XmlElementWrapper(name="mixedStates")
	@XmlElements({
	    @XmlElement(name="mixedState", type=State.class)
	  })
	@XmlIDREF
	private Set<S> mixedStates;
	
	public Set<S> getMixedStates(){
		return this.mixedStates;
	}
	
	private boolean completeEmptiness=true;
	
	/**
	 * creates a new Intersection automaton
	 */
	protected IntersectionAutomaton(){
		super();
		this.mixedStates=new HashSet<S>();
		this.specification=new BuchiAutomaton<S1, T1>();
	}
	/**
	 * creates a new Intersection automaton
	 * @param alphabet is the alphabet of the automaton
	 * @param model is the model to be considered
	 * @param specification is the specification under analysis
	 * @throws IllegalArgumentException when the alphabet, the model of the specification is null
	 */
	public IntersectionAutomaton(Set<String> alphabet, IncompleteBuchiAutomaton<S1, T1> model, BuchiAutomaton<S1, T1> specification) {
		super(alphabet);
		if(model==null){
			throw new IllegalArgumentException("The model to be considered cannot be null");
		}
		if(specification==null){
			throw new IllegalArgumentException("The specification cannot be null");
		}
		this.model=model;
		this.specification=specification;
		mixedStates=new HashSet<S>();
	}
	
	public void addMixedState(S s){
		this.mixedStates.add(s);
		super.addState(s);
	}
	public boolean isMixed(S s){
		return this.mixedStates.contains(s);
	}
	
	@SuppressWarnings("unchecked")
	public S[] getOrderedStates(S init, S end){
		int size=this.getStates().size();
		S[] ret=(S[]) new IntersectionState[size];
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
	public AbstractPredicate<S1> getConstraint(){
		
		AbstractPredicate<S1> ret=new EmptyPredicate<S1>();
		Brzozowski<S1,T1,S,T> b=new Brzozowski<S1,T1,S,T>(this);
		
		for(S init: this.getInitialStates()){
			for(S accept: this.getAcceptStates()){
				
				
				S[] statesOrdered1=this.getOrderedStates(init, accept);
				S[] statesOrdered2=this.getOrderedStates(accept, accept);
				AbstractPredicate<S1>[][] cnsT1=b.getConstraintT(statesOrdered1);
				AbstractPredicate<S1>[] cnsS1=b.getConstraintS(accept, statesOrdered1);
				AbstractPredicate<S1>[][] cnsT2=b.getConstraintT(statesOrdered2);
				AbstractPredicate<S1>[] cnsS2=b.getConstraintS(accept, statesOrdered2);
				
				
				AbstractPredicate<S1> newconstraint=b.getConstraints(
						cnsT1, 
						cnsS1).
						concatenate(
						b.getConstraints(
								cnsT2,
								cnsS2
								).omega());
				ret=ret.union(newconstraint);
			}
		}
		return ret;
	}
	
	/** 
	 * returns true if the complete version (without mixed states) of the intersection automaton is  empty
	 * @return true if the complete version (without mixed states) of the intersection automaton is  empty
	 */
	public boolean isEmpty(){
		this.completeEmptiness=true;
		boolean res=this.isEmpty();
		this.completeEmptiness=false;
		return res;
	}
	
	/**
	 * returns true if an accepting path is found
	 * @param visitedStates contains the set of the visited states during the first DFS
	 * @param currState is the state that is currently analyzed
	 * @param statesOfThePath contains the states of the path that is currently analyzed
	 * @return true if an accepting path is found, false otherwise
	 */
	public boolean firstDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath){
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
	public boolean secondDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath){
		if(this.isMixed(currState) && completeEmptiness){
			return false;
		}
		else{
			return super.secondDFS(visitedStates, currState, statesOfThePath);
		}
	}

	
	public static<S1 extends State, T1 extends Transition<S1>, S extends IntersectionState<S1>, T extends Transition<S>> IntersectionAutomaton<S1, T1, S, T> loadIntAutomaton(String filePath) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		
		IncompleteBuchiAutomaton.validate(filePath);
        
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(IncompleteBuchiAutomaton.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		@SuppressWarnings("unchecked")
		IntersectionAutomaton<S1,T1, S, T> automaton = (IntersectionAutomaton<S1,T1, S, T>) jaxbUnmarshaller.unmarshal(file);
		return automaton;
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
	 * @return the model that generates the intersection automaton
	 */
	public IncompleteBuchiAutomaton<S1, T1> getModel() {
		return model;
	}

	
	
	
}