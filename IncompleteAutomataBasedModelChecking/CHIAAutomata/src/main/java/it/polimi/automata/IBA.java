package it.polimi.automata;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <p>
 * Represents an Incomplete Buchi Automaton which extends a Buchi automaton with
 * transparent states<br>
 * 
 * @see BA The state of the automaton must must implement the {@link State}
 *      interface <br>
 *      The transition of the automaton must must implement the
 *      {@link Transition} interface <br>
 *      The label of the transition must implement the label interface and
 *      depending on whether the automaton represents the model or the claim it
 *      is a set of proposition or a propositional logic formula
 *      </p>
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 */
public interface IBA<S extends State, T extends Transition>
		extends BA<S, T> {

	/**
	 * check if the state is transparent
	 * 
	 * @param s
	 *            is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws NullPointerException
	 *             if the state s is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of the states of
	 *             the automaton
	 */
	public boolean isTransparent(S s);

	/**
	 * returns the set of the transparent states of the Incomplete Buchi
	 * Automaton
	 * 
	 * @return the set of the transparent states of the Incomplete Buchi
	 *         Automaton (if no transparent states are present an empty set is
	 *         returned)
	 */
	public Set<S> getTransparentStates();

	/**
	 * adds the transparent state s to the states of the {@link IBA} and to the
	 * set of the transparent state<br>
	 * if the state is already transparent no action is performed <br>
	 * if the state is a state of the BA but is not transparent, it is also
	 * added to the set of the transparent state
	 * 
	 * @param s
	 *            the state to be added in the {@link IBA}
	 * @throws NullPointerException
	 *             if the state s is null
	 */
	public void addTransparentState(S s);

	/**
	 * returns a new Incomplete Buchi automaton where the transparent state is
	 * replaced by the ibaToInject the inComing and outComing transitions
	 * specifies how the initial and the accepting states are connected with the
	 * current Incomplete Buchi automaton <br>
	 * If the transparent state to be replaced is also accepting, all the states
	 * of the ibaToInject become also accepting for the whole automaton<br>
	 * Similarly, if the transparent state is also initial, all the initial
	 * states of the ibaToInject become initial for the whole automaton
	 * 
	 * @see IBA#clone()
	 * 
	 * @param transparentState
	 *            is the transparent state to be replaced
	 * @param ibaToInject
	 *            is the Incomplete Buchi Automaton to be injected inside the
	 *            Incomplete Buchi Automaton
	 * @param inComing
	 *            are the transitions that connect the refinement of the
	 *            transparent state with the current incomplete Buchi Automaton
	 * @param outComing
	 *            are the transitions that connect the refinement of the
	 *            transparent state with the current incomplete Buchi Automaton
	 * @throws NullPointerException
	 *             if the transparent state, the ibaToInject, the inComing or
	 *             outComing transitions are null
	 * @throws IllegalArgumentException
	 *             if the transparentState is not transparent
	 * @throws IllegalArgumentException
	 *             if the source of an incoming transition was not connected to
	 *             the transparent state
	 * @throws IllegalArgumentException
	 *             if the state pointed by an incoming transition is not an
	 *             initial state of the ibaToInject
	 * @throws IllegalArgumentException
	 *             if the destination of an out-coming transition was not
	 *             connected to the transparent state
	 * @throws IllegalArgumentException
	 *             if the source of an out-coming transition is not a final
	 *             state of the ibaToInject
	 * 
	 */
	public IBA<S, T> replace(S transparentState, IBA<S, T> ibaToInject,
			Map<S, Set<Entry<T, S>>> inComing,
			Map<S, Set<Entry<T, S>>> outComing);

	/**
	 * creates a copy of the Incomplete Buchi Automaton
	 * 
	 * @return a copy of the Incomplete Buchi Automaton
	 */
	public IBA<S, T> clone();
}
