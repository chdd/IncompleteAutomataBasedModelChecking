/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
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

	private ModelTransitionFactoryImpl<State> modelFactory;
	private Set<IGraphProposition> labels1;
	private Set<IGraphProposition> labels2;
	@Before
	public void setUp(){
		this.modelFactory=new ModelTransitionFactoryImpl<State>(Transition.class);
		
		this.labels1=new HashSet<IGraphProposition>();
		labels1.add(new GraphProposition("p", true));
		
		this.labels2=new HashSet<IGraphProposition>();
		labels2.add(new GraphProposition("p", false));
	}
	
	/**
	 * Test method for {@link it.polimi.automata.transition.impl.ModelTransitionFactoryImpl#create(java.util.Set)}.
	 */
	@Test
	public void testCreateSetOfLABEL() {
		Transition t=this.modelFactory.create(labels2);
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
		Transition t=this.modelFactory.create(1, labels2);
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
