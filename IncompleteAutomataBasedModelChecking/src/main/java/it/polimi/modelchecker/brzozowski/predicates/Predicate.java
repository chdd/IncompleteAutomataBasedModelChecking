package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @author claudiomenghi
 * contains a {@link Predicate} which constraints a state to be able to recognize a particular regular expression
 */
public class Predicate<S extends State> extends AbstractPredicate<S>{

	/**
	 * contains the state of the predicate
	 */
	private final S state;
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
	public Predicate(S state, String regularExpression){
		if(state==null){
			throw new IllegalArgumentException("It is not possible to create a predicate with a null state");
		}
		if(regularExpression==null){
			throw new IllegalArgumentException("It is not possible to create a predicate with a null regular expression");
		}
		this.state=state;
		this.regularExpression=regularExpression;
	}
	/**
	 * returns the state of the predicate
	 * @return the state of the predicate
	 */
	public S getState() {
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
	 * a predicate this concatenated with an {@link AbstractPredicate} a is obtained as follows:
	 * -	the concatenation of the {@link Predicate} and the {@link EmptyPredicate} is an {@link EmptyPredicate}
	 * -	the concatenation of the {@link Predicate} and {@link LambdaPredicate} is equal to the {@link Predicate}
	 * -	the concatenation of two {@link Predicate} with the same state is a new {@link Predicate} where the regular expressions are concatenated
	 *		the concatenation of two {@link Predicate} with different states  is a new and {@link AndPredicate} that contains the two predicates
	 * -	the concatenation of a {@link Predicate} and an {@link AndPredicate} mixes the regular expression of this predicate of the regular
	 *  	expression of the first predicate of the and predicate if they refer to the same state
	 * -	the concatenation of a {@link Predicate} and an {@link AndPredicate} add the predicate to the {@link AndPredicate}  if this predicate and
	 *  	the first predicate of the {@link AndPredicate} have a different states
	 * -	the concatenation of a {@link Predicate} and an {@link OrPredicate} creates a new {@link AndPredicate} where the first element is the {@link Predicate} 
	 * 		and the second one is the {@link OrPredicate}
	 * -	the concatenation of a {@link Predicate} and an {@link EpsilonPredicate} creates a new {@link AndPredicate} where the first element is the {@link Predicate} 
	 * 		and the second one is the {@link EpsilonPredicate}
	 * @param a the {@link AbstractPredicate} to be concatenated
	 * @throws IllegalArgumentException if a is null or if the constraint type is not supported
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint a to be concatenated cannot be null");
		}
		// the concatenation of the predicate and the empty constraint is empty
		if(a instanceof EmptyPredicate){
			return a;
		}
		// the concatenation of the predicate and lambda is equal to the predicate
		if(a instanceof LambdaPredicate){
			return this;
		}
		
		if(a instanceof Predicate){
			// the concatenation of two predicate with the same state constrained is a new predicate where the regular expressions are concatenated
			Predicate<S> a1=(Predicate<S>)a;
			if(a1.state.equals(this.state)){
				return new Predicate<S>(this.state, this.regularExpression+a1.regularExpression);
			}
			// the concatenation of two predicate with different state constrained is a new and constraint that contains the two predicates
			else{
				AndPredicate<S> cret=new AndPredicate<S>(this, a);
				return cret;
			}
		}
		/* -	the concatenation of a predicate and an AndConstraint mixes the regular expression of this predicate of the regular
		 *  	expression of the first predicate of the and constraint if they have the same state
		 * -	the concatenation of a predicate and an AndConstraint add the predicate to the and constraint if this predicate and
		 *  	the first predicate of the and constraint have a different state
		 */
		if(a instanceof AndPredicate){
			AndPredicate<S> andPredicate=(AndPredicate<S>)a;
			
			if(andPredicate.getFistPredicate() instanceof Predicate &&
					this.state.equals(((Predicate<S>)andPredicate.getFistPredicate()).state)){
						List<AbstractPredicate<S>> l=new ArrayList<AbstractPredicate<S>>();
						l.add(new Predicate<S>(this.state, this.regularExpression.concat(((Predicate<S>)andPredicate.getFistPredicate()).regularExpression)));
						l.addAll(andPredicate.getPredicates().subList(1, andPredicate.getPredicates().size()));
						AndPredicate<S> cret=new AndPredicate<S>(l);;
						return cret;
				}
			
			else{
				AndPredicate<S> cret=new AndPredicate<S>(this,a);
				return cret;
			}
		}
		/* -	the concatenation of a predicate and an OrConstraint creates a new AndConstraint where the first element is the predicate 
		 * 		and the second one is the or constraint
		*/
		if(a instanceof OrPredicate){
			AndPredicate<S> cret=new AndPredicate<S>(this, a);
			return cret;
		}
		/*
		 * -	the concatenation of a predicate and an EpsilonConstraint creates a new AndConstraint where the first element is the predicate 
		 * 		and the second one is the EpsilonConstraint
		 */
		if(a instanceof EpsilonPredicate){
			AndPredicate<S> cret=new AndPredicate<S>(this, a);
			return cret;
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}
	
