package it.polimi.contraintcomputation.abstraction;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

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
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class Abstractor<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains the intersection automaton to be simplified
	 */
	private IntersectionBA<L, S, T> intBA;

	/**
	 * contains the set of already visited states
	 */
	private Set<S> abstractedStates;

	/**
	 * contains the transition factory
	 */
	private TransitionFactory<L, T> transitionFactory;

	/**
	 * creates a new Abstractor
	 * 
	 * @param intBA
	 *            is the intersection automaton to be considered
	 * @throws NullPointerException
	 *             if the intersection automaton or the factory is null
	 */
	public Abstractor(IntersectionBA<L, S, T> intBA,
			TransitionFactory<L, T> transitionFactory) {
		if (intBA == null) {
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		this.transitionFactory = transitionFactory;
		this.intBA = intBA.clone();
		this.abstractedStates = new HashSet<S>();
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
	public IntersectionBA<L, S, T> abstractIntersection() {

		for (S s : this.intBA.getInitialStates()) {
			this.abstractStateSpace(s);
		}
		return this.intBA;
	}

	/**
	 * abstract the state space of the automaton, by removing the non mixed
	 * states
	 * 
	 * @param currState
	 *            is the current state to be analyzed
	 */
	private void abstractStateSpace(S currState) {
		// if the currState has already been abstracted
		if (this.abstractedStates.contains(currState)) {
			return;
		}
		this.abstractedStates.add(currState);
		if (this.intBA.getMixedStates().contains(currState)
				|| this.intBA.getInitialStates().contains(currState)
				|| this.intBA.getAcceptStates().contains(currState)) {
			for (T successorTransition : this.intBA
					.getOutTransitions(currState)) {
				this.abstractStateSpace(this.intBA
						.getTransitionDestination(successorTransition));
			}
		} else {
			Set<S> toBeAnalyzed=new HashSet<S>();
			for (T incomingTransition : this.intBA.getInTransitions(currState)) {
				for (T outcomingTransition : this.intBA
						.getOutTransitions(currState)) {
					this.mergeTransitions(incomingTransition,
							outcomingTransition);
					toBeAnalyzed.add(this.intBA.getTransitionDestination(outcomingTransition));
				}
			}
			this.intBA.removeState(currState);
			for(S next: toBeAnalyzed){
				this.abstractStateSpace(next);
			}
		}

	}

	private void mergeTransitions(T incoming, T outcoming) {
		Set<L> labels = new HashSet<L>(incoming.getLabels());
		labels.addAll(outcoming.getLabels());
		for (T t : this.intBA.getOutTransitions(this.intBA
				.getTransitionSource(incoming))) {
			if (this.intBA.getTransitionDestination(t).equals(
					this.intBA.getTransitionDestination(outcoming))) {
				this.intBA.removeTransition(t);
				labels.addAll(t.getLabels());
			}
		}
		T newTransition = this.transitionFactory.create(labels);
		this.intBA.addTransition(this.intBA.getTransitionSource(incoming),
				this.intBA.getTransitionDestination(outcoming), newTransition);
	}

}
