/**
 * 
 */
package it.polimi.constraintcomputation.subpropertyidentifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.Checker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.components.SubProperty;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author Claudio1
 *
 */
public class SubPropertyIdentifierTest {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#SubPropertyIdentifier(it.polimi.checker.Checker, null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testSubPropertyIdentifier1() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        new SubPropertyIdentifier(checker, null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#SubPropertyIdentifier(it.polimi.checker.Checker, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testSubPropertyIdentifier2() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        new SubPropertyIdentifier(null, blackState);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#SubPropertyIdentifier(it.polimi.checker.Checker, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSubPropertyIdentifier3() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addState(regularState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        new SubPropertyIdentifier(checker, regularState);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#SubPropertyIdentifier(it.polimi.checker.Checker, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testSubPropertyIdentifier4() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addState(regularState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));

        new SubPropertyIdentifier(checker, blackState);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#SubPropertyIdentifier(it.polimi.checker.Checker, it.polimi.automata.state.State)}
     * .
     */
    @Test
    public void testSubPropertyIdentifier5() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addState(regularState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        assertNotNull(new SubPropertyIdentifier(checker, blackState));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#perform()}
     * .
     */
    @Test
    public void testPerformPropositionsOfTheSubPropertyBa() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addState(regularState);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(property.getAutomaton().getPropositions().size() == 3);
        assertTrue(property.getAutomaton().getPropositions()
                .contains(aproposition));
        assertTrue(property.getAutomaton().getPropositions()
                .contains(bproposition));
        assertTrue(property.getAutomaton().getPropositions()
                .contains(new SigmaProposition()));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#perform()}
     * .
     */
    @Test
    public void testPerformStatesOfTheSubPropertyBa() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(checker.getUpperIntersectionBuilder()
                .getModelIntersectionStates(blackState).size() > 0);
        assertEquals(checker.getUpperIntersectionBuilder()
                .getModelIntersectionStates(blackState), property
                .getAutomaton().getStates());

        Set<State> blackIntersectionStates = new HashSet<State>(checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        blackState));
        Set<State> intersectionInitialStates = checker.getUpperIntersectionBA()
                .getInitialStates();
        blackIntersectionStates.retainAll(intersectionInitialStates);
        assertTrue(blackIntersectionStates.size() > 0);
        assertEquals(blackIntersectionStates, property.getAutomaton()
                .getInitialStates());

