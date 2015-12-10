package it.polimi.replacementchecker.intersectionbuilder;

import it.polimi.automata.IntersectionBA;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;

import com.google.common.base.Preconditions;

/**
 * is used to compute the over approximation automaton
 * 
 * @author Claudio Menghi
 *
 */
public class OverApproximationBuilder {

    /**
     * is the intersection builder used to compute the intersection automaton
     */
    private final ReplacementIntersectionBuilder replacementIntersectionBuilder;

    /**
     * creates a new {@link OverApproximationBuilder}. The over-approximation
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
    public OverApproximationBuilder(Replacement replacement,
            SubProperty subproperty, AcceptingPolicy acceptingPolicy) {
        Preconditions.checkNotNull(replacement,
                "The replacement to be considered cannot be null");
        Preconditions.checkNotNull(subproperty,
                "The sub=property to be considered cannot be null");
        Preconditions.checkNotNull(acceptingPolicy,
                "The accepting policy to be considered cannot be null");

        replacementIntersectionBuilder = new ReplacementIntersectionBuilder(
                replacement, subproperty, false);
    }

    /**
     * returns the intersection automaton
     * 
     * @return the intersection automaton
     */
    public IntersectionBA perform() {
        return replacementIntersectionBuilder.perform();
    }
}
