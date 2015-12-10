package it.polimi.constraints.components;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * The Replacement class extends the Component class and specifies the IBA which
 * represent the model that describes the replacement of the black box state.
 * 
 * @author Claudio Menghi
 *
 */
public class Replacement extends Component {

	/**
	 * contains the IBA corresponding to the sub-property
	 */
	private final IBA automaton;

	/**
	 * contains the incoming ports of the component
	 */
	private final Set<PluggingTransition> incomingTransitions;

	/**
	 * contains the out-coming ports of the component
	 */
	private final Set<PluggingTransition> outgoingTransitions;

	/**
	 * maps a state of the model to the corresponding outgoingTransitions
	 */
	private final Multimap<Triple<State, State, Set<IGraphProposition>>, PluggingTransition> mapOutgoingTransitions;

	/**
	 * 
	 */
	private final Multimap<Entry<State, Set<IGraphProposition>>, PluggingTransition> mapIncomingTransitions;

	/**
	 * creates a new sub-property that refers to a specific model state and
	 * contains the corresponding IBA
	 * 
	 * @param modelState
	 *            is the state of the model to which the sub-property
	 *            corresponds with
	 * @param automaton
	 *            is the automaton related with the sub-property
	 * @param incomingTransitions
	 *            the incoming transitions of the replacement
	 * @param outcomingTransitions
	 *            the outgoing transitions of the replacement
	 * 
	 * @throws NullPointerException
	 *             is generated when the name of the state or when the state of
	 *             the model is null
	 */
	public Replacement(State modelState, IBA automaton,
			Set<PluggingTransition> incomingTransitions,
			Set<PluggingTransition> outcomingTransitions) {
		super(modelState);
		Preconditions.checkNotNull(automaton,
				"The name of the state cannot be null");
		Preconditions.checkNotNull(incomingTransitions,
				"The set of the incoming ports cannot be null");
		Preconditions.checkNotNull(outcomingTransitions,
				"The set of the outcoming ports cannot be null");

		this.incomingTransitions = new HashSet<PluggingTransition>();
		this.outgoingTransitions = new HashSet<PluggingTransition>();
		this.mapOutgoingTransitions = HashMultimap.create();
		this.mapIncomingTransitions = HashMultimap.create();
		this.automaton = automaton;
		for (PluggingTransition incomingTransition : incomingTransitions) {
			this.addIncomingTransition(incomingTransition);
		}
		for (PluggingTransition outgoingTransition : outcomingTransitions) {
			this.addOutgoingTransition(outgoingTransition);
		}

	}

	/**
	 * returns the IBA associated with the replacement
	 * 
	 * @return the IBA associated with the replacement
	 */
	public IBA getAutomaton() {
		return this.automaton;
	}

	/**
	 * adds an outgoing transition to the component
	 * 
	 * @param transition
	 *            is the transition to be added as an outgoing transition
	 * @throws NullPointerException
	 *             if the transition is null
	 * @throws IllegalArgumentException
	 *             if the source of the transition is not a state of the
	 *             automaton associated with the replacement
	 */
	public void addOutgoingTransition(PluggingTransition transition) {

		Preconditions.checkNotNull(transition,
				"The port to be added cannot be null");
		Preconditions
				.checkArgument(
						this.automaton.getStates().contains(
								transition.getSource()),
						"The source "
								+ transition.getSource()
								+ " of the outgoing transition must be a state of the replacement automaton");
		this.outgoingTransitions.add(transition);
		this.mapOutgoingTransitions.put(
				new ImmutableTriple<State, State, Set<IGraphProposition>>(
						transition.getSource(), transition.getDestination(),
						transition.getTransition().getPropositions()),
				transition);
	}

	/**
	 * return the set of the outgoing transitions of the replacement
	 * 
	 * @return the outgoing transitions of the replacement
	 */
	public Set<PluggingTransition> getOutgoingTransitions() {
		return Collections.unmodifiableSet(outgoingTransitions);
	}

	/**
	 * returns the collection of outgoing transitions which have as source the
	 * model state. If no outgoing transitions are associated with the model
	 * state an empty collection is returned
	 * 
	 * @param modelState
	 *            the state of the replacement from which the outgoing
	 *            transitions are fired
	 * @return the collection of the outgoing transitions which have the
	 *         modelState as source state
	 * @throws NullPointerException
	 *             if the model state is null
	 * @throws IllegalArgumentException
	 *             if the model state is not a state of the replacement
	 *             automaton
	 */
	public Collection<PluggingTransition> getOutgoingTransitions(
			State modelState, State destinationState,
			Set<IGraphProposition> propositions) {
		Preconditions.checkNotNull(modelState, "The modelState cannot be null");
		Preconditions
				.checkArgument(
						this.automaton.getStates().contains(modelState),
						"The modelState "
								+ modelState
								+ " must be contained into the set of the states of the automaton associated with the replacement");

		return this.mapOutgoingTransitions
				.get(new ImmutableTriple<State, State, Set<IGraphProposition>>(
						modelState, destinationState, propositions));
	}

