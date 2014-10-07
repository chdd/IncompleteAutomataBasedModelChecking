package it.polimi.model.automata.ba.state;

import javax.xml.bind.annotation.XmlType;

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
	
	
	protected State(int id){
		this.id=id;
		this.name="";
	}
	
	/**
	 * creates a new state with the specified name
	 * @param name: contains the name of the state the name of the state
	 * @throws IllegalArgumentException is raised when the name of the state is null
	 */
	protected State(String name, int id){
		super();
		if(id<0){
			throw new IllegalArgumentException("The id cannot be <0");
		}
		if(name==null){
			throw new IllegalArgumentException("The name of the state cannot be null");
		}
		this.id=id;
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
		return "Id: {"+this.id+"} "+"\n Name:"+this.getName();
	}

	public int getId(){
		return this.id;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(State o) {

		return Integer.compare(this.id, o.id);
	}
	


}
