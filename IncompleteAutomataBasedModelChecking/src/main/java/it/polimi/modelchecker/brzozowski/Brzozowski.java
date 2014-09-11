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
 * contains the Brzozowski algorithm
 */
public class Brzozowski<S1 extends State, T1 extends Transition<S1>,S extends IntersectionState<S1>, T 
extends Transition<S>> {

	/**
	 * contains the intersection automaton to be analyzed
	 */
	private final IntersectionAutomaton<S1, T1, S, T> a;
	
	/**
	 * creates a new Brzozowski object which is responsible to find the constraints associated with a particular (I)BA
	 * @param a is the intersection automaton to be analyzed
	 */
	public Brzozowski(IntersectionAutomaton<S1, T1, S, T> a){
		this.a=a;
	}
	
	public Constraint<S1> getConstraint(){
		
		AbstractPredicate<S1> ret=new EmptyPredicate<S1>();
		
		for(S accept: a.getAcceptStates()){
			
			AbstractPredicate<S1>[][] cnsT1=this.getConstraintT();
			AbstractPredicate<S1>[] cnsS1=this.getConstrainedS(accept);
			
			this.getConstraints(cnsT1, cnsS1);
			System.out.println(cnsT1);
			System.out.println(cnsS1);
			
			
			for(S init: a.getInitialStates()){
				AbstractPredicate<S1> newconstraint=cnsS1[a.statePosition(init)].concatenate(cnsS1[a.statePosition(accept)].omega());
				ret=ret.union(newconstraint);
			}
			
		}
	
		return new Constraint<S1>(ret);
	}
	
	/**
	 * returns the constraint associated with the automaton
	 * @param t: is the matrix t which describes the transition relation of the automaton
	 * @param s: is the matrix s which describes the accepting states of the automaton
	 * @return the constraint associated with the Buchi automaton
	 * @throws
	 */
	protected  void getConstraints(AbstractPredicate<S1>[][] t, AbstractPredicate<S1>[] s) {
		
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

