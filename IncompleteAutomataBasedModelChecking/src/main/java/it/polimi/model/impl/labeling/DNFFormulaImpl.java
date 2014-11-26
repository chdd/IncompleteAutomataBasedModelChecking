package it.polimi.model.impl.labeling;

import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.model.interfaces.labeling.DNFFormula;
import it.polimi.model.interfaces.labeling.Formula;

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
public class DNFFormulaImpl implements DNFFormula{
	
	/**
	 * is the or operator which separates the different {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 */
	private static final String orSimbol="||";
	
	/**
	 * contains the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 */
	private Set<ConjunctiveClause> conjunctiveClause;
	
	/**
	 * creates a new empty {@link DNFFormulaImpl}
	 */
	public DNFFormulaImpl(){
		this.conjunctiveClause=new HashSet<ConjunctiveClause>();
	}
	
	/**
	 * creates a new {@link DNFFormulaImpl} with the clauses specified as parameter
	 * @param conjunctiveClauses contains the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 * @throws NullPointerException if the set of {@link ConjunctiveClauseImpl} is null
	 */
	public DNFFormulaImpl(Set<ConjunctiveClause> conjunctiveClauses){
		if(conjunctiveClauses==null){
			throw new NullPointerException("The set of disjunctionClause cannot be null");
		}
		this.conjunctiveClause=conjunctiveClauses;
	}
	
	/**
	 * creates a new {@link DNFFormulaImpl} with a single {@link ConjunctiveClauseImpl} conjunctiveClause
	 * @param conjunctiveClause is the only {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 */
	public DNFFormulaImpl(ConjunctiveClause conjunctiveClause){
		if(conjunctiveClause==null){
			throw new NullPointerException("The set of disjunctionClause cannot be null");
		}
		this.conjunctiveClause=new HashSet<ConjunctiveClause>();
		this.conjunctiveClause.add(conjunctiveClause);
	}

	/**
	 * return the {@link Set} of the {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 * @return the {@link Set} o {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 */
	public Set<ConjunctiveClause> getConjunctiveClauses() {
		return conjunctiveClause;
	}
	
	/**
	 * sets the {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 * @param disjunctionClause is the set of {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl}
	 * @throws NullPointerException if the {@link Set} of {@link ConjunctiveClauseImpl} is null
	 */
	public void setDisjunctionClause(Set<ConjunctiveClause> disjunctionClause) {
		if(disjunctionClause==null){
			throw new NullPointerException("The formula to be setted cannot be null");
		}
		this.conjunctiveClause = disjunctionClause;
	}
	
	/**
	 * add the {@link ConjunctiveClauseImpl} c to the set of the {@link ConjunctiveClauseImpl} of the {@link DNFFormulaImpl} formula
	 * @param clause is the clause to be added in the {@link Set} of {@link ConjunctiveClauseImpl}
	 * @throws NullPointerException if the clause is null
	 */
	public void addDisjunctionClause(Formula clause){
		if(clause==null){
			throw new NullPointerException("The clause to be added in the set of the clause cannot be null");
		}
		/*if(!(clause instanceof ConjunctiveClause) && !(clause instanceof )){
			throw new IllegalArgumentException("The Formula must be of type ConjunctiveClause");
		}*/
		this.conjunctiveClause.add( (ConjunctiveClause) clause);
	}
	
	public void addDisjunctionClauses(Set<ConjunctiveClause> clauses){
		if(clauses==null){
			throw new NullPointerException("The clause to be added in the set of the clause cannot be null");
		}
		this.conjunctiveClause.addAll(clauses);
	}
	
	/**
	 * returns the {@link Set} of {@link IGraphProposition} involved in the {@link DNFFormulaImpl}
	 * @return the {@link Set} of {@link IGraphProposition} involved in the {@link DNFFormulaImpl}
	 */
	public Set<Proposition> getPropositions(){
		Set<Proposition> dnfPropositons=new HashSet<Proposition>();
		for(ConjunctiveClause c: this.conjunctiveClause){
			if(c instanceof ConjunctiveClauseImpl){
				dnfPropositons.addAll(((ConjunctiveClause)c).getPropositions());
			}
			
		}
		return dnfPropositons;
	}
	
	/**
	 * returns the {@link Set} of {@link ConjunctiveClauseImpl} in common between this {@link DNFFormulaImpl} and the {@link DNFFormulaImpl} formula
	 * @param formula is the formula to be considered to analyze the set of common {@link ConjunctiveClauseImpl}
	 * @return the {@link Set} of {@link ConjunctiveClauseImpl} in common between this {@link DNFFormulaImpl} and the {@link DNFFormulaImpl} formula
	 * @throws NullPointerException if the {@link DNFFormulaImpl} is null
	 */
	public Set<ConjunctiveClause> getCommonClauses(DNFFormulaImpl formula){
		if(formula==null){
			throw new NullPointerException("The formula cannot be null");
		}
		Set<ConjunctiveClause> commonClauses=new HashSet<ConjunctiveClause>();
		commonClauses.addAll(this.conjunctiveClause);
		commonClauses.retainAll(formula.getConjunctiveClauses());
		return commonClauses;
	}
	
	/**
	 * load the {@link DNFFormulaImpl} from its {@link String} representation
	 * @param formula is the {@link String} representation of the {@link DNFFormulaImpl}
	 * @return the {@link DNFFormulaImpl} which corresponds to the {@link String} formula
	 * @throws NullPointerException if the formula is null
	 */
	public static <STATE extends State> DNFFormulaImpl loadFromString(String formula){
		
		
		if(formula==null){
			throw new NullPointerException("The formula cannot be null");
		}
		
		DNFFormulaImpl ret=new DNFFormulaImpl();
		formula+=orSimbol;
		String[] andClauses=formula.split(Pattern.quote(orSimbol));
		for(int i=0; i<andClauses.length; i++){
			ret.addDisjunctionClause(ConjunctiveClauseImpl.loadFromString(andClauses[i]));
		}
		return ret;
	}
	
	/**
	 * returns the {@link String} representation of the {@link DNFFormulaImpl}
	 * @return the {@link String} representation of the {@link DNFFormulaImpl}
	 */
	public String toString(){
		if(conjunctiveClause.isEmpty()){
			return "";
		}
		if(conjunctiveClause.size()==1){
			return conjunctiveClause.iterator().next().toString();
		}
		Iterator<ConjunctiveClause> it=this.conjunctiveClause.iterator();
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
		DNFFormulaImpl other = (DNFFormulaImpl) obj;
		if (conjunctiveClause == null) {
			if (other.conjunctiveClause != null)
				return false;
		} else if (!conjunctiveClause.equals(other.conjunctiveClause))
			return false;
		return true;
	}	
}
