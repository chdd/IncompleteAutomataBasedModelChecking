package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.impl.states.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @author claudiomenghi
 * contains an {@link AndProposition}, which is a set of {@link AbstractProposition} that must be simultaneously satisfied to get the final property
 * satisfied
 */
public class AndProposition<S extends State> extends LogicalProposition<S>{
	
	private final String type="^";
	
	/**
	 * creates a new {@link AndProposition} that contains the two {@link AbstractProposition} firstPredicate, secondPredicate 
	 * @param firstPredicate is the first {@link AbstractProposition} to be included in the {@link AndProposition}
	 * @param secondPredicate is the second {@link AbstractProposition} included in the {@link AndProposition}
	 * @throws IllegalArgumentException is the first or the second {@link AbstractProposition} are null
	 */
	 public AndProposition(AbstractProposition<S> firstPredicate, AbstractProposition<S> secondPredicate){
	 	super(firstPredicate, secondPredicate);
	 }
	 /**
     * Initializes a newly created {@link AndProposition} starting from the list l of {@link AbstractProposition} 
     * @param l: is the list of {@link AbstractProposition} to be added in the and predicate
     * @throws IllegalArgumentException if the list of the {@link AbstractProposition} contains less than 2 {@link AbstractProposition}
     */
	 public AndProposition(List<AbstractProposition<S>> l) {
		super(l);
	 }
	
	 
    /**
   	 * the concatenation of an {@link AndProposition}  is defined as follows:
   	 * -	if a is an {@link EmptyProposition}  the {@link EmptyProposition} is returned
   	 * -	if a is an {@link LambdaProposition}  the {@link AndProposition} is returned
   	 * -	if a is an {@link EpsilonProposition} the concatenation of the {@link AndProposition} constraint and {@link EpsilonProposition} is returned
   	 * -	if a is a  {@link AtomicProposition} and the last element p is a {@link AtomicProposition} that has the same state of a,
   	 * 		the regular expression of p is modified and concatenated to the one of the {@link AtomicProposition} a
   	 * -	if a is a {@link AtomicProposition} and the last element p of this {@link AndProposition} is a {@link AtomicProposition} that has a different state of a, 
   	 * 		a new {@link AndProposition} that contains the original {@link AndProposition} and the {@link AtomicProposition} a is generated
   	 * -	if a is an {@link OrProposition} a new and {@link AndProposition} that contains the and of this {@link AndProposition} and {@link OrProposition}is generated
   	 * -	if a is an {@link AndProposition} and  the last {@link AbstractProposition} of this {@link AndProposition} and the first {@link AbstractProposition} of a have the same state, 
   	 * 		their regular expressions are merged
   	 * -	if a is an {@link AndProposition} and  the last {@link AbstractProposition} of this {@link AndProposition} and the first {@link AbstractProposition} of a do not have 
   	 * 		the same state, a new {@link AndProposition} that contains all {@link AbstractProposition} of these two {@link AndProposition} is generated.
   	 * @param a: is the {@link AbstractProposition} to be concatenated
   	 * @return the {@link AndProposition} which is the concatenation of the {@link AndProposition} and a
   	 * @throws IllegalArgumentException is generated when the {@link AbstractProposition} to be concatenated is null
   	 */
	@Override
	public AbstractProposition<S> concatenate(AbstractProposition<S> a) {
		
		if(a==null){
			throw new IllegalArgumentException("the constraint a cannot be null");
		}
		// if a is an empty constraint the empty constraint is returned
		if(a instanceof EmptyProposition){
			return a;
		}
		// if a is an lambda constraint the and constraint is returned
		if(a instanceof LambdaProposition){
			return this;
		}
		// if a is an EpsilonConstraint the concatenation of the and constraint and EpsilonConstraint is returned
		if(a instanceof EpsilonProposition){
			if(this.getLastPredicate() instanceof EpsilonProposition){
				return this;
			}
			else{
				return new AndProposition<S>(this, a);
			}
		}
		// -	if a is a Predicate and the last element p of this constraint is a Predicate that has the same state of a, 
		// 		the regular expression of p is modified and concatenated to the one of the predicate a
		// -	if a is a Predicate and the last element p of this constraint is a Predicate that has a different state of a, 
		// 		a new and constraint that contains the original constrained and the predicate a is generated
		if(a instanceof AtomicProposition){
			if(this.getLastPredicate() instanceof AtomicProposition &&
					((AtomicProposition<S>)a).getState().equals(((AtomicProposition<S>)this.getLastPredicate()).getState())){
						
						List<AbstractProposition<S>> l=new ArrayList<AbstractProposition<S>>();
						l.addAll(this.getPredicates().subList(0, this.getPredicates().size()-1));
						l.add(new AtomicProposition<S>(((AtomicProposition<S>)this.getLastPredicate()).getState(), ((AtomicProposition<S>)this.getLastPredicate()).getRegularExpression().concat(((AtomicProposition<S>)a).getRegularExpression())))
;						AndProposition<S> cret=new AndProposition<S>(l);
						return cret;
				}
			else{
				return new AndProposition<S>(this, a);
			}
		}
		// if a is an or constraint a new and constraint that contains the constraint of this and constraint and the or constraint is generated
		if(a instanceof OrProposition){
			return new AndProposition<S>(this, a);
		}
		// if a is an and constraint and  the last predicate of this constraint and the first predicate of a have the same state, their regular expressions are merged
		// if a is an and constraint and  the last predicate of this constraint and the first predicate of do not have the same state, a new and constraint
		// that contains all of the constraints of these two and constraints is generated.
		if(a instanceof AndProposition){
			AndProposition<S> c=(AndProposition<S>)a;
			
			if((c.getFistPredicate() instanceof AtomicProposition) && (this.getLastPredicate() instanceof AtomicProposition) &&
				((AtomicProposition<S>)c.getFistPredicate()).getState().equals(((AtomicProposition<S>)this.getLastPredicate()).getState())){
				
				List<AbstractProposition<S>> l=new ArrayList<AbstractProposition<S>>();
				l.addAll(this.getPredicates().subList(0, this.getPredicates().size()-1));
				l.add(new AtomicProposition<S>(((AtomicProposition<S>)this.getLastPredicate()).getState(), ((AtomicProposition<S>)this.getLastPredicate()).getRegularExpression()+((AtomicProposition<S>)c.getFistPredicate()).getRegularExpression()));
				l.addAll(c.getPredicates().subList(1, c.getPredicates().size()));
				AndProposition<S> cret=new AndProposition<S>(l);
				return cret;
			}
			else{
				return new AndProposition<S>(this,  a);
			}
			
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}
	
	/** the union operator applied to an {@link AndProposition}  is defined as follows:
	 * -	the union of an {@link AndProposition} and an {@link EmptyProposition} returns the {@link AndProposition}
	 * -	the union of an {@link AndProposition} and a {@link LambdaProposition} is a new {@link OrProposition} that contains the {@link AndProposition}  and the {@link LambdaProposition}
	 * -	the union of an {@link AndProposition} and an {@link EpsilonProposition} is a new {@link OrProposition} that contains the {@link AndProposition}  and the {@link EpsilonProposition}
	 * -	the union of an {@link AndProposition} and a {@link AtomicProposition} is a new {@link OrProposition} that contains the {@link AndProposition}  and the {@link AtomicProposition}
	 * -	the union of an {@link AndProposition} and an {@link OrProposition} is a new {@link OrProposition} that contains the and {@link AndProposition} the {@link OrProposition} 
	 * -	the union of an {@link AndProposition} and an {@link AndProposition} is a new {@link OrProposition} that contains the two {@link AndProposition}
	 * @param a: is the {@link AbstractProposition} to be unified
	 * @return the {@link AbstractProposition} which is the union of the {@link AndProposition} and the {@link AbstractProposition} a
	 * @throws IllegalArgumentException is generated when the {@link AbstractProposition} to be concatenated is null
	 */
	@Override
	public AbstractProposition<S> union(AbstractProposition<S> a) {
		//System.out.println("and union");
		
		if(a==null){
			throw new IllegalArgumentException("The constraint to be concatenated cannot be null");
		}
		
		// the union of an and constraint and an empty constraint returns the or constraint
		if(a instanceof EmptyProposition){
			return this;
		}
		// the union of an and constraint and a LambdaConstraint is a new orConstraint that contains the and constraint and lambda
		if(a instanceof LambdaProposition){
			return new OrProposition<S>(this,a);
		}
		// the union of an and constraint and an EpsilonConstrain is a new orConstraint that contains the and constraint and the EpsilonConstrain
		if(a instanceof EpsilonProposition){
			return new OrProposition<S>(this, a);
		}
		// the union of an and constraint and a Predicate is a new orConstraint that contains the and constraint and the Predicate
		if(a instanceof AtomicProposition){
			return new OrProposition<S>(this, a);
		}
		// the union of an and constraint and an orConstraint is a new orConstraint that contains the and and the or constraints 
		if(a instanceof OrProposition){
			return new OrProposition<S>(this, a);
		}
		// the union of an and constraint and an andConstraint is a new orConstraint that contains the two and constraints
		if(a instanceof AndProposition){
			return new OrProposition<S>(this, a);
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	/**
	 * the star operator  applied to an {@link AndProposition} does not produce any effect
	 * @return the {@link AndProposition}
	 */
	@Override
	public AbstractProposition<S> star() {
		return this;
	}
	
	/**
	 * the omega operator  applied to an {@link AndProposition} does not produce any effect
	 * @return the {@link AndProposition}
	 */
	@Override
	public AbstractProposition<S> omega() {
		return this;
	}
	
	
	 
	/**
	 * returns the first {@link AbstractProposition} of the {@link List}
	 * @return the first {@link AbstractProposition} of the {@link List}
	 */
	public AbstractProposition<S> getFistPredicate(){
    	return this.getPredicates().get(0);
    }
	/**
	 * returns the last {@link AbstractProposition} of the {@link List}
	 * @return the last {@link AbstractProposition} of the {@link List}
	 */
    public AbstractProposition<S> getLastPredicate(){
    	return this.getPredicates().get(this.getPredicates().size()-1);
    }
    
    public String getType(){
		return this.type;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		AndProposition<S> other = (AndProposition<S>) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
    
    
    
    
  
}
