/**
 * 
 */
package it.polimi.automata.labeling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class LabelImplTest {

	private Set<IGraphProposition> labels;
	private Set<IGraphProposition> labelsSet2;
	
	@Before
	public void setUp() {
		this.labels=new HashSet<IGraphProposition>();
		this.labels.add(new GraphProposition("p", false));
		this.labelsSet2=new HashSet<IGraphProposition>();
		this.labelsSet2.add(new GraphProposition("p", false));
		this.labelsSet2.add(new GraphProposition("c", false));
	}
	
	/**
	 * Test method for {@link it.polimi.automata.labeling.impl.LabelImpl#LabelImpl(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testLabelImplNull() {
		new LabelImpl(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.labeling.impl.LabelImpl#LabelImpl(java.util.Set)}.
	 */
	@Test
	public void testLabelImpl() {
		LabelImpl label=new LabelImpl(labels);
		assertNotNull(label);
		assertEquals(label.getLabels(), labels);
	}

	/**
	 * Test method for {@link it.polimi.automata.labeling.impl.LabelImpl#getLabels()}.
	 */
	@Test
	public void testGetAtomicPropositions() {
		LabelImpl label=new LabelImpl(labels);
		assertNotNull(label);
		assertEquals(label.getLabels(), labels);
	}

	/**
	 * Test method for {@link it.polimi.automata.labeling.impl.LabelImpl#toString()}.
	 */
	@Test
	public void testToString() {
		LabelImpl label=new LabelImpl(new HashSet<IGraphProposition>());
		assertEquals(label.toString(), "");
		label=new LabelImpl(labels);
		assertEquals(label.toString(), "p");
		label=new LabelImpl(labelsSet2);
		assertTrue(label.toString().equals("p^c") || label.toString().equals("c^p"));
	}

}
