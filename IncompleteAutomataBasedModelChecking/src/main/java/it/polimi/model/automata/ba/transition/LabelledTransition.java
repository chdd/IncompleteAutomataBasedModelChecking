package it.polimi.model.automata.ba.transition;

import it.polimi.model.automata.ba.transition.labeling.DNFFormula;

/**
 * @author Claudio Menghi
 * contains an automata transition. The transition contains the character that labels the transition and the destination state
 * 
 */
public class LabelledTransition{
	
	private final int id;

	private DNFFormula dnfFormula;
	
	 
	
	/** 
	 * Constructs a new singleton interval transition. 
	 * @param c transition character: is the character that labels the transition
	 * @param to destination state: is the destination of the transition
	 * @throws IllegalArgumentException is generated is the character that labels the transition is null or if the destination state is null
	 */
	public LabelledTransition(DNFFormula dnfFormula, int id)	{
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Id: {"+Integer.toString(this.id)+"} DNFFormula:{"+this.dnfFormula.toString()+"}";
	}

	public static LabelledTransition parseString(String transition){
		String idString=transition.substring(transition.indexOf("{"), transition.lastIndexOf("} DNFFormula:{"));
		String dnfFormulaString=transition.substring(transition.lastIndexOf("} DNFFormula:{")+14, transition.lastIndexOf("}"));
		
		return new LabelledTransition(DNFFormula.loadFromString(dnfFormulaString), Integer.parseInt(idString));
	}
	
	public void setDNFFormula(DNFFormula dnfFormula){
		this.dnfFormula=dnfFormula;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
