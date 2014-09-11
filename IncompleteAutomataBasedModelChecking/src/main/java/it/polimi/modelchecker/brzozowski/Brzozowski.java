package it.polimi.modelchecker.brzozowski;

import it.polimi.model.ConstrainedTransition;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;
import it.polimi.modelchecker.brzozowski.predicates.Predicate;

import java.util.Hashtable;

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
	
	public AbstractPredicate<S1> getConstraint(){
		
		AbstractPredicate<S1> ret=new EmptyPredicate<S1>();
		
		for(S init: a.getInitialStates()){
			for(S accept: a.getAcceptStates()){
				
				Hashtable<S,Hashtable<S, AbstractPredicate<S1>>> cnsT1=this.getConstraintT();
				Hashtable<S, AbstractPredicate<S1>> cnsS1=this.getConstrainedS(accept);
				
				this.getConstraints(cnsT1, cnsS1);
				
				AbstractPredicate<S1> newconstraint=cnsS1.get(init).concatenate(cnsS1.get(accept).omega());
				ret=ret.union(newconstraint);
			}
		}
		return ret;
	}
	
	/**
	 * returns the constraint associated with the automaton
	 * @param t: is the matrix t which describes the transition relation of the automaton
	 * @param s: is the matrix s which describes the accepting states of the automaton
	 * @return the constraint associated with the Buchi automaton
	 * @throws
	 */
	protected  void getConstraints(Hashtable<S,Hashtable<S, AbstractPredicate<S1>>> t, Hashtable<S, AbstractPredicate<S1>> s) {
		
		
		for(S state1: a.getStates())
		{
			s.put(state1, t.get(state1).get(state1).star().concatenate(s.get(state1)));
			
			for(S state2: a.getStates()){
				if(!state1.equals(state2)){
					t.get(state1).put(state2, t.get(state1).get(state1).star().concatenate(t.get(state1).get(state2)));
				}
			}
			for(S state2: a.getStates()){
				if(!state2.equals(state1)){
					s.put(state2, s.get(state2).union(t.get(state2).get(state1).concatenate(s.get(state1))));
					for(S state3: a.getStates()){
						if(!state3.equals(state1)){
							t.get(state2).put(state3, t.get(state2).get(state3).union(t.get(state2).get(state1).concatenate(t.get(state1).get(state3))));
						}
					}
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
	protected Hashtable<S,Hashtable<S, AbstractPredicate<S1>>>  getConstraintT(){
		
		Hashtable<S, Hashtable<S, AbstractPredicate<S1>>>  ret=new Hashtable<S, Hashtable<S, AbstractPredicate<S1>>>();
		for(S s1: a.getStates()){
			ret.put(s1, new Hashtable<S, AbstractPredicate<S1>>());
			for(T t: a.getTransitionsWithSource(s1)){
				// if the first state of s1 does not change and the state is transparent
				if(t instanceof ConstrainedTransition){
					if(!ret.get(s1).containsKey(t.getDestination())){
						ret.get(s1).put(t.getDestination(), new Predicate<S1>(s1.getS1(),t.getCharacter()+""));
					}
					else{
						ret.get(s1).put(t.getDestination(), 
								ret.get(s1).get(t.getDestination()).union(new Predicate<S1>(s1.getS1(),t.getCharacter()+"")));
					}
				}
				else{
					if(a.isMixed(s1)){
						if(!ret.get(s1).containsKey(t.getDestination())){
							ret.get(s1).put(t.getDestination(), new Predicate<S1>(s1.getS1(), "λ"));
						}
						else{
							ret.get(s1).put(t.getDestination(),
									ret.get(s1).get(t.getDestination()).union(new Predicate<S1>(s1.getS1(), "λ")));
						}
					}
					else{
						if(!ret.get(s1).containsKey(t.getDestination())){
							ret.get(s1).put(t.getDestination(), new LambdaPredicate<S1>());
						}
						else{
							ret.get(s1).put(t.getDestination(), 
									ret.get(s1).get(t.getDestination()).union(new LambdaPredicate<S1>()));
						}
					}
				}
			}
			for(S s2: a.getStates()){
				if(!ret.get(s1).containsKey(s2)){
					ret.get(s1).put(s2, new EmptyPredicate<S1>());
				}
			}
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
	protected Hashtable<S, AbstractPredicate<S1>> getConstrainedS(S accept){
		if(accept==null){
			throw new IllegalArgumentException("The accepting state cannot be null");
		}
		if(!a.isAccept(accept)){
			throw new IllegalArgumentException("The state "+accept.getName()+" must be accepting");
		}
		Hashtable<S, AbstractPredicate<S1>> ret=new Hashtable<S, AbstractPredicate<S1>>();
		
		// for each state in the stateOrdered vector
		for(S s: a.getStates()){
			
			// if the state is equal to the state accept
			if(accept.equals(s)){
				// I add the lambda predicate in the s[i] cell of the vector
				ret.put(s, new LambdaPredicate<S1>());
			}
			else{
				// I add the empty predicate in the s[i] cell of the vector
				ret.put(s, new EmptyPredicate<S1>());
			}
		}
		// returns the vector s
		return ret;
	}
	
}

