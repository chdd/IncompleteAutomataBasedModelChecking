package it.polimi.automata.state;

import it.polimi.automata.state.impl.StateImpl;

/**
 * <p>Represents a state of an automaton. <br>
 * A state is identified by an id and has a name</p>
 * 
 * @author claudiomenghi
 * @see StateImpl
 */
public interface State {
	
	/**
	 * <p>returns the <br>id</br> of the state<br>
	 * The id uniquely identifies the state
	 * </p>
	 * 
	 * @return the <br>id</br> of the state
	 */
	public int getId();

	/**
	 * returns the <br>name</br> of the state
	 * 
	 * @return the <br>name</br> of the state
	 */
	public String getName();
	
	/**
	 * sets the name of the state
	 * 
	 * @param name
	 *            the name of the {@link StateImpl}
	 * @throws AssertionError 
	 *             if the name of the {@link StateImpl} is null
	 */
	public void setName(String name);
}
