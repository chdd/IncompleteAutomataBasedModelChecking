package it.polimi.model.interfaces.labeling;

import it.polimi.model.impl.labeling.Proposition;

import java.util.Set;

/**
 * contains a {@link Formula}
 * @author claudiomenghi
 *
 */
public interface Formula {
	
	/**
	 * returns the {@link Proposition} associated with the {@link Formula}
	 * @return the {@link Proposition} associated with the {@link Formula}
	 */
	public Set<Proposition> getPropositions();
	
	
	/*
	public LogicalItem<CONSTRAINEDELEMENT, Transition<CONSTRAINEDELEMENT>> toLogicItem(){
		
		for(ConjunctiveClause<CONSTRAINEDELEMENT> c: this.condition.getConjunctiveClauses()){
			if(c instanceof ConstraintProposition){
				return new AtomicProposition<CONSTRAINEDELEMENT, Transition<CONSTRAINEDELEMENT>>(this, 
						((ConstraintProposition<CONSTRAINEDELEMENT>) c).getConstrainedState(), 
						c.toString(), false);
				
			}
		}
		return null;
	}*/
}
