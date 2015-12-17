package it.polimi.replacementchecker.intersectionbuilder;

import it.polimi.action.CHIAAction;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.ibablackboxstateremove.IBABlackBoxRemover;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.reachability.ReachabilityEntry;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;
import it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy;
import it.polimi.replacementchecker.utils.Utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

/**
 * Computes the intersection between an Incomplete Buchi automaton and a Buchi
 * Automaton
 * 
 * @author Claudio Menghi
 */
public class ReplacementIntersectionBuilder extends CHIAAction<IntersectionBA> {

    private final static String NAME = "COMPUTE INTERSECTION";
    /**
     * contains the intersection automaton generated
     */
    private IntersectionBA intersection;

    private final State greenState;
    /**
     * The yellow initial state to be injected into the over approximation
     * automaton
     */
    private final State yellowInitialState;

    /**
     * The yellow accepting state to be injected into the over approximation
     * automaton
     */
    private final State yellowAcceptingState;

    private final State redState;
    /**
     * contains the set of the visited states
     */
    private final Set<Triple<State, State, Integer>> visitedStates;

    /**
     * contains the intersection rule which is used to build the intersection
     * transitions
     */
    private final IntersectionTransitionBuilder intersectionTransitionBuilder;

    /**
     * contains a map that associate to each constraint transition the
     * corresponding model state
     */
    private final Map<Transition, State> mapConstrainedTransitionModelBlackBoxState;
    private final SetMultimap<State, Transition> mapBlackBoxStateConstrainedTransition;

    /**
     * Keeps track of the created states. For each couple of state of the model
     * and of the claim, given an integer returns the state of the intersection
     * automaton
     */
    private final Map<State, Map<State, Map<Integer, State>>> createdStates;

    /**
     * for each state of the model contains the corresponding states of the
     * intersection automaton
     */
    private final Map<State, State> intersectionStateModelStateMap;

    private SetMultimap<State, State> modelStateintersectionStateMap;

    private final ReachabilityRelation reachabilityRelation;

    /**
     * for each state of the claim contains the corresponding states of the
     * intersection automaton
     */
    private final Map<State, State> intersectionStateClaimStateMap;
    private SetMultimap<State, State> claimStateintersectionStateMap;

    /**
     * contains the model to be considered in the intersection procedure
     */
    private final Replacement replacement;

    /**
     * contains the claim to be considered in the intersection procedure
     */
    private final SubProperty subproperty;

    private final IBA replacementIBA;

    /**
     * contains the factory which is used to create the states of the
     * intersection automaton
     */
    private final IntersectionStateFactory intersectionStateFactory;

    /**
     * is the accepting policy to be used in the computation of the intersection
     * automaton
     */
    private final ReplacementAcceptingPolicy acceptingPolicy;

    private final boolean underApproximation;

    /**
     * crates a new {@link ReplacementIntersectionBuilder} which is in charge of
     * computing the intersection automaton
     * 
     * @param replacement
     *            is the replacement to be considered
     * @param subproperty
     *            is the sub-property to be considered
     * @param underApproximation
     *            is true if the underApproximation must be considered, false if
     *            the lower approximation must be evaluated
     * @throws NullPointerException
     *             if one of the parameters is null
     * @throws IllegalArgumentException
     *             if the accepting policy is a {@link KripkeAcceptingPolicy}
     *             and not all the states of the model are accepting
     */
    public ReplacementIntersectionBuilder(Replacement replacement,
            SubProperty subproperty, boolean underApproximation) {
        super(NAME);
        Preconditions.checkNotNull(replacement,
                "The replacement cannot be null");
        Preconditions.checkNotNull(subproperty,
                "The subproperty cannot be null");
        this.greenState = new StateFactory().create("GREEN");
        this.redState = new StateFactory().create("RED");
        this.yellowInitialState = new StateFactory().create("YELLOW INITIAL");
        this.yellowAcceptingState = new StateFactory()
                .create("YELLOW ACCEPTING");

        if (underApproximation) {
            this.replacementIBA = new IBABlackBoxRemover(
                    replacement.getAutomaton()).removeBlackBoxes();
            this.reachabilityRelation = subproperty
                    .getLowerReachabilityRelation();
        } else {
            this.replacementIBA = replacement.getAutomaton();
            this.reachabilityRelation = subproperty
                    .getUpperReachabilityRelation();
        }

        this.intersectionStateModelStateMap = new HashMap<State, State>();
        this.modelStateintersectionStateMap = HashMultimap.create();
        this.intersectionStateClaimStateMap = new HashMap<State, State>();
        this.claimStateintersectionStateMap = HashMultimap.create();
        this.mapBlackBoxStateConstrainedTransition = HashMultimap.create();
        this.acceptingPolicy = new ReplacementAcceptingPolicy(subproperty,
                replacement);
        this.intersectionTransitionBuilder = new IntersectionTransitionBuilder();
        this.intersection = new IntersectionBA();
        this.replacement = replacement;
        this.subproperty = subproperty;
        this.mapConstrainedTransitionModelBlackBoxState = new HashMap<Transition, State>();
        this.visitedStates = new HashSet<Triple<State, State, Integer>>();
        this.createdStates = new HashMap<State, Map<State, Map<Integer, State>>>();
        this.intersectionStateFactory = new IntersectionStateFactory();
        this.underApproximation = underApproximation;
    }

