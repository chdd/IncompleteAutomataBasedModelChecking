package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicalPredicate<S extends State> extends AbstractPredicate<S>{

	/** The value is used for character storage. */
    private List<AbstractPredicate<S>> value;
    
    /**
	 * returns the list of the {@link AbstractPredicate} associated with the {@link AndPredicate}
	 * @return the list of the {@link AbstractPredicate} associated with the {@link AndPredicate}
	 */
	public List<AbstractPredicate<S>> getPredicates(){
	   return this.value;
    }
	
	 /**
     * Initializes a newly created {@link AndPredicate} starting from the list l of {@link AbstractPredicate} 
     * @param l: is the list of {@link AbstractPredicate} to be added in the and predicate
     * @throws IllegalArgumentException if the list of the {@link AbstractPredicate} contains less than 2 {@link AbstractPredicate}
     */
	 public LogicalPredicate(List<AbstractPredicate<S>> l) {
		this.value=l;
        if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
        }
	 }
	 
	 /**
	 * creates a new {@link AndPredicate} that contains the two {@link AbstractPredicate} firstPredicate, secondPredicate 
	 * @param firstPredicate is the first {@link AbstractPredicate} to be included in the {@link AndPredicate}
	 * @param secondPredicate is the second {@link AbstractPredicate} included in the {@link AndPredicate}
	 * @throws IllegalArgumentException is the first or the second {@link AbstractPredicate} are null
	 */
	 public LogicalPredicate(AbstractPredicate<S> firstPredicate, AbstractPredicate<S> secondPredicate){
	 	if(firstPredicate==null){
    		throw new IllegalArgumentException("The first constraint cannot be null");
    	}
    	if(secondPredicate==null){
    		throw new IllegalArgumentException("The second constraint cannot be null");
    	}
        this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.add(firstPredicate);
        this.value.add(secondPredicate);
        if(this.value.size()<=1){
        	throw new IllegalArgumentException("It is not possible to create a And or Or predicate that contains less than two predicates");
        }
	 }
	 /**
	 * @see {@link AbstractPredicate}
	 */
    @Override
	public String toString() {
		String ret="";
		int inserted=0;
		List<AbstractPredicate<S>> value=getPredicates();
		for(int i=0; i<value.size();i++){
			if(!(value.get(i) instanceof EpsilonPredicate)){
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
}
