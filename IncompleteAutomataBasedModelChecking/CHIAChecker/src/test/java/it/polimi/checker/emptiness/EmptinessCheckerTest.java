/**
 * 
 */
package it.polimi.checker.emptiness;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

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
		state1=factory.create();
		state2=factory.create();
		state3=factory.create();
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
	public void testIsEmpty() {
		
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state3, transition3);
		
		assertTrue(new EmptinessChecker(this.ba).isEmpty());
		this.ba.addInitialState(state1);
		assertTrue(new EmptinessChecker(this.ba).isEmpty());
		this.ba.addAcceptState(state2);
		assertTrue(new EmptinessChecker(this.ba).isEmpty());
		this.ba.addAcceptState(state3);
		assertFalse(new EmptinessChecker(this.ba).isEmpty());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.emptiness.EmptinessChecker#isEmpty()}.
	 */
	@Test
	public void testIsEmpty2() {
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state2, transition3);
		
		assertTrue(new EmptinessChecker(this.ba).isEmpty());
		this.ba.addInitialState(state1);
		assertTrue(new EmptinessChecker(this.ba).isEmpty());
		this.ba.addAcceptState(state2);
		assertFalse(new EmptinessChecker(this.ba).isEmpty());
		this.ba.addAcceptState(state3);
		assertFalse(new EmptinessChecker(this.ba).isEmpty());
	}

}
