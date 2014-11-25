package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author claudiomenghi contains a possibly incomplete Buchi automaton which
 *         extends classical automaton with transparent states
 * @param <STATE>
 *            contains the type of the states of the automaton
 * @param <TRANSITION>
 *            contains the type of the transitions of the automaton
 * @param <TRANSITIONFACTORY>
 *            contains the factory which allows to create TRANSITIONs
 */
public class IBAImpl<STATE extends State, TRANSITION extends Transition>
		extends BAImpl<STATE, TRANSITION> implements
		IBA<STATE, TRANSITION> {

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<STATE> transparentStates;

	/**
	 * creates a new extended automaton
	 */
	public IBAImpl(
			TransitionFactory<TRANSITION> transitionFactory,
			StateFactory<STATE> stateFactory) {
		super(transitionFactory, stateFactory);
		this.transparentStates = new HashSet<STATE>();
	}

	
	public void addTransparentStates(Set<STATE> setTransparentStates) {
		for (STATE s : setTransparentStates) {
			this.addTransparentState(s);
		}
	}

	/**
	 * add a new transparent state in the automaton (the transparent state is
	 * also added to the set of the states of the automaton
	 * 
	 * @param s
	 *            is the state to be added and is also added to the set of the
	 *            states of the automaton
	 * @throws IllegalArgumentException
	 *             if the state to be added is null
	 */
	public void addTransparentState(STATE s) {
		if (s == null) {
			throw new IllegalArgumentException(
					"The state to be added cannot be null");
		}
		this.transparentStates.add(s);
		this.addState(s);
	}

	/**
	 * check if the state is transparent
	 * 
	 * @param s
	 *            is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws IllegalArgumentException
	 *             if the state s is null
	 */
	public boolean isTransparent(STATE s) {
		if (s == null) {
			throw new IllegalArgumentException(
					"The state to be added cannot be null");
		}
		return this.transparentStates.contains(s);
	}

	/**
	 * returns the set of the transparent states of the automaton
	 * 
	 * @return the set of the transparent states of the automaton (if no
	 *         transparent states are present an empty set is returned)
	 */
	public Set<STATE> getTransparentStates() {
		return this.transparentStates;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((transparentStates == null) ? 0 : transparentStates
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IBAImpl<STATE, TRANSITION> other = (IBAImpl<STATE, TRANSITION>) obj;
		if (transparentStates == null) {
			if (other.transparentStates != null)
				return false;
		} else if (!transparentStates.equals(other.transparentStates))
			return false;
		return true;
	}

	@Override
	public IBAImpl<STATE, TRANSITION> clone() {
		IBAImpl<STATE, TRANSITION> clone = new IBAImpl<STATE, TRANSITION>(
				this.transitionFactory, this.stateFactory);
		clone.setPropositions(this.getPropositions());
		clone.addStates(this.getStates());
		clone.setAcceptStates(this.getAcceptStates());
		clone.setInitialStates(this.getInitialStates());
		for (TRANSITION t : this.automataGraph.getEdges()) {
			clone.addTransition(this.automataGraph.getSource(t), this.automataGraph.getDest(t), t);
		}
		clone.addTransparentStates(this.getTransparentStates());

		return clone;
	}

	

	

	public void replace(
			STATE transparentState,
			IBA<STATE, TRANSITION> ibaToInject) {
		if (!this.isTransparent(transparentState)) {
			throw new IllegalArgumentException(
					"The state t must be transparent");
		}

		IBAImpl<STATE, TRANSITION> ibaToInjectImpl = (IBAImpl<STATE, TRANSITION>) ibaToInject;

		this.addStates(ibaToInjectImpl.getStates());
		this.addTransparentStates(ibaToInjectImpl.getTransparentStates());
		if (ibaToInjectImpl.automataGraph.getEdges() != null) {
			for (TRANSITION transition : ibaToInjectImpl.automataGraph.getEdges()) {
				this.automataGraph.addEdge(transition, ibaToInjectImpl.automataGraph.getSource(transition),
						ibaToInjectImpl.automataGraph.getDest(transition));
			}
		}
		if (this.automataGraph.getInEdges(transparentState) != null) {
			for (TRANSITION transition : this.automataGraph.getInEdges(transparentState)) {

				if (this.automataGraph.getSource(transition).equals(transparentState)) {
					for (STATE initialState : ibaToInjectImpl
							.getInitialStates()) {
						for (STATE acceptingState : ibaToInjectImpl
								.getAcceptStates()) {
							this.automataGraph.addEdge(this.transitionFactory
									.create(transition.getCondition()),
									acceptingState, initialState);
						}
					}
				} else {
					for (STATE initialState : ibaToInjectImpl
							.getInitialStates()) {
						this.automataGraph.addEdge(this.transitionFactory.create(transition
								.getCondition()), this.automataGraph.getSource(transition),
								initialState);
					}
				}
			}
		}

		if (this.automataGraph.getOutEdges(transparentState) != null) {
			for (TRANSITION transition : this.automataGraph.getOutEdges(transparentState)) {
				if (!this.automataGraph.getDest(transition).equals(transparentState)) {
					for (STATE finalState : ibaToInjectImpl.getAcceptStates()) {
						this.automataGraph.addEdge(this.transitionFactory.create(transition
								.getCondition()), finalState, this.automataGraph
								.getDest(transition));
					}
				}
			}
		}

		if (this.isAccept(transparentState)) {
			this.addAcceptStates(ibaToInjectImpl.getAcceptStates());
		}
		if (this.isInitial(transparentState)) {
			this.addInitialStates(ibaToInjectImpl.getInitialStates());
		}
		this.removeState(transparentState);
	}

	@Override
	public boolean removeState(STATE vertex) {

		if (this.transparentStates.contains(vertex)) {
			this.transparentStates.remove(vertex);
		}
		return super.removeState(vertex);
	}
}
