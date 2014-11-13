package it.polimi.model.impl.transitions;

import it.polimi.model.impl.labeling.ConstraintProposition;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

/**
 * @author Claudio Menghi
 * contains an automata transition. The transition contains the character that labels the transition and the destination state
 * 
 */
public class LabelledTransition<STATE extends State>{
	
	/**
	 * contains the id of the {@link LabelledTransition}
	 */
	private final int id;

	/**
	 * contains the {@link DNFFormula} which labels the {@link LabelledTransition}
	 */
	private DNFFormula<STATE> dnfFormula;
	
	/** 
	 * Constructs a new singleton interval transition. 
	 * @param c transition character: is the character that labels the transition
	 * @param to destination state: is the destination of the transition
	 * @throws IllegalArgumentException is generated is the character that labels the transition is null or if the destination state is null
	 * or if the value of the id is less than zero
	 */
	public LabelledTransition(DNFFormula<STATE> dnfFormula, int id){
		if(id<0){
			throw new IllegalArgumentException("The value of the id cannot be less than zero");
		}
		this.id=id;
		if(dnfFormula==null){
			throw new IllegalArgumentException("The character that labels the transition cannot be null");
		}
		this.dnfFormula=dnfFormula;
	}

	/**
	 * @return the character that labels the transition
	 */
	public DNFFormula<STATE> getDnfFormula() {
		return this.dnfFormula;
	}
	
	/**
	 * sets the {@link DNFFormula} that labels the transition
	 * @param dnfFormula the {@link DNFFormula} to be added as a label of the {@link LabelledTransition}
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 */
	public void setDNFFormula(DNFFormula<STATE> dnfFormula){
		if(dnfFormula==null){
			throw new NullPointerException("The DNFFormula cannot be null");
		}
		this.dnfFormula=dnfFormula;
	}
	
	/**
	 * returns the if of the {@link LabelledTransition}
	 * @return the id of the {@link LabelledTransition}
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{"+Integer.toString(this.id)+"} "+this.dnfFormula.toString()+"";
	}
	
	public LogicalItem<STATE, LabelledTransition<STATE>> toLogicItem(){
		
		for(ConjunctiveClause<STATE> c: this.dnfFormula.getConjunctiveClauses()){
			if(c instanceof ConstraintProposition){
				return new AtomicProposition<STATE, LabelledTransition<STATE>>(this, 
						((ConstraintProposition<STATE>) c).getConstrainedState(), 
						c.toString(), false);
				
			}
		}
		return null;
	}

}
