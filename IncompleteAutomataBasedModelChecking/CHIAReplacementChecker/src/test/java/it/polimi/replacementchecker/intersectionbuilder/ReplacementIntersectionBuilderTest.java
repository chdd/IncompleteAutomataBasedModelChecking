/**
 * 
 */
package it.polimi.replacementchecker.intersectionbuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author Claudio Menghi
 *
 */
public class ReplacementIntersectionBuilderTest {

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#ReplacementIntersectionBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, boolean)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReplacementIntersectionBuilder1() {

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementIntersectionBuilder(replacement, null, true);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#ReplacementIntersectionBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, boolean)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReplacementIntersectionBuilder2() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        new ReplacementIntersectionBuilder(null, sub, true);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#ReplacementIntersectionBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, boolean)}
     * .
     */
    @Test
    public void testReplacementIntersectionBuilder3() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertNotNull(new ReplacementIntersectionBuilder(replacement, sub, true));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#ReplacementIntersectionBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, boolean)}
     * .
     */
    @Test
    public void testReplacementIntersectionBuilder4() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertNotNull(new ReplacementIntersectionBuilder(replacement, sub,
                false));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformPropositions() {
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        IGraphProposition b = new GraphProposition("b", false);
        propositions.add(a);
        propositions.add(b);
        propositions.add(new SigmaProposition());
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        IntersectionBA intersection = new ReplacementIntersectionBuilder(
                replacement, sub, false).perform();

        assertEquals(
                "The set of the propositions of the intersection automaton is the union of the propositions of the automaton of the replacement and the sub-property",
                propositions, intersection.getPropositions());

    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformGreenState() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), true, Label.G));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), true));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA intersection = builder.perform();

        State greenState = builder.getIntersectionAutomaton()
                .getInitialStates().iterator().next();
        System.out.println(greenState);
        assertTrue(builder.isGreenState(greenState));
        assertEquals(
                intersection.getTransitionDestination(intersection
                        .getOutTransitions(greenState).iterator().next()),
                builder.getIntersectionStates(replacementState,
                        subPropertyState).iterator().next());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformGreenState2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), true, Label.G));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions2), true));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA intersection = builder.perform();

        State greenState = builder.getIntersectionAutomaton()
                .getInitialStates().iterator().next();
        System.out.println(greenState);
        assertTrue(builder.isGreenState(greenState));
        assertTrue(intersection.getSuccessors(greenState).size() == 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformGreenState3() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);

        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), true, Label.G));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addBlackBoxState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), true));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA intersection = builder.perform();

        State greenState = builder.getIntersectionAutomaton()
                .getInitialStates().iterator().next();
        System.out.println(greenState);
        assertTrue(builder.isGreenState(greenState));
        assertTrue(intersection.getSuccessors(greenState).size() == 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformYellowInitialState() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), true, Label.Y));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), true));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State greenState = builder.getIntersectionAutomaton()
                .getInitialStates().iterator().next();
        System.out.println(greenState);
        State yellowInitialState = builder.getYellowInitialState();
        State greenInitialState = builder.getGreenState();
        assertTrue(intersection.getOutTransitions(greenInitialState).size() == 0);
        assertTrue(intersection.getOutTransitions(yellowInitialState).size() > 0);
        assertEquals(
                intersection.getTransitionDestination(intersection
                        .getOutTransitions(yellowInitialState).iterator()
                        .next()),
                builder.getIntersectionStates(replacementState,
                        subPropertyState).iterator().next());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformYellowInitialState2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);

        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), true, Label.Y));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions2), true));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State greenState = builder.getIntersectionAutomaton()
                .getInitialStates().iterator().next();
        System.out.println(greenState);
        State yellowInitialState = builder.getYellowInitialState();
        State greenInitialState = builder.getGreenState();
        assertTrue(intersection.getOutTransitions(greenInitialState).size() == 0);
        assertTrue(intersection.getOutTransitions(yellowInitialState).size() == 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformRedState() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State redState = builder.getRedState();

        assertTrue(intersection.getInTransitions(redState).size() > 0);
        assertTrue(intersection.getPredecessors(redState).contains(
                builder.getIntersectionStates(replacementState,
                        subPropertyState).iterator().next()));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformRedState2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions2), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State redState = builder.getRedState();

        assertTrue(intersection.getInTransitions(redState).size() > 0);
        assertTrue(intersection.getPredecessors(redState).size() == 1);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformRedState3() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
        propositions2.add(b);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addBlackBoxState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions2), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA intersection = builder.perform();

        State redState = builder.getRedState();

        assertTrue(intersection.getInTransitions(redState).size() > 0);
        assertTrue(intersection.getPredecessors(redState).size() == 1);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformYellowAcceptingState() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.Y));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State redState = builder.getRedState();
        State yellowAcceptingState = builder.getYellowAcceptingState();

        assertEquals(1, intersection.getInTransitions(redState).size());

        assertEquals(2, intersection.getInTransitions(yellowAcceptingState)
                .size());
        assertTrue(intersection.getPredecessors(yellowAcceptingState).contains(
                builder.getIntersectionStates(replacementState,
                        subPropertyState).iterator().next()));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformYellowAcceptingState2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.Y));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions2), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State redState = builder.getRedState();
        State yellowAcceptingState = builder.getYellowAcceptingState();

        assertEquals(1, intersection.getInTransitions(redState).size());

        assertEquals(1, intersection.getInTransitions(yellowAcceptingState)
                .size());
        assertTrue(intersection.getPredecessors(yellowAcceptingState).contains(
                yellowAcceptingState));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State intersectionState1 = builder
                .getIntersectionStates(replacementState, subPropertyState)
                .iterator().next();
        State intersectionState2 = builder
                .getIntersectionStates(replacementState2, subPropertyState2)
                .iterator().next();
        assertTrue(intersection.isSuccessor(intersectionState1,
                intersectionState2));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        State intersectionState1 = builder
                .getIntersectionStates(replacementState, subPropertyState)
                .iterator().next();
        State intersectionState2 = builder
                .getIntersectionStates(replacementState2, subPropertyState2)
                .iterator().next();
        assertTrue(intersection.isSuccessor(intersectionState1,
                intersectionState2));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition3() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        assertTrue(builder.getIntersectionStates(replacementState,
                subPropertyState).isEmpty());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition4() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(b);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        assertTrue(builder.getIntersectionStates(replacementState2,
                subPropertyState2).isEmpty());

        assertEquals(5, automaton.getStates().size());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition5() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        subBA.addInitialState(subPropertyState);
        subBA.addAcceptState(subPropertyState);
        subBA.addTransition(subPropertyState, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState);
        iba.addAcceptState(replacementState);

        iba.addTransition(replacementState, replacementState,
                new ModelTransitionFactory().create(propositions));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        assertEquals(7, automaton.getStates().size());
    }
    
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition6() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        State subPropertyState3 = new StateFactory().create();
        subBA.addState(subPropertyState3);
        
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState, subPropertyState3,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState3, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(b);
        iba.addState(replacementState2);
        iba.addState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        assertTrue(builder.getIntersectionStates(replacementState2,
                subPropertyState2).isEmpty());

        assertEquals(5, automaton.getStates().size());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionReachabilityRelation() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        subBA.addInitialState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        LabeledPluggingTransition incomingTransition = new LabeledPluggingTransition(
                modelState, subPropertyState,
                new ModelTransitionFactory().create(propositions), true,
                Label.G);
        sub.addIncomingTransition(incomingTransition);

        LabeledPluggingTransition outgoingTransition = new LabeledPluggingTransition(
                subPropertyState, modelState,
                new ModelTransitionFactory().create(propositions), false,
                Label.Y);
        sub.addOutgoingTransition(outgoingTransition);
        State replacementState = new StateFactory().create();

        sub.addReachabilityRelation(outgoingTransition, incomingTransition,
                false, false);

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState);


        iba.addTransition(replacementState, replacementState,
                new ModelTransitionFactory().create(propositions));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));

        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA automaton = builder.perform();

        State intersectionState = builder
                .getIntersectionStates(replacementState, subPropertyState)
                .iterator().next();
        System.out.println("---"+automaton.getSuccessors(intersectionState));
        assertTrue(automaton.getSuccessors(intersectionState).contains(
                intersectionState));
        assertTrue(new EmptinessChecker(automaton).isEmpty());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionReachabilityRelation2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        subBA.addInitialState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        LabeledPluggingTransition incomingTransition = new LabeledPluggingTransition(
                modelState, subPropertyState,
                new ModelTransitionFactory().create(propositions), true,
                Label.G);
        sub.addIncomingTransition(incomingTransition);

        LabeledPluggingTransition outgoingTransition = new LabeledPluggingTransition(
                subPropertyState, modelState,
                new ModelTransitionFactory().create(propositions), false,
                Label.Y);
        sub.addOutgoingTransition(outgoingTransition);
        State replacementState = new StateFactory().create();

        sub.addReachabilityRelation(outgoingTransition, incomingTransition,
                true, true);

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState);


        iba.addTransition(replacementState, replacementState,
                new ModelTransitionFactory().create(propositions));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));

        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA automaton = builder.perform();

        assertFalse(new EmptinessChecker(automaton).isEmpty());
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionReachabilityRelation3() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        subBA.addInitialState(subPropertyState);
        subBA.addAcceptState(subPropertyState);
        
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        LabeledPluggingTransition incomingTransition = new LabeledPluggingTransition(
                modelState, subPropertyState,
                new ModelTransitionFactory().create(propositions), true,
                Label.G);
        sub.addIncomingTransition(incomingTransition);

        LabeledPluggingTransition outgoingTransition = new LabeledPluggingTransition(
                subPropertyState, modelState,
                new ModelTransitionFactory().create(propositions), false,
                Label.Y);
        sub.addOutgoingTransition(outgoingTransition);
        State replacementState = new StateFactory().create();

        sub.addReachabilityRelation(outgoingTransition, incomingTransition,
                true, false);

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState);


        iba.addTransition(replacementState, replacementState,
                new ModelTransitionFactory().create(propositions));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));

        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA automaton = builder.perform();

        assertFalse(new EmptinessChecker(automaton).isEmpty());
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionReachabilityRelation4() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        
        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);

        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        subBA.addInitialState(subPropertyState);
        subBA.addAcceptState(subPropertyState);
        
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        LabeledPluggingTransition incomingTransition = new LabeledPluggingTransition(
                modelState, subPropertyState,
                new ModelTransitionFactory().create(propositions), true,
                Label.G);
        sub.addIncomingTransition(incomingTransition);

        LabeledPluggingTransition outgoingTransition = new LabeledPluggingTransition(
                subPropertyState, modelState,
                new ModelTransitionFactory().create(propositions2), false,
                Label.Y);
        sub.addOutgoingTransition(outgoingTransition);
        State replacementState = new StateFactory().create();

        sub.addReachabilityRelation(outgoingTransition, incomingTransition,
                true, false);

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState);


        iba.addTransition(replacementState, replacementState,
                new ModelTransitionFactory().create(propositions));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));

        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA automaton = builder.perform();

        assertTrue(new EmptinessChecker(automaton).isEmpty());
    }
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition_Internal_Accepting_State() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState2, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions));
        iba.addTransition(replacementState2, replacementState,
                new ModelTransitionFactory().create(propositions));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        Set<State> acceptingStates = new HashSet<State>(
                automaton.getAcceptStates());
        acceptingStates.remove(builder.getRedState());
        acceptingStates.remove(builder.getYellowAcceptingState());
        assertTrue(acceptingStates.size() > 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeIntersectionTransition_Internal_Initial_State() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState2, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions));
        iba.addTransition(replacementState2, replacementState,
                new ModelTransitionFactory().create(propositions));
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        Set<State> initialStates = new HashSet<State>(
                automaton.getInitialStates());
        initialStates.remove(builder.getGreenState());
        initialStates.remove(builder.getYellowInitialState());
        assertTrue(initialStates.size() > 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformComputeBlackBox() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addBlackBoxState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        assertTrue(builder.getIntersectionTransitionsBlackBoxStatesMap()
                .keySet().size() == 1);
        assertTrue(intersection.getConstrainedTransitions().size() == 1);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerformAutomatonSelfLoop() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        subBA.addTransition(subPropertyState2, subPropertyState,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addBlackBoxState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        iba.addTransition(replacementState2, replacementState,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        assertTrue(builder.getIntersectionTransitionsBlackBoxStatesMap()
                .keySet().size() == 2);
        assertTrue(intersection.getConstrainedTransitions().size() == 2);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#perform()}
     * .
     */
    @Test
    public void testPerform() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        subBA.addTransition(subPropertyState2, subPropertyState,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addBlackBoxState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        iba.addTransition(replacementState2, replacementState,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA intersection = builder.perform();

        assertTrue(intersection == builder.perform());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#updateIntersection(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Ignore
    @Test
    public void testUpdateIntersection() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getIntersectionTransitionsBlackBoxStatesMap()}
     * .
     */
    @Ignore
    @Test
    public void testGetIntersectionTransitionsBlackBoxStatesMap() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getIntersectionAutomaton()}
     * .
     */
    @Test
    public void testGetIntersectionAutomaton() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        assertTrue(automaton == builder.getIntersectionAutomaton());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getIntersectionStates(it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test
    public void testGetIntersectionStates() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        State greenState = builder.getGreenState();
        assertTrue(builder.isGreenState(greenState));
        assertFalse(builder.isGreenState(automaton.getSuccessors(greenState)
                .iterator().next()));
        assertTrue(builder.getIntersectionStates(replacementState2,
                subPropertyState).isEmpty());
        assertTrue(builder.getIntersectionStates(replacementState,
                subPropertyState2).isEmpty());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#isGreenState(it.polimi.automata.state.State)}
     * .
     */
    @Test
    public void testIsGreenState() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        iba.addProposition(a);
        iba.addState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        State greenState = builder.getGreenState();
        assertTrue(builder.isGreenState(greenState));
        assertFalse(builder.isGreenState(automaton.getSuccessors(greenState)
                .iterator().next()));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#isRedState(it.polimi.automata.state.State)}
     * .
     */
    @Test
    public void testIsRedState() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        State redState = builder.getRedState();
        assertTrue(builder.isRedState(redState));
        Set<State> prev = new HashSet<State>(
                automaton.getPredecessors(redState));
        prev.remove(redState);
        assertFalse(builder.isRedState(prev.iterator().next()));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getModelState(it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testGetModelState_NullPointer() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        builder.getRedState();

        builder.getModelState(null);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getModelState(it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetModelState_IllegalArgument() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        builder.getRedState();

        builder.getModelState(new StateFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getModelState(it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetModelState_IllegalArgument_GreenInitial() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        builder.getRedState();

        builder.getModelState(builder.getGreenState());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getModelState(it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetModelState_IllegalArgument_YellowInitial() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        builder.getRedState();

        builder.getModelState(builder.getYellowInitialState());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getModelState(it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetModelState_IllegalArgument_RedAccepting() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        builder.getRedState();

        builder.getModelState(builder.getRedState());
    }

    /**
     * @return the yellowInitialState
     */

    public void testGetYellowInitialState() {

        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.Y));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        State redState = builder.getRedState();
        assertTrue(builder.isRedState(redState));
        Set<State> prev = new HashSet<State>(
                automaton.getPredecessors(redState));
        prev.remove(redState);

        assertNotNull(builder.getYellowInitialState());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder#getModelState(it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetModelState_IllegalArgument_YellowAccepting() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        builder.getRedState();

        builder.getModelState(builder.getYellowAcceptingState());
    }

    /**
     * @return the yellowInitialState
     */
    @Test(expected = IllegalStateException.class)
    public void testGetYellowInitialStateException() {

        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.Y));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA automaton = builder.perform();

        State redState = builder.getRedState();
        assertTrue(builder.isRedState(redState));
        Set<State> prev = new HashSet<State>(
                automaton.getPredecessors(redState));
        prev.remove(redState);

        builder.getYellowInitialState();
    }

    /**
     * @return the yellowInitialState
     */
    @Test
    public void testGetYellowAcceptingState() {

        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.Y));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        State redState = builder.getRedState();
        assertTrue(builder.isRedState(redState));
        Set<State> prev = new HashSet<State>(
                automaton.getPredecessors(redState));
        prev.remove(redState);

        assertNotNull(builder.getYellowAcceptingState());
    }

    /**
     * @return the yellowInitialState
     */
    @Test(expected = IllegalStateException.class)
    public void testGetYellowAcceptingStateException() {

        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.Y));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, true);
        IntersectionBA automaton = builder.perform();

        State redState = builder.getRedState();
        assertTrue(builder.isRedState(redState));
        Set<State> prev = new HashSet<State>(
                automaton.getPredecessors(redState));
        prev.remove(redState);

        builder.getYellowAcceptingState();
    }

    /**
     * @return the yellowInitialState
     */
    @Test
    public void testGetModelState() {

        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addInitialState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addInitialState(subPropertyState2);
        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create());
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.Y));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addInitialState(replacementState);
        iba.addProposition(a);
        iba.addInitialState(replacementState2);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create());
        Replacement replacement = new Replacement(new StateFactory().create(),
                iba, new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        IntersectionBA automaton = builder.perform();

        State redState = builder.getRedState();
        assertTrue(builder.isRedState(redState));

        Set<State> states = new HashSet<State>(automaton.getStates());
        states.remove(builder.getGreenState());
        states.remove(builder.getYellowInitialState());
        states.remove(builder.getYellowAcceptingState());
        states.remove(builder.getRedState());

        System.out.println(states);
        assertNotNull(builder.getModelState(states.iterator().next()));
    }
}
