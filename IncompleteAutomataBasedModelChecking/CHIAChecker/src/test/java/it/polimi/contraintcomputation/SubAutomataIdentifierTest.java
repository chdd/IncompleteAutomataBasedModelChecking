/**
 * 
 */
package it.polimi.contraintcomputation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;
import it.polimi.contraintcomputation.SubAutomataIdentifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
class SubAutomataIdentifierTest {

	/*
	 * Claim 1
	 */
	private IntersectionBA<Label, State, Transition<Label>> intersectionBA;
	private State intersectionState1;
	private State intersectionState2;
	private State intersectionState3;
	private State intersectionState4;
	private State intersectionState5;
	private State intersectionState6;
	private State intersectionState7;
	private State intersectionState8;
	private Transition<Label> intersectionTransition1;
	private Transition<Label> intersectionTransition2;
	private Transition<Label> intersectionTransition3;
	private Transition<Label> intersectionTransition4;
	private Transition<Label> intersectionTransition5;
	private Transition<Label> intersectionTransition6;
	private Transition<Label> intersectionTransition7;
	private Transition<Label> intersectionTransition8;
	private Transition<Label> intersectionTransition9;
	private Transition<Label> intersectionTransition10;
	private Transition<Label> intersectionTransition11;

	
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
		intersectionState4 = factory.create("intersectionState4");
		intersectionState5 = factory.create("intersectionState5");
		intersectionState6 = factory.create("intersectionState6");
		intersectionState7 = factory.create("intersectionState7");
		intersectionState8 = factory.create("intersectionState8");
		
		this.intersectionBA.addState(intersectionState1);
		this.intersectionBA.addState(intersectionState2);
		this.intersectionBA.addState(intersectionState3);
		this.intersectionBA.addState(intersectionState4);
		this.intersectionBA.addState(intersectionState5);
		this.intersectionBA.addState(intersectionState6);
		this.intersectionBA.addState(intersectionState7);
		this.intersectionBA.addState(intersectionState8);
		
		transitionFactory = new ClaimTransitionFactoryImpl<Label>();

		intersectionTransition1 = transitionFactory.create();
		intersectionTransition2 = transitionFactory.create();
		intersectionTransition3 = transitionFactory.create();
		intersectionTransition4 = transitionFactory.create();
		intersectionTransition5 = transitionFactory.create();
		intersectionTransition6 = transitionFactory.create();
		intersectionTransition7 = transitionFactory.create();
		intersectionTransition8 = transitionFactory.create();
		intersectionTransition9 = transitionFactory.create();
		intersectionTransition10 = transitionFactory.create();
		intersectionTransition11 = transitionFactory.create();

