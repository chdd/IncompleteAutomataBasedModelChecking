package it.polimi.browzozky.predicates.types;

import it.polimi.browzozky.predicates.AbstractConstraint;
import it.polimi.model.State;

/**
 * @author Claudio Menghi
 * contains a predicate constraint
 */
public class Predicate<S extends State> extends AbstractConstraint<S>{

	/**
	 * contains the state of the predicate
	 */
	private S state;
	/**
	 * contains the regular expression of the predicate
	 */
	private String regularExpression;
	
	/**
	 * creates a new predicate
	 * @param state is the state that is constrained
	 * @param regularExpression is the regular expression that the state must satisfy
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
	 * set the state of the predicate
	 * @param state: the state to be set
	 * @throws IllegalArgumentException if the state to be set is null
	 */
	public void setState(S state) {
		if(state==null){
			throw new IllegalArgumentException("The state to be set cannot be null");
		}
		this.state = state;
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
	 * a predicate this concatenated with a constraint a is obtained as follows:
	 * -	the concatenation of the predicate and the empty constraint is empty
	 * -	the concatenation of the predicate and lambda is equal to the predicate
	 * -	the concatenation of two predicate with the same state constrained is a new predicate where the regular expressions are concatenated
	 *		the concatenation of two predicate with different state constrained is a new and constraint that contains the two predicates
	 * -	the concatenation of a predicate and an AndConstraint mixes the regular expression of this predicate of the regular
	 *  	expression of the first predicate of the and constraint if they have the same state
	 * -	the concatenation of a predicate and an AndConstraint add the predicate to the and constraint if this predicate and
	 *  	the first predicate of the and constraint have a different state
	 * -	the concatenation of a predicate and an OrConstraint creates a new AndConstraint where the first element is the predicate 
	 * 		and the second one is the or constraint
	 * -	the concatenation of a predicate and an EpsilonConstraint creates a new AndConstraint where the first element is the predicate 
	 * 		and the second one is the EpsilonConstraint
	 * @param a the constraint to be concatenated
	 * @throws IllegalArgumentException if a is null or if the constraint type is not supported
	 */
	@Override
	public AbstractConstraint<S> concatenate(AbstractConstraint<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint a to be concatenated cannot be null");
		}
		// the concatenation of the predicate and the empty constraint is empty
		if(a instanceof EmptyConstraint){
			return a;
		}
		// the concatenation of the predicate and lambda is equal to the predicate
		if(a instanceof LambdaConstraint){
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
				AndConstraint<S> cret=new AndConstraint<S>();
				cret.addConstraint(this);
				cret.addConstraint(a);
				return cret;
			}
			
		}
		/* -	the concatenation of a predicate and an AndConstraint mixes the regular expression of this predicate of the regular
		 *  	expression of the first predicate of the and constraint if they have the same state
		 * -	the concatenation of a predicate and an AndConstraint add the predicate to the and constraint if this predicate and
		 *  	the first predicate of the and constraint have a different state
		 */
		if(a instanceof AndConstraint){
			AndConstraint<S> atmp=(AndConstraint<S>)a;
			if(atmp.getFistPredicate() instanceof Predicate &&
					this.state.equals(((Predicate<S>)atmp.getFistPredicate()).state)){
						AndConstraint<S> cret=new AndConstraint<S>();
						cret.addConstraint(new Predicate<S>(this.state, this.regularExpression.concat(((Predicate<S>)atmp.getFistPredicate()).regularExpression)));
						cret.addConstraints(atmp.getConstraints().subList(1, atmp.getConstraints().size()));
						return cret;
				}
			
			else{
				
				AndConstraint<S> cret=new AndConstraint<S>();
				cret.addConstraint(this);
				cret.addConstraints(atmp.getConstraints());
				return cret;
			}
		}
		/* -	the concatenation of a predicate and an OrConstraint creates a new AndConstraint where the first element is the predicate 
		 * 		and the second one is the or constraint
		*/
		if(a instanceof OrConstraint){
			AndConstraint<S> cret=new AndConstraint<S>();
			cret.addConstraint(this);
			cret.addConstraint(a);
			return cret;
		}
		/*
		 * -	the concatenation of a predicate and an EpsilonConstraint creates a new AndConstraint where the first element is the predicate 
		 * 		and the second one is the EpsilonConstraint
		 */
		if(a instanceof EpsilonConstraint){
			AndConstraint<S> cret=new AndConstraint<S>();
			cret.addConstraint(this);
			cret.addConstraint(new EpsilonConstraint<S>());
			return cret;
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}
	