    /**
     * computes the intersection of the replacement and the sub-property
     * specified as parameters
     * 
     * @return the intersection between the replacement and the sub-property
     */
    public IntersectionBA perform() {
        if (!this.isPerformed()) {
            this.updateAlphabet();
            this.addingGreenState();

            if (!underApproximation) {
                this.addingYellowInitialState();
            }
            for (State modelInit : this.replacementIBA.getInitialStates()) {
                for (State claimInit : this.subproperty.getAutomaton()
                        .getInitialStates()) {
                    this.computeIntersection(modelInit, claimInit,
                            this.acceptingPolicy.comuteInitNumber(modelInit,
                                    claimInit));
                }
            }
            this.addingRedState();
            if (!underApproximation) {
                this.addingYellowAcceptingState();
            }
            Multimaps.invertFrom(
                    Multimaps.forMap(this.intersectionStateClaimStateMap),
                    this.claimStateintersectionStateMap);
            Multimaps.invertFrom(
                    Multimaps.forMap(this.intersectionStateModelStateMap),
                    this.modelStateintersectionStateMap);

            Multimaps.invertFrom(Multimaps
                    .forMap(this.mapConstrainedTransitionModelBlackBoxState),
                    this.mapBlackBoxStateConstrainedTransition);

            this.performed();
        }
        return this.intersection;
    }

    /**
     * updates the alphabet of the automaton by adding the set of the characters
     * of the model and the claim
     */
    private void updateAlphabet() {
        for (IGraphProposition l : this.replacementIBA.getPropositions()) {
            this.intersection.addProposition(l);
        }
        for (IGraphProposition l : this.subproperty.getAutomaton()
                .getPropositions()) {
            this.intersection.addProposition(l);
        }
    }

    /**
     * add the green initial state and the corresponding outgoing transitions,
     * that connect the green initial states with the states reachable through
     * transitions marked as green
     */
    private void addingGreenState() {
        this.intersection.addState(greenState);
        this.intersection.addInitialState(greenState);

        for (LabeledPluggingTransition initTransitionSubProperty : this.subproperty
                .getIncomingTransitions()) {
            if (initTransitionSubProperty.getLabel() == Label.G) {
                for (PluggingTransition initTransitionReplacement : this.replacement
                        .getIncomingTransitions()) {
                    // if the destination of the incoming transition of the
                    // replacement is a state of the replacement
                    // i.e., it was not a transparent state that could be
                    // removed from the replacement IBA
                    if (this.replacementIBA.getStates().contains(
                            initTransitionReplacement.getDestination())) {
                        // if the two incoming transitions have the same source
                        // and
                        // the same label
                        if (Utils.isIncomingEqual(initTransitionReplacement,
                                initTransitionSubProperty)) {

                            State state = this.computeIntersection(
                                    initTransitionReplacement.getDestination(),
                                    initTransitionSubProperty.getDestination(),
                                    0);

                            Transition transition = new ModelTransitionFactory()
                                    .create();
                            this.intersection.addTransition(greenState, state,
                                    transition);
                            this.computeIntersection(
                                    initTransitionReplacement.getDestination(),
                                    initTransitionSubProperty.getDestination(),
                                    0);
                        }
                    }
                }
            }
        }
    }