	/**
	 * the union of a {@link Predicate} and a {@link AbstractPredicate} a is defined as follows:
	 * -	the union of a {@link Predicate} and the {@link EmptyPredicate}  is equal to the {@link Predicate}
	 * - 	the union of a {@link Predicate} and {@link LambdaPredicate} is a new or {@link OrPredicate} that contains the {@link Predicate} and {@link LambdaPredicate}
	 * -	the union of two {@link Predicate} <state, expression1>, <state, expression2> with the same state s is a new {@link Predicate} that contains the or combination 
	 * 		of their regular expressions <state, (expression1)+(expression2)> 
	 * -	the union of two {@link Predicate} <state1, expression1>, <state2, expression2> with a different state is a new {@link OrPredicate}  that contains the two {@link Predicate} 
	 * -	the union of the {@link Predicate} and an {@link OrPredicate} is a new {@link OrPredicate} where the {@link Predicate} is added
	 * -	the union of the {@link Predicate} and an {@link AndPredicate} is a new {@link OrPredicate} where the {@link Predicate} and the {@link OrPredicate} are added
	 * -	the union of a {@link Predicate} and {@link EpsilonPredicate} is a new {@link OrPredicate} that contains the {@link Predicate} and the {@link EpsilonPredicate}
	 * @param a the {@link AbstractPredicate} to be unified with this {@link Predicate}
	 * @return the union of this {@link Predicate} and the {@link AbstractPredicate} a
	 * @throws IllegalArgumentException id the {@link AbstractPredicate} a is null or the type of predicate is not supported
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint a cannot be null");
		}
		// the union of a predicate and the empty constraint is equal to the predicate
		if(a instanceof EmptyPredicate){
			return this;
		}
		// the union of a predicate and lambda is a new or constraint that contains the predicate and lambda
		if(a instanceof LambdaPredicate){
			return new OrPredicate<S>(this, a);
		}
		// if a is a predicate
		if(a instanceof Predicate){
			Predicate<S> a1=(Predicate<S>) a;
			
			if(this.equals(a1)){
				return this;
			}
			// the union of two predicates <s, reg1>, <s, reg2> with the same state s is a new predicate that contains the or combination 
			// of their regular expressions <s, (reg1)+(reg2)>
			if(a1.getState().equals(this.getState())){
				return new Predicate<S>(this.getState(), "(("+this.regularExpression+")+("+a1.regularExpression+"))");
			}
			// the union of two predicates <s1, reg1>, <s2, reg2> with a different state is a new or constraint that contains the two predicates 
			else{
				return new OrPredicate<S>(this, a1);
			}
		}
		// the union of the predicate and an or constraint is a new or constraint where the predicate is added
		if(a instanceof OrPredicate){
			return new OrPredicate<S>(this,a);
		}
		// the union of the predicate and an and constraint is a new or constraint where the predicate and the and constraint are added
		if(a instanceof AndPredicate){
			return new OrPredicate<S>(this,a);
		}
		// the union of a predicate and epsilon is a new or constraint that contains the predicate
		if(a instanceof EpsilonPredicate){
			return new OrPredicate<>(this,a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the supported types");
	}
	
	/**
	 * the star operator applied to a {@link Predicate} <state, expression> modifies the regular expression into (expression)* generating the new {@link Predicate} <state,(expression)*>
	 * @return the new {@link Predicate} where the star operator is applied to the regular expression
	 */
	@Override
	public AbstractPredicate<S> star() {
		return new Predicate<S>(this.state, "("+this.regularExpression+")*");
	}
	
	/**
	 * the omega operator applied to a {@link Predicate} <state, expression> modifies the regular expression into (expression)ω generating the new {@link Predicate} <state,(expression)ω>
	 * @return the new {@link Predicate} where the omega operator is applied to the regular expression
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return new Predicate<S>(this.getState(), "("+this.getRegularExpression()+")ω");
	}
	
	/**
	 * @see {@link AbstractPredicate}
	 */
	public String toString(){
		return "<"+state+","+this.regularExpression+">";
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
		Predicate<S> other = (Predicate<S>) obj;
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
	
	
	
}
