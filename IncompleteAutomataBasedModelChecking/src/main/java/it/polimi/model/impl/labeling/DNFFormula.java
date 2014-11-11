package it.polimi.model.impl.labeling;

import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import rwth.i2.ltl2ba4j.model.IGraphProposition;


/**
 * contains the a Formula in the Disjunctive Normal Form which labels a transition 
 * 
 * @author claudiomenghi
 */
public class DNFFormula<CONSTRAINTELEMENT extends State> {
	
	/**
	 * is the or operator which separates the different {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	private static final String orSimbol="||";
	
	/**
	 * contains the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	private Set<ConjunctiveClause<CONSTRAINTELEMENT>> conjunctiveClause;
	
	/**
	 * creates a new empty {@link DNFFormula}
	 */
	public DNFFormula(){
		this.conjunctiveClause=new HashSet<ConjunctiveClause<CONSTRAINTELEMENT>>();
	}
	
	/**
	 * creates a new {@link DNFFormula} with the clauses specified as parameter
	 * @param conjunctiveClauses contains the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @throws NullPointerException if the set of {@link ConjunctiveClauseImpl} is null
	 */
	public DNFFormula(Set<ConjunctiveClause<CONSTRAINTELEMENT>> conjunctiveClauses){
		if(conjunctiveClauses==null){
			throw new NullPointerException("The set of disjunctionClause cannot be null");
		}
		this.conjunctiveClause=conjunctiveClauses;
	}
	
	/**
	 * creates a new {@link DNFFormula} with a single {@link ConjunctiveClauseImpl} conjunctiveClause
	 * @param conjunctiveClause is the only {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	public DNFFormula(ConjunctiveClause<CONSTRAINTELEMENT> conjunctiveClause){
		if(conjunctiveClause==null){
			throw new NullPointerException("The set of disjunctionClause cannot be null");
		}
		this.conjunctiveClause=new HashSet<ConjunctiveClause<CONSTRAINTELEMENT>>();
		this.conjunctiveClause.add(conjunctiveClause);
	}

	/**
	 * return the {@link Set} of the {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @return the {@link Set} o {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 */
	public Set<ConjunctiveClause<CONSTRAINTELEMENT>> getConjunctiveClauses() {
		return conjunctiveClause;
	}
	
	/**
	 * sets the {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @param disjunctionClause is the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormula}
	 * @throws NullPointerException if the {@link Set} of {@link ConjunctiveClauseImpl} is null
	 */
	public void setDisjunctionClause(Set<ConjunctiveClause<CONSTRAINTELEMENT>> disjunctionClause) {
		if(disjunctionClause==null){
			throw new NullPointerException("The formula to be setted cannot be null");
		}
		this.conjunctiveClause = disjunctionClause;
	}
	
	/**
	 * add the {@link ConjunctiveClauseImpl} c to the set of the {@link ConjunctiveClauseImpl} of the {@link DNFFormula} formula
	 * @param clause is the clause to be added in the {@link Set} of {@link ConjunctiveClauseImpl}
	 * @throws NullPointerException if the clause is null
	 */
	public void addDisjunctionClause(ConjunctiveClause<CONSTRAINTELEMENT> clause){
		if(clause==null){
			throw new NullPointerException("The clause to be added in the set of the clause cannot be null");
		}
		this.conjunctiveClause.add(clause);
	}
	
	public void addDisjunctionClause(Set<ConjunctiveClause<CONSTRAINTELEMENT>> clauses){
		if(clauses==null){
			throw new NullPointerException("The clause to be added in the set of the clause cannot be null");
		}
		this.conjunctiveClause.addAll(clauses);
	}
	
	/**
	 * returns the {@link Set} of {@link IGraphProposition} involved in the {@link DNFFormula}
	 * @return the {@link Set} of {@link IGraphProposition} involved in the {@link DNFFormula}
	 */
	public Set<Proposition> getPropositions(){
		Set<Proposition> dnfPropositons=new HashSet<Proposition>();
		for(ConjunctiveClause<CONSTRAINTELEMENT> c: this.conjunctiveClause){
			if(c instanceof ConjunctiveClauseImpl){
				dnfPropositons.addAll(((ConjunctiveClauseImpl<CONSTRAINTELEMENT>)c).getPropositions());
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
	public Set<ConjunctiveClause<CONSTRAINTELEMENT>> getCommonClauses(DNFFormula<CONSTRAINTELEMENT> formula){
		if(formula==null){
			throw new NullPointerException("The formula cannot be null");
		}
		Set<ConjunctiveClause<CONSTRAINTELEMENT>> commonClauses=new HashSet<ConjunctiveClause<CONSTRAINTELEMENT>>();
		commonClauses.addAll(this.conjunctiveClause);
		commonClauses.retainAll(formula.getConjunctiveClauses());
		return commonClauses;
	}
	
	/**
	 * load the {@link DNFFormula} from its {@link String} representation
	 * @param formula is the {@link String} representation of the {@link DNFFormula}
	 * @return the {@link DNFFormula} which corresponds to the {@link String} formula
	 * @throws NullPointerException if the formula is null
	 */
	public static <STATE extends State> DNFFormula<STATE> loadFromString(String formula){
		
		
		if(formula==null){
			throw new NullPointerException("The formula cannot be null");
		}
		
		DNFFormula<STATE> ret=new DNFFormula<STATE>();
		formula+=orSimbol;
		String[] andClauses=formula.split(Pattern.quote(orSimbol));
		for(int i=0; i<andClauses.length; i++){
			ret.addDisjunctionClause(ConjunctiveClauseImpl.<STATE>loadFromString(andClauses[i]));
		}
		return ret;
	}
	
	/**
	 * returns the {@link String} representation of the {@link DNFFormula}
	 * @return the {@link String} representation of the {@link DNFFormula}
	 */
	public String toString(){
		if(conjunctiveClause.isEmpty()){
			return "";
		}
		if(conjunctiveClause.size()==1){
			return conjunctiveClause.iterator().next().toString();
		}
		Iterator<ConjunctiveClause<CONSTRAINTELEMENT>> it=this.conjunctiveClause.iterator();
		String ret="";
		for(int i=0;i< this.conjunctiveClause.size()-1;i++){
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
				+ ((conjunctiveClause == null) ? 0 : conjunctiveClause
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
		@SuppressWarnings("unchecked")
		DNFFormula<CONSTRAINTELEMENT> other = (DNFFormula<CONSTRAINTELEMENT>) obj;
		if (conjunctiveClause == null) {
			if (other.conjunctiveClause != null)
				return false;
		} else if (!conjunctiveClause.equals(other.conjunctiveClause))
			return false;
		return true;
	}	
}
