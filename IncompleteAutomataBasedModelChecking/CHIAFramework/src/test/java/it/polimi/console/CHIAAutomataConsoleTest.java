/**
 * 
 */
package it.polimi.console;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Claudio Menghi
 *
 */
public class CHIAAutomataConsoleTest {

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#CHIAAutomataConsole(java.io.PrintStream)}
     * .
     */
    @Test
    public void testCHIAAutomataConsole() {
        assertNotNull(new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out))));
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#CHIAAutomataConsole(java.io.PrintStream)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testCHIAAutomataConsole_NullPointerException() {
        assertNotNull(new CHIAAutomataConsole(null));
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#loadModel(java.lang.String)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testLoadModel_NullPointerException() {
        new CHIAAutomataConsole(new PrintStream((OutputStream) (System.out)))
                .loadModel(null);
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#loadModel(java.lang.String)}
     * .
     */
    @Test
    public void testLoadModel() {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadModel(getClass()
                .getClassLoader().getResource("it/polimi/console/Model.xml").getPath());
        assertNotNull(chiaAutomataConsole.model);
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#loadModel(java.lang.String)}
     * .
     */
    @Test(expected=IllegalArgumentException.class)
    public void testLoadModel_IllegalArgumentException() {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadModel("Not existent model");
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#loadProperty(java.lang.String)}
     * .
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws FileNotFoundException 
     */
    @Test
    public void testLoadProperty() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadProperty(getClass()
                .getClassLoader().getResource("it/polimi/console/Liveness.xml").getPath());
        assertNotNull(chiaAutomataConsole.claim);
    }
    
   


    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#loadLTLProperty(java.lang.String)}
     * .
     */
    @Test
    public void testLoadLTLProperty() {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadLTLProperty(null);
        assertNull(chiaAutomataConsole.claim);
        chiaAutomataConsole.loadLTLProperty("[]a");
        assertNotNull(chiaAutomataConsole.claim);
    }

    /**
     * Test method for {@link it.polimi.console.CHIAAutomataConsole#dispModel()}
     * .
     * @throws Exception 
     * @throws ParserConfigurationException 
     */
    @Test
    public void testDispModel() throws ParserConfigurationException, Exception {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadModel(getClass()
                .getClassLoader().getResource("it/polimi/console/Model.xml").getPath());
        chiaAutomataConsole.dispModel();
        assertNotNull(chiaAutomataConsole.model);
    }
    
    /**
     * Test method for {@link it.polimi.console.CHIAAutomataConsole#dispModel()}
     * .
     * @throws Exception 
     * @throws ParserConfigurationException 
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDispModel_IllegalArgumentException() throws ParserConfigurationException, Exception {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadModel("Not existent model");
        
    }
    
    /**
     * Test method for {@link it.polimi.console.CHIAAutomataConsole#dispModel()}
     * .
     * @throws Exception 
     * @throws ParserConfigurationException 
     */
    @Test(expected=NullPointerException.class)
    public void testDispModel_NullPointerException() throws ParserConfigurationException, Exception {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.dispModel();
    }

    /**
     * Test method for {@link it.polimi.console.CHIAAutomataConsole#dispClaim()}
     * .
     * @throws Exception 
     */
    @Test
    public void testDispClaim() throws Exception {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadProperty(getClass()
                .getClassLoader().getResource("it/polimi/console/Liveness.xml").getPath());
        chiaAutomataConsole.dispClaim();
        assertNotNull(chiaAutomataConsole.claim);
    }

    
    /**
     * Test method for {@link it.polimi.console.CHIAAutomataConsole#check()}.
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws FileNotFoundException 
     */
    @Test
    public void testCheck() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadProperty(getClass()
                .getClassLoader().getResource("it/polimi/console/Liveness.xml").getPath());
        chiaAutomataConsole.loadModel(getClass()
                .getClassLoader().getResource("it/polimi/console/Model.xml").getPath());
        chiaAutomataConsole.check();
        assertTrue(chiaAutomataConsole.checker.isPerformed());
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#computeConstraint()}.
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws FileNotFoundException 
     */
    @Test
    public void testComputeConstraint() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadProperty(getClass()
                .getClassLoader().getResource("it/polimi/console/Liveness.xml").getPath());
        chiaAutomataConsole.loadModel(getClass()
                .getClassLoader().getResource("it/polimi/console/Model.xml").getPath());
        chiaAutomataConsole.check();
        chiaAutomataConsole.computeConstraint();
        assertNotNull(chiaAutomataConsole.constraint);
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#saveConstraint(java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testSaveConstraint() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link it.polimi.console.CHIAAutomataConsole#dispConstraint()}.
     * @throws Exception 
     */
    @Test
    public void testDispConstraint() throws Exception {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.loadProperty(getClass()
                .getClassLoader().getResource("it/polimi/console/Liveness.xml").getPath());
        chiaAutomataConsole.loadModel(getClass()
                .getClassLoader().getResource("it/polimi/console/Model.xml").getPath());
        chiaAutomataConsole.check();
        chiaAutomataConsole.computeConstraint();
        chiaAutomataConsole.dispConstraint();
        assertNotNull(chiaAutomataConsole.constraint);
        
    }

    /**
     * Test method for {@link it.polimi.console.CHIAAutomataConsole#exit()}.
     */
    @Test
    public void testExit() {
        CHIAAutomataConsole chiaAutomataConsole=new CHIAAutomataConsole(new PrintStream(
                (OutputStream) (System.out)));
        chiaAutomataConsole.exit();
    }

}
