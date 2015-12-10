package it.polimi.replacementchecker.intersectionbuilder;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;

import com.google.common.base.Preconditions;

/**
 * is used to compute the under approximation automaton
 * 
 * @author Claudio Menghi
 *
 */
public class UnderApproximationBuilder {


    /**
     * is the intersection builder used to compute the intersection automaton
     */
    private final ReplacementIntersectionBuilder replacementIntersectionBuilder;

    /**
     * creates a new {@link UnderApproximationBuilder}. The over-approximation
     * builder computes an over approximation of the intersection automaton
     * between the replacement and the sub-property
     * 
     * @param replacement
     *            the replacement to be considered
     * @param subproperty
     *            the sub-property under analysis
     * @param acceptingPolicy
     *            the accepting policy to be used in the computation of the
     *            intersection
     * @throws NullPointerException
     *             if one of the parameters is null
     */
    public UnderApproximationBuilder(Replacement replacement,
            SubProperty subproperty, AcceptingPolicy acceptingPolicy) {
        Preconditions.checkNotNull(replacement,
                "The replacement to be considered cannot be null");
        Preconditions.checkNotNull(subproperty,
                "The sub=property to be considered cannot be null");
        Preconditions.checkNotNull(acceptingPolicy,
                "The accepting policy to be considered cannot be null");

        replacementIntersectionBuilder = new ReplacementIntersectionBuilder(
                replacement, subproperty, true);
    }

    /**
     * returns the intersection automaton
     * 
     * @return the intersection automaton
     */
    public IntersectionBA perform() {
        return replacementIntersectionBuilder.perform();
    }

    /**
     * returns true if the state of the intersection is the green state
     * 
     * @param intersectionState
     *            is the intersection state to be considered
     * @return true if the state is the green state
     */
    public boolean isGreenState(State intersectionState) {
        return this.replacementIntersectionBuilder
                .isGreenState(intersectionState);
    }

    /**
     * returns true if the state of the intersection is the red state
     * 
     * @param intersectionState
     *            is the intersection state to be considered
     * @return true if the state is the red state
     */
    public boolean isRedState(State intersectionState) {
        return this.replacementIntersectionBuilder
                .isRedState(intersectionState);
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
        return this.replacementIntersectionBuilder
                .getModelState(intersectionState);
    }
}