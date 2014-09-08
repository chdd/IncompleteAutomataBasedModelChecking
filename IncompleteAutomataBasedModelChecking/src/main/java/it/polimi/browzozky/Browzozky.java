package it.polimi.browzozky;

import it.polimi.browzozky.predicates.AbstractConstraint;
import it.polimi.model.State;

/**
 * @author Claudio Menghi
 * contains a not incomplete automaton
 */
public class Browzozky {

		public static<S extends State> AbstractConstraint<S> getConstraints(AbstractConstraint<S> [][] t, AbstractConstraint<S> [] s) {
			
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

