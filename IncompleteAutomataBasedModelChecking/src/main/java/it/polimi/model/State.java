package it.polimi.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Claudio Menghi
 * contains an automata state. The state is identified by its name
 */
@XmlType
public class State {
	
	/**
	 * contains the name of the state
	 */
	@XmlAttribute(name="id")
	@XmlID
	private String name;
	
	@SuppressWarnings("unused")
	private State(){
		this.name="";
	}
	
	/**
	 * creates a new state with the specified name
	 * @param name: contains the name of the state the name of the state
	 * @throws IllegalArgumentException is raised when the name of the state is null
	 */
	public State(String name){
		super();
		if(name==null){
			throw new IllegalArgumentException("The name of the state cannot be null");
		}
		this.name=name;
	}
	
	/**
	 * returns the name of the state
	 * @return the name of the state
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets the name of the state
	 * @param is the new name of the state
	 */
	protected void setName(String name){
		this.name=name;
	}
	
	@Override
	/**
	 * returns a String representation of the AutomatonState
	 * @return the String representation of the AutomatonState
	 */
	public String toString(){
		return this.getName();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		State other = (State) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
