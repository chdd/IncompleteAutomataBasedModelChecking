package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Claudio Menghi
 * contains an AndConstraint
 */
public class AndPredicate<S extends State> extends ConstraintLanguage<S>{
	
	/**
	 * creates a new AndConstraint that contains the two Constraints firstConstraint, secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the AndConstraint
	 * @param secondConstraint is the second constraint included in the AndConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public AndPredicate(AbstractPredicate<S> firstConstraint, AbstractPredicate<S> secondConstraint){
		 super(firstConstraint, secondConstraint);
	 }
	 /**
     * Initializes a newly created AndConstraint 
     */
	 public AndPredicate() {
		 super();
	 }
	 /**
	 * creates a new AndConstraint that contains the constraint firstConstraint and the set of constraint secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the AndConstraint
	 * @param secondConstraint is the set of constraints to be included in the AndConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public AndPredicate(AbstractPredicate<S> firstConstraint, List<AbstractPredicate<S>> secondConstraint) {
		super(firstConstraint, secondConstraint);   
	 }
	 
	 public AndPredicate(List<AbstractPredicate<S>> firstConstraint, AbstractPredicate<S> secondConstraint) {
	        super(firstConstraint, secondConstraint);
	 }
	 public AndPredicate(List<AbstractPredicate<S>> firstConstraint, List<AbstractPredicate<S>> secondConstraint) {
		 super(firstConstraint, secondConstraint);
	 }
		   
	
    public AbstractPredicate<S> getFistPredicate(){
    	return value.get(0);
    }
    public AbstractPredicate<S> getLastPredicate(){
    	return value.get(value.size()-1);
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
						
						AndPredicate<S> cret=new AndPredicate<S>();
						cret.addConstraints(this.getPredicates().subList(0, this.getPredicates().size()-1));
						cret.addConstraint(new Predicate<S>(((Predicate<S>)this.getLastPredicate()).getState(), ((Predicate<S>)this.getLastPredicate()).getRegularExpression().concat(((Predicate<S>)a).getRegularExpression())));
						return cret;
				}
			else{
				return new AndPredicate<S>(this.getPredicates(), a);
			}
		}
		// if a is an or constraint a new and constraint that contains the constraint of this and constraint and the or constraint is generated
		if(a instanceof OrPredicate){
			return new AndPredicate<S>(this.getPredicates(), a);
		}
		// if a is an and constraint and  the last predicate of this constraint and the first predicate of a have the same state, their regular expressions are merged
		// if a is an and constraint and  the last predicate of this constraint and the first predicate of do not have the same state, a new and constraint
		// that contains all of the constraints of these two and constraints is generated.
		if(a instanceof AndPredicate){
			AndPredicate<S> c=(AndPredicate<S>)a;
			
			if((c.getFistPredicate() instanceof Predicate) && (this.getLastPredicate() instanceof Predicate) &&
				((Predicate<S>)c.getFistPredicate()).getState().equals(((Predicate<S>)this.getLastPredicate()).getState())){
				AndPredicate<S> cret=new AndPredicate<S>();
				cret.addConstraints(this.getPredicates().subList(0, this.getPredicates().size()-1));
				cret.addConstraint(new Predicate<S>(((Predicate<S>)this.getLastPredicate()).getState(), ((Predicate<S>)this.getLastPredicate()).getRegularExpression()+((Predicate<S>)c.getFistPredicate()).getRegularExpression()));
				cret.addConstraints(c.getPredicates().subList(1, c.getPredicates().size()));
				return cret;
			}
			else{
				return new AndPredicate<S>(this.getPredicates(), ((ConstraintLanguage<S>) a).getPredicates());
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
			return new OrPredicate<S>(this, a);
		}

		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}
	@Override
	public String toString() {
		String ret="";
		boolean inserted=false;
		for(int i=0; i<this.value.size()-1;i++){
			if(inserted){
				ret="("+ret+")^("+value.get(i)+")";
			}
			else{
				inserted=true;
				ret=value.get(i).toString();
			}
		}
		if(inserted){
			return "("+ret+"^("+value.get(this.value.size()-1)+")"+")";
		}
		else{
			return "("+value.get(this.value.size()-1).toString()+")";
		}
	}
	@Override
	public AbstractPredicate<S> omega() {
		return this;
	}
	
	public AbstractPredicate<S> simplify(){
		AndPredicate<S> ret=new AndPredicate<S>();
		List<AbstractPredicate<S>> value=new ArrayList<AbstractPredicate<S>>();
		for(AbstractPredicate<S> p: this.value){
			if(!p.equals(new EpsilonPredicate<S>()) && !p.equals(new LambdaPredicate<S>())){
				value.add(p);
			}
		}
		if(value.size()>1){
			ret.addConstraints(value);
			return ret;
		}
		else{
			return value.get(0);
		}
		
	}
	
    
}
