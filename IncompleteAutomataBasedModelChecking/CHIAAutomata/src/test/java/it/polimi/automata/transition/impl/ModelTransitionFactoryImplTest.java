/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.*;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.transition.Transition;

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
public class ModelTransitionFactoryImplTest {

	private ModelTransitionFactoryImpl<Label> modelFactory;
	private Set<Label> labels1;
	private Set<Label> labels2;
	@Before
	public void setUp(){
		this.modelFactory=new ModelTransitionFactoryImpl<Label>();
		
		this.labels1=new HashSet<Label>();
		Set<IGraphProposition> propositions1=new HashSet<IGraphProposition>();
		propositions1.add(new GraphProposition("p", true));
		this.labels1.add(new LabelFactoryImpl().create(propositions1));
		
		this.labels2=new HashSet<Label>();
		Set<IGraphProposition> propositions2=new HashSet<IGraphProposition>();
		propositions2.add(new GraphProposition("p", false));
		this.labels2.add(new LabelFactoryImpl().create(propositions2));
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.ModelTransitionFactoryImpl#create(java.util.Set)}.
	 */
	@Test
	public void testCreateSetOfLABEL() {
		Transition<Label> t=this.modelFactory.create(labels2);
		assertNotNull(t);
		assertTrue(t.getLabels()!=labels2);
		assertTrue(t.getLabels().equals(labels2));
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.ModelTransitionFactoryImpl#createFromLabel(IllegalSet)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateSetOfLABELIllegal() {
		this.modelFactory.create(labels1);
	}

	/**
	 * Test method for {@link it.polimi.automata.transition.impl.ModelTransitionFactoryImpl#createFromLabel(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testCreateSetOfLABELNull() {
		this.modelFactory.create(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.ModelTransitionFactoryImpl#create(int, java.util.Set)}.
	 */
	@Test
	public void testCreateIntSetOfLABEL() {
		Transition<Label> t=this.modelFactory.create(1, labels2);
		assertNotNull(t);
		assertTrue(t.getLabels()!=labels2);
		assertTrue(t.getLabels().equals(labels2));
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.ModelTransitionFactoryImpl#create(int, null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testCreateIntSetOfLABELNull() {
		this.modelFactory.create(1, null);
	}
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.ModelTransitionFactoryImpl#create(int, IllegalArgumen)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateIntSetOfLABELIllegalArgument() {
		this.modelFactory.create(1, labels1);
	}

}
