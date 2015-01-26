/**
 * 
 */
package it.polimi.contraintcomputation.abstraction;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;

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
public class AbstractorTest {

	/*
	 * Claim 1
	 */
	private IntersectionBA<Label, State, Transition<Label>> intersectionBA;
	private State intersectionState1;
	private State intersectionState2;
	private State intersectionState3;
	private Transition<Label> intersectionTransition1;
	private Transition<Label> intersectionTransition2;
	private Transition<Label> intersectionTransition3;

	private Set<Label> intersectionTransition1Labels;
	private Set<Label> intersectionTransition2Labels;
	private Set<Label> intersectionTransition3Labels;
	
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private StateFactory<State> factory;
	
	@Before
	public void setUp() {
		this.intersectionBA = new IntBAFactoryImpl<Label, State, Transition<Label>>()
				.create();

		factory = new StateFactoryImpl();
		intersectionState1 = factory.create("intersectionState1");
		intersectionState2 = factory.create("intersectionState2");
		intersectionState3 = factory.create("intersectionState3");
		this.intersectionBA.addState(intersectionState1);
		this.intersectionBA.addState(intersectionState2);
		this.intersectionBA.addState(intersectionState3);
		transitionFactory = new ClaimTransitionFactoryImpl<Label>();

		this.intersectionTransition1Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		Label label1 = new LabelImplFactory().create(propositionsT1);
		intersectionTransition1Labels.add(label1);
		intersectionTransition1 = transitionFactory.create(intersectionTransition1Labels);

		this.intersectionTransition2Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		Label label2 = new LabelImplFactory().create(propositionsT2);
		intersectionTransition2Labels.add(label2);
		intersectionTransition2 = transitionFactory.create(intersectionTransition2Labels);

		this.intersectionTransition3Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		Label label3 = new LabelImplFactory().create(propositionsT3);
		intersectionTransition3Labels.add(label3);
		intersectionTransition3 = transitionFactory.create(intersectionTransition3Labels);

		this.intersectionBA.addCharacter(label1);
		this.intersectionBA.addCharacter(label2);
		this.intersectionBA.addCharacter(label3);
		this.intersectionBA.addTransition(intersectionState1, intersectionState2, intersectionTransition1);
		this.intersectionBA.addTransition(intersectionState2, intersectionState3, intersectionTransition2);
		this.intersectionBA.addTransition(intersectionState3, intersectionState3, intersectionTransition3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#Abstractor(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAbstractorNullGraph() {
		new Abstractor<Label, State, Transition<Label>>(null, new IntersectionTransitionFactoryImpl<Label>());
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#Abstractor(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAbstractorNullFactory() {
		new Abstractor<Label, State, Transition<Label>>(this.intersectionBA, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#Abstractor(it.polimi.automata.IntersectionBA)}
	 * .
	 */
	@Test
	public void testAbstractor() {
		assertNotNull(new Abstractor<Label, State, Transition<Label>>(this.intersectionBA, new IntersectionTransitionFactoryImpl<Label>()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#abstractIntersection()}
	 * .
	 */
	@Test
	public void testAbstractIntersection1() {
		this.intersectionBA.addInitialState(intersectionState1);
		this.intersectionBA.addAcceptState(intersectionState3);
		IntersectionBA<Label, State, Transition<Label>> ret=new Abstractor<Label, State, Transition<Label>>(this.intersectionBA.clone(), new IntersectionTransitionFactoryImpl<Label>()).abstractIntersection();
		assertTrue(ret.getStates().contains(intersectionState1));
		assertTrue(ret.getStates().contains(intersectionState3));
		assertTrue(ret.getStates().size()==2);
		assertTrue(ret.getInitialStates().contains(intersectionState1));
		assertTrue(ret.getAcceptStates().contains(intersectionState3));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#abstractIntersection()}
	 * .
	 */
	@Test
	public void testAbstractIntersection2() {
		this.intersectionBA.addInitialState(intersectionState1);
		this.intersectionBA.addAcceptState(intersectionState3);
		this.intersectionBA.addMixedState(intersectionState2);
		
		IntersectionBA<Label, State, Transition<Label>> ret=new Abstractor<Label, State, Transition<Label>>(this.intersectionBA.clone(), new IntersectionTransitionFactoryImpl<Label>()).abstractIntersection();
		assertTrue(ret.getStates().contains(intersectionState1));
		assertTrue(ret.getStates().contains(intersectionState2));
		assertTrue(ret.getStates().contains(intersectionState3));
		assertTrue(ret.getStates().size()==3);
		assertTrue(ret.getInitialStates().contains(intersectionState1));
		assertTrue(ret.getAcceptStates().contains(intersectionState3));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#abstractIntersection()}
	 * .
	 */
	@Test
	public void testAbstractIntersection3() {
		State intersectionState4=new StateFactoryImpl().create("intersectionState4");
		this.intersectionBA.addState(intersectionState4);
		this.intersectionBA.addInitialState(intersectionState1);
		this.intersectionBA.addAcceptState(intersectionState3);
		this.intersectionBA.addMixedState(intersectionState2);
		this.intersectionBA.addTransition(intersectionState1, intersectionState4, this.transitionFactory.create());
		this.intersectionBA.addTransition(intersectionState4, intersectionState3, this.transitionFactory.create());
		
		IntersectionBA<Label, State, Transition<Label>> ret=new Abstractor<Label, State, Transition<Label>>(this.intersectionBA.clone(), new IntersectionTransitionFactoryImpl<Label>()).abstractIntersection();
		assertTrue(ret.getStates().contains(intersectionState1));
		assertTrue(ret.getStates().contains(intersectionState2));
		assertTrue(ret.getStates().contains(intersectionState3));
		assertTrue(ret.getStates().size()==3);
		assertTrue(ret.getInitialStates().contains(intersectionState1));
		assertTrue(ret.getAcceptStates().contains(intersectionState3));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#abstractIntersection()}
	 * .
	 */
	@Test
	public void testAbstractIntersection4() {
		State intersectionState4=new StateFactoryImpl().create("intersectionState4");
		this.intersectionBA.addState(intersectionState4);
		this.intersectionBA.addInitialState(intersectionState1);
		this.intersectionBA.addAcceptState(intersectionState3);
		this.intersectionBA.addMixedState(intersectionState2);
		this.intersectionBA.addMixedState(intersectionState4);
		this.intersectionBA.addTransition(intersectionState1, intersectionState4, this.transitionFactory.create());
		this.intersectionBA.addTransition(intersectionState4, intersectionState3, this.transitionFactory.create());
		
		IntersectionBA<Label, State, Transition<Label>> ret=new Abstractor<Label, State, Transition<Label>>(this.intersectionBA.clone(), new IntersectionTransitionFactoryImpl<Label>()).abstractIntersection();
		assertTrue(ret.getStates().contains(intersectionState1));
		assertTrue(ret.getStates().contains(intersectionState2));
		assertTrue(ret.getStates().contains(intersectionState3));
		assertTrue(ret.getStates().size()==4);
		assertTrue(ret.getInitialStates().contains(intersectionState1));
		assertTrue(ret.getAcceptStates().contains(intersectionState3));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Abstractor#abstractIntersection()}
	 * .
	 */
	@Test
	public void testAbstractIntersection5() {
		State intersectionState4=new StateFactoryImpl().create("intersectionState4");
		this.intersectionBA.addState(intersectionState4);
		this.intersectionBA.addInitialState(intersectionState1);
		this.intersectionBA.addAcceptState(intersectionState3);
		this.intersectionBA.addMixedState(intersectionState2);
		this.intersectionBA.addTransition(intersectionState1, intersectionState4, this.transitionFactory.create());
		this.intersectionBA.addTransition(intersectionState4, intersectionState3, this.transitionFactory.create());
		this.intersectionBA.addTransition(intersectionState1, intersectionState3, this.transitionFactory.create());
		
		
		IntersectionBA<Label, State, Transition<Label>> ret=new Abstractor<Label, State, Transition<Label>>(this.intersectionBA.clone(), new IntersectionTransitionFactoryImpl<Label>()).abstractIntersection();
		assertTrue(ret.getStates().contains(intersectionState1));
		assertTrue(ret.getStates().contains(intersectionState2));
		assertTrue(ret.getStates().contains(intersectionState3));
		assertTrue(ret.getStates().size()==3);
		assertTrue(ret.getInitialStates().contains(intersectionState1));
		assertTrue(ret.getAcceptStates().contains(intersectionState3));
	}

}
