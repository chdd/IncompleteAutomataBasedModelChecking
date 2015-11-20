/**
 * 
 */
package it.polimi.constraints.io.in.constraint;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.constraints.Constraint;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author Claudio1
 *
 */
public class ConstraintReaderTest {

	private static final String path = "it.polimi.constraints";

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.constraint.ConstraintReader#ConstraintReader(java.io.File)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testConstraintReaderFileNull() {
		new ConstraintReader(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.constraint.ConstraintReader#ConstraintReader(java.io.File)}
	 * .
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testConstraintReaderFile() throws URISyntaxException {
		assertNotNull(new ConstraintReader(new File(ClassLoader
				.getSystemResource(path + "/test01/constraint.xml").toURI())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.constraint.ConstraintReader#perform()}
	 * .
	 * 
	 * @throws URISyntaxException
	 */
	@Test
	public void testPerform() throws URISyntaxException {
		Constraint constraint = new ConstraintReader(new File(ClassLoader
				.getSystemResource(path + "/test01/constraint.xml").toURI()))
				.perform();
		assertEquals(1, constraint.getSubProperties().size());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.constraint.ConstraintReader#perform()}
	 * .
	 */
	@Test
	public void testPerformIBA() {
		Constraint constraint = new ConstraintReader(new File(getClass()
				.getClassLoader().getResource(path + "/test01/constraint.xml")
				.getFile())).perform();
		BA ba = constraint.getSubProperties().iterator().next().getAutomaton();

		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		propositions.add(new GraphProposition("b", false));
		propositions.add(new SigmaProposition());

		assertEquals(propositions, ba.getPropositions());

		Set<State> states = new HashSet<State>();
		states.add(new StateFactory().create("536", 536));
		states.add(new StateFactory().create("509", 509));
		states.add(new StateFactory().create("528", 528));
		states.add(new StateFactory().create("503", 503));
		states.add(new StateFactory().create("507", 507));
		states.add(new StateFactory().create("514", 514));

		assertEquals(states, ba.getStates());

		assertTrue(ba.getAcceptStates().contains(
				new StateFactory().create("528", 528)));
		assertTrue(ba.getAcceptStates().contains(
				new StateFactory().create("507", 507)));

	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.constraint.ConstraintReader#perform()}
	 * .
	 */
	@Test
	public void testPerformReachability() {
		Constraint constraint = new ConstraintReader(new File(getClass()
				.getClassLoader().getResource(path + "/test01/constraint.xml")
				.getFile())).perform();
		assertEquals(4, constraint.getSubProperties().iterator().next()
				.getLowerReachabilityRelation().getReachabilityAcceptingMap()
				.values().size());

	}
	
	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.constraint.ConstraintReader#perform()}
	 * .
	 */
	@Test
	public void testPerformUpperReachability() {
		Constraint constraint = new ConstraintReader(new File(getClass()
				.getClassLoader().getResource(path + "/test01/constraint.xml")
				.getFile())).perform();
		assertEquals(6, constraint.getSubProperties().iterator().next()
				.getUpperReachabilityRelation().getReachabilityAcceptingMap()
				.values().size());

	}

}
