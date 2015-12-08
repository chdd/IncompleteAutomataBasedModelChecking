package it.polimi.constraintcomputation;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.constraintcomputation.cleaner.AutomatonCleaner;
import it.polimi.constraintcomputation.reachability.ReachabilityIdentifier;
import it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.constraintcomputation.subpropertyidentifier.labeling.TransitionLabeler;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * The constraint generator computes a constraint. A constraint specifies the
 * conditions the behavior if the replacement of the black box state must
 * satisfy
 * 
 * @author Claudio Menghi
 * 
 */
public class ConstraintGenerator extends CHIAAction<Constraint> {

    /**
     * The name of the constraint computation activity
     */
    private final static String NAME = "CONSTRAINT GENERATION";

    /**
     * the checker using to verify the incomplete model against the property of
     * interest
     */
    private final Checker checker;

    /**
     * the constraint generated
     */
    private final Constraint constraint;

    /**
     * contains the CHIA logger
     */
    private static final Logger LOGGER = Logger
            .getLogger(ConstraintGenerator.class);

    /**
     * is a map that contains for each sub-property the identifier used to
     * compute the sub-property
     */
    private final Map<SubProperty, SubPropertyIdentifier> subpropetySubPropertyIdentifierMap;

    /**
     * maps each black box state to the corresponding sub-property
     */
    private final Map<State, SubProperty> mapBlackBoxStateSubProperty;

    /**
     * maps each black box state to the identifier used to compute the
     * sub-property
     */
    private final Map<State, SubPropertyIdentifier> mapBlackBoxStateSubPropertyIdentifier;

    /**
     * creates a new ConstraintGenerator object which starting from the checker
     * used to verify the property of interest over the incomplete model
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
        Preconditions.checkNotNull(checker, "The checker cannot be null");
        Preconditions
                .checkState(
                        checker.perform() == SatisfactionValue.POSSIBLYSATISFIED,
                        "You can perform the constraint generation iff the claim is possibly satisfied");

        this.checker = checker;
        this.constraint = new Constraint();
        this.subpropetySubPropertyIdentifierMap = new HashMap<SubProperty, SubPropertyIdentifier>();
        this.mapBlackBoxStateSubProperty = new HashMap<State, SubProperty>();
        this.mapBlackBoxStateSubPropertyIdentifier = new HashMap<State, SubPropertyIdentifier>();
    }

    /**
     * returns the constraint associated with the black box states
     * 
     * @return the constraint associated with the black box states
     */
    @Override
    public Constraint perform() {

        LOGGER.debug("starting the cleaning phase");
        /*
         * computes the states of the intersection automaton from which it is
         * not possible to reach an accepting state since these states are not
         * useful in the constraint computation
         */
        AutomatonCleaner intersectionCleaner = new AutomatonCleaner(
                this.checker.getUpperIntersectionBuilder()
                        .getIntersectionAutomaton());
        Set<State> removedStates = intersectionCleaner.clean();

        LOGGER.debug("The Automaton cleaner has removed: "
                + removedStates.size());

        // removing the removed states from the intersection and the
        // intersection builder
        for (State s : removedStates) {
            this.checker.getUpperIntersectionBuilder().removeIntersectionState(
                    s);
        }

        for (State blackBoxState : this.checker.getUpperIntersectionBuilder()
                .getModel().getBlackBoxStates()) {

            LOGGER.debug("Computing the sub-property for the blak box state:"
                    + blackBoxState);
            constraint.addSubProperty(this.generateSubProperty(blackBoxState));

        }

        LOGGER.debug("Intersection cleaned");
        LOGGER.debug("Automaton size: "
                + this.checker.getUpperIntersectionBuilder().perform().size());
        this.computeLabels();

        LOGGER.debug("Labels of the incoming and outgoing transitions computed");
        LOGGER.debug("Computing the incoming and outgoing transitions reachability");
        this.computeTransitionReachability();
        LOGGER.debug("Incoming and outgoing transitions reachability computed");
        this.computeIndispensable();
        return constraint;

    }

    /**
     * computes the constraint considering only the specific black box state
     * 
     * @param blackBoxState
     *            the black box state to be considered
     * @return the constraint containing only the sub-property associated with
     *         the black box state
     * @throws NullPointerException
     *             if the black box state is null
     */
    public Constraint perform(State blackBoxState) {
        Preconditions.checkNotNull(blackBoxState,
                "The black box state to be considered cannot be null");
        LOGGER.debug("starting the cleaning phase");
        /*
         * computes the states of the intersection automaton from which it is
         * not possible to reach an accepting state since these states are not
         * useful in the constraint computation
         */
        AutomatonCleaner intersectionCleaner = new AutomatonCleaner(
                this.checker.getUpperIntersectionBuilder()
                        .getIntersectionAutomaton());
        Set<State> removedStates = intersectionCleaner.clean();

        LOGGER.debug("The Automaton cleaner has removed: "
                + removedStates.size());

        // removing the removed states from the intersection and the
        // intersection builder
        for (State s : removedStates) {
            this.checker.getUpperIntersectionBuilder().removeIntersectionState(
                    s);
        }

        LOGGER.debug("Computing the sub-property for the blak box state:"
                + blackBoxState);
        constraint.addSubProperty(this.generateSubProperty(blackBoxState));

        LOGGER.debug("Intersection cleaned");
        LOGGER.debug("Automaton size: "
                + this.checker.getUpperIntersectionBuilder().perform().size());
        this.computeLabels(blackBoxState);

        LOGGER.debug("Labels of the incoming and outgoing transitions computed");
        LOGGER.debug("Computing the incoming and outgoing transitions reachability");
        this.computeTransitionReachability(blackBoxState);
        LOGGER.debug("Incoming and outgoing transitions reachability computed");
        this.computeIndispensable(blackBoxState);
        return constraint;

    }

