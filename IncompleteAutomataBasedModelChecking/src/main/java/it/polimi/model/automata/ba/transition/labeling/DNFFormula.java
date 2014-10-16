package it.polimi.model.automata.ba.transition.labeling;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

public class DNFFormula {
	
	private static final String orSimbol="||";
	
	private Set<ConjunctiveClause> disjunctionClause;
	
	
	public Set<ConjunctiveClause> getCommonClauses(DNFFormula formula){
		Set<ConjunctiveClause> commonClauses=new HashSet<ConjunctiveClause>();
		commonClauses.addAll(this.disjunctionClause);
		commonClauses.retainAll(formula.getConjunctiveClauses());
		return commonClauses;
	}
	
	public Set<IGraphProposition> getPropositions(){
		Set<IGraphProposition> dnfPropositons=new HashSet<IGraphProposition>();
		for(ConjunctiveClause c: this.disjunctionClause){
			dnfPropositons.addAll(c.getPropositions());
		}
		return dnfPropositons;
	}
	
	public DNFFormula(){
		this.disjunctionClause=new HashSet<ConjunctiveClause>();
	}
	
	public DNFFormula(Set<ConjunctiveClause> disjunctionClause){
		this.disjunctionClause=disjunctionClause;
	}

	/**
	 * @return the disjunctionClause
	 */
	public Set<ConjunctiveClause> getConjunctiveClauses() {
		return disjunctionClause;
	}

	/**
	 * @param disjunctionClause the disjunctionClause to set
	 */
	public void setDisjunctionClause(Set<ConjunctiveClause> disjunctionClause) {
		if(disjunctionClause==null){
			throw new NullPointerException("The formula to be setted cannot be null");
		}
		this.disjunctionClause = disjunctionClause;
	}
	
	public void addDisjunctionClause(ConjunctiveClause c){
		this.disjunctionClause.add(c);
	}
	
	public String toString(){
		if(disjunctionClause.isEmpty()){
			return "";
		}
		if(disjunctionClause.size()==1){
			return disjunctionClause.iterator().next().toString();
		}
		Iterator<ConjunctiveClause> it=this.disjunctionClause.iterator();
		String ret="";
		for(int i=0;i< this.disjunctionClause.size()-1;i++){
			ret+=it.next().toString()+orSimbol;
		}
		ret+=it.next().toString();
		return ret;
	}
	
	public static DNFFormula loadFromString(String formula){
		DNFFormula ret=new DNFFormula();
		formula+=orSimbol;
		String[] andClauses=formula.split(Pattern.quote(orSimbol));
		for(int i=0; i<andClauses.length; i++){
			ret.addDisjunctionClause(ConjunctiveClause.loadFromString(andClauses[i]));
		}
		return ret;
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
				+ ((disjunctionClause == null) ? 0 : disjunctionClause
						.hashCode());
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
		DNFFormula other = (DNFFormula) obj;
		if (disjunctionClause == null) {
			if (other.disjunctionClause != null)
				return false;
		} else if (!disjunctionClause.equals(other.disjunctionClause))
			return false;
		return true;
	}
	
}
