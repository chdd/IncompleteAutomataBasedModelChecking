package it.polimi.contraintcomputation.abstractor;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.component.Component;

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
	private AbstractedBA<L, S, T, Component<L, S, T>> intBA;

	private Set<Component<L, S, T>> hashedStates;

	/**
	 * creates a new Abstractor
	 * 
	 * @param intBA
	 *            is the intersection automaton to be considered
	 * @throws NullPointerException
	 *             if the intersection automaton or the factory is null
	 */
	public Abstractor(AbstractedBA<L, S, T, Component<L, S, T>> intBA) {
		if (intBA == null) {
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		this.intBA = intBA;
		hashedStates = new HashSet<Component<L, S, T>>();
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
	public AbstractedBA<L, S, T, Component<L, S, T>> abstractIntersection() {

		for (Component<L, S, T> s : this.intBA.getInitialStates()) {
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
	private void abstractStateSpace(Component<L, S, T> currState) {
		if (this.hashedStates.contains(currState)) {
			return;
		}
		this.hashedStates.add(currState);

		Set<Component<L, S, T>> toBeAnalyzed = new HashSet<Component<L, S, T>>();
		/*
		 * if the state is transparent no action is performed, all the
		 * successors are added to the set of the states to be analyzed next
		 */
		if (currState.isTransparent()) {
			toBeAnalyzed.addAll(this.intBA.getSuccessors(currState));
		}
		/*
		 * otherwise the next state are analyzed to check if there is a
		 * successor that may be joined with this state
		 */
		else {
			Set<T> transitionsToBeAnalyzed = new HashSet<>(
					this.intBA.getOutTransitions(currState));
			while (!transitionsToBeAnalyzed.isEmpty()) {
				T outcomingTransition = transitionsToBeAnalyzed.iterator()
						.next();
				// get the successor of the state
				Component<L, S, T> successorState = this.intBA
						.getTransitionDestination(outcomingTransition);
				if (!this.hashedStates.contains(successorState)) {
					if (successorState.isTransparent()
							|| this.intBA.getInitialStates().contains(
									successorState)
							|| this.intBA.getAcceptStates().contains(
									successorState)) {
						toBeAnalyzed.add(successorState);
					} else {
						for (T t : this.intBA.getInTransitions(successorState)) {
							Component<L, S, T> sourceComponent = this.intBA
									.getTransitionSource(t);
							this.intBA.removeTransition(t);
							if (!this.intBA.isPredecessor(sourceComponent,
									currState)) {
								this.intBA.addTransition(sourceComponent,
										currState, t);
							}
						}
						for (T t : this.intBA.getOutTransitions(successorState)) {
							Component<L, S, T> destinationComponent = this.intBA
									.getTransitionDestination(t);
							this.intBA.removeTransition(t);
							if (!this.intBA.isPredecessor(currState,
									destinationComponent)) {
								this.intBA.addTransition(currState,
										destinationComponent, t);
								transitionsToBeAnalyzed.add(t);
							}
						}
						this.intBA.removeState(successorState);
					}
				}
				transitionsToBeAnalyzed.remove(outcomingTransition);
			}
		}

		for (Component<L, S, T> next : toBeAnalyzed) {
			this.abstractStateSpace(next);
		}
	}
}
