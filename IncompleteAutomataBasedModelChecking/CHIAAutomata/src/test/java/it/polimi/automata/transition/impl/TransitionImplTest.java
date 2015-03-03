/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.Constants;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class TransitionImplTest {

	@Mock
	private IGraphProposition label1;
	
	@Mock
	private IGraphProposition label2;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(label1.toString()).thenReturn("a");
		when(label2.toString()).thenReturn("b");
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#TransitionImpl(java.util.Set, int)}.
	 */
	@Test
	public void testTransitionImpl() {
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		TransitionImpl t=new TransitionImpl(labels, 1);
		assertEquals(labels, t.getPropositions());
		assertEquals(1, t.getId());
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#TransitionImpl(java.util.Set, int)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransitionImpl_Null() {
		new TransitionImpl(null, 1);
	}
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#TransitionImpl(java.util.Set, int)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransitionImpl_Neg() {
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		new TransitionImpl(labels, -1);
		
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#getPropositions()}.
	 */
	@Test
	public void testGetLabels() {

		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		TransitionImpl t=new TransitionImpl(labels, 1);
		assertEquals(labels, t.getPropositions());
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#getId()}.
	 */
	@Test
	public void testGetId() {
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		TransitionImpl t=new TransitionImpl(labels, 1);
		assertEquals(1, t.getId());
	}

	

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#toString()}.
	 */
	@Test
	public void testToStringEmpty() {
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		TransitionImpl t=new TransitionImpl(labels, 1);
		assertTrue(t.toString().equals("{1} "));
	}
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#toString()}.
	 */
	@Test
	public void testToString() {
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		labels.add(label1);
		labels.add(label2);
		TransitionImpl t=new TransitionImpl(labels, 1);
		assertTrue(t.toString().equals("{1} "+"a"+Constants.AND+"b") 
				|| t.toString().equals("{1} "+"b"+Constants.AND+"a"));
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#equals}.
	 */
	@Test
	public void testEquals()  {
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		TransitionImpl t=new TransitionImpl(labels, 1);
		TransitionImpl t2=new TransitionImpl(labels, 2);
		TransitionImpl t3=new TransitionImpl(labels, 1);
		Set<IGraphProposition> labels2=new HashSet<IGraphProposition>();
		labels2.add(label1);
		TransitionImpl t4=new TransitionImpl(labels2, 1);
		assertFalse(t.equals(labels));
		assertFalse(t.equals(null));
		assertTrue(t.equals(t));
		assertFalse(t.equals(t2));
		assertTrue(t.equals(t3));
		assertTrue(t.equals(t4));
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#hashCode()}.
	 */
	@Test
	public void testHashCode(){
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		TransitionImpl t=new TransitionImpl(labels, 1);
		TransitionImpl t2=new TransitionImpl(labels, 2);
		TransitionImpl t3=new TransitionImpl(labels, 1);
		Set<IGraphProposition> labels2=new HashSet<IGraphProposition>();
		labels2.add(label1);
		TransitionImpl t4=new TransitionImpl(labels2, 1);
		assertFalse(t.hashCode()==labels.hashCode());
		assertTrue(t.hashCode()==t.hashCode());
		assertFalse(t.hashCode()==t2.hashCode());
		assertTrue(t.hashCode()==t3.hashCode());
		assertFalse(t.hashCode()!=t4.hashCode());
	}
	
}
