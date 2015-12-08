package it.polimi.constraintcomputation.subpropertyidentifier;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.PropositionalLogicConstants;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.Checker;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * This class identifies the sub-automata of the automaton that refer to the
 * <b>black box</b> states of the original model. In particular it isolates the
 * portions of the state space that refer to the black box states of the model
 * into components. <br>
 * 
 * Each component includes the refinements of the black box states that make the
 * intersection not empty
 *
 * @author claudiomenghi
 * 
 */
public class SubPropertyIdentifier extends CHIAAction<SubProperty> {

    private final Set<IGraphProposition> stutteringPropositions;

    /**
     * contains the subProperty that refers to the black box state
     */
    private final SubProperty subProperty;

    /**
     * is the checker which has been used to check the model against the
     * corresponding claim
     */
    private final Checker checker;

    /**
     * the black box state that is considered
     */
    private final State blackBoxState;

    /**
     * The incoming transitions are the transitions that enters the current
     * refinement level: they can be initial transition, i.e., transitions that
     * arrives from the outside or transition that reaches the current level
     * from the refinement of a black box state
     */
    private final Map<Transition, LabeledPluggingTransition> mapIntersectionTransitionOutgoingTransition;

    /**
     * The out-coming transitions are the transition that leave the current
     * refinement level: they can be final transition, i.e., transitions that
     * leaves the current refinement level to an ``upper level" component or
     * transitions that enter the black box state
     */
    private final Map<Transition, LabeledPluggingTransition> mapIntersectionTransitionIncomingPort;

    /**
     * creates an identifier that is used to isolate the sub-property that
     * refers to the blackBoxState
     * 
     * @param checker
     *            is the checker that has been used to check the model and the
     *            claim
     * @param blackBoxState
     *            is the black box state of interest
     * @throws NullPointerException
     *             if the checker is null or the blackBoxState is null
     * @throws IllegalArgumentException
     *             the blackBoxState must be a black box state of the model
     * @throws IllegalStateException
     *             the checking activity must be performed before the
     *             sub-property identification
     * 
     */
    public SubPropertyIdentifier(Checker checker, State blackBoxState) {

        super("SUB-PROPERTY identifier");
        Preconditions.checkNotNull(checker, "The checker cannot be null");
        Preconditions.checkNotNull(blackBoxState,
                "The black box state to be considered cannot be null");

        Preconditions
                .checkState(
                        checker.isPerformed(),
                        "The checking activity must be performed before the computation of the sub-property");
        Preconditions
                .checkArgument(checker.getUpperIntersectionBuilder().getModel()
                        .getBlackBoxStates().contains(blackBoxState),
                        "The state to be considered must be a black box state of the model");

        this.blackBoxState = blackBoxState;
        this.checker = checker;

        this.subProperty = new SubProperty(blackBoxState, new BA(
                new ClaimTransitionFactory()));

        this.mapIntersectionTransitionOutgoingTransition = new HashMap<Transition, LabeledPluggingTransition>();
        this.mapIntersectionTransitionIncomingPort = new HashMap<Transition, LabeledPluggingTransition>();
        this.stutteringPropositions = new HashSet<IGraphProposition>();
        this.stutteringPropositions.add(new GraphProposition(
                PropositionalLogicConstants.STUTTERING_CHARACTER, false));

    }

    /**
     * returns an abstracted version of the intersection automaton, where each
     * state is a component and represents a state of the original model and
     * aggregates the states of the intersection automaton which refer to the
     * same black box state
     * 
     * @return the sub-automata of the automaton that refer to the black box
     *         states of M.
     */
    @Override
    public SubProperty perform() {

        if (this.isPerformed()) {
            return this.subProperty;
        }

        // adds the propositions to the set of the propositions of the automaton
        // associated with the sub-Property
        this.addPropositions();
        // creates the states associated with the sub-property
        this.createStates();
        // creates the internal transitions and the incoming and outgoing
        // transitions
        this.createTransitions();

        this.performed();
        return this.subProperty;
    }

