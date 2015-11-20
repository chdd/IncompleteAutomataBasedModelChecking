/**
 * 
 */
package it.polimi.constraints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.constraints.components.SubProperty;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class ConstraintTest {

	/**
	 * Test method for {@link it.polimi.constraints.Constraint#Constraint()}.
	 */
	@Test
	public void testConstraint() {
		assertNotNull(new Constraint());
	}

	
	/**
	 * Test method for {@link it.polimi.constraints.Constraint#addSubProperty(it.polimi.constraints.components.SubProperty)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddSubPropertyNull() {
		Constraint c=new Constraint();
		c.addSubProperty(null);
	}
	/**
	 * Test method for {@link it.polimi.constraints.Constraint#addSubProperty(it.polimi.constraints.components.SubProperty)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddSubPropertyIllegalArgument() {
		State state=new StateFactory().create();
		BA ba=new BA(new ClaimTransitionFactory());
		Constraint c=new Constraint();
		SubProperty s=new SubProperty(state, ba);
		SubProperty s2=new SubProperty(state, ba);
		c.addSubProperty(s);
		c.addSubProperty(s2);
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.Constraint#addSubProperty(it.polimi.constraints.components.SubProperty)}.
	 */
	@Test
	public void testAddSubProperty() {
		State state=new StateFactory().create();
		BA ba=new BA(new ClaimTransitionFactory());
		Constraint c=new Constraint();
		SubProperty s=new SubProperty(state, ba);
		c.addSubProperty(s);
		assertNotNull(c.getSubProperty(state));
		assertEquals(s, c.getSubProperty(state));
	}
	/**
	 * Test method for {@link it.polimi.constraints.Constraint#getSubProperties()}.
	 */
	@Test
	public void testGetSubProperties() {
		State state=new StateFactory().create();
		BA ba=new BA(new ClaimTransitionFactory());
		Constraint c=new Constraint();
		SubProperty s=new SubProperty(state, ba);
		c.addSubProperty(s);
		assertTrue(c.getSubProperties().contains(s));
	}

	/**
	 * Test method for {@link it.polimi.constraints.Constraint#getSubProperty(it.polimi.automata.state.State)}.
	 */
	@Test
	public void testGetSubProperty() {
		State state=new StateFactory().create();
		BA ba=new BA(new ClaimTransitionFactory());
		Constraint c=new Constraint();
		SubProperty s=new SubProperty(state, ba);
		c.addSubProperty(s);
		assertEquals(s, c.getSubProperty(state));
	}

	/**
	 * Test method for {@link it.polimi.constraints.Constraint#getSubProperty(it.polimi.automata.state.State)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testGetSubPropertyNullState() {
		State state=new StateFactory().create();
		BA ba=new BA(new ClaimTransitionFactory());
		Constraint c=new Constraint();
		SubProperty s=new SubProperty(state, ba);
		c.addSubProperty(s);
		c.getSubProperty(null);
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.Constraint#getSubProperty(it.polimi.automata.state.State)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetSubPropertyIllegalState() {
		State state=new StateFactory().create();
		BA ba=new BA(new ClaimTransitionFactory());
		Constraint c=new Constraint();
		SubProperty s=new SubProperty(state, ba);
		c.addSubProperty(s);
		c.getSubProperty(new StateFactory().create());
	}
	

	/**
	 * Test method for {@link it.polimi.constraints.Constraint#getConstrainedStates()}.
	 */
	@Test
	public void testGetConstrainedStates() {
		State state=new StateFactory().create();
		BA ba=new BA(new ClaimTransitionFactory());
		Constraint c=new Constraint();
		SubProperty s=new SubProperty(state, ba);
		c.addSubProperty(s);
		assertTrue(c.getConstrainedStates().contains(state));
		assertEquals(1, c.getConstrainedStates().size());
	}

}
