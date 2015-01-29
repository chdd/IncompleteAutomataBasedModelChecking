/**
 * 
 */
package it.polimi.automata.labeling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class LabelImplFactoryTest {

	private LabelImplFactory factory;
	private Set<IGraphProposition> labels;
	private Set<IGraphProposition> labelsSet2;

	
	@Before
	public void setUp() {
		this.factory = new LabelImplFactory();
		this.labels=new HashSet<IGraphProposition>();
		this.labels.add(new GraphProposition("p", false));
		this.labelsSet2=new HashSet<IGraphProposition>();
		this.labelsSet2.add(new GraphProposition("p", false));
		this.labelsSet2.add(new GraphProposition("c", false));
	}

	/**
		 * Test method for
		 * {@link it.polimi.automata.labeling.impl.LabelImplFactory#create()}.
		 */
		@Test
		public void testCreateFromLabel() {
			assertNotNull(this.factory.create());
			assertNotNull(this.factory.create().getLabels());
			assertTrue(this.factory.create().getLabels().isEmpty());
		}

	
	
	/**
		 * Test method for
		 * {@link it.polimi.automata.labeling.impl.LabelImplFactory#create(java.util.Set)}
		 * .
		 */
		@Test
		public void testCreateFromLabelSetOfPropositions() {
			assertNotNull(this.factory.create(labels));
			assertNotNull(this.factory.create(labels).getLabels());
			
			assertFalse(this.factory.create(labels).getLabels().isEmpty());
			assertTrue(this.factory.create(labels).getLabels()!=labels);
			assertEquals(this.factory.create(labels).getLabels(), labels);
		}
	
	/**
		 * Test method for
		 * {@link it.polimi.automata.labeling.impl.LabelImplFactory#createFromLabel(null)}
		 * .
		 */
		@Test(expected=NullPointerException.class)
		public void testCreateFromLabelSetOfPropositionsNUll() {
			this.factory.createFromLabel(null);
		}

}
