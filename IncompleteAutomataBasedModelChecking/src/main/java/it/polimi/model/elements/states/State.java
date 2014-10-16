package it.polimi.model.elements.states;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.BAImpl;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.NodeMetadata;


/**
 * @author claudiomenghi
 * contains an automata state. The state is identified by its name
 */
public class State implements Comparable<State>{
	
	/**
	 * contains the id of the state
	 */
	protected final int id;
	/**
	 * contains the name of the state
	 */
	protected String name;
	
	/**
	 * creates a state with the specified id and an empty name
	 * @param id is the id of the state
	 * @throws IllegalArgumentException if the value of the id is less than 0
	 */
	protected State(int id){
		if(id<0){
			throw new IllegalArgumentException("The id cannot be <0");
		}
		this.id=id;
		this.name="";
	}
	
	/**
	 * creates a new state with the specified name
	 * @param name: contains the name of the state the name of the state
	 * @throws IllegalArgumentException if the value of the id is less than 0
	 * @throws IllegalArgumentException is raised when the name of the state is null
	 */
	protected State(String name, int id){
		this(id);
		if(name==null){
			throw new IllegalArgumentException("The name of the state cannot be null");
		}
		this.name=name;
	}
	/**
	 * returns the id of the {@link State}
	 * @return the id of the {@link State}
	 */
	public int getId(){
		return this.id;
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
	 * @param name the name of the state
	 * @throws NullPointerException if the name of the state is null
	 */
	public void setName(String name){
		if(this.name==null){
			throw new NullPointerException("It is not possible to create a state with a null name");
		}
		this.name=name;
	}
	
	@Override
	/**
	 * returns a String representation of the AutomatonState
	 * @return the String representation of the AutomatonState
	 */
	public String toString(){
		return "Id: {"+this.id+"} "+" Name: {"+this.getName()+"}";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(State o) {

		return Integer.compare(this.id, o.id);
	}
}