	/**
	 * the star operator applied to a predicate <s,exp> modifies the regular expression into (exp)* generating the new constraint <s,(exp)*>
	 * @return the new constraint where the star operator is applied to the predicate
	 */
	@Override
	public AbstractConstraint<S> star() {
		return new Predicate<S>(this.state, "("+this.regularExpression+")*");
	}
	
	/**
	 * the union of a predicate and a constraint a is defined as follows:
	 * -	the union of a predicate and the empty constraint is equal to the predicate
	 * - 	the union of a predicate and lambda is a new or constraint that contains the predicate and lambda
	 * -	the union of two predicates <s, reg1>, <s, reg2> with the same state s is a new predicate that contains the or combination 
	 * 		of their regular expressions <s, (reg1)+(reg2)> 
	 * -	the union of two predicates <s1, reg1>, <s2, reg2> with a different state is a new or constraint that contains the two predicates 
	 * -	the union of the predicate and an or constraint is a new or constraint where the predicate is added
	 * -	the union of the predicate and an and constraint is a new or constraint where the predicate and the and constraint are added
	 * -	the union of a predicate and epsilon is a new or constraint that contains the predicate
	 * @param a the constraint to be unified with this object
	 * @return the union of this predicate and the constraint a
	 * @throws IllegalArgumentException id the constraint a is null or the type of constraint is not supported
	 */
	@Override
	public AbstractConstraint<S> union(AbstractConstraint<S> a) {
		if(a==null){
			throw new IllegalArgumentException("The constraint a cannot be null");
		}
		// the union of a predicate and the empty constraint is equal to the predicate
		if(a instanceof EmptyConstraint){
			return this;
		}
		// the union of a predicate and lambda is a new or constraint that contains the predicate and lambda
		if(a instanceof LambdaConstraint){
			return new OrConstraint<S>(this, a);
		}
		// if a is a predicate
		if(a instanceof Predicate){
			Predicate<S> a1=(Predicate<S>) a;
			
			// the union of two predicates <s, reg1>, <s, reg2> with the same state s is a new predicate that contains the or combination 
			// of their regular expressions <s, (reg1)+(reg2)> 
			if(a1.getState().equals(this.getState())){
				return new Predicate<S>(this.getState(), "(("+this.regularExpression+")+("+a1.regularExpression+"))");
			}
			// the union of two predicates <s1, reg1>, <s2, reg2> with a different state is a new or constraint that contains the two predicates 
			else{
				return new OrConstraint<S>(this, a1);
			}
		}
		// the union of the predicate and an or constraint is a new or constraint where the predicate is added
		if(a instanceof OrConstraint){
			return new OrConstraint<S>(this,a);
		}
		// the union of the predicate and an and constraint is a new or constraint where the predicate and the and constraint are added
		if(a instanceof AndConstraint){
			return new OrConstraint<S>(this,a);
		}
		// the union of a predicate and epsilon is a new or constraint that contains the predicate
		if(a instanceof EpsilonConstraint){
			return this;
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}
	
	/**
	 * see {@link AbstractConstraint}
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
	@Override
	public AbstractConstraint<S> omega() {
		return new Predicate<S>(this.getState(), "("+this.getRegularExpression()+")ω");
	}
	
}
