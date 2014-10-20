package it.polimi.model.impl.labeling;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * contains a new Proposition
 * @author claudiomenghi
 */
public class Proposition extends GraphProposition {

	/**
	 * creates a new {@link Proposition}
	 * @param label is the label of the {@link Proposition} and have to match the regular expression [a-z][a-z0-9]++ 
	 * @param isNegated is true if the atomic {@link Proposition} is negated, false otherwise
	 * @throws IllegalArgumentException if the label does not match the regular expression
	 */
	public Proposition(String label, boolean isNegated){
		super(label, isNegated);
		if(!label.matches("[a-z][a-z0-9]++")) {
	       throw new IllegalArgumentException("proposition must be of form [a-z][a-z0-9]++ (got "+label+")");
	   }
	}
	
	/**
	 * return the {@link String} representation of the {@link GraphProposition}
	 * @return the {@link String} representation of the {@link GraphProposition}
	 */
	public String toString(){
		if(this.isNegated()){
			return "!"+this.label;
		}
		else{
			return this.label;
		}
	}
	
	/**
	 * @param proposition is the {@link String} representation of the proposition. The representation must match the regular expression [a-z][a-z0-9]++ 
	 * @return the object which represent the {@link Proposition}
	 * @throws NullPointerException if the proposition is null
	 * @throws IllegalArgumentException if the proposition does not match the regular expression [a-z][a-z0-9]++ 
	 */
	public static Proposition loadProposition(String proposition){
		if(proposition==null){
			throw new NullPointerException("The String representation of the proposition cannot be null");
		}
		if(!proposition.matches("[a-z][a-z0-9]++")) {
		       throw new IllegalArgumentException("proposition must be of form [a-z][a-z0-9]++ (got "+proposition+")");
		}
		if(proposition.contains("!")){
			return new Proposition(proposition.substring(1), true);
		}
		else{
			return new Proposition(proposition, false);
		}
	}
}
