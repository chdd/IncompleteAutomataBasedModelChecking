/**
 * 
 */
package action;

import static org.junit.Assert.*;
import it.polimi.action.CHIAAction;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class CHIAActionTest {

    /**
     * Test method for {@link it.polimi.action.CHIAAction#CHIAAction(java.lang.String)}.
     */
    @Test
    public void testCHIAAction() {
        assertNotNull(new CHIAAction<Boolean>("NAME") {

            @Override
            public Boolean perform() throws Exception {

                return true;
            }

        });
    }

    /**
     * Test method for {@link it.polimi.action.CHIAAction#CHIAAction(java.lang.String)}.
     */
    @Test(expected = NullPointerException.class)
    public void testCHIAAction_NullName() {
        new CHIAAction<Boolean>(null) {

            @Override
            public Boolean perform() throws Exception {

                return true;
            }

        };
    }

    /**
     * Test method for {@link it.polimi.action.CHIAAction#isPerformed()}.
     */
    @Test
    public void testIsPerformed() {
        assertFalse(new CHIAAction<Boolean>("NAME") {

            @Override
            public Boolean perform() throws Exception {

                return true;
            }

        }.isPerformed());
    }

    /**
     * Test method for {@link it.polimi.action.CHIAAction#perform()}.
     * 
     * @throws Exception
     */
    @Test
    public void testPerform() throws Exception {
        assertTrue(new CHIAAction<Boolean>("NAME") {

            @Override
            public Boolean perform() throws Exception {

                return true;
            }

        }.perform());
    }

    /**
     * Test method for {@link it.polimi.action.CHIAAction#performed()}.
     */
    @Test
    public void testPerformed() {
        CHIAAction<Boolean> action=new CHIAAction<Boolean>("NAME") {

            @Override
            public Boolean perform() throws Exception {

                return true;
            }

        };
        action.performed();
        assertTrue(action.isPerformed());
    }

    /**
     * Test method for {@link it.polimi.action.CHIAAction#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals("NAME", new CHIAAction<Boolean>("NAME") {

            @Override
            public Boolean perform() throws Exception {

                return true;
            }

        }.getName());
    }
}
