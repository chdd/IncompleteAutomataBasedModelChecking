/**
 * 
 */
package it.polimi.constraintcomputation.reachability;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class ReachabilityIdentifierTest {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.ReachabilityIdentifier#ReachabilityIdentifier(null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReachabilityIdentifier1() {

        new ReachabilityIdentifier(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.ReachabilityIdentifier#ReachabilityIdentifier(it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test
    public void testReachabilityIdentifier2() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier subpropertyIdentifier = new SubPropertyIdentifier(
                checker, blackState);
        assertNotNull(new ReachabilityIdentifier(subpropertyIdentifier));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.ReachabilityIdentifier#perform()}
     * .
     */
    @Test
    public void testComputeReachability() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State blackstate3 = new StateFactory().create("black", 3);
        State regular = new StateFactory().create("regular", 4);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);

        model.addBlackBoxState(blackstate2);

        model.addState(regular);
        model.addBlackBoxState(blackstate3);
        model.addAcceptState(blackstate3);

        model.addTransition(blackState, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, regular,
                new ModelTransitionFactory().create());
        model.addTransition(regular, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate3,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, blackstate3,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackstate2);
        SubProperty subProperty = identifier.perform();

        ReachabilityIdentifier labeler = new ReachabilityIdentifier(identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 2);
        assertTrue(subProperty.getIncomingTransitions().size() == 2);
        System.out.println(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap());
        System.out.println(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap());
        assertTrue(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 1);
        assertTrue(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 1);
        for (LabeledPluggingTransition outgoing : subProperty
                .getOutgoingTransitions()) {
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getLowerReachabilityRelation()
                        .get(outgoing.getSource()).size() == 1);
            }
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getUpperReachabilityRelation()
                        .get(outgoing.getSource()).size() == 1);
            }
        }
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.ReachabilityIdentifier#perform()}
     * .
     */
    @Test
    public void testComputeReachability2() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State blackstate3 = new StateFactory().create("black", 3);
        State regular = new StateFactory().create("regular", 4);
        State regular2 = new StateFactory().create("regular", 5);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);

        model.addBlackBoxState(blackstate2);

        model.addState(regular);
        model.addState(regular2);
        model.addBlackBoxState(blackstate3);
        model.addAcceptState(blackstate3);

        model.addTransition(blackState, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, regular,
                new ModelTransitionFactory().create());
        model.addTransition(regular, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(regular, regular2,
                new ModelTransitionFactory().create());
        model.addTransition(regular2, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate3,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, blackstate3,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackstate2);
        SubProperty subProperty = identifier.perform();

        ReachabilityIdentifier labeler = new ReachabilityIdentifier(identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 2);
        assertTrue(subProperty.getIncomingTransitions().size() == 3);
        System.out.println(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap());
        System.out.println(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap());
        assertTrue(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 2);
        assertTrue(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 2);
        for (LabeledPluggingTransition outgoing : subProperty
                .getOutgoingTransitions()) {
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getLowerReachabilityRelation()
                        .get(outgoing.getSource()).size() == 2);
            }
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getUpperReachabilityRelation()
                        .get(outgoing.getSource()).size() == 2);
            }
        }
    }
    
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.ReachabilityIdentifier#perform()}
     * .
     */
    @Test
    public void testComputeReachability3() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State blackstate3 = new StateFactory().create("black", 3);
        State regular = new StateFactory().create("regular", 4);
        State blacknew = new StateFactory().create("regular", 5);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);

        model.addBlackBoxState(blackstate2);

        model.addState(regular);
        model.addBlackBoxState(blacknew);
        model.addBlackBoxState(blackstate3);
        model.addAcceptState(blackstate3);

        model.addTransition(blackState, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, regular,
                new ModelTransitionFactory().create());
        model.addTransition(regular, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(regular, blacknew,
                new ModelTransitionFactory().create());
        model.addTransition(blacknew, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate3,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, blackstate3,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackstate2);
        SubProperty subProperty = identifier.perform();

        ReachabilityIdentifier labeler = new ReachabilityIdentifier(identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 2);
        assertTrue(subProperty.getIncomingTransitions().size() == 3);
        System.out.println(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap());
        System.out.println(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap());
        assertTrue(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 1);
        assertTrue(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 2);
        for (LabeledPluggingTransition outgoing : subProperty
                .getOutgoingTransitions()) {
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getLowerReachabilityRelation()
                        .get(outgoing.getSource()).size() == 1);
            }
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getUpperReachabilityRelation()
                        .get(outgoing.getSource()).size() == 2);
            }
        }
    }
    
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.ReachabilityIdentifier#perform()}
     * .
     */
    @Test
    public void testComputeReachability4() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State blackstate3 = new StateFactory().create("black", 3);
        State regular = new StateFactory().create("regular", 4);
        State regular2 = new StateFactory().create("regular", 6);
        State blacknew = new StateFactory().create("regular", 5);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);

        model.addBlackBoxState(blackstate2);

        model.addState(regular);
        model.addState(regular2);
        model.addBlackBoxState(blacknew);
        model.addBlackBoxState(blackstate3);
        model.addAcceptState(blackstate3);

        model.addTransition(blackState, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, regular,
                new ModelTransitionFactory().create());
        model.addTransition(regular, regular2,
                new ModelTransitionFactory().create());
        model.addTransition(regular2, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(regular2, blacknew,
                new ModelTransitionFactory().create());
        model.addTransition(blacknew, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate3,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, blackstate3,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackstate2);
        SubProperty subProperty = identifier.perform();

        ReachabilityIdentifier labeler = new ReachabilityIdentifier(identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 2);
        assertTrue(subProperty.getIncomingTransitions().size() == 3);
        System.out.println(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap());
        System.out.println(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap());
        assertTrue(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 1);
        assertTrue(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 2);
        for (LabeledPluggingTransition outgoing : subProperty
                .getOutgoingTransitions()) {
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getLowerReachabilityRelation()
                        .get(outgoing.getSource()).size() == 1);
            }
            if (outgoing.getDestination().equals(regular)) {
                assertTrue(subProperty.getUpperReachabilityRelation()
                        .get(outgoing.getSource()).size() == 2);
            }
        }
    }
    
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.reachability.ReachabilityIdentifier#perform()}
     * .
     */
    @Test
    public void testComputeReachability5() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State blackstate3 = new StateFactory().create("black", 3);
        State regular = new StateFactory().create("regular", 4);
        State regular2 = new StateFactory().create("regular", 6);
        State blacknew = new StateFactory().create("regular", 5);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);

        model.addBlackBoxState(blackstate2);

        model.addState(regular);
        model.addState(regular2);
        model.addBlackBoxState(blacknew);
        model.addBlackBoxState(blackstate3);
        model.addAcceptState(blackstate3);

        model.addTransition(blackState, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, regular,
                new ModelTransitionFactory().create());
        model.addTransition(regular, regular2,
                new ModelTransitionFactory().create());
        model.addTransition(regular2, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(regular2, blacknew,
                new ModelTransitionFactory().create());
        model.addTransition(blacknew, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate3,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, blackstate3,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        State claimstate2 = new StateFactory().create("claim2", 2);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate2);
        claim.addAcceptState(claimstate);

        claim.addTransition(claimstate2, claimstate2,
                new ClaimTransitionFactory().create());
        
        claim.addTransition(claimstate2, claimstate,
                new ClaimTransitionFactory().create());

        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackstate2);
        SubProperty subProperty = identifier.perform();

        ReachabilityIdentifier labeler = new ReachabilityIdentifier(identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 6);
        assertTrue(subProperty.getIncomingTransitions().size() == 9);
        System.out.println(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap());
        System.out.println(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap());
        assertTrue(subProperty.getLowerReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 5);
        assertTrue(subProperty.getUpperReachabilityRelation()
                .getReachabilityAcceptingMap().size() == 10);
    }
}
