package it.polimi.modelchecker.brzozowski;

import it.polimi.model.ConstrainedTransition;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Constraint;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EpsilonPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;


/**
 * @author claudiomenghi
 * implements the modified Brzozowski algorithm, given a possibly empty intersection automaton computes the corresponding
 * constraint
 * 
 * @param <S1> the type of the states of the original BA, (I)BA
 * @param <T1> the type of the transitions of the original BA, (I)BA
 * @param <S> the type of the states  of the intersection automaton
 * @param <T> the type of the transitions of the intersection automaton
 */
public class Brzozowski<S1 extends State, T1 extends Transition<S1>,S extends IntersectionState<S1>, T 
extends Transition<S>> {

	/**
	 * contains the {@link IntersectionAutomaton} to be analyzed
	 */
	private final IntersectionAutomaton<S1, T1, S, T> a;
	
	/**
	 * creates a new modified Brzozowski solved which is responsible for finding the constraints associated with a particular {@link IntersectionAutomaton}
	 * @param a is the {@link IntersectionAutomaton} to be analyzed
	 * @throws IllegalArgumentException is generated if a is null
	 */
	public Brzozowski(IntersectionAutomaton<S1, T1, S, T> a){
		if(a==null){
			throw new IllegalArgumentException("The intersection automaton to be analyzed cannot be null");
		}
		this.a=a;
	}
	/**
	 * returns the constraint associated with the intersection automaton a
	 * @return the constraint associated with the intersection automaton a
	 */
	public Constraint<S1> getConstraint(){
		
		// contains the predicates that will be inserted in the final constraint
		AbstractPredicate<S1> ret=new EmptyPredicate<S1>();
		
		// for each accepting states
		for(S accept: a.getAcceptStates()){
			
			//System.out.println("computing the matrixes");
			
			// the matrixes t and s are computed
			AbstractPredicate<S1>[][] t=this.getConstraintT();
			AbstractPredicate<S1>[] s=this.getConstrainedS(accept);
			
			// the system of equations described by the matrixes t and s is solved
			//System.out.println("solving the system");
			this.solveSystem(t, s);
			//System.out.println("system solved");
			
			// each initial state is analyzed
			for(S init: a.getInitialStates()){
				
				//System.out.println("analyzing the initial state: computing the constraint");
				// 	the language (constraint) associated with the initial state is concatenated with the language associated
				// with the accepting state to the omega
				AbstractPredicate<S1> newconstraint=s[a.statePosition(init)].concatenate(s[a.statePosition(accept)].omega());
				
				//System.out.println("updating ret");
				// the language (is added to the set of predicates that will generate the final constraint)
				ret=ret.union(newconstraint);
			}
			
			
		}
		//System.out.println("returnig the constraints");
		// creates the final constraint to be returned
		return new Constraint<S1>(ret);
	}
	
	/**
	 * returns the constraint associated with the automaton
	 * @param t: is the matrix t which describes the transition relation of the automaton
	 * @param s: is the matrix s which describes the accepting states of the automaton
	 * @return the constraint associated with the Buchi automaton
	 * @throws
	 */
	protected  void solveSystem(AbstractPredicate<S1>[][] t, AbstractPredicate<S1>[] s) {
		
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
	 * returns the matrix associated with the Buchi automaton a
	 * @param a is the intersection automaton to be converted into a matrix t
	 * @param statesOrdered is an ordered version of the states in a
	 * @return the matrix that represents the automaton a
	 * @throws IllegalArgumentException when the array of the states ordered is null
	 */
	protected AbstractPredicate<S1>[][]  getConstraintT(){
		
		AbstractPredicate<S1>[][]  ret=new AbstractPredicate[a.getStates().size()][a.getStates().size()];
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
								ret[i][j]=new Predicate<S1>(s1.getS1(),t.getCharacter()+"");
							}
							else{
								ret[i][j]=ret[i][j].union(new Predicate<S1>(s1.getS1(),t.getCharacter()+""));
							}
						}
						else{
							if(a.isMixed(s1)){
								if(!setted){
									ret[i][j]=new Predicate<S1>(s1.getS1(), "λ");
								}
								else{
									ret[i][j]=ret[i][j].union(new Predicate<S1>(s1.getS1(), "λ"));
								}
							}
							else{
								if(!setted){
									ret[i][j]=new EpsilonPredicate<S1>();
								}
								else{
									ret[i][j]=ret[i][j].union(new EpsilonPredicate<S1>());
								}
							}
						}
						setted=true;
					}	
				}
				if(!setted){
					ret[i][j]=new EmptyPredicate<S1>();
				}
				j++;
			}
			i++;
		}
		return ret;
	}
	/**
	 * returns the matrix S associated with the intersection automaton a
	 * @param accept is the accepting states of the automaton considered
	 * @param statesOrdered contains the states of a ordered
	 * @return the matrix S associated with the automaton a
	 * @throws IllegalArgumentException when the accepting state or the ordered set of states is null
	 * if the array of the ordered states does not contains all the states of the automaton and vice-versa
	 * if the state accept is not in the set of accepting states of the intersection automaton
	 */
	protected AbstractPredicate<S1>[] getConstrainedS(S accept){
		if(accept==null){
			throw new IllegalArgumentException("The accepting state cannot be null");
		}
		if(!a.isAccept(accept)){
			throw new IllegalArgumentException("The state "+accept.getName()+" must be accepting");
		}
		AbstractPredicate<S1>[] ret=new AbstractPredicate[a.getStates().size()];
		
		int i=0;
		// for each state in the stateOrdered vector
		for(S s: a.getStates()){
			
			// if the state is equal to the state accept
			if(accept.equals(s)){
				// I add the lambda predicate in the s[i] cell of the vector
				ret[i]=new LambdaPredicate<S1>();
			}
			else{
				// I add the empty predicate in the s[i] cell of the vector
				ret[i]=new EmptyPredicate<S1>();
			}
			i++;
		}
		// returns the vector s
		return ret;
	}
	
}

