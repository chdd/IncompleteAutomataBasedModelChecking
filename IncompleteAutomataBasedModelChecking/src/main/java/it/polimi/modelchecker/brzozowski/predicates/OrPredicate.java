package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.HashSet;
import java.util.Set;

/**
 * @author claudiomenghi
 * contains an OrConstraint
 */
public class OrPredicate<S extends State> extends AbstractPredicate<S> {

	
	private final String type="v";
	
	 private Set<AbstractPredicate<S>> value;
	/**
	 * creates a new OrConstraint that contains the two Constraints firstConstraint, secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the OrConstraint
	 * @param secondConstraint is the second constraint included in the orConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public OrPredicate(AbstractPredicate<S> firstConstraint, AbstractPredicate<S> secondConstraint){
		 value=new HashSet<AbstractPredicate<S>>();
		 value.add(firstConstraint);
		 value.add(secondConstraint);
		 if(this.value.size()<=1){
	        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
	     }
	 }
	 /**
	  * creates a new empty constraint
	  */
	 public OrPredicate(Set<AbstractPredicate<S>> l) {
		 this.value = new HashSet<AbstractPredicate<S>>();
	        this.value.addAll(l);
	        if(this.value.size()<=1){
	        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
	        }
	 }
	 /**
	 * creates a new OrConstraint that contains the constraint firstConstraint and the set of constraint secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the OrConstraint
	 * @param secondConstraint is the set of constraints to be included in the OrConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public OrPredicate(AbstractPredicate<S> firstConstraint, Set<AbstractPredicate<S>> secondConstraint) {
		 this.value = new HashSet<AbstractPredicate<S>>();
		 this.value.add(firstConstraint);
		 this.value.addAll(secondConstraint);
		 if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
		 }  
	 }
	 
	 public OrPredicate(Set<AbstractPredicate<S>> firstConstraint, AbstractPredicate<S> secondConstraint) {
		 this.value = new HashSet<AbstractPredicate<S>>();
	        this.value.addAll(firstConstraint);
	        this.value.add(secondConstraint);
	        if(this.value.size()<=1){
	        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
	        }
	 }
	 
	 public OrPredicate(Set<AbstractPredicate<S>> firstConstraint, Set<AbstractPredicate<S>> secondConstraint) {
		 this.value = new HashSet<AbstractPredicate<S>>();
	        this.value.addAll(firstConstraint);
	        this.value.addAll(secondConstraint);
	        if(this.value.size()<=1){
	        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
	        }
	 }
	 
	 /**
	 * the concatenation of an or constraint is defined as follows:
	 * -	if a is an empty constraint the empty constraint is returned
	 * -	if a is an lambda constraint the or constraint is returned
	 * -	if a is an EpsilonConstraint concatenation of the or constraint and EpsilonConstraint is returned
	 * -	if a is a Predicate the concatenation of the or constraint and Predicate is returned
	 * -	if a is an AndConstraint the concatenation of the or constraint and the AndConstraint is returned
	 * -	if a is an OrConstraint the concatenation of the or constraint and the OrConstraint is returned
	 * @param a: is the constraint to be concatenated
	 * @return the constraint which is the concatenation of the or constraint and a
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> concatenate(AbstractPredicate<S> a) {
		//System.out.println("or concatenate");
		if(a==null){
			throw new IllegalArgumentException("the constraint a cannot be null");
		}
		// if a is an empty constraint the or constraint is returned
		if(a instanceof EmptyPredicate){
			return a;
		}
		// if a is an lambda constraint the or constraint is returned
		if(a instanceof LambdaPredicate){
			return this;
		}
		// if a is an EpsilonConstraint concatenation of the or constraint and EpsilonConstraint is returned
		if(a instanceof EpsilonPredicate){
			return new AndPredicate<S>(this, new EpsilonPredicate<S>());
		}
		// if a is a Predicate the concatenation of the or constraint and Predicate is returned
		if(a instanceof Predicate){
			
			Set<AbstractPredicate<S>> l=new HashSet<AbstractPredicate<S>>();
			
			for(AbstractPredicate<S> s: this.getPredicates()){
				l.add(s.concatenate(a));
			}
			return new OrPredicate<S>(l);
		}
		// if a is an AndConstraint the concatenation of the or constraint and the AndConstraint is returned
		if(a instanceof AndPredicate){
			return new AndPredicate<S>(this, a);
		}
		// if a is an OrConstraint the concatenation of the or constraint and the OrConstraint is returned
		if(a instanceof OrPredicate){
			return new AndPredicate<>(this, a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	/**
	 * the star operator when applied to an or constraint does not produce any effect
	 * @return the or constraint
	 */
	@Override
	public AbstractPredicate<S> star() {
		return this;
	}

	/** the union operator applied to an or constraint is defined as follows:
	 * -	the union of an or constraint and an empty constraint returns the or constraint
	 * -	the union of an or constraint and a LambdaConstraint is a new orConstraint that contains the or constraint and lambda
	 * -	the union of an or constraint and an EpsilonConstrain is a new orConstraint that contains the or constraint and the EpsilonConstrain
	 * -	the union of an or constraint and a Predicate is a new orConstraint that contains the or constraint and the Predicate
	 * -	the union of an or constraint and an orConstraint is a new orConstraint that contains the two or constraints 
	 * -	the union of an or constraint and an andConstraint is a new orConstraint that contains the or constraint and the andConstraint
	 * @param a: is the constraint to be unified
	 * @return the constraint which is the union of the or constraint and a
	 * @throws IllegalArgumentException is generated when the constraint to be concatenated is null
	 */
	@Override
	public AbstractPredicate<S> union(AbstractPredicate<S> a) {
		//System.out.println("or union");
		
		if(a==null){
			throw new IllegalArgumentException("The constraint to be concatenated cannot be null");
		}
		// the union of an or constraint and an empty constraint returns the or constraint
		if(a instanceof EmptyPredicate){
			return this;
		}
		// the union of an or constraint and a LambdaConstraint is a new orConstraint that contains the or constraint and lambda
		if(a instanceof LambdaPredicate){
			return new OrPredicate<>(this.getPredicates(),a);
		}
		// the union of an or constraint and an EpsilonConstrain is a new orConstraint that contains the or constraint and the EpsilonConstrain
		if(a instanceof EpsilonPredicate){
			return new OrPredicate<>(this.getPredicates(),a);
		}
		// the union of an or constraint and a Predicate is a new orConstraint that contains the or constraint and the Predicate
		if(a instanceof Predicate){
			return new OrPredicate<S>(this.getPredicates(),a);
		}
		// the union of an or constraint and an orConstraint is a new orConstraint that contains the two or constraints
		if(a instanceof OrPredicate){
			//System.out.println("or union a");
			return new OrPredicate<S>(this.getPredicates(),((OrPredicate<S>) a).getPredicates());
		}
		// the union of an or constraint and an andConstraint is a new orConstraint that contains the or constraint and the andConstraint
		if(a instanceof AndPredicate){
			//System.out.println("or union b");
			return new OrPredicate<S>(this.getPredicates(), a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}


	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}

	public AbstractPredicate<S> simplify(){
		return this;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	 public Set<AbstractPredicate<S>> getPredicates(){
		   return this.value;
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
		OrPredicate<S> other = (OrPredicate<S>) obj;
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
	@Override
	public String toString() {
		String ret="";
		boolean inserted=false;
		for(AbstractPredicate<S> p: this.value){
			if(inserted){
				ret=ret+this.getType()+"("+p+")";
			}
			else{
				inserted=true;
				ret="("+p+")";
			}
		}
		return ret;
	}
}
