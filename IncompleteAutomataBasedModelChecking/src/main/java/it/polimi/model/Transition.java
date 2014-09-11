package it.polimi.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * @author Claudio Menghi
 * contains an automata transition. The transition contains the character that labels the transition and the destination state
 * 
 */
public class Transition<S extends State>{
	
	/**
	 * the character that labels the transition
	 */
	@XmlElement(name="character")
	private final String character;
	
	/**
	 * the destination state of the transition
	 */
	@XmlElement(name="destinationState", type=State.class)
	@XmlIDREF
	private final S state;
	
	/**
	 * creates a new empty transition
	 */
	@SuppressWarnings("unused")
	private Transition(){
		this.character=null;
		this.state=null;
	}
	
	/** 
	 * Constructs a new singleton interval transition. 
	 * @param c transition character: is the character that labels the transition
	 * @param to destination state: is the destination of the transition
	 * @throws IllegalArgumentException is generated is the character that labels the transition is null or if the destination state is null
	 */
	public Transition(String c, S to)	{
		if(c==null){
			throw new IllegalArgumentException("The character that labels the transition cannot be null");
		}
		if(to==null){
			throw new IllegalArgumentException("The destination state of the transition cannot be null");
		}
		this.character=c;
		this.state=to;
	}

	/**
	 * @return the character that labels the transition
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * @return the destination of the transition
	 */
	public S getDestination() {
		return state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return character + "->"+ state;
	}
	
}
