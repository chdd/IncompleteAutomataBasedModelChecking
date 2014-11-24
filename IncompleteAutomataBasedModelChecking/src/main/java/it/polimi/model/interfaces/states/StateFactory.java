package it.polimi.model.interfaces.states;

import it.polimi.model.impl.states.State;

import org.apache.commons.collections15.Factory;

/**
 * is the {@link StateFactory} interface which allows to create the {@link State} of the Automaton
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the Automaton
 */
public interface StateFactory<STATE extends State> extends Factory<STATE> {

	/**
	 * crates a new {@link State} with an empty name
	 * 
	 * @return a new {@link State} with an empty name
	 */
	@Override
	public STATE create();

	/**
	 * creates a new {@link State} with the specified name
	 * 
	 * @param name
	 *            the name of the {@link State}
	 * @return a new {@link State} with the specified name
	 */
	public STATE create(String name);

	/**
	 * creates a new {@link State} with the specified name and id
	 * 
	 * @param name
	 *            is the name of the {@link State} to be created
	 * @param id
	 *            is the id of the {@link State} to be created
	 * @return a new {@link State} with the specified name and id
	 * @throws IllegalArgumentException
	 *             if the id is less than 0
	 */
	public STATE create(String name, int id);
}
