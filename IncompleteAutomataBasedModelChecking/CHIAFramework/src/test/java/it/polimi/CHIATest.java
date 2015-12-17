/**
 * 
 */
package it.polimi;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class CHIATest {

    /**
     * Test method for {@link it.polimi.CHIA#usage()}.
     * @throws IOException 
     */
    @Test
    public void testUsage() throws IOException {
        CHIA chia=new CHIA();
        chia.usage();
        assertNotNull(chia);
    }

    /**
     * Test method for {@link it.polimi.CHIA#CHIA()}.
     * @throws IOException 
     */
    @Test
    public void testCHIA() throws IOException {
        assertNotNull(new CHIA());
    }

    /**
     * Test method for {@link it.polimi.CHIA#run()}.
     */
    @Ignore @Test
    public void testRun() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link it.polimi.CHIA#main(java.lang.String[])}.
     */
    @Ignore @Test
    public void testMain() {
        fail("Not yet implemented");
    }

}
