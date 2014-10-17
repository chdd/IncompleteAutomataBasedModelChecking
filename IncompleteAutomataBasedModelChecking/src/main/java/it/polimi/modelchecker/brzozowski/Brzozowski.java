package it.polimi.modelchecker.brzozowski;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.IntBAImpl;
import it.polimi.modelchecker.brzozowski.propositions.states.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;

/**
 * @author claudiomenghi
 * implements the modified Brzozowski algorithm, given a possibly empty {@link IntBAImpl} computes the corresponding
 * {@link Constraint}
 * 
 * @param <S1> the type of the {@link State}s of the original BA, (I)BA
 * @param <T1> the type of the {@link LabelledTransition}s of the original BA, (I)BA
 * @param <S> the type of the {@link State}s  of the intersection automaton
 * @param <T> the type of the {@link LabelledTransition}s of the intersection automaton
 */
public class Brzozowski<S1 extends State, T1 extends LabelledTransition,S extends IntersectionState<S1>, T 
extends ConstrainedTransition<S1>> {

	/**
	 * contains the {@link IntBAImpl} to be analyzed
	 */
	private final IntBAImpl<S1, T1, S, T> a;
	
	private final S[] orderedStates;
	
	private final AbstractProposition<S1>[][] constraintmatrix;
	
	
	/**
	 * creates a new modified Brzozowski solved which is responsible for finding the {@link Constraint} associated with a particular {@link IntBAImpl}
	 * @param a is the {@link IntBAImpl} to be analyzed
	 * @throws IllegalArgumentException is generated if the {@link IntBAImpl} a is null
	 */
	public Brzozowski(IntBAImpl<S1, T1, S, T> a){
		if(a==null){
			throw new IllegalArgumentException("The intersection automaton to be analyzed cannot be null");
		}
		this.a=a;
		this.orderedStates=(S[]) a.getVertices().toArray(new IntersectionState[a.getVertexCount()]);
		this.constraintmatrix=this.getConstraintT();
	}
	
	private void setInit(S init){
		for(int i=0; i< orderedStates.length; i++){
			if(orderedStates[i].equals(init)){
				orderedStates[i]=orderedStates[0];
				orderedStates[0]=init;
			}
		}
	}
	
	/**
	 * returns the {@link Constraint} associated with the {@link IntBAImpl} a
	 * @return the {@link Constraint} associated with the {@link IntBAImpl} a
	 */
	public Constraint<S1> getConstraint(){
		
		// contains the predicates that will be inserted in the final constraint
		AbstractProposition<S1> ret=new EmptyProposition<S1>();
		
		Set<AbstractProposition<S1>> predicates=new HashSet<AbstractProposition<S1>>();
		
		// for each accepting states
		for(S accept: a.getAcceptStates()){
			
			// each initial state is analyzed
			for(S init: a.getInitialStates()){
				
				// 	the language (constraint) associated with the initial state is concatenated with the language associated
				// with the accepting state to the omega
				// the matrixes t and s are computed
				this.setInit(init);
				AbstractProposition<S1>[][] t=this.getConstraintT();
				AbstractProposition<S1>[] constr1=this.getConstrainedS(accept);
				
				// the system of equations described by the matrixes t and s is solved
				this.solveSystem(t, constr1);
				
				this.setInit(accept);
				AbstractProposition<S1>[][] t2=this.getConstraintT();
				AbstractProposition<S1>[] constr2=this.getConstrainedS(accept);
				
				// the system of equations described by the matrixes t and s is solved
				this.solveSystem(t2, constr2);
				
				AbstractProposition<S1> newconstraint=constr1[0]
						.concatenate(constr2[0].omega());
				
				// the language (is added to the set of predicates that will generate the final constraint)
				//if(!predicates.contains(newconstraint)){
					//predicates.add(newconstraint);
					ret=ret.union(newconstraint);
				//}
			}
		}
		// creates the final constraint to be returned
		return new Constraint<S1>(ret);
	}
	
	/**
	 * returns the {@link Constraint} associated with the {@link IntBAImpl}
	 * @param t: is the matrix t which describes the transition relation of the {@link IntBAImpl}
	 * @param s: is the matrix s which describes the accepting states of the {@link IntBAImpl}
	 * @return the constraint associated with the {@link IntBAImpl}
	 * @throws IllegalArgumentException if the matrix t or s is null
	 */
	protected  void solveSystem(AbstractProposition<S1>[][] t, AbstractProposition<S1>[] s) {
		if(t==null){
			throw new IllegalArgumentException("The matrix t cannot be null");
		}
		if(s==null){
			throw new IllegalArgumentException("The vector s cannot be null");
		}
		
		int m=a.getVertexCount();
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
	 * returns the matrix associated with the {@link IntBAImpl} a
	 * @param a is the {@link IntBAImpl} to be converted into a matrix t
	 * @param statesOrdered is an ordered version of the {@link State}s in a
	 * @return the matrix that represents the {@link IntBAImpl} a
	 * @throws IllegalArgumentException when the array of the states ordered is null
	 */
	protected AbstractProposition<S1>[][]  getConstraintT(){
		
		AbstractProposition<S1>[][]  ret=new AbstractProposition[a.getVertexCount()][a.getVertexCount()];
		int i=0;
		for(S s1: this.orderedStates){
			int j=0;
			for(S s2: this.orderedStates){
				boolean setted=false;
				for(T t: a.getOutEdges(s1)){
					if(a.getDest(t).equals(s2)){
						// if the first state of s1 does not change and the state is transparent
						if(t.getConstrainedState()!=null){
							if(!setted){
								ret[i][j]=new AtomicProposition<S1>(s1.getS1(),t.getDnfFormula().toString()+"");
							}
							else{
								ret[i][j]=ret[i][j].union(new AtomicProposition<S1>(s1.getS1(),"("+t.getDnfFormula().toString()+")"));
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
	 * returns the matrix S associated with the {@link IntBAImpl} a
	 * @param accept is the accepting states of the {@link IntBAImpl} considered
	 * @param statesOrdered contains the states of the {@link IntBAImpl} a ordered
	 * @return the matrix S associated with the {@link IntBAImpl} a
	 * @throws IllegalArgumentException when the accepting state or the ordered set of states is null
	 * if the array of the ordered states does not contains all the states of the automaton and vice-versa
	 * if the state accept is not in the set of accepting states of the {@link IntBAImpl}
	 */
	protected AbstractProposition<S1>[] getConstrainedS(S accept){
		if(accept==null){
			throw new IllegalArgumentException("The accepting state cannot be null");
		}
		if(!a.isAccept(accept)){
			throw new IllegalArgumentException("The state "+accept.getName()+" must be accepting");
		}
		AbstractProposition<S1>[] ret=new AbstractProposition[a.getVertexCount()];
		
		int i=0;
		// for each state in the stateOrdered vector
		for(S s: this.orderedStates){
			
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
	/**
	 * @return the constraintmatrix
	 */
	public String getConstraintmatrix() {
		String ret="";
		for(int i=0; i< this.orderedStates.length; i++){
			ret+=this.orderedStates[i].getId()+"=";
			boolean first=true;
			for(int j=0; j< this.orderedStates.length; j++){
				if(!this.constraintmatrix[i][j].equals(new EmptyProposition<S>())){
					if(first){
						ret+=this.constraintmatrix[i][j].toString()+""+this.orderedStates[j].getId();
						first=false;
					}
					else{
						ret+=" + "+this.constraintmatrix[i][j].toString()+""+this.orderedStates[j].getId();
					}
				}
			}
			if(a.isAccept(this.orderedStates[i])){
				ret+="+"+new LambdaProposition<S>().toString();
			}
			ret+="\n";
		}
		return ret;
	}
}

