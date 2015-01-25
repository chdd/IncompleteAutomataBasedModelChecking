/**
 * 
 */
package it.polimi.checker.intersection;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;

import java.util.ArrayList;
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
public class IntersectionBuilderTest {

	private BA<Label, State, Transition<Label>> claim;
	private IBA<Label, State, Transition<Label>> model;
	
	private State claimState1;
	private State claimState2;
	private State claimState3;
	private Transition<Label> claimTransition1;
	private Transition<Label> claimTransition2;
	private Transition<Label> claimTransition3;
	
	private State modelState1;
	private State modelState2;
	private State modelState3;
	private Transition<Label> modelTransition1;
	private Transition<Label> modelTransition2;
	private Transition<Label> modelTransition3;
	private Set<Label> labelsT1;
	private Set<Label> labelsT2;
	private Set<Label> labelsT3;

	@Before
	public void setUp() {
		this.claim = new IBAFactoryImpl<Label, State, Transition<Label>>()
				.create();
		
		StateFactory<State> factory = new StateFactoryImpl();
		claimState1 = factory.create("claimState1");
		claimState2 = factory.create("claimState2");
		claimState3 = factory.create("claimState3");
		this.claim.addState(claimState1);
		this.claim.addState(claimState2);
		this.claim.addState(claimState3);
		TransitionFactory<Label, Transition<Label>> transitionFactory = new ModelTransitionFactoryImpl<Label>();

		this.labelsT1 = new HashSet<Label>();
		Set<IGraphProposition> propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		Label label1 = new LabelImplFactory().create(propositionsT1);
		labelsT1.add(label1);
		claimTransition1 = transitionFactory.create(labelsT1);

		this.labelsT2 = new HashSet<Label>();
		Set<IGraphProposition> propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		Label label2 = new LabelImplFactory().create(propositionsT2);
		labelsT2.add(label2);
		claimTransition2 = transitionFactory.create(labelsT2);

		this.labelsT3 = new HashSet<Label>();
		Set<IGraphProposition> propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		Label label3 = new LabelImplFactory().create(propositionsT3);
		labelsT3.add(label3);
		claimTransition3 = transitionFactory.create(labelsT3);

		this.claim.addCharacter(label1);
		this.claim.addCharacter(label2);
		this.claim.addCharacter(label3);
		this.claim.addTransition(claimState1, claimState2, claimTransition1);
		this.claim.addTransition(claimState2, claimState3, claimTransition2);
		this.claim.addTransition(claimState3, claimState3, claimTransition3);
		
		this.model = new IBAFactoryImpl<Label, State, Transition<Label>>()
				.create();
		
		StateFactory<State> modelStateFactory = new StateFactoryImpl();
		this.modelState1 = modelStateFactory.create("modelState1");
		this.modelState2 = modelStateFactory.create("modelState2");
		this.modelState3 = modelStateFactory.create("modelState3");
		this.model.addState(modelState1);
		this.model.addState(modelState2);
		this.model.addState(modelState3);
		TransitionFactory<Label, Transition<Label>> modelTransitionFactory = new ModelTransitionFactoryImpl<Label>();

		Set<Label> labelsT1Model = new HashSet<Label>();
		Set<IGraphProposition> propositionsModelT1 = new HashSet<IGraphProposition>();
		propositionsModelT1.add(new GraphProposition("a", false));
		Label labelModel1 = new LabelImplFactory().create(propositionsModelT1);
		labelsT1Model.add(labelModel1);
		this.modelTransition1 = modelTransitionFactory.create(labelsT1Model);

		Set<Label> labelsT2Model = new HashSet<Label>();
		Set<IGraphProposition> propositionsModelT2 = new HashSet<IGraphProposition>();
		propositionsModelT2.add(new GraphProposition("b", false));
		Label labelModel2 = new LabelImplFactory().create(propositionsModelT2);
		labelsT2Model.add(labelModel2);
		this.modelTransition2 = modelTransitionFactory.create(labelsT2Model);

		Set<Label> labelsT3Model = new HashSet<Label>();
		Set<IGraphProposition> propositionsModelT3 = new HashSet<IGraphProposition>();
		propositionsModelT3.add(new GraphProposition("c", false));
		Label labelModel3 = new LabelImplFactory().create(propositionsModelT3);
		labelsT3Model.add(labelModel3);
		this.modelTransition3 = modelTransitionFactory.create(labelsT3Model);

		this.model.addCharacter(label1);
		this.model.addCharacter(label2);
		this.model.addCharacter(label3);
		this.model.addTransition(modelState1, modelState2, modelTransition1);
		this.model.addTransition(modelState2, modelState3, modelTransition2);
		this.model.addTransition(modelState3, modelState3, modelTransition3);
		
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(null, it.polimi.automata.state.StateFactory, it.polimi.automata.IntersectionBAFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.IBA, it.polimi.automata.BA)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testIntersectionBuilderNullIntersectionRule() {
		new IntersectionBuilder<Label, State, Transition<Label>>(null, new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, null, it.polimi.automata.IntersectionBAFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.IBA, it.polimi.automata.BA)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testIntersectionBuilderNullStateFactory() {
		new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), null, 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, it.polimi.automata.state.StateFactory, null, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.IBA, it.polimi.automata.BA)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testIntersectionBuilderNullTransitionFactory() {
		new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				null, new IntersectionTransitionFactoryImpl<Label>(), model, claim);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, it.polimi.automata.state.StateFactory, it.polimi.automata.IntersectionBAFactory, null, it.polimi.automata.IBA, it.polimi.automata.BA)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testIntersectionBuilderNullIntersectionTransitionFactory() {
		new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), null, model, claim);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, it.polimi.automata.state.StateFactory, it.polimi.automata.IntersectionBAFactory, it.polimi.automata.transition.TransitionFactory, null, it.polimi.automata.BA)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testIntersectionBuilderNullModel() {
		new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), null, claim);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, it.polimi.automata.state.StateFactory, it.polimi.automata.IntersectionBAFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.IBA, null)}.
	 */
	@Test(expected=NullPointerException.class)
	
	public void testIntersectionBuilderNullClaim() {
		new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, null);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, it.polimi.automata.state.StateFactory, it.polimi.automata.IntersectionBAFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.IBA, it.polimi.automata.BA)}.
	 */
	@Test
	public void testIntersectionBuilder() {
		assertNotNull(new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim));
	}

	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}.
	 */
	@Test
	public void testComputeIntersection() {
		IntersectionBA<Label, State, Transition<Label>> intersection=new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim).computeIntersection();
		assertTrue(intersection.getInitialStates().isEmpty());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}.
	 */
	@Test
	public void testComputeIntersection1() {
		this.model.addInitialState(modelState1);
		IntersectionBA<Label, State, Transition<Label>> intersection=new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim).computeIntersection();
		assertTrue(intersection.getInitialStates().isEmpty());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}.
	 */
	@Test
	public void testComputeIntersection2() {
		this.claim.addInitialState(claimState1);
		IntersectionBA<Label, State, Transition<Label>> intersection=new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim).computeIntersection();
		assertTrue(intersection.getInitialStates().isEmpty());
	}

	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}.
	 */
	@Test
	public void testComputeIntersection3() {

		this.model.addInitialState(modelState1);
		this.claim.addInitialState(claimState1);
		IntersectionBA<Label, State, Transition<Label>> intersection=new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim).computeIntersection();
		assertEquals(modelState1.getName()+" - "+claimState1.getName()+" - "+0, new ArrayList<State>(intersection.getInitialStates()).get(0).getName());
		assertTrue(intersection.getStates().size()==3);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}.
	 */
	@Test
	public void testComputeIntersection4() {

		this.model.addInitialState(modelState1);
		this.model.addAcceptState(modelState3);
		this.claim.addInitialState(claimState1);
		this.claim.addAcceptState(claimState3);
		IntersectionBA<Label, State, Transition<Label>> intersection=new IntersectionBuilder<Label, State, Transition<Label>>(new IntersectionRuleImpl<Label, Transition<Label>>(), new StateFactoryImpl(), 
				new IntBAFactoryImpl<Label, State, Transition<Label>>(), new IntersectionTransitionFactoryImpl<Label>(), model, claim).computeIntersection();
		assertEquals(modelState1.getName()+" - "+claimState1.getName()+" - "+0, new ArrayList<State>(intersection.getInitialStates()).get(0).getName());
		assertTrue(intersection.getStates().size()==5);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.intersection.IntersectionBuilder#getMapModelIntersection()}.
	 */
	@Test
	public void testGetMapModelIntersection() {
		this.model.addInitialState(modelState1);
		this.model.addAcceptState(modelState3);
		this.claim.addInitialState(claimState1);
		this.claim.addAcceptState(claimState3);
		Transition<Label> int1=
				new ArrayList<Transition<Label>>(this.model.getOutTransitions(new ArrayList<State>(this.model.getInitialStates()).get(0))).get(0);
		
		assertEquals(this.labelsT1, int1.getLabels());
		Transition<Label> int2=
				new ArrayList<Transition<Label>>(this.model.getOutTransitions(this.model.getTransitionDestination(int1))).get(0);
		assertEquals(this.labelsT2, int2.getLabels());
		Transition<Label> int3=
				new ArrayList<Transition<Label>>(this.model.getOutTransitions(this.model.getTransitionDestination(int2))).get(0);
		assertEquals(this.labelsT3, int3.getLabels());
		
		Transition<Label> int4=
				new ArrayList<Transition<Label>>(this.model.getOutTransitions(this.model.getTransitionDestination(int3))).get(0);
		assertEquals(this.labelsT3, int4.getLabels());
		Transition<Label> int5=
				new ArrayList<Transition<Label>>(this.model.getOutTransitions(this.model.getTransitionDestination(int4))).get(0);
		assertEquals(this.labelsT3, int5.getLabels());
		
		
	}

}
