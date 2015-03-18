package it.polimi.model.ltltoba;

import it.polimi.automata.BA;
import it.polimi.automata.impl.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.LTL2BA4J;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.IState;
import rwth.i2.ltl2ba4j.model.ITransition;

/**
 * contains the transformer which transforms an LTL formula into the
 * corresponding Buchi automaton
 * 
 * @author claudiomenghi
 *
 * @param <L>
 *            is the type of the labels which decorate the Buchi automaton
 * @param <S>
 *            is the type of the states of the Buchi automaton
 * @param <T>
 *            is the type of the transitions of the Buchi automaton
 */
public class LTLtoBATransformer<S extends State, T extends Transition> {

	/**
	 * contains the state factory which is used to create the states of the
	 * automaton
	 */
	private StateFactory<S> stateFactory;

	/**
	 * contains the transition factory which is used to create the transitions
	 * of the automaton
	 */
	private TransitionFactory<S, T> transitionFactory;

	/**
	 * creates the LTL to Buchi automaton transformer
	 * 
	 * @param stateFactory
	 *            is the factory which is used to create the states of the
	 *            automaton
	 * @param transitionFactory
	 *            is the factory which is used
	 * @param labelFactory
	 *            is the factory which is used to create the labels of the
	 *            transitions
	 * @throws NullPointerException
	 *             if the stateFactory or the transition factory is null
	 */
	public LTLtoBATransformer(StateFactory<S> stateFactory,
			TransitionFactory<S, T> transitionFactory) {
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		this.transitionFactory = transitionFactory;
		this.stateFactory = stateFactory;
	}

	/**
	 * transforms the LTL formula into the corresponding Buchi Automaton
	 * 
	 * @param ltlFormula
	 *            contains the LTL formula to be converted
	 * @throws NullPointerException
	 *             if the LTL formula to be transformed is null
	 */
	public BA<S, T> transform(String ltlFormula) {
		if (ltlFormula == null) {
			throw new NullPointerException(
					"The LTL formula to be converted cannot be null");
		}

		/*
		 * creates a new Buchi automaton
		 */
		BA<S, T> ba = new BA<S, T>(transitionFactory);

		/*
		 * calls the LTL2BA4J that transforms the LTL formula into the
		 * corresponding automaton. The tool returns the transitions of the
		 * Buchi automaton
		 */
		Collection<ITransition> transitions = LTL2BA4J.formulaToBA(ltlFormula);

		/*
		 * populates the BA to be returned with the specified set of transitions
		 */
		this.addTransitionsToTheBA(ba, transitions);

		// returns the Buchi automaton
		return ba;
	}

	/**
	 * populates the Buchi Automaton with the set of transitions specified as
	 * parameter
	 * 
	 * @param ba
	 *            is the Buchi Automaton to be populated
	 * @param transitions
	 *            contains the transitions to be added to the Buchi Automaton
	 * 
	 * @throws NullPointerException
	 *             if the Buchi automaton or the set of transitions is null
	 */
	private void addTransitionsToTheBA(BA<S, T> ba,
			Collection<ITransition> transitions) {
		if (ba == null) {
			throw new NullPointerException(
					"The Buchi automaton to be converted cannot be null");
		}
		if (transitions == null) {
			throw new NullPointerException(
					"The set of transitions cannot be null");
		}
		/*
		 * maps each end point (state) of an ITransition to the corresponding
		 * state of the Buchi Automaton
		 */
		Map<IState, S> map = new HashMap<IState, S>();

		/*
		 * analyzes each transition and populates the Buchi Automaton with the
		 * corresponding states. i.e., the end points of the transitions
		 */
		for (ITransition t : transitions) {
			this.analyzeState(t.getSourceState(), map, ba);
			this.analyzeState(t.getTargetState(), map, ba);
		}

		/*
		 * analyzes each transition and populates the corresponding Buchi
		 * Automaton
		 */
		for (ITransition t : transitions) {
			this.analyzeTransition(t, map, ba);
		}
	}

	/**
	 * analyzes the end point state of a transition if the end Point has been
	 * already visited no action is performed, otherwise a new state of the BA
	 * is created and added to the map
	 * 
	 * @param endPoint
	 *            is the endPoint state of a transition
	 * @param map
	 *            is the map which maps each end point state to the
	 *            corresponding state of the Buchi automaton
	 * @param ba
	 *            is the Buchi automaton to be populated
	 * @throws NullPointerException
	 *             if the endPoint, the map or the buchi automaton is null
	 */
	private void analyzeState(IState endPoint, Map<IState, S> map, BA<S, T> ba) {
		if (endPoint == null) {
			throw new NullPointerException("The end point state cannot be null");
		}
		if (map == null) {
			throw new NullPointerException("The map cannot be null");
		}
		if (ba == null) {
			throw new NullPointerException("The Buchi Automaton cannot be null");
		}
		if (!map.containsKey(endPoint)) {
			// a new state which correspond to the source state is created
			S s = stateFactory.create();
			// the source state and the state created are added to the map
			map.put(endPoint, s);
			ba.addState(s);
			// if the endPoint is initial
			if (endPoint.isInitial()) {
				/*
				 * the state created is also added to the set of initial states
				 */
				ba.addInitialState(s);
			}
			// if the endPoing is final
			if (endPoint.isFinal()) {
				/*
				 * the state which has been created is also added to the set of
				 * accepting states
				 */
				ba.addAcceptState(s);
			}
		}
	}

	/**
	 * analyzes a transition of the returned automaton and encodes it into a
	 * transition of the CHIA automaton and add the transition to the Buchi
	 * Automaton
	 * 
	 * @param transition
	 *            is the transition to be converted and added to the Buchi
	 *            Automaton
	 * @param map
	 *            is the map which maps each end point state to the
	 *            corresponding state of the Buchi automaton
	 * @param ba
	 *            is the Buchi Automaton to be populated
	 * @throws NullPointerException
	 *             if the transition, the map or the Buchi Automaton is null
	 */
	private void analyzeTransition(ITransition transition, Map<IState, S> map,
			BA<S, T> ba) {
		Preconditions.checkNotNull(transition,
				"The transition to be added cannot be null");
		Preconditions.checkNotNull(map, "The map cannot be null");
		Preconditions.checkNotNull(ba, "The Buchi automaton cannot be null");

		// returns the source state of the transition
		S source = map.get(transition.getSourceState());

		// returns the destination state of the transition
		S destination = map.get(transition.getTargetState());

		// returns the label of the transition
		Set<IGraphProposition> label = transition.getLabels();

		// creates a new transition
		T t = this.transitionFactory.create(label);

		// adds the label to the current buchi automaton
		ba.addPropositions(t.getPropositions());

		// add the transition from the source state to the destination state
		ba.addTransition(source, destination, t);

	}
}
