package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author claudiomenghi
 * contains a {@link AtomicProposition} which constraints a state to be able to recognize a particular regular expression
 */
public class AtomicProposition<STATE extends State, TRANSITION extends LabelledTransition<STATE>> extends AbstractProposition<STATE, TRANSITION>{

	private boolean finalStateReached;
	
	
	public boolean isFinalStateReacher(){
		return this.finalStateReached;
	}
	
	/**
	 * contains the state of the predicate
	 */
	private final STATE state;
	/**
	 * contains the regular expression of the predicate
	 */
	private String regularExpression;
	
	/**
	 * creates a new predicate
	 * @param state is the state that is constrained
	 * @param regularExpression is the regular expression that the state must satisfy
	 * @throws IllegalArgumentException if the state or the regular expression of the predicate are null
	 */
	public AtomicProposition(TRANSITION transition, STATE state, String regularExpression, boolean finalStateReached){
		super(transition);
		if(state==null){
			throw new IllegalArgumentException("It is not possible to create a predicate with a null state");
		}
		if(regularExpression==null){
			throw new IllegalArgumentException("It is not possible to create a predicate with a null regular expression");
		}
		this.state=state;
		this.regularExpression=regularExpression;
		this.finalStateReached=finalStateReached;
	}
	
	public AtomicProposition(Set<TRANSITION> transitions, STATE state, String regularExpression, boolean finalStateReached){
		super(transitions);
		if(state==null){
			throw new IllegalArgumentException("It is not possible to create a predicate with a null state");
		}
		if(regularExpression==null){
			throw new IllegalArgumentException("It is not possible to create a predicate with a null regular expression");
		}
		this.state=state;
		this.regularExpression=regularExpression;
		this.finalStateReached=finalStateReached;
	}
	
	/**
	 * returns the state of the predicate
	 * @return the state of the predicate
	 */
	public STATE getState() {
		return state;
	}
	
	/**
	 * returns the regular expression of the predicate
	 * @return the regular expression that constraints the predicate
	 */
	public String getRegularExpression() {
		return regularExpression;
	}
	/**
	 * sets the regular expression
	 * @param the regular expression to be set in the predicate
	 * @throws IllegalArgumentException if the regular expression to be set is null
	 */
	public void setRegularExpression(String regex) {
		if(regex==null){
			throw new IllegalArgumentException("The constraint to be set cannot be null");
		}
		this.regularExpression = regex;
	}
	
	
	/**
	 * a predicate this concatenated with an {@link AbstractProposition} a is obtained as follows:
	 * -	the concatenation of the {@link AtomicProposition} and the {@link EmptyProposition} is an {@link EmptyProposition}
	 * -	the concatenation of the {@link AtomicProposition} and {@link LambdaProposition} is equal to the {@link AtomicProposition}
	 * -	the concatenation of two {@link AtomicProposition} with the same state is a new {@link AtomicProposition} where the regular expressions are concatenated
	 *		the concatenation of two {@link AtomicProposition} with different states  is a new and {@link AndProposition} that contains the two predicates
	 * -	the concatenation of a {@link AtomicProposition} and an {@link AndProposition} mixes the regular expression of this predicate of the regular
	 *  	expression of the first predicate of the and predicate if they refer to the same state
	 * -	the concatenation of a {@link AtomicProposition} and an {@link AndProposition} add the predicate to the {@link AndProposition}  if this predicate and
	 *  	the first predicate of the {@link AndProposition} have a different states
	 * -	the concatenation of a {@link AtomicProposition} and an {@link OrProposition} creates a new {@link AndProposition} where the first element is the {@link AtomicProposition} 
	 * 		and the second one is the {@link OrProposition}
	 * -	the concatenation of a {@link AtomicProposition} and an {@link EpsilonProposition} creates a new {@link AndProposition} where the first element is the {@link AtomicProposition} 
	 * 		and the second one is the {@link EpsilonProposition}
	 * @param a the {@link AbstractProposition} to be concatenated
	 * @throws IllegalArgumentException if a is null or if the constraint type is not supported
	 */
	@Override
	public LogicalItem<STATE, TRANSITION> concatenate(LogicalItem<STATE, TRANSITION> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint a to be concatenated cannot be null");
		}
		// the concatenation of the predicate and the empty constraint is empty
		if(a instanceof EmptyProposition){
			return a;
		}
		// the concatenation of the predicate and lambda is equal to the predicate
		if(a instanceof LambdaProposition){
			return this;
		}
		
