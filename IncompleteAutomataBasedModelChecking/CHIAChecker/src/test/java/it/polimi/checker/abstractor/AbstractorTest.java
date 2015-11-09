/**
 * 
 */
package it.polimi.checker.abstractor;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author Claudio Menghi
 *
 */
public class AbstractorTest {

	/**
	 * Test method for {@link it.polimi.checker.abstractor.Abstractor#perform(it.polimi.automata.BA, java.util.Set)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testPerformNullBa() {
		new Abstractor<BA>().perform(null, new HashSet<State>());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.abstractor.Abstractor#perform(it.polimi.automata.BA, java.util.Set)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testPerformNullStates() {
		new Abstractor<BA>().perform(new BA(new ClaimTransitionFactory()), null);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.abstractor.Abstractor#perform(it.polimi.automata.BA, java.util.Set)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPerformStatesNotcontainedInTheBA() {
		Set<State> states=new HashSet<State>();
		states.add(new StateFactory().create());
		new Abstractor<BA>().perform(new BA(new ClaimTransitionFactory()), states);
	}
	/**
	 * Test method for {@link it.polimi.checker.abstractor.Abstractor#perform(it.polimi.automata.BA, java.util.Set)}.
	 */
	@Test
	public void testPerform() {
		
		
		Set<State> states=new HashSet<State>();
		
		TransitionFactory<State, Transition> transtitionFactory=new ClaimTransitionFactory();
		BA ba=new BA(new ClaimTransitionFactory());
		
		State state1=new StateFactory().create("1", 1);
		State state2=new StateFactory().create("2", 2);
		State state3=new StateFactory().create("3", 3);
		State state4=new StateFactory().create("4", 4);
		State state5=new StateFactory().create("5", 5);
		
		Transition transition1=transtitionFactory.create(1, new HashSet<IGraphProposition>());
		Transition transition2=transtitionFactory.create(2, new HashSet<IGraphProposition>());
		Transition transition3=transtitionFactory.create(3, new HashSet<IGraphProposition>());
		Transition transition4=transtitionFactory.create(4, new HashSet<IGraphProposition>());
		Transition transition5=transtitionFactory.create(5, new HashSet<IGraphProposition>());
		Transition transition6=transtitionFactory.create(6, new HashSet<IGraphProposition>());
		Transition transition7=transtitionFactory.create(7, new HashSet<IGraphProposition>());
		Transition transition8=transtitionFactory.create(8, new HashSet<IGraphProposition>());
		Transition transition9=transtitionFactory.create(9, new HashSet<IGraphProposition>());
		
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addState(state3);
		ba.addState(state4);
		ba.addAcceptState(state5);
		
		ba.addTransition(state1, state2, transition1);
		ba.addTransition(state3, state2, transition2);
		ba.addTransition(state4, state5, transition3);
		ba.addTransition(state5, state1, transition4);
		ba.addTransition(state2, state3, transition5);
		ba.addTransition(state4, state2, transition6);
		ba.addTransition(state5, state5, transition7);
		ba.addTransition(state1, state4, transition8);
		ba.addTransition(state3, state5, transition9);
		
		states.add(state1);
		states.add(state3);
		states.add(state5);
		BA anstractedBa=new Abstractor<BA>().perform(ba, states);
		
		System.out.println(anstractedBa);
		assertTrue("The abstracted automaton contains three states", anstractedBa.getStates().size()==3);
		assertTrue("The abstracted automaton contains the initial state state1", anstractedBa.getInitialStates().contains(state1));
		assertTrue("The abstracted automaton contains the state state2", anstractedBa.getStates().contains(state3));
		assertTrue("The abstracted automaton contains the accepting state state5", anstractedBa.getAcceptStates().contains(state5));
		
		System.out.println(anstractedBa.getTransitions());
		assertTrue("The abstracted automaton contains the accepting state state5", anstractedBa.getTransitions().size()==3);
		assertTrue(anstractedBa.isSuccessor(state5, state1));
		assertTrue(anstractedBa.isSuccessor(state5, state5));
		assertTrue(anstractedBa.isSuccessor(state3, state5));
		
		assertFalse(anstractedBa.getTransitions().contains(transition1));
		assertFalse(anstractedBa.getTransitions().contains(transition2));
		assertFalse(anstractedBa.getTransitions().contains(transition3));
		assertTrue(anstractedBa.getTransitions().contains(transition4));
		assertFalse(anstractedBa.getTransitions().contains(transition5));
		assertFalse(anstractedBa.getTransitions().contains(transition6));
		assertTrue(anstractedBa.getTransitions().contains(transition7));
		assertFalse(anstractedBa.getTransitions().contains(transition8));
		assertTrue(anstractedBa.getTransitions().contains(transition9));
	}

}
