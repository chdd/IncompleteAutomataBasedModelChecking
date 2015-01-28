package it.polimi.contraintcomputation;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.contraintcomputation.brzozowski.Brzozowski;
import it.polimi.contraintcomputation.brzozowski.ConcatenateTransformer;
import it.polimi.contraintcomputation.brzozowski.IneffectiveStarTransformer;
import it.polimi.contraintcomputation.brzozowski.UnionTransformer;

import java.util.Map;
import java.util.Set;

/**
 * The constraint generator computes a constraint. A constraint is a (set of)
 * sub-model(s) for the unspecified components is produce
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, Set<S>> modelIntersectionStatesMap;
	
	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<L, S, T> intBA;

	/**
	 * is the factory to be used to create transitions
	 */
	private TransitionFactory<L, T> transitionFactory;

	/**
	 * is the factory to be used to creating the states
	 */
	private StateFactory<S> stateFactory;

	/**
	 * is the factory to be used to create labels
	 */
	private LabelFactory<L> labelFactory;

	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param intBA
	 *            is the intersection automaton
	 * @param modelIntersectionStatesMap
	 *            is the map between the states of the model and the
	 *            corresponding states in the intersection automaton
	 * @param labelFactory
	 *            is the factory to be used to create labels
	 * @param stateFactory
	 *            is the factory to be used to creating the states
	 * @param transitionFactory
	 *            is the factory to be used to create transitions
	 * @throws NullPointerException
	 *             if the intersection automaton or the model intersection state
	 *             map or the label, state or transition factory is null
	 */
	public ConstraintGenerator(IntersectionBA<L, S, T> intBA,
			Map<S, Set<S>> modelIntersectionStatesMap,
			LabelFactory<L> labelFactory, StateFactory<S> stateFactory,
			TransitionFactory<L, T> transitionFactory) {
		if (intBA == null) {
			throw new NullPointerException(
					"The intersection model cannot be null");
		}
		if (modelIntersectionStatesMap == null) {
			throw new NullPointerException(
					"The map between the states of the model and the intersection states cannot be null");
		}
		if (labelFactory == null) {
			throw new NullPointerException("The label factory cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		this.modelIntersectionStatesMap = modelIntersectionStatesMap;
		this.intBA = intBA;
	}

	/**
	 * returns the constraint of the automaton
	 * @return the constraint of the automaton
	 */
	public String generateConstraint() {
		/*
		 * returns the set of the components (set of states) that correspond to
		 * the parts of the automaton that refer to different states of the
		 * model
		 */
		SubAutomataIdentifier<L, S, T> subautomataIdentifier = new SubAutomataIdentifier<L, S, T>(
				this.intBA, modelIntersectionStatesMap);
		Map<S, Set<Component<S>>> modelStateSubAutomataMap = subautomataIdentifier
				.getSubAutomata();
		/*
		 * The abstraction of the state space is a more concise version of the
		 * intersection automaton I where the portions of the state space which
		 * do not correspond to transparent states are removed
		 */

		Abstractor<L, S, T> abstractor = new Abstractor<L, S, T>(this.intBA,
				transitionFactory);
		IntersectionBA<L, S, T> abstractedIntersection = abstractor
				.abstractIntersection();

		Aggregator<L, S, T> aggregator = new Aggregator<L, S, T>(
				abstractedIntersection,
				subautomataIdentifier.getIntersectionStateClusterMap(),
				modelStateSubAutomataMap, new IntBAFactoryImpl<L, S, T>(),
				transitionFactory, stateFactory, labelFactory);

		IntersectionBA<L, S, T> abstractedIntersectionAggrageted = aggregator
				.aggregate();

		String ret = "";
		for (S init : abstractedIntersectionAggrageted.getInitialStates()) {
			for (S accept : abstractedIntersectionAggrageted.getAcceptStates()) {
				ret = ret
						+ "+"
						+ new Brzozowski<L, S, T>(
								abstractedIntersectionAggrageted, init, accept,
								new IneffectiveStarTransformer(), new UnionTransformer(),
								new ConcatenateTransformer())
								.getRegularExpression();
			}
		}
		return ret;
	}
}
