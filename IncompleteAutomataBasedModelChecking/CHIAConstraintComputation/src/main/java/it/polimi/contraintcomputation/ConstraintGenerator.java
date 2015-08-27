package it.polimi.contraintcomputation;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;
import it.polimi.contraintcomputation.reachability.ReachabilityIdentifier;
import it.polimi.contraintcomputation.subpropertyidentifier.IntersectionCleaner;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.contraintcomputation.subpropertyidentifier.coloring.TransitionLabeler;

import java.util.HashMap;
import java.util.Map;

import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * The constraint generator computes a constraint. A constraint specifies the
 * behavior the replacement of the transparent state must satisfy
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator extends CHIAAction<Constraint> {

	private final static String NAME = "CONSTRAINT GENERATION";

	private final Checker checker;

	private final Constraint constraint;

	/**
	 * is a map that contains for each subproperty the subpropertyIdentifier
	 * using to compute the sub-property
	 */
	private final Map<SubProperty, SubPropertyIdentifier> subpropetySubPropertyIdentifierMap;

	private final Map<State, SubProperty> mapTransparentStateSubProperty;

	private final Map<State, SubPropertyIdentifier> mapTransparentStateSubPropertyIdentifier;

	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param checker
	 *            is the model checker
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalStateException
	 *             if the checker has not been executed before the constraint
	 *             generation
	 */
	public ConstraintGenerator(Checker checker) {
		super(NAME);
		Preconditions.checkNotNull(checker, "The model checker cannot be null");
		Preconditions
				.checkState(
						checker.perform() == SatisfactionValue.POSSIBLYSATISFIED,
						"You can perform the constraint generation iff the claim is possibly satisfied");

		Preconditions.checkNotNull(checker,
				"The intersection builder cannot be null");
		this.checker = checker;
		this.constraint = new Constraint();
		this.subpropetySubPropertyIdentifierMap = new HashMap<SubProperty, SubPropertyIdentifier>();
		this.mapTransparentStateSubProperty = new HashMap<State, SubProperty>();
		this.mapTransparentStateSubPropertyIdentifier = new HashMap<State, SubPropertyIdentifier>();
	}

	/**
	 * returns the constraint of the automaton
	 * 
	 * @return the constraint of the automaton
	 */
	public Constraint perform() {

		/*
		 * removes from the intersection automaton the states from which it is
		 * not possible to reach an accepting state since these states are not
		 * useful in the constraint computation
		 */
		IntersectionCleaner intersectionCleaner = new IntersectionCleaner(
				this.checker.getUpperIntersectionBuilder());
		intersectionCleaner.clean();

		for (State transparentState : this.checker
				.getUpperIntersectionBuilder().getModel()
				.getBlackBoxStates()) {

			constraint.addSubProperty(this
					.generateSubProperty(transparentState));

		}

		return constraint;

	}

	/**
	 * returns the sub-property associated with the specific transparent state
	 * 
	 * @param transparentState
	 *            the transparent state to be considered
	 * @return the sub-property associated with the transparent state
	 * @throws NullPointerException
	 *             if the transparent state is null
	 */
	private SubProperty generateSubProperty(State transparentState) {
		Preconditions.checkNotNull(transparentState,
				"The transparent state to be considered cannot be null");
		/*
		 * extract the sub-properties from the intersection automaton. It
		 * identifies the portions of the state space (the set of the mixed
		 * states and the transitions between them) that refer to the same
		 * transparent states of the model. It also compute the corresponding
		 * ports, i.e., the set of the transition that connect the sub-property
		 * to the original model.
		 */
		SubPropertyIdentifier subPropertiesIdentifier = new SubPropertyIdentifier(
				this.checker, transparentState);
		SubProperty subProperty = subPropertiesIdentifier.getSubProperty();
		this.subpropetySubPropertyIdentifierMap.put(subProperty,
				subPropertiesIdentifier);
		this.mapTransparentStateSubProperty.put(transparentState, subProperty);
		this.mapTransparentStateSubPropertyIdentifier.put(transparentState,
				subPropertiesIdentifier);
		return subProperty;
	}

	public void coloring(State transparentState) {

		Preconditions.checkNotNull(transparentState,
				"The transparent state cannot be null");
		Preconditions.checkArgument(this.mapTransparentStateSubProperty
				.containsKey(transparentState), "The transparent state "
				+ transparentState + " is not contained in the map");
		TransitionLabeler coloring = new TransitionLabeler(
				this.mapTransparentStateSubPropertyIdentifier
						.get(transparentState));
		coloring.startColoring();

	}

	public void coloring() {

		for (java.util.Map.Entry<SubProperty, SubPropertyIdentifier> entry : this.subpropetySubPropertyIdentifierMap
				.entrySet()) {
			TransitionLabeler coloring = new TransitionLabeler(entry.getValue());
			coloring.startColoring();
		}
	}

	/**
	 * computes the reachability between the ports, i.e., it updates the
	 * reachability relation between the ports and updates the corresponding
	 * colors
	 * 
	 * @return the constraints where the color of the ports have been updated
	 */
	public Constraint computePortReachability() {

		for (Map.Entry<SubProperty, SubPropertyIdentifier> e : subpropetySubPropertyIdentifierMap
				.entrySet()) {
			ReachabilityIdentifier reachability = new ReachabilityIdentifier(
					this.checker.getUpperIntersectionBuilder(), e.getValue());
			reachability.computeReachability();

		}

		return constraint;

	}

	public Constraint computePortReachability(State transparentState) {

		Preconditions.checkNotNull(transparentState,
				"The transparent state cannot be null");
		Preconditions.checkArgument(
				this.mapTransparentStateSubPropertyIdentifier
						.containsKey(transparentState),
				"The transparent state " + transparentState
						+ " is not contained in the map");
		ReachabilityIdentifier reachability = new ReachabilityIdentifier(
				this.checker.getUpperIntersectionBuilder(),
				this.mapTransparentStateSubPropertyIdentifier
						.get(transparentState));
		reachability.computeReachability();

		return constraint;

	}

	public void computeIndispensable(State transparentState) {
		Preconditions.checkNotNull(transparentState,
				"The transparent state cannot be null");
		Preconditions.checkArgument(this.mapTransparentStateSubProperty
				.containsKey(transparentState), "The transparent state "
				+ transparentState + " is not contained in the map");
		SubProperty subProperty=this.mapTransparentStateSubProperty.get(transparentState);
		State modelState = subProperty.getModelState();
		IBA model = checker.getUpperIntersectionBuilder().getModel().clone();
		model.removeState(modelState);
		SatisfactionValue value = new Checker(model, checker
				.getUpperIntersectionBuilder().getClaim(), checker
				.getUpperIntersectionBuilder().getAcceptingPolicy()).perform();
		if (value == SatisfactionValue.NOTSATISFIED) {
			throw new InternalError(
					"It is not possible that removing a transparent state of the model makes the property not satisfied");
		}
		if (value == SatisfactionValue.POSSIBLYSATISFIED) {
			subProperty.setIndispensable(false);
		}
		if (value == SatisfactionValue.SATISFIED) {
			subProperty.setIndispensable(true);
		}

	}

	public void computeIndispensable() {
		for (java.util.Map.Entry<SubProperty, SubPropertyIdentifier> entry : this.subpropetySubPropertyIdentifierMap
				.entrySet()) {
			State modelState = entry.getKey().getModelState();
			IBA model = checker.getUpperIntersectionBuilder().getModel()
					.clone();
			model.removeState(modelState);
			SatisfactionValue value = new Checker(model, checker
					.getUpperIntersectionBuilder().getClaim(), checker
					.getUpperIntersectionBuilder().getAcceptingPolicy())
					.perform();
			if (value == SatisfactionValue.NOTSATISFIED) {
				throw new InternalError(
						"It is not possible that removing a transparent state of the model makes the property not satisfied");
			}
			if (value == SatisfactionValue.POSSIBLYSATISFIED) {
				entry.getKey().setIndispensable(false);
			}
			if (value == SatisfactionValue.SATISFIED) {
				entry.getKey().setIndispensable(true);
			}
		}

	}
}
