/**
 * 
 */
package it.polimi.statemachine.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class AutomataEventTest {

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getShortCuts()}.
     */
    @Test
    public void testGetShortCuts() {

        assertEquals("ck", AutomataEvent.CHECK.getCommand());
        assertEquals("cc", AutomataEvent.COMPUTECONSTRAINT.getCommand());
        assertEquals("dispc", AutomataEvent.DISPLAYCONSTRAINT.getCommand());
        assertEquals("dispm", AutomataEvent.DISPLAYMODEL.getCommand());
        assertEquals("dispp", AutomataEvent.DISPLAYPROPERTY.getCommand());
        assertEquals("exit", AutomataEvent.EXIT.getCommand());
        assertEquals("help", AutomataEvent.HELP.getCommand());
        assertEquals("lpLTL", AutomataEvent.LOADLTLPROPERTY.getCommand());
        assertEquals("lm", AutomataEvent.LOADMODEL.getCommand());
        assertEquals("lp", AutomataEvent.LOADPROPERTY.getCommand());
        assertEquals("sc", AutomataEvent.SAVECONSTRAINT.getCommand());
        

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getShortCuts()}.
     */
    @Test
    public void testGetCommandMeaning() {

        assertEquals("check", AutomataEvent.CHECK.getCommandMeaning());
        assertEquals("computeConstraint",
                AutomataEvent.COMPUTECONSTRAINT.getCommandMeaning());
        assertEquals("displayConstraint",
                AutomataEvent.DISPLAYCONSTRAINT.getCommandMeaning());
        assertEquals("displayModel",
                AutomataEvent.DISPLAYMODEL.getCommandMeaning());
        assertEquals("displayProperty",
                AutomataEvent.DISPLAYPROPERTY.getCommandMeaning());
        assertEquals("exit", AutomataEvent.EXIT.getCommandMeaning());
        assertEquals("help", AutomataEvent.HELP.getCommandMeaning());
        assertEquals("loadLTLProperty",
                AutomataEvent.LOADLTLPROPERTY.getCommandMeaning());
        assertEquals("loadModel", AutomataEvent.LOADMODEL.getCommandMeaning());
        assertEquals("loadProperty",
                AutomataEvent.LOADPROPERTY.getCommandMeaning());
        assertEquals("saveConstraint",
                AutomataEvent.SAVECONSTRAINT.getCommandMeaning());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getShortCuts()}.
     */
    @Test
    public void testGetParams() {

        assertTrue(null == AutomataEvent.CHECK.getParams());
        assertTrue(null == AutomataEvent.COMPUTECONSTRAINT.getParams());
        assertTrue(null == AutomataEvent.DISPLAYCONSTRAINT.getParams());
        assertTrue(null == AutomataEvent.DISPLAYMODEL.getParams());
        assertTrue(null == AutomataEvent.DISPLAYPROPERTY.getParams());
        assertTrue(null == AutomataEvent.EXIT.getParams());
        assertEquals("command: is the command to be performed",
                AutomataEvent.HELP.getParams());
        assertEquals(
                "LTLFormula: is the LTL formula that represents the property."
                        + "The LTL formula can be created starting from a set of propositional symbol \n"
                        + "true, false any lowercase string"
                        + "A set of boolean operators,\n "
                        + "\t ! (negation)\n" + "\t -> (implication) \n"
                        + "\t <-> (equivalence) \n" + "\t ^ (and)\n"
                        + "\t V (or)," + "A set of temporal operators,\n"
                        + "\t [] (always)\n" + "\t <> (eventually)"
                        + "\t U (until)" + "\t X (next).",
                AutomataEvent.LOADLTLPROPERTY.getParams());
        assertEquals(
                "modelFilePath:  the path of the file that contains the model to be checked",
                AutomataEvent.LOADMODEL.getParams());
        assertEquals("file", AutomataEvent.LOADPROPERTY.getParams());
        assertEquals(
                "constraintFilePath: is the path of the file where the constraint must be saved",
                AutomataEvent.SAVECONSTRAINT.getParams());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getShortCuts()}.
     */
    @Test
    public void testRequireParams() {

        assertFalse(AutomataEvent.CHECK.requiresParams());
        assertFalse(AutomataEvent.COMPUTECONSTRAINT.requiresParams());
        assertFalse(AutomataEvent.DISPLAYCONSTRAINT.requiresParams());
        assertFalse(AutomataEvent.DISPLAYMODEL.requiresParams());
        assertFalse(AutomataEvent.DISPLAYPROPERTY.requiresParams());
        assertFalse(AutomataEvent.EXIT.requiresParams());
        assertFalse(AutomataEvent.HELP.requiresParams());
        assertTrue(AutomataEvent.LOADLTLPROPERTY.requiresParams());
        assertTrue(AutomataEvent.LOADMODEL.requiresParams());
        assertTrue(AutomataEvent.LOADPROPERTY.requiresParams());
        assertTrue(AutomataEvent.SAVECONSTRAINT.requiresParams());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getShortCuts()}.
     */
    @Test
    public void testGetDescription() {

        assertNotNull(AutomataEvent.CHECK.getDescription());
        assertNotNull(AutomataEvent.COMPUTECONSTRAINT.getDescription());
        assertNotNull(AutomataEvent.DISPLAYCONSTRAINT.getDescription());
        assertNotNull(AutomataEvent.DISPLAYMODEL.getDescription());
        assertNotNull(AutomataEvent.DISPLAYPROPERTY.getDescription());
        assertNotNull(AutomataEvent.EXIT.getDescription());
        assertNotNull(AutomataEvent.HELP.getDescription());
        assertNotNull(AutomataEvent.LOADLTLPROPERTY.getDescription());
        assertNotNull(AutomataEvent.LOADMODEL.getDescription());
        assertNotNull(AutomataEvent.LOADPROPERTY.getDescription());
        assertNotNull(AutomataEvent.SAVECONSTRAINT.getDescription());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getCommands()}.
     */
    @Test
    public void testGetCommands() {
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.CHECK.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.COMPUTECONSTRAINT.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.DISPLAYCONSTRAINT.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.DISPLAYMODEL.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.DISPLAYPROPERTY.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.EXIT.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.HELP.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.LOADLTLPROPERTY.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.LOADMODEL.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.LOADPROPERTY.getCommand()));
        assertTrue(AutomataEvent.getCommands().contains(
                AutomataEvent.SAVECONSTRAINT.getCommand()));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getCommands()}.
     */
    @Test
    public void testGetShortCuts_Test() {
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.CHECK.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.COMPUTECONSTRAINT.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.DISPLAYCONSTRAINT.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.DISPLAYMODEL.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.DISPLAYPROPERTY.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.EXIT.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.HELP.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.LOADLTLPROPERTY.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.LOADMODEL.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.LOADPROPERTY.getCommand()));
        assertTrue(AutomataEvent.getShortCuts().contains(
                AutomataEvent.SAVECONSTRAINT.getCommand()));
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#getCommands()}.
     */
    @Test
    public void testToString() {
        assertNotNull(AutomataEvent.CHECK.toString());
        assertNotNull(AutomataEvent.COMPUTECONSTRAINT.toString());
        assertNotNull(AutomataEvent.DISPLAYCONSTRAINT.toString());
        assertNotNull(AutomataEvent.DISPLAYMODEL.toString());
        assertNotNull(AutomataEvent.DISPLAYPROPERTY.toString());
        assertNotNull(AutomataEvent.EXIT.toString());
        assertNotNull(AutomataEvent.HELP.toString());
        assertNotNull(AutomataEvent.LOADLTLPROPERTY.toString());
        assertNotNull(AutomataEvent.LOADMODEL.toString());
        assertNotNull(AutomataEvent.LOADPROPERTY.toString());
        assertNotNull(AutomataEvent.SAVECONSTRAINT.toString());
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#computeCompleters()}.
     */
    @Test
    public void testComputeCompleters() {
        assertNotNull(AutomataEvent.computeCompleters());
    }

}
