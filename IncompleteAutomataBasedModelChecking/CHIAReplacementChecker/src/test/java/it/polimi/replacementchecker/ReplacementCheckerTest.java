/**
 * 
 */
package it.polimi.replacementchecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;
import it.polimi.replacementchecker.intersectionbuilder.ReplacementIntersectionBuilder;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class ReplacementCheckerTest {

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#ReplacementChecker(it.polimi.constraints.components.SubProperty, it.polimi.constraints.components.Replacement, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReplacementChecker1() {
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
        builder.perform();

        new ReplacementChecker(replacement, null,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#ReplacementChecker(it.polimi.constraints.components.SubProperty, it.polimi.constraints.components.Replacement, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReplacementChecker2() {
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
        builder.perform();

        new ReplacementChecker(null, sub, AcceptingPolicy.getAcceptingPolicy(
                AcceptingType.BA, replacement.getAutomaton(), subBA));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#ReplacementChecker(it.polimi.constraints.components.SubProperty, it.polimi.constraints.components.Replacement, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReplacementChecker3() {
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
        builder.perform();

        new ReplacementChecker(replacement, sub, null);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#ReplacementChecker(it.polimi.constraints.components.SubProperty, it.polimi.constraints.components.Replacement, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}
     * .
     */
    @Test
    public void testReplacementChecker4() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        assertNotNull(new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA)));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getReplacement()}.
     */
    @Test
    public void testGetReplacement() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        assertTrue(replacement == rc.getReplacement());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getSubproperty()}.
     */
    @Test
    public void testGetSubproperty() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        assertTrue(sub == rc.getSubproperty());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getUpperIntersectionBA()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetUpperIntersectionBA1() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.getUpperIntersectionBA();
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getUpperIntersectionBA()}
     * .
     */
    @Test
    public void testGetUpperIntersectionBA2() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        assertNotNull(rc.getUpperIntersectionBA());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getUpperIntersectionBA()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetUpperIntersectionBA3() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("b", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("b", false);
        propositions2.add(b);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState2);
        State subPropertyState3 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState3);

        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState, subPropertyState3,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        iba.addAcceptState(replacementState2);
        iba.addAcceptState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        rc.getUpperIntersectionBA();
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getLowerIntersectionBA()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetLowerIntersectionBA1() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.getLowerIntersectionBA();
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getLowerIntersectionBA()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetLowerIntersectionBA2() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        assertNotNull(rc.getLowerIntersectionBA());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getIntersectionAutomataSize()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetIntersectionAutomataSize_IllegalState() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));

        rc.getIntersectionAutomataSize();
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getIntersectionAutomataSize()}
     * .
     */
    @Test
    public void testGetIntersectionAutomataSize() {
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
        SubProperty sub = new SubProperty(modelState, subBA);

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
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        assertEquals(rc.getLowerIntersectionBA().size()
                + rc.getUpperIntersectionBA().size(),
                rc.getIntersectionAutomataSize());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getIntersectionAutomataSize()}
     * .
     */
    @Test
    public void testGetIntersectionAutomataSize2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
        propositions2.add(b);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState2);
        State subPropertyState3 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState3);

        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState, subPropertyState3,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        iba.addAcceptState(replacementState2);
        iba.addAcceptState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        assertEquals(rc.getLowerIntersectionBA().size(),
                rc.getIntersectionAutomataSize());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#isTriviallyPossiblySatisfied()}
     * .
     */
    @Test
    public void testIsTriviallySatisfied() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
        propositions2.add(b);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState2);
        State subPropertyState3 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState3);

        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState, subPropertyState3,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.setIndispensable(false);
        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        iba.addAcceptState(replacementState2);
        iba.addAcceptState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        assertTrue(rc.isTriviallyPossiblySatisfied());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getCouterexample()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetCouterexample_IllegalStateException() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
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
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.setIndispensable(false);
        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        iba.addState(replacementState2);
        iba.addState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        rc.getCouterexample();
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getCouterexample()}
     * .
     */
    @Test
    public void testGetCouterexample() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
        propositions2.add(b);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState2);
        State subPropertyState3 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState3);

        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState, subPropertyState3,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.setIndispensable(false);
        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        iba.addAcceptState(replacementState2);
        iba.addAcceptState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        assertNotNull(rc.getCouterexample());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getCouterexample()}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testGetFilteredCounterexample_IllegalStateException() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
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
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.setIndispensable(false);
        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        iba.addState(replacementState2);
        iba.addState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        rc.getFilteredCounterexample();
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getFilteredCounterexample()}
     * .
     */
    @Test
    public void testGetFilteredCounterexample1() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
        propositions2.add(b);
        BA subBA = new BA(new ClaimTransitionFactory());
        subBA.addProposition(a);
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        State subPropertyState2 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState2);
        State subPropertyState3 = new StateFactory().create();
        subBA.addAcceptState(subPropertyState3);

        subBA.addTransition(subPropertyState, subPropertyState2,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState, subPropertyState3,
                new ModelTransitionFactory().create(propositions));
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.setIndispensable(false);
        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        State replacementState = new StateFactory().create();
        State replacementState2 = new StateFactory().create();
        State replacementState3 = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        iba.addProposition(b);
        iba.addAcceptState(replacementState2);
        iba.addAcceptState(replacementState3);

        iba.addTransition(replacementState, replacementState2,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState, replacementState3,
                new ModelTransitionFactory().create(propositions2));
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState,
                replacementState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        assertNotNull(rc.getFilteredCounterexample());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.ReplacementChecker#getFilteredCounterexample()}
     * .
     */
    @Test
    public void testGetFilteredCounterexample2() {
        State modelState = new StateFactory().create();
        Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
        IGraphProposition a = new GraphProposition("a", false);
        propositions.add(a);

        Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
        IGraphProposition b = new GraphProposition("a", false);
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
        subBA.addTransition(subPropertyState3, subPropertyState,
                new ModelTransitionFactory().create(propositions));
        SubProperty sub = new SubProperty(modelState, subBA);

        sub.setIndispensable(false);
        sub.addIncomingTransition(new LabeledPluggingTransition(modelState,
                subPropertyState, new ModelTransitionFactory()
                        .create(propositions), false, Label.G));
        sub.addOutgoingTransition(new LabeledPluggingTransition(
                subPropertyState, modelState, new ModelTransitionFactory()
                        .create(propositions), false, Label.R));
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
        iba.addTransition(replacementState3, replacementState,
                new ModelTransitionFactory().create(propositions2));
        Replacement replacement = new Replacement(modelState, iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());

        replacement.addIncomingTransition(new PluggingTransition(modelState, replacementState, 
                 new ModelTransitionFactory()
                        .create(propositions), false));
        replacement.addOutgoingTransition(new PluggingTransition(
                replacementState, modelState, new ModelTransitionFactory()
                        .create(propositions), false));
        ReplacementIntersectionBuilder builder = new ReplacementIntersectionBuilder(
                replacement, sub, false);
        builder.perform();

        ReplacementChecker rc = new ReplacementChecker(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        rc.perform();
        assertNotNull(rc.getFilteredCounterexample());
    }

}
