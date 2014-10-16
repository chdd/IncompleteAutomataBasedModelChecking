package it.polimi.model.automata.ba.transition.labeling;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

public class ConjunctiveClause {
	
	private static final String andSymbol="&&";
	
	private Set<IGraphProposition> propositions;

	public ConjunctiveClause(){
		propositions=new HashSet<IGraphProposition>();
	}
	
	/**
	 * @return the propositions
	 */
	public Set<IGraphProposition> getPropositions() {
		return propositions;
	}

	/**
	 * @param propositions the propositions to set
	 */
	public void setPropositions(Set<IGraphProposition> propositions) {
		this.propositions = propositions;
	}
	
	public void addProposition(IGraphProposition proposition){
		this.propositions.add(proposition);
	}
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
