package it.polimi.model.impl.labeling;

import it.polimi.model.interfaces.labeling.ConjunctiveClause;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;


/**
 * contains the a Formula in the Disjunctive Normal Form which labels a transition 
 * 
 * @author claudiomenghi
 */
public class DNFFormula {
	
	/**
	 * is the or operator which separates the different {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	private static final String orSimbol="||";
	
	/**
	 * contains the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	private Set<ConjunctiveClause> disjunctionClause;
	
	/**
	 * creates a new empty {@link DNFFormula}
	 */
	public DNFFormula(){
		this.disjunctionClause=new HashSet<ConjunctiveClause>();
	}
	
	/**
	 * creates a new {@link DNFFormula} with the clauses specified as parameter
	 * @param conjunctiveClauses contains the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @throws NullPointerException if the set of {@link ConjunctiveClauseImpl} is null
	 */
	public DNFFormula(Set<ConjunctiveClause> conjunctiveClauses){
		if(conjunctiveClauses==null){
			throw new NullPointerException("The set of disjunctionClause cannot be null");
		}
		this.disjunctionClause=conjunctiveClauses;
	}
	
	/**
	 * creates a new {@link DNFFormula} with a single {@link ConjunctiveClauseImpl} conjunctiveClause
	 * @param conjunctiveClause is the only {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	public DNFFormula(ConjunctiveClauseImpl conjunctiveClause){
		if(conjunctiveClause==null){
			throw new NullPointerException("The set of disjunctionClause cannot be null");
		}
		this.disjunctionClause=new HashSet<ConjunctiveClause>();
		this.disjunctionClause.add(conjunctiveClause);
	}

	/**
	 * return the {@link Set} of the {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @return the {@link Set} o {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	public Set<ConjunctiveClause> getConjunctiveClauses() {
		return disjunctionClause;
	}
	
	/**
	 * sets the {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @param disjunctionClause is the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @throws NullPointerException if the {@link Set} of {@link ConjunctiveClauseImpl} is null
	 */
	public void setDisjunctionClause(Set<ConjunctiveClause> disjunctionClause) {
		if(disjunctionClause==null){
			throw new NullPointerException("The formula to be setted cannot be null");
		}
		this.disjunctionClause = disjunctionClause;
	}
	
	/**
	 * add the {@link ConjunctiveClauseImpl} c to the set of the {@link ConjunctiveClauseImpl} of the {@link DNFFormula} formula
	 * @param clause is the clause to be added in the {@link Set} of {@link ConjunctiveClauseImpl}
	 * @throws NullPointerException if the clause is null
	 */
	public void addDisjunctionClause(ConjunctiveClause clause){
		if(clause==null){
			throw new NullPointerException("The clause to be added in the set of the clause cannot be null");
		}
		this.disjunctionClause.add(clause);
	}
	
	public void addDisjunctionClause(Set<ConjunctiveClause> clauses){
		if(clauses==null){
			throw new NullPointerException("The clause to be added in the set of the clause cannot be null");
		}
		this.disjunctionClause.addAll(clauses);
	}
	
	/**
	 * returns the {@link Set} of {@link IGraphProposition} involved in the {@link DNFFormula}
	 * @return the {@link Set} of {@link IGraphProposition} involved in the {@link DNFFormula}
	 */
	public Set<GraphProposition> getPropositions(){
		Set<GraphProposition> dnfPropositons=new HashSet<GraphProposition>();
		for(ConjunctiveClause c: this.disjunctionClause){
			if(c instanceof ConjunctiveClauseImpl){
				dnfPropositons.addAll(((ConjunctiveClauseImpl)c).getPropositions());
			}
			
		}
		return dnfPropositons;
	}
	
	/**
	 * returns the {@link Set} of {@link ConjunctiveClauseImpl} in common between this {@link DNFFormula} and the {@link DNFFormula} formula
	 * @param formula is the formula to be considered to analyze the set of common {@link ConjunctiveClauseImpl}
	 * @return the {@link Set} of {@link ConjunctiveClauseImpl} in common between this {@link DNFFormula} and the {@link DNFFormula} formula
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 */
	public Set<ConjunctiveClause> getCommonClauses(DNFFormula formula){
		if(formula==null){
			throw new NullPointerException("The formula cannot be null");
		}
		Set<ConjunctiveClause> commonClauses=new HashSet<ConjunctiveClause>();
		commonClauses.addAll(this.disjunctionClause);
		commonClauses.retainAll(formula.getConjunctiveClauses());
		return commonClauses;
	}
	
	/**
	 * load the {@link DNFFormula} from its {@link String} representation
	 * @param formula is the {@link String} representation of the {@link DNFFormula}
	 * @return the {@link DNFFormula} which corresponds to the {@link String} formula
	 * @throws NullPointerException if the formula is null
	 */
	public static DNFFormula loadFromString(String formula){
		
		
		if(formula==null){
			throw new NullPointerException("The formula cannot be null");
		}
		
		DNFFormula ret=new DNFFormula();
		formula+=orSimbol;
		String[] andClauses=formula.split(Pattern.quote(orSimbol));
		for(int i=0; i<andClauses.length; i++){
			ret.addDisjunctionClause(ConjunctiveClauseImpl.loadFromString(andClauses[i]));
		}
		return ret;
	}
	
	/**
	 * returns the {@link String} representation of the {@link DNFFormula}
	 * @return the {@link String} representation of the {@link DNFFormula}
	 */
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
