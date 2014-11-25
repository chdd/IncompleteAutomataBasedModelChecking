package it.polimi.model.impl.states;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.interfaces.states.IntersectionStateFactory;

import org.apache.commons.collections15.Factory;

/**
 * @author claudiomenghi contains the {@link Factory} method which is in charge
 *         of instantiating the {@link IntersectionState}
 */
public class IntersectionStateFactoryImpl implements IntersectionStateFactory<State, IntersectionState<State>>{

	/**
	 * contains the counter whose value will be associated to the next
	 * {@link IntersectionState} of the {@link IntBAImpl}
	 */
	private static int nodeCount = 0;

	/**
	 * contains the {@link Factory} in charge of generating the {@link State}s
	 */
	private StateFactoryImpl stateFactory;

	/**
	 * creates a new {@link IntersectionStateFactory}
	 */
	public IntersectionStateFactoryImpl() {
		this.stateFactory = new StateFactoryImpl();
	}

	/**
	 * crates a new {@link IntersectionState} with an empty name and two new
	 * {@link State}s for the original automata
	 * 
	 * @return a new {@link IntersectionState} with an empty name and two new
	 *         {@link State}s for the original automata
	 */
	@Override
	public IntersectionState<State> create() {

		IntersectionState<State> s = new IntersectionState<State>(
				this.stateFactory.create(), this.stateFactory.create(), "", 0,
				IntersectionStateFactoryImpl.nodeCount);
		IntersectionStateFactoryImpl.nodeCount++;
		return s;
	}

	/**
	 * creates a new {@link IntersectionState} starting from the two
	 * {@link State}s s1 and s2 and with number num
	 * 
	 * @param s1
	 *            is the first {@link State} of the automaton
	 * @param s2
	 *            is the second {@link State} of the automaton
	 * @param num
	 *            is the number which is associated to the state
	 * @return a new {@link IntersectionState}
	 * 
	 * @throws NullPointerException
	 *             if the {@link State} s1 or the {@link State} s2 is null
	 * @throws IllegalArgumentException
	 *             if num is less than 0
	 */
	public IntersectionState<State> create(State s1, State s2, int num) {

		if (s1 == null) {
			throw new NullPointerException("The state s1 cannot be null");
		}
		if (s2 == null) {
			throw new NullPointerException("The state s2 cannot be null");
		}
		if (num < 0) {
			throw new IllegalArgumentException(
					"The number cannot be less than zero");
		}

		IntersectionState<State> s = new IntersectionState<State>(s1, s2,
				s1.getName() + "-" + s2.getName() + "-" + num, num,
				IntersectionStateFactoryImpl.nodeCount);
		IntersectionStateFactoryImpl.nodeCount++;
		return s;
	}

	/**
	 * creates a new {@link IntersectionState} with the specified name and id
	 * 
	 * @param name
	 *            is the name of the {@link State} to be created
	 * @param id
	 *            is the id of the {@link State} to be created
	 * @return a new {@link IntersectionState} with the specified name and id
	 * 
	 * @throws IllegalArgumentException
	 *             if the id is less than 0
	 * @throws NullPointerException
	 *             if the name of the {@link IntersectionState} is null
	 */
	public IntersectionState<State> create(String name, int id) {
		if (name == null) {
			throw new NullPointerException(
					"The name of the Intersection State cannot be null");
		}

		if (id < 0) {
			throw new IllegalArgumentException(
					"The id must be grater than or equal to 0");
		}
		IntersectionState<State> s = new IntersectionState<State>(
				this.stateFactory.create(), this.stateFactory.create(), name,
				0, id);
		IntersectionStateFactoryImpl.nodeCount = Math.max(
				IntersectionStateFactoryImpl.nodeCount++, id + 1);
		return s;
	}

	/**
	 * creates a new {@link IntersectionState} with the specified name
	 * 
	 * @param name
	 *            is the name of the {@link IntersectionState}
	 * @throws NullPointerException
	 *             if the name of the {@link IntersectionState} is null
	 */
	@Override
	public IntersectionState<State> create(String name) {
		if (name == null) {
			throw new NullPointerException(
					"The name of the IntersectionState cannot be null");
		}
		IntersectionState<State> s = new IntersectionState<State>(
				this.stateFactory.create(), this.stateFactory.create(), name,
				0, IntersectionStateFactoryImpl.nodeCount);
		IntersectionStateFactoryImpl.nodeCount++;
		return s;
	}
}
