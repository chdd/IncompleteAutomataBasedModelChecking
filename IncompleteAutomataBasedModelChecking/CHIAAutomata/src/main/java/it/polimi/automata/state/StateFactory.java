package it.polimi.automata.state;

import org.jgrapht.VertexFactory;

import com.google.common.base.Preconditions;

/**
 * is the factory that allows to create states of the type State
 * 
 * @see {@link State}. It implements the {@link StateFactory} interface
 * 
 * @author claudiomenghi
 * 
 */
public class StateFactory implements VertexFactory<State> {

	/**
	 * contains the counter whose value is associated to the next id of the
	 * state to be created
	 */
	public static int stateCount = 0;

	/**
	 * crates a new state with an empty name the id is auto-assigned to the
	 * state
	 * 
	 * @return a new state with an empty name
	 */
	public State create() {

		State s = new State(Integer.toString(StateFactory.stateCount),
				StateFactory.stateCount);
		StateFactory.stateCount = StateFactory.stateCount + 1;
		return s;
	}

	/**
	 * creates a new state with the specified name the id is auto-assigned to
	 * the state
	 * 
	 * @param name
	 *            the name of the state
	 * @return a new state with the specified name
	 * @throws NullPointerException
	 *             if the name of the state is null
	 */
	public State create(String name) {
		Preconditions
				.checkNotNull(name, "The name of the state cannot be null");

		State s = new State(name, StateFactory.stateCount);
		StateFactory.stateCount = StateFactory.stateCount + 1;
		return s;
	}

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
	 * @throws NullPointerException
	 *             if the name of the state is null
	 * 
	 */
	public State create(String name, int id) {
		Preconditions
				.checkNotNull(name, "The name of the state cannot be null");
		Preconditions.checkArgument(id >= 0,
				"The id must be grater than or equal to 0");

		State s = new State(name, id);
		StateFactory.stateCount = Math.max(StateFactory.stateCount + 1, id + 1);
		return s;
	}

	/**
	 * creates a new state with an empty name and an auto-assigned id
	 * 
	 * @return a new state with an auto assigned id and an empty name
	 */
	public State createVertex() {
		State s = new State(Integer.toString(StateFactory.stateCount),
				StateFactory.stateCount);
		StateFactory.stateCount = StateFactory.stateCount + 1;
		return s;
	}
}
