package it.polimi.modelchecker.brzozowski;

import it.polimi.model.State;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;

/**
 * @author claudiomenghi
 * contains the Brzozowski algorithm
 */
public class Brzozowski<S extends State> {

		/**
		 * creates a new Brzozowski object which is responsible to find the constraints associated with a particular (I)BA
		 */
		public Brzozowski(){
			
		}
		
		/**
		 * returns the constraint associated with the automaton
		 * @param t: is the matrix t which describes the transition relation of the automaton
		 * @param s: is the matrix s which describes the accepting states of the automaton
		 * @return the constraint associated with the Buchi automaton
		 */
		public  AbstractPredicate<S> getConstraints(AbstractPredicate<S> [][] t, AbstractPredicate<S> [] s) {
			
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
}

