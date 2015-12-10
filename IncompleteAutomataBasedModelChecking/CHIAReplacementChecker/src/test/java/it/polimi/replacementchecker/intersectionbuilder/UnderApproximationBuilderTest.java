/**
 * 
 */
package it.polimi.replacementchecker.intersectionbuilder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
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

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class UnderApproximationBuilderTest {

    
    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#UnderApproximationBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}.
     */
    @Test(expected = NullPointerException.class)
    public void testUnderApproximationBuilder1() {
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

        new UnderApproximationBuilder(replacement, null,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
    }

    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#UnderApproximationBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}.
     */
    
    @Test(expected = NullPointerException.class)
    public void testUnderApproximationBuilder2() {
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

        new UnderApproximationBuilder(null, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
    }

    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#UnderApproximationBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}.
     */
    @Test(expected = NullPointerException.class)
    public void testUnderApproximationBuilder3() {
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

        new UnderApproximationBuilder(replacement, sub, null);
    }

    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#UnderApproximationBuilder(it.polimi.constraints.components.Replacement, it.polimi.constraints.components.SubProperty, it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy)}.
     */
    @Test
    public void testUnderApproximationBuilder4() {
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

        assertNotNull(new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA)));
    }

    
    

    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#perform()}.
     */
    @Test
    public void testPerform() {
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

        assertNotNull(new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA)).perform());
    }

    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#isGreenState(it.polimi.automata.state.State)}.
     */
    @Test
    public void testIsGreenState() {
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
        UnderApproximationBuilder builder = new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        IntersectionBA intersection=builder.perform();

        assertNotNull(new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA)).perform());
        boolean founded=false;
        for(State state: intersection.getStates()){
            if(builder.isGreenState(state)){
                founded=true;
            }
        }
        assertTrue(founded);
        
    }

    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#isRedState(it.polimi.automata.state.State)}.
     */
    @Test
    public void testIsRedState() {
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
        UnderApproximationBuilder builder = new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        IntersectionBA intersection=builder.perform();

        assertNotNull(new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA)).perform());
        boolean founded=false;
        for(State state: intersection.getStates()){
            if(builder.isRedState(state)){
                founded=true;
            }
        }
        assertTrue(founded);
        
    }

    /**
     * Test method for {@link it.polimi.replacementchecker.intersectionbuilder.UnderApproximationBuilder#getModelState(it.polimi.automata.state.State)}.
     */
    @Test
    public void testGetModelState() {
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
        UnderApproximationBuilder builder = new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA));
        IntersectionBA intersection=builder.perform();

        assertNotNull(new UnderApproximationBuilder(replacement, sub,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA,
                        replacement.getAutomaton(), subBA)).perform());
        for(State state: intersection.getStates()){
            if(!builder.isRedState(state) && !builder.isGreenState(state)){
                assertNotNull(builder.getModelState(state));
            }
        }
    }

}
