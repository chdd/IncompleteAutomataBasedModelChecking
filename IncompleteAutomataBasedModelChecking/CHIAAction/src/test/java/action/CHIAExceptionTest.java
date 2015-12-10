/**
 * 
 */
package action;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class CHIAExceptionTest {

    /**
     * Test method for
     * {@link action.CHIAException#CHIAException(java.lang.String)}.
     */
    @Test
    public void testCHIAException() {
        assertNotNull(new CHIAException("Exception"));
    }

}
