/**
 * 
 */
package it.polimi.checker.ibatransparentstateremoval;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.IBA;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class IBATransparentStateRemovalTest {

	private IBA<Label, State, Transition<Label>> ba;
	private State state1;
	private State state2;
	private State state3;
	private Transition<Label> transition1;
	private Transition<Label> transition2;
	private Transition<Label> transition3;
	
	
	@Before
	public void setUp() {
		this.ba=new IBAFactoryImpl<Label, State, Transition<Label>>().create();
		StateFactory<State> factory=new StateFactoryImpl();
		state1=factory.create();
		state2=factory.create();
		state3=factory.create();
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		
		TransitionFactory<Label, Transition<Label>> transitionFactory=new ModelTransitionFactoryImpl<Label>();
		transition1=transitionFactory.create();
		transition2=transitionFactory.create();
		transition3=transitionFactory.create();
		this.ba.addTransition(state1, state2, transition1);
		this.ba.addTransition(state2, state3, transition2);
		this.ba.addTransition(state3, state2, transition3);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval#transparentStateRemoval(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransparentStateRemovalNull() {
		new IBATransparentStateRemoval<Label, State, Transition<Label>>().transparentStateRemoval(null);
	}

	
	/**
	 * Test method for {@link it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval#transparentStateRemoval(it.polimi.automata.IBA)}.
	 */
	@Test
	public void testTransparentStateRemoval() {

		IBA<Label, State, Transition<Label>> ret=new IBATransparentStateRemoval<Label, State, Transition<Label>>().transparentStateRemoval(this.ba);
		assertTrue(ret.getStates().contains(state1));
		assertTrue(ret.getStates().contains(state2));
		assertTrue(ret.getStates().contains(state3));
		assertTrue(ret!=ba);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval#transparentStateRemoval(it.polimi.automata.IBA)}.
	 */
	@Test
	public void testTransparentStateRemoval2() {

		this.ba.addTransparentState(state2);
		IBA<Label, State, Transition<Label>> ret=new IBATransparentStateRemoval<Label, State, Transition<Label>>().transparentStateRemoval(this.ba);
		assertTrue(ret.getStates().contains(state1));
		assertFalse(ret.getStates().contains(state2));
		assertTrue(ret.getStates().contains(state3));
		assertTrue(ret!=ba);
	}

}
