package it.polimi.automata.factories;

import it.polimi.automata.State;

import org.apache.commons.collections15.Factory;

/**
 * is the factory interface which allows to create the state of the Buchi
 * automaton or of the incomplete buchi automaton
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the state of the automaton which must extend the
 *            interface {@link State}
 */
public interface StateFactory<STATE extends State> extends Factory<STATE> {

	/**
	 * crates a new state with an empty name the id is auto-assigned to the
	 * state
	 * 
	 * @return a new state with an empty name
	 */
	@Override
	public STATE create();

	/**
	 * creates a new state with the specified name the id is auto-assigned to
	 * the state
	 * 
	 * @param name
	 *            the name of the state
	 * @return a new state with the specified name
	 */
	public STATE create(String name);

	/**
	 * creates a new state with the specified name and id
	 * 
	 * @param name
	 *            is the name of the state to be created
	 * @param id
	 *            is the id of the state to be created
	 * @return a new state with the specified name and id
	 * @throws IllegalArgumentException
	 *             if the id is less than 0
	 *             
	 */
	public STATE create(String name, int id);
}
