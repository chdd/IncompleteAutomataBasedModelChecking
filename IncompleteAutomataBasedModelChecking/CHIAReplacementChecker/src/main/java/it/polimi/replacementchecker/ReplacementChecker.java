package it.polimi.replacementchecker;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.replacementchecker.intersectionbuilder.OverApproximationBuilder;
import it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * Checking whether a replacement satisfies a constraint can be reduced to two
 * emptiness checking problems. The first emptiness checking procedure considers
 * an automaton which encodes the set of behaviors the system is going to
 * exhibit at run-time (an under approximation), and checks whether the property
 * is violated, the second analyzes an automaton which also contains the
 * behaviors the system may exhibit (an over approximation).
 * 
 * <p>
 * Is used to check the replacement of a black box state i.e., check whether the
 * original property is satisfied, possibly satisfied or not satisfied given a
 * specific replacement. It uses the {@link UnderApproximationBuilder} and the
 * {@link OverApproximationBuilder} to build the lower and the upper
 * intersection automaton.
 * </p>
 * 
 * <p>
 * It takes as input the constraint and a replacement for one of the black box
 * states involved in the constraint. The method check returns the
 * {@link SatisfactionValue} SATISFIED if the property is satisfied given the
 * current replacement, the {@link SatisfactionValue} POSSIBLYSATISFIED if the
 * property is possibly satisfied or the {@link SatisfactionValue} NOTSATISFIED
 * if the property is not satisfied. When a POSSIBLYSATISFIED value is generated
 * the satisfaction of the property may depends on the refinement of other black
 * box states involved in the constraint or on the refinement of the black box
 * states of the model specified into the replacement itself.
 * </p>
 * 
 * 
 * @author claudiomenghi
 */
public class ReplacementChecker extends CHIAAction<SatisfactionValue> {

    /**
     * the name of the action
     */
    private static final String NAME = "REPLACEMENT CHECKER";
    /**
     * contains the replacement to be verified
     */
    private final Replacement replacement;

    /**
     * the sub-property to be considered
     */
    private final SubProperty subproperty;

    private UnderApproximationBuilder underApproximationBuilder;

    private final AcceptingPolicy acceptingPolicy;

    private IntersectionBA upperIntersectionBA;

    private IntersectionBA underApproximationIntersectionBA;

    private boolean isTriviallySatisfied;

    private List<Entry<State, Transition>> couterexample;

    /**
     * contains if the replacement satisfies, possibly satisfies or does not
     * satisfy the property of interest
     */
    private SatisfactionValue satisfaction;

    /**
     * creates a new Refinement Checker. The refinement checker is used to check
     * the refinement of a black box state. The refinement checker updates the
     * constraint associated with the black box state and the constraints
     * associated with the other black box states.
     * 
     * @param constraint
     *            is the constraint that must be considered by the
     *            RefinementChecker
     * @param component
     *            is the replacement to be considered by the refinement checker
     * @param acceptingPolicy
     *            is the policy to be used in computing the accepting states
     * @throws NullPointerException
     *             if one of the parameters is null
     * @throws IllegalArgumentException
     *             if the sub-property and the replacement refer to different
     *             black box states
     */
    public ReplacementChecker(Replacement replacement, SubProperty subProperty,
            AcceptingPolicy acceptingPolicy) {
        super(NAME);
        Preconditions.checkNotNull(subProperty,
                "The constraint to be checked cannot be null");
        Preconditions.checkNotNull(replacement,
                "The constraint to be checked cannot be null");
        Preconditions.checkNotNull(acceptingPolicy,
                "The acceptingPolicy cannot be null");
        this.acceptingPolicy = acceptingPolicy;
        Preconditions
                .checkArgument(
                        subProperty.getModelState().equals(
                                replacement.getModelState()),
                        "The sub-property and the replacement must refer to the same model state\\"
                                + "The sub-property refers to the model state"
                                + subProperty.getModelState()
                                + "\\"
                                + "while the replacement refers to the model state"
                                + replacement.getModelState());
        this.replacement = replacement;
        this.subproperty = subProperty;
        this.setTriviallySatisfied(true);
    }

    /**
     * returns the updated constraint
     * 
     * @return the updated constraint
     */
    @Override
    public SatisfactionValue perform() {

        if (this.checkNotSatisfied()) {
            this.performed();
            satisfaction = SatisfactionValue.NOTSATISFIED;
            return SatisfactionValue.NOTSATISFIED;
        }
        if (this.checkPossiblySatisfied()) {
            this.performed();
            satisfaction = SatisfactionValue.POSSIBLYSATISFIED;
            return SatisfactionValue.POSSIBLYSATISFIED;
        }

        this.performed();
        satisfaction = SatisfactionValue.SATISFIED;
        return SatisfactionValue.SATISFIED;
    }

    private boolean checkNotSatisfied() {

        underApproximationBuilder = new UnderApproximationBuilder(replacement,
                subproperty, acceptingPolicy);

        this.underApproximationIntersectionBA = underApproximationBuilder
                .perform();
        EmptinessChecker emptinessChecker = new EmptinessChecker(
                this.underApproximationIntersectionBA);

        if (!emptinessChecker.isEmpty()) {
            this.couterexample = emptinessChecker.getCounterExample();

            return true;
        }
        return false;

    }

