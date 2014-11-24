package it.polimi.model.impl.states;

/**
 * @author claudiomenghi contains an automata {@link State}. The {@link State}
 *         is identified by its id
 */
public class State {

	/**
	 * contains the id of the {@link State}
	 */
	protected final int id;

	/**
	 * contains the name of the {@link State}
	 */
	protected String name;

	/**
	 * creates a {@link State} with the specified id and an empty name
	 * 
	 * @param id
	 *            is the id of the state
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 */
	protected State(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("The id cannot be < 0");
		}
		this.id = id;
		this.name = "";
	}

	/**
	 * creates a new {@link State} with the specified name
	 * 
	 * @param name
	 *            : contains the name of the {@link State} the name of the {@link State}
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 * @throws IllegalArgumentException
	 *             is generated when the name of the state is null
	 */
	protected State(String name, int id) {
		this(id);
		if (name == null) {
			throw new NullPointerException(
					"The name of the state cannot be null");
		}
		this.name = name;
	}

	/**
	 * returns the id of the {@link State}
	 * 
	 * @return the id of the {@link State}
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * returns the name of the {@link State}
	 * 
	 * @return the name of the {@link State}
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the {@link State}
	 * 
	 * @param name
	 *            the name of the {@link State}
	 * @throws NullPointerException
	 *             if the name of the {@link State} is null
	 */
	public void setName(String name) {
		if (this.name == null) {
			throw new NullPointerException(
					"It is not possible to create a state with a null name");
		}
		this.name = name;
	}

	@Override
	/**
	 * returns a {@link String} representation of the {@link State}
	 * @return the {@link String} representation of the {@link State}
	 */
	public String toString() {
		return "<HTML>Id: {" + this.id + "}<BR>Name:" + this.getName()
				+ "</HTML>";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
}
