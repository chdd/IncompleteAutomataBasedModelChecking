/**
 * 
 */
package it.polimi.replacementchecker.acceptingpolicy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.PluggingTransition;
import it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy;

import java.util.HashSet;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class ReplacementAcceptingPolicyTest {

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#ReplacementAcceptingPolicy(null, it.polimi.constraints.components.Replacement)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReplacementAcceptingPolicy1() {
        Replacement rep = new Replacement(new StateFactory().create(), new IBA(
                new ModelTransitionFactory()),
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(null, rep);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#ReplacementAcceptingPolicy(it.polimi.constraints.components.SubProperty, null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testReplacementAcceptingPolicy2() {
        SubProperty sub = new SubProperty(new StateFactory().create(), new BA(
                new ClaimTransitionFactory()));
        new ReplacementAcceptingPolicy(sub, null);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#ReplacementAcceptingPolicy(it.polimi.constraints.components.SubProperty, it.polimi.constraints.components.Replacement)}
     * .
     */
    @Test
    public void testReplacementAcceptingPolicy3() {
        SubProperty sub = new SubProperty(new StateFactory().create(), new BA(
                new ClaimTransitionFactory()));
        Replacement rep = new Replacement(new StateFactory().create(), new IBA(
                new ModelTransitionFactory()),
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertNotNull(new ReplacementAcceptingPolicy(sub, rep));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testComuteInitNumber1() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteInitNumber(null,
                subPropertyState);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testComuteInitNumber2() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteInitNumber(
                replacementState, null);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteInitNumber3() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteInitNumber(
                new StateFactory().create(), subPropertyState);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteInitNumber4() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteInitNumber(
                replacementState, new StateFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test
    public void testComuteInitNumber5() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0,
                new ReplacementAcceptingPolicy(sub, rep).comuteInitNumber(
                        replacementState, subPropertyState));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testComuteNumber1() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteNumber(null,
                subPropertyState, 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testComuteNumber2() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteNumber(replacementState,
                null, 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber3() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteNumber(
                new StateFactory().create(), subPropertyState, 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber4() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteNumber(replacementState,
                new StateFactory().create(), 0);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber5() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteNumber(replacementState,
                subPropertyState, -1);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber6() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).comuteNumber(replacementState,
                subPropertyState, 3);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber7() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0, new ReplacementAcceptingPolicy(sub, rep).comuteNumber(
                replacementState, subPropertyState, 0));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber8() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(1, new ReplacementAcceptingPolicy(sub, rep).comuteNumber(
                replacementState, subPropertyState, 0));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber9() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(2, new ReplacementAcceptingPolicy(sub, rep).comuteNumber(
                replacementState, subPropertyState, 1));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber10() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0, new ReplacementAcceptingPolicy(sub, rep).comuteNumber(
                replacementState, subPropertyState, 2));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber11() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(1, new ReplacementAcceptingPolicy(sub, rep).comuteNumber(
                replacementState, subPropertyState, 1));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#computeNumber(boolean, boolean, int, it.polimi.automata.state.State, it.polimi.automata.state.State)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testComuteNumber12() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).computeNumber(false, false, 0,
                replacementState, null);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testComuteNumber13() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).computeNumber(false, false, 0,
                null, subPropertyState);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber14() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).computeNumber(false, false, 0,
                new StateFactory().create(), subPropertyState);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber15() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).computeNumber(false, false, 0,
                replacementState, new StateFactory().create());
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber16() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).computeNumber(false, false,
                -1, replacementState, subPropertyState);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComuteNumber17() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        new ReplacementAcceptingPolicy(sub, rep).computeNumber(false, false, 3,
                replacementState, subPropertyState);
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber18() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(2, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                true, true, 0, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber19() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(2, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                true, true, 1, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber20() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(2, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                true, false, 1, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber21() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(2, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                false, true, 1, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber22() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(1, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                true, false, 0, replacementState, subPropertyState));
    }
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber23() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(1, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                false, false, 0, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber24() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(2, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                true, false, 0, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber25() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                false, false, 0, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber26() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(1, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                false, false, 1, replacementState, subPropertyState));
    }

    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber27() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                false, false, 2, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber28() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                true, true, 2, replacementState, subPropertyState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber29() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                false, false, 2, replacementState, subPropertyState));
    }
    
    
    /**
     * Test method for
     * {@link it.polimi.replacementchecker.acceptingpolicy.ReplacementAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
     * .
     */
    @Test
    public void testComuteNumber30() {
        BA subBA = new BA(new ClaimTransitionFactory());
        State subPropertyState = new StateFactory().create();
        subBA.addAcceptState(subPropertyState);
        SubProperty sub = new SubProperty(new StateFactory().create(), subBA);

        State replacementState = new StateFactory().create();

        IBA iba = new IBA(new ModelTransitionFactory());
        iba.addAcceptState(replacementState);
        Replacement rep = new Replacement(new StateFactory().create(), iba,
                new HashSet<PluggingTransition>(),
                new HashSet<PluggingTransition>());
        assertEquals(0, new ReplacementAcceptingPolicy(sub, rep).computeNumber(
                true, true, 2, replacementState, subPropertyState));
    }
}