		this.intersectionBA.addTransition(intersectionState1, intersectionState2, intersectionTransition1);
		this.intersectionBA.addTransition(intersectionState2, intersectionState3, intersectionTransition2);
		this.intersectionBA.addTransition(intersectionState3, intersectionState4, intersectionTransition3);
		this.intersectionBA.addTransition(intersectionState2, intersectionState4, intersectionTransition4);
		this.intersectionBA.addTransition(intersectionState4, intersectionState5, intersectionTransition5);
		this.intersectionBA.addTransition(intersectionState5, intersectionState6, intersectionTransition6);
		this.intersectionBA.addTransition(intersectionState6, intersectionState7, intersectionTransition7);
		this.intersectionBA.addTransition(intersectionState7, intersectionState5, intersectionTransition8);
		this.intersectionBA.addTransition(intersectionState7, intersectionState8, intersectionTransition9);
		this.intersectionBA.addTransition(intersectionState8, intersectionState4, intersectionTransition10);
		this.intersectionBA.addTransition(intersectionState1, intersectionState4, intersectionTransition11);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.SubAutomataIdentifier#SubAutomataIdentifier(null, java.util.Map)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testSubAutomataIdentifierNullIntersectionAutomata() {
		new SubAutomataIdentifier<Label, State, Transition<Label>>(null, new HashMap<State, Set<State>>());
	}

	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.SubAutomataIdentifier#SubAutomataIdentifier(it.polimi.automata.IntersectionBA, java.util.Map)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testSubAutomataIdentifierNullMap() {
		new SubAutomataIdentifier<Label, State, Transition<Label>>(this.intersectionBA, null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.SubAutomataIdentifier#SubAutomataIdentifier(it.polimi.automata.IntersectionBA, java.util.Map)}.
	 */
	@Test
	public void testSubAutomataIdentifier() {
		assertNotNull(new SubAutomataIdentifier<Label, State, Transition<Label>>(this.intersectionBA, new HashMap<State, Set<State>>()));
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.SubAutomataIdentifier#getSubAutomata()}.
	 */
	@Test
	public void testGetSubAutomata() {
		this.intersectionBA.addInitialState(this.intersectionState1);
		Map<State, Set<State>> map=new HashMap<State, Set<State>>();
		Set<State> set1=new HashSet<State>();
		set1.add(intersectionState2);
		set1.add(intersectionState3);
		set1.add(intersectionState4);
		
		Set<State> set2=new HashSet<State>();
		set2.add(intersectionState6);
		set2.add(intersectionState7);
		
		Set<State> set=new HashSet<State>();
		set.addAll(set1);
		set.addAll(set2);
		
		State modelState=this.factory.create("modelState");
		map.put(modelState, set);
		
		SubAutomataIdentifier<Label, State, Transition<Label>> subAutomataIdentifier=new SubAutomataIdentifier<Label, State, Transition<Label>>(this.intersectionBA, map);
		Map<State, Set<Set<State>>> returnedMap=subAutomataIdentifier.getSubAutomata();
		assertTrue(returnedMap.containsKey(modelState));
		
		assertTrue(returnedMap.get(modelState).contains(set1));
		assertTrue(returnedMap.get(modelState).contains(set2));
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.SubAutomataIdentifier#getSubAutomata()}.
	 */
	@Test
	public void testGetSubAutomata2() {
		this.intersectionBA.addInitialState(this.intersectionState1);
		Map<State, Set<State>> map=new HashMap<State, Set<State>>();
		Set<State> set1=new HashSet<State>();
		set1.add(intersectionState2);
		set1.add(intersectionState3);
		set1.add(intersectionState4);
		
		Set<State> set2=new HashSet<State>();
		set2.add(intersectionState6);
		set2.add(intersectionState7);
		
		
		State modelState1=this.factory.create("modelState1");
		map.put(modelState1, set1);
		
		State modelState2=this.factory.create("modelState2");
		map.put(modelState2, set2);
		
		SubAutomataIdentifier<Label, State, Transition<Label>> subAutomataIdentifier=new SubAutomataIdentifier<Label, State, Transition<Label>>(this.intersectionBA, map);
		Map<State, Set<Set<State>>> returnedMap=subAutomataIdentifier.getSubAutomata();
		assertTrue(returnedMap.containsKey(modelState1));
		assertTrue(returnedMap.containsKey(modelState2));
		
		assertTrue(returnedMap.get(modelState1).contains(set1));
		assertTrue(returnedMap.get(modelState2).contains(set2));
		
		assertTrue(returnedMap.get(modelState1).size()==1);
		assertTrue(returnedMap.get(modelState2).size()==1);
	}

	@Test
	public void testGetIntersectionStateClusterMap() throws Exception {
		this.intersectionBA.addInitialState(this.intersectionState1);
		Map<State, Set<State>> map=new HashMap<State, Set<State>>();
		Set<State> set1=new HashSet<State>();
		set1.add(intersectionState2);
		set1.add(intersectionState3);
		set1.add(intersectionState4);
		
		Set<State> set2=new HashSet<State>();
		set2.add(intersectionState6);
		set2.add(intersectionState7);
		
		
		State modelState1=this.factory.create("modelState1");
		map.put(modelState1, set1);
		
		State modelState2=this.factory.create("modelState2");
		map.put(modelState2, set2);
		
		SubAutomataIdentifier<Label, State, Transition<Label>> subAutomataIdentifier=new SubAutomataIdentifier<Label, State, Transition<Label>>(this.intersectionBA, map);
		subAutomataIdentifier.getSubAutomata();
		Map<State, Set<State>> returnedMap=subAutomataIdentifier.getIntersectionStateClusterMap();
		
		assertTrue(returnedMap.get(intersectionState2).equals(set1));
		assertTrue(returnedMap.get(intersectionState3).equals(set1));
		assertTrue(returnedMap.get(intersectionState4).equals(set1));
		
		
		assertTrue(returnedMap.get(intersectionState6).equals(set2));
		assertTrue(returnedMap.get(intersectionState7).equals(set2));
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.Aggregator#aggregate()}.
	 */
	@Test
	public void testGetIntersectionStateClusterMap2() {
		
		this.intersectionBA.addInitialState(this.intersectionState1);
		Map<State, Set<State>> map=new HashMap<State, Set<State>>();
		Set<State> set1=new HashSet<State>();
		set1.add(intersectionState2);
		set1.add(intersectionState3);
		set1.add(intersectionState4);
		
		this.intersectionBA.addMixedState(intersectionState2);
		this.intersectionBA.addMixedState(intersectionState3);
		this.intersectionBA.addMixedState(intersectionState4);
		
		
		Set<State> set2=new HashSet<State>();
		set2.add(intersectionState6);
		set2.add(intersectionState7);
		
		this.intersectionBA.addMixedState(intersectionState6);
		this.intersectionBA.addMixedState(intersectionState7);
		
		State modelState1=this.factory.create("modelState1");
		map.put(modelState1, set1);
		
		State modelState2=this.factory.create("modelState2");
		map.put(modelState2, set2);
		
		SubAutomataIdentifier<Label, State, Transition<Label>> subAutomataIdentifier=new SubAutomataIdentifier<Label, State, Transition<Label>>(this.intersectionBA, map);
		Map<State, Set<State>> modelIntersectionStatesMap=subAutomataIdentifier.getModelStateClusterMap();
		
		/*
		 * returns the set of the components (set of states) that correspond to
		 * the parts of the automaton that refer to different states of the
		 * model
		 */
		SubAutomataIdentifier<Label, State, Transition<Label>> subautomataIdentifier=new SubAutomataIdentifier<Label, State, Transition<Label>>(
				this.intersectionBA, modelIntersectionStatesMap);
		Map<State, Set<Set<State>>> modelStateSubAutomataMap =subautomataIdentifier.getSubAutomata();
		/*
		 * The abstraction of the state space is a more concise version of the
		 * intersection automaton I where the portions of the state space which
		 * do not correspond to transparent states are removed
		 */
		
		assertTrue(modelStateSubAutomataMap.containsKey(modelState1));
		assertTrue(modelStateSubAutomataMap.containsKey(modelState2));
		assertTrue(modelStateSubAutomataMap.get(modelState1).contains(set1));
		assertTrue(modelStateSubAutomataMap.get(modelState2).contains(set2));
		assertTrue(modelStateSubAutomataMap.get(modelState1).size()==1);
		assertTrue(modelStateSubAutomataMap.get(modelState2).size()==1);
		
		Map<State, Set<State>> intersectionStateClusterMap=subautomataIdentifier.getIntersectionStateClusterMap();
		assertTrue(intersectionStateClusterMap.containsKey(intersectionState2));
		assertTrue(intersectionStateClusterMap.containsKey(intersectionState3));
		assertTrue(intersectionStateClusterMap.containsKey(intersectionState4));
		assertTrue(intersectionStateClusterMap.containsKey(intersectionState6));
		assertTrue(intersectionStateClusterMap.containsKey(intersectionState7));
		
		assertTrue(intersectionStateClusterMap.get(intersectionState2).equals(set1));
		assertTrue(intersectionStateClusterMap.get(intersectionState3).equals(set1));
		assertTrue(intersectionStateClusterMap.get(intersectionState4).equals(set1));
		assertTrue(intersectionStateClusterMap.get(intersectionState6).equals(set2));
		assertTrue(intersectionStateClusterMap.get(intersectionState7).equals(set2));
		
	}

}
