package it.polimi.modelchecker.brzozowski;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.brzozowski.propositions.states.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author claudiomenghi
 * implements the modified Brzozowski algorithm, given a possibly empty {@link IntBAImpl} computes the corresponding
 * {@link Constraint}
 * 
 * @param <STATE> the type of the {@link State}s of the original BA, (I)BA
 * @param <TRANSITION> the type of the {@link LabelledTransition}s of the original BA, (I)BA
 * @param <INTERSECTIONSTATE> the type of the {@link State}s  of the intersection automaton
 * @param <INTERSECTIONTRANSITION> the type of the {@link LabelledTransition}s of the intersection automaton
 */
public class Brzozowski<
	STATE extends State, 
	TRANSITION extends LabelledTransition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	INTERSECTIONTRANSITIONFACTORY  extends ConstrainedTransitionFactory<STATE,INTERSECTIONTRANSITION>> {

	/**
	 * contains the {@link IntBAImpl} to be analyzed
	 */
	private final DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> a;
	
	private final INTERSECTIONSTATE[] orderedStates;
	
	private final AbstractProposition<STATE>[][] constraintmatrix;
	
	
	/**
	 * creates a new modified Brzozowski solved which is responsible for finding the {@link Constraint} associated with a particular {@link IntBAImpl}
	 * @param a is the {@link IntBAImpl} to be analyzed
	 * @throws IllegalArgumentException is generated if the {@link IntBAImpl} a is null
	 */
	public Brzozowski(DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> a){
		if(a==null){
			throw new IllegalArgumentException("The intersection automaton to be analyzed cannot be null");
		}
		this.a=a;
		this.orderedStates=(INTERSECTIONSTATE[]) a.getVertices().toArray(new IntersectionState[a.getVertexCount()]);
		this.constraintmatrix=this.getConstraintT();
	}
	
	private void setInit(INTERSECTIONSTATE init){
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
	public Constraint<STATE> getConstraint(){
		
		// contains the predicates that will be inserted in the final constraint
		AbstractProposition<STATE> ret=new EmptyProposition<STATE>();
		
		Set<AbstractProposition<STATE>> predicates=new HashSet<AbstractProposition<STATE>>();
		
		// for each accepting states
		for(INTERSECTIONSTATE accept: a.getAcceptStates()){
			
			// each initial state is analyzed
			for(INTERSECTIONSTATE init: a.getInitialStates()){
				
				// 	the language (constraint) associated with the initial state is concatenated with the language associated
				// with the accepting state to the omega
				// the matrixes t and s are computed
				this.setInit(init);
				AbstractProposition<STATE>[][] t=this.getConstraintT();
				AbstractProposition<STATE>[] constr1=this.getConstrainedS(accept);
				
				// the system of equations described by the matrixes t and s is solved
				this.solveSystem(t, constr1);
				
				this.setInit(accept);
				AbstractProposition<STATE>[][] t2=this.getConstraintT();
				AbstractProposition<STATE>[] constr2=this.getConstrainedS(accept);
				
				// the system of equations described by the matrixes t and s is solved
				this.solveSystem(t2, constr2);
				
				AbstractProposition<STATE> newconstraint=constr1[0]
						.concatenate(constr2[0].omega());
				
				// the language (is added to the set of predicates that will generate the final constraint)
				//if(!predicates.contains(newconstraint)){
					//predicates.add(newconstraint);
					ret=ret.union(newconstraint);
				//}
			}
		}
		// creates the final constraint to be returned
		return new Constraint<STATE>(ret);
	}
	
	/**
	 * returns the {@link Constraint} associated with the {@link IntBAImpl}
	 * @param t: is the matrix t which describes the transition relation of the {@link IntBAImpl}
	 * @param s: is the matrix s which describes the accepting states of the {@link IntBAImpl}
	 * @return the constraint associated with the {@link IntBAImpl}
	 * @throws IllegalArgumentException if the matrix t or s is null
	 */
	protected  void solveSystem(AbstractProposition<STATE>[][] t, AbstractProposition<STATE>[] s) {
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
	protected AbstractProposition<STATE>[][]  getConstraintT(){
		
		AbstractProposition<STATE>[][]  ret=new AbstractProposition[a.getVertexCount()][a.getVertexCount()];
		int i=0;
		for(INTERSECTIONSTATE s1: this.orderedStates){
			int j=0;
			for(INTERSECTIONSTATE s2: this.orderedStates){
				boolean setted=false;
				for(INTERSECTIONTRANSITION t: a.getOutEdges(s1)){
					if(a.getDest(t).equals(s2)){
						// if the first state of s1 does not change and the state is transparent
						if(t.getConstrainedState()!=null){
							if(!setted){
								ret[i][j]=new AtomicProposition<STATE>(s1.getS1(),t.getDnfFormula().toString()+"");
							}
							else{
								ret[i][j]=ret[i][j].union(new AtomicProposition<STATE>(s1.getS1(),"("+t.getDnfFormula().toString()+")"));
							}
						}
						else{
							if(a.isMixed(s1)){
								if(!setted){
									ret[i][j]=new AtomicProposition<STATE>(s1.getS1(), "λ");
								}
								else{
									ret[i][j]=ret[i][j].union(new AtomicProposition<STATE>(s1.getS1(), "λ"));
								}
							}
							else{
								if(!setted){
									ret[i][j]=new EpsilonProposition<STATE>();
								}
								else{
									ret[i][j]=ret[i][j].union(new EpsilonProposition<STATE>());
								}
							}
						}
						setted=true;
					}	
				}
				if(!setted){
					ret[i][j]=new EmptyProposition<STATE>();
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
	protected AbstractProposition<STATE>[] getConstrainedS(INTERSECTIONSTATE accept){
		if(accept==null){
			throw new IllegalArgumentException("The accepting state cannot be null");
		}
		if(!a.isAccept(accept)){
			throw new IllegalArgumentException("The state "+accept.getName()+" must be accepting");
		}
		AbstractProposition<STATE>[] ret=new AbstractProposition[a.getVertexCount()];
		
		int i=0;
		// for each state in the stateOrdered vector
		for(INTERSECTIONSTATE s: this.orderedStates){
			
			// if the state is equal to the state accept
			if(accept.equals(s)){
				// I add the lambda predicate in the s[i] cell of the vector
				ret[i]=new LambdaProposition<STATE>();
			}
			else{
				// I add the empty predicate in the s[i] cell of the vector
				ret[i]=new EmptyProposition<STATE>();
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
				if(!this.constraintmatrix[i][j].equals(new EmptyProposition<INTERSECTIONSTATE>())){
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
				ret+="+"+new LambdaProposition<INTERSECTIONSTATE>().toString();
			}
			ret+="\n";
		}
		return ret;
	}
}

