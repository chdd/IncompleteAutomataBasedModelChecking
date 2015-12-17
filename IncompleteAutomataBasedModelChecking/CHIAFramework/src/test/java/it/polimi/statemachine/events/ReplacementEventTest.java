/**
 * 
 */
package it.polimi.statemachine.events;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class ReplacementEventTest {

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.ReplacementEvent#parse(java.lang.String, it.polimi.console.CHIAReplacementConsole)}
     * .
     */
    @Ignore
    @Test
    public void testParse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.ReplacementEvent#getShortCuts()}.
     */
    @Test
    public void testGetShortCuts() {
        assertEquals("ck", ReplacementEvent.CHECK.getCommand());
        assertEquals("dispc", ReplacementEvent.DISPLAYCONSTRAINT.getCommand());
        assertEquals("dispr", ReplacementEvent.DISPLAYREPLACEMENT.getCommand());
        assertEquals("help", ReplacementEvent.HELP.getCommand());
        assertEquals("lc", ReplacementEvent.LOADCONSTRAINT.getCommand());
        assertEquals("lr", ReplacementEvent.LOADREPLACEMENT.getCommand());
        assertEquals("sref", ReplacementEvent.SAVEREFINEMENT.getCommand());
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.ReplacementEvent#getShortCuts()}.
     */
    @Test
    public void testGetCommandMeaning() {
        assertNotNull(ReplacementEvent.CHECK.getCommandMeaning());
        assertNotNull(ReplacementEvent.DISPLAYCONSTRAINT.getCommandMeaning());
        assertNotNull(ReplacementEvent.DISPLAYREPLACEMENT.getCommandMeaning());
        assertNotNull(ReplacementEvent.HELP.getCommandMeaning());
        assertNotNull(ReplacementEvent.LOADCONSTRAINT.getCommandMeaning());
        assertNotNull(ReplacementEvent.LOADREPLACEMENT.getCommandMeaning());
        assertNotNull(ReplacementEvent.SAVEREFINEMENT.getCommandMeaning());
    }

    

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.ReplacementEvent#toString()}.
     */
    @Test
    public void testToString() {
        assertNotNull(ReplacementEvent.CHECK.toString());
        assertNotNull(ReplacementEvent.DISPLAYCONSTRAINT.toString());
        assertNotNull(ReplacementEvent.DISPLAYREPLACEMENT.toString());
        assertNotNull(ReplacementEvent.HELP.toString());
        assertNotNull(ReplacementEvent.LOADCONSTRAINT.toString());
        assertNotNull(ReplacementEvent.LOADREPLACEMENT.toString());
        assertNotNull(ReplacementEvent.SAVEREFINEMENT.toString());
    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.ReplacementEvent#getShortCuts()}.
     */
    @Test
    public void testRequireParams() {

        assertFalse(ReplacementEvent.CHECK.requiresParams());
        assertFalse(ReplacementEvent.DISPLAYCONSTRAINT.requiresParams());
        assertFalse(ReplacementEvent.DISPLAYREPLACEMENT.requiresParams());
        assertTrue(ReplacementEvent.HELP.requiresParams());
        assertTrue(ReplacementEvent.LOADCONSTRAINT.requiresParams());
        assertTrue(ReplacementEvent.LOADREPLACEMENT.requiresParams());
        assertTrue(ReplacementEvent.SAVEREFINEMENT.requiresParams());

    }

    /**
     * Test method for
     * {@link it.polimi.statemachine.events.AutomataEvent#computeCompleters()}.
     */
    @Test
    public void testComputeCompleters() {
        assertNotNull(ReplacementEvent.computeCompleters());
    }

    /**
    * Test method for
    * {@link it.polimi.statemachine.events.AutomataEvent#getCommands()}.
    */
   @Test
   public void testGetCommands() {
       assertTrue(ReplacementEvent.getCommands().contains(
               ReplacementEvent.CHECK.getCommand()));
       assertTrue(ReplacementEvent.getCommands().contains(
               ReplacementEvent.DISPLAYCONSTRAINT.getCommand()));
       assertTrue(ReplacementEvent.getCommands().contains(
               ReplacementEvent.DISPLAYREPLACEMENT.getCommand()));
       assertTrue(ReplacementEvent.getCommands().contains(
               ReplacementEvent.HELP.getCommand()));
       assertTrue(ReplacementEvent.getCommands().contains(
               ReplacementEvent.LOADCONSTRAINT.getCommand()));
       assertTrue(ReplacementEvent.getCommands().contains(
               ReplacementEvent.LOADREPLACEMENT.getCommand()));
       assertTrue(ReplacementEvent.getCommands().contains(
               ReplacementEvent.SAVEREFINEMENT.getCommand()));
      
   }
   /**
    * Test method for
    * {@link it.polimi.statemachine.events.ReplacementEvent#toString()}.
    */
   @Test
   public void testGetDescription() {
       assertNotNull(ReplacementEvent.CHECK.getDescription());
       assertNotNull(ReplacementEvent.DISPLAYCONSTRAINT.getDescription());
       assertNotNull(ReplacementEvent.DISPLAYREPLACEMENT.getDescription());
       assertNotNull(ReplacementEvent.HELP.getDescription());
       assertNotNull(ReplacementEvent.LOADCONSTRAINT.getDescription());
       assertNotNull(ReplacementEvent.LOADREPLACEMENT.getDescription());
       assertNotNull(ReplacementEvent.SAVEREFINEMENT.getDescription());
   }
}
