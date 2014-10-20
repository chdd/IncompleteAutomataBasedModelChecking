package it.polimi.model.impl.labeling;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * contains a Conjunctive clause, i.e., a set of propositions {@link IGraphProposition} separated by the and operator 
 * @author claudiomenghi
 *
 */
public class ConjunctiveClause {
	
	/**
	 * is the operator of the conjunctive clause
	 */
	private static final String andSymbol="&&";
	
	/**
	 * is the set of propositions {@link IGraphProposition} which are separated by the and operator
	 */
	private Set<IGraphProposition> propositions;

	/**
	 * creates a new conjunctive clause
	 */
	public ConjunctiveClause(){
		propositions=new HashSet<IGraphProposition>();
	}
	
	/**
	 * @return the {@link Set} of the propositions ( {@link IGraphProposition}) of the conjunctive clause
	 */
	public Set<IGraphProposition> getPropositions() {
		return propositions;
	}

	/**
	 * set the {@link IGraphProposition} of the conjunctive clause
	 * @param propositions contains the set of propositions to be set in the conjunctive clause
	 * @throws NullPointerException if the set of propositions to be set is null
	 */
	public void setPropositions(Set<IGraphProposition> propositions) {
		if(propositions==null){
			throw new NullPointerException("The set of propositions to be setted cannot be null");
		}
		this.propositions = propositions;
	}
	
	/**
	 * add the {@link IGraphProposition} proposition in the set of the propositions of the conjunctive clause
	 * @param proposition is the proposition to be added in the set of the propositions
	 * @throws NullPointerException if the proposition to be added is null
	 */
	public void addProposition(IGraphProposition proposition){
		if(proposition==null){
			throw new NullPointerException("The proposition to be added cannot be null");
		}
		this.propositions.add(proposition);
	}
	
	/**
	 * convert the conjunctive clause into its {@link String} representation
	 * @return the {@link String} description of the {@link ConjunctiveClause}
	 */
	public String toString(){
		if(propositions.isEmpty()){
			return "";
		}
		if(propositions.size()==1){
			return propositions.iterator().next().toString();
		}
		Iterator<IGraphProposition> it=this.propositions.iterator();
		String ret="";
		for(int i=0;i< this.propositions.size()-1;i++){
			ret+=it.next().toString()+andSymbol;
		}
		ret+=it.next().toString();
		return ret;
	}
	
	/**
	 * loads the {@link ConjunctiveClause} from its {@link String} representation
	 * @param clause is the {@link String} representation of the clause
	 * @return the {@link ConjunctiveClause} loaded from the {@link String}
	 */
	public static ConjunctiveClause loadFromString(String clause){
		
		clause+=andSymbol;
		ConjunctiveClause ret=new ConjunctiveClause();
		String[] andClauses=clause.split(andSymbol);
		for(int i=0; i<andClauses.length; i++){
			ret.addProposition(Proposition.loadProposition(andClauses[i]));
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
		result = prime * result
				+ ((propositions == null) ? 0 : propositions.hashCode());
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
		ConjunctiveClause other = (ConjunctiveClause) obj;
		if (propositions == null) {
			if (other.propositions != null)
				return false;
		} else if (!propositions.equals(other.propositions))
			return false;
		return true;
	}
}