    /**
     * creates the transitions inside the current refinement
     */
    private void createTransitions() {
        this.createInternalTransitions();
        this.createIncomingTransitions();
        this.createOutgoingTransitions();
    }

    /**
     * adds the propositions associated to the claim to the set of propositions
     * of the automaton associated with the sub-property
     */
    private void addPropositions() {
        /*
         * creates a component which correspond with the state modelState
         */
        this.subProperty.getAutomaton().addPropositions(
                this.checker.getUpperIntersectionBuilder().getClaim()
                        .getPropositions());

    }

    /**
     * creates the states of the automaton associated with the sub-property
     */
    private void createStates() {

        /*
         * gets the intersectionState associated with the state of the model
         * modelState
         */
        for (State intersectionState : this.checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        this.blackBoxState)) {
            this.subProperty.getAutomaton().addState(intersectionState);
            if (this.checker.getUpperIntersectionBuilder()
                    .getIntersectionAutomaton().getInitialStates()
                    .contains(intersectionState)) {
                // add the component to the initial states of the
                // abstracted
                // automaton
                this.subProperty.getAutomaton().addInitialState(
                        intersectionState);
            }
            if (this.checker.getUpperIntersectionBuilder()
                    .getIntersectionAutomaton().getAcceptStates()
                    .contains(intersectionState)) {
                // add the component to the accepting states of the
                // abstracted automaton
                this.subProperty.getAutomaton().addAcceptState(
                        intersectionState);
            }
        }
    }

    /**
     * creates the transitions to be inserted into the automaton that refines
     * the sub-property. These transitions include all the transitions that are
     * associated to a black box state of the model
     */
    private void createInternalTransitions() {
        for (Transition internalTransition : this.checker
                .getUpperIntersectionBuilder().getConstrainedTransitions(
                        this.blackBoxState)) {

            Transition newTransition = new ClaimTransitionFactory().create(
                    internalTransition.getId(),
                    internalTransition.getPropositions());
            State sourceState = this.checker.getUpperIntersectionBuilder()
                    .getIntersectionAutomaton()
                    .getTransitionSource(internalTransition);
            State destinationState = this.checker.getUpperIntersectionBuilder()
                    .getIntersectionAutomaton()
                    .getTransitionDestination(internalTransition);
            this.subProperty.getAutomaton().addTransition(sourceState,
                    destinationState, newTransition);

        }
    }

    /**
     * creates the incoming transitions associated with the sub-property
     */
    private void createIncomingTransitions() {
        for (State intersectionState : this.checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        this.blackBoxState)) {

            /*
             * first the algorithm searches for out-coming transition. The
             * transition that reaches an intersection state associated with a
             * black box state of the model are POTENTIAL out-coming transitions
             * since they leaves the current level of refinement, i.e., they
             * enter the refinement of the black box state
             */
            for (Transition incomingTransition : this.checker
                    .getUpperIntersectionBuilder().getIntersectionAutomaton()
                    .getInTransitions(intersectionState)) {

                if (!this.checker.getUpperIntersectionBuilder()
                        .getConstrainedTransitions(this.blackBoxState)
                        .contains(incomingTransition)) {
                    State sourceIntersectionState = this.checker
                            .getUpperIntersectionBuilder()
                            .getIntersectionAutomaton()
                            .getTransitionSource(incomingTransition);

                    if (!this.checker.getUpperIntersectionBuilder()
                            .getModelState(sourceIntersectionState)
                            .equals(blackBoxState)) {

                        /*
                         * the destination is an intersection state since I left
                         * the current level of refinement to go to the
                         * refinement, i.e., the intersection
                         */
                        LabeledPluggingTransition incomingPort = new LabeledPluggingTransition(
                                this.checker.getUpperIntersectionBuilder()
                                        .getModelState(sourceIntersectionState),
                                intersectionState, incomingTransition, true,
                                Label.B);

                        if (!this.subProperty.getIncomingTransitions()
                                .contains(incomingPort)) {
                            this.mapIntersectionTransitionIncomingPort.put(
                                    incomingTransition, incomingPort);
                            /*
                             * the port outcomingPort is out-coming for the
                             * current level of refinement but is an incoming
                             * port with respect to the
                             * intersectionStateComponent
                             */
                            this.subProperty
                                    .addIncomingTransition(incomingPort);
                        } else {
                            this.mapIntersectionTransitionIncomingPort
                                    .put(incomingTransition,
                                            this.subProperty
                                                    .getIncomingTransition(incomingPort));
                        }
                    }
                }
            }
        }
    }

    /**
     * creates the outgoing transition associated with the sub-property
     */
    private void createOutgoingTransitions() {

        for (State intersectionState : this.checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        this.blackBoxState)) {
            /*
             * The transitions that exit a mixed state are potential incoming
             * transitions since they are potential transitions that moves from
             * the refinement of the black box state to the current level of
             * abstraction
             */
            for (Transition outgoingTransition : this.checker
                    .getUpperIntersectionBuilder().getIntersectionAutomaton()
                    .getOutTransitions(intersectionState)) {
                if (!this.checker.getUpperIntersectionBuilder()
                        .getIntersectionAutomaton().getConstrainedTransitions()
                        .contains(outgoingTransition)) {
                    State destinationIntersectionState = this.checker
                            .getUpperIntersectionBuilder()
                            .getIntersectionAutomaton()
                            .getTransitionDestination(outgoingTransition);

                    /*
                     * the source state is an intersection state since I leaved
                     * the previous level of the refinement to go to the current
                     * one (exit the black box)
                     */
                    LabeledPluggingTransition labeledOutgoingTransition = new LabeledPluggingTransition(
                            intersectionState,
                            this.checker
                                    .getUpperIntersectionBuilder()
                                    .getModelState(destinationIntersectionState),
                            outgoingTransition, false, Label.B);

                    if (!this.subProperty.getOutgoingTransitions().contains(
                            labeledOutgoingTransition)) {
                        this.mapIntersectionTransitionOutgoingTransition.put(
                                outgoingTransition, labeledOutgoingTransition);

                        this.subProperty
                                .addOutgoingTransition(labeledOutgoingTransition);
                    } else {
                        this.mapIntersectionTransitionOutgoingTransition
                                .put(outgoingTransition,
                                        this.subProperty
                                                .getOutgoingTransition(labeledOutgoingTransition));
                    }

                }
            }
        }
    }

    /**
     * returns true if the intersection transition T is associated with an
     * incoming transition
     * 
     * @param intersectionTransition
     *            is the transition of the intersection automaton
     * @return true if the transition of the intersection automaton is
     *         associated with a incoming transition
     * @throws NullPointerException
     *             if the transition t is null
     * @throws IllegalStateException
     *             if the subproperty identifier has not been performed
     */
    public boolean isInTransition(Transition intersectionTransition) {
        Preconditions
                .checkState(this.isPerformed(),
                        "You must compute the subproperties before performing this operation");
        Preconditions.checkNotNull(intersectionTransition,
                "The transition to be considered cannot be null");
        if (mapIntersectionTransitionIncomingPort
                .containsKey(intersectionTransition)) {
            return true;
        }
        return false;
    }

    /**
     * returns true if the intersection transition T is associated with an
     * outgoing transition
     * 
     * @param intersectionTransition
     *            is the transition of the intersection automaton
     * @return true if the transition of the intersection automaton is
     *         associated with a outgoing transition
     * @throws NullPointerException
     *             if the transition t is null
     */
    public boolean isOutTransition(Transition intersectionTransition) {
        Preconditions
                .checkState(this.isPerformed(),
                        "You must compute the subproperties before performing this operation");
        Preconditions.checkNotNull(intersectionTransition,
                "The transition to be considered cannot be null");
        if (mapIntersectionTransitionOutgoingTransition
                .containsKey(intersectionTransition)) {
            return true;
        }
        return false;
    }

    /**
     * return the outgoing transition associated with the intersection
     * transition T
     * 
     * @param intersectionTransition
     *            is the transition of the intersection automaton which is
     *            associated with an outgoing transition
     * @return the outgoing transition associated with the transition of the
     *         intersection automaton
     * @throws NullPointerException
     *             if the transition t is null
     * @throws IllegalArgumentException
     *             if the transition t is not associated with an outgoing
     *             transition
     */
    public LabeledPluggingTransition getOutgoingTransition(
            Transition intersectionTransition) {
        Preconditions
                .checkState(this.isPerformed(),
                        "You must compute the subproperties before performing this operation");
        Preconditions.checkNotNull(intersectionTransition,
                "The transition to be considered cannot be null");
        Preconditions.checkArgument(
                this.isOutTransition(intersectionTransition), "The transition "
                        + intersectionTransition
                        + " must be associated with an outgoing transition");
        return mapIntersectionTransitionOutgoingTransition
                .get(intersectionTransition);
    }

    /**
     * return the in-coming port associated with the intersection transition T
     * 
     * @param intersectionTransition
     *            is the transition of the intersection automaton which is
     *            associated with a port
     * @return the in-coming port associated with the transition t
     * @throws NullPointerException
     *             if the transition t is null
     * @throws IllegalArgumentException
     *             if the transition t is not associated with a port
     */
    public LabeledPluggingTransition getIncomingTransition(
            Transition intersectionTransition) {
        Preconditions
                .checkState(this.isPerformed(),
                        "You must compute the subproperties before performing this operation");
        Preconditions.checkNotNull(intersectionTransition,
                "The transition to be considered cannot be null");
        Preconditions.checkArgument(
                this.isInTransition(intersectionTransition), "The transition "
                        + intersectionTransition
                        + " must be associated with a port");
        return mapIntersectionTransitionIncomingPort
                .get(intersectionTransition);
    }

    /**
     * returns a map that associates to each transition of the intersection
     * transition the corresponding incoming transition. If an intersection
     * transition is not associated with an incoming transition it is not
     * contained in the map
     * 
     * @return a map that associates to each transition of the intersection
     *         transition the corresponding incoming transition. If an
     *         intersection transition is not associated with an incoming
     *         transition it is not contained in the map
     * @throws IllegalStateException
     *             if the {@link SubPropertyIdentifier} is not performed before
     *             invoking this method
     */
    public Map<Transition, LabeledPluggingTransition> getMapIntersectionTransitionIncomingTransitions() {
        Preconditions
                .checkState(
                        this.isPerformed(),
                        "The map of the incoming ports can be obtained only after the sub-PropertyIdentifier has been performed");

        return Collections
                .unmodifiableMap(mapIntersectionTransitionIncomingPort);
    }

    /**
     * returns a map that associates to each transition of the intersection
     * transition the corresponding outgoing transition. If an intersection
     * transition is not associated with an outgoing transition it is not
     * contained in the map
     * 
     * @return a map that associates to each transition of the intersection
     *         transition the corresponding outgoing transition. If an
     *         intersection transition is not associated with an outgoing
     *         transition it is not contained in the map
     * @throws IllegalStateException
     *             if the {@link SubPropertyIdentifier} is not performed before
     *             invoking this method
     */
    public Map<Transition, LabeledPluggingTransition> getMapIntersectionTransitionOutgoingTransitions() {
        Preconditions
                .checkState(
                        this.isPerformed(),
                        "The map of the outcoming ports can be obtained only after the sub-PropertyIdentifier has been performed");
        return Collections
                .unmodifiableMap(mapIntersectionTransitionOutgoingTransition);
    }

    /**
     * returns the checker which is associated with the specific sub-property
     * identifier
     * 
     * @return the checker associated with the sub-property identifier
     */
    public Checker getChecker() {
        return this.checker;
    }
}
