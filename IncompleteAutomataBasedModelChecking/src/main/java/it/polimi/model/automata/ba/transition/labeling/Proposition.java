package it.polimi.model.automata.ba.transition.labeling;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class Proposition extends GraphProposition {

	public Proposition(String label, boolean isNegated)
			throws IllegalArgumentException {
		super(label, isNegated);
	}
	
	public String toString(){
		if(this.isNegated()){
			return "!"+this.label;
		}
		else{
			return this.label;
		}
	}
	
	public static Proposition loadProposition(String proposition){
		if(proposition.contains("!")){
			return new Proposition(proposition.substring(1), true);
		}
		else{
			return new Proposition(proposition, false);
		}
	}
	
}
