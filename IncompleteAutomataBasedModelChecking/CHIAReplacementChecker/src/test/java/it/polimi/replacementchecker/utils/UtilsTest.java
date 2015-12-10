/**
 * 
 */
package it.polimi.replacementchecker.utils;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio Menghi
 *
 */
public class UtilsTest {

    @Test
    public void testConstructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException {

        final Class<?> cls = Utils.class;
        final Constructor<?> c = cls.getDeclaredConstructors()[0];
        c.setAccessible(true);

        Throwable targetException = null;
        try {
            c.newInstance((Object[]) null);
        } catch (InvocationTargetException ite) {
            targetException = ite.getTargetException();
        }

        assertNotNull(targetException);
        assertEquals(targetException.getClass(), InstantiationException.class);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isOutgoingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * 
     */
    @Test(expected = NullPointerException.class)
    public void testIsOutgoingEqual1() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        PluggingTransition replacementTransition = new PluggingTransition(
                replacementState, modelState,
                new ModelTransitionFactory().create(), true);
        Utils.isOutgoingEqual(replacementTransition, null);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isOutgoingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testIsOutgoingEqua2() {

        State modelState = new StateFactory().create();
        State subPropertyState = new StateFactory().create();

        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                subPropertyState, modelState,
                new ModelTransitionFactory().create(), true, Label.Y);
        Utils.isOutgoingEqual(null, subPropertyTransition);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isOutgoingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test
    public void testIsOutgoingEqual3() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        PluggingTransition replacementTransition = new PluggingTransition(
                replacementState, modelState,
                new ModelTransitionFactory().create(), true);
        State subPropertyState = new StateFactory().create();

        State modelState2 = new StateFactory().create();
        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                subPropertyState, modelState2,
                new ModelTransitionFactory().create(), true, Label.Y);
        assertFalse(Utils.isOutgoingEqual(replacementTransition,
                subPropertyTransition));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isOutgoingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test
    public void testIsOutgoingEqual4() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        Set<IGraphProposition> replacementPropositions = new HashSet<IGraphProposition>();
        replacementPropositions.add(new GraphProposition("a", false));
        PluggingTransition replacementTransition = new PluggingTransition(
                replacementState, modelState,
                new ModelTransitionFactory().create(replacementPropositions),
                true);
        State subPropertyState = new StateFactory().create();

        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                subPropertyState, modelState,
                new ModelTransitionFactory().create(), true, Label.Y);
        assertFalse(Utils.isOutgoingEqual(replacementTransition,
                subPropertyTransition));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isOutgoingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test
    public void testIsOutgoingEqual5() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        PluggingTransition replacementTransition = new PluggingTransition(
                replacementState, modelState,
                new ModelTransitionFactory().create(), true);
        State subPropertyState = new StateFactory().create();

        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                subPropertyState, modelState,
                new ModelTransitionFactory().create(), true, Label.Y);
        assertTrue(Utils.isOutgoingEqual(replacementTransition,
                subPropertyTransition));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isIncomingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testIsIncomingEqual1() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        PluggingTransition replacementTransition = new PluggingTransition(
                modelState, replacementState,
                new ModelTransitionFactory().create(), true);
        Utils.isIncomingEqual(replacementTransition, null);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isIncomingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testIsIncomingEqual2() {

        State modelState = new StateFactory().create();
        State subPropertyState = new StateFactory().create();

        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                modelState, subPropertyState,
                new ModelTransitionFactory().create(), true, Label.Y);
        Utils.isIncomingEqual(null, subPropertyTransition);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isIncomingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test
    public void testIsIncomingEqual3() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        PluggingTransition replacementTransition = new PluggingTransition(
                modelState, replacementState,
                new ModelTransitionFactory().create(), true);
        State subPropertyState = new StateFactory().create();

        State modelState2 = new StateFactory().create();
        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                modelState2, subPropertyState,
                new ModelTransitionFactory().create(), true, Label.Y);
        assertFalse(Utils.isIncomingEqual(replacementTransition,
                subPropertyTransition));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isIncomingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test
    public void testIsIncomingEqual4() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        Set<IGraphProposition> replacementPropositions = new HashSet<IGraphProposition>();
        replacementPropositions.add(new GraphProposition("a", false));
        PluggingTransition replacementTransition = new PluggingTransition(
                modelState, replacementState,
                new ModelTransitionFactory().create(replacementPropositions),
                true);
        State subPropertyState = new StateFactory().create();

        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                modelState, subPropertyState,
                new ModelTransitionFactory().create(), true, Label.Y);
        assertFalse(Utils.isIncomingEqual(replacementTransition,
                subPropertyTransition));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.utils.Utils#isIncomingEqual(it.polimi.constraints.transitions.PluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition)}
     * .
     */
    @Test
    public void testIsIncomingEqual5() {
        State modelState = new StateFactory().create();
        State replacementState = new StateFactory().create();

        PluggingTransition replacementTransition = new PluggingTransition(
                modelState, replacementState,
                new ModelTransitionFactory().create(), true);
        State subPropertyState = new StateFactory().create();

        LabeledPluggingTransition subPropertyTransition = new LabeledPluggingTransition(
                modelState, subPropertyState,
                new ModelTransitionFactory().create(), true, Label.Y);
        assertTrue(Utils.isIncomingEqual(replacementTransition,
                subPropertyTransition));
    }
}
