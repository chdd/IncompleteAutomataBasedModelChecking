package it.polimi.replacementchecker.acceptingpolicy;

import com.google.common.base.Preconditions;

import it.polimi.automata.state.State;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;

/**
 * contains the accepting policy used in the replacement checking activity
 * 
 * @author Claudio Menghi
 *
 */
public class ReplacementAcceptingPolicy {

    /**
     * The sub-property under analysis
     */
    protected SubProperty subproperty;
    /**
     * The replacement under analysis
     */
    protected Replacement replacement;

    /**
     * Creates a new Replacement accepting policy
     * 
     * @param subproperty
     *            the sub-property to be considered
     * @param replacement
     *            the replacement under analysis
     */
    public ReplacementAcceptingPolicy(SubProperty subproperty,
            Replacement replacement) {
        Preconditions.checkNotNull(subproperty,
                "the sub-property cannot be null");
        Preconditions.checkNotNull(replacement,
                "the replacement cannot be null");
        this.subproperty = subproperty;
        this.replacement = replacement;

    }

    /**
     * returns the number of the next state depending on whether to reach the
     * subpropertyDestinationState and the replacementDestinationState there
     * exists an accepting state of the intersection automaton obtained from an
     * accepting state of the sub-property and to reach the
     * subpropertyDestinationState and the replacementDestinationState there
     * exists an accepting state of the intersection automaton obtained from an
     * accepting state of the replacement.
     * 
     * @param propertyAccepting
     *            is a flag that indicates whether a state of the intersection
     *            automaton obtained from an accepting state of the claim is
     *            traversed to reach the subpropertyDestinationState and the
     *            replacementDestinationState
     * @param modelAccepting
     *            is a flag that indicates whether a state of the intersection
     *            automaton obtained from an accepting state of the model is
     *            traversed to reach the subpropertyDestinationState and the
     *            replacementDestinationState
     * @param previousNumber
     *            is the number associated with the previous state
     * @param subpropertyDestinationState
     *            is the state of the sub-property that will be reached
     * @param replacementDestinationState
     *            is the state of the replacement that will be reached
     * @throws NullPointerException
     *             if one of the parameters is null
     * @throws IllegalArgumentException
     *             if the replacement state is not contained into the
     *             replacement
     * @throws IllegalArgumentException
     *             if the subProperty state is not contained into the
     *             sub-property. If the number is not included between 0 and 2
     */
    public int computeNumber(boolean modelAccepting, boolean propertyAccepting, 
            int previousNumber, State replacementDestinationState,
            State subpropertyDestinationState) {

        Preconditions.checkNotNull(replacementDestinationState,
                "The state of the replacement cannot be null");
        Preconditions.checkNotNull(subpropertyDestinationState,
                "The state of the subproperty cannot be null");
        Preconditions.checkArgument(this.replacement.getAutomaton().getStates()
                .contains(replacementDestinationState), "The state "
                + replacementDestinationState
                + " is not contained into the replacement");
        Preconditions.checkArgument(this.subproperty.getAutomaton().getStates()
                .contains(subpropertyDestinationState), "The state "
                + subpropertyDestinationState
                + " is not contained into the sub-property");
        Preconditions.checkArgument(previousNumber >= 0,
                "the prevNumber must be grater than 0");
        Preconditions.checkArgument(previousNumber <= 2,
                "the prevNumber must not be grater than 2");


        // if x is accepting and there exists an accepting state for the
        // property the model is accepted the value 2 is returned
        if (previousNumber != 2 && modelAccepting &&  propertyAccepting) {
            return 2;
        }
        

        if (previousNumber == 1 && propertyAccepting) {
            return 2;
        }
        // if x is accepting and the destination sate is accepting for the
        // property the value 2 is returned
        if (previousNumber == 1
                && this.subproperty.getAutomaton().getAcceptStates()
                        .contains(subpropertyDestinationState)) {
            return 2;
        }
        // if x ==0 and there is a model accepting state on the reachability
        // relation and the destination state is accepting for the claim
        if (previousNumber == 0
                && modelAccepting
                && this.subproperty.getAutomaton().getAcceptStates()
                        .contains(subpropertyDestinationState)) {
            return 2;
        }
        // if x== and an accepting state of the model is contained in the run
        if (previousNumber == 0 && modelAccepting) {
            return 1;
        }
        // if x==0 and the destination is an accepting state of the model
        if (previousNumber == 0
                && this.replacement.getAutomaton().getAcceptStates()
                        .contains(replacementDestinationState)) {
            return 1;
        }

        if(previousNumber==2){
            return 0;
        }
        // return 0;
        return previousNumber;

    }

    /**
     * returns the number to be associated with the initial state of the
     * automaton
     * 
     * @param replacementState
     *            the initial state of the model to be considered
     * @param subPropertyState
     *            the initial state of the claim to be considered
     * @return the number to be associated with the initial state of the
     *         intersection automaton
     * @throws NullPointerException
     *             if one of the parameters is null
     * @throws IllegalArgumentException
     *             if the replacement state is not contained into the
     *             replacement
     * @throws IllegalArgumentException
     *             if the subProperty state is not contained into the
     *             sub-property
     */
    public int comuteInitNumber(State replacementState, State subPropertyState) {
        Preconditions.checkNotNull(replacementState,
                "The state of the replacement cannot be null");
        Preconditions.checkNotNull(subPropertyState,
                "The state of the sub-property cannot be null");
        Preconditions.checkArgument(this.replacement.getAutomaton().getStates()
                .contains(replacementState), "The state " + replacementState
                + " is not contained into the replacement");
        Preconditions.checkArgument(this.subproperty.getAutomaton().getStates()
                .contains(subPropertyState), "The state " + subPropertyState
                + " is not contained into the sub-property");
        return 0;
    }

    /**
     * given a state of the replacement, a state of the sub-property and the
     * previous number computes the number to be associated with the next state
     * of the automaton
     * 
     * @param replacementState
     *            the state of the replacement to be considered
     * @param subPropertyState
     *            the state of the sub-property to be considered
     * @param prevNumber
     *            the previous number
     * @return the number to be associated to the next state of the intersection
     *         automaton
     * @throws NullPointerException
     *             if one of the parameters is null
     * @throws IllegalArgumentException
     *             if the replacement state is not contained into the
     *             replacement
     * @throws IllegalArgumentException
     *             if the subProperty state is not contained into the
     *             sub-property. If the number is not included between 0 and 2
     */
    public int comuteNumber(State replacementState, State subPropertyState,
            int prevNumber) {
        Preconditions.checkNotNull(replacementState,
                "The state of the model cannot be null");
        Preconditions.checkNotNull(subPropertyState,
                "The state of the claim cannot be null");
        Preconditions.checkArgument(this.replacement.getAutomaton().getStates()
                .contains(replacementState), "The state " + replacementState
                + " is not contained into the replacement");
        Preconditions.checkArgument(this.subproperty.getAutomaton().getStates()
                .contains(subPropertyState), "The state " + subPropertyState
                + " is not contained into the sub-property");
        Preconditions.checkArgument(prevNumber >= 0,
                "the prevNumber must be grater than 0");
        Preconditions.checkArgument(prevNumber <= 2,
                "the prevNumber must not be grater than 2");

        int num = prevNumber;

        if (prevNumber == 0
                && replacement.getAutomaton().getAcceptStates()
                        .contains(replacementState)) {
            num = 1;
        }
        if (prevNumber == 1
                && subproperty.getAutomaton().getAcceptStates()
                        .contains(subPropertyState)) {
            num = 2;
        }

        if (prevNumber == 2) {
            num = 0;
        }
        return num;
    }
}
