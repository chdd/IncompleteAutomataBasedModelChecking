package it.polimi.model.impl.states;

import it.polimi.model.interfaces.states.StateFactory;

import org.apache.commons.collections15.Factory;

/**
 * @author claudiomenghi contains the {@link Factory} method which is in charge
 *         of instantiating the {@link State}s of the Automaton
 */
public class StateFactoryImpl implements StateFactory<State> {

	/**
	 * contains the counter whose value is associated to the next {@link State}
	 * of the Automaton
	 */
	private static int nodeCount = 0;

	/**
	 * crates a new {@link State} with an empty name
	 * 
	 * @return a new {@link State} with an empty name
	 */
	@Override
	public State create() {

		State s = new State("s" + StateFactoryImpl.nodeCount,
				StateFactoryImpl.nodeCount);
		StateFactoryImpl.nodeCount++;
		return s;
	}

	/**
	 * creates a new {@link State} with the specified name
	 * 
	 * @param name
	 *            the name of the {@link State}
	 * @return a new {@link State} with the specified name
	 * @throws NullPointerException
	 *             if the name of the {@link State} is null
	 */
	public State create(String name) {

		if (name == null) {
			throw new NullPointerException(
					"The name of the state cannot be null");
		}
		State s = new State(name, StateFactoryImpl.nodeCount);
		StateFactoryImpl.nodeCount++;
		return s;
	}

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
	 * @throws NullPointerException
	 *             if the name of the {@link State} is null
	 */
	public State create(String name, int id) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"The id must be grater than or equal to 0");
		}
		State s = new State(name, id);
		StateFactoryImpl.nodeCount = Math.max(StateFactoryImpl.nodeCount++,
				id + 1);
		return s;
	}
}
