package it.polimi.contraintcomputation.abstractor;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstractor allows to generate a more concise version of the intersection
 * automaton where the portions of the state space which are not useful in the
 * constraint computation are removed. The state space of the intersection is
 * explored from its initial to its final states and:<br>
 * a) all the transitions that connect a mixed state q1 to a purely regular
 * state (which is not accepting) q2 are removed and replaced with transitions
 * that connect q1 with the successors of q2.<br>
 * b) all the transitions that connect a purely regular state q1 to a purely
 * regular state (which is not accepting) q2 are removed and replaced with
 * transitions that connect q1 with the successors of q2. <br>
 * c) all the transitions that do not allow to reach an accepting state of I are
 * removed. <br>
 * d) all the transitions that connect an accepting state q1 to a purely regular
 * state (which is not accepting) q2 are removed and replaced with transitions
 * that connect q1 with the successors of q2.
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Intersection Buchi Automaton. The
 *            type of the states of the automaton must implement the interface
 *            {@link State}
 * @param <T>
 *            is the type of the transition of the Intersection Buchi Automaton.
 *            The typer of the transitions of the automaton must implement the
 *            interface {@link Transition}
 */
public class Abstractor<S extends State, T extends Transition> {

	/**
	 * is the logger of the Abstractor class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(Abstractor.class);

	/**
	 * contains the intersection automaton to be simplified
	 */
	private IntersectionBA<S, T> intBA;

	/**
	 * contains the set of visited states
	 */
	private Set<S> hashedStates;
	
	private TransitionFactory<S, T> transitionFactory;

	/**
	 * creates a new Abstractor
	 * 
	 * @param intBA
	 *            is the intersection automaton to be considered
	 * @throws NullPointerException
	 *             if the intersection automaton or the factory is null
	 */
	public Abstractor(IntersectionBA<S, T> intBA, TransitionFactory<S, T> transitionFactory) {
		if (intBA == null) {
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		this.intBA = intBA;
		this.hashedStates = new HashSet<S>();
		this.transitionFactory=transitionFactory;
	}

	/**
	 * The abstractor allows to generate a more concise version of the
	 * intersection automaton where the portions of the state space which are
	 * not useful in the constraint computation are removed. The state space of
	 * the intersection is explored from its initial to its final states and: <br>
	 * a) all the transitions that connect a mixed state q1 to a purely regular
	 * state (which is not accepting) q2 are removed and replaced with
	 * transitions that connect q1 with the successors of q2.<br>
	 * b) all the transitions that connect a purely regular state q1 to a purely
	 * regular state (which is not accepting) q2 are removed and replaced with
	 * transitions that connect q1 with the successors of q2. <br>
	 * c) all the transitions that do not allow to reach an accepting state of I
	 * are removed. <br>
	 * d) all the transitions that connect an accepting state q1 to a purely
	 * regular state (which is not accepting) q2 are removed and replaced with
	 * transitions that connect q1 with the successors of q2.
	 * 
	 * @return the abstracted version of the state space
	 */
	public IntersectionBA<S, T> abstractIntersection() {

		logger.info("ABSTRACTION of the intersection automaton");
		for (S initialState : this.intBA.getInitialStates()) {
			this.abstractStateSpace(initialState);
		}
		return this.intBA;
	}

	/**
	 * abstract the state space of the automaton, by removing the non mixed
	 * states
	 * 
	 * @param currState
	 *            is the current state to be analyzed
	 * @throws NullPointerException
	 *             the current state cannot be null
	 */
	private void abstractStateSpace(S currState) {
		if (currState == null) {
			throw new NullPointerException("The current state cannot be null");
		}
		logger.info("abstracting the state: " + currState);
		if (this.hashedStates.contains(currState)) {
			return;
		}
		this.hashedStates.add(currState);

		Set<S> toBeAnalyzed = new HashSet<S>();
		toBeAnalyzed.addAll(this.intBA.getSuccessors(currState));

		/*
		 * if the state is transparent no action is performed, all the
		 * successors are added to the set of the states to be analyzed next
		 */
		if (!(this.intBA.getInitialStates().contains(currState)
				|| this.intBA.getAcceptStates().contains(currState) || this.intBA
				.getMixedStates().contains(currState))) {

			// incomingTransition
			for (T incomingTransition : this.intBA.getInTransitions(currState)) {

				// outcomingTransition
				for (T outcomingTransition : this.intBA
						.getOutTransitions(currState)) {
					S destinationState = this.intBA
							.getTransitionDestination(outcomingTransition);
					S sourceState = this.intBA
							.getTransitionSource(incomingTransition);
					this.intBA.addTransition(sourceState,
							destinationState, transitionFactory.create(incomingTransition.getPropositions()));
				}
			}
			this.intBA.removeState(currState);
		}

		for (S next : toBeAnalyzed) {
			this.abstractStateSpace(next);
		}
	}
}
