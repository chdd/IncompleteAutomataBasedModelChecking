/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.labeling.Label;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class TransitionImplTest {

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
	public void testToString() {
		Set<Label> labels=new HashSet<Label>();
		TransitionImpl<Label> t=new TransitionImpl<Label>(labels, 1);
		//TODO
	}

}