        blackIntersectionStates = new HashSet<State>(checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        blackState));
        Set<State> intersectionAcceptingStates = checker
                .getUpperIntersectionBA().getAcceptStates();
        blackIntersectionStates.retainAll(intersectionAcceptingStates);
        assertTrue(blackIntersectionStates.size() > 0);
        assertEquals(blackIntersectionStates, property.getAutomaton()
                .getAcceptStates());

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#perform()}
     * .
     */
    @Test
    public void testPerformInternalTransitionsOfTheSubPropertyBa() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(checker.getUpperIntersectionBuilder()
                .getConstrainedTransitions(blackState).size() > 0);
        assertEquals(checker.getUpperIntersectionBuilder()
                .getConstrainedTransitions(blackState), property.getAutomaton()
                .getTransitions());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#perform()}
     * .
     */
    @Test
    public void testPerformIncomingTransitionsOfTheSubPropertyBa() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(checker.getUpperIntersectionBuilder()
                .getModelIntersectionStates(blackState).size() > 0);
        Set<State> blackIntersectionStates = new HashSet<State>(checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        blackState));
        assertTrue(property.getIncomingTransitions().size() > 0);
        for (State intersectionState : blackIntersectionStates) {
            for (Transition incomingTransition : checker
                    .getUpperIntersectionBA().getInTransitions(
                            intersectionState)) {
                if (!checker.getUpperIntersectionBA()
                        .getConstrainedTransitions()
                        .contains(incomingTransition)) {
                    assertTrue(property.getIncomingTransitions().contains(
                            identifier
                                    .getIncomingTransition(incomingTransition)));
                }
            }
        }
        assertTrue(property.getOutgoingTransitions().size() > 0);
        for (State intersectionState : blackIntersectionStates) {
            for (Transition outgoingTransition : checker
                    .getUpperIntersectionBA().getOutTransitions(
                            intersectionState)) {
                if (!checker.getUpperIntersectionBA()
                        .getConstrainedTransitions()
                        .contains(outgoingTransition)) {
                    assertTrue(property.getOutgoingTransitions().contains(
                            identifier
                                    .getOutgoingTransition(outgoingTransition)));
                }
            }
        }
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#isInTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testIsInTransition1() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        identifier.isInTransition(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#isInTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testIsInTransition2() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.isInTransition(new ModelTransitionFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#isInTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test
    public void testIsInTransition3() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        assertFalse(identifier.isInTransition(new ModelTransitionFactory()
                .create()));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#perform()}
     * .
     */
    @Test
    public void testIsInTransition4() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(checker.getUpperIntersectionBuilder()
                .getModelIntersectionStates(blackState).size() > 0);
        Set<State> blackIntersectionStates = new HashSet<State>(checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        blackState));
        assertTrue(property.getIncomingTransitions().size() > 0);
        for (State intersectionState : blackIntersectionStates) {
            for (Transition incomingTransition : checker
                    .getUpperIntersectionBA().getInTransitions(
                            intersectionState)) {
                if (!checker.getUpperIntersectionBA()
                        .getConstrainedTransitions()
                        .contains(incomingTransition)) {
                    assertTrue(identifier.isInTransition(identifier
                            .getIncomingTransition(incomingTransition)
                            .getTransition()));
                }
            }
        }

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#isOutTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testIsOutTransition1() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        identifier.isOutTransition(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#isOutTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testIsOutTransition2() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.isOutTransition(new ModelTransitionFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#isOutTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test
    public void testIsOutTransition3() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        assertFalse(identifier.isOutTransition(new ModelTransitionFactory()
                .create()));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#isOutTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test
    public void testIsOutTransition4() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(checker.getUpperIntersectionBuilder()
                .getModelIntersectionStates(blackState).size() > 0);
        Set<State> blackIntersectionStates = new HashSet<State>(checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        blackState));

        assertTrue(property.getOutgoingTransitions().size() > 0);
        for (State intersectionState : blackIntersectionStates) {
            for (Transition outgoingTransition : checker
                    .getUpperIntersectionBA().getOutTransitions(
                            intersectionState)) {
                if (!checker.getUpperIntersectionBA()
                        .getConstrainedTransitions()
                        .contains(outgoingTransition)) {
                    assertTrue(identifier.isOutTransition(identifier
                            .getOutgoingTransition(outgoingTransition)
                            .getTransition()));

                }
            }
        }
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getIncomingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testGetIncomingTransition1() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        identifier.getIncomingTransition(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getIncomingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetIncomingTransition2() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.getIncomingTransition(new ModelTransitionFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getIncomingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetIncomingTransition3() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        identifier.getIncomingTransition(new ModelTransitionFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getIncomingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test
    public void testGetIncomingTransition4() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(checker.getUpperIntersectionBuilder()
                .getModelIntersectionStates(blackState).size() > 0);
        Set<State> blackIntersectionStates = new HashSet<State>(checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        blackState));
        assertTrue(property.getIncomingTransitions().size() > 0);
        for (State intersectionState : blackIntersectionStates) {
            for (Transition incomingTransition : checker
                    .getUpperIntersectionBA().getInTransitions(
                            intersectionState)) {
                if (!checker.getUpperIntersectionBA()
                        .getConstrainedTransitions()
                        .contains(incomingTransition)) {
                    assertNotNull(identifier.getIncomingTransition(identifier
                            .getIncomingTransition(incomingTransition)
                            .getTransition()));
                }
            }
        }

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getOutgoingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void getOutgoingTransition1() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        identifier.getOutgoingTransition(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getOutgoingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetOutTransition2() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.getOutgoingTransition(new ModelTransitionFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getOutgoingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetOutTransition3() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        identifier.getOutgoingTransition(new ModelTransitionFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getOutgoingTransition(it.polimi.automata.transition.Transition)}
     * .
     */
    @Test
    public void testGetOutTransition4() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty property = identifier.perform();
        assertTrue(checker.getUpperIntersectionBuilder()
                .getModelIntersectionStates(blackState).size() > 0);
        Set<State> blackIntersectionStates = new HashSet<State>(checker
                .getUpperIntersectionBuilder().getModelIntersectionStates(
                        blackState));

        assertTrue(property.getOutgoingTransitions().size() > 0);
        for (State intersectionState : blackIntersectionStates) {
            for (Transition outgoingTransition : checker
                    .getUpperIntersectionBA().getOutTransitions(
                            intersectionState)) {
                if (!checker.getUpperIntersectionBA()
                        .getConstrainedTransitions()
                        .contains(outgoingTransition)) {
                    assertNotNull(identifier.getOutgoingTransition(identifier
                            .getOutgoingTransition(outgoingTransition)
                            .getTransition()));

                }
            }
        }
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getMapIntersectionTransitionIncomingTransitions()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetMapIntersectionTransitionIncomingPort1() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.getMapIntersectionTransitionIncomingTransitions();
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getMapIntersectionTransitionIncomingTransitions()}
     * .
     */
    @Test
    public void testGetMapIntersectionTransitionIncomingPort2() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        assertNotNull(identifier.getMapIntersectionTransitionIncomingTransitions());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getMapIntersectionTransitionOutgoingTransitions()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetMapIntersectionTransitionOutgoingPorts1() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.getMapIntersectionTransitionOutgoingTransitions();
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getMapIntersectionTransitionOutgoingTransitions()}
     * .
     */
    @Test
    public void testGetMapIntersectionTransitionOutgoingPorts2() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        identifier.perform();
        assertNotNull(identifier.getMapIntersectionTransitionOutgoingTransitions());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier#getChecker()}
     * .
     */
    @Test
    public void testGetChecker() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        assertNotNull(identifier.getChecker());
    }

}