    /**
     * add the green initial state and the corresponding outgoing transitions,
     * that connect the green initial states with the states reachable through
     * transitions marked as green
     */
    private void addingYellowInitialState() {
        this.intersection.addState(yellowInitialState);
        this.intersection.addInitialState(yellowInitialState);

        for (LabeledPluggingTransition initTransitionSubProperty : this.subproperty
                .getIncomingTransitions()) {

            if (initTransitionSubProperty.getLabel() == Label.Y) {
                for (PluggingTransition initTransitionReplacement : this.replacement
                        .getIncomingTransitions()) {
                    // if the two incoming transitions have the same source
                    // and
                    // the same label
                    if (Utils.isIncomingEqual(initTransitionReplacement,
                            initTransitionSubProperty)) {

                        State state = this.computeIntersection(
                                initTransitionReplacement.getDestination(),
                                initTransitionSubProperty.getDestination(), 0);

                        Transition transition = new ModelTransitionFactory()
                                .create();
                        this.intersection.addTransition(
                                this.yellowInitialState, state, transition);
                        this.computeIntersection(
                                initTransitionReplacement.getDestination(),
                                initTransitionSubProperty.getDestination(), 0);
                    }

                }
            }
        }
    }

    /**
     * Adds the red accepting state. Connects to the red accepting state all the
     * outgoing transitions of the replacement that match one of the red
     * outgoing transitions of the sub-property
     */
    private void addingRedState() {

        this.intersection.addAcceptState(redState);
        this.intersection.addTransition(redState, redState,
                new ModelTransitionFactory().create());
        for (LabeledPluggingTransition outTransitionSubProperty : this.subproperty
                .getOutgoingTransitions()) {
            if (outTransitionSubProperty.getLabel() == Label.R) {
                for (PluggingTransition outTransitionReplacement : this.replacement
                        .getOutgoingTransitions()) {

                    // if the source of the outgoing transition of the
                    // replacement is a state of the replacement
                    // i.e., it was not a transparent state that could be
                    // removed from the replacement IBA
                    if (this.replacementIBA.getStates().contains(
                            outTransitionReplacement.getSource())) {
                        if (Utils.isOutgoingEqual(outTransitionReplacement,
                                outTransitionSubProperty)) {
                            // getting the created intersection states
                            Set<State> outIntersectionStates = this
                                    .getIntersectionStates(
                                            outTransitionReplacement
                                                    .getSource(),
                                            outTransitionSubProperty
                                                    .getSource());
                            // connecting the the intersection states
                            // associated with the source of the outgoing
                            // transitions to the red state
                            for (State outState : outIntersectionStates) {
                                this.intersection.addTransition(outState,
                                        redState,
                                        new ModelTransitionFactory().create());

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * adds the yellow accepting state and its incoming transitions to the
     * intersection automaton
     */
    private void addingYellowAcceptingState() {

        this.intersection.addAcceptState(this.yellowAcceptingState);
        this.intersection.addTransition(this.yellowAcceptingState,
                this.yellowAcceptingState,
                new ModelTransitionFactory().create());
        for (LabeledPluggingTransition outTransitionSubProperty : this.subproperty
                .getOutgoingTransitions()) {
            if (outTransitionSubProperty.getLabel() == Label.Y) {
                for (PluggingTransition outTransitionReplacement : this.replacement
                        .getOutgoingTransitions()) {

                    if (Utils.isOutgoingEqual(outTransitionReplacement,
                            outTransitionSubProperty)) {
                        // getting the created intersection states
                        Set<State> outIntersectionStates = this
                                .getIntersectionStates(
                                        outTransitionReplacement.getSource(),
                                        outTransitionSubProperty.getSource());
                        // connecting the green and the intersection states
                        // associated with the destination of the incoming
                        // transition
                        for (State outState : outIntersectionStates) {
                            this.intersection.addTransition(outState,
                                    this.yellowAcceptingState,
                                    new ModelTransitionFactory().create());

                        }
                    }

                }
            }
        }
    }

    /**
     * is a recursive procedure that computes the intersection of this automaton
     * and the automaton a2
     * 
     * @param replacementState
     *            is the current state of the model under analysis
     * @param subpropertyState
     *            is the current state of the claim under analysis
     * @param number
     *            is the number of the state under analysis
     * @return the state that is generated
     */
    private State computeIntersection(State replacementState,
            State subpropertyState, int number) {
        Preconditions.checkArgument(this.replacementIBA.getStates().contains(
                replacementState));
        Preconditions.checkArgument(this.subproperty.getAutomaton().getStates()
                .contains(subpropertyState));

        // if the state has been already been visited
        if (this.checkVisitedStates(replacementState, subpropertyState, number)) {
            return this.createdStates.get(replacementState)
                    .get(subpropertyState).get(number);
        } else {

            State intersectionState = this.intersectionStateFactory.create(
                    replacementState, subpropertyState, number);
            this.addStateIntoTheIntersectionAutomaton(intersectionState,
                    replacementState, subpropertyState, number);
            this.updateVisitedStates(intersectionState, replacementState,
                    subpropertyState, number);

            // for each transition in the automaton that exits the model state
            for (Transition modelTransition : this.replacementIBA
                    .getOutTransitions(replacementState)) {
                // for each transition in the extended automaton whit exit the
                // claim
                // state
                for (Transition claimTransition : this.subproperty
                        .getAutomaton().getOutTransitions(subpropertyState)) {

                    // if the two transitions are compatible
                    if (this.intersectionTransitionBuilder.isCompatible(
                            modelTransition, claimTransition)) {

                        // creates a new state made by the states s1next and s2
                        // next
                        State nextModelState = this.replacementIBA
                                .getTransitionDestination(modelTransition);
                        State nextClaimState = this.subproperty.getAutomaton()
                                .getTransitionDestination(claimTransition);

                        int nextNumber = this.acceptingPolicy.comuteNumber(
                                nextModelState, nextClaimState, number);

                        State nextIntersectionState = this.computeIntersection(
                                nextModelState, nextClaimState, nextNumber);

                        Transition t = this.intersectionTransitionBuilder
                                .getIntersectionTransition(modelTransition,
                                        claimTransition);
                        this.intersection.addTransition(intersectionState,
                                nextIntersectionState, t);
                    }
                }
            }

            this.checkReachabilityTransitions(intersectionState,
                    replacementState, subpropertyState, number);
            // if the current state of the extended automaton is black box
            if (this.replacementIBA.isBlackBox(replacementState)) {
                computeBlackBox(replacementState, subpropertyState, number,
                        intersectionState);
            }
            return intersectionState;
        }
    }

    /**
     * check the presence of the reachability transitions, i.e., the transitions
     * generated by the reachability relation
     * 
     * @param intersectionState
     *            is the intersection state from which the reachability
     *            transition starts
     * @param modelState
     *            is the state of the model which corresponds to the
     *            intersection state, i.e., the current state of the model under
     *            analysis
     * @param claimState
     *            is the state of the claim which corresponds to the
     *            intersection state, i.e., the current state of the claim under
     *            analysis
     * @param number
     *            is the number associated with the intersection state
     */
    private void checkReachabilityTransitions(State intersectionState,
            State modelState, State claimState, int number) {
        Preconditions.checkNotNull(intersectionState,
                "The intersection state under analysis cannot be null");
        Preconditions.checkNotNull(modelState,
                "Is the state of the model which is under analysis");
        Preconditions.checkNotNull(claimState,
                "Is the state of the claim which is under analysis");

        Collection<ReachabilityEntry> subPropertyReachabilityEntries = this.reachabilityRelation
                .get(claimState);

        for (ReachabilityEntry subpropertyReachabilityEntry : subPropertyReachabilityEntries) {
            LabeledPluggingTransition outgoingTransition = subpropertyReachabilityEntry
                    .getOutgoingTransition();

            if (this.replacement.hasOutgoingTransition(modelState,
                    outgoingTransition.getDestination(), outgoingTransition
                            .getTransition().getPropositions())) {

                LabeledPluggingTransition coloredReachabilityIncomingTransition = subpropertyReachabilityEntry
                        .getIncomingTransition();

                for (PluggingTransition replacementIncomingTransition : this.replacement
                        .getIncomingTransitions(
                                coloredReachabilityIncomingTransition
                                        .getSource(),
                                coloredReachabilityIncomingTransition
                                        .getTransition().getPropositions())) {
                    int nextnumber = this.acceptingPolicy.computeNumber(
                            subpropertyReachabilityEntry.isModelAccepting(),
                            subpropertyReachabilityEntry.isClaimAccepting(),
                            number, replacementIncomingTransition
                                    .getDestination(),
                            subpropertyReachabilityEntry
                                    .getIncomingTransition().getDestination());

                    State nextModelState = replacementIncomingTransition
                            .getDestination();
                    State nextClaimState = subpropertyReachabilityEntry
                            .getIncomingTransition().getDestination();
                    State nextState = this.computeIntersection(nextModelState,
                            nextClaimState, nextnumber);

                    Transition t = new ModelTransitionFactory().create();
                    this.intersection.addTransition(intersectionState,
                            nextState, t);
                }
            }
        }
    }

    /**
     * @param replacementState
     * @param subPropertyState
     * @param number
     * @param previousIntersectionState
     */
    private void computeBlackBox(State replacementState,
            State subPropertyState, int number, State previousIntersectionState) {

        // for each transition in the automaton a2
        for (Transition claimTransition : this.subproperty.getAutomaton()
                .getOutTransitions(subPropertyState)) {

            State nextClaimState = this.subproperty.getAutomaton()
                    .getTransitionDestination(claimTransition);

            int nextNumber = this.acceptingPolicy.comuteNumber(
                    replacementState, nextClaimState, number);

            State nextState = this.computeIntersection(replacementState,
                    nextClaimState, nextNumber);

            Transition intersectionTransition = new ClaimTransitionFactory()
                    .create(claimTransition.getPropositions());

            this.intersection.addConstrainedTransition(
                    previousIntersectionState, nextState,
                    intersectionTransition);

            this.mapConstrainedTransitionModelBlackBoxState.put(
                    intersectionTransition, replacementState);

        }
    }

    private void updateVisitedStates(State intersectionState, State modelState,
            State claimState, int number) {
        Preconditions
                .checkNotNull(modelState, "The model state cannot be null");
        Preconditions
                .checkNotNull(claimState, "The claim state cannot be null");

        this.visitedStates.add(new ImmutableTriple<State, State, Integer>(
                modelState, claimState, number));

        if (!this.createdStates.containsKey(modelState)) {
            Map<State, Map<Integer, State>> map1 = new HashMap<State, Map<Integer, State>>();
            Map<Integer, State> map2 = new HashMap<Integer, State>();
            map2.put(number, intersectionState);
            map1.put(claimState, map2);
            this.createdStates.put(modelState, map1);

        } else {
            if (!this.createdStates.get(modelState).containsKey(claimState)) {
                Map<Integer, State> map2 = new HashMap<Integer, State>();
                map2.put(number, intersectionState);
                this.createdStates.get(modelState).put(claimState, map2);
            } else {
                this.createdStates.get(modelState).get(claimState)
                        .put(new Integer(number), intersectionState);
            }
        }

        this.intersectionStateModelStateMap.put(intersectionState, modelState);
        this.intersectionStateClaimStateMap.put(intersectionState, claimState);
    }

    private void addStateIntoTheIntersectionAutomaton(State intersectionState,
            State modelState, State claimState, int number) {

        this.intersection.addState(intersectionState);
        if (this.replacementIBA.getInitialStates().contains(modelState)
                && this.subproperty.getAutomaton().getInitialStates()
                        .contains(claimState)) {
            // if (this.acceptingPolicy instanceof KripkeAcceptingPolicy) {
            // this.intersection.addInitialState(intersectionState);
            // } else {
            if (number == 0) {
                this.intersection.addInitialState(intersectionState);
            }
            // }

        }
        if (number == 2) {
            this.intersection.addAcceptState(intersectionState);
        }
        if (this.replacementIBA.isBlackBox(modelState)) {
            this.intersection.addMixedState(intersectionState);
        }

        this.intersectionStateModelStateMap.put(intersectionState, modelState);
        this.intersectionStateClaimStateMap.put(intersectionState, claimState);
    }

    private boolean checkVisitedStates(State modelState, State claimState,
            int number) {
        Preconditions
                .checkNotNull(modelState, "The model state cannot be null");
        Preconditions
                .checkNotNull(claimState, "The claim state cannot be null");

        return this.visitedStates
                .contains(new ImmutableTriple<State, State, Integer>(
                        modelState, claimState, number));
    }

    /**
     * returns the intersection automaton
     * 
     * @return the intersection automaton which have been computed
     * @throws IllegalStateException
     *             if the intersection has still to be computed
     */
    public IntersectionBA getIntersectionAutomaton() {
        Preconditions
                .checkState(
                        this.isPerformed(),
                        "it is necessary to compute the intersection before returning the intersection automaton");
        return this.intersection;

    }

    protected Set<State> getIntersectionStates(State replacementState,
            State subpropertyState) {
        Preconditions.checkNotNull(subpropertyState,
                "The state of the claim cannot be null");
        Preconditions.checkNotNull(replacementState,
                "The state of the model cannot be null");
        Preconditions.checkArgument(this.subproperty.getAutomaton().getStates()
                .contains(subpropertyState), "The state " + subpropertyState
                + " is not contained into the set of the states of the claim");
        Preconditions
                .checkArgument(
                        this.replacementIBA.getStates().contains(
                                replacementState),
                        "The state "
                                + replacementState
                                + " is not contained into the set of the states of the model");
        if (!createdStates.containsKey(replacementState)) {
            return new HashSet<State>();
        } else {
            if (!createdStates.get(replacementState).containsKey(
                    subpropertyState)) {
                return new HashSet<State>();
            } else {
                return Collections.unmodifiableSet(new HashSet<State>(
                        createdStates.get(replacementState)
                                .get(subpropertyState).values()));
            }
        }
    }

    /**
     * returns true if the state of the intersection is the green state
     * 
     * @param intersectionState
     *            is the intersection state to be considered
     * @return true if the state is the green state
     */
    public boolean isGreenState(State intersectionState) {
        return this.greenState.equals(intersectionState);
    }

    /**
     * returns true if the state of the intersection is the red state
     * 
     * @param intersectionState
     *            is the intersection state to be considered
     * @return true if the state is the red state
     */
    public boolean isRedState(State intersectionState) {
        return this.redState.equals(intersectionState);
    }

    /**
     * returns the state of the model associated with the specified intersection
     * state. No intersection states are associated with the green and the red
     * state.
     * 
     * 
     * @param intersectionState
     *            the intersection state to be considered
     * @return the state of the model associated with the specified intersection
     *         state
     * @throws NullPointerException
     *             if the state to be considered is null
     * @throws IllegalArgumentException
     *             if the state is not a state of the intersection automaton. If
     *             the state is the green initial, the yellow initial, the red
     *             accepting or the yellow accepting state
     */
    public State getModelState(State intersectionState) {
        Preconditions
                .checkNotNull(intersectionState,
                        "The state of the intersection automaton to be considered cannot be null");
        Preconditions
                .checkArgument(
                        this.intersection.getStates().contains(
                                intersectionState),
                        "The state to be considered must be a state of the intersection automaton");
        Preconditions
                .checkArgument(!this.greenState.equals(intersectionState),
                        "It is not possible to invoke this method over the green initial state");
        Preconditions
                .checkArgument(!this.redState.equals(intersectionState),
                        "It is not possible to invoke this method over the red accepting state");
        Preconditions
                .checkArgument(
                        !this.yellowInitialState.equals(intersectionState),
                        "It is not possible to invoke this method over the yellow initial state");
        Preconditions
                .checkArgument(
                        !this.yellowAcceptingState.equals(intersectionState),
                        "It is not possible to invoke this method over the yellow accepting state");

        return this.intersectionStateModelStateMap.get(intersectionState);

    }

    /**
     * @return the yellowInitialState
     */
    public State getYellowInitialState() {
        if (this.underApproximation) {
            throw new IllegalStateException(
                    "The under approximation does not contain any yellow initial state");
        }
        return yellowInitialState;
    }

    /**
     * @return the greenState
     */
    public State getGreenState() {
        return greenState;
    }

    /**
     * @return the yellowAcceptingState
     */
    public State getYellowAcceptingState() {
        if (this.underApproximation) {
            throw new IllegalStateException(
                    "The under approximation does not contain any yellow accepting state");
        }
        return yellowAcceptingState;
    }

    /**
     * @return the redState
     */
    public State getRedState() {
        return redState;
    }

    /**
     * @return the mapModelStateIntersectionTransitions
     */
    public Map<Transition, State> getIntersectionTransitionsBlackBoxStatesMap() {
        return Collections
                .unmodifiableMap(mapConstrainedTransitionModelBlackBoxState);
    }
}
