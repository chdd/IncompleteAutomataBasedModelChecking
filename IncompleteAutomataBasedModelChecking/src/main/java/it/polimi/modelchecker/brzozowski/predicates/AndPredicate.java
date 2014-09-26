package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @author claudiomenghi
 * contains an {@link AndPredicate}, which is a set of {@link AbstractPredicate} that must be simultaneously satisfied to get the final property
 * satisfied
 */
public class AndPredicate<S extends State> extends LogicalPredicate<S>{
	
	private final String type="^";
	
	/**
	 * creates a new {@link AndPredicate} that contains the two {@link AbstractPredicate} firstPredicate, secondPredicate 
	 * @param firstPredicate is the first {@link AbstractPredicate} to be included in the {@link AndPredicate}
	 * @param secondPredicate is the second {@link AbstractPredicate} included in the {@link AndPredicate}
	 * @throws IllegalArgumentException is the first or the second {@link AbstractPredicate} are null
	 */
	 public AndPredicate(AbstractPredicate<S> firstPredicate, AbstractPredicate<S> secondPredicate){
	 	super(firstPredicate, secondPredicate);
	 }
	 /**
     * Initializes a newly created {@link AndPredicate} starting from the list l of {@link AbstractPredicate} 
     * @param l: is the list of {@link AbstractPredicate} to be added in the and predicate
     * @throws IllegalArgumentException if the list of the {@link AbstractPredicate} contains less than 2 {@link AbstractPredicate}
     */
	 public AndPredicate(List<AbstractPredicate<S>> l) {
		super(l);
	 }
	
	 
    /**
   	 * the concatenation of an {@link AndPredicate}  is defined as follows:
   	 * -	if a is an {@link EmptyPredicate}  the {@link EmptyPredicate} is returned
   	 * -	if a is an {@link LambdaPredicate}  the {@link AndPredicate} is returned
   	 * -	if a is an {@link EpsilonPredicate} the concatenation of the {@link AndPredicate} constraint and {@link EpsilonPredicate} is returned
   	 * -	if a is a  {@link Predicate} and the last element p is a {@link Predicate} that has the same state of a,
   	 * 		the regular expression of p is modified and concatenated to the one of the {@link Predicate} a
   	 * -	if a is a {@link Predicate} and the last element p of this {@link AndPredicate} is a {@link Predicate} that has a different state of a, 
   	 * 		a new {@link AndPredicate} that contains the original {@link AndPredicate} and the {@link Predicate} a is generated
   	 * -	if a is an {@link OrPredicate} a new and {@link AndPredicate} that contains the and of this {@link AndPredicate} and {@link OrPredicate}is generated
   	 * -	if a is an {@link AndPredicate} and  the last {@link AbstractPredicate} of this {@link AndPredicate} and the first {@link AbstractPredicate} of a have the same state, 
   	 * 		their regular expressions are merged
   	 * -	if a is an {@link AndPredicate} and  the last {@link AbstractPredicate} of this {@link AndPredicate} and the first {@link AbstractPredicate} of a do not have 
   	 * 		the same state, a new {@link AndPredicate} that contains all {@link AbstractPredicate} of these two {@link AndPredicate} is generated.
   	 * @param a: is the {@link AbstractPredicate} to be concatenated
   	 * @return the {@link AndPredicate} which is the concatenation of the {@link AndPredicate} and a
   	 * @throws IllegalArgumentException is generated when the {@link AbstractPredicate} to be concatenated is null
   	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		//System.out.println("and concatenate");
		
		if(a==null){
			throw new IllegalArgumentException("the constraint a cannot be null");
		}
		// if a is an empty constraint the empty constraint is returned
		if(a instanceof EmptyPredicate){
			return a;
		}
		// if a is an lambda constraint the and constraint is returned
		if(a instanceof LambdaPredicate){
			return this;
		}
		// if a is an EpsilonConstraint the concatenation of the and constraint and EpsilonConstraint is returned
		if(a instanceof EpsilonPredicate){
			if(this.getLastPredicate() instanceof EpsilonPredicate){
				return this;
			}
			else{
				return new AndPredicate<S>(this, a);
			}
		}
		// -	if a is a Predicate and the last element p of this constraint is a Predicate that has the same state of a, 
		// 		the regular expression of p is modified and concatenated to the one of the predicate a
		// -	if a is a Predicate and the last element p of this constraint is a Predicate that has a different state of a, 
		// 		a new and constraint that contains the original constrained and the predicate a is generated
		if(a instanceof Predicate){
			if(this.getLastPredicate() instanceof Predicate &&
					((Predicate<S>)a).getState().equals(((Predicate<S>)this.getLastPredicate()).getState())){
						
						List<AbstractPredicate<S>> l=new ArrayList<AbstractPredicate<S>>();
						l.addAll(this.getPredicates().subList(0, this.getPredicates().size()-1));
						l.add(new Predicate<S>(((Predicate<S>)this.getLastPredicate()).getState(), ((Predicate<S>)this.getLastPredicate()).getRegularExpression().concat(((Predicate<S>)a).getRegularExpression())))
;						AndPredicate<S> cret=new AndPredicate<S>(l);
						return cret;
				}
			else{
				return new AndPredicate<S>(this, a);
			}
		}
		// if a is an or constraint a new and constraint that contains the constraint of this and constraint and the or constraint is generated
		if(a instanceof OrPredicate){
			return new AndPredicate<S>(this, a);
		}
		// if a is an and constraint and  the last predicate of this constraint and the first predicate of a have the same state, their regular expressions are merged
		// if a is an and constraint and  the last predicate of this constraint and the first predicate of do not have the same state, a new and constraint
		// that contains all of the constraints of these two and constraints is generated.
		if(a instanceof AndPredicate){
			AndPredicate<S> c=(AndPredicate<S>)a;
			
			if((c.getFistPredicate() instanceof Predicate) && (this.getLastPredicate() instanceof Predicate) &&
				((Predicate<S>)c.getFistPredicate()).getState().equals(((Predicate<S>)this.getLastPredicate()).getState())){
				
				List<AbstractPredicate<S>> l=new ArrayList<AbstractPredicate<S>>();
				l.addAll(this.getPredicates().subList(0, this.getPredicates().size()-1));
				l.add(new Predicate<S>(((Predicate<S>)this.getLastPredicate()).getState(), ((Predicate<S>)this.getLastPredicate()).getRegularExpression()+((Predicate<S>)c.getFistPredicate()).getRegularExpression()));
				l.addAll(c.getPredicates().subList(1, c.getPredicates().size()));
				AndPredicate<S> cret=new AndPredicate<S>(l);
				return cret;
			}
			else{
				return new AndPredicate<S>(this,  a);
			}
			
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the predicate is not in the set of the predefined types");
	}
	
	/** the union operator applied to an {@link AndPredicate}  is defined as follows:
	 * -	the union of an {@link AndPredicate} and an {@link EmptyPredicate} returns the {@link AndPredicate}
	 * -	the union of an {@link AndPredicate} and a {@link LambdaPredicate} is a new {@link OrPredicate} that contains the {@link AndPredicate}  and the {@link LambdaPredicate}
	 * -	the union of an {@link AndPredicate} and an {@link EpsilonPredicate} is a new {@link OrPredicate} that contains the {@link AndPredicate}  and the {@link EpsilonPredicate}
	 * -	the union of an {@link AndPredicate} and a {@link Predicate} is a new {@link OrPredicate} that contains the {@link AndPredicate}  and the {@link Predicate}
	 * -	the union of an {@link AndPredicate} and an {@link OrPredicate} is a new {@link OrPredicate} that contains the and {@link AndPredicate} the {@link OrPredicate} 
	 * -	the union of an {@link AndPredicate} and an {@link AndPredicate} is a new {@link OrPredicate} that contains the two {@link AndPredicate}
	 * @param a: is the {@link AbstractPredicate} to be unified
	 * @return the {@link AbstractPredicate} which is the union of the {@link AndPredicate} and the {@link AbstractPredicate} a
	 * @throws IllegalArgumentException is generated when the {@link AbstractPredicate} to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		//System.out.println("and union");
		
		if(a==null){
			throw new IllegalArgumentException("The constraint to be concatenated cannot be null");
		}
		
		// the union of an and constraint and an empty constraint returns the or constraint
		if(a instanceof EmptyPredicate){
			return this;
		}
		// the union of an and constraint and a LambdaConstraint is a new orConstraint that contains the and constraint and lambda
		if(a instanceof LambdaPredicate){
			return new OrPredicate<S>(this,a);
		}
		// the union of an and constraint and an EpsilonConstrain is a new orConstraint that contains the and constraint and the EpsilonConstrain
		if(a instanceof EpsilonPredicate){
			return new OrPredicate<S>(this, a);
		}
		// the union of an and constraint and a Predicate is a new orConstraint that contains the and constraint and the Predicate
		if(a instanceof Predicate){
			return new OrPredicate<S>(this, a);
		}
		// the union of an and constraint and an orConstraint is a new orConstraint that contains the and and the or constraints 
		if(a instanceof OrPredicate){
			return new OrPredicate<S>(this, a);
		}
		// the union of an and constraint and an andConstraint is a new orConstraint that contains the two and constraints
		if(a instanceof AndPredicate){
			return new OrPredicate<S>(this, a);
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	/**
	 * the star operator  applied to an {@link AndPredicate} does not produce any effect
	 * @return the {@link AndPredicate}
	 */
	@Override
	public AbstractPredicate<S> star() {
		return this;
	}
	
	/**
	 * the omega operator  applied to an {@link AndPredicate} does not produce any effect
	 * @return the {@link AndPredicate}
	 */
	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}
	
	
	 
	/**
	 * returns the first {@link AbstractPredicate} of the {@link List}
	 * @return the first {@link AbstractPredicate} of the {@link List}
	 */
	public AbstractPredicate<S> getFistPredicate(){
    	return this.getPredicates().get(0);
    }
	/**
	 * returns the last {@link AbstractPredicate} of the {@link List}
	 * @return the last {@link AbstractPredicate} of the {@link List}
	 */
    public AbstractPredicate<S> getLastPredicate(){
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
		AndPredicate<S> other = (AndPredicate<S>) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
    
    
    
    
  
}
