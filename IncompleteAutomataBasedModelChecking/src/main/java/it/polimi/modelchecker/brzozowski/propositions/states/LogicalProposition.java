package it.polimi.modelchecker.brzozowski.propositions.states;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicalProposition<CONSTRAINTELEMENT extends State, TRANSITION extends LabelledTransition<CONSTRAINTELEMENT>> extends LogicalItem<CONSTRAINTELEMENT, TRANSITION>{

	
	
	/** The value is used for character storage. */
    private List<LogicalItem<CONSTRAINTELEMENT, TRANSITION>> value;
    
    /**
	 * returns the list of the {@link AbstractProposition} associated with the {@link AndProposition}
	 * @return the list of the {@link AbstractProposition} associated with the {@link AndProposition}
	 */
	public List<LogicalItem<CONSTRAINTELEMENT, TRANSITION>> getPredicates(){
	   return this.value;
    }
	
	 /**
     * Initializes a newly created {@link AndProposition} starting from the list l of {@link AbstractProposition} 
     * @param l: is the list of {@link AbstractProposition} to be added in the and predicate
     * @throws IllegalArgumentException if the list of the {@link AbstractProposition} contains less than 2 {@link AbstractProposition}
     */
	 public LogicalProposition(List<LogicalItem<CONSTRAINTELEMENT, TRANSITION>> l) {
		this.value=l;
        if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
        }
	 }
	 
	 /**
	 * creates a new {@link AndProposition} that contains the two {@link AbstractProposition} firstPredicate, secondPredicate 
	 * @param firstPredicate is the first {@link AbstractProposition} to be included in the {@link AndProposition}
	 * @param secondPredicate is the second {@link AbstractProposition} included in the {@link AndProposition}
	 * @throws IllegalArgumentException is the first or the second {@link AbstractProposition} are null
	 */
	 public LogicalProposition(LogicalItem<CONSTRAINTELEMENT, TRANSITION> firstPredicate, LogicalItem<CONSTRAINTELEMENT, TRANSITION> secondPredicate){
	 	 if(firstPredicate==null){
    		throw new IllegalArgumentException("The first constraint cannot be null");
    	}
    	if(secondPredicate==null){
    		throw new IllegalArgumentException("The second constraint cannot be null");
    	}
        this.value = new ArrayList<LogicalItem<CONSTRAINTELEMENT, TRANSITION>>();
        this.value.add(firstPredicate);
        this.value.add(secondPredicate);
        if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
        }
	 }
	 /**
	 * @see {@link AbstractProposition}
	 */
    @Override
	public String toString() {
		String ret="";
		int inserted=0;
		List<LogicalItem<CONSTRAINTELEMENT, TRANSITION>> value=getPredicates();
		for(int i=0; i<value.size();i++){
			if(!(value.get(i) instanceof EpsilonProposition)){
				if(inserted>0){
					if(inserted==1){
						inserted=2;
						ret="("+ret+")"+this.getType()+"("+value.get(i)+")";
					}
					else{
						ret=ret+this.getType()+"("+value.get(i)+")";
					}
				}
				else{
					inserted=1;
					ret=value.get(i).toString();
				}
			}
			
		}
		return ret;
	}
    public abstract String getType();

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		LogicalProposition<CONSTRAINTELEMENT, TRANSITION> other = (LogicalProposition<CONSTRAINTELEMENT, TRANSITION>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
    
    
}