		if(a instanceof AtomicProposition){
			// the concatenation of two predicate with the same state constrained is a new predicate where the regular expressions are concatenated
			AtomicProposition<STATE, TRANSITION> a1=(AtomicProposition<STATE, TRANSITION>)a;
			if(a1.state.equals(this.state)){
				Set<TRANSITION> newtransitions=new HashSet<TRANSITION>();
				newtransitions.addAll(a1.getTransitions());
				newtransitions.addAll(this.getTransitions());
				return new AtomicProposition<STATE, TRANSITION>(newtransitions, this.state, this.regularExpression+a1.regularExpression,
						this.isFinalStateReacher() || a1.isFinalStateReacher());
			}
			// the concatenation of two predicate with different state constrained is a new and constraint that contains the two predicates
			else{
				AndProposition<STATE, TRANSITION> cret=new AndProposition<STATE, TRANSITION>(this, a);
				return cret;
			}
		}
		/* -	the concatenation of a predicate and an AndConstraint mixes the regular expression of this predicate of the regular
		 *  	expression of the first predicate of the and constraint if they have the same state
		 * -	the concatenation of a predicate and an AndConstraint add the predicate to the and constraint if this predicate and
		 *  	the first predicate of the and constraint have a different state
		 */
		if(a instanceof AndProposition){
			AndProposition<STATE, TRANSITION> andPredicate=(AndProposition<STATE, TRANSITION>)a;
			
			if(andPredicate.getFistPredicate() instanceof AtomicProposition &&
					this.state.equals(((AtomicProposition<STATE, TRANSITION>)andPredicate.getFistPredicate()).state)){
						List<LogicalItem<STATE, TRANSITION>> l=new ArrayList<LogicalItem<STATE, TRANSITION>>();
						
						Set<TRANSITION> newtransition=new HashSet<TRANSITION>();
						newtransition.addAll(this.getTransitions());
						newtransition.addAll(((AtomicProposition<STATE, TRANSITION>)andPredicate.getFistPredicate()).getTransitions());
						
						l.add(new AtomicProposition<STATE, TRANSITION>
								(newtransition, this.state, this.regularExpression.concat(((AtomicProposition<STATE, TRANSITION>)andPredicate.getFistPredicate()).regularExpression),
										this.isFinalStateReacher() || ((AtomicProposition<STATE, TRANSITION>)andPredicate.getFistPredicate()).isFinalStateReacher()));
						l.addAll(andPredicate.getPredicates().subList(1, andPredicate.getPredicates().size()));
						AndProposition<STATE, TRANSITION> cret=new AndProposition<STATE, TRANSITION>(l);;
						return cret;
				}
			
			else{
				AndProposition<STATE, TRANSITION> cret=new AndProposition<STATE, TRANSITION>(this,a);
				return cret;
			}
		}
		/* -	the concatenation of a predicate and an OrConstraint creates a new AndConstraint where the first element is the predicate 
		 * 		and the second one is the or constraint
		*/
		if(a instanceof OrProposition){
			AndProposition<STATE, TRANSITION> cret=new AndProposition<STATE, TRANSITION>(this, a);
			return cret;
		}
		/*
		 * -	the concatenation of a predicate and an EpsilonConstraint creates a new AndConstraint where the first element is the predicate 
		 * 		and the second one is the EpsilonConstraint
		 */
		if(a instanceof EpsilonProposition){
			return this;
			//AndProposition<STATE, TRANSITION> cret=new AndProposition<STATE, TRANSITION>(this, a);
			//return cret;
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}
	
