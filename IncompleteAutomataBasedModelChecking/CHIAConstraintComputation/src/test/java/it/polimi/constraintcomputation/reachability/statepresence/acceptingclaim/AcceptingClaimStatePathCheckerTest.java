/**
 * 
 */
package it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class AcceptingClaimStatePathCheckerTest {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim.AcceptingClaimStatePathChecker#AcceptingClaimStatePathChecker(null, it.polimi.checker.intersection.IntersectionBuilder)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testAcceptingClaimStatePathChecker1() {

        IBA iba = new IBA(new ModelTransitionFactory());
        BA ba = new BA(new ClaimTransitionFactory());
        IntersectionBuilder intersectionBuilder = new IntersectionBuilder(iba,
                ba, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, iba,
                        ba));
        intersectionBuilder.perform();
        assertNotNull(new AcceptingClaimStatePathChecker(null,
                intersectionBuilder));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim.AcceptingClaimStatePathChecker#AcceptingClaimStatePathChecker(java.util.Set, null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testAcceptingClaimStatePathChecker2() {

        IBA iba = new IBA(new ModelTransitionFactory());
        BA ba = new BA(new ClaimTransitionFactory());
        IntersectionBuilder intersectionBuilder = new IntersectionBuilder(iba,
                ba, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, iba,
                        ba));
        intersectionBuilder.perform();
        assertNotNull(new AcceptingClaimStatePathChecker(new HashSet<State>(),
                null));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim.AcceptingClaimStatePathChecker#AcceptingClaimStatePathChecker(java.util.Set, it.polimi.checker.intersection.IntersectionBuilder)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAcceptingClaimStatePathChecker3() {

        IBA iba = new IBA(new ModelTransitionFactory());
        BA ba = new BA(new ClaimTransitionFactory());
        IntersectionBuilder intersectionBuilder = new IntersectionBuilder(iba,
                ba, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, iba,
                        ba));
        intersectionBuilder.perform();
        Set<State> states = new HashSet<State>();
        states.add(new StateFactory().create());
        assertNotNull(new AcceptingClaimStatePathChecker(states,
                intersectionBuilder));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim.AcceptingClaimStatePathChecker#AcceptingClaimStatePathChecker(java.util.Set, it.polimi.checker.intersection.IntersectionBuilder)}
     * .
     */
    @Test
    public void testAcceptingClaimStatePathChecker() {

        IBA iba = new IBA(new ModelTransitionFactory());
        BA ba = new BA(new ClaimTransitionFactory());
        IntersectionBuilder intersectionBuilder = new IntersectionBuilder(iba,
                ba, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, iba,
                        ba));
        intersectionBuilder.perform();
        assertNotNull(new AcceptingClaimStatePathChecker(new HashSet<State>(),
                intersectionBuilder));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.statepresence.acceptingclaim.AcceptingClaimStatePathChecker#perform(it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test
    public void testPerform1() {
        IBA iba = new IBA(new ModelTransitionFactory());
        BA ba = new BA(new ClaimTransitionFactory());

        State modelState1 = new StateFactory().create("black1", 1);
        State modelState2 = new StateFactory().create("reg1", 2);
        State modelState3 = new StateFactory().create("reg1", 3);

        iba.addInitialState(modelState1);
        iba.addInitialState(modelState1);
        iba.addState(modelState2);
        iba.addAcceptState(modelState2);
        iba.addState(modelState3);

        iba.addTransition(modelState1, modelState2,
                new ModelTransitionFactory().create());
        iba.addTransition(modelState2, modelState3,
                new ModelTransitionFactory().create());
        iba.addTransition(modelState3, modelState1,
                new ModelTransitionFactory().create());

        State claimState1 = new StateFactory().create();
        State claimState2 = new StateFactory().create();
        ba.addInitialState(claimState1);
        ba.addState(claimState2);
        ba.addTransition(claimState1, claimState2,
                new ClaimTransitionFactory().create());

        ba.addTransition(claimState2, claimState2,
                new ClaimTransitionFactory().create());
        IntersectionBuilder intersectionBuilder = new IntersectionBuilder(iba,
                ba, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, iba,
                        ba));
        intersectionBuilder.perform();

        AcceptingClaimStatePathChecker pathChecker = new AcceptingClaimStatePathChecker(
                intersectionBuilder.getIntersectionAutomaton()
                        .getPurelyRegularStates(), intersectionBuilder);

        for (State stateSource : intersectionBuilder.getIntersectionAutomaton()
                .getPurelyRegularStates()) {
            for (State stateDestination : intersectionBuilder
                    .getIntersectionAutomaton().getPurelyRegularStates()) {
                assertFalse(pathChecker.perform(stateSource, stateDestination));
            }
        }
    }

    @Test
    public void testPerform2() {
        IBA iba = new IBA(new ModelTransitionFactory());
        BA ba = new BA(new ClaimTransitionFactory());

        State modelState1 = new StateFactory().create("black1", 1);
        State modelState4 = new StateFactory().create("black1", 4);
        State modelState2 = new StateFactory().create("reg1", 2);
        State modelState3 = new StateFactory().create("reg1", 3);

        iba.addInitialState(modelState1);
        iba.addBlackBoxState(modelState1);
        iba.addAcceptState(modelState4);
        iba.addBlackBoxState(modelState4);
        
        iba.addAcceptState(modelState2);
        iba.addAcceptState(modelState3);

        iba.addTransition(modelState1, modelState4,
                new ModelTransitionFactory().create());
        iba.addTransition(modelState4, modelState2,
                new ModelTransitionFactory().create());
        iba.addTransition(modelState2, modelState3,
                new ModelTransitionFactory().create());
        iba.addTransition(modelState3, modelState2,
                new ModelTransitionFactory().create());

        State claimState = new StateFactory().create();
        ba.addInitialState(claimState);
        ba.addAcceptState(claimState);
        ba.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        IntersectionBuilder intersectionBuilder = new IntersectionBuilder(iba,
                ba, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, iba,
                        ba));
        intersectionBuilder.perform();

        AcceptingClaimStatePathChecker pathChecker = new AcceptingClaimStatePathChecker(
                intersectionBuilder.getIntersectionAutomaton()
                        .getPurelyRegularStates(), intersectionBuilder);

        for (State stateSource : intersectionBuilder.getIntersectionAutomaton()
                .getPurelyRegularStates()) {
            for (State stateDestination : intersectionBuilder
                    .getIntersectionAutomaton().getPurelyRegularStates()) {
                assertTrue(pathChecker.perform(stateSource, stateDestination));
            }
        }
    }
}
