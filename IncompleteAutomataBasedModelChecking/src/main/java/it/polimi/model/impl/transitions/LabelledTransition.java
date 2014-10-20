package it.polimi.model.impl.transitions;

import it.polimi.model.impl.labeling.DNFFormula;

/**
 * @author Claudio Menghi
 * contains an automata transition. The transition contains the character that labels the transition and the destination state
 * 
 */
public class LabelledTransition{
	
	/**
	 * contains the id of the {@link LabelledTransition}
	 */
	private final int id;

	/**
	 * contains the {@link DNFFormula} which labels the {@link LabelledTransition}
	 */
	private DNFFormula dnfFormula;
	
	/** 
	 * Constructs a new singleton interval transition. 
	 * @param c transition character: is the character that labels the transition
	 * @param to destination state: is the destination of the transition
	 * @throws IllegalArgumentException is generated is the character that labels the transition is null or if the destination state is null
	 * or if the value of the id is less than zero
	 */
	public LabelledTransition(DNFFormula dnfFormula, int id){
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
	public DNFFormula getDnfFormula() {
		return this.dnfFormula;
	}
	
	/**
	 * sets the {@link DNFFormula} that labels the transition
	 * @param dnfFormula the {@link DNFFormula} to be added as a label of the {@link LabelledTransition}
	 * @throws NullPointerException if the {@link DNFFormula} is null
	 */
	public void setDNFFormula(DNFFormula dnfFormula){
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
		return "Id: {"+Integer.toString(this.id)+"} DNFFormula:{"+this.dnfFormula.toString()+"}";
	}

	/**
	 * parses the {@link LabelledTransition} starting from its {@link String} representation
	 * @param transition is the {@link String} representation of the {@link LabelledTransition}
	 * @return the {@link LabelledTransition} parsed from the {@link String}
	 * @throws NullPointerException if the {@link String} is null
	 */
	public static LabelledTransition parseString(String transition){
		if(transition==null){
			throw new NullPointerException("The string which represents the transition cannot be null");
		}
		String idString=transition.substring(transition.indexOf("{"), transition.lastIndexOf("} DNFFormula:{"));
		String dnfFormulaString=transition.substring(transition.lastIndexOf("} DNFFormula:{")+14, transition.lastIndexOf("}"));
		
		return new LabelledTransition(DNFFormula.loadFromString(dnfFormulaString), Integer.parseInt(idString));
	}
}
