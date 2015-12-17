/**
 * 
 */
package action;

import static org.junit.Assert.*;
import it.polimi.action.CHIAException;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class CHIAExceptionTest {

    /**
     * Test method for
     * {@link it.polimi.action.CHIAException#CHIAException(java.lang.String)}.
     */
    @Test
    public void testCHIAException() {
        assertNotNull(new CHIAException("Exception"));
    }

}
