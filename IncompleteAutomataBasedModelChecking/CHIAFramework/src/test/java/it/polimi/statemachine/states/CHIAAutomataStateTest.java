/**
 * 
 */
package it.polimi.statemachine.states;

import static org.junit.Assert.*;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.model.ltltoba.ClaimLTLReader;
import it.polimi.model.ltltoba.LTLtoBATransformer;

import org.junit.Test;

import action.CHIAException;

/**
 * @author Claudio1
 *
 */
public class CHIAAutomataStateTest {

    /**
     * Test method for {@link it.polimi.statemachine.states.CHIAAutomataState#perform(java.lang.Class)}.
     * @throws CHIAException 
     */
    @Test
    public void testPerform_INIT() throws CHIAException {

        assertEquals(CHIAAutomataState.MODELLOADED, CHIAAutomataState.INIT.perform(ModelReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED, CHIAAutomataState.INIT.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED, CHIAAutomataState.INIT.perform(ClaimLTLReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,CHIAAutomataState.INIT.perform(LTLtoBATransformer.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED,CHIAAutomataState.INIT.perform(ClaimReader.class));
        assertEquals(CHIAAutomataState.PROPERTYLOADED, CHIAAutomataState.INIT.perform(ClaimReader.class));
    }

    /**
     * Test method for {@link it.polimi.statemachine.states.CHIAAutomataState#isPerformable(java.lang.Class)}.
     */
    @Test
    public void testIsPerformable_INIT() {
        assertTrue(CHIAAutomataState.INIT.isPerformable(ModelReader.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimLTLReader.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(LTLtoBATransformer.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimReader.class));
        assertTrue(CHIAAutomataState.INIT.isPerformable(ClaimReader.class));
    }

}
