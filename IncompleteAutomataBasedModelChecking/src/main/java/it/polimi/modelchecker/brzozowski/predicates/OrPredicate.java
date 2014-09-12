package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Claudio Menghi
 * contains an OrConstraint
 */
public class OrPredicate<S extends State> extends ConstraintLanguage<S> {

	/**
	 * creates a new OrConstraint that contains the two Constraints firstConstraint, secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the OrConstraint
	 * @param secondConstraint is the second constraint included in the orConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public OrPredicate(AbstractPredicate<S> firstConstraint, AbstractPredicate<S> secondConstraint){
		 super(firstConstraint, secondConstraint);
	 }
	 /**
	  * creates a new empty constraint
	  */
	 public OrPredicate() {
		 super();
	 }
	 /**
	 * creates a new OrConstraint that contains the constraint firstConstraint and the set of constraint secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the OrConstraint
	 * @param secondConstraint is the set of constraints to be included in the OrConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
	 public OrPredicate(AbstractPredicate<S> firstConstraint, List<AbstractPredicate<S>> secondConstraint) {
		super(firstConstraint, secondConstraint);   
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
			return new AndPredicate<S>(this, a);
		}
		// if a is an AndConstraint the concatenation of the or constraint and the AndConstraint is returned
		if(a instanceof AndPredicate){
			return new AndPredicate<S>(this, ((AndPredicate<S>) a).getConstraints());
		}
		// if a is an OrConstraint the concatenation of the or constraint and the OrConstraint is returned
		if(a instanceof OrPredicate){
			return new AndPredicate<S>(this, a);
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
		if(a==null){
			throw new IllegalArgumentException("The constraint to be concatenated cannot be null");
		}
		// the union of an or constraint and an empty constraint returns the or constraint
		if(a instanceof EmptyPredicate){
			return this;
		}
		// the union of an or constraint and a LambdaConstraint is a new orConstraint that contains the or constraint and lambda
		if(a instanceof LambdaPredicate){
			return new OrPredicate<>(this,a);
		}
		// the union of an or constraint and an EpsilonConstrain is a new orConstraint that contains the or constraint and the EpsilonConstrain
		if(a instanceof EpsilonPredicate){
			return new OrPredicate<>(this,a);
		}
		// the union of an or constraint and a Predicate is a new orConstraint that contains the or constraint and the Predicate
		if(a instanceof Predicate){
			return new OrPredicate<S>(this,a);
		}
		// the union of an or constraint and an orConstraint is a new orConstraint that contains the two or constraints
		if(a instanceof OrPredicate){
			return new OrPredicate<S>(this,a);
		}
		// the union of an or constraint and an andConstraint is a new orConstraint that contains the or constraint and the andConstraint
		if(a instanceof AndPredicate){
			return new OrPredicate<S>(this,a);
		}
		throw new IllegalArgumentException("The type:"+a.getClass()+" of the constraint is not in the set of the predefined types");
	}

	/**
	 * see {@link AbstractPredicate}
	 */
	@Override
	public String toString() {
		String ret="";
		boolean inserted=false;
		for(int i=0; i<this.value.size()-1;i++){
			if(inserted){
				ret="("+ret+")v("+value.get(i)+")";
			}
			else{
				inserted=true;
				ret=value.get(i).toString();
			}
		}
		if(inserted){
			return "("+ret+"v("+value.get(this.value.size()-1)+")"+")";
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
		OrPredicate<S> ret=new OrPredicate<S>();
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
