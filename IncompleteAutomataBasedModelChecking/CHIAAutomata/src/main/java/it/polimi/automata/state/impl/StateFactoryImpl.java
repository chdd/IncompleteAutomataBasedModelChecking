package it.polimi.automata.state.impl;

import it.polimi.automata.state.StateFactory;

/**
 * is the factory that allows to create states of the type StateImpl
 * 
 * @see {@link StateImpl}. It implements the {@link StateFactory} interface
 * 
 * @author claudiomenghi
 * 
 */
public class StateFactoryImpl implements StateFactory<StateImpl> {

	/**
	 * contains the counter whose value is associated to the next id of the
	 * state to be created
	 */
	private static int stateCount = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateImpl create() {

		StateImpl s = new StateImpl("",
				StateFactoryImpl.stateCount);
		StateFactoryImpl.stateCount++;
		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateImpl create(String name) {

		if (name == null) {
			throw new NullPointerException(
					"The name of the state cannot be null");
		}
		StateImpl s = new StateImpl(name, StateFactoryImpl.stateCount);
		StateFactoryImpl.stateCount++;
		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateImpl create(String name, int id) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"The id must be grater than or equal to 0");
		}
		if (name == null) {
			throw new NullPointerException(
					"The name of the state cannot be null");
		}
		StateImpl s = new StateImpl(name, id);
		StateFactoryImpl.stateCount = Math.max(StateFactoryImpl.stateCount++,
				id + 1);
		return s;
	}
}
