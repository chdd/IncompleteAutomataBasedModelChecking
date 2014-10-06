package it.polimi.model.automata.ba;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

/**
 * @author claudiomenghi
 * contains an automata state. The state is identified by its name
 */
@XmlType
public class State implements Comparable<State>{
	
	/**
	 * contains the name of the state
	 */
	@XmlAttribute(name="id")
	@XmlID
	protected final String name;
	
	/**
	 * creates a new empty state 
	 */
	@SuppressWarnings("unused")
	private State(){
		this.name="";
	}
	
	/**
	 * creates a new intersection state
	 * @param s1 is the state of the first automaton
	 * @param s2 is the state of the second automaton
	 * @param number is the number of the state
	 * @throws IllegalArgumentException is generated if the state s1 or the state s2 is null or if the number is different from 0,1,2
	 */
	protected State(State s1, State s2, int number) {
		if(s1==null){
			throw new IllegalArgumentException("The state s1 cannot be null");
		}
		if(s2==null){
			throw new IllegalArgumentException("The state s2 cannot be null");
		}
		if(!(number>=0 && number<=2)){
			throw new IllegalArgumentException("the number of the state must be equal to 0,1 or 2");
		}
		this.name=s1.getName()+"-"+s2.getName()+"-"+number;
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

	@Override
	public int compareTo(State o) {

		return this.name.compareTo(o.name);
	}
	
}
