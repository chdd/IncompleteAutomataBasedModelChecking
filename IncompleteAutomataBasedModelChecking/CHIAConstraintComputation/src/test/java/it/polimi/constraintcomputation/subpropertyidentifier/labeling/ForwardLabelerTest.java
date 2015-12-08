/**
 * 
 */
package it.polimi.constraintcomputation.subpropertyidentifier.labeling;

import static org.junit.Assert.assertEquals;
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
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class ForwardLabelerTest {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#ForwardLabeler(null, it.polimi.constraints.transitions.Label, it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testForwardLabeler1() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        new ForwardLabeler(null, Label.Y, identifier);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#ForwardLabeler(java.util.Set, it.polimi.constraints.transitions.Label, it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testForwardLabeler2() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        new ForwardLabeler(new HashSet<State>(), null, identifier);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#ForwardLabeler(java.util.Set, it.polimi.constraints.transitions.Label, it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testForwardLabeler3() {
        new ForwardLabeler(new HashSet<State>(), Label.Y, null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#ForwardLabeler(java.util.Set, it.polimi.constraints.transitions.Label, it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test
    public void testForwardLabeler4() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        assertNotNull(new ForwardLabeler(new HashSet<State>(), Label.Y,
                identifier));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#perform(null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testPerform1() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        ForwardLabeler labeler = new ForwardLabeler(new HashSet<State>(),
                Label.Y, identifier);
        labeler.perform(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#perform(null)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPerform2() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);

        State state = new StateFactory().create();
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        ForwardLabeler labeler = new ForwardLabeler(new HashSet<State>(),
                Label.Y, identifier);
        labeler.perform(state);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#perform(null)}
     * .
     */
    @Test
    public void testPerform3() {
        IBA model = new IBA(new ModelTransitionFactory());
        State modelstate = new StateFactory().create("black1", 1);
        State blackState = new StateFactory().create("regular", 2);
        model.addBlackBoxState(blackState);
        model.addAcceptState(blackState);
        model.addInitialState(modelstate);
        model.addTransition(modelstate, blackState,
                new ModelTransitionFactory().create());
        model.addTransition(blackState, blackState,
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
                blackState);
        SubProperty subProperty = identifier.perform();
        ForwardLabeler labeler = new ForwardLabeler(new HashSet<State>(),
                Label.G, identifier);
        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }

        assertTrue(subProperty.getIncomingTransitions().size() == 1);
        assertEquals(Label.G, subProperty.getIncomingTransitions().iterator()
                .next().getLabel());

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#perform(null)}
     * .
     */
    @Test
    public void testPerform4() {
        IBA model = new IBA(new ModelTransitionFactory());
        State modelstate = new StateFactory().create("black1", 1);
        State blackState2 = new StateFactory().create("black2", 2);
        State blackState3 = new StateFactory().create("black3", 3);
        model.addBlackBoxState(blackState2);
        model.addBlackBoxState(blackState3);
        model.addAcceptState(blackState3);
        model.addInitialState(modelstate);
        model.addTransition(modelstate, blackState2,
                new ModelTransitionFactory().create());
        model.addTransition(blackState2, blackState3,
                new ModelTransitionFactory().create());
        model.addTransition(blackState3, blackState3,
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
                blackState3);
        SubProperty subProperty = identifier.perform();
        Set<State> states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getStates());
        states.removeAll(identifier.perform().getAutomaton().getStates());

        ForwardLabeler labeler = new ForwardLabeler(states, Label.Y, identifier);

        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }

        assertTrue(subProperty.getIncomingTransitions().size() == 1);
        assertEquals(Label.Y, subProperty.getIncomingTransitions().iterator()
                .next().getLabel());

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#perform(null)}
     * .
     */
    @Test
    public void testPerform5() {
        IBA model = new IBA(new ModelTransitionFactory());
        State modelstate = new StateFactory().create("regular1", 1);
        State blackState2 = new StateFactory().create("black2", 2);
        State blackState3 = new StateFactory().create("black3", 3);
        model.addBlackBoxState(blackState2);
        model.addBlackBoxState(blackState3);
        model.addAcceptState(blackState3);
        model.addInitialState(modelstate);
        model.addTransition(modelstate, blackState2,
                new ModelTransitionFactory().create());
        model.addTransition(blackState2, blackState3,
                new ModelTransitionFactory().create());
        model.addTransition(blackState3, blackState3,
                new ModelTransitionFactory().create());
        model.addTransition(modelstate, blackState3,
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
                blackState3);
        SubProperty subProperty = identifier.perform();
        Set<State> states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getPurelyRegularStates());

        ForwardLabeler labeler = new ForwardLabeler(states, Label.G, identifier);

        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }

        states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getStates());
        states.removeAll(identifier.perform().getAutomaton().getStates());

        labeler = new ForwardLabeler(states, Label.Y, identifier);

        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }
        assertTrue(subProperty.getIncomingTransitions().size() == 2);

        for (LabeledPluggingTransition trans : subProperty
                .getIncomingTransitions()) {
            if (trans.getSource().equals(modelstate)) {
                assertEquals(Label.G, trans.getLabel());
            }
            if (trans.getSource().equals(blackState2)) {
                assertEquals(Label.Y, trans.getLabel());
            }
        }

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#perform(null)}
     * .
     */
    @Test
    public void testPerform6() {
        IBA model = new IBA(new ModelTransitionFactory());
        State modelstate = new StateFactory().create("regular1", 1);
        State blackState2 = new StateFactory().create("black2", 2);
        State blackState3 = new StateFactory().create("black3", 3);
        model.addBlackBoxState(blackState2);
        model.addBlackBoxState(blackState3);
        model.addAcceptState(blackState3);
        model.addInitialState(modelstate);
        model.addTransition(modelstate, blackState2,
                new ModelTransitionFactory().create());
        model.addTransition(blackState2, blackState3,
                new ModelTransitionFactory().create());
        model.addTransition(blackState3, blackState3,
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
                blackState3);
        SubProperty subProperty = identifier.perform();
        Set<State> states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getPurelyRegularStates());

        ForwardLabeler labeler = new ForwardLabeler(states, Label.G, identifier);

        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }

        states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getStates());
        states.removeAll(identifier.perform().getAutomaton().getStates());

        labeler = new ForwardLabeler(states, Label.Y, identifier);

        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }
        assertTrue(subProperty.getIncomingTransitions().size() == 1);

        for (LabeledPluggingTransition trans : subProperty
                .getIncomingTransitions()) {
            assertEquals(Label.Y, trans.getLabel());

        }

    }
    
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.ForwardLabeler#perform(null)}
     * .
     */
    @Test
    public void testPerform7() {
        IBA model = new IBA(new ModelTransitionFactory());
        State modelstate = new StateFactory().create("regular1", 1);
        State blackState2 = new StateFactory().create("black2", 2);
        State blackState3 = new StateFactory().create("black3", 3);
        State blackState4 = new StateFactory().create("black4", 4);
        model.addBlackBoxState(blackState2);
        model.addBlackBoxState(blackState3);
        model.addAcceptState(blackState3);
        model.addBlackBoxState(blackState4);
        model.addInitialState(modelstate);
        model.addTransition(modelstate, blackState2,
                new ModelTransitionFactory().create());
        model.addTransition(blackState2, blackState3,
                new ModelTransitionFactory().create());
        model.addTransition(blackState3, blackState3,
                new ModelTransitionFactory().create());
        
        model.addTransition(modelstate, blackState4,
                new ModelTransitionFactory().create());
        model.addTransition(blackState4, blackState3,
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
                blackState3);
        SubProperty subProperty = identifier.perform();
        Set<State> states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getPurelyRegularStates());

        ForwardLabeler labeler = new ForwardLabeler(states, Label.G, identifier);

        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }

        states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getStates());
        states.removeAll(identifier.perform().getAutomaton().getStates());

        labeler = new ForwardLabeler(states, Label.Y, identifier);

        for (State initialState : identifier.getChecker()
                .getUpperIntersectionBuilder().getIntersectionAutomaton()
                .getInitialStates()) {
            labeler.perform(initialState);
        }
        assertTrue(subProperty.getIncomingTransitions().size() == 2);

        for (LabeledPluggingTransition trans : subProperty
                .getIncomingTransitions()) {
            assertEquals(Label.Y, trans.getLabel());

        }

    }
}
