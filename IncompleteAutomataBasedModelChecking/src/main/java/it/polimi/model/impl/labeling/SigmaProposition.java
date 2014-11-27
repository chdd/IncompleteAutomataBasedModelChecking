package it.polimi.model.impl.labeling;

import java.util.Set;

import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;


public class SigmaProposition<STATE extends State> implements ConjunctiveClause{

	private final String sigma="SIGMA";
	
	public SigmaProposition() {
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigma == null) ? 0 : sigma.hashCode());
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
		SigmaProposition<STATE> other = (SigmaProposition<STATE>) obj;
		if (sigma == null) {
			if (other.sigma != null)
				return false;
		} else if (!sigma.equals(other.sigma))
			return false;
		return true;
	}


	@Override
	public String toString(){
		return this.sigma;
	}


	@Override
	public void addProposition(Proposition proposition) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Set<Proposition> getPropositions() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean satisfies(ConjunctiveClause conjunctiveClause) {
		if(conjunctiveClause instanceof SigmaProposition){
			return true;
		}
		return false;
	}
}
