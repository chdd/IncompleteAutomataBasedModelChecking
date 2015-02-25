package it.polimi.automata.state.impl;

import com.google.common.base.Preconditions;

import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

/**
 * is the factory that allows to create states of the type StateImpl
 * 
 * @see {@link StateImpl}. It implements the {@link StateFactory} interface
 * 
 * @author claudiomenghi
 * 
 */
public class StateFactoryImpl implements StateFactory<State> {

	/**
	 * contains the counter whose value is associated to the next id of the
	 * state to be created
	 */
	public static int stateCount = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateImpl create() {

		StateImpl s = new StateImpl("",
				StateFactoryImpl.stateCount);
		StateFactoryImpl.stateCount=StateFactoryImpl.stateCount+1;
		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateImpl create(String name) {
		Preconditions.checkNotNull(name, "The name of the state cannot be null");
		
		StateImpl s = new StateImpl(name, StateFactoryImpl.stateCount);
		StateFactoryImpl.stateCount=StateFactoryImpl.stateCount+1;
		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StateImpl create(String name, int id) {
		Preconditions.checkNotNull(name, "The name of the state cannot be null");
		Preconditions.checkArgument(id >= 0, "The id must be grater than or equal to 0");
		
		StateImpl s = new StateImpl(name, id);
		StateFactoryImpl.stateCount = Math.max(StateFactoryImpl.stateCount+1,
				id + 1);
		return s;
	}
}
