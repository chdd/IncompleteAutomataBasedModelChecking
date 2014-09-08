package it.polimi.modelchecker.brzozowski.predicates;

import it.polimi.model.State;

import java.util.ArrayList;
import java.util.List;

public abstract class ConstraintLanguage<S extends State> extends AbstractPredicate<S> {

	 /** The value is used for character storage. */
    protected List<AbstractPredicate<S>> value;

    public ConstraintLanguage() {
        this.value = new ArrayList<AbstractPredicate<S>>();
    }

	/**
	 * creates a new constraint that contains the two constraint firstConstraint, secondConstraint 
	 * @param firstConstraint is the first constraint to be included in the OrConstraint
	 * @param secondConstraint is the second constraint included in the orConstraint
	 * @throws IllegalArgumentException is the first or the second constraint are null
	 */
    public ConstraintLanguage(AbstractPredicate<S> firstConstraint, AbstractPredicate<S> secondConstraint) {
    	if(firstConstraint==null){
    		throw new IllegalArgumentException("The first constraint cannot be null");
    	}
    	if(secondConstraint==null){
    		throw new IllegalArgumentException("The second constraint cannot be null");
    	}
        this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.add(firstConstraint);
        this.value.add(secondConstraint);
    }
    public ConstraintLanguage(AbstractPredicate<S> firstConstraint, List<AbstractPredicate<S>> secondConstraint) {
        this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.add(firstConstraint);
        this.value.addAll(secondConstraint);
    }
    public ConstraintLanguage(List<AbstractPredicate<S>> firstConstraint, AbstractPredicate<S> secondConstraint) {
        this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.addAll(firstConstraint);
        this.value.add(secondConstraint);
    }
    public ConstraintLanguage(List<AbstractPredicate<S>> firstConstraint, List<AbstractPredicate<S>> secondConstraint) {
        this.value = new ArrayList<AbstractPredicate<S>>();
        this.value.addAll(firstConstraint);
        this.value.addAll(secondConstraint);
    }
    
    /**
	 * add the constraint element at the end of the list
	 * @param constraint the constraint to be added
	 * @throws IllegalArgumentException if the constraint to be added is null
	 */
    public void addConstraint(AbstractPredicate<S> constraint){
    	if(constraint==null){
    		throw new IllegalArgumentException("The constraint to be added cannot be null");
    	}
    	this.value.add(constraint);
    }
    /**
     * add the constraint element at the end of the list
     * @param constraints the constraint to be added
     * @throws IllegalArgumentException if the set of constraints to be added is null
     */
    public void addConstraints(List<AbstractPredicate<S>> constraints){
    	if(constraints==null){
    		throw new IllegalArgumentException("The constraint to be added cannot be null");
    	}
    	this.value.addAll(constraints);
    }
   public List<AbstractPredicate<S>> getConstraints(){
	   return this.value;
   }

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
		ConstraintLanguage<S> other = (ConstraintLanguage<S>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
   
   
}
