package it.polimi.modelchecker.brzozowski;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.intersection.ConstrainedTransition;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.model.graph.State;
import it.polimi.modelchecker.brzozowski.propositions.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.state.AtomicProposition;

/**
 * @author claudiomenghi
 * implements the modified Brzozowski algorithm, given a possibly empty {@link IntersectionAutomaton} computes the corresponding
 * {@link Constraint}
 * 
 * @param <S1> the type of the {@link State}s of the original BA, (I)BA
 * @param <T1> the type of the {@link LabelledTransition}s of the original BA, (I)BA
 * @param <S> the type of the {@link State}s  of the intersection automaton
 * @param <T> the type of the {@link LabelledTransition}s of the intersection automaton
 */
public class Brzozowski<S1 extends State, T1 extends LabelledTransition<S1>,S extends IntersectionState<S1>, T 
extends LabelledTransition<S>> {

	/**
	 * contains the {@link IntersectionAutomaton} to be analyzed
	 */
	private final IntersectionAutomaton<S1, T1, S, T> a;
	
	/**
	 * creates a new modified Brzozowski solved which is responsible for finding the {@link Constraint} associated with a particular {@link IntersectionAutomaton}
	 * @param a is the {@link IntersectionAutomaton} to be analyzed
	 * @throws IllegalArgumentException is generated if the {@link IntersectionAutomaton} a is null
	 */
	public Brzozowski(IntersectionAutomaton<S1, T1, S, T> a){
		if(a==null){
			throw new IllegalArgumentException("The intersection automaton to be analyzed cannot be null");
		}
		this.a=a;
	}
	/**
	 * returns the {@link Constraint} associated with the {@link IntersectionAutomaton} a
	 * @return the {@link Constraint} associated with the {@link IntersectionAutomaton} a
	 */
	public Constraint<S1> getConstraint(){
		
		// contains the predicates that will be inserted in the final constraint
		AbstractProposition<S1> ret=new EmptyProposition<S1>();
		
		Set<AbstractProposition<S1>> predicates=new HashSet<AbstractProposition<S1>>();
		
		// for each accepting states
		for(S accept: a.getAcceptStates()){
			
			//System.out.println("computing the matrixes");
			
			// the matrixes t and s are computed
			AbstractProposition<S1>[][] t=this.getConstraintT();
			AbstractProposition<S1>[] s=this.getConstrainedS(accept);
			
			// the system of equations described by the matrixes t and s is solved
			//System.out.println("solving the system");
			this.solveSystem(t, s);
			//System.out.println("system solved");
			
			// each initial state is analyzed
			for(S init: a.getInitialStates()){
				
				//System.out.println("analyzing the initial state: computing the constraint");
				// 	the language (constraint) associated with the initial state is concatenated with the language associated
				// with the accepting state to the omega
				AbstractProposition<S1> newconstraint=s[a.statePosition(init)].omega();
				
				//System.out.println("updating ret");
				// the language (is added to the set of predicates that will generate the final constraint)
				//if(!predicates.contains(newconstraint)){
					//predicates.add(newconstraint);
					ret=ret.union(newconstraint);
				//}
			}
		}
		//System.out.println("returnig the constraints");
		// creates the final constraint to be returned
		return new Constraint<S1>(ret);
	}
	
	/**
	 * returns the {@link Constraint} associated with the {@link IntersectionAutomaton}
	 * @param t: is the matrix t which describes the transition relation of the {@link IntersectionAutomaton}
	 * @param s: is the matrix s which describes the accepting states of the {@link IntersectionAutomaton}
	 * @return the constraint associated with the {@link IntersectionAutomaton}
	 * @throws IllegalArgumentException if the matrix t or s is null
	 */
	protected  void solveSystem(AbstractProposition<S1>[][] t, AbstractProposition<S1>[] s) {
		if(t==null){
			throw new IllegalArgumentException("The matrix t cannot be null");
		}
		if(s==null){
			throw new IllegalArgumentException("The vector s cannot be null");
		}
		
		int m=a.getStates().size();
		for(int n=m-1; n>=0; n--){
			s[n]=t[n][n].star().concatenate(s[n]);
			for(int j=0; j<n;j++){
				t[n][j]=t[n][n].star().concatenate(t[n][j]);
			}
			for(int i=0;i<n;i++){
				s[i]=s[i].union(t[i][n].concatenate(s[n]));
				for(int j=0; j<n; j++){
					t[i][j]=t[i][j].union(t[i][n].concatenate(t[n][j]));
				}
			}
		}
}
	/**
	 * returns the matrix associated with the {@link IntersectionAutomaton} a
	 * @param a is the {@link IntersectionAutomaton} to be converted into a matrix t
	 * @param statesOrdered is an ordered version of the {@link State}s in a
	 * @return the matrix that represents the {@link IntersectionAutomaton} a
	 * @throws IllegalArgumentException when the array of the states ordered is null
	 */
	protected AbstractProposition<S1>[][]  getConstraintT(){
		
		AbstractProposition<S1>[][]  ret=new AbstractProposition[a.getStates().size()][a.getStates().size()];
		int i=0;
		for(S s1: a.getStates()){
			int j=0;
			for(S s2: a.getStates()){
				boolean setted=false;
				for(T t: a.getTransitionsWithSource(s1)){
					if(t.getDestination().equals(s2)){
						// if the first state of s1 does not change and the state is transparent
						if(t instanceof ConstrainedTransition){
							if(!setted){
								ret[i][j]=new AtomicProposition<S1>(s1.getS1(),t.getCharacter()+"");
							}
							else{
								ret[i][j]=ret[i][j].union(new AtomicProposition<S1>(s1.getS1(),t.getCharacter()+""));
							}
						}
						else{
							if(a.isMixed(s1)){
								if(!setted){
									ret[i][j]=new AtomicProposition<S1>(s1.getS1(), "λ");
								}
								else{
									ret[i][j]=ret[i][j].union(new AtomicProposition<S1>(s1.getS1(), "λ"));
								}
							}
							else{
								if(!setted){
									ret[i][j]=new EpsilonProposition<S1>();
								}
								else{
									ret[i][j]=ret[i][j].union(new EpsilonProposition<S1>());
								}
							}
						}
						setted=true;
					}	
				}
				if(!setted){
					ret[i][j]=new EmptyProposition<S1>();
				}
				j++;
			}
			i++;
		}
		return ret;
	}
	/**
	 * returns the matrix S associated with the {@link IntersectionAutomaton} a
	 * @param accept is the accepting states of the {@link IntersectionAutomaton} considered
	 * @param statesOrdered contains the states of the {@link IntersectionAutomaton} a ordered
	 * @return the matrix S associated with the {@link IntersectionAutomaton} a
	 * @throws IllegalArgumentException when the accepting state or the ordered set of states is null
	 * if the array of the ordered states does not contains all the states of the automaton and vice-versa
	 * if the state accept is not in the set of accepting states of the {@link IntersectionAutomaton}
	 */
	protected AbstractProposition<S1>[] getConstrainedS(S accept){
		if(accept==null){
			throw new IllegalArgumentException("The accepting state cannot be null");
		}
		if(!a.isAccept(accept)){
			throw new IllegalArgumentException("The state "+accept.getName()+" must be accepting");
		}
		AbstractProposition<S1>[] ret=new AbstractProposition[a.getStates().size()];
		
		int i=0;
		// for each state in the stateOrdered vector
		for(S s: a.getStates()){
			
			// if the state is equal to the state accept
			if(accept.equals(s)){
				// I add the lambda predicate in the s[i] cell of the vector
				ret[i]=new LambdaProposition<S1>();
			}
			else{
				// I add the empty predicate in the s[i] cell of the vector
				ret[i]=new EmptyProposition<S1>();
			}
			i++;
		}
		// returns the vector s
		return ret;
	}
}

