package it.polimi.automata.impl;

import it.polimi.automata.State;

/**
 * contains the implementation of a state of the automaton. It implements the
 * interface @see {@link State}
 * 
 * @author claudiomenghi
 */
public class StateImpl implements State {

	/**
	 * contains the id of the state
	 */
	protected final int id;

	/**
	 * contains the name of the state
	 */
	protected String name;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * creates a state with the specified id and an empty name
	 * 
	 * @param id
	 *            is the id of the state
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 */
	protected StateImpl(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("The id cannot be < 0");
		}
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
	 * @see StateImpl#StateImpl(int)
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 * @throws IllegalArgumentException
	 *             is generated when the name of the state is null
	 */
	public StateImpl(String name, int id) {
		this(id);
		if (name == null) {
			throw new NullPointerException(
					"The name of the state cannot be null");
		}
		this.name = name;
	}

	/**
	 * sets the name of the state
	 * 
	 * @param name
	 *            the name of the {@link StateImpl}
	 * @throws NullPointerException
	 *             if the name of the {@link StateImpl} is null
	 */
	public void setName(String name) {
		if (this.name == null) {
			throw new NullPointerException(
					"It is not possible to create a state with a null name");
		}
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.id + ": " + this.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateImpl other = (StateImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
