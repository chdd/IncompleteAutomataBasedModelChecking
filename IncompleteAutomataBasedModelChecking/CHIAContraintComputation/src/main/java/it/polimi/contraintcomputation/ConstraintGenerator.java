package it.polimi.contraintcomputation;

import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.constraints.Constraint;
import it.polimi.contraintcomputation.subautomatafinder.IntersectionCleaner;
import it.polimi.contraintcomputation.subautomatafinder.SubPropertiesIdentifier;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The constraint generator computes a constraint. A constraint is a (set of)
 * sub-model(s) for the unspecified components is produce
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator<S extends State, T extends Transition> {

	/**
	 * is the logger of the ConstraintGenerator class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintGenerator.class);

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, Set<S>> modelIntersectionStatesMap;

	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<S, T> intBA;

	/**
	 * is the factory to be used to create transitions
	 */
	private TransitionFactory<S, T> subPropertiesTransitionFactory;

	/**
	 * contains the model of the system
	 */
	private IBA<S, T> model;

	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param intBA
	 *            is the intersection automaton
	 * @param model
	 *            is the model to be verified
	 * @param modelIntersectionStatesMap
	 *            is the map between the states of the model and the
	 *            corresponding states in the intersection automaton
	 * @param subpropertiestransitionFactory
	 *            is the factory to be used to create transitions
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ConstraintGenerator(IntersectionBA<S, T> intBA, IBA<S, T> model,
			Map<S, Set<S>> modelIntersectionStatesMap,
			TransitionFactory<S, T> subpropertiestransitionFactory) {

		Validate.notNull(intBA, "The intersection model cannot be null");
		Validate.notNull(model, "The model of the system cannot be null");
		Validate.notNull(
				modelIntersectionStatesMap,
				"The map between the states of the model and the intersection states cannot be null");
		Validate.notNull(subpropertiestransitionFactory,
				"The transition factory cannot be null");

		this.modelIntersectionStatesMap = modelIntersectionStatesMap;
		this.subPropertiesTransitionFactory = subpropertiestransitionFactory;
		this.intBA = intBA;
		this.model = model;
	}

	/**
	 * returns the constraint of the automaton
	 * 
	 * @return the constraint of the automaton
	 */
	public Constraint<S, T> generateConstraint() {

		logger.info("Computing the constraint");
		/*
		 * removes from the intersection automaton the states from which it is
		 * not possible to reach an accepting state since these states are not
		 * useful in the constraint computation
		 */
		IntersectionCleaner<S, T> intersectionCleaner = new IntersectionCleaner<S, T>(
				intBA);
		intersectionCleaner.clean();

		SubPropertiesIdentifier<S, T> subPropertiesIdentifier = new SubPropertiesIdentifier<S, T>(
				intBA, model, this.modelIntersectionStatesMap,
				this.subPropertiesTransitionFactory);
		Constraint<S, T> constraint = subPropertiesIdentifier.getSubAutomata();

		logger.info("Constraint computed");
		return constraint;
	}
}
