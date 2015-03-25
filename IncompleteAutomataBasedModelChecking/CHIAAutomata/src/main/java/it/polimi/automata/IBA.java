package it.polimi.automata;

import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.jgrapht.experimental.RandomGraphHelper;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

import com.google.common.base.Preconditions;

/**
 * <p>
 * The \texttt{IBA} class contains the class which describes an Incomplete Buchi
 * Automaton. The \texttt{IBA} class extends \texttt{BA} by storing the set of
 * the \emph{transparent} states. <br>
 * 
 * @author claudiomenghi
 */
public class IBA extends BA {

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<State> transparentStates;

	/**
	 * creates a new incomplete Buchi automaton
	 */
	public IBA(TransitionFactory<State, Transition> transitionFactory) {
		super(transitionFactory);
		this.transparentStates = new HashSet<State>();
	}

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
	public boolean isTransparent(State s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");
		Preconditions
				.checkArgument(this.getStates().contains(s),
						"The state is not contained into the set of the states of the IBA");

		return this.transparentStates.contains(s);
	}

	/**
	 * returns the set of the transparent states of the Incomplete Buchi
	 * Automaton
	 * 
	 * @return the set of the transparent states of the Incomplete Buchi
	 *         Automaton (if no transparent states are present an empty set is
	 *         returned)
	 */
	public Set<State> getTransparentStates() {
		return Collections.unmodifiableSet(this.transparentStates);
	}

