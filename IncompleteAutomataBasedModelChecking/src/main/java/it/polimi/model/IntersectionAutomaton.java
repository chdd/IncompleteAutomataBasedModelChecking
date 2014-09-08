package it.polimi.model;

import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EpsilonPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

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

	private IncompleteBuchiAutomaton<S1, T1> a1;
	@SuppressWarnings("unused")
	private BuchiAutomaton<S1, T1> a2;
	@XmlElementWrapper(name="mixedStates")
	@XmlElements({
	    @XmlElement(name="mixedState", type=State.class)
	  })
	@XmlIDREF
	private Set<S> mixedStates;
	
	public Set<S> getMixedStates(){
		return this.mixedStates;
	}
	
	/**
	 * creates a new Intersection automaton
	 */
	protected IntersectionAutomaton(){
		super();
		this.mixedStates=new HashSet<S>();
		this.a2=new BuchiAutomaton<S1, T1>();
	}
	
	public IntersectionAutomaton(Set<String> alphabet, IncompleteBuchiAutomaton<S1, T1> a1, BuchiAutomaton<S1, T1> a2) {
		super(alphabet);
		this.a1=a1;
		this.a2=a2;
		mixedStates=new HashSet<S>();
		
	}
	
	public void addMixedState(S s){
		this.mixedStates.add(s);
		super.addState(s);
	}
	public boolean isMixed(S s){
		return this.mixedStates.contains(s);
	}
	
	
	
	protected AbstractPredicate<S1> [] getConstraintS(S init, S accept, S[] statesOrdered){
		@SuppressWarnings("unchecked")
		AbstractPredicate<S1>[] s=new AbstractPredicate[statesOrdered.length];
		
		for(int i=0; i< statesOrdered.length; i++){
			
			if(accept.equals(statesOrdered[i])){
				s[i]=new LambdaPredicate<S1>();
			}
			else{
				s[i]=new EmptyPredicate<S1>();
			}
		}

		return s;
	}
	
	
	protected AbstractPredicate<S1> [][] getConstraintT(S init, S accept, S[] statesOrdered){
		@SuppressWarnings("unchecked")
		AbstractPredicate<S1> [][] A=new AbstractPredicate[statesOrdered.length][statesOrdered.length];
		for(int i=0; i< statesOrdered.length; i++){
			for(int j=0; j< statesOrdered.length; j++){
				boolean setted=false;
				if(i!=statesOrdered.length-1){
					for(T t: this.getTransitionsWithSource(statesOrdered[i])){
						if(t.getDestination().equals(statesOrdered[j])){
							if(statesOrdered[i].getS1().equals(statesOrdered[j].getS1()) && a1.isTransparent(statesOrdered[j].getS1())){
								if(!setted){
									A[i][j]=new Predicate<S1>(statesOrdered[i].getS1(),t.getCharacter()+"");
								}
								else{
									A[i][j]=A[i][j].union(new Predicate<S1>(statesOrdered[i].getS1(),t.getCharacter()+""));
								}
								
							}
							else{
								if(a1.isTransparent(statesOrdered[i].getS1())){
									if(!setted){
										A[i][j]=new Predicate<S1>(statesOrdered[i].getS1(), "ε");
									}
									else{
										A[i][j]=A[i][j].union(new Predicate<S1>(statesOrdered[i].getS1(), "ε"));
									}
								}
								else{
									if(!setted){
										A[i][j]=new EpsilonPredicate<S1>();
									}
									else{
										A[i][j]=A[i][j].union(new EpsilonPredicate<S1>());
									}
										
								}
							}
							setted=true;
							
						}
					}
				}
				if(!setted){
					A[i][j]=new EmptyPredicate<S1>();
				}
			}
		}
		return A;
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
		
		for(S init: this.getInitialStates()){
			for(S accept: this.getAcceptStates()){
				
				
				S[] statesOrdered1=this.getOrderedStates(init, accept);
				S[] statesOrdered2=this.getOrderedStates(accept, accept);
				AbstractPredicate<S1>[][] cnsT1=this.getConstraintT(init, accept, statesOrdered1);
				AbstractPredicate<S1>[] cnsS1=this.getConstraintS(init, accept, statesOrdered1);
				AbstractPredicate<S1>[][] cnsT2=this.getConstraintT(accept, accept, statesOrdered2);
				AbstractPredicate<S1>[] cnsS2=this.getConstraintS(accept, accept, statesOrdered2);
				
				
				Brzozowski<S1> b=new Brzozowski<S1>();
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
	
	
	public boolean isNotEmpty(){
		boolean res=false;
		Set<S> visitedStates=new HashSet<S>();
		for(S init: this.getInitialStates()){
			if(firstDFS(visitedStates, init, new Stack<S>())){
				return true;
			}
		}
		return res;
	}
	/**
	 * returns true if an accepting path is found
	 * @param visitedStates
	 * @param currState
	 * @return
	 */
	public boolean firstDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath){
		if(visitedStates.contains(currState) || this.isMixed(currState)){
			return false;
		}
		else{
			visitedStates.add(currState);
			statesOfThePath.push(currState);
			if(this.isAccept(currState)){
				if(this.secondDFS(new HashSet<S>(), currState, statesOfThePath)){
					return true;
				}
			}
			for(T t: this.getTransitionsWithSource(currState)){
				if(firstDFS(visitedStates, t.getDestination(), statesOfThePath)){
					return true;
				}
			}
			statesOfThePath.pop();
			return false;
		}
	}
	public boolean secondDFS(Set<S> visitedStates, S currState, Stack<S> statesOfThePath){
		if(visitedStates.contains(currState) || this.isMixed(currState)){
			return false;
		}
		else{
			if(statesOfThePath.contains(currState)){
				return true;
			}
			else{
				visitedStates.add(currState);
				statesOfThePath.push(currState);
				for(T t: this.getTransitionsWithSource(currState)){
					if(secondDFS(visitedStates, t.getDestination(), statesOfThePath)){
						return true;
					}
				}
				statesOfThePath.pop();
				return false;
			}
		}
	}

	
	
	public void printStatesOrdered(S[] states){
		String toPrint="";
		for(int i=0; i<=states.length-1 ;i++){
			toPrint+=states[i]+"\t";
		}
		System.out.println(toPrint);
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

	
	
}