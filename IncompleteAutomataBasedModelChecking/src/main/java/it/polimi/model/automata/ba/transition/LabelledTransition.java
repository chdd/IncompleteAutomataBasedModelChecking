package it.polimi.model.automata.ba.transition;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Claudio Menghi
 * contains an automata transition. The transition contains the character that labels the transition and the destination state
 * 
 */
public class LabelledTransition{
	
	private final int id;
	/**
	 * the character that labels the transition
	 */
	private Set<String> characters;
	
	/** 
	 * Constructs a new singleton interval transition. 
	 * @param c transition character: is the character that labels the transition
	 * @param to destination state: is the destination of the transition
	 * @throws IllegalArgumentException is generated is the character that labels the transition is null or if the destination state is null
	 */
	protected LabelledTransition(Set<String> c, int id)	{
		this.id=id;
		if(c==null){
			throw new IllegalArgumentException("The character that labels the transition cannot be null");
		}
		this.characters=c;
	}

	public void addCharacter(String character){
		this.characters.add(character);
	}
	/**
	 * @return the character that labels the transition
	 */
	public Set<String> getCharacter() {
		return characters;
	}
	
	public void removeCharacter(String character){
		if(this.characters.contains(character)){
			this.characters.remove(character);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(this.characters.size()==0){
			return "Id: {"+Integer.toString(this.id)+"} Labels:{}";
		}
		if(this.characters.size()==1){
			return "Id: {"+Integer.toString(this.id)+"}\t Labels:{"+this.characters.iterator().next()+"}";
		}
		else{
			Iterator<String> it=this.characters.iterator();
			String ret=it.next();
			while(it.hasNext()){
				String retadd=it.next();
				ret+="^"+retadd;
			}
			return "Id: {"+Integer.toString(this.id)+"}\t Labels:{"+ret+"}";
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
