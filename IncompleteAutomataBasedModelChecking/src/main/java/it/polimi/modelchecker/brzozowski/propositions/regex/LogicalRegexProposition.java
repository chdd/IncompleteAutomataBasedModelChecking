package it.polimi.modelchecker.brzozowski.propositions.regex;

import java.util.List;

public class LogicalRegexProposition implements AbstractRegexProposition {

	/** The value is used for character storage. */
    private List<AbstractRegexProposition> value;
    
    /**
   	 * returns the list of the {@link AbstractRegexProposition} associated with the {@link LogicalRegexProposition}
   	 * @return the list of the {@link AbstractRegexProposition} associated with the {@link LogicalRegexProposition}
   	 */
   	public List<AbstractRegexProposition> getPredicates(){
   	   return this.value;
    }
    
	@Override
	public AbstractRegexProposition concatenate(AbstractRegexProposition a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractRegexProposition union(AbstractRegexProposition a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractRegexProposition star() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractRegexProposition omega() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

}
