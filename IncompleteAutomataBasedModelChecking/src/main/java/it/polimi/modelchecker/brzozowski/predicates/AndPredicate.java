package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @author claudiomenghi
 * contains an AndPredicate, which is a set of predicates that must be simultaneously satisfied to get the final property
 * satisfied
 */
public class AndPredicate<S extends State> extends AbstractPredicate<S>{
	
	private final String type="^";
	
	 /** The value is used for character storage. */
    private List<AbstractPredicate<S>> value;
	
	/**
	 * creates a new AndConstraint that contains the two Constraints firstConstraint, secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the AndConstraint
	 * @param secondConstraint is the second constraint included in the AndConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public AndPredicate(AbstractPredicate<S> firstConstraint, AbstractPredicate<S> secondConstraint){
	 	if(firstConstraint==null){
    		throw new IllegalArgumentException("The first constraint cannot be null");
    	}
    	if(secondConstraint==null){
    		throw new IllegalArgumentException("The second constraint cannot be null");
    	}
        this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.add(firstConstraint);
        this.value.add(secondConstraint);
        if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
        }
	 }
	 /**
     * Initializes a newly created AndConstraint 
     */
	 public AndPredicate(List<AbstractPredicate<S>> l) {
		 this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.addAll(l);
        if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
        }
	 }
	 /**
	 * creates a new AndConstraint that contains the constraint firstConstraint and the set of constraint secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the AndConstraint
	 * @param secondConstraint is the set of constraints to be included in the AndConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public AndPredicate(AbstractPredicate<S> firstConstraint, List<AbstractPredicate<S>> secondConstraint) {
		 this.value = new ArrayList<AbstractPredicate<S>>();
		 this.value.add(firstConstraint);
		 this.value.addAll(secondConstraint);
		 if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
		 }   
	 }
	 
	 public AndPredicate(List<AbstractPredicate<S>> firstConstraint, AbstractPredicate<S> secondConstraint) {
		 this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.addAll(firstConstraint);
        this.value.add(secondConstraint);
        if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
        }
	 }
	 public AndPredicate(List<AbstractPredicate<S>> firstConstraint, List<AbstractPredicate<S>> secondConstraint) {
		 this.value = new ArrayList<AbstractPredicate<S>>();
	        this.value.addAll(firstConstraint);
	        this.value.addAll(secondConstraint);
	        if(this.value.size()<=1){
	        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
	        }
	 }
		   
	
   
    
    /**
   	 * the concatenation of an or constraint is defined as follows:
   	 * -	if a is an empty constraint the empty constraint is returned
   	 * -	if a is an lambda constraint the and constraint is returned
   	 * -	if a is an EpsilonConstraint the concatenation of the and constraint and EpsilonConstraint is returned
   	 * -	if a is a Predicate and the last element p of this constraint is a Predicate that has the same state of a,
   	 * 		the regular expression of p is modified and concatenated to the one of the predicate a
   	 * -	if a is a Predicate and the last element p of this constraint is a Predicate that has a different state of a, 
   	 * 		a new and constraint that contains the original constrained and the predicate a is generated
   	 * -	if a is an or constraint a new and constraint that contains the constraint of this and constraint and the or constraint is generated
   	 * -	if a is an and constraint and  the last predicate of this constraint and the first predicate of a have the same state, 
   	 * 		their regular expressions are merged
   	 * -	if a is an and constraint and  the last predicate of this constraint and the first predicate of do not have 
   	 * 		the same state, a new and constraint that contains all of the constraints of these two and constraints is generated.
   	 * @param a: is the constraint to be concatenated
   	 * @return the constraint which is the concatenation of the or constraint and a
   	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
   	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		//System.out.println("and concatanate");
		
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
				return new AndPredicate<S>(this.getPredicates(), a);
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
				return new AndPredicate<S>(this.getPredicates(), a);
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
				return new AndPredicate<S>(this.getPredicates(), ((AndPredicate<S>) a).getPredicates());
			}
			
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	/**
	 * the star operator when applied to an and constraint does not produce any effect
	 * @return the and constraint
	 */
	@Override
	public AbstractPredicate<S> star() {
		return this;
	}
	
	/** the union operator applied to an and constraint is defined as follows:
	 * -	the union of an and constraint and an empty constraint returns the or constraint
	 * -	the union of an and constraint and a LambdaConstraint is a new orConstraint that contains the and constraint and lambda
	 * -	the union of an and constraint and an EpsilonConstrain is a new orConstraint that contains the and constraint and the EpsilonConstrain
	 * -	the union of an and constraint and a Predicate is a new orConstraint that contains the and constraint and the Predicate
	 * -	the union of an and constraint and an orConstraint is a new orConstraint that contains the and and the or constraints 
	 * -	the union of an and constraint and an andConstraint is a new orConstraint that contains the two and constraints
	 * @param a: is the constraint to be unified
	 * @return the constraint which is the union of the and constraint and a
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
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
			return new OrPredicate<S>(this, ((OrPredicate<S>) a).getPredicates());
		}
		// the union of an and constraint and an andConstraint is a new orConstraint that contains the two and constraints
		if(a instanceof AndPredicate){
			if(this.equals(a)){
				return this;
			}
			else{
				return new OrPredicate<S>(this, a);
			}
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}
	
	public AbstractPredicate<S> simplify(){
		//return this;
		List<AbstractPredicate<S>> value=new ArrayList<AbstractPredicate<S>>();
		for(AbstractPredicate<S> p: this.getPredicates()){
			if(!p.equals(new EpsilonPredicate<S>()) && !p.equals(new LambdaPredicate<S>())){
				value.add(p);
			}
		}
		if(value.size()>1){
			
			return new AndPredicate<S>(value);
		}
		else{
			return value.get(0).simplify();
		}
		
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		AndPredicate<S> other = (AndPredicate<S>) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	 public List<AbstractPredicate<S>> getPredicates(){
		   return this.value;
	   }
	 
	@Override
	public String toString() {
		String ret="";
		boolean inserted=false;
		for(int i=0; i<this.value.size();i++){
			if(inserted){
				ret=ret+this.getType()+"("+value.get(i)+")";
			}
			else{
				inserted=true;
				ret="("+value.get(i).toString()+")";
			}
		}
		return ret;
	}
	public AbstractPredicate<S> getFistPredicate(){
    	return value.get(0);
    }
    public AbstractPredicate<S> getLastPredicate(){
    	return value.get(value.size()-1);
    }
	
	
    
}
