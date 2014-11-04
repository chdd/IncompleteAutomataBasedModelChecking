package it.polimi.model.impl.labeling;

import it.polimi.model.interfaces.labeling.ConjunctiveClause;


public class SigmaProposition implements ConjunctiveClause{

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
		SigmaProposition other = (SigmaProposition) obj;
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
}
