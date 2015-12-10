package it.polimi.replacementchecker.utils;

import it.polimi.constraints.transitions.LabeledPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import com.google.common.base.Preconditions;

public class Utils {

    private Utils() throws InstantiationException{
        throw new InstantiationException("Instances of this type are forbidden.");
    }
    /**
     * returns true if and only if the two transitions are labeled with the same
     * propositions and have the same destination state
     * 
     * @param replacementTransition
     *            is the outgoing transition of the replacement to be considered
     * @param subPropertyTransition
     *            is the outgoing transition of the sub-property to be
     *            considered
     * @return true if and only if the two transitions are labeled with the same
     *         propositions and have the destination source state
     * @throws NullPointerException
     *             if one of the transitions is null
     */
    public static boolean isOutgoingEqual(
            PluggingTransition replacementTransition,
            LabeledPluggingTransition subPropertyTransition) {

        Preconditions.checkNotNull(replacementTransition,
                "The transition of the replacement cannot be null");
        Preconditions.checkNotNull(subPropertyTransition,
                "The transition of the sub-property cannot be null");

        if (replacementTransition
                .getTransition()
                .getPropositions()
                .equals(subPropertyTransition.getTransition().getPropositions())) {
            if (replacementTransition.getDestination().equals(
                    subPropertyTransition.getDestination())) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if and only if the two transitions are labeled with the same
     * propositions and have the same source state
     * 
     * @param replacementTransition
     *            is the incoming transition of the replacement to be considered
     * @param subPropertyTransition
     *            is the incoming transition of the sub-property to be
     *            considered
     * @return true if and only if the two transitions are labeled with the same
     *         propositions and have the same source state
     * @throws NullPointerException
     *             if one of the transitions is null
     */
    public static boolean isIncomingEqual(
            PluggingTransition replacementTransition,
            LabeledPluggingTransition subPropertyTransition) {

        Preconditions.checkNotNull(replacementTransition,
                "The transition of the replacement cannot be null");
        Preconditions.checkNotNull(subPropertyTransition,
                "The transition of the sub-property cannot be null");

        if (replacementTransition
                .getTransition()
                .getPropositions()
                .equals(subPropertyTransition.getTransition().getPropositions())) {
            if (replacementTransition.getSource().equals(
                    subPropertyTransition.getSource())) {
                return true;
            }
        }
        return false;
    }
}