    private boolean checkPossiblySatisfied() {

        if (!this.subproperty.isIndispensable()) {
            this.upperIntersectionBA = new IntersectionBA();
            this.setTriviallySatisfied(true);
            return true;

        }
        this.setTriviallySatisfied(false);
        OverApproximationBuilder overApproximationBuilder = new OverApproximationBuilder(
                replacement, subproperty, acceptingPolicy);

        this.upperIntersectionBA = overApproximationBuilder.perform();

        EmptinessChecker emptinessChecker = new EmptinessChecker(
                this.upperIntersectionBA);

        if (!emptinessChecker.isEmpty()) {
            return true;
        }
        return false;

    }

    /**
     * @return the replacement considered by the replacement checker
     */
    public Replacement getReplacement() {
        return replacement;
    }

    /**
     * @return the sub-property considered by the replacement checker
     */
    public SubProperty getSubproperty() {
        return subproperty;
    }

    /**
     * returns the upper intersection automaton
     * 
     * @return the upper intersection automaton
     * @throws IllegalStateException
     *             if the replacement checking procedure has not been completed
     */
    public IntersectionBA getUpperIntersectionBA() {
        Preconditions
                .checkState(this.isPerformed(),
                        "You must check the replacement before getting the intersection ");
        Preconditions
                .checkState(
                        this.upperIntersectionBA != null,
                        "The upper intersection BA cannot be null, you cannot get the upper intersection automaton if the property is not possibly satisfied");
        return this.upperIntersectionBA;
    }

    /**
     * returns the lower intersection automaton
     * 
     * @return the lower intersection automaton
     * @throws IllegalStateException
     *             if the replacement checking procedure has not been completed
     */
    public IntersectionBA getLowerIntersectionBA() {
        Preconditions
                .checkState(this.isPerformed(),
                        "You must check the replacement before getting the intersection ");
        return this.underApproximationIntersectionBA;
    }

    /**
     * returns the sum of the lower and the upper (if computed) automata size
     * 
     * @return the sum of the lower and the upper (if computed) automata size
     * @throws IllegalStateException
     *             if the replacement checking procedure has not been completed
     */
    public int getIntersectionAutomataSize() {
        Preconditions
                .checkState(this.isPerformed(),
                        "You must check the replacement before getting the intersection ");
        int res = this.underApproximationIntersectionBA.size();
        if (this.upperIntersectionBA != null) {
            res = res + this.upperIntersectionBA.size();
        }
        return res;
    }

    /**
     * returns true if and only if the sub-property is possibly satisfied since
     * another black box state which may violate the property is present
     * 
     * @return the isTriviallySatisfied flag. returns true if a sub-property is
     *         possibly satisfied since another black box state which may
     *         violate the property is present
     */
    public boolean isTriviallyPossiblySatisfied() {
        return isTriviallySatisfied;
    }

    /**
     * @param isTriviallySatisfied
     *            the isTriviallySatisfied to set
     */
    public void setTriviallySatisfied(boolean isTriviallySatisfied) {
        this.isTriviallySatisfied = isTriviallySatisfied;
    }

    /**
     * returns the counterexample, i.e., the set of states included in the
     * violating run
     * 
     * @return the counterexample, i.e., the set of states included in the
     *         violating run
     * @throws IllegalStateException
     *             if the sub-property is not NOT satisfied
     */
    public List<Entry<State, Transition>> getCouterexample() {
        Preconditions
                .checkState(satisfaction == SatisfactionValue.NOTSATISFIED,
                        "To get the counterexample the sub-property must be not satisfied");
        return couterexample;
    }

    /**
     * returns the counterexample, it shows only the state of the model in the
     * counterexample trace
     * 
     * @return the counterexample, it shows only the state of the model in the
     *         counterexample trace
     * @throws IllegalStateException
     *             if the sub-property is not NOT satisfied
     */
    public String getFilteredCounterexample() {
        
        Preconditions
        .checkState(satisfaction == SatisfactionValue.NOTSATISFIED,
                "To get the counterexample the sub-property must be not satisfied");
        
        StringBuilder builder=new StringBuilder();

        Iterator<Entry<State, Transition>> iterator = this.getCouterexample()
                .iterator();
        while (iterator.hasNext()) {
            Entry<State, Transition> next = iterator.next();

            if (this.underApproximationBuilder.isGreenState(next.getKey())) {
                builder.append("<G->" + next.getValue().getPropositions() + ">");
            } else {
                if (this.underApproximationBuilder.isRedState(next.getKey())) {
                    builder.append("<R>");
                } else {
                    State modelState = this.underApproximationBuilder
                            .getModelState(next.getKey());
                    builder.append("<" + modelState.getId() + "->"
                            + next.getValue().getPropositions() + ">");
                }
            }
        }
        return builder.toString();
    }
}
