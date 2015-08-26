package it.polimi.automata.state;

import com.google.common.base.Preconditions;

/**
 * <p>
 * IF is used to represent a state of an automaton. Each state has two final
 * attributes, the <code>id</code> and the <code>name</code> which are returned
 * through the corresponding methods <code>int getId()</code> and
 * <code>String getName()</code>.
 * </p>
 * 
 * @author claudiomenghi
 */
public class State {

	/**
	 * contains the id of the state
	 */
	private final int id;

	/**
	 * contains the name of the state
	 */
	private final String name;

	/**
	 * creates a state with the specified id and an empty name
	 * 
	 * @param id
	 *            is the id of the state
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 */
	protected State(int id) {
		Preconditions.checkArgument(id >= 0, "The id cannot be < 0");

		this.id = id;
		this.name = "";
	}

	/**
	 * creates a new state with the specified name
	 * 
	 * @param name
	 *            contains the name of the state
	 * @param id
	 *            contains the id of the state
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 * @throws NullPointerException
	 *             is generated when the name of the state is null
	 */
	protected State(String name, int id) {
		Preconditions
				.checkNotNull(name, "The name of the state cannot be null");
		Preconditions.checkArgument(id >= 0, "The id cannot be < 0");
		this.id = id;
		this.name = name;
	}

	/**
	 * <p>
	 * returns the <br>
	 * id</br> of the state<br>
	 * The id uniquely identifies the state.
	 * </p>
	 * 
	 * @return the <br>
	 *         id</br> of the state.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * returns the <br>
	 * name</br> of the state
	 * 
	 * @return the <br>
	 *         name</br> of the state
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return id + ": " + name;
	}

	/**
	 * returns the hashCode of the state
	 * 
	 * @return the hashCode of the state
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * returns true if the current state is equal to the other state
	 * 
	 * @param otherState
	 *            is the state to which the current state must be compare with
	 * @return true if the current state is equal to the other state
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

	/**
	 * returns a copy of the current state
	 * 
	 * @return a copy of the current state
	 */
	public State clone() {
		return new State(this.name, this.id);
	}
}
