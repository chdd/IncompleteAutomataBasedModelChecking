/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import it.polimi.Constants;
import it.polimi.automata.labeling.Label;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author claudiomenghi
 *
 */
public class TransitionImplTest {

	@Mock
	private Label label1;
	
	@Mock
	private Label label2;
	
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
		Set<Label> labels=new HashSet<Label>();
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		assertEquals(labels, t.getLabels());
		assertEquals(1, t.getId());
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#TransitionImpl(java.util.Set, int)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransitionImpl_Null() {
		new TransitionImpl<Label>(null, 1);
	}
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#TransitionImpl(java.util.Set, int)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransitionImpl_Neg() {
		Set<Label> labels=new HashSet<Label>();
		new TransitionImpl<Label>(labels, -1);
		
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#getLabels()}.
	 */
	@Test
	public void testGetLabels() {

		Set<Label> labels=new HashSet<Label>();
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		assertEquals(labels, t.getLabels());
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#getId()}.
	 */
	@Test
	public void testGetId() {
		Set<Label> labels=new HashSet<Label>();
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		assertEquals(1, t.getId());
	}

	

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#toString()}.
	 */
	@Test
	public void testToStringEmpty() {
		Set<Label> labels=new HashSet<Label>();
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		assertTrue(t.toString().equals("{1} "));
	}
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#toString()}.
	 */
	@Test
	public void testToString() {
		Set<Label> labels=new HashSet<Label>();
		labels.add(label1);
		labels.add(label2);
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		assertTrue(t.toString().equals("{1} "+Constants.LPAR+"a"+Constants.RPAR+Constants.OR+ Constants.LPAR+"b"+Constants.RPAR) 
				|| t.toString().equals("{1} "+Constants.LPAR+"b"+Constants.RPAR+Constants.OR+ Constants.LPAR+"a"+Constants.RPAR));
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#equals}.
	 */
	@Test
	public void testEquals()  {
		Set<Label> labels=new HashSet<Label>();
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		TransitionImpl<Label> t2=new TransitionImpl<Label>(labels, 2);
		TransitionImpl<Label> t3=new TransitionImpl<Label>(labels, 1);
		Set<Label> labels2=new HashSet<Label>();
		labels2.add(label1);
		TransitionImpl<Label> t4=new TransitionImpl<Label>(labels2, 1);
		assertFalse(t.equals(labels));
		assertFalse(t.equals(null));
		assertTrue(t.equals(t));
		assertFalse(t.equals(t2));
		assertTrue(t.equals(t3));
		assertFalse(t.equals(t4));
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.TransitionImpl#hashCode()}.
	 */
	@Test
	public void testHashCode(){
		Set<Label> labels=new HashSet<Label>();
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		TransitionImpl<Label> t2=new TransitionImpl<Label>(labels, 2);
		TransitionImpl<Label> t3=new TransitionImpl<Label>(labels, 1);
		Set<Label> labels2=new HashSet<Label>();
		labels2.add(label1);
		TransitionImpl<Label> t4=new TransitionImpl<Label>(labels2, 1);
		assertFalse(t.hashCode()==labels.hashCode());
		assertTrue(t.hashCode()==t.hashCode());
		assertFalse(t.hashCode()==t2.hashCode());
		assertTrue(t.hashCode()==t3.hashCode());
		assertFalse(t.hashCode()==t4.hashCode());
	}
	
}