	/**
	 * the union of a {@link AtomicProposition} and a {@link AbstractProposition} a is defined as follows:
	 * -	the union of a {@link AtomicProposition} and the {@link EmptyProposition}  is equal to the {@link AtomicProposition}
	 * - 	the union of a {@link AtomicProposition} and {@link LambdaProposition} is a new or {@link OrProposition} that contains the {@link AtomicProposition} and {@link LambdaProposition}
	 * -	the union of two {@link AtomicProposition} <state, expression1>, <state, expression2> with the same state s is a new {@link AtomicProposition} that contains the or combination 
	 * 		of their regular expressions <state, (expression1)+(expression2)> 
	 * -	the union of two {@link AtomicProposition} <state1, expression1>, <state2, expression2> with a different state is a new {@link OrProposition}  that contains the two {@link AtomicProposition} 
	 * -	the union of the {@link AtomicProposition} and an {@link OrProposition} is a new {@link OrProposition} where the {@link AtomicProposition} is added
	 * -	the union of the {@link AtomicProposition} and an {@link AndProposition} is a new {@link OrProposition} where the {@link AtomicProposition} and the {@link OrProposition} are added
	 * -	the union of a {@link AtomicProposition} and {@link EpsilonProposition} is a new {@link OrProposition} that contains the {@link AtomicProposition} and the {@link EpsilonProposition}
	 * @param a the {@link AbstractProposition} to be unified with this {@link AtomicProposition}
	 * @return the union of this {@link AtomicProposition} and the {@link AbstractProposition} a
	 * @throws IllegalArgumentException id the {@link AbstractProposition} a is null or the type of predicate is not supported
	 */
	@Override
	public LogicalItem<STATE, TRANSITION> union(LogicalItem<STATE, TRANSITION> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint a cannot be null");
		}
		// the union of a predicate and the empty constraint is equal to the predicate
		if(a instanceof EmptyProposition){
			return this;
		}
		// the union of a predicate and lambda is a new or constraint that contains the predicate and lambda
		if(a instanceof LambdaProposition){
			return new OrProposition<STATE, TRANSITION>(this, a);
		}
		// if a is a predicate
		if(a instanceof AtomicProposition){
			AtomicProposition<STATE, TRANSITION> a1=(AtomicProposition<STATE, TRANSITION>) a;
			
			if(this.equals(a1)){
				return this;
			}
			// the union of two predicates <s, reg1>, <s, reg2> with the same state s is a new predicate that contains the or combination 
			// of their regular expressions <s, (reg1)+(reg2)>
			if(a1.getState().equals(this.getState())){
				Set<TRANSITION> transitions=new HashSet<TRANSITION>();
				transitions.addAll(this.getTransitions());
				transitions.addAll(a1.getTransitions());
				return new AtomicProposition<STATE, TRANSITION>(transitions, this.getState(), "(("+this.regularExpression+")|("+a1.regularExpression+"))",
						this.isFinalStateReacher() || a1.isFinalStateReacher());
			}
			// the union of two predicates <s1, reg1>, <s2, reg2> with a different state is a new or constraint that contains the two predicates 
			else{
				return new OrProposition<STATE, TRANSITION>(this, a1);
			}
		}
		// the union of the predicate and an or constraint is a new or constraint where the predicate is added
		if(a instanceof OrProposition){
			return new OrProposition<STATE, TRANSITION>(this,a);
		}
		// the union of the predicate and an and constraint is a new or constraint where the predicate and the and constraint are added
		if(a instanceof AndProposition){
			return new OrProposition<STATE, TRANSITION>(this,a);
		}
		// the union of a predicate and epsilon is a new or constraint that contains the predicate
		if(a instanceof EpsilonProposition){
			return new OrProposition<STATE, TRANSITION>(this,a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the supported types");
	}
	
	/**
	 * the star operator applied to a {@link AtomicProposition} <state, expression> modifies the regular expression into (expression)* generating the new {@link AtomicProposition} <state,(expression)*>
	 * @return the new {@link AtomicProposition} where the star operator is applied to the regular expression
	 */
	@Override
	public AbstractProposition<STATE, TRANSITION> star() {
		if(!finalStateReached){
			return new AtomicProposition<STATE, TRANSITION>(this.getTransitions(), this.state, "("+this.regularExpression+")*",
					this.isFinalStateReacher());
		}
		else{
			return this;
		}
	}
	
	/**
	 * the omega operator applied to a {@link AtomicProposition} <state, expression> modifies the regular expression into (expression)ω generating the new {@link AtomicProposition} <state,(expression)ω>
	 * @return the new {@link AtomicProposition} where the omega operator is applied to the regular expression
	 */
	@Override
	public AbstractProposition<STATE, TRANSITION> omega() {
		if(!finalStateReached){
			return new AtomicProposition<STATE, TRANSITION>(this.getTransitions(),this.getState(), "("+this.getRegularExpression()+")ω", this.isFinalStateReacher());
		}
		else{
			return this;
		}
	}
	
	/**
	 * @see {@link AbstractProposition}
	 */
	public String toString(){
		return "<"+state.getId()+","+this.regularExpression+">";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((regularExpression == null) ? 0 : regularExpression
						.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		AtomicProposition<STATE, TRANSITION> other = (AtomicProposition<STATE, TRANSITION>) obj;
		if (regularExpression == null) {
			if (other.regularExpression != null)
				return false;
		} else if (!regularExpression.equals(other.regularExpression))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public LogicalItem<STATE, TRANSITION> simplify() {
		return this;
	}
}
