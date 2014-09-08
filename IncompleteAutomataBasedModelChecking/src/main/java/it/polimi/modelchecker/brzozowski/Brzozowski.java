package it.polimi.modelchecker.brzozowski;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EpsilonPredicate;
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
	private IntersectionAutomaton<S1, T1, S, T> a;
	
	/**
	 * creates a new Brzozowski object which is responsible to find the constraints associated with a particular (I)BA
	 * @param a is the intersection automaton to be analyzed
	 */
	public Brzozowski(IntersectionAutomaton<S1, T1, S, T> a){
		this.a=a;
	}
	
	/**
	 * returns the constraint associated with the automaton
	 * @param t: is the matrix t which describes the transition relation of the automaton
	 * @param s: is the matrix s which describes the accepting states of the automaton
	 * @return the constraint associated with the Buchi automaton
	 * @throws
	 */
	public  AbstractPredicate<S1> getConstraints(AbstractPredicate<S1> [][] t, AbstractPredicate<S1> [] s) {
		
		for(int n=s.length-1; n>=0; n--)
		{
			
			
			s[n]= t[n][n].star().concatenate(s[n]);
			if(s[n]==null){
				throw new IllegalArgumentException("The vector s cannot contain null elements (s["+n+"])");
			}	
			
			for(int j=0;j<=n;j++)
			{
				
				t[n][j]=t[n][n].star().concatenate(t[n][j]); 
				if(t[n][j]==null){
					throw new IllegalArgumentException("The matrix t cannot contain null elements (t["+n+"]["+j+"])");
				}
			}
			
			for(int i=0;i<=n-1;i++)
			{
				
				s[i] = s[i].union(t[i][n].concatenate(s[n]));
				if(s[i]==null){
					throw new IllegalArgumentException("The matrix t cannot contain null elements (t["+i+"]["+n+"])");
				}
				for(int j=0;j<=n-1; j++)
				{ 
					
					t[i][j]  =t[i][j].union(t[i][n].concatenate(t[n][j]));
					if(t[i][j]==null){
						throw new IllegalArgumentException("The matrix t cannot contain null elements (t["+i+"]["+j+"])");
					}
				}
			}
		}
		return s[0];
	}
	/**
	 * returns the matrix associated with the Buchi automaton a
	 * @param a is the intersection automaton to be converted into a matrix t
	 * @param statesOrdered is an ordered version of the states in a
	 * @return the matrix that represents the automaton a
	 */
	public AbstractPredicate<S1> [][] getConstraintT(S[] statesOrdered){
		@SuppressWarnings("unchecked")
		AbstractPredicate<S1> [][] A=new AbstractPredicate[statesOrdered.length][statesOrdered.length];
		for(int i=0; i< statesOrdered.length; i++){
			for(int j=0; j< statesOrdered.length; j++){
				boolean setted=false;
				if(i!=statesOrdered.length-1){
					for(T t: a.getTransitionsWithSource(statesOrdered[i])){
						if(t.getDestination().equals(statesOrdered[j])){
							if(statesOrdered[i].getS1().equals(statesOrdered[j].getS1()) && a.getA1().isTransparent(statesOrdered[j].getS1())){
								if(!setted){
									A[i][j]=new Predicate<S1>(statesOrdered[i].getS1(),t.getCharacter()+"");
								}
								else{
									A[i][j]=A[i][j].union(new Predicate<S1>(statesOrdered[i].getS1(),t.getCharacter()+""));
								}
								
							}
							else{
								if(a.getA1().isTransparent(statesOrdered[i].getS1())){
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
}