	/**
	 * returns the set of the transparent states of the Incomplete Buchi
	 * Automaton
	 * 
	 * @return the set of the transparent states of the Incomplete Buchi
	 *         Automaton
	 */
	public Set<State> getRegularStates() {
		Set<State> states = new HashSet<State>();
		states.addAll(this.getStates());
		states.removeAll(this.getTransparentStates());
		return states;
	}

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
	public void addTransparentState(State s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");

		this.transparentStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}
	}

	/**
	 * creates a copy of the Incomplete Buchi Automaton
	 * 
	 * @return a copy of the Incomplete Buchi Automaton
	 */
	@Override
	public IBA clone() {
		IBA clone = new IBA(
				(TransitionFactory<State, Transition>) this.automataGraph
						.getEdgeFactory());
		for (IGraphProposition l : this.getPropositions()) {
			clone.addProposition(l);
		}
		for (State s : this.getStates()) {
			clone.addState(s);
		}
		for (State s : this.getAcceptStates()) {
			clone.addAcceptState(s);
		}
		for (State s : this.getInitialStates()) {
			clone.addInitialState(s);
		}
		for (State s : this.getTransparentStates()) {
			clone.addTransparentState(s);
		}
		for (Transition t : this.getTransitions()) {
			clone.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}

		return clone;
	}

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
	public IBA replace(State transparentState, IBA ibaToInject,
			Map<State, Set<Entry<Transition, State>>> inComing,
			Map<State, Set<Entry<Transition, State>>> outComing) {
		Preconditions.checkNotNull(transparentState,
				"The state to be replaced is null");
		Preconditions.checkNotNull(ibaToInject, "The IBA to inject is null");
		Preconditions.checkNotNull(inComing, "The inComing map is null");
		Preconditions.checkNotNull(outComing, "The outComing map is null");
		Preconditions.checkArgument(this.isTransparent(transparentState),
				"The state t must be transparent");

		for (State s : inComing.keySet()) {
			if (!this.getPredecessors(transparentState).contains(s)) {
				throw new IllegalArgumentException(
						"The source of an incoming transition to be injected was not connected to the transparent state");
			}
		}
		for (Set<Entry<Transition, State>> e : inComing.values()) {
			for (Entry<Transition, State> entry : e) {
				if (!ibaToInject.getInitialStates().contains(entry.getValue())) {
					throw new IllegalArgumentException(
							"The state pointed by an incoming transition is not an initial state of the ibaToInject");
				}
			}
		}
		for (Set<Entry<Transition, State>> e : outComing.values()) {
			for (Entry<Transition, State> entry : e) {
				if (!this.getSuccessors(transparentState).contains(
						entry.getValue())) {
					throw new IllegalArgumentException(
							"the destination of an out-coming transition was not connected to the transparent state");
				}
			}
		}
		for (State s : outComing.keySet()) {
			if (!ibaToInject.getAcceptStates().contains(s)) {
				throw new IllegalArgumentException(
						"The source of an out-coming transition is not a final state of the ibaToInject");
			}
		}

		IBA newIba = (IBA) this.clone();

		for (State s : ibaToInject.getStates()) {
			newIba.addState(s);
		}
		for (State s : ibaToInject.getTransparentStates()) {
			newIba.addTransparentState(s);
		}
		// copy the transition into the refinement
		for (Transition t : ibaToInject.getTransitions()) {
			newIba.addTransition(ibaToInject.getTransitionSource(t),
					ibaToInject.getTransitionDestination(t), t);
		}
		// if the transparent state is accepting
		if (this.getAcceptStates().contains(transparentState)) {
			// adding the accepting states of the IBA to inject to the accepting
			// states of the refinement
			for (State s : ibaToInject.getAcceptStates()) {
				newIba.addAcceptState(s);
			}
		}
		// if the transparent state is initial
		if (this.getInitialStates().contains(transparentState)) {
			// add the initial state of the IBA to inject to the initial states
			// of the refinement
			for (State s : ibaToInject.getInitialStates()) {
				newIba.addInitialState(s);
			}
		}
		// processing the incoming transitions
		// for each entry
		for (Entry<State, Set<Entry<Transition, State>>> entry : inComing
				.entrySet()) {
			// for each transition
			for (Entry<Transition, State> transitionEntry : entry.getValue()) {
				newIba.addTransition(entry.getKey(),
						transitionEntry.getValue(), transitionEntry.getKey());
			}
		}

		// processing the out-coming transitions
		for (Entry<State, Set<Entry<Transition, State>>> entry : outComing
				.entrySet()) {
			// for each transition
			for (Entry<Transition, State> transitionEntry : entry.getValue()) {
				newIba.addTransition(entry.getKey(),
						transitionEntry.getValue(), transitionEntry.getKey());
			}
		}

		// removing the transparent state to the new IBA
		newIba.removeState(transparentState);
		return newIba;
	}

	public void removeState(State state) {
		super.removeState(state);
		if (this.transparentStates.contains(state)) {
			this.transparentStates.remove(state);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String ret = "";

		ret = "ALPHABET: " + this.getPropositions() + "\n";
		ret = ret + "STATES: " + this.automataGraph.vertexSet() + "\n";
		ret = ret + "INITIAL STATES: " + this.getInitialStates() + "\n";
		ret = ret + "ACCEPTING STATES: " + this.getAcceptStates() + "\n";
		ret = ret + "TRANSPARENT STATES: " + this.getTransparentStates() + "\n";
		ret = ret + "TRANSITIONS\n";
		for (State s : this.automataGraph.vertexSet()) {
			ret = ret + "state " + s + " ->\n";
			for (Transition outEdge : this.automataGraph.outgoingEdgesOf(s)) {
				ret = ret + "\t \t" + outEdge + "\t"
						+ this.getTransitionDestination(outEdge);
			}
			ret = ret + "\n";

		}
		return ret;
	}

	public static IBA generateRandomBA(double transitionDensity,
			double acceptanceDensity, int nStates, double transparentDensity) {

		ClaimTransitionFactory transitionFactory = new ClaimTransitionFactory();

		List<IGraphProposition> proposition1 = new ArrayList<IGraphProposition>();
		proposition1.add(new GraphProposition("a", false));
		proposition1.add(new GraphProposition("b", false));

		IBA ba = new IBA(new ClaimTransitionFactory());
		ba.addProposition(new GraphProposition("a", false));
		ba.addProposition(new GraphProposition("b", false));

		int numTransition = (int) Math.round(nStates * transitionDensity);
		int numAcceptingStates = (int) Math.round(nStates * acceptanceDensity);
		int numTransparentStates = (int) Math.round(nStates
				* transparentDensity);

		RandomGraphHelper.addVertices(ba.automataGraph, new StateFactory(),
				nStates);

		List<State> states = new ArrayList<State>(ba.getStates());

		Random randSingleton = new Random();
		for (int i = 0; i < numTransition; ++i) {
			ba.addTransition(states.get(randSingleton.nextInt(nStates)),
					states.get(randSingleton.nextInt(nStates)),
					transitionFactory.create());
		}

		ArrayList<State> baStates = new ArrayList<State>(ba.getStates());
		Collections.shuffle(baStates);

		int i = 0;
		for (State s : baStates) {
			if (i < numAcceptingStates) {
				ba.addAcceptState(s);
			}
			i++;
		}
		i = 0;
		Collections.shuffle(baStates);
		for (State s : baStates) {
			if (i < numAcceptingStates) {
				ba.addAcceptState(s);
			}
			i++;
		}
		i = 0;
		Collections.shuffle(baStates);
		for (State s : baStates) {
			if (i < numTransparentStates) {
				ba.addTransparentState(s);
			}
			i++;
		}
		Collections.shuffle(baStates);
		ba.addInitialState(baStates.get(0));
		ba.addTransition(baStates.get(0), baStates.get(1),
				transitionFactory.create());
		ba.addTransition(baStates.get(0), baStates.get(2),
				transitionFactory.create());
		return ba;
	}
}
