/**
 * 
 */
package it.polimi.console;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Claudio Menghi
 *
 */
public class CHIAReplacementConsoleTest {

    /**
     * Test method for {@link it.polimi.console.CHIAReplacementConsole#CHIAReplacementConsole()}.
     */
    @Test
    public void testCHIAReplacementConsole() {
        assertNotNull(new CHIAReplacementConsole());
    }

    /**
     * Test method for {@link it.polimi.console.CHIAReplacementConsole#loadConstraint(java.lang.String)}.
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws FileNotFoundException 
     */
    @Test
    public void testLoadConstraint() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        CHIAReplacementConsole rep=new CHIAReplacementConsole();
        rep.loadConstraint(getClass()
                .getClassLoader().getResource("it/polimi/console/LivenessConstraint.xml").getPath());
        assertNotNull(rep.constraint);
    }

    /**
     * Test method for {@link it.polimi.console.CHIAReplacementConsole#dispConstraint()}.
     * @throws Exception 
     */
    @Test
    public void testDispConstraint() throws Exception {
        CHIAReplacementConsole rep=new CHIAReplacementConsole();
        rep.loadConstraint(getClass()
                .getClassLoader().getResource("it/polimi/console/LivenessConstraint.xml").getPath());
        rep.dispConstraint();
        assertNotNull(rep.constraint);
    }

    /**
     * Test method for {@link it.polimi.console.CHIAReplacementConsole#loadReplacement(java.lang.String)}.
     * @throws Exception 
     */
    @Test
    public void testLoadReplacement() throws Exception {
        CHIAReplacementConsole rep=new CHIAReplacementConsole();
        rep.loadReplacement(getClass()
                .getClassLoader().getResource("it/polimi/console/ReplacementT10.xml").getPath());
        assertNotNull(rep.replacement);
    }

    /**
     * Test method for {@link it.polimi.console.CHIAReplacementConsole#dispReplacement()}.
     * @throws Exception 
     */
    @Test
    public void testDispReplacement() throws Exception {
        CHIAReplacementConsole rep=new CHIAReplacementConsole();
        rep.loadReplacement(getClass()
                .getClassLoader().getResource("it/polimi/console/ReplacementT10.xml").getPath());
        rep.replacementChecking();
        rep.dispReplacement();
        assertNotNull(rep.replacement);
    }

    /**
     * Test method for {@link it.polimi.console.CHIAReplacementConsole#saveRefinement(java.lang.String)}.
     */
    @Ignore @Test
    public void testSaveRefinement() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link it.polimi.console.CHIAReplacementConsole#replacementChecking()}.
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws FileNotFoundException 
     */
    @Ignore @Test
    public void testReplacementChecking() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        CHIAReplacementConsole rep=new CHIAReplacementConsole();
        rep.loadReplacement(getClass()
                .getClassLoader().getResource("it/polimi/console/ReplacementT10.xml").getPath());
        rep.loadConstraint(getClass()
                .getClassLoader().getResource("it/polimi/console/LivenessConstraint.xml").getPath());
        rep.replacementChecking();
        assertNotNull(rep.replacement);
    }
}
