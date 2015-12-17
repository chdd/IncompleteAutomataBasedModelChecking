/**
 * 
 */
package it.polimi.statemachine.states;

import static org.junit.Assert.*;
import it.polimi.action.CHIAException;
import it.polimi.automata.io.out.ModelToStringTrasformer;
import it.polimi.constraints.components.RefinementGenerator;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.replacement.ReplacementToStringTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class CHIAReplacementStateTest {

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_INIT() throws CHIAException {

        assertEquals(CHIAReplacementState.REPLACEMENTLOADED,
                CHIAReplacementState.INIT.perform(ReplacementReader.class));
        assertEquals(CHIAReplacementState.CONSTRAINTLOADED,
                CHIAReplacementState.INIT.perform(ConstraintReader.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void isPerformable_INIT() {

        assertTrue(CHIAReplacementState.INIT
                .isPerformable(ReplacementReader.class));
        assertTrue(CHIAReplacementState.INIT
                .isPerformable(ConstraintReader.class));
        assertFalse(CHIAReplacementState.INIT
                .isPerformable(ReplacementChecker.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testPerform_INIT_Exception() throws CHIAException {
        CHIAReplacementState.INIT.perform(ReplacementChecker.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_REPLACEMENTLOADED() throws CHIAException {

        assertEquals(CHIAReplacementState.REPLACEMENTLOADED,
                CHIAReplacementState.REPLACEMENTLOADED
                        .perform(ReplacementReader.class));
        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.REPLACEMENTLOADED
                        .perform(ConstraintReader.class));
        assertEquals(CHIAReplacementState.REPLACEMENTLOADED,
                CHIAReplacementState.REPLACEMENTLOADED
                        .perform(RefinementGenerator.class));
        assertEquals(CHIAReplacementState.REPLACEMENTLOADED,
                CHIAReplacementState.REPLACEMENTLOADED
                        .perform(ReplacementToStringTransformer.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void isPerformable_REPLACEMENTLOADED() {

        assertTrue(CHIAReplacementState.REPLACEMENTLOADED
                .isPerformable(ReplacementReader.class));
        assertTrue(CHIAReplacementState.REPLACEMENTLOADED
                .isPerformable(ConstraintReader.class));
        assertTrue(CHIAReplacementState.REPLACEMENTLOADED
                .isPerformable(ReplacementToStringTransformer.class));
        assertTrue(CHIAReplacementState.REPLACEMENTLOADED
                .isPerformable(RefinementGenerator.class));
        assertFalse(CHIAReplacementState.REPLACEMENTLOADED
                .isPerformable(ReplacementChecker.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testPerform_REPLACEMENTLOADED_Exception() throws CHIAException {
        CHIAReplacementState.REPLACEMENTLOADED
                .perform(ReplacementChecker.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_CONSTRAINTLOADED() throws CHIAException {

        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.CONSTRAINTLOADED
                        .perform(ReplacementReader.class));
        assertEquals(CHIAReplacementState.CONSTRAINTLOADED,
                CHIAReplacementState.CONSTRAINTLOADED
                        .perform(ConstraintReader.class));
        assertEquals(CHIAReplacementState.CONSTRAINTLOADED,
                CHIAReplacementState.CONSTRAINTLOADED
                        .perform(ConstraintToStringTrasformer.class));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void isPerformable_CONSTRAINTLOADED() {

        assertTrue(CHIAReplacementState.CONSTRAINTLOADED
                .isPerformable(ReplacementReader.class));
        assertTrue(CHIAReplacementState.CONSTRAINTLOADED
                .isPerformable(ConstraintReader.class));
        assertTrue(CHIAReplacementState.CONSTRAINTLOADED
                .isPerformable(ConstraintToStringTrasformer.class));
        assertFalse(CHIAReplacementState.CONSTRAINTLOADED
                .isPerformable(ReplacementChecker.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testPerform_CONSTRAINTLOADED_Exception() throws CHIAException {
        CHIAReplacementState.CONSTRAINTLOADED.perform(ReplacementChecker.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_READY() throws CHIAException {

        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.READY.perform(ReplacementReader.class));
        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.READY.perform(ConstraintReader.class));
        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.READY
                        .perform(ReplacementToStringTransformer.class));
        assertEquals(CHIAReplacementState.CHECKED,
                CHIAReplacementState.READY.perform(ReplacementChecker.class));
        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.READY
                        .perform(ConstraintToStringTrasformer.class));
        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.READY.perform(RefinementGenerator.class));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void isPerformable_READY() {

        assertTrue(CHIAReplacementState.READY
                .isPerformable(ReplacementReader.class));
        assertTrue(CHIAReplacementState.READY
                .isPerformable(ConstraintReader.class));
        assertTrue(CHIAReplacementState.READY
                .isPerformable(ConstraintToStringTrasformer.class));
        assertTrue(CHIAReplacementState.READY
                .isPerformable(ReplacementToStringTransformer.class));
        assertTrue(CHIAReplacementState.READY
                .isPerformable(RefinementGenerator.class));
        assertTrue(CHIAReplacementState.READY
                .isPerformable(ReplacementChecker.class));
        assertFalse(CHIAReplacementState.READY
                .isPerformable(ModelToStringTrasformer.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected=CHIAException.class)
    public void testPerform_READY_CHIAException() throws CHIAException {
        CHIAReplacementState.READY.perform(ModelToStringTrasformer.class);

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_CHECKED() throws CHIAException {

        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.CHECKED.perform(ReplacementReader.class));
        assertEquals(CHIAReplacementState.READY,
                CHIAReplacementState.CHECKED.perform(ConstraintReader.class));
        assertEquals(CHIAReplacementState.CHECKED,
                CHIAReplacementState.CHECKED
                        .perform(ConstraintToStringTrasformer.class));
        assertEquals(CHIAReplacementState.CHECKED,
                CHIAReplacementState.CHECKED
                        .perform(ReplacementToStringTransformer.class));
        assertEquals(CHIAReplacementState.CHECKED,
                CHIAReplacementState.CHECKED.perform(RefinementGenerator.class));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void isPerformable_CHECKED() {

        assertTrue(CHIAReplacementState.CHECKED
                .isPerformable(ReplacementReader.class));
        assertTrue(CHIAReplacementState.CHECKED
                .isPerformable(ConstraintReader.class));
        assertTrue(CHIAReplacementState.CHECKED
                .isPerformable(ConstraintToStringTrasformer.class));
        assertTrue(CHIAReplacementState.CHECKED
                .isPerformable(ReplacementToStringTransformer.class));
        assertTrue(CHIAReplacementState.CHECKED
                .isPerformable(RefinementGenerator.class));
        assertFalse(CHIAReplacementState.CHECKED
                .isPerformable(ReplacementChecker.class));
    }
    
    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAReplacementState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected=CHIAException.class)
    public void testPerform_CHECKED_CHIAException() throws CHIAException {
        CHIAReplacementState.CHECKED.perform(ModelToStringTrasformer.class);

    }

}
