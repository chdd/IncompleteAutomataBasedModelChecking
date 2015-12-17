/**
 * 
 */
package it.polimi.statemachine.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.action.CHIAException;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ClaimToStringTrasformer;
import it.polimi.automata.io.out.ModelToStringTrasformer;
import it.polimi.checker.Checker;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.io.out.constraint.ConstraintToStringTrasformer;
import it.polimi.constraints.io.out.constraint.ConstraintWriter;
import it.polimi.model.ltltoba.ClaimLTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class CHIAAutomataStateTest {

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_INIT() {
        assertTrue(CHIAAutomataState.INIT.isPerformable(ModelReader.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimLTLReader.class));
        assertTrue(CHIAAutomataState.INIT
                .isPerformable(LTLtoBATransformer.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimReader.class));
        assertFalse(CHIAAutomataState.INIT.isPerformable(Checker.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_INIT() throws CHIAException {

        assertEquals(CHIAAutomataState.MODELLOADED,
                CHIAAutomataState.INIT.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.INIT.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.INIT.perform(ClaimLTLReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.INIT.perform(LTLtoBATransformer.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.INIT.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.INIT.perform(ClaimReader.class));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testIsPerformable_INIT_CHIAException() throws CHIAException {
        CHIAAutomataState.INIT.perform(ConstraintGenerator.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_MODELLOADED() {
        assertTrue(CHIAAutomataState.MODELLOADED
                .isPerformable(ModelReader.class));
        assertTrue(CHIAAutomataState.MODELLOADED
                .isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.MODELLOADED
                .isPerformable(ClaimLTLReader.class));
        assertTrue(CHIAAutomataState.MODELLOADED
                .isPerformable(LTLtoBATransformer.class));
        assertTrue(CHIAAutomataState.MODELLOADED
                .isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.MODELLOADED
                .isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.MODELLOADED
                .isPerformable(ModelToStringTrasformer.class));
        assertFalse(CHIAAutomataState.MODELLOADED.isPerformable(Checker.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_MODELLOADED() throws CHIAException {

        assertEquals(CHIAAutomataState.MODELLOADED,
                CHIAAutomataState.MODELLOADED.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.MODELLOADED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.MODELLOADED.perform(ClaimLTLReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.MODELLOADED.perform(LTLtoBATransformer.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.MODELLOADED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.MODELLOADED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.MODELLOADED,
                CHIAAutomataState.MODELLOADED
                        .perform(ModelToStringTrasformer.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testIsPerformable_MODELLOADED_CHIAException()
            throws CHIAException {
        CHIAAutomataState.MODELLOADED.perform(IntersectionBuilder.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_PROPERTYLOADED() throws CHIAException {

        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.PROPERTYLOADED.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.PROPERTYLOADED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.PROPERTYLOADED.perform(ClaimLTLReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.PROPERTYLOADED
                        .perform(LTLtoBATransformer.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.PROPERTYLOADED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,
                CHIAAutomataState.PROPERTYLOADED
                        .perform(ClaimToStringTrasformer.class));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_PROPERTYLOADED() {
        assertTrue(CHIAAutomataState.PROPERTYLOADED
                .isPerformable(ModelReader.class));
        assertTrue(CHIAAutomataState.PROPERTYLOADED
                .isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.PROPERTYLOADED
                .isPerformable(ClaimLTLReader.class));
        assertTrue(CHIAAutomataState.PROPERTYLOADED
                .isPerformable(LTLtoBATransformer.class));
        assertTrue(CHIAAutomataState.PROPERTYLOADED
                .isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.PROPERTYLOADED
                .isPerformable(ClaimToStringTrasformer.class));
        assertFalse(CHIAAutomataState.PROPERTYLOADED
                .isPerformable(Checker.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testPerform_PROPERTYLOADED_CHIAException() throws CHIAException {
        CHIAAutomataState.PROPERTYLOADED.perform(IntersectionBuilder.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_READY() throws CHIAException {

        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.PROPERTYLOADED.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(ClaimLTLReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(LTLtoBATransformer.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(ModelToStringTrasformer.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(ClaimToStringTrasformer.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.READY.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.CHECKED,
                CHIAAutomataState.READY.perform(Checker.class));
        assertEquals(CHIAAutomataState.CONSTRAINTCOMPUTED,
                CHIAAutomataState.CHECKED.perform(ConstraintGenerator.class));
       
     }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_READY() {
        assertTrue(CHIAAutomataState.READY.isPerformable(ModelReader.class));
        assertTrue(CHIAAutomataState.READY.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.READY.isPerformable(ClaimLTLReader.class));
        assertTrue(CHIAAutomataState.READY
                .isPerformable(LTLtoBATransformer.class));
        assertTrue(CHIAAutomataState.READY.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.READY.isPerformable(Checker.class));
        assertTrue(CHIAAutomataState.READY
                .isPerformable(ClaimToStringTrasformer.class));
        assertTrue(CHIAAutomataState.READY
                .isPerformable(ModelToStringTrasformer.class));
        assertFalse(CHIAAutomataState.READY
                .isPerformable(ConstraintGenerator.class));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testPerform_READY_CHIAException() throws CHIAException {

        CHIAAutomataState.READY.perform(ConstraintGenerator.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_CHECKED() throws CHIAException {

        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CHECKED.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CHECKED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CHECKED.perform(ClaimLTLReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CHECKED.perform(LTLtoBATransformer.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CHECKED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CHECKED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CHECKED.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.CHECKED,
                CHIAAutomataState.CHECKED
                        .perform(ModelToStringTrasformer.class));
        assertEquals(CHIAAutomataState.CHECKED,
                CHIAAutomataState.CHECKED
                        .perform(ClaimToStringTrasformer.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected = CHIAException.class)
    public void testPerform_CHECKED_CHIAException() throws CHIAException {
        CHIAAutomataState.CHECKED.perform(ConstraintToStringTrasformer.class);
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_CHECKED() {
        assertTrue(CHIAAutomataState.CHECKED.isPerformable(ModelReader.class));
        assertTrue(CHIAAutomataState.CHECKED.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.CHECKED
                .isPerformable(ClaimLTLReader.class));
        assertTrue(CHIAAutomataState.CHECKED
                .isPerformable(LTLtoBATransformer.class));
        assertTrue(CHIAAutomataState.CHECKED.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.CHECKED
                .isPerformable(ClaimToStringTrasformer.class));
        assertTrue(CHIAAutomataState.CHECKED
                .isPerformable(ModelToStringTrasformer.class));
        assertFalse(CHIAAutomataState.CHECKED.isPerformable(Checker.class));

        assertTrue(CHIAAutomataState.CHECKED.isPerformable(ConstraintGenerator.class));

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test
    public void testPerform_CONSTRAINTCOMPUTED() throws CHIAException {

        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CONSTRAINTCOMPUTED.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CONSTRAINTCOMPUTED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CONSTRAINTCOMPUTED
                        .perform(ClaimLTLReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CONSTRAINTCOMPUTED
                        .perform(LTLtoBATransformer.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CONSTRAINTCOMPUTED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CONSTRAINTCOMPUTED.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.READY,
                CHIAAutomataState.CONSTRAINTCOMPUTED.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.CONSTRAINTCOMPUTED,
                CHIAAutomataState.CONSTRAINTCOMPUTED
                        .perform(ModelToStringTrasformer.class));
        assertEquals(CHIAAutomataState.CONSTRAINTCOMPUTED,
                CHIAAutomataState.CONSTRAINTCOMPUTED
                        .perform(ClaimToStringTrasformer.class));
        assertEquals(CHIAAutomataState.CONSTRAINTCOMPUTED,
                CHIAAutomataState.CONSTRAINTCOMPUTED
                        .perform(ConstraintToStringTrasformer.class));
        assertEquals(CHIAAutomataState.CONSTRAINTCOMPUTED,
                CHIAAutomataState.CONSTRAINTCOMPUTED
                        .perform(ConstraintWriter.class));
        assertEquals(CHIAAutomataState.CONSTRAINTCOMPUTED,
                CHIAAutomataState.CONSTRAINTCOMPUTED.perform(ConstraintWriter.class));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}
     * .
     * 
     * @throws CHIAException
     */
    @Test(expected=CHIAException.class)
    public void testPerform_CONSTRAINTCOMPUTED_CHIAException() throws CHIAException{
        CHIAAutomataState.CONSTRAINTCOMPUTED.perform(ConstraintGenerator.class);
    }
    /**
     * Test method for
     * {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}
     * .
     */
    @Test
    public void testIsPerformable_CONSTRAINTCOMPUTED() {
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ModelReader.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ClaimLTLReader.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(LTLtoBATransformer.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ClaimToStringTrasformer.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ModelToStringTrasformer.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ConstraintToStringTrasformer.class));
        assertTrue(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ConstraintWriter.class));
        

        assertFalse(CHIAAutomataState.CONSTRAINTCOMPUTED
                .isPerformable(ConstraintGenerator.class));

    }

}
