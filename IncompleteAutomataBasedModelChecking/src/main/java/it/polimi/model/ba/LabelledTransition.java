package it.polimi.model.ba;

import it.polimi.model.graph.State;
import it.polimi.model.graph.Transition;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Claudio Menghi
 * contains an automata transition. The transition contains the character that labels the transition and the destination state
 * 
 */
public class LabelledTransition<S extends State> extends Transition<S>{
	
	/**
	 * the character that labels the transition
	 */
	@XmlElement(name="character")
	private final String character;
	
	/**
	 * creates a new empty transition
	 */
	@SuppressWarnings("unused")
	private LabelledTransition(){
		super();
		this.character=null;
	}
	
	/** 
	 * Constructs a new singleton interval transition. 
	 * @param c transition character: is the character that labels the transition
	 * @param to destination state: is the destination of the transition
	 * @throws IllegalArgumentException is generated is the character that labels the transition is null or if the destination state is null
	 */
	public LabelledTransition(String c, S to)	{
		super(to);
		if(c==null){
			throw new IllegalArgumentException("The character that labels the transition cannot be null");
		}
		
		this.character=c;
	}

	/**
	 * @return the character that labels the transition
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return character;
	}
	
}