	/**
	 * returns the true if there is an outgoing transition of the replacement
	 * which has the specified source and destination states and is labeled with
	 * the specified propositions
	 * 
	 * @param modelState
	 *            the state of the replacement from which the outgoing
	 *            transitions are fired
	 * @return the collection of the outgoing transitions which have the
	 *         modelState as source state
	 * @throws NullPointerException
	 *             if the model state is null
	 * @throws IllegalArgumentException
	 *             if the model state is not a state of the replacement
	 *             automaton
	 */
	public boolean hasOutgoingTransition(State modelState,
			State destinationState, Set<IGraphProposition> propositions) {
		Preconditions.checkNotNull(modelState, "The modelState cannot be null");
		Preconditions.checkNotNull(destinationState,
				"The destination state cannot be null");
		Preconditions.checkNotNull(propositions,
				"The set of the propositions cannot be null");
		Preconditions
				.checkArgument(
						this.automaton.getStates().contains(modelState),
						"The modelState "
								+ modelState
								+ " must be contained into the set of the states of the automaton associated with the replacement");

		return !this.getOutgoingTransitions(modelState, destinationState,
				propositions).isEmpty();
	}

	/**
	 * adds an incoming transition to the replacement
	 * 
	 * @param transition
	 *            is the transition to be added as an incoming transition
	 * @throws NullPointerException
	 *             if the transition is null
	 * @throws IllegalArgumentException
	 *             if the destination of the transition is not a state of the
	 *             automaton associated with the replacement
	 */
	public void addIncomingTransition(PluggingTransition transition) {

		Preconditions.checkNotNull(transition,
				"The incoming transition to be added cannot be null");
		Preconditions
				.checkArgument(
						this.automaton.getStates().contains(
								transition.getDestination()),
						"The destination "
								+ transition.getDestination()
								+ " of the incoming transition must be a state of the replacement automaton");

		this.incomingTransitions.add(transition);
		this.mapIncomingTransitions.put(
				new AbstractMap.SimpleEntry<State, Set<IGraphProposition>>(
						transition.getSource(), transition.getTransition()
								.getPropositions()), transition);

	}

	/**
	 * return the set of the in-coming ports of the component
	 * 
	 * @return the incomingPorts of the component
	 */
	public Set<PluggingTransition> getIncomingTransitions() {
		return Collections.unmodifiableSet(incomingTransitions);
	}

	/**
	 * returns the collection of incoming transitions with the specified
	 * modelState, destinationState and propositions. If no incoming transitions
	 * are associated with the modelState, destinationState and the set of
	 * propositions an empty collection is returned
	 * 
	 * @param modelState
	 *            the state of the model which is the source from which the incoming transitions is
	 *            fired
	 * @param destinationState
	 *            the state of the automaton which is reached by the specified
	 *            transition
	 * @return the collection of the incoming transitions which have the
	 *         specifiedmodelState, destinationState and the set of propositions
	 * @throws NullPointerException
	 *             if the model state is null
	 * @throws IllegalArgumentException
	 *             if the model state is not a state of the replacement
	 *             automaton
	 */
	public Collection<PluggingTransition> getIncomingTransitions(
			State modelState, Set<IGraphProposition> propositions) {
		Preconditions.checkNotNull(modelState, "The modelState cannot be null");

		return this.mapIncomingTransitions
				.get(new AbstractMap.SimpleEntry<State, Set<IGraphProposition>>(
						modelState, propositions));
	}

	/**
	 * returns the true if there is an incoming transition of the replacement
	 * which has the specified destination state and is labeled with the
	 * specified propositions
	 * 
	 * @param modelState
	 *            the state of the model which is the source of the
	 *            incoming transition
	 * @param propositions
	 *            the propositions that label the replacement
	 * @return true if there is whose destination is the specified state and the
	 *         transition is labeled according to the propositions specified as
	 *         parameter
	 * @throws NullPointerException
	 *             if the model state is null
	 * @throws IllegalArgumentException
	 *             if the model state is not a state of the replacement
	 *             automaton
	 */
	public boolean hasIncomingTransition(State modelState,
			Set<IGraphProposition> propositions) {
		Preconditions.checkNotNull(modelState, "The modelState cannot be null");
		Preconditions.checkNotNull(propositions,
				"The set of the propositions cannot be null");

		return !this.getIncomingTransitions(modelState, propositions).isEmpty();
	}

	/**
	 * removes the incoming or outgoing transitions from the set of incoming or
	 * outgoing transitions
	 * 
	 * @param p
	 *            is the port to be removed
	 * @throws NullPointerException
	 *             if the port p is null
	 * @throws IllegalArgumentException
	 *             if the port is not contained into the set of incoming or
	 *             outgoing transitions
	 */
	public void removePluggingTransition(PluggingTransition p) {
		Preconditions.checkNotNull(p, "The port p cannot be null");
		Preconditions
				.checkArgument(this.getIncomingTransitions().contains(p)
						|| this.getOutgoingTransitions().contains(p),
						"The port must be contained in the set of incoming or utcoming ports");
		if (this.getIncomingTransitions().contains(p)) {
			this.incomingTransitions.remove(p);
			
			this.mapIncomingTransitions
					.get(new AbstractMap.SimpleEntry<State, Set<IGraphProposition>>(p
							.getDestination(), p.getTransition().getPropositions())).remove(p);
		}
		if (this.getOutgoingTransitions().contains(p)) {
			this.outgoingTransitions.remove(p);
			this.mapOutgoingTransitions.remove(
					new ImmutableTriple<State, State, Set<IGraphProposition>>(
							p.getSource(), p.getDestination(),
							p.getTransition().getPropositions())
					, p);
			
		}
	}
	

}