    /**
     * returns the sub-property associated with the specific black box state
     * 
     * @param blackBoxState
     *            the black box state to be considered
     * @return the sub-property associated with the black box state
     * @throws NullPointerException
     *             if the black box state is null
     */
    private SubProperty generateSubProperty(State blackBoxState) {

        /*
         * extract the sub-properties from the intersection automaton. It
         * identifies the portions of the state space (the set of the mixed
         * states and the transitions between them) that refer to the same black
         * box states of the model. It also compute the corresponding ports,
         * i.e., the set of the transition that connect the sub-property to the
         * original model.
         */
        SubPropertyIdentifier subPropertiesIdentifier = new SubPropertyIdentifier(
                this.checker, blackBoxState);
        SubProperty subProperty = subPropertiesIdentifier.perform();
        this.subpropetySubPropertyIdentifierMap.put(subProperty,
                subPropertiesIdentifier);
        this.mapBlackBoxStateSubProperty.put(blackBoxState, subProperty);
        this.mapBlackBoxStateSubPropertyIdentifier.put(blackBoxState,
                subPropertiesIdentifier);
        return subProperty;
    }

    private void computeLabels(State blackBoxState) {

        Preconditions.checkNotNull(blackBoxState,
                "The black box state cannot be null");
        Preconditions.checkArgument(
                this.mapBlackBoxStateSubProperty.containsKey(blackBoxState),
                "The black box state " + blackBoxState
                        + " is not contained in the map");
        TransitionLabeler labeler = new TransitionLabeler(
                this.mapBlackBoxStateSubPropertyIdentifier.get(blackBoxState));
        labeler.perform();

    }

    /**
     * computes the labels associated with the different black box states
     */
    private void computeLabels() {

        for (java.util.Map.Entry<SubProperty, SubPropertyIdentifier> entry : this.subpropetySubPropertyIdentifierMap
                .entrySet()) {
            TransitionLabeler labeler = new TransitionLabeler(entry.getValue());
            labeler.perform();
        }
    }

    /**
     * computes the reachability between the ports, i.e., it updates the
     * reachability relation between the ports and updates the corresponding
     * colors
     * 
     * @return the constraints where the color of the ports have been updated
     */
    private Constraint computeTransitionReachability() {

        for (Map.Entry<SubProperty, SubPropertyIdentifier> e : subpropetySubPropertyIdentifierMap
                .entrySet()) {
            LOGGER.debug("Computing the reachability of the sub-property: "
                    + e.getKey().getModelState());
            ReachabilityIdentifier reachability = new ReachabilityIdentifier(
                    e.getValue());
            reachability.perform();

        }

        return constraint;

    }

    private Constraint computeTransitionReachability(State blackBoxState) {

        Preconditions.checkNotNull(blackBoxState,
                "The black box state cannot be null");
        Preconditions.checkArgument(this.mapBlackBoxStateSubPropertyIdentifier
                .containsKey(blackBoxState), "The black box state "
                + blackBoxState + " is not contained in the map");
        ReachabilityIdentifier reachability = new ReachabilityIdentifier(
                this.mapBlackBoxStateSubPropertyIdentifier.get(blackBoxState));
        reachability.perform();

        return constraint;

    }

    private void computeIndispensable(State blackBoxState) {
        Preconditions.checkNotNull(blackBoxState,
                "The black box state cannot be null");
        Preconditions.checkArgument(
                this.mapBlackBoxStateSubProperty.containsKey(blackBoxState),
                "The black box state " + blackBoxState
                        + " is not contained in the map");
        SubProperty subProperty = this.mapBlackBoxStateSubProperty
                .get(blackBoxState);
        State modelState = subProperty.getModelState();
        IBA model = checker.getUpperIntersectionBuilder().getModel().clone();
        model.removeState(modelState);
        SatisfactionValue value = new Checker(model, checker
                .getUpperIntersectionBuilder().getClaim(), checker
                .getUpperIntersectionBuilder().getAcceptingPolicy()).perform();
        if (value == SatisfactionValue.NOTSATISFIED) {
            throw new InternalError(
                    "It is not possible that removing a black box state of the model makes the property not satisfied");
        }
        if (value == SatisfactionValue.POSSIBLYSATISFIED) {
            subProperty.setIndispensable(false);
        }
        if (value == SatisfactionValue.SATISFIED) {
            subProperty.setIndispensable(true);
        }

    }

    private void computeIndispensable() {
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
                        "It is not possible that removing a black box state of the model makes the property not satisfied");
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
