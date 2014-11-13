package it.polimi.modelchecker.brzozowski;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.brzozowski.propositions.states.AbstractProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;
import it.polimi.modelchecker.brzozowski.transformers.AcceptingStatesTransformer;
import it.polimi.modelchecker.brzozowski.transformers.TransitionMatrixTranformer;

import java.util.ArrayList;
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
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	INTERSECTIONTRANSITIONFACTORY  extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT,INTERSECTIONTRANSITION>> {

	/**
	 * contains the {@link IntBAImpl} to be analyzed
	 */
	private final DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersectionAutomaton;
	
	private final ArrayList<INTERSECTIONSTATE> orderedStates;
	
	private final LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][] constraintmatrix;
	
	
	/**
	 * creates a new modified Brzozowski solved which is responsible for finding the {@link Constraint} associated with a particular {@link IntBAImpl}
	 * @param intersectionAutomaton is the {@link IntBAImpl} to be analyzed
	 * @throws IllegalArgumentException is generated if the {@link IntBAImpl} a is null
	 */
	public Brzozowski(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersectionAutomaton){
		if(intersectionAutomaton==null){
			throw new IllegalArgumentException("The intersection automaton to be analyzed cannot be null");
		}
		this.intersectionAutomaton=intersectionAutomaton;
		this.orderedStates=new ArrayList<INTERSECTIONSTATE>(intersectionAutomaton.getVertices());
				
		this.constraintmatrix=this.getConstraintT();
	}
	
	private void setInit(INTERSECTIONSTATE init){
		
		this.orderedStates.set(this.orderedStates.indexOf(init), this.orderedStates.get(0));
		this.orderedStates.set(0, init);
	}
	
	/**
	 * returns the {@link Constraint} associated with the {@link IntBAImpl} a
	 * @return the {@link Constraint} associated with the {@link IntBAImpl} a
	 */
	public Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> getConstraint(){
		
		// contains the predicates that will be inserted in the final constraint
		LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> ret=new EmptyProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>();
		
		Set<AbstractProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> predicates=new HashSet<AbstractProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>();
		
		// for each accepting states
		for(INTERSECTIONSTATE accept: intersectionAutomaton.getAcceptStates()){
			
			// each initial state is analyzed
			for(INTERSECTIONSTATE init: intersectionAutomaton.getInitialStates()){
				
				// 	the language (constraint) associated with the initial state is concatenated with the language associated
				// with the accepting state to the omega
				// the matrixes t and s are computed
				this.setInit(init);
				LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][] t=this.getConstraintT();
				LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] constr1=this.getConstrainedS(accept);
				
				// the system of equations described by the matrixes t and s is solved
				this.solveSystem(t, constr1);
				
				this.setInit(accept);
				LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][] t2=this.getConstraintT();
				LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] constr2=this.getConstrainedS(accept);
				
				// the system of equations described by the matrixes t and s is solved
				this.solveSystem(t2, constr2);
				
				LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> newconstraint=constr1[0]
						.concatenate(constr2[0].omega());
				
				ret=ret.union(newconstraint);
			}
		}
		// creates the final constraint to be returned
		return new Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(ret);
	}
	
	/**
	 * returns the {@link Constraint} associated with the {@link IntBAImpl}
	 * @param t: is the matrix t which describes the transition relation of the {@link IntBAImpl}
	 * @param s: is the matrix s which describes the accepting states of the {@link IntBAImpl}
	 * @return the constraint associated with the {@link IntBAImpl}
	 * @throws NullPointerException if the matrix t or s is null
	 */
	protected  void solveSystem(LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][] t, LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] s) {
		if(t==null){
			throw new NullPointerException("The matrix t cannot be null");
		}
		if(s==null){
			throw new NullPointerException("The vector s cannot be null");
		}
		
		int m=intersectionAutomaton.getVertexCount();
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
			for(int i=0;i<n;i++){
				t[i][n]=new EmptyProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>();
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
	protected LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][]  getConstraintT(){
		
		return new TransitionMatrixTranformer<
				CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION,
				TRANSITIONFACTORY,
				INTERSECTIONSTATE, 
				INTERSECTIONTRANSITION,
				INTERSECTIONTRANSITIONFACTORY> 
				(this.orderedStates).transform(this.intersectionAutomaton);
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
	protected LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] getConstrainedS(INTERSECTIONSTATE accept){
		
		return new AcceptingStatesTransformer<
				CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION,
				TRANSITIONFACTORY,
				INTERSECTIONSTATE, 
				INTERSECTIONTRANSITION,
				INTERSECTIONTRANSITIONFACTORY>(orderedStates, intersectionAutomaton, accept).transform(intersectionAutomaton);
	}
	/**
	 * @return the constraintmatrix
	 */
	public String getConstraintmatrix() {
		String ret="";
		for(int i=0; i< this.orderedStates.size(); i++){
			ret+=this.orderedStates.get(i).getId()+"=";
			boolean first=true;
			for(int j=0; j< this.orderedStates.size(); j++){
				if(!this.constraintmatrix[i][j].equals(new EmptyProposition<CONSTRAINEDELEMENT, TRANSITION>())){
					if(first){
						ret+=this.constraintmatrix[i][j].toString()+""+this.orderedStates.get(j).getId();
						first=false;
					}
					else{
						ret+=" + "+this.constraintmatrix[i][j].toString()+""+this.orderedStates.get(j).getId();
					}
				}
			}
			if(intersectionAutomaton.isAccept(this.orderedStates.get(i))){
				ret+="+"+new LambdaProposition<CONSTRAINEDELEMENT, TRANSITION>().toString();
			}
			ret+="\n";
		}
		return ret;
	}
}

