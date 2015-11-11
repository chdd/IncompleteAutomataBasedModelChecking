/**
 * 
 */
package it.polimi.checker.emptiness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class EmptinessCheckerTest {

	private BA ba;
	private State state1;
	private State state2;
	private State state3;
	private Transition transition1;
	private Transition transition2;
	private Transition transition3;
	
	@Before
	public void setUp() {
		TransitionFactory<State, Transition> transitionFactory=new ModelTransitionFactory();
		
		this.ba=new IBA(transitionFactory);
		StateFactory factory=new StateFactory();
		state1=factory.create("1",1);
		state2=factory.create("2",2);
		state3=factory.create("3",3);
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		transition1=transitionFactory.create();
		transition2=transitionFactory.create();
		transition3=transitionFactory.create();
	}
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#EmptinessChecker(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testEmptinessCheckerNull() {
		new EmptinessChecker(null);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#EmptinessChecker(it.polimi.automata.BA)}.
	 */
	@Test
	public void testEmptinessChecker() {
		assertNotNull(new EmptinessChecker(this.ba));
	}

	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty0() {
		
		this.ba=new BA(new ClaimTransitionFactory());
		this.ba.addInitialState(state1);
		this.ba.addAcceptState(state1);
		this.ba.addTransition(state1, state1, transition1);
		
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba);
		assertFalse("A single automaton with a selfloop is not empty", emptinessChecker.isEmpty());
		
		List<Entry<State, Transition>> counterexample=new ArrayList<Entry<State, Transition>>();
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state1, transition1));
		
		assertEquals("The counterexample of a single automaton with a selfloop is not empty", counterexample, emptinessChecker.getCounterExample());
	}
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty1() {
		
		this.ba=new BA(new ClaimTransitionFactory());
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state3, transition3);
		
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba); 
		assertTrue("An automaton with no initial states is empty", emptinessChecker.isEmpty());
	}
	
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty2() {
		
		this.ba=new BA(new ClaimTransitionFactory());
		this.ba.addInitialState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state3, transition3);
		
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba); 
		assertTrue("An automaton with no accepting states is empty", emptinessChecker.isEmpty());
		
		
	}
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty3() {
		
		this.ba=new BA(new ClaimTransitionFactory());
		this.ba.addInitialState(state1);
		this.ba.addAcceptState(state2);
		this.ba.addState(state3);
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state3, transition3);
		
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba); 
		assertTrue("An automaton where it is not possible to enter an accepting states infinitely often is empty", emptinessChecker.isEmpty());
		
		
		
	}
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty4() {
		
		this.ba=new BA(new ClaimTransitionFactory());
		this.ba.addInitialState(state1);
		this.ba.addAcceptState(state2);
		this.ba.addState(state3);
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state3, transition3);
		
		this.ba.addAcceptState(state3);
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba); 
		assertFalse("An automaton where it is not possible to enter an accepting states infinitely often is not empty", emptinessChecker.isEmpty());
		
		List<Entry<State, Transition>> counterexample=new ArrayList<Entry<State, Transition>>();
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state1, transition1));
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state2, transition2));
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state3, transition3));
		
		assertEquals(counterexample, emptinessChecker.getCounterExample());
		
	}
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty5() {
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state2, transition3);
		
		assertTrue(new EmptinessChecker(this.ba).isEmpty());
		this.ba.addInitialState(state1);
		assertTrue(new EmptinessChecker(this.ba).isEmpty());
		
	}
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty6() {
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state2, transition3);
		
		this.ba.addInitialState(state1);
		this.ba.addAcceptState(state2);
		
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba); 
		
		assertFalse(emptinessChecker.isEmpty());
	
		List<Entry<State, Transition>> counterexample=new ArrayList<Entry<State, Transition>>();
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state1, transition1));
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state2, transition2));
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state3, transition3));
		
		assertEquals(counterexample, emptinessChecker.getCounterExample());
	}
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty7() {
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state2, transition3);
		
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba); 
		
		this.ba.addInitialState(state1);
		this.ba.addAcceptState(state3);
		assertFalse(emptinessChecker.isEmpty());
		
		List<Entry<State, Transition>> counterexample=new ArrayList<Entry<State, Transition>>();
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state1, transition1));
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state2, transition2));
		counterexample.add(new AbstractMap.SimpleEntry<State, Transition>(state3, transition3));
		
		assertEquals(counterexample, emptinessChecker.getCounterExample());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#getCounterExample()}.
	 */
	@Test(expected=InternalError.class)
	public void testgetCounterexample() {
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state2, transition3);
		
		EmptinessChecker emptinessChecker=new EmptinessChecker(this.ba); 
		emptinessChecker.getCounterExample();
	}

}
